package com.veilingsite.client.widgets;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.veilingsite.shared.*;
import com.veilingsite.shared.domain.User;

public class UserRegisterWidget extends VerticalPanel {
	
	private FlexTable table = new FlexTable();
	private Label systemStatus = new Label("Register User");
	private TextBox name = new TextBox();
	private Button login = new Button("Register");
	private TextBox password = new TextBox();
	
	public UserRegisterWidget() {

		//add class for styling
		this.addStyleName("widget");
		
		add(systemStatus);
		add(table);
		table.setWidget(0, 0, new Label("Username: "));
		table.setWidget(0, 1, name);
		table.setWidget(1, 0, new Label("Password: "));
		table.setWidget(1, 1, password);
		table.setWidget(2, 0, login);
		
		login.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				addUser(new User(name.getText(), password.getText()));
				systemStatus.setText("Register request processing...");
			}
		});
	}
	
	private void setRegisterStatus(User u) {
		if(u != null) {
			systemStatus.setText("Welcome "+u.getUserName());
		} else {
			systemStatus.setText("User addition was unsuccesfull, username probably already existed");
		}
	}
	
	private void addUser(User u) {		
		ServerServiceAsync myService = (ServerServiceAsync) GWT.create(ServerService.class);
		AsyncCallback<User> callback = new AsyncCallback<User>() {		
			@Override
			public void onFailure(Throwable caught) {}	
			
			@Override
			public void onSuccess(User result) {
				setRegisterStatus(result);
			}
		};
		myService.addUser(u, callback);
	}
}