/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.uc.service;

import java.util.List;

import com.tmser.tr.common.service.BaseService;
import com.tmser.tr.uc.bo.UserMenu;

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
	 * 根据用户获取所有菜单
	 * @param uid
	 * @param sysRoleId
	 * @param display
	 * @return
	 */
	List<UserMenu> findUserMenuByUser(Integer spaceid,Integer sysRoleId,Boolean display);
	
	/**
	 * 重排序用户菜单
	 * @param mids 菜单列表
	 * @param uid 菜单所属用户
	 * @return
	 */
	int sortUser(String mids,Integer userid);
}
