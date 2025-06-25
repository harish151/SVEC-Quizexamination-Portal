package com.project.Backend.repository;


import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.project.Backend.model.Subjects;

public interface SubjectsRepo extends MongoRepository<Subjects, String> {

	List<Subjects> findByRegulationAndBranchAndSemester(String reg, String branch, String sem);

}
