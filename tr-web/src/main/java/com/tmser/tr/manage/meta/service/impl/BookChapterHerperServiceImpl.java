/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.manage.meta.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
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
import com.tmser.tr.utils.StringUtils;

/**
 * <pre>
 *
 * </pre>
 *
 * @author tmser
 * @version $Id: MetaDataHerperServiceImpl.java, v 1.0 2015年2月3日 上午9:32:15 tmser Exp $
 */
@Service
@Transactional
public class BookChapterHerperServiceImpl implements BookChapterHerperService {

	@Autowired
	private BookChapterDao bookChapterDao;

	@Resource(name="cacheManger")
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
		if(StringUtils.isNotEmpty(comId)){

			Cache cache = cacheManager.getCache(MetaConstants.BOOKCHAPTER_CACHE_NAME);
			Object element = cache.get(comId+"_list");
			if(element==null){
				BookChapter model = new BookChapter();
				model.setComId(comId);
				model.addOrder("chapterLevel , order_num");
				model.addCustomCulomn("chapterId,chapterName,parentId,orderNum");
				List<BookChapter> listAll = bookChapterDao.listAll(model);
				for(BookChapter  cbc : listAll){
					BookLessonVo bcv = new BookLessonVo();
					bcv.setLessonId(cbc.getChapterId());
					bcv.setLessonName(cbc.getChapterName());
					bcv.setComId(comId);
					bcv.setOrderNum(cbc.getOrderNum());
					bcv.setParentId(cbc.getParentId());
					list.add(bcv);
				}
				cache.put(comId+"_list", list);
			}else{
				list = (List<BookLessonVo>)cache.get(comId+"_list").get();
			}
		}
		return list;
	}

	/**
	 * 根据书id从缓存中获取课题map
	 * @param comId
	 * @return
	 * @see com.tmser.tr.common.cacheService.BookChapterHerperService#getBookChapterByComId(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map<String,BookLessonVo> getBookChapterMapByComId(String comId) {
		Map<String,BookLessonVo> map = new HashMap<String, BookLessonVo>();
		if(StringUtils.isNotEmpty(comId)){
			Cache cache = cacheManager.getCache(MetaConstants.BOOKCHAPTER_CACHE_NAME);
			Object element = cache.get(comId+"_map");
			if(element==null){
				BookChapter model = new BookChapter();
				model.setComId(comId);
				model.addOrder("chapterLevel , orderNum ");
				model.addCustomCulomn("chapterId,chapterName,parentId,orderNum");
				List<BookChapter> listAll = bookChapterDao.listAll(model);
				for(BookChapter  cbc : listAll){
					BookLessonVo bcv = new BookLessonVo();
					bcv.setLessonId(cbc.getChapterId());
					bcv.setLessonName(cbc.getChapterName());
					bcv.setComId(comId);
					bcv.setOrderNum(cbc.getOrderNum());
					bcv.setParentId(cbc.getParentId());
					map.put(bcv.getLessonId(), bcv);
				}
				cache.put(comId+"_map", map);
			}else{
				map = (Map<String,BookLessonVo>)cache.get(comId+"_map").get();
			}
		}
		return map;
	}


	/**
	 * 根据书id从缓存中获取课题map的层级结构，并返回对象集合
	 * @param comId
	 * @return
	 * @see com.tmser.tr.common.cacheService.BookChapterHerperService#getBookChapterTreeByComId(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BookLessonVo> getBookChapterTreeByComId(String comId) {
		List<BookLessonVo> returnList = new ArrayList<BookLessonVo>();
		if(StringUtils.isNotEmpty(comId)){
			Cache cache = cacheManager.getCache(MetaConstants.BOOKCHAPTER_CACHE_NAME);
			Object element = cache.get(comId+"_tree");
			if(element==null){
				BookChapter model = new BookChapter();
				model.setComId(comId);
				//model.setParentId("-1");
				model.addOrder(" orderNum ");
				model.addCustomCulomn("chapterId,chapterName,parentId,orderNum");
				List<BookChapter> listOne = bookChapterDao.listAll(model);
				
				Map<String,BookLessonVo> bkmap = new LinkedHashMap<String,BookLessonVo>();
				for(BookChapter  bc : listOne){
					BookLessonVo bcv = new BookLessonVo();
					bcv.setLessonId(bc.getChapterId());
					bcv.setLessonName(bc.getChapterName());
					bcv.setComId(comId);
					bcv.setOrderNum(bc.getOrderNum());
					bcv.setParentId(bc.getParentId());
					bcv.setIsLeaf(true);
					bkmap.put(bc.getChapterId(), bcv);
				}
				
				for(String blvId : bkmap.keySet()){
					BookLessonVo bcv = bkmap.get(blvId);
					if("-1".equals(bcv.getParentId())){//非父级节点
						returnList.add(bcv);
					}else{
						BookLessonVo parent = bkmap.get(bcv.getParentId());
						if(parent != null){
							parent.setIsLeaf(false);
							List<BookLessonVo> childList = parent.getBookLessons();
							if(childList == null){
								childList = new ArrayList<BookLessonVo>();
							}
							childList.add(bcv);
							parent.setBookLessons(childList);
						}
					}
				}
		
				cache.put(comId+"_tree", returnList);
			}else{
				returnList = (List<BookLessonVo>)cache.get(comId+"_tree").get();
			}
		}
		return returnList;
	}
}
