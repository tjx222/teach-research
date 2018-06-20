/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.check.cklp.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.tmser.tr.check.cklp.service.CheckLessonService;
import com.tmser.tr.common.utils.WebThreadLocalUtils;
import com.tmser.tr.common.web.controller.AbstractController;
import com.tmser.tr.manage.meta.Meta;
import com.tmser.tr.manage.meta.MetaUtils;
import com.tmser.tr.manage.meta.bo.Book;
import com.tmser.tr.manage.meta.bo.MetaRelationship;
import com.tmser.tr.manage.meta.bo.SysDic;
import com.tmser.tr.manage.meta.service.BookService;
import com.tmser.tr.manage.org.bo.Organization;
import com.tmser.tr.manage.org.service.OrganizationService;
import com.tmser.tr.uc.SysRole;
import com.tmser.tr.uc.bo.User;
import com.tmser.tr.uc.bo.UserSpace;
import com.tmser.tr.uc.service.UserSpaceService;
import com.tmser.tr.uc.utils.CurrentUserContext;
import com.tmser.tr.uc.utils.SessionKey;

/**
 * <pre>
 * 按课题查阅基础类
 * </pre>
 *
 * @author tmser
 * @version $Id: CheckLessonPlanController.java, v 1.0 2015年3月14日 下午2:32:31
 *          tmser Exp $
 */
@Controller
@RequestMapping("/jy/check/lesson/{type}")
public class CheckLessonController extends AbstractController {

  @Autowired
  private CheckLessonService checkLessonService;
  @Autowired
  private UserSpaceService userSpaceService;
  @Autowired
  private BookService bookService;

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
   * 查阅入口页
   * 
   * @param type
   * @return
   */
  @RequestMapping(value = "/index", method = RequestMethod.GET)
  public String index(@PathVariable("type") Integer type,
      @RequestParam(value = "grade", required = false) Integer grade,
      @RequestParam(value = "subject", required = false) Integer subject,
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
    m.addAttribute("type", type);
    m.addAttribute("grade", grade);
    m.addAttribute("subject", subject);

    return "check/cklp/index";
  }

  /**
   * 教师统计列表
   * 
   * @return
   */
  @RequestMapping(value = "/tchlist", method = RequestMethod.GET)
  public String teacherList(@PathVariable("type") Integer type, Integer grade, Integer subject, Model m) {
    m.addAttribute("grade", grade);
    m.addAttribute("subject", subject);
    m.addAttribute("staticsMap", checkLessonService.listTchLessonStatics(grade, subject, type));
    return "check/cklp/user_list";
  }

  /**
   * 查阅单个教师单册书课题入口
   * 
   * @param userid
   * @param bookid
   * @return
   */
  @RequestMapping(value = "/tch/{userid}", method = RequestMethod.GET)
  public String entryCheckTeacher(@PathVariable("type") Integer type, @PathVariable("userid") Integer userId,
      @RequestParam(value = "grade", required = false) Integer grade,
      @RequestParam(value = "subject", required = false) Integer subject,
      @RequestParam(value = "searchType", required = false) Integer searchType,
      @RequestParam(value = "termId", required = false) Integer termId,
      @RequestParam(value = "fasciculeId", required = false) Integer fasciculeId, Model m) {
    if (searchType == null) {
      searchType = 1;
      termId = fasciculeId = fasciculeId == null ? 0 :fasciculeId ;
    }
    if (fasciculeId == null) {
      UserSpace us = userSpaceService.getUserSpace(userId, SysRole.TEACHER.getId(), grade, subject);
      Book book = bookService.findOne(us.getBookId());
      fasciculeId = book.getFasciculeId();
    }
    m.addAttribute("checkIds", checkLessonService.checkedLessonMap(userId, grade, subject, type));
    m.addAttribute("resList",
        checkLessonService.listTchLessons(type, userId, grade, subject, searchType, termId, fasciculeId));
    m.addAttribute("writeCount",
        checkLessonService.countLessonInfo(type, userId, grade, subject, searchType, termId, fasciculeId));
    m.addAttribute("writeCount1", checkLessonService.countLessonPlan(type, userId, grade, subject, searchType, termId,
        fasciculeId, false, false));
    m.addAttribute("checkCount",
        checkLessonService.countCheckLesson(type, userId, grade, subject, searchType, termId, fasciculeId));
    m.addAttribute("checkCount1",
        checkLessonService.countCheckLessonPlan(type, userId, grade, subject, searchType, termId, fasciculeId));
    m.addAttribute("submitCount",
        checkLessonService.countSubmitLesson(type, userId, grade, subject, searchType, termId, fasciculeId));
    m.addAttribute("submitCount1",
        checkLessonService.countLessonPlan(type, userId, grade, subject, searchType, termId, fasciculeId, true, false));
    m.addAttribute("userId", userId);
    m.addAttribute("type", type);
    m.addAttribute("grade", grade);
    m.addAttribute("subject", subject);
    m.addAttribute("searchType", searchType);
    m.addAttribute("termId", termId);
    m.addAttribute("fasciculeId", fasciculeId);
    return "check/cklp/user_detail";
  }

  /**
   * 查看课题
   * 
   * @param lessPlan
   * @param m
   * @return
   */
  @RequestMapping("/tch/{userid}/view")
  public String viewLesson1(@PathVariable("type") Integer type, @RequestParam(value = "lesInfoId") Integer lesInfoId,
      Model m) {
    checkLessonService.viewLesson(type, lesInfoId, m);
    m.addAttribute("type", type);

    return "check/cklp/view_lesson";
  }

  /**
   * 查看课题
   * 
   * @param lessPlan
   * @param m
   * @return
   */
  @RequestMapping("/view")
  public String viewCheckedLesson(@PathVariable("type") Integer type,
      @RequestParam(value = "lesInfoId") Integer lesInfoId, Model m) {

    checkLessonService.viewCheckedLesson(type, lesInfoId, m);
    return "check/cklp/view_checked_lesson";
  }

  /**
   * 查阅单个教师单册书课题入口
   * 
   * @param userid
   * @param bookid
   * @return
   */
  @RequestMapping(value = "/tch/other/{userid}", method = RequestMethod.GET)
  public String entryCheckTeacherOther(@PathVariable("type") Integer type, @PathVariable("userid") Integer userId,
      @RequestParam(value = "grade", required = false) Integer grade,
      @RequestParam(value = "subject", required = false) Integer subject,
      @RequestParam(value = "searchType", required = false) Integer searchType,
      @RequestParam(value = "termId", required = false) Integer termId,
      @RequestParam(value = "fasciculeId", required = false) Integer fasciculeId, Model m) {
	searchType = 1;
    if (fasciculeId == null) {
    	fasciculeId = 0;
    }
    m.addAttribute("checkIds", checkLessonService.checkedLessonMap(userId, grade, subject, type));
    m.addAttribute("resList", checkLessonService.listTchLessonsOther(type, userId, grade, subject, searchType, fasciculeId));
    m.addAttribute("userId", userId);
    m.addAttribute("type", type);
    m.addAttribute("grade", grade);
    m.addAttribute("subject", subject);
    m.addAttribute("fasciculeId", fasciculeId);
    m.addAttribute("searchType", searchType);
    m.addAttribute("termId", termId);
    return "check/cklp/user_otherdetail";
  }

  /**
   * 查看其他反思
   * 
   * @param lessPlan
   * @param m
   * @return
   */
  @RequestMapping("/tch/other/{userid}/view")
  public String viewLessonOther(@PathVariable("type") Integer type, @RequestParam(value = "planId") Integer planId,
      Model m) {
    checkLessonService.viewOtherLesson(type, planId, m);
    return "check/cklp/view_otherlesson";
  }
}
