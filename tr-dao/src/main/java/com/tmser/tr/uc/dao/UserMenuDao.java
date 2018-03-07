/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.uc.dao;

import com.tmser.tr.common.dao.BaseDAO;
import com.tmser.tr.uc.bo.UserMenu;

 /**
 * 用户菜单 DAO接口
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: UserMenu.java, v 1.0 2015-02-12 Generate Tools Exp $
 */
public interface UserMenuDao extends BaseDAO<UserMenu, Integer>{

	/**
	 * 复制用户角色菜单
	 * @param roleid 角色id
	 * @param userid 用户id
	 */
	void copyUserRoleMenut(Integer roleid,Integer userid);
	
	/**
	 * 清除用户角色菜单
	 * @param roleid 用户id
	 * @param userid 角色id
	 */
	void clearUserRoleMenut(Integer roleid,Integer userid);
	
	
}