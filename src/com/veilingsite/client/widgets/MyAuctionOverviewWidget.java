package com.veilingsite.client.widgets;

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
import com.google.gwt.user.datepicker.client.DatePicker;
import com.veilingsite.client.controllers.UC;
import com.veilingsite.shared.ServerService;
import com.veilingsite.shared.ServerServiceAsync;
import com.veilingsite.shared.domain.Auction;


public class MyAuctionOverviewWidget extends VerticalPanel{

		Button newauct = new Button("New Auction");
		
		private void addAuction(Auction a){	
			
			ServerServiceAsync myService = (ServerServiceAsync) GWT.create(ServerService.class);
			AsyncCallback<Auction> callback = new AsyncCallback<Auction>() {		
				@Override
				public void onFailure(Throwable caught) {
					
					caught.printStackTrace();
				}	
				@Override
				public void onSuccess(Auction result) {
					Window.alert("Auction has been added succesfully");
				}
			};
			myService.addAuction(a, callback);
		}
		
		public MyAuctionOverviewWidget() {				
			Label ltitle = new Label();
			ltitle.setText("Title: ");
			
			Label ldescription = new Label();
			ldescription.setText("Description: ");
			
			Label lstart = new Label();
			lstart.setText("Start amount: ");
			
			Label lempty = new Label();
			
			final TextBox title = new TextBox();
			final TextBox description = new TextBox();
			
			final Button addAuction = new Button();
			
			final DatePicker auctionclosedate = new DatePicker();
			
			final TextBox startamount = new TextBox();
			final FlexTable table = new FlexTable();
			table.setWidget(0, 0, ltitle);
			table.setWidget(0, 1, title);
			table.setWidget(1, 0, ldescription);
			table.setWidget(1, 1, description);
			table.setWidget(2, 0, lempty);
			table.setWidget(2, 1, auctionclosedate);
			table.setWidget(3, 0, lstart);
			table.setWidget(3, 1, startamount);
			table.setWidget(4, 1, addAuction);
			
			add(newauct);
			
			
			newauct.addClickHandler(new ClickHandler(){
				@Override
				public void onClick(ClickEvent event) {
					add(table);
				}
			});
			
			addAuction.addClickHandler(new ClickHandler(){
				@Override
				public void onClick(ClickEvent event) {
					addAuction(new Auction(title.getText(), description.getText(), Double.parseDouble(startamount.getText()), UC.getLoggedIn().getUserName(), null, auctionclosedate.getValue()));
				}
			});
			
			
		}
		
}
