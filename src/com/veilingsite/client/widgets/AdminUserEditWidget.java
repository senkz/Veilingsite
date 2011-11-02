package com.veilingsite.client.widgets;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.veilingsite.client.controllers.UC;
import com.veilingsite.shared.ServerService;
import com.veilingsite.shared.ServerServiceAsync;
import com.veilingsite.shared.domain.User;

public class AdminUserEditWidget extends VerticalPanel {
	//Regular Expressions used by the widget for email and password checks
	private String			regExpEmail				   	  = new String("^[a-z0-9._%-]+@[a-z0-9.-]+[.][a-z.]{2,4}$");
	private String			regExpPassword			   	  = new String("^[A-Za-z]\\w{4,}[A-Za-z]$");
	private String			regExpMobilePhone		   	  = new String("^06+[0-9]{8}$");
	
	private Label 			systemStatus 		   	   	  = new Label();					// The Status of the editing process will be displayed in this label
	private Timer 			systemStatusTimer;
	private TextBox			user_Username 		  	   	  = new TextBox(); 				// Contains username - editable
	private Button			findButton		   	  	   	  = new Button("Find User");		// Button, onclick -> find user specified in user_Username Textbox and put data into Textfields. 
	private Label 			user_Active_Status 		   	  = new Label("User Status: -"); 	// Password check - Uneditable
	private TextBox			user_Firstname		   = new TextBox(); 				// A User's firstname - Editable
	private Image 			user_Firstname_Status = new Image(); 					// Password check - Uneditable
	private TextBox			user_Surname		   = new TextBox(); 				// A User's surname - Editable
	private Image 			user_Surname_Status = new Image(); 					// Password check - Uneditable
	private TextBox 		user_Email			       	  = new TextBox(); 				// A User's Email - Editable
	private Image 			user_Email_Status          	  = new Image(); 					// Email check - Uneditable
	private TextBox 		user_MobilePhoneNumber     	  = new TextBox(); 				// A User's mobile phone number - Editable
	private Image 			user_MobilePhoneNumber_Status = new Image(); 					// Email check - Uneditable
	
	private HorizontalPanel panel_Password	  	       	  = new HorizontalPanel();		    // Contains user_Password & user_Password_Status
	private PasswordTextBox user_Password 		       	  = new PasswordTextBox(); 		// A User's password - Editable
	private Image 			user_Password_Status       	  = new Image(); 					// Password check - Uneditable
	
	private HorizontalPanel panel_Password_Check   	   	  = new HorizontalPanel();			// Contains user_Password & user_Password_Status	
	private PasswordTextBox user_Password_Check    	   	  = new PasswordTextBox(); 		// A User's password check - Editable
	private Image 			user_Password_Check_Status 	  = new Image(); 					// Password check - Uneditable
	
	private Panel			password_Checks_StatusPanel	  = new HorizontalPanel();			// Panel that contains final result of password check
	private Label 			password_Checks_StatusLab  	  = new Label("Passwords match: ");// Password check - Uneditable
	private Image 			password_Checks_StatusImg  	  = new Image(); 					// Password check - Uneditable
	
	private Panel 			buttonPanel			   	   	  = new HorizontalPanel();			// Panel that contains the form's buttons
	private Button 			confirmButton 		   	   	  = new Button("Confirm changes"); // The Button to confirm changes made to the found
	private Button 			blockButton 		   	   	  = new Button("Block user"); 		// The Button to block the found user
	private Button 			deleteButton 		   	   	  = new Button("Delete user"); 	// The Button to delete the found user
	private Button 			makeAdminButton 		   	   	  = new Button("Delete user"); 	// The Button to delete the found user
	
	private User 			widgetUser;												// The Widget's User
	private User 			findResult;												// The Widget's User
	
	private FlexTable table = new FlexTable();
	
	public AdminUserEditWidget() {
		systemStatusTimer = new Timer() {
		      public void run() {
					systemStatus.setVisible(false);
		      }
		 };
		systemStatus.setVisible(false);
		Label title = new Label();
		title.setText("Edit your account");
		title.setStyleName("heading");
		add(title);
		
		//add class for styling
		this.addStyleName("adminWidget");
		this.addStyleName("widget");
		
		// Load logged User object into local variable widgetUser
		widgetUser = (UC.getLoggedIn());
		
		// Construct the widget layout
		add(table);
		
		user_Firstname_Status.setUrl("./images/cross.png");
		user_Surname_Status.setUrl("./images/cross.png");
		user_Email_Status.setUrl("./images/cross.png");
		user_MobilePhoneNumber_Status.setUrl("./images/cross.png");
		user_Password_Status.setUrl("./images/cross.png");
		user_Password_Check_Status.setUrl("./images/cross.png");
		password_Checks_StatusImg.setUrl("./images/cross.png");
		
		password_Checks_StatusPanel.add(password_Checks_StatusLab);
		password_Checks_StatusPanel.add(password_Checks_StatusImg);
		
		buttonPanel.add(confirmButton);
		buttonPanel.add(blockButton);
		buttonPanel.add(deleteButton);
		buttonPanel.add(makeAdminButton);
		
		table.setWidget(0, 0, new Label("Username:"));
		table.setWidget(0, 1, user_Username);
		table.setWidget(0, 2, findButton);
		table.setWidget(1, 1, user_Active_Status);
		table.setWidget(2, 0, new Label("Firstname:"));
		table.setWidget(2, 1, user_Firstname);
		table.setWidget(2, 2, user_Firstname_Status);
		table.setWidget(3, 0, new Label("Surname:"));
		table.setWidget(3, 1, user_Surname);
		table.setWidget(3, 2, user_Surname_Status);
		table.setWidget(4, 0, new Label("Email:"));
		table.setWidget(4, 1, user_Email);
		table.setWidget(4, 2, user_Email_Status);
		table.setWidget(5, 0, new Label("Mobile Phone Number:"));
		table.setWidget(5, 1, user_MobilePhoneNumber);
		table.setWidget(5, 2, user_MobilePhoneNumber_Status);
		table.setWidget(6, 0, new Label("Password*:"));
		table.setWidget(6, 1, user_Password);
		table.setWidget(6, 2, user_Password_Status);
		table.setWidget(7, 0, new Label("Repeat Password*:"));
		table.setWidget(7, 1, user_Password_Check);
		table.setWidget(7, 2, user_Password_Check_Status);
		table.setWidget(8, 1, password_Checks_StatusPanel);
		table.setWidget(9, 0, buttonPanel);
		
		add(new Label("* The given password needs to be at least 6 characters long and has to start and end with a letter."));
		add(systemStatus);
		
		//Form Check KeyUpHandlers
		user_Username.addBlurHandler(new BlurHandler() {
		    @Override
		    public void onBlur(BlurEvent event) {
		    	namesCheck();
		    }
		});
		user_Firstname.addKeyUpHandler(new KeyUpHandler() {
		    @Override
		    public void onKeyUp(KeyUpEvent event) {
		    	namesCheck();
		    }
		});
		user_Surname.addKeyUpHandler(new KeyUpHandler() {
		    @Override
		    public void onKeyUp(KeyUpEvent event) {
		    	namesCheck();
		    }
		});
		user_Email.addKeyUpHandler(new KeyUpHandler() {
		    @Override
		    public void onKeyUp(KeyUpEvent event) {
		    	emailCheck();
		    }
		});
		user_MobilePhoneNumber.addKeyUpHandler(new KeyUpHandler() {
		    @Override
		    public void onKeyUp(KeyUpEvent event) {
		    	phoneCheck();
		    }
		});
		
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
		
		findButton.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				String username = user_Username.getText();
				try{
					findUserData(username);
				}catch(NullPointerException e){
					systemStatus.setText("Error: Something went wrong, no User Data found.");
					systemStatus.setStyleName("error");
					systemStatus.setVisible(true);
					systemStatusTimer.schedule(3000);
				}
				namesCheck();
				emailCheck();
				phoneCheck();
				passwordCheck();
			}
		});
		
		confirmButton.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				if(passwordCheck() == true && emailCheck() == true){
					String username = user_Username.getText();
					String firstname = user_Firstname.getText();
					String surname = user_Surname.getText();
					String email = user_Email.getText();
					String mobilephonenumber = user_MobilePhoneNumber.getText();
					String password = user_Password.getText();
					
					User userx = new User(username,password,email,firstname,surname);
					userx.setMobilePhoneNumber(mobilephonenumber);
					updateUser(userx);
				}else if(passwordCheck() == false && emailCheck() == true){
					systemStatus.setText("Passwordcheck didn't pass, User not edited.");
					systemStatus.setStyleName("error");
					systemStatus.setVisible(true);
					systemStatusTimer.schedule(3000);
				}else if(passwordCheck() == true && emailCheck() == false){
					systemStatus.setText("Emailcheck didn't pass, User not edited.");
					systemStatus.setStyleName("error");
					systemStatus.setVisible(true);
					systemStatusTimer.schedule(3000);
				}else{
					systemStatus.setText("Password and Emailcheck didn't pass, User not edited.");
					systemStatus.setStyleName("error");
					systemStatus.setVisible(true);
					systemStatusTimer.schedule(3000);
				}
			}
		});
		
		blockButton.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				if(findResult != null){
					if(findResult.getPermission() != 2){
						String username = user_Username.getText();
						blockUser(username);
					}else{
						systemStatus.setText("Found user is a admin and can not be blocked");
						systemStatus.setStyleName("status");
						systemStatus.setVisible(true);
						systemStatusTimer.schedule(3000);
					}
				}else{
					systemStatus.setText("ERROR: FindResult not set.");
					systemStatus.setStyleName("error");
					systemStatus.setVisible(true);
					systemStatusTimer.schedule(3000);
				}				
			}
		});
		
		deleteButton.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				String username = user_Username.getText();
				deleteUser(username);
			}
		});
		
		makeAdminButton.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				if(findResult != null){
						String username = user_Username.getText();
						makeUserAdmin(username);
				}else{
					systemStatus.setText("findResult not set.");
					systemStatus.setStyleName("error");
					systemStatus.setVisible(true);
					systemStatusTimer.schedule(3000);
				}				
			}
		});
	}
	private boolean namesCheck(){
		Boolean checkFirstName = false;
		Boolean checkSurName = false;
		Boolean checkOk = false;
		String Firstname = user_Firstname.getText();
		String Username = user_Username.getText();
		String Surname = user_Surname.getText();
		
		//Usernamecheck
		if(!Username.equals("")){
			try{
				findUserData(Username);
			}catch(NullPointerException e){
				systemStatus.setText("Error: Something went wrong, no User Data found.");
				systemStatus.setStyleName("error");
				systemStatus.setVisible(true);
				systemStatusTimer.schedule(3000);
			}
    	}else{
    		systemStatus.setText("Error: Fill in Username to find a user.");
    		systemStatus.setStyleName("status");
			systemStatus.setVisible(true);
			systemStatusTimer.schedule(3000);
    	}
		//Firstnamecheck
    	if(Firstname.equals("")){
    		user_Firstname_Status.setUrl("./images/cross.png");
    		checkFirstName = false;
    	}else{
    		user_Firstname_Status.setUrl("./images/tick.png");
    		checkFirstName = true;
    	}
		//Surnamecheck
    	if(Surname.equals("")){
    		user_Surname_Status.setUrl("./images/cross.png");
    		checkSurName = false;
    	}else{
    		user_Surname_Status.setUrl("./images/tick.png");
    		checkSurName = true;
    	}
    	
		if(checkFirstName && checkSurName){
			checkOk = true;
		}else{
			checkOk = false;
		}
		return checkOk;
		
	}
	private boolean emailCheck(){
		final String email = user_Email.getText().toLowerCase();	//needs to be transformed to lowercase to validate correctly using the regular expression
		Boolean checkOk = false;
		
		if(email.equals("")){											//email can not be empty
			user_Email_Status.setUrl("./images/cross.png");
		}else if(!email.equals("") && !email.matches(regExpEmail)){		//if email is filled but does not match regular expression - check FAIL
			user_Email_Status.setUrl("./images/cross.png");
		}else if(email.matches(regExpEmail)){						//if email is filled and matches regular expression - check is passed and change cross to tick image
			user_Email_Status.setUrl("./images/tick.png");
			checkOk = true;
		}
		else{
			checkOk = false;
		}
		return checkOk;
		
	}
	private boolean phoneCheck(){
		final String phone = user_MobilePhoneNumber.getText();
		Boolean checkOk = false;
		
		if(phone.equals("")){												//phone can not be empty
			user_MobilePhoneNumber_Status.setUrl("./images/cross.png");
		}else if(!phone.equals("") && !phone.matches(regExpMobilePhone)){		//if phone is filled but does not match regular expression - check FAIL
			user_MobilePhoneNumber_Status.setUrl("./images/cross.png");
		}else if(phone.matches(regExpMobilePhone)){						//if phone is filled and matches regular expression - check is passed and change cross to tick image
			user_MobilePhoneNumber_Status.setUrl("./images/tick.png");
			checkOk = true;
		}
		else{
			checkOk = false;
		}
		return checkOk;
		
	}
	private boolean passwordCheck(){
		final String password = user_Password.getText();
		final String passwordcheck = user_Password_Check.getText();
		Boolean checkOk = false;
		
		if(password.equals("")){
			//user_Password_Status.setText("Password field cannot be empty");
			user_Password_Status.setUrl("./images/cross.png");
			password_Checks_StatusImg.setUrl("./images/cross.png");
		}else if(!password.equals("") && !password.matches("^[A-Za-z]\\w{6,}[A-Za-z]$")){
			//user_Password_Status.setText("Your password needs to be at least 6 chars long and has to start and end with a letter ");
			user_Password_Status.setUrl("./images/cross.png");
			password_Checks_StatusImg.setUrl("./images/cross.png");
		}else if(password.matches("^[A-Za-z]\\w{6,}[A-Za-z]$")){
			//user_Password_Status.setText("Password field matches criteria");
			user_Password_Status.setUrl("./images/tick.png");
			password_Checks_StatusImg.setUrl("./images/cross.png");
		}
		if(passwordcheck.equals("")){
			//user_Password_Check_Status.setText("Password check field cannot be empty");
			user_Password_Check_Status.setUrl("./images/cross.png");
			password_Checks_StatusImg.setUrl("./images/cross.png");
		}else if(!passwordcheck.equals("") && !passwordcheck.matches("^[A-Za-z]\\w{6,}[A-Za-z]$")){
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
	
	public void updateUser(User u){
		ServerServiceAsync myService = (ServerServiceAsync) GWT.create(ServerService.class);
		AsyncCallback<Void> callback = new AsyncCallback<Void>() {		
			@Override
			public void onFailure(Throwable caught) {
				systemStatus.setText("Error: Something went wrong, no User Data Updated.");
				systemStatus.setStyleName("error");
				systemStatus.setVisible(true);
				systemStatusTimer.schedule(3000);
			}
			@Override
			public void onSuccess(Void result) {
				systemStatus.setText("User Data Updated.");
				systemStatus.setStyleName("succesfull");
				systemStatus.setVisible(true);
				systemStatusTimer.schedule(3000);
			}
		};
		myService.updateUser(u, callback);
	}
	public void removeUser(User u){
		ServerServiceAsync myService = (ServerServiceAsync) GWT.create(ServerService.class);
		AsyncCallback<Void> callback = new AsyncCallback<Void>() {		
			@Override
			public void onFailure(Throwable caught) {
				systemStatus.setText("Error: Something went wrong, no User removed");
				systemStatus.setStyleName("error");
				systemStatus.setVisible(true);
				systemStatusTimer.schedule(3000);
			}
			@Override
			public void onSuccess(Void result) {
				systemStatus.setText("User removed");
				systemStatus.setStyleName("succesfull");
				systemStatus.setVisible(true);
				systemStatusTimer.schedule(3000);
			}
		};
		myService.removeUser(u, callback);
	}
	public void findUserData(String u){
		ServerServiceAsync myService = (ServerServiceAsync) GWT.create(ServerService.class);
		AsyncCallback<User> callback = new AsyncCallback<User>() {		
			@Override
			public void onFailure(Throwable caught) {
				systemStatus.setText("no user found!");
				systemStatus.setStyleName("error");
				systemStatus.setVisible(true);
				systemStatusTimer.schedule(3000);
			}
			@Override
			public void onSuccess(User result) {
				if(result != null){
					findResult = result;
					String userStatus;
					if(result.getPermission() == 0){
						userStatus = "Blocked";
						blockButton.setText("Unblock user");
					}else{
						String rights = "";
						if(result.getPermission() == 2){
							rights = "Admin";
						}else{
							rights = "User";
						}
						userStatus = "Active " + rights;
						blockButton.setText("Block user");
					}
					if(result.getPermission() == 2){
						makeAdminButton.setText("Make User");
					}else{
						makeAdminButton.setText("Make Admin");
					}
					systemStatus.setText("User Data fetched: " + result.getUserName());				
					user_Username.setText(result.getUserName());
					user_Active_Status.setText("User Status: " + userStatus);
					user_Firstname.setText(result.getFirstName());
					user_Surname.setText(result.getSurName());
					user_Email.setText(result.getEmail());
					user_MobilePhoneNumber.setText(result.getMobilePhoneNumber());
					user_Password.setText(result.getPassword());
					user_Password_Check.setText(result.getPassword());
				}else{
					systemStatus.setText("No user found");
					systemStatus.setStyleName("error");
					systemStatus.setVisible(true);
					systemStatusTimer.schedule(3000);
				}
			namesCheck();
			emailCheck();
			phoneCheck();
			passwordCheck();
			}
		};
		myService.getUser(u, callback);
	}
	public void blockUser(String u){
		ServerServiceAsync myService = (ServerServiceAsync) GWT.create(ServerService.class);
		AsyncCallback<User> callback = new AsyncCallback<User>() {		
			@Override
			public void onFailure(Throwable caught) {
				systemStatus.setText("No user found");
				systemStatus.setStyleName("error");
				systemStatus.setVisible(true);
				systemStatusTimer.schedule(3000);
			}
			@Override
			public void onSuccess(User result) {
				User tmpResultUser = result;
				if(result.getPermission() != 0){
					tmpResultUser.setPermission(0);
				}else if(result.getPermission() != 2){
					tmpResultUser.setPermission(1);
				}
				updateUser(tmpResultUser);
				findUserData(tmpResultUser.getUserName());
			}
		};
		myService.getUser(u, callback);
	}
	public void makeUserAdmin(String u){
		ServerServiceAsync myService = (ServerServiceAsync) GWT.create(ServerService.class);
		AsyncCallback<User> callback = new AsyncCallback<User>() {		
			@Override
			public void onFailure(Throwable caught) {
				systemStatus.setText("No user found");
				systemStatus.setStyleName("error");
				systemStatus.setVisible(true);
				systemStatusTimer.schedule(3000);
			}
			@Override
			public void onSuccess(User result) {
				User tmpResultUser = result;
				if(result.getPermission() != 2){
					tmpResultUser.setPermission(2);
				}else if(result.getPermission() == 2){
					tmpResultUser.setPermission(1);
				}
				updateUser(tmpResultUser);
				findUserData(tmpResultUser.getUserName());
			}
		};
		myService.getUser(u, callback);
	}
	public void deleteUser(String u){
		ServerServiceAsync myService = (ServerServiceAsync) GWT.create(ServerService.class);
		AsyncCallback<User> callback = new AsyncCallback<User>() {		
			@Override
			public void onFailure(Throwable caught) {
				systemStatus.setText("No user found");
				systemStatus.setStyleName("error");
				systemStatus.setVisible(true);
				systemStatusTimer.schedule(3000);
			}
			@Override
			public void onSuccess(User result) {
				User tmpResultUser = result;
				removeUser(tmpResultUser);
			}
		};
		myService.getUser(u, callback);
	}
}