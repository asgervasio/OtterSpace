
package edu.ycp.cs320.otterspace.derbyDatabase;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import edu.ycp.cs320.otterspace.controller.EditorItemController;
import edu.ycp.cs320.otterspace.controller.EditorRoomController;
import edu.ycp.cs320.otterspace.controller.PlayerController;
import edu.ycp.cs320.otterspace.controller.game.Item;
import edu.ycp.cs320.otterspace.controller.game.Player;
import edu.ycp.cs320.otterspace.controller.game.Room;
import edu.ycp.cs320.otterspace.model.EditorItemModel;
import edu.ycp.cs320.otterspace.model.EditorRoomModel;
import edu.ycp.cs320.roomsdb.persist.DerbyDatabase;

public class DerbyDatabaseTest {
	private DerbyDatabase database;
	private EditorRoomModel roomModel;
	private EditorRoomController controller;
	private EditorItemModel itemModel;
	private EditorItemController itemController;
	private PlayerController playerController;
	private Room room1, roomBlank;
	private Item item, itemBlank;
	private List<Item> itemBlankList;
	private Player player, playerBlank;
	private String username;
 	
	@Before
	public void setUp(){
		database = new DerbyDatabase();
		
		username = "tester";
		roomModel = new EditorRoomModel();
		itemModel = new EditorItemModel();
		controller = new EditorRoomController();
		itemController = new EditorItemController();
		playerController = new PlayerController();
		controller.setModel(roomModel);
		itemController.setModel(itemModel);
		String roomTitle = "title";
		String roomDescription = "description";
		boolean requirement = true;
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		map.put("north", 2);
		map.put("south", 3);
		
		roomModel.setTitle(roomTitle);
		roomModel.setDescription(roomDescription);
		roomModel.setRequirement(requirement);
		roomModel.setConnections(map);
		
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
		
		String playerName = "AAron";
		String playerDescrip = "Just here in chemistry";
		int health = 100;
		int gold = 50;
		int score = 900000;
		int attack = 25;
		int defense = 75;
		boolean hostile = false;
		
		player = playerController.createPlayer(playerName, playerDescrip, health, gold,
				score, attack, defense, hostile, room1);

		database.dropTables(username);
		database.createTables(username);
		database.loadInitialData(username);
	}
	
	
/*	@Test
	public void testInsertPlayer(){
		database.insertPlayer(player, username);
		playerBlank = database.findPlayerUsingLocation(room1, username);
		assertEquals(player.getName(), playerBlank.getName());
	}
	*/
	
	@Test
	public void testInsertRoom(){
		roomBlank = database.findRoomUsingRoomId(1);
		HashMap<String, Integer> connections = roomBlank.getTrueConnections();
		System.out.println(connections.get("north"));
		assertEquals("testroom1", roomBlank.getTitle());
	}
	
	@Test
	public void testInsertItem(){
		database.insertItem(item, username);
		itemBlank = database.findItemUsingTitle("titlePerItem", username);
		assertEquals(item.getTitle(), itemBlank.getTitle());
		assertEquals(item.getStatAffected(), itemBlank.getStatAffected());
	}
	
	@Test
	public void testFindItemUsingRoomLocat(){
		database.insertItem(item, username);
		itemBlankList = database.findItemsUsingLocation(4, username);
		assertEquals(item.getTitle(), itemBlankList.get(0).getTitle());
		assertEquals(item.getStatAffected(), itemBlankList.get(0).getStatAffected());		
	}
	
	
}
