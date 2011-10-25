package com.veilingsite.client.widgets;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class HomePageWidget extends VerticalPanel {

	private Label welcome = new Label("Welcome to this site bladibla");
	
		
	public HomePageWidget() {
		
		//add class for styling
		this.addStyleName("widget");
		add(welcome);
		
	}
	
}