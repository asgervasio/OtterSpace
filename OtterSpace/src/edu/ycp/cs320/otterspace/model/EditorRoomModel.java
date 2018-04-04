package edu.ycp.cs320.otterspace.model;

import java.util.HashMap;

import edu.ycp.cs320.otterspace.controller.game.Item;
import edu.ycp.cs320.otterspace.controller.game.Room;

// Model class for GameModel
// Only the controller should be allowed to call the set methods
public class EditorRoomModel {

	private String description, title, requirement, connectionTemp, location, itemList;
	private String description, title;
	private HashMap<String,Room> connections;
	
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
	
	public void setRequirement(String color){
		this.requirement = color;
	}
	
	public String getRequirement(){
		return requirement;
	}

	public void setConnectionTemp(String connections){
		this.connectionTemp = connections;
	}

	public String getConnectionTemp(){
    return connectionTemp; 
  }

	public void setConnection(String key, Room room){
		connections.put(key, room);
	}

	public HashMap<String, Room> getConnections(){
		return connections;
	}
	
	public void setLocation(String location){
		this.location = location;
	}
	
	public String getLocation(){
		return location;
	}
	
	public void setItemList(String itemList){
		this.itemList = itemList;
	}
	
	public String getItemList(){
		return itemList;
	}
	
}// end of class
