package com.gooseeker.fss.commons.exception;

public class FssViewException extends Exception {
	
	private String message;
	
	public FssViewException(String message) {
		super();
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
