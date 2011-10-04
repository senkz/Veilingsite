package com.veilingsite.shared;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.veilingsite.shared.domain.User;

@RemoteServiceRelativePath("myServer")
public interface ServerService extends RemoteService {
	
	/**
	 * Get a single user based on a user name.
	 * @param s String User name of the user to be returned.
	 * @return User returns the User if found, else null.
	 */
	public User getUser(String s);
	
	/**
	 * adds User u to the DataStore.
	 * @param u User The user to be added.
	 * @return True or False if user is added
	 */
	public Boolean addUser(User u);
	
	/**
	 * Gets an ArrayList of all users currently in the DataStore.
	 * @return ArrayList<User> ArrayList of users.
	 */
	public ArrayList<User> getUserList();
	
	public User loginUser(User u);
}