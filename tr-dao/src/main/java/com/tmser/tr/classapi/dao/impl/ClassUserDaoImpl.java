/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.classapi.dao.impl;

import org.springframework.stereotype.Repository;

import com.tmser.tr.classapi.bo.ClassUser;
import com.tmser.tr.classapi.dao.ClassUserDao;
import com.tmser.tr.common.dao.AbstractDAO;

/**
 * 课堂附件信息 Dao 实现类
 * <pre>
 *
 * </pre>
 *
 * @author yc
 * @version $Id: ClassUser.java, v 1.0 2016-09-20 yc Exp $
 */
@Repository
public class ClassUserDaoImpl extends AbstractDAO<ClassUser,Integer> implements ClassUserDao {

	/**
	 * @param classId
	 * @see com.tmser.tr.classapi.dao.ClassUserDao#deleteByClassId(java.lang.String)
	 */
	@Override
	public void deleteByClassId(String classId) {
		super.update("delete from ClassUser where classId = ?", new Object[]{classId});
	}

}
