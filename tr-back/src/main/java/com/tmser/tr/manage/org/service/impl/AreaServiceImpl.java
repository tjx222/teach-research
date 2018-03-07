/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.manage.org.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.tmser.tr.common.dao.BaseDAO;
import com.tmser.tr.common.orm.SqlMapping;
import com.tmser.tr.common.service.AbstractService;
import com.tmser.tr.common.utils.WebThreadLocalUtils;
import com.tmser.tr.manage.org.bo.Area;
import com.tmser.tr.manage.org.dao.AreaDao;
import com.tmser.tr.manage.org.service.AreaService;
import com.tmser.tr.uc.SysRole;
import com.tmser.tr.uc.bo.UserManagescope;
import com.tmser.tr.uc.bo.UserSpace;
import com.tmser.tr.uc.dao.UserManagescopeDao;
import com.tmser.tr.uc.utils.SessionKey;
import com.tmser.tr.utils.JyCollectionUtils;

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
public class AreaServiceImpl extends AbstractService<Area, Integer> implements AreaService {

	@Autowired
	private AreaDao areaDao;
	@Autowired
	private UserManagescopeDao userManagescopeDao;

	/**
	 * @return
	 * @see com.tmser.tr.common.service.BaseService#getDAO()
	 */
	@Override
	public BaseDAO<Area, Integer> getDAO() {
		return areaDao;
	}

	/**
	 * 根据areaIds 获取相应的名字连成的字符串
	 * 
	 * @param areaIds
	 * @return
	 * @see com.tmser.tr.manage.org.service.AreaService#getAreaNamesByAreaIds(java.lang.String)
	 */
	@Override
	public String getAreaNamesByAreaIds(String areaIds) {
		String areaNames = "";
		if (areaIds != null) {
			String[] areaIdArray = areaIds.substring(1, areaIds.length() - 1).split(",");
			for (String areaId : areaIdArray) {
				areaNames = areaNames + findOne(Integer.valueOf(areaId)).getName();
			}
		}
		return areaNames;
	}

	@Override
	public List<Area> getAllOrgByName(String name, Integer type) {
		UserSpace us = (UserSpace) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE);
		Area area = new Area();
		Map<String, Object> paramter = new HashMap<>();
		Set<Area> areaSet = new LinkedHashSet<>();
		if (type != null) {// 直属校区域查询设置
			Set<Integer> levelSet = new HashSet<>();
			levelSet.add(1);
			levelSet.add(2);
			paramter.put("level", levelSet);
			area.addCustomCondition("and level in (:level)", paramter);
		}
		area.setName(SqlMapping.LIKE_PRFIX + name + SqlMapping.LIKE_PRFIX);
		List<Area> listAll = null;
		if (us.getSysRoleId().intValue() != SysRole.ADMIN.getId().intValue()) { // 不是超级管理员
			UserManagescope um = new UserManagescope();
			um.addCustomCulomn("distinct areaId");
			um.setUserId(us.getUserId());
			um.setRoleId(us.getRoleId());
			List<UserManagescope> umList = userManagescopeDao.listAll(um);
			List<Integer> spaceIds = JyCollectionUtils.getValues(umList, "areaId");
			if (!CollectionUtils.isEmpty(spaceIds)) {
				area.buildCondition(" and id in (:areaId)").put("areaId", spaceIds);
			}
			listAll = areaDao.listAll(area);
		} else {
			listAll = areaDao.listAll(area);
		}
		for (Area areaTemp : listAll) {
			if (areaTemp.getLevel() != null && areaTemp.getLevel() != 3) {
				// 如果本身有下级节点的找出来
				Area newArea = new Area();
				if (type != null) {// 直属校区域查询设置
					Set<Integer> levelSet = new HashSet<>();
					levelSet.add(1);
					levelSet.add(2);
					paramter.put("level", levelSet);
					newArea.addCustomCondition("and level in (:level)", paramter);
				}
				newArea.setParentId(areaTemp.getId());
				List<Area> listParent = areaDao.listAll(newArea);
				if (null != listParent && listParent.size() > 0) {
					areaSet.addAll(listParent);
				}
				if (type == null) {// 为空时为直属非直属校
					for (Area area2 : listParent) {
						if (area2.getLevel() != null && area2.getLevel() != 3) {// 查找市下的
							Area area1 = new Area();
							area1.setParentId(area2.getId());
							areaSet.addAll(areaDao.listAll(area1));
						}
					}
				}
			}
			List<Area> areaLis = new ArrayList<>();
			// 找自身节点的所有上级目录
			while (areaTemp.getId() != 0) {
				areaLis.add(areaTemp);
				if (areaTemp.getParentId() == 0) {
					break;
				}
				areaTemp = areaDao.get(areaTemp.getParentId());
			}
			areaSet.addAll(areaLis);
		}
		// 去除重复的节点
		return new ArrayList<>(areaSet);
	}

  @Override
  public List<Integer> getAreaIds(Integer areaId) {
      return areaDao.getAreaIds(areaId);
  }
}
