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
import telran.java48.security.model.Role;

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
    Set<Role> roles = new HashSet<>();
    
    
	public User(String login, String password, String firstName, String lastName) {
		this.login = login;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.roles.add(Role.USER);
	}
    
	public Boolean addRole(Role role) {
		return roles.add(role);
	}
	
	public Boolean deleteRole(Role role) {
		return roles.remove(role);
	}
    
}
