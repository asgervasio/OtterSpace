package edu.ycp.cs320.roomsdb.persist;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.ycp.cs320.otterspace.controller.game.Item;
import edu.ycp.cs320.otterspace.controller.game.Room;

public class FakeDatabase implements IDatabase {
	private List<Item> itemList;
	private List<Room> roomList;
	
	public FakeDatabase() {
		roomList = new ArrayList<Room>();
		
		// Add initial data
		readInitialData();
		
		System.out.println(roomList.size() + " rooms");
	}

	// Getting the author and book tables to access in the fake database
	public void readInitialData() {
		try {
			roomList.addAll(InitialData.getRooms());
		} catch (IOException e) {
			throw new IllegalStateException("Couldn't read initial data", e);
		}
	}
	

	// Creates a new room that contains item(s) and inserts that room into the database
	@Override
	public void insertRoom(Room room) {
		int roomId = roomList.size();
		Room newRoom = new Room();
		newRoom.setRoomId(roomId);
		newRoom.setTitle(room.getTitle());
		newRoom.setDescription(room.getDescription());
		newRoom.setItems(room.getItems());
		newRoom.setConnectionTemp(room.getConnectionTemp());
		newRoom.setLocation(room.getLocation());
		newRoom.setRequirement(room.getRequirement());
		roomList.add(newRoom);
		System.out.println("Stored new room!!");		
	}	

	@Override
	public Room findRoomUsingTitle(String title) {
		Room result = new Room();
		for(Room room: roomList) {
			if (room.getTitle().equals(title)){
				result = room;
			}
		}
		return result;
	}

	@Override
	public List<Room> findRoomUsingLocation(String location) {
		List<Room> result = new ArrayList<Room>();	
		return null;
	}

	@Override
	public Room findRoomUsingRoomId(int roomId) {
		Room result = new Room();		
		for (Room room : roomList){
			if(room.getRoomId() == roomId){
				result = room;
			}
		}
		return result;
	}
	
}// end of class
