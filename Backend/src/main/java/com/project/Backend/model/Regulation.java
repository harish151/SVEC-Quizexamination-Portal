package com.project.Backend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "batchwise_regulation")
public class Regulation {
	@Id
	String id;
	String batch;
	String regulation;
	public String getBatch() {
		return batch;
	}
	public void setBatch(String batch) {
		this.batch = batch;
	}
	public String getRegulation() {
		return regulation;
	}
	public void setRegulation(String regulation) {
		this.regulation = regulation;
	}
	@Override
	public String toString() {
		return "Regulation [batch=" + batch + ", regulation=" + regulation + "]";
	}
	
}
