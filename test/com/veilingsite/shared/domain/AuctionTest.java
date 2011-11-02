package com.veilingsite.shared.domain;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class AuctionTest {
	
	@Test
	public void testAuctionTitle(){		
		String title = "ueurk";
		Auction a = new Auction(title, null, null, null, null, null);
		assertTrue(a.getTitle().length() > 4 && a.getTitle().length() < 40 && a.getTitle().matches("^[0-9a-zA-z]+$"));
	}
}
