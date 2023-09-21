package telran.java48.account.service;

import telran.java48.account.dto.ChangeRolesDto;
import telran.java48.account.dto.UserCreateDto;
import telran.java48.account.dto.UserDto;
import telran.java48.account.dto.UserUpdateDto;

public interface AccountService {
	UserDto register(UserCreateDto userCreateDto);
	UserDto login();
	UserDto deleteUser(String login);
	UserDto updateUser(UserUpdateDto userUpdateDto, String login);
	ChangeRolesDto addRole(String login, String role);
	ChangeRolesDto deletRole(String login, String role);
	Boolean changePassword();
	UserDto getUser(String login);
}

