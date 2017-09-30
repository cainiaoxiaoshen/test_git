package com.gooseeker.fss.entity.vo;

import com.gooseeker.fss.entity.Annotate;

public class FactorsDetailsVo extends Annotate {
	
	private String country;
	private String type;
	
	public FactorsDetailsVo() {
		super();
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
}
