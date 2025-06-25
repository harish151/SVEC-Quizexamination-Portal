package com.project.Backend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotNull;

@Document(collection = "users")
public class User{
	@Id
	String id;
	String name;
	@Indexed(unique = true)
	String username;
	String password="1234";
	String academic_year=null;
	String regulation;
	@NotNull
	String role;
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
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", role=" + role + "]";
	}
}