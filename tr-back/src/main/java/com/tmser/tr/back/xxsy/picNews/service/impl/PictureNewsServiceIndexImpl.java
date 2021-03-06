/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.back.xxsy.picNews.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmser.tr.back.xxsy.picNews.service.PictureNewsIndexService;
import com.tmser.tr.common.dao.BaseDAO;
import com.tmser.tr.common.service.AbstractService;
import com.tmser.tr.ptgg.bo.PictureNews;
import com.tmser.tr.ptgg.dao.PictureNewsDao;
/**
 * 学校首页---->图片新闻 服务实现类
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: FlatformAnnouncement.java, v 1.0 2015-11-02 Generate Tools Exp $
 */
@Service
@Transactional
public class PictureNewsServiceIndexImpl extends AbstractService<PictureNews, Integer> implements PictureNewsIndexService {

	@Autowired
	private PictureNewsDao pictureNewsDao;
	@Override
	public BaseDAO<PictureNews, Integer> getDAO() {
		// TODO Auto-generated method stub
		return pictureNewsDao;
	}
}
