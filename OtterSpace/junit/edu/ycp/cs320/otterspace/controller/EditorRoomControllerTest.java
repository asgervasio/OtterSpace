package edu.ycp.cs320.otterspace.controller;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import edu.ycp.cs320.otterspace.controller.game.Room;
import edu.ycp.cs320.otterspace.model.EditorRoomModel;

public class EditorRoomControllerTest {
	private EditorRoomModel model;
	private EditorRoomController controller;
	private Room roomBlank;
	
	// Set-up for the upcoming tests
	@Before
	public void setUp() {
		model = new EditorRoomModel();
		controller = new EditorRoomController();
		HashMap<String, Integer> connections = new HashMap<String, Integer>();
		connections.put("North", 5);
		connections.put("Warp", 2);
		
		controller.setModel(model);
		
		model.setTitle("title");
		model.setDescription("description");
		model.setRequirement(true);
		model.setConnections(connections);
	}
	
	@Test
	public void testCreateRoom(){
		roomBlank = controller.createRoom();
		assertEquals(roomBlank.getTitle(), model.getTitle());
		assertEquals(roomBlank.getRequirement(), model.getRequirement());
	}
	
}// end of class
