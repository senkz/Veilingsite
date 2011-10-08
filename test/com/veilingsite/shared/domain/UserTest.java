package com.veilingsite.shared.domain;

import static org.junit.Assert.*;

import org.junit.Test;

public class UserTest {

	@Test
	public void testUser() {
		// Testcase: create User
		User u = new User();
		assertEquals(null, u.getUserName());
	}

	@Test
	public void testUserStringString() {
		// Testcase: create User with password and username
		User u = new User("test","test");
		assertEquals("test", u.getUserName());
		assertEquals("test", u.getPassword());
	}

	@Test
	public void testUserStringStringStringStringString() {
		// Testcase: create User with extended constructor
		User u = new User("senkz", "test", "sethsnel@hotmail.com", "seth", "snel");
		assertEquals("senkz", u.getUserName());
		assertEquals("test", u.getPassword());
		assertEquals("sethsnel@hotmail.com", u.getEmail());
		assertEquals("seth", u.getFirstName());
		assertEquals("snel", u.getSurName());		
	}

	@Test
	public void testGetUserName() {
		// Testcase: set & retrieve username
		User u = new User();
		u.setUserName("username");
		assertEquals("username", u.getUserName());		
	}

	@Test
	public void testGetPassword() {
		// Testcase: set & retrieve password
		User u = new User();
		u.setPassword("password");
		assertEquals("password", u.getPassword());	
	}

	@Test
	public void testGetEmail() {
		// Testcase: set & retrieve email
		User u = new User();
		u.setEmail("email");
		assertEquals("email", u.getEmail());	
	}

	@Test
	public void testGetFirstName() {
		// Testcase: set & retrieve first name
		User u = new User();
		u.setFirstName("firstname");
		assertEquals("firstname", u.getFirstName());
	}

	@Test
	public void testGetSurName() {
		// Testcase: set & retrieve sur name
		User u = new User();
		u.setSurName("surname");
		assertEquals("surname", u.getSurName());
	}

	@Test
	public void testGetMobilePhoneNumber() {
		// Testcase: set & retrieve phone number
		User u = new User();
		u.setMobilePhoneNumber("0620422667");
		assertEquals("0620422667", u.getMobilePhoneNumber());
	}

	@Test
	public void testGetPermission() {
		// Testcase: set & retrieve permissions
		User u = new User();
		u.setPermission(1);
		assertEquals(1, u.getPermission());
	}

	@Test
	public void testSetPermission() {
		// Testcase: set & set permissions
		User u = new User();
		u.setPermission(1);
		assertEquals(1, u.getPermission());
	}
}
