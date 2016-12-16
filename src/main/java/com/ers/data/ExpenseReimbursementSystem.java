package com.ers.data;

import org.mindrot.jbcrypt.BCrypt;

public class ExpenseReimbursementSystem {
	
	public static void main(String[] args) {
		Facade facade = new Facade();
		
/*		facade.addUserToDB("treehugger", "always", "Chris", "Flores", "cflores@gmail.com", 2);*/
		//facade.getUserByName("pusheen");
		
/*		if (BCrypt.checkpw("always", facade.usersHashedPassword("treehugger")))
			System.out.println("It matches");
		else System.out.println("It does not match");*/
		
		facade.updateUsersWithHash("pusheen");
		
/*		Users user = facade.createUserObject(1);
		//Users user = facade.getUserByName("123unicorn");
		System.out.println(user);*/
		//System.out.println(user.getPassword().equals("party5"));
		
/*		System.out.println(facade.showAllReimbByUsername("burgers4me"));*/
		
/*		facade.showAllByStatus("Pending");*/
		
/*		facade.updateReimbursement("Approved", "coder123", 11);*/
		
/*		facade.showAllReimbByUsername("bird102");*/
		
		//facade.showAllPending();
		//facade.showAllApproved();
		//facade.showAllDenied();
		
		//facade.showAllUserPending("bird102");
		//facade.showAllUserPending("bird102");
		//facade.showAllUserDenied("bird102");
		
/*		facade.accountFound("bird1000");*/
			
		//Users author = new Users(2,facade.getUsername(2), facade.getPassword(2), facade.getUserFirstName(2), facade.getUserLastName(2), facade.getUserEmail(2), facade.getUserRoleId(2));
		
		//ReimbursementType type = new ReimbursementType(2,facade.typeGivenId(2));


/*		Users author = facade.createUserObject(2);
		ReimbursementType type = facade.createTypeObject(2);*/
		
/*		facade.createReimbObject(100.49, "Travel to Canada", 2, 2);*/
		
		//facade.addNewReimbursement(reimbObj);
		
	}
}
