package com.ers.middle;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PrivateResources extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		/*if(req.getRequestURI().equals("/Auth/secure/data.jsp"))
			//absolute path
			resp.sendRedirect(req.getContextPath()+"/secure/data.jsp");
		if(req.getRequestURI().equals("/Auth/secure/data.jsp")){
			resp.sendRedirect(req.getContextPath() + "/login.jsp");
		}*/
		System.out.println(req.getRequestURI());
		//default
		resp.getWriter().print("Successfully opened secret data!!");
	}
}
