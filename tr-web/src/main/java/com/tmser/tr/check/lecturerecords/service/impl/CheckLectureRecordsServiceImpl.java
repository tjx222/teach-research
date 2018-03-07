/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.check.lecturerecords.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.tmser.tr.check.bo.CheckInfo;
import com.tmser.tr.check.bo.CheckOpinion;
import com.tmser.tr.check.lecturerecords.service.CheckLectureRecordsService;
import com.tmser.tr.check.service.CheckInfoService;
import com.tmser.tr.check.service.CheckOpinionService;
import com.tmser.tr.common.ResTypeConstants;
import com.tmser.tr.common.bo.QueryObject;
import com.tmser.tr.common.utils.WebThreadLocalUtils;
import com.tmser.tr.lecturerecords.bo.LectureRecords;
import com.tmser.tr.lecturerecords.service.LectureRecordsService;
import com.tmser.tr.manage.meta.Meta;
import com.tmser.tr.manage.meta.MetaUtils;
import com.tmser.tr.manage.org.bo.Organization;
import com.tmser.tr.manage.org.service.OrganizationService;
import com.tmser.tr.uc.SysRole;
import com.tmser.tr.uc.bo.UserSpace;
import com.tmser.tr.uc.service.UserSpaceService;
import com.tmser.tr.uc.utils.SessionKey;
import com.tmser.tr.utils.StringUtils;

/**
 * <pre>
 * 查阅听课记录service
 * </pre>
 *
 * @author wangdawei
 * @version $Id: CheckLectureRecordsServiceImpl.java, v 1.0 2016年8月18日
 *          上午10:03:13 wangdawei Exp $
 */
@Service
@Transactional
public class CheckLectureRecordsServiceImpl implements CheckLectureRecordsService {

  @Autowired
  private OrganizationService orgService;
  @Autowired
  private UserSpaceService userSpaceService;
  @Autowired
  private LectureRecordsService lectureRecordsService;
  @Autowired
  private CheckInfoService checkInfoService;
  @Autowired
  private CheckOpinionService checkOpinionService;

  /**
   * 获取当前学段下的年级
   * 
   * @return
   * @author wangdawei
   */
  @Override
  public List<Map<String, String>> getGradeList() {
    UserSpace userSpace = (UserSpace) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE); // 用户空间
    Organization org = orgService.findOne(userSpace.getOrgId());
    List<Meta> listAllGrade = MetaUtils.getOrgTypeMetaProvider().listAllGrade(org.getSchoolings(),
        userSpace.getPhaseId());

    List<Map<String, String>> gradeList = new ArrayList<Map<String, String>>();
    if (userSpace.getGradeId().intValue() != 0) { // 只显示当前身份的年级
      for (Meta meta : listAllGrade) {
        Map<String, String> gradeMap = new HashMap<String, String>();
        if (meta.getId().intValue() == userSpace.getGradeId().intValue()) {
          gradeMap.put("id", meta.getId().toString());
          gradeMap.put("name", meta.getName());
          gradeList.add(gradeMap);
          break;
        }
      }
    } else {// 显示全部年级
      for (Meta meta : listAllGrade) {
        Map<String, String> gradeMap = new HashMap<String, String>();
        gradeMap.put("id", meta.getId().toString());
        gradeMap.put("name", meta.getName());
        gradeList.add(gradeMap);
      }
    }
    return gradeList;
  }

  /**
   * 获取当前学段下的学科
   * 
   * @return
   * @author wangdawei
   */
  @Override
    public List<Map<String,String>> getSubjectList(){
    	UserSpace userSpace = (UserSpace)WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE); //用户空间
      Organization org = orgService.findOne(userSpace.getOrgId());
      Integer[] areaIds = StringUtils.toIntegerArray(org.getAreaIds().substring(1, org.getAreaIds().lastIndexOf(",")), ",");
    	List<Meta> listAllSubjectByPhaseId = MetaUtils.getPhaseSubjectMetaProvider().listAllSubject(org.getId(), userSpace.getPhaseId(), areaIds);
    	List<Map<String,String>> subjectList = new ArrayList<Map<String,String>>();
    	if(userSpace.getSubjectId().intValue()!=0){
    		for(Meta meta : listAllSubjectByPhaseId){
        		Map<String,String> subjectMap = new HashMap<String,String>();
        		if(meta.getId().intValue() == userSpace.getSubjectId().intValue()){
        			subjectMap.put("id", meta.getId().toString());
            		subjectMap.put("name", meta.getName());
            		subjectList.add(subjectMap);
            		break;
        		}
        	}
    	}else{
    		for(Meta meta : listAllSubjectByPhaseId){
        		Map<String,String> subjectMap = new HashMap<String,String>();
        			subjectMap.put("id", meta.getId().toString());
            		subjectMap.put("name", meta.getName());
            		subjectList.add(subjectMap);
        	}
    	}
    	return subjectList;
    }

  /**
   * 获取可查阅教师信息的集合
   * 
   * @param lr
   * @param m
   * @return
   * @see com.tmser.tr.check.lecturerecords.service.CheckLectureRecordsService#getTeacherMapList(com.tmser.tr.lecturerecords.bo.LectureRecords,
   *      org.springframework.ui.Model)
   */
  @Override
  public List<Map<String, Object>> getTeacherMapList(LectureRecords lr, Model m) {
    UserSpace userSpace = (UserSpace) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE); // 用户空间
    Organization org = orgService.findOne(userSpace.getOrgId());
    Integer schoolYear = (Integer) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SCHOOLYEAR);
    UserSpace us = new UserSpace();
    us.setOrgId(org.getId());
    us.setSubjectId(lr.getSubjectId());
    us.setGradeId(lr.getGradeId());
    us.setSchoolYear(schoolYear);
    us.setPhaseId(userSpace.getPhaseId());
    us.setSysRoleId(SysRole.TEACHER.getId());
    us.setEnable(1);
    List<UserSpace> usList = userSpaceService.findAll(us);
    Map<String, Object> teacherMap = null;
    List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
    Integer totalWriteCount = 0;
    Integer totalSubmitCount = 0;
    Integer totalScanCount = 0;
    for (UserSpace temp : usList) {
      teacherMap = new HashMap<String, Object>();
      teacherMap.put("userSpace", temp);
      LectureRecords record = new LectureRecords();
      record.setLecturepeopleId(temp.getUserId());
      record.setSchoolYear(schoolYear);
      record.setTerm(lr.getTerm());
      record.setIsDelete(false);
      record.setIsEpub(1);
      Integer writeCount = lectureRecordsService.count(record);
      teacherMap.put("writeCount", writeCount);
      totalWriteCount = totalWriteCount + writeCount;
      record.setIsSubmit(1);
      Integer submitCount = lectureRecordsService.count(record);
      teacherMap.put("submitCount", submitCount);
      totalSubmitCount = totalSubmitCount + submitCount;
      CheckInfo ci = new CheckInfo();
      Map<String, Object> paramMap = new HashMap<String, Object>();
      paramMap.put("term", lr.getTerm());
      ci.addAlias("a");
      ci.addCustomCulomn(" distinct a.resId ");
      ci.setResType(ResTypeConstants.LECTURE);
      ci.setUserId(userSpace.getUserId());
      ci.setAuthorId(temp.getUserId());
      ci.setSchoolYear(schoolYear);
      ci.addJoin(QueryObject.JOINTYPE.INNER, "LectureRecords b").on("a.resId=b.id and b.term=:term ");
      ci.addCustomCondition("", paramMap);
      Integer scanCount = checkInfoService.count(ci);
      teacherMap.put("scanCount", scanCount);
      totalScanCount = totalScanCount + scanCount;
      mapList.add(teacherMap);
    }
    m.addAttribute("totalWriteCount", totalWriteCount);
    m.addAttribute("totalSubmitCount", totalSubmitCount);
    m.addAttribute("totalScanCount", totalScanCount);
    m.addAttribute("teacherCount", usList.size());
    return mapList;
  }

  /**
   * 获取教师的听课记录信息
   * 
   * @param lr
   * @return
   * @see com.tmser.tr.check.lecturerecords.service.CheckLectureRecordsService#getTeacherDataMap(com.tmser.tr.lecturerecords.bo.LectureRecords)
   */
  @Override
  public Map<String, Object> getLectureRecordsDataMap(LectureRecords lr) {
    UserSpace userSpace = (UserSpace) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE); // 用户空间
    Integer schoolYear = (Integer) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SCHOOLYEAR);
    Map<String, Object> dataMap = new HashMap<String, Object>();
    LectureRecords record = new LectureRecords();
    record.setLecturepeopleId(lr.getLecturepeopleId());
    record.setSchoolYear(schoolYear);
    record.setTerm(lr.getTerm());
    record.setIsDelete(false);
    record.setIsEpub(1);
    record.addOrder(" isScan asc,submitTime desc");
    dataMap.put("writeCount", lectureRecordsService.count(record));
    record.setIsSubmit(1);
    List<LectureRecords> recordsList = lectureRecordsService.findAll(record);
    dataMap.put("recordsList", recordsList);
    dataMap.put("submitCount", recordsList.size());
    CheckInfo info = null;
    Integer scanCount = 0;
    // 循环遍历 是否被当前管理者查阅过
    for (LectureRecords temp : recordsList) {
      if (temp.getIsScan() != null && temp.getIsScan() == 1) {
        info = new CheckInfo();
        info.setResId(temp.getId());
        info.setUserId(userSpace.getUserId());
        info.setSchoolYear(schoolYear);
        Integer count = checkInfoService.count(info);
        if (count != null && count > 0) {
          scanCount++;
        } else {
          temp.setIsScan(0);
        }
      }
    }
    dataMap.put("scanCount", scanCount);
    return dataMap;
  }

  /**
   * 获取当前页的听课记录
   * 
   * @param lr
   * @return
   * @see com.tmser.tr.check.lecturerecords.service.CheckLectureRecordsService#getLectureRecordsByNum(com.tmser.tr.lecturerecords.bo.LectureRecords)
   */
  @Override
  public LectureRecords getLectureRecordsByNum(LectureRecords lr, Model m) {
    Integer schoolYear = (Integer) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SCHOOLYEAR);
    LectureRecords record = new LectureRecords();
    record.setLecturepeopleId(lr.getLecturepeopleId());
    record.setSchoolYear(schoolYear);
    record.setTerm(lr.getTerm());
    record.setIsDelete(false);
    record.setIsEpub(1);
    record.addOrder(" isScan asc,submitTime desc");
    // m.addAttribute("writeCount",lectureRecordsService.count(record));
    record.setIsSubmit(1);
    List<LectureRecords> recordsList = lectureRecordsService.findAll(record);
    m.addAttribute("submitCount", recordsList.size());
    lr = recordsList.get(Integer.valueOf(lr.getFlags()));
    // record.setIsScan(1);
    // m.addAttribute("scanCount", lectureRecordsService.count(record));
    return lr;
  }

  /**
   * 获取该课题的其他老师的听课记录
   * 
   * @param lectureRecords
   * @return
   * @see com.tmser.tr.check.lecturerecords.service.CheckLectureRecordsService#getOtherLectureRecordsByLessonId(com.tmser.tr.lecturerecords.bo.LectureRecords)
   */
  @Override
  public List<LectureRecords> getOtherLectureRecordsByLessonId(LectureRecords lectureRecords) {
    UserSpace userSpace = (UserSpace) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE); // 用户空间
    Integer schoolYear = (Integer) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SCHOOLYEAR);
    LectureRecords lr = new LectureRecords();
    lr.addAlias("a");
    lr.setOrgId(userSpace.getOrgId());
    lr.setSchoolYear(schoolYear);
    lr.setTerm(lectureRecords.getTerm());
    lr.setType(0);
    lr.setTopic(lectureRecords.getTopic());
    lr.setIsDelete(false);
    lr.setIsEpub(1);
    lr.setIsSubmit(1);
    Map<String, Object> paramMap = new HashMap<String, Object>();
    paramMap.put("orgId", userSpace.getOrgId());
    paramMap.put("schoolYear", schoolYear);
    paramMap.put("enable", 1);
    paramMap.put("checkUserId", lectureRecords.getLecturepeopleId());
    if (ifGroupLeader()) { // 组长
      paramMap.put("sysRoleId", SysRole.TEACHER.getId());
      lr.addCustomCondition(
          " and a.lecturepeopleId != :checkUserId and a.lecturepeopleId in (select distinct b.userId from UserSpace b where b.orgId = :orgId and b.schoolYear = :schoolYear and b.enable = :enable and b.sysRoleId = :sysRoleId) ",
          paramMap);
    } else {
      String temp = "";
      if (userSpace.getSysRoleId().intValue() == SysRole.ZR.getId().intValue()) {
        temp = SysRole.XKZZ.getId() + "," + SysRole.NJZZ.getId() + "," + SysRole.BKZZ.getId() + ","
            + SysRole.TEACHER.getId();
      } else {
        temp = SysRole.ZR.getId() + "," + SysRole.XKZZ.getId() + "," + SysRole.NJZZ.getId() + ","
            + SysRole.BKZZ.getId() + "," + SysRole.TEACHER.getId();
      }
      lr.addCustomCondition(
          " and a.lecturepeopleId != :checkUserId and a.lecturepeopleId in (select distinct b.userId from UserSpace b where b.orgId = :orgId and b.schoolYear = :schoolYear and b.enable = :enable and b.sysRoleId in ("
              + temp + ") )", paramMap);
    }
    List<LectureRecords> tempList = lectureRecordsService.findAll(lr);
    // List<LectureRecords> recordsList = new ArrayList<LectureRecords>();
    // for(LectureRecords temp:tempList){
    // if(temp.getLecturepeopleId().intValue()!=lectureRecords.getLecturepeopleId().intValue()){//听课人不为当前查阅的人
    // if(ifGroupLeader()){ //组长
    // if(ifHaveTeacherSpace(temp.getLecturepeopleId(),schoolYear)){//有教师身份
    // recordsList.add(temp);
    // }
    // }else{ //非组长，即校长和主任
    // if(ifHaveUnderCurrentRole(userSpace.getSysRoleId())){//如果身份中有低于查阅者身份的身份
    // recordsList.add(temp);
    // }
    // }
    // }
    // }
    return tempList;
  }

  /**
   * 查阅听课记录
   * 
   * @param checkInfo
   * @param content
   * @see com.tmser.tr.check.lecturerecords.service.CheckLectureRecordsService#checkLectureRecords(com.tmser.tr.check.bo.CheckInfo,
   *      java.lang.String)
   */
  @Override
  public void checkLectureRecords(CheckInfo checkInfo, String content) {
    UserSpace userSpace = (UserSpace) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE); // 用户空间
    LectureRecords lr = lectureRecordsService.findOne(checkInfo.getResId());
    CheckInfo ci = new CheckInfo();
    ci.setResId(checkInfo.getResId());
    ci.setResType(ResTypeConstants.LECTURE);
    ci.setUserId(userSpace.getUserId());
    ci = checkInfoService.findOne(ci);
    if (ci == null) { // 未被查阅
      // 保存查阅记录
      Integer schoolYear = (Integer) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SCHOOLYEAR);
      Integer term = (Integer) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_TERM);
      checkInfo.setTitle(lr.getTopic());
      checkInfo.setAuthor(lr.getLecturePeople());
      checkInfo.setAuthorId(lr.getLecturepeopleId());
      checkInfo.setUserId(userSpace.getUserId());
      checkInfo.setUsername(userSpace.getUsername());
      checkInfo.setIsUpdate(false);
      checkInfo.setCreatetime(new Date());
      checkInfo.setSchoolYear(schoolYear);
      checkInfo.setSpaceId(userSpace.getId());
      checkInfo.setPhase(userSpace.getPhaseId());
      checkInfo.setTerm(term);
      if (StringUtils.isBlank(content)) {
        checkInfo.setHasOptinion(false);
      } else {
        checkInfo.setHasOptinion(true);
      }
      checkInfo = checkInfoService.save(checkInfo);
      // 保存查阅意见记录
      CheckOpinion co = new CheckOpinion();
      if (StringUtils.isBlank(content)) {
        co.setContent("已查阅");
      } else {
        co.setContent(content);
      }
      co.setAuthorId(lr.getLecturepeopleId());
      co.setCheckInfoId(checkInfo.getId());
      co.setType(0);
      co.setUserId(userSpace.getUserId());
      co.setUsername(userSpace.getUsername());
      co.setIsDelete(false);
      co.setIsHidden(false);
      co.setCrtTime(new Date());
      co.setResId(checkInfo.getResId());
      co.setResType(ResTypeConstants.LECTURE);
      co.setOpinionId(0);
      co.setSpaceId(userSpace.getId());
      checkOpinionService.save(co);
      // 更新听课记录为已查阅
      if (lr.getIsScan() != null && lr.getIsScan() == 0) {
        lr.setIsScan(1);
        lectureRecordsService.update(lr);
      }
    } else {
      ci.setLevel(checkInfo.getLevel());
      if (StringUtils.isNotBlank(content)) {
        // 保存查阅意见记录
        CheckOpinion co = new CheckOpinion();
        co.setContent(content);
        co.setAuthorId(lr.getLecturepeopleId());
        co.setCheckInfoId(checkInfo.getId());
        co.setType(0);
        co.setUserId(userSpace.getUserId());
        co.setUsername(userSpace.getUsername());
        co.setIsDelete(false);
        co.setIsHidden(false);
        co.setCrtTime(new Date());
        co.setResId(checkInfo.getResId());
        co.setResType(ResTypeConstants.LECTURE);
        co.setOpinionId(0);
        co.setSpaceId(userSpace.getId());
        checkOpinionService.save(co);
        ci.setHasOptinion(true);
        ci.setIsUpdate(true);
      }
      checkInfoService.update(ci);
    }

  }

  /**
   * 获取可查阅管理者的信息集合
   * 
   * @param sysRoleId
   * @param lr
   * @param m
   * @return
   * @see com.tmser.tr.check.lecturerecords.service.CheckLectureRecordsService#getLeaderMapList(java.lang.Integer,
   *      com.tmser.tr.lecturerecords.bo.LectureRecords,
   *      org.springframework.ui.Model)
   */
  @Override
  public List<Map<String, Object>> getLeaderMapList(Integer sysRoleId, LectureRecords lr, Model m) {
    UserSpace userSpace = (UserSpace) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE); // 用户空间
    Organization org = orgService.findOne(userSpace.getOrgId());
    Integer schoolYear = (Integer) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SCHOOLYEAR);
    UserSpace us = new UserSpace();
    us.setOrgId(org.getId());
    if (sysRoleId.intValue() == SysRole.BKZZ.getId().intValue()) {
      us.setGradeId(lr.getGradeId());
    }
    us.setSchoolYear(schoolYear);
    us.setPhaseId(userSpace.getPhaseId());
    us.setSysRoleId(sysRoleId);
    us.setEnable(1);
    List<UserSpace> usList = userSpaceService.findAll(us);
    Map<String, Object> leaderMap = null;
    List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
    Integer totalWriteCount = 0;
    Integer totalSubmitCount = 0;
    Integer totalScanCount = 0;
    for (UserSpace temp : usList) {
      leaderMap = new HashMap<String, Object>();
      leaderMap.put("userSpace", temp);
      LectureRecords record = new LectureRecords();
      record.setLecturepeopleId(temp.getUserId());
      record.setSchoolYear(schoolYear);
      record.setTerm(lr.getTerm());
      record.setIsDelete(false);
      record.setIsEpub(1);
      Integer writeCount = lectureRecordsService.count(record);
      leaderMap.put("writeCount", writeCount);
      totalWriteCount = totalWriteCount + writeCount;
      record.setIsSubmit(1);
      Integer submitCount = lectureRecordsService.count(record);
      leaderMap.put("submitCount", submitCount);
      totalSubmitCount = totalSubmitCount + submitCount;
      CheckInfo ci = new CheckInfo();
      Map<String, Object> paramMap = new HashMap<String, Object>();
      paramMap.put("term", lr.getTerm());
      ci.addAlias("a");
      ci.addCustomCulomn(" distinct a.resId ");
      ci.setResType(ResTypeConstants.LECTURE);
      ci.setUserId(userSpace.getUserId());
      ci.setAuthorId(temp.getUserId());
      ci.setSchoolYear(schoolYear);
      ci.addJoin(QueryObject.JOINTYPE.INNER, "LectureRecords b").on("a.resId=b.id and b.term=:term ");
      ci.addCustomCondition("", paramMap);
      Integer scanCount = checkInfoService.count(ci);
      leaderMap.put("scanCount", scanCount);
      totalScanCount = totalScanCount + scanCount;
      mapList.add(leaderMap);
    }
    m.addAttribute("totalWriteCount", totalWriteCount);
    m.addAttribute("totalSubmitCount", totalSubmitCount);
    m.addAttribute("totalScanCount", totalScanCount);
    m.addAttribute("leaderCount", usList.size());
    return mapList;
  }

  /**
   * 判断是否为组长
   * 
   * @return
   */
  private boolean ifGroupLeader() {
    UserSpace userSpace = (UserSpace) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE);
    Integer roleId = userSpace.getSysRoleId();
    if (roleId.intValue() == SysRole.NJZZ.getId().intValue() || roleId.intValue() == SysRole.XKZZ.getId().intValue()
        || roleId.intValue() == SysRole.BKZZ.getId().intValue()) {
      return true;
    }
    return false;
  }

}
