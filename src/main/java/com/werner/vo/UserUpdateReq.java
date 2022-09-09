package com.werner.vo;

import lombok.Data;

@Data
public class UserUpdateReq {
	private String uid;
	private String userCode;
	private String userName;
	private String gender;
	private String birthday;
	private String phone;
	private String address;
	private String userRole;
}
