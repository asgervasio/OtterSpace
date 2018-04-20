package edu.ycp.cs320.otterspace.controller;

import edu.ycp.cs320.otterspace.controller.game.Item;
import edu.ycp.cs320.otterspace.model.EditorItemModel;

public class EditorItemController {
	private EditorItemModel model;
	
	public void setModel(EditorItemModel model) {
		this.model = model;
	}
	
	public Item createItem(){
		Item item = new Item();
		item.setTitle(model.getTitle());
		item.setDescription(model.getDescription());
		item.setRoomLocat(model.getRoomLocat());
		item.setStatAffected(model.getStatAffected());
		item.setStatChangeVal(model.getStatChangeVal());
		
		return item;
	}
	
}
