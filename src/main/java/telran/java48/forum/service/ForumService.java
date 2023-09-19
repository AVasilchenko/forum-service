package telran.java48.forum.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import telran.java48.forum.dto.CommentCreateDto;
import telran.java48.forum.dto.PostCreateDto;
import telran.java48.forum.dto.PostDateDto;
import telran.java48.forum.dto.PostDto;
import telran.java48.forum.dto.PostUpdateDto;

public interface ForumService {
	PostDto addPost(String author, PostCreateDto postCreateDto);
	PostDto findPostById(String id);
	
	@ResponseStatus(HttpStatus.NO_CONTENT)
	long addLike(String id);
	
	List<PostDto> findPostsByAuthor(String author);
	PostDto addComment(String id, String author, CommentCreateDto commentCreateDto);
	PostDto deletePost(String id);
	List<PostDto> findPostsByTags(List<String> tags);
	List<PostDto> findPostsByPeriod(PostDateDto period);
	PostDto updatePostDto (String id, PostUpdateDto postUpdateDto);
}
