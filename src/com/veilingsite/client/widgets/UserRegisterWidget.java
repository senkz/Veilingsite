package com.veilingsite.client.widgets;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.veilingsite.client.controllers.UC;
import com.veilingsite.shared.ServerService;
import com.veilingsite.shared.ServerServiceAsync;
import com.veilingsite.shared.domain.User;

public class UserRegisterWidget extends VerticalPanel {
	
	private Label 			systemStatus 		   = new Label();					// The Status of the editing process will be displayed in this label
	private TextBox			user_Username 		   = new TextBox(); 				// Contains username - editable
	private TextBox			user_Firstname		   = new TextBox(); 				// A User's firstname - Editable
	private TextBox			user_Surname		   = new TextBox(); 				// A User's surname - Editable
	private TextBox 		user_Email			   = new TextBox(); 				// A User's Email - Editable
	private TextBox 		user_MobilePhoneNumber = new TextBox(); 				// A User's mobile phone number - Editable
	
	private HorizontalPanel panel_Password	  	       = new HorizontalPanel();		    // Contains user_Password & user_Password_Status
	private PasswordTextBox user_Password 		       = new PasswordTextBox(); 		// A User's password - Editable
	private Image 			user_Password_Status       = new Image(); 					// Password check - Uneditable
	
	private HorizontalPanel panel_Password_Check   	   = new HorizontalPanel();			// Contains user_Password & user_Password_Status	
	private PasswordTextBox user_Password_Check    	   = new PasswordTextBox(); 		// A User's password check - Editable
	private Image 			user_Password_Check_Status = new Image(); 					// Password check - Uneditable
	
	private Panel			password_Checks_StatusPanel= new HorizontalPanel();			// Panel that contains final result of password check
	private Label 			password_Checks_StatusLab  = new Label("Passwords match: ");// Password check - Uneditable
	private Image 			password_Checks_StatusImg  = new Image(); 					// Password check - Uneditable
	
	private Button 			confirmButton 		   = new Button("Register"); // The Button to confirm changes made to a user object
	private User 			widgetUser 			   = new User();					// The Widget's User
	
	private FlexTable table = new FlexTable();
	
	
	public UserRegisterWidget() {
		//add class for styling
		this.addStyleName("adminWidget");
		this.addStyleName("widget");
		
		// Load logged User object into local variable widgetUser
		widgetUser = (UC.getLoggedIn());
		
		// Construct the widget layout
		add(systemStatus);
		add(table);
		user_Password_Status.setUrl("./images/cross.png");
		user_Password_Check_Status.setUrl("./images/cross.png");
		password_Checks_StatusImg.setUrl("./images/cross.png");
		
		password_Checks_StatusPanel.add(password_Checks_StatusLab);
		password_Checks_StatusPanel.add(password_Checks_StatusImg);		
		
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
		table.setWidget(5, 0, new Label("Password*:"));
		table.setWidget(5, 1, user_Password);
		table.setWidget(5, 2, user_Password_Status);
		table.setWidget(6, 0, new Label("Repeat Password*:"));
		table.setWidget(6, 1, user_Password_Check);
		table.setWidget(6, 2, user_Password_Check_Status);
		table.setWidget(7, 1, password_Checks_StatusPanel);
		table.setWidget(8, 0, confirmButton);
		
		add(new Label("* The given password needs to be at least 6 characters long and has to start and end with a letter."));
		
		// Fill TextBoxes and Labels with User/System Information 
		systemStatus.setText("Register User");
		
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
				if(passwordCheck() == true){
					String username = user_Username.getText();
					String firstname = user_Firstname.getText();
					String surname = user_Surname.getText();
					String email = user_Email.getText();
					String mobilephonenumber = user_MobilePhoneNumber.getText();
					String password = user_Password.getText();
					
					User userx = new User(username,password,email,firstname,surname);
					userx.setMobilePhoneNumber(mobilephonenumber);
					addUser(userx);
				}else{
					systemStatus.setText("Passwordcheck didn't pass, User not created.");
				}
			}
		});
		
	}
	
	//PasswordCheck Function - Is called when KeyUp event is fired from user_Password or user_Password_Check
	private boolean passwordCheck(){
		final String password = user_Password.getText();
		final String passwordcheck = user_Password_Check.getText();
		Boolean checkOk = false;
		
		if(password == ""){
			//user_Password_Status.setText("Password field cannot be empty");
			user_Password_Status.setUrl("./images/cross.png");
			password_Checks_StatusImg.setUrl("./images/cross.png");
		}else if(password != "" && !password.matches("^[A-Za-z]\\w{6,}[A-Za-z]$")){
			//user_Password_Status.setText("Your password needs to be at least 6 chars long and has to start and end with a letter ");
			user_Password_Status.setUrl("./images/cross.png");
			password_Checks_StatusImg.setUrl("./images/cross.png");
		}else if(password.matches("^[A-Za-z]\\w{6,}[A-Za-z]$")){
			//user_Password_Status.setText("Password field matches criteria");
			user_Password_Status.setUrl("./images/tick.png");
			password_Checks_StatusImg.setUrl("./images/cross.png");
		}
		if(passwordcheck == ""){
			//user_Password_Check_Status.setText("Password check field cannot be empty");
			user_Password_Check_Status.setUrl("./images/cross.png");
			password_Checks_StatusImg.setUrl("./images/cross.png");
		}else if(passwordcheck != "" && !passwordcheck.matches("^[A-Za-z]\\w{6,}[A-Za-z]$")){
			//user_Password_Check_Status.setText("Your password needs to be at least 6 chars long and has to start and end with a letter ");
			user_Password_Check_Status.setUrl("./images/cross.png");
			password_Checks_StatusImg.setUrl("./images/cross.png");
		}else if(passwordcheck.matches("^[A-Za-z]\\w{6,}[A-Za-z]$")){
			//user_Password_Check_Status.setText("Password check field matches criteria");
			user_Password_Check_Status.setUrl("./images/tick.png");
			password_Checks_StatusImg.setUrl("./images/cross.png");
		}
		if(password.matches("^[A-Za-z]\\w{6,}[A-Za-z]$") && passwordcheck.matches("^[A-Za-z]\\w{6,}[A-Za-z]$")){
			//user_Password_Status.setText("Password field matches criteria but does not match with password check field");
			//user_Password_Check_Status.setText("Password check field matches criteria but does not match with password field");
			user_Password_Status.setUrl("./images/tick.png");
			user_Password_Check_Status.setUrl("./images/tick.png");
			password_Checks_StatusImg.setUrl("./images/cross.png");
		}
		if(password.equals(passwordcheck) && password.matches("^[A-Za-z]\\w{6,}[A-Za-z]$") && passwordcheck.matches("^[A-Za-z]\\w{6,}[A-Za-z]$")){
			//user_Password_Status.setText("Password field matches criteria and matches password check field");
			//user_Password_Check_Status.setText("Password check field matches criteria and matches password field");
			user_Password_Status.setUrl("./images/tick.png");
			user_Password_Check_Status.setUrl("./images/tick.png");
			password_Checks_StatusImg.setUrl("./images/tick.png");
			checkOk = true;
		}else{
			checkOk = false;
		}
		return checkOk;
		
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