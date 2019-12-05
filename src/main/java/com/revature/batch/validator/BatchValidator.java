package com.revature.batch.validator;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.batch.dao.CandidateDaoImpl;
import com.revature.batch.dto.BatchDataDto;
import com.revature.batch.dto.BatchTraineeDto;
import com.revature.batch.exception.ValidatorException;

@Service
public class BatchValidator {

	@Autowired
	private CandidateDaoImpl candidateDaoImpl;
	
	
	public void createBatchValidator(BatchDataDto batchDataDto) {
		
		if (batchDataDto.getBatch().getName() == null || "".equals(batchDataDto.getBatch().getName().trim())) 
			throw new ValidatorException("Invalid Name, Please try again");
		
		
		try {
			ScoreRange scorerange = validatordao.findGrade(grade);
			if(scorerange != null)
				throw new ValidatorException("Grade already Exist, Please try another.");
		} catch (DBException e) {
			System.out.println(e.getMessage());
		}
		
		try {
			ScoreRange scorerange1 = validatordao.findRange(min);
			if(scorerange1 != null)
				throw new ValidatorException("Min_mark already updated, Please try another.");
		} catch (DBException e) {
			System.out.println(e.getMessage());
		}
	
		
		try {
			ScoreRange scorerange2 = validatordao.findRange(min);
			if(scorerange2 != null)
				throw new ValidatorException("Max_mark already updated, Please try another.");
		} catch (DBException e) {
			System.out.println(e.getMessage());
		}
	}

	public List<BatchTraineeDto> batchTraineeValidator(List<BatchTraineeDto> batchTraineeList) throws ValidatorException {

		System.out.println("======>"+batchTraineeList);
		List<BatchTraineeDto> batchTraineeList1 = new ArrayList<BatchTraineeDto>();
		
		for (BatchTraineeDto batchTraineeDto : batchTraineeList) {
			boolean candidate = candidateDaoImpl.getCandidate(batchTraineeDto.getUserMail());
		    if(candidate != true) {
		    	batchTraineeList1.add(batchTraineeDto);
			}
		}
		batchTraineeList.removeAll(batchTraineeList1);
		
		System.out.println("======><"+batchTraineeList);
		System.out.println("-------------->"+batchTraineeList.size());
		return batchTraineeList;
	}

}
