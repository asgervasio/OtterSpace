package edu.ycp.cs320.otterspace.modelTest;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

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
		model.setConnections("Kitchen,Yard,Boss Fight");
		String answer = "Kitchen,Yard,Boss Fight";
		assertEquals(answer, model.getConnections());
	}
	
	@Test
	public void testSetLocation(){
		model.setLocation("25,2,10");;
		String answer = "25,2,10";
		assertEquals(answer, model.getLocation());
	}
		
}// end of class
