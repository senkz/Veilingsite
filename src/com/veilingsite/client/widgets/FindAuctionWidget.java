package com.veilingsite.client.widgets;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
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


public class FindAuctionWidget extends VerticalPanel{
		private Label search = new Label("Search Auctions");
		private FlexTable table = new FlexTable();
		private FlexTable ltable = new FlexTable();
		private FlexTable ctable = new FlexTable();
		private ListBox lcat = new ListBox();
		private ListBox olist = new ListBox();
		private ListBox olist2 = new ListBox();
		private TextBox lsearch = new TextBox();
		private Button searchb = new Button("Search");
		private Category selCat;
		private ArrayList<Category> categories;
		private ArrayList<Category> subcats;
		public ArrayList<Category> blablabla;
		
		public FindAuctionWidget()  {
			//add class for styling
			this.addStyleName("widget");
			loadCategories();
			add(search);
			add(table);	
			table.setWidget(0, 0, new Label("Choose category:"));
			table.setWidget(0, 1, lcat);	
			table.setWidget(1, 0, new Label("Search word:"));
			table.setWidget(1, 1, lsearch);
			add(ctable);
			add(ltable);
			ltable.setWidget(0, 0, new Label("Order by:"));
			olist.addItem("Auction Close Date");
			olist.addItem("Price");
			olist.addItem("Title");
			olist.addItem("Owner");
			ltable.setWidget(0, 1, olist);
			olist2.addItem("Ascending");
			olist2.addItem("Descending");
			ltable.setWidget(0, 2, olist2);
			ltable.setWidget(1, 0, searchb);
			
			lcat.addChangeHandler(new ChangeHandler() {
				@Override
				public void onChange(ChangeEvent event) {
					table.setWidget(2, 0, null);
					ctable.clear();
					for(Category c : categories) {
						if(lcat.getItemText(lcat.getSelectedIndex()).equals(c.title)){
							table.setWidget(2, 0, new Label("Choose sub categories: "));
							selCat = c;
							int count = 0;
							for(Category a : categories){
								if(a.getParent().equals(selCat.getTitle())){
									ctable.setWidget(count, 0, new Label(a.getTitle()));
									ctable.setWidget(count, 1, new CheckBox());
									count++;
								}
							}
						}
					}
				}
			});
			
		
		searchb.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if(checkFields() == true){ // All fields meet the required values...
					
				}
			}
		});
	}
		
		public boolean checkFields(){
			if(lcat.getSelectedIndex() == 0){ // User chose "Choose Category..." as category
				Window.alert("You have forgotten to choose a category.");
				return false;
			}
			if(lsearch.getText().isEmpty() == true){
				Window.alert("You have forgotten to fill in a search word.");
				return false;
			}
			if(lsearch.getText().length() < 5){
				Window.alert("The search word has to be at least 5 characters.");
				return false;
			}
			return true;
		}
		
		public void getSub(Category c) {
			ServerServiceAsync myService = (ServerServiceAsync) GWT.create(ServerService.class);
			AsyncCallback<ArrayList<Category>> callback = new AsyncCallback<ArrayList<Category>>() {		
				@Override
				public void onFailure(Throwable caught) {}	
				
				@Override
				public void onSuccess(ArrayList<Category> result) {
					subcats = result;
					fillSubList(result);
				}
			};
			myService.getChildrenOfCategory(c, callback);
		}
		
		
		
		public void loadCategories() {
			ServerServiceAsync myService = (ServerServiceAsync) GWT.create(ServerService.class);
			AsyncCallback<ArrayList<Category>> callback = new AsyncCallback<ArrayList<Category>>() {		
				@Override
				public void onFailure(Throwable caught) {}	
				
				@Override
				public void onSuccess(ArrayList<Category> result) {
					categories = result;
					fillCatList();
				}
			};
			myService.getCategoryList(callback);
		}
		
		private void fillCatList() {
			lcat.addItem("Choose Category...");
			for(Category c : categories){
				if(c.getParent().isEmpty() == true){
					lcat.addItem(c.getTitle());
				}
			}
			
		}
		
		private void fillSubList(ArrayList<Category> sub) {
			for(Category c : sub) {
				subcats.add(c);				
			}
		}
	}
