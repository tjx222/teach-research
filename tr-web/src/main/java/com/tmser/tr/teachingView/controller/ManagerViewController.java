package com.tmser.tr.teachingView.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tmser.tr.common.utils.WebThreadLocalUtils;
import com.tmser.tr.common.web.controller.AbstractController;
import com.tmser.tr.lessonplan.bo.LessonPlan;
import com.tmser.tr.teachingView.service.TeachingViewService;
import com.tmser.tr.teachingview.vo.SearchVo;
import com.tmser.tr.uc.bo.UserSpace;
import com.tmser.tr.uc.utils.SessionKey;

/**
 * 教研一览管理者详情页
 * 
 * <pre>
 *
 * </pre>
 *
 * @author dell
 * @version $Id: ManagerViewController.java, v 1.0 2016年5月9日 上午10:22:37 dell Exp
 *          $
 */
@Controller
@RequestMapping("/jy/teachingView")
public class ManagerViewController extends AbstractController {

  @Autowired
  private TeachingViewService teachingViewService;

  /**
   * 教学管理者情况一览
   * 
   * @author wangyao
   * @param searchVo
   * @param model
   * @return
   */
  @RequestMapping("/manager/m_user_list")
  public String teachingView_manager(SearchVo searchVo, Model model) {
    UserSpace userSpace = (UserSpace) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE); // 用户空间
    Integer schoolYear = (Integer) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SCHOOLYEAR); // 学年
    searchVo.setOrgId(userSpace.getOrgId());
    if (searchVo.getTermId() == null) {
      searchVo.setTermId((Integer) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_TERM));
    }
    searchVo.setPhaseId(userSpace.getPhaseId());
    searchVo.setUserId(userSpace.getUserId());
    searchVo.setSchoolYear(schoolYear);
    try {
      List<Map<String, Object>> dataList = teachingViewService.getManagerDataList(searchVo, userSpace);
      model.addAttribute("dataList", dataList);
      model.addAttribute("dataSize", dataList.size());
    } catch (Exception e) {
      logger.error("查阅管理者用户一览列表失败", e);
    }
    return "/teachingview/manager/m_user_list";
  }

  @ResponseBody
  @RequestMapping("/manager/m_user_list_sort")
  public Object teachingView_managers(SearchVo searchVo, Model model) {
    UserSpace userSpace = (UserSpace) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE); // 用户空间
    try {
      List<Map<String, Object>> dataList = teachingViewService.getManagerDataList(searchVo, userSpace);
      return dataList;
    } catch (Exception e) {
      logger.error("管理者一览列表排序失败", e);
    }
    return null;
  }

  /**
   * 教学管理者详情页
   * 
   * @author wangyao
   * @param searchVo
   * @param model
   * @return
   */
  @RequestMapping("/manager/m_details")
  public String teachingView_manager_detail(SearchVo searchVo, Model model) {
    Integer schoolYear = (Integer) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SCHOOLYEAR); // 学年
    searchVo.setSchoolYear(schoolYear);
    UserSpace userSpace = (UserSpace) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE); // 用户空间
    searchVo.setPhaseId(userSpace.getPhaseId());
    searchVo.setOrgId(userSpace.getOrgId());
    try {
      List<Map<String, Object>> dataList = teachingViewService.getManagerDetailDataList(searchVo);
      model.addAttribute("dataMap", dataList.size() > 0 ? dataList.get(0) : new HashMap<String, Object>());
    } catch (Exception e) {
      logger.error("管理者详情页查询失败", e);
    }
    return "/teachingview/manager/m_details";
  }

  /**
   * 教学管理者详情页(教案课件)
   * 
   * @author wangyao
   * @param searchVo
   * @param model
   * @return
   */
  @RequestMapping("/manager/m_lesson")
  public String m_lesson(SearchVo searchVo, Model model) {
    try {
      Map<String, Object> dataList = teachingViewService.getManagerLessonDetailDataByType(searchVo);
      model.addAttribute("chekInfoData", dataList.get("chekInfoData"));
      model.addAttribute("lessonInfoData", dataList.get("lessonInfoData"));
    } catch (Exception e) {
      logger.error("查询管理者教案、课件、反思资源详情失败", e);
    }
    return "/teachingview/manager/m_lesson";
  }

  /**
   * 教学管理者详情页(教案课件反思)
   * 
   * @author wangyao
   * @param searchVo
   * @param model
   * @return
   */
  @RequestMapping("/manager/m_lesson_fansi")
  public String m_lesson_fansi(SearchVo searchVo, Model model) {
    try {
      searchVo.setFlago(String.valueOf(LessonPlan.KE_HOU_FAN_SI));
      Map<String, Object> fansiMap = teachingViewService.getManagerLessonDetailDataByType(searchVo);
      model.addAttribute("fansiChekInfoData", fansiMap.get("chekInfoData"));
      model.addAttribute("fansiData", fansiMap.get("lessonInfoData"));
      Map<String, Object> qiTaFanSiMap = teachingViewService.getManagerLessonFanSi(searchVo,
          String.valueOf(LessonPlan.QI_TA_FAN_SI));
      model.addAttribute("qiTaFanSiChekInfoData", qiTaFanSiMap.get("fansiCheckData"));
      model.addAttribute("qiTaFanSiData", qiTaFanSiMap.get("fansiData"));

    } catch (Exception e) {
      logger.error("查询管理者反思资源详情失败", e);
    }
    return "/teachingview/manager/m_lesson_fansi";
  }

  /**
   * 管理者可查阅集体备课
   * 
   * @author wangyao
   * @param model
   * @param page
   * @param request
   * @return
   */
  @RequestMapping("/manager/m_check_jitibeike")
  public String manager_check_jitibeike(Model model, SearchVo searchVo) {
    try {
      Map<String, Object> data = teachingViewService.getManagerCheckActivityDetailData(searchVo);
      model.addAttribute("listPage", data.get("listPage"));
      model.addAttribute("activityData", data.get("activityData"));
    } catch (Exception e) {
      logger.error("查询管理者集体备课资源详情失败", e);
    }
    return "/teachingview/manager/m_check_jitibeike";
  }

  /**
   * 管理者发起、参与集体备课
   * 
   * @author wangyao
   * @param model
   * @param page
   * @param request
   * @return
   */
  @RequestMapping("/manager/m_partLaunchActivity")
  public String m_partLaunchActivity(Model model, SearchVo searchVo) {
    try {
      Map<String, Object> dataMap = teachingViewService.getManagerActivityDetailData(searchVo);// 发起
      model.addAttribute("launchData", dataMap.get("launchData"));
      model.addAttribute("partInData", dataMap.get("partInData"));
      boolean isLeader = (boolean) dataMap.get("isLeader");
      if (!isLeader) {
        return "/teachingview/manager/m_partActivity";
      }
    } catch (Exception e) {
      logger.error("查询管理者发起参与集体备课资源详情失败", e);
    }
    return "/teachingview/manager/m_partLaunchActivity";
  }

  /**
   * 管理者同伴互助资源交流数
   * 
   * @author wangyao
   * @param model
   * @param page
   * @param request
   * @return
   */
  @RequestMapping("/manager/m_companion")
  public String m_companion(Model model, SearchVo searchVo) {
    try {
      Map<String, Object> messageData = teachingViewService.getManagerCompanionDetailData(searchVo);
      model.addAttribute("messageData", messageData.get("listPage"));
    } catch (Exception e) {
      logger.error("查询管理者同伴互助资源交流数详情失败", e);
    }
    return "/teachingview/manager/m_companion";
  }

}
