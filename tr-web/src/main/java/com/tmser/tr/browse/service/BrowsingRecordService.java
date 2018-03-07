/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.browse.service;

import com.tmser.tr.browse.bo.BrowsingRecord;
import com.tmser.tr.common.service.BaseService;

/**
 * 资源浏览记录 服务类
 * <pre>
 *
 * </pre>
 *
 * @author zpp
 * @version $Id: BrowsingRecord.java, v 1.0 2015-11-09 zpp Exp $
 */

public interface BrowsingRecordService extends BaseService<BrowsingRecord, Integer>{

	/**
	 * 添加浏览记录
	 * @param type
	 * @param resId
	 */
	void addBrowseRecord(Integer type, Integer resId);

	/**
	 * 修改浏览记录资源的分享状态
	 * @param type
	 * @param resId
	 * @param isShare
	 */
	void updateBrowseRecordShare(Integer type, Integer resId,Boolean isShare);

}
