package com.veilingsite.shared.domain;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Date;

import org.junit.Test;

public class AuctionTest {

	//wrong value, less than 5 characters
	@Test
	public void testGetTitle() {
		String t = "test";
		Auction a = new Auction(t, null, null, null, null, null);
		assertFalse(a.getTitle().length() > 4 && a.getTitle().length() < 40 && a.getTitle().matches("^[0-9a-zA-z]+$"));
	}
	
	//wrong value, more than 40 characters
	@Test
	public void testGetTitle2() {
		String t = "testtesttesttesttesttesttesttesttesttesttest";
		Auction a = new Auction(t, null, null, null, null, null);
		assertFalse(a.getTitle().length() > 4 && a.getTitle().length() < 40 && a.getTitle().matches("^[0-9a-zA-z]+$"));
	}
	
	//correct value
	@Test
	public void testGetTitle3() {
		String t = "title";
		Auction a = new Auction(t, null, null, null, null, null);
		assertTrue(a.getTitle().length() > 4 && a.getTitle().length() < 40 && a.getTitle().matches("^[0-9a-zA-z]+$"));
	}

	@Test
	public void testGetCloseDate() {
		Date d = new Date(new Long(100));
		Auction a = new Auction(null, null, null, null, null, d);
		assertTrue(a.getCloseDate().after(new Date()));
	}

	@Test
	public void testGetStartAmount() {
		Double d = 0.00;
		Auction a = new Auction(null, null, d, null, null, null);
		assertTrue(a.getStartAmount().equals(0.00));
	}

	@Test
	public void testGetCategory() {
		Category c = new Category();
		Auction a = new Auction(null, null, null, null, c, null);
		assertTrue(a.getCategory() instanceof Category);
	}

	@Test
	public void testGetHighestBid() {
		Bid b1 = new Bid(null, 1.00, null);
		Bid b2 = new Bid(null, 0.00, null);
		Auction a = new Auction(null, null, null, null, null, null);
		a.addBid(b1);
		a.addBid(b2);
		assertTrue(a.getHighestBid().equals(b2));
	}
}
