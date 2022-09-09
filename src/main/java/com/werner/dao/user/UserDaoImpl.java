package com.werner.dao.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.mysql.jdbc.StringUtils;
import com.werner.dao.BaseDao;
import com.werner.pojo.User;

@Repository
public class UserDaoImpl implements UserDao{

	//修改使用者密碼
	public int updatePwd(Connection connection, int id, String userPassword) throws SQLException {

		PreparedStatement pstm = null;
		int updateRow =0;

		if(connection != null) {
			String sql = "update smbms_user set userPassword=? where id=?";
			Object[] params = {userPassword, id};
			updateRow = BaseDao.execute(connection, pstm, sql, params);

			BaseDao.closeResources(null, pstm, null);

			System.out.println("id為 "+id+" 的使用者修改密碼完成");
		}
		return updateRow;
	}

	//獲取使用者數量
	public int getUserCount(Connection connection, String userName, int userRole ) throws SQLException {

		PreparedStatement pstm = null;
		ResultSet rs = null;
		int count = 0;

		if(connection != null) {
			StringBuffer sql = new StringBuffer();
			List<Object> list = new ArrayList();

			sql.append("select count(1) as count from smbms_user u, smbms_role r where u.userRole=r.id");
			if(!StringUtils.isNullOrEmpty(userName)) {
				sql.append(" and u.userName like ?");
				list.add("%"+userName+"%");
			}
			if(userRole>0) {
				sql.append(" and u.userRole=?");
				list.add(userRole);
			}			
			Object[] params = list.toArray();

			rs = BaseDao.execute(connection, pstm, rs, sql.toString(), params);
			if(rs.next()) {
				count = rs.getInt("count");
			}
			BaseDao.closeResources(null, pstm, rs);
		}
		return count;	
	}

	//獲取使用者列表
	public List<User> getUserList(Connection connection, String userName, int userRole, int currentPage, int perPageSize) throws SQLException {

		PreparedStatement pstm = null;
		ResultSet rs = null;
		List<User> userList = new ArrayList<User>();

		if(connection != null) {
			StringBuffer sql = new StringBuffer();
			sql.append("select u.*, r.roleName as userRoleName from smbms_user u, smbms_role r where u.userRole=r.id");

			List<Object> list = new ArrayList<Object>();
			if(!StringUtils.isNullOrEmpty(userName)) {
				sql.append(" and u.userName like ?");
				list.add("%"+userName+"%");
			}
			if(userRole>0) {
				sql.append(" and u.userRole=?");
				list.add(userRole);
			}

			//從資料庫中分頁
			sql.append(" order by creationDate DESC limit ?,?");
			currentPage = (currentPage-1)*perPageSize;
			list.add(currentPage);
			list.add(perPageSize);

			Object[] params = list.toArray();

			rs = BaseDao.execute(connection, pstm, rs, sql.toString(), params);

			while(rs.next()) {
				User user = new User();
				user.setId(rs.getInt("id"));
				user.setUserCode(rs.getString("userCode"));
				user.setUserName(rs.getString("userName"));
				user.setGender(rs.getInt("gender"));
				user.setBirthday(rs.getDate("birthday"));
				user.setPhone(rs.getString("phone"));
				user.setUserRole(rs.getInt("userRole"));
				user.setUserRoleName(rs.getString("userRoleName"));
				userList.add(user);
			}
			BaseDao.closeResources(null, pstm, rs);
		}
		return userList; 
	}

	//新增使用者
	public int createUser(Connection connection, User user) throws SQLException {
		PreparedStatement pstm = null;
		int updateRows = 0;
		
		if(connection != null) {
			String sql = "insert into smbms_user (userCode, userName, userPassword, userRole, gender, birthday, phone, address, creationDate, createdBy) values (?,?,?,?,?,?,?,?,?,?)";
			Object[] params = {
					user.getUserCode(),
					user.getUserName(),
					user.getUserPassword(),
					user.getUserRole(),
					user.getGender(),
					user.getBirthday(),
					user.getPhone(),
					user.getAddress(),
					user.getCreationDate(),
					user.getCreatedBy()
			};
			updateRows = BaseDao.execute(connection, pstm, sql, params);
			BaseDao.closeResources(null, pstm, null);
		}		
		return updateRows;
	}

	//修改使用者
	public int updateUser(Connection connection, User user) throws SQLException {
		PreparedStatement pstm = null;
		int updateRows = 0;
		
		System.out.println("userId = "+user.getId());
		
		if(connection != null) {
			String sql= "update smbms_user set userName=?, userRole=?, gender=?, birthday=?, phone=?, address=?, modifyDate=?, modifyBy=? where id=?";
			Object[] params= {					
					user.getUserName(),					
					user.getUserRole(),
					user.getGender(),
					user.getBirthday(),
					user.getPhone(),
					user.getAddress(),
					user.getModifyDate(),
					user.getModifyBy(),
					user.getId()
			};
			updateRows = BaseDao.execute(connection, pstm, sql, params);
		}
		BaseDao.closeResources(null, pstm, null);
		return updateRows;
	}

	//刪除使用者
	public int deleteUser(Connection connection, Integer id) throws SQLException {
		PreparedStatement pstm = null;
		int updateRows = 0;
		
		if(connection != null) {
			String sql = "delete from smbms_user where id=?";
			Object[] params= {id};			
			updateRows = BaseDao.execute(connection, pstm, sql, params);
		}
		BaseDao.closeResources(null, pstm, null);		
		return updateRows;
	}

	//查詢使用者
	public User queryUser(Connection connection, Integer id) throws SQLException {
		PreparedStatement pstm = null;
		ResultSet rs = null;
		User user = null;
		
		if(connection != null) {
			String sql = "select u.*, r.roleName as userRoleName from smbms_user u, smbms_role r where u.userRole=r.id and u.id=?";
			Object[] params = {id};
			rs = BaseDao.execute(connection, pstm, rs, sql, params);
			if(rs.next()) {
				user = new User();
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
                user.setUserRoleName(rs.getString("userRoleName"));
			}
			BaseDao.closeResources(null, pstm, rs);
		}		
		return user;
	}
}
