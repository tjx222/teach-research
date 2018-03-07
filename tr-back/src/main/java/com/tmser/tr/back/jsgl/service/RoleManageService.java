package com.tmser.tr.back.jsgl.service;

import java.util.List;

import com.tmser.tr.uc.bo.Role;

public interface RoleManageService{

	/**
	 * 同步系统预制角色到方案中
	 * @param solutionId 方案id
	 * @return state true : 成功， false: 无需同步
	 */
	boolean syncRole(Integer solutionId);
	
	/**
	 * 新增或编辑角色
	 * @param role
	 * @param pv
	 * @return
	 */
	int addOrEditNeed(Role role,List<Integer> pv);
}
