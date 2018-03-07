/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.classapi.dao;

import java.util.List;

import com.tmser.tr.classapi.bo.ClassInfo;
import com.tmser.tr.common.dao.BaseDAO;

 /**
 * 课堂附件信息 DAO接口
 * <pre>
 *
 * </pre>
 *
 * @author yc
 * @version $Id: ClassInfo.java, v 1.0 2016-09-20 yc Exp $
 */
public interface ClassInfoDao extends BaseDAO<ClassInfo, String>{

	/**
	 * 用户进入课堂时间按天统计
	 * @author ljh
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