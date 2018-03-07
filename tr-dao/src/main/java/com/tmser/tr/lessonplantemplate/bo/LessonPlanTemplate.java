package com.tmser.tr.lessonplantemplate.bo;



import javax.persistence.*;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.tmser.tr.common.bo.BaseObject;

/**
 * 教案模板 Entity
 * @author wangdawei
 * @version 1.0
 * 2015-01-29
 */
@SuppressWarnings("serial")
@Entity
@Table(name = LessonPlanTemplate.TABLE_NAME)
public class LessonPlanTemplate extends BaseObject {
	public static final String TABLE_NAME="lesson_plan_template";
	
		/**
	 *模板id
	 **/
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="tp_id")
	private Integer tpId;

	/**
	 *模板名称
	 **/
	@Column(name="tp_name",nullable=false)
	private String tpName;

	/**
	 *机构id
	 **/
	@Column(name="org_id")
	private Integer orgId;

	/**
	 * 机构名称
	 */
	@Column(name="org_name")
	private String orgName;
	
	/**
	 *对应资源id
	 **/
	@Column(name="res_id")
	private String resId;

	/**
	 *是否默认（1：默认，0：不默认）
	 **/
	@Column(name="tp_isDefault")
	private Integer tpIsdefault;

	/**
	 * 模板类型（0：系统提供，1：学校提供）
	 */
	@Column(name="tp_type")
	private Integer tpType;
	
	
	/**
	 * 模板显示排序
	 */
	@Column(name="sort")
	private Integer sort;
	
	/**
	 * 适用的学段id
	 */
	@Column(name="phaseIds")
	private String phaseIds;
	
	/**
	 * 适用的学段名称
	 */
	@Column(name="phaseNames")
	private String phaseNames;
	
	/**
	 * 模板图标
	 */
	@Column(name="ico")
	private String ico;

	public Integer getOrgId() {
		return orgId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	public Integer getTpType() {
		return tpType;
	}

	public void setTpType(Integer tpType) {
		this.tpType = tpType;
	}

	public void setTpId(Integer tpId){
		this.tpId = tpId;
	}

	public Integer getTpId(){
		return this.tpId;
	}

	public void setTpName(String tpName){
		this.tpName = tpName;
	}

	public String getTpName(){
		return this.tpName;
	}

	public void setResId(String resId){
		this.resId = resId;
	}

	public String getResId(){
		return this.resId;
	}

	public void setTpIsdefault(Integer tpIsdefault){
		this.tpIsdefault = tpIsdefault;
	}

	public Integer getTpIsdefault(){
		return this.tpIsdefault;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getIco() {
		return ico;
	}

	public void setIco(String ico) {
		this.ico = ico;
	}

	public String getPhaseIds() {
		return phaseIds;
	}

	public void setPhaseIds(String phaseIds) {
		this.phaseIds = phaseIds;
	}

	public String getPhaseNames() {
		return phaseNames;
	}

	public void setPhaseNames(String phaseNames) {
		this.phaseNames = phaseNames;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	@Override
	public boolean equals(final Object other) {
			if (!(other instanceof LessonPlanTemplate))
				return false;
			LessonPlanTemplate castOther = (LessonPlanTemplate) other;
			return new EqualsBuilder().append(tpId, castOther.tpId).isEquals();
	}

	@Override
	public int hashCode() {
			return new HashCodeBuilder().append(tpId).toHashCode();
	}
}


