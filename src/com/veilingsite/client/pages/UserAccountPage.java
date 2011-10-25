package com.veilingsite.client.pages;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.veilingsite.client.controllers.UC;
import com.veilingsite.client.exceptions.UserException;
import com.veilingsite.client.widgets.AdminUserEditWidget;
import com.veilingsite.client.widgets.AuctionViewWidget;
import com.veilingsite.client.widgets.UserEditWidget;
import com.veilingsite.client.widgets.UserLoginWidget;
import com.veilingsite.client.widgets.AuctionCreateWidget;
import com.veilingsite.client.widgets.UserProductWidget;

public class UserAccountPage extends VerticalPanel {
	private UserEditWidget uew = new UserEditWidget();
	private UserProductWidget upw = new UserProductWidget();
	private AuctionViewWidget avc = new AuctionViewWidget();
	private RootPanel containerLeft = RootPanel.get("containerLeft");
	private RootPanel containerRight = RootPanel.get("containerRight");
	
	public UserAccountPage(){
		containerLeft.add(uew);
		avc.setLimitUser(UC.getLoggedIn());
		containerRight.add(avc);
		avc.loadAuctions();
	}

}
