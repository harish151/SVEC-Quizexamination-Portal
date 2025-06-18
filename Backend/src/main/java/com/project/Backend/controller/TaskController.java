package com.project.Backend.controller;

import java.util.List;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.project.Backend.model.Userdetails;
import com.project.Backend.repository.Repo1;
import com.project.Backend.service.UserService;

@CrossOrigin(origins = {
	    "http://192.168.152.193:3000",
	    "http://localhost:3000"
	})
@RestController
public class TaskController{
	
	@Autowired
	Repo1 re1;
	
	 @Autowired
	UserService us;
	
	@PostMapping("/signup")
	public int signup(@RequestBody Userdetails ud) {
	   return us.signup(re1, ud);  
	}
	
	@GetMapping("/login")
	public String login(@RequestParam("email") String email,@RequestParam("password") String password) {
		return us.checkpassword(re1, email, password);
	}
	
	@GetMapping("/dashboard")
	public List<Userdetails> dashboard() {
		return re1.findAll();
	}
	
}