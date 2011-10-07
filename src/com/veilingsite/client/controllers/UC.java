package com.veilingsite.client.controllers;

import java.util.ArrayList;

import com.veilingsite.shared.domain.User;

public class UC {

	/**
	 *  Use this variable to return Boolean values.
	 */
	private static User loggedIn = null;
	private static ArrayList<UserChangeListener> listeners = new ArrayList<UserChangeListener>();
	
	public static void setLoggedIn(User u) {
		loggedIn = u;
		for(UserChangeListener ucl : listeners)
			ucl.fireListener();
	}
	
	public static User getLoggedIn() {
		return loggedIn;
	}
	
	public static void addUserChangeListener(UserChangeListener ucl) {
		listeners.add(ucl);
	}
	
	public static void removeUserChangeListener(UserChangeListener ucl) {
		listeners.remove(ucl);
	}
}