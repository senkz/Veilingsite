package com.veilingsite.client.widgets;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Timer;
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

public class AuctionBidWidget extends VerticalPanel {

	private Auction auction;
	private Button addBid = new Button("Place bid");
	private TextBox bidamount = new TextBox();
	private FlexTable table = new FlexTable();
	private FlexTable tableBids = new FlexTable();
	private Label systemStatus = new Label();
	private Timer systemStatusTimer;
		
		public AuctionBidWidget(Auction a) {
			
			//add class for styling
			this.addStyleName("widget");
			
			auction = a;
			
			systemStatusTimer = new Timer() {
			      public void run() {
						systemStatus.setVisible(false);
			      }
			 };
			
			if(UC.getLoggedIn() == null)
				bidamount.setReadOnly(true);
			
			table.setWidget(0, 0, new Label("Amount: "));
			table.setWidget(0, 1, bidamount);
			table.setWidget(1, 1, addBid);
			
			int i=1;
			tableBids.setWidget(0, 0, new Label("User"));
			tableBids.setWidget(0, 1, new Label("Amount"));
			tableBids.setWidget(i, 0, new Label("None was found"));
			for(Bid b : auction.getBidList()) {
				tableBids.setWidget(i, 0, new Label(b.getMyUser().getUserName()));
				tableBids.setWidget(i, 1, new Label(b.getAmount().toString()));
				i++;
			}
			
			add(table);
			add(new Label("Last bids:"));
			add(tableBids);
			add(systemStatus);
			
			addBid.addClickHandler(new ClickHandler(){
				@Override
				public void onClick(ClickEvent event) {
					systemStatusTimer.cancel();
					double d;
					try {
						d = Double.parseDouble(bidamount.getText());
					} catch(NumberFormatException nfe) {
						systemStatus.setText("Bid must be a numerical value");			
						systemStatus.setStyleName("error");
						systemStatus.setVisible(true);
						systemStatusTimer.schedule(3000);
						return;
					}
					if(UC.getLoggedIn() == null)
						return;
					if(auction.getHighestBid() == null) {
						if(auction.getStartAmount() > d) {
							systemStatus.setText("Bid must be higher than start amount");			
							systemStatus.setStyleName("error");
							systemStatus.setVisible(true);
							systemStatusTimer.schedule(3000);
						} else {
							addBid(new Bid(UC.getLoggedIn(),d,auction));
						}
					} else {
						if(auction.getHighestBid().getAmount() > d) {
							systemStatus.setText("Bid must be higher than highest bid");			
							systemStatus.setStyleName("error");
							systemStatus.setVisible(true);
							systemStatusTimer.schedule(3000);
						} else {
							addBid(new Bid(UC.getLoggedIn(),d,auction));
						}
					}
				}
			});
			
			
		}
		
		private void addBid(Bid b) {
			ServerServiceAsync myService = (ServerServiceAsync) GWT.create(ServerService.class);
			AsyncCallback<Bid> callback = new AsyncCallback<Bid>() {		
				@Override
				public void onFailure(Throwable caught) {}	
				
				@Override
				public void onSuccess(Bid result) {
					updateAuction(result);
				}
			};
			myService.addBid(b, callback);	
		}
		
		private void updateAuction(Bid b){	
			ServerServiceAsync myService = (ServerServiceAsync) GWT.create(ServerService.class);
			AsyncCallback<Void> callback = new AsyncCallback<Void>() {		
				@Override
				public void onFailure(Throwable caught) {}	
				
				@Override
				public void onSuccess(Void result) {
					systemStatus.setText("Bid has been placed succesfully");			
					systemStatus.setStyleName("succesfull");
					systemStatus.setVisible(true);
					systemStatusTimer.schedule(3000);
					refreshInput();
				}
			};
			auction.addBid(b);
			myService.updateAuction(auction, callback);
		}
		
		private void refreshInput() {
			bidamount.setText("");
		}
}
