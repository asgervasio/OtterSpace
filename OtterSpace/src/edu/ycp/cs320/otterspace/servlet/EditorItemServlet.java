package edu.ycp.cs320.otterspace.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.ycp.cs320.otterspace.controller.EditorItemController;
import edu.ycp.cs320.otterspace.controller.game.Item;
import edu.ycp.cs320.otterspace.model.EditorItemModel;
import edu.ycp.cs320.roomsdb.persist.FakeDatabase;

public class EditorItemServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	FakeDatabase database = new FakeDatabase();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		System.out.println("Editor Servlet: doGet");	
		
		// call JSP to generate empty form
		req.getRequestDispatcher("/_view/EditorItemPage.jsp").forward(req, resp);	
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		System.out.println("Editor Servlet: doPost");
		
		EditorItemModel model = new EditorItemModel();
		EditorItemController controller = new EditorItemController();
		controller.setModel(model);

		String errorMessage = null;
		try{
			String title = req.getParameter("title");
			String description = req.getParameter("description");
			String statAffected = req.getParameter("statAffected");
			String statChangeVal = req.getParameter("statChangeVal");
			String roomLocat = req.getParameter("roomLocat");
			
			model.setTitle(title);
			model.setDescription(description);
			model.setStatAffected(statAffected);
			model.setStatChangeVal(statChangeVal);
			model.setRoomLocat(roomLocat);
			Item item = controller.createItem();
			System.out.println("Created item, now sending to database");
			database.insertItem(item);
			System.out.println("Sent to database");
			
		} catch (NumberFormatException e){
			errorMessage = "Invalid input";
		}
		
		// Add parameters as request attributes
		req.setAttribute("title", req.getParameter("title"));
		req.setAttribute("description", req.getParameter("description"));
		req.setAttribute("statAffected", req.getParameter("statAffected"));
		req.setAttribute("statChangeVal", req.getParameter("statChangeVal"));
		req.setAttribute("roomLocat", req.getParameter("roomLocat"));

		req.setAttribute("errorMessage", errorMessage);

		// Forward to view to render the result HTML document
		req.getRequestDispatcher("/_view/EditorItemPage.jsp").forward(req, resp);		
	}
	
}
