package telran.java48.account.controller;

import java.security.Principal;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
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
	public UserDto login(Principal principal) {  //интерф у которого есть метод гет юзер, это юзер после аутентификации
		return getUser(principal.getName());
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
		return accountService.changeRole(login, role, true);
	}

	@DeleteMapping("/user/{login}/role/{role}")
	public ChangeRolesDto deletRole(@PathVariable String login,@PathVariable String role) {
		return accountService.changeRole(login, role, false);
	}

	@PutMapping("/password")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void changePassword(Principal principal, @RequestHeader("X-Password") String newPassword) {
		accountService.changePassword(principal.getName(), newPassword);
	}

	@GetMapping("/user/{login}")
	public UserDto getUser(@PathVariable String login) {
		return accountService.getUser(login);
	}

}
