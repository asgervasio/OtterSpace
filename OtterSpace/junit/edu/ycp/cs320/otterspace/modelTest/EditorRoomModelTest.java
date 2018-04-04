
package edu.ycp.cs320.otterspace.modelTest;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import edu.ycp.cs320.otterspace.controller.game.Room;
import edu.ycp.cs320.otterspace.model.EditorRoomModel;

public class EditorRoomModelTest {
	private EditorRoomModel model;
	
	@Before
	public void setUp(){
		model = new EditorRoomModel();
	}
	
	@Test
	public void testSetTitle(){
		model.setTitle("Otter Space");
		String answer = "Otter Space";
		assertEquals(answer, model.getTitle());
	}
	
	@Test
	public void testSetDescription(){
		model.setDescription("A game about an otter thats in space");;
		String answer = "A game about an otter thats in space";
		assertEquals(answer, model.getDescription());
	}
	
	@Test
	public void testSetRequirement(){
		model.setRequirement("red");
		String answer = "red";
		assertEquals(answer, model.getRequirement());
	}
	
	@Test
	public void testConnections(){
		model.setConnectionTemp("Kitchen,Yard,Boss Fight");
		String answer = "Kitchen,Yard,Boss Fight";
		assertEquals(answer, model.getConnectionTemp());
	}
	
	@Test
	public void testSetLocation(){
		model.setLocation("25,2,10");;
		String answer = "25,2,10";
		assertEquals(answer, model.getLocation());
	}
		
	@Test
	public void testConnections2(){
		Room kitchen = new Room();
		Room yard = new Room();
		Room forest = new Room();
		Room lake = new Room();
		
		model.setConnection("north", kitchen);
		model.setConnection("south", yard);
		model.setConnection("east", forest);
		model.setConnection("west", lake);

		assertEquals(model.getConnections().get("north"), kitchen);
		assertEquals(model.getConnections().get("south"), yard);
		assertEquals(model.getConnections().get("east"), forest);
		assertEquals(model.getConnections().get("west"), lake);
	}

}// end of class