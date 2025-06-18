package com.project.Backend.service;


import java.util.Optional;

import org.springframework.stereotype.Service;

import com.project.Backend.model.Userdetails;
import com.project.Backend.repository.Repo1;

@Service
public class UserService{
	
	public int signup(Repo1 re1,Userdetails ud) {
		try {
		  re1.save(ud);
		  return 1;   // 1 means correct
		}
		catch(Exception e) {
			System.out.println(e);
			return 2;  // 2 means already user exist
		}
	}
	
	public String checkpassword(Repo1 re1, String email, String password) {
	    Optional<Userdetails> l = re1.CheckMailAndPassord(email, password);
	    System.out.println(l);
	    if (l.isPresent()) {
	        return "Correct";
	    } else {
	        return "Incorrect";
	    }
	}


}