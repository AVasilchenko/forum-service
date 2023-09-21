package telran.java48.account.dto;

import java.util.List;

import lombok.Getter;

@Getter
public class UserDto {
	String login;
    String firstName;
    String lastName;
    List<String> roles;
}
