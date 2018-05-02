package edu.ycp.cs320.otterspace.model;

import edu.ycp.cs320.otterspace.controller.game.Room;

public class GameModel 
{
	private String cmd, username;
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
	
	public void setUsername(String username)
	{
		this.username = username;
	}
	
	public String getUsername()
	{
		return username;
	}
	
	

}
