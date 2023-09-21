package telran.java48.account.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@EqualsAndHashCode(of = "login")
@NoArgsConstructor
public class User {
	@Id
	String login;
	@Setter
	String password;
	@Setter
    String firstName;
	@Setter
    String lastName;
	@Setter
    List<String> roles = new ArrayList<>();
    
    
	public User(String login, String password, String firstName, String lastName) {
		this.login = login;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.roles = new ArrayList<String>();
		this.roles.add("USER");
	}
    
	public void addRole(String role) {
		roles.add(role.toUpperCase());
	}
	
	public void deleteRole(String role) {
		roles.remove(role.toUpperCase());
	}
    
}
