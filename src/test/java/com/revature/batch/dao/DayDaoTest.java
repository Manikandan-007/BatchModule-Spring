package com.revature.batch.dao;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.revature.batch.model.ActiveDay;

@RunWith(SpringRunner.class)
@SpringBootTest
class DayDaoTest {

	@InjectMocks
	private DayDaoImpl dayDao;
	
	@Test
	public void testCheckIsDayPresent() {
		ActiveDay activeDay = new ActiveDay();
		activeDay.setDayId(1);
		boolean isDayPresent = dayDao.checkIsDayPresent(activeDay);
		
		assertEquals(true, isDayPresent);
	}
	
	@Test
	public void testCheckIsDayPresentInValid() {
		ActiveDay activeDay = new ActiveDay();
		activeDay.setDayId(8);
		boolean isDayPresent = dayDao.checkIsDayPresent(activeDay);
		
		assertEquals(false, isDayPresent);
	}


}
