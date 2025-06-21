package com.project.Backend.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.Backend.model.Questions;
import com.project.Backend.model.User;
import com.project.Backend.repository.QuestionsRepo;
import com.project.Backend.repository.Repo1;
import com.project.Backend.service.UserService;

@CrossOrigin("*")
@RestController
public class TaskController{
	
	@Autowired
	Repo1 r;
	
	@Autowired
	QuestionsRepo qr;
	
	@Autowired
	UserService us;
	
	@PostMapping("/create")
	public String create(@RequestBody User u) {
		return us.create(r, u);
	}
	
	@GetMapping("/login")
	public Optional<User> login(@RequestParam("username") String username,@RequestParam("password") String password) {
		return us.login(r, username,password);
	}
	
	@PostMapping("/addquestions")
	public String addquestion(@RequestBody Questions q) {
		return us.createquestion(qr,q);
	}
	
	@PutMapping("/updatequestion")
	public int updatequestion(@RequestBody Questions q) {
		return us.updatequestion(qr,q);
	}
	
	@GetMapping("/getquestions")
	public List<Questions> findallquestions(@RequestParam("acedemic_year") String year,@RequestParam("exam_type") String type,@RequestParam("branch") String branch,@RequestParam("semester") String sem)
	{
		List<Questions> q = us.getAllQuestions(qr,year,type,branch,sem);
		return q;
	}
	
	@DeleteMapping("/deletequestion")
	public String deletequestion(@RequestParam("id") String id) {
		return us.deleteQuestion(qr,id);
	}
	
	
}