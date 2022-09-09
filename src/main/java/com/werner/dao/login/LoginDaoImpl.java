package com.werner.dao.login;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Repository;

import com.werner.dao.BaseDao;
import com.werner.pojo.User;

@Repository
public class LoginDaoImpl implements LoginDao{

	@Override
	public User findLoginUser(Connection connection, String userCode) throws SQLException {
		
		PreparedStatement pstm = null;
		ResultSet rs = null;
		User user = null;

		if(connection != null) {
			String sql = "select * from smbms_user where userCode=?";
			Object[] params = {userCode};

			rs = BaseDao.execute(connection, pstm, rs, sql, params);

			if(rs.next()) {
				user = new User(); //查出來的登入的用戶數據，封裝到用戶user裡面
				user.setId(rs.getInt("id"));
				user.setUserCode(rs.getString("userCode"));
				user.setUserName(rs.getString("userName"));
				user.setUserPassword(rs.getString("userPassword"));
				user.setGender(rs.getInt("gender"));
				user.setBirthday(rs.getDate("birthday"));
				user.setPhone(rs.getString("phone"));
				user.setAddress(rs.getString("address"));
				user.setUserRole(rs.getInt("userRole"));
				user.setCreatedBy(rs.getInt("createdBy"));
				user.setCreationDate(rs.getTimestamp("creationDate"));
				user.setModifyBy(rs.getInt("modifyBy"));
				user.setModifyDate(rs.getTimestamp("modifyDate"));
			}
			BaseDao.closeResources(null, pstm, rs);			
		}		
		return user;
	}

}
