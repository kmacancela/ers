package com.ers.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.naming.AuthenticationException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ers.beans.Users;
import com.ers.middle.BusinessDelegate;

public class DispatcherServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*String username = req.getParameter("username"); //add username in jsp as name in login tag
		String password = req.getParameter("password");
		BusinessDelegate bd = new BusinessDelegate();
		bd.authenticate(username, password);
		req.getRequestDispatcher("index.jsp").forward(req, resp);
		
		String requestURI = req.getRequestURI();*/
		/*switch(requestURI){
		case "/JSTL/employees.do":{
			new EmployeeController().doAll(request, response);			
			break;
		}case "/JSTL/pickDay.do":{
			new EmployeeController().pickDay(request, response);			
			break;
		}
		default:{
			response.setStatus(404);
		}
		}*/
/*		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("Beer Select Advice<br>");
		String c = request.getParameter("username");
		out.println("<br>Got username:" + c);*/
		
/*		String c = request.getParameter("username");
		BusinessDelegate bd = new BusinessDelegate();
		List<String> result = bd.getBrands(c);
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("Beer Select Advice<br>");
		Iterator it = result.iterator();
		while(it.hasNext()){
			out.print("<br>Try: " + it.next());
		}*/
		
/*		//WORKS
 * 		String c = request.getParameter("username");
		BusinessDelegate bd = new BusinessDelegate();
		//List<String> result = bd.getBrands(c);
		//request.setAttribute(arg0, arg1);
		RequestDispatcher view = request.getRequestDispatcher("index.jsp");
		view.forward(request, response);*/
		
/*		String username = request.getParameter("username");
		String password = request.getParameter("password");
		BusinessDelegate bd = new BusinessDelegate();
		if(bd.authenticate(username, password)){
			RequestDispatcher view = request.getRequestDispatcher("index.jsp");
			view.forward(request, response);
		} else{
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			out.println("Wrong login");
		}*/
		
		//String requestURI = request.getRequestURI();
		UserController userCtrl = new UserController();
		userCtrl.login(request, response);
		
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doPost(req, resp);
	}
}
