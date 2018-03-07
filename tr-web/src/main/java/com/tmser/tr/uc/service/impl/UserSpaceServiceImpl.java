/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.uc.service.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmser.tr.common.dao.BaseDAO;
import com.tmser.tr.common.service.AbstractService;
import com.tmser.tr.common.utils.WebThreadLocalUtils;
import com.tmser.tr.uc.bo.UserSpace;
import com.tmser.tr.uc.dao.UserSpaceDao;
import com.tmser.tr.uc.service.UserSpaceService;
import com.tmser.tr.uc.utils.SessionKey;
/**
 * 用户空间信息 服务实现类
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: UserSpace.java, v 1.0 2015-02-03 Generate Tools Exp $
 */
@Service
@Transactional
public class UserSpaceServiceImpl extends AbstractService<UserSpace, Integer> implements UserSpaceService {

	@Autowired
	private UserSpaceDao userSpaceDao;
	
	/**
	 * @return
	 * @see com.tmser.tr.common.service.BaseService#getDAO()
	 */
	@Override
	public BaseDAO<UserSpace, Integer> getDAO() {
		return userSpaceDao;
	}

	/**
	 * @param userid
	 * @param schoolYear
	 * @return
	 * @see com.tmser.tr.uc.service.UserSpaceService#listUserSpaceBySchoolYear(java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public List<UserSpace> listUserSpaceBySchoolYear(Integer userid,
			Integer schoolYear) {
		if(userid == null || schoolYear == null){
			return Collections.emptyList();
		}
		UserSpace userSpace = new UserSpace();
		userSpace.setUserId(userid);
		userSpace.setSchoolYear(schoolYear);
		userSpace.setEnable(UserSpace.ENABLE);
		userSpace.addOrder("sysRoleId");
		return userSpaceDao.listAll(userSpace);
	}

	/**
	 * 获取用户的某个身份空间
	 * @param userId
	 * @param grade
	 * @param subject
	 * @see com.tmser.tr.uc.service.UserSpaceService#getUserSpace(java.lang.Integer, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public UserSpace getUserSpace(Integer userId,Integer role, Integer grade, Integer subject) {
		Integer schoolYear = (Integer)WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SCHOOLYEAR);
		UserSpace us = new UserSpace();
		us.setUserId(userId);
		us.setSysRoleId(role);
		us.setSubjectId(subject);
		us.setGradeId(grade);
		us.setSchoolYear(schoolYear);
		us.setEnable(UserSpace.ENABLE);
		return userSpaceDao.getOne(us);
	}
}
