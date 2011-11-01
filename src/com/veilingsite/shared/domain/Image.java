package com.veilingsite.shared.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Image implements Serializable{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  public int imageId;

  public String imageUrl;

  public Auction auction;

  public Image(){};
  
  public Image(String s, Auction a) {
	  this.imageUrl = s;
	  this.auction = a;
  }

  public String getImageUrl() {
	  return imageUrl;
  }
  
  public int getImageId() {
	  return imageId;
  }
}