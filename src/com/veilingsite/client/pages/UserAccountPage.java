package com.veilingsite.client.pages;

import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.veilingsite.client.controllers.UC;
import com.veilingsite.client.controllers.UserChangeListener;
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
		
		final TabPanel myTabPanel = new TabPanel();
		myTabPanel.setAnimationEnabled(false);

		myTabPanel.addSelectionHandler(new SelectionHandler<Integer>() {
			@Override
			public void onSelection(SelectionEvent<Integer> event) {
				Panel p = (Panel) myTabPanel.getWidget(event.getSelectedItem());
				p.clear();
				RootPanel.get("containerLeft").clear();
				RootPanel.get("containerRight").clear();
				switch(event.getSelectedItem()) {
				default:
				case 0:
					refreshPage();
					break;
				case 1:
					p.add(new UserCreateAuctionPage());
					break;
				case 2:
					p.add(new UserEditAccountPage());
					break;
				}
			}
		});

		myTabPanel.add(new HorizontalPanel(), "Account Overview");
		myTabPanel.add(new HorizontalPanel(), "Create Auction");
		myTabPanel.add(new HorizontalPanel(), "Edit Account");
		
		myTabPanel.selectTab(0);

		//myTabPanel.getTabBar().setStyleName("menu");
		//myTabPanel.getDeckPanel().setStyleName("page");
		add(myTabPanel.getTabBar());
		add(myTabPanel.getDeckPanel());
		
		refreshPage();
	}
	
	private void refreshPage() {
		avc.setLimitUser(UC.getLoggedIn());
		containerRight.add(avc);
		avc.loadAuctions();
	}

}
