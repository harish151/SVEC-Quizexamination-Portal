package com.project.Backend.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.project.Backend.model.QuesAndAnsProgress;

@Repository
public interface ProgressRepo extends MongoRepository<QuesAndAnsProgress,String>{

	@Query("{ 'username': ?0, 'exam_type': ?1, 'branch': ?2, 'coursecode':?3}")
	List<QuesAndAnsProgress> findByDetails(String username,String examtype,String branch,String coursecode);

}
