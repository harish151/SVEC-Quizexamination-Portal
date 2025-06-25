package com.project.Backend.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.project.Backend.model.Regulation;

@Repository
public interface RegulationRepo extends MongoRepository<Regulation,String>{

	Regulation findByBatch(String batch);
	
}
