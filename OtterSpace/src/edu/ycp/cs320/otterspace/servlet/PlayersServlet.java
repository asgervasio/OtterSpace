package edu.ycp.cs320.otterspace.servlet;

import java.util.List;
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.ycp.cs320.otterspace.model.User;
import edu.ycp.cs320.roomsdb.persist.*;

public class PlayersServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		HttpSession session = req.getSession();
		System.out.println(session.getAttribute("username"));
		IDatabase db = DatabaseProvider.getInstance();
		List<User> poop = null;
		poop = db.findAllUsers();
		String errorMessage   = null;
		
		
		System.out.println("PlayersServlet: doGet");
		
		
		req.setAttribute("poop", poop);
		req.getRequestDispatcher("/_view/Players.jsp").forward(req, resp);
	}
	
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		

		// Add result objects as request attributes
		
		req.getRequestDispatcher("/_view/Players.jsp").forward(req, resp);

	}
}