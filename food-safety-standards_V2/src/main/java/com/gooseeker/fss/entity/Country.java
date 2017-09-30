package com.gooseeker.fss.entity;

public class Country {
	private int id;
	private String country;
	
	public Country() {
		
	}
	
	public Country(String country) {
		super();
		this.country = country;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	
	
}
