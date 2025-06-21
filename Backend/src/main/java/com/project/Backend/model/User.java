package com.project.Backend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import jakarta.validation.constraints.NotNull;



@Document(collection = "usersdetails")
public class Userdetails {
	@Id
	String id;
	@NotNull(message="email cannot be null")
	@Indexed(unique = true)
	String email;
	@NotNull(message = "password cannot be null")
	String password;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public String toString() {
		return "userdetails [id=" + id + ", email=" + email + ", Password=" + password + "]";
	}
	
}
