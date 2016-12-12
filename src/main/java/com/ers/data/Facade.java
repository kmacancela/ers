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
		UsersDAO userDAO = new UsersDAO(getConnection());
		Users user = null;
		user = userDAO.createUserObj(userId);
		return user;
	}
	
	
	public Users getUserByName(String username){
		UsersDAO dao = new UsersDAO(getConnection());
		Users result = null;
		try {
			result = dao.getUserInfoByUsername(username);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	public ReimbursementType createTypeObject(int typeId){
		ReimbursementTypeDAO typeDAO = new ReimbursementTypeDAO(getConnection());
		ReimbursementType type = null;
		try {
			type = typeDAO.createTypeObj(typeId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return type;
	}
	
	public ReimbursementStatus createStatusObject(int statusId){
		ReimbursementStatusDAO statusDAO = new ReimbursementStatusDAO(getConnection());
		ReimbursementStatus status = null;
		try {
			status = statusDAO.createStatusObj(statusId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return status;
	}
	
	public void createReimbObject(double amount, String description, int authorId, int typeId) throws SQLException{
		ReimbursementDAO reimbDAO = new ReimbursementDAO(getConnection());
		ReimbursementStatusDAO statusDAO = new ReimbursementStatusDAO(getConnection());
		Users author = createUserObject(authorId);
		ReimbursementType type = createTypeObject(typeId);
			ReimbursementStatus status = statusDAO.createStatusObj(1);
			Reimbursement reimb = new Reimbursement(reimbDAO.getNextId(), amount, getCurrentTimeStamp(),
					(Timestamp)null, description, author, null, status, type);
			reimbDAO.insert(reimb);
	}
	
	public void addNewReimbursement(Reimbursement reimb){
		ReimbursementDAO dao = new ReimbursementDAO(getConnection());
		try {
			dao.insert(reimb);
		} catch (SQLException e) {
			System.out.println("Could not add new reimbursement.");
		}
	}
	
	public void updateReimbursement(String status, String username, int reimbId){
		Timestamp currentDate = getCurrentTimeStamp();
		ReimbursementDAO dao = new ReimbursementDAO(getConnection());
		try {
			dao.updateReimbursement(status, username, reimbId, currentDate);
		} catch (SQLException e) {
			System.out.println("Could not update reimbursement ID# " + reimbId + ".");
		}
	}
	
	public List<Reimbursement> showAllReimbByUsername(String username){
		ReimbursementDAO dao = new ReimbursementDAO(getConnection());
		List<Reimbursement> result = null;
		try {
			result = dao.selectAllByUsername(username);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	//for managers
	public void showAllPending(){
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
	}
	
	//for employees
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
	}
	
	public boolean accountFound(String username){
		UsersDAO dao = new UsersDAO(getConnection());
		try {
			if(dao.usernameFound(username)){
				System.out.println(username + " found!");
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println(username + " not found!");
		return false;
	}
	
	public boolean correctLogin(String username, String password){
		UsersDAO dao = new UsersDAO(getConnection());
		try {
			if(dao.passwordCorrect(username, password)){
				System.out.println("Correct login! " + username + " is now logged in!");
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Incorrect login. Try again.");
		return false;
	}
	
	public String empOrManager(String username){
		String result = null;
		UsersDAO dao = new UsersDAO(getConnection());
		try {
			if(dao.empOrManager(username) == 1){
				result = "Employee";
				return result;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		result = "Manager";
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
	
	public void close(){
		try {
			getConnection().close();
		} catch (SQLException e) {
			System.out.println("Could not close connection.");
		}
	}
}
