package edu.ycp.cs320.roomsdb.persist;

import java.awt.Color;
import java.util.List;

import edu.ycp.cs320.otterspace.controller.game.*;
import edu.ycp.cs320.otterspace.model.*;

public interface IDatabase {
	public void insertRoom(Room room);
	public List<Room> findRoomUsingTitle(String title);
	public List<Room> findRoomUsingLocation(String location);
	public List<Room> findRoomUsingRoomId(int roomId);


	//User-based methods
	List<User> getAccountInfo(String Username);
	List<User> matchUsernameWithPassword(String Username, String Password);	
	List<User> addUserToDatabase(String Username, String pass, String email, String first, String last);
	List<User> DeleteUserFromDatabase(String name, String pswd);
	List<User> changePassword(String name, String pswd, String newPassword);
	List<User> findAllUsers();
	List<User> changePassByNameAndEmailAndUsername(String Firstname, String lastname, String email,String usernm, String pass);
	
}
