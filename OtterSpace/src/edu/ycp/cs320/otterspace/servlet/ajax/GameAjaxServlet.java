package edu.ycp.cs320.otterspace.servlet.ajax;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.ycp.cs320.otterspace.controller.GameController;
import edu.ycp.cs320.otterspace.controller.game.GameEngine;
import edu.ycp.cs320.otterspace.controller.game.Player;
import edu.ycp.cs320.otterspace.model.GameModel;

public class GameAjaxServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	public GameEngine game = new GameEngine();


	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		doRequest(req, resp);
		HttpSession session = req.getSession();
		String username = (String) session.getAttribute("username");
		System.out.println(username);


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
		String result = "";
		HttpSession session = req.getSession();
		String username = (String) session.getAttribute("username");

		controller.setModel(model);
		model.setUsername(username);
		String errorMessage = null;
		
		try 
		{
			cmd = getString(req, "cmd");
			model.setCommand(cmd);
			result = game.parse(model.getCommand(), model.getUsername());
		} 
		catch (NumberFormatException e) 
		{
			errorMessage = "Invalid String";
		}
		resp.setContentType("text/plain");
		resp.getWriter().println(result);

	}
	
	private String getString(HttpServletRequest req, String name) {
		// TODO Auto-generated method stub
		return req.getParameter(name);
	}
}
