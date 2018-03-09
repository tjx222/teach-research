/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.activity.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.tmser.tr.activity.bo.SchoolActivity;
import com.tmser.tr.activity.dao.SchoolActivityDao;
import com.tmser.tr.common.ResTypeConstants;
import com.tmser.tr.common.dao.AbstractDAO;
import com.tmser.tr.uc.bo.User;
import com.tmser.tr.uc.bo.UserSpace;

/**
 * 校际教研活动表 Dao 实现类
 * 
 * <pre>
 *
 * </pre>
 *
 * @author tmser
 * @version $Id: SchoolActivity.java, v 1.0 2015-05-20 tmser Exp $
 */
@Repository
public class SchoolActivityDaoImpl extends AbstractDAO<SchoolActivity, Integer> implements SchoolActivityDao {

	/**
	 * 查询用户作为临时的专家
	 * 
	 * @param userName
	 * @return
	 * @see com.tmser.tr.activity.dao.SchoolActivityDao#findUserObjectByName(java.lang.String)
	 */
	@Override
	public List<Map<String, Object>> findUserObjectByName(String userName) {
		String sql = "select u.id as id,u.name as name,o.name as orgName from User u,Organization o where u.orgId=o.id and u.name like '%?%' and u.userType=?";
		List<Map<String, Object>> queryByNamedSql = query(sql, new Object[] { userName, User.EXPERT_USER });
		return queryByNamedSql;
	}

	/**
	 * 通过专家ids 查询专家用户
	 * 
	 * @param expertIds
	 * @return
	 * @see com.tmser.tr.activity.dao.SchoolActivityDao#findUserObjectByIds(java.lang.String)
	 */
	@Override
	public List<Map<String, Object>> findUserObjectByIds(String expertIds) {
		if (expertIds.length() > 2) {
			expertIds = expertIds.substring(1, expertIds.length() - 1);
			String sql = "select u.id as id,u.name as name,o.name as orgName from User u,Organization o where u.orgId=o.id and u.id in (?) ";
			return query(sql, new Object[] { expertIds });
		} else {
			return null;
		}
	}

	/**
	 * @param userSpace
	 * @param schoolYear
	 * @return
	 * @see com.tmser.tr.activity.dao.SchoolActivityDao#countSchoolActivity(com.tmser.tr.uc.bo.UserSpace, java.lang.Integer)
	 */
	@Override
	public Integer countSchoolActivity(UserSpace userSpace, Integer schoolYear,Integer term) {
		// TODO Auto-generated method stub
		String sql;
		Object[] args;
		if (term != null) {
			sql = "select count(distinct a.id) from SchoolActivity a right join Discuss b on a.id=b.activityId and a.status=? and b.typeId= ? and a.term=? and a.orgId = ?  and a.phaseId= ? and a.schoolYear= ?" ;
			args = new Object[] { SchoolActivity.ENABLE,ResTypeConstants.SCHOOLTEACH,term,userSpace.getOrgId(),userSpace.getPhaseId(), schoolYear};
		} else {
			sql = "select count(distinct a.id) from SchoolActivity a right join Discuss b on a.id=b.activityId and a.status=? and b.typeId= ? and a.orgId = ?  and a.phaseId= ? and a.schoolYear= ?"
					+ schoolYear;
			args = new Object[] { SchoolActivity.ENABLE,ResTypeConstants.SCHOOLTEACH, userSpace.getOrgId(),userSpace.getPhaseId(),schoolYear };
		}
		return count(sql, args);
	}

	/**
	 * @param userSpace
	 * @param organization
	 * @param schoolYear
	 * @return
	 * @see com.tmser.tr.activity.dao.SchoolActivityDao#countJoinSchoolActivity(com.tmser.tr.uc.bo.UserSpace, com.tmser.tr.manage.org.bo.Organization, java.lang.Integer)
	 */
	@Override
	public Integer countJoinSchoolActivity(UserSpace userSpace, Integer schoolYear) {
		// TODO Auto-generated method stub
		String sql = "select count(distinct a.id) from SchoolActivity a right join Discuss b on a.id=b.activityId and a.status=? and b.typeId=? and a.orgId = ? and a.phaseId= ? and a.schoolYear= ?";
		Object[] args = new Object[] { SchoolActivity.ENABLE,ResTypeConstants.SCHOOLTEACH,userSpace.getOrgId(),userSpace.getPhaseId(),schoolYear};
		return count(sql, args);
	}

}
