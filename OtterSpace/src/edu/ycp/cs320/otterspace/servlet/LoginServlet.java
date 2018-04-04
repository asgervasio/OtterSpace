package edu.ycp.cs320.otterspace.servlet;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//import ycp.cs320.teamProject.model.MileStone1_User;
import edu.ycp.cs320.otterspace.servlet.User;
import edu.ycp.cs320.otterspace.controller.GameController;

//Priority #1
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private GameController g;
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		System.out.println("\nLoginServlet: doGet");
		
		//no need to get session info here: this is the first page 
		req.getRequestDispatcher("/_view/login.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("\nLoginServlet: doPost");

		String errorMessage = null;
		String email = null;
		String password = null;

		
		User U = new User();
		//get user and PW
		
		
		email = req.getParameter("username");
		password = req.getParameter("password");

		System.out.println("   Name: <" + email + "> PW: <" + password + ">");			

		if (email == null || password == null || email.equals("") || password.equals("")) {
			errorMessage = "Please specify both user name and password";
		} 
					
						req.setAttribute("email", U.getEmail());


						resp.sendRedirect(req.getContextPath() + "/index");
					}

			

}