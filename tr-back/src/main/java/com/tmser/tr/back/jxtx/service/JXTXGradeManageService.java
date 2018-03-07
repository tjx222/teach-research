/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.back.jxtx.service;

import java.util.List;

import com.tmser.tr.manage.meta.Meta;
import com.tmser.tr.manage.meta.service.MetaRelationshipService;

/**
 * 
 * <pre>
 * 
 * </pre>
 * 
 * @author wy
 * @version $Id: JXTXGradeManageService.java, v 1.0 2015年8月27日 下午6:25:52 wy Exp
 *          $
 */
public interface JXTXGradeManageService extends MetaRelationshipService{

	/**
	 * 根据学段查询所有的年级
	 * 
	 * @param id
	 * @return
	 */
	List<Meta> queryGradeByEid(Integer eid);

	/**
	 * 根据ID查询未添加的年级
	 * 
	 * @param id
	 * @return
	 */
	List<Meta> queryMeta(Integer eid);

	/**
	 * 保存未添加的年级
	 * 
	 * @param metas
	 */
	void saveGradeMate(Integer[] ids, Integer eid);

	/**
	 * 删除已添加的年级
	 * 
	 * @param metaship
	 */
	void deleteById(Integer phaseId, Integer id);

	/**
	 * 修改年级排序
	 * 
	 * @param gradeIds
	 * @param eid
	 */
	boolean sortGradeMate(List<Integer> gradeIds, String gradeNames, Integer eid);

}
