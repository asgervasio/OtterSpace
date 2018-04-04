package edu.ycp.cs320.otterspace.model;

import edu.ycp.cs320.otterspace.controller.game.Room;

public class GameModel 
{
	private String cmd;
	private Room currentRoom;
	
	public void setCommand(String cmd)
	{
		this.cmd = cmd;
	}
	
	public String getCommand()
	{
		return cmd;
	}
	
	public void setCurrentRoom(Room room)
	{
		currentRoom = room;
	}
	public Room getCurrentRoom()
	{
		return currentRoom;
	}
	
	
	

}
