/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.plainsummary.vo;

import com.tmser.tr.plainsummary.bo.PlainSummary;

/**
 * <pre>
 *
 * </pre>
 *
 * @author wanzheng
 * @version $Id: PlainSummaryVo.java, v 1.0 Apr 12, 2015 3:32:05 PM wanzheng Exp $
 */
public class PlainSummaryVo extends PlainSummary{
	
	/**
	 *	已经查看
	 */
	public static final Integer IS_VIEW_VIEWED = 1;
	/**
	 * <pre>
	 *
	 * </pre>
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 审阅状态
	 */
	private Integer checkStatus;
	
	/**
	 * 编辑人姓名
	 */
	private String editName;
	
	/**
	 * 是否已经查看
	 */
	private Integer isViewed;
	
	/**
	 * 用户名称
	 */
	private String userName;

	public Integer getCheckStatus() {
		return checkStatus;
	}
	
	public void setCheckStatus(Integer checkStatus) {
		this.checkStatus = checkStatus;
	}

	public String getEditName() {
		return editName;
	}

	public void setEditName(String editName) {
		this.editName = editName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getIsViewed() {
		return isViewed;
	}

	public void setIsViewed(Integer isViewed) {
		this.isViewed = isViewed;
	}
	
	
}
