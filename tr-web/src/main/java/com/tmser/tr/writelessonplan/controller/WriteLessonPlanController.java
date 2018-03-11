/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.writelessonplan.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tmser.tr.common.page.PageList;
import com.tmser.tr.common.utils.WebThreadLocalUtils;
import com.tmser.tr.common.vo.Result;
import com.tmser.tr.common.web.controller.AbstractController;
import com.tmser.tr.lessonplan.bo.LessonPlan;
import com.tmser.tr.lessonplantemplate.bo.LessonPlanTemplate;
import com.tmser.tr.lessonplantemplate.service.LessonPlanTemplateService;
import com.tmser.tr.manage.meta.bo.Book;
import com.tmser.tr.manage.meta.service.BookChapterHerperService;
import com.tmser.tr.manage.meta.service.BookService;
import com.tmser.tr.manage.meta.vo.BookLessonVo;
import com.tmser.tr.manage.resources.bo.ResRecommend;
import com.tmser.tr.manage.resources.bo.Resources;
import com.tmser.tr.manage.resources.service.ResRecommendService;
import com.tmser.tr.manage.resources.service.ResourcesService;
import com.tmser.tr.uc.bo.User;
import com.tmser.tr.uc.bo.UserSpace;
import com.tmser.tr.uc.utils.SessionKey;
import com.tmser.tr.writelessonplan.service.LessonPlanService;
import com.zhuozhengsoft.pageoffice.DocumentVersion;
import com.zhuozhengsoft.pageoffice.FileSaver;
import com.zhuozhengsoft.pageoffice.OfficeVendorType;
import com.zhuozhengsoft.pageoffice.OpenModeType;
import com.zhuozhengsoft.pageoffice.PageOfficeCtrl;
import com.zhuozhengsoft.pageoffice.wordwriter.WordDocument;

/**
 * <pre>
 * 撰写教案
 * </pre>
 *
 * @author wangdawei
 * @version $Id: WriteLessonPlan.java, v 1.0 2015年2月2日 上午10:34:29 wangdawei Exp
 *          $
 */
@Controller
@RequestMapping("/jy")
public class WriteLessonPlanController extends AbstractController {

  @Autowired
  private LessonPlanTemplateService lptService; // 教案模板service
  @Autowired
  private BookChapterHerperService bchService; // 获取书——课题service
  @Autowired
  private LessonPlanService lpService; // 教案service
  @Autowired
  private BookService bookService;// 获取上下册书 service
  @Autowired
  private ResRecommendService resRecommendService;// 推送资源service
  @Autowired
  private ResourcesService resourcesService; // 资源service

  /**
   * 跳转到写教案页
   * 
   * @param m
   * @return
   */
  @RequestMapping("toWriteLessonPlan")
  public String toWriteLessonPlan(Integer spaceId,Model m) {

    @SuppressWarnings("unchecked")
	List<UserSpace> userSpaceList = (List<UserSpace>) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.USER_SPACE_LIST); // 用户空间
    
    LessonPlan lessonPlan = lpService.getLatestLessonPlan(0);
    if(lessonPlan == null){
    	lessonPlan = new LessonPlan();
    }
    
    String bookId = null;
    Integer gradeId = null;
    Integer subjectId = null;
    if(spaceId == null){
    	 // 获取上次最后操作的教案
        if(lessonPlan.getBookId() != null){
        	bookId= lessonPlan.getBookId();
        	gradeId = lessonPlan.getGradeId();
        	subjectId = lessonPlan.getSubjectId();
        }else{
        	for (UserSpace userSpace : userSpaceList) {
    			if(userSpace.getBookId() != null){
    				bookId = userSpace.getBookId();
    				gradeId = userSpace.getGradeId();
    				subjectId = userSpace.getSubjectId();
    				break;
    			}
    		}
        }
    }else{
    	for (UserSpace userSpace : userSpaceList) {
			if(userSpace.getId().equals(spaceId) && userSpace.getBookId() != null){
				bookId = userSpace.getBookId();
				gradeId = userSpace.getGradeId();
				subjectId = userSpace.getSubjectId();
				break;
			}
		}
    }
    
    lessonPlan.setSubjectId(subjectId);
    lessonPlan.setGradeId(gradeId);
    List<Book> bookList = new ArrayList<Book>();
    // 从缓存中获取上下册书
    Book book1 = bookService.findOne(bookId);
    Book book2 = bookService.findOne(book1.getRelationComId());
    bookList.add(book1);
    if (book2 != null) {
      bookList.add(book2);
    }
    // 获取推荐的电子教材

    m.addAttribute("bookList", bookList);
    m.addAttribute("lessonPlan", lessonPlan);
    m.addAttribute("currentBook", book1);
    return "/writelessonplan/writelessonplan";
  }

  /**
   * 保存教案
   * 
   * @param request
   * @param response
   */
  @RequestMapping("saveLessonPlan")
  public void saveLessonPlan(HttpServletRequest request, HttpServletResponse response) {
    FileSaver fs = new FileSaver(request, response);
    // 保存教案，返回教案id
    try {
      Integer planId = lpService.saveLessonPlan(fs);
      if (planId != null && planId != 0 && planId != -1) {
        fs.setCustomSaveResult("success");
      } else if (planId != null && planId == 0) {
        fs.setCustomSaveResult("fail2");
      } else if (planId != null && planId == -1) {
        fs.setCustomSaveResult("fail3");
      } else {
        fs.setCustomSaveResult("fail1");
      }
    } catch (Exception e) {
      logger.error("保存教案失败", e);
      fs.setCustomSaveResult("fail");
    }
    fs.close();
  }

  /**
   * 通过上传教案 保存教案
   * 
   * @param request
   * @param response
   */
  @RequestMapping("saveLessonPlanWithResId")
  @ResponseBody
  public Result saveLessonPlanWithResId(String resId, LessonPlan params) {
    Result result = Result.newInstance();
    try {
      Integer planId = lpService.saveLessonPlanWithFile(params, resId);
      if (planId != null && planId != 0 && planId != -1) {
        // 持久化文件
        resourcesService.updateTmptResources(resId);
        logger.info("保存教案成功,planId:{}", planId);
      } else {
        logger.info("保存教案失败");
        result.setMsg("保存教案失败");
      }
    } catch (Exception e) {
      logger.error("保存教案失败", e);
      result.setCode(500);
      result.setMsg("保存教案失败");
    }
    return result;
  }

  /**
   * 通过上传教案 更新教案
   * 
   * @param request
   * @param response
   */
  @RequestMapping("updateLessonPlanWithResId")
  @ResponseBody
  public Result updateLessonPlanWithResId(Integer planId, String resourceId) {
    return lpService.updateLessonPlanWithResId(resourceId, planId);
  }

  /**
   * 跳转到写新教案页面
   * 
   * @param tpId
   * @param request
   * @return
   */
  @RequestMapping("toEditWordPage")
  public String toEditWordPage(Integer tpId, HttpServletRequest request, Model m) {
    User user = (User) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_USER); // 用户
    Integer orgId = user.getOrgId();
    PageOfficeCtrl poc = new PageOfficeCtrl(request);
    poc.setServerPage(request.getContextPath() + "/poserver.zz");
    // 设置word文件的保存页面
    poc.setSaveFilePage("saveLessonPlan");
    // 设置文档加载后执行的js方法
    poc.setJsFunction_AfterDocumentOpened("AfterDocumentOpened");
    // 设置控件标题
    poc.setCaption("撰写教案");
    poc.setOfficeVendor(OfficeVendorType.AutoSelect);
    WordDocument doc = new WordDocument();
    poc.setWriter(doc);
    LessonPlanTemplate lessonPlanTemplate = null;
    // 获取教案模板（以id获取，id不存在则获取默认模板，没有默认模板则打开新的word）
    if (tpId != null) {
      // 根据id获取模板
      lessonPlanTemplate = lptService.getTemplateById(tpId);
    } else {
      // 获取默认模板
      lessonPlanTemplate = lptService.getDefaultTemplateByOrgId(orgId);
    }
    if (lessonPlanTemplate == null) {
      // 打开新文档
      poc.webCreateNew(user.getName(), DocumentVersion.Word2003);
    } else {
      Resources resources = resourcesService.findOne(lessonPlanTemplate.getResId());
      // 调用接口获取下载到本地的临时文件路径
      String localResPath = resourcesService.viewResources(resources.getId());
      File f = new File(localResPath);
      if (f.isAbsolute() && localResPath.startsWith("/")) {
        localResPath = "file://" + localResPath;
      }
      // 调用下载接口获下载后的取本地路径
      // String resPath =
      // storageservice.download(lessonPlanTemplate.getResPath());
      // String resPath = resources.getPath();
      // 打开指定的Word文档
      poc.webOpen(localResPath, OpenModeType.docNormalEdit, user.getName());
      m.addAttribute("template", lessonPlanTemplate);
    }
    poc.setTagId("PageOfficeCtrl1");// 此行必需
    return "/writelessonplan/wordtemplate_edit";
  }

  /**
   * 加载课题元数据集合
   * 
   * @param m
   * @return
   */
  @RequestMapping("getLessonList")
  public String getLessonList(String bookId, Model m) {
    List<BookLessonVo> lessonList = bchService.getBookChapterByComId(bookId);
    m.addAttribute("lessonList", lessonList);
    return "/writelessonplan/lesson_tree";
  }

  /**
   * 根据课题id获取该课题下已经写过的课时（课时id相连的字符串）
   * 
   * @param lessonId
   * @param m
   * @return
   */
  @RequestMapping("getHoursStrOfWritedLesson")
  public String getHoursStrOfWritedLesson(LessonPlan lessonPlan, Model m) {
    // 获取当前用户空间
    UserSpace userSpace = (UserSpace) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE);
    // 学年
    Integer schoolYear = (Integer) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SCHOOLYEAR);
    lessonPlan.setPlanType(LessonPlan.JIAO_AN);
    lessonPlan.setUserId(userSpace.getUserId());
    lessonPlan.setSchoolYear(schoolYear);
    lessonPlan.addOrder("hoursId");
    String hoursStr = lpService.getHoursStrOfWritedLessonById(lessonPlan);
    m.addAttribute("hoursStr", hoursStr);
    return "/writelessonplan/lessontree";
  }

  /**
   * 获取可用的教案模板
   */
  @RequestMapping("getLessonPlanTemplate")
  public void getLessonPlanTemplate(Model m) {
    UserSpace userSpace = (UserSpace) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE); // 用户空间
    Integer orgId = userSpace.getOrgId();
    // 获取模板集合(包含系统自带的)
    List<LessonPlanTemplate> lptList = lptService.getTemplateListByOrg(orgId);
    m.addAttribute("templateList", lptList);
  }

  /**
   * 获取同伴资源
   * 
   * @param lessonPlan
   * @return
   */
  @RequestMapping("getPeerResource")
  public String getPeerResource(LessonPlan lessonPlan, Model m) {
    if ("".equals(lessonPlan.getLessonId()) || lessonPlan.getPlanType() == null) {
      m.addAttribute("result", "fail");
      return "";
    }
    PageList<LessonPlan> lessonPlanList = lpService.getPeerResource(lessonPlan);
    m.addAttribute("lessonPlanPageList", lessonPlanList);
    m.addAttribute("result", "success");
    return "";
  }

  /**
   * 获取推送资源
   * 
   * @param recommend
   * @param m
   */
  @RequestMapping("getCommendResource")
  public void getCommendResource(ResRecommend resRecommend, Model m) {
    if (resRecommend.getResType() == null) {
      m.addAttribute("result", "fail");
      return;
    }
    resRecommend.setEnable(1);
    List<ResRecommend> recommendResList = resRecommendService.findAll(resRecommend);
    m.addAttribute("commendResList", recommendResList);
    m.addAttribute("result", "success");
  }

  /**
   * 获取所有的推送资源
   * 
   * @param resRecommend
   * @param m
   * @return
   */
  @RequestMapping("getAllCommendResource")
  public String getAllCommendResource(ResRecommend resRecommend, Model m) {
    resRecommend.setEnable(1);
    resRecommend.pageSize(8);
    resRecommend.addOrder(" qualify desc,resSecondType asc");
    PageList<ResRecommend> recommendResList = resRecommendService.findByPage(resRecommend);
    m.addAttribute("commendResList", recommendResList);
    m.addAttribute("resType", resRecommend.getResType());
    return "/writelessonplan/res_list";
  }

  /**
   * 浏览备课资源
   * 
   * @return
   */
  @RequestMapping("viewRes")
  public String viewResFile(Integer resId) {
    LessonPlan plan = lpService.findOne(resId);
    return "redirect:/jy/scanResFile?resId=" + plan.getResId();
  }

  /**
   * 空方法
   */
  @RequestMapping("toEmptyMethod")
  public void emptyMethod() {

  }
}
