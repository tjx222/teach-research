/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.manage.org.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import com.tmser.tr.common.dao.BaseDAO;
import com.tmser.tr.manage.org.bo.Area;
import com.tmser.tr.common.service.AbstractService;
import com.tmser.tr.manage.org.service.AreaService;
import com.tmser.tr.manage.org.dao.AreaDao;

/**
 * 区域树 服务实现类
 * 
 * <pre>
 * 
 * </pre>
 * 
 * @author Generate Tools
 * @version $Id: Area.java, v 1.0 2015-05-07 Generate Tools Exp $
 */
@Service
@Transactional
public class AreaServiceImpl extends AbstractService<Area, Integer> implements
		AreaService {

	@Autowired
	private AreaDao areaDao;

	/**
	 * @return
	 * @see com.tmser.tr.common.service.BaseService#getDAO()
	 */
	@Override
	public BaseDAO<Area, Integer> getDAO() {
		return areaDao;
	}

	@Override
	public List<Area> findAreaListByParentId(Integer parentId, Integer level) {
		List<Area> areaList = new ArrayList<Area>();
		if (parentId == null || parentId < 0) {
			return areaList;
		}
		if (level == null || (level < 1 && level > 3)) {
			return areaList;
		}
		Area area = new Area();
		area.setLevel(level);
		area.setParentId(parentId);

		area.addCustomCulomn(" id,parentId,name,level,sort ");
		area.setOrder(" sort asc ");// 默认升序排序

		return this.findAll(area);
	}

}
