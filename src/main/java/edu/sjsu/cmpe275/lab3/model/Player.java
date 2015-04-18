package edu.sjsu.cmpe275.lab3.model;

import java.util.List;

import javax.validation.constraints.NotNull;


import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Player")
@Entity
public class Player {
	
	private long id;
	@NotNull
    private String firstname;
	@NotNull
    private String lastname;
	@NotNull
    private String email;
    private String description;
    
    @Embedded
    private Address address;
    
    @Embedded
    private long sponsor;
    private List<Player> opponents;
    
	/**
	 * @return Returns the Id of the Player
	 */
	public long getId() {
		return id;
	}
	/**
	 * @param id id of the Player is passed
	 */
	public void setId(long id) {
		this.id = id;
	}
	/**
	 * @return Returns the firstname of the Player
	 */
	public String getFirstname() {
		return firstname;
	}
	/**
	 * @param firstname firstname of the Player is passed
	 */
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	/**
	 * @return Returns the lastname of the Player
	 */
	public String getLastname() {
		return lastname;
	}
	/**
	 * @param lastname firstname of the Player is passed 
	 */
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	/**
	 * @return Returns the email of the Player
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email email of the Player is passed 
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return Returns the description of the Player
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description description of the Player is passed
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return Returns the address of the Player
	 */
	public Address getAddress() {
		return address;
	}
	/**
	 * @param address address of the Sponsor is passed of the Sponsor is passed
	 */
	public void setAddress(Address address) {
		this.address = address;
	}
	/**
	 * @return It returns the sponsor object
	 */
	public long getSponsor() {
		return sponsor;
	}
	/**
	 * @param sponsor An instance of class Sponsor
	 */
	public void setSponsor(long sponsor) {
		this.sponsor = sponsor;
	}
	/**
	 * @return Returns the list of opponents
	 */
	public List<Player> getOpponents() {
		return opponents;
	}
	/**
	 * @param opponents
	 */
	public void setOpponents(List<Player> opponents) {
		this.opponents = opponents;
	}
    
    
}