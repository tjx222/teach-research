/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.webservice.vo;

import java.io.Serializable;

/**
 * <pre>
 *
 * </pre>
 *
 * @author zpp
 * @version $Id: AAA.java, v 1.0 2016年3月1日 下午3:28:18 zpp Exp $
 */
public class JxParams implements Serializable{
	private static final long serialVersionUID = 3822401223275328194L;
	/**
	 * 用户ID
	 */
	private Integer userId;
	/**
	 * 机构ID
	 */
	private Integer orgId;
	/**
	 * 开始时间
	 */
	private String beginTime;
	/**
	 * 结束时间
	 */
	private String endTime;
	/**
	 * 学段
	 */
	private Integer phaseId;
	/*
	 * 是否是学期
	 */
	private Integer isTerm;//1学年 2学期
	
	private Integer term;//0上学期 1下学期
	public Integer getUserId() {
		return userId;
	}
	public Integer getTerm() {
		return term;
	}
	public void setTerm(Integer term) {
		this.term = term;
	}
	public Integer getIsTerm() {
		return isTerm;
	}
	public void setIsTerm(Integer isTerm) {
		this.isTerm = isTerm;
	}
	public Integer getPhaseId() {
		return phaseId;
	}
	public void setPhaseId(Integer phaseId) {
		this.phaseId = phaseId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getOrgId() {
		return orgId;
	}
	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}
	public String getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
	
}
