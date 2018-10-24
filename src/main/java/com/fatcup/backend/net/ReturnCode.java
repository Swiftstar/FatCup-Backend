package com.fatcup.backend.net;

public enum ReturnCode {
	OK(0),ERROR(500);

	private long value;

	private ReturnCode(long value) {
		this.value = value;
	}

	public long getValue() {
		return this.value;
	}

	public String getMsg(ReturnCode returnCode) {
		switch (returnCode) {
			case OK:
				return "OK";
			case ERROR:
				return "ERROR";
			default :
				return "";
		}
	}
}
