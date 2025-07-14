package com.project.Backend.service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.project.Backend.model.Questions;
import com.project.Backend.model.Schedule;
import com.project.Backend.model.Students;
import com.project.Backend.repository.QuestionsRepo;
import com.project.Backend.repository.ScheduleRepo;
import com.project.Backend.repository.StudentRepo;
import com.project.Backend.security.JwtUtil;

@Service
public class StudentServices {
	
	@Value("${imgbb.api.key}")
    private String imgbbApiKey;
	
	
	public String createstu(StudentRepo sturepo, String name, String username, String batch, String regulation,
			String branch, String semester, String section, MultipartFile image, String role) {
		Students s = new Students();
		try {
		s.setName(name);
		s.setUsername(username);
		s.setBatch(batch);
		s.setRegulation(regulation);
		s.setBranch(branch);
		s.setSemester(semester);
		s.setSection(section);
		if(image != null && !image.isEmpty()) {
			CommonFuncServices cfs = new CommonFuncServices();
			String imageUrl = cfs.uploadImage(image,imgbbApiKey);
			s.setImage(imageUrl);}
			s.setRole(role);
			sturepo.save(s);
			return s.toString();
		}
		catch (Exception e) {
			System.out.println(e);
			return "failed";
		}	
	}
	
	public HashMap<String, Object> loginstu(StudentRepo sturepo, String username, String password) {
		List<Object> l = sturepo.findByUsernameAndPassword(username, password);
		if(!l.isEmpty()) {  //if document is present
				JwtUtil jw = new JwtUtil();
				HashMap<String,Object> hm = new HashMap<>();
				hm.put("token", jw.generateToken(username,"STUDENT"));
				hm.put("details", l);
				return hm; 
		}
		else {
			return null; //there is no document
		}
	}
	
	public List<Schedule> getexams(ScheduleRepo schr, String branch, String semester, String date) {
		List<Schedule> sh = schr.findByBranchAndSemesterAndDate(branch,semester,date);
		return sh;
	}
	
	public List<Questions> getAllexamQuestions(QuestionsRepo qr, String year, String type, String branch, String code) {
		List<Questions> q =qr.findQuestions(year,type,branch,code);
		return shuffleQuestions(q);
	}

	public List<Questions> shuffleQuestions(List<Questions> q) {
		Collections.shuffle(q);
		for (Questions que : q) {
            Collections.shuffle(que.getOptions());
        }
		return q;
	}
}
