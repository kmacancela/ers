package com.ers.middle;

import java.util.List;

import javax.naming.AuthenticationException;

import org.mindrot.jbcrypt.BCrypt;

import com.ers.beans.Reimbursement;
import com.ers.beans.Users;
import com.ers.data.Facade;


public class UserService {

	/**
	 * Returns the Users object of the user with the given username and password. If user does not
	 * exist that throw an authentication exception. Uses BCrypt to check password.
	 * @param username
	 * @param password
	 * @return
	 * @throws AuthenticationException
	 */
	public Users authenticate(String username, String password) throws AuthenticationException{
		Facade facade = new Facade();
		Users user = facade.getUserByName(username);
		if(user == null) throw new AuthenticationException();
		else if (BCrypt.checkpw(password, facade.usersHashedPassword(username))){
			System.out.println("True");
			return user;
		}
		throw new AuthenticationException();
	}
	
	/**
	 * Returns the list of reimbursements of the employee with the given username.
	 * @param username
	 * @return
	 */
	public List<Reimbursement> returnUserReimb(String username){
		Facade facade = new Facade();
		List<Reimbursement> reimb = facade.showAllReimbByUsername(username);
		return reimb;
	}

	/**
	 * Returns the list of reimbursements with a given status. Used for managers.
	 * @param status
	 * @return
	 */
	public List<Reimbursement> returnReimbByStatus(String status) {
		Facade facade = new Facade();
		List<Reimbursement> reimb = facade.showAllByStatus(status);
		System.out.println(reimb);
		return reimb;
	}

	/**
	 * Returns the list of reimbursements after a reimbursement has been updated given the new status, 
	 * manager's username, and reimbursement ID of the updated reimbursement.
	 * @param newStatus
	 * @param resolverUsername
	 * @param reimbId
	 * @return
	 */
	public List<Reimbursement> updateReimb(String newStatus, String resolverUsername, int reimbId) {
		Facade facade = new Facade();
		facade.updateReimbursement(newStatus, resolverUsername, reimbId);
		return returnReimbByStatus("Pending");
	}

	/**
	 * Returns the list of reimbursements after a new reimbursement has been added given the amount,
	 * description, manager's ID, and type of the new reimbursement.
	 * @param amount
	 * @param description
	 * @param author
	 * @param type
	 * @return
	 */
	public List<Reimbursement> addReimb(double amount, String description, int author, int type) {
		Facade facade = new Facade();
		Reimbursement newReimb = facade.addNewReimbursement(amount, description, author, type);
		return facade.showAllReimbByUsername(newReimb.getAuthor().getUsername());
	}

	/**
	 * Returns the list of completed (resolved) reimbursement that have been approved or denied
	 * by the given username. Used for managers.
	 * @param username
	 * @return
	 */
	public List<Reimbursement> showCompleted(String username) {
		Facade facade = new Facade();
		return facade.showCompleted(username);
	}
}
