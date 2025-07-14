package com.project.Backend.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.project.Backend.model.Questions;
import com.project.Backend.model.Regulation;
import com.project.Backend.model.Result;
import com.project.Backend.model.Schedule;
import com.project.Backend.model.Students;
import com.project.Backend.model.Subjects;
import com.project.Backend.model.Teachers;
import com.project.Backend.repository.QuestionsRepo;
import com.project.Backend.repository.RegulationRepo;
import com.project.Backend.repository.ScheduleRepo;
import com.project.Backend.repository.StudentRepo;
import com.project.Backend.repository.SubjectsRepo;
import com.project.Backend.repository.TeacherRepo;
import com.project.Backend.service.EmployeeServices;
//@CrossOrigin(
//"*"
////origins = "http://localhost:3000",
////allowedHeaders = "*",
////exposedHeaders = "Authorization",
////allowCredentials = "true"
//)

@RestController
public class EmployeeController {
	
	
	@Autowired
	EmployeeServices emps;

	@Autowired
	TeacherRepo teacherrepo;
	
	@PostMapping("/noauth/createteacher")
	public String createTeacher(@RequestParam("name") String name,@RequestParam("username") String username,@RequestParam("branch") String branch,@RequestParam("teachsub") List<String> teachsub,@RequestParam(value="image",required = false) MultipartFile image,@RequestParam("role") String role) {
		return emps.createemp(teacherrepo, name,username,branch,teachsub,image,role);
	}
	
	@GetMapping("/teacher/getteachers")
	public List<Teachers> getTeachers(){
		List<Teachers> u =teacherrepo.findAll();
		return u;
	}
	
	@Autowired
	StudentRepo sturepo;
	
	@GetMapping("/teacher/getstudents")
	public List<Students> getStudents(){
		List<Students> s =sturepo.findAll();
		return s;
	}
	
	@GetMapping("/noauth/loginemp")
	public HashMap<String,Object> loginemp(@RequestParam("username") String username,@RequestParam("password") String password) {
		return emps.loginemp(teacherrepo, username.toUpperCase(),password);
	}
	
	@GetMapping("/teacher/checkeligibility")
	public String checkeligibility(@RequestParam("username") String username,@RequestParam("coursecode") String coursecode) {
		return emps.checkeligibility(teacherrepo, username, coursecode);
	}
	
	@Autowired
	RegulationRepo rr;
	
	@PostMapping("/teacher/setregulation")
	public String setregulation(@RequestBody Regulation reg) {
		return emps.setregulation(rr,reg);
	}
	
	@GetMapping("/teacher/getregulation")
	public List<Regulation> getregulation(@RequestParam("batch") String batch,@RequestParam("branch") String branch) {
		return emps.getregulation(rr,batch,branch);
	}
	
	@Autowired
	SubjectsRepo sr;
	
	@PostMapping("/teacher/postsubjects")
	public String postsubjects(@RequestBody Subjects s) {
		return emps.postsubjects(sr,s);
	}
	
	@GetMapping("/teacher/getsubjects")
	public List<Subjects> getsubjects(@RequestParam("regulation") String reg,@RequestParam("branch") String branch,@RequestParam("semester") String sem){
		return emps.getsubjects(sr,reg,branch,sem);
	}
	
	@Autowired
	QuestionsRepo qr;
	
	@PostMapping("/teacher/addquestions")
	public String addquestion(@RequestBody Questions q) {
		return emps.createquestion(qr,q);
	}
	
	@PutMapping("/teacher/updatequestion")
	public int updatequestion(@RequestBody Questions q) {
		return emps.updatequestion(qr,q);
	}
	
	@GetMapping("/teacher/getquestions")
	public List<Questions> findallquestions(@RequestParam("batch") String year,@RequestParam("branch") String branch,@RequestParam("coursecode") String code,@RequestParam("exam_type") String type)
	{
		List<Questions> q = emps.getAllQuestions(qr,year,type,branch,code);
		return q;
	}
	
	@GetMapping("/teacher/getnumofqueposted")
	public int findnumofqueposted(@RequestParam("batch") String year,@RequestParam("branch") String branch,@RequestParam("coursecode") String code,@RequestParam("exam_type") String type) {
		List<Questions> faqc = findallquestions(year,branch,code,type);
		int count = faqc.size();
		return count;
	}
	
	@DeleteMapping("/teacher/deletequestion")
	public String deletequestion(@RequestParam("id") String id) {
		return emps.deleteQuestion(qr,id);
	}
	
	@Autowired
	ScheduleRepo schr;
	
	@PostMapping("/teacher/addschedule")
	public String addschedule(@RequestBody Schedule sch) {
		return emps.addschedule(schr,sch);
	}
	
	@GetMapping("/teacher/getresultslist")
	public List<Result> getresultswithoutusername(@RequestParam("batch") String batch,@RequestParam("branch") String branch,@RequestParam("coursecode") String code,@RequestParam("exam_type") String type,@RequestParam("semester") String semester,@RequestParam("section") String section) {
		return emps.getresultswithoutusername(batch,branch,code,type,semester,section);
	}
	
}
