package edu.ycp.cs320.otterspace.controller.game;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

//This is only used as a stand-in for the real room class
//Do not try to create or learn about items from this class,
//it will teach you nothing

public class Room {
	String title, description;
	Color requirement;
	//boolean[] connections;
	HashMap<String,Room> connections = new HashMap<String,Room>();
	int[] location;
	Item[] itemList;
	
	public Room(String title, String description, Color requirement,
			HashMap connections, int[] location, Item[] itemList){
		this.title = title;
		this.description = description;
		this.requirement = requirement;
		this.connections = connections;
		this.location = location;
		this.itemList = itemList;
	}
	
	public Room()
	{
		title = "title";
		description = "No Description";
		requirement = null;
		
	}
	
	public Room getConnection(String key)
	{
		return connections.get(key);
	}
	
	public void setConnection(String key, Room room)
	{
		connections.put(key, room);
	}
	

	
	
	

}
