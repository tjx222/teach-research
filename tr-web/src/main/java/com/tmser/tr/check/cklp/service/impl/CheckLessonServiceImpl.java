/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.check.cklp.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;

import com.tmser.tr.check.bo.CheckInfo;
import com.tmser.tr.check.cklp.service.CheckLessonService;
import com.tmser.tr.check.dao.CheckInfoDao;
import com.tmser.tr.common.ResTypeConstants;
import com.tmser.tr.common.bo.QueryObject.JOINTYPE;
import com.tmser.tr.common.utils.WebThreadLocalUtils;
import com.tmser.tr.lessonplan.bo.LessonInfo;
import com.tmser.tr.lessonplan.bo.LessonPlan;
import com.tmser.tr.lessonplan.dao.LessonInfoDao;
import com.tmser.tr.lessonplan.dao.LessonPlanDao;
import com.tmser.tr.uc.SysRole;
import com.tmser.tr.uc.bo.User;
import com.tmser.tr.uc.bo.UserSpace;
import com.tmser.tr.uc.dao.UserSpaceDao;
import com.tmser.tr.uc.utils.SessionKey;
import com.tmser.tr.utils.JyAssert;

/**
 * <pre>
 *
 * </pre>
 *
 * @author tmser
 * @version $Id: CheckLessonServiceImpl.java, v 1.0 2015年3月14日 下午4:49:33 tmser
 *          Exp $
 */
@Service
@Transactional
public class CheckLessonServiceImpl implements CheckLessonService {

  @Autowired
  private LessonInfoDao lessonInfoDao;

  @Autowired
  private LessonPlanDao lessonPlanDao;

  @Autowired
  private CheckInfoDao checkInfoDao;

  @Autowired
  private UserSpaceDao userSpaceDao;

  /**
   * @param grade
   * @param subject
   * @param type
   * @return
   * @see com.tmser.tr.check.cklp.service.CheckLessonService#listTchLessonStatics(java.lang.Integer,
   *      java.lang.Integer, java.lang.Integer)
   */
  @Override
  public Map<Integer, List<Object>> listTchLessonStatics(Integer grade, Integer subject, Integer type) {
    JyAssert.notNull(type, "type  can't be null");
    User u = (User) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_USER);
    Integer schoolYear = (Integer) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SCHOOLYEAR);
    Map<Integer, List<Object>> rsMap = new HashMap<Integer, List<Object>>();

    UserSpace usmodel = new UserSpace();
    usmodel.setGradeId(grade);
    usmodel.setSubjectId(subject);
    usmodel.setSysRoleId(SysRole.TEACHER.getId());
    usmodel.setEnable(UserSpace.ENABLE);
    usmodel.setOrgId(u.getOrgId());
    usmodel.setSchoolYear(schoolYear);
    List<UserSpace> allUser = userSpaceDao.listAll(usmodel);

    for (UserSpace us : allUser) {
      List<Object> list = new ArrayList<Object>(4);
      list.add(0, 0l);
      list.add(1, 0l);
      list.add(2, 0l);
      list.add(3, 0l);
      list.add(4, 0l);
      list.add(5, 0l);
      rsMap.put(us.getUserId(), list);
    }

    // 撰写课题数
    LessonInfo model = new LessonInfo();
    model.addAlias("l");
    model.setGradeId(grade);
    model.setSubjectId(subject);
    model.setOrgId(u.getOrgId());
    model.addCustomCondition(buildWriteLessonTypeSql(type, model.alias()), null);
    model.setSchoolYear(schoolYear);
    List<Map<String, Object>> unSubmitList = lessonInfoDao.countLessonGroupByUser(model);
    model.addCustomCondition(buildCommitLessonTypeSql(type, model.alias()), null);
    List<Map<String, Object>> submitList = lessonInfoDao.countLessonGroupByUser(model);

    // 撰写篇数
    LessonPlan plan1 = new LessonPlan();
    plan1.addAlias("p");
    plan1.setGradeId(grade);
    plan1.setSubjectId(subject);
    plan1.setOrgId(u.getOrgId());
    plan1.setSchoolYear(schoolYear);
    plan1.setPlanType(type);
    List<Map<String, Object>> writePlanCountList = lessonPlanDao.countLessonPlanGroupByUser(plan1);
    plan1.setIsSubmit(true);
    List<Map<String, Object>> writePlanCountSubmitList = lessonPlanDao.countLessonPlanGroupByUser(plan1);

    CheckInfo cmodel = new CheckInfo();
    cmodel.setGradeId(grade);
    cmodel.setSubjectId(subject);
    cmodel.setResType(type);
    cmodel.setUserId(u.getId());
    cmodel.setSchoolYear(schoolYear);
    List<Map<String, Object>> checkedList = checkInfoDao.countCheckInfoGroupByAuth(cmodel);
    // 已查阅篇数
    List<Map<String, Object>> writePlanCountCheckList = checkInfoDao.countCheckLessonPlanGroupByAuth(cmodel);

    if (type.equals(ResTypeConstants.FANSI)) {
      LessonPlan plan = new LessonPlan();
      plan.addAlias("l");
      plan.setGradeId(grade);
      plan.setSubjectId(subject);
      plan.setOrgId(u.getOrgId());
      plan.setIsSubmit(null);
      plan.setPlanType(ResTypeConstants.FANSI_OTHER);
      plan.setSchoolYear(schoolYear);
      List<Map<String, Object>> unSubmitPlanList = lessonPlanDao.countLessonPlanGroupByUser(plan);
      plan.setIsSubmit(true);
      List<Map<String, Object>> submitPlanList = lessonPlanDao.countLessonPlanGroupByUser(plan);

      if (!CollectionUtils.isEmpty(submitPlanList)) {
        for (Map<String, Object> m : submitPlanList) {
          List<Object> list = rsMap.get(m.get("userId"));
          if (list != null)
            list.set(1, m.get("cnt"));
        }
      }
      if (!CollectionUtils.isEmpty(unSubmitPlanList)) {
        for (Map<String, Object> m : unSubmitPlanList) {
          List<Object> list = rsMap.get(m.get("userId"));
          if (list != null)
            list.set(0, m.get("cnt"));
          list.set(3, m.get("cnt"));
        }
      }
    }

    if (!CollectionUtils.isEmpty(unSubmitList)) {
      for (Map<String, Object> m : unSubmitList) {
        List<Object> list = rsMap.get(m.get("userId"));
        if (list != null) {
          Long old = (Long) list.get(0);
          list.set(0, old + (Long) m.get("cnt"));
        }
      }
    }
    if (!CollectionUtils.isEmpty(writePlanCountList)) {
      for (Map<String, Object> m : writePlanCountList) {
        List<Object> list = rsMap.get(m.get("userId"));
        if (list != null) {
          Long old = (Long) list.get(3);
          list.set(3, old + (Long) m.get("cnt"));
        }
      }
    }
    if (!CollectionUtils.isEmpty(writePlanCountCheckList)) {
      for (Map<String, Object> m : writePlanCountCheckList) {
        List<Object> list = rsMap.get(m.get("userId"));
        if (list != null) {
          Long old = (Long) list.get(5);
          list.set(5, old + (Long) m.get("cnt"));
        }
      }
    }
    if (!CollectionUtils.isEmpty(checkedList)) {
      for (Map<String, Object> m : checkedList) {
        List<Object> list = rsMap.get(m.get("userId"));
        if (list != null)
          list.set(2, m.get("cnt"));
      }
    }
    if (!CollectionUtils.isEmpty(writePlanCountSubmitList)) {
      for (Map<String, Object> m : writePlanCountSubmitList) {
        List<Object> list = rsMap.get(m.get("userId"));
        if (list != null) {
          Long old = (Long) list.get(4);
          list.set(4, old + (Long) m.get("cnt"));
        }
      }
    }
    if (!CollectionUtils.isEmpty(submitList)) {
      for (Map<String, Object> m : submitList) {
        List<Object> list = rsMap.get(m.get("userId"));
        if (list != null) {
          Long old = (Long) list.get(1);
          list.set(1, old + (Long) m.get("cnt"));
        }
      }
    }
    return rsMap;
  }

  /**
   * @param userid
   * @param bookid
   * @return
   * @see com.tmser.tr.check.cklp.service.CheckLessonService#listTchLessons(java.lang.Integer,
   *      java.lang.String)
   */
  @Override
  public List<LessonInfo> listTchLessons(Integer type, Integer userId, Integer gradeId, Integer subjectId,
      Integer searchType, Integer termId, Integer fasciculeId) {
    LessonInfo model = new LessonInfo();
    model.setUserId(userId);
    model.setGradeId(gradeId);
    model.setSubjectId(subjectId);
    model.setSchoolYear((Integer) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SCHOOLYEAR));
    model.addCustomCondition(buildCommitLessonTypeSql(type, null), null);
    if (searchType == 0) { // 按册别
      model.setFasciculeId(fasciculeId);
    } else if (searchType == 1) { // 按撰写学期
      model.setTermId(termId);
    }
    return lessonInfoDao.listAll(model);
  }

  private String buildWriteLessonTypeSql(Integer type, String alias) {
    String sql = "";
    String as = "";
    if (!StringUtils.isEmpty(alias)) {
      as = alias + ".";
    }
    switch (type) {
    case LessonPlan.JIAO_AN:
      sql = "and " + as + "jiaoanCount > 0";
      break;
    case LessonPlan.KE_JIAN:
      sql = "and " + as + "kejianCount > 0";
      break;
    case LessonPlan.KE_HOU_FAN_SI:
      sql = "and " + as + "fansiCount > 0";
      break;
    }
    return sql;
  }

  private String buildCommitLessonTypeSql(Integer type, String alias) {
    String sql = "";
    String as = "";
    if (!StringUtils.isEmpty(alias)) {
      as = alias + ".";
    }
    switch (type) {
    case LessonPlan.JIAO_AN:
      sql = "and " + as + "jiaoanSubmitCount > 0";
      break;
    case LessonPlan.KE_JIAN:
      sql = "and " + as + "kejianSubmitCount > 0";
      break;
    case LessonPlan.KE_HOU_FAN_SI:
      sql = "and " + as + "fansiSubmitCount > 0";
      break;
    }
    return sql;
  }

  /**
   * 撰写课题数
   * 
   * @param type
   * @param userid
   * @param fasciculeId
   * @return
   * @see com.tmser.tr.check.cklp.service.CheckLessonService#countLessonInfo(java.lang.Integer,
   *      java.lang.Integer, java.lang.Integer)
   */
  @Override
  public Integer countLessonInfo(Integer type, Integer userId, Integer gradeId, Integer subjectId, Integer searchType,
      Integer termId, Integer fasciculeId) {
    LessonInfo model = new LessonInfo();
    model.setUserId(userId);
    model.setGradeId(gradeId);
    model.setSubjectId(subjectId);
    model.setSchoolYear((Integer) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SCHOOLYEAR));
    model.addCustomCondition(buildWriteLessonTypeSql(type, null), null);
    if (searchType == 0) { // 按册别
      model.setFasciculeId(fasciculeId);
    } else if (searchType == 1) { // 按撰写学期
      model.setTermId(termId);
    }
    int lessonId = 0;
    if (type.equals(ResTypeConstants.FANSI)) {
      LessonPlan plan = new LessonPlan();
      plan.setGradeId(gradeId);
      plan.setSubjectId(subjectId);
      plan.setUserId(userId);
      plan.setIsSubmit(null);
      if (searchType == 1) { // 按撰写学期
        plan.setTermId(termId);
      }
      plan.setPlanType(ResTypeConstants.FANSI_OTHER);
      plan.setSchoolYear((Integer) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SCHOOLYEAR));
      lessonId = lessonPlanDao.count(plan);
    }
    return lessonId + lessonInfoDao.count(model);
  }

  /**
   * 撰写篇数
   * 
   * @param type
   * @param userId
   * @param gradeId
   * @param subjectId
   * @param searchType
   * @param termId
   * @param fasciculeId
   * @return
   * @see com.tmser.tr.check.cklp.service.CheckLessonService#countLessonPlan(java.lang.Integer,
   *      java.lang.Integer, java.lang.Integer, java.lang.Integer,
   *      java.lang.Integer, java.lang.Integer, java.lang.Integer)
   */
  @Override
  public Integer countLessonPlan(Integer type, Integer userId, Integer gradeId, Integer subjectId, Integer searchType,
      Integer termId, Integer fasciculeId, Boolean isSubmit, Boolean isScan) {
    LessonPlan model = new LessonPlan();
    model.setUserId(userId);
    model.setGradeId(gradeId);
    model.setSubjectId(subjectId);
    model.setSchoolYear((Integer) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SCHOOLYEAR));
    model.setPlanType(type);
    if (isSubmit == true) {
      model.setIsSubmit(isSubmit);
    }
    if (isScan == true) {
      model.setIsScan(isScan);
    }
    if (searchType == 0) { // 按册别
      model.setFasciculeId(fasciculeId);
    } else if (searchType == 1) { // 按撰写学期
      model.setTermId(termId);
    }
    int otherFansiCount = 0;
    if (type.equals(ResTypeConstants.FANSI)) {
      LessonPlan plan = new LessonPlan();
      plan.setGradeId(gradeId);
      plan.setSubjectId(subjectId);
      plan.setUserId(userId);
      if (searchType == 1) { // 按撰写学期
        plan.setTermId(termId);
      }
      plan.setPlanType(ResTypeConstants.FANSI_OTHER);
      plan.setSchoolYear((Integer) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SCHOOLYEAR));
      otherFansiCount = lessonPlanDao.count(plan);
    }
    return otherFansiCount + lessonPlanDao.count(model);
  }

  @Override
  public Integer findFasciculeId(Integer type, Integer gradeId, Integer subjectId, Integer userId) {
    LessonInfo model = new LessonInfo();
    model.setUserId(userId);
    model.setGradeId(gradeId);
    model.setSubjectId(subjectId);
    model.setSchoolYear((Integer) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SCHOOLYEAR));
    model.addCustomCondition(buildWriteLessonTypeSql(type, null), null);
    LessonInfo li = lessonInfoDao.getOne(model);
    return li != null ? li.getFasciculeId() : null;
  }

  /**
   * @param lesInfoId
   * @param planId
   * @param m
   * @see com.tmser.tr.check.cklp.service.CheckLessonService#viewLesson(java.lang.Integer,
   *      java.lang.Integer, org.springframework.ui.Model)
   */
  @Override
  public void viewLesson(Integer type, Integer lesInfoId, Model m) {
    Integer schoolYear = (Integer) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SCHOOLYEAR);
    if (lesInfoId != null) {
      LessonInfo lessonInfo = lessonInfoDao.get(lesInfoId);
      LessonPlan model = new LessonPlan();
      model.setBookId(lessonInfo.getBookId());
      model.setLessonId(lessonInfo.getLessonId());
      model.setIsSubmit(true);
      model.setUserId(lessonInfo.getUserId());
      model.setSchoolYear(schoolYear);
      model.addOrder("planType,orderValue");
      List<LessonPlan> lplist = lessonPlanDao.listAll(model);
      m.addAttribute("lessonList", lplist);
      m.addAttribute("data", lessonInfo);
    }

  }

  /**
   * @param lesInfoId
   * @param planId
   * @param m
   * @see com.tmser.tr.check.cklp.service.CheckLessonService#viewLesson(java.lang.Integer,
   *      java.lang.Integer, org.springframework.ui.Model)
   */
  @Override
  public void viewCheckedLesson(Integer type, Integer lesInfoId, Model m) {
    Integer schoolYear = (Integer) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SCHOOLYEAR);
    if (lesInfoId != null) {
      LessonInfo lessonInfo = lessonInfoDao.get(lesInfoId);
      LessonPlan model = new LessonPlan();
      model.setInfoId(lesInfoId);
      model.setUserId(lessonInfo.getUserId());
      model.setSchoolYear(schoolYear);
      model.addOrder("planType,orderValue");
      model.setPlanType(type);
      List<LessonPlan> lplist = lessonPlanDao.listAll(model);
      m.addAttribute("lessonList", lplist);
      m.addAttribute("data", lessonInfo);
    }
  }

  @Override
  public Map<Integer, CheckInfo> checkedLessonMap(Integer authorId, Integer gradeId, Integer subjectId, Integer type) {
    User u = (User) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_USER);
    CheckInfo model = new CheckInfo();
    model.addCustomCulomn("id,resId,isUpdate");
    model.setAuthorId(authorId);
    model.setUserId(u.getId());
    model.setResType(type);
    model.setSubjectId(subjectId);
    model.setGradeId(gradeId);
    model.setSchoolYear((Integer) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SCHOOLYEAR));
    List<CheckInfo> cklist = checkInfoDao.listAll(model);
    Map<Integer, CheckInfo> checkIds = new HashMap<Integer, CheckInfo>();
    for (CheckInfo c : cklist) {
      checkIds.put(c.getResId(), c);
      if (c.getIsUpdate()) {
        model = new CheckInfo();
        model.setId(c.getId());
        model.setIsUpdate(false);
        checkInfoDao.update(model);
      }

    }
    return checkIds;
  }

  /**
   * @param userid
   * @param bookid
   * @return
   * @see com.tmser.tr.check.cklp.service.CheckLessonService#listTchLessonsOthers(java.lang.Integer,
   *      java.lang.String)
   */
  @Override
  public List<LessonPlan> listTchLessonsOther(Integer type, Integer userId, Integer gradeId, Integer subjectId,
      Integer searchType, Integer termId) {
    LessonPlan model = new LessonPlan();
    model.setPlanType(type);
    model.setIsSubmit(true);
    model.setUserId(userId);
    model.setGradeId(gradeId);
    model.setSubjectId(subjectId);
    model.setSchoolYear((Integer) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SCHOOLYEAR));
    if (searchType == 1) { // 按撰写学期
      model.setTermId(termId);
    }
    model.addCustomCondition(buildCommitLessonTypeSql(type, null), null);

    return lessonPlanDao.listAll(model);
  }

  /**
   * @param type
   * @param userid
   * @param fasciculeId
   * @return
   * @see com.tmser.tr.check.cklp.service.CheckLessonService#countLessonOther(java.lang.Integer,
   *      java.lang.Integer, java.lang.Integer)
   */
  @Override
  public Integer countLessonOther(Integer type, Integer userId, Integer gradeId, Integer subjectId,
      Integer fasciculeId) {
    LessonPlan model = new LessonPlan();
    model.setPlanType(type);
    model.setUserId(userId);
    model.setFasciculeId(fasciculeId);
    model.setGradeId(gradeId);
    model.setSubjectId(subjectId);
    model.setSchoolYear((Integer) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SCHOOLYEAR));
    model.addCustomCondition(buildWriteLessonTypeSql(type, null), null);
    return lessonPlanDao.count(model);
  }

  /**
   * @param planId
   * @param m
   * @see com.tmser.tr.check.cklp.service.CheckLessonService#viewOtherLesson(java.lang.Integer,
   *      java.lang.Integer, org.springframework.ui.Model)
   */
  @Override
  public void viewOtherLesson(Integer type, Integer planId, Model m) {
    Integer schoolYear = (Integer) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SCHOOLYEAR);
    if (planId != null) {
      LessonPlan lessonPlan = lessonPlanDao.get(planId);
      LessonPlan model = new LessonPlan();
      model.setBookId(lessonPlan.getBookId());
      model.setLessonId(lessonPlan.getLessonId());
      model.setIsSubmit(true);
      model.setUserId(lessonPlan.getUserId());
      model.setSchoolYear(schoolYear);
      model.addOrder("orderValue");
      List<LessonPlan> lplist = lessonPlanDao.listAll(model);
      m.addAttribute("lessonList", lplist);
      m.addAttribute("data", lessonPlan);
    }

  }

  /**
   * @param type
   * @param userid
   * @param gradeId
   * @param subjectId
   * @param fasciculeId
   * @return
   * @see com.tmser.tr.check.cklp.service.CheckLessonService#countCheckLesson(java.lang.Integer,
   *      java.lang.Integer, java.lang.Integer, java.lang.Integer,
   *      java.lang.Integer)
   */
  @Override
  public Integer countCheckLesson(Integer type, Integer userid, Integer gradeId, Integer subjectId, Integer searchType,
      Integer termId, Integer fasciculeId) {

    User u = (User) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_USER);
    CheckInfo cmodel = new CheckInfo();
    cmodel.addAlias("c");
    cmodel.addCustomCulomn("c.*");
    cmodel.setGradeId(gradeId);
    cmodel.setSubjectId(subjectId);
    cmodel.setUserId(u.getId());
    cmodel.setAuthorId(userid);
    cmodel.setSchoolYear((Integer) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SCHOOLYEAR));
    cmodel.setResType(type);
    Map<String, Object> paramMap = new HashMap<String, Object>();
    paramMap.put("fascicule", fasciculeId);
    paramMap.put("term", termId);
    if (searchType == 0) { // 按册别
      cmodel.addJoin(JOINTYPE.INNER, " LessonInfo a").on(" c.resId = a.id and a.fasciculeId = :fascicule");
    } else if (searchType == 1) { // 按撰写学期
      cmodel.addJoin(JOINTYPE.INNER, " LessonInfo a").on(" c.resId = a.id and a.termId = :term");
    }
    cmodel.addCustomCondition("", paramMap);
    Integer otherCount = 0;
    if (type.equals(ResTypeConstants.FANSI)) {
      CheckInfo other = new CheckInfo();
      other.addAlias("o");
      other.addCustomCulomn("o.*");
      other.setGradeId(gradeId);
      other.setSubjectId(subjectId);
      other.setResType(ResTypeConstants.FANSI_OTHER);
      other.setUserId(u.getId());
      other.setAuthorId(userid);
      other.setSchoolYear((Integer) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SCHOOLYEAR));
      if (searchType == 1) { // 按撰写学期
        other.addJoin(JOINTYPE.INNER, " LessonPlan a").on(" o.resId = a.planId and a.termId = :term");
      }
      other.addCustomCondition("", paramMap);
      otherCount = checkInfoDao.count(other);
    }
    return checkInfoDao.count(cmodel) + otherCount;
  }

  /**
   * @param type
   * @param userid
   * @param gradeId
   * @param subjectId
   * @param fasciculeId
   * @return
   * @see com.tmser.tr.check.cklp.service.CheckLessonService#countSubmitLesson(java.lang.Integer,
   *      java.lang.Integer, java.lang.Integer, java.lang.Integer,
   *      java.lang.Integer)
   */
  @Override
  public Integer countSubmitLesson(Integer type, Integer userId, Integer gradeId, Integer subjectId, Integer searchType,
      Integer termId, Integer fasciculeId) {
    LessonInfo model = new LessonInfo();
    model.setUserId(userId);
    model.setGradeId(gradeId);
    model.setSubjectId(subjectId);
    model.setSchoolYear((Integer) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SCHOOLYEAR));
    model.addCustomCondition(buildCommitLessonTypeSql(type, null), null);
    if (searchType == 0) { // 按册别
      model.setFasciculeId(fasciculeId);
    } else if (searchType == 1) { // 按撰写学期
      model.setTermId(termId);
    }
    int lessonId = 0;
    if (type.equals(ResTypeConstants.FANSI)) {
      LessonPlan plan = new LessonPlan();
      plan.setGradeId(gradeId);
      plan.setSubjectId(subjectId);
      plan.setUserId(userId);
      plan.setIsSubmit(true);
      if (searchType == 1) { // 按撰写学期
        plan.setTermId(termId);
      }
      plan.setPlanType(ResTypeConstants.FANSI_OTHER);
      plan.setSchoolYear((Integer) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SCHOOLYEAR));
      lessonId = lessonPlanDao.count(plan);
    }
    lessonId = lessonId + lessonInfoDao.count(model);
    return lessonId;
  }

  /**
   * (non-Javadoc)
   * 
   * @see com.tmser.tr.check.cklp.service.CheckLessonService#countCheckLessonPlan(java.lang.Integer,
   *      java.lang.Integer, java.lang.Integer, java.lang.Integer,
   *      java.lang.Integer, java.lang.Integer, java.lang.Integer)
   */
  @Override
  public Integer countCheckLessonPlan(Integer type, Integer userid, Integer gradeId, Integer subjectId,
      Integer searchType, Integer termId, Integer fasciculeId) {

    User u = (User) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_USER);
    LessonPlan model = new LessonPlan();
    model.addAlias("a");
    model.addCustomCulomn("a.*");
    if (searchType == 0) { // 按册别
      model.setFasciculeId(fasciculeId);
    } else if (searchType == 1) { // 按撰写学期
      model.setTermId(termId);
    }
    model.setPlanType(type);
    model.addJoin(JOINTYPE.INNER, " CheckInfo c").on(
        " c.resId = a.infoId and c.gradeId = :gradeId and c.subjectId = :subjectId and c.userId = :userId and c.authorId = :authorId and c.schoolYear = :schoolYear and c.resType = :resType");
    Map<String, Object> paramMap = new HashMap<String, Object>();
    paramMap.put("gradeId", gradeId);
    paramMap.put("subjectId", subjectId);
    paramMap.put("userId", u.getId());
    paramMap.put("authorId", userid);
    paramMap.put("schoolYear", (Integer) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SCHOOLYEAR));
    paramMap.put("resType", type);
    model.addCustomCondition("", paramMap);
    return lessonPlanDao.count(model);
  }
}