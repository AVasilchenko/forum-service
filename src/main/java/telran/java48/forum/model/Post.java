package telran.java48.forum.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
public class Post {
		String id;
		@Setter
		String title;
		@Setter
		String content;
		@Setter
		String author;
	    LocalDateTime dateCreated = LocalDateTime.now();
	    List<String> tags;
	    @Setter
	    long likes;
	    List<Comment> comments = new ArrayList<>();
	    
		public Post(String title, String content, String author, List<String> tags, List<Comment> comments) {
			this.title = title;
			this.content = content;
			this.author = author;
			this.tags = tags;
		}
	    
		public void addTag(List<String> newTags) {
			tags.addAll(newTags);
		}
		
		public void addComment(Comment newComment) {
			comments.add(newComment);
		}
		
		public long addLike() {
			return ++likes;
		}
	    
}

