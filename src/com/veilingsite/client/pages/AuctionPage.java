package com.veilingsite.client.pages;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.veilingsite.client.exceptions.UserException;
import com.veilingsite.client.widgets.UserLoginWidget;
import com.veilingsite.client.widgets.AuctionCreateWidget;

public class AuctionPage extends VerticalPanel {
	private AuctionCreateWidget myauct;
	
	public AuctionPage(){
		try {
			myauct = new AuctionCreateWidget();
			add(myauct);
		} catch(UserException ue) {
			add(new Label(ue.getMessage()));
		}
	}

}
