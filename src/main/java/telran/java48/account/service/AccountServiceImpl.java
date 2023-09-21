package telran.java48.account.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import telran.java48.account.dao.UserRepository;
import telran.java48.account.dto.ChangeRolesDto;
import telran.java48.account.dto.UserCreateDto;
import telran.java48.account.dto.UserDto;
import telran.java48.account.dto.UserUpdateDto;
import telran.java48.account.model.User;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
	final ModelMapper modelMapper;
	final UserRepository userRepository;

	@Override
	public UserDto register(UserCreateDto userCreateDto) {
		if (userRepository.existsByLogin(userCreateDto.getLogin())) {
			return null;
		}
		User user = modelMapper.map(userCreateDto, User.class);
		user.addRole("USER");
		user = userRepository.save(user);
		return modelMapper.map(user, UserDto.class);
	}

	@Override
	public UserDto login() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserDto deleteUser(String login) {
		if (!userRepository.existsByLogin(login)){
			return null;
		}
		User user = userRepository.findByLogin(login);
		userRepository.deleteByLogin(login);
		return modelMapper.map(user, UserDto.class);
	}

	@Override
	public UserDto updateUser(UserUpdateDto userUpdateDto, String login) {
		if (!userRepository.existsByLogin(login)){
			return null;
		}
		User user = userRepository.findByLogin(login);
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
	public ChangeRolesDto addRole(String login, String role) {
		if (!userRepository.existsByLogin(login)){
			return null;
		}
		User user = userRepository.findByLogin(login);
		user.addRole(role);
		userRepository.save(user);
		return modelMapper.map(user, ChangeRolesDto.class);
	}

	@Override
	public ChangeRolesDto deletRole(String login, String role) {
		if (!userRepository.existsByLogin(login)){
			return null;
		}
		User user = userRepository.findByLogin(login);
		user.deleteRole(role);
		userRepository.save(user);
		return modelMapper.map(user, ChangeRolesDto.class);
	}

	@Override
	public Boolean changePassword() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserDto getUser(String login) {
		if (!userRepository.existsByLogin(login)){
			return null;
		}
		User user = userRepository.findByLogin(login);
		return modelMapper.map(user, UserDto.class);	
	}

}
