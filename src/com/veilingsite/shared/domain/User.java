package com.veilingsite.shared.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * TODO	constructor checking if user name already exists upon creation.
 */
@Entity
public class User implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;
	private String userName;
	private String email;
	private String firstName;
	private String surName;
	private String mobilePhoneNumber;
	private String password;
	
	private int permission = 0;
	
	public User() {}
	
	/**
	 * Simple constructor for domain User with 2 parameters
	 * @param un String User name
	 * @param pwd String Password
	 * @return void
	 */
	public User(String un, String pwd) {
		this.userName = un;
		this.password = pwd;
	}

	/**
	 * Advanced constructor for domain User with 5 parameters
	 * @param un String User name
	 * @param pwd String Password
	 * @param email String Email address
	 * @param fn String Personal name
	 * @param sn String Surname
	 * @return void
	 */
	public User(String un, String pwd, String email, String fn, String sn) {
		this.userName = un;
		this.password = pwd;
		this.email = email;
		this.firstName = fn;
		this.surName = sn;
	}

	/**
	 * Getter for user name
	 * @return String User name
	 */
	public String getUserName() {
		return this.userName;
	}
	
	/**
	 * Getter for the password
	 * @return String Password
	 */
	public String getPassword() {
		return this.password;
	}
	
	/**
	 * Getter for the email address
	 * @return String Email address
	 */
	public String getEmail() {
		return this.email;
	}
	
	/**
	 * Getter for first/personal name
	 * @return String First name
	 */
	public String getFirstName() {
		return this.firstName;
	}
	
	/**
	 * Getter for the Surname
	 * @return String Surname
	 */
	public String getSurName() {
		return this.surName;
	}
	
	/**
	 * Getter for the mobile phone number
	 * @return String Mobile phone number
	 */
	public String getMobilePhoneNumber() {
		return this.mobilePhoneNumber;
	}
	
	/**
	 * Getter for the permissions of the user
	 * @return integer 0 indicates a normal user, 1 indicates an administrator.
	 */
	public int getPermission() {
		return this.permission;
	}
	
	/**
	 * Setter for the permissions of the user
	 * @param i integer 0 indicates a normal user, 1 indicates an administrator.
	 */
	public void setPermission(int i) {
		this.permission = i;
	}
}