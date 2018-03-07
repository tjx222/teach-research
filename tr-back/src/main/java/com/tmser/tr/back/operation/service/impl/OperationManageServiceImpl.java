/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.back.operation.service.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmser.tr.back.operation.service.OperationManageService;
import com.tmser.tr.back.operation.vo.LeaderOperationInfoVo;
import com.tmser.tr.back.operation.vo.OrgOperationInfoVo;
import com.tmser.tr.back.operation.vo.TeacherOperationInfoVo;
import com.tmser.tr.back.operationmanage.dao.OperationManageDao;
import com.tmser.tr.back.operationmanage.vo.SearchVo;
import com.tmser.tr.common.orm.SqlMapping;
import com.tmser.tr.common.page.PageList;
import com.tmser.tr.common.utils.WebThreadLocalUtils;
import com.tmser.tr.manage.org.bo.Organization;
import com.tmser.tr.manage.org.service.OrganizationService;
import com.tmser.tr.uc.SysRole;
import com.tmser.tr.uc.bo.Login;
import com.tmser.tr.uc.bo.UserManagescope;
import com.tmser.tr.uc.bo.UserSpace;
import com.tmser.tr.uc.dao.UserManagescopeDao;
import com.tmser.tr.uc.service.LoginService;
import com.tmser.tr.uc.service.SchoolYearService;
import com.tmser.tr.uc.service.UserSpaceService;
import com.tmser.tr.uc.utils.SessionKey;
import com.tmser.tr.utils.DateUtils;
import com.tmser.tr.utils.StringUtils;

/**
 * <pre>
 * 运营管理service
 * </pre>
 * 
 * @author daweiwbs
 * @version $Id: OperationManageServiceImpl.java, v 1.0 2015年11月4日 下午5:49:18
 *          daweiwbs Exp $
 */
@Service
@Transactional
public class OperationManageServiceImpl implements OperationManageService {
  @Autowired
  private OrganizationService orgService;
  @Autowired
  private OperationManageDao operationManageDao;
  @Autowired
  private UserSpaceService userSpaceService;
  @Autowired
  private UserManagescopeDao userManagescopeDao;
  @Autowired
  private LoginService loginService;
  @Autowired
  private SchoolYearService schoolYearService;

  /**
   * 获取地区下学校运营统计集合
   * 
   * @param search
   * @return
   * @see com.tmser.tr.back.operation.service.OperationManageService#getOrgOperationInfoList(com.tmser.tr.back.operation.vo.SearchVo)
   */
  @Override
  public PageList<OrgOperationInfoVo> getOrgOperationInfoList(SearchVo search) {
    UserSpace us = (UserSpace) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE);
    String scopeOrgIdsStr = null;
    if (us.getSysRoleId().intValue() != SysRole.ADMIN.getId().intValue()) { // 不是超级管理员,有管辖范围时，只显示管辖的学校
      scopeOrgIdsStr = getScopeOrgIdsStr(search, us);
    }
    Organization org = new Organization();
    org.setType(0);
    org.setAreaId(search.getAreaId());
    org.setPhaseTypes(SqlMapping.LIKE_PRFIX + "," + search.getPhaseId() + "," + SqlMapping.LIKE_PRFIX);
    org.addPage(search.getPage());
    if (!StringUtils.isBlank(scopeOrgIdsStr)) {
      org.addCustomCondition(" and id in (" + scopeOrgIdsStr.substring(0, scopeOrgIdsStr.length() - 1) + ")");
    }
    PageList<Organization> orgPageList = orgService.findByPage(org); // 分页获取符合所选学段的学校集合
    List<Organization> orgList = orgPageList.getDatalist();
    search = datatimeForSearch(search);// 处理搜索时间
    List<OrgOperationInfoVo> operationInfoList = new ArrayList<OrgOperationInfoVo>();
    for (Organization o : orgList) {
      OrgOperationInfoVo oper = new OrgOperationInfoVo();
      oper.setOrgId(o.getId());
      oper.setOrgName(o.getName());
      search.setOrgId(o.getId());
      // 查询学校用户数
      Integer userCount = operationManageDao.getUserCount(search);
      oper.setUserCount(userCount);
      // 撰写教案总数
      Integer lessonPlanCount = operationManageDao.getLessonPlanCount(search);
      oper.setLessonPlanCount(lessonPlanCount);
      Integer lessonPlanCountLesson = operationManageDao.getLessonPlanCountLesson(search);
      oper.setLessonPlanCountLesson(lessonPlanCountLesson);

      // 查阅数
      Integer viewCount = operationManageDao.getViewCount(search);
      oper.setViewCount(viewCount);
      // 分享发表数
      Integer shareCount = operationManageDao.getShareCount(search);
      oper.setShareCount(shareCount);
      // 集体备课发起数
      Integer activityPushCount = operationManageDao.getActivityPushCount(search);
      oper.setActivityPushCount(activityPushCount);
      // 集体备课参与次数
      Integer activityJoinCount = operationManageDao.getActivityJoinCount(search);
      oper.setActivityJoinCount(activityJoinCount);
      // 成长档案精品资源数
      Integer progressResCount = operationManageDao.getProgressResCount(search);
      oper.setProgressResCount(progressResCount);
      // 同伴互助留言数
      Integer peerMessageCount = operationManageDao.getPeerMessageCount(search);
      oper.setPeerMessageCount(peerMessageCount);
      // 资源总数
      Integer resTotalCount = operationManageDao.getResTotalCount(search);
      oper.setResTotalCount(resTotalCount);
      // 人均资源数
      Double perResCount = (double) Math.round((double) resTotalCount / (double) (userCount == 0 ? 1 : userCount)
          * 10000) / 10000;
      oper.setPerResCount(perResCount);
      operationInfoList.add(oper);
    }
    return new PageList<OrgOperationInfoVo>(operationInfoList, orgPageList.getPage());
  }

  /**
   * 获取管辖机构的id字符串
   * 
   * @param search
   * @param us
   * @return
   */
  private String getScopeOrgIdsStr(SearchVo search, UserSpace us) {
    StringBuilder scopeOrgIdsStr = new StringBuilder("");
    UserManagescope um = new UserManagescope();
    um.setAreaId(search.getAreaId());
    um.setUserId(us.getUserId());
    um.setRoleId(us.getRoleId());
    um.buildCondition(" and orgId > :defautOrgId").put("defautOrgId", UserManagescope.DEFAULT_ORG_ID);
    List<UserManagescope> umList = userManagescopeDao.listAll(um);
    if (umList != null && umList.size() > 0) {
      for (UserManagescope temp : umList) {
        scopeOrgIdsStr.append(temp.getOrgId()).append(",");
      }
    }
    return scopeOrgIdsStr.toString();
  }

  /**
   * 区域下各项统计数据合计
   * 
   * @param search
   * @return
   * @see com.tmser.tr.back.operation.service.OperationManageService#getTotalCountOfArea(com.tmser.tr.back.vo.SearchVo)
   */
  @Override
  public OrgOperationInfoVo getTotalCountOfArea(SearchVo search) {
    search = datatimeForSearch(search);// 处理搜索时间
    OrgOperationInfoVo oper = new OrgOperationInfoVo();
    oper.setOrgName("合计");
    Integer userTotalCount = operationManageDao.getUserTotalCountOfArea(search);
    oper.setUserCount(userTotalCount);
    Integer lessonPlanTotalCount = operationManageDao.getLessonPlanTotalCountOfArea(search);
    oper.setLessonPlanCount(lessonPlanTotalCount);
    Integer lessonPlanTotalCountLessonOfArea = operationManageDao.getLessonPlanTotalCountLessonOfArea(search);
    oper.setLessonPlanCountLesson(lessonPlanTotalCountLessonOfArea);
    Integer viewTotalCount = operationManageDao.getViewTotalCountOfArea(search);
    oper.setViewCount(viewTotalCount);
    Integer shareTotalCount = operationManageDao.getShareTotalCountOfArea(search);
    oper.setShareCount(shareTotalCount);
    Integer activityPushTotalCount = operationManageDao.getActivityPushTotalCountOfArea(search);
    oper.setActivityPushCount(activityPushTotalCount);
    Integer activityJoinTotalCount = operationManageDao.getActivityJoinTotalCountOfArea(search);
    oper.setActivityJoinCount(activityJoinTotalCount);
    Integer progressResTotalCount = operationManageDao.getProgressResTotalCountOfArea(search);
    oper.setProgressResCount(progressResTotalCount);
    Integer peerMessageTotalCount = operationManageDao.getPeerMessageTotalCountOfArea(search);
    oper.setPeerMessageCount(peerMessageTotalCount);
    Integer resTotalCountOfArea = operationManageDao.getResTotalCountOfArea(search);
    oper.setResTotalCount(resTotalCountOfArea);
    Double perResCountOfArea = (double) Math.round((double) resTotalCountOfArea
        / (double) (userTotalCount == 0 ? 1 : userTotalCount) * 10000) / 10000;
    oper.setPerResCount(perResCountOfArea);
    return oper;
  }

  /**
   * 处理搜索时间, 默认为学期开始至昨天的数据,A在当天0点开始统计，B统计到当天的24点
   * 
   * @param search
   * @return
   */
  private SearchVo datatimeForSearch(SearchVo search) {
    Date startTime = search.getStartTime();
    Date endTime = search.getEndTime();
    if (startTime == null) {
      Integer term = (Integer) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_TERM);// 学期
      startTime = term.intValue() == 0 ? schoolYearService.getUpTermStartTime() : schoolYearService
          .getNextTermStartTime();
    }
    /*
     * else {
     * startTime = DateUtils.parseDate(DateUtils.formatDate(startTime,
     * "yyyy-MM-dd") + " 00:00:00");
     * }
     */
    if (endTime == null) {
      endTime = DateUtils.parseDate(DateUtils.formatDate(new Date(new Date().getTime() - 24 * 60 * 60 * 1000),
          "yyyy-MM-dd") + " 23:59:59");
    }
    /*
     * else {
     * endTime = DateUtils.parseDate(DateUtils.formatDate(endTime, "yyyy-MM-dd")
     * + " 23:59:59");
     * }
     */
    search.setStartTime(startTime);
    search.setEndTime(endTime);
    return search;
  }

  /**
   * 获取学校下老师的运营统计集合
   * 
   * @param search
   * @return
   * @see com.tmser.tr.back.operation.service.OperationManageService#getTeacherOperationInfoList(com.tmser.tr.back.vo.SearchVo)
   */
  @Override
  public PageList<TeacherOperationInfoVo> getTeacherOperationInfoList(SearchVo search) {
    UserSpace userspase = new UserSpace();
    userspase.addCustomCulomn("distinct userId,username");
    userspase.setOrgId(search.getOrgId());
    userspase.setPhaseId(search.getPhaseId());
    userspase.setSysRoleId(SysRole.TEACHER.getId());
    userspase.addPage(search.getPage());
    userspase.setSubjectId(search.getSubjectId());
    userspase.setGradeId(search.getGradeId());
    if (!StringUtils.isBlank(search.getUserName())) {
      userspase.setUsername(SqlMapping.LIKE_PRFIX + search.getUserName() + SqlMapping.LIKE_PRFIX);
    }
    PageList<UserSpace> userSpacePageList = userSpaceService.findByPage(userspase);
    List<UserSpace> userSpaceList = userSpacePageList.getDatalist();
    search = datatimeForSearch(search);// 处理搜索时间
    List<TeacherOperationInfoVo> operationInfoList = new ArrayList<TeacherOperationInfoVo>();
    for (UserSpace space : userSpaceList) {
      Login login = loginService.findOne(space.getUserId());
      TeacherOperationInfoVo oper = new TeacherOperationInfoVo();
      oper.setUserId(space.getUserId());
      oper.setUserName(space.getUsername());
      oper.setStatus(login.getEnable() == 1 ? "未冻结" : "已冻结");
      search.setUserId(space.getUserId());
      Integer lessonPlanCount = operationManageDao.getLessonPlanCount_teacher(search);
      oper.setLessonPlanCount(lessonPlanCount);
      Integer lessonCount = operationManageDao.getLessonCount_teacher(search);
      oper.setLessonCount(lessonCount);
      Integer kejianCount = operationManageDao.getKejianCount_teacher(search);
      oper.setKejianCount(kejianCount);
      Integer kejianLesson = operationManageDao.getKejianLesson_teacher(search);
      oper.setKejianLesson(kejianLesson);
      Integer fansiCount = operationManageDao.getFansiCount_teacher(search);
      oper.setFansiCount(fansiCount);
      Integer fansiLesson = operationManageDao.getFansiLesson_teacher(search);
      oper.setFansiLesson(fansiLesson);
      Integer listenCount = operationManageDao.getListenCount_teacher(search);
      oper.setListenCount(listenCount);
      Integer teachTextCount = operationManageDao.getTeachTextCount_teacher(search);
      oper.setTeachTextCount(teachTextCount);
      Integer planSummaryCount = operationManageDao.getPlanSummaryCount_teacher(search);
      oper.setPlanSummaryCount(planSummaryCount);
      Integer activityJoinCount = operationManageDao.getActivityJoinCount_teacher(search);
      oper.setActivityJoinCount(activityJoinCount);
      Integer activityDiscussCount = operationManageDao.getActivityDiscussCount_teacher(search);
      oper.setActivityDiscussCount(activityDiscussCount);
      Integer activityMainCount = operationManageDao.getActivityMainCount_teacher(search);
      oper.setActivityMainCount(activityMainCount);
      Integer schoolActivityJoinCount = operationManageDao.getSchoolActivityJoinCount_teacher(search);
      oper.setSchoolActivityJoinCount(schoolActivityJoinCount);
      Integer schoolActivityDiscussCount = operationManageDao.getSchoolActivityDiscussCount_teacher(search);
      oper.setSchoolActivityDiscussCount(schoolActivityDiscussCount);
      Integer schoolActivityMainCount = operationManageDao.getSchoolActivityMainCount_teacher(search);
      oper.setSchoolActivityMainCount(schoolActivityMainCount);
      Integer peerMessageCount = operationManageDao.getPeerMessageCount_teacher(search);
      oper.setPeerMessageCount(peerMessageCount);
      Integer progressResCount = operationManageDao.getProgressResCount_teacher(search);
      oper.setProgressResCount(progressResCount);
      Integer shareCount = operationManageDao.getShareCount_teacher(search);
      oper.setShareCount(shareCount);
      oper.setResTotalCount(lessonPlanCount + kejianCount + fansiCount + teachTextCount + listenCount
          + progressResCount + planSummaryCount + activityJoinCount + schoolActivityJoinCount);
      operationInfoList.add(oper);
    }
    return new PageList<TeacherOperationInfoVo>(operationInfoList, userSpacePageList.getPage());
  }

  /**
   * 学校下老师的各项统计数据合计
   * 
   * @param search
   * @return
   * @see com.tmser.tr.back.operation.service.OperationManageService#getTotalCountOfTeacherOfOrg(com.tmser.tr.back.vo.SearchVo)
   */
  @Override
  public TeacherOperationInfoVo getTotalCountOfTeacherOfOrg(SearchVo search) {
    search = datatimeForSearch(search);// 处理搜索时间
    TeacherOperationInfoVo oper = new TeacherOperationInfoVo();
    oper.setUserName("合计");
    oper.setStatus("");
    Integer lessonPlanCount = operationManageDao.getLessonPlanTotalCount_teacher(search);
    oper.setLessonPlanCount(lessonPlanCount);
    Integer lessonTotalCount_teacher = operationManageDao.getLessonTotalCount_teacher(search);
    oper.setLessonCount(lessonTotalCount_teacher);
    Integer kejianCount = operationManageDao.getKejianTotalCount_teacher(search);
    oper.setKejianCount(kejianCount);
    Integer kejianTotalLesson_teacher = operationManageDao.getKejianTotalLesson_teacher(search);
    oper.setKejianLesson(kejianTotalLesson_teacher);
    Integer fansiCount = operationManageDao.getFansiTotalCount_teacher(search);
    oper.setFansiCount(fansiCount);
    Integer fansiTotalLesson_teacher = operationManageDao.getFansiTotalLesson_teacher(search);
    oper.setFansiLesson(fansiTotalLesson_teacher);
    Integer listenCount = operationManageDao.getListenTotalCount_teacher(search);
    oper.setListenCount(listenCount);
    Integer teachTextCount = operationManageDao.getTeachTextTotalCount_teacher(search);
    oper.setTeachTextCount(teachTextCount);
    Integer planSummaryCount = operationManageDao.getPlanSummaryTotalCount_teacher(search);
    oper.setPlanSummaryCount(planSummaryCount);
    Integer activityJoinCount = operationManageDao.getActivityJoinTotalCount_teacher(search);
    oper.setActivityJoinCount(activityJoinCount);
    Integer activityDiscussCount = operationManageDao.getActivityDiscussTotalCount_teacher(search);
    oper.setActivityDiscussCount(activityDiscussCount);
    Integer activityMainCount = operationManageDao.getActivityMainTotalCount_teacher(search);
    oper.setActivityMainCount(activityMainCount);
    Integer schoolActivityJoinCount = operationManageDao.getSchoolActivityJoinTotalCount_teacher(search);
    oper.setSchoolActivityJoinCount(schoolActivityJoinCount);
    Integer schoolActivityDiscussCount = operationManageDao.getSchoolActivityDiscussTotalCount_teacher(search);
    oper.setSchoolActivityDiscussCount(schoolActivityDiscussCount);
    Integer schoolActivityMainCount = operationManageDao.getSchoolActivityMainTotalCount_teacher(search);
    oper.setSchoolActivityMainCount(schoolActivityMainCount);
    Integer peerMessageCount = operationManageDao.getPeerMessageTotalCount_teacher(search);
    oper.setPeerMessageCount(peerMessageCount);
    Integer progressResCount = operationManageDao.getProgressResTotalCount_teacher(search);
    oper.setProgressResCount(progressResCount);
    Integer shareCount = operationManageDao.getShareTotalCount_teacher(search);
    oper.setShareCount(shareCount);
    oper.setResTotalCount(lessonPlanCount + kejianCount + fansiCount + teachTextCount + listenCount + progressResCount
        + planSummaryCount + activityJoinCount + schoolActivityJoinCount);
    return oper;
  }

  /**
   * 获取学校下管理人员的运营情况集合
   * 
   * @param search
   * @return
   * @see com.tmser.tr.back.operation.service.OperationManageService#toLeaderOperationInfoList(com.tmser.tr.back.vo.SearchVo)
   */
  @Override
  public PageList<LeaderOperationInfoVo> toLeaderOperationInfoList(SearchVo search) {
    UserSpace userspase = new UserSpace();
    userspase.addCustomCulomn("distinct userId,username");
    userspase.setOrgId(search.getOrgId());
    userspase.setPhaseId(search.getPhaseId());
    Map<String, Object> paramMap = new HashMap<String, Object>();
    paramMap.put("teacher", SysRole.TEACHER.getId());
    userspase.addCustomCondition(" and sysRoleId <> :teacher", paramMap);
    userspase.setSysRoleId(search.getRoleId());
    userspase.setGradeId(search.getGradeId());
    userspase.setSubjectId(search.getSubjectId());
    if (!StringUtils.isBlank(search.getUserName())) {
      userspase.setUsername(SqlMapping.LIKE_PRFIX + search.getUserName() + SqlMapping.LIKE_PRFIX);
    }
    userspase.addPage(search.getPage());
    PageList<UserSpace> userSpacePageList = userSpaceService.findByPage(userspase);
    List<UserSpace> userSpaceList = userSpacePageList.getDatalist();
    search = datatimeForSearch(search);// 处理搜索时间
    List<LeaderOperationInfoVo> operationInfoList = new ArrayList<LeaderOperationInfoVo>();
    for (UserSpace space : userSpaceList) {
      Login login = loginService.findOne(space.getUserId());
      LeaderOperationInfoVo oper = new LeaderOperationInfoVo();
      oper.setUserId(space.getUserId());
      oper.setUserName(space.getUsername());
      oper.setStatus(login.getEnable() == 1 ? "未冻结" : "已冻结");
      search.setUserId(space.getUserId());
      Integer viewLessonPlanCount = operationManageDao.getViewLessonPlanCount_leader(search);
      oper.setViewLessonPlanCount(viewLessonPlanCount);
      Integer viewKejianCount = operationManageDao.getViewKejianCount_leader(search);
      oper.setViewKejianCount(viewKejianCount);
      Integer viewFansiCount = operationManageDao.getViewFansiCount_leader(search);
      oper.setViewFansiCount(viewFansiCount);
      Integer viewPlanSummaryCount = operationManageDao.getViewPlanSummaryCount_leader(search);
      oper.setViewPlanSummaryCount(viewPlanSummaryCount);
      Integer listenCount = operationManageDao.getListenCount_leader(search);
      oper.setListenCount(listenCount);
      Integer teachTextCount = operationManageDao.getTeachTextCount_leader(search);
      oper.setTeachTextCount(teachTextCount);
      Integer planSummaryCount = operationManageDao.getPlanSummaryCount_leader(search);
      oper.setPlanSummaryCount(planSummaryCount);
      Integer activityPushCount = operationManageDao.getActivityPushCount_leader(search);
      oper.setActivityPushCount(activityPushCount);
      Integer activityJoinCount = operationManageDao.getActivityJoinCount_leader(search);
      oper.setActivityJoinCount(activityJoinCount);
      Integer activityDiscussCount = operationManageDao.getActivityDiscussCount_leader(search);
      oper.setActivityDiscussCount(activityDiscussCount);
      Integer activityViewCount = operationManageDao.getActivityViewCount_leader(search);
      oper.setActivityViewCount(activityViewCount);
      Integer schoolActivityPushCount = operationManageDao.getSchoolActivityPushCount_leader(search);
      oper.setSchoolActivityPushCount(schoolActivityPushCount);
      Integer schoolActivityJoinCount = operationManageDao.getSchoolActivityJoinCount_leader(search);
      oper.setSchoolActivityJoinCount(schoolActivityJoinCount);
      Integer schoolActivityDiscussCount = operationManageDao.getSchoolActivityDiscussCount_leader(search);
      oper.setSchoolActivityDiscussCount(schoolActivityDiscussCount);
      Integer peerMessageCount = operationManageDao.getPeerMessageCount_leader(search);
      oper.setPeerMessageCount(peerMessageCount);
      Integer shareCount = operationManageDao.getShareCount_leader(search);
      oper.setShareCount(shareCount);
      oper.setResTotalCount(teachTextCount + listenCount + planSummaryCount + activityPushCount + activityJoinCount
          + schoolActivityPushCount + schoolActivityJoinCount);
      operationInfoList.add(oper);
    }
    return new PageList<LeaderOperationInfoVo>(operationInfoList, userSpacePageList.getPage());
  }

  /**
   * 学校下管理者的各项统计数据合计
   * 
   * @param search
   * @return
   * @see com.tmser.tr.back.operation.service.OperationManageService#getTotalCountOfLeaderOfOrg(com.tmser.tr.back.vo.SearchVo)
   */
  @Override
  public LeaderOperationInfoVo getTotalCountOfLeaderOfOrg(SearchVo search) {
    search = datatimeForSearch(search);// 处理搜索时间
    LeaderOperationInfoVo oper = new LeaderOperationInfoVo();
    oper.setUserName("合计");
    oper.setStatus("");
    Integer viewLessonPlanCount = operationManageDao.getViewLessonPlanTotalCount_leader(search);
    oper.setViewLessonPlanCount(viewLessonPlanCount);
    Integer viewKejianCount = operationManageDao.getViewKejianTotalCount_leader(search);
    oper.setViewKejianCount(viewKejianCount);
    Integer viewFansiCount = operationManageDao.getViewFansiTotalCount_leader(search);
    oper.setViewFansiCount(viewFansiCount);
    Integer viewPlanSummaryCount = operationManageDao.getViewPlanSummaryTotalCount_leader(search);
    oper.setViewPlanSummaryCount(viewPlanSummaryCount);
    Integer listenCount = operationManageDao.getListenTotalCount_leader(search);
    oper.setListenCount(listenCount);
    Integer teachTextCount = operationManageDao.getTeachTextTotalCount_leader(search);
    oper.setTeachTextCount(teachTextCount);
    Integer planSummaryCount = operationManageDao.getPlanSummaryTotalCount_leader(search);
    oper.setPlanSummaryCount(planSummaryCount);
    Integer activityPushCount = operationManageDao.getActivityPushTotalCount_leader(search);
    oper.setActivityPushCount(activityPushCount);
    Integer activityJoinCount = operationManageDao.getActivityJoinTotalCount_leader(search);
    oper.setActivityJoinCount(activityJoinCount);
    Integer activityDiscussCount = operationManageDao.getActivityDiscussTotalCount_leader(search);
    oper.setActivityDiscussCount(activityDiscussCount);
    Integer activityViewCount = operationManageDao.getActivityViewTotalCount_leader(search);
    oper.setActivityViewCount(activityViewCount);
    Integer schoolActivityPushCount = operationManageDao.getSchoolActivityPushTotalCount_leader(search);
    oper.setSchoolActivityPushCount(schoolActivityPushCount);
    Integer schoolActivityJoinCount = operationManageDao.getSchoolActivityJoinTotalCount_leader(search);
    oper.setSchoolActivityJoinCount(schoolActivityJoinCount);
    Integer schoolActivityDiscussCount = operationManageDao.getSchoolActivityDiscussTotalCount_leader(search);
    oper.setSchoolActivityDiscussCount(schoolActivityDiscussCount);
    Integer peerMessageCount = operationManageDao.getPeerMessageTotalCount_leader(search);
    oper.setPeerMessageCount(peerMessageCount);
    Integer shareCount = operationManageDao.getShareTotalCount_leader(search);
    oper.setShareCount(shareCount);
    oper.setResTotalCount(teachTextCount + listenCount + planSummaryCount + activityPushCount + activityJoinCount
        + schoolActivityPushCount + schoolActivityJoinCount);
    return oper;
  }

  /**
   * 导出到excel
   * 
   * @param headers
   * @param propertyNames
   * @param orginfoList
   * @param title
   * @see com.tmser.tr.back.operation.service.OperationManageService#exportToExcel(java.lang.String[],
   *      java.lang.String[], java.util.List, java.lang.String)
   */
  @Override
  public <T> void exportToExcel(String[] headers, String[] propertyNames, List<T> objectList, String title,
      HttpServletResponse response) {
    HSSFWorkbook workbook = new HSSFWorkbook();
    createSheet(workbook, headers, propertyNames, objectList, title);
    try {
      response.reset();
      response.setContentType("application/x-msdownload");
      response.setHeader("Content-Disposition", "attachment; filename="
          + new String(title.getBytes("gb2312"), "ISO-8859-1") + ".xls");
      ServletOutputStream out = response.getOutputStream();
      workbook.write(out);
      out.flush();
      out.close();
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  /**
   * 生成sheet页并将数据导入
   * 
   * @param workbook
   * @param headers
   * @param propertyNames
   * @param objectList
   * @param title
   */
  private <T> void createSheet(HSSFWorkbook workbook, String[] headers, String[] propertyNames, List<T> objectList,
      String title) {
    HSSFSheet sheet = workbook.createSheet(title);
    // 设置表格默认列宽度为20个字节
    sheet.setDefaultColumnWidth(20);
    // 生成一个样式
    HSSFCellStyle style = workbook.createCellStyle();
    // 设置样式
    style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
    style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
    style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
    style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
    style.setBorderRight(HSSFCellStyle.BORDER_THIN);
    style.setBorderTop(HSSFCellStyle.BORDER_THIN);
    style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
    // 生成一个字体
    HSSFFont font = workbook.createFont();
    font.setColor(HSSFColor.VIOLET.index);
    font.setFontHeightInPoints((short) 12);
    font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
    // 把字体应用到当前的样式
    style.setFont(font);
    HSSFRow row = sheet.createRow(0);
    // 设置标题行
    for (int i = 0; i < headers.length; i++) {
      HSSFCell cell = row.createCell(i);
      cell.setCellStyle(style);
      cell.setCellValue(new HSSFRichTextString(headers[i]));
    }
    // 设置数据行、
    int index = 0;
    for (int i = 0; i < objectList.size(); i++) {
      index = i + 1;
      row = sheet.createRow(index);
      T t = objectList.get(i);
      for (int j = 0; j < propertyNames.length; j++) {
        HSSFCell cell = row.createCell(j);
        String fieldName = propertyNames[j];
        String getMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
        Class<?> clazz = t.getClass();
        try {
          Method m = clazz.getMethod(getMethodName);
          Object value = m.invoke(t);
          cell.setCellValue(new HSSFRichTextString(String.valueOf(value)));
        } catch (NoSuchMethodException e) {
          e.printStackTrace();
        } catch (SecurityException e) {
          e.printStackTrace();
        } catch (IllegalAccessException e) {
          e.printStackTrace();
        } catch (IllegalArgumentException e) {
          e.printStackTrace();
        } catch (InvocationTargetException e) {
          e.printStackTrace();
        }
      }
    }
  }

  /**
   * 导出学校下老师和管理者的运营情况
   * 
   * @param headers1
   * @param propertyNames1
   * @param teacherInfoList
   * @param headers2
   * @param propertyNames2
   * @param leaderInfoList
   * @param response
   * @see com.tmser.tr.back.operation.service.OperationManageService#exportUserOperationToExcel(java.lang.String[],
   *      java.lang.String[], java.util.List, java.lang.String[],
   *      java.lang.String[], java.util.List,
   *      javax.servlet.http.HttpServletResponse)
   */
  @Override
  public void exportUserOperationToExcel(String[] headers1, String[] propertyNames1,
      List<TeacherOperationInfoVo> teacherInfoList, String[] headers2, String[] propertyNames2,
      List<LeaderOperationInfoVo> leaderInfoList, String orgName, HttpServletResponse response) {
    HSSFWorkbook workbook = new HSSFWorkbook();
    createSheet(workbook, headers1, propertyNames1, teacherInfoList, orgName + "教师教研情况");
    createSheet(workbook, headers2, propertyNames2, leaderInfoList, orgName + "教学管理情况");
    try {
      response.reset();
      response.setContentType("application/x-msdownload");
      response.setHeader("Content-Disposition",
          "attachment; filename=" + new String((orgName + "运营情况一览").getBytes("gb2312"), "ISO-8859-1") + ".xls");
      ServletOutputStream out = response.getOutputStream();
      workbook.write(out);
      out.flush();
      out.close();
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

}
