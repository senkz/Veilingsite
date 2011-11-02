package com.veilingsite.shared.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;
import javax.persistence.PostLoad;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Auction implements Serializable {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long auctionId;

	private String title;
	private String description;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date startDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date closeDate;
	
	private Double startAmount;
	private User owner;

	private Category category;
   	public Image image;

    @OneToMany
    public List<Bid> bidList = new ArrayList<Bid>();
    
    @PostLoad
    public void fix(){
        List<Bid> bidList = new ArrayList<Bid>(this.bidList);
        this.bidList = bidList;
    }

	public Auction() {
    }
    
    public Auction(String title, String desc, Double amount, User owner, Category cat, Date date) {
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
    	if(s != null &&!s.equals("")){
    		title = s;
    	}else{
    		title = null;
    	}
    	
    }

    public String getDescription() {
    	return description;
    }

    public void setDescription(String s) {
    	if(s != null &&!s.equals("")){
    		description = s;
    	}else{
    		description = null;
    	}
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
    	if(d != null && !d.equals("")){
    		startAmount = d;
    	}else{
    		startAmount = null;
    	}
    	
    }
    
    public User getOwner() {
    	return owner;
    }
    
    private void setOwner(User owner) {
    	this.owner = owner;
    }
    
    public Category getCategory() {
    	return category;
    }
    
    public void setCategory(Category cat) {
    	this.category = cat;
    }
    
    public Image getImage() {
    	return image;
    }
    
    public void setImage(Image i) {
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
		if(bidList.size() > 0 && bidList.get(bidList.size()-1).getAmount() < bid.getAmount()) {
			bidList.add(bid);
			return true;
		} else if(bidList.size() == 0 && bid.getAmount() > this.getStartAmount()) {
			bidList.add(bid);
			return true;
		} else
			return false;
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
		return (new ArrayList<Bid> (bidList));
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