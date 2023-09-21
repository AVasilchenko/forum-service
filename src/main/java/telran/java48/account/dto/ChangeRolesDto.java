package telran.java48.account.dto;

import java.util.List;

import lombok.Getter;

@Getter
public class ChangeRolesDto {
	String login;
    List<String> roles;
}
