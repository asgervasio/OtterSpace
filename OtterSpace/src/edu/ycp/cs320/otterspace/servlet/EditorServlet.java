package edu.ycp.cs320.otterspace.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.ycp.cs320.otterspace.controller.EditorRoomController;
import edu.ycp.cs320.otterspace.controller.game.Room;
import edu.ycp.cs320.otterspace.model.EditorRoomModel;
import edu.ycp.cs320.roomsdb.persist.FakeDatabase;

public class EditorServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	FakeDatabase database = new FakeDatabase();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		System.out.println("Editor Servlet: doGet");	
		
		// call JSP to generate empty form
		req.getRequestDispatcher("/_view/EditorRoomPage.jsp").forward(req, resp);	
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
		
		try {
			String title = req.getParameter("title");
			System.out.println(title);
			String description = req.getParameter("description");
			String requirement = req.getParameter("requirement");
			String connections = req.getParameter("connections");
			String itemName = req.getParameter("itemList");
			System.out.println(itemName);
			String locationX = req.getParameter("locationX");
			String locationY = req.getParameter("locationY");
			String locationZ = req.getParameter("locationZ");

			model.setTitle(title);
			model.setDescription(description);
			model.setConnections(connections);
			model.setRequirement(requirement);
			String location = locationX + "," + locationY + "," + locationZ + "!";
			model.setLocation(location);
			model.setItemList(itemName);
			Room room = controller.createRoom();
			System.out.println("Created the room, now adding to the database");
			database.insertRoom(room);
			System.out.println("Sent to database");
		
		} catch (NumberFormatException e){
			errorMessage = "Invalid input";
		}
		
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
		
		req.setAttribute("errorMessage", errorMessage);


		// Forward to view to render the result HTML document
		req.getRequestDispatcher("/_view/EditorRoomPage.jsp").forward(req, resp);
	}


}
