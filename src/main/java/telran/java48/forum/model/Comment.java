package telran.java48.forum.model;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
public class Comment {
	@Setter
	String author;
	@Setter
    String message;
    LocalDateTime dateCreated  = LocalDateTime.now();
    @Setter
    long likes;
    
    
	public Comment(String author, String message) {
		this.author = author;
		this.message = message;
	}
    
	public void addLike() {
		likes++;
	}
    
}
