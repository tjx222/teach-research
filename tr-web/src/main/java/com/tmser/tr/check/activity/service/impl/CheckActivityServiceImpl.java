/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.check.activity.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmser.tr.activity.bo.Activity;
import com.tmser.tr.activity.dao.ActivityDao;
import com.tmser.tr.check.activity.service.CheckActivityService;
import com.tmser.tr.check.bo.CheckInfo;
import com.tmser.tr.check.service.CheckInfoService;
import com.tmser.tr.common.ResTypeConstants;
import com.tmser.tr.common.orm.SqlMapping;
import com.tmser.tr.common.page.Page;
import com.tmser.tr.common.page.PageList;
import com.tmser.tr.common.utils.WebThreadLocalUtils;
import com.tmser.tr.uc.bo.User;
import com.tmser.tr.uc.utils.SessionKey;

/**
 * 查阅活动（集体备课） 服务实现类
 * 
 * <pre>
 *
 * </pre>
 * 
 * @version $Id: CheckActivityServiceImpl.java, v 1.0 2015-04-20 Generate Tools
 *          Exp $
 */
@Service
@Transactional
public class CheckActivityServiceImpl implements CheckActivityService {

  @Autowired
  private ActivityDao activityDao;

  @Resource
  private CheckInfoService checkInfoService;

  /**
   * 查阅活动信息数据
   * 
   * @param grade
   * @param subject
   * @param page
   * @return
   * @see com.tmser.tr.check.activity.service.CheckActivityService#findCheckActivity(java.lang.Integer,
   *      java.lang.Integer, com.tmser.tr.common.page.Page)
   */
  @Override
  public Map<String, Object> findCheckActivity(Integer grade, Integer subject, Integer term, Page page) {
    Map<String, Object> map = new HashMap<String, Object>();
    Activity model = new Activity();
    User u = (User) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_USER);// 用户
    Integer schoolYear = (Integer) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SCHOOLYEAR);// 学年
    model.setIsSubmit(true);// 已提交
    if (grade != null && grade > 0) {// 年级
      model.setGradeIds(SqlMapping.LIKE_PRFIX + "," + grade + "," + SqlMapping.LIKE_PRFIX);
    }
    if (subject != null && subject > 0) {// 学科
      model.setSubjectIds(SqlMapping.LIKE_PRFIX + "," + subject + "," + SqlMapping.LIKE_PRFIX);
    }
    model.setOrgId(u.getOrgId());// 机构
    model.setSchoolYear(schoolYear);// 学年
    model.setTerm(term);// 学期
    //model.addCustomCondition(" and organizeUserId != " + u.getId(), new HashMap<String, Object>());
    model.addOrder(" createTime desc ");
    int countAll = activityDao.count(model);// 总数
    map.put("countAll", countAll);
    page.setPageSize(17);
    model.addPage(page);
    PageList<Activity> listPage = activityDao.listPage(model);
    if (!CollectionUtils.isEmpty(listPage.getDatalist())) {
      List<Activity> alist = getNewPageList(listPage.getDatalist(), u.getId());
      listPage.setDatalist(alist);
      int count = getCountAudit(model, u.getId());
      map.put("countAudit", count);
    } else {
      map.put("countAudit", 0);
    }
    map.put("listPage", listPage);
    return map;
  }

  /**
   * 获得被人查阅的集备数量
   * 
   * @param model
   * @return
   */
  private int getCountAudit(Activity model, Integer userId) {
    List<Activity> list = activityDao.listAll(model);
    List<Integer> actids = new ArrayList<Integer>();
    for (Activity a : list) {
      actids.add(a.getId());
    }
    Map<String, Object> params = new HashMap<String, Object>();
    params.put("actids", actids);
    CheckInfo ci = new CheckInfo();
    ci.setUserId(userId);
    ci.setResType(ResTypeConstants.ACTIVITY);
    ci.addCustomCondition("and resId in(:actids)", params);
    return checkInfoService.count(ci);
  }

  /**
   * 获取最新的教研活动列表
   * 
   * @param list
   * @return
   */
  private List<Activity> getNewPageList(List<Activity> list, Integer userId) {
    List<Integer> actids = new ArrayList<Integer>();
    if (!CollectionUtils.isEmpty(list)) {
      for (Activity a : list) {
        actids.add(a.getId());
      }
      Map<String, Object> params = new HashMap<String, Object>();
      params.put("actids", actids);
      CheckInfo ci = new CheckInfo();
      ci.setUserId(userId);
      ci.setResType(ResTypeConstants.ACTIVITY);
      ci.addCustomCondition("and resId in(:actids)", params);
      ci.addCustomCulomn("resId");
      List<CheckInfo> cilist = checkInfoService.findAll(ci);
      for (CheckInfo cit : cilist) {
        params.put(String.valueOf(cit.getResId()), cit);
      }
      for (Activity a : list) {
        if (params.get(String.valueOf(a.getId())) != null) {
          a.setIsAudit(true);
        } else {
          a.setIsAudit(false);
        }
      }
    }
    return list;
  }

}
