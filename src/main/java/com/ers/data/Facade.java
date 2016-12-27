package com.ers.data;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import com.ers.beans.Reimbursement;
import com.ers.beans.ReimbursementStatus;
import com.ers.beans.ReimbursementType;
import com.ers.beans.Users;

public class Facade {
	
	Connection getConnection(){
		Connection conn = null;
		try {
			conn = ServiceLocator.getERSDatabase().getConnection();
		} catch (SQLException e) {
			System.out.println("No connection established.");
		}
		return conn;
	}
	
	/**
	 * Returns a Users object when passed the user's ID. 
	 * @param userId
	 * @return
	 */
	public Users createUserObject(int userId){
		Connection conn = getConnection();
		UsersDAO userDAO = new UsersDAO(conn);
		Users user = null;
		try {
			user = userDAO.createUserObj(userId);
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}
	
	/**
	 * Returns a Users object when passed the user's username.
	 * @param username
	 * @return
	 */
	public Users getUserByName(String username){
		Connection conn = getConnection();
		UsersDAO dao = new UsersDAO(conn);
		Users result = null;
		try {
			result = dao.getUserInfoByUsername(username);
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * Returns a ReimbursementType object when passed the type ID.
	 * @param typeId
	 * @return
	 */
	public ReimbursementType createTypeObject(int typeId){
		Connection conn = getConnection();
		ReimbursementTypeDAO typeDAO = new ReimbursementTypeDAO(conn);
		ReimbursementType type = null;
		try {
			type = typeDAO.createTypeObj(typeId);
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return type;
	}
	
	/**
	 * Returns a ReimbursementStatus object when passed the status ID. 
	 * 1 is pending.
	 * 2 is approved.
	 * 3 is denied.
	 * @param statusId
	 * @return
	 */
	public ReimbursementStatus createStatusObject(int statusId){
		Connection conn = getConnection();
		ReimbursementStatusDAO statusDAO = new ReimbursementStatusDAO(conn);
		ReimbursementStatus status = null;
		try {
			status = statusDAO.createStatusObj(statusId);
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return status;
	}
	
	/**
	 * Returns a Reimbursement object after having added the reimbursement in the DB when passed
	 * the amount, description, user's ID, and type ID. 
	 * Only used by employees.
	 * @param amount
	 * @param description
	 * @param authorId
	 * @param typeId
	 * @return
	 */
	public Reimbursement addNewReimbursement(double amount, String description, int authorId, int typeId){
		Connection conn = getConnection();
		ReimbursementDAO reimbDAO = new ReimbursementDAO(conn);
		ReimbursementStatusDAO statusDAO = new ReimbursementStatusDAO(getConnection());
		Users author = null;
		ReimbursementType type = createTypeObject(typeId);
		ReimbursementStatus status = null;
		Reimbursement reimb = null;
		try {
			author = createUserObject(authorId);
			status = statusDAO.createStatusObj(1);
			reimb = new Reimbursement(reimbDAO.getNextId(), amount, getCurrentTimeStamp(),
					(Timestamp)null, description, author, null, status, type);
			reimbDAO.insert(reimb);
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return reimb;
	}
	
	/**
	 * Updated an existing reimbursement when passed the new status, the username of the resolver, and
	 * the reimbursement's ID.
	 * @param status
	 * @param username
	 * @param reimbId
	 */
	public void updateReimbursement(String status, String username, int reimbId){
		Connection conn = getConnection();
		Timestamp currentDate = getCurrentTimeStamp();
		ReimbursementDAO dao = new ReimbursementDAO(conn);
		try {
			dao.updateReimbursement(status, username, reimbId, currentDate);
			conn.close();
		} catch (SQLException e) {
			System.out.println("Could not update reimbursement ID# " + reimbId + ".");
		}
	}
	
	/**
	 * Returns a list of reimbursements when given the user's username. 
	 * Used only by employees.
	 * @param username
	 * @return
	 */
	public List<Reimbursement> showAllReimbByUsername(String username){
		Connection conn = getConnection();
		ReimbursementDAO dao = new ReimbursementDAO(conn);
		List<Reimbursement> result = null;
		try {
			result = dao.selectAllByUsername(username);
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * Returns a list of reimbursements of the given status. 
	 * Used by managers.
	 * @param status
	 * @return
	 */
	public List<Reimbursement> showAllByStatus(String status){
		Connection conn = getConnection();
		ReimbursementDAO dao = new ReimbursementDAO(conn);
		List<Reimbursement> result = null;
		try {
			result = dao.selectByStatus(status);
			conn.close();
		} catch (SQLException e) {
			System.out.println("Could not show pending reimbursements.");
		}
		System.out.println(result);
		return result;
	}
	
	/**
	 * Returns if the account was found given the username the user entered.
	 * @param username
	 * @return
	 */
	public boolean accountFound(String username){
		Connection conn = getConnection();
		UsersDAO dao = new UsersDAO(conn);
		try {
			if(dao.usernameFound(username)){
				System.out.println(username + " found!");
				conn.close();
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println(username + " not found!");
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * Returns if the login was correct provided the username and password.
	 * @param username
	 * @param password
	 * @return
	 */
	public boolean correctLogin(String username, String password){
		Connection conn = getConnection();
		UsersDAO dao = new UsersDAO(conn);
		try {
			if(dao.passwordCorrect(username, password)){
				System.out.println("Correct login! " + username + " is now logged in!");
				conn.close();
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Incorrect login. Try again.");
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * Returns 'Employee' or 'Manager' given the username of the user.
	 * @param username
	 * @return
	 */
	public String empOrManager(String username){
		Connection conn = getConnection();
		String result = null;
		UsersDAO dao = new UsersDAO(conn);
		try {
			if(dao.empOrManager(username) == 1){
				result = "Employee";
				conn.close();
				return result;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		result = "Manager";
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * Adds a new user to the DB. Made it for personal use but can be implemented with a sign up JSP page.
	 * @param username
	 * @param password
	 * @param fName
	 * @param lName
	 * @param email
	 * @param roleId
	 */
	public void addUserToDB(String username, String password, String fName, String lName, String email, int roleId){
		Connection conn = getConnection();
		UsersDAO dao = new UsersDAO(conn);
		try {
			dao.addUserToDB(username, password, fName, lName, email, roleId);
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Returns the user's hashed password given the user's username. For personal use.
	 * @param username
	 * @return
	 */
	public String usersHashedPassword(String username){
		Connection conn = getConnection();
		UsersDAO dao = new UsersDAO(conn);
		String hashed = null;
		try {
			hashed = dao.getHashed(username);
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return hashed;
	}
	
	/**
	 * Updated a user's unhashed password with a hashed version of their password given the user's
	 * username. Made for personal use. 
	 * @param username
	 */
	public void updateUsersWithHash(String username){
		Connection conn = getConnection();
		UsersDAO dao = new UsersDAO(conn);
		try {
			dao.updateUserPassword(username);
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Returns a list of completed (approved or denied) reimbursements resolved by the given username.
	 * For managers only.
	 * @param resolverUsername
	 * @return
	 */
	public List<Reimbursement> showCompleted(String resolverUsername){
		Connection conn = getConnection();
		ReimbursementDAO dao = new ReimbursementDAO(conn);
		List<Reimbursement> results = null;
		try {
			results = dao.reimbCompleted(resolverUsername);
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return results;
	}
	
	/**
	 * Returns the time stamp.
	 * @return
	 */
	
	public static java.sql.Timestamp getCurrentTimeStamp(){
	    java.util.Date today = new java.util.Date();
	    return new java.sql.Timestamp(today.getTime());
	}
}
