
package edu.ycp.cs320.otterspace.controller.game;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

//This is only used as a stand-in for the real room class
//Do not try to create or learn about items from this class,
//it will teach you nothing

public class Room {
	int roomId;
	String title, description, requirement;
	//               Key     Value
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
	
	public void setRequirement(String requirement){
		this.requirement = requirement;		
	}
	
	public String getRequirement(){
		return requirement;
	}
		
	public void setConnections(){
		connections = new HashMap<String, Integer>();
	}
	
	public Integer getConnectionID(String direction){
		Integer id = connections.get(direction);
		return id;
	}
	
	public HashMap<String, Integer> getTrueConnections(){
		return connections;
	}
	
	public Set getConnections(){
		return connections.entrySet();
	}
	
	public String getConnectionDirection(Integer id){
		String returnVal = null;
		Set<String> set = connections.keySet();
		for(String name : set){
			if (connections.get(name) == id)
				returnVal = name;
		}
		return returnVal;
	}
	
	public void setConnections(String key, int val){
		connections.put(key, val);
	}
	
}// end of room class
