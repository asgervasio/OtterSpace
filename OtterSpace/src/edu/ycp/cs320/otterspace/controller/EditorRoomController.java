package edu.ycp.cs320.otterspace.controller;


import edu.ycp.cs320.otterspace.controller.game.Item;
import edu.ycp.cs320.otterspace.controller.game.Room;
import edu.ycp.cs320.otterspace.model.EditorRoomModel;;

public class EditorRoomController {
	private EditorRoomModel model;
	
	public void setModel(EditorRoomModel model) {
		this.model = model;
	}
	
	public Room createRoom(){
		Room room = new Room();
		room.setTitle(model.getTitle());
		room.setDescription(model.getDescription());
		room.setRequirement(model.getRequirement());		
		return room;
	}
	
}// end of class
