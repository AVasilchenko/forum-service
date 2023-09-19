package telran.java48.forum.dto;

import lombok.Getter;

@Getter
public class PostCreateDto {
	String title;
	String content;
	String[] tags;
}
