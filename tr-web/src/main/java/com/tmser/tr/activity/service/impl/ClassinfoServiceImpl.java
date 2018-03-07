/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.activity.service.impl;


import com.tmser.tr.activity.service.ClassinfoService;
import com.tmser.tr.classapi.bo.ClassInfo;
import com.tmser.tr.classapi.dao.ClassInfoDao;
import com.tmser.tr.common.dao.BaseDAO;
import com.tmser.tr.common.service.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * <pre>
 * 直播课堂
 * </pre>
 */
@Service
@Transactional
public class ClassinfoServiceImpl extends AbstractService<ClassInfo, String> implements ClassinfoService {

	@Autowired
	private ClassInfoDao classInfoDao;
	@Override
	public BaseDAO<ClassInfo, String> getDAO() {
		return classInfoDao;
	}

}
