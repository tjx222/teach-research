package com.tmser.tr.check.activity.service;

import java.util.Map;

import com.tmser.tr.common.page.Page;

/**
 * 
 * @author 
 * 查阅活动service
 */
public interface CheckActivityService {

	/**
	 * 查阅活动信息数据
	 * @param grade
	 * @param subject
	 * @param page
	 * @return
	 */
	Map<String, Object> findCheckActivity(Integer grade, Integer subject,
			Integer term,Page page);
	
}
