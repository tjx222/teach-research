/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.check.activity.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.tmser.tr.activity.bo.Activity;
import com.tmser.tr.activity.bo.ActivityTracks;
import com.tmser.tr.activity.service.ActivityService;
import com.tmser.tr.activity.service.ActivityTracksService;
import com.tmser.tr.check.activity.service.CheckActivityService;
import com.tmser.tr.common.ResTypeConstants;
import com.tmser.tr.common.page.Page;
import com.tmser.tr.common.utils.WebThreadLocalUtils;
import com.tmser.tr.common.web.controller.AbstractController;
import com.tmser.tr.lessonplan.service.LessonPlanService;
import com.tmser.tr.manage.meta.Meta;
import com.tmser.tr.manage.meta.MetaUtils;
import com.tmser.tr.manage.meta.bo.MetaRelationship;
import com.tmser.tr.manage.meta.bo.SysDic;
import com.tmser.tr.manage.org.bo.Organization;
import com.tmser.tr.manage.org.service.OrganizationService;
import com.tmser.tr.manage.resources.bo.Attach;
import com.tmser.tr.manage.resources.service.AttachService;
import com.tmser.tr.uc.SysRole;
import com.tmser.tr.uc.bo.User;
import com.tmser.tr.uc.bo.UserSpace;
import com.tmser.tr.uc.service.SchoolYearService;
import com.tmser.tr.uc.utils.CurrentUserContext;
import com.tmser.tr.uc.utils.SessionKey;

/**
 * <pre>
 * 查阅活动基础类
 * </pre>
 *
 * @author
 * @version $Id: CheckActivityController.java, v 1.0 2015-4-16 10:09:35
 */
@Controller
@RequestMapping("/jy/check/activity")
public class CheckActivityController extends AbstractController {

  @Autowired
  private CheckActivityService checkActivityService;
  @Resource
  private ActivityService activityService;
  @Resource
  private LessonPlanService lessonPlanService;
  @Resource
  private AttachService activityAttachService;
  @Resource
  private ActivityTracksService activityTracksService;
  @Resource
  private SchoolYearService schoolYearService;

  @Autowired
  private OrganizationService organizationService;

  private static final Comparator<Meta> metaCt = new Comparator<Meta>() {
    @Override
    public int compare(Meta o1, Meta o2) {
      if (o1 instanceof SysDic && o2 instanceof SysDic) {
        return ((SysDic) o1).getDicOrderby() - ((SysDic) o2).getDicOrderby();
      }
      return 0;
    }
  };

  /**
   * 查阅集体备课（活动）入口页
   * 
   * @param type
   * @return
   */
  @RequestMapping(value = "/index", method = RequestMethod.GET)
  public String index(@RequestParam(value = "grade", required = false) Integer grade,
      @RequestParam(value = "subject", required = false) Integer subject,
      @RequestParam(value = "term", required = false) Integer term,
      @RequestParam(value = "phaseId", required = false) Integer phaseId, Model m) {

    User user = CurrentUserContext.getCurrentUser(); // 用户
    @SuppressWarnings("unchecked")
    List<UserSpace> spaces = (List<UserSpace>) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.USER_SPACE_LIST);
    Organization org = organizationService.findOne(user.getOrgId());
    List<MetaRelationship> phases = null;

    boolean hasPhase = true;
    Set<Integer> actPhases = new HashSet<>();
    for (UserSpace userSpace : spaces) {
      if (!SysRole.TEACHER.getId().equals(userSpace.getSysRoleId())) {
        if (userSpace.getPhaseId() != null && userSpace.getPhaseId() != 0) {
          actPhases.add(userSpace.getPhaseId());
        } else {
          hasPhase = false;// 全学段
        }
      }
    }

    if (hasPhase) {
      phases = new ArrayList<>();
      for (Integer pid : actPhases) {
        phases.add(MetaUtils.getMetaRelation(pid));
      }
    } else {
      phases = MetaUtils.getOrgTypeMetaProvider().listAllPhase(org.getSchoolings());
    }

    if (grade == null) {
      List<Meta> grades = new ArrayList<Meta>();
      List<Meta> subjects = new ArrayList<Meta>();

      if (phaseId == null) {
        for (MetaRelationship meta : phases) {// 默认学段
          phaseId = meta.getId();
          break;
        }
      }

      boolean hasGrade = true;
      boolean hasSub = true;
      for (UserSpace userSpace : spaces) {
        if (!SysRole.TEACHER.getId().equals(userSpace.getSysRoleId())) {
          if (userSpace.getGradeId() == null || 0 == userSpace.getGradeId()) {
            hasGrade = false;
          } else if (phaseId.equals(userSpace.getPhaseId())) {
            grades.add(MetaUtils.getMeta(userSpace.getGradeId()));
          }

          if (userSpace.getSubjectId() == null || 0 == userSpace.getSubjectId()) {
            hasSub = false;
          } else if (phaseId.equals(userSpace.getPhaseId())) {
            subjects.add(MetaUtils.getMeta(userSpace.getSubjectId()));
          }

          if (userSpace.getPhaseId() != null && userSpace.getPhaseId() != 0) {
            actPhases.add(userSpace.getPhaseId());
          } else {
            hasPhase = false;// 全学段
          }
        }
      }

      if (hasSub) {
        Collections.sort(grades, metaCt);
        m.addAttribute("grades", grades);
      }

      if (hasGrade) {
        Collections.sort(subjects, metaCt);
        m.addAttribute("subjects", subjects);
      }

    } else if (phaseId == null) {
      findPhase: for (Entry<Integer, List<Meta>> pg : MetaUtils.getOrgTypeMetaProvider()
          .listPhaseGradeMap(org.getSchoolings()).entrySet()) {
        for (Meta g : pg.getValue()) {
          if (g.getId().equals(grade)) {
            phaseId = pg.getKey();
            break findPhase;
          }
        }
      }
    }

    m.addAttribute("phases", phases);
    m.addAttribute("phaseId", phaseId);
    m.addAttribute("phase", MetaUtils.getMetaRelation(phaseId));
    m.addAttribute("term", term);
    m.addAttribute("currentterm", schoolYearService.getCurrentTerm());
    m.addAttribute("subject", subject);
    m.addAttribute("grade", grade);
    return "check/activity/index";
  }

  /**
   * 已提交可查阅的活动列表
   * 
   * @return
   */
  @RequestMapping(value = "/activitylist")
  public String activitylist(Integer grade, Integer subject, Integer term, Model m, Page page) {
    m.addAttribute("grade", grade);
    m.addAttribute("subject", subject);
    if (term == null) {
      term = (Integer) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_TERM);// 学期
    }
    m.addAttribute("term", term);
    Map<String, Object> dataMap = checkActivityService.findCheckActivity(grade, subject, term, page);
    m.addAttribute("countAll", dataMap.get("countAll"));
    m.addAttribute("countAudit", dataMap.get("countAudit"));
    m.addAttribute("listPage", dataMap.get("listPage"));
    m.addAttribute("checkedResIdMap", dataMap.get("checkedResIdMap"));
    return "check/activity/activity_list";
  }

  /**
   * 查阅活动列表
   * 
   * @return
   */
  @RequestMapping(value = "/chayueActivity")
  public String chayueActivity(Integer activityId, Integer typeId, Integer grade, Integer subject, Integer term,
      Model m) {
    if (typeId != null && Activity.TBJA.equals(typeId.intValue())) {// 同备教案
      StringBuilder url = new StringBuilder(
          "/jy/check/activity/chayueActivity1?activityId=" + activityId + "&typeId=" + typeId);
      if (grade != null) {
        url.append("&grade=" + grade);
      }
      if (subject != null) {
        url.append("&subject=" + subject);
      }
      if (term != null) {
        url.append("&term=" + term);
      }
      m.addAttribute("url", url.toString());
      return "/resview/pageofficeOpenWindow";
    } else if (typeId != null
        && (Activity.ZTYT.intValue() == typeId.intValue() || Activity.SPJY.intValue() == typeId.intValue())) {// 主题研讨、视频教研
      Activity activity = activityService.findOne(activityId);
      // 判断是否有权限
      Assert.isTrue(ifHavePower(activity), "没有权限");
      m.addAttribute("activity", activity);
      // 有权限的参与人列表查询
      List<UserSpace> usList = activityService.findUserBySubjectAndGrade(activity);
      m.addAttribute("usList", usList);
      m.addAttribute("type", ResTypeConstants.ACTIVITY);
      m.addAttribute("grade", grade);
      m.addAttribute("subject", subject);
      m.addAttribute("term", term);

      Attach temp = new Attach();
      temp.setActivityId(activityId);
      List<Attach> attachList = activityAttachService.findAll(temp);
      m.addAttribute("activity", activity);
      m.addAttribute("attachList", attachList);
      if (Activity.ZTYT.equals(typeId.intValue())) {
        return "/check/activity/chayueZhuYan";
      }
      return "/check/activity/chayueShipin";
    } else {
      return "";
    }

  }

  /**
   * 查阅活动列表
   * 
   * @return
   */
  @RequestMapping(value = "/chayueActivity1")
  public String chayueActivity1(Integer activityId, Integer typeId, Integer grade, Integer subject, Integer term,
      Model m) {
    Activity activity = activityService.findOne(activityId);
    // 判断是否有权限
    Assert.isTrue(ifHavePower(activity), "没有权限");
    m.addAttribute("activity", activity);
    // 有权限的参与人列表查询
    List<UserSpace> usList = activityService.findUserBySubjectAndGrade(activity);
    m.addAttribute("usList", usList);
    m.addAttribute("type", ResTypeConstants.ACTIVITY);
    m.addAttribute("grade", grade);
    m.addAttribute("subject", subject);
    m.addAttribute("term", term);
    // 获取主备人的教案
    List<ActivityTracks> zhubeiList = activityTracksService.getActivityTracks_zhubei(activityId);
    m.addAttribute("zhubeiList", zhubeiList);
    // 整理教案集合
    List<ActivityTracks> zhengliList = activityTracksService.getActivityTracks_zhengli(activity.getId());
    m.addAttribute("zhengliList", zhengliList);
    return "/check/activity/chayueJiBei";

  }

  /**
   * 判断是否有权限
   * 
   * @param activity
   * @return
   */
  private boolean ifHavePower(Activity activity) {
    boolean flag = false;
    User cuser = CurrentUserContext.getCurrentUser();
    if (!activity.getOrgId().equals(cuser.getOrgId())) {
      return false;
    }

    boolean[] hasroles = SecurityUtils.getSubject().hasRoles(Arrays.asList(SysRole.XZ.name().toLowerCase(),
        SysRole.FXZ.name().toLowerCase(), SysRole.ZR.name().toLowerCase()));
    for (boolean b : hasroles) {
      if (b) {
        return b;
      }
    }
    @SuppressWarnings("unchecked")
    List<UserSpace> userSpaces = (List<UserSpace>) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.USER_SPACE_LIST); // 用户空间
    for (UserSpace userSpace : userSpaces) {
      if (SysRole.XKZZ.getId().equals(userSpace.getSysRoleId())
          && activity.getSubjectIds().contains("," + String.valueOf(userSpace.getSubjectId()) + ",")) {
        flag = true;
      } else if (SysRole.NJZZ.getId().equals(userSpace.getSysRoleId())
          && activity.getGradeIds().contains("," + String.valueOf(userSpace.getGradeId()) + ",")) {
        flag = true;
      } else if ((SysRole.BKZZ.getId().equals(userSpace.getSysRoleId()))
          && activity.getSubjectIds().contains("," + String.valueOf(userSpace.getSubjectId()) + ",")
          && activity.getGradeIds().contains("," + String.valueOf(userSpace.getGradeId()) + ",")) {
        flag = true;
      }
    }

    return flag;
  }
}
