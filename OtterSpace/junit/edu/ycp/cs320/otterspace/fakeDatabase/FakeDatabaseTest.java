package edu.ycp.cs320.otterspace.fakeDatabase;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import edu.ycp.cs320.otterspace.controller.EditorRoomController;
import edu.ycp.cs320.otterspace.controller.game.Room;
import edu.ycp.cs320.otterspace.model.EditorRoomModel;
import edu.ycp.cs320.roomsdb.persist.FakeDatabase;

public class FakeDatabaseTest {
	private FakeDatabase database;
	private List<Room> roomList;
	private EditorRoomModel model;
	private EditorRoomController controller;
	private Room room1, room2, room3, room4;
	
	@Before
	public void setUp(){
		database = new FakeDatabase();
		
		model = new EditorRoomModel();
		controller = new EditorRoomController();
		controller.setModel(model);
		String title = "title";
		String description = "description";
		String requirement = "requirement";
		String connections = "connections";
		String itemName = "itemName";
		String itemNameNone = null;
		String location = "location";
		String actor = "actor";
		String actorNone = null;
		
		model.setTitle(title);
		model.setDescription(description);
		model.setRequirement(requirement);
		model.setConnectionTemp(connections);
		model.setItemList(itemName);
		model.setLocation(location);
		
		room1 = controller.createRoom();
		
		model.setItemList(itemNameNone);
		room2 = controller.createRoom();
	}
	
	// Tests to see if you can add a room that has an item but no Actor into the fake database
	@Test
	public void testInsertRoomWithItem(){
		database.insertRoom(room1);
		roomList = database.findRoomUsingTitle("title");
		assertEquals(room1.getTitle(), roomList.get(roomList.size()-1).getTitle());
		assertEquals(room1.getItems(), roomList.get(roomList.size()-1).getItems());
	}
	
	// Tests to see if you can add a room that has an Actor but no item into the fake database
	public void testInsertRoomWithActor(){
		
	}
	
	// Tests to see if you can add a room that has both an Actor and an item into the fake database
	public void testInsertRoomWithItemAndActor(){
		
	}
	
	// Tests to see if you can add a room that has neither an Actor nor an item into the fake database
	@Test
	public void testInsertRoomWithNeither(){
		database.insertRoom(room2);
		roomList = database.findRoomUsingTitle("title");
		assertEquals(room2.getTitle(), roomList.get(roomList.size()-1).getTitle());
		assertEquals(room2.getItems(), roomList.get(roomList.size()-1).getItems());
	}
		
}// end of class
