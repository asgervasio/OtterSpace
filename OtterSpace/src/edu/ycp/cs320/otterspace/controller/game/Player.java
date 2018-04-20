package edu.ycp.cs320.otterspace.controller.game;

import java.util.ArrayList;
import java.util.List;

import edu.ycp.cs320.roomsdb.persist.DatabaseProvider;
import edu.ycp.cs320.roomsdb.persist.IDatabase;

public class Player 
{
	Room currentRoom;
	List<Item> inventory = new ArrayList<Item>();
	IDatabase db = DatabaseProvider.getInstance();	
	
	public void setCurrentRoom(Room room)
	{
		currentRoom = room;
	}
	
	public Room getCurrentRoom()
	{
		return currentRoom;
	}
	
	public List<Item> getInventory()
	{
		return inventory;
	}
	
	public void addItem(Item item)
	{
		inventory.add(item);
		System.out.println(inventory.size());
	}
	
	public boolean inventoryContains(Item item)
	{
		if(inventory.contains(item))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	

}
