package com.veilingsite.client.pages;

import java.util.ArrayList;

import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.veilingsite.client.controllers.UC;
import com.veilingsite.client.listeners.PageChangeListener;
import com.veilingsite.client.widgets.AuctionViewWidget;
import com.veilingsite.client.widgets.FindAuctionWidget;
import com.veilingsite.shared.domain.Auction;

public class FindAuctionPage extends VerticalPanel {
	private FindAuctionWidget fa = new FindAuctionWidget();
	private RootPanel containerLeft = RootPanel.get("containerLeft");
	private RootPanel containerRight = RootPanel.get("containerRight");
	private AuctionViewWidget avc = new AuctionViewWidget();
	private ArrayList<AuctionViewPage> avw = new ArrayList<AuctionViewPage>();
	private ArrayList<Auction> results = new ArrayList<Auction>();
	
	public FindAuctionPage(){
		containerLeft.add(fa);	
		containerRight.add(avc);

		fa.addPageChangeListener(new PageChangeListener<ArrayList<Auction>>() {
			@Override
			public void fireListener(ArrayList<Auction> a) {
				results = a;
				avc.showList(a);
				clearPage();
				refreshPage();
			}
		});
	}
	private void refreshPage() {
		avc.setLimitUser(UC.getLoggedIn());
		avc.showList(results);
		containerRight.add(avc);
	}
	
	private void clearPage() {
		containerRight.clear();
		for(AuctionViewPage a: avw) {
			a.close();
		}
	}
	
}
