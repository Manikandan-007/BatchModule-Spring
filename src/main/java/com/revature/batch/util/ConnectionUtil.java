package com.revature.batch.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.revature.batch.exception.DBException;

public class ConnectionUtil {

	private static final String DRIVERCLASSNAME = "com.mysql.cj.jdbc.Driver";
	private static final String URL = "jdbc:mysql://localhost:3306/batch";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "root";

	public static Connection getConnection() throws DBException {

		Connection con = null;

		try {
			Class.forName(DRIVERCLASSNAME);
			con = DriverManager.getConnection(URL, USERNAME, PASSWORD);

		} catch (ClassNotFoundException e) {
			throw new DBException(MessageConstants.DRIVER_CLASS, e);
		} catch (SQLException e) {
			throw new DBException(MessageConstants.SQL_CONNECTION, e);
		}

		return con;
	}

	public static void close(Connection con, PreparedStatement pst, ResultSet rs) throws DBException {

		try {
			if (pst != null)
				pst.close();
			if (con != null)
				con.close();
			if (rs != null)
				rs.close();
		} catch (Exception e) {
			throw new DBException(MessageConstants.CLOSE_CONNECTION, e);
		}
	}

	public static void close(Connection con, PreparedStatement pst) throws DBException {

		try {
			if (pst != null)
				pst.close();
			if (con != null)
				con.close();
		} catch (Exception e) {
			throw new DBException(MessageConstants.CLOSE_CONNECTION, e);
		}

	}
}
