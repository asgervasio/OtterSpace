package edu.ycp.cs320.otterspace.controller;

import java.awt.Color;

import edu.ycp.cs320.otterspace.controller.game.Item;
import edu.ycp.cs320.otterspace.controller.game.Room;
import edu.ycp.cs320.otterspace.model.EditorRoomModel;;

public class EditorRoomController {
	private EditorRoomModel model;
	
	public void setModel(EditorRoomModel model) {
		this.model = model;
	}
	
	public Room createRoom(){
		return new Room(model.getTitle(), model.getDescription(), model.getRequirement(), model.getConnections(),
				model.getLocation(), model.getItemList());
	}
	
}// end of class
