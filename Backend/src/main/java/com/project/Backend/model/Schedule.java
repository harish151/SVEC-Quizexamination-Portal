package com.project.Backend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "schedule")
public class Schedule {
	@Id
	private String id;
	private String exam_type;
	private String branch;
	private String semester;
	private String coursecode;
	private String subject;
	private String date;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getExam_type() {
		return exam_type;
	}
	public void setExam_type(String exam_type) {
		this.exam_type = exam_type;
	}
	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
	}
	public String getSemester() {
		return semester;
	}
	public void setSemester(String semester) {
		this.semester = semester;
	}
	public String getCoursecode() {
		return coursecode;
	}
	public void setCoursecode(String coursecode) {
		this.coursecode = coursecode;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		//SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
	    this.date = date;
	}
	@Override
	public String toString() {
		return "Schedule [id=" + id + ", exam_type=" + exam_type + ", branch=" + branch + ", semester=" + semester
				+ ", coursecode=" + coursecode + ", subject=" + subject + ", date=" + date + "]";
	}
	
	
}
