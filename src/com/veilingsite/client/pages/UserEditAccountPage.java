package com.veilingsite.client.pages;

import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.veilingsite.client.widgets.UserEditWidget;

public class UserEditAccountPage extends VerticalPanel {
	
	private UserEditWidget uew = new UserEditWidget();
	private RootPanel containerLeft = RootPanel.get("containerLeft");
	private RootPanel containerRight = RootPanel.get("containerRight");
	
	public UserEditAccountPage() {
		containerLeft.add(uew);		
	}

}
