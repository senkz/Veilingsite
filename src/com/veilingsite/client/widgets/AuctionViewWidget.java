package com.veilingsite.client.widgets;

import com.veilingsite.shared.domain.*;

public class AuctionViewWidget {
	private User limitUser;
	private Category limitCat;
	
	public AuctionViewWidget() {}
	
	public void loadAuctions() {
		
	}

	/**
	 * @return the limitUser
	 */
	public User getLimitUser() {
		return limitUser;
	}

	/**
	 * @param limitUser the limitUser to set
	 */
	public void setLimitUser(User limitUser) {
		this.limitUser = limitUser;
	}

	/**
	 * @return the limitCat
	 */
	public Category getLimitCat() {
		return limitCat;
	}

	/**
	 * @param limitCat the limitCat to set
	 */
	public void setLimitCat(Category limitCat) {
		this.limitCat = limitCat;
	}
}