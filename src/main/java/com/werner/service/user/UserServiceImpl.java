package com.werner.service.user;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.werner.dao.BaseDao;
import com.werner.dao.login.LoginDaoImpl;
import com.werner.dao.user.UserDaoImpl;
import com.werner.pojo.User;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserDaoImpl userDao;
	@Autowired
	private LoginDaoImpl loginDao;	
	
	//修改使用者密碼
	public boolean updatePwd(int id, String userPassword) {		
		
		boolean flag = true;		
		Connection connection = BaseDao.getConnection();
		
		try {
			int updateRow = userDao.updatePwd(connection, id, userPassword);
			if(updateRow<=0) {
				flag = false;
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}finally {
			BaseDao.closeResources(connection, null, null);
		}		
		return flag;
	}

	//獲取使用者數量
	public int getUserCount(String userName, int userRole) {
		
		Connection connection = null;
		int userCount = 0;
		
		try {
			connection = BaseDao.getConnection();
			userCount = userDao.getUserCount(connection, userName, userRole);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			BaseDao.closeResources(connection, null, null);
		}	
		return userCount;
	}
	
	//獲取使用者列表
	public List<User> getUserList(String userName, int userRole, int currentPage, int perPageSize){
		
		Connection connection = null;
		List<User> userList = null;

		try {
			connection = BaseDao.getConnection();
			userList = userDao.getUserList(connection, userName, userRole, currentPage, perPageSize);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			BaseDao.closeResources(connection, null, null);
		}		
		return userList;		
	}

	//新增使用者
	public boolean createUser(User user) {
		Connection connection = null;
		int create = 0;	
		boolean flag = false;
		try {
			connection = BaseDao.getConnection();
			create = userDao.createUser(connection, user);
			if(create > 0) {
				flag = true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			BaseDao.closeResources(connection, null, null);
		}			
		return flag;
	}

	//修改使用者
	public boolean updateUser(User user) {
		
		Connection connection = null;
		int update = 0;	
		boolean flag = false;
		try {
			connection = BaseDao.getConnection();
			update = userDao.updateUser(connection, user);
			if(update > 0) {
				flag = true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			BaseDao.closeResources(connection, null, null);
		}			
		return flag;
	}

	//刪除使用者
	public boolean deleteUser(Integer id) {
		Connection connection = null;
		int deleteId = 0;	
		boolean flag = false;
		try {
			connection = BaseDao.getConnection();
			deleteId = userDao.deleteUser(connection, id);
			if(deleteId > 0) {
				flag = true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			BaseDao.closeResources(connection, null, null);
		}			
		return flag;
	}

	//查詢使用者
	public User queryUser(Integer id) {		
		Connection connection = null;
		User user = null; 		
		try {
			connection = BaseDao.getConnection();
			user = userDao.queryUser(connection, id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			BaseDao.closeResources(connection, null, null);
		}		
		return user;
	}
	
	//查詢已登入的使用者
	public User queryLoginUser(String userCode) {
		Connection connection = null;
		User user = null;
		try {
			connection = BaseDao.getConnection();
			user = loginDao.findLoginUser(connection, userCode);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			BaseDao.closeResources(connection, null, null);
		}		
		return user;
	}
	
		


}
