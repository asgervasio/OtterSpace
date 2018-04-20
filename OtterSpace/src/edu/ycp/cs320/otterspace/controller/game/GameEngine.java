package edu.ycp.cs320.otterspace.controller.game;

import java.io.IOException;
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
				result = move(commandSplit);
			case "look":
			case "inspect":
				result = inspect(commandSplit);
			case "take":
			case "pick":
				result = take(commandSplit);
			case "inventory":
			case "i":
				result = displayInventory();
			default:
				result = invalid(); 
		}	
		return result;
			
			
	}
	
	public String displayInventory()
	{
		String result = "Your inventory contains: \n";
		List<Item> inventory = player.getInventory();
		for(int i = 0; i < inventory.size(); i++)
		{
			result = result + inventory.get(i) + "\n";
		}
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
				result = inspectedItem.getTitle() + " picked up";
			}
		}
		
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
			result = inspectedItem.getDescription();
		}
		
		return result;
	}
	
	public String move(String[] direction)
	{
		
		Room currentRoom = player.getCurrentRoom();
		String destination = currentRoom.getConnection(direction[1]).getTitle();
		String result = "";
		Room testRoom;
		
		if (destination == null)
		{
			result = "Could not find that room";
		}
		else
		{
			player.setCurrentRoom(db.findRoomUsingTitle(destination));
			currentRoom = player.getCurrentRoom();
			String room = currentRoom.getTitle();
			String description = currentRoom.getDescription();
			String items = currentRoom.getItems();
			result = room + "\n" + description + "\n" + items;
						
			
	/*	if(direction.length > 2)
		{
			result = "Unknown Movement";
		}
		else
		{
			testRoom = db.findRoomUsingTitle(direction[1]);
			result = testRoom.getTitle() + "<br /><br />" + testRoom.getDescription() + "<br /><br /> You see the following items on the ground: <br />" + testRoom.getItems();
		}
		*/
		}
		return result;
	}
	public String invalid()
	{
		String result = "";
		result = "Invalid Command";
		return result;

	}
	
	public String initializePlayer()
	{
		String result = "";
		player.setCurrentRoom(db.findRoomUsingRoomId(1));
		List<Item> inventory = db.findItemsUsingLocation(1);		
		for(int i = 0; i < inventory.size(); i++)
		{
			player.addItem(inventory.get(i));
		}
		Room currentRoom = player.getCurrentRoom();
		result = ("You are standing in the " + currentRoom.getTitle() + "<br /><br />" + currentRoom.getDescription() 
			+ "<br /><br /> You see the following items on the ground: <br />" + currentRoom.getItems());
		
		return result;
	}

}
