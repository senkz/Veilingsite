package com.veilingsite.shared.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Setting implements Serializable{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  public long settingId;
  public String name;
  public String value;

  public User myUser;

  public Setting() {}
  
  public Setting(String nm, String va, User owner) {
	  this.name = nm;
	  this.value = va;
	  this.myUser = owner;
  }

  public String getValue() {
	  return null;
  }

}