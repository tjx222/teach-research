/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.manage.org.service;

import java.util.List;

import com.tmser.tr.manage.org.bo.Area;
import com.tmser.tr.common.service.BaseService;

/**
 * 区域树 服务类
 * 
 * <pre>
 * 
 * </pre>
 * 
 * @author Generate Tools
 * @version $Id: Area.java, v 1.0 2015-05-07 Generate Tools Exp $
 */

public interface AreaService extends BaseService<Area, Integer> {

	/**
	 * 获取级别区域数据列表
	 * @param parentId 区域父级id
	 * @param level	区域级别
	 * @return
	 */
	public List<Area> findAreaListByParentId(Integer parentId, Integer level);
	
}
