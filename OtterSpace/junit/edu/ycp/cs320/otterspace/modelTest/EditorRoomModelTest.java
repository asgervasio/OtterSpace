package edu.ycp.cs320.otterspace.modelTest;

import static org.junit.Assert.*;

import java.awt.Color;

import org.junit.Before;
import org.junit.Test;

import edu.ycp.cs320.otterspace.model.EditorRoomModel;

public class EditorRoomModelTest {
	private EditorRoomModel model;
	private Color color;
	private boolean[] connections;
	private int x, y, z;
	
	@Before
	public void setUp(){
		model = new EditorRoomModel();
		
		connections = new boolean[]{true, false, false, false};
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
		model.setConnections(connections);;
		boolean answer1 = true;
		boolean answer2 = false;
		boolean answer3 = false;
		boolean answer4 = false;

		assertEquals(answer1, model.getConnections()[0]);
		assertEquals(answer2, model.getConnections()[1]);
		assertEquals(answer3, model.getConnections()[2]);
		assertEquals(answer4, model.getConnections()[3]);
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
