package edu.ycp.cs320.otterspace.derbyDatabase;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import edu.ycp.cs320.otterspace.controller.EditorItemController;
import edu.ycp.cs320.otterspace.controller.EditorRoomController;
import edu.ycp.cs320.otterspace.controller.game.Item;
import edu.ycp.cs320.otterspace.controller.game.Room;
import edu.ycp.cs320.otterspace.model.EditorItemModel;
import edu.ycp.cs320.otterspace.model.EditorRoomModel;
import edu.ycp.cs320.roomsdb.persist.DerbyDatabase;

public class DerbyDatabaseTest {
	private DerbyDatabase database;
	private EditorRoomModel model;
	private EditorRoomController controller;
	private EditorItemModel itemModel;
	private EditorItemController itemController;
	private Room room1, roomBlank;
	private Item item, itemBlank;
 	
	@Before
	public void setUp(){
		database = new DerbyDatabase();
		
		model = new EditorRoomModel();
		itemModel = new EditorItemModel();
		controller = new EditorRoomController();
		itemController = new EditorItemController();
		controller.setModel(model);
		itemController.setModel(itemModel);
		String roomTitle = "title";
		String roomDescription = "description";
		boolean requirement = true;
		
		model.setTitle(roomTitle);
		model.setDescription(roomDescription);
		model.setRequirement(requirement);
		
		room1 = controller.createRoom();
				
		String itemTitle = "titlePerItem";
		String itemDescription = "descriptionPerItem";
		String statAffected = "affected";
		int statChangeVal = 12;
		int roomLocat = 4;
		
		itemModel.setTitle(itemTitle);
		itemModel.setDescription(itemDescription);
		itemModel.setStatAffected(statAffected);
		itemModel.setStatChangeVal(statChangeVal);
		itemModel.setRoomLocat(roomLocat);
		
		item = itemController.createItem();	
		
		database.createTables();
		database.loadInitialData();
		System.out.println("Created database!");
	}
	
	@Test
	public void testInsertRoom(){
		database.insertRoom(room1);
		roomBlank = database.findRoomUsingTitle("title");
		assertEquals(room1.getTitle(), roomBlank.getTitle());
	}
	
	@Test
	public void testInsertItem(){
		database.insertItem(item);
		itemBlank = database.findItemUsingTitle("titlePerItem");
		assertEquals(item.getTitle(), itemBlank.getTitle());
		assertEquals(item.getStatAffected(), itemBlank.getStatAffected());
	}
	
	@Test
	public void testFindItemUsingRoomLocat(){
		database.insertItem(item);
		itemBlank = database.findItemUsingLocation(4);
		assertEquals(item.getTitle(), itemBlank.getTitle());
		assertEquals(item.getStatAffected(), itemBlank.getStatAffected());		
	}
	
	
}
