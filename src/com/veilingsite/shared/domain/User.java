package com.veilingsite.shared.domain;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="tbl_user")
public class User implements Serializable {
	@Id	
	private String userName;
	private String email;
	private String firstName;
	private String surName;
	private String mobilePhoneNumber;
	private String password;
	private int permission;

	@Transient
	private String			regExpOnlyLetters	  		  = new String("^[A-Za-z]{1,}$");
	@Transient
	private String			regExpEmail				   	  = new String("^[a-z0-9._%-]+@[a-z0-9.-]+[.][a-z.]{2,4}$");
	@Transient
	private String			regExpPassword			   	  = new String("^[A-Za-z]\\w{4,}[A-Za-z]$");
	@Transient
	private String			regExpMobilePhone		   	  = new String("^06+[0-9]{8}$");
	
	public User() {}
	
	/**
	 * Simple constructor for domain User with 2 parameters
	 * @param un String User name
	 * @param pwd String Password
	 * @return void
	 */
	public User(String un, String pwd) {
		this.setUserName(un);
		this.setPassword(pwd);
		this.setPermission(1);
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
		this.setUserName(un);
		this.setPassword(pwd);
		this.setEmail(email);
		this.setFirstName(fn);
		this.setSurName(sn);
		this.setPermission(1);
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
		if(i == 1 || i == 2){
			this.permission = i;
		}else{
			this.permission = 0;
		}
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName){
		this.userName = userName;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		if(email != null && email.matches(regExpEmail)){
			this.email = email;
		}else{
			this.email = null;
		}
		
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		if(firstName != null && firstName.matches(regExpOnlyLetters)){
			this.firstName = firstName;
		}else{
			this.firstName = null;
		}
		if(firstName.matches("^[^0-9]+$")){
			this.firstName = firstName;
		}
	}

	/**
	 * @param surName the surName to set
	 */
	public void setSurName(String surName) {
		if(surName != null && surName.matches(regExpOnlyLetters)){
			this.surName = surName;
		}else{
			this.surName = null;
		}
		
		if(surName.matches("^[^0-9]+$")){
			this.surName = surName;
		}
	}

	/**
	 * @param mobilePhoneNumber the mobilePhoneNumber to set
	 */
	public void setMobilePhoneNumber(String mobilePhoneNumber) {
		if(mobilePhoneNumber != null && mobilePhoneNumber.matches(regExpMobilePhone)){
			this.mobilePhoneNumber = mobilePhoneNumber;
		}else{
			this.mobilePhoneNumber = null;
		}
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		if(password != null && password.matches(regExpPassword)){
			this.password = password;
		}else{
			this.password = null;
		}
	}
	
	public String getUserI(){
		return this.userName.toUpperCase();
	}
}