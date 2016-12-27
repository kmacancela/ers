package com.ers.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.ers.beans.UserRoles;

class UserRolesDAO {

	private Connection conn;

	public UserRolesDAO(Connection conn) {
		super();
		this.conn = conn;
	}

	/**
	 * Returns a UserRoles object when passed the role ID.
	 * @param roleId
	 * @return
	 * @throws SQLException
	 */
	public UserRoles createRoleObj(int roleId) throws SQLException {
		String sql = "SELECT USER_ROLE FROM ERS_USER_ROLES " + 
				   	 "WHERE ERS_USER_ROLE_ID = ?";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setInt(1, roleId);
		ResultSet rs = stmt.executeQuery();
		rs.next();
		String role = rs.getString("USER_ROLE");
		UserRoles roleObj = new UserRoles(roleId, role);
		System.out.println(roleObj);
		return roleObj;
	}
}
