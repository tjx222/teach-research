/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.recordbag.dao;

import com.tmser.tr.common.dao.BaseDAO;
import com.tmser.tr.recordbag.bo.Record;

 /**
 * 精选档案 DAO接口
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: Record.java, v 1.0 2015-04-16 Generate Tools Exp $
 */
public interface RecordDao extends BaseDAO<Record, Integer>{
	/**
	 * bag分享时同时分享资源
	 * @param bagId
	 * @param share
	 * @return
	 */
	int shareRecordByBagId(Integer bagId, Integer share,Integer schoolYear);
}