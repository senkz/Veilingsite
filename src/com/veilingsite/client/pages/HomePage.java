package com.veilingsite.client.pages;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.veilingsite.client.widgets.LoginWidget;

public class HomePage extends VerticalPanel {
	
	private Label myLabel = new Label();

	private Label l_naam = new Label("Name: ");
	private Label l_password = new Label("Password: ");
	
	private TextBox tb_naam = new TextBox();
	private TextBox tb_password = new TextBox();
	
	private Button b = new Button("add user");
	
	private LoginWidget lw = new LoginWidget();
	
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
		
		add(l_naam);
		add(tb_naam);
		
		add(l_password);
		add(tb_password);
		add(b);
		
		add(lw);
	}

}
