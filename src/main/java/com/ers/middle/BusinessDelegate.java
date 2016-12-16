package com.ers.middle;

import java.util.List;

import javax.naming.AuthenticationException;

import com.ers.beans.Reimbursement;
import com.ers.beans.Users;



public class BusinessDelegate {

	public Users login(String username, String password) throws AuthenticationException{
		return new UserService().authenticate(username, password);
	}
	
/*	public boolean login(String username, String password){
		UserService us = new UserService();
		return us.authenticate(username, password);
	}*/
	
/*	public List<String> getBrands(String username){
		List<String> brands = new ArrayList<>();
		if(username.equals("yo")){
			brands.add("He said yo");
			brands.add("it works!");
		}
		else{
			brands.add("He didnt say yo");
		}
		return brands;
	}*/
	
	public List<Reimbursement> reimbursements(String username){
		return new UserService().returnUserReimb(username);
	}
	
	public List<Reimbursement> reimbursementsByStatus(String status){
		return new UserService().returnReimbByStatus(status);
	}

	
	/**
	 * Wiil update the reimbursements and then return the pending reimbursements.
	 * @param rowId: the reimbursement ID of the reimbursement to be updated.
	 * @param resolverUsername: the username of the manager making this update.
	 * @param newStatus: the new status of the reimbursement. Either 'Approved' or 'Denied'.
	 * @return: a list of reimbursements (after the update has been performed).
	 */
	public List<Reimbursement> updateReimbursement(String rowId, String resolverUsername, String newStatus) {
		System.out.println(rowId +resolverUsername+ newStatus + "!!!!!!!!");
		return new UserService().updateReimb(newStatus, resolverUsername, Integer.parseInt(rowId));
	}

	public List<Reimbursement> addReimbursement(String amount, String description, String author, String type) {
		//System.out.println(amount + description + author + type +"##############");
		return new UserService().addReimb(Double.parseDouble(amount), description, Integer.parseInt(author), Integer.parseInt(type));
	}

/*	public List<Reimbursement> addReimbursement(String newAmount, String username, String newDescription, String newType) {
		new UserService().addReimb(newAmount, username, newDescription, newType);
		return new UserService().returnUserReimb(username);
	}*/
}
