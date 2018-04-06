package edu.ycp.cs320.otterspace.servlet.UserTest;

import edu.ycp.cs320.otterspace.model.*;

import static org.junit.Assert.*;

import java.math.BigInteger;

import org.junit.Before;
import org.junit.Test;




public class UserTest {
	private String email;
	private String usernm;
	private String pw;
	private String fn;
	private String ln; 
	private String emailq;
	private String usernmq;
	private String pwq;
	private String fnq;
	private String lnq; 
	private String sessionid;
	private User u = new User();
	
	// Set-up for the upcoming tests
	@Before
	public void setUp() {
		User u = new User();
		//Before Change
		email = "Me@noOneCare.net";
		pw = "T3ST1NG";
		fn = "John";
		ln = "Doe";
		usernm = "User123";
		
		emailq = "Why@WouldICare.org";
		pwq = "T3st3d";
		fnq = "Jane";
		lnq = "Duck";
		usernmq = "WhatsAUsername";
		sessionid = "14dd9s87f637";
	}
	

	
	
	
	@Test
	public void testSetsUserAccountInfo(){
	u.setUserAccountInformation(usernm, fn, ln, email, pw);
	
	assertEquals("User123",u.getUsername());
	assertEquals("John",u.getFirstName());
	assertEquals("Doe",u.getLastName());
	assertEquals("Me@noOneCare.net",u.getEmail());
	assertEquals("T3ST1NG",u.getPassword());
	
	}
	
	@Test
	public void testSets(){
	u.setEmail(emailq);
	u.setFirstName(fnq);
	u.setLastName(lnq);
	u.setPassword(pwq);
	u.setSessionid(sessionid);
	u.setUsername(usernm);
	assertEquals("T3ST1NG",u.getPassword());
	
	}
	
	
}// end of class
