package edu.ycp.cs320.otterspace.modelTest;

import static org.junit.Assert.*;

import java.awt.Color;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import edu.ycp.cs320.otterspace.controller.game.Room;
import edu.ycp.cs320.otterspace.model.EditorRoomModel;

public class EditorRoomModelTest {
	private EditorRoomModel model;
	private Color color;
	//private HashMap<String, Room> connections;
	private int x, y, z;
	
	@Before
	public void setUp(){
		model = new EditorRoomModel();
		
		x = 25;
		y = 2;
		z = 10;
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
		model.setRequirement(color.red);
		Color answer = color.red;
		assertEquals(answer, model.getRequirement());
	}
	
	@Test
	public void testConnections(){
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
	
	@Test
	public void testSetLocation(){
		model.setLocation(x, y, z);;
		int answerX = 25;
		int answerY = 2;
		int answerZ = 10;
		assertEquals(answerX, model.getLocation()[0]);
		assertEquals(answerY, model.getLocation()[1]);
		assertEquals(answerZ, model.getLocation()[2]);
	}
		
}// end of class
