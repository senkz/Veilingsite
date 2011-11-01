package com.veilingsite.shared.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Bid implements Serializable{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int bidId;
  private Double amount;

  @Temporal(TemporalType.TIMESTAMP)
  private Date placementDate;
  
  @OneToOne
  private User owner;

  private Auction auction;
  
  public Bid() {}
  
  public Bid(User u, Double b, Auction a) {
	  this.owner = u;
	  this.amount = b;
	  this.auction = a;
	  placementDate = new Date();
  }

  public Double getAmount() {
	  return amount;
  }

  public Date getDate() {
	  return placementDate;
  }
  /**
 * @return the bidId
 */
public int getBidId() {
	return bidId;
}

/**
 * @param bidId the bidId to set
 */
public void setBidId(int bidId) {
	this.bidId = bidId;
}

/**
 * @return the myUser
 */
public User getMyUser() {
	return owner;
}

/**
 * @param myUser the myUser to set
 */
public void setMyUser(User myUser) {
	this.owner = myUser;
}

/**
 * @return the myAuction
 */
public Auction getMyAuction() {
	return auction;
}

/**
 * @param myAuction the myAuction to set
 */
public void setMyAuction(Auction myAuction) {
	this.auction = myAuction;
}

/**
 * @param amount the amount to set
 */
public void setAmount(Double amount) {
	this.amount = amount;
}

/**
 * @param date the date to set
 */
public void setDate(Date date) {
	this.placementDate = date;
}

}