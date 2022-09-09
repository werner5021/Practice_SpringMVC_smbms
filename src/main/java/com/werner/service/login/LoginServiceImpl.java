package com.werner.service.login;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.werner.dao.BaseDao;
import com.werner.dao.login.LoginDaoImpl;
import com.werner.pojo.User;

@Service
public class LoginServiceImpl implements LoginService{

	@Autowired
	private LoginDaoImpl loginDao;				
	
	//獲取使用者帳號
	public User findLoginUser(String userCode, String userPassword) {
		
		Connection connection = null;		
		User user = null;		
		
		try {
			connection = BaseDao.getConnection();
			user = loginDao.findLoginUser(connection, userCode);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			BaseDao.closeResources(connection, null, null);
		}
		
		if(null != user) {
			if(!userPassword.equals(user.getUserPassword())) {
				user = null;
			}
		}
		return user;
	}	
	
	
	@Test
	public void test() {
		LoginServiceImpl userService = new LoginServiceImpl();
		User ddd = userService.findLoginUser("admin", "000000");
		System.out.println(ddd.getUserPassword());
	}
	
	

}
