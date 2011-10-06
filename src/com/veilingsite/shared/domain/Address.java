package com.veilingsite.shared.domain;

public class Address {

  public String street;

  public String postalCode;

  public String city;

  public String country;

  public String number;

  public String phoneNumber;

  public String type;

  public int adressId;

    public User myUser;

  public Address() {
  }

  public Address(String pc, String cntr, String str, String type) {
	  this.postalCode = pc;
	  this.country = cntr;
	  this.street = str;
	  this.type = type;
  }

  public String getStreet() {
  return null;
  }

  public String getCity() {
  return null;
  }

  public String getCountry() {
  return null;
  }

  public String getNumber() {
  return null;
  }

  private String getPhoneNumber() {
  return null;
  }

  public String getAddressType() {
  return null;
  }

  public int getAdressId() {
  return 0;
  }

}