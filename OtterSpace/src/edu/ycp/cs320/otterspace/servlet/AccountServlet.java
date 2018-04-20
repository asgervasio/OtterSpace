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
		
		System.out.println("\nAccountServlet: doGet");
		HttpSession session = req.getSession();
		req.setAttribute("username", session.getAttribute("username"));
		req.setAttribute("password", session.getAttribute("password"));
		req.setAttribute("emailAddress", session.getAttribute("emailAddress"));
		req.setAttribute("FirstName", session.getAttribute("FirstName"));
		req.setAttribute("LastName", session.getAttribute("LastName"));
		//no need to get session info here: this is the first page 
		req.getRequestDispatcher("/_view/Account.jsp").forward(req, resp);
	}
	
	@Override
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("AccountServlet: doPost");
		
		//session data 
		HttpSession session = req.getSession();
		
		User model = new User();
		
		UserController controller = new UserController();
		controller.setModel(model);
		
		
		req.setAttribute("username", session.getAttribute("username"));
		//emailAddress password firstname lastname Username
		if(req.getAttribute("password")==req.getAttribute("passconfirm")){
			if(req.getAttribute("password")!=null && !req.getAttribute("password").toString().isEmpty()){
				model.setPassword(req.getAttribute("password").toString());}}
		if(req.getAttribute("emailAddress")!=null && !req.getAttribute("emailAddress").toString().isEmpty()){
			model.setPassword(req.getAttribute("emailAddress").toString());}
		if(req.getAttribute("password")!=null && !req.getAttribute("password").toString().isEmpty()){
			model.setPassword(req.getAttribute("password").toString());}
		if(req.getAttribute("password")!=null && !req.getAttribute("password").toString().isEmpty()){
			model.setPassword(req.getAttribute("password").toString());}
		
		
		req.setAttribute("sessionid", model);
		if (req.getParameter("index") != null) {
			resp.sendRedirect(req.getContextPath() + "/Index");
		}
		
		req.getRequestDispatcher("/_view/Account.jsp").forward(req, resp);

	}
	



}
