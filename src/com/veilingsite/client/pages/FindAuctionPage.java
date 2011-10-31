package com.veilingsite.client.pages;

import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.veilingsite.client.widgets.AuctionCreateWidget;
import com.veilingsite.client.widgets.AuctionViewWidget;
import com.veilingsite.client.widgets.FindAuctionWidget;

public class FindAuctionPage extends VerticalPanel {
	private FindAuctionWidget fa = new FindAuctionWidget();
	private RootPanel containerLeft = RootPanel.get("containerLeft");
	private RootPanel containerRight = RootPanel.get("containerRight");
	private AuctionViewWidget avc = new AuctionViewWidget();
	
	public FindAuctionPage(){
		containerLeft.add(fa);
		containerRight.add(avc);
	}
}
