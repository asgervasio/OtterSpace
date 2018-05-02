package edu.ycp.cs320.roomsdb.persist;

import java.awt.Color;
import java.util.List;

import edu.ycp.cs320.otterspace.controller.game.*;
import edu.ycp.cs320.otterspace.model.*;

public interface IDatabase {
	public Room insertRoom(Room room);
	public Room findRoomUsingTitle(String title);

	public Room findRoomUsingRoomId(int roomId);
	
	public Item insertItem(Item item, String username);
	public Item findItemUsingTitle(String title, String username);
	public Item findItemUsingItemId(int itemId, String username);
	public List<Item> findItemsUsingLocation(int location, String username);
	public Integer findRoomIdFromConnection(String connection);

	public Player insertPlayer(Player actor, String username);
	public Player findPlayerUsingName(String name, String username);
	public Player findPlayerUsingLocation(Room roomLoc, String username);
  
	//User-based methods
	List<User> getAccountInfo(String Username);
	List<User> DeleteUserFromDatabase(String name, String pswd);
	List<User> changePassword(String name, String pswd, String newPassword);
	List<User> findAllUsers();
	List<User> findUserByLastName(String lastname);
	List<User> matchUsernameWithPassword(String Username, String pass);
	String insertConsole(String data, String username);
	List<String> loadConsole(String username);


	Boolean addUserToDatabase(String Username, String pass, String email, String first, String last);
	String changeInfo(String newEmail, String username, String pswd, String newPassword);
	public void createTables(String username);
	public void createPersistingTables(String username);
    



}
