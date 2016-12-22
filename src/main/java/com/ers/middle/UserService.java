package com.ers.middle;

import java.util.List;

import javax.naming.AuthenticationException;

import org.mindrot.jbcrypt.BCrypt;

import com.ers.beans.Reimbursement;
import com.ers.beans.Users;
import com.ers.data.Facade;


public class UserService {

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
	
	public List<Reimbursement> returnUserReimb(String username){
		Facade facade = new Facade();
		List<Reimbursement> reimb = facade.showAllReimbByUsername(username);
		return reimb;
	}

	public List<Reimbursement> returnReimbByStatus(String status) {
		Facade facade = new Facade();
		List<Reimbursement> reimb = facade.showAllByStatus(status);
		System.out.println(reimb);
		return reimb;
	}

	public List<Reimbursement> updateReimb(String newStatus, String resolverUsername, int reimbId) {
		Facade facade = new Facade();
		facade.updateReimbursement(newStatus, resolverUsername, reimbId);
		return returnReimbByStatus("Pending");
	}

	public List<Reimbursement> addReimb(double amount, String description, int author, int type) {
		Facade facade = new Facade();
		Reimbursement newReimb = facade.addNewReimbursement(amount, description, author, type);
		return facade.showAllReimbByUsername(newReimb.getAuthor().getUsername());
	}

	public List<Reimbursement> showCompleted(String username) {
		Facade facade = new Facade();
		return facade.showCompleted(username);
	}

/*	public void addReimb(String newAmount, String username, String newDescription, String newType) {
		Facade facade = new Facade();
		Reimbursement reimb = facade.createReimbObject(newAmount, newDescription, username, newType);
		facade.addNewReimbursement(reimb);
		
	}*/
}
