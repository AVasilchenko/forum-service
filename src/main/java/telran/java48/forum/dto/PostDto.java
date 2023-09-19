package telran.java48.forum.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import telran.java48.forum.model.Comment;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {
	String id;
	String title;
	String content;
	String author;
    LocalDateTime dateCreated;
    List<String> tags;
    Long likes;
    List<Comment> comments;
}

