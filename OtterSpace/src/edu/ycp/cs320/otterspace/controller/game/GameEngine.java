package edu.ycp.cs320.otterspace.controller.game;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

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
	boolean dead = false;
	boolean win = false;
	boolean getAttacked = false;
	
	public String parse(String cmd, String username)
	{
		String command = cmd.toLowerCase();
		String[] commandSplit = command.split(" ");
		String result = "";

		if(dead)
		{
			switch(commandSplit[0])
			{
				case "restart":
				case "start":
				case "r":
					result = initializePlayer(command, username);
					break;
				default:
					result = "You are dead! <br /> Your score was: " + player.getScore() +  "<br />Type 'r' to restart <br />";
					break;
			}
				
		}
		else if(win)
		{
			switch(commandSplit[0])
			{
				case "restart":
				case "start":
				case "r":
					result = initializePlayer(command, username);
					break;
				default:
					result = "CONGRATULATIONS, YOU HAVE ESCAPED!<br /> YOUR SCORE WAS: " + player.getScore() +  "<br />TYPE 'r' TO RESTART <br />";
					break;
			}
		}
		else
		{

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
					getAttacked = true;
					break;
				case "take":
				case "pick":
					db.insertConsole("> " + command + "<br />", username);
					result = take(commandSplit, username);
					getAttacked = true;
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
					getAttacked = true;
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
				case "attackstat":
					db.insertConsole("> " + command + "<br />", username);
					result = result + player.getAttack() + "<br />";
					db.insertConsole(result, username);
					break;
				case "defensestat":
					db.insertConsole("> " + command + "<br />", username);
					result = result + player.getDefense() + "<br />";
					db.insertConsole(result, username);
					break;
				case "drop":
					db.insertConsole("> " + command + "<br />", username);
					result = drop(commandSplit, username);
					getAttacked = true;
					break;
				case "escape":
					db.insertConsole("> " + command + "<br />", username);
					result = drop(commandSplit, username);
					break;
				default:
					db.insertConsole("> " + command + "<br />", username);
					result = invalid(username); 
					break;
			}
			if(getAttacked)
			{
				result = result + "<br /> " + checkAttack(username);
				getAttacked = false;
			}
			checkDead(username);
		}
		return result;

			
			
	}
	public String escape(String username)
	{
		String result = "";
		Room currentRoom = player.getCurrentRoom();
		if(currentRoom.getTitle().equals("Escape Pod"))
		{
			win = true;
			result = "The escape pod disengages from the ship and the boosters fire as you are hurled away through space. <br /> CONGRATULATIONS! YOU HAVE ESCAPED TYPE 'R' to restart";
		}
		
		return result;
	}
	public String checkAttack(String username)
	{
		boolean enemyFound = false;
		List<Player> playerList = db.findPlayersUsingLocation(player.getCurrentRoom(), username);
		List<Player> enemyList = new ArrayList<Player>();
		String result = "";
		for(int i = 0; i < playerList.size(); i++)
		{
			if (playerList.get(i).getName().equals("otter"))
			{
				
			}
			else if(playerList.get(i).getHostility())
			{
				enemyFound = true;
				enemyList.add(playerList.get(i));
			}

		}
		if(enemyFound)
		{
			for(int j = 0; j < enemyList.size(); j++)
			{
				Player enemy = db.findPlayerUsingName(enemyList.get(j).getName(), username);
				enemy.setCurrentRoom(db.findRoomUsingRoomId(enemy.getRoomId()));
				enemy.attackEnemy(player);
				System.out.println("");
				result = result + enemy.getName() + " hit you! <br />";
				enemyList.remove(j);
			}
		}
		enemyFound = false;
		updatePlayer(player, username);
		return result;
	}
	
	public String attack(String[] command, String username)
	{
		Player enemy = db.findPlayerUsingName(command[1], username);
		enemy.setCurrentRoom(db.findRoomUsingRoomId(enemy.getRoomId()));
		String result = "";

		if(enemy.getCurrentRoom().getRoomId() == player.getCurrentRoom().getRoomId())
		{
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
					/*
					if(enemy.getHostility())
					{
						enemy.attackEnemy(player);
						result = result + enemy.getName() + " hit you! <br />";
					}
					*/
					if(!enemy.getHostility())
					{
						enemy.setHostility(true);
						result = result + enemy.getName() + " looks angry. <br />";
					}
				}
				
				//If enemy is not hostile and is attacked, he becomes hostile

				
				//updates the user and the enemy
				updatePlayer(player, username);
				updatePlayer(enemy, username);
			}
			else
			{
				result = enemy.getName() + " is dead.";
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
					if(inspectedItem.getStatAffected().toLowerCase().equals("attack"))
					{
						System.out.println("ATTACK INCREASED");
						player.setAttack(player.getAttack() + inspectedItem.getStatChangeVal());
					}
					else if(inspectedItem.getStatAffected().toLowerCase().equals("defense"))
					{
						player.setDefense(player.getDefense() + inspectedItem.getStatChangeVal());
					}
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
	
	public String drop(String[] item, String username)
	{
		String result = "";
		Item dropItem;
		
		if(item.length > 2)
		{
			result = "Too many arguments";
		}
		else
		{
			dropItem = db.findItemUsingTitle(item[1], username);
			if(dropItem.getRoomLocat() == 0)
			{
				if(dropItem == null)
				{
					result = "Could not find that item";
				}
				else
				{
					player.removeItem(dropItem);
					result = dropItem.getTitle() + " dropped <br />";
					if(dropItem.getStatAffected().toLowerCase().equals("attack"))
					{
						player.setAttack(player.getAttack() - dropItem.getStatChangeVal());
					}
					else if(dropItem.getStatAffected().toLowerCase().equals("defense"))
					{
						player.setDefense(player.getDefense() - dropItem.getStatChangeVal());
					}
					dropItem.setRoomLocat(player.getCurrentRoom().getRoomId());
					db.UpdateItem(dropItem, username);
				}
			}
			else
			{
				result = "You do not have that item <br />";
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
			if(inspectedItem.getRoomLocat() == player.getCurrentRoom().getRoomId() || inspectedItem.getRoomLocat() == 0)
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
		dead = false;
		win = false;
		String result = "";
		db.dropTables(username);
		db.createTables(username);
		db.loadInitialData(username);
		Player loser = db.findPlayerUsingName("sean", username);
		Player loser2 = db.findPlayerUsingName("bill", username);
		System.out.println("INITAL DATA HOSTILITY:  "+ loser.getName() + "   " + loser.getHostility());
		System.out.println("INITAL DATA HOSTILITY:  "+ loser2.getName() + "   " + loser2.getHostility());
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
		HashMap<String, Integer> connectionList = currentRoom.getTrueConnections();
		Player loser = db.findPlayerUsingName("sean", username);
		System.out.println("ONONWONEWONEOWNEOWNE "+ loser.getName() + "   " + loser.getHostility());
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
				players = players + playerList.get(j).getName() + playerList.get(j).getHostility() + "<br />";
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
		itemFound = false;
		playerFound = false;
		
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
	public void checkDead(String username)
	{
		if(player.getHealth() <=0)
		{
			dead = true;
		}
	}

}
