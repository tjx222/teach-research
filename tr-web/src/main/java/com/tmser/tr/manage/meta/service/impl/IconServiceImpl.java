/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.manage.meta.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import com.tmser.tr.common.dao.BaseDAO;
import com.tmser.tr.manage.meta.bo.Icon;
import com.tmser.tr.common.service.AbstractService;
import com.tmser.tr.manage.meta.service.IconService;
import com.tmser.tr.manage.meta.dao.IconDao;
/**
 * 系统图标资源 服务实现类
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: Icon.java, v 1.0 2015-02-11 Generate Tools Exp $
 */
@Service
@Transactional
public class IconServiceImpl extends AbstractService<Icon, Integer> implements IconService {

	@Autowired
	private IconDao iconDao;
	
	/**
	 * @return
	 * @see com.tmser.tr.common.service.BaseService#getDAO()
	 */
	@Override
	public BaseDAO<Icon, Integer> getDAO() {
		return iconDao;
	}

	/**
	 * @param identity
	 * @return
	 * @see com.tmser.tr.manage.meta.service.IconService#findByIdentity(java.lang.String)
	 */
	@Override
	public Icon findByIdentity(String identity) {
		if(StringUtils.isEmpty(identity)){
			return null;
		}
		Icon model = new Icon();
		model.setIdentity(identity);
		return iconDao.getOne(model);
	}

}
