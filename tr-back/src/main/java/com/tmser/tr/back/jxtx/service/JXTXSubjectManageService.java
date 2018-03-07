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
 * @version $Id: JXTXSubjectManageService.java, v 1.0 2015年8月28日 下午5:19:14 wy
 *          Exp $
 */
public interface JXTXSubjectManageService extends MetaRelationshipService{

	/**
	 * 根据学段查询所有的年级
	 * 
	 * @param id
	 * @return
	 */
	List<Meta> querySubjectByEid(Integer eid);

	/**
	 * 根据ID查询未添加的年级
	 * 
	 * @param id
	 * @return
	 */
	List<Meta> queryMeta(Integer eid);

	/**
	 * 保存未添加的学科
	 * 
	 * @param metas
	 */
	void saveSubjectMate(Integer[] ids, Integer eid);

	/**
	 * 删除已添加的年级
	 * 
	 * @param metaship
	 */
	void deleteById(Integer phaseId, Integer id);

	/**
	 * 排序
	 * 
	 * @param subjectIds
	 * @param descs
	 * @param eid
	 * @return
	 */
	boolean sortSubjectMate(List<Integer> subjectIds, String descs, Integer eid);

}
