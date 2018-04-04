package edu.ycp.cs320.otterspace.servlet.ajax;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.ycp.cs320.otterspace.controller.GameController;
import edu.ycp.cs320.otterspace.model.GameModel;

public class GameAjaxServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doRequest(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doRequest(req, resp);
	}

	private void doRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Get parameters
		String cmd;
		
		// Check whether parameters are valid

		
		// Use a controller to process the request
		GameController controller = new GameController();
		GameModel model = new GameModel();
		controller.setModel(model);
		String errorMessage = null;

		try 
		{
			cmd = getString(req, "cmd");		
			model.setCommand(cmd);
		} 
		catch (NumberFormatException e) 
		{
			errorMessage = "Invalid String";
		}
		// Send back a response
		String result = "success";
		resp.setContentType("text/plain");
		resp.getWriter().println(result.toString());
	}
	
	private String getString(HttpServletRequest req, String name) {
		// TODO Auto-generated method stub
		return req.getParameter(name);
	}
}
