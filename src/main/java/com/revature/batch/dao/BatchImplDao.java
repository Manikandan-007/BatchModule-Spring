package com.revature.batch.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.revature.batch.dto.BatchDataDto;
import com.revature.batch.exception.DBException;
import com.revature.batch.exception.ServiceException;
import com.revature.batch.model.ActiveDay;
import com.revature.batch.model.Batch;
import com.revature.batch.model.CoTrainer;
import com.revature.batch.util.ConnectionUtil;
import com.revature.batch.util.MessageConstants;

@Repository
public class BatchImplDao {
	
	public int createBatchDao(BatchDataDto batchDataDto) throws DBException {
		
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		int BatchRows = 0;
		int activeDayRows = 0;
		int containerRows = 0;
		Savepoint addbatches = null;
		try {
				con = ConnectionUtil.getConnection();
				con.setAutoCommit(false);
				addbatches = con.setSavepoint("SP1");
				String sql = "insert into batches (name, start_date, end_date, trainer_id, active_hrs, start_time) values (?,?,?,?,?,?)";
				pst = con.prepareStatement(sql);
				pst.setString(1, batchDataDto.getBatch().getName());
				pst.setDate(2, batchDataDto.getBatch().getStartDate());
				pst.setDate(3, batchDataDto.getBatch().getEndDate());
				pst.setInt(4, batchDataDto.getBatch().getTrainerId());
				pst.setInt(5, batchDataDto.getBatch().getActiveHrs());
				pst.setTime(6, batchDataDto.getBatch().getStartTime());
				
				BatchRows = pst.executeUpdate();

				int batchId = 0;
					sql = "select last_insert_id()";
					pst = con.prepareStatement(sql);
					rs = pst.executeQuery();
	
				if (rs.next()) {
					batchId = rs.getInt("id");
					System.out.println(batchId);
				}
	
				List<ActiveDay> dayList = batchDataDto.getDayList();
				for (ActiveDay activeDay : dayList) {
					sql = "insert into active_day (day_id, batch_id) values (?, ?)";
					pst = con.prepareStatement(sql);
					pst.setInt(1, activeDay.getDayId());
					pst.setInt(2, batchId);
					
					activeDayRows = pst.executeUpdate();
				}
				
				List<CoTrainer> coTrainerList = batchDataDto.getCoTrainer();
				for (CoTrainer coTrainer : coTrainerList) {
					sql = "insert into cotrainers (trainer_id, batch_id) values (?, ?)";
					pst = con.prepareStatement(sql);
					pst.setInt(1, coTrainer.getTrainerId());
					pst.setInt(2, batchId);
					
					containerRows = pst.executeUpdate();
				}
				
				con.commit();
		} catch (SQLException e) {
			try {
				con.rollback(addbatches);
			} catch (SQLException e1) {
				System.out.println(e1.getMessage());
			}
			throw new DBException(MessageConstants.UNABLE_TO_STORE, e);
		} finally {
			ConnectionUtil.close(con, pst, rs);
		}
		
		if(BatchRows==0 || activeDayRows==0 || containerRows==0) {
			throw new DBException(MessageConstants.UNABLE_TO_STORE);
		}
		return containerRows;
	}

}
