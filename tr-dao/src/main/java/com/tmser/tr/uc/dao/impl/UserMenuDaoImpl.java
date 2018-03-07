/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.uc.dao.impl;

import org.springframework.stereotype.Repository;

import com.tmser.tr.common.dao.AbstractDAO;
import com.tmser.tr.uc.bo.RoleMenu;
import com.tmser.tr.uc.bo.UserMenu;
import com.tmser.tr.uc.dao.UserMenuDao;

/**
 * 用户菜单 Dao 实现类
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: UserMenu.java, v 1.0 2015-02-12 Generate Tools Exp $
 */
@Repository
public class UserMenuDaoImpl extends AbstractDAO<UserMenu,Integer> implements UserMenuDao {

	@Override
	public void copyUserRoleMenut(Integer roleid,Integer userid){
		String sql = "insert into UserMenu(sysRoleId,name,menuId,display,sort,userId)"
				+ " select ?,sm.permBname,sm.menuId,?,sm.menuId st,? from RoleMenu sm where sm.roleId = ? and sm.isDel = ?";
		
		update(sql, new Object[]{roleid,Boolean.TRUE,userid,roleid,RoleMenu.NORMAL});
	}
	
	@Override
	public void clearUserRoleMenut(Integer roleid,Integer userid){
		String sql = "delete UserMenu where sysRoleId = ? and userId = ?";
		update(sql, new Object[]{roleid,userid});
	}
	
	
}
