/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.back.xxsy.schoolbanner.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmser.tr.back.xxsy.schoolbanner.service.SchoolBannerService;
import com.tmser.tr.common.dao.BaseDAO;
import com.tmser.tr.common.service.AbstractService;
import com.tmser.tr.manage.resources.service.ResourcesService;
import com.tmser.tr.uc.utils.CurrentUserContext;
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
public class SchoolBannerServiceImpl extends AbstractService<SchoolBanner, Integer> implements SchoolBannerService {

	@Autowired
	private SchoolBannerDao schoolBannerDao;
	@Autowired ResourcesService resourcesService;
	/**
	 * @return
	 * @see com.tmser.tr.common.service.BaseService#getDAO()
	 */
	@Override
	public BaseDAO<SchoolBanner, Integer> getDAO() {
		return schoolBannerDao;
	}

	@Override
	public void saveSchoolBanner(SchoolBanner schoolBanner) {
		// TODO Auto-generated method stub
		schoolBanner.setCrtDttm(new Date());
		schoolBanner.setCrtId(CurrentUserContext.getCurrentUserId());
		schoolBanner.setAttachsname(resourcesService.findOne(schoolBanner.getAttachs()).getName()+resourcesService.findOne(schoolBanner.getAttachs()).getExt());
		schoolBanner.setIsview(1);
		updateStatus(schoolBanner);
		schoolBannerDao.insert(schoolBanner);
	}

	@Override
	public void updateSchoolBanner(SchoolBanner schoolBanner) {
		// TODO Auto-generated method stub
		// 查看是否有三条数据是isview为1（显示状态）的
		updateStatus(schoolBanner);
		schoolBanner.setIsview(1);
		this.schoolBannerDao.update(schoolBanner);
	}
	
	/**
	 * 更新数据:显示状态>3,把最早数据置为非显示
	 */
	public void updateStatus(SchoolBanner schoolBanner){
		SchoolBanner oldSchoolBanner = new SchoolBanner();
		oldSchoolBanner.setIsview(1);
		oldSchoolBanner.setOrgId(schoolBanner.getOrgId());
		int count = 0;
		count = this.schoolBannerDao.count(oldSchoolBanner);
		if (count > 2) {
			oldSchoolBanner.addOrder("crtDttm");
			oldSchoolBanner = this.schoolBannerDao.getOne(oldSchoolBanner);
			oldSchoolBanner.setIsview(0);// 把日期最早的数据隐藏，新数据显示
			this.schoolBannerDao.update(oldSchoolBanner);
		}
	}
}
