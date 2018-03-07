/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.recordbag.service;

import com.tmser.tr.common.service.BaseService;
import com.tmser.tr.recordbag.bo.Record;

/**
 * 精选档案 服务类
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: Record.java, v 1.0 2015-04-16 Generate Tools Exp $
 */

public interface RecordService extends BaseService<Record, Integer>{
	
	/**
	 * 更新自定义记录
	 * @param record
	 */
	void updateSelfRecord(Record record);
	
	/**
	 * 保存自定义记录
	 * @param record
	 */
	void saveSelfRecord(Record record);

	/**
	 * bag分享时同时分享资源
	 * @param bagId
	 * @param share
	 * @return
	 */
	int shareRecordByBagId(Integer bagId,Integer share);
}
