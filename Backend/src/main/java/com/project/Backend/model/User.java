package com.project.Backend.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotNull;

@Document(collection = "users")
public class User{
	@Id
	private String id;
	private String name;
	@Indexed(unique = true)
	private String username;
	private String password="1234";
	private String academic_year=null;
	private String regulation=null;
	private String branch=null;
	private String semester=null;
	private String section = null;
	private List<String> teachsub=null;
	@NotNull
	private String role;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getAcademic_year() {
		return academic_year;
	}
	public void setAcademic_year(String academic_year) {
		this.academic_year = academic_year;
	}
	public String getRegulation() {
		return regulation;
	}
	
	public void setRegulation(String regulation) {
		this.regulation = regulation;
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
	public String getSection() {
		return section;
	}
	public void setSection(String section) {
		this.section = section;
	}
	public List<String> getTeachsub() {
		return teachsub;
	}
	public void setTeachsub(List<String> teachsub) {
		this.teachsub = teachsub;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", username=" + username + ", password=" + password
				+ ", academic_year=" + academic_year + ", regulation=" + regulation + ", teachsub=" + teachsub
				+ ", role=" + role + "]";
	}
}