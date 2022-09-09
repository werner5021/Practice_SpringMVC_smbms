package com.werner.vo;

import lombok.Data;

@Data
public class UserQueryReq {

	//前端帶來的
	private String queryname;
	private String queryUserRole;
	private String pageIndex;	
	
}
