package com.veilingsite.client.pages;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.veilingsite.client.widgets.LoginWidget;
import com.veilingsite.client.widgets.RegisterWidget;

public class HomePage extends VerticalPanel {
	
	private Label myLabel = new Label();
	
	private LoginWidget lw = new LoginWidget();
	private RegisterWidget rw = new RegisterWidget();
	
	public HomePage(){
		
		/**
		 *  needs to be moved else where
		 
		b.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				uc.addUser(new User(tb_naam.getText(), tb_password.getText()));
			}
		});
		*/
		
		add(myLabel);
		
		add(rw);
		
		add(lw);
	}

}
