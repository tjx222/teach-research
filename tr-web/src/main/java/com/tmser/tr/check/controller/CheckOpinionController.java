/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.check.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tmser.tr.check.bo.CheckInfo;
import com.tmser.tr.check.bo.CheckOpinion;
import com.tmser.tr.check.service.CheckInfoService;
import com.tmser.tr.check.service.CheckOpinionService;
import com.tmser.tr.common.ResTypeConstants;
import com.tmser.tr.common.page.PageList;
import com.tmser.tr.common.utils.WebThreadLocalUtils;
import com.tmser.tr.common.vo.Result;
import com.tmser.tr.common.web.controller.AbstractController;
import com.tmser.tr.lessonplan.bo.LessonInfo;
import com.tmser.tr.lessonplan.service.LessonInfoService;
import com.tmser.tr.uc.bo.User;
import com.tmser.tr.uc.utils.SessionKey;

/**
 * <pre>
 * 查阅意见基础类
 * </pre>
 *
 * @author
 * @version $Id: CheckOpinionController.java, v 1.0 2015-3-24 10:09:35
 */
@Controller
@RequestMapping("/jy/check")
public class CheckOpinionController extends AbstractController {

  @Autowired
  private CheckInfoService checkInfoService;
  @Autowired
  private CheckOpinionService checkOpinionService;
  @Autowired
  private LessonInfoService lessonInfoService;

  private static final Map<String, Object> paramMap = new HashMap<String, Object>();
  static {
    List<Integer> typeIds = new ArrayList<Integer>();
    typeIds.add(ResTypeConstants.JIAOAN);
    typeIds.add(ResTypeConstants.FANSI);
    typeIds.add(ResTypeConstants.KEJIAN);
    paramMap.put("resTypes", typeIds);
  }

  /**
   * 查阅信息入口页
   * 
   * @param type
   * @return
   */
  @RequestMapping(value = "/infoIndex")
  public String index(CheckInfo checkop, Model m) {
    return lookCheckOption(checkop, m);
  }

  /**
   * 查阅信息入口页
   * 仅查阅教学文章和听课记录用
   * 
   * @param type
   * @return
   */
  @RequestMapping(value = "/lookCheckOption")
  public String lookCheckOption(CheckInfo checkop, Model m) {
    User user = (User) WebThreadLocalUtils
        .getSessionAttrbitue(SessionKey.CURRENT_USER); // 用户
    if (user != null) {
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

      // 意见回复
      Map<Integer, List<CheckOpinion>> coMap = new HashMap<Integer, List<CheckOpinion>>();
      if (coList != null && coList.getPageSize() > 0) {

        CheckOpinion replyco = null;
        for (CheckOpinion checkopinion : coList.getDatalist()) {
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
      checkop.setUserId(user.getId());
      CheckInfo cinfo = checkInfoService.findOne(checkop);
      if (cinfo != null) {
        checkop.setLevel(cinfo.getLevel());
      }

      m.addAttribute("data", coList);
      m.addAttribute("coMap", coMap);
      m.addAttribute(
          "containsInput",
          "true".equalsIgnoreCase(checkop.getFlags())
              || "1".equals(checkop.getFlags()) ? 1 : 0);
      m.addAttribute("canReply", "false".equalsIgnoreCase(checkop.getFlago())
          || "0".equalsIgnoreCase(checkop.getFlago()) ? 0 : 1);
      m.addAttribute("model", checkop);
      m.addAttribute("titleShow", (checkop.getTitleShow() != null && checkop
          .getTitleShow() == true) ? "1" : "0");
    }

    return "check/opinion_list";
  }

  /**
   * 移动端（教案、课件、反思）查阅信息入口页
   * 
   * @param checkop
   * @return
   */
  @RequestMapping(value = "/infoIndexM")
  public String infoIndexM(CheckInfo checkop, Model m) {
    User user = (User) WebThreadLocalUtils
        .getSessionAttrbitue(SessionKey.CURRENT_USER); // 用户
    if (user != null) {
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

      // 意见回复
      Map<Integer, List<CheckOpinion>> coMap = new HashMap<Integer, List<CheckOpinion>>();
      if (coList != null && coList.getPageSize() > 0) {

        CheckOpinion replyco = null;
        for (CheckOpinion checkopinion : coList.getDatalist()) {
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
      m.addAttribute("data", coList);
      m.addAttribute("coMap", coMap);
      m.addAttribute(
          "containsInput",
          "true".equalsIgnoreCase(checkop.getFlags())
              || "1".equals(checkop.getFlags()) ? 1 : 0);
      m.addAttribute("model", checkop);
      m.addAttribute("titleShow", (checkop.getTitleShow() != null && checkop
          .getTitleShow() == true) ? "1" : "0");
    }

    return "check/check_list";
  }

  /**
   * 移动端（集体备课）查阅信息入口页
   * 
   * @param checkop
   * @return
   */
  @RequestMapping(value = "/infoIndexMjb")
  public String infoIndexMjb(CheckInfo checkop, Model m) {
    User user = (User) WebThreadLocalUtils
        .getSessionAttrbitue(SessionKey.CURRENT_USER); // 用户
    if (user != null) {
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

      // 意见回复
      Map<Integer, List<CheckOpinion>> coMap = new HashMap<Integer, List<CheckOpinion>>();
      if (coList != null && coList.getPageSize() > 0) {

        CheckOpinion replyco = null;
        for (CheckOpinion checkopinion : coList.getDatalist()) {
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
      m.addAttribute("data", coList);
      m.addAttribute("coMap", coMap);
      m.addAttribute(
          "containsInput",
          "true".equalsIgnoreCase(checkop.getFlags())
              || "1".equals(checkop.getFlags()) ? 1 : 0);
      m.addAttribute("model", checkop);
      m.addAttribute("titleShow", (checkop.getTitleShow() != null && checkop
          .getTitleShow() == true) ? "1" : "0");
    }

    return "check/activity/check_listjb";
  }

  /**
   * 离线端我的备课本查阅信息入口页
   * 
   * @param type
   * @return
   */
  @RequestMapping(value = "/checklistMobile", method = RequestMethod.GET)
  public String checklistMobile(
      @RequestParam(value = "lessonId", required = true, defaultValue = "0") String lessonId,
      @RequestParam(value = "userId", required = true, defaultValue = "0") Integer userId,
      Model m) {
    LessonInfo li = new LessonInfo();
    li.setLessonId(lessonId);
    li.setUserId(userId);
    li.setSchoolYear((Integer) WebThreadLocalUtils
        .getSessionAttrbitue(SessionKey.CURRENT_SCHOOLYEAR));
    li = lessonInfoService.findOne(li);
    if (li != null) {
      CheckInfo checkop = new CheckInfo();
      checkop.setResId(li.getId());
      return lessonInfoCheck(checkop, m);
    }
    return "check/lesson_list";
  }

  /**
   * 查阅信息入口页
   * 
   * @param type
   * @return
   */
  @RequestMapping(value = "/checklist")
  public String lessonInfoCheck(CheckInfo checkop, Model m) {
    User user = (User) WebThreadLocalUtils
        .getSessionAttrbitue(SessionKey.CURRENT_USER); // 用户
    if (user != null) {
      CheckOpinion co = new CheckOpinion();
      co.setType(0);
      co.setIsDelete(false);
      co.setIsHidden(false);
      co.currentPage(checkop.getPage().getCurrentPage());
      co.getPage().setPageSize(5);
      co.addOrder("id desc");
      co.setResId(checkop.getResId());
      co.addCustomCondition(" and resType in (:resTypes)", paramMap);
      PageList<CheckOpinion> coList = checkOpinionService.findByPage(co);

      // 意见回复
      Map<Integer, List<CheckOpinion>> coMap = new HashMap<Integer, List<CheckOpinion>>();
      if (coList != null && coList.getPageSize() > 0) {

        CheckOpinion replyco = null;
        for (CheckOpinion checkopinion : coList.getDatalist()) {
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
      m.addAttribute("data", coList);
      m.addAttribute("coMap", coMap);
      m.addAttribute(
          "containsInput",
          "true".equalsIgnoreCase(checkop.getFlags())
              || "1".equals(checkop.getFlags()) ? 1 : 0);
      m.addAttribute("model", checkop);
    }

    return "check/lesson_list";
  }

  /**
   * 保存查阅意见
   */
  @RequestMapping(value = "/check")
  @ResponseBody
  public Result saveOpinion(CheckInfo co, Model m) {
    Result rs = new Result();
    if (co != null) {
      boolean isOk = checkOpinionService.saveCheckbo(co);
      m.addAttribute("isOk", isOk);
      rs.setCode(isOk ? 1 : 0);
      rs.setMsg(isOk ? "查阅成功" : "查阅失败");
    }
    return rs;
  }

  /**
   * 保存查阅意见
   */
  @RequestMapping(value = "/delete/{id}")
  @ResponseBody
  public Result deleteOpinion(@PathVariable(value = "id") Integer opid) {
    Result rs = new Result();
    User user = (User) WebThreadLocalUtils
        .getSessionAttrbitue(SessionKey.CURRENT_USER); // 用户
    if (opid != null) {
      CheckOpinion cp = checkOpinionService.findOne(opid);
      if (cp != null && cp.getUserId().equals(user.getId())) {
        // TODO 删除元素意见，回复是否同步删除
        checkOpinionService.delete(opid);
      }

    } else {
      rs.setCode(0);
    }
    return rs;
  }

  /**
   * 保存查阅意见
   */
  @RequestMapping(value = "/reply")
  public String saveReply(CheckOpinion co, Model m) {
    if (co != null) {
      CheckOpinion isOk = checkOpinionService.saveCheckReply(co);
      m.addAttribute("reply", isOk);
    }
    return viewName("opinion_reply");
  }

}
