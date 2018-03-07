/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.uc.dao;

import java.util.List;

import com.tmser.tr.common.dao.BaseDAO;
import com.tmser.tr.uc.bo.Permission;

 /**
 * 权限表 DAO接口
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: Permission.java, v 1.0 2015-02-10 Generate Tools Exp $
 */
public interface PermissionDao extends BaseDAO<Permission, Integer>{
	/**
	 * 根据角色id 获取权限列表
	 * @param roleid
	 * @return
	 */
	List<Permission> listPermissionByRoleid(Integer roleid);
}