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
import edu.ycp.cs320.roomsdb.persist.DatabaseProvider;
import edu.ycp.cs320.roomsdb.persist.IDatabase;
import edu.ycp.cs320.otterspace.controller.*;
import edu.ycp.cs320.otterspace.model.*;

//Priority #1
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserController g;
	IDatabase db = DatabaseProvider.getInstance();
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
		String username = null;
		String password = null;

		
		User model = new User();
		//get user and PW
		UserController g = new UserController();
		g.setModel(model);
		username = req.getParameter("username");
		password = req.getParameter("password");

		System.out.println("   Name: <" + username + "> PW: <" + password + ">");			

		if (username == null || password == null || username.equals("") || password.equals("")) {
			errorMessage = "Please specify both Username and password";
			req.setAttribute("errorMessage", errorMessage);
			req.getRequestDispatcher("/_view/login.jsp").forward(req, resp);
			System.out.println("Please specify both Username and Password");
		} else {
			
			ArrayList<User> user = new ArrayList<User>();
			
			user = g.matchUserNameWithPassword(username, password);
			
			
			if(user != null && user.size()>0) {
				System.out.println("if(user != null && user.size()>0) { = true");
				User u = user.get(0);
				System.out.println(u.getUsername());
				//Authenticate the user
				if(UserController.authenticate(u, password) == true){
					System.out.println("UserController.authenticate(u, password) == true = true");
					HttpSession session = req.getSession();
					u.setSessionid(session.getId());
					session.setAttribute("username", u.getUsername());
					session.setAttribute("firstName", u.getFirstName());
					session.setAttribute("lastName", u.getLastName());
					session.setAttribute("emailAddress", u.getEmail());
					System.out.println("Session info:");
					System.out.println(req.getSession().getAttribute("username"));
					System.out.println(req.getSession().getAttribute("firstName"));
					System.out.println(req.getSession().getAttribute("lastName"));
					System.out.println(req.getSession().getAttribute("emailAddress"));
					System.out.println(req.getSession().getAttribute("sessionid"));
					model.setSessionid(req.getSession().toString());
					//If user is an owner send them to a page of their restaurants
					
						
					
					//If user is a patron send to the homepage
					resp.sendRedirect(req.getContextPath() + "/index");
					

				}
				//display error meassage for incorrect username or password
				else{
					System.out.println("UserController.authenticate(u, password) == true = false");
					errorMessage = "Incorrect Username or Password";
					req.setAttribute("errorMessage", errorMessage);
					req.getRequestDispatcher("/_view/login.jsp").forward(req, resp);
				}
			}
			//otherwise, print an error message
			else{
				System.out.println("if(user != null && user.size()>0) { = False");
				errorMessage = "Incorrect Username or Password";
				req.setAttribute("errorMessage", errorMessage);
				req.getRequestDispatcher("/_view/login.jsp").forward(req, resp);
				System.out.println("User is doesn");
			}

			req.setAttribute("sessionid", model.getSessionid());
			req.getRequestDispatcher("/_view/login.jsp").forward(req, resp);
		}
	}

}