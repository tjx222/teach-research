/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.uc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import com.tmser.tr.common.dao.BaseDAO;
import com.tmser.tr.uc.bo.App;
import com.tmser.tr.common.service.AbstractService;
import com.tmser.tr.uc.service.AppService;
import com.tmser.tr.uc.dao.AppDao;
/**
 * 应用信息 服务实现类
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: App.java, v 1.0 2016-01-11 Generate Tools Exp $
 */
@Service
@Transactional
public class AppServiceImpl extends AbstractService<App, Integer> implements AppService {

	@Autowired
	private AppDao appDao;
	
	/**
	 * @return
	 * @see com.tmser.tr.common.service.BaseService#getDAO()
	 */
	@Override
	public BaseDAO<App, Integer> getDAO() {
		return appDao;
	}

}
