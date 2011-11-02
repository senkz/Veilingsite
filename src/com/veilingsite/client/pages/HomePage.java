package com.veilingsite.client.pages;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.veilingsite.client.widgets.LabelWidget;
import com.veilingsite.client.widgets.UserLoginWidget;
import com.veilingsite.client.widgets.UserRegisterWidget;

public class HomePage extends VerticalPanel {
	
	private LabelWidget hp = new LabelWidget("Welcome in the Auction House, this is the place to find Auctions and bid on them!");
	private UserRegisterWidget rw = new UserRegisterWidget();
	private RootPanel containerLeft = RootPanel.get("containerLeft");
	private RootPanel containerRight = RootPanel.get("containerRight");
	
	public HomePage(){
		containerLeft.add(hp);
		containerRight.add(rw);
	}

}
