package telran.java48.forum.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import telran.java48.forum.dao.ForumRepository;
import telran.java48.forum.dto.CommentCreateDto;
import telran.java48.forum.dto.PostCreateDto;
import telran.java48.forum.dto.PostDateDto;
import telran.java48.forum.dto.PostDto;
import telran.java48.forum.dto.PostUpdateDto;
import telran.java48.forum.dto.exception.PostNotFoundException;
import telran.java48.forum.model.Comment;
import telran.java48.forum.model.Post;

@Service
@RequiredArgsConstructor
public class ForumServiceImpl implements ForumService {

	final ForumRepository forumRepository;
	final ModelMapper modelMapper;

	@Override
	public PostDto addPost(String author, PostCreateDto postCreateDto) {
		Post post = modelMapper.map(postCreateDto, Post.class);
		post.setAuthor(author);
		post = forumRepository.save(post);
		return modelMapper.map(post, PostDto.class);
	}

	@Override
	public PostDto findPostById(String id) {
		Post post = forumRepository.findById(id).orElseThrow(PostNotFoundException::new);
		return modelMapper.map(post, PostDto.class);
	}

	@Override
	public long addLike(String id) {
		Post post = forumRepository.findById(id).orElseThrow(PostNotFoundException::new);
		long likes = post.addLike();
		post.setLikes(likes);
		forumRepository.save(post);
		return likes;
	}

	@Override
	public List<PostDto> findPostsByAuthor(String author) {
		return forumRepository.findByAuthorIgnoreCase(author)
				.map(p -> modelMapper.map(p, PostDto.class))
				.collect(Collectors.toList());
	}

	@Override
	public PostDto addComment(String id, String author, CommentCreateDto commentCreateDto) {
		Post post = forumRepository.findById(id).orElseThrow(PostNotFoundException::new);
		Comment comment = modelMapper.map(commentCreateDto, Comment.class);
		comment.setAuthor(author);
		post.addComment(comment);
		forumRepository.save(post);
		return modelMapper.map(post, PostDto.class);
	}

	@Override
	public PostDto deletePost(String id) {
		Post post = forumRepository.findById(id).orElseThrow(PostNotFoundException::new);
		forumRepository.deleteById(id);
		return modelMapper.map(post, PostDto.class);
	}

	@Override
	public List<PostDto> findPostsByTags(List<String> tags) {
		return forumRepository.findByTagsInIgnoreCase(tags)
				.map(p -> modelMapper.map(p, PostDto.class))
				.collect(Collectors.toList());
	}

	@Override
	public List<PostDto> findPostsByPeriod(PostDateDto period) {
		return forumRepository.findByDateCreatedBetween(period.getDateFrom(), period.getDateTo())
				.map(p -> modelMapper.map(p, PostDto.class))
				.collect(Collectors.toList());
	}

	@Override
	public PostDto updatePostDto(String id, PostUpdateDto postUpdateDto) {
		Post post = forumRepository.findById(id).orElseThrow(PostNotFoundException::new);
		if (postUpdateDto.getTitle() != null) {
			post.setTitle(postUpdateDto.getTitle());
		}
		if (postUpdateDto.getContent() != null) {
			post.setContent(postUpdateDto.getContent());
		}
		if (postUpdateDto.getTags() != null) {
			post.addTag(postUpdateDto.getTags());
		}
		forumRepository.save(post);
		return modelMapper.map(post, PostDto.class);
	}

}
