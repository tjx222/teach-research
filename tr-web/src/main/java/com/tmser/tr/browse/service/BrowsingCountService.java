/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.browse.service;

import com.tmser.tr.browse.bo.BrowsingCount;
import com.tmser.tr.common.service.BaseService;

/**
 * 资源浏览总数记录 服务类
 * <pre>
 *
 * </pre>
 *
 * @author tmser
 * @version $Id: BrowsingCount.java, v 1.0 2015-11-11 tmser Exp $
 */

public interface BrowsingCountService extends BaseService<BrowsingCount, Integer>{

	/**
	 * 通过资源类型和资源ID获得浏览次数
	 * @param type
	 * @param resId
	 * @return
	 */
	public int getCount(Integer type,Integer resId);
}
