package com.werner.vo;

import lombok.Data;

@Data
public class UserCreateReq {

	private String id;
	private String userCode;
	private String userName;
	private String userPassword;
	private String ruserPassword;
	private String gender;
	private String birthday;
	private String phone;
	private String address;
	private String userRole;
	
}
