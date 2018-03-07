/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.manage.resources.service;

import java.util.List;
import java.util.Map;

import com.tmser.tr.common.service.BaseService;
import com.tmser.tr.manage.resources.bo.Attach;
import com.tmser.tr.manage.resources.bo.Resources;

/**
 * 集体备课活动资源 服务类
 * 
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: ActivityRes.java, v 1.0 2015-03-06 Generate Tools Exp $
 */

public interface AttachService extends BaseService<Attach, Integer> {

  /**
   * 按类型及活动id更新附件
   * 
   * @param type
   * @param activeId
   * @param crtId
   * @param resids
   */
  Map<String, List<Resources>> updateAttach(Integer type, Integer activeId, Integer crtId, String... resids);

  /**
   * 按类型及活动id增加附件
   * 
   * @param type
   * @param activeId
   * @param crtId
   * @param resids
   */
  void addAttach(Integer type, Integer activeId, Integer crtId, String... resids);

  /**
   * 按类型及活动id增加附件
   * 
   * @param type
   * @param activeId
   * @param crtId
   * @param resids
   */
  void addAttach(Integer type, Integer activeId, Integer crtId, List<String> resids);

  /**
   * 删除活动下的附件
   * 
   * @param type
   * @param activityId
   */
  void deleteAttach(Integer type, Integer activityId);
}
