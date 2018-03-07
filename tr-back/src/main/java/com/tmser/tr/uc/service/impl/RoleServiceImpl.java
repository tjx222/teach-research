/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.uc.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmser.tr.common.dao.BaseDAO;
import com.tmser.tr.common.service.AbstractService;
import com.tmser.tr.manage.meta.bo.Menu;
import com.tmser.tr.manage.meta.dao.MenuDao;
import com.tmser.tr.uc.bo.Role;
import com.tmser.tr.uc.bo.RoleMenu;
import com.tmser.tr.uc.bo.SysOrgSolution;
import com.tmser.tr.uc.dao.RoleDao;
import com.tmser.tr.uc.dao.RoleMenuDao;
import com.tmser.tr.uc.dao.SysOrgSolutionDao;
import com.tmser.tr.uc.service.RoleService;
/**
 * 用户角色 服务实现类
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: Role.java, v 1.0 2015-02-05 Generate Tools Exp $
 */
@Service
@Transactional
public class RoleServiceImpl extends AbstractService<Role, Integer> implements RoleService {

	@Autowired
	private RoleDao roleDao;
	
	@Autowired
	private RoleMenuDao roleMenuDao;
	
	@Autowired
	private MenuDao menuDao;
	
	@Autowired
	private SysOrgSolutionDao sosDao;
	
	/**
	 * @return
	 * @see com.tmser.tr.common.service.BaseService#getDAO()
	 */
	@Override
	public BaseDAO<Role, Integer> getDAO() {
		return roleDao;
	}

	/**
	 * @param userid
	 * @return
	 * @see com.tmser.tr.uc.service.RoleService#findRoleByUserid(java.lang.Integer)
	 */
	@Override
	public List<Role> findRoleByUserid(Integer userid,Integer sysRoleId) {
		if(userid != null){
			return roleDao.listRoleByUserid(userid,sysRoleId);
		}
		return null;
	}
	
	
	@Override
	public List<Role> getRoleListByOrgId(Integer orgId) {
		Role role = new Role();
		SysOrgSolution sos = new SysOrgSolution();
		sos.setOrgId(orgId);
		sos.setIsDelete(false);
		SysOrgSolution sysOrgSolution = sosDao.getOne(sos);
		if (sysOrgSolution != null) {
			role.setSolutionId(sysOrgSolution.getSolutionId());
		} else {
			role.setSolutionId(0);
		}
		List<Role> list = this.roleDao.listAll(role);
		return list;
	}

	@Override
	public List<Role> findRoleListByUseOrgId(Integer orgId,
			Integer usePosition) {
		Role rl = new Role();
		if (orgId != null) {
			SysOrgSolution sos = new SysOrgSolution();
			sos.setOrgId(orgId);
			sos.setIsDelete(false);
			SysOrgSolution orgSolution = sosDao.getOne(sos);
			if (orgSolution != null) {
				rl.setSolutionId(orgSolution.getSolutionId());
			} else {
				rl.setSolutionId(0);
			}
		} else {
			rl.setSolutionId(0);
		}
		rl.setIsDel(false);
		rl.setUsePosition(usePosition);
		List<Role> list = roleDao.listAll(rl);
		return list;
	}

	
	@Override
	public List<Menu> findMenuByRole(Integer sysRoleId) {
		RoleMenu model = new RoleMenu();
		model.setRoleId(sysRoleId);
		model.setIsDel(RoleMenu.NORMAL);
		List<Menu> menuList=new ArrayList<>();
		List<RoleMenu> rmList = roleMenuDao.listAll(model);
		for(RoleMenu rm : rmList){
			menuList.add(menuDao.get(rm.getMenuId()));
		}
		
		return menuList;
	}
	

	@Override
	public void copyRole(Integer solutionId) {
		roleDao.copyRoleForSolution(solutionId);
	}


	@Override
	public void copyRoleMenu(List<Role> list) {
		roleMenuDao.copyMenuToSolution(list);
	}

}
