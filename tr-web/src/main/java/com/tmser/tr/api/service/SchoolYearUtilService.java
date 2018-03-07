/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.api.service;

import java.util.Date;

/**
 * <pre>
 * 获取当前学年
 * </pre>
 *
 * @author tmser
 * @version $Id: SchoolYearUtilService.java, v 1.0 2015年2月3日 下午5:11:23 tmser Exp
 *          $
 */
public interface SchoolYearUtilService {

	/**
	 * 返回当前学年
	 * 
	 * @return
	 */
	Integer getCurrentSchoolYear();

	/**
	 * 返回当前学年
	 * 
	 * @return 0 上学期 1下学期
	 */
	Integer getCurrentTerm();

	/**
	 * 获取当前学期开始日期
	 * 
	 * @return
	 */
	Date getCurrentTermStartTime();

	/**
	 * 获得下学期开始时间
	 * 
	 * @return
	 */
	public Date getNextTremStartTime();

	/**
	 * 获得上学期的开始时间
	 * 
	 * @return
	 */
	public Date getUpTremStartTime();

	/**
	 * 获得下学年上学期的开始时间
	 * 
	 * @return
	 */
	public Date getNextYearUpTremStartTime();
}
