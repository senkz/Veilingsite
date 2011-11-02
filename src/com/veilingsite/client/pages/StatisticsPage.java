package com.veilingsite.client.pages;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.veilingsite.client.widgets.StatBidWidget;

public class StatisticsPage extends VerticalPanel {
	
	private Label myLabel = new Label();
	
	private RootPanel containerLeft = RootPanel.get("containerLeft");
	private RootPanel containerRight = RootPanel.get("containerRight");
	
	private StatBidWidget sdw = new StatBidWidget();
	
	public StatisticsPage(){
		add(myLabel);
		containerLeft.add(sdw);
	}

}
