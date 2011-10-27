package com.veilingsite.shared.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import javax.jdo.annotations.NotPersistent;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Auction implements Serializable {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long auctionId;

	private String title;
	private String description;
	private Date startDate;
	private Date closeDate;
	private Double startAmount;
	private String owner;
	private String category;
    public String image;
    public ArrayList<Bid> bidList = new ArrayList<Bid>();

	public Auction() {
    }
    
    public Auction(String title, String desc, Double amount, String owner, String cat, Date date) {
    	setTitle(title);
    	setDescription(desc);
    	setStartAmount(amount);
    	setOwner(owner);
    	setCategory(cat);
    	setCloseDate(date);
    	setStartDate(new Date());
    }

    public String getTitle() {
    	return title;
    }

    public void setTitle(String s) {
    	title = s;
    }

    public String getDescription() {
    	return description;
    }

    public void setDescription(String s) {
    	description = s;
    }

    public Date getStartDate() {
    	return startDate;
    }
    
    public void setStartDate(Date d) {
    	startDate = d;
    }

    public Date getCloseDate() {
    	return closeDate;
    }

    public void setCloseDate(Date d) {
    	this.closeDate = d;
    }

    public Double getStartAmount() {
    	return startAmount;
    }

    public void setStartAmount(Double d) {
    	startAmount = d;
    }
    
    public String getOwner() {
    	return owner;
    }
    
    private void setOwner(String owner) {
    	this.owner = owner;
    }
    
    public String getCategory() {
    	return category;
    }
    
    public void setCategory(String category) {
    	this.category = category;
    }
    
    public String getImage() {
    	return image;
    }
    
    public void setImage(String i) {
    	image = i;
    }
    
    public Bid getHighestBid() {
    	Bid bid = null;
    	for(Bid nbid : bidList)
    		if(bid == null)
    			bid = nbid;
    		else if(bid.getAmount() < nbid.getAmount())
    			bid = nbid;
    	return bid;
    }

	public boolean addBid(Bid bid) {
		if(bidList.get(bidList.size()-1).getAmount() < bid.getAmount()) {
			bidList.add(bid);
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * @return the auctionId
	 */
	public Long getAuctionId() {
		return auctionId;
	}

    /**
	 * @return the bidList
	 */
	public ArrayList<Bid> getBidList() {
		return bidList;
	}

	/**
	 * @param bidList the bidList to set
	 */
	public void setBidList(ArrayList<Bid> bidList) {
		this.bidList = bidList;
	}
	
	public long getId() {
		return auctionId;
	}
}