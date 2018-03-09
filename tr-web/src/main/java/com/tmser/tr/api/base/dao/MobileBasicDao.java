/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.api.base.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.tmser.tr.common.dao.BaseDAO;
import com.tmser.tr.uc.bo.UserSpace;

/**
 * <pre>
 * api基础信息dao层接口
 * </pre>
 *
 * @author tmser
 * @version $Id: MobileBasicDao.java, v 1.0 2016年4月19日 下午3:05:40 3020mt Exp $
 */
public interface MobileBasicDao extends BaseDAO<UserSpace, Serializable> {

	/**
	 * 通过用户空间机构ID，学段ID，学年，获得用户信息
	 * 
	 * @param us
	 * @return
	 */
	List<Map<String, Object>> findUsersMap(UserSpace us);

}
