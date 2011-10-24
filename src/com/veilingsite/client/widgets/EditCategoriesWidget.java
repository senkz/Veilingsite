package com.veilingsite.client.widgets;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.veilingsite.shared.ServerService;
import com.veilingsite.shared.ServerServiceAsync;
import com.veilingsite.shared.domain.Category;
import com.veilingsite.shared.domain.User;

public class EditCategoriesWidget extends VerticalPanel {

		ArrayList<Category> categories;
		private ListBox lcat = new ListBox(false);
		private FlexTable table = new FlexTable();
		private Label info = new Label("Add/Modify Category");
		private TextBox name = new TextBox();
		private Button toggleModify = new Button("Toggle add/modify mode");
		private Button modify = new Button("Add/Modify Category");
	
		public EditCategoriesWidget() {
			
			//add class for styling
			this.addStyleName("widget");
			
			loadCategories();
			
			lcat.addItem("Categorien worden geladen.....");
			
			add(info);
			add(table);
			
			table.setWidget(0, 0, new Label("Category: "));			table.setWidget(0, 1, name);
			table.setWidget(1, 0, new Label("Parent: "));			table.setWidget(1, 1, lcat);
			table.setWidget(2, 0, modify);
			
			modify.addClickHandler(new ClickHandler(){
				@Override
				public void onClick(ClickEvent event) {
					//addCategory(new Category(name.getText(),null));
					info.setText("Category modify/edit request processing");
				}
			});
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
			lcat.removeItem(0);
			for(Category c : categories) {
				lcat.addItem(c.getTitle());
			}
		}
/*
	private void addCategory(Category u) {		
		ServerServiceAsync myService = (ServerServiceAsync) GWT.create(ServerService.class);
		AsyncCallback<User> callback = new AsyncCallback<User>() {		
			@Override
			public void onFailure(Throwable caught) {}	
			
			@Override
			public void onSuccess(User result) {
				setRegisterStatus(result);
			}
		};
		myService.addUser(u, callback);
	}
	*/
}
