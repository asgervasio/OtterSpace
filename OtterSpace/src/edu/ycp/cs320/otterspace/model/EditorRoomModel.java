package edu.ycp.cs320.otterspace.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

// Model class for GameModel
// Only the controller should be allowed to call the set methods
public class EditorRoomModel {

	private String description, title;
	private boolean requirement;
	private HashMap<String, Integer> connections;
	
	// Constructor for EditorRoomModel 
	public EditorRoomModel() {
	}
	
	// All of the getters and setters for the creation of a room
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
	
	public void setRequirement(boolean requirement){
		this.requirement = requirement;
	}
	
	public boolean getRequirement(){
		return requirement;
	}
	
	public void setConnections(String direction, Integer id){
		connections.put(direction, id);
	}
	
	public Integer getConnectionID(String direction){
		Integer id = connections.get(direction);
		return id;
	}
	
	public Set getConnections(){
		return connections.entrySet();
	}
}// end of class
