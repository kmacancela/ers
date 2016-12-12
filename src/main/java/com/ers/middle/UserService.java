package com.ers.middle;

import java.util.List;

import javax.naming.AuthenticationException;

import com.ers.beans.Reimbursement;
import com.ers.beans.Users;
import com.ers.data.Facade;


public class UserService {

	public Users authenticate(String username, String password) throws AuthenticationException{
		//String password = "password";
		// Hash a password for the first time
/*		Facade facade = new Facade();
		boolean correct = facade.correctLogin(username, password);
		return correct;*/
/*		String hashed = BCrypt.hashpw(password, BCrypt.gensalt());*/

		// gensalt's log_rounds parameter determines the complexity
		// the work factor is 2**log_rounds, and the default is 10
		//String hashed = BCrypt.hashpw(password, BCrypt.gensalt(12));

		// Check that an unencrypted password matches one that has
		// previously been hashed
		//String candidate = "passwod";
		
		
/*		if (BCrypt.checkpw(password, hashed)){
			System.out.println("It matches");
			return true;
		}
		System.out.println("It does not match"); 
		return false;*/
		
		
		Facade facade = new Facade();
		Users user = facade.getUserByName(username);
		if(user.getPassword().equals(password))
			return user;
		throw new AuthenticationException();
	}
	
	public List<Reimbursement> returnUserReimb(String username){
		Facade facade = new Facade();
		List<Reimbursement> reimb = facade.showAllReimbByUsername(username);
		return reimb;
	}
}
