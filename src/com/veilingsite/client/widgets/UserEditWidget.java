package com.veilingsite.client.widgets;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.veilingsite.client.controllers.UC;
import com.veilingsite.shared.ServerService;
import com.veilingsite.shared.ServerServiceAsync;
import com.veilingsite.shared.domain.User;

public class UserEditWidget extends VerticalPanel {
	
	private Label 			systemStatus 		   = new Label();					// The Status of the editing process will be displayed in this label
	private Label 			user_Username 		   = new Label(); 					// Contains username and userID - Uneditable
	private Label 			user_Firstname		   = new Label(); 					// A User's firstname - Uneditable
	private Label 			user_Surname		   = new Label(); 					// A User's surname - Uneditable
	private TextBox 		user_Email			   = new TextBox(); 				// A User's Email - Editable
	private TextBox 		user_MobilePhoneNumber = new TextBox(); 				// A User's mobile phone number - Editable
	
	private HorizontalPanel panel_Password		   = new HorizontalPanel();		    // Contains user_Password & user_Password_Status
	private PasswordTextBox user_Password 		       = new PasswordTextBox(); 		// A User's password - Editable
	private Label 			user_Password_Status       = new Label("Status"); 					// Password check - Uneditable
	
	private HorizontalPanel panel_Password_Check   = new HorizontalPanel();			// Contains user_Password & user_Password_Status	
	private PasswordTextBox user_Password_Check    	   = new PasswordTextBox(); 		// A User's password check - Editable
	private Label 			user_Password_Check_Status = new Label("Check_Status"); 					// Password check - Uneditable
	
	private Button 			confirmButton 		   = new Button("Confirm changes"); // The Button to confirm changes made to a user object
	private User 			widgetUser 			   = new User();					// The Widget's User
	
	private FlexTable table = new FlexTable();
	
	
	public UserEditWidget() {
		
		// Load logged User object into local variable widgetUser
		widgetUser = (UC.getLoggedIn());
		
		// Construct the widget layout
		add(systemStatus);
		
		add(table);
		
		table.setWidget(0, 0, new Label("Username:"));
		table.setWidget(0, 1, user_Username);
		table.setWidget(1, 0, new Label("Firstname:"));
		table.setWidget(1, 1, user_Firstname);
		table.setWidget(2, 0, new Label("Surname:"));
		table.setWidget(2, 1, user_Surname);
		table.setWidget(3, 0, new Label("Email:"));
		table.setWidget(3, 1, user_Email);
		table.setWidget(4, 0, new Label("Mobile Phone Number:"));
		table.setWidget(4, 1, user_MobilePhoneNumber);
		table.setWidget(5, 0, new Label("Password:"));
		table.setWidget(5, 1, user_Password);
		table.setWidget(5, 2, user_Password_Status);
		table.setWidget(6, 0, new Label("Repeat Password:"));
		table.setWidget(6, 1, user_Password_Check);
		table.setWidget(6, 2, user_Password_Check_Status);
		table.setWidget(7, 0, confirmButton);
		
		// Fill TextBoxes and Labels with User/System Information 
		systemStatus.setText("Edit Your Account Page");
		if(UC.getLoggedIn()==null){
			user_Username.setText("TEST USERNAME");
			user_Firstname.setText("TEST FIRSTNAME");
			user_Surname.setText("TEST SURNAME");
			user_Email.setText("TEST EMAIL");
			user_MobilePhoneNumber.setText("TEST MOBILEPHONENUMBER");
			user_Password.setText("TEST PASSWORD");
			user_Password_Check.setText("TEST PASSWORD");
		}else{
			user_Username.setText("" + widgetUser.getUserName());
			user_Firstname.setText("" + widgetUser.getFirstName());
			user_Surname.setText("" + widgetUser.getSurName());
			user_Email.setText("" + widgetUser.getEmail());
			user_MobilePhoneNumber.setText("" + widgetUser.getMobilePhoneNumber());
		}
		//Password Check KeyUpHandlers
		user_Password.addKeyUpHandler(new KeyUpHandler() {
		    @Override
		    public void onKeyUp(KeyUpEvent event) {
		    	passwordCheck();
		    }
		});
		user_Password_Check.addKeyUpHandler(new KeyUpHandler() {
		    @Override
		    public void onKeyUp(KeyUpEvent event) {
		    	passwordCheck();
		    }
		});
		
		confirmButton.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				String username = user_Username.getText();
				String firstname = user_Firstname.getText();
				String surname = user_Surname.getText();
				String email = user_Email.getText();
				String mobilephonenumber = user_MobilePhoneNumber.getText();
				String password = user_Password.getText();
				
				User userx = new User(username,password,email,firstname,surname);
				userx.setMobilePhoneNumber(mobilephonenumber);
				updateUser(userx);
			}
		});
	}
	
	//PasswordCheck Function - Is called when KeyUp event is fired from user_Password or user_Password_Check
	private boolean passwordCheck(){
		final String password = user_Password.getText();
		final String passwordcheck = user_Password_Check.getText();
		Boolean checkOk = false;
		
		if(password == ""){
			user_Password_Status.setText("Password field cannot be empty");
		}else if(password != "" && !password.matches("^[A-Za-z]\\w{6,}[A-Za-z]$")){
			user_Password_Status.setText("Your password needs to be at least 6 chars long and has to start and end with a letter ");
		}else if(password.matches("^[A-Za-z]\\w{6,}[A-Za-z]$")){
			user_Password_Status.setText("Password field matches criteria");
		}
		if(passwordcheck == ""){
			user_Password_Check_Status.setText("Password check field cannot be empty");
		}else if(passwordcheck != "" && !passwordcheck.matches("^[A-Za-z]\\w{6,}[A-Za-z]$")){
			user_Password_Check_Status.setText("Your password needs to be at least 6 chars long and has to start and end with a letter ");
		}else if(passwordcheck.matches("^[A-Za-z]\\w{6,}[A-Za-z]$")){
			user_Password_Check_Status.setText("Password check field matches criteria");
		}
		if(password.matches("^[A-Za-z]\\w{6,}[A-Za-z]$") && passwordcheck.matches("^[A-Za-z]\\w{6,}[A-Za-z]$")){
			user_Password_Status.setText("Password field matches criteria but does not match with password check field");
			user_Password_Check_Status.setText("Password check field matches criteria but does not match with password field");
		}
		if(password.equals(passwordcheck) && password.matches("^[A-Za-z]\\w{6,}[A-Za-z]$") && passwordcheck.matches("^[A-Za-z]\\w{6,}[A-Za-z]$")){
			user_Password_Status.setText("Password field matches criteria and matches password check field");
			user_Password_Check_Status.setText("Password check field matches criteria and matches password field");
			checkOk = true;
		}else{
			checkOk = false;
		}
		return checkOk;
		
	}
	public void updateUser(User u){
		ServerServiceAsync myService = (ServerServiceAsync) GWT.create(ServerService.class);
		AsyncCallback<Void> callback = new AsyncCallback<Void>() {		
			@Override
			public void onFailure(Throwable caught) {
				systemStatus.setText("Error: Something went wrong, no User Data Updated.");
			}
			@Override
			public void onSuccess(Void result) {
				systemStatus.setText("User Data Updated.");
			}
		};
		myService.updateUser(u, callback);
	}
}