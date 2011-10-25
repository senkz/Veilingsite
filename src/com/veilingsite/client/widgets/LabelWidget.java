package com.veilingsite.client.widgets;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class LabelWidget extends VerticalPanel {

	private Label l = new Label("");
	
		
	public LabelWidget() {	
		this.setStyleName("widget");
		add(l);
	}
	
	public LabelWidget(String s) {
		this.setStyleName("widget");
		add(l);
		setLabel(s);
	}
	
	public void setLabel(String s) {
		l.setText(s);
	}
	
}