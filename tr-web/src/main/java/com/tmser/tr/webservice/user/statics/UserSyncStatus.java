package com.tmser.tr.webservice.user.statics;

public enum UserSyncStatus {
	fail("error",-1);
	private int code;
	private String msg;
	UserSyncStatus(String msg, int code) {
		this.code=code;
		this.msg=msg;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
}
