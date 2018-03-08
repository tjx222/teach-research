package com.tmser.tr.teachingView.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tmser.tr.activity.bo.Activity;
import com.tmser.tr.activity.bo.ActivityTracks;
import com.tmser.tr.activity.bo.SchoolActivity;
import com.tmser.tr.activity.bo.SchoolActivityTracks;
import com.tmser.tr.activity.service.ActivityService;
import com.tmser.tr.activity.service.ActivityTracksService;
import com.tmser.tr.activity.service.SchoolActivityService;
import com.tmser.tr.activity.service.SchoolActivityTracksService;
import com.tmser.tr.activity.service.SchoolTeachCircleOrgService;
import com.tmser.tr.check.bo.CheckInfo;
import com.tmser.tr.check.bo.CheckOpinion;
import com.tmser.tr.check.service.CheckInfoService;
import com.tmser.tr.check.service.CheckOpinionService;
import com.tmser.tr.comment.bo.CommentInfo;
import com.tmser.tr.common.ResTypeConstants;
import com.tmser.tr.common.page.PageList;
import com.tmser.tr.common.utils.WebThreadLocalUtils;
import com.tmser.tr.common.vo.Result;
import com.tmser.tr.common.web.controller.AbstractController;
import com.tmser.tr.lecturerecords.bo.LectureRecords;
import com.tmser.tr.lecturerecords.service.LectureRecordsService;
import com.tmser.tr.lessonplan.bo.LessonInfo;
import com.tmser.tr.lessonplan.bo.LessonPlan;
import com.tmser.tr.lessonplan.service.LessonInfoService;
import com.tmser.tr.manage.resources.bo.Attach;
import com.tmser.tr.manage.resources.service.AttachService;
import com.tmser.tr.plainsummary.bo.PlainSummary;
import com.tmser.tr.plainsummary.service.JyPlainSummaryCheckService;
import com.tmser.tr.plainsummary.service.PlainSummaryService;
import com.tmser.tr.plainsummary.vo.PlainSummaryVo;
import com.tmser.tr.teachingView.service.TeachingViewService;
import com.tmser.tr.teachingview.vo.SearchVo;
import com.tmser.tr.thesis.bo.Thesis;
import com.tmser.tr.thesis.service.ThesisService;
import com.tmser.tr.uc.bo.User;
import com.tmser.tr.uc.bo.UserSpace;
import com.tmser.tr.uc.service.UserService;
import com.tmser.tr.uc.service.UserSpaceService;
import com.tmser.tr.uc.utils.SessionKey;
import com.tmser.tr.utils.StringUtils;
import com.tmser.tr.writelessonplan.service.LessonPlanService;

/**
 * 教研一览详情页
 * 
 * <pre>
 *
 * </pre>
 *
 * @author wangyao
 * @version $Id: DetailViewController.java, v 1.0 2016年5月9日 上午10:19:27 wangyao
 *          Exp $
 */
@Controller
@RequestMapping("/jy/teachingView")
public class DetailViewController extends AbstractController {

  @Autowired
  private TeachingViewService teachingViewService;
  @Autowired
  private LessonInfoService lessonInfoService;
  @Autowired
  private LessonPlanService lessonPlanService;
  @Autowired
  private CheckOpinionService checkOpinionService;
  @Autowired
  private CheckInfoService checkInfoService;
  @Autowired
  private LectureRecordsService lectureRecordsService;
  @Autowired
  private UserService userService;
  @Autowired
  private JyPlainSummaryCheckService jyPlainSummaryCheckService;
  @Autowired
  private PlainSummaryService plainSummaryService;
  @Autowired
  private ThesisService thesisService;
  @Autowired
  private ActivityTracksService activityTracksService;
  @Autowired
  private AttachService activityAttachService;
  @Autowired
  private ActivityService activityService;
  @Autowired
  private SchoolTeachCircleOrgService schoolTeachCircleOrgService;
  @Autowired
  private SchoolActivityService schoolActivityService;
  @Autowired
  private SchoolActivityTracksService schoolActivityTracksService;
  @Autowired
  private UserSpaceService userSpaceService;

  /**
   * 查看课题
   * 
   * @param lessPlan
   * @param m
   * @return
   */
  @RequestMapping("/view/lesson")
  public String viewLesson(Integer type, Integer infoId, Model m, String showType) {

    if (infoId != null) {
      LessonInfo lessonInfo = lessonInfoService.findOne(infoId);
      LessonPlan model = new LessonPlan();
      if (lessonInfo != null) {
        model.setBookId(lessonInfo.getBookId());
        model.setLessonId(lessonInfo.getLessonId());
        model.setUserId(lessonInfo.getUserId());
        model.addOrder("planType,orderValue");
        List<LessonPlan> lplist = lessonPlanService.findAll(model);
        m.addAttribute("lessonList", lplist);
        m.addAttribute("data", lessonInfo);
      }
    }
    m.addAttribute("type", type);
    m.addAttribute("showType", showType);
    return "/teachingview/view_lesson";
  }

  /**
   * 查看其他反思
   * 
   * @param lessPlan
   * @param m
   * @return
   */
  @RequestMapping("/view/other/lesson")
  public String viewLessonOther(Integer type, Integer planId, Model m, String showType) {

    if (planId != null) {
      LessonPlan lessonPlan = lessonPlanService.findOne(planId);
      LessonPlan model = new LessonPlan();
      if (lessonPlan != null) {
        model.setBookId(lessonPlan.getBookId());
        model.setLessonId(lessonPlan.getLessonId());
        model.setUserId(lessonPlan.getUserId());
        model.addOrder("orderValue");
        List<LessonPlan> lplist = lessonPlanService.findAll(model);
        m.addAttribute("lessonList", lplist);
        m.addAttribute("data", lessonPlan);
      }
    }
    m.addAttribute("showType", showType);
    m.addAttribute("type", type);
    return "/teachingview/view_otherlesson";
  }

  /**
   * 查阅信息入口页
   * 
   * @param type
   * @return
   */
  @RequestMapping(value = "/view/infoIndex")
  public String index(CheckInfo checkop, Model m) {
    CheckOpinion co = new CheckOpinion();
    co.setType(0);
    co.setIsDelete(false);
    co.setIsHidden(false);
    co.currentPage(checkop.getPage().getCurrentPage());
    co.getPage().setPageSize(5);
    co.addOrder("id desc");
    co.setResId(checkop.getResId());
    co.setResType(checkop.getResType());
    PageList<CheckOpinion> coList = checkOpinionService.findByPage(co);

    Integer infoId = null;
    // 意见回复
    Map<Integer, List<CheckOpinion>> coMap = new HashMap<Integer, List<CheckOpinion>>();
    if (coList != null && coList.getPageSize() > 0) {

      CheckOpinion replyco = null;
      for (CheckOpinion checkopinion : coList.getDatalist()) {
        infoId = checkopinion.getCheckInfoId();
        replyco = new CheckOpinion();
        replyco.setOpinionId(checkopinion.getId());
        replyco.setType(CheckOpinion.TYPE_REPLY);
        replyco.setIsDelete(false);
        replyco.setIsHidden(false);
        replyco.addOrder("id desc");
        List<CheckOpinion> reployList = checkOpinionService.findAll(replyco);
        coMap.put(checkopinion.getId(), reployList);
      }
    }
    if (infoId != null) {
      CheckInfo cinfo = checkInfoService.findOne(infoId);
      checkop.setLevel(cinfo.getLevel());
    }

    m.addAttribute("data", coList);
    m.addAttribute("coMap", coMap);
    m.addAttribute("containsInput",
        "true".equalsIgnoreCase(checkop.getFlags()) || "1".equals(checkop.getFlags()) ? 1 : 0);
    m.addAttribute("model", checkop);
    m.addAttribute("titleShow", (checkop.getTitleShow() != null && checkop.getTitleShow() == true) ? "1" : "0");

    return "/teachingview/view_lesson_opinion_list";
  }

  /**
   * 查看单个听课记录
   * 
   * @param info
   * @param m
   * @return
   */
  @RequestMapping(value = "/view/LectureRecord")
  public String seeTopic(Model m, Integer id, @RequestParam(value = "showType", required = false) String showType) {
    LectureRecords lr = lectureRecordsService.findOne(id);
    m.addAttribute("lr", lr);// 按照主键查询单个
    m.addAttribute("showType", showType);// 按照主键查询单个
    return "/teachingview/view_lecturerecords";
  }

  /**
   * 单用户空间，计划总结详情页面
   * 
   * @param planSummaryId
   *          计划总结id
   * @param type
   *          类型
   * @param
   * @return
   */
  @RequestMapping("/view/planSummary/{planSummaryId}")
  public String planSummaryView(@PathVariable("planSummaryId") Integer planSummaryId, Model model, SearchVo searchVo) {
    // 获取计划总结信息
    PlainSummaryVo plainSummary = jyPlainSummaryCheckService.getPlanSummaryVo(planSummaryId);
    // 获取用户信息
    User user = userService.findOne(plainSummary.getUserId());
    model.addAttribute("user", user);
    model.addAttribute("ps", plainSummary);
    return "/teachingview/view_planSummary";
  }

  /**
   * 获取计划总结详情
   * 
   * @param planSummaryId
   * @return
   */
  @RequestMapping("/view/planSummaryCheck/{planSummaryId}")
  public Result getPlanSummary(@PathVariable("planSummaryId") Integer planSummaryId) {
    PlainSummaryVo result = new PlainSummaryVo();
    HashMap<String, Object> map = new HashMap<String, Object>();
    // 查询计划总结
    PlainSummary plainSummary = plainSummaryService.findOne(planSummaryId);
    if (plainSummary != null) {
      BeanUtils.copyProperties(plainSummary, result);
      Integer crtId = plainSummary.getCrtId();
      // 查询用户消息
      User user = userService.findOne(crtId);
      result.setEditName(user.getName());
      // 获取用户信息
      User users = userService.findOne(result.getUserId());
      if (users != null) {
        result.setEditName(users.getName());
      }
      map.put("ps", result);
    }
    return new Result(map);
  }

  // 查看教学论文
  @RequestMapping("/view/thesisview")
  public String thesisView(Model m, Integer id, String showType) {

    Thesis thesis = thesisService.findOne(id);
    m.addAttribute("thesis", thesis);
    m.addAttribute("showType", showType);
    return "/teachingview/view_thesis";
  }

  /**
   * 查阅集体备课列表
   * 
   * @return
   */
  @RequestMapping(value = "/view/chayueActivity")
  public String chayueActivity(Activity data, Integer activityId, Integer typeId, Model m) {
    Activity activity = activityService.findOne(activityId);
    if (typeId == null) {
      if (activity != null) {
        typeId = activity.getTypeId();
      } else {
        return "";
      }
    }
    // 判断是否有权限
    m.addAttribute("activity", activity);
    m.addAttribute("data", data);

    if (typeId != null && Activity.TBJA.equals(typeId.intValue())) {// 同备教案
      m.addAttribute("url", "/jy/teachingView/view/chayueActivity1?activityId=" + activityId + "&typeId=" + typeId);
      return "/resview/pageofficeOpenWindow";
    } else if (typeId != null && (Activity.ZTYT.equals(typeId.intValue()) || Activity.SPJY.equals(typeId.intValue()))) {// 主题研讨
      // 有权限的参与人列表查询
      List<UserSpace> usList = activityService.findUserBySubjectAndGrade(activity);
      m.addAttribute("usList", usList);
      m.addAttribute("type", ResTypeConstants.ACTIVITY);

      Attach temp = new Attach();
      temp.setActivityId(activityId);
      List<Attach> attachList = activityAttachService.findAll(temp);
      m.addAttribute("activity", activity);
      m.addAttribute("attachList", attachList);
      return "/teachingview/view_chayueZhuYan";
    } else {
      return "";
    }

  }

  /**
   * 查阅集体备课列表
   * 
   * @return
   */
  @RequestMapping(value = "/view/chayueActivity1")
  public String chayueActivity1(Activity data, Integer activityId, Integer typeId, Model m) {

    Activity activity = activityService.findOne(activityId);
    if (typeId == null) {
      if (activity != null) {
        typeId = activity.getTypeId();
      } else {
        return "";
      }
    }
    // 判断是否有权限
    m.addAttribute("activity", activity);
    m.addAttribute("data", data);
    // 有权限的参与人列表查询
    List<UserSpace> usList = activityService.findUserBySubjectAndGrade(activity);
    m.addAttribute("usList", usList);
    m.addAttribute("type", ResTypeConstants.ACTIVITY);
    // 获取主备人的教案
    List<ActivityTracks> zhubeiList = activityTracksService.getActivityTracks_zhubei(activityId);
    m.addAttribute("zhubeiList", zhubeiList);
    // 整理教案集合
    List<ActivityTracks> zhengliList = activityTracksService.getActivityTracks_zhengli(activity.getId());
    m.addAttribute("zhengliList", zhengliList);
    return "/teachingview/view_chayueJiBei";
  }

  /**
   * 查看校际教研-主题研讨
   * 
   * @param id
   * @param m
   * @return
   */
  @RequestMapping("/view/view_schActivity_zhuyan")
  public String viewZtytSchoolActivity(Integer id, Model m, Integer listType) {
    UserSpace userSpace = (UserSpace) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE); // 用户空间
    SchoolActivity schoolActivity = schoolActivityService.findOne(id);
    if (schoolActivity.getInfoId() != null) {
      LessonInfo lessonInfo = lessonInfoService.findOne(schoolActivity.getInfoId());
      m.addAttribute("lessonName", lessonInfo.getLessonName());
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
    return "/teachingview/view_schActivity_zhuyan";
  }

  /**
   * 查看校际教研-集备教案
   * 
   * @param id
   * @param m
   * @return
   */
  @RequestMapping("/view/view_schActivity_jibei")
  public String viewTbjaSchoolActivity(Integer id, Model m, Integer listType) {

    SchoolActivity schoolActivity = schoolActivityService.findOne(id);
    if (schoolActivity.getInfoId() != null) {
      LessonInfo lessonInfo = lessonInfoService.findOne(schoolActivity.getInfoId());
      m.addAttribute("lessonName", lessonInfo.getLessonName());
      m.addAttribute("lessonInfo", lessonInfo);
    }
    m.addAttribute("listType", listType);
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
    // 整理教案集合
    List<SchoolActivityTracks> zhengliList = schoolActivityTracksService.getActivityTracks_zhengli(id);
    m.addAttribute("zhengliList", zhengliList);
    m.addAttribute("type", ResTypeConstants.SCHOOLTEACH);
    m.addAttribute("user1", userService.findOne(schoolActivity.getMainUserId()));
    m.addAttribute("user2", userService.findOne(schoolActivity.getOrganizeUserId()));
    m.addAttribute("us", userSpaceService.findOne(schoolActivity.getSpaceId()));
    m.addAttribute("operateType", 0);
    return "/teachingview/view_schActivity_jibei";
  }

  /**
   * 跳到评论页面
   * info:从页面获得当前页数的参数
   * m:把查询分页结果设置到内存里面，可以在页面进行展示
   * 
   * @return
   */
  @RequestMapping("/view/comment/list")
  public String list(CommentInfo info, Model m) {
    teachingViewService.findAllCommentReply(info, m);
    return "/teachingview/view_comment";
  }

  /**
   * 
   * @param info
   * @param m
   * @return
   */
  @RequestMapping("/view/companionMessage")
  public String companionMessage(SearchVo searchVo, Model m, Integer userIdSender) {
    User user = (User) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_USER);
    searchVo.setOrgId(user.getOrgId());
    Map<String, Object> dataMap = teachingViewService.getViewCompanionMessage(searchVo, userIdSender);
    m.addAttribute("messageData", dataMap.get("messageData"));
    m.addAttribute("userIdReceiver", searchVo.getUserId());
    m.addAttribute("userIdSender", userIdSender);
    return "/teachingview/companionNews";
  }
}
