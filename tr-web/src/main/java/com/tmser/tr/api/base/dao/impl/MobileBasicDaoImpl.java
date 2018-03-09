/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.api.base.dao.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.tmser.tr.api.base.dao.MobileBasicDao;
import com.tmser.tr.common.dao.AbstractDAO;
import com.tmser.tr.uc.SysRole;
import com.tmser.tr.uc.bo.UserSpace;

/**
 * <pre>
 * api基础信息dao层实现操作
 * </pre>
 *
 * @author tmser
 * @version $Id: MobileBasicDaoImpl.java, v 1.0 2016年4月19日 下午3:07:12 3020mt Exp
 *          $
 */
@Repository
public class MobileBasicDaoImpl extends AbstractDAO<UserSpace, Serializable> implements MobileBasicDao {

	/**
	 * 通过用户空间机构ID，学段ID，学年，获得用户信息
	 * 
	 * @param us
	 * @return
	 * @see com.tmser.tr.api.base.dao.MobileBasicDao#findUsersMap(com.tmser.tr.uc.bo.UserSpace)
	 */
	@Override
	public List<Map<String, Object>> findUsersMap(UserSpace us) {
		String sql = "select us.grade_id,us.subject_id,us.user_id,us.user_name,u.photo as user_photo,us.book_id as com_id from sys_user u,sys_user_space us "
				+ "where us.user_id=u.id and us.org_id=? and us.phase_id=? and us.school_year=? and us.sys_role_id=?";
		return super.query(sql, new Object[] { us.getOrgId(), us.getPhaseId(), us.getSchoolYear(),SysRole.TEACHER.getId() });
	}

}
