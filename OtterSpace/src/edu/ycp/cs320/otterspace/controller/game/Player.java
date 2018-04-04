package edu.ycp.cs320.otterspace.controller.game;

public class Player 
{
	Room currentRoom;
	
	public void setCurrentRoom(Room room)
	{
		currentRoom = room;
	}
	
	public Room getCurrentRoom()
	{
		return currentRoom;
	}

}
