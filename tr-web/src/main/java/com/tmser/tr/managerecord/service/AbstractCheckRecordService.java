/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.managerecord.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import com.tmser.tr.check.bo.CheckInfo;
import com.tmser.tr.check.bo.CheckOpinion;
import com.tmser.tr.check.dao.CheckInfoDao;
import com.tmser.tr.check.dao.CheckOpinionDao;
import com.tmser.tr.common.page.Page;
import com.tmser.tr.common.page.PageList;
import com.tmser.tr.common.utils.WebThreadLocalUtils;
import com.tmser.tr.manage.meta.Meta;
import com.tmser.tr.manage.meta.MetaUtils;
import com.tmser.tr.manage.org.bo.Organization;
import com.tmser.tr.manage.org.service.OrganizationService;
import com.tmser.tr.uc.SysRole;
import com.tmser.tr.uc.bo.UserSpace;
import com.tmser.tr.uc.utils.SessionKey;
import com.tmser.tr.utils.StringUtils;

/**
 * <pre>
 *
 * </pre>
 *
 * @author tmser
 * @version $Id: ManagerAbstractService.java, v 1.0 2015年5月25日 下午4:45:20 tmser
 *          Exp $
 */
public abstract class AbstractCheckRecordService implements CheckRecordService, CheckRecordInfoService {

  @Autowired
  private CheckInfoDao checkInfoDao;
  @Autowired
  private CheckOpinionDao checkOpinionDao;
  @Autowired
  private OrganizationService organizationService;

  /**
   * @param grade
   * @param term
   * @param subject
   * @return
   * @see com.tmser.tr.managerecord.service.ManagerInterFaceService#callBackModel(int,
   *      int, int)
   */
  @Override
  public ManagerVO getCheckRecord(Integer grade, Integer term, Integer subject) {
    ManagerVO mv = new ManagerVO();
    Set<Integer> gradeIds = null, subjectIds = null;
    if (grade == null || grade == 0) {
      gradeIds = mergeGrade();
    } else {
      gradeIds = new HashSet<Integer>();
      gradeIds.add(grade);
    }

    if (subject == null || subject == 0) {
      subjectIds = mergeSub();
    } else {
      subjectIds = new HashSet<Integer>();
      subjectIds.add(grade);
    }

    mv.setSubmitNum(getSubmitSum(gradeIds, subjectIds, term));
    mv.setCheckNum(countCheckNum(gradeIds, subjectIds, term, false));
    mv.setCommentNum(countCheckNum(gradeIds, subjectIds, term, true));
    mv.setName(getName());
    mv.setTitle(getTitle() == null ? "提交总数" : getTitle());
    mv.setType(getType());
    mv.setUri(getUri());
    return mv;
  }

  protected Set<Integer> mergeSub() {
    UserSpace currentSpace = (UserSpace) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE);
    @SuppressWarnings("unchecked")
    List<UserSpace> userSpaces = (List<UserSpace>) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.USER_SPACE_LIST);
    Set<Integer> subs = new HashSet<Integer>();
    Organization org = organizationService.findOne(currentSpace.getOrgId());
    for (UserSpace s : userSpaces) {
      Integer sub = s.getSubjectId();
      if (sub == 0) {
        Integer[] areaIds = StringUtils.toIntegerArray(
            org.getAreaIds().substring(1, org.getAreaIds().lastIndexOf(",")), ",");
        List<Meta> ss = MetaUtils.getPhaseSubjectMetaProvider().listAllSubject(org.getId(), currentSpace.getPhaseId(),
            areaIds);
        for (Meta o : ss) {
          subs.add(o.getId());
        }
        subs.add(sub);
      } else {
        subs.add(s.getSubjectId());
      }
    }
    return subs;
  }

  protected Set<Integer> mergeGrade() {
    UserSpace currentSpace = (UserSpace) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE);
    @SuppressWarnings("unchecked")
    List<UserSpace> userSpaces = (List<UserSpace>) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.USER_SPACE_LIST);
    Organization org = organizationService.findOne(currentSpace.getOrgId());
    Set<Integer> grades = new HashSet<Integer>();
    for (UserSpace g : userSpaces) {
      Integer grade = g.getGradeId();
      if (grade == 0) {
        List<Meta> ss = MetaUtils.getOrgTypeMetaProvider().listAllGrade(org.getSchoolings(), currentSpace.getPhaseId());
        for (Meta o : ss) {
          grades.add(o.getId());
        }
        grades.add(grade);
      } else {
        grades.add(g.getGradeId());
      }
    }
    return grades;
  }

  /**
   * 统计查阅数
   * 
   * @param grades
   * @param subjects
   * @param needOptinion
   * @return
   */
  protected Integer countCheckNum(Set<Integer> grades, Set<Integer> subjects, Integer term, boolean needOptinion) {
    Integer checkCount = 0;
    UserSpace currentSpace = (UserSpace) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE);
    Integer schoolYear = (Integer) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SCHOOLYEAR);
    Map<String, Object> paramMap = new HashMap<String, Object>(2);

    paramMap.put("gradeIds", grades);
    paramMap.put("subjectIds", subjects);
    CheckInfo model = new CheckInfo();
    model.setUserId(currentSpace.getUserId());
    model.setPhase(currentSpace.getPhaseId());
    model.setSchoolYear(schoolYear);
    if (term != null)
      model.setTerm(term);
    if (needOptinion)
      model.setHasOptinion(true);
    // model.addCustomCondition("and gradeId in(:gradeIds) and subjectId in (:subjectIds)",
    // paramMap);
    for (Integer type : getType()) {
      model.setResType(type);
      checkCount += checkInfoDao.count(model); // 查阅数
    }

    return checkCount;
  }

  /**
   * 提交列显示名称名称
   * 
   * @return
   */
  protected String getTitle() {
    @SuppressWarnings("unchecked")
    List<UserSpace> userSpaces = (List<UserSpace>) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.USER_SPACE_LIST);
    Map<Integer, List<UserSpace>> sb = new HashMap<>();
    for (UserSpace us : userSpaces) {
      List<UserSpace> usList = sb.get(us.getRoleId());
      if (usList == null) {
        usList = new ArrayList<>();
      }
      usList.add(us);
      sb.put(us.getSysRoleId(), usList);
    }
    return titleRule(sb);
  }

  protected String titleRule(Map<Integer, List<UserSpace>> str) {
    if (str.containsKey(SysRole.XZ.getId()) || str.containsKey(SysRole.ZR) || str.containsKey(SysRole.FXZ)) {
      return "各学科提交总数";
    }
    String grade = "";
    String subject = "";
    String gradeSubject = "";
    Set<Integer> gradeIds = new HashSet<>();
    Set<Integer> subjectIds = new HashSet<>();
    boolean hasNJ = false;
    boolean hasXK = false;
    boolean hasBK = false;
    if (str.containsKey(SysRole.NJZZ.getId())) {
      List<UserSpace> sps = str.get(SysRole.NJZZ.getId());
      for (UserSpace sp : sps) {
        Meta g = MetaUtils.getMeta(sp.getGradeId());
        if (g != null) {
          grade += (grade == "") ? "" : "、" + g.getName();
          gradeIds.add(g.getId());
        }
      }

      hasNJ = true;
    }

    if (str.containsKey(SysRole.XKZZ.getId())) {
      List<UserSpace> sps = str.get(SysRole.XKZZ.getId());
      for (UserSpace sp : sps) {
        Meta g = MetaUtils.getMeta(sp.getSubjectId());
        if (g != null) {
          subject += (subject == "") ? "" : "、" + g.getName();
        }
        subjectIds.add(g.getId());
      }
      hasXK = true;
    }

    if (str.containsKey(SysRole.BKZZ.getId())) {
      List<UserSpace> sps = str.get(SysRole.BKZZ.getId());
      for (UserSpace sp : sps) {
        if (gradeIds.contains(sp.getGradeId()) || subjectIds.contains(sp.getSubjectId())) {
          continue;
        }

        Meta g = MetaUtils.getMeta(sp.getGradeId());
        if (g != null) {
          gradeSubject += (gradeSubject == "") ? "" : "、" + g.getName();
        }

        Meta s = MetaUtils.getMeta(sp.getSubjectId());
        if (s != null) {
          gradeSubject += s.getName();
        }
      }

      hasBK = true;
    }

    if (hasNJ && hasXK) {
      return subject + "学科及" + grade + "其他学科提交总数";
    }

    if (hasXK && hasBK) {
      return subject + "学科" + (gradeSubject == "" ? "" : "及" + gradeSubject) + "提交总数";
    }

    if (hasNJ && hasBK) {
      return grade + "各学科" + (gradeSubject == "" ? "" : "及" + gradeSubject) + "提交总数";
    }

    if (hasXK && !hasBK) {
      return subject + "提交总数";
    }

    if (hasNJ && !hasBK) {
      return grade + "各学科提交总数";
    }

    if (hasBK) {
      return gradeSubject + "学科提交总数";
    }

    return "";
  }

  /**
   * 入口地址uri
   * 默认是将type以'-'链接
   * 
   * @return
   */
  protected String getUri() {
    StringBuilder uri = new StringBuilder();
    for (Integer type : getType()) {
      uri.append(type).append("-");
    }
    if (uri.length() > 0)
      uri.deleteCharAt(uri.length() - 1);
    return uri.toString();
  }

  /**
   * 显示名称
   * 
   * @return
   */
  protected abstract String getName();

  /**
   * 统计可以供当前用户查阅的提交资源总数
   * 
   * @param grades
   *          资源年级。
   * @param subjects
   *          资源学科。
   * @param term
   *          资源学期。 null 所有
   * @return
   */

  public abstract Integer getSubmitSum(Set<Integer> grades, Set<Integer> subjects, Integer term);

  @Override
  public int compareTo(CheckRecordService arg0) {
    return this.getOrder().compareTo(arg0.getOrder());
  }

  /**
   * 获取已查阅的资源
   * 
   * @param type
   *          资源类型 ResTypeConstants中获取
   * @param userId
   * @param termId
   * @return
   * @see com.tmser.tr.managerecord.service.CheckRecordInfoService#getCheckInfoListByType(java.lang.Integer,
   *      java.lang.Integer, java.lang.Integer)
   */
  @Override
  public PageList<CheckInfo> getCheckInfoListByType(Integer type, Integer userId, Integer termId, Page page,
      Boolean onlyHasOptions) {
    // 获取当前用户空间
    UserSpace userSpace = (UserSpace) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE);
    Integer phaseId = userSpace.getPhaseId();
    Integer schoolYear = (Integer) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SCHOOLYEAR);// 学年
    Integer term = (Integer) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_TERM); // 学期
    CheckInfo checkInfo = new CheckInfo();
    checkInfo.addPage(page);
    checkInfo.setResType(type);
    checkInfo.setSchoolYear(schoolYear);
    checkInfo.setUserId(userId);
    checkInfo.addOrder(" createtime desc");
    if (termId == null) {
      checkInfo.setTerm(term);
    } else {
      checkInfo.setTerm(termId);
    }
    if (onlyHasOptions) {
      checkInfo.setHasOptinion(true);
    }
    checkInfo.setPhase(phaseId);
    PageList<CheckInfo> checkInfoPageList = checkInfoDao.listPage(checkInfo);
    return checkInfoPageList;
  }

  /**
   * 获取资源的查阅意见
   * 
   * @param jiaoan
   * @param userId
   * @param term
   * @param page
   * @return Map,包含资源和其查阅意见集合
   * @see com.tmser.tr.managerecord.service.CheckRecordInfoService#getCheckOptionMapList(int,
   *      java.lang.Integer, java.lang.Integer, com.tmser.tr.common.page.Page)
   */
  @Override
  public List<Map<String, Object>> getCheckOptionMapList(Integer type, Integer userId, Integer term, Page page) {
    PageList<CheckInfo> checkInfoPageList = getCheckInfoListByType(type, userId, term, page, true);
    List<CheckInfo> checkInfoList = checkInfoPageList.getDatalist();
    List<Map<String, Object>> checkMapList = new ArrayList<Map<String, Object>>();

    CheckOpinion temp1 = new CheckOpinion();
    temp1.setType(0);
    temp1.setIsDelete(false);
    temp1.setIsHidden(false);
    temp1.addOrder(" crtTime desc");
    for (CheckInfo checkInfo : checkInfoList) {
      Map<String, Object> checkMap = new HashMap<String, Object>();
      checkMap.put("checkInfo", checkInfo);
      temp1.setCheckInfoId(checkInfo.getId());
      List<CheckOpinion> parentList = checkOpinionDao.listAll(temp1);
      List<Map<String, Object>> optionMapList = new ArrayList<Map<String, Object>>();

      CheckOpinion temp2 = new CheckOpinion();
      temp2.setType(1);
      temp2.setIsDelete(false);
      temp2.setIsHidden(false);
      temp2.setCheckInfoId(checkInfo.getId());
      temp2.addOrder(" crtTime asc");
      for (CheckOpinion checkOption : parentList) {
        Map<String, Object> optionMap = new HashMap<String, Object>();
        temp2.setParentId(checkOption.getId());
        List<CheckOpinion> childList = checkOpinionDao.listAll(temp2);
        optionMap.put("parent", checkOption);
        optionMap.put("childList", childList);
        optionMapList.add(optionMap);
      }
      checkMap.put("optionMapList", optionMapList);
      checkMapList.add(checkMap);
    }
    return checkMapList;
  }

  /**
   * 获取资源的查阅意见
   * 
   * @param checkInfoPageList
   * @see com.tmser.tr.managerecord.service.CheckRecordInfoService#getCheckOptionMapList(com.tmser.tr.common.page.PageList)
   */
  @Override
  public List<Map<String, Object>> getCheckOptionMapList(PageList<CheckInfo> checkInfoPageList) {
    List<CheckInfo> checkInfoList = checkInfoPageList.getDatalist();
    List<Map<String, Object>> checkMapList = new ArrayList<Map<String, Object>>();
    // Integer phaseId = us.getPhaseId();//学段
    CheckOpinion temp1 = new CheckOpinion();
    temp1.setType(0);
    temp1.setIsDelete(false);
    temp1.setIsHidden(false);
    temp1.setParentId(0);
    temp1.addOrder(" crtTime desc");
    for (CheckInfo checkInfo : checkInfoList) {
      Map<String, Object> checkMap = new HashMap<String, Object>();
      checkMap.put("checkInfo", checkInfo);
      temp1.setCheckInfoId(checkInfo.getId());
      List<CheckOpinion> parentList = checkOpinionDao.listAll(temp1);
      List<Map<String, Object>> optionMapList = new ArrayList<Map<String, Object>>();

      CheckOpinion temp2 = new CheckOpinion();
      // temp2.setParentId(phaseId);
      temp2.setType(1);
      temp2.setIsDelete(false);
      temp2.setIsHidden(false);
      // temp2.setCheckInfoId(checkInfo.getId());
      temp2.addOrder(" crtTime asc");
      for (CheckOpinion checkOption : parentList) {
        Map<String, Object> optionMap = new HashMap<String, Object>();
        temp2.setParentId(checkOption.getId());
        List<CheckOpinion> childList = checkOpinionDao.listAll(temp2);
        optionMap.put("parent", checkOption);
        optionMap.put("childList", childList);
        optionMapList.add(optionMap);
      }
      checkMap.put("optionMapList", optionMapList);
      checkMapList.add(checkMap);
    }
    return checkMapList;
  }

  /**
   * 获取资源的查阅意见数量
   * 
   * @param type
   * @param userId
   * @param term
   * @return Map
   * @see com.tmser.tr.managerecord.service.CheckRecordInfoService#getCheckNumbers(int,
   *      java.lang.Integer, java.lang.Integer)
   */
  @Override
  public Map<String, Integer> getCheckNumbers(int type, Integer userId, Integer term) {
    // 获取当前用户空间
    UserSpace userSpace = (UserSpace) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE);
    Integer phaseId = userSpace.getPhaseId();
    Integer schoolYear = (Integer) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SCHOOLYEAR);// 学年
    Integer nowTerm = (Integer) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_TERM); // 学期
    CheckInfo ci = new CheckInfo();
    ci.setSchoolYear(schoolYear);
    ci.setUserId(userId);
    ci.setResType(type);
    if (term == null) {
      ci.setTerm(nowTerm);
    } else {
      ci.setTerm(term);
    }
    ci.setPhase(phaseId);
    int checkCount = checkInfoDao.count(ci);
    ci.setHasOptinion(true);// 查阅意见
    int yijianCount = checkInfoDao.count(ci);
    Map<String, Integer> countMap = new HashMap<String, Integer>();
    countMap.put("checkCount", checkCount);
    countMap.put("yijianCount", yijianCount);
    return countMap;
  }

}
