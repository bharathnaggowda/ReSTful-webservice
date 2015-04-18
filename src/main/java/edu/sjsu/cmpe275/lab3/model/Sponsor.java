package edu.sjsu.cmpe275.lab3.model;

import org.springframework.data.annotation.Id;
import org.hibernate.validator.constraints.NotEmpty;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;

@Entity
@Embedded
public class Sponsor {

	@Id
	private long id;
	
	@NotEmpty
    private String name;
	
    private String description;
    
    @Embedded
    private Address address;
    
    
	/**
	 * @return Returns the Id of the Sponsor
	 */
	public long getId() {
		return id;
	}
	/**
	 * @param id id of the Sponsor is passed
	 */
	public void setId(long id) {
		this.id = id;
	}
	
	/**
	 * @return Returns the name of the Sponsor
	 */
	@NotEmpty
	public String getName() {
		return name;
	}
	/**
	 * @param name of the Sponsor is passed
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return Returns the description of the Sponsor
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description of the Sponsor is passed
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return Returns the address of the Sponsor
	 */
	public Address getAddress() {
		return address;
	}
	/**
	 * @param address of the Sponsor is passed
	 */
	public void setAddress(Address address) {
		this.address = address;
	}

    
}
