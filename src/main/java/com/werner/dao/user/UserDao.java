package com.werner.dao.user;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.werner.pojo.User;

public interface UserDao {
	
	//修改使用者密碼
	public int updatePwd(Connection connection, int id, String userPassword) throws SQLException;
	//獲取使用者數量
	public int getUserCount(Connection connection, String userName, int userRole) throws SQLException;
	//獲取使用者清單
	public List<User> getUserList(Connection connection, String userName, int userRole, int currentPage, int perPageSize) throws SQLException;
	
	//新增使用者
	public int createUser(Connection connection, User user) throws SQLException;
	//修改使用者
	public int updateUser(Connection connection, User user) throws SQLException;
	//刪除使用者
	public int deleteUser(Connection connection, Integer id) throws SQLException;
	//查詢
	public User queryUser(Connection connection, Integer id) throws SQLException;

}
