package com.veilingsite.client.widgets;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.veilingsite.client.controllers.UC;
import com.veilingsite.shared.ServerService;
import com.veilingsite.shared.ServerServiceAsync;
import com.veilingsite.shared.domain.Auction;
import com.veilingsite.shared.domain.Bid;
import com.veilingsite.shared.domain.Category;
import com.veilingsite.shared.domain.User;

public class AuctionViewWidget extends VerticalPanel {
	private User limitUser = null;
	private Category limitCat = null;
	private FlexTable table = new FlexTable();
	
	public AuctionViewWidget() {
		add(table);
		table.setWidth("100%");
	}
	
	public void loadAuctions() {
		ServerServiceAsync myService = (ServerServiceAsync) GWT.create(ServerService.class);
		AsyncCallback<ArrayList<Auction>> callback = new AsyncCallback<ArrayList<Auction>>() {		
			@Override
			public void onFailure(Throwable caught) {}	
			
			@Override
			public void onSuccess(ArrayList<Auction> result) {
				showList(result);
			}
		};
		myService.getAuctionList(limitUser, limitCat, callback);
	}

	/**
	 * @return the limitUser
	 */
	public User getLimitUser() {
		return limitUser;
	}

	/**
	 * @param limitUser the limitUser to set
	 */
	public void setLimitUser(User limitUser) {
		this.limitUser = limitUser;
	}

	/**
	 * @return the limitCat
	 */
	public Category getLimitCat() {
		return limitCat;
	}

	/**
	 * @param limitCat the limitCat to set
	 */
	public void setLimitCat(Category limitCat) {
		this.limitCat = limitCat;
	}
	
	private void showList(ArrayList<Auction> al) {
		table.clear();
		if(al == null) {
			table.setWidget(0, 0, new Label("No auctions found."));
			return;
		}
		table.setWidget(0, 0, new Label("Title"));
		table.setWidget(0, 1, new Label("Description"));
		table.setWidget(0, 2, new Label("Owner"));
		table.setWidget(0, 3, new Label("Closing Date"));
		table.setWidget(0, 4, new Label("Current Bid"));
		
		for(final Auction a : al) {
			final TextBox t_bid = new TextBox();
			Button b_bid = new Button("Bid");
			int rown = table.getRowCount();
			
			table.setWidget(rown, 0, new Label(a.getTitle()));
			table.setWidget(rown, 1, new Label(a.getDescription()));
			table.setWidget(rown, 2, new Label(a.getOwner()));
			table.setWidget(rown, 3, new Label(a.getCloseDate() + ""));
			
			String s;
			if(a.getHighestBid() != null)
				s = a.getHighestBid().getAmount().toString();
			else
				s = a.getStartAmount().toString();
			table.setWidget(rown, 4, new Label(s));
			
			if(UC.getLoggedIn() != null) {
				table.setWidget(rown, 5, t_bid);
				table.setWidget(rown, 6, b_bid);
				
				b_bid.addClickHandler(new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						try {
							double bid = Double.parseDouble(t_bid.getText());
							saveBid(a, new Bid(UC.getLoggedIn(), bid, a));
						} catch(Exception e) {
							Window.alert("We only accept numberical values for bids.");
						}
					}
				});
			}
		}
	}
	
	public void saveBid(Auction a, Bid b) {
		System.out.println("com.veilingsite.client.widgets.AuctionViewWidget -> 124, save bid is not completed yet");
	}
}