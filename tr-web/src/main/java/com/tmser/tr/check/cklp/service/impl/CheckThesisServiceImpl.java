/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.check.cklp.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.tmser.tr.check.bo.CheckInfo;
import com.tmser.tr.check.cklp.service.CheckThesisService;
import com.tmser.tr.check.dao.CheckInfoDao;
import com.tmser.tr.check.service.CheckInfoService;
import com.tmser.tr.common.ResTypeConstants;
import com.tmser.tr.common.bo.QueryObject.JOINTYPE;
import com.tmser.tr.common.utils.WebThreadLocalUtils;
import com.tmser.tr.manage.meta.Meta;
import com.tmser.tr.manage.meta.MetaUtils;
import com.tmser.tr.manage.org.bo.Organization;
import com.tmser.tr.manage.org.service.OrganizationService;
import com.tmser.tr.thesis.bo.Thesis;
import com.tmser.tr.thesis.dao.ThesisDao;
import com.tmser.tr.uc.bo.User;
import com.tmser.tr.uc.bo.UserSpace;
import com.tmser.tr.uc.service.SchoolYearService;
import com.tmser.tr.uc.service.UserService;
import com.tmser.tr.uc.service.UserSpaceService;
import com.tmser.tr.uc.utils.SessionKey;
import com.tmser.tr.utils.StringUtils;

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
public class CheckThesisServiceImpl implements CheckThesisService {

  private static final Integer UP_XUE_QI = 0;// 上学期

  @Autowired
  private ThesisDao thesisDao;
  @Autowired
  private CheckInfoService checkInfoService;
  @Autowired
  private UserSpaceService userSpaceService;
  @Autowired
  private UserService userService;
  @Autowired
  private SchoolYearService schoolYearService;
  @Autowired
  private OrganizationService organizationService;

  @Override
  public Map<Integer, Map<String, String>> getUserDetail(Map<Integer, Map<String, Long>> dataMap, Integer userId) {
    UserSpace space = (UserSpace) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE);
    User currentUser = (User) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_USER);
    Organization org = organizationService.findOne(currentUser.getOrgId());
    Map<Integer, Map<String, String>> userMap = new HashMap<Integer, Map<String, String>>();
    if (org != null && space != null && currentUser != null) {
      Integer[] areaIds = StringUtils.toIntegerArray(org.getAreaIds().substring(1, org.getAreaIds().lastIndexOf(",")),
          ",");
      List<Meta> listAllSubjectByPhaseId = MetaUtils.getPhaseSubjectMetaProvider().listAllSubject(org.getId(),
          space.getPhaseId(), areaIds);
      List<Meta> listAllGrade = MetaUtils.getOrgTypeMetaProvider()
          .listAllGrade(org.getSchoolings(), space.getPhaseId());
      Map<String, String> subjectMap = null;
      if (listAllSubjectByPhaseId != null && listAllSubjectByPhaseId.size() > 0) {
        subjectMap = new HashMap<String, String>();
        for (Meta meta : listAllSubjectByPhaseId) {
          subjectMap.put(meta.getId().toString(), meta.getName());
        }
      }
      Map<String, String> gradeMap = null;
      if (listAllGrade != null && listAllGrade.size() > 0) {
        gradeMap = new HashMap<String, String>();
        for (Meta meta : listAllGrade) {
          gradeMap.put(meta.getId().toString(), meta.getName());
        }
      }
      if (userId != null) {
        Map<String, String> detailMap = new HashMap<String, String>();
        this.getDetail(userId, detailMap, subjectMap, gradeMap, space.getPhaseId());
        userMap.put(userId, detailMap);
      } else {
        for (Integer userid : dataMap.keySet()) {
          Map<String, String> detailMap = null;
          if (userMap.containsKey(userid)) {
            detailMap = userMap.get(userid);
          } else {
            detailMap = new HashMap<String, String>();
            userMap.put(userid, detailMap);
          }
          this.getDetail(userid, detailMap, subjectMap, gradeMap, space.getPhaseId());
        }
      }
      return userMap;
    }
    return null;
  }

  /**
   * @param userId
   * @param detailMap
   * @param subjectMap
   * @param gradeMap
   * @param phaseId
   */
  private void getDetail(Integer userId, Map<String, String> detailMap, Map<String, String> subjectMap,
      Map<String, String> gradeMap, Integer phaseId) {
    User user = userService.findOne(userId);
    UserSpace space = new UserSpace();
    space.setUserId(userId);
    space.setEnable(UserSpace.ENABLE);
    space.setSchoolYear((Integer) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SCHOOLYEAR));
    space.setPhaseId(phaseId);
    List<UserSpace> allSpace = userSpaceService.findAll(space);
    if (user != null) {
      detailMap.put("profession", user.getProfession());
      detailMap.put("username", user.getName());
    }
    if (!CollectionUtils.isEmpty(allSpace)) {
      StringBuilder grade = new StringBuilder();
      StringBuilder subject = new StringBuilder();
      StringBuilder role = new StringBuilder();
      Set<Integer> gradeSet = new HashSet<Integer>();
      Set<Integer> subjectSet = new HashSet<Integer>();
      for (UserSpace userSpace : allSpace) {
        if (userSpace.getGradeId() != null) {
          gradeSet.add(userSpace.getGradeId());
        }
        if (userSpace.getSubjectId() != null) {
          subjectSet.add(userSpace.getSubjectId());
        }
        if (StringUtils.isNotEmpty(userSpace.getSpaceName())) {
          role.append(userSpace.getSpaceName());
          role.append("、");
        }
      }
      for (Integer grade_id : gradeSet) {
        String grad = gradeMap.get(String.valueOf(grade_id));
        if (StringUtils.isNotEmpty(grad)) {
          grade.append(grad);
          grade.append("、");
        }
      }
      for (Integer subject_id : subjectSet) {
        String sub = subjectMap.get(String.valueOf(subject_id));
        if (StringUtils.isNotEmpty(sub)) {
          subject.append(sub);
          subject.append("、");
        }
      }
      if (grade.length() >= 2)
        detailMap.put("gradeName", grade.substring(0, grade.length() - "、".length()));
      if (subject.length() >= 2)
        detailMap.put("subjectName", subject.substring(0, subject.length() - "、".length()));
      if (role.length() >= 2)
        detailMap.put("roleName", role.substring(0, role.length() - "、".length()));
    }

  }

  /**
   * @param grade
   * @param subject
   * @param type
   * @return
   * @see com.tmser.tr.check.cklp.service.CheckLessonService#listTchLessonStatics(java.lang.Integer,
   *      java.lang.Integer, java.lang.Integer)
   */
  @Override
  public Map<Integer, Map<String, Long>> listTchThesisStatics(Thesis thesis, UserSpace paramSpace) {
    UserSpace userSpace = (UserSpace) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE);
    Map<Integer, Map<String, Long>> thesisMap = new HashMap<Integer, Map<String, Long>>();
    if (userSpace != null && thesis != null) {
      UserSpace usmodel = new UserSpace();
      usmodel.setGradeId(paramSpace.getGradeId());
      usmodel.setSubjectId(paramSpace.getSubjectId());
      usmodel.setSysRoleId(paramSpace.getSysRoleId());
      usmodel.setEnable(UserSpace.ENABLE);
      usmodel.setOrgId(userSpace.getOrgId());
      usmodel.setSchoolYear(thesis.getSchoolYear());
      usmodel.setPhaseId(userSpace.getPhaseId());
      List<UserSpace> allUser = userSpaceService.findAll(usmodel);

      for (UserSpace us : allUser) {
        if (us.getUserId() != null) {
          Map<String, Long> data = new HashMap<String, Long>();
          data.put("write", 0L);
          data.put("submit", 0L);
          data.put("check", 0L);
          thesisMap.put(us.getUserId(), data);
        }
      }
      thesis.setUserId(null);
      thesis.setOrgId(userSpace.getOrgId());
      thesis.setPhaseId(userSpace.getPhaseId());
      List<Map<String, Object>> writeMap = this.getThesisData(thesis, false, paramSpace);
      thesis.setIsSubmit(Thesis.SUBMIT);
      List<Map<String, Object>> submitMap = this.getThesisData(thesis, true, paramSpace);
      for (Map<String, Object> m : writeMap) {// 撰写数
        Integer userId = (Integer) m.get("userId");
        Long totalNum = (Long) m.get("totalNum");
        if (userId != null) {
          Map<String, Long> map = thesisMap.get(userId);
          if (map != null) {
            map.put("write", map.get("write") + totalNum);
          }
        }
      }
      for (Map<String, Object> m : submitMap) {// 提交数
        Integer userId = (Integer) m.get("userId");
        Long totalNum = (Long) m.get("totalNum");
        if (userId != null) {
          Map<String, Long> map = thesisMap.get(userId);
          if (map != null) {
            map.put("submit", map.get("submit") + totalNum);
          }
        }
      }
      List<Map<String, Object>> checkedList = this.getCheckThesisData(userSpace.getId(), thesis, paramSpace);
      for (Map<String, Object> m : checkedList) {// 查阅数
        Integer userId = (Integer) m.get("userId");
        Long totalNum = (Long) m.get("cnt");
        if (userId != null) {
          Map<String, Long> map = thesisMap.get(userId);
          if (map != null) {
            map.put("check", map.get("check") + totalNum);
          }
        }
      }
    }
    return thesisMap;
  }

  private List<Map<String, Object>> getThesisData(Thesis thesis, boolean isSubmit, UserSpace paramSpace) {
    Map<String, Object> paramMap = new HashMap<String, Object>();
    if (thesis.getSchoolTerm() != null) {
      thesis.setSubjectId(null);
      if (isSubmit) {
        thesis.addCustomCondition(" and t.submitTime >= :startTime and t.submitTime < :endTime", paramMap);
        thesis.setIsSubmit(Thesis.SUBMIT);
      } else {
        thesis.addCustomCondition(" and t.crtDttm < :endTime and t.crtDttm >= :startTime", paramMap);
        thesis.setIsSubmit(null);
      }
      paramMap.put("startTime", this.getDateByTerm(thesis.getSchoolTerm(), 0));
      paramMap.put("endTime", this.getDateByTerm(thesis.getSchoolTerm(), 1));
    }
    return thesisDao.countThesisGroupByAuth(thesis, paramSpace.getSubjectId(), paramSpace.getGradeId(),
        paramSpace.getSysRoleId());
  }

  private List<Map<String, Object>> getCheckThesisData(Integer checkUserSpaceId, Thesis thesis, UserSpace paramSpace) {
    CheckInfo cmodel = new CheckInfo();
    cmodel.setResType(Integer.valueOf(ResTypeConstants.JIAOXUELUNWEN));
    cmodel.setSpaceId(checkUserSpaceId);
    cmodel.setAuthorId(thesis.getUserId());
    cmodel.setSchoolYear(thesis.getSchoolYear());
    Map<String, Object> paramMap = new HashMap<String, Object>();
    if (thesis.getSchoolTerm() != null) {
      paramMap.put("startTime", this.getDateByTerm(thesis.getSchoolTerm(), 0));
      paramMap.put("endTime", this.getDateByTerm(thesis.getSchoolTerm(), 1));
      cmodel.addCustomCondition(" and createtime >= :startTime and createtime < :endTime", paramMap);
    }
    CheckInfoDao dao = (CheckInfoDao) checkInfoService.getDAO();
    return dao.countCheckInfoGroupByUser(cmodel);
  }

  private Date getDateByTerm(Integer term, Integer tag) {// tag 0:开始时间 1:结束时间
    Date startTime = new Date();
    Date endTime = new Date();
    if (UP_XUE_QI.equals(term)) {
      startTime = schoolYearService.getUpTermStartTime();
      endTime = schoolYearService.getNextTermStartTime();
    } else {
      startTime = schoolYearService.getNextTermStartTime();
      endTime = schoolYearService.getNextYearUpTermStartTime();
    }
    return tag == 0 ? startTime : endTime;
  }

  /**
   * @param userId
   * @param thesis
   * @return
   * @see com.tmser.tr.check.cklp.service.CheckThesisService#countWriteInfo(java.lang.Integer,
   *      com.tmser.tr.thesis.bo.Thesis)
   */
  @Override
  public Long countWriteInfo(Thesis thesis, UserSpace paramSpace) {
    Long count = 0L;
    List<Map<String, Object>> writeMap = this.getThesisData(thesis, false, paramSpace);
    if (!CollectionUtils.isEmpty(writeMap)) {
      count = (Long) writeMap.get(0).get("totalNum");
    }
    return count;
  }

  /**
   * @param userId
   * @param thesis
   * @param year
   * @return
   * @see com.tmser.tr.check.cklp.service.CheckThesisService#countSubmitLesson(java.lang.Integer,
   *      com.tmser.tr.thesis.bo.Thesis, java.lang.Integer)
   */
  @Override
  public Long countSubmitThesis(Thesis thesis, UserSpace paramSpace) {
    Long count = 0L;
    List<Map<String, Object>> writeMap = this.getThesisData(thesis, true, paramSpace);
    if (!CollectionUtils.isEmpty(writeMap)) {
      count = (Long) writeMap.get(0).get("totalNum");
    }
    return count;
  }

  /**
   * @param checkUser
   *          查阅者
   * @param authUserId
   *          被查阅者
   * @param thesis
   * @param year
   * @return
   * @see com.tmser.tr.check.cklp.service.CheckThesisService#countCheckLesson(java.lang.Integer,
   *      com.tmser.tr.thesis.bo.Thesis, java.lang.Integer)
   */
  @Override
  public Long countCheckThesis(UserSpace checkUser, Thesis thesis, UserSpace paramSpace) {
    Long count = 0L;
    if (checkUser != null) {
      List<Map<String, Object>> writeMap = this.getCheckThesisData(checkUser.getId(), thesis, paramSpace);
      if (!CollectionUtils.isEmpty(writeMap)) {
        count = (Long) writeMap.get(0).get("cnt");
      }
    }
    return count;
  }

  /**
   * 
   * @param checkUser
   *          查阅者
   * @param authorId
   *          被查阅者
   * @param thesis
   *          年级、学科、学期
   * @return
   */
  @Override
  public Map<Integer, CheckInfo> checkedThesisMap(UserSpace checkUser, Integer authorId, Thesis thesis,
      UserSpace paramSpace) {
    Map<Integer, CheckInfo> checkIds = new HashMap<Integer, CheckInfo>();
    if (checkUser != null) {
      CheckInfo model = new CheckInfo();
      model.addAlias("c");
      model.setAuthorId(authorId);
      model.setUserId(checkUser.getUserId());
      model.setSpaceId(checkUser.getId());
      model.setResType(Integer.valueOf(ResTypeConstants.JIAOXUELUNWEN));
      model.addJoin(JOINTYPE.INNER, " Thesis t").on(" c.resId = t.id");
      Map<String, Object> paramMap = new HashMap<String, Object>();
      if (thesis.getSchoolTerm() != null) {
        paramMap.put("startTime", this.getDateByTerm(thesis.getSchoolTerm(), 0));
        paramMap.put("endTime", this.getDateByTerm(thesis.getSchoolTerm(), 1));
        model.addCustomCondition(" and c.createtime >= :startTime and c.createtime < :endTime", paramMap);
      }
      model.setSchoolYear((Integer) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SCHOOLYEAR));
      List<CheckInfo> cklist = checkInfoService.findAll(model);
      for (CheckInfo c : cklist) {
        checkIds.put(c.getResId(), c);
        if (c.getIsUpdate()) {
          model = new CheckInfo();
          model.setId(c.getId());
          model.setIsUpdate(false);
          checkInfoService.update(model);
        }
      }
    }
    return checkIds;
  }

  /**
   * 
   * @param authorId
   *          撰写者
   * @param thesis
   *          根据学科、年级上下学期查询
   * @return
   */
  @Override
  public List<Thesis> getThesisData(Integer authorId, Thesis thesis) {
    List<Thesis> listAll = null;
    if (thesis != null) {
      thesis.setUserId(authorId);
      thesis.setIsSubmit(Thesis.SUBMIT);
      thesis.setSubjectId(null);
      Map<String, Object> paramMap = new HashMap<String, Object>();
      if (thesis.getSchoolTerm() != null) {
        paramMap.put("startTime", this.getDateByTerm(thesis.getSchoolTerm(), 0));
        paramMap.put("endTime", this.getDateByTerm(thesis.getSchoolTerm(), 1));
        thesis.addCustomCondition(" and submitTime >= :startTime and submitTime < :endTime", paramMap);
      }
      thesis.addOrder("submitTime desc");
      listAll = thesisDao.listAll(thesis);
    }
    return listAll;
  }
}
