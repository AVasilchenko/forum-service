package telran.java48.security.filter;

import java.io.IOException;
import java.security.Principal;
import java.util.Base64;
import java.util.Set;

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
import telran.java48.security.context.SecurityContext;
import telran.java48.security.model.Role;
import telran.java48.security.model.UserPr;

@Component
@RequiredArgsConstructor
@Order(10)
public class AuthenticationFilter implements Filter {

	final UserRepository userRepository;
	final SecurityContext securityContext;
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
//		System.out.println(request.getServletPath() + " -> " + request.getMethod());
	//	System.out.println(request.getHeader("Authorization"));
		if (checkEndPoint(request.getMethod(), request.getServletPath())) {
			String sessionId = request.getSession().getId();
			UserPr user = securityContext.getUserBySessionId(sessionId);
			if (user == null) {
				try {
					String[] credentials = getCredentials(request.getHeader("Authorization"));
					User userAccount = userRepository.findById(credentials[0]).orElseThrow(RuntimeException::new);
					if (!BCrypt.checkpw(credentials[1], userAccount.getPassword())) {
						throw new RuntimeException();
					}
					user = new UserPr(userAccount.getLogin(), userAccount.getRoles());
					securityContext.addUserSession(sessionId, user);
					
				} catch (Exception e) {
					response.sendError(401);
					return;
				} 
			}
			request = new WrappedRequest(request, user.getName(), user.getRoles());
			
		}
		chain.doFilter(request, response);

	}

	private boolean checkEndPoint(String method, String path) {
		
		return !(
				(HttpMethod.POST.matches(method) && path.matches("/account/register/?")) 
				|| path.matches("/forum/posts/\\w+(/\\w+)?/?")
				);
	}

	private String[] getCredentials(String header) {
		String token = header.substring(6);
		String decode = new String(Base64.getDecoder().decode(token));
		return decode.split(":");
	}

	private class WrappedRequest extends HttpServletRequestWrapper{
		String login;
		Set<Role> roles;

		public WrappedRequest(HttpServletRequest request, String login, Set<Role> roles) {
			super(request);
			this.login = login;
			this.roles = roles;
		}
		
		@Override
		public Principal getUserPrincipal() {
			return new UserPr(login, roles);
		}
		
	}
}
