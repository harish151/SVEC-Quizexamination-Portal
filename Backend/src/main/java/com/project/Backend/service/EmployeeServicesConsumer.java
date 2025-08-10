package com.project.Backend.service;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.project.Backend.model.Questions;
import com.project.Backend.model.Regulation;
import com.project.Backend.model.Result;
import com.project.Backend.model.Schedule;
import com.project.Backend.model.Subjects;
import com.project.Backend.model.Teachers;
import com.project.Backend.repository.QuestionsRepo;
import com.project.Backend.repository.RegulationRepo;
import com.project.Backend.repository.ResultRepo;
import com.project.Backend.repository.ScheduleRepo;
import com.project.Backend.repository.SubjectsRepo;
import com.project.Backend.repository.TeacherRepo;
import com.project.Backend.security.JwtUtil;

@Service
public class EmployeeServices {
	
	@Value("${imgbb.api.key}")
    private String imgbbApiKey;
	
	public String createemp(TeacherRepo teacherrepo, String name, String username, String branch, List<String> teachsub,MultipartFile image, String role) {
		Teachers t = new Teachers();
		try {
			t.setName(name);
			t.setUsername(username);
			t.setBranch(branch);
			t.setTeachsubjects(teachsub);
			if(image != null && !image.isEmpty()) {
				CommonFuncServices cfs = new CommonFuncServices();
				String imageUrl = cfs.uploadImage(image,imgbbApiKey);
				t.setImage(imageUrl);
			}
			t.setRole(role);
			teacherrepo.save(t);
			return t.toString();
		}
		catch (Exception e) {
			System.out.println(e);
			return "failed";
		}
	}
	
	public HashMap<String,Object> loginemp(TeacherRepo teacherrepo, String username, String password) {
		List<Teachers> t = teacherrepo.findByUsernameAndPassword(username, password);
		if(!t.isEmpty()) {  //if document is present
				Teachers teacher = t.get(0);        
				String role = teacher.getRole();
				JwtUtil jw = new JwtUtil();
				HashMap<String,Object> hm = new HashMap<>();
				//String r = (String) t.get("role");
				hm.put("token", jw.generateToken(username,role));
				hm.put("details", t);
				return hm; 
		}
		else {
			return null; //there is no document
		}
	}
	
	public String checkeligibility(TeacherRepo teacherrepo,String username,String coursecode) {
		Teachers u = teacherrepo.findByUsernameAndTeachsubjects(username,coursecode);
		if(u!=null) {
			return "eligible";
		}
		else {
			return "noteligible";
		}
	}
	
	public String setregulation(RegulationRepo rr,Regulation reg) {
		rr.save(reg);
		return reg.toString();
	}
	
	public List<Regulation> getregulation(RegulationRepo rr,String batch,String branch) {
		List<Regulation> reg = rr.findByBatchAndBranch(batch,branch);
		if(reg == null)	
			return null;
		else 
			return reg;
	}
	
	public String postsubjects(SubjectsRepo sr, Subjects s) {
		Subjects sub = sr.save(s);
		return sub.toString();
		}
	
	public List<Subjects> getsubjects(SubjectsRepo sr, String reg, String branch, String sem) {
		List<Subjects> s = sr.findByRegulationAndBranchAndSemester(reg,branch,sem);
		return s;
	}
	
	public String createquestion(QuestionsRepo qr,Questions q) {
		Questions que = new Questions();
		que.setBatch(q.getBatch());
		que.setExam_type(q.getExam_type());
		que.setBranch(q.getBranch());
		que.setSemester(q.getSemester());
		que.setCoursecode(q.getCoursecode());
		que.setQuestion_no(q.getQuestion_no());
		que.setQuestion(q.getQuestion().trim());
		List<String> options = q.getOptions();
	    options.replaceAll(String::trim);
	    que.setOptions(options);
	    que.setAnswer(q.getAnswer().trim());
		
		qr.save(que);
		return q.getId();
	}

	public int updatequestion(QuestionsRepo qr, Questions q) {
	    Optional<Questions> optional = qr.findById(q.getId());
	    if (optional.isPresent()) {
	        Questions existing = optional.get();
	        existing.setBatch(q.getBatch());
	        existing.setExam_type(q.getExam_type());
	        existing.setBranch(q.getBranch());
//	        existing.setSubject(q.getSubject());
	        existing.setSemester(q.getSemester());
	        existing.setQuestion_no(q.getQuestion_no());
	        existing.setQuestion(q.getQuestion().trim());
	        List<String> options = q.getOptions();
		    options.replaceAll(String::trim);
		    existing.setOptions(options);
	        existing.setAnswer(q.getAnswer().trim());
	        qr.save(existing);
	        return 1;
	    } else {
	    	return 2;
	    }
	}
	
	public List<Questions> getAllQuestions(QuestionsRepo qr, String year, String type, String branch,String code) {
		return qr.findQuestions(year,type,branch,code);
		
	}
	
	public String deleteQuestion(QuestionsRepo qr,String id) {
		if(qr.existsById(id)) {
			qr.deleteById(id);
			return "deleted";
		}
		else {
			return "id not found";
		}
	}

	public String addschedule(ScheduleRepo schr, Schedule sch) {
		schr.save(sch);
		return sch.toString();
	}
	
	@Autowired
	ResultRepo rr;
	
	public List<Result> getresultswithoutusername(String batch, String branch, String code, String type,
			String semester, String section) {
		List<Result>  res = rr.findByBatchAndBranchAndCoursecodeAndExamTypeAndSemesterAndSection(batch, branch, code, type, semester, section);
		res.sort(Comparator.comparing(Result::getUsername));
		return res;
	}
}
