package com.veilingsite.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TabPanel;
import com.veilingsite.client.pages.AuctionPage;
import com.veilingsite.client.pages.HomePage;

public class Entry implements EntryPoint {

	@Override
	public void onModuleLoad() {
		
		final TabPanel myTabPanel = new TabPanel();

		myTabPanel.setSize("960px", "100px");
		myTabPanel.setAnimationEnabled(true);

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
					p.add(new HomePage());
					break;
				case 1:
					p.add(new AuctionPage());
					break;
				}
			}
		});

		myTabPanel.add(new HorizontalPanel(), "Home");
		myTabPanel.add(new HorizontalPanel(), "Auctions");
		//myTabPanel.add(new HorizontalPanel(), "Bestemmingen");
		//myTabPanel.add(new HorizontalPanel(), "Mijn Boekingen");
		//myTabPanel.add(new HorizontalPanel(), "Contact");
		
		myTabPanel.selectTab(0);

		//TabBar myTabBar = myTabPanel.getTabBar();
		//myTabBar.setTabEnabled(3, false);

		myTabPanel.getTabBar().setStyleName("menu");
		myTabPanel.getDeckPanel().setStyleName("page");
		//RootPanel.get("login").add(new InloggenPage(serviceImpl));
		RootPanel.get("menubar").add(myTabPanel.getTabBar());
		RootPanel.get("content").add(myTabPanel.getDeckPanel());
	}
	
}