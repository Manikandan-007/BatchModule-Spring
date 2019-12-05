package com.revature.batch.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Repository;

import com.revature.batch.exception.DBException;
import com.revature.batch.util.ConnectionUtil;

@Repository
public class CandidateDaoImpl {

	public boolean getCandidate(String userMail) {
		
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		boolean isCandidateAvailable = false;
			try {
				con = ConnectionUtil.getConnection();
				String sql = "select * from candidate where email = ?";
				pst = con.prepareStatement(sql);
				pst.setString(1, userMail);
				
				rs = pst.executeQuery();
				if (rs.next()) {
					isCandidateAvailable = true;
				}
			} catch (DBException e) {
				throw new DBException(e.getMessage());
			} catch (SQLException e) {
				throw new DBException(e.getMessage());
		}
		return isCandidateAvailable;
	}
	
	
}
