/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.activity.service.impl;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.tmser.tr.activity.bo.SchoolActivity;
import com.tmser.tr.activity.bo.SchoolActivityTracks;
import com.tmser.tr.activity.bo.SchoolTeachCircleOrg;
import com.tmser.tr.activity.dao.SchoolActivityDao;
import com.tmser.tr.activity.dao.SchoolActivityTracksDao;
import com.tmser.tr.activity.dao.SchoolTeachCircleOrgDao;
import com.tmser.tr.activity.service.SchoolActivityService;
import com.tmser.tr.activity.service.SchoolActivityTracksService;
import com.tmser.tr.activity.service.SchoolTeachCircleOrgService;
import com.tmser.tr.classapi.bo.ClassInfo;
import com.tmser.tr.classapi.service.ClassOperateService;
import com.tmser.tr.comment.bo.Discuss;
import com.tmser.tr.comment.service.DiscussService;
import com.tmser.tr.common.ResTypeConstants;
import com.tmser.tr.common.dao.BaseDAO;
import com.tmser.tr.common.orm.SqlMapping;
import com.tmser.tr.common.page.PageList;
import com.tmser.tr.common.service.AbstractService;
import com.tmser.tr.common.utils.WebThreadLocalUtils;
import com.tmser.tr.lessonplan.bo.LessonInfo;
import com.tmser.tr.lessonplan.bo.LessonPlan;
import com.tmser.tr.lessonplan.dao.LessonInfoDao;
import com.tmser.tr.manage.meta.MetaUtils;
import com.tmser.tr.manage.org.bo.Organization;
import com.tmser.tr.manage.org.dao.OrganizationDao;
import com.tmser.tr.manage.resources.bo.Attach;
import com.tmser.tr.manage.resources.bo.Resources;
import com.tmser.tr.manage.resources.service.AttachService;
import com.tmser.tr.manage.resources.service.ResourcesService;
import com.tmser.tr.notice.constants.NoticeType;
import com.tmser.tr.notice.service.impl.NoticeUtils;
import com.tmser.tr.uc.SysRole;
import com.tmser.tr.uc.bo.UserSpace;
import com.tmser.tr.uc.dao.UserSpaceDao;
import com.tmser.tr.uc.utils.SessionKey;
import com.tmser.tr.utils.StringUtils;
import com.tmser.tr.writelessonplan.service.LessonPlanService;

/**
 * 校际教研活动表 服务实现类
 * 
 * <pre>
 * 
 * </pre>
 * 
 * @author zpp
 * @version $Id: SchoolActivity.java, v 1.0 2015-05-20 zpp Exp $
 */
@Service
@Transactional
public class SchoolActivityServiceImpl extends AbstractService<SchoolActivity, Integer>
    implements SchoolActivityService {

  private static final Logger logger = LoggerFactory.getLogger(SchoolActivityServiceImpl.class);
  @Autowired
  private SchoolActivityDao schoolActivityDao;
  @Autowired
  private SchoolTeachCircleOrgDao schoolTeachCircleOrgDao;
  @Autowired
  private OrganizationDao organizationDao;
  @Autowired
  private UserSpaceDao userSpaceDao;
  @Autowired
  private LessonInfoDao lessonInfoDao;
  @Autowired
  private AttachService attachService;
  @Autowired
  private SchoolActivityTracksDao schoolActivityTracksDao;
  @Autowired
  private DiscussService discussService;
  @Resource
  private ResourcesService resourcesService;
  @Autowired
  private SchoolTeachCircleOrgService schoolTeachCircleOrgService;
  @Autowired
  private LessonPlanService lessonPlanService;
  @Autowired
  private SchoolActivityTracksService schoolActivityTracksService;

  @Resource(name = "classApi")
  private ClassOperateService classOperateService;

  /**
   * @return
   * @see com.tmser.tr.common.service.BaseService#getDAO()
   */
  @Override
  public BaseDAO<SchoolActivity, Integer> getDAO() {
    return schoolActivityDao;
  }

  private Integer zeroToNull(Integer v) {
    return v != null && v > 0 ? v : null;
  }

  /**
   * 进入校际教研界面
   * 
   * @param sa
   * @return
   * @see com.tmser.tr.activity.service.SchoolActivityService#findSchoolActivity(com.tmser.tr.activity.bo.SchoolActivity)
   */
  @SuppressWarnings("unchecked")
  @Override
  public Map<String, Object> findSchoolActivity(SchoolActivity sa, Integer listType) {
    if (listType == null) {
      listType = 0;
    }
    boolean drafCount = true;
    boolean isDiscuss = false;// 是否需要查询讨论数据
    Map<String, Object> returnMap = new HashMap<String, Object>();
    UserSpace userSpace = (UserSpace) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE); // 用户空间
    Integer schoolYear = (Integer) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SCHOOLYEAR); // 学年
    Integer term = (Integer) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_TERM); // 学年
    Integer sysRoleId = userSpace.getSysRoleId();
    sa.setStatus(1);// 正式发文
    sa.setTerm(term);// 学期
    sa.setSchoolYear(schoolYear);// 学年

    Map<String, Object> circleMap = getCircleMap(userSpace.getOrgId(), schoolYear);
    circleMap.put("userId", userSpace.getUserId());
    circleMap.put("subjectId", userSpace.getSubjectId());
    circleMap.put("gradeId", userSpace.getGradeId());
    circleMap.put("userId", userSpace.getUserId());

    StringBuilder hql = new StringBuilder();
    if (listType != 1 && !SysRole.TEACHER.getId().equals(sysRoleId)) {// 发起与管理
      // 当前身份年级学科发起的
      sa.setOrganizeUserId(userSpace.getUserId());
      sa.setOrganizeGradeId(zeroToNull(userSpace.getGradeId()));
      sa.setOrganizeSubjectId(zeroToNull(userSpace.getSubjectId()));
      isDiscuss = true;
    } else {
      hql.append(" and ((orgids like :lkorgId ");
      if (zeroToNull(userSpace.getSubjectId()) != null) {
        hql.append(" and subjectIds like :lksubjectId ");
        circleMap.put("lksubjectId", "%," + userSpace.getSubjectId() + ",%");
      }

      if (zeroToNull(userSpace.getGradeId()) != null) {
        hql.append(" and gradeIds like :lkgradeId ");
        circleMap.put("lkgradeId", "%," + userSpace.getGradeId() + ",%");
      }

      hql.append(") or expertIds like :lkuserId or (mainUserId=:userId and mainUserSubjectId=:subjectId and mainUserGradeId=:gradeId)) ");
      circleMap.put("lkuserId", "%," + userSpace.getUserId() + ",%");
      circleMap.put("lkorgId", "%," + userSpace.getOrgId() + ",%");
      if (!SysRole.TEACHER.getId().equals(sysRoleId)) {
        hql.append(" and organizeUserId != :userId ");
      }
    }

    if (sysRoleId.intValue() == SysRole.TEACHER.getId().intValue()) {// 教师
      // 查询要包含当前人是备课
      sa.addCustomCondition(hql.toString(), circleMap);
      returnMap.put("tuiCircleIds", circleMap.get("tuiCircleIds"));
      returnMap.put("pageUrl", "/schoolactivity/notCreateActivityIndex");
      returnMap.put("role", "js");
      listType = 1;
      drafCount = false;
    } else if (sysRoleId.intValue() == SysRole.XZ.getId().intValue()
        || sysRoleId.intValue() == SysRole.FXZ.getId().intValue()
        || sysRoleId == SysRole.ZR.getId().intValue()) {// 校长、副校长或者是主任
      if (listType == 1) {
        sa.addCustomCondition(hql.toString(), circleMap);
        drafCount = false;
        returnMap.put("pageUrl", "/schoolactivity/notCreateActivityIndex");
      } else {
        returnMap.put("pageUrl", "/schoolactivity/canCreateActivityIndex");
        listType = -1;
      }
      returnMap.put("tuiCircleIds", circleMap.get("tuiCircleIds"));
      returnMap.put("role", "xzzr");
    } else if (SysRole.BKZZ.getId().equals(sysRoleId) ||
        SysRole.XKZZ.getId().equals(sysRoleId) ||
        SysRole.NJZZ.getId().equals(sysRoleId)) {// 备课组长
      sa.addCustomCondition(hql.toString(), circleMap);
      returnMap.put("tuiCircleIds", circleMap.get("tuiCircleIds"));
      returnMap.put("role", "zuzhang");
      returnMap.put("pageUrl", "/schoolactivity/canCreateActivityIndex");
    } else if (sysRoleId.intValue() == SysRole.JYY.getId().intValue()) {// 教研员
      if (listType == 1) {// 参与及查看
        Organization organization = organizationDao.get(userSpace.getOrgId());
        sa.addCustomCondition(
            " and ((organizeUserId != :userId and subjectIds like :lksubjectId )or expertIds like :lkuserId )",
            circleMap);
        sa.setAreaIds(SqlMapping.LIKE_PRFIX + "," + organization.getAreaId() + "," + SqlMapping.LIKE_PRFIX);
      }
      returnMap.put("role", "jy");
      returnMap.put("pageUrl", "/schoolactivity/canCreateActivityIndex");
    } else if (sysRoleId.intValue() == SysRole.JYZR.getId().intValue()) {// 教研主任
      if (listType == 1) {// 参与及查看
        Organization organization = organizationDao.get(userSpace.getOrgId());
        sa.addCustomCondition(" and (organizeUserId != :userId or expertIds like :lkuserId )", circleMap);
        sa.setAreaIds(SqlMapping.LIKE_PRFIX + "," + organization.getAreaId() + "," + SqlMapping.LIKE_PRFIX);
      }
      returnMap.put("role", "jy");
      returnMap.put("pageUrl", "/schoolactivity/canCreateActivityIndex");
    }

    sa.setPhaseId(userSpace.getPhaseId());
    sa.addOrder("releaseTime desc");
    PageList<SchoolActivity> listPage = schoolActivityDao.listPage(sa);

    boolean isTuiChu = false;
    if (listPage.getDatalist() != null && listPage.getDatalist().size() > 0) {
      for (SchoolActivity saTemp : listPage.getDatalist()) {
        List<Integer> listTwo = (List<Integer>) returnMap.get("tuiCircleIds");
        if (listTwo != null && listTwo.size() > 0) {
          for (Integer stcId : listTwo) {
            if (saTemp.getSchoolTeachCircleId().intValue() == stcId.intValue()) {
              isTuiChu = true;
              break;
            }
          }
          if (!isTuiChu) {// 没有退出，才进行封装数据
            SchoolTeachCircleOrg stco = new SchoolTeachCircleOrg();
            stco.setStcId(saTemp.getSchoolTeachCircleId());
            stco.addCustomCondition(" and state != " + SchoolTeachCircleOrg.YI_JU_JUE,
                new HashMap<String, Object>());
            stco.addOrder("sort asc");
            saTemp.setStcoList(schoolTeachCircleOrgDao.listAll(stco));
          }
          saTemp.setIsTuiChu(isTuiChu);
          isTuiChu = false;
        } else {
          saTemp.setIsTuiChu(isTuiChu);
          SchoolTeachCircleOrg stco = new SchoolTeachCircleOrg();
          stco.setStcId(saTemp.getSchoolTeachCircleId());
          stco.addCustomCondition(" and state != " + SchoolTeachCircleOrg.YI_JU_JUE,
              new HashMap<String, Object>());
          stco.addOrder("sort asc");
          saTemp.setStcoList(schoolTeachCircleOrgDao.listAll(stco));
        }
        if (isDiscuss) {// 需要查询讨论信息
          Discuss rad = new Discuss();
          rad.setActivityId(saTemp.getId());
          rad.setTypeId(ResTypeConstants.SCHOOLTEACH);
          List<Discuss> list = discussService.find(rad, 1);
          if (list != null && list.size() > 0) {
            saTemp.setIsDiscuss(true);// 有讨论信息
          } else {
            saTemp.setIsDiscuss(false);// 无讨论信息
          }
        }

        Date endTime = saTemp.getEndTime();// 结束时间
        if (endTime != null) {
          boolean flag = endTime.before(new Date());
          if (flag) {// 已结束
            Boolean isOver = saTemp.getIsOver();
            if (!isOver) {
              saTemp.setIsOver(true);
              SchoolActivity sa_temp = new SchoolActivity();
              sa_temp.setId(saTemp.getId());
              sa_temp.setIsOver(true);
              schoolActivityDao.update(sa_temp);
            }
          }
        }
      }
    }
    returnMap.put("listPage", listPage);
    returnMap.put("listType", listType);

    if (drafCount) {// 是否需要查询此人的草稿箱数量
      SchoolActivity model = new SchoolActivity();
      model.setOrganizeUserId(userSpace.getUserId());
      model.setSchoolYear(schoolYear);
      model.setTerm(term);
      model.setStatus(0);
      model.setSpaceId(userSpace.getId());
      int count = schoolActivityDao.count(model);
      returnMap.put("activityDraftNum", count);
    }
    return returnMap;
  }

  // 获得的当前用户所在学校参与的教研圈，分别获得所参与教研圈并且状态是通过、恢复 还有就是退出的
  public Map<String, Object> getCircleMap(Integer orgId, Integer schoolYear) {
    Map<String, Object> returnMap = new HashMap<String, Object>();
    // 查询当前教师所在机构参与的当前学年的教研圈，并且状态分别为（同意：2 恢复：5 和 退出：4）,其中退出只能查看退出之前的信息
    SchoolTeachCircleOrg stco = new SchoolTeachCircleOrg();
    stco.setState(SchoolTeachCircleOrg.YI_TUI_CHU);
    List<SchoolTeachCircleOrg> list = schoolTeachCircleOrgDao.listAll(stco);
    // 教研圈Ids
    List<Integer> tuiCircleIds = new ArrayList<Integer>();
    for (SchoolTeachCircleOrg stco2 : list) {
      tuiCircleIds.add(stco2.getStcId());
    }
    returnMap.put("tuiCircleIds", tuiCircleIds);
    return returnMap;
  }

  /**
   * 校际教研草稿箱
   * 
   * @param sa
   * @return
   * @see com.tmser.tr.activity.service.SchoolActivityService#findSchoolActivityDraf(com.tmser.tr.activity.bo.SchoolActivity)
   */
  @Override
  public PageList<SchoolActivity> findSchoolActivityDraf(SchoolActivity sa) {
    UserSpace userSpace = (UserSpace) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE); // 用户空间
    Integer schoolYear = (Integer) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SCHOOLYEAR); // 学年
    Integer term = (Integer) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_TERM); // 学期
    sa.setOrganizeUserId(userSpace.getUserId());
    sa.setSchoolYear(schoolYear);
    sa.setTerm(term);
    sa.setStatus(0);
    sa.setSpaceId(userSpace.getId());
    sa.addOrder("createTime desc");
    return schoolActivityDao.listPage(sa);
  }

  // 读主备人列表
  @Override
  public List<UserSpace> findMainUserList(UserSpace us) {
    us.addCustomCulomn("distinct userId,username");
    List<UserSpace> userList = userSpaceDao.listAll(us);
    return userList;
  }

  // 读主备课题列表
  @Override
  public List<LessonInfo> findChapterList(Integer userId, Integer subjectId, Integer gradeId) {
    // 学年
    Integer schoolYear = (Integer) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SCHOOLYEAR);
    LessonInfo lessonInfo = new LessonInfo();
    lessonInfo.setUserId(userId);
    lessonInfo.setSubjectId(subjectId);
    lessonInfo.setGradeId(gradeId);
    lessonInfo.setSchoolYear(schoolYear);
    Map<String, Object> paramMap = new HashMap<String, Object>();
    paramMap.put("count", 0);
    lessonInfo.addCustomCondition(" and jiaoanCount > :count", paramMap);// 教案数量大于0（表示有教案）
    List<LessonInfo> lessonInfoList = lessonInfoDao.listAll(lessonInfo);
    return lessonInfoList;
  }

  /**
   * 查询用户作为临时的专家
   * 
   * @param userName
   * @return
   * @see com.tmser.tr.activity.service.SchoolActivityService#findUserObjectByName(java.lang.String)
   */
  @Override
  public List<Map<String, Object>> findUserObjectByName(String userName) {
    return schoolActivityDao.findUserObjectByName(userName);
  }

  /**
   * 保存 校际教研集体备课活动-同备教案-主题研讨
   * 
   * @param sa
   * @return
   * @see com.tmser.tr.activity.service.SchoolActivityService#saveOrUpdateSchoolActivity(com.tmser.tr.activity.bo.SchoolActivity)
   */
  @Override
  public Integer saveOrUpdateSchoolActivity(SchoolActivity sa, String resIds, Boolean isDiscuss, Boolean haveTrack) {
    UserSpace userSpace = (UserSpace) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE); // 用户空间
    Integer schoolYear = (Integer) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SCHOOLYEAR);// 学年
    Integer term = (Integer) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_TERM);// 学期
    if (sa == null) {
      return 2;
    }
    // 增加元素项
    if (StringUtils.isEmpty(sa.getSubjectIds()) && sa.getMainUserSubjectId() != null) {
      sa.setSubjectIds("," + sa.getMainUserSubjectId() + ",");
    }
    sa.setSubjectName(getObjectNamesByIds(sa.getSubjectIds()));
    sa.setGradeName(getObjectNamesByIds(sa.getGradeIds()));
    if (sa.getStatus() == 0) {// 存草稿箱
      sa.setStartTime(null);
      sa.setEndTime(null);
    }
    if (sa.getId() != null && sa.getId().intValue() != 0) {// 更新
      if (sa.getEndTime() == null) {
        sa.addCustomCulomn("endTime=null");
      }
      SchoolActivity schoolActivity = schoolActivityDao.get(sa.getId());
      if (schoolActivity != null && schoolActivity.getStatus() == 0 && sa.getStatus() == 1) {
        sa.setReleaseTime(new Date());
        String orgids = getOrgIdsByCircleId(sa.getSchoolTeachCircleId(), schoolYear);
        sa.setOrgids(orgids);
        schoolActivityDao.update(sa);
        sa = schoolActivityDao.get(sa.getId());
        if (sa.getTypeId().intValue() == SchoolActivity.TBJA.intValue()) {
          if (schoolActivity.getStatus().intValue() == 1) { // 原来就是正式发文
            if (schoolActivityTracksService.getTrackCountByActivityId(sa.getId()) <= 0) {
              List<SchoolActivityTracks> trackList = schoolActivityTracksService
                  .getActivityTracks_zhubei(sa.getId());
              for (SchoolActivityTracks track : trackList) {
                schoolActivityTracksDao.delete(track.getId());
                resourcesService.deleteResources(track.getResId());
              }
              // 拷贝主备教案
              copyZhubei(sa);
            }
          } else {// 原来就是草稿
            // 拷贝主备教案
            copyZhubei(sa);
          }
        }
        try {
          // 给参与人发送消息
          sendNoticeOfIssues(sa);
          // 发送邀请专家消息
          setYaoQingZhuanjiaMessage(sa);
        } catch (Exception e) {
          logger.error("校级教研消息发送失败！", e);
        }
      } else {
        if (sa.getStatus() == 1) {// 发布
          // 是否是已经发布的修改,在跳转到修改页面时，还没有整理意见和讨论信息
          if (sa.getStatus() == 1 && !(isDiscuss != null ? isDiscuss : false)
              && !(haveTrack != null ? haveTrack : false)) {
            if (sa.getTypeId().intValue() == SchoolActivity.ZTYT.intValue()
                || sa.getTypeId().intValue() == SchoolActivity.SPYT.intValue()) {
              // 是否有讨论信息
              Boolean haveDiscuss = isDiscuss(sa.getId());
              if (haveDiscuss && schoolActivity != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                if (schoolActivity.getStartTime() != null && sa.getStartTime() != null) {
                  String startTime1 = sdf.format(schoolActivity.getStartTime());
                  String startTime2 = sdf.format(sa.getStartTime());
                  if (!schoolActivity.getGradeIds().equals(sa.getGradeIds())
                      || !startTime1.equals(startTime2)
                      || !schoolActivity.getSchoolTeachCircleId()
                          .equals(sa.getSchoolTeachCircleId())
                      || !(schoolActivity.getExpertIds() != null ? schoolActivity.getExpertIds()
                          : "").equals(sa.getExpertIds() != null ? sa.getExpertIds() : "")) {
                    return 2;
                  }
                }
              }
            } else if (sa.getTypeId().intValue() == SchoolActivity.TBJA.intValue()) {
              SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
              String startTime1 = sdf.format(schoolActivity.getStartTime());
              String startTime2 = sdf.format(sa.getStartTime());
              // 是否有讨论信息
              Boolean haveDiscuss = isDiscuss(sa.getId());
              if (haveDiscuss) {
                if (!schoolActivity.getGradeIds().equals(sa.getGradeIds())
                    || schoolActivity.getMainUserId().intValue() != sa.getMainUserId().intValue()
                    || !schoolActivity.getInfoId().equals(sa.getInfoId())
                    || !startTime1.equals(startTime2)
                    || !schoolActivity.getSchoolTeachCircleId().equals(sa.getSchoolTeachCircleId())
                    || !(schoolActivity.getExpertIds() != null ? schoolActivity.getExpertIds() : "")
                        .equals(sa.getExpertIds() != null ? sa.getExpertIds() : "")) {
                  return 2;
                }
              }
              // 是否有整理意见
              Integer trackCount = schoolActivityTracksService.getTrackCountByActivityId(sa.getId());
              if (trackCount > 0) {
                if (!schoolActivity.getGradeIds().equals(sa.getGradeIds())
                    || schoolActivity.getMainUserId().intValue() != sa.getMainUserId().intValue()
                    || !schoolActivity.getInfoId().equals(sa.getInfoId())
                    || !startTime1.equals(startTime2)
                    || !schoolActivity.getSchoolTeachCircleId().equals(sa.getSchoolTeachCircleId())
                    || !(schoolActivity.getExpertIds() != null ? schoolActivity.getExpertIds() : "")
                        .equals(sa.getExpertIds() != null ? sa.getExpertIds() : "")) {
                  return 3;
                }
              }
            }
          }
          if (sa.getTypeId().intValue() == SchoolActivity.TBJA.intValue()) {
            if (schoolActivity.getStatus().intValue() == 1) { // 原来就是正式发文
              if (schoolActivityTracksService.getTrackCountByActivityId(sa.getId()) <= 0) {
                List<SchoolActivityTracks> trackList = schoolActivityTracksService
                    .getActivityTracks_zhubei(sa.getId());
                for (SchoolActivityTracks track : trackList) {
                  schoolActivityTracksDao.delete(track.getId());
                  resourcesService.deleteResources(track.getResId());
                }
                // 拷贝主备教案
                copyZhubei(sa);
              }
            } else {// 原来就是草稿
              // 拷贝主备教案
              copyZhubei(sa);
            }
          }
          if (sa.getTypeId().intValue() == SchoolActivity.ZBKT.intValue()) {
            this.updateZbktClassId(schoolActivity, resIds, userSpace);
          }
        }
        schoolActivityDao.update(sa);
        sa = schoolActivityDao.get(sa.getId());
      }
    } else {// 新增
      Organization organization = organizationDao.get(userSpace.getOrgId());
      sa.setAreaIds(organization.getAreaIds());
      sa.setTypeName(sa.getTypeId() == 1 ? "同备教案"
          : (sa.getTypeId() == 2 ? "主题研讨" : (sa.getTypeId() == 3 ? "视频研讨" : "直播课堂")));
      sa.setOrganizeUserId(userSpace.getUserId());
      sa.setOrganizeUserName(userSpace.getUsername());
      sa.setSpaceId(userSpace.getId()); // 用户空间id
      sa.setOrgId(userSpace.getOrgId());
      sa.setOrganizeSubjectId(userSpace.getSubjectId() != null ? userSpace.getSubjectId() : 0);
      sa.setOrganizeGradeId(userSpace.getGradeId() != null ? userSpace.getGradeId() : 0);
      sa.setPhaseId(userSpace.getPhaseId());
      sa.setCreateTime(new Date());
      sa.setReleaseTime(new Date());
      sa.setCommentsNum(0);
      sa.setIsOver(false);
      sa.setIsSubmit(false);
      sa.setIsAudit(false);
      sa.setIsShare(false);
      sa.setIsComment(false);
      sa.setIsSend(false);
      sa.setSchoolYear(schoolYear);
      sa.setTerm(term);
      if (sa.getStatus() == 1) {
        String orgids = getOrgIdsByCircleId(sa.getSchoolTeachCircleId(), schoolYear);
        sa.setOrgids(orgids);
      }
      sa = schoolActivityDao.insert(sa);
      if (sa.getStatus() == 1) {
        if (sa.getTypeId().intValue() == SchoolActivity.TBJA.intValue()) {
          // 向整理意见表中拷贝一份主备人的教案数据作为原始教案，防止与主备人操作教案起冲突
          copyZhubei(sa);
        }
        // 直播课堂修改
        if (sa.getTypeId().intValue() == SchoolActivity.ZBKT.intValue()) {
          this.insertZbktAttach(sa, resIds, userSpace);
          schoolActivityDao.update(sa);
        }
        try {
          // 给参与人发送消息
          sendNoticeOfIssues(sa);
          // 发送邀请专家消息
          setYaoQingZhuanjiaMessage(sa);
        } catch (Exception e) {
          logger.error("校级教研消息发送失败!");
        }
      }
    }
    if ((sa.getTypeId() == 2 || sa.getTypeId() == 3) && StringUtils.isNotEmpty(resIds)) {
      setZtytFj(sa.getId(), resIds);
    }
    return 1;
  }

  /**
   * 添加直播课堂附件
   * 
   * @param sa
   * @param resIds
   * @param userSpace
   */
  private void insertZbktAttach(SchoolActivity sa, String resIds, UserSpace userSpace) {
    ClassInfo classInfo = new ClassInfo(sa.getActivityName(), userSpace.getUserId(), userSpace.getUsername(),
        sa.getStartTime(), sa.getEndTime());
    List<File> fileList = new ArrayList<File>();
    // 保存参考附件
    if (StringUtils.isNotEmpty(resIds)) {
      String[] attachResIdsArray = resIds.split(",");
      attachService.addAttach(Attach.XJJY, sa.getId(), userSpace.getUserId(), attachResIdsArray);
      for (String attachResId : attachResIdsArray) {
        fileList.add(new File(resourcesService.download(attachResId)));
      }
    }
    if (fileList.size() > 0) {
      // 将文件批量同步到学点云直播平台
      List<String> docIds = classOperateService.uploadDoc(fileList, userSpace.getUserId(), userSpace.getUsername());
      if (!CollectionUtils.isEmpty(docIds)) {
        classInfo.setDocIds(docIds);// 将同步到学点云的文件id加入到课堂
      }
    }
    // 创建课堂
    classInfo = classOperateService.createClass(classInfo);
    sa.setClassId(classInfo.getId());

    try {
      // TODO 接口ERROR 同步聊天数据
      // classOperateService.addChatCallback(classInfo.getId());
    } catch (Exception e) {
      logger.warn("regist sync interface failed!");
      logger.debug("", e);
    }
  }

  /**
   * 通过校级教研圈id获得可参与的机构字符转
   * 
   * @param schoolTeachCircleId
   * @return
   */
  private String getOrgIdsByCircleId(Integer schoolTeachCircleId, Integer schoolYear) {
    SchoolTeachCircleOrg stco = new SchoolTeachCircleOrg();
    stco.setSchoolYear(schoolYear);
    stco.setStcId(schoolTeachCircleId);
    List<Integer> states = new ArrayList<Integer>();
    states.add(SchoolTeachCircleOrg.YI_TONG_YI);
    states.add(SchoolTeachCircleOrg.YI_HUI_FU);
    Map<String, Object> map = new HashMap<String, Object>();
    map.put("states", states);
    stco.addCustomCondition("and state in (:states) ", map);
    stco.addOrder("orgId");
    List<SchoolTeachCircleOrg> list = schoolTeachCircleOrgService.findAll(stco);
    String orgids = ",";
    for (SchoolTeachCircleOrg stcot : list) {
      orgids += stcot.getOrgId() + ",";
    }
    return orgids;
  }

  /**
   * 修改直播课堂附件
   * 
   * @param sa
   * @param resIds
   * @param userSpace
   */
  private void updateZbktClassId(SchoolActivity sa, String resIds, UserSpace userSpace) {
    ClassInfo classInfo = classOperateService.getClassInfo(sa.getClassId());
    List<File> fileList = new ArrayList<File>();
    // 保存参考附件
    String[] attachResIdsArray = resIds.split(",");
    Map<String, List<Resources>> attchInfos = attachService.updateAttach(Attach.XJJY, sa.getId(),
        userSpace.getUserId(), attachResIdsArray);
    for (Resources attachResId : attchInfos.get("add")) {
      fileList.add(new File(resourcesService.download(attachResId.getId())));
    }

    List<Resources> delResources = attchInfos.get("del");
    List<String> delDocIds = new ArrayList<String>();
    for (Resources resources : delResources) {
      delDocIds.add(String.valueOf(resources.getDeviceId()));
    }
    classInfo.setUnBindDocIds(delDocIds);

    if (fileList.size() > 0) {
      // 将文件批量同步到学点云直播平台
      List<String> docIds = classOperateService.uploadDoc(fileList, userSpace.getUserId(), userSpace.getUsername());
      if (!CollectionUtils.isEmpty(docIds)) {
        classInfo.setDocIds(docIds);// 将同步到学点云的文件id加入到课堂
      }
    }
    // 创建课堂
    classInfo = classOperateService.updateClass(classInfo);
    sa.setClassId(classInfo.getId());
  }

  /**
   * 向整理意见表中拷贝一份主备人的教案数据作为原始教案
   * 
   * @param sa
   */
  private void copyZhubei(SchoolActivity sa) {
    UserSpace userSpace = (UserSpace) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE); // 用户空间
    List<LessonPlan> lessonPlanList = lessonPlanService.getJiaoanByInfoId(sa.getInfoId());
    for (LessonPlan lp : lessonPlanList) {
      // 复制教案资源
      String newResId = null;
      newResId = resourcesService.copyRes(lp.getResId());
      SchoolActivityTracks sat = new SchoolActivityTracks();
      sat.setActivityId(sa.getId());
      sat.setPlanId(lp.getPlanId());
      sat.setPlanName(lp.getPlanName());
      sat.setEditType(SchoolActivityTracks.ZHUBEI);
      sat.setLessonId(lp.getLessonId());
      sat.setResId(newResId);
      sat.setHoursId(lp.getHoursId());
      sat.setOrderValue(lp.getOrderValue());
      sat.setUserId(lp.getUserId());
      sat.setUserName(lp.getUserName());
      sat.setSubjectId(lp.getSubjectId());
      sat.setSchoolYear(lp.getSchoolYear());
      sat.setCrtId(userSpace.getUserId());
      sat.setCrtDttm(new Date());
      sat.setLastupDttm(new Date());
      schoolActivityTracksDao.insert(sat);
    }
  }

  /**
   * 发送发布活动的消息
   * 
   * @param activity
   */
  public void sendNoticeOfIssues(SchoolActivity activity) {
    try {
      UserSpace userSpace = (UserSpace) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE); // 用户空间
      Integer userId = activity.getOrganizeUserId();// 发起人id
      String subjectIds = activity.getSubjectIds(); // 0表示没有相应id
      List<Integer> subjectIdList = new ArrayList<Integer>();
      if (StringUtils.isNotEmpty(subjectIds)) {
        subjectIdList = idsStrToIdsList(subjectIds);
      } else {
        subjectIdList.add(0);
      }
      String gradeIds = activity.getGradeIds();
      List<Integer> gradeIdList = new ArrayList<Integer>();
      if (StringUtils.isNotEmpty(gradeIds)) {
        gradeIdList = idsStrToIdsList(gradeIds);
      } else {
        gradeIdList.add(0);
      }
      Integer schoolTeachCircleId = activity.getSchoolTeachCircleId();// 校际教研圈Id
      SchoolTeachCircleOrg stco = new SchoolTeachCircleOrg();
      stco.setStcId(schoolTeachCircleId);
      stco.addCustomCondition(
          " and state in (" + SchoolTeachCircleOrg.YI_TONG_YI + "," + SchoolTeachCircleOrg.YI_HUI_FU + ")",
          new HashMap<String, Object>());
      List<SchoolTeachCircleOrg> listAll = schoolTeachCircleOrgDao.listAll(stco);
      List<Integer> orgIdList = new ArrayList<Integer>();
      if (listAll != null && listAll.size() > 0) {
        for (SchoolTeachCircleOrg sctot : listAll) {
          orgIdList.add(sctot.getOrgId());
        }
      }
      List<UserSpace> userSpacesList = userSpaceDao.getUserSpaceByOrg_Subject_Grade(orgIdList, subjectIdList,
          gradeIdList); // 有参与权限的用户
      Map<String, Object> info = new HashMap<String, Object>();
      info.put("activityId", activity.getId());
      info.put("lessonName", activity.getActivityName());
      info.put("typeName", activity.getTypeName());
      info.put("activityType", "schoolActivity");
      info.put("typeId", activity.getTypeId());
      info.put("fq_role", userSpace.getSpaceName());
      info.put("fq_userName", activity.getOrganizeUserName());
      String fq_startDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(activity.getStartTime());
      info.put("fq_startDate", fq_startDate);
      NoticeType title = NoticeType.ACTIVITY_FB;
      title.setTitlePrefix("校际教研");
      for (UserSpace us : userSpacesList) {
        if (us.getUserId().intValue() != userId.intValue()) { // 不包含发起人自己
          NoticeUtils.sendNotice(title, "发布 " + activity.getActivityName() + " 校际教研",
              activity.getOrganizeUserId(), us.getUserId(), info);
        }
      }
    } catch (Exception e) {
      logger.error("校际教研发布后消息发送失败！", e);
    }
  }

  /**
   * 发送邀请专家消息
   * 
   * @param sa
   */
  private void setYaoQingZhuanjiaMessage(SchoolActivity sa) {
    if (sa != null && StringUtils.isNotEmpty(sa.getExpertIds())) {
      String userIds = sa.getExpertIds();
      String[] userIdArr = userIds.split(",");
      if (userIdArr != null && userIdArr.length > 0) {
        UserSpace userSpace = (UserSpace) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE); // 用户空间
        Map<String, Object> noticeInfo = new HashMap<String, Object>();
        String title = "邀请专家";
        Organization organization = organizationDao.get(userSpace.getOrgId());
        noticeInfo.put("type", 2);
        noticeInfo.put("areaName", organization.getAreaName());
        noticeInfo.put("spaceName", userSpace.getSpaceName());
        noticeInfo.put("userName", userSpace.getUsername());
        noticeInfo.put("typeName", sa.getTypeName());
        noticeInfo.put("activityId", sa.getId());
        noticeInfo.put("activityTypeId", sa.getTypeId());
        noticeInfo.put("activityName", sa.getActivityName());
        for (String userId : userIdArr) {
          if (StringUtils.isNotEmpty(userId)) {
            NoticeUtils.sendNotice(NoticeType.SCHOOL_ACTIVITY_TZ, title, userSpace.getUserId(),
                Integer.parseInt(userId), noticeInfo);
          }
        }
      }
    }
  }

  /**
   * 保存主题研讨的附件
   * 
   * @param id
   * @param resIds
   */
  private void setZtytFj(Integer id, String resIds) {
    UserSpace userSpace = (UserSpace) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE); // 用户空间
    attachService.updateAttach(Attach.XJJY, id, userSpace.getUserId(), resIds.split(","));
  }

  private String getObjectNamesByIds(String ids) {
    String names = "";
    if (StringUtils.isNotEmpty(ids)) {
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
   * 查询专家用户，通过ids
   * 
   * @param expertIds
   * @return
   * @see com.tmser.tr.activity.service.SchoolActivityService#findUserObjectByIds(java.lang.String)
   */
  @Override
  public List<Map<String, Object>> findUserObjectByIds(String expertIds) {
    return schoolActivityDao.findUserObjectByIds(expertIds);
  }

  /**
   * 查询主题研讨活动附件
   * 
   * @param id
   * @return
   * @see com.tmser.tr.activity.service.SchoolActivityService#findActivityAttach(java.lang.Integer)
   */
  @Override
  public List<Attach> findActivityAttach(Integer id) {
    Attach aa = new Attach();
    aa.setActivityId(id);
    aa.setActivityType(Attach.XJJY);
    aa.addOrder("crtDttm desc");
    List<Attach> listAll = attachService.findAll(aa);
    return listAll;
  }

  /**
   * 删除校际教研活动
   * 
   * @param id
   * @see com.tmser.tr.activity.service.SchoolActivityService#deleteActivity(java.lang.Integer)
   */
  @Override
  public String deleteActivity(Integer id) {
    // 该活动下的讨论是否存在
    Discuss rad = new Discuss();
    rad.setActivityId(id);
    rad.setTypeId(ResTypeConstants.SCHOOLTEACH);
    List<Discuss> radList = discussService.findAll(rad);
    if (radList != null && radList.size() > 0) {// 存在讨论不可删除
      return "notdelete";
    } else {
      // 删除活动
      schoolActivityDao.delete(id);
      // 删除教案下的修改留痕
      SchoolActivityTracks schoolActivityTracks = new SchoolActivityTracks();
      schoolActivityTracks.setActivityId(id);
      List<SchoolActivityTracks> ratList = schoolActivityTracksDao.listAll(schoolActivityTracks);
      for (SchoolActivityTracks temp : ratList) {
        schoolActivityTracksDao.delete(temp.getId());
        resourcesService.deleteResources(temp.getResId());
      }
      attachService.deleteAttach(Attach.XJJY, id);
      return "delete";
    }

  }

  /**
   * 将整理好的教案发送给参与人
   * 
   * @param id
   * @see com.tmser.tr.activity.service.SchoolActivityService#sendToJoiner(java.lang.Integer)
   */
  @Override
  public void sendToJoiner(Integer id) {
    SchoolActivity activity = schoolActivityDao.get(id);
    Integer userId = activity.getOrganizeUserId();// 发起人id
    String subjectIdsStr = "0," + activity.getSubjectIds().substring(1, activity.getSubjectIds().length() - 1); // 0表示没有相应id
    List<Integer> subjectIdList = idsStrToIdsList(subjectIdsStr);
    String gradeIdsStr = "0," + activity.getGradeIds().substring(1, activity.getGradeIds().length() - 1);
    List<Integer> gradeIdList = idsStrToIdsList(gradeIdsStr);
    List<Integer> orgIdList = schoolTeachCircleOrgService
        .getJoinOrgIdsByCircleId(activity.getSchoolTeachCircleId());
    List<UserSpace> userSpacesList = userSpaceDao.getUserSpaceByOrg_Subject_Grade(orgIdList, subjectIdList,
        gradeIdList); // 有参与权限的用户
    Map<String, Object> info = new HashMap<String, Object>();
    info.put("activityId", id);
    info.put("lessonName", activity.getActivityName());
    info.put("typeName", activity.getTypeName());
    info.put("activityType", "schoolActivity");
    info.put("isOver", ifActivityIsOver(activity));
    NoticeType title = NoticeType.ACTIVITY_ZL;
    title.setTitlePrefix("校际教研");
    for (UserSpace us : userSpacesList) {
      if (us.getUserId().intValue() != userId.intValue()) { // 不包含发起人自己
        NoticeUtils.sendNotice(title, "整理 " + activity.getActivityName() + " 主备教案",
            activity.getOrganizeUserId(), us.getUserId(), info);
      }
    }
    // 将活动更新为已发送给参与人状态（之后发起人将不可再整理）
    activity.setIsSend(true);
    schoolActivityDao.update(activity);
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
      if (StringUtils.isNotEmpty(id)) {
        idList.add(Integer.valueOf(id));
      }
    }
    return idList;
  }

  /**
   * 判断活动是否已结束，如果已结束则更改状态为“结束”
   * 
   * @param activity
   * @return
   */
  @Override
  public boolean ifActivityIsOver(SchoolActivity activity) {
    if (activity.getIsOver()) {
      return true;
    } else {
      Date nowDate = new Date();
      Date endTime = activity.getEndTime();
      if (endTime != null) {
        if (endTime.before(nowDate)) { // 活动已结束
          activity.setIsOver(true);
          schoolActivityDao.update(activity);
          return true;
        }
      }
      return false;
    }
  }

  /**
   * 此校际教研活动下是否有讨论信息
   * 
   * @param id
   * @return
   * @see com.tmser.tr.activity.service.SchoolActivityService#isDiscuss(java.lang.Integer)
   */
  @Override
  public Boolean isDiscuss(Integer id) {
    Discuss rad = new Discuss();
    rad.setTypeId(ResTypeConstants.SCHOOLTEACH);
    rad.setActivityId(id);
    Discuss one = discussService.findOne(rad);
    if (one != null) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * 判断活动是否被删除，活动是否到了可以参与的时间
   * 
   * @param activityId
   * @return
   * @see com.tmser.tr.activity.service.SchoolActivityService#activityIsDelete(java.lang.Integer)
   */
  @Override
  public Map<String, String> activityIsDelete(Integer activityId) {
    Map<String, String> resultMap = new HashMap<String, String>();
    SchoolActivity sa = schoolActivityDao.get(activityId);
    if (sa != null) {
      Date startTime = sa.getStartTime();
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
      String format = sdf.format(startTime);
      Date nowdate = new Date();
      boolean flag = startTime.before(nowdate);
      if (flag) {
        resultMap.put("state", "ok");
      } else {
        resultMap.put("state", "not-at-time");
        resultMap.put("msg", "该活动还未开始，请于" + format + "来准时参加活动");
      }
    } else {
      resultMap.put("state", "delete");
    }
    return resultMap;
  }

  @Override
  public List<UserSpace> findUserBySubjectAndGrade(SchoolActivity activity) {
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

  /**
   * 上传同辈教案的修改课件的保存
   * 
   * @param sat
   */
  @Override
  public void schKjSave(SchoolActivityTracks sat) {
    UserSpace us = (UserSpace) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE); // 用户空间
    sat.setPlanId(0);
    sat.setUserId(us.getUserId());
    sat.setUserName(us.getUsername());
    sat.setSubjectId(us.getSubjectId());
    sat.setSchoolYear(us.getSchoolYear());
    sat.setCrtId(us.getUserId());
    sat.setCrtDttm(new Date());
    sat.setLastupDttm(new Date());
    sat.setOrgId(us.getOrgId());
    sat.setGradeId(us.getGradeId());
    sat.setSpaceId(us.getId());
    sat = schoolActivityTracksService.save(sat);
    resourcesService.updateTmptResources(sat.getResId());
  }
}
