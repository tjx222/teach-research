/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.activity.dao.impl;

import org.springframework.stereotype.Repository;

import com.tmser.tr.activity.bo.Activity;
import com.tmser.tr.activity.dao.ActivityDao;
import com.tmser.tr.common.dao.AbstractDAO;
import com.tmser.tr.uc.bo.UserSpace;

/**
 * 集体备课活动实体 Dao 实现类
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: Activity.java, v 1.0 2015-03-06 Generate Tools Exp $
 */
@Repository
public class ActivityDaoImpl extends AbstractDAO<Activity,Integer> implements ActivityDao {

	/**
	 * @param term
	 * @return
	 * @see com.tmser.tr.activity.dao.ActivityDao#countActivity(java.lang.Integer)
	 */
	@Override
	public Integer countActivity(UserSpace userSpace,Integer term,Integer schoolYear) {
		// TODO Auto-generated method stub
		String sql = "select count( distinct a.id) from Activity a right join ActivityDiscuss b on a.id=b.activityId and b.userId=? and a.term=?"
				+ "  and a.orgId= ? and a.phaseId= ? and a.status=? and a.schoolYear=?";
		Object[] args = new Object[] { userSpace.getUserId(),term, userSpace.getOrgId(),userSpace.getPhaseId(),Activity.ENABLE,schoolYear };
		return count(sql, args);
	}

	/**
	 * @param userSpace
	 * @param schoolYear
	 * @return
	 * @see com.tmser.tr.activity.dao.ActivityDao#countJoinActivity(com.tmser.tr.uc.bo.UserSpace, java.lang.Integer)
	 */
	@Override
	public Integer countJoinActivity(UserSpace userSpace, Integer schoolYear) {
		// TODO Auto-generated method stub

		String sql = "select count(distinct a.id) from Activity a right join ActivityDiscuss b on a.id=b.activityId and b.userId=  ? and a.orgId= ? and a.phaseId= ? and a.status=? and a.schoolYear=?";
		Object[] args = new Object[] { userSpace.getUserId(), userSpace.getOrgId(),userSpace.getPhaseId(),Activity.ENABLE,schoolYear};
		return count(sql, args);
	}

}
