package com.veilingsite.client.widgets;

import java.util.ArrayList;
import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.datepicker.client.DatePicker;
import com.veilingsite.client.controllers.UC;
import com.veilingsite.client.exceptions.UserException;
import com.veilingsite.shared.*;
import com.veilingsite.shared.domain.*;

public class AuctionEditWidget extends VerticalPanel {
	
	//System Components - invisible
	private Label				auctionID 		  	   	  	  = new Label("");
	private User 				widgetUser 			   	   	  = (UC.getLoggedIn());
	private ArrayList<Category> categoriesList 				  = new ArrayList<Category>();
	
	//Graphical Components - visible (DUH)
	private Label 				systemStatus 		   	   	  = new Label("Edit This Auction");	
	private Label				auctionTitle_Label 		  	  = new Label("Title: ");
	private TextBox				auctionTitle		   		  = new TextBox();
	private Label				auctionOwner_Label 		  	  = new Label("Owner: ");
	private Label 				auctionOwner          	  	  = new Label();
	private Label				auctionCategories_Label 	  = new Label("Category: ");
	private ListBox 			auctionCategories     	  	  = new ListBox();
	private Label				auctionStartAmount_Label 	  = new Label("Amount: ");
	private TextBox 			auctionStartAmount			  = new TextBox();
	private Label				auctionDescription_Label 	  = new Label("Description: ");
	private TextArea			auctionDescription		   	  = new TextArea();
	private Label				auctionCloseDatePicker_Label  = new Label("Close Date: ");
	private DatePicker 			auctionCloseDatePicker 		  = new DatePicker();
	private Panel 				buttonPanel			   	   	  = new HorizontalPanel();		
	private Button 				confirmButton 		   	   	  = new Button("Confirm changes"); 
	private Button 				deleteButton 		   	   	  = new Button("Delete Auction");
	private FlexTable 			table 						  = new FlexTable();
	
	
	public AuctionEditWidget() {
		
		//add class for styling
		this.addStyleName("widget");
		
		// Load information from database
		loadCategories();
				
		auctionCategories.addItem("Categories are being loaded.....");
		
		// Construct the widget layout
		add(systemStatus);
		
		buttonPanel.add(confirmButton);
		buttonPanel.add(deleteButton);
		
		table.setWidget(0, 0, auctionTitle_Label);
		table.setWidget(0, 1, auctionTitle);
		table.setWidget(1, 0, auctionOwner_Label);
		table.setWidget(1, 1, auctionOwner);
		table.setWidget(2, 0, auctionCategories_Label);
		table.setWidget(2, 1, auctionCategories);
		table.setWidget(3, 0, auctionStartAmount_Label);
		table.setWidget(3, 1, auctionStartAmount);
		table.setWidget(4, 0, auctionDescription_Label);
		table.setWidget(4, 1, auctionDescription);
		table.setWidget(5, 0, auctionCloseDatePicker_Label);
		table.setWidget(5, 1, auctionCloseDatePicker);
		table.setWidget(6, 0, buttonPanel);
		
		add(table);
		
		//adding handlers to components
		confirmButton.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
			}
		});
		deleteButton.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
			}
		});
	}
	
	private boolean emailCheck(){
		return false;
	}
	
	public void loadCategories() {
		ServerServiceAsync myService = (ServerServiceAsync) GWT.create(ServerService.class);
		AsyncCallback<ArrayList<Category>> callback = new AsyncCallback<ArrayList<Category>>() {		
			@Override
			public void onFailure(Throwable caught) {}	
			
			@Override
			public void onSuccess(ArrayList<Category> result) {
				auctionCategories.removeItem(0);
				categoriesList = result;
				for(Category c : categoriesList) {
					auctionCategories.addItem(c.getTitle());
				}
			}
		};
		myService.getCategoryList(callback);
	}
	
	public void updateUser(User u){
		ServerServiceAsync myService = (ServerServiceAsync) GWT.create(ServerService.class);
		AsyncCallback<Void> callback = new AsyncCallback<Void>() {		
			@Override
			public void onFailure(Throwable caught) {
				systemStatus.setText("Error: Something went wrong, no User Data Updated.");
			}
			@Override
			public void onSuccess(Void result) {
				systemStatus.setText("User Data Updated.");
			}
		};
		myService.updateUser(u, callback);
	}
	public void removeUser(User u){
		ServerServiceAsync myService = (ServerServiceAsync) GWT.create(ServerService.class);
		AsyncCallback<Void> callback = new AsyncCallback<Void>() {		
			@Override
			public void onFailure(Throwable caught) {
				systemStatus.setText("Error: Something went wrong, no User removed");
			}
			@Override
			public void onSuccess(Void result) {
				systemStatus.setText("User removed");
			}
		};
		myService.removeUser(u, callback);
	}
	public void deleteUser(String u){
		ServerServiceAsync myService = (ServerServiceAsync) GWT.create(ServerService.class);
		AsyncCallback<User> callback = new AsyncCallback<User>() {		
			@Override
			public void onFailure(Throwable caught) {
				Window.alert("no user found!");
				systemStatus.setText("No user found");
			}
			@Override
			public void onSuccess(User result) {
				User tmpResultUser = result;
				removeUser(tmpResultUser);
			}
		};
		myService.getUser(u, callback);
	}
}