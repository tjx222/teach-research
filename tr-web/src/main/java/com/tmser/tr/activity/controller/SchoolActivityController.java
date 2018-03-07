package com.tmser.tr.activity.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tmser.tr.activity.bo.SchoolActivity;
import com.tmser.tr.activity.bo.SchoolActivityTracks;
import com.tmser.tr.activity.bo.SchoolTeachCircle;
import com.tmser.tr.activity.bo.SchoolTeachCircleOrg;
import com.tmser.tr.activity.service.ActivityCoordinateControlService;
import com.tmser.tr.activity.service.ClassinfoService;
import com.tmser.tr.activity.service.SchoolActivityService;
import com.tmser.tr.activity.service.SchoolActivityTracksService;
import com.tmser.tr.activity.service.SchoolTeachCircleOrgService;
import com.tmser.tr.activity.service.SchoolTeachCircleService;
import com.tmser.tr.bjysdk.model.BJYClassStatusResult;
import com.tmser.tr.classapi.ChatInfo;
import com.tmser.tr.classapi.ClassUserType;
import com.tmser.tr.classapi.bo.ClassInfo;
import com.tmser.tr.classapi.bo.ClassUser;
import com.tmser.tr.classapi.service.ClassOperateService;
import com.tmser.tr.comment.bo.Discuss;
import com.tmser.tr.comment.service.DiscussService;
import com.tmser.tr.common.ResTypeConstants;
import com.tmser.tr.common.annotation.UseToken;
import com.tmser.tr.common.page.Page;
import com.tmser.tr.common.page.PageList;
import com.tmser.tr.common.utils.WebThreadLocalUtils;
import com.tmser.tr.common.vo.Result;
import com.tmser.tr.common.web.controller.AbstractController;
import com.tmser.tr.lessonplan.bo.LessonInfo;
import com.tmser.tr.lessonplan.bo.LessonPlan;
import com.tmser.tr.lessonplan.service.LessonInfoService;
import com.tmser.tr.manage.meta.Meta;
import com.tmser.tr.manage.meta.MetaUtils;
import com.tmser.tr.manage.org.bo.Organization;
import com.tmser.tr.manage.org.service.OrganizationService;
import com.tmser.tr.manage.resources.bo.Attach;
import com.tmser.tr.manage.resources.service.AttachService;
import com.tmser.tr.manage.resources.service.ResourcesService;
import com.tmser.tr.uc.SysRole;
import com.tmser.tr.uc.bo.UserSpace;
import com.tmser.tr.uc.service.UserService;
import com.tmser.tr.uc.service.UserSpaceService;
import com.tmser.tr.uc.utils.CurrentUserContext;
import com.tmser.tr.uc.utils.SessionKey;
import com.tmser.tr.utils.StringUtils;
import com.tmser.tr.writelessonplan.service.LessonPlanService;
import com.zhuozhengsoft.pageoffice.FileSaver;
import com.zhuozhengsoft.pageoffice.OpenModeType;
import com.zhuozhengsoft.pageoffice.PageOfficeCtrl;

/**
 * <pre>
 * 校际教研活动controller
 * </pre>
 * 
 * @author zpp
 * @version $Id: SchoolActivityController.java, v 1.0 2015年5月20日 上午10:17:31 zpp
 *          Exp $
 */
@Controller
@RequestMapping("/jy/schoolactivity")
public class SchoolActivityController extends AbstractController {

  private static final Logger logger = LoggerFactory.getLogger(SchoolActivityController.class);

  @Resource
  private SchoolActivityService schoolActivityService;
  @Resource
  private SchoolTeachCircleService schoolTeachCircleService;
  @Resource
  private UserService userService;
  @Autowired
  private SchoolTeachCircleOrgService schoolTeachCircleOrgService;
  @Resource
  private ResourcesService resourcesService;
  @Autowired
  private OrganizationService organizationService;
  @Autowired
  private AttachService activityAttachService;
  @Autowired
  private SchoolActivityTracksService schoolActivityTracksService;
  @Autowired
  private ActivityCoordinateControlService activityCoordinateControlService;
  @Resource(name = "classApi")
  private ClassOperateService classOperateService;
  @Autowired
  private LessonInfoService lessonInfoService;
  @Autowired
  private DiscussService discussService;
  @Autowired
  private UserSpaceService userSpaceService;
  @Autowired
  private LessonPlanService lessonPlanervice;
  @Autowired
  private ClassinfoService classinfoService;

  /**
   * 各个管理空间-进入校际教研界面
   * 
   * @return
   */
  @RequestMapping("/index")
  public String indexSchoolTeach(Model model, Page page, SchoolActivity sa, Integer listType) {
    page.setPageSize(15);
    sa.addPage(page);
    Map<String, Object> returnMap = schoolActivityService.findSchoolActivity(sa, listType);
    String pageUrl = (String) returnMap.get("pageUrl");
    model.addAttribute("listPage", returnMap.get("listPage"));
    model.addAttribute("role", returnMap.get("role"));
    model.addAttribute("listType", returnMap.get("listType"));
    model.addAttribute("activityDraftNum", returnMap.get("activityDraftNum"));
    return pageUrl;
  }

  @RequestMapping("/queryClassInfo")
  public @ResponseBody ClassInfo queryClassInfo(SchoolActivity schoolActivity) {
    if (schoolActivity == null || schoolActivity.getId() == 0) {
      return null;
    }
    SchoolActivity old_sa = schoolActivityService.findOne(schoolActivity.getId());
    if (old_sa == null || StringUtils.isEmpty(old_sa.getClassId())) {
      return null;
    }
    ClassInfo classInfo = classinfoService.findOne(old_sa.getClassId());
    return classInfo;
  }

  /**
   * 校际教研草稿箱
   * 
   * @return
   */
  @RequestMapping("/indexDraft")
  public String indexDraft(SchoolActivity sa, Model model) {
    PageList<SchoolActivity> activityDraftList = schoolActivityService.findSchoolActivityDraf(sa);
    model.addAttribute("activityDraftList", activityDraftList);
    return "/schoolactivity/draftListPage";
  }

  /**
   * 删除校际教研活动草稿
   */
  @RequestMapping("/delSchoolActivity")
  public String delSchoolActivity(SchoolActivity sa, Model m) {
    Boolean isOk = true;
    try {
      schoolActivityService.delete(sa.getId());
    } catch (Exception e) {
      logger.error("校际教研草稿箱删除出现错误！", e);
      isOk = false;
    }
    m.addAttribute("isOk", isOk);
    return "";
  }

  /**
   * 删除校际教研活动
   */
  @RequestMapping("/deleteActivity")
  public String deleteActivity(SchoolActivity sa, Model m) {
    Boolean isSuccess = true;
    try {
      String deleteActivity = schoolActivityService.deleteActivity(sa.getId());
      m.addAttribute("deleteState", deleteActivity);
    } catch (Exception e) {
      logger.error("校际教研活动删除出现错误！", e);
      isSuccess = false;
    }
    m.addAttribute("isSuccess", isSuccess);
    return "";
  }

  /**
   * 删除校际教研活动
   */
  @RequestMapping("/deleteActivity_m")
  @ResponseBody
  public Result deleteActivity_m(SchoolActivity sa) {
    Result result = new Result();
    try {
      String deleteActivity = schoolActivityService.deleteActivity(sa.getId());
      if ("notdelete".equals(deleteActivity)) {
        result.setCode(0);
        result.setMsg("此教研活动不存在");
      }
      if ("delete".equals(deleteActivity)) {
        result.setCode(1);
        result.setMsg("删除成功");
      }
    } catch (Exception e) {
      logger.error("校际教研活动删除出现错误！", e);
      result.setCode(2);
      result.setMsg("校际教研活动删除出现错误");
    }
    return result;
  }

  /**
   * 发起校际教研活动（1:同备教案 2:主题研讨 3:视频研讨 4:直播课堂）
   */
  @RequestMapping("/fqSchoolActivity")
  @UseToken
  public String fqSchoolActivity(SchoolActivity sa, Model m) {
    UserSpace userSpace = (UserSpace) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE);
    // 查询校际教研圈
    List<SchoolTeachCircle> stcList = schoolTeachCircleService.findCircleByOrg();
    m.addAttribute("stcList", stcList);
    // 学科
    List<Meta> subjectList = this.getSchoolSubjectList(userSpace);
    // 年级
    List<Meta> gradeList = this.getSchoolGradeList(userSpace);
    m.addAttribute("subjectList", subjectList);
    m.addAttribute("gradeList", gradeList);
    sa.setStartTime(new Date());
    if (sa.getId() != null) {
      sa = schoolActivityService.findOne(sa.getId());
    }
    m.addAttribute("act", sa);
    m.addAttribute("userSpace", userSpace);
    return "/schoolactivity/fq_schoolactivity_index";
  }

  /**
   * 教研学科列表
   * 
   * @param userSpace
   * @param subjectList
   */
  private List<Meta> getSchoolSubjectList(UserSpace userSpace) {
    List<Meta> subjectList = new ArrayList<Meta>();
    if (userSpace.getSubjectId() != null && userSpace.getSubjectId() != 0 && userSpace.getGradeId() != null
        && userSpace.getGradeId() != 0) {
      Meta sd = MetaUtils.getMeta(userSpace.getSubjectId());
      subjectList.add(sd);
    } else {
      Organization org = organizationService.findOne(userSpace.getOrgId());
      Integer[] areas = StringUtils.toIntegerArray(org.getAreaIds().substring(1, org.getAreaIds().lastIndexOf(",")),
          ",");
      subjectList = MetaUtils.getPhaseSubjectMetaProvider().listAllSubject(org.getId(), userSpace.getPhaseId(), areas);
    }
    return subjectList;
  }

  /**
   * 教研年级列表
   * 
   * @param userSpace
   * @param gradeList
   */
  @RequestMapping("/mainGradeList")
  @ResponseBody
  public List<Meta> getSchoolGradeList(UserSpace userSpace) {
    List<Meta> gradeList = new ArrayList<Meta>();
    if (userSpace.getSubjectId() != null && userSpace.getSubjectId() != 0 && userSpace.getGradeId() != null
        && userSpace.getGradeId() != 0) {
      Meta sd = MetaUtils.getMeta(userSpace.getGradeId());
      gradeList.add(sd);
    } else {
      if (SysRole.JYZR.getId().equals(userSpace.getSysRoleId()) || SysRole.JYY.getId().equals(userSpace.getSysRoleId())) {
        gradeList = MetaUtils.getPhaseGradeMetaProvider().listAllGradeByPhaseId(userSpace.getPhaseId());
      } else {
        Organization org = organizationService.findOne(userSpace.getOrgId());
        gradeList = MetaUtils.getOrgTypeMetaProvider().listAllGrade(org.getSchoolings(), userSpace.getPhaseId());
      }
    }

    return gradeList;
  }

  /**
   * 加载主备人列表
   * 
   * @return
   */
  @RequestMapping("/mainUserList")
  public List<UserSpace> userList(Integer subjectId, Integer gradeId, Integer orgId) {
    List<UserSpace> mainUserList = null;
    if (subjectId != null && gradeId != null) {
      UserSpace us = new UserSpace();
      us.setGradeId(gradeId);
      us.setSubjectId(subjectId);
      us.setOrgId(orgId);
      us.setSysRoleId(SysRole.TEACHER.getId());// 教师身份
      mainUserList = schoolActivityService.findMainUserList(us);
    }
    return mainUserList;
  }

  /**
   * 加载主备课题列表
   * 
   * @return
   */
  @RequestMapping("/chapterList")
  public List<LessonInfo> chapterList(Integer userId, Integer subjectId, Integer gradeId) {
    List<LessonInfo> chapterList = null;
    if (userId != null) {
      chapterList = schoolActivityService.findChapterList(userId, subjectId, gradeId);
    }
    return chapterList;
  }

  /**
   * 查询用户作为临时的专家
   */
  @RequestMapping("/searchUsers")
  public String searchUsers(String userName, Model m) {
    List<Map<String, Object>> userObjectList = schoolActivityService.findUserObjectByName(userName);
    m.addAttribute("userObjectList", userObjectList);
    return "";
  }

  /**
   * 保存 校际教研集体备课活动-同备教案-主题研讨-直播课堂
   * 
   * @return
   */
  @RequestMapping("/saveSchoolActivityTbja")
  @UseToken
  public Map<String, Integer> saveSchoolActivityTbja(SchoolActivity sa, String resIds, Boolean haveDiscuss,
      Boolean haveTrack) {
    Integer resultCode = 0;
    try {
      resultCode = schoolActivityService.saveOrUpdateSchoolActivity(sa, resIds, haveDiscuss, haveTrack);
    } catch (Exception e) {
      logger.error("--- 校际教研保存操作异常 ---", e);
    }
    Map<String, Integer> map = new HashMap<String, Integer>();
    map.put("resultCode", resultCode);
    return map;
  }

  /**
   * 分享-取消分享 教研活动
   * 
   * @return
   */
  @RequestMapping("/sharingActivity")
  public String sharingActivity(SchoolActivity sa, Model m) {
    Boolean isSuccess = true;
    try {
      sa.setShareTime(new Date());
      schoolActivityService.update(sa);
    } catch (Exception e) {
      isSuccess = false;
      logger.error("--- 校际教研分享操作异常 ---", e);
    }
    m.addAttribute("isSuccess", isSuccess);
    return "";
  }

  /**
   * 修改校际教研集体备课活动-（同备教案、主题研讨、视频研讨、直播课堂）
   * 
   * @return
   */
  @RequestMapping("/editActivity")
  @UseToken
  public String editActivity(SchoolActivity sa, Model m) {
    UserSpace userSpace = (UserSpace) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE);
    // 查询校际教研圈
    List<SchoolTeachCircle> stcList = schoolTeachCircleService.findCircleByOrg();
    m.addAttribute("stcList", stcList);
    // 是否有讨论信息
    Boolean isDiscuss = schoolActivityService.isDiscuss(sa.getId());
    m.addAttribute("isDiscuss", isDiscuss);
    // 学科
    List<Meta> subjectList = this.getSchoolSubjectList(userSpace);
    // 年级
    List<Meta> gradeList = this.getSchoolGradeList(userSpace);
    m.addAttribute("subjectList", subjectList);
    m.addAttribute("gradeList", gradeList);
    SchoolActivity schoolActivity = schoolActivityService.findOne(sa.getId());
    if (schoolActivity != null) {
      if (schoolActivity.getStartTime() == null) {
        schoolActivity.setStartTime(new Date());
      }
      m.addAttribute("act", schoolActivity);
      String expertIds = schoolActivity.getExpertIds();
      if (expertIds != null && !"".equals(expertIds)) {
        List<Map<String, Object>> userObjectList = schoolActivityService.findUserObjectByIds(expertIds);
        m.addAttribute("userObjectList", userObjectList);
      }

      m.addAttribute("isAudit", true);
      if ((sa.getTypeId() != null && sa.getTypeId().equals(1))
          || (sa.getTypeId() == null && schoolActivity.getTypeId().equals(1))) {
        UserSpace us = new UserSpace();
        us.setGradeId(schoolActivity.getMainUserGradeId());
        us.setSubjectId(schoolActivity.getMainUserSubjectId());
        us.setOrgId(schoolActivity.getMainUserOrgId());
        List<UserSpace> mainUserList = schoolActivityService.findMainUserList(us);
        m.addAttribute("mainUserList", mainUserList);
        List<LessonInfo> chapterList = schoolActivityService.findChapterList(schoolActivity.getMainUserId(),
            schoolActivity.getMainUserSubjectId(), schoolActivity.getMainUserGradeId());
        m.addAttribute("chapterList", chapterList);
        // 是否有整理或意见留痕(如果有则只允许修改部分数据)
        Integer trackCount = schoolActivityTracksService.getTrackCountByActivityId(sa.getId());
        if (trackCount.intValue() > 0) {
          m.addAttribute("haveTrack", true);
        } else {
          m.addAttribute("haveTrack", false);
        }
        m.addAttribute("userSpace", userSpace);
      } else {
        // 查询附件
        List<Attach> fjList = schoolActivityService.findActivityAttach(schoolActivity.getId());
        m.addAttribute("fjList", fjList);
      }
    }
    return "/schoolactivity/fq_schoolactivity_index";
  }

  /**
   * 参与校际教研——同备教案
   * 
   * @param id
   * @param m
   * @return
   */
  @RequestMapping("/joinTbjaSchoolActivity")
  public String joinTbjaSchoolActivity(Integer id, Model m, Integer listType) {

    UserSpace userSpace = (UserSpace) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE); // 用户空间
    SchoolActivity schoolActivity = schoolActivityService.findOne(id);
    Assert.isTrue(ifHaveJoinPower(schoolActivity, "join"), "没有权限");
    m.addAttribute("listType", listType);
    // 获取主备人的教案
    List<SchoolActivityTracks> zhubeiList = schoolActivityTracksService.getActivityTracks_zhubei(id);
    m.addAttribute("zhubeiList", zhubeiList);
    m.addAttribute("activity", schoolActivity);
    if (schoolActivity.getInfoId() != null) {
      LessonInfo lessonInfo = lessonInfoService.findOne(schoolActivity.getInfoId());
      m.addAttribute("lessonInfo", lessonInfo);
      // 获得该课题下的该人的课件反思集合
      LessonPlan lp = new LessonPlan();
      lp.setInfoId(schoolActivity.getInfoId());
      lp.buildCondition(" and (planType=1 or planType=2)");
      lp.addOrder("planType");
      lp.addCustomCulomn("resId,planType");
      m.addAttribute("lplist", lessonPlanervice.findAll(lp));
    }
    m.addAttribute("user1", userService.findOne(schoolActivity.getMainUserId()));
    m.addAttribute("user2", userService.findOne(schoolActivity.getOrganizeUserId()));
    m.addAttribute("us", userSpaceService.findOne(schoolActivity.getSpaceId()));
    // 获取所有参与的学校的名称
    String joinOrgNames = schoolTeachCircleOrgService
        .getJoinOrgNamesByCircleId(schoolActivity.getSchoolTeachCircleId());
    m.addAttribute("joinOrgNames", joinOrgNames);
    if (StringUtils.isNotEmpty(joinOrgNames)) {
      String[] joinOrgs = joinOrgNames.split("、");
      m.addAttribute("joinOrgNames", joinOrgs);
      m.addAttribute("joinOrgLength", joinOrgs.length);
    } else {
      m.addAttribute("joinOrgLength", 0);
    }
    // 整理教案集合
    List<SchoolActivityTracks> zhengliList = schoolActivityTracksService.getActivityTracks_zhengli(id);
    m.addAttribute("zhengliList", zhengliList);
    // 是否可以接收教案
    if (userSpace.getSysRoleId().intValue() == SysRole.TEACHER.getId().intValue()
        && userSpace.getGradeId().intValue() == schoolActivity.getMainUserGradeId().intValue()
        && schoolActivity.getIsSend()) {
      m.addAttribute("canReceive", true);
    }
    m.addAttribute("operateType", 1);
    m.addAttribute("activityType", ResTypeConstants.SCHOOLTEACH);
    if (userSpace.getUserId().intValue() == schoolActivity.getMainUserId().intValue()) { // 如果是主备人,必须是老师才可进入整理页
      if (userSpace.getSysRoleId().intValue() == SysRole.TEACHER.getId().intValue()
          && schoolActivity.getMainUserGradeId().intValue() == userSpace.getGradeId().intValue()
          && schoolActivity.getMainUserSubjectId().intValue() == userSpace.getSubjectId().intValue()) {
        m.addAttribute("zhengLiTongBei", true);
        return "/schoolactivity/activityJoinView";
      } else {
        return "/schoolactivity/activityJoinView";
      }
    } else {
      if (schoolActivityService.ifActivityIsOver(schoolActivity)) {
        return "redirect:/jy/schoolactivity/viewTbjaSchoolActivity?_no_office_&id=" + id;
      }
      return "/schoolactivity/activityJoinView";
    }
  }

  /**
   * 查看集备跳转判断
   * 
   * @return
   */
  @RequestMapping("/views")
  public String views(Integer activityId) {
    SchoolActivity activity = schoolActivityService.findOne(activityId);
    if (activity != null) {
      if (activity.getTypeId().intValue() == SchoolActivity.TBJA.intValue()) {// 同备教案
        return "redirect:/jy/schoolactivity/viewTbjaSchoolActivity?id=" + activityId;
      } else if (activity.getTypeId().intValue() == SchoolActivity.ZTYT.intValue()) {// 主题研讨
        return "redirect:/jy/schoolactivity/viewZtytSchoolActivity?id=" + activityId;
      }
    }
    return "";
  }

  /**
   * 查看集备教案
   * 
   * @param id
   * @param m
   * @return
   */
  @RequestMapping("/viewTbjaSchoolActivity")
  public String viewTbjaSchoolActivity(Integer id, Model m, Integer listType) {

    UserSpace userSpace = (UserSpace) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE); // 用户空间
    SchoolActivity schoolActivity = schoolActivityService.findOne(id);
    m.addAttribute("listType", listType);
    // 如果是主备人,即使活动结束，也能进入整理页，但必须是老师才可进入整理页（身份区别）
    if (userSpace.getUserId().intValue() == schoolActivity.getMainUserId().intValue()) {
      if (userSpace.getSysRoleId().intValue() == SysRole.TEACHER.getId().intValue()
          && schoolActivity.getMainUserGradeId().intValue() == userSpace.getGradeId().intValue()
          && schoolActivity.getMainUserSubjectId().intValue() == userSpace.getSubjectId().intValue()) {
        return "redirect:/jy/schoolactivity/joinTbjaSchoolActivity?_no_office_&id=" + id;
      }
    }
    // 判断是否有权限
    Assert.isTrue(ifHaveJoinPower(schoolActivity, "view"), "没有权限");
    if (schoolTeachCircleOrgService.ifMatchTheCircleByStates(userSpace.getOrgId(),
        schoolActivity.getSchoolTeachCircleId(), new Integer[] { SchoolTeachCircleOrg.YI_TUI_CHU })) {
      schoolActivity.setIsTuiChu(true);
    }
    // 获取主备人的教案
    List<SchoolActivityTracks> zhubeiList = schoolActivityTracksService.getActivityTracks_zhubei(id);
    m.addAttribute("zhubeiList", zhubeiList);
    // 获取所有参与的学校的名称
    String joinOrgNames = schoolTeachCircleOrgService
        .getJoinOrgNamesByCircleId(schoolActivity.getSchoolTeachCircleId());
    if (StringUtils.isNotEmpty(joinOrgNames)) {
      String[] joinOrgs = joinOrgNames.split("、");
      m.addAttribute("joinOrgNames", joinOrgs);
      m.addAttribute("joinOrgLength", joinOrgs.length);
    } else {
      m.addAttribute("joinOrgLength", 0);
    }
    m.addAttribute("activity", schoolActivity);
    if (schoolActivity.getInfoId() != null) {
      LessonInfo lessonInfo = lessonInfoService.findOne(schoolActivity.getInfoId());
      m.addAttribute("lessonInfo", lessonInfo);
      // 获得该课题下的该人的课件反思集合
      LessonPlan lp = new LessonPlan();
      lp.setInfoId(schoolActivity.getInfoId());
      lp.buildCondition(" and (planType=1 or planType=2)");
      lp.addOrder("planType");
      lp.addCustomCulomn("resId,planType");
      m.addAttribute("lplist", lessonPlanervice.findAll(lp));
    }
    m.addAttribute("user1", userService.findOne(schoolActivity.getMainUserId()));
    m.addAttribute("user2", userService.findOne(schoolActivity.getOrganizeUserId()));
    m.addAttribute("us", userSpaceService.findOne(schoolActivity.getSpaceId()));
    // 整理教案集合
    List<SchoolActivityTracks> zhengliList = schoolActivityTracksService.getActivityTracks_zhengli(id);
    m.addAttribute("zhengliList", zhengliList);
    // 是否可以接收教案
    if (userSpace.getSysRoleId().intValue() == SysRole.TEACHER.getId().intValue()
        && userSpace.getGradeId().intValue() == schoolActivity.getMainUserGradeId().intValue()
        && schoolActivity.getIsSend()) {
      m.addAttribute("canReceive", true);
    }
    m.addAttribute("operateType", 0);
    m.addAttribute("activityType", ResTypeConstants.SCHOOLTEACH);
    return "/schoolactivity/activityJoinView";
  }

  /**
   * 参与主题研讨,视频研讨
   * 
   * @param id
   * @param m
   * @return
   */
  @RequestMapping("/joinZtytSchoolActivity")
  public String joinZtytSchoolActivity(Integer id, Model m, Integer listType) {
    SchoolActivity schoolActivity = schoolActivityService.findOne(id);
    if (schoolActivityService.ifActivityIsOver(schoolActivity)) {
      return "redirect:/jy/schoolactivity/viewZtytSchoolActivity?id=" + id + "&listType=" + listType;
    }
    // 判断是否有权限
    Assert.isTrue(ifHaveJoinPower(schoolActivity, "join"), "没有权限");
    Attach temp = new Attach();
    temp.setActivityId(id);
    temp.setActivityType(Attach.XJJY);
    List<Attach> attachList = activityAttachService.findAll(temp);
    // 有权限的参与人列表查询
    List<UserSpace> usList = schoolActivityService.findUserBySubjectAndGrade(schoolActivity);
    m.addAttribute("usList", usList);
    m.addAttribute("activity", schoolActivity);
    m.addAttribute("attachList", attachList);
    // 获取所有参与的学校的名称
    String joinOrgNames = schoolTeachCircleOrgService
        .getJoinOrgNamesByCircleId(schoolActivity.getSchoolTeachCircleId());
    if (StringUtils.isNotEmpty(joinOrgNames)) {
      String[] joinOrgs = joinOrgNames.split("、");
      m.addAttribute("joinOrgNames", joinOrgs);
      m.addAttribute("joinOrgLength", joinOrgs.length);
    } else {
      m.addAttribute("joinOrgLength", 0);
    }
    m.addAttribute("user2", userService.findOne(schoolActivity.getOrganizeUserId()));
    m.addAttribute("us", userSpaceService.findOne(schoolActivity.getSpaceId()));
    m.addAttribute("listType", listType);
    m.addAttribute("operateType", 1);
    m.addAttribute("activityType", ResTypeConstants.SCHOOLTEACH);
    return "/schoolactivity/activityJoinView";
  }

  /**
   * 查看主题研讨
   * 
   * @param id
   * @param m
   * @return
   */
  @RequestMapping("/viewZtytSchoolActivity")
  public String viewZtytSchoolActivity(Integer id, Model m, Integer listType) {
    SchoolActivity schoolActivity = schoolActivityService.findOne(id);
    // 判断是否有权限
    Assert.isTrue(ifHaveJoinPower(schoolActivity, "view"), "没有权限");
    UserSpace userSpace = (UserSpace) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE); // 用户空间
    if (schoolTeachCircleOrgService.ifMatchTheCircleByStates(userSpace.getOrgId(),
        schoolActivity.getSchoolTeachCircleId(), new Integer[] { SchoolTeachCircleOrg.YI_TUI_CHU })) {
      schoolActivity.setIsTuiChu(true);
    }
    Attach temp = new Attach();
    temp.setActivityId(id);
    List<Attach> attachList = activityAttachService.findAll(temp);
    // 有权限的参与人列表查询
    List<UserSpace> usList = schoolActivityService.findUserBySubjectAndGrade(schoolActivity);
    m.addAttribute("usList", usList);
    m.addAttribute("activity", schoolActivity);
    m.addAttribute("attachList", attachList);
    m.addAttribute("userSpace", userSpace);
    // 获取所有参与的学校的名称
    String joinOrgNames = schoolTeachCircleOrgService
        .getJoinOrgNamesByCircleId(schoolActivity.getSchoolTeachCircleId());
    if (StringUtils.isNotEmpty(joinOrgNames)) {
      String[] joinOrgs = joinOrgNames.split("、");
      m.addAttribute("joinOrgNames", joinOrgs);
      m.addAttribute("joinOrgLength", joinOrgs.length);
    } else {
      m.addAttribute("joinOrgLength", 0);
    }
    m.addAttribute("user2", userService.findOne(schoolActivity.getOrganizeUserId()));
    m.addAttribute("us", userSpaceService.findOne(schoolActivity.getSpaceId()));
    m.addAttribute("listType", listType);
    m.addAttribute("operateType", 0);
    m.addAttribute("activityType", ResTypeConstants.SCHOOLTEACH);
    return "/schoolactivity/activityJoinView";
  }

  /**
   * 无权限用户查看校际教研
   * 
   * @param id
   * @param m
   * @return
   */
  @RequestMapping("/onlyViewSchoolActivity")
  public String onlyViewSchoolActivity(Integer id, Model m) {
    SchoolActivity schoolActivity = schoolActivityService.findOne(id);
    if (schoolActivity.getTypeId().intValue() == SchoolActivity.TBJA.intValue()) {
      m.addAttribute("url", "/jy/schoolactivity/onlyViewSchoolActivity1?id=" + id);
      return "/resview/pageofficeOpenWindow";
    } else if ((schoolActivity.getTypeId().intValue() == SchoolActivity.ZTYT.intValue())) {
      Attach temp = new Attach();
      temp.setActivityId(id);
      List<Attach> attachList = activityAttachService.findAll(temp);
      m.addAttribute("activity", schoolActivity);
      m.addAttribute("attachList", attachList);
      // 获取所有参与的学校的名称
      String joinOrgNames = schoolTeachCircleOrgService.getJoinOrgNamesByCircleId(schoolActivity
          .getSchoolTeachCircleId());
      m.addAttribute("joinOrgNames", joinOrgNames);
      return "/schoolactivity/onlyChaKanZhuYan";
    }
    return "";
  }

  /**
   * 无权限用户查看校际教研
   * 
   * @param id
   * @param m
   * @return
   */
  @RequestMapping("/onlyViewSchoolActivity1")
  public String onlyViewSchoolActivity1(Integer id, Model m) {

    SchoolActivity schoolActivity = schoolActivityService.findOne(id);
    // 获取主备人的教案
    List<SchoolActivityTracks> zhubeiList = schoolActivityTracksService.getActivityTracks_zhubei(id);
    m.addAttribute("zhubeiList", zhubeiList);
    // 获取所有参与的学校的名称
    String joinOrgNames = schoolTeachCircleOrgService
        .getJoinOrgNamesByCircleId(schoolActivity.getSchoolTeachCircleId());
    m.addAttribute("joinOrgNames", joinOrgNames);
    m.addAttribute("activity", schoolActivity);
    // 整理教案集合
    List<SchoolActivityTracks> zhengliList = schoolActivityTracksService.getActivityTracks_zhengli(id);
    m.addAttribute("zhengliList", zhengliList);
    return "/schoolactivity/onlyChaKanJiBei";
  }

  /**
   * 显示主备教案的整理教案或意见教案
   * 
   * @param planId
   * @param activityId
   * @param request
   * @param editType
   *          修改类型， 整理or意见
   * @return
   */
  @RequestMapping("/showLessonPlanTracks")
  public String showLessonPlanTracks(Integer planId, Integer activityId, Integer editType, HttpServletRequest request,
      Model m) {
    SchoolActivityTracks track = schoolActivityTracksService.openTracksFileOfLessonPlan(planId, activityId, request,
        editType, m);
    if (track.getEditType().intValue() == SchoolActivityTracks.ZHUBEI.intValue()) {
      m.addAttribute("trackId", "");
    } else if (track.getEditType().intValue() == SchoolActivityTracks.ZHENGLI.intValue()
        || track.getEditType().intValue() == SchoolActivityTracks.YIJIAN.intValue()) {
      try {
        m.addAttribute("trackId", track.getId());
      } catch (Exception e) {
        logger.error("显示主备教案的整理教案或修改教案出错", e);
      }
    }
    m.addAttribute("planId", planId);
    m.addAttribute("activityId", activityId);
    m.addAttribute("editType", editType);
    return "/schoolactivity/showLessonPlan_edit";
  }

  /**
   * 显示主备教案
   * 
   * @param planId
   * @param request
   * @return
   */
  @RequestMapping("/showLessonPlan")
  public String showLessonPlan(Integer planId, HttpServletRequest request) {
    PageOfficeCtrl poc = new PageOfficeCtrl(request);
    poc.setServerPage(request.getContextPath() + "/poserver.zz");
    poc.setCaption("主备教案");
    SchoolActivityTracks track = schoolActivityTracksService.findOne(planId);
    schoolActivityTracksService.openWordFileByRevision(track.getResId(), poc, OpenModeType.docReadOnly);
    return "/schoolactivity/showLessonPlan_edit";
  }

  /**
   * 获取主备教案的意见教案集合
   * 
   * @param activityId
   * @return
   */
  @RequestMapping("/getYijianTrackList")
  public String getYijianTrackList(Integer planId, Integer activityId, Model m) {
    List<SchoolActivityTracks> yijianList = schoolActivityTracksService.getActivityTracks_yijian(planId, activityId);
    m.addAttribute("yijianList", yijianList);
    return "/schoolactivity/tracks_yijianList";
  }

  /**
   * 获取主备教案的整理教案合集
   * 
   * @param planId
   * @param activityId
   * @param m
   * @return
   */
  @RequestMapping("/getZhengliTrackList")
  public String getZhengliTrackList(Integer activityId, Model m) {
    List<SchoolActivityTracks> zhengliList = schoolActivityTracksService.getActivityTracks_zhengli(activityId);
    m.addAttribute("zhengliList", zhengliList);
    return "/schoolactivity/tracks_zhengliList";
  }

  /**
   * 保存修改教案（教案意见或教案整理）
   * 
   * @param request
   * @param response
   */
  @RequestMapping("/saveLessonPlanTracks")
  public void saveLessonPlanTracks(HttpServletRequest request, HttpServletResponse response) {
    FileSaver fs = new FileSaver(request, response);
    Integer trackId = schoolActivityTracksService.saveLessonPlanTracks(fs);
    if (trackId != null) {
      fs.setCustomSaveResult(String.valueOf(trackId));
    }
    fs.close();
  }

  /**
   * 浏览集备教案的修改教案（留痕模式）
   * 
   * @param trackId
   * @param request
   * @return
   */
  @RequestMapping("/scanLessonPlanTrack")
  public String scanLessonPlanTrack(String resId, HttpServletRequest request, Integer orgId, Model m) {

    PageOfficeCtrl poc = new PageOfficeCtrl(request);
    poc.setServerPage(request.getContextPath() + "/poserver.zz");
    schoolActivityTracksService.openWordFileByRevision(resId, poc, OpenModeType.docRevisionOnly);
    m.addAttribute("resId", resId);
    m.addAttribute("orgId", orgId);
    return "/schoolactivity/track_read";
  }

  /**
   * 结束校际教研活动
   * 
   * @param activity
   * @param m
   */
  @RequestMapping("/overActivity")
  public void overActivity(SchoolActivity activity, Model m) {
    activity.setIsOver(true);
    try {
      schoolActivityService.update(activity);
      m.addAttribute("result", "success");
    } catch (Exception e) {
      logger.error("结束校际教研活动出错", e);
      m.addAttribute("result", "fail");
    }
  }

  /**
   * 判断是否有参与权限
   * 
   * @param schoolActivity
   * @param flag
   * @return
   */
  private boolean ifHaveJoinPower(SchoolActivity schoolActivity, String flag) {
    UserSpace userSpace = (UserSpace) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE); // 用户空间
    Integer roleId = userSpace.getSysRoleId();
    // 如果是专家则跳过验证
    if (schoolActivity.getExpertIds() != null
        && schoolActivity.getExpertIds().contains("," + userSpace.getUserId() + ",")) {
      return true;
    }
    // 用户所在机构是否满足活动指定的教研圈
    boolean isMatchTheCircle = false;
    if (roleId.intValue() != SysRole.JYZR.getId().intValue() && roleId.intValue() != SysRole.JYY.getId().intValue()) {
      if ("join".equals(flag)) {
        isMatchTheCircle = schoolTeachCircleOrgService.ifMatchTheCircleByStates(userSpace.getOrgId(),
            schoolActivity.getSchoolTeachCircleId(), new Integer[] { SchoolTeachCircleOrg.YI_TONG_YI,
                SchoolTeachCircleOrg.YI_HUI_FU });
      } else if ("view".equals(flag)) {
        isMatchTheCircle = schoolTeachCircleOrgService.ifMatchTheCircleByStates(userSpace.getOrgId(),
            schoolActivity.getSchoolTeachCircleId(), new Integer[] { SchoolTeachCircleOrg.YI_TONG_YI,
                SchoolTeachCircleOrg.YI_HUI_FU, SchoolTeachCircleOrg.YI_TUI_CHU });
      }
    }
    String areaIds = organizationService.findOne(userSpace.getOrgId()).getAreaIds(); // 当前用户所属的地区层级
    if (roleId.intValue() == SysRole.JYZR.getId().intValue() || roleId.intValue() == SysRole.JYY.getId().intValue()) {
      if (areaIds.equals(schoolActivity.getAreaIds())) {
        return true;
      }
    } else if (roleId.intValue() == SysRole.XZ.getId().intValue()
        || roleId.intValue() == SysRole.FXZ.getId().intValue() || roleId.intValue() == SysRole.ZR.getId().intValue()) {// 校长或主任
      if (isMatchTheCircle) {
        return true;
      }
    } else if (roleId.intValue() == SysRole.XKZZ.getId().intValue()) {// 学科组长
      if (isMatchTheCircle && schoolActivity.getSubjectIds().contains("," + userSpace.getSubjectId() + ",")) {
        return true;
      }
    } else if (roleId.intValue() == SysRole.NJZZ.getId().intValue()) {// 年级组长
      if (isMatchTheCircle && schoolActivity.getGradeIds().contains("," + userSpace.getGradeId() + ",")) {
        return true;
      }
    } else if (roleId.intValue() == SysRole.BKZZ.getId().intValue()
        || roleId.intValue() == SysRole.TEACHER.getId().intValue()) {// 备课组长或老师
      if (isMatchTheCircle && schoolActivity.getSubjectIds().contains("," + userSpace.getSubjectId() + ",")
          && schoolActivity.getGradeIds().contains("," + userSpace.getGradeId() + ",")) {
        return true;
      }
    }
    return false;
  }

  /**
   * 将整理好的教案发送给参与人
   * 
   * @param id
   * @param m
   */
  @RequestMapping("/sendToJoiner")
  public void sendToJoiner(Integer id, Model m) {
    try {
      schoolActivityService.sendToJoiner(id);
      m.addAttribute("result", "success");
    } catch (Exception e) {
      logger.error("集体备课：整理教案发送给参与人出错", e);
      m.addAttribute("result", "error");
    }
  }

  /**
   * 判断活动是否删除以及是否可以参与此活动
   */
  @RequestMapping("/activityIsDelete")
  public void activityIsDelete(Integer activityId, Model m) {
    Map<String, String> resultMap = schoolActivityService.activityIsDelete(activityId);
    m.addAttribute("result", resultMap);
  }

  /**
   * 判断活动是否有效
   * 
   * @param id
   */
  @RequestMapping("/ifActivityValid")
  public void ifActivityValid(Integer id, Model m) {
    SchoolActivity activity = schoolActivityService.findOne(id);
    if (activity != null) {
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      String startTime = sdf.format(activity.getStartTime());
      String currentTime = sdf.format(new Date());
      if (startTime.compareTo(currentTime) > 0) { // 活动尚未开始
        m.addAttribute("result", "fail3");
        m.addAttribute("startTime", startTime);
      } else {
        if (schoolActivityService.ifActivityIsOver(activity)) {
          if (ifHaveJoinPower(activity, "view")) {
            m.addAttribute("result", "success");
          } else {
            m.addAttribute("result", "fail2");
          }
        } else {
          if (ifHaveJoinPower(activity, "join")) {
            m.addAttribute("result", "success");
          } else {
            m.addAttribute("result", "fail2");
          }
        }
      }
    } else {
      m.addAttribute("result", "fail1");
    }
  }

  /**
   * 获取教案资源的整理控制权
   * 
   * @param resId
   * @param m
   */
  @RequestMapping("/getResZhengliPower")
  public void getResZhengliPower(String resId, Model m) {
    boolean flag = activityCoordinateControlService.getPowerOfZhengli(resId);
    if (flag) {
      m.addAttribute("result", "success");
    } else {
      m.addAttribute("result", "fail");
    }
  }

  /**
   * 参与专家指导——直播课堂
   * 
   * @param id
   * @param m
   * @return
   */
  @RequestMapping("joinZbkt")
  public String joinActivityZbkt(Integer id, Integer listType, Model m) {
    UserSpace userSpace = (UserSpace) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE); // 用户空间
    SchoolActivity sa = schoolActivityService.findOne(id);
    Assert.isTrue(ifHaveJoinPower(sa, "join"), "没有权限");
    ClassUserType userType = null;
    if (userSpace.getUserId().intValue() == sa.getOrganizeUserId().intValue()) {// 若参与者为发起人，则该用户进入课堂后即为主持人
      userType = ClassUserType.MASTER;
    } else {
      userType = ClassUserType.NORMAL;
    }
    String classUrl = "";
    if (sa != null && sa.getIsOver()) {
      classUrl = "/jy/schoolactivity/viewZbkt?id=" + id;
    } else {
      ClassUser cu = classOperateService.joinClass(sa.getClassId(), userSpace.getUserId(), userSpace.getUsername(),
          userType);
      classUrl = cu.getClassUrl();
    }
    return "redirect:" + classUrl;
  }

  /**
   * 查看-直播课堂
   * 
   * @param id
   * @param m
   * @return
   */
  @RequestMapping("viewZbkt")
  public String viewActivityZbkt(Integer id, Model m) {
    SchoolActivity sa = schoolActivityService.findOne(id);
    if (sa.getCommentsNum() == null || sa.getCommentsNum() == 0) {
      this.syncDiscuss(sa);
      schoolActivityService.update(sa);
    }

    Assert.isTrue(ifHaveJoinPower(sa, "view"), "没有权限");
    // 有权限的参与人列表查询
    List<UserSpace> usList = schoolActivityService.findUserBySubjectAndGrade(sa);
    m.addAttribute("usList", usList);
    m.addAttribute("activity", sa);
    m.addAttribute("activityType", ResTypeConstants.SCHOOLTEACH);
    m.addAttribute("listType", sa.getTypeId());
    m.addAttribute("operateType", 0);

    // 获取直播课堂录制的视频
    ClassInfo classInfo = classOperateService.generateClassRecordUrl(sa.getClassId());
    m.addAttribute("recordUrl", classInfo == null ? null : classInfo.getRecordUrl());
    m.addAttribute("user2", userService.findOne(sa.getOrganizeUserId()));
    m.addAttribute("us", userSpaceService.findOne(sa.getSpaceId()));
    // 获取所有参与的学校的名称
    String joinOrgNames = schoolTeachCircleOrgService.getJoinOrgNamesByCircleId(sa.getSchoolTeachCircleId());
    if (StringUtils.isNotEmpty(joinOrgNames)) {
      String[] joinOrgs = joinOrgNames.split("、");
      m.addAttribute("joinOrgNames", joinOrgs);
      m.addAttribute("joinOrgLength", joinOrgs.length);
    } else {
      m.addAttribute("joinOrgLength", 0);
    }
    if (StringUtils.isEmpty(sa.getClassId())) {
      return "/schoolactivity/activityJoinView";
    }
    // 获取参考附件集合
    Attach attach = new Attach();
    attach.setActivityId(id);
    attach.setActivityType(Attach.XJJY);
    List<Attach> attachList = activityAttachService.findAll(attach);
    m.addAttribute("attachList", attachList);
    return "/schoolactivity/activityJoinView";
  }

  /**
   * 获取参与的机构集合
   * 
   * @param activityId
   */
  @RequestMapping("getJoinOrgsOfActivity")
  @ResponseBody
  public List<Organization> getJoinOrgsOfActivity(Integer activityId, Model m) {
    SchoolActivity sa = schoolActivityService.findOne(activityId);
    return organizationService.getOrgListByIdsStr(sa.getOrgids());
  }

  /**
   * 结束直播课堂
   * 
   * @param activity
   * @param m
   */
  @RequestMapping("/overZbktActivity")
  public void overZbktActivity(SchoolActivity activity, Model m) {
    if (activity.getId() != null) {
      activity = schoolActivityService.findOne(activity.getId());
      if (activity != null && activity.getIsOver()) {
        return;
      }
      // 获取直播课堂录制的视频
      try {
        classOperateService.generateClassRecordUrl(activity.getClassId());
        classOperateService.saveChartRecord(activity.getClassId());
      } catch (Exception e) {
        logger.warn("get class record failed! actid:{}", activity.getClassId());
        logger.error("", e);
      }
      if (classStatusIsOver(activity.getClassId())) {
        this.syncDiscuss(activity);
        activity.setIsOver(true);
        schoolActivityService.update(activity);
        m.addAttribute("result", "success");
      } else {
        m.addAttribute("result", "fail");
      }
    } else {
      m.addAttribute("result", "fail");
    }
  }

  /**
   * 判断房间是否结束
   * 
   * @return true:结束 false 未结束
   */
  private boolean classStatusIsOver(String roomId) {
    Integer status = classOperateService.getRoomStatus(roomId);
    if (status == BJYClassStatusResult.STATUS_LIVE) {
      return false;
    }
    return true;
  }

  /**
   * 同步直播课堂讨论数据
   * 
   * @param activity
   */
  private void syncDiscuss(SchoolActivity activity) {
    if (activity == null || (activity.getCommentsNum() != null && activity.getCommentsNum() > 0)) {
      logger.info("aready sync discuss!");
      return;
    }
    // 用户交流记录
    List<ChatInfo> chatList = new ArrayList<ChatInfo>();
    try {
      chatList = classOperateService.listAllChatInfoByClassId(activity.getClassId());
    } catch (Exception e) {
      logger.error("", e);
      return;
    }
    // 讨论同步
    List<Discuss> discussList = new ArrayList<Discuss>();
    if (!CollectionUtils.isEmpty(chatList)) {
      for (ChatInfo chatInfo : chatList) {
        Discuss discuss = new Discuss();
        discuss.setActivityId(activity.getId());
        discuss.setDiscussLevel(1);
        discuss.setParentId(0);
        discuss.setTypeId(ResTypeConstants.SCHOOLTEACH);
        if (chatInfo.getFrom() != null) {
          discuss.setCrtId(chatInfo.getFrom());
          UserSpace us = new UserSpace();
          us.setUserId(discuss.getCrtId());
          us.setOrder(" sort asc ");
          us.setSchoolYear(CurrentUserContext.getCurrentSchoolYear());
          us = userSpaceService.findOne(us);
          if (us == null) {
            logger.info("sync one term failed!userSpace is null,userId is,{}", discuss.getCrtId());
            continue;
          }
          discuss.setSpaceId(us.getId());
        }
        discuss.setCrtDttm(chatInfo.getTime());
        discuss.setContent(chatInfo.getContent());
        discussList.add(discuss);
      }
    }
    if (!CollectionUtils.isEmpty(discussList)) {
      discussService.batchSave(discussList);
      activity.setCommentsNum(discussList.size());
    }
  }

  /**
   * 上传同辈教案的修改课件的保存
   * 
   * @param m
   * @param sat
   */
  @RequestMapping("schKjSave")
  public Model getJoinOrgsOfActivity(Model m, SchoolActivityTracks sat) {
    Boolean isOk = true;
    try {
      schoolActivityService.schKjSave(sat);
    } catch (Exception e) {
      logger.error("上传同辈教案的修改课件的保存出现错误！", e);
      isOk = false;
    }
    m.addAttribute("isOk", isOk);
    return m;
  }
}
