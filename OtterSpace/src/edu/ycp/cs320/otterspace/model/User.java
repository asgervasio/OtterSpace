package edu.ycp.cs320.otterspace.model;

public class User {
	private String emailAddress;
	private String password;
	private String firstName;
	private String lastName; 
	private String Username; 
	private boolean loginStatus = false;
	private String sessionid;
	
	public void setUserAccountInformation(String usernm, String fn, String ln, String email, String pw) {
		this.firstName = fn;
		this.lastName = ln;
		this.emailAddress = email;
		this.password = pw;
		this.Username = usernm;
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
	
	public String getSessionid() {
		return sessionid;
	}

	public void setSessionid(String sessionid) {
		this.sessionid = sessionid;
	}
	
	public String getUsername() {
		return Username;
	}

	public void setUsername(String usernm) {
		this.Username = usernm;
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
			
			//probably set this up in a controller 
			//electronicSignatureFlag = true;
		}
		
		return loginStatus;
	}
	
	public boolean logOut(String Username, String password){
		loginStatus = false;
		
		return loginStatus;
	}

}