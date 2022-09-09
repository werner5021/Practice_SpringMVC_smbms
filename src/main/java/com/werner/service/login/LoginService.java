package com.werner.service.login;

import com.werner.pojo.User;

public interface LoginService {
	
	public User findLoginUser(String userCode, String userPassword);
	
}
