package edu.ycp.cs320.otterspace.controller.game;

import java.awt.Color;

//This is only used as a stand-in for the real room class
//Do not try to create or learn about items from this class,
//it will teach you nothing

public class Room {
	String title, description;
	Color requirement;
	boolean[] connections;
	int[] location;
	Item[] itemList;
	
	public Room(String title, String description, Color requirement,
			boolean[] connections, int[] location, Item[] itemList){
		this.title = title;
		this.description = description;
		this.requirement = requirement;
		this.connections = connections;
		this.location = location;
		this.itemList = itemList;
	}
	
	
	

}
