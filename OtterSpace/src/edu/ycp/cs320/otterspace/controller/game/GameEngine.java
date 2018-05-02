package edu.ycp.cs320.otterspace.controller.game;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.servlet.http.HttpServletResponse;

import edu.ycp.cs320.roomsdb.InitDatabase;
import edu.ycp.cs320.roomsdb.persist.DatabaseProvider;
import edu.ycp.cs320.roomsdb.persist.IDatabase;
import edu.ycp.cs320.roomsdb.persist.FakeDatabase;
import edu.ycp.cs320.otterspace.model.GameModel;

public class GameEngine 
{
	GameModel model = new GameModel();
	Player player = new Player();
	//HttpServletResponse resp;
	IDatabase db = DatabaseProvider.getInstance();	
	
	public String parse(String cmd)
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
				db.insertConsole("> " + command + "<br />");
				result = move(commandSplit);
				break;
			case "look":
			case "inspect":
				db.insertConsole("> " + command + "<br />");
				result = inspect(commandSplit);
				break;
			case "take":
			case "pick":
				db.insertConsole("> " + command + "<br />");
				result = take(commandSplit);
				break;
			case "inventory":
			case "i":
				db.insertConsole("> " + command + "<br />");
				result = displayInventory();
				break;
			case "start":
			case "s":
				db.insertConsole("> " + command + "<br />");
				result = initializePlayer();
				break;
			case "load":
			case "l":
				//Do not load "load" load command into console
				result = loadPlayer();
				break;
			default:
				db.insertConsole("> " + command + "<br />");
				result = invalid(); 
				break;
		}	
		return result;
			
			
	}
	
	public String displayInventory()
	{
		String result = "Your inventory contains: <br />";
		List<Item> inventory = new ArrayList<Item>();
		inventory = player.getInventory();
		for(int i = 0; i < inventory.size(); i++)
		{
			result = result + inventory.get(i).getTitle();
		}
		db.insertConsole(result);
		return result;
	}
	
	public String take(String[] item)
	{
		String result = "";
		Item inspectedItem;
		
		if(item.length > 2)
		{
			result = "Too many arguments";
		}
		else
		{
			inspectedItem = db.findItemUsingTitle(item[1]);
			if(inspectedItem == null)
			{
				result = "Could not find that item";
			}
			else
			{
				player.addItem(inspectedItem);
				inspectedItem.setLocation("player");
				db.insertChange("item", inspectedItem.getItemId(), (Integer) null, "actor", 1);
				result = inspectedItem.getTitle() + " picked up <br />";
			}
		}
		db.insertConsole(result);
		return result;
	}
	
	public String inspect(String[] item)
	{
		String result = "";
		Item inspectedItem = db.findItemUsingTitle(item[1]);
		
		if(item.length > 2)
		{
			result = "Too many arguments";
		}
		else
		{
			result = inspectedItem.getDescription() + "<br />";
		}
		db.insertConsole(result);
		return result;
	}

	
	public String move(String[] direction)
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
			db.insertChange("actor", 1, (Integer) null, "room", player.getCurrentRoom().getRoomId());
			result = outputRoomData();
						
			
		}
		db.insertConsole(result);
		return result;
	}
	
	public String invalid()
	{
		String result = "";
		result = "Invalid Command <br />";
		db.insertConsole(result);
		return result;
	}
	
	public String initializePlayer()
	{
		String result = "";
		player.setCurrentRoom(db.findRoomUsingRoomId(1));
		//List<Item> inventory = db.findItemsUsingLocation(1);
		
		//for(int i = 0; i < inventory.size(); i++)
		//{
		//	player.addItem(inventory.get(i));
		//}
		

		result = outputRoomData();
		db.insertConsole(result);
		return result;
	}
	
	public String loadPlayer()
	{
		String result = "";
		List<String> consoleLog = new ArrayList<String>();
		consoleLog = db.loadConsole();
		for(int i = 1; i < consoleLog.size(); i++)
		{
			result += consoleLog.get(i);

		}
		return result;
		
	}
	
	public String outputRoomData()
	{
		String result = "";
		Room currentRoom = player.getCurrentRoom();
		String items = "";
		List<Item> itemList = db.findItemsUsingLocation(currentRoom.getRoomId());		
		for(int i = 0; i < itemList.size(); i++)
		{
			items = items + itemList.get(i).getTitle() + "<br /> ";
		}
		result = ("You are in " + currentRoom.getTitle() + "<br /><br />" + currentRoom.getDescription() 
			+ "<br /><br /> You see the following items on the ground: <br />" + items + "<br />");
		
		return result;
	}

}
