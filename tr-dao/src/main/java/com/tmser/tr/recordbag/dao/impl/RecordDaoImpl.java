/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.recordbag.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.tmser.tr.common.dao.AbstractDAO;
import com.tmser.tr.recordbag.bo.Record;
import com.tmser.tr.recordbag.dao.RecordDao;

/**
 * 精选档案 Dao 实现类
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: Record.java, v 1.0 2015-04-16 Generate Tools Exp $
 */
@Repository
public class RecordDaoImpl extends AbstractDAO<Record,Integer> implements RecordDao {

	/**
	 * @param bagId
	 * @param share
	 * @return
	 * @see com.tmser.tr.recordbag.dao.RecordDao#shareRecordByBagId(java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public int shareRecordByBagId(Integer bagId, Integer share,Integer schoolYear) {
		Map<String,Object> args = new HashMap<String, Object>();
		args.put("share", share);
		args.put("bagId", bagId);
		args.put("schoolYear", schoolYear);
		String sql = "update Record set share = :share where bagId = :bagId and schoolYear = :schoolYear";
		return this.updateWithNamedSql(sql, args);
	}

}
