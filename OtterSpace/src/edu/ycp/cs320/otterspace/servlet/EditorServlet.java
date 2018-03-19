package edu.ycp.cs320.otterspace.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.ycp.cs320.otterspace.controller.EditorRoomController;
import edu.ycp.cs320.otterspace.model.EditorRoomModel;

public class EditorServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		System.out.println("Editor Servlet: doGet");	
		
		// call JSP to generate empty form
		req.getRequestDispatcher("/_view/editor.jsp").forward(req, resp);	
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		System.out.println("Editor Servlet: doPost");
		
		EditorRoomModel model = new EditorRoomModel();
		
		EditorRoomController controller = new EditorRoomController();

		controller.setModel(model);

		// holds the error message text, if there is any
		String errorMessage = null;
		
		// Add parameters as request attributes
		// this creates attributes named "first" and "second for the response, and grabs the
		// values that were originally assigned to the request attributes, also named "first" and "second"
		// they don't have to be named the same, but in this case, since we are passing them back
		// and forth, it's a good idea
		req.setAttribute("title", req.getParameter("title"));
		req.setAttribute("description", req.getParameter("description"));
		req.setAttribute("requirement", req.getParameter("requirement"));
		req.setAttribute("connections", req.getParameter("connections"));
		req.setAttribute("location", req.getParameter("location"));
		req.setAttribute("itemList", req.getParameter("itemList"));

		// Forward to view to render the result HTML document
		req.getRequestDispatcher("/_view/editor.jsp").forward(req, resp);
	}


}
