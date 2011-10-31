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
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.datepicker.client.DatePicker;
import com.veilingsite.client.controllers.UC;
import com.veilingsite.client.exceptions.UserException;
import com.veilingsite.shared.ServerService;
import com.veilingsite.shared.ServerServiceAsync;
import com.veilingsite.shared.domain.Auction;
import com.veilingsite.shared.domain.Category;


public class AuctionCreateWidget extends VerticalPanel{
		
	private ListBox listCategories = new ListBox(false);
	private ArrayList<Category> categories = new ArrayList<Category>();
	private Button newauct = new Button("New Auction");
	private TextBox title = new TextBox();
	private TextBox description = new TextBox();
	private Button addAuction = new Button("Add Auction");
	private DatePicker auctionclosedate = new DatePicker();
	private TextBox startamount = new TextBox();
	private FlexTable table = new FlexTable();
		
		public AuctionCreateWidget() throws UserException  {
			
			//add class for styling
			this.addStyleName("widget");
			
			loadCategories();
			
			if(UC.getLoggedIn() == null)
				throw new UserException("ERROR: User must be logged in.");
			
			listCategories.addItem("Categories are being loaded.....");
			
			table.setWidget(0, 0, new Label("Title: "));
			table.setWidget(0, 1, title);
			table.setWidget(1, 0, new Label("Description: "));
			table.setWidget(1, 1, description);
			table.setWidget(2, 0, new Label("Category: "));
			table.setWidget(2, 1, listCategories);
			table.setWidget(3, 0, new Label("Auction close date: "));
			table.setWidget(3, 1, auctionclosedate);
			table.setWidget(4, 0, new Label("Start amount: "));
			table.setWidget(4, 1, startamount);
			table.setWidget(5, 0, addAuction);
			
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
					double d;
					try {
						d = Double.parseDouble(startamount.getText());
					} catch(NumberFormatException nfe) {
						Window.alert("Start amount must be a numerical value");
						return;
					}
					if(UC.getLoggedIn() == null)
						return;
					addAuction(new Auction(title.getText(), description.getText(), d, UC.getLoggedIn().getUserName(),
							categories.get(listCategories.getSelectedIndex()), auctionclosedate.getValue()));
				}
			});
			
			
		}
		
		private void addAuction(Auction a){	
			ServerServiceAsync myService = (ServerServiceAsync) GWT.create(ServerService.class);
			AsyncCallback<Auction> callback = new AsyncCallback<Auction>() {		
				@Override
				public void onFailure(Throwable caught) {}	
				
				@Override
				public void onSuccess(Auction result) {
					Window.alert("Auction has been added succesfully");
				}
			};
			myService.addAuction(a, callback);
		}
		
		public void loadCategories() {
			ServerServiceAsync myService = (ServerServiceAsync) GWT.create(ServerService.class);
			AsyncCallback<ArrayList<Category>> callback = new AsyncCallback<ArrayList<Category>>() {		
				@Override
				public void onFailure(Throwable caught) {}	
				
				@Override
				public void onSuccess(ArrayList<Category> result) {
					listCategories.removeItem(0);
					categories = result;
					for(Category c : result) {
						listCategories.addItem(c.getTitle());
					}
				}
			};
			myService.getCategoryList(callback);
		}
}
