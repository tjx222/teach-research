/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.uc.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmser.tr.common.dao.BaseDAO;
import com.tmser.tr.common.service.AbstractService;
import com.tmser.tr.common.utils.FrontCacheUtils;
import com.tmser.tr.manage.meta.service.MenuService;
import com.tmser.tr.uc.bo.UserMenu;
import com.tmser.tr.uc.bo.UserSpace;
import com.tmser.tr.uc.dao.UserMenuDao;
import com.tmser.tr.uc.service.UserMenuService;
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
	 * 根据角色和用户Id判断菜单是否存在
	 * @param roleId
	 * @param userId
	 * @return
	 * @see com.tmser.tr.uc.service.UserMenuService#menuAlreadyExist(java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public boolean menuAlreadyExist(Integer roleId, Integer userId) {
	    UserMenu userMenu = new UserMenu();
	    userMenu.setSysRoleId(roleId);
	    userMenu.setUserId(userId);
	    if(count(userMenu)>0){
	    	return true;
	    }
		return false;
	}

	/**
	 * 通过角色ID获得菜单列表并插入菜单
	 * @param us
	 * @see com.tmser.tr.uc.service.UserMenuService#addUserMenus(java.lang.Integer)
	 */
	@Override
	public void addUserMenus(UserSpace us) {
			userMenuDao.clearUserRoleMenut(us.getRoleId(), us.getUserId());
			userMenuDao.copyUserRoleMenut(us.getRoleId(), us.getUserId());
			FrontCacheUtils.clear(UserMenu.class);//清空缓存
	}

	/**
	 * 通过用户空间对象删除用户菜单
	 * @param us
	 * @see com.tmser.tr.uc.service.UserMenuService#deleteUserMenus(com.tmser.tr.uc.bo.UserSpace)
	 */
	@Override
	public void deleteUserMenus(UserSpace us) {
		userMenuDao.clearUserRoleMenut(us.getRoleId(), us.getUserId());
		FrontCacheUtils.clear(UserMenu.class);
	}

}
