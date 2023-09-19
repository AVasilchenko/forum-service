package telran.java48.forum.controller;

import java.util.List;

import org.apache.logging.log4j.util.StringBuilderFormattable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import telran.java48.forum.dto.CommentCreateDto;
import telran.java48.forum.dto.PostCreateDto;
import telran.java48.forum.dto.PostDateDto;
import telran.java48.forum.dto.PostDto;
import telran.java48.forum.dto.PostUpdateDto;
import telran.java48.forum.service.ForumService;

@RestController
@RequiredArgsConstructor
public class ForumController{
	final ForumService forumService;
	
	@PostMapping("/forum/post/{author}")
	public PostDto addPost(@PathVariable String author, @RequestBody PostCreateDto postCreateDto) {
		return forumService.addPost(author, postCreateDto);
	}

	@GetMapping("/forum/post/{id}")
	public PostDto findPostById(@PathVariable String id) {
		return forumService.findPostById(id);
	}

	@PutMapping("/forum/post/{id}/like")
	public long addLike(@PathVariable String id) {
		return forumService.addLike(id);
	}

	@GetMapping("/forum/posts/author/{author}")
	public List<PostDto> findPostsByAuthor(@PathVariable String author) {
		return forumService.findPostsByAuthor(author);
	}

	@PutMapping("/forum/post/{id}/comment/{author}")
	public PostDto addComment(@PathVariable String id, @PathVariable String author, @RequestBody CommentCreateDto commentCreateDto) {
		return forumService.addComment(id, author, commentCreateDto);
	}

	@DeleteMapping("/forum/post/{id}")
	public PostDto deletePost(@PathVariable String id) {
		return forumService.deletePost(id);
	}

	@PostMapping("/forum/posts/tags")
	public List<PostDto> findPostsByTags(@RequestBody List<String> tags) {
		return forumService.findPostsByTags(tags);
	}

	@PostMapping("/forum/posts/period")
	public List<PostDto> findPostsByPeriod(@RequestBody PostDateDto period) {
		return forumService.findPostsByPeriod(period);
	}

	@PutMapping("/forum/post/{id}")
	public PostDto updatePostDto(@PathVariable String id, @RequestBody PostUpdateDto postUpdateDto) {
		return forumService.updatePostDto(id, postUpdateDto);
	}
	
}
