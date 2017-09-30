package com.gooseeker.fss.entity;

public class Authority {
	private Long id;
	private Long uid;
	private String role;
	
	public Authority()
	{
	    
	}
	
	public Authority(Long uid, String role) {
		super();
		this.uid = uid;
		this.role = role;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getUid() {
		return uid;
	}
	public void setUid(Long uid) {
		this.uid = uid;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	
	
}
