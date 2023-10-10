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

@Component
@Order(70)
@RequiredArgsConstructor
public class DeletePostByOwnerModeratorFilter implements Filter {
	final ForumRepository forumRepository;
	final UserRepository userRepository;
	
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;

		if (checkEndPoint(request.getMethod(), request.getServletPath())) {
			Principal principal = request.getUserPrincipal();
			String[] arr = request.getServletPath().split("/");
			String id = arr[arr.length - 1];
			Post post = forumRepository.findById(id).get();
			User user = userRepository.findById(principal.getName()).get();
			if((!principal.getName().equalsIgnoreCase(post.getAuthor())) && (!user.getRoles().contains("MODERATOR"))) {
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
