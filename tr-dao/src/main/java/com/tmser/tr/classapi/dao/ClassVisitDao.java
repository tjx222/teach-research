/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.classapi.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.tmser.tr.classapi.bo.ClassVisit;
import com.tmser.tr.common.dao.BaseDAO;

 /**
 * 课堂参与信息 DAO接口
 * <pre>
 *
 * </pre>
 *
 * @author ljh
 * @version $Id: ClassUser.java, v 1.0 2016-12-21 ljh Exp $
 */
@Repository
public interface ClassVisitDao extends BaseDAO<ClassVisit, Integer>{

	/**
	 * 用户进入课堂时间按天统计
	 * @param model
	 * @return
	 */
	List<ClassVisit> yhjrsjResultByDay(ClassVisit model);
	/**
	 * 用户进入课堂时间实时
	 * @param model
	 * @return
	 */
	List<ClassVisit> yhjrsjResult(ClassVisit model);

}