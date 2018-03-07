/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.uc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import com.tmser.tr.common.dao.BaseDAO;
import com.tmser.tr.uc.bo.Permission;
import com.tmser.tr.common.service.AbstractService;
import com.tmser.tr.uc.service.PermissionService;
import com.tmser.tr.uc.dao.PermissionDao;
/**
 * 权限表 服务实现类
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: Permission.java, v 1.0 2015-02-10 Generate Tools Exp $
 */
@Service
@Transactional
public class PermissionServiceImpl extends AbstractService<Permission, Integer> implements PermissionService {

	@Autowired
	private PermissionDao permissionDao;
	
	/**
	 * @return
	 * @see com.tmser.tr.common.service.BaseService#getDAO()
	 */
	@Override
	public BaseDAO<Permission, Integer> getDAO() {
		return permissionDao;
	}
	
	
	/**
	 * @param roleid
	 * @return
	 * @see com.tmser.tr.uc.service.RolePermissionService#findPermissionByUserid(java.lang.Integer)
	 */
	@Override
	public List<Permission> findPermissionByRoleid(Integer roleid) {
		if(roleid != null){
			return permissionDao.listPermissionByRoleid(roleid);
		}
		return null;
	}

}
