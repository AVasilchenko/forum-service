package telran.java48.account.dto;

import java.util.List;
import java.util.Set;

import lombok.Getter;
import telran.java48.security.model.Role;

@Getter
public class ChangeRolesDto {
	String login;
    Set<Role> roles;
}
