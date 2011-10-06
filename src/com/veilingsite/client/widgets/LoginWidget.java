package com.veilingsite.client.widgets;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.veilingsite.client.controllers.UC;
import com.veilingsite.shared.ServerService;
import com.veilingsite.shared.ServerServiceAsync;
import com.veilingsite.shared.domain.User;

public class LoginWidget extends VerticalPanel {

	private Label myLabel = new Label();
	Button login = new Button("Login");
	
	public LoginWidget() {	
		final TextBox name = new TextBox();
		final TextBox pw = new TextBox();
		
		add(myLabel);
		add(name);
		add(pw);
		add(login);
		
		login.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				if(UC.getLoggedIn() == null) {
					loginUser(new User(name.getText(), pw.getText()));
					myLabel.setText("Login request processing...");
				} else {
					myLabel.setText("User logged out.");
					login.setText("Login");
					UC.setLoggedIn(null);
					return;
				}
			}
		});
	}
	
	private void setLogin(User u) {
		if(u != null) {
			myLabel.setText("Welkom "+u.getUserName());
			login.setText("Logout");
			UC.setLoggedIn(u);
		} else {
			myLabel.setText("User was not found or submitted data was incorrect.");
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