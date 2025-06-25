package com.project.Backend.service;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.project.Backend.model.Questions;
import com.project.Backend.model.Regulation;
import com.project.Backend.model.Subjects;
import com.project.Backend.model.User;
import com.project.Backend.repository.QuestionsRepo;
import com.project.Backend.repository.RegulationRepo;
import com.project.Backend.repository.Repo1;
import com.project.Backend.repository.SubjectsRepo;

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
	
	public String setregulation(RegulationRepo rr,Regulation reg) {
		rr.save(reg);
		return reg.toString();
	}
	
	public String getregulation(RegulationRepo rr,String batch) {
		Regulation reg = rr.findByBatch(batch);
		if(reg == null)	
			return null;
		else 
			return reg.getRegulation();
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
		qr.save(q);
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
	        existing.setQuestion(q.getQuestion());
	        existing.setOptions(q.getOptions());
	        existing.setAnswer(q.getAnswer());
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


	
	

	
}
