package com.veilingsite.client.controllers;

import com.veilingsite.shared.domain.User;

public class UserController {

	/**
	 *  Use this variable to return Boolean values.
	 */
	private User loggedIn = null;
	
	public UserController() {
	}
	
	public void setLoggedIn(User u) {
		loggedIn = u;
	}
	
	public User getLoggedIn() {
		return this.loggedIn;
	}
	
}