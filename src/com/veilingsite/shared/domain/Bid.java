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
  public int bidId;

  public Double amount;

  public Date date;

  public User myUser;
  public Auction myAuction;

  public Double getAmount() {
  return null;
  }

  public Date getDate() {
	  return null;
  }

}