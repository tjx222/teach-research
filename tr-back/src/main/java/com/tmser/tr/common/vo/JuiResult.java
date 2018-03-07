/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.common.vo;

import java.io.Serializable;

/**
 * <pre>
 * jui ajax 请求结果类
 * </pre>
 *
 * @author tmser
 * @version $Id: JuiResult.java, v 1.0 2015年8月21日 下午4:52:48 tmser Exp $
 */
@SuppressWarnings("serial")
public class JuiResult implements Serializable{
	
	public final static int SUCCESS = 200;
	
	public final static int FAILED = 300;
	
	public final static int TIME_OUT = 301;
	
	public final static String CB_CLOSE = "closeCurrent";
	
	public final static String FORWARD_CONFIRM = "forwardConfirm";
	
	public final static String CB_FORWARD = "forward";
	
	public final static String DEFAULT_SUC_MSG = "操作成功";
	
	/**
	 * 
	 */
	public JuiResult() {
		this.statusCode = SUCCESS;
		this.callbackType = CB_CLOSE;
	}
	
	public static final JuiResult forwardInstance(){
		JuiResult r = new JuiResult();
		r.setMessage(DEFAULT_SUC_MSG);
		r.setCallbackType(CB_FORWARD);
		r.setForwardUrl("");
		return r;
	}
	
	private String navTabId = "";
	
	private String rel;
	
	private String callbackType;
	
	private String forwardUrl;
	
	private Object data;
	
	private int statusCode;
	
	private String message;

	public String getNavTabId() {
		return navTabId;
	}

	public void setNavTabId(String navTabId) {
		this.navTabId = navTabId;
	}

	public String getRel() {
		return rel;
	}

	public void setRel(String rel) {
		this.rel = rel;
	}

	public String getCallbackType() {
		return callbackType;
	}

	public void setCallbackType(String callbackType) {
		this.callbackType = callbackType;
	}

	public String getForwardUrl() {
		return forwardUrl;
	}

	public void setForwardUrl(String forwardUrl) {
		this.forwardUrl = forwardUrl;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
