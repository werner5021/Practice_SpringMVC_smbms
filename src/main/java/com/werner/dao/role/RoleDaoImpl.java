package com.werner.dao.role;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.werner.dao.BaseDao;
import com.werner.pojo.Role;

@Repository
public class RoleDaoImpl implements RoleDao{

	//獲得角色列表
	public List<Role> getRoleList(Connection connection) throws SQLException {
		
		PreparedStatement pstm = null;
		ResultSet rs = null;
		List<Role> roleList = new ArrayList<Role>();

		if(connection != null) {
			String sql = "select * from smbms_role";
			Object[] params = {};
			
			rs = BaseDao.execute(connection, pstm, rs, sql, params);
			while(rs.next()) {
				Role role = new Role();
				role.setId(rs.getInt("id"));
				role.setRoleCode(rs.getString("roleCode"));
				role.setRoleName(rs.getString("roleName"));
				roleList.add(role);
			}
			BaseDao.closeResources(null, pstm, rs);
		}
		return roleList;
	}
}
