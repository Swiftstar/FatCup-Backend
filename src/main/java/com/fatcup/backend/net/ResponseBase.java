package com.fatcup.backend.net;

import java.util.HashMap;
import java.util.Map;

public class ResponseBase {
	
	long returnCode;
	String returnMessage;
	Map<String, Object> data;
	
	public ResponseBase() {
		returnCode = 0;
		returnMessage = "";
		data = null;
	}
	
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
	public void Set(Map<String, Object> map) {	
		data = map;
	}
	
	public Object Get(String key) {
		if ( data == null )
			return null;
		return data.get(key);
	}
	
}
