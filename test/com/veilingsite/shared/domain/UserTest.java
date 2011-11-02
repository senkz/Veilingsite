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

	
}
