/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.activity.dao;

import java.util.List;

import com.tmser.tr.activity.bo.SchoolTeachCircleOrg;
import com.tmser.tr.common.dao.BaseDAO;

 /**
 * 校际教研圈附属机构 DAO接口
 * <pre>
 *
 * </pre>
 *
 * @author zpp
 * @version $Id: SchoolTeachCircleOrg.java, v 1.0 2015-05-12 zpp Exp $
 */
public interface SchoolTeachCircleOrgDao extends BaseDAO<SchoolTeachCircleOrg, Integer>{
	/**
	 * 获取 教研圈
	 * @param state
	 * @param orgId
	 * @param schoolYear  
	 * @return
	 * @see com.tmser.tr.TeachSchedule.dao.LessonPlanDao#getSchoolTeachCircle(java.lang.Integer, java.lang.Integer)
	 */
	 
	public List<SchoolTeachCircleOrg> getSchoolTeachCircle( Integer orgId, Integer schoolYear);
}