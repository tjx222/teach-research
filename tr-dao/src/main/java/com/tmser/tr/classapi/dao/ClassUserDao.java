/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.classapi.dao;

import com.tmser.tr.classapi.bo.ClassUser;
import com.tmser.tr.common.dao.BaseDAO;

 /**
 * 课堂附件信息 DAO接口
 * <pre>
 *
 * </pre>
 *
 * @author yc
 * @version $Id: ClassUser.java, v 1.0 2016-09-20 yc Exp $
 */
public interface ClassUserDao extends BaseDAO<ClassUser, Integer>{

	/**
	 * 按class id删除
	 * @param classId
	 */
	void deleteByClassId(String classId);
}