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
		
		IDatabase db = DatabaseProvider.getInstance();
		List<User> users = null;
		users = db.findAllUsers();
		String user = null;
		String email= null;
		String errorMessage   = null;
		List<String> usernames = new ArrayList<String>();
		List<String> emails = new ArrayList<String>();
		System.out.println("there are"+users.size()+"players");
		for(int i = 0; i < users.size(); i++){
			emails.add(i, users.get(i).getUsername());
			usernames.add(i, users.get(i).getEmail());
		}
		System.out.println("PlayersServlet: doGet");
		user = "";
		email = "";
		
		
		user = usernames.get(0);
		email = emails.get(0);
		req.setAttribute("user", user);
		req.setAttribute("users", usernames);	
		req.setAttribute("email", email);
		req.setAttribute("emaild", emails);	
		req.getRequestDispatcher("/_view/Players.jsp").forward(req, resp);
	}
	
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		

		// Add result objects as request attributes
		
		req.getRequestDispatcher("/_view/players.jsp").forward(req, resp);

	}
}