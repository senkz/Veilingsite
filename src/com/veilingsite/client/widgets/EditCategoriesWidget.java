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

		private ListBox listParents = new ListBox(false);
		private ListBox listCategories = new ListBox(false);
		private FlexTable table = new FlexTable();
		
		private Label info = new Label("Add/Modify Category");
		private Label parent = new Label();
		private Label error = new Label();
		private Label succes = new Label();
		
		private TextBox name = new TextBox();
		private Button toggleModify = new Button("Toggle add/delete mode");
		private Button submitData = new Button("Add/Delete Category");
	
		public EditCategoriesWidget() {
			
			//add class for styling
			this.addStyleName("widget");
			error.addStyleName("error");
			succes.addStyleName("succesfull");
			
			error.setVisible(false);
			succes.setVisible(false);
			
			loadCategories();
			
			listParents.addItem("Categories are being loaded.....");
			
			add(info);
			add(toggleModify);
			add(table);
			table.setWidget(0, 0, new Label("Category: "));			table.setWidget(0, 1, name);
			table.setWidget(1, 0, new Label("Parent: "));			table.setWidget(1, 1, listParents);
			table.setWidget(2, 0, submitData);
			
			add(succes);
			add(error);
			
			submitData.addClickHandler(new ClickHandler(){
				@Override
				public void onClick(ClickEvent event) {
					error.setVisible(false);
					succes.setVisible(false);
					if(table.getWidget(0, 1).equals(name)) {
						addCategory(new Category(name.getText(),listParents.getItemText(listParents.getSelectedIndex())));
					} else {
						deleteCategory(listCategories.getItemText(listCategories.getSelectedIndex()));
					}
				}
			});
			
			toggleModify.addClickHandler(new ClickHandler(){
				@Override
				public void onClick(ClickEvent event) {
					error.setVisible(false);
					succes.setVisible(false);
					if(table.getWidget(0, 1).equals(name)) {
						getCategory(listCategories.getItemText(listCategories.getSelectedIndex()));
						table.setWidget(0, 1, listCategories);
						table.setWidget(1, 1, parent);
					} else {
						table.setWidget(0, 1, name);	
						table.setWidget(1, 1, listParents);
					}
				}
			});
			
			listCategories.addChangeHandler(new ChangeHandler() {
				@Override
				public void onChange(ChangeEvent event) {
					error.setVisible(false);
					getCategory(listCategories.getItemText(listCategories.getSelectedIndex()));
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
					listParents.removeItem(0);
					listParents.addItem("No sub category");
					for(Category c : result) {
						listParents.addItem(c.getTitle());
						listCategories.addItem(c.getTitle());
					}
				}
			};
			myService.getCategoryList(callback);
		}

		private void addCategory(Category c) {		
			ServerServiceAsync myService = (ServerServiceAsync) GWT.create(ServerService.class);
			AsyncCallback<Category> callback = new AsyncCallback<Category>() {		
				@Override
				public void onFailure(Throwable caught) {}	
				
				@Override
				public void onSuccess(Category result) {
					if(result != null) {
						loadCategories();
						name.setText("");
						succes.setText(result.getTitle() + " succesfull created");
						succes.setVisible(true);
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
		
		private void deleteCategory(String s) {		
			ServerServiceAsync myService = (ServerServiceAsync) GWT.create(ServerService.class);
			AsyncCallback<Void> callback = new AsyncCallback<Void>() {		
				@Override
				public void onFailure(Throwable caught) {
					error.setText("Category deletion unsuccesfull, reason: "+ caught.getMessage());
					error.setVisible(true);
				}
				
				@Override
				public void onSuccess(Void result) {
					loadCategories();
					succes.setText("Category succesfull deleted");
					succes.setVisible(true);
				}
			};
			myService.deleteCategory(s, callback);
		}
}
