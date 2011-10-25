package com.veilingsite.shared;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.veilingsite.shared.domain.Auction;
import com.veilingsite.shared.domain.Bid;
import com.veilingsite.shared.domain.Category;
import com.veilingsite.shared.domain.User;

public interface ServerServiceAsync {

	/**
	 * Get a single user based on a user name.
	 * @param s String User name of the user to be returned.
	 * @param callback AsyncCallback<User> AsyncCallback with the result.
	 * @return User returns the User if found, else null.
	 */
	void getUser(String s, AsyncCallback<User> callback);
	
	/**
	 * adds User u to the DataStore.
	 * @param u User The user to be added.
	 * @param callback AsyncCallback<Boolean> AsyncCallback with the result.
	 * @return Boolean
	 */
	void addUser(User u, AsyncCallback<User> callback);
	
	void removeUser(User u, AsyncCallback<Void> callback);
	
	void updateUser(User u, AsyncCallback<Void> callback);
	
	void addAuction(Auction a, AsyncCallback<Auction> callback);
	
	/**
	 * Gets an ArrayList of all users currently in the DataStore.
	 * @return ArrayList<User> ArrayList of users.
	 * @param callback AsyncCallback<ArrayList<User>> AsyncCallback with the result.
	 */
	void getUserList(AsyncCallback<ArrayList<User>> callback);

	void loginUser(User u, AsyncCallback<User> callback);

	void getAuctionList(User limitUser, Category limitCat, AsyncCallback<ArrayList<Auction>> callback);
	
	void getCategoryList(AsyncCallback<ArrayList<Category>> callback);
}