package telran.java48.security;

import java.io.PipedOutputStream;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import telran.java48.forum.dao.ForumRepository;
import telran.java48.forum.model.Post;

@Service
@RequiredArgsConstructor
public class CustomWebSecurity {
	final ForumRepository forumRepository;
	
	
	public boolean checkPostAuthor(String postId, String userName) {
		Post post = forumRepository.findById(postId).orElse(null);
		return post != null && userName.equalsIgnoreCase(post.getAuthor());
	}

}
