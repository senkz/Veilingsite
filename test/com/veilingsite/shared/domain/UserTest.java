package com.veilingsite.shared.domain;

import static org.junit.Assert.*;

import org.junit.Test;

public class UserTest {

	@Test
	public void testUser() {
		// Testcase: create User
		User u = new User();
		assertTrue(u instanceof User);
	}
	
	@Test
	public void testUsernameIsCaseSensitive(){		
		String username = "test";
		String testusername = "TeSt";		
		
		User u = new User(username, null);
		User u2 = new User(testusername, null);
		assertTrue(u.getUserI().equals(u2.getUserI()));
	}
	
	@Test
	public void testPasswordCriteria(){
		User u = new User("user", "fewgerge");
		assertTrue(u.getPassword().matches("^[A-Za-z]\\w{4,}[A-Za-z]$"));
	}
	
	@Test
	public void testFirstnameCriteria(){
		String fn = "henkie";
		User u = new User(null, null, null, fn, null);
		assertTrue(u.getFirstName().matches("^[^0-9]+$"));
	}
	
	@Test
	public void testSurnameCriteria(){
		String sn = "henkie";
		User u = new User(null, null, null, null, sn);
		assertTrue(u.getSurName().matches("^[^0-9]+$"));
	}
	
	@Test
	public void testEmailCriteria(){
		String em = "hahaha@haha.com";
		User u = new User(null, null, em, null, null);
		assertTrue(u.getEmail().matches("^[a-z0-9._%-]+@[a-z0-9.-]+[.][a-z.]{2,4}$"));
	}
	
	@Test
	public void testPhoneCriteria(){
		User u = new User(null, null);
		u.setMobilePhoneNumber("0685858585");
		assertTrue(u.getMobilePhoneNumber().matches("^[0-9]{10}[0-9]*"));
	}

	
}
