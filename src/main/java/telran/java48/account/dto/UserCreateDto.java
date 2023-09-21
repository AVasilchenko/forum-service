package telran.java48.account.dto;

import lombok.Getter;

@Getter
public class UserCreateDto {
	String login;
	String password;
    String firstName;
    String lastName;
}
