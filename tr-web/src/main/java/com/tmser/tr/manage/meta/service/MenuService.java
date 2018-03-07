/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.manage.meta.service;

import java.util.List;

import com.tmser.tr.manage.meta.bo.Menu;
import com.tmser.tr.common.service.BaseService;

/**
 * 系统入口模块 服务类
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: Menu.java, v 1.0 2015-02-12 Generate Tools Exp $
 */

public interface MenuService extends BaseService<Menu, Integer>{

	/**
	 *  获取菜单，并关联图标
	 * @param mid
	 * @return
	 */
	Menu getMenuWithIcon(Integer mid);
	
	List<Menu> getMenuListByRole(Integer roleId);
}
