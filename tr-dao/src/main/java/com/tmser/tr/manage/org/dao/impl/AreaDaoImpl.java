/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.manage.org.dao.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.tmser.tr.common.dao.AbstractDAO;
import com.tmser.tr.common.dao.annotation.UseCache;
import com.tmser.tr.manage.org.bo.Area;
import com.tmser.tr.manage.org.dao.AreaDao;

/**
 * 区域树 Dao 实现类
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: Area.java, v 1.0 2015-05-07 Generate Tools Exp $
 */
@Repository
@UseCache
public class AreaDaoImpl extends AbstractDAO<Area,Integer> implements AreaDao {

  @Override
  public List<Integer> getAreaIds(Integer areaId) {
    List<Integer> areaIds = new ArrayList<>();
    while (areaId != null && areaId != 0) {
      areaIds.add(areaId);
      Area area = get(areaId);
      areaId = area.getParentId();
    }
    Collections.reverse(areaIds);
    return areaIds;
  }

}
