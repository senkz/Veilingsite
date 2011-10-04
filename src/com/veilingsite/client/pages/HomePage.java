package com.veilingsite.client.pages;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.veilingsite.client.controllers.UserController;
import com.veilingsite.client.widgets.LoginWidget;
import com.veilingsite.client.widgets.RegisterWidget;

public class HomePage extends VerticalPanel {
	
	private Label myLabel = new Label();
	
	private LoginWidget lw = new LoginWidget();
	private RegisterWidget rw = new RegisterWidget();
	private UserController uc = null;
	
	public HomePage(UserController uc){
		
		this.uc = uc;
		add(myLabel);
		
		add(rw);
		
		add(lw);
	}

}
