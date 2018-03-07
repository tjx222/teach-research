/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.browse.dao;

import com.tmser.tr.browse.bo.BrowsingRecord;
import com.tmser.tr.common.dao.BaseDAO;

/**
 * 资源浏览记录 DAO接口
 * <pre>
 *
 * </pre>
 *
 * @author zpp
 * @version $Id: BrowsingRecord.java, v 1.0 2015-11-09 zpp Exp $
 */
public interface BrowsingRecordDao extends BaseDAO<BrowsingRecord, Integer>{

	/**
	 * 通过条件修改记录操作
	 * @param type
	 * @param resId
	 * @param isShare
	 */
	public void update(Integer type, Integer resId,Boolean isShare);
}