/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.back.classapi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmser.tr.back.classapi.service.ClassuserService;
import com.tmser.tr.classapi.bo.ClassUser;
import com.tmser.tr.classapi.dao.ClassUserDao;
import com.tmser.tr.common.dao.BaseDAO;
import com.tmser.tr.common.service.AbstractService;

/**
 * <pre>
 * 直播课堂用户
 * </pre>
 */
@Service
@Transactional
public class ClassuserServiceImpl extends AbstractService<ClassUser, Integer> implements ClassuserService {

	@Autowired
	private ClassUserDao classUserDao;
	@Override
	public BaseDAO<ClassUser, Integer> getDAO() {
		return classUserDao;
	}

}
