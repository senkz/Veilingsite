package com.veilingsite.client.pages;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.veilingsite.client.widgets.AdminUserEditWidget;
import com.veilingsite.client.widgets.EditCategoriesWidget;

public class AdminAuctionPage extends VerticalPanel {
	
	private Label myLabel = new Label();
	
	private EditCategoriesWidget ecw = new EditCategoriesWidget();
	private AdminUserEditWidget auew = new AdminUserEditWidget();
	private RootPanel containerLeft = RootPanel.get("containerLeft");
	private RootPanel containerRight = RootPanel.get("containerRight");
	
	public AdminAuctionPage(){
		add(myLabel);
		
		containerLeft.add(ecw);
		containerRight.add(auew);	
	}

}
