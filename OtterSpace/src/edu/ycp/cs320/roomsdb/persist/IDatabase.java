package edu.ycp.cs320.roomsdb.persist;

import java.awt.Color;
import java.util.List;

import edu.ycp.cs320.otterspace.controller.game.*;
import edu.ycp.cs320.otterspace.model.*;

public interface IDatabase {
	public Room insertRoom(Room room);
	public Room findRoomUsingTitle(String title);

	public Room findRoomUsingRoomId(int roomId);
	
	public Item insertItem(Item item);
	public Item findItemUsingTitle(String title);
	public Item findItemUsingItemId(int itemId);
	public List<Item> findItemsUsingLocation(int location);
  
	//User-based methods
	List<User> getAccountInfo(String Username);
	List<User> addUserToDatabase(String Username, String pass, String email, String first, String last);
	List<User> DeleteUserFromDatabase(String name, String pswd);
	List<User> changePassword(String name, String pswd, String newPassword);
	List<User> findAllUsers();
	List<User> findUserByLastName(String lastname);
	List<User> matchUsernameWithPassword(String Username, String pass);

	
}
