package com.revature.batch.model;

import java.sql.Date;
import java.sql.Time;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class Batch {

	private String name;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date startDate;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date endDate;
	
	private int trainerId;
	
	private int activeHrs;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
	private Time startTime;
}
