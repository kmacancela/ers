package com.ers.web;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns="*") //takes all HTTP requests
public class DispatcherServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String requestURI = request.getRequestURI();
		UserController userCtrl = new UserController();
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
				break;
			}
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
