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
 	
	@Before
	public void setUp(){
		database = new DerbyDatabase();
		
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
		map.put("North", 2);
		map.put("South", 3);
		
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

		database.createTables();
		database.loadInitialData();
	}
	
	
	@Test
	public void testInsertPlayer(){
		database.insertPlayer(player);
		playerBlank = database.findPlayerUsingLocation(room1);
		assertEquals(player.getName(), playerBlank.getName());
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
		itemBlankList = database.findItemsUsingLocation(4);
		assertEquals(item.getTitle(), itemBlankList.get(0).getTitle());
		assertEquals(item.getStatAffected(), itemBlankList.get(0).getStatAffected());		
	}
	
	
}
