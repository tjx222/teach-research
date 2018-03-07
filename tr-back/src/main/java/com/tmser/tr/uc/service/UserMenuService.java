/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.uc.service;

import com.tmser.tr.common.service.BaseService;
import com.tmser.tr.uc.bo.UserMenu;
import com.tmser.tr.uc.bo.UserSpace;

/**
 * 用户菜单 服务类
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: UserMenu.java, v 1.0 2015-02-12 Generate Tools Exp $
 */

public interface UserMenuService extends BaseService<UserMenu, Integer>{


	/**
	 * 根据角色和用户Id判断菜单是否存在
	 * @param roleId
	 * @param userId
	 */
	boolean menuAlreadyExist(Integer roleId, Integer userId);

	/**
	 * 通过角色ID获得菜单列表并插入菜单
	 * @param us
	 */
	void addUserMenus(UserSpace us);

	/**
	 * 通过用户空间对象删除用户菜单
	 * @param us
	 */
	void deleteUserMenus(UserSpace us);
	
	
}
