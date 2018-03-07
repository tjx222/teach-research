/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.manage.org.service;

import java.util.List;

import com.tmser.tr.common.service.BaseService;
import com.tmser.tr.manage.org.bo.Area;

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
	 * 根据areaIds获取相应名字连成的字符串
	 * 
	 * @param areaIds
	 * @return
	 */
	String getAreaNamesByAreaIds(String areaIds);

	/**
	 * 根据名称查询机构
	 * 
	 * @param name
	 * @param type
	 * @return
	 */
	List<Area> getAllOrgByName(String name, Integer type);
	
	/**
   * 获取区域id 列表
   * 
   * @param areaId
   *          区域id
   * @return List
   */
  List<Integer> getAreaIds(Integer areaId);
}
