/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.uc.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmser.tr.common.dao.BaseDAO;
import com.tmser.tr.common.service.AbstractService;
import com.tmser.tr.manage.meta.bo.Menu;
import com.tmser.tr.manage.meta.service.MenuService;
import com.tmser.tr.uc.bo.UserMenu;
import com.tmser.tr.uc.dao.UserMenuDao;
import com.tmser.tr.uc.service.UserMenuService;
import com.tmser.tr.utils.JyAssert;
/**
 * 用户菜单 服务实现类
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: UserMenu.java, v 1.0 2015-02-12 Generate Tools Exp $
 */
@Service
@Transactional
public class UserMenuServiceImpl extends AbstractService<UserMenu, Integer> implements UserMenuService {

	@Resource	
	private UserMenuDao userMenuDao;
	
	@Resource
	private MenuService menuService;
	
	/**
	 * @return
	 * @see com.tmser.tr.common.service.BaseService#getDAO()
	 */
	@Override
	public BaseDAO<UserMenu, Integer> getDAO() {
		return userMenuDao;
	}

	/**
	 * @param uid
	 * @param sysRoleId
	 * @return
	 * @see com.tmser.tr.uc.service.UserMenuService#findUserMenuByUser(java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public List<UserMenu> findUserMenuByUser(Integer userId, Integer sysRoleId,Boolean display) {
		JyAssert.notNull(userId, "user id can't be null!");
		JyAssert.notNull(sysRoleId, "sysRoleId id can't be null!");
		UserMenu model = new UserMenu();
		model.setUserId(userId);
		model.setSysRoleId(sysRoleId);
		model.setDisplay(display);
		model.addOrder("sort");
		
		List<UserMenu> umList = userMenuDao.listAll(model);
		List<UserMenu> acList = new ArrayList<UserMenu>();
		for(UserMenu um : umList){
			Menu m = menuService.getMenuWithIcon(um.getMenuId());
			if(m != null){
				if(m.getParentid() == 0 && !m.getIsNormal()){
					continue;
				}
				um.setMenu(m);
				acList.add(um);
			}
		}
		
		return acList;
	}

	/**
	 * @param mids
	 * @param userid
	 * @see com.tmser.tr.uc.service.UserMenuService#sortUser(java.lang.String, java.lang.Integer)
	 */
	@Override
	public int sortUser(String mids, Integer userid) {
		String[] midlist =  mids.split(",");
		int sort = 0;
		int count = 0;
		for(String mid : midlist){
			UserMenu model = new UserMenu();
			model.setSort(sort);
			Map<String,Object> paramMap = new HashMap<String,Object>(2);
			paramMap.put("userId", userid);
			paramMap.put("nsort", sort);
			model.addCustomCondition("and userId = :userId and sort != :nsort", paramMap);
			model.setId(Integer.valueOf(mid));
			sort++;
			count = count + userMenuDao.update(model);
		}
		return count;
	}

}
