package edu.ycp.cs320.otterspace.servlet;

import java.util.List;
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import edu.ycp.cs320.otterspace.model.User;
import edu.ycp.cs320.roomsdb.persist.*;

public class PlayersServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		User u = new User();
		IDatabase db = DatabaseProvider.getInstance();
		List<User> users = db.findAllUsers();
		u = users.get(0);
		System.out.println("PlayersServlet: doGet");
		req.setAttribute("users", users);
		req.setAttribute("user",  u);
		
		req.getRequestDispatcher("/_view/Players.jsp").forward(req, resp);
	}

}
