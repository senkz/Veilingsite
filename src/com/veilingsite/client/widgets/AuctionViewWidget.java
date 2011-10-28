package com.veilingsite.client.widgets;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.logging.client.ConsoleLogHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.veilingsite.client.controllers.UC;
import com.veilingsite.client.listeners.PageChangeListener;
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
	private static ArrayList<PageChangeListener<Auction>> listeners = new ArrayList<PageChangeListener<Auction>>();
	
	public AuctionViewWidget() {
		
		//add class for styling
		this.addStyleName("widget");
		
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
		System.out.println(al);
		if(al == null) {
			table.setWidget(0, 0, new Label("No auctions found."));
			return;
		}
		table.setWidget(0, 0, new Label("Title"));
		table.setWidget(0, 1, new Label("Owner"));
		table.setWidget(0, 2, new Label("Closing Date"));
		table.setWidget(0, 3, new Label("Current Bid"));
		
		for(final Auction a : al) {
			Button viewAuction = new Button("View");
			Button e_bid = new Button("Edit");
			int rown = table.getRowCount();
			
			table.setWidget(rown, 0, new Label(a.getTitle()));
			table.setWidget(rown, 1, new Label(a.getOwner()));
			table.setWidget(rown, 2, new Label(a.getCloseDate() + ""));
			
			String s;
			if(a.getHighestBid() != null)
				s = a.getHighestBid().getAmount().toString();
			else
				s = a.getStartAmount().toString();
			table.setWidget(rown, 3, new Label(s));
			
			table.setWidget(rown, 4, viewAuction);
			if(UC.getLoggedIn() != null) {
				if(UC.getLoggedIn().getUserName().equals(a.getOwner())){
					table.setWidget(rown, 6, e_bid);
				}
			}
			
			viewAuction.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					for(PageChangeListener<Auction> pcl : listeners)
						pcl.fireListener(a);
				}
			});
		}
	}
	
	public void addPageChangeListener(PageChangeListener<Auction> pcl) {
		listeners.add(pcl);
	}
	
	public static void removeUserPageListener(PageChangeListener<Auction> pcl) {
		listeners.remove(pcl);
	}
}