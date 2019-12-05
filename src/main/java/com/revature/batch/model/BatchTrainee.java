package com.revature.batch.model;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class BatchTrainee {
	private int id;
	private int batchId;
	private int candidateId;
	private int activeStatus;
	private Timestamp added_on;
	
	private Candidate candidate;
}
