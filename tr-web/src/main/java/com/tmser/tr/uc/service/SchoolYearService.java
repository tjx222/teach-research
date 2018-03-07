/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.uc.service;

import java.util.Date;

/**
 * <pre>
 * 获取当前学年
 * </pre>
 *
 * @author tmser
 * @version $Id: SchoolYearService.java, v 1.0 2015年2月3日 下午5:11:23 tmser Exp $
 */
public interface SchoolYearService {
	
	/**
	 * 返回当前学年
	 * @return
	 */
	Integer getCurrentSchoolYear();
	
	/**
	 * 返回当前学年
	 * @return 0 上学期  1下学期
	 */
	Integer getCurrentTerm();
	
	/**
	 * 获取当前学期开始日期
	 * @return
	 */
	Date getCurrentTermStartTime();
	
	/**
	 * 获取当前学年学期开始时间
	 * @param term 制定的学期， 0 上学期， 1 下学期
	 * @return
	 */
	Date getTermStartDate(int term);
	
	/**
	 * 获得下学期开始时间
	 * @return
	 */
	public Date getNextTermStartTime();
	
	/**
	 * 获得上学期的开始时间
	 * @return
	 */
	public Date getUpTermStartTime();
	
	/**
	 * 获得下学年上学期的开始时间
	 * @return
	 */
	public Date getNextYearUpTermStartTime();
}
