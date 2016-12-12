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
}
