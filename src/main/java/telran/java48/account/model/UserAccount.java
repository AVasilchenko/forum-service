package telran.java48.account.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.data.annotation.Id;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@EqualsAndHashCode(of = "login")
@NoArgsConstructor
public class UserAccount {
	@Id
	String login;
	@Setter
	String password;
	@Setter
    String firstName;
	@Setter
    String lastName;
	@Setter
    Set<String> roles = new HashSet<>();
    
    
	public UserAccount(String login, String password, String firstName, String lastName) {
		this.login = login;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.roles.add("USER");
	}
    
	public Boolean addRole(String role) {
		return roles.add(role);
	}
	
	public Boolean deleteRole(String role) {
		return roles.remove(role);
	}
    
}
