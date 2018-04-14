package edu.ycp.cs320.otterspace.servlet;
import edu.ycp.cs320.roomsdb.*;

public class User {
	private String emailAddress;
	private String password;
	private String firstName;
	private String lastName; 
	private boolean loginStatus = false;
	
	
	public void setUserAccountInformation(String fn, String ln, String email, String pw) {
		this.firstName = fn;
		this.lastName = ln;
		this.emailAddress = email;
		this.password = pw;
	}
	
	public String getEmail() {
		return emailAddress;
	}

	public void setEmail(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public boolean logIn(String Username, String password){
		//working with authenticate in the controller 
		if(loginStatus != true) {
			loginStatus = true; 
			
		
		}
		
		return loginStatus;
	}

}
