/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.back.classapi.service;

import java.util.List;

import com.tmser.tr.classapi.bo.ClassVisit;
import com.tmser.tr.common.service.BaseService;


/**
 * <pre>
 * 直播课堂用户进出记录
 * </pre>
 */
public interface ClassvisitService extends BaseService<ClassVisit, Integer> {

	/**
	 * 用户进入课堂时间按天统计
	 * @param model
	 * @return
	 */
	List<ClassVisit> yhjrsjResultByDay(ClassVisit model);
	/**
	 * 用户进入课堂时间实时统计
	 * @param model
	 * @return
	 */
	List<ClassVisit> yhjrsjResult(ClassVisit model);

}
