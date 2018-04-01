package edu.ycp.cs320.otterspace.controller.game;

import java.awt.Color;

//This is only used as a stand-in for the real room class
//Do not try to create or learn about items from this class,
//it will teach you nothing

public class Room {
	int roomId;
	String title, description, requirement, connections, location, itemList;
	
	public Room(){
	}
	
	public void setRoomId(int roomId){
		this.roomId = roomId;		
	}
	
	public int getRoomId(){
		return roomId;
	}

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
	
	public void setRequirement(String requirement){
		this.requirement = requirement;		
	}
	
	public String getRequirement(){
		return requirement;
	}
	
	public void setConnections(String connections){
		this.connections = connections;		
	}
	
	public String getConnections(){
		return connections;
	}
		
	public void setLocation(String location){
		this.location = location;		
	}
	
	public String getLocation(){
		return location;
	}
	
	public void setItems(String itemList){
		this.itemList = itemList;		
	}
	
	public String getItems(){
		return itemList;
	}

}
