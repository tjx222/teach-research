/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.back.classapi.service;

import java.util.List;

import com.tmser.tr.classapi.bo.ClassInfo;
import com.tmser.tr.common.service.BaseService;



/**
 * <pre>
 * 直播课堂用户
 * </pre>
 */
public interface ClassinfoService extends BaseService<ClassInfo, String> {

	/**
	 * 用户进入课堂时间按天统计
	 * @param model
	 * @return
	 */
	List<ClassInfo> ktbftjResultByDay(ClassInfo model);

	/**
	 * 课堂开始时间实时统计
	 * @param model
	 * @return
	 */
	List<ClassInfo> ktbftjResult(ClassInfo model);
}
