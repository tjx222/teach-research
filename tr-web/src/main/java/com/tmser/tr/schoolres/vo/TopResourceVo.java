package com.tmser.tr.schoolres.vo;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.tmser.tr.browse.bo.BrowsingCount;
import com.tmser.tr.common.bo.BaseObject;

public class TopResourceVo extends BaseObject {
	/**
	 * 学校首页 热点排行视图bean
	 */
	private static final long serialVersionUID = 1L;
	//学校ID
	private Integer orgID;
	//资源统计信息（点击量）
	private BrowsingCount bc;
	//资源信息
	private Object resObj;
	
	public Integer getOrgID() {
		return orgID;
	}
	public void setOrgID(Integer orgID) {
		this.orgID = orgID;
	}
	public BrowsingCount getBc() {
		return bc;
	}
	public void setBc(BrowsingCount bc) {
		this.bc = bc;
	}
	public Object getResObj() {
		return resObj;
	}
	public void setResObj(Object obj) {
		this.resObj = obj;
	}
	
	@Override
	public boolean equals(final Object other) {
			if (!(other instanceof TopResourceVo))
				return false;
			TopResourceVo castOther = (TopResourceVo) other;
			return new EqualsBuilder().append(orgID, castOther.orgID)
					.append(bc, castOther.bc)
					.isEquals();
	}

	@Override
	public int hashCode() {
			return new HashCodeBuilder().append(orgID).append(bc).toHashCode();
	}
}
