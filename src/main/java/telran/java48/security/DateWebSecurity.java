package telran.java48.security;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import telran.java48.account.dao.UserRepository;
import telran.java48.account.model.UserAccount;


@Service
@RequiredArgsConstructor
public class DateWebSecurity {
	
	final UserRepository userRepository;
	
	public boolean checkDatePassword(String login) {
		UserAccount user = userRepository.findById(login).orElse(null);
		return ((user != null) && (user.getDatePasswordExp().isAfter(LocalDate.now())));
	}

}
