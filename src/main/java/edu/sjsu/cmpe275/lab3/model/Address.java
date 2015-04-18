package edu.sjsu.cmpe275.lab3.model;

import org.mongodb.morphia.annotations.Embedded;

@Embedded
public class Address {
	
	private String street;
    private String city;
    private String state;
    private String zip;
    
    public Address(String street,String city,String state,String zip){
		this.street=street;
		this.city=city;
		this.state=state;
		this.zip=zip;
	}
	/**
	 * @return Returns the street of the Player or Sponsor
	 */
	public String getStreet() {
		return street;
	}
	/**
	 * @param street of the Player or Sponsor is passed
	 */
	public void setStreet(String street) {
		this.street = street;
	}
	
	/**
	 * @return Returns the city of the Player or Sponsor
	 */
	public String getCity() {
		return city;
	}
	/**
	 * @param city of the Player or Sponsor is passed
	 */
	public void setCity(String city) {
		this.city = city;
	}
	/**
	 * @return Returns the state of the Player or Sponsor
	 */
	public String getState() {
		return state;
	}
	/**
	 * @param state of the Player or Sponsor is passed
	 */
	public void setState(String state) {
		this.state = state;
	}
	/**
	 * @return Returns the zip of the Player or Sponsor
	 */
	public String getZip() {
		return zip;
	}
	/**
	 * @param zip of the Player or Sponsor is passed
	 */
	public void setZip(String zip) {
		this.zip = zip;
	}
    
    
}
