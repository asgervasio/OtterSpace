package edu.ycp.cs320.roomsdb.persist;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.ycp.cs320.otterspace.controller.game.Item;
import edu.ycp.cs320.otterspace.controller.game.Room;
import edu.ycp.cs320.otterspace.model.User;

public class FakeDatabase implements IDatabase {
	private List<Item> itemList;
	private List<Room> roomList;
	private List<User> userList;
	
	public FakeDatabase() {
		roomList = new ArrayList<Room>();
		itemList = new ArrayList<Item>();
		userList = new ArrayList<User>();
		// Add initial data
		readInitialData();
		
		System.out.println(roomList.size() + " rooms");
		System.out.println(userList.size() + " users");
	}

	// Getting the author and book tables to access in the fake database
	public void readInitialData() {
		try {
			roomList.addAll(InitialData.getRooms());
			itemList.addAll(InitialData.getItems());
			userList.addAll(InitialData.getUsers());
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
		for(Room room: roomList) {
			if (room.getTitle().equals(title)){
				return room;
			}
		}
		return null;
	}

	@Override
	public Room findRoomUsingLocation(String location) {
		return null;
	}

	@Override
	public Room findRoomUsingRoomId(int roomId) {
		for (Room room : roomList){
			if(room.getRoomId() == roomId){
				return room;
			}
		}
		return null;
	}

	@Override
	public void insertItem(Item item) {
		int itemId = roomList.size();
		Item newItem = new Item();
		newItem.setItemId(itemId);
		newItem.setTitle(item.getTitle());
		newItem.setDescription(item.getDescription());
		newItem.setRoomLocat(item.getRoomLocat());
		newItem.setStatAffected(item.getStatAffected());
		newItem.setStatChangeVal(item.getStatChangeVal());
		itemList.add(newItem);
		System.out.println("Stored new item!!");				
	}

	@Override
	public Item findItemUsingTitle(String title) {
		for (Item item : itemList){
			if(item.getTitle() == title){
				return item;
			}
		}
		return null;
	}

	@Override
	public Item findItemUsingItemId(int itemId) {
		for (Item item : itemList){
			if(item.getItemId() == itemId){
				return item;
			}
		}
		return null;
	}

	@Override
	public Item findItemUsingLocation(String location) {
		for (Item item : itemList){
			if(item.getRoomLocat() == location){
				return item;
			}
		}
		return null;
	}

	@Override
	public List<User> getAccountInfo(String name) {
		List<User> result = new ArrayList<User>();
		for(User user: userList) {
			if (user.getUsername().equals(name)){
				result.add(user);
			}
		}
		return result;
	}
	                
	@Override
	public List<User> matchUsernameWithPassword(String Username, String pass) {
		List<User> result = new ArrayList<User>();
		for(User user: userList) {
			
			if (user.getPassword().equals(pass)){
				if (user.getUsername().equals(Username)){
				result.add(user);
			}
		}}
		return result;
		
	}

	@Override
	public List<User> addUserToDatabase(String name, String pass, String email, String fn, String ln) {
		User u = new User();
		u.setUserAccountInformation(name, fn, ln, email, pass);
		userList.add(u);
		return userList;
	}

	@Override
	public List<User> DeleteUserFromDatabase(String name, String pswd) {
		
		for(User user: userList) {
			if (user.getUsername().equals(name)){
				if(user.getPassword().equals(pswd)){
					userList.remove(user);
				}
			}
		}
		return userList;
	}

	@Override
	public List<User> changePassword(String name, String pswd, String newPassword) {
		for(User user: userList) {
			if (user.getUsername().equals(name)){
				if(user.getPassword().equals(pswd)){
					user.setPassword(newPassword);
				}
			}
		}
		return userList;
	}

	@Override
	public List<User> findAllUsers() {
		return userList;
	}

	@Override
	public List<User> changePassByNameAndEmailAndUsername(String fn, String ln, String email, String usernm, String newPassword) {
		for(User user: userList) {
			if (user.getUsername().equals(usernm)){
				if(user.getFirstName().equals(fn)){
					if(user.getLastName().equals(ln)){
						if(user.getEmail().equals(email)){
					}
					user.setPassword(newPassword);
						}
					}	
				}	
			}
		
		return userList;
	}


	
}// end of class
