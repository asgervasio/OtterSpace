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
		String username = null;
		String password = null;

		UserController p = new UserController();
		User model = new User();
		//get user and PW
		username = req.getParameter("username");
		password = req.getParameter("password");

		System.out.println("   Name: <" + username + "> PW: <" + password + ">");			

		if (username == null || password == null || username.equals("") || password.equals("")) {
			errorMessage = "Please specify both user name and password";
		} else {

			ArrayList<User> user = null;
			user = p.matchUserNameWithPassword(username);
			if(user != null && user.size()>0) {
				User u = user.get(0);
				System.out.println(u.getUsername());
				//Authenticate the user
				if(UserController.authenticate(u, password) == true){
					//Set the session true and set their username
					HttpSession session = req.getSession();
					session.setAttribute("username", u.getUsername());
					session.setAttribute("firstName", u.getFirstName());
					session.setAttribute("lastName", u.getLastName());
					session.setAttribute("emailAddress", u.getEmail());
					System.out.println("Session info");
					System.out.println(req.getSession().getAttribute("username"));
					System.out.println(req.getSession().getAttribute("fristName"));
					System.out.println(req.getSession().getAttribute("lastName"));
					System.out.println(req.getSession().getAttribute("emailAddress"));
					model.setSessionid(req.getSession().getId());
					//If user is an owner send them to a page of their restaurants
					
						
					
					//If user is a patron send to the homepage
					resp.sendRedirect(req.getContextPath() + "/Index");
					

				}
				//display error meassage for incorrect username or password
				else{
					errorMessage = "Incorrect Username or Password";
					req.setAttribute("errorMessage", errorMessage);
					req.getRequestDispatcher("/_view/Login.jsp").forward(req, resp);
				}
			}
			//otherwise, print an error message
			else{
				errorMessage = "Incorrect Username or Password";
				req.setAttribute("errorMessage", errorMessage);
				req.getRequestDispatcher("/_view/Login.jsp").forward(req, resp);
				System.out.println("   Invalid login - returning to /Login");
			}

			req.setAttribute("sessionid", model.getSessionid());
			req.getRequestDispatcher("/_view/Login.jsp").forward(req, resp);
		}
	}

}