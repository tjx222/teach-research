/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.plainsummary.vo;

import java.io.Serializable;

import com.tmser.tr.uc.bo.User;

/**
 * <pre>
 *	计划总结查阅统计vo
 * </pre>
 *
 * @author wanzheng
 * @version $Id: JyPlainSummaryCheckVo.java, v 1.0 Apr 10, 2015 10:08:49 AM wanzheng Exp $
 */
public class PlainSummaryCheckStatisticsVo implements Serializable{

	/**
	 * <pre>
	 *
	 * </pre>
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 用户信息
	 */
	private User user;
	
	/**
	 * 工作空间id
	 */
	private Integer userSpaceId;
	
	/**
	 * 用户id
	 */
	private Integer userId;
	
	/**
	 * 撰写计划数目
	 */
	private Integer plainNum;
	
	/**
	 * 撰写总结数目
	 */
	private Integer summaryNum;
	
	/**
	 * 计划提交数目
	 */
	private Integer plainSubmitNum;
	
	/**
	 * 总结提交数目
	 */
	private Integer summarySubmitNum;
	
	/**
	 * 计划已查阅的数目
	 */
	public Integer plainCheckedNum;
	
	/**
	 * 总结已查阅的数目
	 */
	private Integer summaryCheckedNum;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Integer getPlainNum() {
		return plainNum==null?0:plainNum;
	}

	public void setPlainNum(Integer plainNum) {
		this.plainNum = plainNum;
	}

	public Integer getSummaryNum() {
		return summaryNum==null?0:summaryNum;
	}

	public void setSummaryNum(Integer summaryNum) {
		this.summaryNum = summaryNum;
	}

	public Integer getPlainSubmitNum() {
		return plainSubmitNum==null?0:plainSubmitNum;
	}

	public void setPlainSubmitNum(Integer plainSubmitNum) {
		this.plainSubmitNum = plainSubmitNum;
	}

	public Integer getSummarySubmitNum() {
		return summarySubmitNum==null?0:summarySubmitNum;
	}

	public void setSummarySubmitNum(Integer summarySubmitNum) {
		this.summarySubmitNum = summarySubmitNum;
	}

	public Integer getPlainCheckedNum() {
		return plainCheckedNum==null?0:plainCheckedNum;
	}

	public void setPlainCheckedNum(Integer plainCheckedNum) {
		this.plainCheckedNum = plainCheckedNum;
	}

	public Integer getSummaryCheckedNum() {
		return summaryCheckedNum==null?0:summaryCheckedNum;
	}

	public void setSummaryCheckedNum(Integer summaryCheckedNum) {
		this.summaryCheckedNum = summaryCheckedNum;
	}
	
	public Integer getUserSpaceId() {
		return userSpaceId;
	}

	public void setUserSpaceId(Integer userSpaceId) {
		this.userSpaceId = userSpaceId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
}

