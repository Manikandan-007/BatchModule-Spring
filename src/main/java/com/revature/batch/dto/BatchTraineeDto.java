package com.revature.batch.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class BatchTraineeDto {

	@JsonIgnore
	private int id;
	private int batchId;
	private String userMail;
}
