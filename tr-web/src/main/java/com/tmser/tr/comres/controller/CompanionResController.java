/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.comres.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tmser.tr.browse.BaseResTypes;
import com.tmser.tr.browse.utils.BrowseRecordUtils;
import com.tmser.tr.common.orm.SqlMapping;
import com.tmser.tr.common.web.controller.AbstractController;
import com.tmser.tr.lessonplan.bo.LessonInfo;
import com.tmser.tr.lessonplan.bo.LessonPlan;
import com.tmser.tr.lessonplan.service.LessonInfoService;
import com.tmser.tr.lessonplan.service.LessonPlanService;
import com.tmser.tr.manage.meta.MetaUtils;
import com.tmser.tr.manage.meta.bo.Book;
import com.tmser.tr.manage.meta.bo.BookSync;
import com.tmser.tr.manage.meta.bo.MetaRelationship;
import com.tmser.tr.manage.meta.service.BookService;
import com.tmser.tr.manage.meta.service.BookSyncService;
import com.tmser.tr.manage.org.bo.Organization;
import com.tmser.tr.manage.org.service.OrganizationService;
import com.tmser.tr.manage.resources.bo.Resources;
import com.tmser.tr.manage.resources.service.ResViewService;
import com.tmser.tr.manage.resources.service.ResourcesService;
import com.tmser.tr.uc.bo.User;
import com.tmser.tr.uc.utils.CurrentUserContext;

/**
 * <pre>
 *
 * </pre>
 *
 * @author tmser
 * @version $Id: CompanionRes.java, v 1.0 2015年3月12日 下午2:17:25 tmser Exp $
 */
@Controller
@RequestMapping("/jy/comres")
public class CompanionResController extends AbstractController {
  @Autowired
  private LessonPlanService lessonPlanServie;// 备课资源service

  @Autowired
  private LessonInfoService lessonInfoServie;

  @Autowired
  private OrganizationService organizationServie;

  @Autowired
  private BookService bookServie;

  @Autowired
  private BookSyncService bookSyncService;
  @Autowired
  private ResViewService resViewService;
  @Autowired
  private ResourcesService resService;

  private List<MetaRelationship> getCurrentPhaseList() {
    User user = CurrentUserContext.getCurrentUser(); // 用户
    Organization org = organizationServie.findOne(user.getOrgId());
    return MetaUtils.getOrgTypeMetaProvider().listAllPhase(org.getSchoolings());
  }

  /**
   * 分类资源首页，默认显示教案
   * 
   * @param lessPlan
   * @param m
   * @return
   */
  @RequestMapping("/index_type")
  public String indexType(LessonPlan lessPlan, Model m) {
    LessonPlan model = new LessonPlan();
    if (lessPlan != null) {
      model.currentPage(lessPlan.getPage().getCurrentPage());
      if (lessPlan.getPlanType() != null) {
        model.setPlanType(lessPlan.getPlanType());
      } else {
        model.setPlanType(LessonPlan.JIAO_AN);
      }

      if (!StringUtils.isEmpty(lessPlan.getBookId())) {
        model.setBookId(lessPlan.getBookId());
      }
      if (!StringUtils.isEmpty(lessPlan.getBookShortname())) {
        model.setBookShortname(lessPlan.getBookShortname());
      }
      if (!StringUtils.isEmpty(lessPlan.getPlanName())) {
        model.setPlanName(SqlMapping.LIKE_PRFIX + lessPlan.getPlanName() + SqlMapping.LIKE_PRFIX);
      }
    } else {
      model.setPlanType(LessonPlan.JIAO_AN);
    }

    List<MetaRelationship> phases = getCurrentPhaseList();
    m.addAttribute("phases", phases);

    if (lessPlan == null || lessPlan.getPhaseId() == null) {
      model.setPhaseId(phases.get(0).getEid());
      m.addAttribute("phaseId", phases.get(0).getId());
    } else {
      model.setPhaseId(MetaUtils.getMetaRelation(lessPlan.getPhaseId()).getEid());
      m.addAttribute("phaseId", lessPlan.getPhaseId());
    }

    model.setIsShare(true);
    model.pageSize(15);

    // model.setSchoolYear(schoolYear);
    model.setSubjectId(lessPlan.getSubjectId());
    model.setGradeId(lessPlan.getGradeId());

    // String bookid = model.getBookId();
    // if (StringUtils.isEmpty(bookid)) {
    // bookid = lessPlan.getBookId();
    // }
    // if (StringUtils.isEmpty(model.getBookShortname())) {
    // model.setBookShortname(bookServie.findOne(bookid).getFormatName());
    // }

    // 清空booid 防止生成查询
    model.setBookId(null);
    Map<String, Object> params = new HashMap<String, Object>(1);
    // params.put("self", us.getUserId());
    // params.put("bookids", listBookids(bookid));
    StringBuilder sql = new StringBuilder();
    // sql.append(" and bookId in (:bookids) "); // and userId != :self 2017-3-2
    if (model.getPlanType() == LessonPlan.KE_HOU_FAN_SI) {
      sql.append(" and planType in(:ptypes)");
      List<Integer> types = new ArrayList<Integer>();
      types.add(LessonPlan.KE_HOU_FAN_SI);
      types.add(LessonPlan.QI_TA_FAN_SI);
      params.put("ptypes", types);
      model.setPlanType(null);
    }

    sql.append(" and orgId ").append(buildOrgSql(params));
    model.addCustomCondition(sql.toString(), params);
    model.addOrder("shareTime desc");

    m.addAttribute("data", lessonPlanServie.findByPage(model));
    if (!StringUtils.isEmpty(model.getPlanName())) {
      model.setPlanName(lessPlan.getPlanName());
    }
    if (model.getPlanType() == null) {
      model.setPlanType(LessonPlan.KE_HOU_FAN_SI);
    }

    // 还原bookid
    // model.setBookId(bookid);
    m.addAttribute("model", model);

    return viewName("index_type");
  }

  public String buildOrgSql(Map<String, Object> params) {
    StringBuilder sql = new StringBuilder();
    Organization org = organizationServie.findOne(CurrentUserContext.getCurrentUser().getOrgId());
    sql.append(" in (select o.id from Organization o where ")
        .append(" o.type = :otype and o.orgType = :orgType and o.enable = :enable )");
    params.put("orgType", org.getOrgType());
    params.put("otype", 0);
    params.put("enable", 1);
    // params.put("phaseTypes", "%," + us.getPhaseId() + ",%");
    return sql.toString();
  }

  /**
   * 分类资源首页，默认显示教案
   * 
   * @param lessPlan
   * @param m
   * @return
   */
  @RequestMapping("/index")
  public String index(LessonPlan lessPlan, Model m) {
    LessonInfo model = new LessonInfo();
    if (lessPlan != null) {
      if (!StringUtils.isEmpty(lessPlan.getBookId())) {
        model.setBookId(lessPlan.getBookId());
      }
      if (!StringUtils.isEmpty(lessPlan.getBookShortname())) {
        model.setBookShortname(lessPlan.getBookShortname());
      }
      if (!StringUtils.isEmpty(lessPlan.getPlanName())) {
        model.setLessonName(SqlMapping.LIKE_PRFIX + lessPlan.getPlanName() + SqlMapping.LIKE_PRFIX);
      }
      model.currentPage(lessPlan.getPage().getCurrentPage());
    }

    List<MetaRelationship> phases = getCurrentPhaseList();
    m.addAttribute("phases", phases);
    if (lessPlan == null || lessPlan.getPhaseId() == null) {
      model.setPhaseId(phases.get(0).getEid());
      m.addAttribute("phaseId", phases.get(0).getId());
    } else {
      model.setPhaseId(MetaUtils.getMetaRelation(lessPlan.getPhaseId()).getEid());
      m.addAttribute("phaseId", lessPlan.getPhaseId());
    }

    // model.setSchoolYear(schoolYear);
    model.pageSize(15);
    model.setSubjectId(lessPlan.getSubjectId());
    model.setGradeId(lessPlan.getGradeId());
    /*
     * String bookid = model.getBookId();
     * if (StringUtils.isEmpty(bookid)) {
     * bookid = lessPlan.getBookId();
     * }
     * if (StringUtils.isEmpty(model.getBookShortname())) {
     * model.setBookShortname(bookServie.findOne(bookid).getFormatName());
     * }
     */
    // 清空booid 防止生成查询
    model.setBookId(null);

    Map<String, Object> params = new HashMap<String, Object>(1);
    // params.put("self", us.getUserId());
    params.put("cnt", 0);
    // params.put("bookids", listBookids(bookid));
    model.addCustomCondition(
        " and ( jiaoanShareCount + fansiShareCount + kejianShareCount > :cnt ) " + " and orgId " + buildOrgSql(params),
        params); // and userId != :self 2017-3-2
    model.addOrder("shareTime desc");

    m.addAttribute("data", lessonInfoServie.findByPage(model));
    model.setLessonName(lessPlan != null ? lessPlan.getPlanName() : "");
    // 还原bookid
    // model.setBookId(bookid);
    m.addAttribute("model", model);
    return viewName("index");
  }

  /**
   * 查看课题
   * 
   * @param lessPlan
   * @param m
   * @return
   */
  @RequestMapping("/viewlesson")
  public String viewLesson(@RequestParam(value = "lessPlan", required = false) Integer lesInfoId, Model m) {

    if (lesInfoId != null) {
      LessonInfo lessonInfo = lessonInfoServie.findOne(lesInfoId);
      LessonPlan model = new LessonPlan();
      model.setBookId(lessonInfo.getBookId());
      model.setLessonId(lessonInfo.getLessonId());
      model.setIsShare(true);
      model.setUserId(lessonInfo.getUserId());
      // model.setSchoolYear(schoolYear);
      model.addOrder("planType asc,orderValue asc");
      m.addAttribute("lessonList", lessonPlanServie.findAll(model));
      m.addAttribute("data", lessonInfo);
    }
    return viewName("view_lesson");
  }

  /**
   * 查看文章
   * 
   * @param lesid
   * @param m
   * @return
   */
  @RequestMapping("/view")
  public String view(@RequestParam(value = "lesid") Integer lesid, Model m) {

    if (lesid != null) {
      LessonPlan lesson = lessonPlanServie.findOne(lesid);
      m.addAttribute("data", lesson);
      Resources res = resService.findOne(lesson.getResId());
      resViewService.choseView(res);
      if (lesson != null) {
        // 添加浏览记录
        BrowseRecordUtils.addBrowseRecord(BaseResTypes.BKZY, lesson.getPlanId());
      }
    }
    return viewName("view");
  }

  /**
   * 根据书查找上下册
   * 
   * @param bookid
   * @return
   */
  private List<String> listBookids(String bookid) {
    List<String> lessonInfoList = new ArrayList<String>();
    Book book = bookServie.findOne(bookid);
    BookSync model = new BookSync();
    if (book != null && book.getFormatName() != null) {
      model.setFormatName(book.getFormatName());
      model.setSubjectId(book.getSubjectId());
      model.setGradeLevelId(book.getGradeLevelId());
      model.setPublisherId(book.getPublisherId());
      List<BookSync> synbooks = bookSyncService.findAll(model);
      for (BookSync synbook : synbooks) {
        if (!bookid.equals(synbook.getComId()))
          lessonInfoList.add(synbook.getComId());
      }
    }

    lessonInfoList.add(bookid);
    return lessonInfoList;
  }

  /**
   * 添加资源浏览记录
   * 
   * @param type
   * @param resId
   */
  @RequestMapping("/addBrowsingRecord")
  public void addBrowsingRecord(@RequestParam(value = "type") Integer type,
      @RequestParam(value = "resId") Integer resId) {
    // 添加浏览记录
    BrowseRecordUtils.addBrowseRecord(type, resId);
  }
}
