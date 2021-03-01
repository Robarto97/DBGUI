package com.ru.mag.db.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBUtil {
	
	private PreparedStatement workersForPosition = null;
	
	private static final String GET_WORKERS_FOR_POSITION_QUERY = "SELECT * FROM Workers WHERE position = ?";
	
	private Connection cachedConnection = null;
	
	private static final DBUtil instance = new DBUtil();
	
	private DBUtil()
	{
		
	}
	
	public static DBUtil getInstance(){
		return instance;
	}
	
	private Connection getConnection()
	{
		try {
			if (cachedConnection == null ||
					cachedConnection.isClosed() ||
					!cachedConnection.isValid(10)){
				System.out.println("Attempting to get a new connection to DB!");
				DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
				cachedConnection = DriverManager.getConnection(
						"jdbc:oracle:thin:@172.16.251.135:1521:orcl", "c##ex20_robart", "123456");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return this.cachedConnection;
	}
	
	private PreparedStatement getWorkersStatement(){
		if (workersForPosition == null){
			try {
				workersForPosition = getConnection().prepareStatement(GET_WORKERS_FOR_POSITION_QUERY);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return workersForPosition;
	}
	
	public ResultSet getWorkersForPosition(String position){
		ResultSet result = null;
		try {
			PreparedStatement stmt = getWorkersStatement();
			stmt.setNString(1, position);
			result = stmt.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
}
