/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.uc.service;

import java.util.List;

import com.tmser.tr.uc.bo.Role;
import com.tmser.tr.common.service.BaseService;
import com.tmser.tr.manage.meta.bo.Menu;

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
	 * 根据用户获取所有菜单
	 * @param sysRoleId
	 * @return
	 */
	List<Menu> findMenuByRole(Integer sysRoleId);
	
    List<Role> findRoleListByUseOrgId(Integer orgId,Integer usePosition);
	
	List<Role> getRoleListByOrgId(Integer orgId);
	
	/**
	 * 拷贝系统预制角色到销售方案下，只拷贝区域、学校的为删除状态角色
	 * @param id
	 */
	void copyRole(Integer id);
	
	/**
	 * 批量拷贝系统角色权限到对应方案的角色中
	 * @param roles
	 * @param list0
	 */
	void copyRoleMenu(List<Role> roles);
}
