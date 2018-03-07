/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.webservice.vo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 *  采集结果数据类
 * </pre>
 *
 * @author zpp
 * @version $Id: DataResult.java, v 1.0 2016年3月1日 下午4:09:56 zpp Exp $
 */
public class DataResult implements Serializable{
	
	/**
	 * <pre>
	 *
	 * </pre>
	 */
	private static final long serialVersionUID = 3822401223275328193L;

	/**
	 * 错误代码
	 */
	private int errcode;
	
	/**
	 * 错误信息
	 */
	private String msg;
	
	/**
	 * 采集的数
	 */
	private Integer data;
	
	/**
	 * 采集数据详情
	 */
	private List<Map<String,Object>> detail;

	public int getErrcode() {
		return errcode;
	}

	public void setErrcode(int errcode) {
		this.errcode = errcode;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Integer getData() {
		return data;
	}

	public void setData(Integer data) {
		this.data = data;
	}

	public List<Map<String, Object>> getDetail() {
		return detail;
	}

	public void setDetail(List<Map<String, Object>> detail) {
		this.detail = detail;
	}
	

}
