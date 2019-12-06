package com.revature.batch.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.batch.dto.BatchDataDto;
import com.revature.batch.dto.BatchListDto;
import com.revature.batch.dto.RemovedCoTrainerAndDays;
import com.revature.batch.exception.ServiceException;
import com.revature.batch.service.BatchService;
import com.revature.batch.util.Message;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("batch")
public class CreateBatch {

	@Autowired
	private BatchService batchService;
	
	@PostMapping("/create_new")
	@ApiOperation(value = "Batch API")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = Message.class),
			@ApiResponse(code = 400, message = "Invalid Credentials", response = Message.class) })
	public ResponseEntity<?> batchCreation(@RequestBody BatchDataDto batchDataDto) {
	
		try {
			RemovedCoTrainerAndDays removedCoTrainerAndDays = batchService.batchCreationService(batchDataDto);
			Message message = new Message("Batch Created Successfully except these data, "+removedCoTrainerAndDays);
			return new ResponseEntity<>(message, HttpStatus.OK );
		} catch (ServiceException e) {
			Message message = new Message(e.getMessage());
			return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST );
		}
	}
	
	@GetMapping("/batch_list")
	@ApiOperation(value = "Batch API")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = List.class),
			@ApiResponse(code = 400, message = "Unable to get data", response = Message.class) })
	public ResponseEntity<?> batchList() {
	
		try {
			List<BatchListDto> batchListDto = batchService.batchListService();
			return new ResponseEntity<>(batchListDto, HttpStatus.OK );
		} catch (ServiceException e) {
			Message message = new Message(e.getMessage());
			return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST );
		}
	}
}
