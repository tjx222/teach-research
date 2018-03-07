/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.uc.service;

import java.util.List;

import com.tmser.tr.common.service.BaseService;
import com.tmser.tr.uc.bo.Role;
import com.tmser.tr.uc.bo.RoleType;

/**
 * 用户角色 服务类
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: Role.java, v 1.0 2015-02-05 Generate Tools Exp $
 */

public interface RoleService extends BaseService<Role, Integer>{
    
	/**
	 * 查找用户所拥有角色
	 * @param userid
	 * @param sysRoleId
	 * @return
	 */
	List<Role> findRoleByUserid(Integer userid,Integer sysRoleId);
	
	/**
	 * 
	 * @param orgId
	 * @param usePosition
	 * @return
	 */
	List<Role> findRoleListByUseOrgId(Integer orgId, Integer usePosition);
	
	/**
	 * 
	 * 根据角色名称和应用方向查找
	 * @param roleName
	 * @param usePosition
	 * @return
	 */
	public List<Role> findRoleByRoleName(String roleName, Integer usePosition);
	
	/**
	 * 根据角色名称和应用方向查找
	 * @param roleName
	 * @param usePosition
	 * @return
	 */
	public List<Role> findRoleBySysRoleId(Integer sysRoleId, Integer usePosition);
	
	/**
	 * 更加角色id 查询角色类型
	 * @param sysRoleId
	 * @return
	 */
	public RoleType findRoleTypeBySysRoleId(Integer sysRoleId);
}
