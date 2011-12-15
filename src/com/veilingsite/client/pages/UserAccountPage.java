package com.veilingsite.client.pages;

import java.util.ArrayList;

import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TabBar;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.veilingsite.client.controllers.UC;
import com.veilingsite.client.listeners.PageChangeListener;
import com.veilingsite.client.widgets.AuctionViewWidget;
import com.veilingsite.client.widgets.LabelWidget;
import com.veilingsite.client.widgets.UserEditWidget;
import com.veilingsite.client.widgets.UserProductWidget;
import com.veilingsite.shared.domain.Auction;

public class UserAccountPage extends VerticalPanel {
	private UserEditWidget uew = new UserEditWidget();
	private UserProductWidget upw = new UserProductWidget();
	private AuctionViewWidget avc = new AuctionViewWidget();
	private RootPanel subMenu = RootPanel.get("subMenu");
	private RootPanel containerLeft = RootPanel.get("containerLeft");
	private RootPanel containerRight = RootPanel.get("containerRight");
	private ArrayList<AuctionViewPage> avw = new ArrayList<AuctionViewPage>();
	private LabelWidget userInfo;
	public TabBar menu = new TabBar();
	
	public UserAccountPage(){
		menu.addSelectionHandler(new SelectionHandler<Integer>() {
			@Override
			public void onSelection(SelectionEvent<Integer> event) {
				clearPage();
				switch(event.getSelectedItem()) {
				default:
				case 0:
					refreshPage();
					break;
				case 1:
					add(new UserCreateAuctionPage());
					break;
				case 2:
					add(new UserEditAccountPage());
					break;
				}
			}
		});

		menu.addTab("Account Overview");
		menu.addTab( "Create Auction");
		menu.addTab( "Edit Account");
		
		menu.selectTab(0);

		subMenu.add(menu);
		
		avc.addPageChangeListener(new PageChangeListener<Auction>() {
			@Override
			public void fireListener(Auction a) {
				System.out.println("Auction laden: "+a.getTitle());
				clearPage();
				addAuctionView(a);
			}
		});
	}
	
	private void refreshPage() {
		subMenu.add(menu);
		userInfo = new LabelWidget();
		
		int pos = 0;
		int neg = 0;
		
		if(UC.getLoggedIn()!=null) {
			ArrayList<String> rating = UC.getLoggedIn().getRecommendation();
			
			if(!rating.isEmpty()){
				for(String s : rating) {
					if(s.equals("Positive")) {
						pos++;
					} else if(s.equals("Negative")){
						System.out.println(s);
						neg++;
					}
				}
			}
		}
		
		if(UC.getLoggedIn() != null) {
			avc.setLimitUser(UC.getLoggedIn());
			avc.loadAuctions();
			FlexTable ft = new FlexTable();
			ft.setWidget(0, 0, new Label("Username:"));		ft.setWidget(0, 1,  new Label(UC.getLoggedIn().getUserName()));
			ft.setWidget(1, 0,  new Label("Name:"));		ft.setWidget(1, 1,  new Label(UC.getLoggedIn().getFirstName()));
			ft.setWidget(2, 0,  new Label("Sur Name:"));	ft.setWidget(2, 1,  new Label(UC.getLoggedIn().getSurName()));
			ft.setWidget(3, 0,  new Label("Postive rating:"));	ft.setWidget(3, 1,  new Label(pos+""));
			ft.setWidget(4, 0,  new Label("Negative rating:"));	ft.setWidget(4, 1,  new Label(neg+""));
			userInfo.setTitle("User Info");
			userInfo.add(ft);
			containerLeft.add(userInfo);
		}
		containerRight.add(avc);
	}
	
	private void clearPage() {
		containerLeft.clear();
		containerRight.clear();
		for(AuctionViewPage a: avw) {
			a.close();
		}
	}
	
	private void addAuctionView(Auction a) {
		AuctionViewPage page = new AuctionViewPage(a);
		if(avw.contains(page)) {
			add(avw.get(avw.indexOf(page)));
		} else {
			avw.add(page);
			add(page);
		}
	}
}
