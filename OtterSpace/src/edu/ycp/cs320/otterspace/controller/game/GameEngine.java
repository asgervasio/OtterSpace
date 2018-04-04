package edu.ycp.cs320.otterspace.controller.game;

import edu.ycp.cs320.otterspace.model.GameModel;

public class GameEngine 
{
	GameModel model = new GameModel();
	Player player = new Player();
	
	public void parse(String cmd)
	{
		String command = cmd.toLowerCase();
		String[] commandSplit = command.split(" ");
		
		switch(commandSplit[0])
		{
			case "north":
			case "n":
				move("north");
			case "south":
			case "s":
				move("south");
			case "east":
			case "e":
				move("east");
			case "west":
			case "w":
				move("west");
			case "up":
			case "u":
				move("up");
			case "down":
			case "d":
				move("down");
		}
			
			
	}
	
	public void move(String direction)
	{
		Room currentRoom = player.getCurrentRoom();
		Room destination = currentRoom.getConnection(direction);
		if (destination.equals(null))
		{
			
		}
		else
		{
			player.setCurrentRoom(destination);
		}
	}

}
