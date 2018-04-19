package edu.ycp.cs320.otterspace.controller;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import edu.ycp.cs320.otterspace.controller.game.Item;
import edu.ycp.cs320.otterspace.model.EditorItemModel;

public class EditorItemControllerTest {
	private EditorItemModel model;
	private EditorItemController controller;
	private Item itemBlank;
	
	// Set-up before testing
	@Before
	public void setUp() {
		model = new EditorItemModel();
		controller = new EditorItemController();
		
		controller.setModel(model);
		model.setTitle("title");
		model.setDescription("description");
		model.setStatAffected("statAffected");
		model.setStatChangeVal(12);
		model.setRoomLocat(4);
	}

	// Testing
	@Test
	public void testCreateItem(){
		itemBlank = controller.createItem();
		assertEquals(itemBlank.getTitle(), model.getTitle());
		assertEquals(itemBlank.getRoomLocat(), model.getRoomLocat());
		assertEquals(itemBlank.getStatChangeVal(), model.getStatChangeVal());		
	}
	
}// end of class
