package com.werner.service.role;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.werner.dao.BaseDao;
import com.werner.dao.role.RoleDaoImpl;
import com.werner.pojo.Role;

@Service
public class RoleServiceImpl implements RoleService{

	@Autowired
	private RoleDaoImpl roleDao;
	
	//獲得角色列表
	public List<Role> getRoleList() throws SQLException {
		
		Connection connection = null;
		List<Role> roleList = null;
		
		connection = BaseDao.getConnection();
		roleList = roleDao.getRoleList(connection);
		BaseDao.closeResources(connection, null, null);		
		
		return roleList;
	}

}
