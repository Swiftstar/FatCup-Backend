package com.fatcup.backend.response;

public class register {
	private final String errorCode;
	
	public register(String errorCode) {
		this.errorCode = errorCode;
	}
	
	public String getErrorCode() {
		return errorCode;
	}
}
