package com.veilingsite.client.pages;

import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.sun.org.apache.bcel.internal.generic.NEW;
import com.veilingsite.client.controllers.UC;
import com.veilingsite.client.widgets.AuctionBidWidget;
import com.veilingsite.shared.domain.Auction;

public class AuctionViewPage extends VerticalPanel{
	
	private RootPanel containerLeft = RootPanel.get("container34");
	private RootPanel containerRight = RootPanel.get("container14");
	private VerticalPanel page = new VerticalPanel();
	private AuctionBidWidget bidWidget;
	private FlexTable ft = new FlexTable();
	private Label title = new Label();
	private Auction auction;
	
	AuctionViewPage(Auction a) {
		auction = a;
		bidWidget = new AuctionBidWidget(a);
		
		page.setStyleName("page");
		containerRight.add(bidWidget);		
		
		loadPage();
		open();
	}
	
	private void loadPage() {
		title.setText(auction.getTitle());
		title.setStyleName("heading");

		Label owner = new Label(auction.getOwner().getUserName());
		Label category = new Label(auction.getCategory().getTitle());
		Label startDate = new Label(auction.getStartDate().toString());
		Label closeDate = new Label(auction.getCloseDate().toString());
		Label startAmount = new Label(auction.getStartAmount().toString());
		
		ft.setWidget(0, 0, new Label("Owner:"));			ft.setWidget(0, 1, owner);		
		ft.setWidget(1, 0, new Label("Category:"));			ft.setWidget(1, 1, category);
		ft.setWidget(2, 0, new Label("Start date:"));		ft.setWidget(2, 1, startDate);		
		ft.setWidget(3, 0, new Label("Close date:"));		ft.setWidget(3, 1, closeDate);
		ft.setWidget(3, 0, new Label("Start Amount:"));		ft.setWidget(3, 1, startAmount);
		
		page.add(title);
		page.add(new Label(auction.getDescription()));
		page.add(ft);
	}
	
	public void close() {
		containerLeft.clear();
		containerRight.clear();
	}
	
	public void open() {
		containerLeft.add(page);
	}

}
