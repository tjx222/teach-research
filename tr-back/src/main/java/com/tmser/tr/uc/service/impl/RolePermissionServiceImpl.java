/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.uc.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmser.tr.common.dao.BaseDAO;
import com.tmser.tr.common.service.AbstractService;
import com.tmser.tr.uc.bo.RolePermission;
import com.tmser.tr.uc.dao.RolePermissionDao;
import com.tmser.tr.uc.service.RolePermissionService;
/**
 * 权限表 服务实现类
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: RolePermission.java, v 1.0 2015-02-10 Generate Tools Exp $
 */
@Service
@Transactional
public class RolePermissionServiceImpl extends AbstractService<RolePermission, Integer> implements RolePermissionService {

	@Autowired
	private RolePermissionDao rolePermissionDao;
	/**
	 * @return
	 * @see com.tmser.tr.common.service.BaseService#getDAO()
	 */
	@Override
	public BaseDAO<RolePermission, Integer> getDAO() {
		return rolePermissionDao;
	}
}
