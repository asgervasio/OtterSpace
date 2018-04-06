package edu.ycp.cs320.roomsdb.persist;

import java.awt.Color;
import java.util.List;

import edu.ycp.cs320.otterspace.controller.game.Item;
import edu.ycp.cs320.otterspace.controller.game.Room;

public interface IDatabase {
	public void insertRoom(Room room);
	public Room findRoomUsingTitle(String title);
	public Room findRoomUsingLocation(String location);
	public Room findRoomUsingRoomId(int roomId);
	
	public void insertItem(Item item);
	public Item findItemUsingTitle(String title);
	public Item findItemUsingItemId(int itemId);
	public Item findItemUsingLocation(String location);
}
