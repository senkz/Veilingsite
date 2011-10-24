package com.veilingsite.client.pages;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.veilingsite.client.exceptions.UserException;
import com.veilingsite.client.widgets.AuctionViewWidget;
import com.veilingsite.client.widgets.UserLoginWidget;
import com.veilingsite.client.widgets.AuctionCreateWidget;

public class AuctionPage extends VerticalPanel {
	private AuctionCreateWidget myauct;
	private AuctionViewWidget avc = new AuctionViewWidget();
	private RootPanel containerLeft = RootPanel.get("containerLeft");
	private RootPanel containerRight = RootPanel.get("containerRight");
	
	public AuctionPage(){
		try {
			myauct = new AuctionCreateWidget();
			containerLeft.add(myauct);
		} catch(UserException ue) {
			add(new Label(ue.getMessage()));
		}
		
		containerRight.add(avc);
		avc.loadAuctions();
	}

}
