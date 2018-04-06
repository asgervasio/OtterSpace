package edu.ycp.cs320.otterspace.controller;

import static org.junit.Assert.*;

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
		
		controller.setModel(model);
		
		model.setTitle("title");
		model.setDescription("description");
		model.setRequirement("requirement");
		model.setConnectionTemp("connectionn");
		model.setItemList("items");
		model.setLocation("location");
	}
	
	@Test
	public void testCreateRoom(){
		roomBlank = controller.createRoom();
		assertEquals(roomBlank.getTitle(), model.getTitle());
		assertEquals(roomBlank.getRequirement(), model.getRequirement());
		assertEquals(roomBlank.getLocation(), model.getLocation());
	}
	
}// end of class
