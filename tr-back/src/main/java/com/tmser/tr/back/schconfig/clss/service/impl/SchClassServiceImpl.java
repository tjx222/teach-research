/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.back.schconfig.clss.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmser.tr.back.schconfig.clss.service.SchClassService;
import com.tmser.tr.common.dao.BaseDAO;
import com.tmser.tr.common.service.AbstractService;
import com.tmser.tr.schconfig.clss.bo.SchClass;
import com.tmser.tr.schconfig.clss.dao.SchClassDao;
import com.tmser.tr.schconfig.clss.dao.SchClassUserDao;
/**
 * 学校班级 服务实现类
 * <pre>
 *
 * </pre>
 *
 * @author tmser
 * @version $Id: SchClass.java, v 1.0 2016-02-19 tmser Exp $
 */
@Service
@Transactional
public class SchClassServiceImpl extends AbstractService<SchClass, Integer> implements SchClassService {

	@Autowired
	private SchClassDao schClassDao;
	
	@Autowired
	private SchClassUserDao schClassUserDao;
	
	/**
	 * @return
	 * @see com.tmser.tr.common.service.BaseService#getDAO()
	 */
	@Override
	public BaseDAO<SchClass, Integer> getDAO() {
		return schClassDao;
	}

	/**
	 * @param clsid
	 * @see com.tmser.tr.back.schconfig.clss.service.SchClassService#realDelete(java.lang.Integer)
	 */
	@Override
	public void realDelete(Integer clsid) {
		this.delete(clsid);
		schClassUserDao.deleteByClassId(clsid);
	}

}
