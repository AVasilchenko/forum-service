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
import telran.java48.forum.dao.ForumRepository;
import telran.java48.forum.model.Post;
import telran.java48.security.model.Role;
import telran.java48.security.model.UserPr;

@Component
@Order(60)
@RequiredArgsConstructor
public class DeletePostByOwnerModeratorFilter implements Filter {
	final ForumRepository forumRepository;
	
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;

		if (checkEndPoint(request.getMethod(), request.getServletPath())) {
			UserPr user = (UserPr) request.getUserPrincipal();
			String[] arr = request.getServletPath().split("/");
			String id = arr[arr.length - 1];
			Post post = forumRepository.findById(id).orElse(null);
			if(post == null) {
				response.sendError(404);
				return;
			}
			if((!user.getName().equalsIgnoreCase(post.getAuthor())) && (!user.getRoles().contains(Role.MODERATOR))) {
				response.sendError(403);
				return;
			}
		}
		chain.doFilter(request, response);
	}

	private boolean checkEndPoint(String method, String path) {
		return HttpMethod.DELETE.matches(method) && path.matches("/forum/post/\\w+/?");
	}

}
