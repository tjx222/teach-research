/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.check.dao;

import java.util.List;
import java.util.Map;

import com.tmser.tr.check.bo.CheckInfo;
import com.tmser.tr.common.dao.BaseDAO;

/**
 * 查阅基础信息 DAO接口
 * 
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: CheckInfo.java, v 1.0 2015-03-14 Generate Tools Exp $
 */
public interface CheckInfoDao extends BaseDAO<CheckInfo, Integer> {
  /**
   * 按模型分组查询统计
   * 
   * @param model
   * @return map 包含 userid -- cnt (统计数)
   */
  List<Map<String, Object>> countCheckInfoGroupByAuth(CheckInfo model);

  /**
   * 按模型分组查询统计
   * 
   * @param model
   * @return
   */
  List<Map<String, Object>> countCheckInfoGroupByUser(CheckInfo model);

  /**
   * 更新状态
   * 
   * @param resId
   * @param restype
   * @return
   */
  Integer updateCheckInfoUpdateState(Integer resId, Integer restype,
      Integer schoolYear);

  /**
   * 统计查阅的资源数目
   * 
   * @param info
   * @return
   */
  Integer countResNum(CheckInfo info);

  /**
   * 获取所有审阅的资源id
   * 
   * @param model
   * @return
   */
  List<Integer> findResIds(List<Integer> resTypes);

  /**
   * 获取所有审阅的资源id
   * 
   * @param resTypes
   * @param currentUserId
   *          查阅用户id
   * @return
   */
  List<Integer> findResIdsAndUserId(List<Integer> resTypes, Integer userId);

  /**
   * 获取所有本人已查阅的所有人员的所有项目平均分
   * 
   * @param vo
   * @return
   */
  List<Map<String, Object>> getAllCheckInfoAverage(CheckInfo ck, Integer orgId);

  /**
   * 通过author获取单项平均分
   * 
   * @param ck
   * @return
   */
  List<Map<String, Object>> getAvgByAuthordId(CheckInfo ck);

  /**
   * 通过author获取相关平均分
   * 
   * @param ck
   * @return
   */
  List<Map<String, Object>> getAllCheckInfoAverageByAuthor(CheckInfo ck);

  /**
   * 获取用户单个课题得分平均分
   * 
   * @param ck
   * @return
   */
  Integer getAvgByInfoId(CheckInfo ck);
  
  /**
   * 按模型分组查询已查阅篇数统计
   * 
   * @param model
   * @return map 包含 userid -- cnt (统计数)
   */
  List<Map<String, Object>> countCheckLessonPlanGroupByAuth(CheckInfo model);
}