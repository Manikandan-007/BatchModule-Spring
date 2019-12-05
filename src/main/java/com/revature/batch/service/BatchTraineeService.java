package com.revature.batch.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.batch.dao.BatchTraineeDaoImpl;
import com.revature.batch.dto.BatchTraineeDto;
import com.revature.batch.dto.BatchTraineeListDto;
import com.revature.batch.exception.DBException;
import com.revature.batch.exception.ServiceException;
import com.revature.batch.exception.ValidatorException;
import com.revature.batch.validator.BatchValidator;

@Service
public class BatchTraineeService {

	@Autowired
	private BatchTraineeDaoImpl batchTraineeDaoImpl;
	
	@Autowired
	private BatchValidator batchValidator;
	
	public void addBatchTraineeService(BatchTraineeListDto batchTraineeListDto) throws ServiceException{

		List<BatchTraineeDto> batchTraineeList = batchTraineeListDto.getBatchTraineeList();
		
		try {
			//Validation for add trainee in batch
			batchTraineeList = batchValidator.batchTraineeValidator(batchTraineeList);
			
			//If validation success means it will Add
			batchTraineeDaoImpl.addTraineeIntoBatch(batchTraineeList);
		} catch (ValidatorException e) {
			throw new ServiceException(e.getMessage());
		}catch (DBException e) {
			throw new ServiceException(e.getMessage());
		}
		
	}

}
