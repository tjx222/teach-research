/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.courseware.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

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
import com.tmser.tr.courseware.service.CoursewareService;
import com.tmser.tr.lessonplan.bo.LessonPlan;
import com.tmser.tr.lessonplan.service.LessonPlanService;
import com.tmser.tr.lessonplan.service.MyPlanBookService;
import com.tmser.tr.manage.meta.bo.Book;
import com.tmser.tr.manage.meta.service.BookChapterHerperService;
import com.tmser.tr.manage.meta.service.BookService;
import com.tmser.tr.manage.meta.vo.BookLessonVo;
import com.tmser.tr.manage.resources.bo.Resources;
import com.tmser.tr.manage.resources.service.ResourcesService;
import com.tmser.tr.uc.utils.SessionKey;

/**
 * <pre>
 * 课件相关功能操作Controller
 * </pre>
 *
 * @author tmser
 * @version $Id: CoursewareController.java, v 1.0 2015年2月10日 下午2:43:31 tmser Exp
 *          $
 */
@Controller
@RequestMapping("jy/courseware")
public class CoursewareController extends AbstractController {

  private static final Logger logger = LoggerFactory.getLogger(CoursewareController.class);

  @Resource
  private CoursewareService coursewareService;
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

  /**
   * 进入课件管理首页
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
    PageList<LessonPlan> lpList = coursewareService.findCourseList(lp);
    m.addAttribute("coursewareList", lpList);
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
    return "/courseware/coursewareIndex";
  }

  /**
   * 移动端课件提交页面
   * 
   * @param lp
   * @param m
   * @param page
   * @return
   */
  @RequestMapping("/submitIndex_mobile")
  public String submitIndex_mobile(LessonPlan lp, Integer spaceId, Model m) {
    lp.pageSize(1000);
    PageList<LessonPlan> lpList = coursewareService.findCourseList(lp);
    m.addAttribute("coursewareList", lpList);
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
    return "/courseware/coursewareSubmit";
  }

  @RequestMapping("/dlog")
  public String index_d() {
    return "/courseware/dlog_course";
  }

  /**
   * 保存课件
   */
  @RequestMapping("/save")
  @UseToken
  public void saveCourseware(LessonPlan lp, Model m) {
    try {
      coursewareService.saveCourseware(lp);
      m.addAttribute("isOk", true);
    } catch (Exception e) {
      logger.error("课件保存失败...", e);
      e.printStackTrace();
      m.addAttribute("isOk", false);
    }
  }

  /**
   * 删除课件
   */
  @RequestMapping("/delete")
  public void delete(LessonPlan lp, Model m) {
    Boolean isOk = true;
    try {
      isOk = coursewareService.deleteCourseware(lp);
    } catch (Exception e) {
      isOk = false;
      logger.error("---课件删除出错---", e);
    }
    m.addAttribute("isOk", isOk);
  }

  /**
   * 分享课件
   */
  @RequestMapping("/sharing")
  public void sharing(LessonPlan lp, Model m) {
    Integer isOk = 0;
    try {
      Boolean state = coursewareService.sharingCourseware(lp);
      if (!state)
        isOk = 1;
    } catch (Exception e) {
      isOk = 2;
      logger.error("---课件分享的相关操作失败---", e);
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
   * 提交课件之前的数据查询展示
   */
  @RequestMapping("/preSubmit")
  public String preSubmit(@RequestParam(value = "isSubmit") Integer isSubmit, Model m) {
    Map<String, Object> submitDatas = coursewareService.getSubmitData(isSubmit);
    m.addAttribute("bookName", submitDatas.get("name"));
    m.addAttribute("bookName2", submitDatas.get("name2"));
    m.addAttribute("treeList", submitDatas.get("treeList"));
    m.addAttribute("dataMap", submitDatas.get("list"));
    m.addAttribute("treeList2", submitDatas.get("treeList2"));
    m.addAttribute("dataMap2", submitDatas.get("list2"));

    m.addAttribute("isSubmit", isSubmit);
    return "/courseware/coursewareSubmit";
  }

  /**
   * 提交或者取消提交课件
   */
  @RequestMapping("/submitCourseware")
  public void submitCourseware(@RequestParam(value = "isSubmit") String isSubmit,
      @RequestParam(value = "planIds") String planIds, Model m) {
    Boolean isOk = true;
    try {
      isOk = coursewareService.submitCourseware(isSubmit, planIds);
    } catch (Exception e) {
      isOk = false;
      logger.error("---课件提交相关操作出错---", e);
    }
    m.addAttribute("isOk", isOk);
  }

  /**
   * 提交或者取消提交单条课件 为移动端操作而加入了此接口 by daweiwbs
   */
  @RequestMapping("/submitCoursewareByOne")
  public void submitCoursewareByOne(@RequestParam(value = "isSubmit") String isSubmit,
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
      logger.error("---提交课件操作出错---", e);
    }
    m.addAttribute("isOk", isOk);
  }
}
