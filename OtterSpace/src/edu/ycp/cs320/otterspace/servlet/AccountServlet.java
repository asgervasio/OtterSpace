package edu.ycp.cs320.otterspace.servlet;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//import ycp.cs320.teamProject.model.MileStone1_User;
import edu.ycp.cs320.otterspace.model.User;
import edu.ycp.cs320.otterspace.controller.*;
import edu.ycp.cs320.otterspace.model.*;


public class AccountServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		System.out.println("\nLoginServlet: doGet");
		
		//no need to get session info here: this is the first page 
		req.getRequestDispatcher("/_view/Register.jsp").forward(req, resp);
	}
	
	@Override
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("CreateAccountServlet: doPost");
		
		//session data 
		User model = new User();
		UserController controller = new UserController();
		controller.setModel(model);
		
		//I'm taking this out because the DB will assign a user number
		//int UserNumber = (int) req.getSession().getAttribute("UserID");
		//model.setUserID(UserNumber);
		String UserName = (String) req.getParameter("username");
		model.setLastName(UserName);
		String Password = (String) req.getParameter("password");
		model.setLastName(Password);
		String FirstName = (String) req.getParameter("FirstName");
		model.setFirstName(FirstName);
		String LastName = (String) req.getParameter("LastName");
		model.setLastName(LastName);
		String Email = (String) req.getParameter("Email");
		model.setEmail(Email);
		String isAdmin = (String) req.getParameter("Admin");
		model.setEmail(isAdmin);

		controller.addUserToDatabase(UserName, Password, Email, FirstName, LastName);
		req.setAttribute("sessionid", model);
		if (req.getParameter("index") != null) {
			resp.sendRedirect(req.getContextPath() + "/Index");
		}
		
		req.getRequestDispatcher("/_view/CreateAccount.jsp").forward(req, resp);

	}
	



}
