package com.veilingsite.client.controllers;

import com.veilingsite.shared.domain.User;

public class UC {

	/**
	 *  Use this variable to return Boolean values.
	 */
	private static User loggedIn = null;
	
	public static void setLoggedIn(User u) {
		loggedIn = u;
	}
	
	public static User getLoggedIn() {
		return loggedIn;
	}
	
}