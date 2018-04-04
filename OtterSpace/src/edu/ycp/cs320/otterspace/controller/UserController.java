package edu.ycp.cs320.otterspace.controller;


import java.util.ArrayList;
import java.util.List;

import edu.ycp.cs320.otterspace.model.*;

public class UserController 
{
	private User model;
	//private IDatabase db;
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

public ArrayList<User> matchUserNameWithPassword(String username) {
		
		List<User> userList = null;
	  //List<User> userList = db.matchUsernameWithPassword(username);
		
		ArrayList<User> users = new ArrayList<User>();
		
		for(User user : userList) {
			users.add(user);
		}
		
		return users;
	}
}
