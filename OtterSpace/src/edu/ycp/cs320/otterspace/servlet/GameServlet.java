package edu.ycp.cs320.otterspace.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GameServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException 
	{
		
		System.out.println("GamePage Servlet: doGet");
		
		req.getRequestDispatcher("/_view/GamePage.jsp").forward(req, resp);
	}
}
