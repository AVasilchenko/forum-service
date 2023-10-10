package telran.java48.security.filter;

import java.io.IOException;
import java.security.Principal;
import java.util.Base64;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import telran.java48.account.dao.UserRepository;
import telran.java48.account.model.User;

@Component
@RequiredArgsConstructor
@Order(10)
public class AuthenticationFilter implements Filter {

	final UserRepository userRepository;
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
//		System.out.println(request.getServletPath() + " -> " + request.getMethod());
	//	System.out.println(request.getHeader("Authorization"));
		if (checkEndPoint(request.getMethod(), request.getServletPath())) {
			try {
				String[] credentials = getCredentials(request.getHeader("Authorization"));
				User user = userRepository.findById(credentials[0]).orElseThrow(RuntimeException::new);
				if (!BCrypt.checkpw(credentials[1], user.getPassword())) {
					throw new RuntimeException();
				}
				request = new WrappedRequest(request, user.getLogin());
			} catch (Exception e) {
				response.sendError(401);
				return;
			} 
			
		}
		chain.doFilter(request, response);

	}

	private boolean checkEndPoint(String method, String path) {
		
		return !(HttpMethod.POST.matches(method) && path.matches("/account/register/?")) && 
			   !(HttpMethod.POST.matches(method) && path.matches("/forum/posts/?\\w+/?")) &&
			   !(HttpMethod.GET.matches(method) && path.matches("/forum/posts/author/?\\w+/?"));
	}

	private String[] getCredentials(String header) {
		String token = header.substring(6);
		String decode = new String(Base64.getDecoder().decode(token));
		return decode.split(":");
	}

	private class WrappedRequest extends HttpServletRequestWrapper{
		String login;

		public WrappedRequest(HttpServletRequest request, String login) {
			super(request);
			this.login = login;
		}
		
		@Override
		public Principal getUserPrincipal() {
			return () -> login;
		}
		
	}
}
