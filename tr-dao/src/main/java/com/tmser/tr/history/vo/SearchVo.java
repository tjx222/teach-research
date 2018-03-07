/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.history.vo;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.tmser.tr.common.bo.QueryObject;

/**
 * <pre>
 *  查询vo
 * </pre>
 *
 * @author wangdawei
 * @version $Id: SearchVo.java, v 1.0 2016年5月25日 下午5:00:25 wangdawei Exp $
 */
public class SearchVo extends QueryObject{

	/**
	 * <pre>
	 *
	 * </pre>
	 */
	private static final long serialVersionUID = -2183693133343033337L;
	
	private Integer userId;//用户ID
	
	private Integer schoolYear;//学年
	
	private Integer spaceId;//身份ID
	
	private Integer termId;//学期ID
	
	private String searchStr; //搜索名称字符串
	
	private Integer typeId; //资源类型
	
	private String startTime;
	
	private String endTime;
	
	
	/** 
	 * Getter method for property <tt>userId</tt>. 
	 * @return property value of userId 
	 */
	public Integer getUserId() {
		return userId;
	}
	/**
	 * Setter method for property <tt>userId</tt>.
	 * @param userId value to be assigned to property userId
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	/** 
	 * Getter method for property <tt>startTime</tt>. 
	 * @return property value of startTime 
	 */
	public String getStartTime() {
		return startTime;
	}
	/**
	 * Setter method for property <tt>startTime</tt>.
	 * @param startTime value to be assigned to property startTime
	 */
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	/** 
	 * Getter method for property <tt>endTime</tt>. 
	 * @return property value of endTime 
	 */
	public String getEndTime() {
		return endTime;
	}
	/**
	 * Setter method for property <tt>endTime</tt>.
	 * @param endTime value to be assigned to property endTime
	 */
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	/** 
	 * Getter method for property <tt>typeId</tt>. 
	 * @return property value of typeId 
	 */
	public Integer getTypeId() {
		return typeId;
	}
	/**
	 * Setter method for property <tt>typeId</tt>.
	 * @param typeId value to be assigned to property typeId
	 */
	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}
	/** 
	 * Getter method for property <tt>schoolYear</tt>. 
	 * @return property value of schoolYear 
	 */
	public Integer getSchoolYear() {
		return schoolYear;
	}
	/**
	 * Setter method for property <tt>schoolYear</tt>.
	 * @param schoolYear value to be assigned to property schoolYear
	 */
	public void setSchoolYear(Integer schoolYear) {
		this.schoolYear = schoolYear;
	}
	/** 
	 * Getter method for property <tt>spaceId</tt>. 
	 * @return property value of spaceId 
	 */
	public Integer getSpaceId() {
		return spaceId;
	}
	/**
	 * Setter method for property <tt>spaceId</tt>.
	 * @param spaceId value to be assigned to property spaceId
	 */
	public void setSpaceId(Integer spaceId) {
		this.spaceId = spaceId;
	}
	/** 
	 * Getter method for property <tt>termId</tt>. 
	 * @return property value of termId 
	 */
	public Integer getTermId() {
		return termId;
	}
	/**
	 * Setter method for property <tt>termId</tt>.
	 * @param termId value to be assigned to property termId
	 */
	public void setTermId(Integer termId) {
		this.termId = termId;
	}
	/** 
	 * Getter method for property <tt>searchStr</tt>. 
	 * @return property value of searchStr 
	 */
	public String getSearchStr() {
		return searchStr;
	}
	/**
	 * Setter method for property <tt>searchStr</tt>.
	 * @param searchStr value to be assigned to property searchStr
	 */
	public void setSearchStr(String searchStr) {
		this.searchStr = searchStr;
	}
	

	@Override
	public boolean equals(final Object other) {
			if (!(other instanceof SearchVo))
				return false;
			SearchVo castOther = (SearchVo) other;
			return new EqualsBuilder().append(userId, castOther.userId)
					.append(schoolYear, castOther.schoolYear)
					.append(endTime, castOther.endTime)
					.append(typeId, castOther.typeId)
					.append(spaceId, castOther.spaceId)
					.append(startTime, castOther.startTime)
					.append(termId, castOther.termId)
					.append(searchStr, castOther.searchStr).isEquals();
	}

	@Override
	public int hashCode() {
			return new HashCodeBuilder().append(userId)
					.append(schoolYear)
					.append(endTime)
					.append(typeId)
					.append(spaceId)
					.append(startTime)
					.append(termId)
					.append(searchStr)
					.toHashCode();
	}
	
}
