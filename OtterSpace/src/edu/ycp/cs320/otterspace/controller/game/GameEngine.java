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
/*
		if(player.getHealth() <= 0)
		{
			switch(commandSplit[0])
			{
				case "restart":
				case "start":
				case "r":
					result = initializePlayer(command, username);
					break;
				default:
					result = "You are dead! type 'r' to restart";
					break;
			}
				
		}
		*/

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
				case "score":
					db.insertConsole("> " + command + "<br />", username);
					result = result + player.getScore() + "<br />";
					db.insertConsole(result, username);
					break;
				case "health":
					db.insertConsole("> " + command + "<br />", username);
					result = result + player.getHealth() + "<br />";
					db.insertConsole(result, username);
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
		enemy.setCurrentRoom(db.findRoomUsingRoomId(enemy.getRoomId()));
		String result = "";

		if(enemy.getCurrentRoom().getRoomId() == player.getCurrentRoom().getRoomId())
		{
			System.out.println("ENEMY IN THE SAME ROOM");
			if(enemy.getHealth() > 0)
			{
				player.attackEnemy(enemy);
	
				//if enemy dies
				if(enemy.getHealth() <= 0)
				{
					result = enemy.getName() + " is dead. You find " + enemy.getGold() + " gold on his body.";
					player.setGold(player.getGold() + enemy.getGold());
					player.setScore(player.getScore() + enemy.getScore());
					enemy.setRoomId(0);
				}
				
				//if enemy is not dead
				else
				{
					result = enemy.getName() + " was hit! " + enemy.getHealth() + "<br />";
					if(enemy.getHostility())
					{
						enemy.attackEnemy(player);
						result = result + enemy.getName() + " hit you! <br />";
					}
				}
				
				//If enemy is not hostile and is attacked, he becomes hostile
				if(!enemy.getHostility())
				{
					enemy.setHostility(true);
					result = result + enemy.getName() + " looks angry. <br />";
				}
				
				//updates the user and the enemy
				updatePlayer(player, username);
				updatePlayer(enemy, username);
			}
			else
			{
				result = enemy.getName() + " is dead";
			}
		}
		else
		{
			result = command[1] + " is not here! <br />";
		}
		db.insertConsole(result, username);
		return result;
	}
	
	/********************DISPLAY INVENTORY*******************************************/
	public String displayInventory(String username)
	{
		String result = "Your inventory contains: <br />";
		List<Item> inventory = new ArrayList<Item>();
		inventory = player.getInventory();
		for(int i = 0; i < inventory.size(); i++)
		{
			result = result + inventory.get(i).getTitle() + "<br />";
		}
		result = result + "Gold: " + player.getGold() + "<br />";
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
			if(inspectedItem.getRoomLocat() == player.getCurrentRoom().getRoomId() )
			{
				if(inspectedItem == null)
				{
					result = "Could not find that item";
				}
				else
				{
					player.addItem(inspectedItem);
					result = inspectedItem.getTitle() + " picked up <br />";
					inspectedItem.setRoomLocat(0);
					db.UpdateItem(inspectedItem, username);
				}
			}
			else
			{
				result = "That item is not here <br />";
			}
		}
		db.insertConsole(result, username);
		updatePlayer(player, username);
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
		else if(item[1].toLowerCase().equals("around") || item[1].toLowerCase().equals("room"))
		{
			result = outputRoomData(username);
		}
		else
		{
			if(inspectedItem.getRoomLocat() == player.getCurrentRoom().getRoomId() || inspectedItem.getRoomLocat() == 999)
			{
				result = inspectedItem.getDescription() + "<br />";
			}
			else
			{
				Player inspectedPlayer = db.findPlayerUsingName(item[1], username);
				if(inspectedPlayer.getRoomId() == player.getRoomId())
				{
					result = inspectedPlayer.getDescription() + "<br />";
				}
				else
				{
					result = "That isn't here <br />";
				}
			}
		}
		db.insertConsole(result, username);
		return result;
	}
	
	public String move(String[] direction, String username)
	{
		
		Room currentRoom = player.getCurrentRoom();
		Room destinationRoom;
		//int destinationRoomId = (int)db.findRoomIdFromConnection(direction[1]);
		//System.out.println("destination " + destinationRoomId);
		String result = "";
			if(currentRoom.getTrueConnections().containsKey(direction[1]))
			{
				destinationRoom = db.findRoomUsingRoomId(currentRoom.getTrueConnections().get(direction[1]));
				//destinationRoom = db.findRoomUsingRoomId(currentRoom.getConnectionID(direction[1]));
				if(destinationRoom.getRequirement().equals("none"))
				{
					player.setCurrentRoom(destinationRoom);
					result = outputRoomData(username);
				}
				
				else //Room is locked
				{
					String requirement = destinationRoom.getRequirement();
					List<Item> inventory = new ArrayList<Item>();
					boolean hasItem = false;
					inventory = player.getInventory();
					
					//Iterate through player inventory
					for(int i = 0; i < inventory.size(); i++)
					{
						//if the requirement is in the player's inventory
						if(inventory.get(i).getTitle().equals(requirement))
						{
							hasItem = true;
							player.setCurrentRoom(destinationRoom);
							result = destinationRoom.getTitle() + " opened with " + inventory.get(i).getTitle() + "<br /> <br />";
							result = result + outputRoomData(username);
						}
	
					}
					if(!hasItem)
					{
						result = requirement + " is required";
					}
				}
			}
			else
			{
				result = "That direction does not lead anywhere";
			}
						
		db.insertConsole(result, username);
		System.out.println("UPDATE " + player.getName());
		updatePlayer(player, username);
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
		db.dropTables(username);
		db.createTables(username);
		db.loadInitialData(username);
		db.insertConsole("> " + command + "<br />", username);
		player = db.findPlayerUsingName("otter", username);
		player.setCurrentRoom(db.findRoomUsingRoomId(1));

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
		player.setCurrentRoom(db.findRoomUsingRoomId(player.getRoomId()));

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
			result = result + ("<br /><br /> You see the following items on the ground: <br />" + items);
		}
		if(playerFound)
		{
			result = result + "<br /><br /> These actors are in the room: <br />" + players;
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
