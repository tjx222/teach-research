/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.schoolbanner.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmser.tr.common.dao.BaseDAO;
import com.tmser.tr.common.service.AbstractService;
import com.tmser.tr.schoolbanner.SchoolBannerServices;
import com.tmser.tr.xxsy.bannermanner.bo.SchoolBanner;
import com.tmser.tr.xxsy.bannermanner.dao.SchoolBannerDao;
/**
 * 学校横幅广告 服务实现类
 * <pre>
 *
 * </pre>
 *
 * @author tmser
 * @version $Id: SchoolBanner.java, v 1.0 2015-10-28 tmser Exp $
 */
@Service
@Transactional
public class SchoolBannerServicesImpl extends AbstractService<SchoolBanner, Integer> implements SchoolBannerServices {

	@Autowired
	private SchoolBannerDao schoolBannerDao;
	/**
	 * @return
	 * @see com.tmser.tr.common.service.BaseService#getDAO()
	 */
	@Override
	public BaseDAO<SchoolBanner, Integer> getDAO() {
		return schoolBannerDao;
	}
}
