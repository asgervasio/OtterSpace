package edu.ycp.cs320.otterspace.model;

import java.awt.Color;
import java.util.HashMap;

import edu.ycp.cs320.otterspace.controller.game.Item;
import edu.ycp.cs320.otterspace.controller.game.Room;

// Model class for GameModel
// Only the controller should be allowed to call the set methods
public class EditorRoomModel {
	private String description, title;
	private Color requirement;
	private HashMap<String,Room> connections;
	private int[] location = new int[3];
	private Item[] itemList;
	
	// Constructor for GameModel class
	public EditorRoomModel() {
	}
	
	// All of the getters and setters for the creation of a 
	public void setTitle(String title){
		this.title = title;
	}

	public String getTitle(){
		return title;
	}

	public void setDescription(String description){
		this.description = description;
	}

	public String getDescription(){
		return description;
	}
	
	public void setRequirement(Color color){
		this.requirement = color;
	}
	
	public Color getRequirement(){
		return requirement;
	}

	public void setConnection(String key, Room room){
		connections.put(key, room);
	}

	public HashMap<String, Room> getConnections(){
		return connections;
	}
	
	public void setLocation(int x, int y, int z){
		location[0] = x;
		location[1] = y;
		location[2] = z;
	}
	
	public int[] getLocation(){
		return location;
	}
	
	public void setItemList(Item[] itemList){
		for (int i = 0; i < itemList.length; i++){
			this.itemList[i] = itemList[i];
		}
	}
	
	public Item[] getItemList(){
		return itemList;
	}
	
}// end of class
