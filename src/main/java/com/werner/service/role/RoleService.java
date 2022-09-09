package com.werner.service.role;

import java.sql.SQLException;
import java.util.List;

import com.werner.pojo.Role;

public interface RoleService {

	//獲得角色列表
	public List<Role> getRoleList() throws SQLException;
}
