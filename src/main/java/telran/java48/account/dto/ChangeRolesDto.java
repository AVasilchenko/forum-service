package telran.java48.account.dto;

import java.util.List;
import java.util.Set;

import lombok.Getter;

@Getter
public class ChangeRolesDto {
	String login;
    Set<String> roles;
}
