package com.werner.dao.role;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.werner.pojo.Role;

public interface RoleDao {
	
	//獲得角色清單
	public List<Role> getRoleList(Connection connection) throws SQLException;
}
