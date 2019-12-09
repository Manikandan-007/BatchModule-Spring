package com.revature.batch.dao;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class CandidateDaoTest {

	@InjectMocks
	private CandidateDaoImpl CandidateDao;
	
	@Test
	public void testGetCandidate() {
		String userMail = "user1@gmail.com";
		boolean isCandidateAvailable = CandidateDao.getCandidate(userMail);
		
		assertEquals(true, isCandidateAvailable);
	}
	
	@Test
	public void testGetCandidateInvalid() {
		String userMail = "user11@gmail.com";
		boolean isCandidateAvailable = CandidateDao.getCandidate(userMail);
		
		assertEquals(false, isCandidateAvailable);
	}

}
