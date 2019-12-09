package com.revature.batch.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.revature.batch.dto.BatchTraineeDto;
import com.revature.batch.exception.DBException;
import com.revature.batch.model.Candidate;
import com.revature.batch.util.ConnectionUtil;

@Repository
public class CandidateDaoImpl {

	public List<Candidate> getCandidate(List<BatchTraineeDto> batchTraineeList) {
		
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		List<Candidate> availableCandidateList = null;
		try {
			String dayDataStr = "";
			int count = 0;
			for (BatchTraineeDto batchTraineeDto : batchTraineeList) {
				
					dayDataStr = dayDataStr + "\""+batchTraineeDto.getUserMail()+"\"";
					count ++; 
					if(count < batchTraineeList.size()) {
						dayDataStr = dayDataStr + ",";
					}
					if(count == batchTraineeList.size()) {
						dayDataStr = dayDataStr + ")";
					}
					
			}
				con = ConnectionUtil.getConnection();
				String sql = "select id, email from candidate where email in ("+dayDataStr;
				System.out.println(sql);
				pst = con.prepareStatement(sql);
				
				rs = pst.executeQuery();
				availableCandidateList = new ArrayList<Candidate>();
				while (rs.next()) {
					Candidate candidate = new Candidate();
					candidate.setId(rs.getInt("id"));
					candidate.setEmail(rs.getString("email"));
					
					availableCandidateList.add(candidate);
				}
			} catch (DBException e) {
				throw new DBException(e.getMessage());
			} catch (SQLException e) {
				throw new DBException(e.getMessage());
		}
		return availableCandidateList;
	}
	
	
}
