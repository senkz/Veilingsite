package com.veilingsite.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TabPanel;
import com.veilingsite.client.controllers.UC;
import com.veilingsite.client.listeners.UserChangeListener;
import com.veilingsite.client.pages.*;
import com.veilingsite.client.widgets.UserLoginWidget;

public class Entry implements EntryPoint {
	
	private UserLoginWidget lw = new UserLoginWidget();

	@Override
	public void onModuleLoad() {
		
		final TabPanel myTabPanel = new TabPanel();

		myTabPanel.setSize("960px", "100px");
		myTabPanel.setAnimationEnabled(false);
		
		final UserAccountPage uap = new UserAccountPage();

		myTabPanel.addSelectionHandler(new SelectionHandler<Integer>() {
			@Override
			public void onSelection(SelectionEvent<Integer> event) {
				Panel p = (Panel) myTabPanel.getWidget(event.getSelectedItem());
				p.clear();
				clearPages();
				switch(event.getSelectedItem()) {
				default:
				case 0:
					p.add(new HomePage());
					break;
				case 1:
					p.add(new AuctionPage());
					break;
				case 2:
					p.add(new FindAuctionPage());
					break;
				case 3:
					uap.menu.selectTab(0);
					p.add(uap);
					break;
				case 4:
					p.add(new AdminPage());
					break;
				case 5:
					p.add(new StatisticsPage());
				}
			}
		});

		myTabPanel.add(new HorizontalPanel(), "Home");
		myTabPanel.add(new HorizontalPanel(), "Auctions");
		myTabPanel.add(new HorizontalPanel(), "Find Auctions");
		myTabPanel.add(new HorizontalPanel(), "My Account");
		myTabPanel.add(new HorizontalPanel(), "Admin Auctions");
		myTabPanel.add(new HorizontalPanel(), "Statistics");
		
		myTabPanel.getTabBar().setTabEnabled(3, false);
		myTabPanel.getTabBar().setTabEnabled(4, false);
		myTabPanel.getTabBar().setTabEnabled(5, false);
		
		myTabPanel.selectTab(0);

		myTabPanel.getTabBar().setStyleName("menu");
		myTabPanel.getDeckPanel().setStyleName("page");
		RootPanel.get("login").add(lw);
		RootPanel.get("menubar").add(myTabPanel.getTabBar());
		//RootPanel.get("content").add(myTabPanel.getDeckPanel());
		
		UC.addUserChangeListener(new UserChangeListener() {
			
			@Override
			public void fireListener() {
				if(UC.getLoggedIn()==null) {
					myTabPanel.getTabBar().setTabEnabled(3, false);
					myTabPanel.getTabBar().setTabEnabled(4, false);
					myTabPanel.getTabBar().setTabEnabled(5, false);
					myTabPanel.selectTab(0);
				}else if(UC.getLoggedIn() != null && UC.getLoggedIn().getPermission() == 2){
					myTabPanel.getTabBar().setTabEnabled(3, true);
					myTabPanel.getTabBar().setTabEnabled(4, true);
					myTabPanel.getTabBar().setTabEnabled(5, true);
					myTabPanel.selectTab(3);
				}else{
					myTabPanel.getTabBar().setTabEnabled(3, true);
					myTabPanel.getTabBar().setTabEnabled(4, false);
					myTabPanel.getTabBar().setTabEnabled(5, false);
					myTabPanel.selectTab(3);
				}
			}
		});
	}
	
	private void clearPages() {
		RootPanel.get("containerLeft").clear();
		RootPanel.get("containerRight").clear();
		RootPanel.get("container34").clear();
		RootPanel.get("container14").clear();
		RootPanel.get("subMenu").clear();		
	}
}