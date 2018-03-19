package edu.ycp.cs320.otterspace.servlet;

public class User {
	private String emailAddress;
	private String password;
	private String firstName;
	private String lastName; 
	
	
	
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

	public boolean ValidateCredetials(String Username, String  password) {
		
		//
		//call to db interface
		//
		return false;
	}

}
