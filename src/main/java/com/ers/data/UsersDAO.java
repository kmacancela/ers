package com.ers.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ers.beans.UserRoles;
import com.ers.beans.Users;

class UsersDAO {
	private Connection conn;

	public UsersDAO(Connection conn) {
		super();
		this.conn = conn;
	}
	
/*	//don't think i need
	public void selectAllUsers() throws SQLException{
		String sql = "SELECT * FROM ERS_USERS";
		PreparedStatement stmt = conn.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
		mapRows(rs);	
	}*/

	/**
	 * 
	 * @param givenId
	 * @throws SQLException
	 */
	
	public void selectById(int givenId) throws SQLException{
		String sql = "SELECT * FROM ERS_USERS WHERE " + 
					 "ERS_USERS_ID = ?";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setInt(1,givenId);
		ResultSet rs = stmt.executeQuery();
		Users obj = null;
		while(rs.next()){
			int id = rs.getInt("ERS_USERS_ID");
			String username = rs.getString("ERS_USERNAME");
			String password = rs.getString("ERS_PASSWORD");
			String firstName = rs.getString("USER_FIRST_NAME");
			String lastName = rs.getString("USER_LAST_NAME");
			String email = rs.getString("USER_EMAIL");
			
			int roleId = rs.getInt("USER_ROLE_ID");
			UserRolesDAO roleDAO = new UserRolesDAO(conn);
			UserRoles role = roleDAO.createRoleObj(roleId);
			obj = new Users(id,username,password,firstName,lastName,email,role);
		}
		System.out.println(obj);
	}
	
	/**
	 * 
	 * @param rs
	 * @throws SQLException
	 */
	
	private void mapRows(ResultSet rs) throws SQLException {
		List<Users> results = new ArrayList<Users>();
		while(rs.next()){
			int id = rs.getInt("ERS_USERS_ID");
			String username = rs.getString("ERS_USERNAME");
			String password = rs.getString("ERS_PASSWORD");
			String firstName = rs.getString("USER_FIRST_NAME");
			String lastName = rs.getString("USER_LAST_NAME");
			String email = rs.getString("USER_EMAIL");
			
			int roleId = rs.getInt("USER_ROLE_ID");
			UserRolesDAO roleDAO = new UserRolesDAO(conn);
			UserRoles role = roleDAO.createRoleObj(roleId);
			
			Users obj = new Users(id,username,password,firstName,lastName,email,role);
			results.add(obj);
		}
		for(int i = 0; i < results.size(); i++) {
            System.out.println(results.get(i));
        }
	}
	
	/**
	 * 
	 * @param givenUsername
	 * @return
	 * @throws SQLException
	 */
	
	//would return 1 row because username is unique
	public Users getUserInfoByUsername(String givenUsername) throws SQLException{
		String sql = "SELECT * FROM ERS_USERS " +
					 "WHERE ERS_USERNAME = ?";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setString(1, givenUsername);
		ResultSet rs = stmt.executeQuery();
		Users obj = null;
		while(rs.next()){
			int id = rs.getInt("ERS_USERS_ID");
			String username = rs.getString("ERS_USERNAME");
			String password = rs.getString("ERS_PASSWORD");
			String firstName = rs.getString("USER_FIRST_NAME");
			String lastName = rs.getString("USER_LAST_NAME");
			String email = rs.getString("USER_EMAIL");
			
			int roleId = rs.getInt("USER_ROLE_ID");
			UserRolesDAO roleDAO = new UserRolesDAO(conn);
			UserRoles role = roleDAO.createRoleObj(roleId);
			
			obj = new Users(id,username,password,firstName,lastName,email,role);
		}
		System.out.println(obj);
		return obj;
	}
	
	/**
	 * Username was found. User exist!
	 * @param username
	 * @return
	 * @throws SQLException
	 */
	
	public boolean usernameFound(String username) throws SQLException{
		String sql = "SELECT ERS_USERNAME FROM ERS_USERS " +
					 "WHERE ERS_USERNAME = ?";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setString(1, username);
		if(stmt.executeUpdate() == 1) return true;	
		return false;
	}
	
	public boolean passwordCorrect(String username, String password) throws SQLException{
		if(!usernameFound(username)){
			System.out.println("User not found.");
			return false;
		}
		String sql = "SELECT ERS_USERNAME, ERS_PASSWORD FROM ERS_USERS " +
					 "WHERE ERS_USERNAME = ? AND ERS_PASSWORD = ?";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setString(1, username);
		stmt.setString(2, password);
		if(stmt.executeUpdate() == 1) return true;
		System.out.println("Incorrect password.");
		return false;
	}
	
	public int empOrManager(String username) throws SQLException{
		String sql = "SELECT USER_ROLE_ID FROM ERS_USERS " + 
					 "WHERE ERS_USERNAME = ?";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setString(1, username);
		ResultSet rs = stmt.executeQuery();
		rs.next();
		if(rs.getInt("USER_ROLE_ID") == 1) return 1;
		return 2;
	}
	
	public Users createUserObj(int userId) throws SQLException{
		String sql = "SELECT ERS_USERNAME, ERS_PASSWORD, USER_FIRST_NAME, USER_LAST_NAME, USER_EMAIL, USER_ROLE_ID FROM ERS_USERS " +
					 "WHERE ERS_USERS_ID = ?";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setInt(1, userId);
		ResultSet rs = stmt.executeQuery();
		rs.next();
		String username = rs.getString("ERS_USERNAME");
		String password = rs.getString("ERS_PASSWORD");
		String firstName = rs.getString("USER_FIRST_NAME");
		String lastName = rs.getString("USER_LAST_NAME");
		String email = rs.getString("USER_EMAIL");
		
		int roleId = rs.getInt("USER_ROLE_ID");
		UserRolesDAO roleDAO = new UserRolesDAO(conn);
		UserRoles role = roleDAO.createRoleObj(roleId);
		
		Users user = new Users(userId, username, password, firstName, lastName, email, role);
		return user;
	}
}

