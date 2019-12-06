package com.revature.batch.dto;

import java.util.List;

import com.revature.batch.model.ActiveDay;
import com.revature.batch.model.CoTrainer;

import lombok.Data;

@Data
public class RemovedCoTrainerAndDays {

	private List<CoTrainer> coTrainerList;
	
	private List<ActiveDay> dayList;
}
