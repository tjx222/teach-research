/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.manage.meta.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmser.tr.common.bo.QueryObject;
import com.tmser.tr.common.dao.BaseDAO;
import com.tmser.tr.common.service.AbstractService;
import com.tmser.tr.manage.meta.bo.Menu;
import com.tmser.tr.manage.meta.dao.IconDao;
import com.tmser.tr.manage.meta.dao.MenuDao;
import com.tmser.tr.manage.meta.service.MenuService;
/**
 * 系统入口模块 服务实现类
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: Menu.java, v 1.0 2015-02-12 Generate Tools Exp $
 */
@Service
@Transactional
public class MenuServiceImpl extends AbstractService<Menu, Integer> implements MenuService {

	@Resource
	private MenuDao menuDao;
	
	@Resource
	private IconDao iconDao;
	
	/**
	 * @return
	 * @see com.tmser.tr.common.service.BaseService#getDAO()
	 */
	@Override
	public BaseDAO<Menu, Integer> getDAO() {
		return menuDao;
	}

	/**
	 * @param mid
	 * @return
	 * @see com.tmser.tr.manage.meta.service.MenuService#getMenuWithIcon(java.lang.Integer)
	 */
	@Override
	public Menu getMenuWithIcon(Integer mid) {
		Menu m = menuDao.get(mid);
		if(m ==  null || m.getParentid() != 0){
			return null;
		}
		if(m != null && m.getIcoId() != null){
			m.setIcon(iconDao.get(m.getIcoId()));
		}
		return m;
	}

	/**
	 * 通过角色获取菜单集合
	 * 
	 * @param roleId
	 * @return
	 * @see com.tmser.tr.manage.meta.service.MenuService#getMenuListByRole(java.lang.Integer)
	 */
	@Override
	public List<Menu> getMenuListByRole(Integer roleId) {
		Menu menu = new Menu();
		menu.addAlias("m");
		menu.addCustomCulomn("m.*");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("roleId", roleId);
		paramMap.put("isDel", 0);
		menu.addJoin(QueryObject.JOINTYPE.INNER, "RoleMenu a").on("m.id = a.menuId and a.roleId = :roleId and a.isDel = :isDel");
		menu.addCustomCondition("", paramMap);
		return findAll(menu);
	}
}
