package edu.ycp.cs320.otterspace.model;

public class EditorItemModel {
	String statAffected, title, description, roomLocat, statChangeVal;
	int itemId;
	
	// Constructor for EditorItemModel
	public EditorItemModel(){
	}

	// All of the getters and setters
	public void setStatAffected(String statAffected) {
		this.statAffected = statAffected;
	}
	
	public String getStatAffected() {
		return statAffected;
	}

	public void setStatChangeVal(String statChangeVal) {
		this.statChangeVal = statChangeVal;
	}
	
	public String getStatChangeVal() {
		return statChangeVal;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setRoomLocat(String roomLocat) {
		this.roomLocat = roomLocat;
	}
	
	public String getRoomLocat() {
		return roomLocat;
	}
	
}
