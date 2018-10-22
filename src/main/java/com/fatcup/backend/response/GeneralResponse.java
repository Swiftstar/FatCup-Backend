package com.fatcup.backend.response;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

public class GeneralResponse {

	final ObjectMapper objectMapper = new ObjectMapper();
	
	long returnCode;
	String returnMessage;
	Map<String, Object> data;
	
	public long getReturnCode() {
		return returnCode;
	}
	public void setReturnCode(ReturnCode returnCode) {
		this.returnCode = returnCode.getValue();
		this.returnMessage = returnCode.getMsg(returnCode);
	}
	public void setReturnCode(long returnCode) {
		this.returnCode = returnCode;
	}

	public String getReturnMessage() {
		return returnMessage;
	}
	public void setReturnMessage(String returnMessage) {
		this.returnMessage = returnMessage;
	}
	
	public void setData( Map<String, Object> map ) {
		this.data = map;
	}
	public Map<String, Object> getData() {
		return this.data;
	}
	
	public void Set(String key, Object value) {
		if ( data == null )
			data = new HashMap<String, Object>();
		Map<String, Object> map = data;
		map.put(key, value);
	}
	
	public Object Get(String key) {
		if ( data == null )
			return null;
		return data.get(key);
	}
	
}
