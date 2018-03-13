package com.tmser.tr.teachingView.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tmser.tr.common.utils.WebThreadLocalUtils;
import com.tmser.tr.common.web.controller.AbstractController;
import com.tmser.tr.manage.meta.MetaUtils;
import com.tmser.tr.recordbag.bo.Record;
import com.tmser.tr.teachingView.service.TeachingViewService;
import com.tmser.tr.teachingview.vo.SearchVo;
import com.tmser.tr.uc.SysRole;
import com.tmser.tr.uc.bo.User;
import com.tmser.tr.uc.bo.UserSpace;
import com.tmser.tr.uc.service.UserService;
import com.tmser.tr.uc.service.UserSpaceService;
import com.tmser.tr.uc.utils.SessionKey;

/**
 * 
 * <pre>
 * 教研一览controller
 * </pre>
 *
 * @author wangdawei
 * @version $Id: TeachingViewController.java, v 1.0 2016年4月8日 上午10:42:36
 *          wangdawei Exp $
 */
@Controller
@RequestMapping("/jy/teachingView")
public class TeacherViewController extends AbstractController {

  private static final Logger logger = LoggerFactory.getLogger(TeacherViewController.class);

  @Autowired
  private TeachingViewService teachingViewService;
  @Autowired
  private UserSpaceService userspaceService;
  @Autowired
  private UserService userService;

  /**
   * 教研一览入口
   * 
   * @return
   * @author wangdawei
   */
  @RequestMapping("/index")
  public String index(Model model) {
    UserSpace userSpace = (UserSpace) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE); // 用户空间
    if (userSpace == null) {
      return "";
    }
    Integer roleId = userSpace.getSysRoleId();// 角色id
    if (roleId.intValue() == SysRole.TEACHER.getId().intValue()) { // 老师
      return "redirect:/jy/teachingView/teacher/index";
    } else { // 管理者
      model.addAttribute("roleId", roleId);
      return "/teachingview/manager/index";
    }
  }

  /**
   * 教师首页
   * 
   * @return
   * @author wangdawei
   */
  @RequestMapping("/teacher/index")
  public String teacherIndex(SearchVo searchVo, Model m) {
    UserSpace userSpace = (UserSpace) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE); // 用户空间
    searchVo.setOrgId(userSpace.getOrgId());
    searchVo.setTermId((Integer) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_TERM));
    searchVo.setUserId(userSpace.getUserId());
    searchVo.setSpaceId(userSpace.getId());
    searchVo.setGradeId(userSpace.getGradeId());
    searchVo.setSubjectId(userSpace.getSubjectId());
    searchVo.setPhaseId(userSpace.getPhaseId());
    try {
      Map<String, Object> dataMap = teachingViewService.getTeacherDataDetail(searchVo);
      m.addAttribute("dataMap", dataMap);
    } catch (Exception e) {
      logger.error("获取本人教研情况详情出错", e);
    }
    m.addAttribute("search", searchVo);
    return "/teachingview/teacher/index";
  }

  /**
   * 教师教研情况一览
   * 
   * @param searchVo
   * @param model
   * @return
   * @author wangdawei
   */
  @RequestMapping("/manager/teachingView_t")
  public String teachingView_teacherList(SearchVo searchVo, Model m) {
    UserSpace userSpace = (UserSpace) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE); // 用户空间
    List<Map<String, String>> gradeList = teachingViewService.getGradeList();
    List<Map<String, String>> subjectList = teachingViewService.getSubjectList();
    searchVo.setOrgId(userSpace.getOrgId());
    searchVo.setTermId((Integer) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_TERM));
    searchVo.setGradeIdIfNull(Integer.valueOf(gradeList.get(0).get("id")));
    searchVo.setSubjectIdIfNull(Integer.valueOf(subjectList.get(0).get("id")));
    try {
      List<Map<String, Object>> dataList = teachingViewService.getTeacherDataList(searchVo);
      m.addAttribute("dataList", dataList);
    } catch (Exception e) {
      logger.error("获取教师教研情况集合出错", e);
    }
    m.addAttribute("gradeList", gradeList);
    m.addAttribute("subjectList", subjectList);
    m.addAttribute("search", searchVo);
    m.addAttribute("gradeName", MetaUtils.getMeta(searchVo.getGradeId()).getName());
    m.addAttribute("subjectName", MetaUtils.getMeta(searchVo.getSubjectId()).getName());
    return "/teachingview/manager/teacherDataList";
  }

  /**
   * 教师详情
   * 
   * @param searchVo
   * @param m
   * @return
   * @author wangdawei
   */
  @RequestMapping("/manager/teachingView_t_detail")
  public String teachingView_teacherDetail(SearchVo searchVo, Model m) {
    UserSpace userSpace = userspaceService.findOne(searchVo.getSpaceId()); // 所查询的教师的用户空间
    User user = userService.findOne(userSpace.getUserId());
    searchVo.setOrgId(userSpace.getOrgId());
    searchVo.setTermId((Integer) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_TERM));
    searchVo.setUserId(userSpace.getUserId());
    searchVo.setSpaceId(userSpace.getId());
    searchVo.setGradeId(userSpace.getGradeId());
    searchVo.setSubjectId(userSpace.getSubjectId());
    searchVo.setPhaseId(userSpace.getPhaseId());
    try {
      Map<String, Object> dataMap = teachingViewService.getTeacherDataDetail(searchVo);
      m.addAttribute("dataMap", dataMap);
    } catch (Exception e) {
      logger.error("获取教师教研情况详情出错", e);
    }
    m.addAttribute("search", searchVo);
    m.addAttribute("userSpace", userSpace);
    m.addAttribute("user", user);
    m.addAttribute("gradeName", MetaUtils.getMeta(searchVo.getGradeId()).getName());
    m.addAttribute("subjectName", MetaUtils.getMeta(searchVo.getSubjectId()).getName());
    return "/teachingview/manager/teacherDataDetail";
  }

  /**
   * 年级教研情况一览
   * 
   * @param searchVo
   * @param model
   * @return
   * @author wangdawei
   */
  @RequestMapping("/manager/teachingView_g")
  public String teachingView_grade(SearchVo searchVo, Model m) {
    UserSpace userSpace = (UserSpace) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE); // 用户空间
    searchVo.setOrgId(userSpace.getOrgId());
    searchVo.setPhaseId(userSpace.getPhaseId());
    searchVo.setTermId((Integer) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_TERM));
    if (userSpace.getSubjectId() != null && userSpace.getSubjectId() != 0) {
      searchVo.setSubjectId(userSpace.getSubjectId());
    }
    try {
      List<Map<String, Object>> dataList = teachingViewService.getGradeDataList(searchVo);
      m.addAttribute("dataList", dataList);
    } catch (Exception e) {
      logger.error("获取年级教研情况一览出错", e);
    }
    m.addAttribute("search", searchVo);
    return "/teachingview/manager/gradeDataList";
  }

  /**
   * 学科教研情况一览
   * 
   * @param searchVo
   * @param m
   * @return
   * @author wangdawei
   */
  @RequestMapping("/manager/teachingView_s")
  public String teachingView_subject(SearchVo searchVo, Model m) {
    UserSpace userSpace = (UserSpace) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE); // 用户空间
    searchVo.setOrgId(userSpace.getOrgId());
    searchVo.setTermId((Integer) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_TERM));
    if (userSpace.getGradeId() != null && userSpace.getGradeId() != 0) {
      searchVo.setGradeId(userSpace.getGradeId());
    }
    try {
      List<Map<String, Object>> dataList = teachingViewService.getSubjectDataList(searchVo);
      m.addAttribute("dataList", dataList);
    } catch (Exception e) {
      logger.error("获取学科教研情况一览出错", e);
    }
    m.addAttribute("search", searchVo);
    return "/teachingview/manager/subjectDataList";
  }

  /**
   * 获取撰写和分享的教案集合
   * 
   * @param searchVo
   * @param m
   * @return
   * @author wangdawei
   */
  @RequestMapping("/teacher/list_jiaoan")
  public String list_jiaoan(SearchVo searchVo, Model m) {
    teachingViewService.getJiaoanDataList(searchVo, m);
    return "/teachingview/teacher/list_jiaoan";
  }

  /**
   * 获取撰写和分享的课件集合
   * 
   * @param searchVo
   * @param m
   * @return
   * @author wangdawei
   */
  @RequestMapping("/teacher/list_kejian")
  public String list_kejian(SearchVo searchVo, Model m) {
    teachingViewService.getKejianDataList(searchVo, m);
    return "/teachingview/teacher/list_kejian";
  }

  /**
   * 撰写和分享的反思集合
   * 
   * @param searchVo
   * @param m
   * @return
   */
  @RequestMapping("/teacher/list_fansi")
  public String list_fansi(SearchVo searchVo, Model m) {
    teachingViewService.getFansiDataList(searchVo, m);
    return "/teachingview/teacher/list_fansi";
  }

  /**
   * 获取已参与集体备课集合
   * 
   * @param searchVo
   * @param m
   * @return
   * @author wangdawei
   */
  @RequestMapping("/teacher/list_activity")
  public String list_activity(SearchVo searchVo, Model m) {
    teachingViewService.getActivityDataList(searchVo, m);
    return "/teachingview/teacher/list_activity";
  }

  /**
   * 获取自己的留言和附件
   * 
   * @param searchVo
   * @param m
   * @return
   * @author wangdawei
   */
  @RequestMapping("/teacher/list_companion")
  public String list_companion(SearchVo searchVo, Model m) {
    teachingViewService.getCompanionDataList(searchVo, m);
    return "/teachingview/teacher/list_companion";
  }

  /**
   * 获取成长档案袋集合
   * 
   * @param searchVo
   * @param m
   * @return
   * @author wangdawei
   */
  @RequestMapping("/teacher/list_recordBag")
  public String list_recordBag(SearchVo searchVo, Model m) {
    teachingViewService.getRecordBagDataList(searchVo, m);
    return "/teachingview/teacher/list_recordBag";
  }

  /**
   * 获取成长档案袋内详情
   * 
   * @param id
   * @param type
   * @param m
   * @return
   */
  @RequestMapping("/teacher/detail_recordBag")
  public String detail_recordBag(Record model, Integer type, Integer termId, Integer spaceId, Model m) {
    Integer flag = teachingViewService.getRecordBagDetail(model, termId, m);
    m.addAttribute("id", model.getBagId());
    m.addAttribute("type", type);
    m.addAttribute("userSpace", userspaceService.findOne(spaceId));
    if (flag == 1) {
      return "/teachingview/teacher/detail_recordBag_activity";
    } else if (flag == 2) {
      return "/teachingview/teacher/detail_recordBag_lecture";
    }
    return "/teachingview/teacher/detail_recordBag_other";
  }

}
