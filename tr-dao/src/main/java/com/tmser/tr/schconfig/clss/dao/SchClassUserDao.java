/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.schconfig.clss.dao;

import com.tmser.tr.common.dao.BaseDAO;
import com.tmser.tr.schconfig.clss.bo.SchClassUser;

 /**
 * 学校班级用户关联 DAO接口
 * <pre>
 *
 * </pre>
 *
 * @author tmser
 * @version $Id: SchClassUser.java, v 1.0 2016-02-19 tmser Exp $
 */
public interface SchClassUserDao extends BaseDAO<SchClassUser, Integer>{

	/**
	 * 清除班级用户，不可恢复清除
	 * @param clssid
	 */
	void deleteByClassId(Integer clssid);
}