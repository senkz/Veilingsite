package com.veilingsite.client.widgets;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.veilingsite.client.controllers.UC;
import com.veilingsite.shared.ServerService;
import com.veilingsite.shared.ServerServiceAsync;
import com.veilingsite.shared.domain.Auction;
import com.veilingsite.shared.domain.Bid;
import com.veilingsite.shared.domain.Category;
import com.veilingsite.shared.domain.User;

public class UserProductWidget extends VerticalPanel {
	private User limitUser = null;
	private Category limitCat = null;
	private FlexTable table = new FlexTable();
	
	public UserProductWidget() {
		//add class for styling
		this.addStyleName("widget");
		
		add(table);
		table.setWidth("100%");
	}
			
		
	}