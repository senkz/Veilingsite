package com.veilingsite.client.widgets;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.veilingsite.client.controllers.UC;
import com.veilingsite.shared.*;
import com.veilingsite.shared.domain.User;

public class UserLoginWidget extends VerticalPanel {

	private Label systemStatus = new Label();
	private Button login = new Button("Login");
	private TextBox username = new TextBox();
	private TextBox password = new TextBox();
	private FlexTable table = new FlexTable();
		
	public UserLoginWidget() {
		
		//add class for styling
		this.addStyleName("widget");
		
		if(UC.getLoggedIn() != null) {
			table.setVisible(false);
		}
		
		systemStatus.setVisible(false);
		add(table);	
		table.setWidget(0, 0, new Label("Username:"));		table.setWidget(0, 1, new Label("Password:"));
		table.setWidget(1, 0, username);					table.setWidget(1, 1, password);		table.setWidget(1, 2, login);
		table.setWidget(2, 0, systemStatus);
		table.getFlexCellFormatter().setColSpan(2, 0, 3);
		
		login.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				systemStatus.setVisible(false);
				if(UC.getLoggedIn() == null) {
					loginUser(new User(username.getText(), password.getText()));
					systemStatus.setText("Login request processing...");
				} else {
					table.getRowFormatter().setVisible(0, true);
					table.getCellFormatter().setVisible(1, 0, true);
					table.getCellFormatter().setVisible(1, 1, true);
					systemStatus.setText("User logged out");			
					systemStatus.setStyleName("succesfull");
					systemStatus.setVisible(true);
					login.setText("Login");
					UC.setLoggedIn(null);
					return;
				}
			}
		});
	}
	
	private void setLogin(User u) {
		if(u != null) {
			table.getRowFormatter().setVisible(0, false);
			table.getCellFormatter().setVisible(1, 0, false);
			table.getCellFormatter().setVisible(1, 1, false);
			systemStatus.setText("Welcome "+u.getUserName());
			systemStatus.setStyleName("succesfull");
			systemStatus.setVisible(true);
			login.setText("Logout");
			UC.setLoggedIn(u);
		} else {
			systemStatus.setText("User was not found or submitted data was incorrect.");
			systemStatus.setStyleName("error");
			systemStatus.setVisible(true);
		}
	}
	
	private void loginUser(User u) {
		ServerServiceAsync myService = (ServerServiceAsync) GWT.create(ServerService.class);
		AsyncCallback<User> callback = new AsyncCallback<User>() {		
			@Override
			public void onFailure(Throwable caught) {
				systemStatus.setText("User login failed, reason: " + caught.getMessage());
				systemStatus.setStyleName("error");
				systemStatus.setVisible(true);
			}	
			
			@Override
			public void onSuccess(User result) {
				setLogin(result);
			}
		};
		myService.loginUser(u, callback);
	}
}