/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.manage.meta.service;

import com.tmser.tr.manage.meta.bo.Icon;
import com.tmser.tr.common.service.BaseService;

/**
 * 系统图标资源 服务类
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: Icon.java, v 1.0 2015-02-11 Generate Tools Exp $
 */

public interface IconService extends BaseService<Icon, Integer>{
	/**
	 * 根据用户标识查询图标
	 * @param identity
	 * @return
	 */
	Icon findByIdentity(String identity);
}
