package com.veilingsite.shared.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Address implements Serializable {

	public String street;
    public String postalCode;
    public String city;
    public String country;
  	public String housenumber;
  	public String phoneNumber;
  	public String adressType;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int adressId;
	
	@ManyToOne
    public User myUser;

    public Address() {
    }

    public Address(String pc, String cntr, String str, String type) {
		  setPostalCode(pc);
		  setCountry(cntr);
		  setStreet(str);
		  setAddressType(type);
    }

    public String getStreet() {
    	return street;
    }
    
    public void setStreet(String street) {
    	this.street = street;
    }

    public String getPostalCode() {
    	return postalCode;
    }    
    
    public void setPostalCode(String postalCode) {
    	this.postalCode = postalCode;
    }   
    
    public String getCity() {
    	return city;
    }
    
    public void setCity(String city) {
    	this.city = city;
    }

    public String getCountry() {
    	return country;
    }
    
    public void setCountry(String country) {
    	this.country = country;
    }

    public String getNumber() {
    	return housenumber;
    }
    
    public void setNumber(String number) {
    	this.housenumber = number;
    }

    public String getPhoneNumber() {
    	return phoneNumber;
    }
    
    public void setPhoneNumber(String phoneNumber) {
    	this.phoneNumber = phoneNumber;
    }

    public String getAddressType() {
    	return adressType;
    }
    
    public void setAddressType(String type) {
    	this.adressType = type;
    }

    public int getAdressId() {
    	return adressId;
    }

}