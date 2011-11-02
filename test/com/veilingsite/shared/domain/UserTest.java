package com.veilingsite.shared.domain;

import static org.junit.Assert.*;

import org.junit.Test;

public class UserTest {
	private String			regExpOnlyLetters	  		  = new String("^[A-Za-z]{1,}$");
	private String			regExpEmail				   	  = new String("^[a-z0-9._%-]+@[a-z0-9.-]+[.][a-z.]{2,4}$");
	private String			regExpPassword			   	  = new String("^[A-Za-z]\\w{4,}[A-Za-z]$");
	private String			regExpMobilePhone		   	  = new String("^06+[0-9]{8}$");
	@Test
	public void testUser() {
		// Testcase: create User
		User u = new User();
		assertTrue(u instanceof User);
	}
	
	@Test
	public void testPasswordCriteria(){
		User u = new User("user", "fewgerge");
		assertTrue(u.getPassword().matches(regExpPassword));
	}
	
	@Test
	public void testPasswordLengthCriteria(){
		User u = new User("user", "fdsk");
		
		assertTrue(u.getPassword() == null);
	}
	
	@Test
	public void testPasswordLetterCriteria(){
		User u = new User("user", "4fdsk5");
		assertTrue(u.getPassword() == null);
	}
	
	
	@Test
	public void testFirstnameCriteria(){
		String fn = "henkie";
		User u = new User("test", "test", "test@test.nl", fn, "koelewijn");
		assertTrue(u.getFirstName().matches(regExpOnlyLetters));
	}
	
	public void testFirstnameNumberCriteria(){
		String fn = "henki3";
		User u = new User("test", "test", "test@test.nl", fn, "koelewijn");
		assertFalse(u.getFirstName().matches(regExpOnlyLetters));
	}
	
	@Test
	public void testSurnameCriteria(){
		String sn = "henkie";
		User u = new User("test", "test", "test@test.nl", "jan", sn);
		assertTrue(u.getSurName().matches(regExpOnlyLetters));
	}
	
	public void testSurnameNumberCriteria(){
		String sn = "henki3";
		User u = new User("test", "test", "test@test.nl", "jan", sn);
		assertFalse(u.getSurName().matches(regExpOnlyLetters));
	}
	
	@Test
	public void testEmailCriteria(){
		String em = "hahaha@haha.com";
		User u = new User("test", "test", em, "jan", "koelewijn");
		assertTrue(u.getEmail().matches(regExpEmail));
	}
	
	@Test
	public void testEmailFalseCriteria(){
		User u = new User("test", "test", "hahahahahacom", "jan", "koelewijn");
		assertTrue(u.getEmail() == null);
	}
	
	@Test
	public void testPhoneCriteria(){
		User u = new User("test", "test", "hahaha@haha.com", "jan", "koelewijn");
		u.setMobilePhoneNumber("0685858585");
		assertTrue(u.getMobilePhoneNumber().matches(regExpMobilePhone));
	}

	@Test
	public void testPhoneFalseCriteria(){
		User u = new User("test", "test", "hahaha@haha.com", "jan", "koelewijn");
		u.setMobilePhoneNumber("4485858585");
		assertTrue(u.getMobilePhoneNumber() == null);
	}
	
	@Test
	public void testPhoneLengthCriteria(){
		User u = new User("test", "test", "hahaha@haha.com", "jan", "koelewijn");
		u.setMobilePhoneNumber("06858585");
		assertTrue(u.getMobilePhoneNumber() == null);
	}
}
