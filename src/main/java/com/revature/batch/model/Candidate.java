package com.revature.batch.model;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class Candidate {

	private int id;
	private String name;
	private long mobile;
	private String email;
	private Timestamp created_on; 
}
