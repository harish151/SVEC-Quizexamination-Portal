package com.project.Backend.repository;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.project.Backend.model.User;

@Repository
public interface Repo1 extends MongoRepository<User,String>{

	Optional<User> findByUsernameAndPassword(String username, String password);
	
//	@Query("{'username':?0,'password':?1}")
//	User checkcrediantials(String username, String password);
	
}


