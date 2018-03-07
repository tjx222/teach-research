/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.back.classapi.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmser.tr.back.classapi.service.ClassvisitService;
import com.tmser.tr.classapi.bo.ClassVisit;
import com.tmser.tr.classapi.dao.ClassVisitDao;
import com.tmser.tr.common.dao.BaseDAO;
import com.tmser.tr.common.service.AbstractService;

/**
 * <pre>
 * 直播课堂用户
 * </pre>
 */
@Service
@Transactional
public class ClassvisitServiceImpl extends AbstractService<ClassVisit, Integer> implements ClassvisitService {

	@Autowired
	private ClassVisitDao classVisitDao;

	@Override
	public BaseDAO<ClassVisit, Integer> getDAO() {
		return classVisitDao;
	}

	@Override
	public List<ClassVisit> yhjrsjResultByDay(ClassVisit model) {
		return classVisitDao.yhjrsjResultByDay(model);
	}

	@Override
	public List<ClassVisit> yhjrsjResult(ClassVisit model) {
		return classVisitDao.yhjrsjResult(model);
	}

}
