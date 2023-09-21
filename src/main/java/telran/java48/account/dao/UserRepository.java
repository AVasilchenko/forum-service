package telran.java48.account.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import telran.java48.account.model.User;

public interface UserRepository extends MongoRepository<User, String> {
	
	Boolean existsByLogin(String login);
	
	User findByLogin(String login);
	
	Long deleteByLogin(String login);
}
