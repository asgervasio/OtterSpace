
package edu.ycp.cs320.otterspace.fakeDatabase;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import edu.ycp.cs320.otterspace.controller.EditorItemController;
import edu.ycp.cs320.otterspace.controller.EditorRoomController;
import edu.ycp.cs320.otterspace.controller.UserController;
import edu.ycp.cs320.otterspace.controller.game.Item;
import edu.ycp.cs320.otterspace.controller.game.Room;
import edu.ycp.cs320.otterspace.model.EditorItemModel;
import edu.ycp.cs320.otterspace.model.EditorRoomModel;
import edu.ycp.cs320.otterspace.model.User;
import edu.ycp.cs320.roomsdb.persist.FakeDatabase;

public class FakeDatabaseTest {
	private FakeDatabase database;
	private EditorRoomModel model;
	private EditorRoomController controller;
	private EditorItemModel itemModel;
	private EditorItemController itemController;
	private Room room1, room2, room3, room4, roomBlank;
	private Item item, itemBlank;
  private List<User> userList;
	private User u;
	private UserController usercontroller;
	private User User1, User2;
	
	@Before
	public void setUp(){
		database = new FakeDatabase();
		
		model = new EditorRoomModel();
		itemModel = new EditorItemModel();
		controller = new EditorRoomController();
		itemController = new EditorItemController();
		controller.setModel(model);
		itemController.setModel(itemModel);
		String roomTitle = "title";
		String roomDescription = "description";
		String requirement = "requirement";
		String connections = "connections";
		String itemName = "itemName";
		String itemNameNone = null;
		String location = "location";
		String actor = "actor";
		String actorNone = null;
		
		model.setTitle(roomTitle);
		model.setDescription(roomDescription);
		model.setRequirement(requirement);
		model.setConnectionTemp(connections);
		model.setItemList(itemName);
		model.setLocation(location);
		
		room1 = controller.createRoom();
		
		model.setItemList(itemNameNone);
		room2 = controller.createRoom();
		
		String itemTitle = "titlePerItem";
		String itemDescription = "descriptionPerItem";
		String statAffected = "affected";
		String statChangeVal = "ChangeVal";
		String roomLocat = "The Room where nothing breaks (imagination)";
		
		itemModel.setTitle(itemTitle);
		itemModel.setDescription(itemDescription);
		itemModel.setStatAffected(statAffected);
		itemModel.setStatChangeVal(statChangeVal);
		itemModel.setRoomLocat(roomLocat);
		
		item = itemController.createItem();
    
    
    User1.setUserAccountInformation("Ashling", "Ashley", "Ainsley", "Ash@gmail.com", "AAaassh");
		User2.setUserAccountInformation("BBBaited", "Bailey", "Butch", "Bait@gmail.com", "BBBBait");
		userList.add(User1);
		userList.add(User2);
	}
	
	// Tests to see if you can add a room that has an item but no Actor into the fake database
	@Test
	public void testInsertRoomWithItem(){
		database.insertRoom(room1);
		roomBlank = database.findRoomUsingTitle("title");
		assertEquals(room1.getTitle(), roomBlank.getTitle());
		assertEquals(room1.getItems(), roomBlank.getItems());
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
		roomBlank = database.findRoomUsingTitle("title");
		assertEquals(room2.getTitle(), roomBlank.getTitle());
		assertEquals(room2.getItems(), roomBlank.getItems());
	}
	
	@Test
	public void testInsertItem(){
		database.insertItem(item);
		itemBlank = database.findItemUsingTitle("titlePerItem");
		assertEquals(item.getTitle(), itemBlank.getTitle());
		assertEquals(item.getStatAffected(), itemBlank.getStatAffected());
	}
	
		
}// end of class

