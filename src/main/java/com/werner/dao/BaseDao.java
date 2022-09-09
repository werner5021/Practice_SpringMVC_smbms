package com.werner.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import org.springframework.stereotype.Repository;

@Repository
public class BaseDao {

	private static String driver;
	private static String url;
	private static String user;
	private static String password;
	
	static{
		InputStream in = BaseDao.class.getClassLoader().getResourceAsStream("db.properties");
		Properties prop = new Properties();
		
		try {
			prop.load(in);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		driver = prop.getProperty("driver");
		url = prop.getProperty("url");
		user = prop.getProperty("user");
		password = prop.getProperty("password");		
	}
	
	//建立連接
	public static Connection getConnection() {
		Connection connection = null;		
		try {
			Class.forName(driver);
			connection = DriverManager.getConnection(url, user, password);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return connection;
	}
	
	//查詢資料庫
	public static ResultSet execute(Connection connection, PreparedStatement pstm,  ResultSet rs, String sql, Object[] params) throws SQLException {

		pstm = connection.prepareStatement(sql);
		for (int i = 0; i < params.length; i++) {
			pstm.setObject(i+1, params[i]);
		}
		rs = pstm.executeQuery();
		return rs;
	}
	//新刪改資料庫
	public static int execute(Connection connection, PreparedStatement pstm, String sql, Object[] params) throws SQLException {
		pstm = connection.prepareStatement(sql);
		for (int i = 0; i < params.length; i++) {
			pstm.setObject(i+1, params[i]);
		}
		int updateRow = pstm.executeUpdate();
		return updateRow;
	}

	//關閉資源
	public static boolean closeResources(Connection connection, PreparedStatement pstm, ResultSet rs) {
		boolean flag = true;
		
		if(connection != null) {
			try {
				connection.close();
				connection = null;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				flag = false;
			}
		}
		if(pstm != null) {
			try {
				pstm.close();
				pstm = null;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				flag = false;
			}
		}
		if(rs != null) {
			try {
				rs.close();
				rs = null;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				flag = false;
			}
		}
		return flag;
	}
	
	
	
	
	

	
	
}
