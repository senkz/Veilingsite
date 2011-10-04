package com.veilingsite.client.controllers;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.veilingsite.client.widgets.LoginWidget;
import com.veilingsite.shared.ServerService;
import com.veilingsite.shared.ServerServiceAsync;
import com.veilingsite.shared.domain.User;

public class UserController {

	/**
	 *  Use this variable to return Boolean values.
	 */
	private Boolean b;

	private LoginWidget lw;
	
	public UserController(LoginWidget lw) {
		this.lw = lw;
	}
	
	/*
	 *  Update this later?
	 * 
	public void updateList() {
		ServerServiceAsync myService = (ServerServiceAsync) GWT.create(ServerService.class);
		AsyncCallback<ArrayList<User>> callback = new AsyncCallback<ArrayList<User>>() {
			@Override
			public void onFailure(Throwable caught) {}
			@Override
			public void onSuccess(ArrayList<User> result) {
				p.clear();
				
				for(User u : result) {
					HorizontalPanel pl = new HorizontalPanel();

					pl.add(new Label(u.getUserName()));
					pl.add(new Label(u.getPassword()));
					pl.add(new Label(u.getFirstName()));
					pl.add(new Label(u.getSurName()));
					pl.add(new Label(u.getEmail()));
					
					p.add(pl);
				}
			}
		};
		myService.getUserList(callback);
	}
	*/
}