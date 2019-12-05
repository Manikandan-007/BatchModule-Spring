package com.revature.batch.dto;

import com.revature.batch.model.Trainer;

import lombok.Data;

@Data
public class CoTrainerListDto {
	private int id;
	private int TrainerId;
	private int batchId;
	private Trainer trainer;
}
