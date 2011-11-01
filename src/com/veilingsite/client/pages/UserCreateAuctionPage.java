package com.veilingsite.client.pages;

import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.veilingsite.client.exceptions.UserException;
import com.veilingsite.client.widgets.AuctionCreateWidget;

public class UserCreateAuctionPage extends VerticalPanel {

	private RootPanel containerLeft = RootPanel.get("containerLeft");
	
	public UserCreateAuctionPage() {
		try {
			containerLeft.add(new AuctionCreateWidget());
		} catch (UserException e) {
			e.printStackTrace();
		}
	}
}
