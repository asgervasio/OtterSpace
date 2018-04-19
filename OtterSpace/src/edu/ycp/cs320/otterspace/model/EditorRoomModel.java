package edu.ycp.cs320.otterspace.model;

import edu.ycp.cs320.otterspace.controller.game.Item;
import edu.ycp.cs320.otterspace.controller.game.Room;

// Model class for GameModel
// Only the controller should be allowed to call the set methods
public class EditorRoomModel {

	private String description, title;
	private boolean requirement;
	
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
	
}// end of class
