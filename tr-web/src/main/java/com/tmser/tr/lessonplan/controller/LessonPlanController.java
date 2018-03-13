/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.lessonplan.controller;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tmser.tr.common.annotation.UseToken;
import com.tmser.tr.common.page.PageList;
import com.tmser.tr.common.utils.MobileUtils;
import com.tmser.tr.common.utils.WebThreadLocalUtils;
import com.tmser.tr.common.web.controller.AbstractController;
import com.tmser.tr.lessonplan.bo.LessonInfo;
import com.tmser.tr.lessonplan.bo.LessonPlan;
import com.tmser.tr.lessonplan.service.LessonInfoService;
import com.tmser.tr.lessonplan.service.LessonPlanService;
import com.tmser.tr.manage.meta.bo.Book;
import com.tmser.tr.manage.meta.service.BookChapterHerperService;
import com.tmser.tr.manage.meta.service.BookService;
import com.tmser.tr.manage.meta.vo.BookLessonVo;
import com.tmser.tr.manage.resources.bo.Resources;
import com.tmser.tr.manage.resources.service.ResourcesService;
import com.tmser.tr.myplanbook.service.MyPlanBookService;
import com.tmser.tr.uc.bo.UserSpace;
import com.tmser.tr.uc.utils.SessionKey;
import com.zhuozhengsoft.pageoffice.FileSaver;
import com.zhuozhengsoft.pageoffice.OfficeVendorType;
import com.zhuozhengsoft.pageoffice.OpenModeType;
import com.zhuozhengsoft.pageoffice.PageOfficeCtrl;

/**
 * <pre>
 * 教案相关功能操作Controller
 * </pre>
 *
 * @author tmser
 * @version $Id: LessonPlanController.java, v 1.0 2015年2月10日 下午2:43:31 tmser Exp
 *          $
 */
@Controller
@RequestMapping("jy/lessonplan")
public class LessonPlanController extends AbstractController {

  private static final Logger logger = LoggerFactory.getLogger(LessonPlanController.class);

  @Resource
  private BookChapterHerperService bookChapterHerperService;
  @Resource
  private ResourcesService resourcesService;
  @Autowired
  private MyPlanBookService myPlanBookService;
  @Autowired
  private BookService bookService;
  @Autowired
  private LessonPlanService lessonPlanService;

  @Autowired
  private LessonInfoService lessonInfoService;

  /**
   * 进入教案管理首页
   * 
   * @return
   */
  @RequestMapping("/index")
  @UseToken
  public String index(LessonPlan lp, Integer spaceId, Model m) {
    String bookId = lessonPlanService.filterCurrentBook(lp, spaceId);
    m.addAttribute("currentBookId", bookId);
    if (MobileUtils.isNormal()) {
      lp.pageSize(8);
    } else {
      lp.pageSize(1000);
    }
    lp.setPlanId(null);
    PageList<LessonPlan> lpList = lessonPlanService.findValidPlanList(lp);
    m.addAttribute("lessonplanList", lpList);
    if (StringUtils.isNotEmpty(bookId)) {
      Integer term = (Integer) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_TERM);
      Book book = bookService.findOne(bookId);
      if (book.getFasciculeId() != 178) {
        String bookId2 = "";
        if (term == 0) {// 上学期
          if (book.getFasciculeId() == 176) {
            bookId2 = book.getRelationComId();
          } else {
            bookId2 = bookId;
            bookId = book.getRelationComId();
          }
          m.addAttribute("fasiciculeName", "上册书籍目录");
          m.addAttribute("fasiciculeName2", "下册书籍目录");
        } else {// 下学期
          if (book.getFasciculeId() == 177) {
            bookId2 = book.getRelationComId();
          } else {
            bookId2 = bookId;
            bookId = book.getRelationComId();
          }
          m.addAttribute("fasiciculeName2", "上册书籍目录");
          m.addAttribute("fasiciculeName", "下册书籍目录");
        }
        List<BookLessonVo> bookChapters = bookChapterHerperService.getBookChapterTreeByComId(bookId);
        m.addAttribute("bookChapters", bookChapters);
        List<BookLessonVo> bookChapters2 = bookChapterHerperService.getBookChapterTreeByComId(bookId2);
        m.addAttribute("bookChapters2", bookChapters2);
      } else {
        List<BookLessonVo> bookChapters = bookChapterHerperService.getBookChapterTreeByComId(bookId);
        m.addAttribute("bookChapters", bookChapters);
        m.addAttribute("fasiciculeName", "全一册书籍目录");
      }
    }
    m.addAttribute("lessonPlan", lp);
    return "/lessonplan/lessonplanIndex";
  }

  @RequestMapping(value = "toEditLessonPlan")
  public String toEditLessonPlan(Integer planId, Model m) {
    m.addAttribute("planId", planId);
    return "/lessonplan/lessonplan_view";
  }

  /**
   * 上传页面
   */
  @RequestMapping("tolessonplanEditFile")
  public String emptyMethod() {
    return "/lessonplan/lessonplan_edit._file";
  }

  /**
   * 进入修改教案页面
   * 
   * @param planId
   * @param m
   * @return
   */
  @RequestMapping(value = "toEditLessonPlanView")
  public String toEditLessonPlanView(Integer planId, HttpServletRequest request, Model m,
      HttpServletResponse response) {

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
    LessonPlan lessonPlan = lessonPlanService.findOne(planId);
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
    String hoursStr = lessonPlanService.getHoursStrOfWritedLessonById(temp);
    m.addAttribute("hoursStr", hoursStr);
    m.addAttribute("lessonPlan", lessonPlan);
    return "/lessonplan/lessonplan_edit";
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
    Boolean flag = lessonPlanService.editLessonPlan(fs);
    if (flag) {
      fs.setCustomSaveResult("success");
    }
    fs.close();
  }

  /**
   * 移动端教案提交页面
   * 
   * @param lp
   * @param m
   * @param page
   * @return
   */
  @RequestMapping("/submitIndex_mobile")
  public String submitIndex_mobile(LessonPlan lp, Integer spaceId, Model m) {
    lp.pageSize(1000);
    PageList<LessonPlan> lpList = lessonPlanService.findValidPlanList(lp);
    m.addAttribute("lessonplanList", lpList);
    String bookId = lessonPlanService.filterCurrentBook(lp, spaceId);
    if (StringUtils.isNotEmpty(bookId)) {
      Integer term = (Integer) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_TERM);
      Book book = bookService.findOne(bookId);
      if (book.getFasciculeId() != 178) {
        String bookId2 = "";
        if (term == 0) {// 上学期
          if (book.getFasciculeId() == 176) {
            bookId2 = book.getRelationComId();
          } else {
            bookId2 = bookId;
            bookId = book.getRelationComId();
          }
          m.addAttribute("fasiciculeName", "上册书籍目录");
          m.addAttribute("fasiciculeName2", "下册书籍目录");
        } else {// 下学期
          if (book.getFasciculeId() == 177) {
            bookId2 = book.getRelationComId();
          } else {
            bookId2 = bookId;
            bookId = book.getRelationComId();
          }
          m.addAttribute("fasiciculeName2", "上册书籍目录");
          m.addAttribute("fasiciculeName", "下册书籍目录");
        }
        List<BookLessonVo> bookChapters = bookChapterHerperService.getBookChapterTreeByComId(bookId);
        m.addAttribute("bookChapters", bookChapters);
        List<BookLessonVo> bookChapters2 = bookChapterHerperService.getBookChapterTreeByComId(bookId2);
        m.addAttribute("bookChapters2", bookChapters2);
      } else {
        List<BookLessonVo> bookChapters = bookChapterHerperService.getBookChapterTreeByComId(bookId);
        m.addAttribute("bookChapters", bookChapters);
        m.addAttribute("fasiciculeName", "全一册书籍目录");
      }
    }
    m.addAttribute("bookId", bookId);
    m.addAttribute("lessonId", lp.getLessonId());
    return "/lessonplan/lessonplanSubmit";
  }

  @RequestMapping("/dlog")
  public String index_d() {
    return "/lessonplan/dlog_course";
  }

  /**
   * 删除教案
   */
  @RequestMapping("/delete")
  public void delete(LessonPlan lp, Model m) {
    Boolean isOk = true;
    try {
      isOk = lessonPlanService.deleteLessonPlan(lp);
    } catch (Exception e) {
      isOk = false;
      logger.error("---教案删除出错---", e);
    }
    m.addAttribute("isOk", isOk);
  }

  /**
   * 分享教案
   */
  @RequestMapping("/sharing")
  public void sharing(LessonPlan lp, Model m) {
    Integer isOk = 0;
    try {
      Boolean state = lessonPlanService.sharingLessonPlan(lp);
      if (!state)
        isOk = 1;
    } catch (Exception e) {
      isOk = 2;
      logger.error("---教案分享的相关操作失败---", e);
    }
    m.addAttribute("isOk", isOk);
    m.addAttribute("isShare", lp.getIsShare());
  }

  /**
   * 通过文件Id得到文件对象
   */
  @RequestMapping("/getFileById")
  public void getFileById(String resId, Model m) {
    Resources res = resourcesService.findOne(resId);
    m.addAttribute("res", res);
  }

  /**
   * 通过书籍Id获得书籍章节目录的树形结构
   */
  @ResponseBody
  @RequestMapping("/charpterTree")
  public List<BookLessonVo> charpterTree(@RequestParam(value = "lessonId") String bookId) {
    List<BookLessonVo> lessonList = null;
    if (bookId != null) {
      lessonList = bookChapterHerperService.getBookChapterByComId(bookId);
    }
    return lessonList;
  }

  /**
   * 提交教案之前的数据查询展示
   */
  @RequestMapping("/preSubmit")
  public String preSubmit(@RequestParam(value = "isSubmit") Integer isSubmit, Model m) {
    Map<String, Object> submitDatas = lessonPlanService.getSubmitData(isSubmit);
    m.addAttribute("bookName", submitDatas.get("name"));
    m.addAttribute("bookName2", submitDatas.get("name2"));
    m.addAttribute("treeList", submitDatas.get("treeList"));
    m.addAttribute("dataMap", submitDatas.get("list"));
    m.addAttribute("treeList2", submitDatas.get("treeList2"));
    m.addAttribute("dataMap2", submitDatas.get("list2"));

    m.addAttribute("isSubmit", isSubmit);
    return "/lessonplan/lessonplanSubmit";
  }

  /**
   * 提交或者取消提交教案
   */
  @RequestMapping("/submitLessonPlan")
  public void submitLessonPlan(@RequestParam(value = "isSubmit") String isSubmit,
      @RequestParam(value = "planIds") String planIds, Model m) {
    Boolean isOk = true;
    try {
      isOk = lessonPlanService.submitLessonPlan(isSubmit, planIds);
    } catch (Exception e) {
      isOk = false;
      logger.error("---教案提交相关操作出错---", e);
    }
    m.addAttribute("isOk", isOk);
  }

  /**
   * 提交或者取消提交单条教案 为移动端操作而加入了此接口 by daweiwbs
   */
  @RequestMapping("/submitLessonPlanByOne")
  public void submitLessonPlanByOne(@RequestParam(value = "isSubmit") String isSubmit,
      @RequestParam(value = "planId") Integer planId, Model m) {
    Integer isOk = 0;
    try {
      if ("0".equals(isSubmit)) { // 提交
        myPlanBookService.submitLessonPlansByIdStr(String.valueOf(planId));
      } else if ("1".equals(isSubmit)) { // 取消提交
        isOk = myPlanBookService.unSubmitLessonPlansById(planId);
      }
    } catch (Exception e) {
      isOk = 2;
      logger.error("---提交教案操作出错---", e);
    }
    m.addAttribute("isOk", isOk);
  }

  /**
   * 根据教案类型将查阅意见的状态更新为已查看_other（查阅意见已更新置为0-课件-反思）
   * 
   * @param lessonPlan
   * @param m
   */
  @RequestMapping(value = "setScanKJFASI")
  public void setScanKJFASI(LessonPlan lessonPlan) {
    lessonPlanService.setScanListAlreadyShowByType(lessonPlan);
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
    lessonPlanService.update(lessonPlan);
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
    return "/lessonplan/lesson_submit_others";
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
      LessonPlan lp = lessonPlanService.findOne(planId);
      if (lp != null // &&lp.getPlanType()==LessonPlan.JIAO_AN
      ) {
        LessonPlan model = new LessonPlan();
        model.setUserId(lp.getUserId());
        model.setInfoId(lp.getInfoId());
        // model.setPlanType(LessonPlan.JIAO_AN);
        model.setPlanType(lp.getPlanType());
        List<LessonPlan> lpList = lessonPlanService.findAll(model);
        m.addAttribute("lpList", lpList);
        m.addAttribute("lessonName", lessonInfoService.findOne(lp.getInfoId()).getLessonName());
      }
      m.addAttribute("lp", lp);
    }
    return "/lessonplan/view_other_lesson";
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

    LessonPlan plan = lessonPlanService.findOne(planId);
    m.addAttribute("plan", plan);
    return "/lessonplan/scanLessonPlan";
  }

}
