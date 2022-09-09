package com.werner.service.user;

import java.util.List;

import com.werner.pojo.User;

public interface UserService {

	//修改使用者密碼
	public boolean updatePwd(int id, String userPassword);
	//獲取使用者數量
	public int getUserCount(String userName, int userRole);	
	//獲取使用者列表
	public List<User> getUserList(String userName, int userRole, int currentPage, int perPageSize);

	//新增使用者
	public boolean createUser(User user);
	//修改使用者
	public boolean updateUser(User user);
	//刪除使用者
	public boolean deleteUser(Integer id);
	//查詢
	public User queryUser(Integer id);



}
