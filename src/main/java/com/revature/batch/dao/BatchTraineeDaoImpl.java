package com.revature.batch.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.revature.batch.dto.BatchTraineeDto;
import com.revature.batch.exception.DBException;
import com.revature.batch.model.BatchTrainee;
import com.revature.batch.model.Candidate;
import com.revature.batch.util.ConnectionUtil;
import com.revature.batch.util.MessageConstants;

@Repository
public class BatchTraineeDaoImpl {

	public void addTraineeIntoBatch(List<BatchTraineeDto> batchTraineeList) {

		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		Savepoint addTrainee = null;
		try {
			String dayDataStr = "";
			int count = 0;
				for (BatchTraineeDto batchTraineeDto : batchTraineeList) {
					
						dayDataStr = dayDataStr + "("+batchTraineeDto.getBatchId()+","+batchTraineeDto.getUserMail()+")";
						count ++;
						if(count < batchTraineeList.size()) {
							dayDataStr = dayDataStr + ",";
						}
					}
				
				con = ConnectionUtil.getConnection();
				con.setAutoCommit(false);
				addTrainee = con.setSavepoint("SP1");
				String sql = "insert into batch_trainees (batch_id, candidate_id) values (?,(select id from candidate where email = ?))";
				pst = con.prepareStatement(sql);

				pst.executeUpdate();
				pst.close();
				con.commit();
		} catch (DBException e) {
			throw new DBException(e.getMessage());
		}catch (SQLException e) {
			try {
				con.rollback(addTrainee);
			} catch (SQLException e1) {
				System.out.println(e1.getMessage());
			}
			throw new DBException(MessageConstants.UNABLE_TO_ADD_TRAINEE);
		} finally {
			ConnectionUtil.close(con, pst, rs);
		}
		
	}

	public List<BatchTrainee> getBatchTraineeList(int id) {
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		List<BatchTrainee> batchTraineeList;
			try {
				con = ConnectionUtil.getConnection();
				String sql = "select bt.id, bt.batch_id, bt.candidate_id, bt.active_status,"
						+ " bt.added_on, c.id, c.name, c.mobile, c.email, c.created_on  "
						+ "from candidate c, batch_trainees bt where c.id=bt.candidate_id"
						+ " and bt.batch_id = ?";
				pst = con.prepareStatement(sql);
				pst.setInt(1, id);
				
				rs = pst.executeQuery();
				batchTraineeList= new ArrayList<BatchTrainee>();
				while (rs.next()) {
					BatchTrainee batchTrainee = new BatchTrainee();
					batchTrainee.setId(rs.getInt("bt.id"));
					batchTrainee.setBatchId(rs.getInt("bt.batch_id"));
					batchTrainee.setCandidateId(rs.getInt("bt.candidate_id"));
					batchTrainee.setActiveStatus(rs.getInt("active_status"));
					batchTrainee.setAdded_on(rs.getTimestamp("added_on"));
					
					Candidate candidate = new Candidate();
					candidate.setId(rs.getInt("c.id"));
					candidate.setName(rs.getString("c.name"));
					candidate.setEmail(rs.getString("email"));
					candidate.setMobile(rs.getLong("mobile"));
					candidate.setCreated_on(rs.getTimestamp("c.created_on"));
					batchTrainee.setCandidate(candidate);
					
					batchTraineeList.add(batchTrainee);
				}
			} catch (DBException e) {
				throw new DBException(MessageConstants.UNABLE_TO_GET_BATCH_TRAINEE_LIST);
			} catch (SQLException e) {
				throw new DBException(e.getMessage());
			} finally {
				ConnectionUtil.close(con, pst, rs);
			}
		return batchTraineeList;
	}

}
