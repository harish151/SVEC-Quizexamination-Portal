package com.project.Backend.service;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.project.Backend.model.Questions;
import com.project.Backend.model.User;
import com.project.Backend.repository.QuestionsRepo;
import com.project.Backend.repository.Repo1;

@Service
public class UserService{
	
	
	public String create(Repo1 r,User u) {
		try {
			r.save(u);
			return "success";
		}
		catch(Exception e) {
			return "failed";
		}
	}

	public Optional<User> login(Repo1 r, String username,String password) {
		Optional<User> l = r.findByUsernameAndPassword(username, password);
		if(l!=null) {  //if document is present
				return l; 
		}
		else {
			System.out.println(l);
			return l; //there is no document
		}
	}
	
	public String createquestion(QuestionsRepo qr,Questions q) {
		qr.save(q);
		return q.getId();
	}

	public int updatequestion(QuestionsRepo qr, Questions q) {
	    Optional<Questions> optional = qr.findById(q.getId());
	    if (optional.isPresent()) {
	        Questions existing = optional.get();
	        existing.setAcedemic_year(q.getAcedemic_year());
	        existing.setExam_type(q.getExam_type());
	        existing.setBranch(q.getBranch());
	        existing.setSubject(q.getSubject());
	        existing.setSemester(q.getSemester());
	        existing.setQuestion(q.getQuestion());
	        existing.setOptions(q.getOptions());
	        existing.setAnswer(q.getAnswer());
	        qr.save(existing);
	        return 1;
	    } else {
	    	return 2;
	    }
	}

	public List<Questions> getAllQuestions(QuestionsRepo qr, String year, String type, String branch, String sem) {
		return qr.findQuestions(year,type,branch,sem);
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
	
	

	
}
