package com.veilingsite.client.widgets;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;
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
import com.veilingsite.shared.domain.Category;
import com.veilingsite.shared.domain.User;

public class AuctionViewWidget extends VerticalPanel {
	private User limitUser = null;
	private Category limitCat = null;
	private FlexTable table = new FlexTable();
	private static ArrayList<PageChangeListener<Auction>> listeners = new ArrayList<PageChangeListener<Auction>>();
	
	public AuctionViewWidget() {
		Label title = new Label();
		title.setText("Overview of Auctions");
		title.setStyleName("heading");
		add(title);
		//add class for styling
		this.addStyleName("widget");
		table.addStyleName("auctionlist");
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
	
	public void showList(ArrayList<Auction> al) {
		table.clear();
		table.removeAllRows();
		if(al == null) {
			table.setWidget(0, 0, new Label("No auctions found."));
			return;
		}
		table.setWidget(0, 0, new Label("Title"));
		table.setWidget(0, 1, new Label("Owner"));
		table.setWidget(0, 2, new Label("Closing Date"));
		table.setWidget(0, 3, new Label("Current Bid"));
		int i = 1;
		System.out.println("al size: "+al.size());
		for(final Auction a : al) {
			System.out.println("Times looped: "+i);
			Button viewAuction = new Button("View");
			Button e_bid = new Button("Edit");
			
			table.setWidget(i, 0, new Label(a.getTitle()));
			table.setWidget(i, 1, new Label(a.getOwner().getUserName()));
			table.setWidget(i, 2, new Label(DateTimeFormat.getShortDateFormat().format(a.getCloseDate())));
			
			String s;
            NumberFormat format = NumberFormat.getFormat( "#.##" );
			if(a.getHighestBid() != null)
				s =  format.format(a.getHighestBid().getAmount());
			else
				s =  format.format(a.getStartAmount());
			table.setWidget(i, 3, new Label(s));
			
			table.setWidget(i, 4, viewAuction);
			if(UC.getLoggedIn() != null) {
				if(UC.getLoggedIn().getUserName().equals(a.getOwner())){
					table.setWidget(i, 6, e_bid);
				}
			}
			
			viewAuction.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					for(PageChangeListener<Auction> pcl : listeners)
						pcl.fireListener(a);
				}
			});
			
			table.getRowFormatter().getElement(i).addClassName("row"+i%2);
			i++;
		}
	}
	
	public void addPageChangeListener(PageChangeListener<Auction> pcl) {
		listeners.add(pcl);
	}
	
	public static void removeUserPageListener(PageChangeListener<Auction> pcl) {
		listeners.remove(pcl);
	}
}