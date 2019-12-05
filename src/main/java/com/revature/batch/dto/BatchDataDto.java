package com.revature.batch.dto;

import java.util.List;

import com.revature.batch.model.ActiveDay;
import com.revature.batch.model.Batch;
import com.revature.batch.model.CoTrainer;

import lombok.Data;

@Data
public class BatchDataDto {

	private Batch batch;
	
	private List<ActiveDay> dayList;
	
	private List<CoTrainer> coTrainer;
	
	public BatchDataDto(Batch batch, List<ActiveDay> dayList, List<CoTrainer> coTrainer) {
		super();
		this.batch = batch;
		this.dayList = dayList;
		this.coTrainer = coTrainer;
	}
}


