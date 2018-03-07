package com.tmser.tr.activity.vo;

import java.util.List;

import com.tmser.tr.activity.bo.SchoolTeachCircle;
import com.tmser.tr.activity.bo.SchoolTeachCircleOrg;

public class SchoolTeachCircleVo extends SchoolTeachCircle{

	/**
	 * <pre>
	 *	serialVersionUID
	 * </pre>
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 参加校际教研圈的机构
	 */
	private List<SchoolTeachCircleOrg> orgs;

	public List<SchoolTeachCircleOrg> getOrgs() {
		return orgs;
	}

	public void setOrgs(List<SchoolTeachCircleOrg> orgs) {
		this.orgs = orgs;
	}
}
