/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.back.task.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.tmser.tr.recordbag.dao.RecordbagDao;

/**
 * <pre>
 *
 * </pre>
 *
 * @author tmser
 * @version $Id: RecordSchoolYearTask.java, v 1.0 2016年8月18日 上午11:31:45 tmser Exp $
 */
public class RecordSchoolYearTask implements SchoolYearTask{
	
	@Autowired
	private RecordbagDao recordbagDao;
	
	/**
	 * @return
	 * @see com.tmser.tr.back.task.service.task.SchoolYearTask#name()
	 */
	@Override
	public String name() {
		return "成长档案记录数清除";
	}

	/**
	 * 
	 * @see com.tmser.tr.back.task.service.task.SchoolYearTask#execute()
	 */
	@Override
	public void execute(Integer schoolYear) {
		recordbagDao.updateResCount(schoolYear, null, null);
	}

	/**
	 * @return
	 * @see com.tmser.tr.back.task.service.SchoolYearTask#desc()
	 */
	@Override
	public String desc() {
		return "学年更新后个人成长档案数统计清零";
	}

}
