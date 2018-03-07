/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.back.ptgg.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmser.tr.back.ptgg.service.FlatformAnnouncementService;
import com.tmser.tr.common.dao.BaseDAO;
import com.tmser.tr.common.service.AbstractService;
import com.tmser.tr.manage.resources.service.ResourcesService;
import com.tmser.tr.ptgg.bo.FlatformAnnouncement;
import com.tmser.tr.ptgg.dao.FlatformAnnouncementDao;
import com.tmser.tr.uc.utils.CurrentUserContext;
/**
 * 平台公告---->首页广告 服务实现类
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: FlatformAnnouncement.java, v 1.0 2015-09-28 Generate Tools Exp $
 */
@Service
@Transactional
public class FlatforAnnouncementServiceImpl extends AbstractService<FlatformAnnouncement, Integer> implements FlatformAnnouncementService {
	@Autowired
	private ResourcesService resourcesService;
	@Autowired
	private FlatformAnnouncementDao FlatformAnnouncementDao;
	
	/**
	 * @return
	 * @see com.tmser.tr.common.service.BaseService#getDAO()
	 */
	@Override
	public BaseDAO<FlatformAnnouncement, Integer> getDAO() {
		return FlatformAnnouncementDao;
	}

	@Override
	public void saveHomeAds(FlatformAnnouncement flatformAnnouncement) {
		// TODO Auto-generated method stub
		flatformAnnouncement.setCdate(new Date());
		flatformAnnouncement.setUserid(CurrentUserContext.getCurrentUserId());
		flatformAnnouncement.setUsername(CurrentUserContext.getCurrentUser().getName());
		flatformAnnouncement.setPictureName(resourcesService.findOne(flatformAnnouncement.getPictureid()).getName()+resourcesService.findOne(flatformAnnouncement.getPictureid()).getExt());
		flatformAnnouncement.setLittlepictureName(resourcesService.findOne(flatformAnnouncement.getLittlepictureId()).getName()+resourcesService.findOne(flatformAnnouncement.getPictureid()).getExt());
		flatformAnnouncement.setIsview(0);
		this.save(flatformAnnouncement);
	}

	@Override
	public void updatePic(FlatformAnnouncement flatformAnnouncement) {
		// TODO Auto-generated method stub
			//隐藏其他照片
			FlatformAnnouncement flatformAnnouncementHid = new FlatformAnnouncement();
			flatformAnnouncementHid.setIsview(1);//显示
			List<FlatformAnnouncement> list = this.FlatformAnnouncementDao.listAll(flatformAnnouncementHid);
			for(int i = 0;i<list.size();i++){
				list.get(i).setIsview(0);
				this.update(list.get(i));
			}
			flatformAnnouncement.setIsview(1);//设置显示当前图片
			//显示当前照片
			this.update(flatformAnnouncement);
	}
}
