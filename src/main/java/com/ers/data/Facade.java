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
	
	public Users createUserObject(int userId) throws SQLException{
		Connection conn = getConnection();
		UsersDAO userDAO = new UsersDAO(conn);
		Users user = null;
		user = userDAO.createUserObj(userId);
		conn.close();
		return user;
	}
	
	
	public Users getUserByName(String username){
		Connection conn = getConnection();
		UsersDAO dao = new UsersDAO(conn);
		Users result = null;
		try {
			result = dao.getUserInfoByUsername(username);
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
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
	
																	//maybe change this to String username
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
	
/*	public void addNewReimbursement(Reimbursement reimb){
		Connection conn = getConnection();
		ReimbursementDAO dao = new ReimbursementDAO(conn);
		try {
			dao.insert(reimb);
			conn.close();
		} catch (SQLException e) {
			System.out.println("Could not add new reimbursement.");
		}
	}*/
	
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
	
	//for managers
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
	
/*	public void showAllPending(){
		ReimbursementDAO dao = new ReimbursementDAO(getConnection());
		try {
			dao.selectByStatus("Pending");
		} catch (SQLException e) {
			System.out.println("Could not show pending reimbursements.");
		}
	}
	
	public void showAllApproved(){
		ReimbursementDAO dao = new ReimbursementDAO(getConnection());
		try {
			dao.selectByStatus("Approved");
		} catch (SQLException e) {
			System.out.println("Could not show approved reimbursements.");
		}
	}
	
	public void showAllDenied(){
		ReimbursementDAO dao = new ReimbursementDAO(getConnection());
		try {
			dao.selectByStatus("Denied");
		} catch (SQLException e) {
			System.out.println("Could not show denied reimbursements.");
		}
	}*/
	
/*	//for employees
	public void showAllUserPending(String username){
		ReimbursementDAO dao = new ReimbursementDAO(getConnection());
		try {
			dao.showAllByStatusOfUser("Pending", username);
		} catch (SQLException e) {
			System.out.println("Could not show pending reimbursements. The username " + username + " might not have been found.");
		}
	}
	
	public void showAllUserApproved(String username){
		ReimbursementDAO dao = new ReimbursementDAO(getConnection());
		try {
			dao.showAllByStatusOfUser("Approved", username);
		} catch (SQLException e) {
			System.out.println("Could not show approved reimbursements. The username " + username + " might not have been found.");
		}
	}
	
	public void showAllUserDenied(String username){
		ReimbursementDAO dao = new ReimbursementDAO(getConnection());
		try {
			dao.showAllByStatusOfUser("Denied", username);
		} catch (SQLException e) {
			System.out.println("Could not show denied reimbursements. The username " + username + " might not have been found.");
		}
	}*/
	
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * Returns the time stamp.
	 * @return
	 */
	
	public static java.sql.Timestamp getCurrentTimeStamp(){
	    java.util.Date today = new java.util.Date();
	    return new java.sql.Timestamp(today.getTime());
	}
	
/*	public void close(){
		try {
			getConnection().close();
		} catch (SQLException e) {
			System.out.println("Could not close connection.");
		}
	}*/
}
