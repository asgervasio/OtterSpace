

package edu.ycp.cs320.otterspace.fakeDatabase;

import static org.junit.Assert.*;

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
	private Room room1, roomBlank;
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
    
    
//		User1.setUserAccountInformation("Ashling", "Ashley", "Ainsley", "Ash@gmail.com", "AAaassh");
//		User2.setUserAccountInformation("BBBaited", "Bailey", "Butch", "Bait@gmail.com", "BBBBait");
//		userList.add(User1);
//		userList.add(User2);
	}
	
	// Tests to see if you can add a room that has neither an Actor nor an item into the fake database
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
		
}// end of class