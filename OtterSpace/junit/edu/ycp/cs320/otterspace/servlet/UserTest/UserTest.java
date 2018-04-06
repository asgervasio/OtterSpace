package edu.ycp.cs320.otterspace.servlet.UserTest;

import edu.ycp.cs320.otterspace.model.*;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;




public class UserTest {
	private String email;
	private String pw;
	private String fn;
	private String ln; 
	private User u = new User();
	// Set-up for the upcoming tests
	@Before
	public void setUp() {
		User u = new User();
		email = "Me@noOneCare.net";
		pw = "T3ST1NG";
		fn = "John";
		ln = "Doe";
	}
	

	
	
	
	@Test
	public void testSetPassword(){
	
	}
	
	@Test
	public void testSetEmail(){
	
	}
	
	@Test
	public void testSetFirst(){
	
	}
	
	@Test
	public void testSetLast(){
	
	}
	
}// end of class
