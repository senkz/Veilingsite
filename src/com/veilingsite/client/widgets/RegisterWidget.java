package com.veilingsite.client.widgets;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.veilingsite.shared.ServerService;
import com.veilingsite.shared.ServerServiceAsync;
import com.veilingsite.shared.domain.User;

public class RegisterWidget extends VerticalPanel {

	private Label myLabel = new Label();
	
	public RegisterWidget() {	
		final TextBox name = new TextBox();
		final TextBox pw = new TextBox();
		Button login = new Button("Register user");
		
		add(myLabel);
		add(name);
		add(pw);
		add(login);
		
		login.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				addUser(new User(name.getText(), pw.getText()));
				myLabel.setText("Register request processing...");
			}
		});
	}
	
	private void setRegisterStatus(User u) {
		if(u != null) {
			myLabel.setText("Welkom "+u.getUserName());
		} else {
			myLabel.setText("User addition was unsuccesfull, username probably already existed");
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