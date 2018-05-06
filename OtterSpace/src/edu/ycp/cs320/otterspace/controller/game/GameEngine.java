package edu.ycp.cs320.otterspace.controller.game;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.servlet.http.HttpServletResponse;

import com.sun.media.jfxmedia.events.PlayerStateListener;

import edu.ycp.cs320.roomsdb.InitDatabase;
import edu.ycp.cs320.roomsdb.persist.DatabaseProvider;
import edu.ycp.cs320.roomsdb.persist.IDatabase;
import edu.ycp.cs320.roomsdb.persist.FakeDatabase;
import edu.ycp.cs320.otterspace.model.GameModel;

public class GameEngine
{
	Player player = new Player();
	//HttpServletResponse resp;
	IDatabase db = DatabaseProvider.getInstance();
	
	public String parse(String cmd, String username)
	{
		String command = cmd.toLowerCase();
		String[] commandSplit = command.split(" ");
		String result = "";

		
		switch(commandSplit[0])
		{
		
			case "move":
			case "m":
			case "walk":
			case "head":
				db.insertConsole("> " + command + "<br />", username);
				result = move(commandSplit, username);
				break;
			case "look":
			case "inspect":
				db.insertConsole("> " + command + "<br />", username);
				result = inspect(commandSplit, username);
				break;
			case "take":
			case "pick":
				db.insertConsole("> " + command + "<br />", username);
				result = take(commandSplit, username);
				break;
			case "inventory":
			case "i":
				db.insertConsole("> " + command + "<br />", username);
				result = displayInventory(username);
				break;
			case "start":
			case "s":
				result = initializePlayer(command, username);
				break;
			case "load":
			case "l":
				//Do not load "load" command into console
				result = loadPlayer(username);
				break;
			case "attack":
				db.insertConsole("> " + command + "<br />", username);
				result = attack(commandSplit, username);
				break;
			default:
				db.insertConsole("> " + command + "<br />", username);
				result = invalid(username); 
				break;
		}	
		return result;
			
			
	}
	
	public String attack(String[] command, String username)
	{
		Player enemy = db.findPlayerUsingName(command[1], username);
		String result = "";
		player.attackEnemy(enemy);
		if(enemy.getHealth() == 0)
		{
			result = enemy.getName() + " is dead. You find " + enemy.getGold() + " gold on his body.";
			player.setScore(player.getScore() + enemy.getGold());
		}
		else
		{
			result = enemy.getName() + " was hit!";
		}
		//updatePlayer(player, username);
		updatePlayer(enemy, username);
		db.insertConsole(result, username);
		return result;
	}
	public String displayInventory(String username)
	{
		String result = "Your inventory contains: <br />";
		List<Item> inventory = new ArrayList<Item>();
		inventory = player.getInventory();
		for(int i = 0; i < inventory.size(); i++)
		{
			result = result + inventory.get(i).getTitle();
		}
		db.insertConsole(result, username);
		return result;
	}
	
	public String take(String[] item, String username)
	{
		String result = "";
		Item inspectedItem;
		
		if(item.length > 2)
		{
			result = "Too many arguments";
		}
		else
		{
			inspectedItem = db.findItemUsingTitle(item[1], username);
			if(inspectedItem == null)
			{
				result = "Could not find that item";
			}
			else
			{
				player.addItem(inspectedItem);
				result = inspectedItem.getTitle() + " picked up <br />";
				inspectedItem.setRoomLocat(99);
				db.UpdateItem(inspectedItem, username);
			}
		}
		db.insertConsole(result, username);
		return result;
	}
	
	public String inspect(String[] item, String username)
	{
		String result = "";
		Item inspectedItem = db.findItemUsingTitle(item[1], username);
		
		if(item.length > 2)
		{
			result = "Too many arguments";
		}
		else if(item[1].equals("around"))
		{
			result = outputRoomData(username);
		}
		else
		{
			result = inspectedItem.getDescription() + "<br />";
		}
		db.insertConsole(result, username);
		return result;
	}
	
	public String move(String[] direction, String username)
	{
		
		Room currentRoom = player.getCurrentRoom();
		int destinationRoomId = (int)db.findRoomIdFromConnection(direction[1]);
		System.out.println("destination " + destinationRoomId);
		String result = "";
		Room testRoom;
		
		if (destinationRoomId == 0)
		{
			result = "Could not find that room";
		}
		else
		{
			player.setCurrentRoom(db.findRoomUsingRoomId(destinationRoomId));
			result = outputRoomData(username);
						
		}
		db.insertConsole(result, username);
		return result;
	}
	
	public String invalid(String username)
	{
		String result = "";
		result = "Invalid Command <br />";
		db.insertConsole(result, username);
		return result;
	}
	
	public String initializePlayer(String command, String username)
	{
		String result = "";
		player.setCurrentRoom(db.findRoomUsingRoomId(1));
		db.dropTables(username);
		db.createTables(username);
		db.loadInitialData(username);
		db.insertConsole("> " + command + "<br />", username);	

		result = outputRoomData(username);
		db.insertConsole(result, username);
		return result;
	}
	
	public String loadPlayer(String username)
	{
		//ADD PLAYER INFO LOAD
		String result = "";
		List<String> consoleLog = new ArrayList<String>();
		consoleLog = db.loadConsole(username);
		player = db.findPlayerUsingName("otter", username);
		for(int i = 1; i < consoleLog.size(); i++)
		{
			result += consoleLog.get(i) + "<br />";

		}
		return result;
		
	}
	
	public String outputRoomData(String username)
	{
		String result = "";
		Room currentRoom = player.getCurrentRoom();
		String items = "";
		String players = "";
		boolean itemFound = false;
		boolean playerFound = false;
		List<Item> itemList = db.findItemsUsingLocation(currentRoom.getRoomId(), username);	
		List<Player> playerList = db.findPlayersUsingLocation(currentRoom, username);
		for(int i = 0; i < itemList.size(); i++)
		{
			itemFound = true;
			items = items + itemList.get(i).getTitle() + "<br /> ";
		}
		for(int j = 0; j < playerList.size(); j++)
		{
			if (playerList.get(j).getName().equals("otter"))
			{
				
			}
			else
			{
				players = players + playerList.get(j).getName() + "<br />";
				playerFound = true;
			}
		}
		result = ("You are in " + currentRoom.getTitle() + "<br /><br />" + currentRoom.getDescription());
		if (itemFound)
		{
			result = result + ("<br /><br /> You see the following items on the ground: <br />" + items + "<br />");
		}
		if(playerFound)
		{
			result = result + "<br /><br /> These actors are in the room: <br />" + players + "<br />";
		}
		
		return result;
	}
	
	public void updatePlayer(Player player, String username)
	{
		db.UpdatePlayer(player, username);
	}
	
	public void updateItem(Item item, String username)
	{
		db.UpdateItem(item, username);
	}

}
