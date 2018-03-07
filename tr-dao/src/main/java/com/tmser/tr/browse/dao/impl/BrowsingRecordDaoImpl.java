/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.browse.dao.impl;

import org.springframework.stereotype.Repository;

import com.tmser.tr.browse.bo.BrowsingRecord;
import com.tmser.tr.browse.dao.BrowsingRecordDao;
import com.tmser.tr.common.dao.AbstractDAO;

/**
 * 资源浏览记录 Dao 实现类
 * <pre>
 *
 * </pre>
 *
 * @author zpp
 * @version $Id: BrowsingRecord.java, v 1.0 2015-11-09 zpp Exp $
 */
@Repository
public class BrowsingRecordDaoImpl extends AbstractDAO<BrowsingRecord,Integer> implements BrowsingRecordDao {

	/**
	 * 通过条件修改记录操作
	 * @param type
	 * @param resId
	 * @param isShare
	 * @see com.tmser.tr.browse.dao.BrowsingRecordDao#update(java.lang.Integer, java.lang.Integer, java.lang.Boolean)
	 */
	@Override
	public void update(Integer type, Integer resId, Boolean isShare) {
		String sql = "update BrowsingRecord set resShare=? where type=? and resId=?";
		update(sql, new Object[]{isShare==true?1:0,type,resId});
	}
}
