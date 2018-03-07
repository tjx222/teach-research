/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.webservice.service.impl;

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
import com.tmser.tr.activity.bo.ActivityTracks;
import com.tmser.tr.activity.bo.SchoolActivity;
import com.tmser.tr.activity.bo.SchoolActivityTracks;
import com.tmser.tr.activity.service.ActivityService;
import com.tmser.tr.activity.service.ActivityTracksService;
import com.tmser.tr.activity.service.SchoolActivityService;
import com.tmser.tr.activity.service.SchoolActivityTracksService;
import com.tmser.tr.comment.bo.Discuss;
import com.tmser.tr.comment.service.DiscussService;
import com.tmser.tr.common.ResTypeConstants;
import com.tmser.tr.common.utils.WebThreadLocalUtils;
import com.tmser.tr.lecturerecords.bo.LectureRecords;
import com.tmser.tr.lecturerecords.service.LectureRecordsService;
import com.tmser.tr.lessonplan.bo.LessonPlan;
import com.tmser.tr.manage.meta.Meta;
import com.tmser.tr.manage.meta.MetaUtils;
import com.tmser.tr.manage.meta.bo.Book;
import com.tmser.tr.manage.meta.service.BookService;
import com.tmser.tr.manage.org.bo.Organization;
import com.tmser.tr.manage.org.service.OrganizationService;
import com.tmser.tr.manage.resources.bo.Resources;
import com.tmser.tr.manage.resources.service.ResourcesService;
import com.tmser.tr.plainsummary.bo.PlainSummary;
import com.tmser.tr.plainsummary.service.PlainSummaryService;
import com.tmser.tr.recordbag.bo.Record;
import com.tmser.tr.recordbag.bo.Recordbag;
import com.tmser.tr.recordbag.service.RecordService;
import com.tmser.tr.recordbag.service.RecordbagService;
import com.tmser.tr.thesis.bo.Thesis;
import com.tmser.tr.thesis.service.ThesisService;
import com.tmser.tr.uc.SysRole;
import com.tmser.tr.uc.bo.User;
import com.tmser.tr.uc.bo.UserSpace;
import com.tmser.tr.uc.service.SchoolYearService;
import com.tmser.tr.uc.service.UserSpaceService;
import com.tmser.tr.uc.utils.SessionKey;
import com.tmser.tr.utils.DateUtils;
import com.tmser.tr.utils.StringUtils;
import com.tmser.tr.webservice.service.JxkhService;
import com.tmser.tr.webservice.vo.DataResult;
import com.tmser.tr.webservice.vo.JxParams;
import com.tmser.tr.writelessonplan.service.LessonPlanService;

/**
 * <pre>
 * 绩效考核系统数据采集 服务实现类
 * </pre>
 * 
 * @author zpp
 * @version $Id: AAA.java, v 1.0 2016年3月1日 下午3:28:18 zpp Exp $
 */
@Service
@Transactional
public class JxkhServiceImpl implements JxkhService {

  private static final Integer XUE_QI = 2;// 学期考核
  private static final Integer UP_XUE_QI = 1;// 上学期
  private static final Integer BOOK = 178;// 上学期
  private static final Integer SHARE = 1;// 分享

  @Autowired
  private LessonPlanService lessonPlanService;
  @Autowired
  private LectureRecordsService lectureRecordsService;
  @Autowired
  private PlainSummaryService plainSummaryService;
  @Autowired
  private RecordService recordService;// 精选档案service
  @Autowired
  private ActivityService activityService;
  @Autowired
  private RecordbagService recordbagService;
  @Autowired
  private UserSpaceService userSpaceService;
  @Autowired
  private SchoolActivityService schoolActivityService;
  @Autowired
  private ResourcesService resourceService;
  @Resource
  private ThesisService thesisService;
  @Resource
  private SchoolYearService schoolYearService;
  @Resource
  private OrganizationService organizationService;
  @Resource
  private ActivityTracksService activityTracksService;
  @Resource
  private DiscussService discussService;
  @Resource
  private SchoolActivityTracksService schoolActivityTracksService;
  @Resource
  private BookService bookService;

  private List<UserSpace> getCurrentUserSpaceList(JxParams jp) {
    UserSpace userSpace = new UserSpace();
    userSpace.setUserId(jp.getUserId());
    userSpace.setEnable(UserSpace.ENABLE);
    userSpace.setPhaseId(jp.getPhaseId());
    userSpace.setOrgId(jp.getOrgId());
    userSpace.addCustomCulomn("id,userId,gradeId,subjectId,username,orgId,sysRoleId,spaceName,bookId,roleId");
    userSpace.setSchoolYear((Integer) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SCHOOLYEAR));
    return userSpaceService.findAll(userSpace);
  }

  private Map<String, String> getGradeMapByOrgId(JxParams jp) {
    Organization org = organizationService.findOne(jp.getOrgId());
    Map<String, String> findGradesByPhaseId = null;
    if (org != null) {
      List<Meta> listAllGrade = MetaUtils.getOrgTypeMetaProvider().listAllGrade(org.getSchoolings(), jp.getPhaseId());
      if (!CollectionUtils.isEmpty(listAllGrade)) {
        findGradesByPhaseId = new HashMap<String, String>();
        for (Meta meta : listAllGrade) {
          findGradesByPhaseId.put(meta.getId().toString(), meta.getName());
        }
      }
    } else {
      List<Meta> listAllGrade = MetaUtils.getPhaseGradeMetaProvider().listAllGradeByPhaseId(jp.getPhaseId());
      if (!CollectionUtils.isEmpty(listAllGrade)) {
        findGradesByPhaseId = new HashMap<String, String>();
        for (Meta meta : listAllGrade) {
          findGradesByPhaseId.put(meta.getId().toString(), meta.getName());
        }
      }
    }
    return findGradesByPhaseId;
  }

  /**
   * 若教师有多重身份，针对于“教案、课件、反思”模块收集的数据为各身份各模块的数据之和
   * 
   * @param jp
   * @param jiaoan
   * @return
   * @see com.tmser.tr.webservice.service.JxkhService#getBeiKeRes(com.tmser.tr.webservice.vo.JxParams,
   *      int)
   */
  @Override
  public DataResult getBeiKeRes(JxParams jp, int type, LessonPlan lp) {
    List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
    String typeName = "";
    DataResult dr = new DataResult();
    UserSpace userSpace = new UserSpace();
    userSpace.setUserId(jp.getUserId());
    userSpace.setEnable(UserSpace.ENABLE);
    userSpace.setSysRoleId(SysRole.TEACHER.getId());
    userSpace.setPhaseId(jp.getPhaseId());
    userSpace.addCustomCulomn("id,userId,gradeId,subjectId,username,sysRoleId,orgId,spaceName,bookId");
    userSpace.setSchoolYear((Integer) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SCHOOLYEAR));
    List<UserSpace> allUserSpace = userSpaceService.findAll(userSpace);
    lp.setUserId(jp.getUserId());
    lp.setPhaseId(jp.getPhaseId());
    if (ResTypeConstants.JIAOAN == type) {
      typeName = "教案";
      lp.setPlanType(type);
      getJiaoAnDatas(allUserSpace, dr, lp, jp, typeName);
      return dr;
    } else if (ResTypeConstants.KEJIAN == type) {
      typeName = "课件";
      lp.setPlanType(type);
      getJiaoAnDatas(allUserSpace, dr, lp, jp, typeName);
      return dr;
    } else if (ResTypeConstants.FANSI == type) {
      Map<String, String> findGradesByPhaseId = this.getGradeMapByOrgId(jp);
      Map<String, String> findSubjectsByPhaseId = findSubject(jp.getPhaseId());
      LessonPlan lesson = new LessonPlan();
      lesson.setIsShare(lp.getIsShare());
      lesson.setUserId(jp.getUserId());
      lesson.setPhaseId(jp.getPhaseId());
      lesson.setPlanType(ResTypeConstants.FANSI);
      for (UserSpace userSpace2 : allUserSpace) {
        String sql = "";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        String grade = findGradesByPhaseId.get(userSpace2.getGradeId().toString());
        String subject = findSubjectsByPhaseId.get(userSpace2.getSubjectId().toString());
        if (StringUtils.isNotEmpty(userSpace2.getBookId())) {
          lesson.setSchoolYear(schoolYearService.getCurrentSchoolYear());
          lesson.addCustomCulomn("planId,planName,resId,subjectId,gradeId,userId,hoursId");
          Integer isTerm = jp.getIsTerm();
          lesson.setEnable(1);
          lesson.addOrder("crtDttm asc");
          lesson.setSubjectId(userSpace2.getSubjectId());
          lesson.setGradeId(userSpace2.getGradeId());
          if (lesson.getIsShare() != null && lesson.getIsShare()) {
            getShare(lesson, paramMap, jp);
          } else {
            if (XUE_QI.equals(isTerm)) {// 如果是学期考核
              String bookId = userSpace2.getBookId();
              Book book = bookService.findOne(bookId);
              if (book != null) {
                Integer fasciculeId = book.getFasciculeId();// 册别
                Integer term = jp.getTerm();
                if (Integer.valueOf(BOOK).equals(fasciculeId)) {// 如果是全一册的情况下按撰写时间查找
                  if (UP_XUE_QI.equals(term)) {// 上学期
                    paramMap.put("startTime", schoolYearService.getNextTermStartTime());
                    lesson.addCustomCondition(sql + "and crtDttm < :startTime", paramMap);
                  } else {// 下学期
                    paramMap.put("endTime", schoolYearService.getNextTermStartTime());
                    lesson.addCustomCondition(sql + "and crtDttm >= :endTime ", paramMap);
                  }
                } else {
                  lesson.setBookId(userSpace2.getBookId());
                }
              } else {
                lp.setBookId("-1");
              }
            }
          }
          List<LessonPlan> lessonAll = lessonPlanService.findAll(lesson);
          for (LessonPlan lpt : lessonAll) {
            String resId = lpt.getResId();
            Map<String, Object> datamap = new HashMap<String, Object>();
            datamap.put("subjectGradeName", grade + subject);
            datamap.put("resourceName", lpt.getPlanName());
            datamap.put("resourceId", resId);
            datamap.put("subjectId", userSpace2.getSubjectId());
            datamap.put("gradeId", userSpace2.getGradeId());
            datamap.put("userId", lpt.getUserId());
            datamap.put("resourceTypeName", typeName);
            datamap.put("spaceType", userSpace2.getSysRoleId());
            Resources findOne = resourceService.findOne(resId);
            if (findOne != null) {
              datamap.put("resourceExt", findOne.getExt());
            }

            result.add(datamap);
          }
        }
      }
      typeName = "反思";
      for (UserSpace userSpace2 : allUserSpace) {
        String sql = "";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        String grade = findGradesByPhaseId.get(userSpace2.getGradeId().toString());
        String subject = findSubjectsByPhaseId.get(userSpace2.getSubjectId().toString());
        LessonPlan lesson1 = new LessonPlan();
        lesson1.setGradeId(userSpace2.getGradeId());
        lesson1.setSubjectId(userSpace2.getSubjectId());
        lesson1.setUserId(jp.getUserId());
        lesson1.setPhaseId(jp.getPhaseId());
        lesson1.setIsShare(lp.getIsShare());
        lesson1.setPlanType(ResTypeConstants.FANSI_OTHER);
        Integer isTerm = jp.getIsTerm();
        lesson1.setSchoolYear(schoolYearService.getCurrentSchoolYear());
        if (lesson1.getIsShare() != null && lesson1.getIsShare()) {
          getShare(lesson1, paramMap, jp);
        } else {
          if (XUE_QI.equals(isTerm)) {// 如果是学期考核
            Integer term = jp.getTerm();
            if (UP_XUE_QI.equals(term)) {// 上学期
              paramMap.put("startTime", schoolYearService.getNextTermStartTime());
              lesson1.addCustomCondition(sql + "and crtDttm < :startTime", paramMap);
            } else {// 下学期
              paramMap.put("endTime", schoolYearService.getNextTermStartTime());
              lesson1.addCustomCondition(sql + "and crtDttm >= :endTime ", paramMap);
            }
          } else {
            lesson1.addCustomCondition(sql, paramMap);
          }
        }
        lesson1.addCustomCulomn("planName,resId,subjectId,gradeId,userId,hoursId");
        lesson1.setEnable(1);
        lesson1.addOrder("crtDttm asc");
        List<LessonPlan> lessonAll = lessonPlanService.findAll(lesson1);
        for (LessonPlan lpt : lessonAll) {
          String resId = lpt.getResId();
          Map<String, Object> datamap = new HashMap<String, Object>();
          datamap.put("subjectGradeName", grade + subject);
          datamap.put("resourceName", lpt.getPlanName());
          datamap.put("resourceId", resId);
          datamap.put("subjectId", userSpace2.getSubjectId());
          datamap.put("gradeId", userSpace2.getGradeId());
          datamap.put("userId", lpt.getUserId());
          datamap.put("resourceTypeName", typeName);
          datamap.put("spaceType", userSpace2.getSysRoleId());
          Resources findOne = resourceService.findOne(resId);
          if (findOne != null) {
            datamap.put("resourceExt", findOne.getExt());
          }

          result.add(datamap);
        }
      }

      dr.setDetail(result);
      dr.setData(result.size());
    }
    return dr;
  }

  private Map<String, String> findSubject(Integer phaseId) {
    User user = (User) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_USER);
    Organization org = organizationService.findOne(user.getOrgId());
    Integer[] areas = StringUtils.toIntegerArray(org.getAreaIds().substring(1, org.getAreaIds().lastIndexOf(",")), ",");
    List<Meta> listAllSubject = MetaUtils.getPhaseSubjectMetaProvider().listAllSubject(user.getOrgId(), phaseId, areas);
    Map<String, String> gradesMapo = null;
    if (!CollectionUtils.isEmpty(listAllSubject)) {
      gradesMapo = new HashMap<String, String>();
      for (Meta meta : listAllSubject) {
        gradesMapo.put(meta.getId().toString(), meta.getName());
      }
    }
    return gradesMapo;
  }

  private void getShare(LessonPlan lp, Map<String, Object> paramMap, JxParams jp) {
    if (XUE_QI.equals(jp.getIsTerm())) {
      Integer term = jp.getTerm();
      if (UP_XUE_QI.equals(term)) {// 上学期
        lp.setSchoolYear(null);
        paramMap.put("startTime", schoolYearService.getUpTermStartTime());
        paramMap.put("endTime", schoolYearService.getNextTermStartTime());
        lp.addCustomCondition("and shareTime < :endTime and shareTime >= :startTime", paramMap);
      } else {
        lp.setSchoolYear(null);
        paramMap.put("startTime", schoolYearService.getNextTermStartTime());
        paramMap.put("endTime", schoolYearService.getNextYearUpTermStartTime());
        lp.addCustomCondition("and shareTime >= :startTime and shareTime < :endTime", paramMap);
      }
    } else {
      lp.setSchoolYear(null);
      lp.addCustomCondition(" and shareTime >= :startTime and shareTime < :endTime ", paramMap);
      paramMap.put("startTime", schoolYearService.getUpTermStartTime());
      paramMap.put("endTime", schoolYearService.getNextYearUpTermStartTime());
    }
  }

  private void getJiaoAnDatas(List<UserSpace> allUserSpace, DataResult dr, LessonPlan lp, JxParams jp, String typeName) {
    List<Integer> resourceId = new ArrayList<Integer>();
    List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
    Map<String, String> findGradesByPhaseId = this.getGradeMapByOrgId(jp);
    Map<String, String> findSubjectsByPhaseId = findSubject(jp.getPhaseId());
    for (UserSpace userSpace2 : allUserSpace) {
      String sql = "";
      Map<String, Object> subjectGradeMap = new HashMap<String, Object>();
      Map<String, Object> paramMap = new HashMap<String, Object>();
      lp.setSchoolYear(schoolYearService.getCurrentSchoolYear());
      lp.addCustomCulomn("planId,planName,resId,subjectId,gradeId,userId,hoursId");
      Integer isTerm = jp.getIsTerm();
      lp.setSubjectId(userSpace2.getSubjectId());
      lp.setGradeId(userSpace2.getGradeId());
      lp.addOrder("crtDttm asc");
      if (lp.getIsShare() != null && lp.getIsShare()) {
        getShare(lp, paramMap, jp);
      } else {
        if (XUE_QI.equals(isTerm)) {// 如果是学期考核
          String bookId = userSpace2.getBookId();
          Book book = bookService.findOne(bookId);
          if (book != null) {
            Integer fasciculeId = book.getFasciculeId();
            Integer term = jp.getTerm();
            if (Integer.valueOf(BOOK).equals(fasciculeId)) {// 如果是全一册的情况下按撰写时间查找
              if (UP_XUE_QI.equals(term)) {// 上学期
                paramMap.put("startTime", schoolYearService.getNextTermStartTime());
                lp.addCustomCondition(sql + "and crtDttm < :startTime", paramMap);
              } else {// 下学期
                paramMap.put("endTime", schoolYearService.getNextTermStartTime());
                lp.addCustomCondition(sql + "and crtDttm >= :endTime ", paramMap);
              }
            } else {
              lp.setBookId(userSpace2.getBookId());
            }
          } else {
            lp.setBookId("-1");
          }
        } else {
          lp.addCustomCondition(sql, paramMap);
        }
      }
      lp.setEnable(1);// 有效
      lp.addOrder("crtDttm asc");
      List<LessonPlan> lplist = lessonPlanService.findAll(lp);
      List<Map<String, Object>> jiaoAnList = new ArrayList<Map<String, Object>>();
      if (!CollectionUtils.isEmpty(lplist)) {
        for (LessonPlan lesson : lplist) {
          if (!resourceId.contains(lesson.getPlanId())) {
            resourceId.add(lesson.getPlanId());
            Map<String, Object> datamap = new HashMap<String, Object>();
            String resId = lesson.getResId();
            datamap.put("resourceId", resId);
            datamap.put("userId", lesson.getUserId());
            Resources findOne = resourceService.findOne(resId);
            if (findOne != null) {
              datamap.put("resourceName", lesson.getPlanName() + "." + findOne.getExt());
            }
            jiaoAnList.add(datamap);
          }
        }
        String grade = findGradesByPhaseId.get(userSpace2.getGradeId().toString());
        String subject = findSubjectsByPhaseId.get(userSpace2.getSubjectId().toString());
        if (grade != null && subject != null) {
          subjectGradeMap.put("resource", jiaoAnList);
          subjectGradeMap.put("subjectGradeName", grade + subject);
          subjectGradeMap.put("subjectId", userSpace2.getSubjectId());
          subjectGradeMap.put("gradeId", userSpace2.getGradeId());
          subjectGradeMap.put("resourceSize", jiaoAnList.size());
          subjectGradeMap.put("resourceTypeName", typeName);
          subjectGradeMap.put("spaceType", userSpace2.getSysRoleId());
          result.add(subjectGradeMap);
        } else {
          subjectGradeMap.put("resource", jiaoAnList);
          subjectGradeMap.put("subjectGradeName", userSpace2.getSpaceName());
          subjectGradeMap.put("resourceSize", jiaoAnList.size());
          subjectGradeMap.put("subjectId", userSpace2.getSubjectId());
          subjectGradeMap.put("gradeId", userSpace2.getGradeId());
          subjectGradeMap.put("resourceTypeName", typeName);
          subjectGradeMap.put("spaceType", userSpace2.getSysRoleId());
          result.add(subjectGradeMap);
        }
      }
    }
    dr.setData(resourceId.size());
    dr.setDetail(result);
  }

  /**
   * 听课记录
   */
  @Override
  public DataResult getLectureRes(JxParams jp, Integer type, LectureRecords lp) {
    DataResult dr = new DataResult();
    lp.setLecturepeopleId(jp.getUserId());
    lp.setPhaseId(jp.getPhaseId());
    Map<String, Object> paramMap = new HashMap<String, Object>();
    String typeName = "";
    String sql = "";
    if (ResTypeConstants.LECTURE == type) {
      typeName = "听课记录";
      lp.setResType(type);
    }
    Integer isTerm = jp.getIsTerm();
    lp.setSchoolYear(schoolYearService.getCurrentSchoolYear());
    if (XUE_QI.equals(isTerm)) {// 如果是学期考核
      Integer term = jp.getTerm();
      if (UP_XUE_QI.equals(term)) {// 上学期
        if (SHARE.equals(lp.getIsShare())) {
          lp.setSchoolYear(null);
          paramMap.put("startTime", schoolYearService.getUpTermStartTime());
          paramMap.put("endTime", schoolYearService.getNextTermStartTime());
          lp.addCustomCondition(sql + "and shareTime < :endTime and shareTime >= :startTime", paramMap);
        } else {
          paramMap.put("startTime", schoolYearService.getNextTermStartTime());
          lp.addCustomCondition(sql + "and crtDttm < :startTime", paramMap);
        }
      } else {// 下学期
        if (SHARE.equals(lp.getIsShare())) {
          lp.setSchoolYear(null);
          paramMap.put("startTime", schoolYearService.getNextTermStartTime());
          paramMap.put("endTime", schoolYearService.getNextYearUpTermStartTime());
          lp.addCustomCondition(sql + "and shareTime >= :startTime and shareTime < :endTime", paramMap);
        } else {
          paramMap.put("endTime", schoolYearService.getNextTermStartTime());
          lp.addCustomCondition(sql + "and crtDttm >= :endTime ", paramMap);
        }
      }
    } else {
      if (SHARE.equals(lp.getIsShare())) {
        lp.setSchoolYear(null);
        lp.addCustomCondition(" and shareTime >= :startTime and shareTime < :endTime ", paramMap);
        paramMap.put("startTime", schoolYearService.getUpTermStartTime());
        paramMap.put("endTime", schoolYearService.getNextYearUpTermStartTime());
      }
    }
    lp.addCustomCulomn("id,type,topicId,topic,grade,subjectId,teachingpeopleId,lecturepeopleId,numberLectures");
    lp.addOrder("crtDttm asc");// 按照发布时间降序
    lp.setIsEpub(1);
    lp.setIsDelete(false);// 不删除
    List<LectureRecords> lectures = lectureRecordsService.findAll(lp);
    List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
    Integer count = 0;
    if (!CollectionUtils.isEmpty(lectures)) {
      for (LectureRecords lpt : lectures) {
        Map<String, Object> datamap = new HashMap<String, Object>();
        datamap.put("resourceName", lpt.getTopic());
        datamap.put("resourceType", lpt.getType());
        datamap.put("resourceId", lpt.getId());
        datamap.put("subjectId", lp.getSubjectId());
        datamap.put("gradeId", lp.getGradeId());
        datamap.put("userId", lpt.getLecturepeopleId());
        datamap.put("resourceTypeName", typeName);
        datamap.put("resourceCount", lpt.getNumberLectures());
        count += lpt.getNumberLectures() == null ? 0 : lpt.getNumberLectures();
        result.add(datamap);
      }
    }
    dr.setData(count);
    dr.setDetail(result);
    return dr;
  }

  @Override
  public DataResult getPlainSummarys(JxParams jp, Integer type, PlainSummary model) {
    DataResult dr = new DataResult();
    List<UserSpace> allUserSpace = getCurrentUserSpaceList(jp);
    List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
    Map<String, Object> paramMap = new HashMap<String, Object>();
    String typeName = "";
    if (Integer.valueOf(ResTypeConstants.TPPLAIN_SUMMARY_PLIAN).equals(type)) {
      typeName = "个人计划";
      model.setCategory(1);
    } else if (Integer.valueOf(ResTypeConstants.TPPLAIN_SUMMARY_SUMMARY).equals(type)) {
      typeName = "个人总结";
      model.setCategory(2);
    }
    for (UserSpace userSpace2 : allUserSpace) {
      model.setUserRoleId(userSpace2.getRoleId());
      model.setUserId(jp.getUserId());
      model.setPhaseId(jp.getPhaseId());
      model.setSubjectId(userSpace2.getSubjectId());
      model.setGradeId(userSpace2.getGradeId());
      Integer isTerm = jp.getIsTerm();
      model.setSchoolYear(schoolYearService.getCurrentSchoolYear());
      if (XUE_QI.equals(isTerm)) {// 如果是学期考核
        Integer term = jp.getTerm();
        if (UP_XUE_QI.equals(term)) {// 上学期
          if (SHARE.equals(model.getIsShare())) {
            model.setSchoolYear(null);
            paramMap.put("startTime", schoolYearService.getUpTermStartTime());
            paramMap.put("endTime", schoolYearService.getNextTermStartTime());
            model.addCustomCondition(" and shareTime < :endTime and shareTime >= :startTime ", paramMap);
          } else {
            model.setTerm(0);
          }
        } else {// 下学期
          if (SHARE.equals(model.getIsShare())) {
            model.setSchoolYear(null);
            paramMap.put("startTime", schoolYearService.getNextTermStartTime());
            paramMap.put("endTime", schoolYearService.getNextYearUpTermStartTime());
            model.addCustomCondition(" and shareTime >= :startTime and shareTime < :endTime ", paramMap);
          } else {
            model.setTerm(1);
          }
        }
      } else {
        if (SHARE.equals(model.getIsShare())) {
          model.setSchoolYear(null);
          model.addCustomCondition(" and shareTime >= :startTime and shareTime < :endTime ", paramMap);
          paramMap.put("startTime", schoolYearService.getUpTermStartTime());
          paramMap.put("endTime", schoolYearService.getNextYearUpTermStartTime());
        }
      }
      model.addCustomCulomn("userId,subjectId,title,gradeId,contentFileKey,contentFileName,contentFileType");
      model.addOrder("crtDttm asc");
      List<PlainSummary> findAll = plainSummaryService.findAll(model);
      for (PlainSummary plan : findAll) {
        Map<String, Object> datamap = new HashMap<String, Object>();
        datamap.put("resourceName", plan.getTitle());
        datamap.put("resourceId", plan.getContentFileKey());
        datamap.put("subjectId", plan.getSubjectId());
        datamap.put("gradeId", plan.getGradeId());
        datamap.put("userId", plan.getUserId());
        datamap.put("resourceTypeName", typeName);
        datamap.put("resourceNameType", plan.getContentFileType());
        datamap.put("spaceType", userSpace2.getSysRoleId());
        result.add(datamap);
      }
    }

    dr.setData(result.size());
    dr.setDetail(result);
    return dr;
  }

  /**
   * 精选成长档案袋
   * 
   * @param jp
   * @param type
   * @return
   */
  @Override
  public DataResult getRecords(JxParams jp, Recordbag bag) {

    DataResult dr = new DataResult();
    List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();

    List<UserSpace> allUserSpace = getCurrentUserSpaceList(jp);
    Integer size = 0;
    for (UserSpace userSpace2 : allUserSpace) {
      Map<String, Object> paramMap = new HashMap<String, Object>();
      String sql = "";
      bag.setTeacherId(jp.getUserId().toString());
      bag.setSubjectId(userSpace2.getSubjectId());
      bag.setGradeId(userSpace2.getGradeId());
      List<Recordbag> find = recordbagService.find(bag, Integer.MAX_VALUE);
      List<Map<String, Object>> recordLists = new ArrayList<Map<String, Object>>();
      Map<String, Object> dataMap1 = new HashMap<String, Object>();
      String gradeName = "";
      String subjectName = "";
      for (Recordbag recordbag : find) {
        Integer id = recordbag.getId();
        Record record = new Record();
        Integer isTerm = jp.getIsTerm();
        record.setSchoolYear(schoolYearService.getCurrentSchoolYear());
        if (XUE_QI.equals(isTerm)) {// 如果是学期考核
          Integer term = jp.getTerm();
          if (UP_XUE_QI.equals(term)) {// 上学期
            paramMap.put("startTime", schoolYearService.getNextTermStartTime());
            record.addCustomCondition(sql + "and createTime < :startTime", paramMap);
          } else {// 下学期
            paramMap.put("endTime", schoolYearService.getNextTermStartTime());
            record.addCustomCondition(sql + "and createTime >= :endTime ", paramMap);
          }
        }
        record.setBagId(id);
        record.addOrder("createTime asc");
        List<Record> recordList = recordService.findAll(record);
        int flag = 0;

        if (!CollectionUtils.isEmpty(recordList)) {
          for (Record record2 : recordList) {
            String time = DateUtils.formatDate(record2.getCreateTime(), "yyyy-MM-dd");
            record.setTime(time);
            Integer recourseTypeId = null;
            if (0 == Recordbag.switchResType(recordbag.getName()) || 1 == Recordbag.switchResType(recordbag.getName())
                || 2 == Recordbag.switchResType(recordbag.getName())) {
              recordbagService.saveLessonPlan(record2);
              recourseTypeId = Recordbag.switchResType(recordbag.getName());
            } else if (3 == Recordbag.switchResType(recordbag.getName())) {
              flag = recordbagService.saveActivity(record2, flag, id);
              recourseTypeId = 3;
            } else if (4 == Recordbag.switchResType(recordbag.getName())) {
              recordbagService.saveThesis(record2, id);
              recourseTypeId = Recordbag.switchResType(recordbag.getName());
            } else if (5 == Recordbag.switchResType(recordbag.getName())) {
              recordbagService.savePlainSummary(record2, id);
              recourseTypeId = Recordbag.switchResType(recordbag.getName());
            } else if (6 == Recordbag.switchResType(recordbag.getName()) && Recordbag.TKJL.equals(recordbag.getName())) {
              flag = recordbagService.saveLectureRecords(record2, flag, id);
              recourseTypeId = 6;
            } else {
              flag = 9;// 自建档案
              recourseTypeId = 9;
            }
            Map<String, Object> dataMap = new HashMap<String, Object>();
            if (recourseTypeId == 3 || recourseTypeId == 6) {
              dataMap.put("resourceName", record2.getFlago() + record2.getRecordName());
            } else if (recourseTypeId == 9) {
              dataMap.put("resourceName", record2.getRecordName());
            } else {
              dataMap.put("resourceName", record2.getFlago() + record2.getDesc() + record2.getRecordName() + "."
                  + record2.getFlags());
            }
            dataMap.put("resourceId", record2.getResId());
            dataMap.put("resourcePath", record2.getPath());
            dataMap.put("recourseType", recourseTypeId);
            dataMap.put("resourceTypeName", recordbag.getName());
            recordLists.add(dataMap);
          }
          gradeName = recordbag.getGrade();
          subjectName = recordbag.getSubject();

        }
      }
      dataMap1.put("gradeName", gradeName);
      dataMap1.put("subjectName", subjectName);
      dataMap1.put("resource", recordLists);
      dataMap1.put("gradeId", userSpace2.getGradeId());
      dataMap1.put("subjectId", userSpace2.getSubjectId());
      dataMap1.put("spaceType", userSpace2.getSysRoleId());
      size += recordLists.size();
      result.add(dataMap1);
    }

    dr.setDetail(result);
    dr.setData(size);
    return dr;
  }

  /**
   * 集体备课
   * 
   * @param jp
   * @param type
   * @return
   */
  @Override
  public DataResult getActivityData(JxParams jp, Integer type) {
    DataResult dr = new DataResult();
    List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
    List<Integer> resourceId = new ArrayList<Integer>();
    List<UserSpace> allUserSpace = getCurrentUserSpaceList(jp);
    Map<String, String> findGradesByPhaseId = this.getGradeMapByOrgId(jp);
    Map<String, String> findSubjectsByPhaseId = findSubject(jp.getPhaseId());
    for (UserSpace userSpace2 : allUserSpace) {
      String sql = " and editType <> :editType";
      List<Integer> resourceSpaceId = new ArrayList<Integer>();
      Map<String, Object> subjectGradeMap = new HashMap<String, Object>();
      Map<String, Object> paramMap = new HashMap<String, Object>();
      paramMap.put("editType", ActivityTracks.ZHUBEI);
      ActivityTracks tracks = new ActivityTracks();
      tracks.setUserId(userSpace2.getUserId());
      tracks.setSubjectId(userSpace2.getSubjectId());
      tracks.setGradeId(userSpace2.getGradeId());
      Integer isTerm = jp.getIsTerm();
      tracks.setSchoolYear(schoolYearService.getCurrentSchoolYear());
      tracks.setSpaceId(userSpace2.getId());
      if (XUE_QI.equals(isTerm)) {// 如果是学期考核
        Integer term = jp.getTerm();
        if (UP_XUE_QI.equals(term)) {// 上学期
          paramMap.put("startTime", schoolYearService.getNextTermStartTime());
          tracks.addCustomCondition(sql + " and crtDttm < :startTime", paramMap);
        } else {// 下学期
          paramMap.put("endTime", schoolYearService.getNextTermStartTime());
          tracks.addCustomCondition(sql + " and crtDttm >= :endTime ", paramMap);
        }
      } else {
        tracks.addCustomCondition(sql, paramMap);
      }
      tracks.addOrder(" crtDttm asc");
      List<ActivityTracks> tracksList = activityTracksService.findAll(tracks);
      for (ActivityTracks activityTracks : tracksList) {
        if (activityTracks.getActivityId() != null && !resourceSpaceId.contains(activityTracks.getActivityId())) {
          resourceSpaceId.add(activityTracks.getActivityId());
        }
        if (activityTracks.getActivityId() != null && !resourceId.contains(activityTracks.getActivityId())) {
          resourceId.add(activityTracks.getActivityId());
        }
      }
      Map<String, Object> paramMap1 = new HashMap<String, Object>();
      Discuss discuss = new Discuss();
      discuss.setCrtId(userSpace2.getUserId());
      discuss.setSpaceId(userSpace2.getId());
      discuss.setTypeId(ResTypeConstants.ACTIVITY);
      if (XUE_QI.equals(isTerm)) {// 如果是学期考核
        Integer term = jp.getTerm();
        if (UP_XUE_QI.equals(term)) {// 上学期
          paramMap1.put("endTime", schoolYearService.getNextTermStartTime());
          paramMap1.put("startTime", schoolYearService.getUpTermStartTime());
          discuss.addCustomCondition(" and createTime < :endTime and createTime >= :startTime", paramMap1);
        } else {// 下学期
          paramMap1.put("startTime", schoolYearService.getNextTermStartTime());
          paramMap1.put("endTime", schoolYearService.getNextYearUpTermStartTime());
          discuss.addCustomCondition(" and createTime >= :startTime and createTime < :endTime", paramMap1);
        }
      } else {
        paramMap1.put("startTime", schoolYearService.getUpTermStartTime());
        paramMap1.put("endTime", schoolYearService.getNextYearUpTermStartTime());
        discuss.addCustomCondition(" and createTime >= :startTime and createTime < :endTime", paramMap1);
      }
      discuss.addOrder(" createTime asc");
      List<Discuss> findAllDiscuss = discussService.findAll(discuss);
      for (Discuss discusses : findAllDiscuss) {
        if (discusses.getActivityId() != null && !resourceSpaceId.contains(discusses.getActivityId())) {
          resourceSpaceId.add(discusses.getActivityId());
        }
        if (discusses.getActivityId() != null && !resourceId.contains(discusses.getActivityId())) {
          resourceId.add(discusses.getActivityId());
        }
      }
      Activity activity = new Activity();
      List<Activity> findAllActivity = new ArrayList<Activity>();
      if (!CollectionUtils.isEmpty(resourceSpaceId)) {
        activity.buildCondition(" and id in (:activityId)").put("activityId", resourceSpaceId);
        findAllActivity = activityService.findAll(activity);
      }
      List<Map<String, Object>> activityList = new ArrayList<Map<String, Object>>();
      for (Activity activity2 : findAllActivity) {
        Map<String, Object> datamap = new HashMap<String, Object>();
        datamap.put("resourceTypeName", activity2.getTypeName());
        datamap.put("resourceName", activity2.getActivityName());
        datamap.put("resourceId", activity2.getId());
        datamap.put("resourceType", activity2.getTypeId());
        activityList.add(datamap);
      }
      String grade = findGradesByPhaseId.get(userSpace2.getGradeId().toString());
      String subject = findSubjectsByPhaseId.get(userSpace2.getSubjectId().toString());
      if (grade != null && subject != null) {
        subjectGradeMap.put("resource", activityList);
        subjectGradeMap.put("subjectGradeName", grade + subject);
        subjectGradeMap.put("resourceSize", activityList.size());
        subjectGradeMap.put("subjectId", userSpace2.getSubjectId());
        subjectGradeMap.put("gradeId", userSpace2.getGradeId());
        subjectGradeMap.put("spaceType", userSpace2.getSysRoleId());
        resultList.add(subjectGradeMap);
      } else {
        subjectGradeMap.put("resource", activityList);
        subjectGradeMap.put("subjectGradeName", userSpace2.getSpaceName());
        subjectGradeMap.put("resourceSize", activityList.size());
        subjectGradeMap.put("subjectId", userSpace2.getSubjectId());
        subjectGradeMap.put("gradeId", userSpace2.getGradeId());
        subjectGradeMap.put("spaceType", userSpace2.getSysRoleId());
        resultList.add(subjectGradeMap);
      }
    }
    dr.setDetail(resultList);
    dr.setData(resourceId.size());
    return dr;
  }

  /**
   * 针对于“集体担任主备人”收集的数据为该用户各身份参与的集体备课中担任主备人的数量之和
   * 
   * @param jp
   * @param type
   * @return
   */
  @Override
  public DataResult getActivityMainUser(JxParams jp, Integer type) {
    DataResult dr = new DataResult();
    List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
    List<Map<String, Object>> activitysResult = new ArrayList<Map<String, Object>>();
    List<Map<String, Object>> SchResult = new ArrayList<Map<String, Object>>();
    Map<String, Object> activityMap = new HashMap<String, Object>();
    List<UserSpace> currentUserSpaceList = getCurrentUserSpaceList(jp);
    List<Activity> activitys = this.getActivityMainUsers(jp, currentUserSpaceList, activitysResult);
    List<SchoolActivity> findAllSchActivity = this.getSchActivityMainUsers(jp, currentUserSpaceList, SchResult);
    activityMap.put("activityMainUser", activitysResult);
    activityMap.put("schoolActivityMainUser", SchResult);
    result.add(activityMap);
    dr.setDetail(result);
    dr.setData(activitys.size() + findAllSchActivity.size());
    return dr;
  }

  private List<SchoolActivity> getSchActivityMainUsers(JxParams jp, List<UserSpace> userSpaceList,
      List<Map<String, Object>> schResult) {
    Integer isTerm = jp.getIsTerm();
    List<SchoolActivity> findAll = new ArrayList<SchoolActivity>();
    for (UserSpace userSpace2 : userSpaceList) {
      Map<String, Object> paramMap = new HashMap<String, Object>();
      Map<String, Object> subjectGradeMap = new HashMap<String, Object>();
      SchoolActivity sa = new SchoolActivity();
      sa.setMainUserId(jp.getUserId());
      sa.setPhaseId(jp.getPhaseId());
      sa.setMainUserGradeId(userSpace2.getGradeId());
      sa.setMainUserSubjectId(userSpace2.getSubjectId());
      sa.setSchoolYear(schoolYearService.getCurrentSchoolYear());
      sa.addGroup("id");
      if (XUE_QI.equals(isTerm)) {// 如果是学期考核
        Integer term = jp.getTerm();
        if (UP_XUE_QI.equals(term)) {// 上学期
          paramMap.put("startTime", schoolYearService.getNextTermStartTime());
          sa.addCustomCondition(" and createTime < :startTime", paramMap);
        } else {// 下学期
          paramMap.put("endTime", schoolYearService.getNextTermStartTime());
          sa.addCustomCondition(" and createTime >= :endTime ", paramMap);
        }
      }
      sa.addOrder("createTime asc");
      findAll.addAll(schoolActivityService.findAll(sa));
      subjectGradeMap.put("subjectId", userSpace2.getSubjectId());
      subjectGradeMap.put("gradeId", userSpace2.getGradeId());
      subjectGradeMap.put("spaceType", userSpace2.getSysRoleId());
      subjectGradeMap.put("resource", schoolActivityService.findAll(sa));
      schResult.add(subjectGradeMap);
    }
    return findAll;
  }

  private List<Activity> getActivityMainUsers(JxParams jp, List<UserSpace> userSpaceList,
      List<Map<String, Object>> activitysResult) {
    Integer isTerm = jp.getIsTerm();
    List<Activity> findAll = new ArrayList<Activity>();
    for (UserSpace userSpace2 : userSpaceList) {
      Map<String, Object> paramMap = new HashMap<String, Object>();
      Map<String, Object> subjectGradeMap = new HashMap<String, Object>();
      Activity activity = new Activity();
      activity.setMainUserGradeId(userSpace2.getGradeId());
      activity.setMainUserSubjectId(userSpace2.getSubjectId());
      activity.setMainUserId(jp.getUserId());
      activity.setPhaseId(jp.getPhaseId());
      activity.setSchoolYear(schoolYearService.getCurrentSchoolYear());
      activity.addGroup("id");
      if (XUE_QI.equals(isTerm)) {// 如果是学期考核
        Integer term = jp.getTerm();
        if (UP_XUE_QI.equals(term)) {// 上学期
          paramMap.put("startTime", schoolYearService.getNextTermStartTime());
          activity.addCustomCondition(" and createTime < :startTime", paramMap);
        } else {// 下学期
          paramMap.put("endTime", schoolYearService.getNextTermStartTime());
          activity.addCustomCondition(" and createTime >= :endTime ", paramMap);
        }
      }
      activity.addCustomCulomn("id,infoId,subjectName,gradeIds,typeName,activityName,typeId");
      activity.addOrder("createTime asc");
      findAll.addAll(activityService.findAll(activity));
      subjectGradeMap.put("subjectId", userSpace2.getSubjectId());
      subjectGradeMap.put("gradeId", userSpace2.getGradeId());
      subjectGradeMap.put("spaceType", userSpace2.getSysRoleId());
      subjectGradeMap.put("resource", activityService.findAll(activity));
      activitysResult.add(subjectGradeMap);
    }
    return findAll;
  }

  /**
   * 校际教研
   * 
   * @param jp
   * @param type
   * @return
   */
  @Override
  public DataResult getSchoolActivity(JxParams jp, Integer type) {
    DataResult dr = new DataResult();
    Integer userId = jp.getUserId();
    List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
    List<Integer> resourceId = new ArrayList<Integer>();
    Map<String, String> findGradesByPhaseId = this.getGradeMapByOrgId(jp);
    Map<String, String> findSubjectsByPhaseId = findSubject(jp.getPhaseId());
    if (userId != null) {
      List<UserSpace> allUserSpace = getCurrentUserSpaceList(jp);

      for (UserSpace userSpace2 : allUserSpace) {
        String sql = " and editType <> :editType";
        List<Integer> resourceSpaceId = new ArrayList<Integer>();
        Map<String, Object> subjectGradeMap = new HashMap<String, Object>();
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("editType", SchoolActivityTracks.ZHUBEI);
        SchoolActivityTracks tracks = new SchoolActivityTracks();
        tracks.setUserId(userSpace2.getUserId());
        tracks.setSubjectId(userSpace2.getSubjectId());
        tracks.setGradeId(userSpace2.getGradeId());
        Integer isTerm = jp.getIsTerm();
        tracks.setSchoolYear(schoolYearService.getCurrentSchoolYear());
        tracks.setSpaceId(userSpace2.getId());
        if (XUE_QI.equals(isTerm)) {// 如果是学期考核
          Integer term = jp.getTerm();
          if (UP_XUE_QI.equals(term)) {// 上学期
            paramMap.put("startTime", schoolYearService.getNextTermStartTime());
            tracks.addCustomCondition(sql + " and crtDttm < :startTime", paramMap);
          } else {// 下学期
            paramMap.put("endTime", schoolYearService.getNextTermStartTime());
            tracks.addCustomCondition(sql + " and crtDttm >= :endTime ", paramMap);
          }
        } else {
          tracks.addCustomCondition(sql, paramMap);
        }
        tracks.addOrder(" crtDttm asc");
        List<SchoolActivityTracks> tracksList = schoolActivityTracksService.findAll(tracks);
        for (SchoolActivityTracks activityTracks : tracksList) {
          if (activityTracks.getActivityId() != null && !resourceSpaceId.contains(activityTracks.getActivityId())) {
            resourceSpaceId.add(activityTracks.getActivityId());
          }
          if (activityTracks.getActivityId() != null && !resourceId.contains(activityTracks.getActivityId())) {
            resourceId.add(activityTracks.getActivityId());
          }
        }
        Map<String, Object> paramMap1 = new HashMap<String, Object>();
        Discuss discuss = new Discuss();
        discuss.setCrtId(userSpace2.getUserId());
        discuss.setSpaceId(userSpace2.getId());
        discuss.setTypeId(ResTypeConstants.SCHOOLTEACH);
        if (XUE_QI.equals(isTerm)) {// 如果是学期考核
          Integer term = jp.getTerm();
          if (UP_XUE_QI.equals(term)) {// 上学期
            paramMap1.put("endTime", schoolYearService.getNextTermStartTime());
            paramMap1.put("startTime", schoolYearService.getUpTermStartTime());
            discuss.addCustomCondition(" and crtDttm < :endTime and crtDttm >= :startTime", paramMap1);
          } else {// 下学期
            paramMap1.put("startTime", schoolYearService.getNextTermStartTime());
            paramMap1.put("endTime", schoolYearService.getNextYearUpTermStartTime());
            discuss.addCustomCondition(" and crtDttm >= :startTime and crtDttm < :endTime", paramMap1);
          }
        } else {
          paramMap1.put("startTime", schoolYearService.getUpTermStartTime());
          paramMap1.put("endTime", schoolYearService.getNextYearUpTermStartTime());
          discuss.addCustomCondition(" and crtDttm >= :startTime and crtDttm < :endTime", paramMap1);
        }
        discuss.addOrder(" crtDttm asc");
        List<Discuss> findAllDiscuss = discussService.findAll(discuss);
        for (Discuss discusses : findAllDiscuss) {
          if (discusses.getActivityId() != null && !resourceSpaceId.contains(discusses.getActivityId())) {
            resourceSpaceId.add(discusses.getActivityId());
          }
          if (discusses.getActivityId() != null && !resourceId.contains(discusses.getActivityId())) {
            resourceId.add(discusses.getActivityId());
          }
        }
        SchoolActivity sa = new SchoolActivity();
        List<Map<String, Object>> activityList = new ArrayList<Map<String, Object>>();
        List<SchoolActivity> findAllActivity = new ArrayList<SchoolActivity>();
        if (!CollectionUtils.isEmpty(resourceSpaceId)) {
          sa.buildCondition(" and id in (:activityId)").put("activityId", resourceSpaceId);
          findAllActivity = schoolActivityService.findAll(sa);
        }
        for (SchoolActivity schActivity2 : findAllActivity) {
          Map<String, Object> datamap = new HashMap<String, Object>();
          datamap.put("resourceTypeName", schActivity2.getTypeName());
          datamap.put("resourceName", schActivity2.getActivityName());
          datamap.put("resourceId", schActivity2.getId());
          activityList.add(datamap);
        }
        String grade = findGradesByPhaseId.get(userSpace2.getGradeId().toString());
        String subject = findSubjectsByPhaseId.get(userSpace2.getSubjectId().toString());
        if (grade != null && subject != null) {
          subjectGradeMap.put("resource", activityList);
          subjectGradeMap.put("subjectGradeName", grade + subject);
          subjectGradeMap.put("subjectId", userSpace2.getSubjectId());
          subjectGradeMap.put("gradeId", userSpace2.getGradeId());
          subjectGradeMap.put("spaceType", userSpace2.getSysRoleId());
          result.add(subjectGradeMap);
        } else {
          subjectGradeMap.put("resource", activityList);
          subjectGradeMap.put("subjectGradeName", userSpace2.getSpaceName());
          subjectGradeMap.put("subjectId", userSpace2.getSubjectId());
          subjectGradeMap.put("gradeId", userSpace2.getGradeId());
          subjectGradeMap.put("spaceType", userSpace2.getSysRoleId());
          result.add(subjectGradeMap);
        }
      }
    }
    dr.setDetail(result);
    dr.setData(resourceId.size());
    return dr;
  }

  @Override
  public DataResult getWebShareDatas(JxParams jp) {
    List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
    List<UserSpace> allUserSpace = getCurrentUserSpaceList(jp);
    Map<String, Object> webDataMap = new HashMap<String, Object>();
    Integer isTerm = jp.getIsTerm();
    DataResult dr = new DataResult();
    LessonPlan lessonPlan = new LessonPlan();
    lessonPlan.setIsShare(true);
    DataResult jiaoanRes = getBeiKeRes(jp, ResTypeConstants.JIAOAN, lessonPlan);// 教案
    webDataMap.put("jiaoan", jiaoanRes.getDetail());

    LessonPlan lessonPlan1 = new LessonPlan();
    lessonPlan1.setIsShare(true);
    DataResult kejianRes = getBeiKeRes(jp, ResTypeConstants.KEJIAN, lessonPlan1);// 课件
    webDataMap.put("kejian", kejianRes.getDetail());

    LessonPlan lessonPlan2 = new LessonPlan();
    lessonPlan2.setIsShare(true);
    DataResult fansiRes = getBeiKeRes(jp, ResTypeConstants.FANSI, lessonPlan2);// 反思
    webDataMap.put("fansi", fansiRes.getDetail());

    LectureRecords lectureRecords = new LectureRecords();
    lectureRecords.setIsShare(1);
    DataResult lectureRes = getLectureRes(jp, ResTypeConstants.LECTURE, lectureRecords);
    webDataMap.put("lecture", lectureRes.getDetail());

    PlainSummary plainSummary = new PlainSummary();
    plainSummary.setIsShare(1);
    DataResult plainSummarys = getPlainSummarys(jp, ResTypeConstants.TPPLAIN_SUMMARY_SUMMARY, plainSummary);// 个人计划
    webDataMap.put("personPlan", plainSummarys.getDetail());

    PlainSummary plainSummary1 = new PlainSummary();
    plainSummary1.setIsShare(1);
    DataResult plainSummaryes = getPlainSummarys(jp, ResTypeConstants.TPPLAIN_SUMMARY_PLIAN, plainSummary1);// 个人总结
    webDataMap.put("personSummary", plainSummaryes.getDetail());

    PlainSummary plainSummary2 = new PlainSummary();
    plainSummary2.setIsShare(1);
    plainSummary2.setCategory(3);// 学校计划
    DataResult schPlain = getPlainSummarys(jp, null, plainSummary2);
    webDataMap.put("schPlain", schPlain.getDetail());

    PlainSummary plainSummary3 = new PlainSummary();
    plainSummary3.setIsShare(1);
    plainSummary3.setCategory(4);// 学校总结
    DataResult schPlainSummary = getPlainSummarys(jp, null, plainSummary3);
    webDataMap.put("schPlainSummary", schPlainSummary.getDetail());

    List<Record> findAllRecords = new ArrayList<Record>();
    List<Integer> findIds = new ArrayList<Integer>();
    List<Map<String, Object>> recordLists = new ArrayList<Map<String, Object>>();
    for (UserSpace userSpace2 : allUserSpace) {
      Recordbag recordbag = new Recordbag();
      Map<String, Object> datamap1 = new HashMap<String, Object>();
      Map<String, Object> datamap = new HashMap<String, Object>();
      recordbag.setShare(1);
      recordbag.setTeacherId(jp.getUserId().toString());
      recordbag.setSubjectId(userSpace2.getSubjectId());
      recordbag.setGradeId(userSpace2.getGradeId());
      String sql1 = "";
      if (XUE_QI.equals(isTerm)) {
        Integer term = jp.getTerm();
        if (UP_XUE_QI.equals(term)) {// 上学期
          datamap1.put("startTime", schoolYearService.getUpTermStartTime());
          datamap1.put("endTime", schoolYearService.getNextTermStartTime());
          recordbag.addCustomCondition(sql1 + " and shareTime < :endTime and shareTime >= :startTime", datamap1);
        } else {// 下学期
          datamap1.put("startTime", schoolYearService.getNextTermStartTime());
          datamap1.put("endTime", schoolYearService.getNextYearUpTermStartTime());
          recordbag.addCustomCondition(sql1 + " and shareTime >= :startTime and shareTime < :endTime", datamap1);
        }
      } else {
        datamap1.put("startTime", schoolYearService.getUpTermStartTime());
        datamap1.put("endTime", schoolYearService.getNextYearUpTermStartTime());
        recordbag.addCustomCondition(" and shareTime >= :startTime and shareTime < :endTime", datamap1);
      }
      List<Recordbag> find = recordbagService.find(recordbag, Integer.MAX_VALUE);
      List<Record> recordList = new ArrayList<Record>();
      for (Recordbag recordbag2 : find) {
        if (!findIds.contains(recordbag2.getId())) {
          Record record = new Record();
          record.setBagId(recordbag2.getId());
          record.addOrder("createTime asc");
          recordList.addAll(recordService.findAll(record));
          findAllRecords.addAll(recordService.findAll(record));
        }
      }
      datamap.put("resource", recordList);
      datamap.put("gradeId", userSpace2.getGradeId());
      datamap.put("subjectId", userSpace2.getSubjectId());
      datamap.put("spaceType", userSpace2.getSysRoleId());
      recordLists.add(datamap);
    }
    webDataMap.put("record", recordLists);

    Thesis thesis = new Thesis();
    thesis.setPhaseId(jp.getPhaseId());

    String sql = "";
    Map<String, Object> datamap = new HashMap<String, Object>();
    if (XUE_QI.equals(isTerm)) {
      Integer term = jp.getTerm();
      if (UP_XUE_QI.equals(term)) {// 上学期
        datamap.put("startTime", schoolYearService.getUpTermStartTime());
        datamap.put("endTime", schoolYearService.getNextTermStartTime());
        thesis.addCustomCondition(sql + " and shareTime < :endTime and shareTime >= :startTime", datamap);
      } else {// 下学期
        datamap.put("startTime", schoolYearService.getNextTermStartTime());
        datamap.put("endTime", schoolYearService.getNextYearUpTermStartTime());
        thesis.addCustomCondition(sql + " and shareTime >= :startTime and shareTime < :endTime ", datamap);
      }
    } else {
      datamap.put("startTime", schoolYearService.getUpTermStartTime());
      datamap.put("endTime", schoolYearService.getNextYearUpTermStartTime());
      thesis.addCustomCondition(" and shareTime >= :startTime and shareTime < :endTime", datamap);
    }
    thesis.setUserId(jp.getUserId());
    thesis.setIsShare(1);
    thesis.setEnable(1);
    thesis.addOrder("crtDttm asc");
    List<Thesis> findAllThies = thesisService.findAll(thesis);
    webDataMap.put("thesis", findAllThies.size());

    Integer totalSize = jiaoanRes.getData() + kejianRes.getData() + fansiRes.getData() + lectureRes.getDetail().size()
        + plainSummarys.getData() + findAllRecords.size() + findAllThies.size() + plainSummaryes.getData()
        + schPlain.getData() + schPlainSummary.getData();
    result.add(webDataMap);
    dr.setDetail(result);
    dr.setData(totalSize);
    return dr;
  }
}
