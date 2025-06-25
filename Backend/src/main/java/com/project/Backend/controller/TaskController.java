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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.Backend.model.Questions;
import com.project.Backend.model.Regulation;
import com.project.Backend.model.Subjects;
import com.project.Backend.model.User;
import com.project.Backend.repository.QuestionsRepo;
import com.project.Backend.repository.RegulationRepo;
import com.project.Backend.repository.Repo1;
import com.project.Backend.repository.SubjectsRepo;
import com.project.Backend.service.UserService;

@CrossOrigin("*")
@RestController
public class TaskController{
	
	@Autowired
	UserService us;
	
	@Autowired
	Repo1 r;
	
	@Autowired
	QuestionsRepo qr;
	
	@Autowired
	RegulationRepo rr;
	
	@Autowired
	SubjectsRepo sr;
	
	@PostMapping("/create")
	public String create(@RequestBody User u) {
		return us.create(r, u);
	}
	
	@GetMapping("/login")
	public Optional<User> login(@RequestParam("username") String username,@RequestParam("password") String password) {
		return us.login(r, username,password);
	}
	
	@PostMapping("/setregulation")
	public String setregulation(@RequestBody Regulation reg) {
		return us.setregulation(rr,reg);
	}
	
	@GetMapping("/getregulation")
	public String getregulation(@RequestParam("batch") String batch) {
		return us.getregulation(rr,batch);
	}
	
	@PostMapping("/postsubjects")
	public String postsubjects(@RequestBody Subjects s) {
		return us.postsubjects(sr,s);
	}
	
	@GetMapping("/getsubjects")
	public List<Subjects> getsubjects(@RequestParam("regulation") String reg,@RequestParam("branch") String branch,@RequestParam("semester") String sem){
		return us.getsubjects(sr,reg,branch,sem);
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
	public List<Questions> findallquestions(@RequestParam("batch") String year,@RequestParam("branch") String branch,@RequestParam("coursecode") String code,@RequestParam("exam_type") String type)
	{
		List<Questions> q = us.getAllQuestions(qr,year,type,branch,code);
		return q;
	}
	
	@GetMapping("/getnumofqueposted")
	public int findnumofqueposted(@RequestParam("batch") String year,@RequestParam("branch") String branch,@RequestParam("coursecode") String code,@RequestParam("exam_type") String type) {
		List<Questions> faqc = findallquestions(year,branch,code,type);
		int count = faqc.size();
		return count;
	}
	
	@DeleteMapping("/deletequestion")
	public String deletequestion(@RequestParam("id") String id) {
		return us.deleteQuestion(qr,id);
	}
	
	
}