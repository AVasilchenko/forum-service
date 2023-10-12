package telran.java48.account.service;

import telran.java48.account.dto.ChangeRolesDto;
import telran.java48.account.dto.UserCreateDto;
import telran.java48.account.dto.UserDto;
import telran.java48.account.dto.UserUpdateDto;
import telran.java48.security.model.Role;

public interface AccountService {
	UserDto register(UserCreateDto userCreateDto);
//	UserDto login(String login, String password);
	UserDto deleteUser(String login);
	UserDto updateUser(UserUpdateDto userUpdateDto, String login);
//	ChangeRolesDto addRole(String login, String role);
//	ChangeRolesDto deletRole(String login, String role);
	ChangeRolesDto changeRole(String login, Role role, Boolean change);
	void changePassword(String login, String newPassvord);
	UserDto getUser(String login);
}

