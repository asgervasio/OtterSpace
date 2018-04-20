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
				result = move(commandSplit);
				break;
			case "look":
			case "inspect":
				result = inspect(commandSplit);
				break;
			case "take":
			case "pick":
				result = take(commandSplit);
				break;
			case "inventory":
			case "i":
				result = displayInventory();
				break;
			case "start":
			case "s":
				result = initializePlayer();
				break;
			default:
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
			result = result + inventory.get(i).getTitle() + "<br />";
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
			result = outputRoomData();
						
			
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
		//List<Item> inventory = db.findItemsUsingLocation(1);
		
		//for(int i = 0; i < inventory.size(); i++)
		//{
		//	player.addItem(inventory.get(i));
		//}
		

		result = outputRoomData();
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
			+ "<br /><br /> You see the following items on the ground: <br />" + items);
		
		return result;
	}

}
