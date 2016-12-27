package com.ers.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.mindrot.jbcrypt.BCrypt;

import com.ers.beans.UserRoles;
import com.ers.beans.Users;

class UsersDAO {
	private Connection conn;

	public UsersDAO(Connection conn) {
		super();
		this.conn = conn;
	}
	
	/**
	 * Returns a Users object of the passed username.
	 * @param givenUsername
	 * @return
	 * @throws SQLException
	 */
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
			UserRoles role = null;
			if(roleId != 0){
				UserRolesDAO roleDAO = new UserRolesDAO(conn);
				role = roleDAO.createRoleObj(roleId);
			}
			
			obj = new Users(id,username,password,firstName,lastName,email,role);
		}
		System.out.println("Users object: " +obj);
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
	
	/**
	 * Returns if the password is correct or not given the username and password of the user.
	 * @param username
	 * @param password
	 * @return
	 * @throws SQLException
	 */
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
	
	/**
	 * Returns 1 for employee or 2 for manager after determining if the user is an employee or manager.
	 * @param username
	 * @return
	 * @throws SQLException
	 */
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
	
	/**
	 * Creates and returns a Users object when passed the user ID.
	 * @param userId
	 * @return
	 * @throws SQLException
	 */
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
	
	/**
	 * Returns the hashed password of an unhashed password.
	 * @param unhashedPassword
	 * @return
	 */
	public String hashPassword(String unhashedPassword){
		return BCrypt.hashpw(unhashedPassword, BCrypt.gensalt(15));
	}
	
	/**
	 * Retrieves the int of the next ID available in the ERS_USERS table.
	 * @return
	 * @throws SQLException
	 */
	public int getNextId() throws SQLException{
		String sqlForMaxId = "SELECT MAX(ERS_USERS_ID) FROM ERS_USERS";
		PreparedStatement maxId = conn.prepareStatement(sqlForMaxId);
		ResultSet rs = maxId.executeQuery();
		rs.next();
		int theId = rs.getInt("MAX(ERS_USERS_ID)") + 1;
		return theId;
	}
	
	/**
	 * Adds a user to the DB.
	 * @param username
	 * @param password
	 * @param fName
	 * @param lName
	 * @param email
	 * @param roleId
	 * @throws SQLException
	 */
	public void addUserToDB(String username, String password, String fName, String lName, String email, int roleId) throws SQLException{
		String sql = "INSERT INTO ERS_USERS (ERS_USERS_ID, ERS_USERNAME, ERS_PASSWORD, USER_FIRST_NAME, USER_LAST_NAME, USER_EMAIL, USER_ROLE_ID) " + 
					 "VALUES (?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setInt(1, getNextId());
		stmt.setString(2, username);
		System.out.println("before hash");
		stmt.setString(3, hashPassword(password));
		//stmt.setString(3, password);
		System.out.println("after hash");
		stmt.setString(4, fName);
		stmt.setString(5, lName);
		stmt.setString(6, email);
		stmt.setInt(7, roleId);
		stmt.executeUpdate();
		conn.commit();
		System.out.println("commited");
	}
	
	/**
	 * Returns the hashed password of a user's username.
	 * @param username
	 * @return
	 * @throws SQLException
	 */
	public String getHashed(String username) throws SQLException{
		String sql = "SELECT ERS_PASSWORD FROM ERS_USERS " + 
					 "WHERE ERS_USERNAME = ?";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setString(1, username);
		System.out.println("before execute");
		ResultSet rs = stmt.executeQuery();
		System.out.println("after execute");
		rs.next();
		String hashed = rs.getString("ERS_PASSWORD");
		System.out.println(hashed);
		return hashed;
	}
	
	/**
	 * Updated a user's password. Temporary use, was used to update unhashed passwords.
	 * @param username
	 * @throws SQLException
	 */
	public void updateUserPassword(String username) throws SQLException{
		String sql2 = "SELECT ERS_PASSWORD FROM ERS_USERS " + 
				 	  "WHERE ERS_USERNAME = ?";
		PreparedStatement stmt2 = conn.prepareStatement(sql2);
		stmt2.setString(1, username);
		ResultSet rs = stmt2.executeQuery();
		rs.next();
		String unhashed = rs.getString("ERS_PASSWORD");
		System.out.println(unhashed);
		String sql = "UPDATE ERS_USERS " + 
					 "SET ERS_PASSWORD = ?" +
					 "WHERE ERS_USERNAME = ?";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setString(1, hashPassword(unhashed));
		stmt.setString(2, username);
		stmt.executeUpdate();
		conn.commit();
		System.out.println("password updated");
	}
}

