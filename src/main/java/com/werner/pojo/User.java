package com.werner.pojo;

import java.util.Date;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

	private Integer id;	
	private String userCode;
	private String userName;
	private String userPassword;
	private Integer gender;	
	private Date birthday;
	private String phone;
	private String address;
	private Integer userRole;
	private Integer createdBy;
	private Date creationDate;
	private Integer modifyBy;
	private Date modifyDate;
	
	private Integer age;
	private String userRoleName;
	
	public Integer getAge() {
		Date date = new Date();
		Integer age = date.getYear()-birthday.getYear();
		return age;
	}
	
	
}
