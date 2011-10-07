package com.veilingsite.client.widgets;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.veilingsite.client.controllers.UC;
import com.veilingsite.shared.*;
import com.veilingsite.shared.domain.User;

public class LoginWidget extends VerticalPanel {

	private Label systemStatus = new Label("Login User");
	private Button login = new Button("Login");
	private TextBox username = new TextBox();
	private TextBox password = new TextBox();
	private FlexTable table = new FlexTable();
		
	public LoginWidget() {
		add(systemStatus);
		add(table);	
		table.setWidget(0, 0, new Label("Username:"));
		table.setWidget(0, 1, username);
		table.setWidget(1, 0, new Label("Password:"));
		table.setWidget(1, 1, password);
		table.setWidget(2, 0, login);		
		
		login.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				if(UC.getLoggedIn() == null) {
					loginUser(new User(username.getText(), password.getText()));
					systemStatus.setText("Login request processing...");
				} else {
					systemStatus.setText("User logged out.");
					login.setText("Login");
					UC.setLoggedIn(null);
					return;
				}
			}
		});
	}
	
	private void setLogin(User u) {
		if(u != null) {
			systemStatus.setText("Welkom "+u.getUserName());
			login.setText("Logout");
			UC.setLoggedIn(u);
		} else {
			systemStatus.setText("User was not found or submitted data was incorrect.");
		}
	}
	
	private void loginUser(User u) {
		ServerServiceAsync myService = (ServerServiceAsync) GWT.create(ServerService.class);
		AsyncCallback<User> callback = new AsyncCallback<User>() {		
			@Override
			public void onFailure(Throwable caught) {}	
			
			@Override
			public void onSuccess(User result) {
				setLogin(result);
			}
		};
		myService.loginUser(u, callback);
	}
}