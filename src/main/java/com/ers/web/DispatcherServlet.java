package com.ers.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.naming.AuthenticationException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ers.beans.Users;
import com.ers.middle.BusinessDelegate;

@WebServlet(urlPatterns="*") //takes all HTTP requests
public class DispatcherServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String requestURI = request.getRequestURI();
		UserController userCtrl = new UserController();
		/*userCtrl.login(request, response);*/
		switch(requestURI){
			case "/ers/home.do": {
				userCtrl.login(request, response);
				break;
			} 
			case "/ers/submitted.do":{
				userCtrl.update(request, response);
				break;
			}
			case "/ers/added.do":{
				userCtrl.add(request, response);
				/*response.sendRedirect("index.jsp");*/
				break;
			}
			/*case "/ers/pending.do":{
				userCtrl.pending()
				break;
			}*/
			case "/ers/logoff.do":{
				request.getSession().invalidate();
		        response.sendRedirect(request.getContextPath() + "/login.jsp");
				break;
			}
			default:{
				response.setStatus(404);
				response.sendRedirect("oops.html");
			}
		}
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doPost(req, resp);
	}
}
