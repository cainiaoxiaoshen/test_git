//package com.sst.report.commons.base.dao.common;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.apache.commons.lang.math.NumberUtils;
///**
// * 
// * Description:
// * 分页用到的POJO
// * Pager.java Create on 2013-4-18 下午9:18:15 
// * @author held
// * @version 1.0
// * Copyright (c) 2013 held,Inc. All Rights Reserved.
// */
//public class Pager<T> {
//	
//	
//	public Criteria getCriteria(){
//		return this.criteria != null ?criteria:new Criteria();
//	}
//	
//	public void setCriteria(Criteria criteria){
//		this.criteria = criteria != null ?criteria:new Criteria();
//	}
//	
//	public Integer getPn() {
//		return pn;
//	}
//
//	public void setPn(Integer pn) {
//		this.pn = pn;
//	}
//	
//	/**
//	 * 设置分页数，默认为1
//	 * @param pageNumber
//	 */
//	public void setPageNumber(String pageNumber) {
//		this.setPn(NumberUtils.toInt(pageNumber, 1));
//	}
//	
//	public void setPageSize(String pagesize){
//		this.setPageSize(NumberUtils.toInt(pagesize, 10));
//	}
//
//	public Integer getPageSize() {
//		return pageSize;
//	}
//
//	public void setPageSize(int pageSize) {
//		this.pageSize = pageSize < 1 ? -1 : pageSize;
//		this.pageSize = this.pageSize > MAX_PAGE_SIZE ? MAX_PAGE_SIZE : this.pageSize;
//	}
//
//	public Integer getTotalCount() {
//		return totalCount;
//	}
//
//	public void setTotalCount(Integer totalCount) {
//		this.totalCount = totalCount;
//		// 计算总页数
//		setPageCount((this.totalCount / getPageSize()) + (this.totalCount % getPageSize() == 0?0:1));
//		// 预处理页码
//		pn = (pn > pageCount ? pageCount : pn);
//		pn = (pn <= 0 ? 1 : pn);
//	}
//
//	public Integer getPageCount() {
//		return pageCount;
//	}
//
//	public void setPageCount(Integer pageCount) {
//		this.pageCount = pageCount;
//	}
//
//	public List<T> getList() {
//		return list == null ? new ArrayList<T>() : list;
//	}
//
//	public void setList(List<T> list) {
//		this.list = list;
//	}
//
//	public String getLimit() {
//		return pageSize < 0 ? "" : new StringBuilder(" limit ").append((pn - 1) * pageSize).append(",").append(pageSize).toString();
//	}
//
//	public PagerFooter getPagerFooter() {
//		
//		return pagerFooter;
//	}
//
//	public void setPagerFooter(PagerFooter pagerFooter) {
//		this.pagerFooter = pagerFooter;
//	}
//
//	public static final Integer MAX_PAGE_SIZE = 500;// 每页最大记录数限制
//	private Integer pageSize = -1;// 每页记录数 -1 表示没有limit限制
//	private Integer totalCount = 0;// 总记录数
//	private Integer pageCount = 0;// 总页数
//	private Integer pn = 1;// 页码(必须是数字)
//	private PagerFooter pagerFooter = new PagerFooter(); //页脚
//	private List<T> list = new ArrayList<T>();// 数据List
//	private Criteria criteria = new Criteria(); // 条件
//}