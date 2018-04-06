package edu.ycp.cs320.otterspace.controller;


import java.util.ArrayList;
import java.util.List;


import edu.ycp.cs320.roomsdb.persist.DatabaseProvider;
import edu.ycp.cs320.roomsdb.persist.FakeDatabase;
import edu.ycp.cs320.roomsdb.persist.IDatabase;
import edu.ycp.cs320.roomsdb.*;

import edu.ycp.cs320.otterspace.model.User;

public class UserController 
{
	private User model;

	IDatabase db = DatabaseProvider.getInstance();
	
	public void setModel(User model)
	{
		this.model = model;
	}
	
	public static boolean authenticate(User u, String pass)
	{
		boolean real = false;
		if(u.getPassword().equals(pass)){

			real = true;
		}
		
		return real;
	}
	
	public ArrayList<User> getAccountInformation(String username){
		List<User> userList = db.getAccountInfo(username);
		
	}
	
	public void changeUserPassword(String Username, String oldpassword, String newpassword)  {
		 db.changePassword(Username, oldpassword, newpassword);
	}
	
	//adding a user to the database 
	public void addUserToDatabase(String userName, String passWord, String email, String type, String firstName, String lastname) {
		
		db.addUserToDatabase(userName, passWord, email, type, firstName);
	}
	
	//need to work out how to change this to archive user, not delete 
	public void DeleteUserFromDatabase(String userName, String password){
		
		db.DeleteUserFromDatabase(userName, password);
	}
	
	
	public ArrayList<User> matchUserNameWithPassword(String username, String pass) {
		
db.matchUsernameWithPassword(username, pass);
	}
}
