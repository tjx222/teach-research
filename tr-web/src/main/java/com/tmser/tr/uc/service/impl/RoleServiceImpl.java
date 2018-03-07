/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.uc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmser.tr.common.dao.BaseDAO;
import com.tmser.tr.common.orm.SqlMapping;
import com.tmser.tr.common.service.AbstractService;
import com.tmser.tr.common.utils.WebThreadLocalUtils;
import com.tmser.tr.uc.bo.Role;
import com.tmser.tr.uc.bo.RoleType;
import com.tmser.tr.uc.bo.SysOrgSolution;
import com.tmser.tr.uc.bo.User;
import com.tmser.tr.uc.dao.RoleDao;
import com.tmser.tr.uc.dao.RoleTypeDao;
import com.tmser.tr.uc.dao.SysOrgSolutionDao;
import com.tmser.tr.uc.service.RoleService;
import com.tmser.tr.uc.utils.SessionKey;
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
	private RoleTypeDao roleTypeDao;
	
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

	/**
	 * 通过机构和应用方向获取角色集合
	 * @param orgId
	 * @param usePosition
	 * @return
	 */
	@Override
	public List<Role> findRoleListByUseOrgId(Integer orgId, Integer usePosition) {
		SysOrgSolution sos = new SysOrgSolution();
		Role rl = new Role();
		if(orgId != null){
			sos.setOrgId(orgId);
			SysOrgSolution orgSolution = sosDao.getOne(sos);
			if(orgSolution != null){
				rl.setSolutionId(orgSolution.getSolutionId());
			}else{
				rl.setSolutionId(0);
			}
		}else{
			rl.setSolutionId(0);
		}
		rl.setIsDel(false);
		if(usePosition != null){
			rl.setUsePosition(usePosition);
		}
		return roleDao.listAll(rl);
	}
	
	@Override
	public List<Role> findRoleByRoleName(String roleName, Integer usePosition) {
		SysOrgSolution sos = new SysOrgSolution();
		User user = (User) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_USER);
		Role rl = new Role();
		rl.setRoleName(SqlMapping.LIKE_PRFIX +roleName+ SqlMapping.LIKE_PRFIX);
		if(user != null && user.getOrgId()!= null){
			sos.setOrgId(user.getOrgId());
			SysOrgSolution orgSolution = sosDao.getOne(sos);
			if(orgSolution != null){
				rl.setSolutionId(orgSolution.getSolutionId());
			}else{
				rl.setSolutionId(0);
			}
		}else{
			rl.setSolutionId(0);
		}
		rl.setIsDel(false);
		rl.setUsePosition(usePosition);
		return  roleDao.listAll(rl);
	}

	@Override
	public List<Role> findRoleBySysRoleId(Integer sysRoleId, Integer usePosition) {
		SysOrgSolution sos = new SysOrgSolution();
		User user = (User) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_USER);
		Role rl = new Role();
		rl.setSysRoleId(sysRoleId);
		if(user != null && user.getOrgId()!= null){
			sos.setOrgId(user.getOrgId());
			SysOrgSolution orgSolution = sosDao.getOne(sos);
			if(orgSolution != null){
				rl.setSolutionId(orgSolution.getSolutionId());
			}else{
				rl.setSolutionId(0);
			}
		}else{
			rl.setSolutionId(0);
		}
		rl.setIsDel(false);
		rl.setUsePosition(usePosition);
		return  roleDao.listAll(rl);
	}

	/**
	 * @param sysRoleId
	 * @return
	 * @see com.tmser.tr.uc.service.RoleService#findRoleTypeBySysRoleId(java.lang.Integer)
	 */
	@Override
	public RoleType findRoleTypeBySysRoleId(Integer sysRoleId) {
		RoleType model = new RoleType();
		model.setCode(sysRoleId);
		return roleTypeDao.getOne(model);
	}
}
