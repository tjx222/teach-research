/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.manage.meta.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmser.tr.manage.meta.MetaConstants;
import com.tmser.tr.manage.meta.bo.BookChapter;
import com.tmser.tr.manage.meta.dao.BookChapterDao;
import com.tmser.tr.manage.meta.service.BookChapterHerperService;
import com.tmser.tr.manage.meta.vo.BookLessonVo;

/**
 * <pre>
 *
 * </pre>
 *
 * @author zpp
 * @version $Id: MetaDataHerperServiceImpl.java, v 1.0 2015年2月3日 上午9:32:15 zpp
 *          Exp $
 */
@Service
@Transactional
public class BookChapterHerperServiceImpl implements BookChapterHerperService {

	@Autowired
	private BookChapterDao commdityBookChapterDao;

	@Resource(name = "cacheManger")
	private CacheManager cacheManager;

	/**
	 * @param comId
	 * @return
	 * @see com.tmser.tr.common.cacheService.BookChapterHerperService#getBookChapterByComId(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BookLessonVo> getBookChapterByComId(String comId) {
		List<BookLessonVo> list = new ArrayList<BookLessonVo>();
		if (org.apache.commons.lang3.StringUtils.isNotEmpty(comId)) {

			Cache cache = cacheManager.getCache(MetaConstants.BOOKCHAPTER_CACHE_NAME);
			Object element = cache.get(comId + "_list");
			if (element == null) {
				BookChapter model = new BookChapter();
				model.setComId(comId);
				model.addOrder("chapterLevel , order_num");
				model.addCustomCulomn("chapterId,chapterName,parentId,orderNum");
				List<BookChapter> listAll = commdityBookChapterDao.listAll(model);
				for (BookChapter cbc : listAll) {
					BookLessonVo bcv = new BookLessonVo();
					bcv.setLessonId(cbc.getChapterId());
					bcv.setLessonName(cbc.getChapterName());
					bcv.setComId(comId);
					bcv.setOrderNum(cbc.getOrderNum());
					bcv.setParentId(cbc.getParentId());
					list.add(bcv);
				}
				cache.put(comId + "_list", list);
			} else {
				list = (List<BookLessonVo>) cache.get(comId + "_list").get();
			}
		}
		return list;
	}

	/**
	 * 根据书id从缓存中获取课题map
	 * 
	 * @param comId
	 * @return
	 * @see com.tmser.tr.common.cacheService.BookChapterHerperService#getBookChapterByComId(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, BookLessonVo> getBookChapterMapByComId(String comId) {
		Map<String, BookLessonVo> map = new HashMap<String, BookLessonVo>();
		if (org.apache.commons.lang3.StringUtils.isNotEmpty(comId)) {
			Cache cache = cacheManager.getCache(MetaConstants.BOOKCHAPTER_CACHE_NAME);
			Object element = cache.get(comId + "_map");
			if (element == null) {
				BookChapter model = new BookChapter();
				model.setComId(comId);
				model.addOrder("chapterLevel , orderNum ");
				model.addCustomCulomn("chapterId,chapterName,parentId,orderNum");
				List<BookChapter> listAll = commdityBookChapterDao.listAll(model);
				for (BookChapter cbc : listAll) {
					BookLessonVo bcv = new BookLessonVo();
					bcv.setLessonId(cbc.getChapterId());
					bcv.setLessonName(cbc.getChapterName());
					bcv.setComId(comId);
					bcv.setOrderNum(cbc.getOrderNum());
					bcv.setParentId(cbc.getParentId());
					map.put(bcv.getLessonId(), bcv);
				}
				cache.put(comId + "_map", map);
			} else {
				map = (Map<String, BookLessonVo>) cache.get(comId + "_map").get();
			}
		}
		return map;
	}

	/**
	 * 根据书id从缓存中获取课题map的层级结构，并返回对象集合
	 * 
	 * @param comId
	 * @return
	 * @see com.tmser.tr.common.cacheService.BookChapterHerperService#getBookChapterTreeByComId(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BookLessonVo> getBookChapterTreeByComId(String comId) {
		List<BookLessonVo> returnList = new ArrayList<BookLessonVo>();
		if (org.apache.commons.lang3.StringUtils.isNotEmpty(comId)) {
			Cache cache = cacheManager.getCache(MetaConstants.BOOKCHAPTER_CACHE_NAME);
			Object element = cache.get(comId + "_tree");
			if (element == null) {
				BookChapter model = new BookChapter();
				model.setComId(comId);
				model.setParentId("-1");
				model.addOrder(" orderNum ");
				model.addCustomCulomn("chapterId,chapterName,parentId,orderNum");
				List<BookChapter> listOne = commdityBookChapterDao.listAll(model);
				for (BookChapter bc : listOne) {
					BookLessonVo bcv = new BookLessonVo();
					bcv.setLessonId(bc.getChapterId());
					bcv.setLessonName(bc.getChapterName());
					bcv.setComId(comId);
					bcv.setOrderNum(bc.getOrderNum());
					bcv.setParentId(bc.getParentId());
					returnList.add(bcv);
				}
				for (BookLessonVo blv : returnList) {
					BookChapter model2 = new BookChapter();
					model2.setParentId(blv.getLessonId());
					model2.setComId(comId);
					List<BookChapter> listTwo = commdityBookChapterDao.listAll(model2);
					if (listTwo != null && listTwo.size() > 0) {
						blv.setIsLeaf(false);
						List<BookLessonVo> blvList2 = new ArrayList<BookLessonVo>();
						for (BookChapter bc2 : listTwo) {
							BookLessonVo blv2 = new BookLessonVo();
							blv2.setLessonId(bc2.getChapterId());
							blv2.setLessonName(bc2.getChapterName());
							blv2.setComId(comId);
							blv2.setOrderNum(bc2.getOrderNum());
							blv2.setParentId(bc2.getParentId());
							BookChapter model3 = new BookChapter();
							model3.setParentId(blv2.getLessonId());
							model3.setComId(comId);
							List<BookChapter> listThree = commdityBookChapterDao.listAll(model3);
							if (listThree != null && listThree.size() > 0) {
								blv2.setIsLeaf(false);
								List<BookLessonVo> blvList3 = new ArrayList<BookLessonVo>();
								for (BookChapter bc3 : listThree) {
									BookLessonVo blv3 = new BookLessonVo();
									blv3.setLessonId(bc3.getChapterId());
									blv3.setLessonName(bc3.getChapterName());
									blv3.setComId(comId);
									blv3.setOrderNum(bc3.getOrderNum());
									blv3.setParentId(bc3.getParentId());
									blv3.setIsLeaf(true);
									blvList3.add(blv3);
								}
								blv2.setBookLessons(blvList3);
							} else {
								blv2.setIsLeaf(true);
							}
							blvList2.add(blv2);
						}
						blv.setBookLessons(blvList2);
					} else {
						blv.setIsLeaf(true);
					}
				}
				cache.put(comId + "_tree", returnList);
			} else {
				returnList = (List<BookLessonVo>) cache.get(comId + "_tree").get();
			}
		}
		return returnList;
	}
}
