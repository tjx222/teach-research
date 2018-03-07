package com.tmser.tr.uc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmser.tr.common.dao.BaseDAO;
import com.tmser.tr.common.service.AbstractService;
import com.tmser.tr.uc.bo.RoleMenu;
import com.tmser.tr.uc.dao.RoleMenuDao;
import com.tmser.tr.uc.service.RoleMenuService;
/**
 * 
 * <pre>
 *
 * </pre>
 *
 * @author 川子
 * @version $Id: RoleMenuServiceImpl.java, v 1.0 2015年9月8日 下午4:47:18 川子 Exp $
 */
@Service
@Transactional
public class RoleMenuServiceImpl extends AbstractService<RoleMenu, Integer> implements RoleMenuService{

	@Autowired
	private RoleMenuDao roleMenuDao;
	
	@Override
	public BaseDAO<RoleMenu, Integer> getDAO() {
		return roleMenuDao;
	}
	
	
}
