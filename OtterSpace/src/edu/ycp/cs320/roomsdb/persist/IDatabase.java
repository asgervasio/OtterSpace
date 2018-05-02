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
	public Integer findRoomIdFromConnection(String connection);

	public Player insertPlayer(Player player);
	public Player findPlayerUsingName(String name);
	public Player findPlayerUsingLocation(Room roomLoc);
  
	//User-based methods
	List<User> getAccountInfo(String Username);
	List<User> addUserToDatabase(String Username, String pass, String email, String first, String last);
	List<User> DeleteUserFromDatabase(String name, String pswd);
	List<User> changePassword(String name, String pswd, String newPassword);
	List<User> findAllUsers();
	List<User> findUserByLastName(String lastname);
	List<User> matchUsernameWithPassword(String Username, String pass);
	String insertConsole(String data);
	List<String> loadConsole();
	String insertChange(String typeChange, int typeId, int healthChange, String locatTypeChange, int locatTypeId);


		List<User> getAccountInfo(String Username);
		Boolean    addUserToDatabase(String Username, String pass, String email, String first, String last);
		List<User> DeleteUserFromDatabase(String name, String pswd);
		String changeInfo(String newEmail, String username, String pswd, String newPassword);
		List<User> findAllUsers();
		List<User> findUserByLastName(String lastname);
		List<User> matchUsernameWithPassword(String Username, String pass);
    
  //Console methods
	  String insertConsole(String data);
	  List<String> loadConsole();


}
