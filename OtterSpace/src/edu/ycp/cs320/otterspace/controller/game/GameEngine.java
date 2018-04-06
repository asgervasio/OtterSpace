package edu.ycp.cs320.otterspace.controller.game;

import java.io.IOException;
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
				result = move(commandSplit);
			/*case "south":
			case "s":
				result = move("south");
			case "east":
			case "e":
				result = move("east");
			case "west":
			case "w":
				result = move("west");
			case "up":
			case "u":
				result = move("up");
			case "down":
			case "d":
				result = move("down");
			case "move":
			case "walk":
				//result = move(commandSplit[1]);
				 * */
				 
		}
		
		return result;
			
			
	}
	
	public String move(String[] direction)
	{
		
		//Room currentRoom = player.getCurrentRoom();
		//String destination = currentRoom.getConnection(direction).getTitle();
		String result = "";
		Room testRoom;
		//if (direction == null)
		//{
			
		//}
		//else
		//{
			//player.setCurrentRoom(db.findRoomUsingTitle(destination));
			//currentRoom = player.getCurrentRoom();
			//String room = currentRoom.getTitle();
			//String description = currentRoom.getDescription();
			//String items = currentRoom.getItems();
			//result = room + "\n" + description + "\n" + items;
			
			//player.setCurrentRoom(db.findRoomUsingTitle(direction));
			//result = player.getCurrentRoom().getTitle();
			//result = direction[1];
			testRoom = db.findRoomUsingTitle(direction[1]);
			result = testRoom.getTitle() + "<br /><br />" + testRoom.getDescription() + "<br /><br /> You see the following items on the ground: <br />" + testRoom.getItems();

		//}
		return result;
	}

}
