/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.indexPicAds.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmser.tr.common.dao.BaseDAO;
import com.tmser.tr.common.service.AbstractService;
import com.tmser.tr.indexPicAds.IndexPicAdsService;
import com.tmser.tr.ptgg.bo.FlatformAnnouncement;
import com.tmser.tr.ptgg.dao.FlatformAnnouncementDao;
/**
 * 用户反馈信息 服务实现类
 * <pre>
 *@author lijianghu 
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: Recieve.java, v 1.0 2015-09-15 Generate Tools Exp $
 */
@Service
@Transactional
public class IndexPicAdsServiceImpl extends AbstractService<FlatformAnnouncement, Integer> implements IndexPicAdsService {
	@Autowired
	private FlatformAnnouncementDao flatformAnnouncementDao;
	@Override
	public BaseDAO<FlatformAnnouncement, Integer> getDAO() {
		return flatformAnnouncementDao;
	}

	
}
