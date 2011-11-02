package com.veilingsite.client.widgets;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Timer;
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
import com.veilingsite.client.listeners.PageChangeListener;
import com.veilingsite.shared.ServerService;
import com.veilingsite.shared.ServerServiceAsync;
import com.veilingsite.shared.domain.Auction;
import com.veilingsite.shared.domain.Category;


public class FindAuctionWidget extends VerticalPanel{
		private Label systemStatus = new Label();
		private Timer systemStatusTimer;
		private Label search = new Label("Search Auctions");
		private FlexTable table = new FlexTable();
		private FlexTable ltable = new FlexTable();
		private FlexTable ctable = new FlexTable();
		private ListBox lcat = new ListBox();
		private ListBox olist = new ListBox();
		private ListBox olist2 = new ListBox();
		private TextBox lsearch = new TextBox();
		private Button searchb = new Button("Search");
		private ArrayList<Category> categories;
		public ArrayList<Category> blablabla;
		private ArrayList<Category> subcats = new ArrayList<Category>();
		private ArrayList<Category> subcats2;
		private static ArrayList<PageChangeListener<ArrayList<Auction>>> listeners = new ArrayList<PageChangeListener<ArrayList<Auction>>>();
		
		public FindAuctionWidget()  {
			systemStatusTimer = new Timer() {
			      public void run() {
						systemStatus.setVisible(false);
			      }
			 };
			//add class for styling
			this.addStyleName("widget");
			loadCategories();
			add(search);
			add(systemStatus);
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
			ltable.setWidget(0, 1, olist);
			olist2.addItem("Ascending");
			olist2.addItem("Descending");
			ltable.setWidget(0, 2, olist2);
			//ltable.setWidget(1, 0, new Label("*By selecting a subcategory, "));
			ltable.setWidget(1, 0, searchb);
			
			lcat.addChangeHandler(new ChangeHandler() {
				@Override
				public void onChange(ChangeEvent event) {
					int count = 0;
					boolean choosetest = false;
					table.setWidget(2, 0, null);
					ctable.clear();
					ctable.removeAllRows();
					for(final Category a : categories){
						if(a.getParent() != null){
							for(Category s : categories){
								if(s.getTitle().equals(a.getParent())){
									getSub(s);
								}
							}
							if(a.getParent().equals(lcat.getItemText(lcat.getSelectedIndex()))){
								
								if(choosetest == false){
									table.setWidget(2, 0, new Label("Choose sub categories: "));
									choosetest = true;
								}
								ctable.setWidget(count, 0, new Label(a.getTitle()));
								CheckBox ck = new CheckBox();
								ctable.setWidget(count, 1, ck);
								ck.addClickHandler(new ClickHandler() {
									@Override
									public void onClick(ClickEvent event) {
										if(subcats.contains(a)) {
											subcats.remove(a);
										}
										else {
											subcats.add(a);
										}
									}
								});
							}
						}
						count++;
					}
				}
			});
			
		searchb.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if(checkFields() == true){ // All fields meet the required values...
					Category category = null;
					String orderBy = olist.getItemText(olist.getSelectedIndex());
					String ascDesc = olist2.getItemText(olist2.getSelectedIndex());
					String searchWord = lsearch.getText();
					for(Category c : categories){
						if(lcat.getItemText(lcat.getSelectedIndex()).equals(c.getTitle())){
							category = c;
						}
					}
					
					//Listbox strings in database fields veranderen....
					
					if(ascDesc.equals("Ascending")){
						ascDesc = "asc";
					}
					else{
						ascDesc = "desc";
					}
					
					if(orderBy.equals("Price")){
						orderBy = null;
					}
					
					if(orderBy.equals("Title")){
						orderBy = "title";
					}
					
					if(orderBy.equals("Auction Close Date")){
						orderBy = "closeDate";
					}
					
					if(subcats.isEmpty()){
						startSearch(searchWord, category, subcats2, orderBy, ascDesc);
					}
					else{
						startSearch(searchWord, category, subcats, orderBy, ascDesc);
					}
					
				}
			}
		});
	}
		
		public void startSearch(String sw, Category ct, ArrayList<Category> c, String or, String asc) {
			ServerServiceAsync myService = (ServerServiceAsync) GWT.create(ServerService.class);
			AsyncCallback<ArrayList<Auction>> callback = new AsyncCallback<ArrayList<Auction>>() {		
				@Override
				public void onFailure(Throwable caught) {}	
				
				@Override
				public void onSuccess(ArrayList<Auction> result){
					if(result.isEmpty()){
						systemStatus.setText("No results found.");			
						systemStatus.setStyleName("error");
						systemStatus.setVisible(true);
						systemStatusTimer.schedule(3000);
					}else{
						String succesfullString = new String();
						if(result.size() == 1){
							succesfullString = result.size() + " result found.";
						}else{
							succesfullString = result.size() + " results found.";
						}
						systemStatus.setText(succesfullString);			
						systemStatus.setStyleName("succesfull");
						systemStatus.setVisible(true);
						systemStatusTimer.schedule(10000);
						int count = 1; 
						try{
							
							for(PageChangeListener<ArrayList<Auction>> pcl : listeners){
								if(count < 10){
									pcl.fireListener(result);
								}
								else{
									break;
								}
							}
							
						}
						catch(NullPointerException e){
							systemStatus.setText("No results found.");			
							systemStatus.setStyleName("error");
							systemStatus.setVisible(true);
							systemStatusTimer.schedule(3000);
						}
					}
					
				}
			};
			myService.findAuction(sw, ct, c, or, asc, callback);
		}
		
		public boolean checkFields(){
			if(lcat.getSelectedIndex() == 0){ // User chose "Choose Category..." as category
				systemStatus.setText("You have forgotten to choose a category.");			
				systemStatus.setStyleName("error");
				systemStatus.setVisible(true);
				systemStatusTimer.schedule(3000);
				return false;
			}
			if(lsearch.getText().isEmpty() == true){
				systemStatus.setText("You have forgotten to fill in a search word.");			
				systemStatus.setStyleName("error");
				systemStatus.setVisible(true);
				systemStatusTimer.schedule(3000);
				return false;
			}
			if(lsearch.getText().length() < 5){
				systemStatus.setText("The search word has to be at least 5 characters.");			
				systemStatus.setStyleName("error");
				systemStatus.setVisible(true);
				systemStatusTimer.schedule(3000);
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
					subcats2 = result;
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
					lcat.addItem("Choose Category...");
					for(Category c : categories){
						if(c.getParent() == null){
							lcat.addItem(c.getTitle());
						}
					}
				}
			};
			myService.getCategoryList(callback);
		}
		
		
		private void fillSubList(ArrayList<Category> sub) {
			for(Category c : sub) {
				subcats2.add(c);				
			}
		}
		
		private void showResults(ArrayList<Auction> au) {
			for(Auction a : au) {
				table.clear();
				System.out.println(au);
				if(au == null) {
					systemStatus.setText("No auctions found.");			
					systemStatus.setStyleName("error");
					systemStatus.setVisible(true);
					systemStatusTimer.schedule(3000);
					return;
				}
				table.setWidget(0, 0, new Label("Title"));
				table.setWidget(0, 1, new Label("Owner"));
				table.setWidget(0, 2, new Label("Closing Date"));
				table.setWidget(0, 3, new Label("Current Bid"));
				
				for(final Auction a2 : au) {
					Button viewAuction = new Button("View");
					Button e_bid = new Button("Edit");
					int rown = table.getRowCount();
					
					table.setWidget(rown, 0, new Label(a2.getTitle()));
					table.setWidget(rown, 1, new Label(a2.getOwner().getUserName()));
					table.setWidget(rown, 2, new Label(a2.getCloseDate() + ""));
					
					String s;
					if(a2.getHighestBid() != null)
						s = a2.getHighestBid().getAmount().toString();
					else
						s = a2.getStartAmount().toString();
					table.setWidget(rown, 3, new Label(s));
					
					table.setWidget(rown, 4, viewAuction);
					if(UC.getLoggedIn() != null) {
						if(UC.getLoggedIn().getUserName().equals(a2.getOwner())){
							table.setWidget(rown, 6, e_bid);
						}
					}
				}			
			}
		}

		public void addPageChangeListener(PageChangeListener<ArrayList<Auction>> pcl) {
			listeners.add(pcl);
		}
	}
