package edu.ycp.cs320.otterspace.modelTest;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import edu.ycp.cs320.otterspace.model.EditorItemModel;

public class EditorItemModelTest {
	private EditorItemModel model;
	
	@Before
	public void setUp(){
		model = new EditorItemModel();
	}
	
	@Test
	public void testSetTitle(){
		model.setTitle("title");
		String answer = "title";
		assertEquals(answer, model.getTitle());
	}
	
	@Test
	public void testSetDescription(){
		model.setDescription("A game about an otter thats in space");
		String answer = "A game about an otter thats in space";
		assertEquals(answer, model.getDescription());
	}

	@Test
	public void testSetStatAffected(){
		model.setStatAffected("Attack");
		String answer = "Attack";
		assertEquals(answer, model.getStatAffected());
	}

	@Test
	public void testSetStatChangeVal(){
		model.setStatChangeVal("25");
		String answer = "25";
		assertEquals(answer, model.getStatChangeVal());
	}

	@Test
	public void testSetRoomLocat(){
		model.setRoomLocat("A room");
		String answer = "A room";
		assertEquals(answer, model.getRoomLocat());
	}

}
