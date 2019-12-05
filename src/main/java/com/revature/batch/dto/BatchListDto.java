package com.revature.batch.dto;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.revature.batch.model.BatchTrainee;
import com.revature.batch.model.Trainer;

import lombok.Data;

@Data
public class BatchListDto {
	
	private int id;
	private String name;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Timestamp startDate;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Timestamp endDate;
	
	private int trainerId;
	
	private int activeHrs;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
	private Time startTime;
	
	private Timestamp createdOn;
	
	private Trainer trainer;
	
	private List<CoTrainerListDto> coTrainerList;
	
	private List<BatchTrainee> batchTraineeList;
}
