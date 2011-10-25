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
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.sun.java.swing.plaf.windows.resources.windows;
import com.veilingsite.shared.ServerService;
import com.veilingsite.shared.ServerServiceAsync;
import com.veilingsite.shared.domain.Category;
import com.veilingsite.shared.domain.User;

public class EditCategoriesWidget extends VerticalPanel {

		ArrayList<Category> categories;
		private ListBox lcat = new ListBox(false);
		private ListBox lpar = new ListBox(false);
		private FlexTable table = new FlexTable();
		
		private Label info = new Label("Add/Modify Category");
		private Label parent = new Label();
		private Label error = new Label();
		private TextBox name = new TextBox();
		private Button toggleModify = new Button("Toggle add/delete mode");
		private Button modify = new Button("Add/Delete Category");
	
		public EditCategoriesWidget() {
			
			//add class for styling
			this.addStyleName("widget");
			error.addStyleName("error");
			
			loadCategories();
			
			lcat.addItem("Categories are being loaded.....");
			
			add(info);
			add(toggleModify);
			add(table);
			table.setWidget(0, 0, new Label("Category: "));			table.setWidget(0, 1, name);
			table.setWidget(1, 0, new Label("Parent: "));			table.setWidget(1, 1, lcat);
			table.setWidget(2, 0, modify);
			
			error.setVisible(false);
			add(error);
			
			modify.addClickHandler(new ClickHandler(){
				@Override
				public void onClick(ClickEvent event) {
					error.setVisible(false);
					addCategory(new Category(name.getText(),lcat.getItemText(lcat.getSelectedIndex())));
				}
			});
			
			toggleModify.addClickHandler(new ClickHandler(){
				@Override
				public void onClick(ClickEvent event) {
					if(table.getWidget(0, 1).equals(name)) {
						getCategory(lpar.getItemText(lpar.getSelectedIndex()));
						table.setWidget(0, 1, lpar);
						table.setWidget(1, 1, parent);
					} else {
						table.setWidget(0, 1, name);	
						table.setWidget(1, 1, lcat);
					}
				}
			});
			
			lpar.addChangeHandler(new ChangeHandler() {
				
				@Override
				public void onChange(ChangeEvent event) {
					error.setVisible(false);
					getCategory(lpar.getItemText(lpar.getSelectedIndex()));
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
			lcat.addItem("No sub category");
			for(Category c : categories) {
				lcat.addItem(c.getTitle());
				lpar.addItem(c.getTitle());
			}
		}

		private void addCategory(Category c) {		
			ServerServiceAsync myService = (ServerServiceAsync) GWT.create(ServerService.class);
			AsyncCallback<Category> callback = new AsyncCallback<Category>() {		
				@Override
				public void onFailure(Throwable caught) {}	
				
				@Override
				public void onSuccess(Category result) {
					if(result != null) {
						lcat.addItem(result.getTitle());
						lpar.addItem(result.getTitle());
					} else {
						error.setText("Add category failed, this category probably already exists.");
						error.setVisible(true);
					}
				}
			};
			myService.addCategory(c, callback);
		}
		
		private void getCategory(String s) {		
			ServerServiceAsync myService = (ServerServiceAsync) GWT.create(ServerService.class);
			AsyncCallback<Category> callback = new AsyncCallback<Category>() {		
				@Override
				public void onFailure(Throwable caught) {}	
				
				@Override
				public void onSuccess(Category result) {
					if(result != null) {
						if(result.getParent().equals("")) {
							parent.setText("No parent");
						} else {
							parent.setText(result.getParent());
						}
					} else {
						error.setText("Retrieving parent info failed");
						error.setVisible(true);
					}
				}
			};
			myService.getCategory(s, callback);
		}
		
		/* moet delete Category worden
		private void modifyCategory(Category c) {		
			ServerServiceAsync myService = (ServerServiceAsync) GWT.create(ServerService.class);
			AsyncCallback<Category> callback = new AsyncCallback<Category>() {		
				@Override
				public void onFailure(Throwable caught) {}	
				
				@Override
				public void onSuccess(Category result) {
					if(result != null) {
						lcat.addItem(result.getTitle());
						lpar.addItem(result.getTitle());
					} else {
						error.setText("Add category failed, this category probably already exists.");
						error.setVisible(true);
					}
				}
			};
			myService.addCategory(c, callback);
		}
		*/
}
