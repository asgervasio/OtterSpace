package edu.ycp.cs320.otterspace.controller.game;

// This is only used as a stand-in for the real item class
// Do not try to create or learn about items from this class,
// it will teach you nothing

public class Item {
	String statAffected, title, description;
	int itemId, roomLocat, statChangeVal;
	
	public Item(){
	}

	public void setStatAffected(String statAffected) {
		this.statAffected = statAffected;
	}
	
	public String getStatAffected() {
		return statAffected;
	}

	public void setStatChangeVal(int statChangeVal) {
		this.statChangeVal = statChangeVal;
	}
	
	public int getStatChangeVal() {
		return statChangeVal;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	
	public int getItemId() {
		return itemId;
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
	
	public void setRoomLocat(int roomLocat) {
		this.roomLocat = roomLocat;
	}
	
	public int getRoomLocat() {
		return roomLocat;
	}
	
}
