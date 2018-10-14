package com.fatcup.backend.response;

public class Register {
	private final String errorCode;
	
	public Register(String errorCode) {
		this.errorCode = errorCode;
	}
	
	public String getErrorCode() {
		return errorCode;
	}
}
