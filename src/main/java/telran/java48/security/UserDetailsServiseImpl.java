package telran.java48.security;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import telran.java48.account.dao.UserRepository;
import telran.java48.account.model.UserAccount;

@Service
@RequiredArgsConstructor
public class UserDetailsServiseImpl implements UserDetailsService {
	final UserRepository userAccountRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserAccount userAccount = userAccountRepository.findById(username)
				.orElseThrow(() -> new UsernameNotFoundException(username));
		String[] roles = userAccount.getRoles()
						.stream()
						.map(r -> "ROLE_" + r.toUpperCase())
						.toArray(String[]::new);
		return new User(username, userAccount.getPassword(), AuthorityUtils.createAuthorityList(roles));
	}

}
