package telran.java48.account.service;


import org.mindrot.jbcrypt.BCrypt;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import telran.java48.account.dao.UserRepository;
import telran.java48.account.dto.ChangeRolesDto;
import telran.java48.account.dto.UserCreateDto;
import telran.java48.account.dto.UserDto;
import telran.java48.account.dto.UserUpdateDto;
import telran.java48.account.dto.exception.UserExistsException;
import telran.java48.account.dto.exception.UserNotFoundException;
import telran.java48.account.model.UserAccount;


@Service
@RequiredArgsConstructor
public class accountServiceImpl implements AccountService, CommandLineRunner{
	
	final ModelMapper modelMapper;
	final UserRepository userRepository;
	
	
	@Override
	public UserDto register(UserCreateDto userCreateDto) {
		if(userRepository.existsById(userCreateDto.getLogin())) {
			throw new UserExistsException();
		}
		UserAccount user = modelMapper.map(userCreateDto, UserAccount.class);
		user.addRole("USER");
		String password = BCrypt.hashpw(userCreateDto.getPassword(), BCrypt.gensalt());
		user.setPassword(password);
		user = userRepository.save(user);
		return modelMapper.map(user, UserDto.class);
	}

	@Override
	public UserDto getUser(String login) {
		UserAccount user = userRepository.findById(login).orElseThrow(UserNotFoundException::new);
		return modelMapper.map(user, UserDto.class);	
	}
	

	@Override
	public UserDto deleteUser(String login) {
		UserAccount user = userRepository.findById(login).orElseThrow(UserNotFoundException::new);
		userRepository.deleteById(login);
		return modelMapper.map(user, UserDto.class);
	}

	@Override
	public UserDto updateUser(UserUpdateDto userUpdateDto, String login) {
		UserAccount user = userRepository.findById(login).orElseThrow(UserNotFoundException::new);
		if (userUpdateDto.getFirstName() != null) {
			user.setFirstName(userUpdateDto.getFirstName());
		}
		if (userUpdateDto.getLastName() != null) {
			user.setLastName(userUpdateDto.getLastName());
		}
		userRepository.save(user);
		return modelMapper.map(user, UserDto.class);
	}


	@Override
	public void changePassword(String login, String newPassword) {
		UserAccount user = userRepository.findById(login).orElseThrow(UserNotFoundException::new);
		String password = BCrypt.hashpw(newPassword, BCrypt.gensalt());
		user.setPassword(password);
		userRepository.save(user);
	}

	

	@Override
	public ChangeRolesDto changeRole(String login, String role, Boolean isAddRole) {
		UserAccount user = userRepository.findById(login).orElseThrow(UserNotFoundException::new);
		boolean res;
		if(isAddRole) {
			res = user.addRole(role.toUpperCase());
		} else {
			res = user.deleteRole(role.toUpperCase());
		}
		if (res) {
			userRepository.save(user);
		}
		return modelMapper.map(user, ChangeRolesDto.class);
	}
	
	@Override
	public void run(String... args) throws Exception{
		if (!userRepository.existsById("admin")) {
			String password = BCrypt.hashpw("admin", BCrypt.gensalt());
			UserAccount user = new UserAccount("admin", password, "", "");
			user.addRole("ADMINISTRATOR");
			user.addRole("MODERATOR");
			user.addRole("USER");
			userRepository.save(user);
			
		}
	}

}
