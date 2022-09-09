package com.werner.dao.login;

import java.sql.Connection;
import java.sql.SQLException;

import com.werner.pojo.User;

public interface LoginDao {

	//查登入用戶
	public User findLoginUser(Connection connection, String userCode) throws SQLException;
		
		
	
}
