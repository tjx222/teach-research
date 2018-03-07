/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.recordbag.dao;

import com.tmser.tr.common.dao.BaseDAO;
import com.tmser.tr.recordbag.bo.Recordbag;

 /**
 * 成长档案袋 DAO接口
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: Recordbag.java, v 1.0 2015-04-13 Generate Tools Exp $
 */
public interface RecordbagDao extends BaseDAO<Recordbag, Integer>{
	
	/**
	 * 更新成长档案功能
	 * @param schoolYear
	 * @param orgId
	 * @param userId
	 * @return
	 */
	int updateResCount(Integer schoolYear,Integer orgId,Integer userId);

}