package com.stanley.wang.model;

public class HelloWorld {
	
	private String 	message = null;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public HelloWorld withMessage(String message) {
		this.message = message;
		
		return this;
	}
}
