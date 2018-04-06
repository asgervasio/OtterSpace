package edu.ycp.cs320.roomsdb.persist;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import edu.ycp.cs320.otterspace.controller.game.Item;
import edu.ycp.cs320.otterspace.controller.game.Room;

public class InitialData {
	public static List<Room> getRooms() throws IOException {
		List<Room> roomList = new ArrayList<Room>();
		ReadCSV readRooms = new ReadCSV("rooms.csv");
		try {
			// auto-generated primary key for authors table
			Integer roomId = 1;
			while (true) {
				List<String> tuple = readRooms.next();
				if (tuple == null) {
					break;
				}
				Iterator<String> i = tuple.iterator();
				Room room = new Room();
				room.setRoomId(roomId++);				
				room.setTitle(i.next());
				room.setDescription(i.next());
				room.setItems(i.next());
				room.setConnectionTemp(i.next());
				room.setRequirement(i.next());
				room.setLocation(i.next());
				roomList.add(room);
			}
			return roomList;
		} finally {
			readRooms.close();
		}
	}

	public static List<Item> getItems() throws IOException {
		List<Item> itemList = new ArrayList<Item>();
		ReadCSV readRooms = new ReadCSV("items.csv");
		try {
			// auto-generated primary key for authors table
			Integer itemId = 1;
			while (true) {
				List<String> tuple = readRooms.next();
				if (tuple == null) {
					break;
				}
				Iterator<String> i = tuple.iterator();
				Item item = new Item();
				item.setItemId(itemId++);				
				item.setTitle(i.next());
				item.setDescription(i.next());
				item.setRoomLocat(i.next());
				item.setStatAffected(i.next());
				item.setStatChangeVal(i.next());
				itemList.add(item);
			}
			return itemList;
		} finally {
			readRooms.close();
		}
	}

}
