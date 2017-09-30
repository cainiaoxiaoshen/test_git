package com.gooseeker.fss.entity;

import java.sql.Timestamp;

public class DocReplace {
	private long id;
	private String createUserName;
	private Timestamp createTime;
	private String modiUserName;
	private Timestamp modiTime;
	private String newDoc;
	private String oldDoc;
	private String relation;
	private String remark;
	private boolean isDel;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getCreateUserName() {
		return createUserName;
	}
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	public String getModiUserName() {
		return modiUserName;
	}
	public void setModiUserName(String modiUserName) {
		this.modiUserName = modiUserName;
	}
	public Timestamp getModiTime() {
		return modiTime;
	}
	public void setModiTime(Timestamp modiTime) {
		this.modiTime = modiTime;
	}
	public String getNewDoc() {
		return newDoc;
	}
	public void setNewDoc(String newDoc) {
		this.newDoc = newDoc;
	}
	public String getOldDoc() {
		return oldDoc;
	}
	public void setOldDoc(String oldDoc) {
		this.oldDoc = oldDoc;
	}
	public String getRelation() {
		return relation;
	}
	public void setRelation(String relation) {
		this.relation = relation;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public boolean isDel() {
		return isDel;
	}
	public void setDel(boolean isDel) {
		this.isDel = isDel;
	}
	@Override
	public String toString() {
		return "DocReplace [id=" + id + ", createUserName=" + createUserName + ", createTime=" + createTime
				+ ", modiUserName=" + modiUserName + ", modiTime=" + modiTime + ", newDoc=" + newDoc + ", oldDoc="
				+ oldDoc + ", relation=" + relation + ", remark=" + remark + ", isDel=" + isDel + "]";
	}
	
	public boolean docReplaceEquals(DocReplace rpc){
		if(this.getNewDoc().equals(rpc.getNewDoc())
				&& this.getOldDoc().equals(rpc.getOldDoc())
				&& this.getRelation().equals(rpc.getRelation())
				&& this.getRemark().equals(rpc.getRemark())){
			return true;
		} else {
			return false;
		}
	}
	
	
}
