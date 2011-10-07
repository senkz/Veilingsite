package com.veilingsite.shared.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Bid implements Serializable{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int bidId;
  private Double amount;
  private Date date;
  private User myUser;
  private Auction myAuction;
  
  public Bid(User u, Double b, Auction a) {
	  this.myUser = u;
	  this.amount = b;
	  this.myAuction = a;
  }

  public Double getAmount() {
	  return amount;
  }

  public Date getDate() {
	  return date;
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
	return myUser;
}

/**
 * @param myUser the myUser to set
 */
public void setMyUser(User myUser) {
	this.myUser = myUser;
}

/**
 * @return the myAuction
 */
public Auction getMyAuction() {
	return myAuction;
}

/**
 * @param myAuction the myAuction to set
 */
public void setMyAuction(Auction myAuction) {
	this.myAuction = myAuction;
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
	this.date = date;
}

}