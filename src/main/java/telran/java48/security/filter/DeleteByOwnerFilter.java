package telran.java48.security.filter;

import java.io.IOException;
import java.security.Principal;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import telran.java48.account.dao.UserRepository;
import telran.java48.account.model.User;
import telran.java48.security.model.Role;
import telran.java48.security.model.UserPr;

@Component
@Order(40)
public class DeleteByOwnerFilter implements Filter{

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;

		if (checkEndPoint(request.getMethod(), request.getServletPath())) {
			UserPr user = (UserPr) request.getUserPrincipal();
			String[] arr = request.getServletPath().split("/");
			String login = arr[arr.length - 1];
			if((!user.getName().equalsIgnoreCase(login)) && (!user.getRoles().contains(Role.ADMINISTRATOR))) {
				response.sendError(403);
				return;
			}
		}
		chain.doFilter(request, response);
	}

	private boolean checkEndPoint(String method, String path) {
		return HttpMethod.DELETE.matches(method) && path.matches("/account/user/\\w+/?");
	}
		

}
