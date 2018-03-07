/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.uc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmser.tr.common.dao.BaseDAO;
import com.tmser.tr.common.service.AbstractService;
import com.tmser.tr.uc.bo.AppParam;
import com.tmser.tr.uc.dao.AppParamDao;
import com.tmser.tr.uc.service.AppParamService;

/**
 * app_param 服务实现类
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: AppParam.java, v 1.0 2016-08-03 Generate Tools Exp $
 */
@Service
@Transactional
public class AppParamServiceImpl extends AbstractService<AppParam, Integer> implements AppParamService {

	@Autowired
	private AppParamDao appParamDao;
	
	/**
	 * @return
	 * @see com.tmser.tr.common.service.BaseService#getDAO()
	 */
	@Override
	public BaseDAO<AppParam, Integer> getDAO() {
		return appParamDao;
	}

}
