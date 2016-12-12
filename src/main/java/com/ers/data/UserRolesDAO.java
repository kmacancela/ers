package com.ers.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ers.beans.UserRoles;
import com.ers.beans.Users;

class UserRolesDAO {
	
	private Connection conn;

	public UserRolesDAO(Connection conn) {
		super();
		this.conn = conn;
	}
	
	public void selectAll() throws SQLException{
		String sql = "SELECT * FROM ERS_USER_ROLES";
		PreparedStatement stmt = conn.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
		mapRows(rs);	
	}

	public UserRoles selectById(int givenId) throws SQLException{
		String sql = "SELECT USER_ROLE FROM ERS_USERS " + 
					 "WHERE ERS_USERS_ID = ?";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setInt(1,givenId);
		ResultSet rs = stmt.executeQuery();
		rs.next();
		String role = rs.getString("USER_ROLE");
		UserRoles roleObj = new UserRoles(givenId, role);
		System.out.println(roleObj);
		return roleObj;
	}
	
	private void mapRows(ResultSet rs) throws SQLException {
		List<UserRoles> results = new ArrayList<UserRoles>();
		while(rs.next()){
			int roleId = rs.getInt("ERS_USER_ROLE_ID");
			String role = rs.getString("USER_ROLE");
			UserRoles obj = new UserRoles(roleId,
								  role);
								  
			results.add(obj);
		}
		for(int i = 0; i < results.size(); i++) {
            System.out.println(results.get(i));
        }
	}
}
