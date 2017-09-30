//package com.sst.report.commons.base.dao.common;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import javax.servlet.http.HttpServletRequest;
//
//import org.apache.commons.lang.StringUtils;
///**
// * 
// * Description:
// * PagerFooter.java Create on 2013-4-18 下午9:18:50 
// * @author held
// * @version 1.0
// * Copyright (c) 2013 held,Inc. All Rights Reserved.
// */
//public class PagerFooter {
//	private String  httpPath="";//页面url用于组装页脚
//	private Map<String, String> addtionParamsMap = new HashMap<String, String>();
//	private StringBuilder addtionParamsSB = new StringBuilder();
//	private static final String PNKey = "pn";
//	
//	public void addURIParameter(String key, String value){
// 		addtionParamsMap.put(key, value);
// 	}
// 	public void removeURIParameter(String key){
// 		if(addtionParamsMap.containsKey(key)){
// 			addtionParamsMap.remove(key);
// 		}
// 	}
// 	public String getParameter(){
// 		if(addtionParamsSB.length() == 0){
// 			for(Map.Entry<String, String> each : addtionParamsMap.entrySet()){
// 	 			addtionParamsSB.append(each.getKey()).append("=").append(each.getValue()).append("&");
// 	 		}
// 		}
// 		return addtionParamsSB.toString();
// 	}
// 	
// 	public void setRequest(HttpServletRequest request){
// 		this.httpPath = request.getRequestURI();
// 		String query = request.getQueryString();
// 		if(query == null) return;
// 		String[] keyAndValueArray = StringUtils.split(query, "&");
// 		if(keyAndValueArray == null) return;
// 		for(String each : keyAndValueArray){
// 			String[] eachKeyAndValueArray = StringUtils.split(each, "=");
// 			String key = "";
// 			String value = "";
// 			if (eachKeyAndValueArray.length == 2) {
// 				key = eachKeyAndValueArray[0];
// 				value = eachKeyAndValueArray[1];
// 			}
// 			else if (eachKeyAndValueArray.length == 1) {
// 				key = eachKeyAndValueArray[0];
// 			}
// 			if (StringUtils.isNotEmpty(key)) {
// 				addtionParamsMap.put(key, value);
// 			}
// 		}
// 		removeURIParameter(PNKey);
// 	}
//	public String getHttpPath() {
//		return httpPath;
//	}
//	public void setHttpPath(String httpPath) {
//		this.httpPath = httpPath;
//	}
// 	
// 	
//}
