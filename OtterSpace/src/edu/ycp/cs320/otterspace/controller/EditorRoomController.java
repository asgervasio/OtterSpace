package edu.ycp.cs320.otterspace.controller;

import java.awt.Color;

import edu.ycp.cs320.otterspace.controller.game.Item;
import edu.ycp.cs320.otterspace.model.EditorRoomModel;;

public class EditorRoomController {
	private EditorRoomModel model;
	
	public void setModel(EditorRoomModel model) {
		this.model = model;
	}

	public void setTitle(String title){
		model.setDescription(title);
	}

	public void setDescription(String description){
		model.setDescription(description);
	}

	public void setRequirement(Color color){
		model.setRequirement(color);
	}

	public void setConnections(boolean[] connections){
		model.setConnections(connections);
	}

	public void setLocation(int x, int y, int z){
		model.setLocation(x, y, z);;
	}
	
	public void setItemList(Item[] itemList){
		model.setItemList(itemList);;
	}
	
}// end of class
