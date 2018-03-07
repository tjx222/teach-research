/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.manage.org.dao;

import java.util.List;

import com.tmser.tr.common.dao.BaseDAO;
import com.tmser.tr.manage.org.bo.Area;

 /**
 * 区域树 DAO接口
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: Area.java, v 1.0 2015-05-07 Generate Tools Exp $
 */
public interface AreaDao extends BaseDAO<Area, Integer>{
  /**
   * 获取区域id 列表
   * 
   * @param areaId
   *          区域id
   * @return List
   */
  List<Integer> getAreaIds(Integer areaId);
}