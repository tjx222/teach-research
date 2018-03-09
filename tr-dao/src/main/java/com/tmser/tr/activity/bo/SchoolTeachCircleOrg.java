/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.activity.bo;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.tmser.tr.common.bo.BaseObject;

 /**
 * 校际教研圈附属机构 Entity
 * <pre>
 *
 * </pre>
 *
 * @author tmser
 * @version $Id: SchoolTeachCircleOrg.java, v 1.0 2015-05-12 tmser Exp $
 */
@SuppressWarnings("serial")
@Entity
@Table(name = SchoolTeachCircleOrg.TABLE_NAME)
public class SchoolTeachCircleOrg extends BaseObject {
	public static final String TABLE_NAME="school_teach_circle_org";
	/**
	 * 待接受
	 */
	public static final Integer DAI_JIE_SHOU = 1;
	
	/**
	 * 已同意
	 */
	public static final Integer YI_TONG_YI = 2;
	
	/**
	 * 已拒绝
	 */
	public static final Integer YI_JU_JUE = 3;
	
	/**
	 * 已退出
	 */
	public static final Integer YI_TUI_CHU = 4;
	
	/**
	 * 已恢复
	 */
	public static final Integer YI_HUI_FU = 5;
	
		/**
	 *校际教研圈附属表ID
	 **/
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;

	/**
	 *校际教研圈ID
	 **/
	@Column(name="stc_id")
	private Integer stcId;

	/**
	 *学校机构ID
	 **/
	@Column(name="org_id")
	private Integer orgId;
	
	/**
	 *学校机构名称
	 **/
	@Column(name="org_name")
	private String orgName;
	
	/**
	 *创建学年
	 **/
	@Column(name="school_year")
	private Integer schoolYear;

	/**
	 *1：待接收  2：已接受  3：已拒绝  4：已退出  5：已恢复
	 **/
	@Column(name="state")
	private Integer state;
	
	/**
	 *数据的排序值
	 **/
	@Column(name="sort")
	private Integer sort;
	

	public void setId(Integer id){
		this.id = id;
	}

	public Integer getId(){
		return this.id;
	}

	public Integer getStcId() {
		return stcId;
	}

	public void setStcId(Integer stcId) {
		this.stcId = stcId;
	}

	public void setState(Integer state){
		this.state = state;
	}

	public Integer getState(){
		return this.state;
	}
	
	public Integer getOrgId() {
		return orgId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Integer getSchoolYear() {
		return schoolYear;
	}

	public void setSchoolYear(Integer schoolYear) {
		this.schoolYear = schoolYear;
	}

	@Override
	public boolean equals(final Object other) {
			if (!(other instanceof SchoolTeachCircleOrg))
				return false;
			SchoolTeachCircleOrg castOther = (SchoolTeachCircleOrg) other;
			return new EqualsBuilder().append(id, castOther.id).isEquals();
	}

	@Override
	public int hashCode() {
			return new HashCodeBuilder().append(id).toHashCode();
	}
}


