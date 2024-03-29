package com.veilingsite.shared.domain;

import java.io.Serializable;
import java.util.Vector;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Payment implements Serializable{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  public int paymentId;

  public String paymentType;

  public String accountidentifier;
  
  public User user;
  
  public Payment() {};

  public Payment(String pt, String ai, User u) {
	  this.paymentType = pt;
	  this.accountidentifier = ai;
	  this.user = u;
  }

  public String getPaymentType() {
  return null;
  }

  public String getAccountIdentiefier() {
  return null;
  }

}