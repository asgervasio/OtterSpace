package edu.ycp.cs320.otterspace.controller;


import java.util.ArrayList;
import java.util.List;

import edu.ycp.cs320.roomsdb.persist.FakeDatabase;
import edu.ycp.cs320.otterspace.model.User;

public class UserController {
	
	private User model;
	private FakeDatabase db = new FakeDatabase();
	
	public void setModel(User model)
	{
		this.model = model;
	}
	
	public static boolean authenticate(User u, String pass)
	{
		boolean isReal = false;
		if(u.getPassword().equals(pass)){

			isReal = true;
		}
		
		return isReal;
	}
	
	public List<User> getAccountInformation(String username){
		List<User> u = db.getAccountInfo(username);
		return u;
	}
	
	public void changeUserPassword(String Username, String oldpassword, String newpassword)  {
		 
		 db.changePassword(Username, oldpassword, newpassword);
	}
	
	
	//adding a user to the database 
	public void addUserToDatabase(String userName, String pass, String email, String first, String last) {
		
		db.addUserToDatabase(userName, pass, email, first, last);
	}
	
	//need to work out how to change this to archive user, not delete 
	public void DeleteUserFromDatabase(String userName, String password){
		
		db.DeleteUserFromDatabase(userName, password);
	}
	
	
public ArrayList<User> matchUserNameWithPassword(String Username, String Password) {
		
	List<User> u = db.matchUsernameWithPassword(Username, Password);
		
		ArrayList<User> users = new ArrayList<User>();
		
		for(User user : u) {
			users.add(user);
		}
		
		return users;
	
	}
}
