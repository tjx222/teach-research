/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */

package com.tmser.tr.manage.meta.dao;

import java.util.List;

import com.tmser.tr.common.dao.BaseDAO;
import com.tmser.tr.manage.meta.bo.BookSync;

/**
 * <pre>
 *
 * </pre>
 *
 * @author ghw
 * @version $Id: BookSyncDao.java, v 1.0 2016年7月15日 下午4:01:35 ghw Exp $
 */
public interface BookSyncDao extends BaseDAO<BookSync, Integer> {
  /**
   * 通过comId列表获取书籍
   * 
   * @return List
   */
  List<BookSync> getByIds(List<Integer> ids);

  /**
   * 根据comId修改关联书籍id为空
   * 
   * @param comId
   *          主键id
   * @return Integer
   */
  Integer delRelationComId(String comId);

  /**
   * 获取学校或区域同步的书籍
   * 
   * @param orgId
   *          机构id
   * @param areaIds
   *          区域id
   * @param gradeId
   *          年级id
   * @param subjectId
   *          学科id
   * @param phaseId
   *          学段id
   * @param publisherId
   *          发布者id
   * @return List
   */
  List<BookSync> getBookSyncsByOrg(Integer orgId, List<Integer> areaIds, Integer gradeId, Integer subjectId,
      Integer phaseId, Integer publisherId);
}
