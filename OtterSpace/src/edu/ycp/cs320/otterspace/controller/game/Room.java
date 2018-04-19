
package edu.ycp.cs320.otterspace.controller.game;

import java.awt.Color;
import java.util.HashMap;

//This is only used as a stand-in for the real room class
//Do not try to create or learn about items from this class,
//it will teach you nothing

public class Room {
	int roomId;
	String title, description, connectionTemp, location, itemList;
	boolean requirement;
	HashMap<String,Room> connections = new HashMap<String,Room>();
	
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
	
	public void setRequirement(boolean requirement){
		this.requirement = requirement;		
	}
	
	public Boolean getRequirement(){
		return requirement;
	}
	
	public void setConnectionTemp(String connections){
		this.connectionTemp = connections;		
	}
	
	public String getConnectionTemp()
	{
		return connectionTemp;
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
	
	public String getItems()
	{
		return itemList;
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
