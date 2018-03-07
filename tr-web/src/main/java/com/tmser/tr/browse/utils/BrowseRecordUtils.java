/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.browse.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tmser.tr.browse.service.BrowsingCountService;
import com.tmser.tr.browse.service.BrowsingRecordService;
import com.tmser.tr.utils.SpringContextHolder;

/**
 * <pre>
 *  浏览记录工具类
 * </pre>
 *
 * @author zpp
 * @version $Id: BrowseRecordUtils.java, v 1.0 2015年11月11日 下午11:28:21 zpp Exp $
 */
public class BrowseRecordUtils {

	private static Logger logger = LoggerFactory.getLogger(BrowseRecordUtils.class);

	private final static BrowsingRecordService getRecordService(){
		return SpringContextHolder.getBean(BrowsingRecordService.class);
	}
	
	private final static BrowsingCountService getCountService(){
		return SpringContextHolder.getBean(BrowsingCountService.class);
	}

	/**
	 * 添加浏览记录和数量
	 * @param type
	 * @param resId
	 */
	public static void addBrowseRecord(Integer type, Integer resId){
		try {
			getRecordService().addBrowseRecord(type, resId);
		} catch (Exception e) {
			logger.error("--添加浏览记录出错--", e);
		}
	}

	/**
	 * 修改资源记录的分享状态
	 * @param type
	 * @param resId
	 * @param isShare
	 */
	public static void updateBrowseRecordShare(Integer type, Integer resId,Boolean isShare){
		try {
			getRecordService().updateBrowseRecordShare(type, resId, isShare);
		} catch (Exception e) {
			logger.error("--修改浏览记录分享状态出错--", e);
		}
	}

	/**
	 * 获取资源浏览数量
	 * @param type
	 * @param resId
	 * @return
	 */
	public static int getResBrowseCount(Integer type, Integer resId){
		try {
			return getCountService().getCount(type, resId);
		} catch (Exception e) {
			logger.error("--获取资源浏览数量失败--", e);
			return 0;
		}
	}
}
