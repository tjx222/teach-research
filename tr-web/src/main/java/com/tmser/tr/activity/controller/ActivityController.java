package com.tmser.tr.activity.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tmser.tr.activity.bo.Activity;
import com.tmser.tr.activity.bo.ActivityTracks;
import com.tmser.tr.activity.service.ActivityCoordinateControlService;
import com.tmser.tr.activity.service.ActivityService;
import com.tmser.tr.activity.service.ActivityTracksService;
import com.tmser.tr.common.annotation.UseToken;
import com.tmser.tr.common.page.Page;
import com.tmser.tr.common.page.PageList;
import com.tmser.tr.common.utils.WebThreadLocalUtils;
import com.tmser.tr.common.web.controller.AbstractController;
import com.tmser.tr.lessonplan.bo.LessonInfo;
import com.tmser.tr.lessonplan.service.LessonPlanService;
import com.tmser.tr.manage.resources.bo.Attach;
import com.tmser.tr.manage.resources.service.AttachService;
import com.tmser.tr.uc.SysRole;
import com.tmser.tr.uc.bo.UserSpace;
import com.tmser.tr.uc.utils.SessionKey;
import com.zhuozhengsoft.pageoffice.FileSaver;
import com.zhuozhengsoft.pageoffice.OpenModeType;
import com.zhuozhengsoft.pageoffice.PageOfficeCtrl;

/**
 * <pre>
 * 集体备课
 * </pre>
 *
 * @author csj
 * @version $Id: ActivityController.java, v 1.0 2015年3月6日 上午10:17:31 csj Exp $
 */
@Controller
@RequestMapping("/jy/activity")
public class ActivityController extends AbstractController {

  private static final Logger logger = LoggerFactory.getLogger(ActivityController.class);

  @Resource
  private ActivityService activityService;
  @Resource
  private AttachService attachService;
  @Resource
  private LessonPlanService lessonPlanService;
  @Autowired
  private ActivityTracksService activityTracksService;
  @Autowired
  private ActivityCoordinateControlService activityCoordinateControlService;

  /**
   * 管理者 进入集体活动(管理者 集体备课)首页
   * 
   * @return
   */
  @RequestMapping("/index")
  public String index(String listType, Model model, Page page, HttpServletRequest request) {
    UserSpace userSpace = (UserSpace) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE); // 用户空间
    if (!activityService.isLeader(userSpace.getSysRoleId())) {
      return "redirect:/jy/activity/tchIndex";
    }
    listType = listType == null ? "0" : listType;
    // 列表选型
    model.addAttribute("listType", listType);
    Activity activity = new Activity(); // activity.getPage().setPageSize(3);
    // 页码
    if (page != null)
      page.setPageSize(10);
    activity.addPage(page);
    if ("0".equals(listType)) {
      // 加载发起活动列表
      PageList<Activity> activityList = activityService.findMyOrganizeActivityList(activity);
      model.addAttribute("activityList", activityList);
    } else {
      // 加载发起者参与查看列表
      PageList<Activity> activityList = activityService.findOtherActivityList(activity);
      model.addAttribute("activityList", activityList);
    }
    // 草稿(总数)
    Activity temp = new Activity();
    temp.setOrganizeUserId(userSpace.getUserId());
    temp.setStatus(0);
    int activityDraftNum = activityService.count(temp);
    model.addAttribute("activityDraftNum", activityDraftNum);
    return "/activity/leader/activityIndex";
  }

  /**
   * 管理者 进入集体活动 草稿箱
   * 
   * @return
   */
  @RequestMapping("/indexDraft")
  public String indexDraft(Activity activity, Model model) {
    UserSpace userSpace = (UserSpace) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE); // 用户空间
    // 草稿(总数)
    activity.setOrganizeUserId(userSpace.getUserId());
    activity.setStatus(0);
    activity.addOrder("createTime desc");
    activity.getPage().setPageSize(10);
    PageList<Activity> activityDraftList = activityService.findByPage(activity);
    model.addAttribute("activityDraftList", activityDraftList);
    return "/activity/leader/draftListPage";
  }

  /**
   * 管理者 进入集体活动编辑页
   * 
   * @return
   */
  @RequestMapping("/toEditActivity")
  public String toEditActivity(Integer id, Model model) {
    if (id != null) {
      Activity activity = activityService.findOne(id);
      model.addAttribute("act", activity);
    }
    return "/activity/leader/activityEdit";
  }

  /**
   * 进入同备教案编辑页
   * 
   * @param id
   * @param model
   * @return
   */
  @RequestMapping("/toEditActivityTbja")
  @UseToken
  public String toEditActivityTbja(Integer id, Model model) {
    Date startDate = null;
    if (id != null) {
      // 读取加载记录
      Activity activity = activityService.findOne(id);
      if (activity.getStatus().intValue() == 1) {
        Assert.isTrue(!activity.getIsSubmit(), "集体备课——同备教案已经提交，不可以修改");
      }
      activity.setTypeId(Activity.TBJA);
      model.addAttribute("act", activity);
      startDate = activity.getStartTime();
      // 加载主备人List
      if (activity.getMainUserGradeId() != null && activity.getMainUserSubjectId() != null) {
        UserSpace us = new UserSpace();
        us.setGradeId(activity.getMainUserGradeId());
        us.setSubjectId(activity.getMainUserSubjectId());
        us.setSysRoleId(SysRole.TEACHER.getId());
        model.addAttribute("mainUserList", activityService.findMainUserList(us));
      }
      // 加载章节（课题）List
      if (activity.getMainUserId() != null) {
        try {
          model.addAttribute("chapterList", activityService.findChapterList(activity.getMainUserId(),
              activity.getMainUserGradeId(), activity.getMainUserSubjectId()));
        } catch (ParseException e) {
          logger.error("集体备课：获取章节列表出错", e);
        }
      }

      // 是否有整理或意见留痕(如果有则只允许修改部分数据)
      Integer trackCount = activityTracksService.getTrackCountByActivityId(id);
      if (trackCount.intValue() > 0) {
        model.addAttribute("haveTrack", true);
      } else {
        model.addAttribute("haveTrack", false);
      }
    }
    // 加载学科List
    model.addAttribute("subjectList", activityService.findSubjectList());
    // 加载年级List
    model.addAttribute("gradeList", activityService.findGradeList());
    // 加载开始时间
    model.addAttribute("startDate", startDate != null ? startDate : new Date());
    return "/activity/leader/activityEditTbja";
  }

  /**
   * 管理者 进入集体活动 主题研讨编辑页
   * 
   * @return
   */
  @RequestMapping("/toEditActivityZtyt")
  @UseToken
  public String toEditActivityZtyt(Integer id, Model model) {
    Date startDate = null;
    if (id != null) {
      // 草稿中读取加载记录
      Activity activity = activityService.findOne(id);
      if (activity.getStatus().intValue() == 1) {
        Assert.isTrue(!activity.getIsSubmit(), "集体备课——主题研讨已经提交，不可以修改");
      }
      activity.setTypeId(Activity.ZTYT);
      model.addAttribute("act", activity);
      startDate = activity.getStartTime();
      // 加载主题研讨资源list
      Attach res = new Attach();
      res.setActivityId(id);
      res.setActivityType(Attach.JTBK);
      List<Attach> resList = attachService.findAll(res);
      model.addAttribute("resList", resList);
      String resIds = getResIds(resList);
      model.addAttribute("resIds", resIds);
    }
    // 加载学科List
    model.addAttribute("subjectList", activityService.findSubjectList());
    // 加载年级List
    model.addAttribute("gradeList", activityService.findGradeList());
    // 加载开始时间
    model.addAttribute("startDate", startDate != null ? startDate : new Date());
    return "/activity/leader/activityEditZtyt";
  }

  /**
   * 管理者 进入集体活动 视频教研编辑页
   * 
   * @return
   */
  @RequestMapping("/toEditActivitySpjy")
  @UseToken
  public String toEditActivitySpjy(Integer id, Model model) {
    Date startDate = null;
    if (id != null) {
      // 草稿中读取加载记录
      Activity activity = activityService.findOne(id);
      if (activity.getStatus().intValue() == 1) {
        Assert.isTrue(!activity.getIsSubmit(), "集体备课——视频教研已经提交，不可以修改");
      }
      activity.setTypeId(Activity.SPJY);
      model.addAttribute("act", activity);
      startDate = activity.getStartTime();
      // 加载主题研讨资源list
      Attach res = new Attach();
      res.setActivityId(id);
      res.setActivityType(Attach.JTBK);
      List<Attach> resList = attachService.findAll(res);
      model.addAttribute("resList", resList);
      String resIds = getResIds(resList);
      model.addAttribute("resIds", resIds);
    }
    // 加载学科List
    model.addAttribute("subjectList", activityService.findSubjectList());
    // 加载年级List
    model.addAttribute("gradeList", activityService.findGradeList());
    // 加载开始时间
    model.addAttribute("startDate", startDate != null ? startDate : new Date());
    return "/activity/leader/activityEditSpjy";
  }

  /**
   * 删除一条草稿
   * 
   * @return
   */
  @RequestMapping("/delActivityDraft")
  public String delActivityDraft(Integer id, Model model) {
    if (id != null) {
      // activityService.delete(id);
      activityService.deleteDraft(id);
    }
    return "redirect:/jy/activity/indexDraft";
  }

  /**
   * 删除活动
   * 
   * @return
   */
  @RequestMapping("/delActivity")
  public void delActivity(Integer activity_id, Model m) {
    if (activity_id != null) {
      // 活动下有讨论或提交后不允许删除
      Activity activity = activityService.findOne(activity_id);
      if (activity.getCommentsNum().intValue() > 0) {
        m.addAttribute("result", "fail1");
      } else if (activity.getIsSubmit()) {
        m.addAttribute("result", "fail2");
      } else {
        try {
          activityService.deleteActivity(activity_id);
          m.addAttribute("result", "success");
        } catch (Exception e) {
          logger.error("删除集体备课出错", e);
          m.addAttribute("result", "fail3");
        }
      }

    }
  }

  /**
   * 加载主备人列表
   * 
   * @return
   */
  @RequestMapping("/mainUserList")
  public List<UserSpace> userList(Integer subjectId, Integer gradeId, HttpServletRequest request) {
    List<UserSpace> mainUserList = null;
    if (subjectId != null && gradeId != null) {
      UserSpace us = new UserSpace();
      us.setGradeId(gradeId);
      us.setSubjectId(subjectId);
      us.setSysRoleId(SysRole.TEACHER.getId());
      mainUserList = activityService.findMainUserList(us);
    }
    return mainUserList;
  }

  /**
   * 加载章节列表
   * 
   * @return
   */
  @RequestMapping("/chapterList")
  public List<LessonInfo> chapterList(Integer userId, Integer gradeId, Integer subjectId, HttpServletRequest request) {
    List<LessonInfo> chapterList = null;
    if (userId != null) {
      try {
        chapterList = activityService.findChapterList(userId, gradeId, subjectId);
      } catch (ParseException e) {
        logger.error("发布集体备课：获取章节列表出错", e);
      }
    }
    return chapterList;
  }

  /**
   * 保存 集体备课活动-同备教案
   * 
   * @return
   */
  @RequestMapping("/saveActivityTbja")
  @UseToken(true)
  public void saveActivityTbja(Activity activity, Integer status, HttpServletRequest request, Model m) {
    Activity result = null;
    try {
      activity.setStatus(status);
      if (activity.getId() != null && activity.getId().intValue() != 0) {
        // 更新
        result = activityService.updateActivityTbja(activity, m);
      } else {
        // 新增
        result = activityService.saveActivityTbja(activity);
      }

    } catch (Exception e) {
      logger.error("保存集体备课——同备教案失败", e);
      m.addAttribute("result", "error");
    }
    if (result != null) {
      m.addAttribute("result", "success");
    }
  }

  /**
   * 保存 集体备课活动-主题研讨
   * 
   * @return
   */
  @RequestMapping("/saveActivityZtyt")
  @UseToken(true)
  public void saveActivityZtyt(Activity activity, Integer status, String resIds, HttpServletRequest request, Model m) {
    Activity result = null;
    activity.setStatus(status);
    if (activity.getId() != null && activity.getId().intValue() != 0) {
      // 更新
      result = activityService.updateActivityZtyt(activity, resIds, m);
    } else {
      // 新增
      result = activityService.saveActivityZtyt(activity, resIds);
    }
    if (result != null) {
      m.addAttribute("result", "success");
    }
  }

  /**
   * 保存 集体备课活动-主题研讨
   * 
   * @return
   */
  @RequestMapping("/saveActivitySpjy")
  @UseToken(true)
  public void saveActivitySpjy(Activity activity, Integer status, String resIds, HttpServletRequest request, Model m) {
    Activity result = null;
    activity.setStatus(status);
    if (activity.getId() != null && activity.getId().intValue() != 0) {
      // 更新
      result = activityService.updateActivityZtyt(activity, resIds, m);
    } else {
      // 新增
      result = activityService.saveActivityZtyt(activity, resIds);
    }
    if (result != null) {
      m.addAttribute("result", "success");
    }
  }

  /**
   * 普通老师 进入集体活动(集体备课) 首页2
   * 
   * @return
   */
  @RequestMapping("/tchIndex")
  public String tchIndex(String listType, Model model, Page page, HttpServletRequest request) {
    Activity activity = new Activity();
    activity.getPage().setPageSize(10);
    // 页码
    if (page != null) {
      activity.getPage().setCurrentPage(page.getCurrentPage());
    }

    // 加载可参与列表
    PageList<Activity> activityList = activityService.findOtherActivityList(activity);
    model.addAttribute("activityList", activityList);
    return "/activity/tch/tchIndex";
  }

  // 组合资源id
  private String getResIds(List<Attach> resList) {
    String resIds = "";
    if (resList != null && resList.size() > 0) {
      for (Attach res : resList) {
        if (!"".equals(resIds))
          resIds += ",";
        resIds += res.getResId();
      }
    }
    return resIds;
  }

  /**
   * 参与集备教案
   * 
   * @param id
   * @param request
   * @param m
   * @return
   */
  @RequestMapping("/joinTbjaActivity")
  public String joinTbjaActivity(Integer id, Model m) {
    Activity activity = activityService.findOne(id);
    UserSpace userSpace = (UserSpace) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE); // 用户空间
    // 判断是否有权限
    Assert.isTrue(ifHavePower(activity), "没有权限");
    // List<LessonPlan> lessonPlanList =
    // lessonPlanService.getJiaoanByInfoId(activity.getInfoId());
    List<ActivityTracks> zhubeiList = activityTracksService.getActivityTracks_zhubei(id);
    m.addAttribute("zhubeiList", zhubeiList);
    m.addAttribute("activity", activity);
    // 整理教案集合
    List<ActivityTracks> zhengliList = activityTracksService.getActivityTracks_zhengli(id);
    m.addAttribute("zhengliList", zhengliList);
    // 是否可以接收教案
    if (userSpace.getSysRoleId().intValue() == SysRole.TEACHER.getId().intValue()
        && userSpace.getGradeId().intValue() == activity.getMainUserGradeId().intValue() && activity.getIsSend()) {
      // LessonInfo lessonInfo =
      // myPlanBookService.getLessonInfoByLessonId(myPlanBookService.findOne(activity.getInfoId()).getLessonId());
      // if(lessonInfo==null ||
      // lessonInfo.getCurrentFrom()!=LessonInfo.FROM_ACTIVITY){
      m.addAttribute("canReceive", true);
      // }
    }

    // 有权限的参与人列表查询
    List<UserSpace> usList = activityService.findUserBySubjectAndGrade(activity);
    m.addAttribute("usList", usList);

    // if(userSpace.getUserId().intValue() ==
    // activity.getOrganizeUserId().intValue()){ //如果是 发起人（整理教案）
    // if(userSpace.getSysRoleId().intValue() ==
    // activityUserSpace.getSysRoleId().intValue()){ //身份是发起所用的身份
    // //判断主备人是否在整理教案
    // if(activityCoordinateControlService.mainUserIsZhengliing(zhubeiList,zhengliList)){
    // m.addAttribute("mainUserZhengliing", true);
    // }
    // return "/activity/view/zhengLiTongBei";
    // }else if(userSpace.getUserId().intValue() ==
    // activity.getMainUserId().intValue() &&
    // userSpace.getSysRoleId().intValue() == SysRole.TEACHER.getId().intValue()
    // &&
    // activity.getMainUserGradeId().intValue()==userSpace.getGradeId().intValue()
    // &&
    // activity.getMainUserSubjectId().intValue()==userSpace.getSubjectId().intValue()){
    // //又同时是主备人且当前角色是老师
    // return "/activity/view/zhengLiTongBei";
    // }else{
    // return "/activity/view/canYuJiBei";
    // }
    // }else
    if (userSpace.getUserId().intValue() == activity.getMainUserId().intValue()) { // 如果是主备人,必须是老师才可进入整理页
      if (userSpace.getSysRoleId().intValue() == SysRole.TEACHER.getId().intValue()
          && activity.getMainUserGradeId().intValue() == userSpace.getGradeId().intValue()
          && activity.getMainUserSubjectId().intValue() == userSpace.getSubjectId().intValue()) {
        return "/activity/view/zhengLiTongBei";
      } else {
        return "/activity/view/canYuJiBei";
      }
    } else {
      if (activityService.ifActivityIsOver(activity)) {
        return "redirect:/jy/activity/viewTbjaActivity?_no_office_&id=" + id;
      }
      return "/activity/view/canYuJiBei";
    }
  }

  /**
   * 查看集备教案
   * 
   * @param id
   * @param m
   * @return
   */
  @RequestMapping("/viewTbjaActivity")
  public String viewTbjaActivity(Integer id, Model m) {

    UserSpace userSpace = (UserSpace) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE); // 用户空间
    Activity activity = activityService.findOne(id);
    // 如果是主备人,即使活动结束，也能进入整理页，但必须是老师才可进入整理页（身份区别）
    if (userSpace.getUserId().intValue() == activity.getMainUserId().intValue()) {
      if (userSpace.getSysRoleId().intValue() == SysRole.TEACHER.getId().intValue()
          && activity.getMainUserGradeId().intValue() == userSpace.getGradeId().intValue()
          && activity.getMainUserSubjectId().intValue() == userSpace.getSubjectId().intValue()) {
        return "redirect:/jy/activity/joinTbjaActivity?_no_office_&id=" + id;
      }
    }
    // 判断是否有权限
    Assert.isTrue(ifHavePower(activity), "没有权限");
    // 获取主备人的教案
    // List<LessonPlan> lessonPlanList =
    // lessonPlanService.getJiaoanByInfoId(activity.getInfoId());
    List<ActivityTracks> zhubeiList = activityTracksService.getActivityTracks_zhubei(id);
    m.addAttribute("zhubeiList", zhubeiList);
    m.addAttribute("activity", activity);
    // 整理教案集合
    List<ActivityTracks> zhengliList = activityTracksService.getActivityTracks_zhengli(id);
    m.addAttribute("zhengliList", zhengliList);
    // 是否可以接收教案
    if (userSpace.getSysRoleId().intValue() == SysRole.TEACHER.getId().intValue()
        && userSpace.getGradeId().intValue() == activity.getMainUserGradeId().intValue() && activity.getIsSend()) {
      // LessonInfo lessonInfo =
      // myPlanBookService.getLessonInfoByLessonId(myPlanBookService.findOne(activity.getInfoId()).getLessonId());
      // if(lessonInfo==null ||
      // lessonInfo.getCurrentFrom()!=LessonInfo.FROM_ACTIVITY){
      m.addAttribute("canReceive", true);
      // }
    }

    // 有权限的参与人列表查询
    List<UserSpace> usList = activityService.findUserBySubjectAndGrade(activity);
    m.addAttribute("usList", usList);
    return "/activity/view/chaKanJiBei";
  }

  /**
   * 参与主题研讨
   * 
   * @param id
   * @param m
   * @return
   */
  @RequestMapping("/joinZtytActivity")
  public String joinZtytActivity(Integer id, Model m) {
    Activity activity = activityService.findOne(id);
    if (activityService.ifActivityIsOver(activity)) {
      return "redirect:/jy/activity/viewZtytActivity?id=" + id;
    }
    // 判断是否有权限
    Assert.isTrue(ifHavePower(activity), "没有权限");
    Attach temp = new Attach();
    temp.setActivityId(id);
    temp.setActivityType(Attach.JTBK);
    List<Attach> attachList = attachService.findAll(temp);
    m.addAttribute("activity", activity);
    m.addAttribute("attachList", attachList);
    // 有权限的参与人列表查询
    List<UserSpace> usList = activityService.findUserBySubjectAndGrade(activity);
    m.addAttribute("usList", usList);
    return "/activity/view/canYuZhuYan";
  }

  /**
   * 查看主题研讨
   * 
   * @param id
   * @param m
   * @return
   */
  @RequestMapping("/viewZtytActivity")
  public String viewZtytActivity(@RequestParam(value = "", required = false) String mark, Integer id, Model m) {
    Activity activity = activityService.findOne(id);
    m.addAttribute("mark", mark);// 区分管理记录和集备面包屑
    // 判断是否有权限
    Assert.isTrue(ifHavePower(activity), "没有权限");
    Attach temp = new Attach();
    temp.setActivityId(id);
    List<Attach> attachList = attachService.findAll(temp);
    m.addAttribute("activity", activity);
    m.addAttribute("attachList", attachList);
    // 有权限的参与人列表查询
    List<UserSpace> usList = activityService.findUserBySubjectAndGrade(activity);
    m.addAttribute("usList", usList);
    return "/activity/view/chaKanZhuYan";
  }

  /**
   * 参与视频教研（移动端专用）
   * 
   * @param id
   * @param m
   * @return
   */
  @RequestMapping("/joinSpjyActivity")
  public String joinSpjyActivity(Integer id, Model m) {
    Activity activity = activityService.findOne(id);
    if (activityService.ifActivityIsOver(activity)) {
      return "redirect:/jy/activity/viewSpjyActivity?id=" + id;
    }
    // 判断是否有权限
    Assert.isTrue(ifHavePower(activity), "没有权限");
    Attach temp = new Attach();
    temp.setActivityId(id);
    temp.setActivityType(Attach.JTBK);
    List<Attach> attachList = attachService.findAll(temp);
    m.addAttribute("activity", activity);
    m.addAttribute("attachList", attachList);
    // 有权限的参与人列表查询
    List<UserSpace> usList = activityService.findUserBySubjectAndGrade(activity);
    m.addAttribute("usList", usList);
    return "/activity/view/canYuShipin";
  }

  /**
   * 查看视频教研（移动端专用）
   * 
   * @param id
   * @param m
   * @return
   */
  @RequestMapping("/viewSpjyActivity")
  public String viewSpjyActivity(@RequestParam(value = "", required = false) String mark, Integer id, Model m) {
    Activity activity = activityService.findOne(id);
    m.addAttribute("mark", mark);// 区分管理记录和集备面包屑
    // 判断是否有权限
    Assert.isTrue(ifHavePower(activity), "没有权限");
    Attach temp = new Attach();
    temp.setActivityId(id);
    List<Attach> attachList = attachService.findAll(temp);
    m.addAttribute("activity", activity);
    m.addAttribute("attachList", attachList);
    // 有权限的参与人列表查询
    List<UserSpace> usList = activityService.findUserBySubjectAndGrade(activity);
    m.addAttribute("usList", usList);
    return "/activity/view/chaKanShipin";
  }

  /**
   * 首页无权限查看教研活动
   * 
   * @param id
   * @param m
   * @return
   */
  @RequestMapping("/onlyViewActivity")
  public String onlyViewActivity(Integer id, Model m) {
    Activity activity = activityService.findOne(id);
    if (activity.getTypeId().intValue() == Activity.TBJA.intValue()) {
      m.addAttribute("url", "/jy/activity/onlyViewActivity1?id=" + id);
      return "/resview/pageofficeOpenWindow";
    } else if (activity.getTypeId().intValue() == Activity.ZTYT.intValue()
        || activity.getTypeId().intValue() == Activity.SPJY.intValue()) {
      Attach temp = new Attach();
      temp.setActivityId(id);
      List<Attach> attachList = attachService.findAll(temp);
      m.addAttribute("activity", activity);
      m.addAttribute("attachList", attachList);
      // 有权限的参与人列表查询
      List<UserSpace> usList = activityService.findUserBySubjectAndGrade(activity);
      m.addAttribute("usList", usList);
      return "/activity/view/onlyChaKanZhuYan";
    }
    return "";
  }

  /**
   * 首页无权限查看教研活动
   * 
   * @param id
   * @param m
   * @return
   */
  @RequestMapping("/onlyViewActivity1")
  public String onlyViewActivity1(Integer id, Model m) {

    Activity activity = activityService.findOne(id);
    // 获取主备人的教案
    List<ActivityTracks> zhubeiList = activityTracksService.getActivityTracks_zhubei(id);
    m.addAttribute("zhubeiList", zhubeiList);
    m.addAttribute("activity", activity);
    // 整理教案集合
    List<ActivityTracks> zhengliList = activityTracksService.getActivityTracks_zhengli(id);
    m.addAttribute("zhengliList", zhengliList);
    // 有权限的参与人列表查询
    List<UserSpace> usList = activityService.findUserBySubjectAndGrade(activity);
    m.addAttribute("usList", usList);
    return "/activity/view/onlyChaKanJiBei";
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
    ActivityTracks track = activityTracksService.openTracksFileOfLessonPlan(planId, activityId, request, editType, m);
    if (track.getEditType().intValue() == ActivityTracks.ZHUBEI.intValue()) {
      m.addAttribute("trackId", "");
    } else if (track.getEditType().intValue() == ActivityTracks.ZHENGLI.intValue()
        || track.getEditType().intValue() == ActivityTracks.YIJIAN.intValue()) {
      try {
        m.addAttribute("trackId", track.getId());
      } catch (Exception e) {
        logger.error("显示主备教案的整理教案或修改教案出错", e);
      }
    }
    m.addAttribute("planId", planId);
    m.addAttribute("activityId", activityId);
    m.addAttribute("editType", editType);
    return "/activity/view/showLessonPlan_edit";
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
    ActivityTracks track = activityTracksService.findOne(planId);
    activityTracksService.openWordFileByRevision(track.getResId(), poc, OpenModeType.docReadOnly);
    return "/activity/view/showLessonPlan_edit";
  }

  /**
   * 获取主备教案的意见教案集合
   * 
   * @param activityId
   * @return
   */
  @RequestMapping("/getYijianTrackList")
  public String getYijianTrackList(Integer planId, Integer activityId, Model m) {
    List<ActivityTracks> yijianList = activityTracksService.getActivityTracks_yijian(planId, activityId);
    m.addAttribute("yijianList", yijianList);
    return "/activity/view/tracks_yijianList";
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
    List<ActivityTracks> zhengliList = activityTracksService.getActivityTracks_zhengli(activityId);
    m.addAttribute("zhengliList", zhengliList);
    return "/activity/view/tracks_zhengliList";
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
    Integer trackId = activityTracksService.saveLessonPlanTracks(fs);
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
  public String scanLessonPlanTrack(String resId, Integer orgId, HttpServletRequest request, Model m) {
    PageOfficeCtrl poc = new PageOfficeCtrl(request);
    poc.setServerPage(request.getContextPath() + "/poserver.zz");
    activityTracksService.openWordFileByRevision(resId, poc, OpenModeType.docRevisionOnly);
    m.addAttribute("resId", resId);
    m.addAttribute("orgId", orgId);
    return "/activity/view/track_read";
  }

  /**
   * 结束集体备课活动
   * 
   * @param activity
   * @param m
   */
  @RequestMapping("/overActivity")
  public void overActivity(Activity activity, Model m) {
    activity.setIsOver(true);
    try {
      activityService.update(activity);
      m.addAttribute("result", "success");
    } catch (Exception e) {
      logger.error("结束集体备课活动出错", e);
      m.addAttribute("result", "fail");
    }
  }

  /**
   * 判断是否有权限
   * 
   * @param activity
   * @return
   */
  private boolean ifHavePower(Activity activity) {
    boolean flag = false;
    UserSpace userSpace = (UserSpace) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE); // 用户空间
    Integer roleId = userSpace.getSysRoleId();// 角色id
    if (roleId.intValue() == SysRole.XZ.getId().intValue() || roleId.intValue() == SysRole.FXZ.getId().intValue()) {// 校长或副校长
      flag = true;
    } else if (roleId.intValue() == SysRole.ZR.getId().intValue()) {// 主任
      flag = true;
    } else if (roleId.intValue() == SysRole.XKZZ.getId().intValue()) {// 学科组长
      if (activity.getOrgId().intValue() == userSpace.getOrgId().intValue()
          && activity.getSubjectIds().contains("," + String.valueOf(userSpace.getSubjectId()) + ",")) {
        flag = true;
      }
    } else if (roleId.intValue() == SysRole.NJZZ.getId().intValue()) {// 年级组长
      if (activity.getOrgId().intValue() == userSpace.getOrgId().intValue()
          && activity.getGradeIds().contains("," + String.valueOf(userSpace.getGradeId()) + ",")) {
        flag = true;
      }
    } else if (roleId.intValue() == SysRole.BKZZ.getId().intValue()) {// 备课组长
      if (activity.getOrgId().intValue() == userSpace.getOrgId().intValue()
          && activity.getSubjectIds().contains("," + String.valueOf(userSpace.getSubjectId()) + ",")
          && activity.getGradeIds().contains("," + String.valueOf(userSpace.getGradeId()) + ",")) {
        flag = true;
      }
    } else if (roleId.intValue() == SysRole.TEACHER.getId().intValue()) {// 老师
      if (activity.getOrgId().intValue() == userSpace.getOrgId().intValue()
          && activity.getSubjectIds().contains("," + String.valueOf(userSpace.getSubjectId()) + ",")) {
        if (activity.getGradeIds().contains("," + String.valueOf(userSpace.getGradeId()) + ",")
            || activity.getMainUserId().intValue() == userSpace.getUserId().intValue()) {
          flag = true;
        }
      }
    }
    return flag;
  }

  /**
   * 提交集体备课
   * 
   * @param id
   * @param m
   */
  @RequestMapping("/submitActivity")
  public void submitActivity(Integer id, Model m) {
    try {
      Activity activity = activityService.findOne(id);
      if (activity.getIsOver()) {
        activity.setIsSubmit(true);
        activityService.update(activity);
        m.addAttribute("result", "success");
      } else {
        m.addAttribute("result", "fail");
      }
    } catch (Exception e) {
      logger.error("提交集体备课活动出错", e);
      m.addAttribute("result", "error");
    }
  }

  /**
   * 取消提交集体备课
   * 
   * @param id
   * @param m
   */
  @RequestMapping("/unSubmitActivity")
  public void unSubmitActivity(Integer id, Model m) {
    try {
      Activity activity = activityService.findOne(id);
      if (!activity.getIsAudit()) {
        activity.setIsSubmit(false);
        activityService.update(activity);
        m.addAttribute("result", "success");
      } else {
        m.addAttribute("result", "fail");
      }
    } catch (Exception e) {
      logger.error("取消提交集体备课活动出错", e);
      m.addAttribute("result", "error");
    }
  }

  /**
   * 分享集体备课
   * 
   * @param activity
   * @param m
   */
  @RequestMapping("/shareActivity")
  public void shareActivity(Activity activity, Model m) {
    try {
      activity.setIsShare(true);
      activity.setShareTime(new Date());
      activityService.update(activity);
      m.addAttribute("result", "success");
    } catch (Exception e) {
      logger.error("分享集体备课活动出错", e);
      m.addAttribute("result", "error");
    }
  }

  /**
   * 取消分享集体备课
   * 
   * @param id
   * @param m
   */
  @RequestMapping("/unShareActivity")
  public void unShareActivity(Integer id, Model m) {
    try {
      Activity activity = activityService.findOne(id);
      if (!activity.getIsComment()) {
        activity.setIsShare(false);
        activityService.update(activity);
        m.addAttribute("result", "success");
      } else {
        m.addAttribute("result", "fail");
      }
    } catch (Exception e) {
      logger.error("取消分享集体备课活动出错", e);
      m.addAttribute("result", "error");
    }
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
      activityService.sendToJoiner(id);
      m.addAttribute("result", "success");
    } catch (Exception e) {
      logger.error("集体备课：整理教案发送给参与人出错", e);
      m.addAttribute("result", "error");
    }
  }

  /**
   * 查阅集备跳转判断
   * 
   * @return
   */
  @RequestMapping("/views")
  public String views(Integer activityId) {
    Activity activity = activityService.findOne(activityId);
    if (activity != null) {
      if (activity.getTypeId().intValue() == Activity.TBJA.intValue()) {// 同备教案
        return "redirect:/jy/activity/viewTbjaActivity?id=" + activityId;
      } else if (activity.getTypeId().intValue() == Activity.ZTYT.intValue()) {// 主题研讨
        return "redirect:/jy/activity/viewZtytActivity?id=" + activityId;
      } else if (activity.getTypeId().intValue() == Activity.SPJY.intValue()) {// 视频研讨
        return "redirect:/jy/activity/viewZtytActivity?id=" + activityId;
      }
    }
    return "";
  }

  /**
   * 置为已查看查阅意见（更新auditUp状态）
   * 
   * @param activityId
   */
  @RequestMapping("/setScanListAlreadyShow")
  public void setScanListAlreadyShow(Activity activity) {
    activity.setAuditUp(false);
    activityService.update(activity);
  }

  /**
   * 判断活动是否有效
   * 
   * @param id
   */
  @RequestMapping("/ifActivityValid")
  public void ifActivityValid(Integer id, Model m) {
    Activity activity = activityService.findOne(id);
    if (activity != null) {
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      String startTime = sdf.format(activity.getStartTime());
      String currentTime = sdf.format(new Date());
      if (startTime.compareTo(currentTime) > 0) { // 活动尚未开始
        m.addAttribute("result", "fail3");
        m.addAttribute("startTime", startTime);
      } else {
        if (ifHavePower(activity)) {
          m.addAttribute("result", "success");
          m.addAttribute("startDate", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(activity.getStartTime()));
        } else {
          m.addAttribute("result", "fail2");
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

}
