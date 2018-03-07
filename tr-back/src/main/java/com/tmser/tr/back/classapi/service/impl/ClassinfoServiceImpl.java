/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.back.classapi.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmser.tr.back.classapi.service.ClassinfoService;
import com.tmser.tr.classapi.bo.ClassInfo;
import com.tmser.tr.classapi.dao.ClassInfoDao;
import com.tmser.tr.common.dao.BaseDAO;
import com.tmser.tr.common.service.AbstractService;

/**
 * <pre>
 * 直播课堂
 * </pre>
 */
@Service
@Transactional
public class ClassinfoServiceImpl extends AbstractService<ClassInfo, String>  implements ClassinfoService {

	@Autowired
	private ClassInfoDao classInfoDao;
	@Override
	public BaseDAO<ClassInfo, String> getDAO() {
		return classInfoDao;
	}
	@Override
	public List<ClassInfo> ktbftjResultByDay(ClassInfo model) {
		return classInfoDao.ktbftjResultByDay(model);
	}
	@Override
	public List<ClassInfo> ktbftjResult(ClassInfo model) {
		return classInfoDao.ktbftjResult(model);
	}
}
