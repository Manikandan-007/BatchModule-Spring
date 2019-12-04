package com.revature.batch.model;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class Trainer {

	private int id;
	private String name;
	private String email;
	private long mobile;
	private Timestamp created_on;
}
