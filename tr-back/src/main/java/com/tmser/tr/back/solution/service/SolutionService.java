package com.tmser.tr.back.solution.service;

import com.tmser.tr.common.page.PageList;
import com.tmser.tr.common.service.BaseService;
import com.tmser.tr.uc.bo.SysSolution;

/**
 * 
 * <pre>
 * 
 * </pre>
 * 
 * @author 3020mt
 * @version $Id: SolutionService.java, v 1.0 2016年3月31日 下午2:33:46 3020mt Exp $
 */

public interface SolutionService extends BaseService<SysSolution, Integer> {

	/**
	 * 添加销售方案,将同时拷贝系统角色及角色权限到方案下
	 * 
	 * @param solution
	 * @return
	 */
	SysSolution addSolution(SysSolution solution);

	/**
	 * 清除销售方案所有信息，包括方案信息，方案角色，方案角色权限，方案应用范围
	 * 
	 * @param id
	 *            方案id
	 */
	void clearSolution(Integer id);

	/**
	 * 查询方案分页数据
	 * 
	 * @param solution
	 * @return
	 */
	PageList<SysSolution> getPageSolutionList(SysSolution solution);
}
