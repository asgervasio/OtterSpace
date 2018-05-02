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
	
	public User getModel()
	{
		return this.model;
	}
	
	public static boolean authenticate(User u, String pass)
	{
		boolean real = false;
		if(u.getPassword().equals(pass)){

			real = true;
		}
		
		return real;
	}
	
	public void getAccountInformation(String username){
		List<User> userList = db.getAccountInfo(username);
		
	}
	/*
	public void setAccountInfomation(String UserName, String Password, String Email, String FirstName, String LastName){
		model.setUserAccountInformation(UserName, FirstName, LastName, Email, Password);
		//basically unused/unneeded function
	}
	*/
	
	public void changeUserInfo(String Email, String username, String pswd, String newPassword)  {
		 db.changeInfo(Email,  username,  pswd,  newPassword);
	}
	
	//adding a user to the database 
	public void addUserToDatabase(String userName, String passWord, String email, String firstName, String lastName) {
		
		db.addUserToDatabase(userName, passWord, email, firstName, lastName);
	}
	
	//need to work out how to change this to archive user, not delete 
	public void DeleteUserFromDatabase(String userName, String password){
		
		db.DeleteUserFromDatabase(userName, password);
	}
	
	
	public ArrayList<User> matchUserNameWithPassword(String username, String pass) {
		ArrayList<User> u = new ArrayList<User>();
		db.matchUsernameWithPassword(username, pass);
		u.addAll(db.matchUsernameWithPassword(username, pass));
		return u;
	}
}