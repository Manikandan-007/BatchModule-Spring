package com.revature.batch.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.batch.dao.BatchImplDao;
import com.revature.batch.dao.BatchTraineeDaoImpl;
import com.revature.batch.dto.BatchDataDto;
import com.revature.batch.dto.BatchListDto;
import com.revature.batch.dto.CoTrainerListDto;
import com.revature.batch.dto.RemovedCoTrainerAndDays;
import com.revature.batch.exception.DBException;
import com.revature.batch.exception.ServiceException;
import com.revature.batch.exception.ValidatorException;
import com.revature.batch.model.ActiveDay;
import com.revature.batch.model.BatchTrainee;
import com.revature.batch.model.CoTrainer;
import com.revature.batch.validator.BatchValidator;

@Service
public class BatchService {
	
	@Autowired
	private BatchImplDao batchImplDao;
	
	@Autowired
	private BatchValidator batchValidator;
	
	@Autowired
	private BatchTraineeDaoImpl batchTraineeDaoImpl;

	public RemovedCoTrainerAndDays batchCreationService(BatchDataDto batchDataDto) throws ServiceException {

		RemovedCoTrainerAndDays removedCoTrainerAndDays = null;
		try {
			
			removedCoTrainerAndDays = batchValidator.createBatchValidator(batchDataDto);
			
			List<ActiveDay> dayList = batchDataDto.getDayList();
			List<CoTrainer> coTrainerList = batchDataDto.getCoTrainer();
			
			//Remove failed data in validation from CoTrainerList and DayList 
			coTrainerList.removeAll(removedCoTrainerAndDays.getCoTrainerList());
			dayList.removeAll(removedCoTrainerAndDays.getDayList());
			
			batchDataDto.setCoTrainer(coTrainerList);
			batchDataDto.setDayList(dayList);
			
			System.out.println(batchDataDto);
			System.out.println(removedCoTrainerAndDays);
			batchImplDao.createBatchDao(batchDataDto);
			
		} catch (DBException e) {
			throw new ServiceException(e.getMessage());
		} catch (ValidatorException e) {
			throw new ServiceException(e.getMessage());
		}
		
		return removedCoTrainerAndDays;
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
