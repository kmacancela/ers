package com.ers.web;

import java.io.IOException;
import java.util.List;

import javax.naming.AuthenticationException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ers.beans.Reimbursement;
import com.ers.beans.Users;
import com.ers.middle.BusinessDelegate;

public class UserController {

	public void login(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		/*String selectedStatus  =  request.getParameter("newStatus");*/
		
		System.out.println(username);
		System.out.println(password);
		
		try {
			//returns the information of a user with the credentials above
			Users session = new BusinessDelegate().login(username,password);
			request.getSession().setAttribute("user", session);
			
			//returns the list of reimbursements of an employee to the employee
			List<Reimbursement> session2 = new BusinessDelegate().reimbursements(username);
			request.getSession().setAttribute("usersData", session2);
			
			//returns the list of pending reimbursements to the manager
			List<Reimbursement> session3 = new BusinessDelegate().reimbursementsByStatus("Pending");
			request.getSession().setAttribute("pending", session3);
					
/*			//add a new reimbursement
			String newAmount = request.getParameter("rAmount");
			String newDescription = request.getParameter("rDescription");
			String newType = request.getParameter("rType");
			List<Reimbursement> reimbAfterAdd = new BusinessDelegate().addReimbursement(newAmount, username, newDescription, newType);
			request.getSession().setAttribute("afterNew", reimbAfterAdd);
			//
*/			
			request.getRequestDispatcher("index.jsp").forward(request, response);
			
		} catch (AuthenticationException e) {
			request.setAttribute("authFailed", "Try to login again");
			request.getRequestDispatcher("login.html").forward(request, response);
		}
	}

	public void update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String resolver = request.getParameter("resolver");
		String newStatus = request.getParameter("newStatus");
		String rowId = request.getParameter("rowId");
		
		//String submit = request.getParameter("submit"); //need?
		List<Reimbursement> reimbAfterUpdate = new BusinessDelegate().updateReimbursement(rowId, resolver, newStatus);
		request.getSession().setAttribute("pending", reimbAfterUpdate);
		
		request.getRequestDispatcher("index.jsp").forward(request, response);
	}

	public void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String amount = request.getParameter("amount");
		String description = request.getParameter("description");
		String author = request.getParameter("author");
		String type = request.getParameter("type");
		
		List<Reimbursement> reimbAfterAdd = new BusinessDelegate().addReimbursement(amount, description, author, type);
		request.getSession().setAttribute("usersData", reimbAfterAdd);
		
		request.getRequestDispatcher("index.jsp").forward(request, response);
	}

}
