package com.revature.batch.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.revature.batch.dto.BatchDataDto;
import com.revature.batch.dto.BatchListDto;
import com.revature.batch.dto.CoTrainerListDto;
import com.revature.batch.exception.DBException;
import com.revature.batch.model.ActiveDay;
import com.revature.batch.model.CoTrainer;
import com.revature.batch.model.Trainer;
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
				pst.close();

				int batchId = 0;
					sql = "select last_insert_id()";
					pst = con.prepareStatement(sql);
					rs = pst.executeQuery();
	
				if (rs.next()) {
					batchId = rs.getInt("last_insert_id()");
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
			throw new DBException(MessageConstants.UNABLE_TO_STORE);
		} finally {
			ConnectionUtil.close(con, pst, rs);
		}
		
		if(BatchRows==0 || activeDayRows==0 || containerRows==0) {
			throw new DBException(MessageConstants.UNABLE_TO_STORE);
		}
		return containerRows;
	}
	
	public List<BatchListDto> getBatchList() {
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		List<BatchListDto> list;
		try {
			con = ConnectionUtil.getConnection();
			String sql = "select b.id, b.name, b.start_date, b.end_date, b.trainer_id, b.active_hrs, b.start_time, b.created_on, t.id, t.name, t.mobile, t.email, t.created_on  from batches b, trainers t where b.trainer_id=t.id";
			pst = con.prepareStatement(sql);
			
			rs = pst.executeQuery();
			list= new ArrayList<BatchListDto>();
			while (rs.next()) {
				BatchListDto batchListDto = toRow1(rs);
				
				list.add(batchListDto);
			}
		} catch (DBException e) {
			throw new DBException(e.getMessage());
		} catch (SQLException e) {
			throw new DBException(e.getMessage());
		}
		
		return list;
	}

	private BatchListDto toRow1(ResultSet rs) throws SQLException {
		int batchId = rs.getInt("b.id");
		String batchName = rs.getString("b.name");
		Timestamp startDate = rs.getTimestamp("start_date");
		Timestamp endDate = rs.getTimestamp("end_date");
		int trainerId = rs.getInt("trainer_id");
		int activeHrs = rs.getInt("active_hrs");
		Time startTime = rs.getTime("start_time");
		Timestamp batchCreatedOn = rs.getTimestamp("b.created_on");
		
		BatchListDto batchListDto = new BatchListDto();
		batchListDto.setId(batchId);
		batchListDto.setName(batchName);
		batchListDto.setStartDate(startDate);
		batchListDto.setEndDate(endDate);
		batchListDto.setTrainerId(trainerId);
		batchListDto.setActiveHrs(activeHrs);
		batchListDto.setStartTime(startTime);
		batchListDto.setCreatedOn(batchCreatedOn);
		
		int tId = rs.getInt("t.id");
		String trainerName = rs.getString("t.name");
		String email = rs.getString("email");
		long mobile = rs.getLong("mobile");
		Timestamp trainerCreatedOn = rs.getTimestamp("t.created_on");
		Trainer trainer = new Trainer();
		trainer.setId(tId);
		trainer.setName(trainerName);
		trainer.setEmail(email);
		trainer.setMobile(mobile);
		trainer.setCreated_on(trainerCreatedOn);
		batchListDto.setTrainer(trainer);
		
		return batchListDto;
	}

	public List<CoTrainerListDto> getCoTrainerList(int batchID) {
		
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		List<CoTrainerListDto> coTrainerList;
			try {
				con = ConnectionUtil.getConnection();
				String sql = "select c.id, c.trainer_id, c.batch_id, t.id, t.name, t.mobile, t.email, t.created_on  from cotrainers c, trainers t where c.trainer_id=t.id and c.batch_id = ?";
				pst = con.prepareStatement(sql);
				pst.setInt(1, batchID);
				
				rs = pst.executeQuery();
				coTrainerList= new ArrayList<CoTrainerListDto>();
				while (rs.next()) {
					CoTrainerListDto coTrainerListDto = new CoTrainerListDto();
					coTrainerListDto.setId(rs.getInt("c.id"));
					coTrainerListDto.setTrainerId(rs.getInt("c.trainer_id"));
					coTrainerListDto.setBatchId(rs.getInt("c.batch_id"));
					
					Trainer trainer = new Trainer();
					trainer.setId(rs.getInt("t.id"));
					trainer.setName(rs.getString("t.name"));
					trainer.setEmail(rs.getString("email"));
					trainer.setMobile(rs.getLong("mobile"));
					trainer.setCreated_on(rs.getTimestamp("t.created_on"));
					coTrainerListDto.setTrainer(trainer);
					
					coTrainerList.add(coTrainerListDto);
				}
			} catch (DBException e) {
				throw new DBException(MessageConstants.UNABLE_TO_GET_BATCH_LIST);
			} catch (SQLException e) {
				throw new DBException(MessageConstants.UNABLE_TO_GET_BATCH_LIST);
		}
		
		return coTrainerList;
	}

}
