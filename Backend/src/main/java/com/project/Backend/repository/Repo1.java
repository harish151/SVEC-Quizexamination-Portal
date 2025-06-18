package com.project.Backend.repository;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.project.Backend.model.Userdetails;

@Repository
public interface Repo1 extends MongoRepository<Userdetails,String>{
	
	@Query("{'email':?0,'password':?1}")
	Optional<Userdetails> CheckMailAndPassord(String email,String password);

}
