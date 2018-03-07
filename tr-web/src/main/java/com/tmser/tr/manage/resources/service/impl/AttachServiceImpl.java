/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.manage.resources.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmser.tr.common.dao.BaseDAO;
import com.tmser.tr.common.service.AbstractService;
import com.tmser.tr.manage.resources.bo.Attach;
import com.tmser.tr.manage.resources.bo.Resources;
import com.tmser.tr.manage.resources.dao.AttachDao;
import com.tmser.tr.manage.resources.service.AttachService;
import com.tmser.tr.utils.StringUtils;

/**
 * 集体备课活动资源 服务实现类
 * 
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: ActivityRes.java, v 1.0 2015-03-06 Generate Tools Exp $
 */
@Service
@Transactional
public class AttachServiceImpl extends AbstractService<Attach, Integer> implements AttachService {

  @Autowired
  private AttachDao attachDao;

  @Autowired
  private ResourcesServiceImpl resourcesService;

  /**
   * @return
   * @see com.tmser.tr.common.service.BaseService#getDAO()
   */
  @Override
  public BaseDAO<Attach, Integer> getDAO() {
    return attachDao;
  }

  /**
   * @param type
   * @param activeId
   * @param resids
   * @see com.tmser.tr.manage.resources.service.AttachService#updateAttach(java.lang.Integer,
   *      java.lang.Integer, java.lang.String[])
   */
  @Override
  public Map<String, List<Resources>> updateAttach(Integer type, Integer activeId, Integer crtId, String... resids) {
    if (type == null || activeId == null) {
      logger.warn("parameter illegal [type={},activeId={}", type, activeId);
      return null;
    }

    Map<String, List<Resources>> attachMap = new HashMap<String, List<Resources>>();

    Attach model = new Attach();
    model.setActivityType(type);
    model.setActivityId(activeId);
    List<Attach> atts = attachDao.listAll(model);

    Iterator<Attach> attIts = atts.iterator();
    List<String> resList = new ArrayList<String>();
    if (resids != null) {
      resList.addAll(Arrays.asList(resids));
    }

    while (attIts.hasNext()) {
      Attach ach = attIts.next();
      if (resList.size() > 0 && resList.contains(ach.getResId())) {
        attIts.remove();
        resList.remove(ach.getResId());
      }
    }

    List<Resources> delResources = new ArrayList<Resources>();
    if (atts.size() > 0) {
      for (Attach ach : atts) {
        attachDao.delete(ach.getId());
        delResources.add(resourcesService.findOne(ach.getResId()));
        resourcesService.deleteResources(ach.getResId());
      }
    }

    attachMap.put("del", delResources);
    List<Resources> addResources = new ArrayList<Resources>();
    for (String resId : resList) {
      if (StringUtils.isNotEmpty(resId)) {
        addResources.add(resourcesService.findOne(resId));
      }
    }

    attachMap.put("add", addResources);
    if (resList.size() > 0) {
      addAttach(type, activeId, crtId, resList);
    }
    return attachMap;
  }

  @Override
  public void addAttach(Integer type, Integer activeId, Integer crtId, List<String> resids) {
    if (type == null || activeId == null) {
      logger.warn("parameter illegal [type={},activeId={}", type, activeId);
      return;
    }

    if (resids != null) {
      List<Attach> addAttachs = new ArrayList<Attach>(resids.size());
      Date now = new Date();
      for (String resid : resids) {
        if (StringUtils.isNotBlank(resid)) {
          Attach m = new Attach();
          m.setActivityType(type);
          m.setActivityId(activeId);
          m.setResId(resid);
          m.setCrtId(crtId);
          m.setCrtDttm(now);
          Resources res = resourcesService.updateTmptResources(resid);
          m.setAttachName(res.getName());
          m.setExt(res.getExt());
          addAttachs.add(m);
        }
      }
      attachDao.batchInsert(addAttachs);
    }
  }

  /**
   * @param type
   * @param activeId
   * @param resids
   * @see com.tmser.tr.manage.resources.service.AttachService#addAttach(java.lang.Integer,
   *      java.lang.Integer, java.lang.String[])
   */
  @Override
  public void addAttach(Integer type, Integer activeId, Integer crtId, String... resids) {
    addAttach(type, activeId, crtId, Arrays.asList(resids));
  }

  /**
   * 删除活动下的附件
   * 
   * @param type
   * @param activityId
   * @see com.tmser.tr.manage.resources.service.AttachService#deleteAttach(java.lang.Integer,
   *      java.lang.Integer)
   */
  @Override
  public void deleteAttach(Integer type, Integer activityId) {
    updateAttach(type, activityId, null);
  }

}
