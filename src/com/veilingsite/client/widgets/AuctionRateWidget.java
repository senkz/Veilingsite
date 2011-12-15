package com.veilingsite.client.widgets;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.veilingsite.client.controllers.UC;
import com.veilingsite.shared.ServerService;
import com.veilingsite.shared.ServerServiceAsync;
import com.veilingsite.shared.domain.Auction;
import com.veilingsite.shared.domain.User;

public class AuctionRateWidget extends VerticalPanel {

	private Auction auction;
	private Button rateUser = new Button("rate!");
	private ListBox listRate = new ListBox(false);
	private FlexTable table = new FlexTable();
	private Label systemStatus = new Label();
	private Timer systemStatusTimer;
		
		public AuctionRateWidget(Auction a) {
			
			Label title = new Label();
			title.setText("Rate this user");
			title.setStyleName("heading");
			add(title);
			
			//add class for styling
			this.addStyleName("widget");
			
			auction = a;
			
			listRate.addItem("Positive");
			listRate.addItem("Negative");		
			
			systemStatusTimer = new Timer() {
			      public void run() {
						systemStatus.setVisible(false);
			      }
			 };
			if(UC.getLoggedIn().getUserName().equals(a.getOwner().getUserName())){
				HorizontalPanel ymboutbec = new HorizontalPanel();
				Label ymboutbe = new Label("You cannot rate yourself.");
				ymboutbe.setStyleName("status");
				ymboutbec.add(ymboutbe);			
				rateUser.setEnabled(false);
				add(ymboutbe);
			}else if(UC.getLoggedIn() == null){
				HorizontalPanel ymbliec = new HorizontalPanel();
				Label ymblie = new Label("You must be logged in to rate users.");
				ymblie.setStyleName("status");
				ymbliec.add(ymblie);			
				rateUser.setEnabled(false);
				add(ymbliec);
			}else{
				table.setWidget(0, 0, new Label("Rate user: "));
				table.setWidget(0, 1, listRate);
				table.setWidget(0, 2, rateUser);
			}
			
			add(table);
			add(systemStatus);
			
			rateUser.addClickHandler(new ClickHandler(){
				@Override
				public void onClick(ClickEvent event) {
					systemStatusTimer.cancel();
					if(UC.getLoggedIn() == null)
						return;
					System.out.println(auction.getOwner().getFirstName());
					auction.getOwner().addRecommendation(listRate.getValue(listRate.getSelectedIndex()));
					rateUser(auction.getOwner());
					systemStatus.setText("Please wait a moment, your input is being submitted");
					systemStatus.setStyleName("status");
					systemStatus.setVisible(true);
				}
			});			
		}
		
		private void rateUser(User u) {
			ServerServiceAsync myService = (ServerServiceAsync) GWT.create(ServerService.class);
			AsyncCallback<Void> callback = new AsyncCallback<Void>() {		
				@Override
				public void onFailure(Throwable caught) {
					systemStatus.setText("Failed to submit input");
					systemStatus.setStyleName("unsuccesfull");
					systemStatus.setVisible(true);
					systemStatusTimer.schedule(3000);
				}	

				@Override
				public void onSuccess(Void result) {
					systemStatus.setText("Your input is submitted");
					systemStatus.setStyleName("succesfull");
					systemStatus.setVisible(true);
					systemStatusTimer.schedule(3000);
				}
			};
			myService.updateUser(u, callback);	
		}
}
