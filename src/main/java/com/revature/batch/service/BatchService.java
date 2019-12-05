package com.revature.batch.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.batch.dao.BatchImplDao;
import com.revature.batch.dao.BatchTraineeDaoImpl;
import com.revature.batch.dto.BatchDataDto;
import com.revature.batch.dto.BatchListDto;
import com.revature.batch.dto.CoTrainerListDto;
import com.revature.batch.exception.DBException;
import com.revature.batch.exception.ServiceException;
import com.revature.batch.model.BatchTrainee;
import com.revature.batch.util.MessageConstants;
import com.revature.batch.validator.BatchValidator;

@Service
public class BatchService {
	
	@Autowired
	private BatchImplDao batchImplDao;
	
	@Autowired
	private BatchValidator batchValidator;
	
	@Autowired
	private BatchTraineeDaoImpl batchTraineeDaoImpl;

	public int batchCreationService(BatchDataDto batchDataDto) throws ServiceException {

		int isBatchCreated = 0;
		try {
			
			batchValidator.createBatchValidator(batchDataDto);
			
			isBatchCreated = batchImplDao.createBatchDao(batchDataDto);
			
			if(isBatchCreated!=1) {
				throw new ServiceException(MessageConstants.UNABLE_TO_STORE);
			}
		} catch (DBException e) {
			throw new ServiceException(e.getMessage());
		}
		
		return 1;
	}

	public List<BatchListDto> batchListService() throws ServiceException {
		
		List<BatchListDto> batchList = batchImplDao.getBatchList();
		
		for (BatchListDto batchListDto : batchList) {
			List<CoTrainerListDto> coTrainerListDto = batchImplDao.getCoTrainerList(batchListDto.getId());
			batchListDto.setCoTrainerList(coTrainerListDto);
			
			List<BatchTrainee> batchTraineeList = batchTraineeDaoImpl.getBatchTraineeList(batchListDto.getId());
			batchListDto.setBatchTraineeList(batchTraineeList);
		}
		
		return batchList;
	}

}
