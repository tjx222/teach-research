/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.activity.dao.impl;

import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.tmser.tr.activity.bo.SchoolTeachCircleOrg;
import com.tmser.tr.activity.dao.SchoolTeachCircleOrgDao;
import com.tmser.tr.common.dao.AbstractDAO;

/**
 * 校际教研圈附属机构 Dao 实现类
 * 
 * <pre>
 *
 * </pre>
 *
 * @author tmser
 * @version $Id: SchoolTeachCircleOrg.java, v 1.0 2015-05-12 tmser Exp $
 */
@Repository
public class SchoolTeachCircleOrgDaoImpl extends AbstractDAO<SchoolTeachCircleOrg, Integer> implements SchoolTeachCircleOrgDao {
	/**
	 * 获取 教研圈
	 * 
	 * @param state
	 * @param orgId
	 * @param schoolYear
	 *            资源类型
	 * @return
	 * @see com.tmser.tr.TeachSchedule.dao.LessonPlanDao#getSchoolTeachCircle(java.lang.Integer,
	 *      java.lang.Integer)
	 */

	public List<SchoolTeachCircleOrg> getSchoolTeachCircle(Integer orgId, Integer schoolYear) {
		String sql = "";
		Object[] args = null;
		sql = "select * from SchoolTeachCircleOrg where (state = ? or state=?)  and schoolYear = ? and orgId = ?  group by stcId ";
		args = new Object[] { SchoolTeachCircleOrg.YI_TONG_YI, SchoolTeachCircleOrg.YI_HUI_FU, schoolYear, orgId };
		RowMapper<SchoolTeachCircleOrg> rowMapper = getMapper();
		List<SchoolTeachCircleOrg> sList = query(sql, args, rowMapper);
		return sList;
	}

}
