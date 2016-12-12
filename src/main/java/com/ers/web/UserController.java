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
		//BusinessDelegate bd = new BusinessDelegate();
		try {
			Users session = new BusinessDelegate().login(username,password);
			request.getSession().setAttribute("user", session);
			
			List<Reimbursement> session2 = new BusinessDelegate().reimbursements(username);
			request.getSession().setAttribute("usersData", session2);
			
			request.getRequestDispatcher("index.jsp").forward(request, response);
		} catch (AuthenticationException e) {
			request.setAttribute("authFailed", "Try to login again");
			request.getRequestDispatcher("login.jsp").forward(request, response);
		}
	}

}
