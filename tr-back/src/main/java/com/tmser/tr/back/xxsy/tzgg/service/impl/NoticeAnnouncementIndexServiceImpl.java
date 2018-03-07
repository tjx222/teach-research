/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.back.xxsy.tzgg.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmser.tr.annunciate.bo.JyAnnunciate;
import com.tmser.tr.back.xxsy.tzgg.service.NoticeAnnouncementIndexService;
import com.tmser.tr.common.dao.BaseDAO;
import com.tmser.tr.common.service.AbstractService;
import com.tmser.tr.ptgg.dao.NoticeAnnouncementDao;
/**
 * 平台公告---->首页广告 服务实现类
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: FlatformAnnouncement.java, v 1.0 2015-11-02 Generate Tools Exp $
 */
@Service
@Transactional
public class NoticeAnnouncementIndexServiceImpl extends AbstractService<JyAnnunciate, Integer> implements NoticeAnnouncementIndexService {

	@Autowired
	private NoticeAnnouncementDao noticeAnnouncementDao;
	@Override
	public BaseDAO<JyAnnunciate, Integer> getDAO() {
		return noticeAnnouncementDao;
	}
}
