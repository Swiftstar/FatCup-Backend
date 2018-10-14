package com.fatcup.backend.response;

public class Check {

	final String uid;
	final Boolean isUser;
	
	public Check(String uid, Boolean isUser) {
		this.uid = uid;
		this.isUser = isUser;
	}
	
	public String getUid() {
		return this.uid;
	}
	
	public String getIsUser() {
		return this.isUser.toString();
	}
}
