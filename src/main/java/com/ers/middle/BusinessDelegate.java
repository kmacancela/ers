package com.ers.middle;

import java.util.List;

import javax.naming.AuthenticationException;

import com.ers.beans.Reimbursement;
import com.ers.beans.Users;



public class BusinessDelegate {

	/**
	 * Returns the Users object if user exist when the username and password are passed at login.
	 * @param username
	 * @param password
	 * @return
	 * @throws AuthenticationException
	 */
	public Users login(String username, String password) throws AuthenticationException{
		return new UserService().authenticate(username, password);
	}
	
	/**
	 * Returns the list of reimbursements of the user with the given username.
	 * @param username
	 * @return
	 */
	public List<Reimbursement> reimbursements(String username){
		return new UserService().returnUserReimb(username);
	}
	
	/**
	 * Returns the list of reimbursements with the given status.
	 * @param status
	 * @return
	 */
	public List<Reimbursement> reimbursementsByStatus(String status){
		return new UserService().returnReimbByStatus(status);
	}

	/**
	 * Returns the list of reimbursements after a reimbursement has been updated, given the row ID
	 * of the reimbursement to be updated, the manager's username, and the new status given to the 
	 * reimbursement.
	 * @param rowId
	 * @param resolverUsername
	 * @param newStatus
	 * @return
	 */
	public List<Reimbursement> updateReimbursement(String rowId, String resolverUsername, String newStatus) {
		return new UserService().updateReimb(newStatus, resolverUsername, Integer.parseInt(rowId));
	}

	/**
	 * Returns the list of reimbursements after a reimbursement has been added, given the amount, description,
	 * employee's username, and type of the new reimbursement.
	 * @param amount
	 * @param description
	 * @param author
	 * @param type
	 * @return
	 */
	public List<Reimbursement> addReimbursement(String amount, String description, String author, String type) {
		return new UserService().addReimb(Double.parseDouble(amount), description, Integer.parseInt(author), Integer.parseInt(type));
	}

	/**
	 * Returns the list of reimbursements that have been either approved or denied by the manager with the
	 * given username.
	 * @param username
	 * @return
	 */
	public List<Reimbursement> completedReimbursements(String username) {
		return new UserService().showCompleted(username);
	}
}
