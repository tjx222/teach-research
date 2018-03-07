/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.activity.service.impl;

import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.tmser.tr.activity.bo.Activity;
import com.tmser.tr.activity.bo.ActivityTracks;
import com.tmser.tr.activity.dao.ActivityDao;
import com.tmser.tr.activity.dao.ActivityTracksDao;
import com.tmser.tr.activity.service.ActivityService;
import com.tmser.tr.activity.service.ActivityTracksService;
import com.tmser.tr.check.bo.CheckInfo;
import com.tmser.tr.check.bo.CheckOpinion;
import com.tmser.tr.check.dao.CheckInfoDao;
import com.tmser.tr.check.dao.CheckOpinionDao;
import com.tmser.tr.comment.bo.Discuss;
import com.tmser.tr.comment.dao.DiscussDao;
import com.tmser.tr.common.ResTypeConstants;
import com.tmser.tr.common.dao.BaseDAO;
import com.tmser.tr.common.orm.SqlMapping;
import com.tmser.tr.common.page.PageList;
import com.tmser.tr.common.service.AbstractService;
import com.tmser.tr.common.utils.WebThreadLocalUtils;
import com.tmser.tr.lessonplan.bo.LessonInfo;
import com.tmser.tr.lessonplan.bo.LessonPlan;
import com.tmser.tr.lessonplan.dao.LessonInfoDao;
import com.tmser.tr.manage.meta.Meta;
import com.tmser.tr.manage.meta.MetaUtils;
import com.tmser.tr.manage.org.bo.Organization;
import com.tmser.tr.manage.org.service.OrganizationService;
import com.tmser.tr.manage.resources.bo.Attach;
import com.tmser.tr.manage.resources.service.AttachService;
import com.tmser.tr.manage.resources.service.ResourcesService;
import com.tmser.tr.notice.constants.NoticeType;
import com.tmser.tr.notice.service.impl.NoticeUtils;
import com.tmser.tr.uc.SysRole;
import com.tmser.tr.uc.bo.User;
import com.tmser.tr.uc.bo.UserSpace;
import com.tmser.tr.uc.dao.UserDao;
import com.tmser.tr.uc.dao.UserSpaceDao;
import com.tmser.tr.uc.utils.SessionKey;
import com.tmser.tr.utils.StringUtils;
import com.tmser.tr.writelessonplan.service.LessonPlanService;

/**
 * 集体备课活动 服务实现类
 * 
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: Activity.java, v 1.0 2015-03-06 Generate Tools Exp $
 */
@Service
@Transactional
public class ActivityServiceImpl extends AbstractService<Activity, Integer> implements ActivityService {

  private static final Logger logger = LoggerFactory.getLogger(ActivityServiceImpl.class);
  @Autowired
  private ActivityDao activityDao;
  @Autowired
  private UserSpaceDao userSpaceDao;
  @Autowired
  private LessonInfoDao lessonInfoDao;
  @Autowired
  private UserDao userDao;
  @Autowired
  private AttachService attachService;
  @Autowired
  private ActivityTracksDao activityTracksDao;
  @Autowired
  private CheckInfoDao checkInfoDao;
  @Autowired
  private CheckOpinionDao checkOpinionDao;
  @Autowired
  private ResourcesService resourcesService;
  @Autowired
  private LessonPlanService lessonPlanService;
  @Autowired
  private ActivityTracksService activityTracksService;
  @Autowired
  private OrganizationService organizationService;
  @Autowired
  private DiscussDao discussDao;

  /**
   * @return
   * @see com.tmser.tr.common.service.BaseService#getDAO()
   */
  @Override
  public BaseDAO<Activity, Integer> getDAO() {
    return activityDao;
  }

  // 我发起的备课
  @Override
  public PageList<Activity> findMyOrganizeActivityList(Activity activity) {
    UserSpace userSpace = (UserSpace) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE); // 用户空间
    Integer schoolYear = (Integer) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SCHOOLYEAR);// 学年
    activity.setOrganizeUserId(userSpace.getUserId());// 用户Id
    activity.setSchoolYear(schoolYear);
    activity.setOrganizeSubjectId(userSpace.getSubjectId());
    activity.setOrganizeGradeId(userSpace.getGradeId());
    activity.setStatus(1);
    activity.setPhaseId(userSpace.getPhaseId());
    activity.addOrder("releaseTime desc");
    PageList<Activity> listPage = activityDao.listPage(activity);
    updateStateByActivityTime(listPage.getDatalist());
    // 统计讨论数
    addDiscussCount(listPage.getDatalist());
    return listPage;
  }

  /**
   * 加入讨论数
   * 
   * @param datalist
   */
  private void addDiscussCount(List<Activity> datalist) {
    for (Activity activity : datalist) {
      Discuss discuss = new Discuss();
      discuss.setActivityId(activity.getId());
      discuss.setTypeId(ResTypeConstants.ACTIVITY);
      activity.setFlago(String.valueOf(discussDao.count(discuss)));// 加入讨论统计数
    }
  }

  // 查看可参与、可见活动
  @Override
  public PageList<Activity> findOtherActivityList(Activity activity) {
    UserSpace userSpace = (UserSpace) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE); // 用户空间
    Integer schoolYear = (Integer) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SCHOOLYEAR);// 学年
    // 非自己 同学校
    // 学科 年级
    Integer sysRoleId = userSpace.getSysRoleId();
    // Assert.assertNotNull(sysRoleId);//
    // activity.setOrganizeUserId(userSpace.getUserId());//用户Id
    activity.setOrgId(userSpace.getOrgId());
    activity.setSchoolYear(schoolYear);
    activity.setStatus(1);
    activity.setPhaseId(userSpace.getPhaseId());
    Map<String, Object> map = new HashMap<String, Object>();
    map.put("spaceId", userSpace.getId());
    map.put("gradeId", "%" + userSpace.getGradeId() + "%");
    map.put("subjectId", "%" + userSpace.getSubjectId() + "%");
    map.put("userId", userSpace.getUserId());
    if (sysRoleId.intValue() == SysRole.XKZZ.getId().intValue()) {
      // 学科组长
      activity.addCustomCondition(" and spaceId!=:spaceId ", map);
      activity.setSubjectIds(SqlMapping.LIKE_PRFIX + "," + userSpace.getSubjectId() + "," + SqlMapping.LIKE_PRFIX);
    } else if (sysRoleId.intValue() == SysRole.NJZZ.getId().intValue()) {
      // 年级组长
      activity.setGradeIds(SqlMapping.LIKE_PRFIX + "," + userSpace.getGradeId() + "," + SqlMapping.LIKE_PRFIX);
    } else if (sysRoleId.intValue() == SysRole.BKZZ.getId().intValue()) {
      // 备课组长
      activity.addCustomCondition(" and spaceId!=:spaceId ", map);
      activity.setGradeIds(SqlMapping.LIKE_PRFIX + "," + userSpace.getGradeId() + "," + SqlMapping.LIKE_PRFIX);
      activity.setSubjectIds(SqlMapping.LIKE_PRFIX + "," + userSpace.getSubjectId() + "," + SqlMapping.LIKE_PRFIX);
    } else if (sysRoleId.intValue() == SysRole.TEACHER.getId().intValue()) {
      // 老师
      // activity.setGradeIds(SqlMapping.LIKE_PRFIX + userSpace.getGradeId() +
      // SqlMapping.LIKE_PRFIX);
      // activity.setSubjectIds(SqlMapping.LIKE_PRFIX + "," +
      // userSpace.getSubjectId()+ "," + SqlMapping.LIKE_PRFIX);
      activity
          .addCustomCondition(
              " and ((gradeIds like :gradeId and subjectIds like :subjectId) or (mainUserGradeId=:gradeId and mainUserSubjectId=:subjectId and mainUserId=:userId))",
              map);
    }
    activity.addOrder("releaseTime desc");
    PageList<Activity> listPage = activityDao.listPage(activity);
    updateStateByActivityTime(listPage.getDatalist());
    // 统计讨论数
    addDiscussCount(listPage.getDatalist());
    return listPage;
  }

  // 判断是否有发起权限 : 学科组长,备课组长 才有发起权限
  // 只有教师无权限
  @Override
  public boolean isLeader(Integer sysRoleId) {
    if (sysRoleId.intValue() != SysRole.TEACHER.getId()) {
      return true;
    }
    return false;
  }

  // 读取当前可显示的学科集合
  @Override
  public List<Meta> findSubjectList() {
    UserSpace userSpace = (UserSpace) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE); // 用户空间
    Integer sysRoleId = userSpace.getSysRoleId();
    List<Meta> sysDicList = null;
    if (sysRoleId.intValue() == SysRole.XZ.getId() || sysRoleId.intValue() == SysRole.FXZ.getId()
        || sysRoleId.intValue() == SysRole.ZR.getId() || sysRoleId.intValue() == SysRole.NJZZ.getId()
        || sysRoleId.intValue() == SysRole.XKZZ.getId()) {

      Organization org = organizationService.findOne(userSpace.getOrgId());
      Integer[] areas = StringUtils.toIntegerArray(org.getAreaIds().substring(1, org.getAreaIds().lastIndexOf(",")),
          ",");
      sysDicList = MetaUtils.getPhaseSubjectMetaProvider().listAllSubject(org.getId(), userSpace.getPhaseId(), areas);
    } else if (sysRoleId.intValue() == SysRole.BKZZ.getId().intValue()) {
      Meta sysDic = MetaUtils.getMeta(userSpace.getSubjectId());
      sysDicList = new ArrayList<>();
      sysDicList.add(sysDic);
    }

    return sysDicList;
  }

  // 读取当前可显示的年级集合
  @Override
  public List<Meta> findGradeList() {
    UserSpace userSpace = (UserSpace) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE); // 用户空间
    Integer sysRoleId = userSpace.getSysRoleId();
    Organization org = organizationService.findOne(userSpace.getOrgId());
    List<Meta> sysDicList = null;
    if (sysRoleId.intValue() == SysRole.XZ.getId() || sysRoleId.intValue() == SysRole.FXZ.getId()
        || sysRoleId.intValue() == SysRole.ZR.getId() || sysRoleId.intValue() == SysRole.NJZZ.getId()
        || sysRoleId.intValue() == SysRole.XKZZ.getId()) {
      sysDicList = MetaUtils.getOrgTypeMetaProvider().listAllGrade(org.getSchoolings(), userSpace.getPhaseId());
    } else if (sysRoleId.intValue() == SysRole.BKZZ.getId().intValue()) {
      Meta sysDic = MetaUtils.getMeta(userSpace.getGradeId());
      sysDicList = new ArrayList<>();
      sysDicList.add(sysDic);
    }
    return sysDicList;
  }

  // 读主备人列表
  @Override
  public List<UserSpace> findMainUserList(UserSpace us) {
    UserSpace userSpace = (UserSpace) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE); // 用户空间
    Integer schoolYear = (Integer) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SCHOOLYEAR);
    us.setOrgId(userSpace.getOrgId());
    us.setSchoolYear(schoolYear);
    us.addCustomCulomn("distinct userId,username");
    List<UserSpace> userList = userSpaceDao.list(us, 200);
    return userList;
  }

  // 读章节列表
  @Override
  public List<LessonInfo> findChapterList(Integer userId, Integer gradeId, Integer subjectId) throws ParseException {
    // 获取当前用户空间
    // 学年
    Integer schoolYear = (Integer) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SCHOOLYEAR);
    LessonInfo lessonInfo = new LessonInfo();
    lessonInfo.setUserId(userId);
    // lessonInfo.setBookId(userSpace.getBookId());
    lessonInfo.setGradeId(gradeId);
    lessonInfo.setSubjectId(subjectId);
    lessonInfo.setSchoolYear(schoolYear);
    Map<String, Object> paramMap = new HashMap<String, Object>();
    paramMap.put("count", 0);
    lessonInfo.addCustomCondition(" and jiaoanCount > :count", paramMap);// 教案数量大于0（表示有教案）
    // //设置当前学期 (和撰写教案和我的备课本统一，讲此条件放开)
    // lessonInfo.setTermId(schoolYearService.getCurrentTerm());
    // Date currentDate = new Date();
    // SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd" );
    // Date tempDate1 = sdf.parse( schoolYear+"-07-15" );
    // SchoolYearService
    // Date tempDate2 = sdf.parse( (schoolYear+1)+"-02-01" );
    // if(currentDate.after(tempDate1) && currentDate.before(tempDate2)){
    // lessonInfo.setTermId(0); //上学期
    // }else if(currentDate.after(tempDate2)){
    // lessonInfo.setTermId(1); //下学期
    // }
    List<LessonInfo> lessonInfoList = lessonInfoDao.listAll(lessonInfo);
    return lessonInfoList;
  }

  // 保存集体备课活动
  @Override
  public Activity saveActivityTbja(Activity activity) throws Exception {
    UserSpace userSpace = (UserSpace) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE); // 用户空间
    Integer schoolYear = (Integer) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SCHOOLYEAR);// 学年
    Integer term = (Integer) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_TERM);// 学期
    activity.setTypeName(activity.getTypeId() == 1 ? "同备教案" : "主题研讨");
    activity.setOrganizeUserId(userSpace.getUserId());
    activity.setOrganizeUserName(userSpace.getUsername());// userDao
    activity.setSpaceId(userSpace.getId()); // 用户空间id
    activity.setOrganizeGradeId(userSpace.getGradeId());
    activity.setOrganizeSubjectId(userSpace.getSubjectId());
    activity.setOrgId(userSpace.getOrgId());
    activity.setCreateTime(new Date());
    activity.setSchoolYear(schoolYear);
    activity.setTerm(term);
    activity.setPhaseId(userSpace.getPhaseId());
    activity.setReleaseTime(new Date());
    activity.setCommentsNum(0);
    activity.setIsOver(false);
    activity.setIsSubmit(false);
    activity.setIsAudit(false);
    activity.setAuditUp(false);
    activity.setIsShare(false);
    activity.setIsComment(false);
    activity.setIsSend(false);
    if (activity.getStatus().intValue() == 1) { // 正式发文
      // 增加元素项
      if (activity.getSubjectIds() == null) {
        activity.setSubjectIds("," + activity.getMainUserSubjectId() + ",");
        activity.setSubjectName(MetaUtils.getMeta(activity.getMainUserSubjectId()).getName());
      }
      activity.setGradeName(getGradeNames(activity.getGradeIds()));
      User user = userDao.get(activity.getMainUserId());
      activity.setMainUserName(user != null ? user.getName() : "");
    } else {
      activity.setStartTime(null);
      activity.setEndTime(null);
    }
    // 新增
    activity = activityDao.insert(activity);
    if (activity.getStatus().intValue() == 1) { // 正式发文
      // 向整理意见表中拷贝一份主备人的教案数据作为原始教案，防止与主备人操作教案起冲突
      copyZhubei(activity);
      // 发送通知
      sendNoticeOfIssues(activity);
    }
    return activity;
  }

  // 保存主题研讨
  @Override
  public Activity saveActivityZtyt(Activity activity, String resIds) {
    UserSpace userSpace = (UserSpace) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE); // 用户空间
    Integer schoolYear = (Integer) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SCHOOLYEAR);// 学年
    Integer term = (Integer) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_TERM);// 学期
    activity.setTypeName(activity.getTypeId() == 2 ? "主题研讨" : "视频研讨");
    activity.setOrganizeUserId(userSpace.getUserId());
    activity.setOrganizeUserName(userSpace.getUsername());// userDao
    activity.setSpaceId(userSpace.getId()); // 用户空间id
    activity.setOrganizeGradeId(userSpace.getGradeId());
    activity.setOrganizeSubjectId(userSpace.getSubjectId());
    activity.setOrgId(userSpace.getOrgId());
    activity.setCreateTime(new Date());
    activity.setSchoolYear(schoolYear);
    activity.setTerm(term);
    activity.setPhaseId(userSpace.getPhaseId());
    activity.setReleaseTime(new Date());
    activity.setCommentsNum(0);
    activity.setIsOver(false);
    activity.setIsSubmit(false);
    activity.setIsAudit(false);
    activity.setAuditUp(false);
    activity.setIsShare(false);
    activity.setIsComment(false);
    activity.setIsSend(false);
    if (activity.getStatus().intValue() == 1) { // 正式发文
      // 增加元素项
      activity.setSubjectName(getSubjectNames(activity.getSubjectIds()));
      activity.setGradeName(getGradeNames(activity.getGradeIds()));
    } else {
      activity.setStartTime(null);
      activity.setEndTime(null);
    }
    // 新增
    activity = activityDao.insert(activity);
    // }
    // 增加活动资源
    if (resIds != null && !"".equals(resIds)) {
      String[] ids = resIds.split(",");
      attachService.addAttach(Attach.JTBK, activity.getId(), userSpace.getUserId(), ids);
    }
    if (activity.getStatus().intValue() == 1) { // 正式发文
      // 发送通知
      sendNoticeOfIssues(activity);
    }
    return activity;
  }

  // ------------------
  private String getGradeNames(String ids) {
    String names = "";
    if (ids != null) {
      String[] idss = ids.split(",");
      for (String pid : idss) {
        if (pid != null && !"".equals(pid.trim())) {
          names += "".equals(names) ? "" : "、";
          names += MetaUtils.getMeta(Integer.parseInt(pid)).getName();
        }
      }
    }
    return names;
  }

  private String getSubjectNames(String ids) {
    String names = "";
    if (ids != null) {
      String[] idss = ids.split(",");
      for (String pid : idss) {
        if (pid != null && !"".equals(pid.trim())) {
          names += "".equals(names) ? "" : "、";
          names += MetaUtils.getMeta(Integer.parseInt(pid)).getName();
        }
      }
    }
    return names;
  }

  /**
   * 删除活动，级联删除活动相关的信息
   * 
   * @param activityId
   * @see com.tmser.tr.activity.service.ActivityService#deleteActivity(java.lang.Integer)
   */
  @Override
  public void deleteActivity(Integer activityId) {
    // 删除活动
    activityDao.delete(activityId);
    // 删除活动的附属信息
    attachService.deleteAttach(Attach.JTBK, activityId);
    ActivityTracks at = new ActivityTracks();
    at.setActivityId(activityId);
    List<ActivityTracks> atList = activityTracksDao.listAll(at);
    for (ActivityTracks ats : atList) {
      activityTracksDao.delete(ats.getId());
      resourcesService.deleteResources(ats.getResId());
    }

    // 删除活动的查阅信息CheckInfo和CheckOpinion
    CheckInfo ci = new CheckInfo();
    ci.setResType(ResTypeConstants.ACTIVITY);
    ci.setResId(activityId);
    List<CheckInfo> ciList = checkInfoDao.listAll(ci);
    for (CheckInfo cis : ciList) {
      checkInfoDao.delete(cis.getId());
    }
    CheckOpinion co = new CheckOpinion();
    co.setResType(ResTypeConstants.ACTIVITY);
    co.setResId(activityId);
    List<CheckOpinion> coList = checkOpinionDao.listAll(co);
    for (CheckOpinion cos : coList) {
      checkOpinionDao.delete(cos.getId());
    }

  }

  /**
   * 更新集体备课——同备教案
   * 
   * @param activity
   * @return
   * @throws FileNotFoundException
   * @see com.tmser.tr.activity.service.ActivityService#updateActivityTbja(com.tmser.tr.activity.bo.Activity)
   */
  @Override
  public Activity updateActivityTbja(Activity activity, Model m) throws FileNotFoundException {
    // 增加元素项
    if (activity.getTypeId() == Activity.TBJA) {
      activity.setTypeName("同备教案");
    } else if (activity.getTypeId() == Activity.ZTYT) {
      activity.setTypeName("主题研讨");
    }
    if (activity.getTypeId() != null) {// 说明修改的是草稿，则增加发布时间，用于以后排序
      activity.setReleaseTime(new Date());
    }
    // 获取更新前活动
    Activity tempActivity = activityDao.get(activity.getId());
    if (activity.getStatus().intValue() == 1) {
      if (tempActivity.getStatus().intValue() == 1) {
        // 安全性验证
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String startTime1 = sdf.format(tempActivity.getStartTime());
        String startTime2 = sdf.format(activity.getStartTime());
        if (tempActivity.getCommentsNum().intValue() > 0) { // 活动已被讨论，只能修改部分数据
          if (!tempActivity.getGradeIds().equals(activity.getGradeIds())
              || tempActivity.getMainUserId().intValue() != activity.getMainUserId().intValue()
              || tempActivity.getInfoId().intValue() != activity.getInfoId().intValue()
              || !startTime1.equals(startTime2)) {
            m.addAttribute("result", "isHaveDiscuss");
            return null;
          }
        }
        Integer trackCount = activityTracksService.getTrackCountByActivityId(activity.getId());
        if (trackCount.intValue() > 0) { // 活动已被参与并有整理或修改意见留痕，只能修改部分数据
          if (!tempActivity.getGradeIds().equals(activity.getGradeIds())
              || tempActivity.getMainUserId().intValue() != activity.getMainUserId().intValue()
              || tempActivity.getInfoId().intValue() != activity.getInfoId().intValue()
              || !startTime1.equals(startTime2)) {
            m.addAttribute("result", "isHaveTracks");
            return null;
          }
        }
      }
      if (activity.getSubjectIds() == null || "".equals(activity.getSubjectIds())) {
        activity.setSubjectIds("," + activity.getMainUserSubjectId() + ",");
        // activity.setSubjectName(metaDataHerperService.getMetaDataById(activity.getMainUserSubjectId()).getMetaName());
      }
      activity.setGradeName(getGradeNames(activity.getGradeIds()));
      activity.setSubjectName(getSubjectNames(activity.getSubjectIds()));
      User user = activity.getMainUserId() != null ? userDao.get(activity.getMainUserId()) : null;
      activity.setMainUserName(user != null ? user.getName() : "");
    } else {
      activity.setStartTime(null);
      activity.setEndTime(null);
    }
    if (activity.getEndTime() == null) {
      activity.addCustomCulomn("endTime=null");
    }
    activityDao.update(activity);
    if (activity.getStatus().intValue() == 1) { // 正式发文
      if (tempActivity.getStatus().intValue() == 1) {// 原来的就是正式发文
        // if(tempActivity.getInfoId().intValue()!=activity.getInfoId().intValue()){
        // //主备教研发生变化，则删除原来的，拷贝新的
        // 删除track表中原始主备教案
        // activityTracksDao.deleteActivityTracks(activity.getId(),ActivityTracks.ZHUBEI);
        if (activityTracksService.getTrackCountByActivityId(activity.getId()) <= 0) {
          List<ActivityTracks> tracksList = activityTracksService.getActivityTracks_zhubei(activity.getId());
          for (ActivityTracks at : tracksList) {
            activityTracksDao.delete(at.getId());
            resourcesService.deleteResources(at.getResId());
          }
          // 拷贝主备教案
          copyZhubei(activity);
        }
        // }
      } else { // 原来的是草稿
        // 拷贝主备教案
        copyZhubei(activity);
      }
    }
    return activity;
  }

  /**
   * 向整理意见表中拷贝一份主备人的教案数据作为原始教案
   * 
   * @param activity
   * @throws FileNotFoundException
   */
  private void copyZhubei(Activity activity) {
    UserSpace userSpace = (UserSpace) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE); // 用户空间
    List<LessonPlan> lessonPlanList = lessonPlanService.getJiaoanByInfoId(activity.getInfoId());
    for (LessonPlan lp : lessonPlanList) {
      // 复制教案资源
      String newResId = resourcesService.copyRes(lp.getResId());
      ActivityTracks at = new ActivityTracks();
      at.setActivityId(activity.getId());
      at.setPlanId(lp.getPlanId());
      at.setPlanName(lp.getPlanName());
      at.setEditType(ActivityTracks.ZHUBEI);
      at.setLessonId(lp.getLessonId());
      at.setResId(newResId);
      at.setHoursId(lp.getHoursId());
      at.setOrderValue(lp.getOrderValue());
      at.setUserId(lp.getUserId());
      at.setUserName(lp.getUserName());
      at.setSubjectId(lp.getSubjectId());
      at.setSchoolYear(lp.getSchoolYear());
      at.setCrtId(userSpace.getUserId());
      at.setCrtDttm(new Date());
      at.setLastupDttm(new Date());
      activityTracksDao.insert(at);
    }
  }

  /**
   * 更新集备活动——主题研讨
   * 
   * @param activity
   * @return
   * @see com.tmser.tr.activity.service.ActivityService#updateActivityZtyt(com.tmser.tr.activity.bo.Activity)
   */
  @Override
  public Activity updateActivityZtyt(Activity activity, String resIds, Model m) {
    UserSpace userSpace = (UserSpace) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE); // 用户空间
    // 增加元素项
    if (activity.getTypeId() == Activity.TBJA) {
      activity.setTypeName("同备教案");
    } else if (activity.getTypeId() == Activity.ZTYT) {
      activity.setTypeName("主题研讨");
    } else if (activity.getTypeId() == Activity.SPJY) {
      activity.setTypeName("视频研讨");
    }
    if (activity.getTypeId() != null) {// 说明修改的是草稿，则增加发布时间，用于以后排序
      activity.setReleaseTime(new Date());
    }
    // 获取更新前活动
    Activity tempActivity = activityDao.get(activity.getId());
    if (activity.getStatus().intValue() == 1) {
      // 安全性验证
      if (tempActivity.getCommentsNum().intValue() > 0) { // 活动已被讨论，只能修改部分数据
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String startTime1 = sdf.format(tempActivity.getStartTime());
        String startTime2 = sdf.format(activity.getStartTime());
        if (!tempActivity.getGradeIds().equals(activity.getGradeIds()) || !startTime1.equals(startTime2)) {
          m.addAttribute("result", "isHaveDiscuss");
          return null;
        }
      }
      activity.setGradeName(getGradeNames(activity.getGradeIds()));
      activity.setSubjectName(getSubjectNames(activity.getSubjectIds()));
      User user = activity.getMainUserId() != null ? userDao.get(activity.getMainUserId()) : null;
      activity.setMainUserName(user != null ? user.getName() : "");
    } else {
      activity.setStartTime(null);
      activity.setEndTime(null);
    }
    if (activity.getEndTime() == null) {
      activity.addCustomCulomn("endTime=null");
    }
    int flag = activityDao.update(activity);
    if (flag > 0) {
      // 更新附件资源
      String[] ids = resIds.split(",");
      attachService.updateAttach(Attach.JTBK, activity.getId(), userSpace.getUserId(), ids);
    }
    return activity;
  }

  /**
   * 将整理的教案发送给参与人
   * 
   * @param id
   * @see com.tmser.tr.activity.service.ActivityService#sendToJoiner(java.lang.Integer)
   */
  @Override
  public void sendToJoiner(Integer id) {
    Activity activity = activityDao.get(id);
    Integer userId = activity.getOrganizeUserId();// 发起人id
    String subjectIdsStr = "0," + activity.getSubjectIds().substring(1, activity.getSubjectIds().length() - 1);
    List<Integer> subjectIdList = idsStrToIdsList(subjectIdsStr);
    String gradeIdsStr = "0," + activity.getGradeIds().substring(1, activity.getGradeIds().length() - 1);
    List<Integer> gradeIdList = idsStrToIdsList(gradeIdsStr);
    List<Integer> orgIdList = idsStrToIdsList(activity.getOrgId().toString());
    List<UserSpace> userSpacesList = userSpaceDao
        .getUserSpaceByOrg_Subject_Grade(orgIdList, subjectIdList, gradeIdList); // 有参与权限的用户

    Map<String, Object> info = new HashMap<String, Object>();
    info.put("activityId", id);
    info.put("lessonName", activity.getActivityName());
    info.put("typeName", activity.getTypeName());
    info.put("activityType", "activity");
    info.put("isOver", ifActivityIsOver(activity));
    NoticeType title = NoticeType.ACTIVITY_ZL;
    title.setTitlePrefix("集体备课");
    for (UserSpace us : userSpacesList) {
      if (us.getUserId().intValue() != userId.intValue()) { // 不包含发起人自己
        NoticeUtils.sendNotice(title, "整理 " + activity.getActivityName() + " 主备教案", activity.getOrganizeUserId(),
            us.getUserId(), info);
      }
    }
    // 将活动更新为已发送给参与人状态（之后发起人将不可再整理）
    activity.setIsSend(true);
    activityDao.update(activity);
  }

  /**
   * ids字符串转ids Integer集合
   * 
   * @param subjectIdsStr
   * @return
   */
  private List<Integer> idsStrToIdsList(String subjectIdsStr) {
    List<Integer> idList = new ArrayList<Integer>();
    String[] idsArray = subjectIdsStr.split(",");
    for (String id : idsArray) {
      idList.add(Integer.valueOf(id));
    }
    return idList;
  }

  /**
   * 根据活动的结束时间更新活动的状态
   * 
   * @param raList
   * @return
   */
  private List<Activity> updateStateByActivityTime(List<Activity> raList) {
    Date nowDate = new Date();
    for (Activity activity : raList) {
      Date endTime = activity.getEndTime();
      if (endTime != null) {
        if (endTime.before(nowDate)) { // 活动已结束
          activity.setIsOver(true);
          activityDao.update(activity);
        }
      }
    }
    return raList;
  }

  /**
   * 判断活动是否已结束，如果已结束则更改状态为“结束”
   * 
   * @param activity
   * @return
   */
  @Override
  public boolean ifActivityIsOver(Activity activity) {
    if (activity.getIsOver()) {
      return true;
    } else {
      Date nowDate = new Date();
      Date endTime = activity.getEndTime();
      if (endTime != null) {
        if (endTime.before(nowDate)) { // 活动已结束
          activity.setIsOver(true);
          activityDao.update(activity);
          return true;
        }
      }
      return false;
    }
  }

  /**
   * 发送发布活动的消息
   * 
   * @param activity
   */
  public void sendNoticeOfIssues(Activity activity) {
    try {
      UserSpace userSpace = (UserSpace) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE); // 用户空间
      Integer userId = activity.getOrganizeUserId();// 发起人id
      String subjectIdsStr = "0," + activity.getSubjectIds().substring(1, activity.getSubjectIds().length() - 1);
      List<Integer> subjectIdList = idsStrToIdsList(subjectIdsStr);
      String gradeIdsStr = "0," + activity.getGradeIds().substring(1, activity.getGradeIds().length() - 1);
      List<Integer> gradeIdList = idsStrToIdsList(gradeIdsStr);
      List<Integer> orgIdList = idsStrToIdsList(activity.getOrgId().toString());
      List<UserSpace> userSpacesList = userSpaceDao.getUserSpaceByOrg_Subject_Grade(orgIdList, subjectIdList,
          gradeIdList); // 有参与权限的用户

      Map<String, Object> info = new HashMap<String, Object>();
      info.put("activityId", activity.getId());
      info.put("lessonName", activity.getActivityName());
      info.put("typeName", activity.getTypeName());
      info.put("activityType", "activity");
      info.put("typeId", activity.getTypeId());
      info.put("fq_role", userSpace.getSpaceName());
      info.put("fq_userName", activity.getOrganizeUserName());
      String fq_startDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(activity.getStartTime());
      info.put("fq_startDate", fq_startDate);
      NoticeType title = NoticeType.ACTIVITY_FB;
      title.setTitlePrefix("集体备课");
      for (UserSpace us : userSpacesList) {
        if (us.getUserId().intValue() != userId.intValue()) { // 不包含发起人自己
          NoticeUtils.sendNotice(title, "发布 " + activity.getActivityName() + " 集体备课", activity.getOrganizeUserId(),
              us.getUserId(), info);
        }
      }
    } catch (Exception e) {
      logger.error("集体备课发布后的消息发送失败！", e);
    }
  }

  /**
   * 删除一条草稿
   * 
   * @param id
   * @see com.tmser.tr.activity.service.ActivityService#deleteDraft(java.lang.Integer)
   */
  @Override
  public void deleteDraft(Integer activityId) {
    // 删除活动
    activityDao.delete(activityId);
    // 删除活动的附属信息
    attachService.deleteAttach(Attach.JTBK, activityId);
  }

  /**
   * 通过学科年级查询用户
   * 
   * @param activityId
   * @return
   * @see com.tmser.tr.activity.service.ActivityDiscussService#findUserBySubjectAndGrade(java.lang.Integer)
   */
  @Override
  public List<UserSpace> findUserBySubjectAndGrade(Activity activity) {
    // 查询有参与权限的用户
    if (activity != null) {
      String subjectIds = activity.getSubjectIds();
      if (subjectIds != null && subjectIds.length() > 2) {
        subjectIds = subjectIds.substring(0, subjectIds.length() - 1);
      } else {
        subjectIds = "";
      }
      subjectIds = "0" + subjectIds;
      String gradeIds = activity.getGradeIds();
      if (gradeIds != null && gradeIds.length() > 2) {
        gradeIds = gradeIds.substring(0, gradeIds.length() - 1);
      } else {
        gradeIds = "";
      }
      gradeIds = "0" + gradeIds;
      UserSpace us = new UserSpace();
      us.setPhaseId(activity.getPhaseId());
      us.setOrgId(activity.getOrgId());// 属活动机构的
      us.setSchoolYear(activity.getSchoolYear());
      us.addCustomCondition(" and subjectId in (" + subjectIds + ") and gradeId in (" + gradeIds + ") ",
          new HashMap<String, Object>());
      us.addOrder(" sysRoleId desc ");
      us.addCustomCulomn(" distinct(userId) ");
      List<UserSpace> usList = userSpaceDao.listAll(us);
      return usList;
    } else {
      return null;
    }
  }

}
