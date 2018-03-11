/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.myplanbook.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tmser.tr.browse.BaseResTypes;
import com.tmser.tr.browse.utils.BrowseRecordUtils;
import com.tmser.tr.common.utils.WebThreadLocalUtils;
import com.tmser.tr.common.web.controller.AbstractController;
import com.tmser.tr.lessonplan.bo.LessonInfo;
import com.tmser.tr.lessonplan.bo.LessonPlan;
import com.tmser.tr.lessonplan.service.LessonInfoService;
import com.tmser.tr.manage.meta.bo.Book;
import com.tmser.tr.manage.meta.service.BookService;
import com.tmser.tr.manage.meta.vo.BookLessonVo;
import com.tmser.tr.manage.resources.service.ResourcesService;
import com.tmser.tr.myplanbook.service.MyPlanBookService;
import com.tmser.tr.uc.bo.UserSpace;
import com.tmser.tr.uc.utils.SessionKey;
import com.tmser.tr.utils.StringUtils;
import com.tmser.tr.writelessonplan.service.LessonPlanService;
import com.zhuozhengsoft.pageoffice.FileSaver;
import com.zhuozhengsoft.pageoffice.OfficeVendorType;
import com.zhuozhengsoft.pageoffice.OpenModeType;
import com.zhuozhengsoft.pageoffice.PageOfficeCtrl;

/**
 * <pre>
 * 我的备课本
 * </pre>
 *
 * @author wangdawei
 * @version $Id: MyPlanBook.java, v 1.0 2015年3月5日 下午3:13:47 wangdawei Exp $
 */
@Controller
@RequestMapping(value = "/jy/myplanbook/")
public class MyPlanBookController extends AbstractController {

  @Autowired
  private BookService bookService;// 获取上下册书 service
  @Autowired
  private MyPlanBookService myPlanBookService;// 我的备课本service
  @Autowired
  private LessonPlanService lessonPlanServie;// 备课资源service
  @Autowired
  private ResourcesService resourcesService;
  @Autowired
  private LessonInfoService lessonInfoService;

  /**
   * 跳转到我的备课本首页
   * 
   * @param m
   * @return
   */
  @RequestMapping(value = "tomyplanbook")
  public String toMyPlanBook(Model m) {
    UserSpace userSpace = (UserSpace) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE); // 用户空间
    // 从缓存中获取上下册书
    List<Book> bookList = new ArrayList<Book>();
    Book book1 = bookService.findOne(userSpace.getBookId());
    Book book2 = bookService.findOne(book1.getRelationComId());
    bookList.add(book1);
    if (book2 != null) {
      bookList.add(book2);
    }
    // 获取最近保存的备课资源
    LessonPlan lessonPlan = lessonPlanServie.getLatestLessonPlan(null);
    m.addAttribute("bookList", bookList);
    m.addAttribute("lessonPlan", lessonPlan);
    m.addAttribute("currentBook", book1);
    return "/myplanbook/myplanbook";
  }

  /**
   * 获取书下的备课详情
   * 
   * @param bookId
   * @param m
   */
  @RequestMapping(value = "getplanbookdetail")
  public void getPlanBookDetail(String bookId, Model m) {
    // 获取书下的备课资源统计数据
    Map<String, Integer> countData = lessonPlanServie.getCountDataOfPlanForBook(bookId);
    // 获取有备课资源的课题集合
    List<BookLessonVo> lessonList = myPlanBookService.getLessonListForMyPlanBook(bookId);
    m.addAttribute("countData", countData);
    m.addAttribute("lessonList", lessonList);
  }

  /**
   * 获取课题下的备课资源列表
   * 
   * @param lessonId
   * @param m
   * @return
   */
  @RequestMapping(value = "tomyplandetail")
  public String tomyplandetail(String lessonId, Model m) {
    if (StringUtils.isNotBlank(lessonId)) {
      // 获取课题下的备课资源统计数据
      Map<String, Integer> countData = lessonPlanServie.getCountDataOfPlanForLesson(lessonId);
      // 获取课题下的教案资源集合
      List<LessonPlan> lessonPlanList = lessonPlanServie.getLessonPlanListForLesson(lessonId);
      // 获取课题下的课件资源集合
      List<LessonPlan> kejianList = lessonPlanServie.getKeJianListForLesson(lessonId);
      // 获取课题下的课后反思资源集合
      List<LessonPlan> fansiList = lessonPlanServie.getFanSiListForLesson(lessonId);
      // 获取课题扩展信息
      LessonInfo lessonInfo = myPlanBookService.getLessonInfoByLessonId(lessonId);
      m.addAttribute("countData1", countData);
      m.addAttribute("lessonPlanList", lessonPlanList);
      m.addAttribute("kejianList", kejianList);
      m.addAttribute("fansiList", fansiList);
      m.addAttribute("lessonInfo", lessonInfo);
    }
    return "/myplanbook/myplan_list";
  }

  /**
   * 删除备课资源
   * 
   * @param planId
   * @param m
   */
  @RequestMapping(value = "deleteLessonPlan")
  public void deleteLessonPlan(Integer planId, Model m) {
    try {
      myPlanBookService.deleteLessonPlanById(planId);
      m.addAttribute("result", "success");
    } catch (Exception e) {
      logger.error("删除备课资源失败，程序出错", e);
      m.addAttribute("result", "fail");
    }
  }

  /**
   * 分享备课资源
   * 
   * @param planId
   * @param m
   */
  @RequestMapping(value = "shareLessonPlan")
  public void shareLessonPlan(Integer planId, Model m) {
    try {
      myPlanBookService.sharePlanOfLessonById(planId);
      m.addAttribute("result", "success");
      // 修改浏览记录分享状态
      BrowseRecordUtils.updateBrowseRecordShare(BaseResTypes.BKZY, planId, true);
    } catch (Exception e) {
      logger.error("分享备课资源失败，程序出错", e);
      m.addAttribute("result", "fail");
    }
  }

  /**
   * 取消分享备课资源
   * 
   * @param planId
   * @param m
   */
  @RequestMapping(value = "unShareLessonPlan")
  public void unShareLessonPlan(Integer planId, Model m) {
    try {
      Boolean flag = myPlanBookService.unSharePlanOfLessonById(planId);
      if (flag) {
        m.addAttribute("result", 0);
        // 修改浏览记录分享状态
        BrowseRecordUtils.updateBrowseRecordShare(BaseResTypes.BKZY, planId, false);
      } else {
        m.addAttribute("result", 1);
      }
    } catch (Exception e) {
      logger.error("取消分享备课资源失败", e);
      m.addAttribute("result", 2);
    }
  }

  @RequestMapping(value = "toEditLessonPlan")
  public String toEditLessonPlan(Integer planId,Model m) {
    m.addAttribute("planId", planId);
    return "/myplanbook/lessonplan_view";
  }
  
  /**
   * 进入修改教案页面
   * 
   * @param planId
   * @param m
   * @return
   */
  @RequestMapping(value = "toEditLessonPlanView")
  public String toEditLessonPlanView(Integer planId, HttpServletRequest request, Model m, HttpServletResponse response) {

    // 获取当前用户空间
    UserSpace userSpace = (UserSpace) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE);
    // 学年
    Integer schoolYear = (Integer) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SCHOOLYEAR);
    PageOfficeCtrl poc = new PageOfficeCtrl(request);
    poc.setServerPage(request.getContextPath() + "/poserver.zz");
    // 设置word文件的保存的action方法
    poc.setSaveFilePage("editLessonPlan");
    // 设置文档加载后执行的js方法
    poc.setJsFunction_AfterDocumentOpened("AfterDocumentOpened");
    // 设置控件标题
    poc.setCaption("修改教案");
    LessonPlan lessonPlan = lessonPlanServie.findOne(planId);
    // 调用接口获取下载到本地的临时文件路径
    String localResPath = resourcesService.viewResources(lessonPlan.getResId());
    File f = new File(localResPath);
    if (f.isAbsolute() && localResPath.startsWith("/")) {
      localResPath = "file://" + localResPath;
    }
    poc.setOfficeVendor(OfficeVendorType.AutoSelect);
    poc.webOpen(localResPath, OpenModeType.docNormalEdit, "");
    poc.setTagId("PageOfficeCtrl1");// 此行必需
    LessonPlan temp = new LessonPlan();
    temp.setLessonId(lessonPlan.getLessonId());
    temp.setEnable(1);
    temp.setPlanType(LessonPlan.JIAO_AN);
    temp.setSchoolYear(schoolYear);
    temp.setUserId(userSpace.getUserId());
    // 获取已写过教案的课时id连成的字符串，如1,2,3
    String hoursStr = lessonPlanServie.getHoursStrOfWritedLessonById(temp);
    m.addAttribute("hoursStr", hoursStr);
    m.addAttribute("lessonPlan", lessonPlan);
    return "/myplanbook/lessonplan_edit";
  }

  /**
   * 修改教案
   * 
   * @param request
   * @param response
   */
  @RequestMapping(value = "editLessonPlan")
  public void editLessonPlan(HttpServletRequest request, HttpServletResponse response) {
    FileSaver fs = new FileSaver(request, response);
    Boolean flag = lessonPlanServie.editLessonPlan(fs);
    if (flag) {
      fs.setCustomSaveResult("success");
    }
    fs.close();
  }

  /**
   * 获取已提交或未提交的备课资源（含有层级关系）
   * 
   * @param bookId
   * @param isSubmit
   *          (0:未提交
   *          ，1:已提交)
   * @param m
   * @return
   */
  @RequestMapping(value = "getIsOrNotSubmitLessonPlan")
  public String getIsOrNotSubmitLessonPlan(String bookId, Boolean isSubmit, Model m) {
    List<BookLessonVo> treeList = myPlanBookService.getLessonTreeMyPlanBook(bookId);
    Map<String, Object> dataMap = myPlanBookService.getIsOrNotSubmitLessonPlanByBookId(bookId, isSubmit, null);
    m.addAttribute("treeList", treeList);
    m.addAttribute("dataMap", dataMap);
    m.addAttribute("isSubmit", isSubmit);
    m.addAttribute("bookId", bookId);
    return "/myplanbook/lessonplan_submit";
  }

  /**
   * 提交备课资源
   * 
   * @param lessonPlanIdsStr
   * @param m
   */
  @RequestMapping(value = "submitLessonPlans")
  public void submitLessonPlans(String lessonPlanIdsStr, Model m) {
    try {
      myPlanBookService.submitLessonPlansByIdStr(lessonPlanIdsStr);
      m.addAttribute("result", "success");
    } catch (Exception e) {
      logger.error("提交备课资源出错", e);
      m.addAttribute("result", "fail");
    }
  }

  /**
   * 取消提交备课资源
   * 
   * @param lessonPlanIdsStr
   * @param m
   */
  @RequestMapping(value = "unSubmitLessonPlans")
  public void unSubmitLessonPlans(String lessonPlanIdsStr, Model m) {
    try {
      myPlanBookService.unSubmitLessonPlansByIdStr(lessonPlanIdsStr);
      m.addAttribute("result", "success");
    } catch (Exception e) {
      logger.error("取消提交备课资源出错", e);
      m.addAttribute("result", "fail");
    }
  }

  /**
   * 根据教案类型将查阅意见的状态更新为已查看（查阅意见已更新置为0）
   * 
   * @param lessonInfo
   * @param m
   */
  @RequestMapping(value = "setScanListAlreadyShowByType")
  public void setScanListAlreadyShowByType(LessonInfo lessonInfo) {
    // lessonPlanServie.setScanListAlreadyShowByType(lessonPlan);
    lessonInfo.setScanUp(false);
    lessonInfoService.update(lessonInfo);
  }

  /**
   * 根据教案类型将查阅意见的状态更新为已查看_other（查阅意见已更新置为0-课件-反思）
   * 
   * @param lessonPlan
   * @param m
   */
  @RequestMapping(value = "setScanKJFASI")
  public void setScanKJFASI(LessonPlan lessonPlan) {
    lessonPlanServie.setScanListAlreadyShowByType(lessonPlan);
  }

  /**
   * 将评论意见的状态更新为已查看（评论意见已更新置为0）
   * 
   * @param lessonPlan
   * @param m
   */
  @RequestMapping(value = "setCommentListAlreadyShowByType")
  public void setCommentListAlreadyShowByType(LessonPlan lessonPlan) {
    lessonPlan.setCommentUp(false);
    lessonPlanServie.update(lessonPlan);
  }

  /**
   * 将听课意见更新为已查看
   * 
   * @param lessonInfo
   */
  @RequestMapping(value = "setVisitListAlreadyShowByType")
  public void setVisitListAlreadyShowByType(LessonInfo lessonInfo) {
    lessonInfo.setVisitUp(false);
    lessonInfoService.update(lessonInfo);
  }

  /**
   * 接收集体备课的整理教案
   * 
   * @param activityId
   * @param m
   */
  @RequestMapping(value = "receiveLessonPlanOfActivity")
  public void receiveLessonPlanOfActivity(Integer activityId, Model m) {
    try {
      myPlanBookService.receiveLessonPlanOfActivity(activityId, m);
    } catch (Exception e) {
      logger.error("接收集体备课的整理教案", e);
      m.addAttribute("result", "error");
      m.addAttribute("info", "系统出错！请重试");
    }
  }

  /**
   * 接收校际教研的整理教案
   * 
   * @param activityId
   * @param m
   */
  @RequestMapping(value = "receiveLessonPlanOfSchoolActivity")
  public void receiveLessonPlanOfSchoolActivity(Integer activityId, Model m) {
    try {
      myPlanBookService.receiveLessonPlanOfSchoolActivity(activityId, m);
    } catch (Exception e) {
      logger.error(" 接收校际教研的整理教案", e);
      m.addAttribute("result", "error");
      m.addAttribute("info", "系统出错！请重试");
    }
  }

  /**
   * 获取提交本课题资源除了当前人的其他人列表
   * 
   * @param lessonInfo
   * @param m
   */
  @RequestMapping(value = "lessonSubmitOthers")
  public String lessonSubmitOthers(HttpServletRequest request, LessonInfo lessonInfo, Model m) {
    List<LessonPlan> datalist = myPlanBookService.lessonSubmitOthers(lessonInfo);
    m.addAttribute("datalist", datalist);
    m.addAttribute("type", lessonInfo.getPhaseId());// 复用学段字段存放资源类型
    m.addAttribute("sessionId", request.getSession().getId());
    return "/myplanbook/lesson_submit_others";
  }

  /**
   * 查看提交本课题资源除了当前人的其他人列表
   * 
   * @param lessonInfo
   * @param m
   */
  @RequestMapping(value = "viewOtherLesson")
  public String viewOtherLesson(Integer planId, Model m) {
    if (planId != null) {
      LessonPlan lp = lessonPlanServie.findOne(planId);
      if (lp != null // &&lp.getPlanType()==LessonPlan.JIAO_AN
      ) {
        LessonPlan model = new LessonPlan();
        model.setUserId(lp.getUserId());
        model.setInfoId(lp.getInfoId());
        // model.setPlanType(LessonPlan.JIAO_AN);
        model.setPlanType(lp.getPlanType());
        List<LessonPlan> lpList = lessonPlanServie.findAll(model);
        m.addAttribute("lpList", lpList);
        m.addAttribute("lessonName", lessonInfoService.findOne(lp.getInfoId()).getLessonName());
      }
      m.addAttribute("lp", lp);
    }
    return "/myplanbook/view_other_lesson";
  }

  /**
   * 浏览教案课件反思（含评论）
   * 
   * @param planId
   * @param m
   * @return
   */
  @RequestMapping(value = "scanLessonPlan")
  public String scanLessonPlan(Integer planId, Model m) {

    LessonPlan plan = lessonPlanServie.findOne(planId);
    m.addAttribute("plan", plan);
    return "/myplanbook/scanLessonPlan";
  }

  /**
   * 上传页面
   */
  @RequestMapping("tolessonplanEditFile")
  public String emptyMethod() {
    return "/myplanbook/lessonplan_edit._file";
  }
}
