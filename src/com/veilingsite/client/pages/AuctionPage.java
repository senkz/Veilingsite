package com.veilingsite.client.pages;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.veilingsite.client.exceptions.UserException;
import com.veilingsite.client.widgets.LoginWidget;
import com.veilingsite.client.widgets.MyAuctionOverviewWidget;

public class AuctionPage extends VerticalPanel {
	private MyAuctionOverviewWidget myauct;
	
	public AuctionPage(){
		try {
			myauct = new MyAuctionOverviewWidget();
			add(myauct);
		} catch(UserException ue) {
			add(new Label(ue.getMessage()));
		}
	}

}
