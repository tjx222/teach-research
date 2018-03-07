package com.tmser.tr.back.solution.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmser.tr.back.solution.service.SolutionService;
import com.tmser.tr.common.dao.BaseDAO;
import com.tmser.tr.common.orm.SqlMapping;
import com.tmser.tr.common.page.PageList;
import com.tmser.tr.common.service.AbstractService;
import com.tmser.tr.uc.bo.Role;
import com.tmser.tr.uc.bo.SysSolution;
import com.tmser.tr.uc.dao.SysSolutionDao;
import com.tmser.tr.uc.service.RoleService;

@Service
@Transactional
public class SolutionServiceImpl extends AbstractService<SysSolution, Integer> implements SolutionService {

	@Autowired
	private SysSolutionDao sysSolutionDao;

	@Autowired
	private RoleService roleService;

	@Override
	public BaseDAO<SysSolution, Integer> getDAO() {
		return sysSolutionDao;
	}

	@Override
	public void clearSolution(Integer solutionId) {
		sysSolutionDao.delete(solutionId);
		sysSolutionDao.clearSolution(solutionId);
	}

	@Override
	public PageList<SysSolution> getPageSolutionList(SysSolution solution) {

		if (StringUtils.isEmpty(solution.getName()) && StringUtils.isEmpty(solution.getDescs())) {
			return findByPage(solution);
		} else {
			SysSolution model = new SysSolution();
			if (StringUtils.isNotEmpty(solution.getName())) {
				model.setName(SqlMapping.LIKE_PRFIX + solution.getName() + SqlMapping.LIKE_PRFIX);
			}

			if (StringUtils.isNotEmpty(solution.getDescs())) {
				model.buildCondition("and id in (select so.solutionId from SysOrgSolution so where so.orgId in (select o.id from Organization o where o.name like :orgname)) ").put("orgname",
						"%" + solution.getDescs() + "%");
			}
			model.addPage(solution.getPage());

			return findByPage(model);
		}

	}

	@Override
	public SysSolution addSolution(SysSolution solution) {
		solution = this.save(solution);// 保存方案
		roleService.copyRole(solution.getId());
		Role role = new Role(); // 复制角色对应的权限
		role.setSolutionId(solution.getId());
		List<Role> roles = roleService.findAll(role);
		roleService.copyRoleMenu(roles);
		return solution;
	}

}
