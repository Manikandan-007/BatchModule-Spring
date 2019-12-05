package com.revature.batch.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.revature.batch.dto.BatchTraineeListDto;
import com.revature.batch.exception.ServiceException;
import com.revature.batch.service.BatchTraineeService;
import com.revature.batch.util.Message;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("batchTrainee")
public class BatchTraineeController {

	@Autowired
	private BatchTraineeService batchTraineeService;
	
	@PostMapping("/add_trainee")
	@ApiOperation(value = "Batch API")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = Message.class),
			@ApiResponse(code = 400, message = "Invalid Credentials", response = Message.class) })
	public ResponseEntity<?> addTrainee(@RequestBody BatchTraineeListDto batchTraineeListDto) {
	
		try {
			batchTraineeService.addBatchTraineeService(batchTraineeListDto);
			Message message = new Message("Trainee added successfully");
			return new ResponseEntity<>(message, HttpStatus.OK );
		} catch (ServiceException e) {
			Message message = new Message(e.getMessage());
			return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST );
		}
	}
	
	/*
	 * @PostMapping("/add_trainee")
	 * 
	 * @ApiOperation(value = "Batch API")
	 * 
	 * @ApiResponses(value = { @ApiResponse(code = 200, message = "Success",
	 * response = Message.class),
	 * 
	 * @ApiResponse(code = 400, message = "Unable to get data", response =
	 * Message.class) }) public ResponseEntity<?> addTrainee(@RequestParam int
	 * BatchId) {
	 * 
	 * try { List<> batchTraineeService.getBatchTraineeList(BatchId); return new
	 * ResponseEntity<>(message, HttpStatus.OK ); } catch (ServiceException e) {
	 * Message message = new Message(e.getMessage()); return new
	 * ResponseEntity<>(message, HttpStatus.BAD_REQUEST ); } }
	 */
}
