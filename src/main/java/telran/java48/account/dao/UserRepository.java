package telran.java48.account.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import telran.java48.account.model.UserAccount;

public interface UserRepository extends MongoRepository<UserAccount, String> {
	
}
