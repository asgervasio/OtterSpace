
package edu.ycp.cs320.otterspace.controller.game;

import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

//This is only used as a stand-in for the real room class
//Do not try to create or learn about items from this class,
//it will teach you nothing

public class Room {
	int roomId;
	String title, description;
	boolean requirement;
	private HashMap<String, Integer> connections;
	
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
	
}// end of room class
