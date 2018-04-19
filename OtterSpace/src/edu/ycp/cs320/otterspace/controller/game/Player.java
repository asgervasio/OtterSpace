package edu.ycp.cs320.otterspace.controller.game;

import java.util.List;

public class Player 
{
	Room currentRoom;
	List<Item> inventory;
	
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
