package com.revature.batch.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.revature.batch.dao.BatchTraineeDaoImpl;
import com.revature.batch.validator.BatchValidator;

@RunWith(SpringRunner.class)
@SpringBootTest
class BatchTraineeServiceTest {

	@InjectMocks
	private BatchTraineeService batchTraineeService;
	
	@Mock
	private BatchTraineeDaoImpl batchTraineeDaoImpl;
	
	@Mock
	private BatchValidator batchValidator;
	
	@Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }
	
	@Test
	void testAddBatchTraineeService() {
		
		
		fail("Not yet implemented");
	}

}
