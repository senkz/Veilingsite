package com.veilingsite.client.pages;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.veilingsite.client.controllers.UC;
import com.veilingsite.client.widgets.*;

public class HomePage extends VerticalPanel {
	
	private Label myLabel = new Label();
	
	private UserEditWidget uew = new UserEditWidget();
	private LoginWidget lw = new LoginWidget();
	private RegisterWidget rw = new RegisterWidget();
	
	public HomePage(){
		add(myLabel);
		
		add(uew);
		add(rw);
		add(lw);
	}

}
