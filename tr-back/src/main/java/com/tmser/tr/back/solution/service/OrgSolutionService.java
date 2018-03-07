package com.tmser.tr.back.solution.service;

import com.tmser.tr.common.service.BaseService;
import com.tmser.tr.uc.bo.SysOrgSolution;

public interface OrgSolutionService extends BaseService<SysOrgSolution, Integer> {

	/**
	 * 编辑学校应用范围
	 * 
	 * @param orgIds
	 *            　范围机构id列表
	 * @param solId
	 *            范围所属方案id
	 */
	void editOrgSolution(String ids, Integer solId);

	/**
	 * 更新机构用户销售方案
	 * 
	 * @param solutionId
	 * @param orgId
	 */
	void updateUserSolution(Integer solutionId, Integer orgId);

	/**
	 * 删除方案范围
	 * @param orgId 机构ID，
	 * @param sid 方案ID
	 */
	void deleteSolutionFw(Integer orgId, Integer sid);
}
