/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.managerecord.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmser.tr.activity.bo.Activity;
import com.tmser.tr.activity.dao.ActivityDao;
import com.tmser.tr.check.bo.CheckInfo;
import com.tmser.tr.check.dao.CheckInfoDao;
import com.tmser.tr.common.ResTypeConstants;
import com.tmser.tr.common.dao.AbstractQuery;
import com.tmser.tr.common.utils.WebThreadLocalUtils;
import com.tmser.tr.managerecord.service.CheckRecordContainer;
import com.tmser.tr.managerecord.service.CheckRecordService;
import com.tmser.tr.managerecord.service.ManagerService;
import com.tmser.tr.managerecord.service.ManagerVO;
import com.tmser.tr.uc.bo.UserSpace;
import com.tmser.tr.uc.utils.SessionKey;

/**
 * <pre>
 *
 * </pre>
 *
 * @author sysc
 * @version $Id: managerServiceImpl.java, v 1.0 2015年5月7日 上午11:26:21 sysc Exp $
 */
@Service
@Transactional
public class ManagerServiceImpl extends AbstractQuery implements ManagerService {

  @Autowired
  private CheckInfoDao checkInfoDao;

  @Autowired
  private ActivityDao activityDao;

  /**
   * @param grade
   * @param term
   * @param subject
   * @return
   * @see com.tmser.tr.managerecord.service.ManagerService#findRecordList(int,
   *      int, int)
   */
  @Override
  public List<ManagerVO> findRecordList(int grade, int subject, int term) {
    List<ManagerVO> mapList = new ArrayList<ManagerVO>();
    Set<CheckRecordService> serviceSet = CheckRecordContainer.getCheckRecordServiceSet();
    for (CheckRecordService crs : serviceSet) {
      mapList.add(crs.getCheckRecord(grade, term, subject));
    }
    return mapList;
  }

  /**
   * @param term
   * @return
   * @see com.tmser.tr.managerecord.service.ManagerService#findMangerList(int)
   */
  @Override
  public List<ManagerVO> findMangerList(Integer term) {
    List<ManagerVO> mapList = new ArrayList<ManagerVO>();
    return mapList;
  }

  /**
   * @param term
   * @return
   * @see com.tmser.tr.managerecord.service.ManagerService#findActivityList(int)
   */
  @Override
  public List<ManagerVO> findActivityList(Integer term) {
    UserSpace userSpace = (UserSpace) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE);
    Integer schoolYear = (Integer) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SCHOOLYEAR);
    List<ManagerVO> voList = new ArrayList<ManagerVO>();
    ManagerVO am = new ManagerVO();

    Activity activity = new Activity();
    activity.addAlias("a");
    activity.addCustomCulomn("a.id");
    activity.setTerm(term);
    activity.setOrgId(userSpace.getOrgId());
    activity.setStatus(1);
    activity.setPhaseId(userSpace.getPhaseId());
    activity.setSchoolYear(schoolYear);
    int allCount = activityDao.count(activity);// 全校集体备课活动数
    activity.setOrganizeUserId(userSpace.getUserId());// 用户Id

    int myCount = activityDao.count(activity);// 发起集体备课活动数

    activity.setIsShare(true);
    int shareCount = activityDao.count(activity);// 分享集体备课活动数

    activity.setOrganizeUserId(null);
    activity.setIsShare(null);
    activity
        .buildCondition(
            " and EXISTS(select b.id from Discuss b where b.activityId = a.id and b.typeId=:typeId and b.crtId=:crtId LIMIT 0,1)")
        .put("typeId", ResTypeConstants.ACTIVITY).put("crtId", userSpace.getUserId());
    int joinCount = activityDao.count(activity);

    am.setSubmitNum(allCount); // 总数
    am.setCheckNum(myCount);// 个人发起数
    am.setCommentNum(joinCount);// 个人参与数

    am.setShareNum(shareCount); // 分享数

    am.setName("集体备课");
    am.setUri("activity");
    voList.add(am);

    return voList;
  }

  /**
   * @param sql
   * @param id
   * @return
   * @see com.tmser.tr.managerecord.service.ManagerService#count(java.lang.String,
   *      int)
   */
  @Override
  public void index(Map<String, Integer> map) {
    UserSpace userSpace = (UserSpace) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE);
    Integer schoolYear = (Integer) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SCHOOLYEAR);
    List<Integer> resTypes = new ArrayList<Integer>();
    Set<CheckRecordService> serviceSet = CheckRecordContainer.getCheckRecordServiceSet();
    for (CheckRecordService crs : serviceSet) {
      for (Integer type : crs.getType()) {
        resTypes.add(type);
      }
    }
    Map<String, Object> paramMap = new HashMap<String, Object>(2);
    paramMap.put("resTypes", resTypes);

    CheckInfo model = new CheckInfo();
    model.setSchoolYear(schoolYear);
    model.setUserId(userSpace.getUserId());
    model.setPhase(userSpace.getPhaseId());
    model.addCustomCondition("and resType in(:resTypes) ", paramMap);
    int checkCount = checkInfoDao.count(model);// 查阅数

    model.setHasOptinion(true);
    int optionCount = checkInfoDao.count(model);// 查阅意见数

    Activity activity = new Activity();
    activity.addAlias("a");
    activity.addCustomCulomn("a.id");
    activity.setOrganizeUserId(userSpace.getUserId());// 用户Id
    activity.setStatus(1);
    activity.setPhaseId(userSpace.getPhaseId());
    activity.setSchoolYear(schoolYear);
    int myCount = activityDao.count(activity);// 发起集体备课活动数

    activity.setIsShare(true);
    int shareCount = activityDao.count(activity);// 分享集体备课活动数

    activity.setOrganizeUserId(null);
    activity.setIsShare(null);
    activity
        .buildCondition(
            " and EXISTS(select b.id from Discuss b where b.activityId = a.id and b.typeId=:typeId and b.crtId=:crtId LIMIT 0,1)")
        .put("typeId", ResTypeConstants.ACTIVITY).put("crtId", userSpace.getUserId());

    int joinCount = activityDao.count(activity);

    map.put("checkCount", checkCount);
    map.put("optionCount", optionCount);
    map.put("writeCount", 0);
    map.put("punishCount", 0);
    map.put("myCount", myCount);
    map.put("shareCount", shareCount);
    map.put("jionCount", joinCount);
  }
}
