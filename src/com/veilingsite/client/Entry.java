package com.veilingsite.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;
import com.veilingsite.client.pages.HomePage;

public class Entry implements EntryPoint {
	
	@Override
	public void onModuleLoad() {
		RootPanel rp = RootPanel.get("main");
		rp.add(new HomePage());		
	}
	
}