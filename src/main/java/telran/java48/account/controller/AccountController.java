package telran.java48.account.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import telran.java48.account.dto.ChangeRolesDto;
import telran.java48.account.dto.UserCreateDto;
import telran.java48.account.dto.UserDto;
import telran.java48.account.dto.UserUpdateDto;
import telran.java48.account.service.AccountService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/account")
public class AccountController {
final AccountService accountService;
	
	@PostMapping("/register")
	public UserDto register(@RequestBody UserCreateDto userCreateDto) {
		return accountService.register(userCreateDto);
	}

	@PostMapping("/login")
	public UserDto login() {
		return accountService.login();
	}

	@DeleteMapping("/user/{login}")
	public UserDto deleteUser(@PathVariable String login) {
		return accountService.deleteUser(login);
	}

	@PutMapping("/user/{login}")
	public UserDto updateUser(@RequestBody UserUpdateDto userUpdateDto, @PathVariable String login) {
		return accountService.updateUser(userUpdateDto, login);
	}

	@PutMapping("/user/{login}/role/{role}")
	public ChangeRolesDto addRole(@PathVariable String login,@PathVariable String role) {
		return accountService.addRole(login, role);
	}

	@DeleteMapping("/user/{login}/role/{role}")
	public ChangeRolesDto deletRole(@PathVariable String login,@PathVariable String role) {
		return accountService.deletRole(login, role);
	}

	@PutMapping("/password")
	public Boolean changePassword() {
		return accountService.changePassword();
	}

	@GetMapping("/user/{login}")
	public UserDto getUser(@PathVariable String login) {
		return accountService.getUser(login);
	}

}
