package com.gooseeker.fss.entity;

import java.sql.Timestamp;

public class Synonym {
	private int id;
	private String createUser;
	private Timestamp createTime;
	private String mainWord;
	private String synWord;
	private short isDel;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	public String getMainWord() {
		return mainWord;
	}
	public void setMainWord(String mainWord) {
		this.mainWord = mainWord;
	}
	public String getSynWord() {
		return synWord;
	}
	public void setSynWord(String synWord) {
		this.synWord = synWord;
	}
	public short getIsDel() {
		return isDel;
	}
	public void setIsDel(short isDel) {
		this.isDel = isDel;
	}
}
