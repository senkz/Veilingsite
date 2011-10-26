package com.veilingsite.client.pages;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.veilingsite.shared.ServerService;
import com.veilingsite.shared.ServerServiceAsync;
import com.veilingsite.shared.domain.Auction;

public class AuctionViewPage extends VerticalPanel{
	
	//private RootPanel containerLeft = RootPanel.get("container43");
	//private RootPanel containerRight = RootPanel.get("container14");
	private VerticalPanel auction = new VerticalPanel();
	private FlexTable ft = new FlexTable();
	private Label title = new Label();
	
	AuctionViewPage(String t) {
		getAuction(t);
		
		auction.setStyleName("page");
		auction.add(ft);
		
		//containerLeft.add(auction);
	}
	
	private void loadPage(Auction a) {
		title.setText(a.getTitle());
		title.setStyleName("heading");
	
		ft.setWidget(0, 0, new Label("Title:"));		ft.setWidget(0, 1, title);		
	}
	
	private void getAuction(String t) {
			ServerServiceAsync myService = (ServerServiceAsync) GWT.create(ServerService.class);
			AsyncCallback<Auction> callback = new AsyncCallback<Auction>() {		
				@Override
				public void onFailure(Throwable caught) {}	
				
				@Override
				public void onSuccess(Auction result) {
					if(result != null) {
						loadPage(result);
					} else {
						System.out.println("null..... auction");
					}
				}
			};
			myService.getAuction(t, callback);
	}
	
	public void Close() {
		//containerLeft.clear();
		//containerRight.clear();
	}

}
