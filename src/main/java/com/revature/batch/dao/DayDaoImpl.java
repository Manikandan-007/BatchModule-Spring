package com.revature.batch.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Repository;

import com.revature.batch.exception.DBException;
import com.revature.batch.model.ActiveDay;
import com.revature.batch.util.ConnectionUtil;
import com.revature.batch.util.MessageConstants;

@Repository
public class DayDaoImpl {

	public boolean checkIsDayPresent(ActiveDay activeDay) {

		boolean isDayPresent = false;
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
			try {
				con = ConnectionUtil.getConnection();
				String sql = "select * from days where id = ?";
				pst = con.prepareStatement(sql);
				pst.setInt(1, activeDay.getDayId());
				
				rs = pst.executeQuery();
				if (rs.next()) {
					isDayPresent = true;
				}
			} catch (DBException e) {
				throw new DBException(MessageConstants.UNABLE_TO_GET_DAY);
			} catch (SQLException e) {
				throw new DBException(MessageConstants.UNABLE_TO_GET_DAY);
		}
		return isDayPresent;
	}
}
