package edu.ycp.cs320.otterspace.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import edu.ycp.cs320.otterspace.controller.GameController;
import edu.ycp.cs320.otterspace.model.GameModel;

public class GameServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
	{
		System.out.println("GamePage Servlet: doGet");
		req.getRequestDispatcher("/_view/GamePage.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		System.out.println("GamePage Servlet: doPost");
		GameModel model = new GameModel();
		GameController controller = new GameController();
		controller.setModel(model);

		// holds the error message text, if there is any
		String errorMessage = null;

		Double result = null;
		
		// decode POSTed form parameters and dispatch to controller
		try 
		{
			String cmd = getString(req, "cmd");		
			model.setCommand(cmd);
		} 
		catch (NumberFormatException e) 
		{
			errorMessage = "Invalid String";
		}
		
		// Add parameters as request attributes
		// this creates attributes named "first" and "second for the response, and grabs the
		// values that were originally assigned to the request attributes, also named "first" and "second"
		// they don't have to be named the same, but in this case, since we are passing them back
		// and forth, it's a good idea
		req.setAttribute("cmd", model);
		
		// add result objects as attributes
		// this adds the errorMessage text and the result to the response
		req.setAttribute("errorMessage", errorMessage);
		
		// Forward to view to render the result HTML document
		req.getRequestDispatcher("/_view/GamePage.jsp").forward(req, resp);
	}

	private String getString(HttpServletRequest req, String name) {
		// TODO Auto-generated method stub
		return req.getParameter(name);
	}
}
