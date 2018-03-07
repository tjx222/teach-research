package com.tmser.tr.teachingView.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;

import com.tmser.tr.activity.bo.Activity;
import com.tmser.tr.activity.bo.ActivityTracks;
import com.tmser.tr.activity.bo.SchoolActivity;
import com.tmser.tr.activity.bo.SchoolActivityTracks;
import com.tmser.tr.activity.bo.SchoolTeachCircleOrg;
import com.tmser.tr.activity.service.ActivityService;
import com.tmser.tr.activity.service.ActivityTracksService;
import com.tmser.tr.activity.service.SchoolActivityService;
import com.tmser.tr.activity.service.SchoolActivityTracksService;
import com.tmser.tr.activity.service.SchoolTeachCircleOrgService;
import com.tmser.tr.check.bo.CheckInfo;
import com.tmser.tr.check.service.CheckInfoService;
import com.tmser.tr.comment.bo.CommentInfo;
import com.tmser.tr.comment.bo.Discuss;
import com.tmser.tr.comment.service.CommentInfoService;
import com.tmser.tr.comment.service.DiscussService;
import com.tmser.tr.common.ResTypeConstants;
import com.tmser.tr.common.bo.QueryObject.JOINTYPE;
import com.tmser.tr.common.page.Page;
import com.tmser.tr.common.page.PageList;
import com.tmser.tr.common.utils.WebThreadLocalUtils;
import com.tmser.tr.companion.bo.JyCompanionMessage;
import com.tmser.tr.companion.service.JyCompanionMessageService;
import com.tmser.tr.lecturerecords.bo.LectureRecords;
import com.tmser.tr.lecturerecords.service.LectureRecordsService;
import com.tmser.tr.lessonplan.bo.LessonInfo;
import com.tmser.tr.lessonplan.bo.LessonPlan;
import com.tmser.tr.lessonplan.service.LessonInfoService;
import com.tmser.tr.manage.meta.Meta;
import com.tmser.tr.manage.meta.MetaUtils;
import com.tmser.tr.manage.meta.bo.Book;
import com.tmser.tr.manage.meta.service.BookService;
import com.tmser.tr.manage.org.bo.Organization;
import com.tmser.tr.manage.org.service.OrganizationService;
import com.tmser.tr.manage.resources.bo.Resources;
import com.tmser.tr.manage.resources.service.ResourcesService;
import com.tmser.tr.plainsummary.bo.PlainSummary;
import com.tmser.tr.plainsummary.service.PlainSummaryService;
import com.tmser.tr.recordbag.bo.Record;
import com.tmser.tr.recordbag.bo.Recordbag;
import com.tmser.tr.recordbag.service.RecordService;
import com.tmser.tr.recordbag.service.RecordbagService;
import com.tmser.tr.teachingView.service.TeachingViewService;
import com.tmser.tr.teachingview.ManagerDetailView;
import com.tmser.tr.teachingview.ManagerView;
import com.tmser.tr.teachingview.TeacherDetailView;
import com.tmser.tr.teachingview.TeacherView;
import com.tmser.tr.teachingview.dao.TeachingViewDao;
import com.tmser.tr.teachingview.vo.SearchVo;
import com.tmser.tr.thesis.bo.Thesis;
import com.tmser.tr.thesis.service.ThesisService;
import com.tmser.tr.uc.SysRole;
import com.tmser.tr.uc.bo.User;
import com.tmser.tr.uc.bo.UserSpace;
import com.tmser.tr.uc.service.SchoolYearService;
import com.tmser.tr.uc.service.UserService;
import com.tmser.tr.uc.service.UserSpaceService;
import com.tmser.tr.uc.utils.SessionKey;
import com.tmser.tr.utils.DateUtils;
import com.tmser.tr.utils.JyCollectionUtils;
import com.tmser.tr.utils.StringUtils;
import com.tmser.tr.writelessonplan.service.LessonPlanService;

/**
 * 
 * <pre>
 * 教研一览接口实现类
 * </pre>
 *
 * @author wangdawei
 * @version $Id: TeachingViewServiceImpl.java, v 1.0 2016年4月8日 上午10:57:07
 *          wangdawei Exp $
 */
@Service
@Transactional
public class TeachingViewServiceImpl implements TeachingViewService {

  @Resource(name = "cacheManger")
  private CacheManager cacheManager;

  private Cache teachingViewDataCache; // 数据缓存

  @Autowired
  private OrganizationService orgService;
  @Autowired
  private UserSpaceService userSpaceService;
  @Autowired
  private TeachingViewDao teachingViewDao;
  @Autowired
  private ActivityService activityService;
  @Autowired
  private CheckInfoService checkInfoService;
  @Autowired
  private LessonInfoService lessonInfoService;
  @Autowired
  private PlainSummaryService plainSummaryService;
  @Autowired
  private LectureRecordsService lectureRecordsService;
  @Autowired
  private ThesisService thesisService;
  @Autowired
  private SchoolTeachCircleOrgService schoolTeachCircleOrgService;
  @Autowired
  private SchoolActivityService schoolActivityService;
  @Autowired
  private JyCompanionMessageService jyCompanionMessageService;
  @Autowired
  private SchoolYearService schoolYearService;
  @Autowired
  private SchoolActivityTracksService schoolActivityTracksService;
  @Autowired
  private ActivityTracksService activityTracksService;
  @Autowired
  private DiscussService discussService;
  @Autowired
  private LessonPlanService lessonPlanService;
  @Autowired
  private PlainSummaryService planSummaryService;
  @Autowired
  private JyCompanionMessageService companionMessageService;
  @Autowired
  private CommentInfoService commentInfoService;
  @Autowired
  private RecordbagService recordBagService;
  @Autowired
  private RecordService recordService;
  @Autowired
  private BookService bookService;
  @Autowired
  private UserService userService;
  @Autowired
  private LectureRecordsService lectureRecordService;
  @Autowired
  private ResourcesService resService;

  @PostConstruct
  public void init() {
    teachingViewDataCache = cacheManager.getCache("teachingViewDataCache");
  }

  // private String createKey(Object... cons){
  //
  // Integer orgId = userSpace.getOrgId();
  // String dateStr = new SimpleDateFormat("yyyyMMdd").format(new Date());
  // StringBuilder keyStr = new
  // StringBuilder().append(String.valueOf(orgId)).append("_");
  // for(Object con:cons){
  // if(con!=null){
  // keyStr.append(con).append("_");
  // }
  // }
  // keyStr.append(dateStr);
  // return keyStr.toString();
  // }
  /**
   * 根据查询条件参数生成缓存key
   * 
   * @param cons
   * @return
   * @author wangdawei
   */
  private String createKey(String moduleName, SearchVo searchVo) {
    UserSpace userSpace = (UserSpace) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE); // 用户空间
    Integer orgId = userSpace.getOrgId();
    String dateStr = new SimpleDateFormat("yyyyMMdd").format(new Date());
    StringBuilder keyStr = new StringBuilder().append(String.valueOf(orgId)).append("_").append(moduleName).append("_");
    if (searchVo.getTermId() != null) {
      keyStr.append("term").append(searchVo.getTermId()).append("_");
    }
    if (searchVo.getGradeId() != null) {
      keyStr.append("grade").append(searchVo.getGradeId()).append("_");
    }
    if (searchVo.getSubjectId() != null) {
      keyStr.append("subject").append(searchVo.getSubjectId()).append("_");
    }
    if (searchVo.getUserId() != null) {
      keyStr.append("user").append(searchVo.getUserId()).append("_");
    }
    keyStr.append("space").append(userSpace.getId()).append("_");
    keyStr.append(dateStr);
    return keyStr.toString();
  }

  /**
   * 获取当前学段下的年级
   * 
   * @return
   * @author wangdawei
   */
  @Override
  public List<Map<String, String>> getGradeList() {
    UserSpace userSpace = (UserSpace) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE); // 用户空间
    Organization org = orgService.findOne(userSpace.getOrgId());
    List<Meta> listAllGrade = MetaUtils.getOrgTypeMetaProvider().listAllGrade(org.getSchoolings(),
        userSpace.getPhaseId());
    List<Map<String, String>> gradeList = new ArrayList<Map<String, String>>();
    if (userSpace.getGradeId().intValue() != 0) { // 只显示当前身份的年级
      for (Meta meta : listAllGrade) {
        Map<String, String> gradeMap = new HashMap<String, String>();
        if (meta.getId().intValue() == userSpace.getGradeId().intValue()) {
          gradeMap.put("id", meta.getId().toString());
          gradeMap.put("name", meta.getName());
          gradeList.add(gradeMap);
          break;
        }
      }
    } else {// 显示全部年级
      for (Meta meta : listAllGrade) {
        Map<String, String> gradeMap = new HashMap<String, String>();
        gradeMap.put("id", meta.getId().toString());
        gradeMap.put("name", meta.getName());
        gradeList.add(gradeMap);
      }
    }
    return gradeList;
  }

  /**
   * 获取当前学段下的学科
   * 
   * @return
   * @author wangdawei
   */
  @Override
  public List<Map<String, String>> getSubjectList() {
    UserSpace userSpace = (UserSpace) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE); // 用户空间
    Organization org = orgService.findOne(userSpace.getOrgId());
    Integer[] areaIds = StringUtils.toIntegerArray(org.getAreaIds().substring(1, org.getAreaIds().lastIndexOf(",")),
        ",");
    List<Meta> listAllSubjectByPhaseId = MetaUtils.getPhaseSubjectMetaProvider().listAllSubject(org.getId(),
        userSpace.getPhaseId(), areaIds);
    List<Map<String, String>> subjectList = new ArrayList<Map<String, String>>();
    if (userSpace.getSubjectId().intValue() != 0) {
      for (Meta meta : listAllSubjectByPhaseId) {
        Map<String, String> subjectMap = new HashMap<String, String>();
        if (meta.getId().intValue() == userSpace.getSubjectId().intValue()) {
          subjectMap.put("id", meta.getId().toString());
          subjectMap.put("name", meta.getName());
          subjectList.add(subjectMap);
          break;
        }
      }
    } else {
      for (Meta meta : listAllSubjectByPhaseId) {
        Map<String, String> subjectMap = new HashMap<String, String>();
        subjectMap.put("id", meta.getId().toString());
        subjectMap.put("name", meta.getName());
        subjectList.add(subjectMap);
      }
    }
    return subjectList;
  }

  /**
   * 教师数据列表
   * 
   * @param searchVo
   * @return
   * @throws Exception
   * @see com.tmser.tr.teachingView.service.TeachingViewService#getTeacherDataList(com.tmser.tr.teachingview.vo.SearchVo)
   * @author wangdawei
   */
  @SuppressWarnings("unchecked")
  @Override
  public List<Map<String, Object>> getTeacherDataList(SearchVo searchVo) throws Exception {
    Integer schoolYear = (Integer) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SCHOOLYEAR);// 学年
    searchVo = setDateRange(searchVo);
    List<Map<String, Object>> dataList;
    String cacheKey = createKey(SearchVo.TW_TEACHER_LIST, searchVo);
    ValueWrapper element = teachingViewDataCache.get(cacheKey);
    if (element == null) {
      dataList = new ArrayList<Map<String, Object>>();
      UserSpace us = new UserSpace();
      us.setOrgId(searchVo.getOrgId());
      us.setSysRoleId(SysRole.TEACHER.getId().intValue());
      us.setSchoolYear(schoolYear);
      us.setGradeId(searchVo.getGradeId());
      us.setSubjectId(searchVo.getSubjectId());
      us.setEnable(1);
      List<UserSpace> teacherList = userSpaceService.findAll(us);
      for (UserSpace teacher : teacherList) {
        searchVo.setUserId(teacher.getUserId());
        searchVo.setSpaceId(teacher.getId());
        searchVo.setPhaseId(teacher.getPhaseId());
        Map<String, Object> tempMap = getDataMapByEunm(searchVo, TeacherView.getIdsList());
        tempMap.put("userName", teacher.getUsername());
        tempMap.put("url",
            WebThreadLocalUtils.getRequest().getContextPath()
                + "/jy/teachingView/manager/teachingView_t_detail?termId=" + searchVo.getTermId() + "&gradeId="
                + searchVo.getGradeId() + "&subjectId=" + searchVo.getSubjectId() + "&spaceId=" + teacher.getId());
        tempMap.put("spaceId", teacher.getId());
        dataList.add(tempMap);
      }
      if (!dataList.isEmpty()) {
        teachingViewDataCache.put(cacheKey, dataList);
      }
    } else {
      dataList = (List<Map<String, Object>>) element.get();
    }
    dataList = orderList(searchVo.getOrderFlag(), searchVo.getOrderMode(), dataList);
    return dataList;
  }

  /**
   * 对集合排序
   * 
   * @param orderFlag
   * @param orderMode
   * @param dataList
   * @return
   */
  private List<Map<String, Object>> orderList(final String orderFlag, final String orderMode,
      List<Map<String, Object>> dataList) {
    if (!StringUtils.isBlank(orderFlag)) {
      Collections.sort(dataList, new Comparator<Map<String, Object>>() {
        @Override
        public int compare(Map<String, Object> arg0, Map<String, Object> arg1) {
          if ("up".equals(orderMode)) {
            return ((Integer) arg0.get(String.valueOf(orderFlag))).compareTo((Integer) arg1.get(orderFlag));
          }
          return ((Integer) arg1.get(String.valueOf(orderFlag))).compareTo((Integer) arg0.get(orderFlag));
        }
      });
    }
    return dataList;
  }

  /**
   * 教师数据详情
   * 
   * @param searchVo
   * @return
   * @throws Exception
   * @see com.tmser.tr.teachingView.service.TeachingViewService#getTeacherDataDetail(com.tmser.tr.teachingview.vo.SearchVo)
   * @author wangdawei
   */
  @SuppressWarnings("unchecked")
  @Override
  public Map<String, Object> getTeacherDataDetail(SearchVo searchVo) throws Exception {
    searchVo = setDateRange(searchVo);
    Map<String, Object> dataMap;
    String cacheKey = createKey(SearchVo.TW_TEACHER_DETAIL, searchVo);
    ValueWrapper element = teachingViewDataCache.get(cacheKey);
    if (element == null) {
      dataMap = getDataMapByEunm(searchVo, TeacherDetailView.getIdsList());
      teachingViewDataCache.put(cacheKey, dataMap);
    } else {
      dataMap = (Map<String, Object>) element.get();
    }
    return dataMap;
  }

  /**
   * 年级教研情况列表
   * 
   * @param searchVo
   * @return
   * @throws Exception
   * @see com.tmser.tr.teachingView.service.TeachingViewService#getGradeDataList(com.tmser.tr.teachingview.vo.SearchVo)
   * @author wangdawei
   */
  @SuppressWarnings("unchecked")
  @Override
  public List<Map<String, Object>> getGradeDataList(SearchVo searchVo) throws Exception {
    searchVo = setDateRange(searchVo);
    List<Map<String, Object>> dataList;
    String cacheKey = createKey(SearchVo.TW_GRADE, searchVo);
    ValueWrapper element = teachingViewDataCache.get(cacheKey);
    if (element == null) {
      dataList = new ArrayList<Map<String, Object>>();
      List<Map<String, String>> gradeList = getGradeList();
      UserSpace us = new UserSpace();
      us.addCustomCulomn("distinct userId");
      us.setOrgId(searchVo.getOrgId());
      us.setSysRoleId(SysRole.TEACHER.getId());
      us.setPhaseId(searchVo.getPhaseId());
      us.setEnable(1);
      for (Map<String, String> grade : gradeList) {
        searchVo.setGradeId(Integer.valueOf(grade.get("id")));
        Map<String, Object> tempMap = getDataMapByEunm(searchVo, TeacherView.getIdsList());
        tempMap.put("gradeId", grade.get("id"));
        tempMap.put("gradeName", grade.get("name"));
        us.setGradeId(searchVo.getGradeId());
        us.setSubjectId(searchVo.getSubjectId());
        tempMap.put("teacherCount", userSpaceService.findAll(us).size());
        tempMap.put("url", WebThreadLocalUtils.getRequest().getContextPath()
            + "/jy/teachingView/manager/teachingView_t?flagz=grade&termId=" + searchVo.getTermId() + "&gradeId="
            + searchVo.getGradeId());
        dataList.add(tempMap);
      }
      if (!dataList.isEmpty()) {
        teachingViewDataCache.put(cacheKey, dataList);
      }
    } else {
      dataList = (List<Map<String, Object>>) element.get();
    }
    dataList = orderList(searchVo.getOrderFlag(), searchVo.getOrderMode(), dataList);
    return dataList;
  }

  /**
   * 学科教研情况列表
   * 
   * @param searchVo
   * @return
   * @throws Exception
   * @see com.tmser.tr.teachingView.service.TeachingViewService#getSubjectDataList(com.tmser.tr.teachingview.vo.SearchVo)
   * @author wangdawei
   */
  @SuppressWarnings("unchecked")
  @Override
  public List<Map<String, Object>> getSubjectDataList(SearchVo searchVo) throws Exception {
    searchVo = setDateRange(searchVo);
    List<Map<String, Object>> dataList;
    String cacheKey = createKey(SearchVo.TW_SUBJECT, searchVo);
    ValueWrapper element = teachingViewDataCache.get(cacheKey);
    if (element == null) {
      dataList = new ArrayList<Map<String, Object>>();
      List<Map<String, String>> subjectList = getSubjectList();
      UserSpace us = new UserSpace();
      us.setOrgId(searchVo.getOrgId());
      us.setSysRoleId(SysRole.TEACHER.getId());
      us.addCustomCulomn(" distinct userId ");
      us.setEnable(1);
      for (Map<String, String> subject : subjectList) {
        searchVo.setSubjectId(Integer.valueOf(subject.get("id")));
        Map<String, Object> tempMap = getDataMapByEunm(searchVo, TeacherView.getIdsList());
        tempMap.put("subjectId", subject.get("id"));
        tempMap.put("subjectName", subject.get("name"));
        us.setSubjectId(searchVo.getSubjectId());
        us.setGradeId(searchVo.getGradeId());
        tempMap.put("teacherCount", userSpaceService.findAll(us).size());
        tempMap.put("url", WebThreadLocalUtils.getRequest().getContextPath()
            + "/jy/teachingView/manager/teachingView_t?flagz=subject&termId=" + searchVo.getTermId() + "&subjectId="
            + searchVo.getSubjectId());
        dataList.add(tempMap);
      }
      if (!dataList.isEmpty()) {
        teachingViewDataCache.put(cacheKey, dataList);
      }
    } else {
      dataList = (List<Map<String, Object>>) element.get();
    }
    dataList = orderList(searchVo.getOrderFlag(), searchVo.getOrderMode(), dataList);
    return dataList;
  }

  /**
   * 获取撰写或分享的教案数据集合
   * 
   * @param searchVo
   * @param m
   * @see com.tmser.tr.teachingView.service.TeachingViewService#getJiaoanDataList(com.tmser.tr.teachingview.vo.SearchVo,
   *      org.springframework.ui.Model)
   */
  @Override
  public void getJiaoanDataList(SearchVo searchVo, Model m) {
    UserSpace us = userSpaceService.findOne(searchVo.getSpaceId());
    User user = userService.findOne(us.getUserId());
    searchVo = setDateRange(searchVo);
    // 获取已撰写的课题集合
    LessonInfo li = new LessonInfo();
    li.addAlias("a");
    Map<String, Object> paramMap = new HashMap<String, Object>();
    paramMap.put("startTime", searchVo.getStartTime());
    paramMap.put("endTime", searchVo.getEndTime());
    paramMap.put("userId", us.getUserId());
    paramMap.put("orgId", us.getOrgId());
    paramMap.put("gradeId", us.getGradeId());
    paramMap.put("subjectId", us.getSubjectId());
    paramMap.put("planType", LessonPlan.JIAO_AN);
    li.addCustomCondition(
        " and a.id in (select b.infoId from LessonPlan b where b.planType = :planType and b.orgId = :orgId and b.userId = :userId and b.gradeId = :gradeId and b.subjectId = :subjectId and b.crtDttm>=:startTime and b.crtDttm<:endTime ) ",
        paramMap);
    List<LessonInfo> lessonList = lessonInfoService.findAll(li);
    // 获取已分享的教案集合
    LessonPlan lp = new LessonPlan();
    lp.setOrgId(us.getOrgId());
    lp.setUserId(us.getUserId());
    lp.setGradeId(us.getGradeId());
    lp.setSubjectId(us.getSubjectId());
    lp.setIsShare(true);
    lp.setEnable(1);
    lp.setPlanType(LessonPlan.JIAO_AN);
    lp.addCustomCondition(" and shareTime>=:startTime and shareTime<:endTime ", paramMap);
    lp.addPage(searchVo.getPage());
    lp.pageSize(28);
    Integer planCount = lessonPlanService.count(lp);
    PageList<LessonPlan> planList = lessonPlanService.findByPage(lp);
    m.addAttribute("lessonList", lessonList);
    m.addAttribute("planList", planList);
    m.addAttribute("searchVo", searchVo);
    m.addAttribute("userSpace", us);
    m.addAttribute("planCount", planCount);
    m.addAttribute("user", user);
    m.addAttribute("gradeName", MetaUtils.getMeta(us.getGradeId()).getName());
    m.addAttribute("subjectName", MetaUtils.getMeta(us.getSubjectId()).getName());
  }

  /**
   * 获取撰写或分享的课件数据集合
   * 
   * @param searchVo
   * @param m
   * @see com.tmser.tr.teachingView.service.TeachingViewService#getJiaoanDataList(com.tmser.tr.teachingview.vo.SearchVo,
   *      org.springframework.ui.Model)
   */
  @Override
  public void getKejianDataList(SearchVo searchVo, Model m) {
    UserSpace us = userSpaceService.findOne(searchVo.getSpaceId());
    User user = userService.findOne(us.getUserId());
    searchVo = setDateRange(searchVo);
    // 获取已撰写的课题集合
    LessonInfo li = new LessonInfo();
    li.addAlias("a");
    li.addOrder(" a.crtDttm desc ");
    Map<String, Object> paramMap = new HashMap<String, Object>();
    paramMap.put("startTime", searchVo.getStartTime());
    paramMap.put("endTime", searchVo.getEndTime());
    paramMap.put("userId", us.getUserId());
    paramMap.put("orgId", us.getOrgId());
    paramMap.put("gradeId", us.getGradeId());
    paramMap.put("subjectId", us.getSubjectId());
    paramMap.put("planType", LessonPlan.KE_JIAN);
    li.addCustomCondition(
        " and a.id in (select b.infoId from LessonPlan b where b.planType = :planType and b.orgId = :orgId and b.userId = :userId and b.gradeId = :gradeId and b.subjectId = :subjectId and b.crtDttm>=:startTime and b.crtDttm<:endTime ) ",
        paramMap);
    List<LessonInfo> lessonList = lessonInfoService.findAll(li);
    // 获取已分享的教案集合
    LessonPlan lp = new LessonPlan();
    lp.addOrder(" shareTime desc ");
    lp.setOrgId(us.getOrgId());
    lp.setUserId(us.getUserId());
    lp.setGradeId(us.getGradeId());
    lp.setSubjectId(us.getSubjectId());
    lp.setIsShare(true);
    lp.setEnable(1);
    lp.setPlanType(LessonPlan.KE_JIAN);
    lp.addCustomCondition(" and shareTime>=:startTime and shareTime<:endTime ", paramMap);
    lp.addPage(searchVo.getPage());
    lp.pageSize(28);
    Integer planCount = lessonPlanService.count(lp);
    PageList<LessonPlan> planList = lessonPlanService.findByPage(lp);
    m.addAttribute("lessonList", lessonList);
    m.addAttribute("planList", planList);
    m.addAttribute("searchVo", searchVo);
    m.addAttribute("userSpace", us);
    m.addAttribute("planCount", planCount);
    m.addAttribute("user", user);
    m.addAttribute("gradeName", MetaUtils.getMeta(us.getGradeId()).getName());
    m.addAttribute("subjectName", MetaUtils.getMeta(us.getSubjectId()).getName());
  }

  /**
   * 获取撰写和分享的反思数据集合
   * 
   * @param searchVo
   * @param m
   * @see com.tmser.tr.teachingView.service.TeachingViewService#getFansiDataList(com.tmser.tr.teachingview.vo.SearchVo,
   *      org.springframework.ui.Model)
   * @author wangdawei
   */
  @Override
  public void getFansiDataList(SearchVo searchVo, Model m) {
    UserSpace us = userSpaceService.findOne(searchVo.getSpaceId());
    User user = userService.findOne(us.getUserId());
    searchVo = setDateRange(searchVo);
    // 获取已撰写的课题集合
    LessonInfo li = new LessonInfo();
    li.addAlias("a");
    Map<String, Object> paramMap = new HashMap<String, Object>();
    paramMap.put("startTime", searchVo.getStartTime());
    paramMap.put("endTime", searchVo.getEndTime());
    paramMap.put("userId", us.getUserId());
    paramMap.put("orgId", us.getOrgId());
    paramMap.put("gradeId", us.getGradeId());
    paramMap.put("subjectId", us.getSubjectId());
    paramMap.put("planType", LessonPlan.KE_HOU_FAN_SI);
    li.addCustomCondition(
        " and a.id in (select b.infoId from LessonPlan b where b.planType = :planType and b.orgId = :orgId and b.userId = :userId and b.gradeId = :gradeId and b.subjectId = :subjectId and b.crtDttm>=:startTime and b.crtDttm<:endTime ) ",
        paramMap);
    Integer kehoufansiCount = lessonInfoService.count(li);
    if ((searchVo.getFlags() == null && searchVo.getFlago() == null)
        || ("0".equals(searchVo.getFlags()) && "0".equals(searchVo.getFlago()))) {
      List<LessonInfo> kehoufansiList = lessonInfoService.findAll(li);
      m.addAttribute("planList", kehoufansiList);
    }
    // 获取已分享的教案集合
    LessonPlan lp = new LessonPlan();
    lp.setOrgId(us.getOrgId());
    lp.setUserId(us.getUserId());
    lp.setGradeId(us.getGradeId());
    lp.setSubjectId(us.getSubjectId());
    lp.setEnable(1);
    lp.addCustomCondition(" and crtDttm>=:startTime and crtDttm<:endTime ", paramMap);
    lp.addPage(searchVo.getPage());
    lp.pageSize(28);
    lp.setPlanType(LessonPlan.QI_TA_FAN_SI);
    Integer qitafansiCount = lessonPlanService.count(lp);
    if ("0".equals(searchVo.getFlags()) && "1".equals(searchVo.getFlago())) {
      PageList<LessonPlan> qitafansiList = lessonPlanService.findByPage(lp);
      m.addAttribute("planList", qitafansiList);
    }
    LessonPlan lp1 = new LessonPlan();
    lp1.setOrgId(us.getOrgId());
    lp1.setUserId(us.getUserId());
    lp1.setGradeId(us.getGradeId());
    lp1.setSubjectId(us.getSubjectId());
    lp1.setEnable(1);
    lp1.addPage(searchVo.getPage());
    lp1.pageSize(28);
    lp1.setPlanType(LessonPlan.QI_TA_FAN_SI);
    lp1.setIsShare(true);
    lp1.addCustomCondition(" and shareTime>=:startTime and shareTime<:endTime ", paramMap);
    Integer qitafansiCount_share = lessonPlanService.count(lp1);
    if ("1".equals(searchVo.getFlags()) && "1".equals(searchVo.getFlago())) {
      PageList<LessonPlan> qitafansiList_share = lessonPlanService.findByPage(lp1);
      m.addAttribute("planList", qitafansiList_share);
    }
    lp1.setPlanType(LessonPlan.KE_HOU_FAN_SI);
    Integer kehoufansiCount_share = lessonPlanService.count(lp1);
    if ("1".equals(searchVo.getFlags()) && "0".equals(searchVo.getFlago())) {
      PageList<LessonPlan> kehoufansiList_share = lessonPlanService.findByPage(lp1);
      m.addAttribute("planList", kehoufansiList_share);
    }
    m.addAttribute("kehoufansiCount", kehoufansiCount);
    m.addAttribute("kehoufansiCount_share", kehoufansiCount_share);
    m.addAttribute("qitafansiCount", qitafansiCount);
    m.addAttribute("qitafansiCount_share", qitafansiCount_share);
    m.addAttribute("writeCount", kehoufansiCount + qitafansiCount);
    m.addAttribute("shareCount", kehoufansiCount_share + qitafansiCount_share);
    m.addAttribute("searchVo", searchVo);
    m.addAttribute("userSpace", us);
    m.addAttribute("user", user);
    m.addAttribute("gradeName", MetaUtils.getMeta(us.getGradeId()).getName());
    m.addAttribute("subjectName", MetaUtils.getMeta(us.getSubjectId()).getName());
  }

  /**
   * 获取听课记录相关数据集合
   * 
   * @param searchVo
   * @param m
   * @see com.tmser.tr.teachingView.service.TeachingViewService#getListenDataList(com.tmser.tr.teachingview.vo.SearchVo,
   *      org.springframework.ui.Model)
   */
  @Override
  public void getListenDataList(SearchVo searchVo, Model m) {
    UserSpace us = userSpaceService.findOne(searchVo.getSpaceId());
    User user = userService.findOne(us.getUserId());
    searchVo = setDateRange(searchVo);
    LectureRecords lr = new LectureRecords();
    lr.setLecturepeopleId(us.getUserId());
    lr.setIsEpub(1);
    lr.setIsDelete(false);
    Map<String, Object> paramMap = new HashMap<String, Object>();
    paramMap.put("startTime", searchVo.getStartTime());
    paramMap.put("endTime", searchVo.getEndTime());
    lr.addCustomCondition(" and crtDttm >= :startTime and crtDttm < :endTime", paramMap);
    lr.addPage(searchVo.getPage());
    lr.pageSize(10);
    lr.addOrder(" crtDttm desc ");
    PageList<LectureRecords> recordList = lectureRecordService.findByPage(lr);
    m.addAttribute("recordList", recordList);
    m.addAttribute("searchVo", searchVo);
    m.addAttribute("userSpace", us);
    m.addAttribute("user", user);
    m.addAttribute("gradeName", MetaUtils.getMeta(us.getGradeId()).getName());
    m.addAttribute("subjectName", MetaUtils.getMeta(us.getSubjectId()).getName());
  }

  /**
   * 获取集体备课相关数据集合
   * 
   * @param searchVo
   * @param m
   * @see com.tmser.tr.teachingView.service.TeachingViewService#getActivityDataList(com.tmser.tr.teachingview.vo.SearchVo,
   *      org.springframework.ui.Model)
   * @author wangdawei
   */
  @Override
  public void getActivityDataList(SearchVo searchVo, Model m) {
    UserSpace us = userSpaceService.findOne(searchVo.getSpaceId());
    User user = userService.findOne(us.getUserId());
    searchVo = setDateRange(searchVo);
    Activity activity = new Activity();
    activity.addAlias("a");
    activity.setStatus(1);
    Map<String, Object> paramMap = new HashMap<String, Object>();
    paramMap.put("startTime", searchVo.getStartTime());
    paramMap.put("endTime", searchVo.getEndTime());
    paramMap.put("userId", us.getUserId());
    paramMap.put("spaceId", us.getId());
    paramMap.put("editType", 2);
    activity
        .addCustomCondition(
            " and (a.id in (select distinct b.activityId from ActivityTracks b where b.editType!=:editType and b.userId=:userId and b.spaceId=:spaceId and b.crtDttm>=:startTime and b.crtDttm<:endTime) or a.id in (select distinct c.activityId from Discuss c where c.typeId = :typeId and c.crtId=:userId and c.spaceId=:spaceId and c.crtDttm>=:startTime and c.crtDttm<:endTime))",
            paramMap);
    paramMap.put("typeId", ResTypeConstants.ACTIVITY);
    activity.addPage(searchVo.getPage());
    activity.pageSize(2);
    PageList<Activity> activitList = activityService.findByPage(activity);
    List<Map<String, Object>> activityMapList = new ArrayList<Map<String, Object>>();
    for (Activity a : activitList.getDatalist()) {
      Map<String, Object> activityMap = new HashMap<String, Object>();
      ActivityTracks at = new ActivityTracks();
      at.setActivityId(a.getId());
      at.setUserId(us.getUserId());
      at.setSpaceId(searchVo.getSpaceId());
      List<ActivityTracks> trackList = activityTracksService.findAll(at);
      Discuss ad = new Discuss();
      ad.setTypeId(ResTypeConstants.ACTIVITY);
      ad.setActivityId(a.getId());
      ad.setCrtId(us.getUserId());
      ad.setSpaceId(searchVo.getSpaceId());
      List<Discuss> discussList = discussService.findAll(ad);
      activityMap.put("activity", a);
      activityMap.put("trackList", trackList);
      activityMap.put("discussList", discussList);
      activityMapList.add(activityMap);
    }
    m.addAttribute("activityMapList", activityMapList);
    m.addAttribute("searchVo", searchVo);
    m.addAttribute("userSpace", us);
    m.addAttribute("activityList", activitList);
    m.addAttribute("user", user);
    m.addAttribute("gradeName", MetaUtils.getMeta(us.getGradeId()).getName());
    m.addAttribute("subjectName", MetaUtils.getMeta(us.getSubjectId()).getName());
  }

  /**
   * 获取已参与的校际教研数据集合
   * 
   * @param searchVo
   * @param m
   * @see com.tmser.tr.teachingView.service.TeachingViewService#getSchoolActivityDataList(com.tmser.tr.teachingview.vo.SearchVo,
   *      org.springframework.ui.Model)
   * @author wangdawei
   */
  @Override
  public void getSchoolActivityDataList(SearchVo searchVo, Model m) {
    UserSpace us = userSpaceService.findOne(searchVo.getSpaceId());
    User user = userService.findOne(us.getUserId());
    searchVo = setDateRange(searchVo);
    SchoolActivity activity = new SchoolActivity();
    activity.addAlias("a");
    activity.setStatus(1);
    Map<String, Object> paramMap = new HashMap<String, Object>();
    paramMap.put("startTime", searchVo.getStartTime());
    paramMap.put("endTime", searchVo.getEndTime());
    paramMap.put("userId", us.getUserId());
    paramMap.put("spaceId", us.getId());
    paramMap.put("editType", 2);
    paramMap.put("typeId", ResTypeConstants.SCHOOLTEACH);
    activity
        .addCustomCondition(
            " and a.id in (select distinct b.activityId from SchoolActivityTracks b where b.editType!=:editType and b.userId=:userId and b.spaceId=:spaceId and b.crtDttm>=:startTime and b.crtDttm<:endTime) or a.id in (select distinct c.activityId from Discuss c where c.typeId = :typeId and c.crtId=:userId and c.spaceId=:spaceId and c.crtDttm>=:startTime and c.crtDttm<:endTime)",
            paramMap);
    activity.addPage(searchVo.getPage());
    activity.pageSize(2);
    PageList<SchoolActivity> activitList = schoolActivityService.findByPage(activity);
    List<Map<String, Object>> activityMapList = new ArrayList<Map<String, Object>>();
    for (SchoolActivity a : activitList.getDatalist()) {
      Map<String, Object> activityMap = new HashMap<String, Object>();
      SchoolActivityTracks at = new SchoolActivityTracks();
      at.setActivityId(a.getId());
      at.setUserId(us.getUserId());
      at.setSpaceId(searchVo.getSpaceId());
      List<SchoolActivityTracks> trackList = schoolActivityTracksService.findAll(at);
      Discuss ad = new Discuss();
      ad.setActivityId(a.getId());
      ad.setCrtId(us.getUserId());
      ad.setSpaceId(searchVo.getSpaceId());
      ad.setTypeId(ResTypeConstants.SCHOOLTEACH);
      List<Discuss> discussList = discussService.findAll(ad);
      activityMap.put("activity", a);
      activityMap.put("trackList", trackList);
      activityMap.put("discussList", discussList);
      activityMapList.add(activityMap);
    }
    m.addAttribute("activityMapList", activityMapList);
    m.addAttribute("searchVo", searchVo);
    m.addAttribute("userSpace", us);
    m.addAttribute("activityList", activitList);
    m.addAttribute("user", user);
    m.addAttribute("gradeName", MetaUtils.getMeta(us.getGradeId()).getName());
    m.addAttribute("subjectName", MetaUtils.getMeta(us.getSubjectId()).getName());
  }

  /**
   * 获取撰写和分享的计划总结数据集合
   * 
   * @param searchVo
   * @param m
   * @see com.tmser.tr.teachingView.service.TeachingViewService#getSummaryDataList(com.tmser.tr.teachingview.vo.SearchVo,
   *      org.springframework.ui.Model)
   * @author wangdawei
   */
  @Override
  public void getSummaryDataList(SearchVo searchVo, Model m) {
    UserSpace us = userSpaceService.findOne(searchVo.getSpaceId());
    User user = userService.findOne(us.getUserId());
    searchVo = setDateRange(searchVo);
    PlainSummary ps = new PlainSummary();
    ps.setUserId(us.getUserId());
    ps.setUserRoleId(us.getRoleId());
    ps.setGradeId(us.getGradeId());
    ps.setSubjectId(us.getSubjectId());
    ps.setPhaseId(us.getPhaseId());
    Map<String, Object> paramMap = new HashMap<String, Object>();
    paramMap.put("startTime", searchVo.getStartTime());
    paramMap.put("endTime", searchVo.getEndTime());
    ps.addCustomCondition(
        " and crtDttm>=:startTime and crtDttm<:endTime and (category=:category1 or category=:category2)", paramMap);
    paramMap.put("category1", 1);
    paramMap.put("category2", 3);
    Integer planCount = planSummaryService.count(ps);
    if ((searchVo.getFlags() == null && searchVo.getFlago() == null)
        || ("0".equals(searchVo.getFlags()) && "0".equals(searchVo.getFlago()))) {
      List<PlainSummary> planList = planSummaryService.findAll(ps);
      m.addAttribute("dataList", planList);
    }
    paramMap.put("category1", 2);
    paramMap.put("category2", 4);
    Integer summaryCount = planSummaryService.count(ps);
    if ("0".equals(searchVo.getFlags()) && "1".equals(searchVo.getFlago())) {
      List<PlainSummary> summaryList = planSummaryService.findAll(ps);
      m.addAttribute("dataList", summaryList);
    }
    ps.setIsShare(1);
    Integer summaryShareCount = planSummaryService.count(ps);
    if ("1".equals(searchVo.getFlags()) && "1".equals(searchVo.getFlago())) {
      List<PlainSummary> summaryShareList = planSummaryService.findAll(ps);
      m.addAttribute("dataList", summaryShareList);
    }
    paramMap.put("category1", 1);
    paramMap.put("category2", 3);
    Integer planShareCount = planSummaryService.count(ps);
    if ("1".equals(searchVo.getFlags()) && "0".equals(searchVo.getFlago())) {
      List<PlainSummary> planShareList = planSummaryService.findAll(ps);
      m.addAttribute("dataList", planShareList);
    }
    m.addAttribute("planCount", planCount);
    m.addAttribute("planShareCount", planShareCount);
    m.addAttribute("summaryCount", summaryCount);
    m.addAttribute("summaryShareCount", summaryShareCount);
    m.addAttribute("writeCount", planCount + summaryCount);
    m.addAttribute("shareCount", planShareCount + summaryShareCount);
    m.addAttribute("searchVo", searchVo);
    m.addAttribute("userSpace", us);
    m.addAttribute("user", user);
    m.addAttribute("gradeName", MetaUtils.getMeta(us.getGradeId()).getName());
    m.addAttribute("subjectName", MetaUtils.getMeta(us.getSubjectId()).getName());
  }

  /**
   * 获取撰写和分享的教学文章集合
   * 
   * @param searchVo
   * @param m
   * @see com.tmser.tr.teachingView.service.TeachingViewService#getThesisDataList(com.tmser.tr.teachingview.vo.SearchVo,
   *      org.springframework.ui.Model)
   * @author wangdawei
   */
  @Override
  public void getThesisDataList(SearchVo searchVo, Model m) {
    UserSpace us = userSpaceService.findOne(searchVo.getSpaceId());
    User user = userService.findOne(us.getUserId());
    searchVo = setDateRange(searchVo);
    Thesis thesis = new Thesis();
    thesis.setEnable(1);
    thesis.setUserId(us.getUserId());
    thesis.setOrgId(us.getOrgId());
    Map<String, Object> paramMap = new HashMap<String, Object>();
    paramMap.put("startTime", searchVo.getStartTime());
    paramMap.put("endTime", searchVo.getEndTime());
    thesis.addCustomCondition(" and crtDttm>=:startTime and crtDttm<:endTime", paramMap);
    if ("0".equals(searchVo.getFlags())) {
      thesis.addPage(searchVo.getPage());
    }
    thesis.pageSize(28);
    Integer writeCount = thesisService.count(thesis);
    PageList<Thesis> writeList = thesisService.findByPage(thesis);
    thesis.setIsShare(1);
    thesis.addPage(new Page());
    if ("1".equals(searchVo.getFlags())) {
      thesis.addPage(searchVo.getPage());
    }
    thesis.pageSize(28);
    Integer shareCount = thesisService.count(thesis);
    PageList<Thesis> shareList = thesisService.findByPage(thesis);
    m.addAttribute("writeCount", writeCount);
    m.addAttribute("writeList", writeList);
    m.addAttribute("shareCount", shareCount);
    m.addAttribute("shareList", shareList);
    m.addAttribute("searchVo", searchVo);
    m.addAttribute("userSpace", us);
    m.addAttribute("user", user);
    m.addAttribute("gradeName", MetaUtils.getMeta(us.getGradeId()).getName());
    m.addAttribute("subjectName", MetaUtils.getMeta(us.getSubjectId()).getName());
  }

  /**
   * 获取自己的留言和附件
   * 
   * @param searchVo
   * @param m
   * @see com.tmser.tr.teachingView.service.TeachingViewService#getCompanionDataList(com.tmser.tr.teachingview.vo.SearchVo,
   *      org.springframework.ui.Model)
   * @author wangdawei
   */
  @Override
  public void getCompanionDataList(SearchVo searchVo, Model m) {
    UserSpace us = userSpaceService.findOne(searchVo.getSpaceId());
    User user = userService.findOne(us.getUserId());
    searchVo = setDateRange(searchVo);
    JyCompanionMessage message = new JyCompanionMessage();
    message.setUserIdSender(us.getUserId());
    Map<String, Object> paramMap = new HashMap<String, Object>();
    paramMap.put("startTime", searchVo.getStartTime());
    paramMap.put("endTime", searchVo.getEndTime());
    message.addCustomCondition(" and senderTime>=:startTime and senderTime<:endTime", paramMap);
    message.addPage(searchVo.getPage());
    message.pageSize(5);
    message.addOrder(" senderTime desc ");
    Integer messageCount = companionMessageService.count(message);
    PageList<JyCompanionMessage> messageList = companionMessageService.findByPage(message);
    m.addAttribute("messageList", messageList);
    m.addAttribute("messageCount", messageCount);
    m.addAttribute("searchVo", searchVo);
    m.addAttribute("userSpace", us);
    m.addAttribute("user", user);
    m.addAttribute("gradeName", MetaUtils.getMeta(us.getGradeId()).getName());
    m.addAttribute("subjectName", MetaUtils.getMeta(us.getSubjectId()).getName());
  }

  /**
   * 获取成长档案袋的集合
   * 
   * @param searchVo
   * @param m
   * @see com.tmser.tr.teachingView.service.TeachingViewService#getRecordBagDataList(com.tmser.tr.teachingview.vo.SearchVo,
   *      org.springframework.ui.Model)
   * @author wangdawei
   */
  @Override
  public void getRecordBagDataList(SearchVo searchVo, Model m) {
    setDateRange(searchVo);
    UserSpace us = userSpaceService.findOne(searchVo.getSpaceId());
    User user = userService.findOne(us.getUserId());
    Recordbag bag = new Recordbag();
    bag.setDelete(0);
    bag.setOrgId(us.getOrgId());
    bag.setTeacherId(String.valueOf(us.getUserId()));
    bag.setGradeId(us.getGradeId());
    bag.setSubjectId(us.getSubjectId());
    bag.addOrder("sort asc");
    List<Recordbag> bagList = recordBagService.find(bag, 1000);
    Map<String, Object> paramMap = new HashMap<String, Object>();
    paramMap.put("startTime", searchVo.getStartTime());
    paramMap.put("endTime", searchVo.getEndTime());
    for (Recordbag temp : bagList) {
      Record record = new Record();
      record.setBagId(temp.getId());
      // record.setStatus(1);
      record.addCustomCondition(" and createTime>=:startTime and createTime<=:endTime ", paramMap);
      temp.setResCount(recordService.count(record));
    }
    m.addAttribute("bagList", bagList);
    m.addAttribute("searchVo", searchVo);
    m.addAttribute("userSpace", us);
    m.addAttribute("user", user);
    m.addAttribute("gradeName", MetaUtils.getMeta(us.getGradeId()).getName());
    m.addAttribute("subjectName", MetaUtils.getMeta(us.getSubjectId()).getName());
  }

  /**
   * 获取成长档案袋内详情
   * 
   * @param id
   * @param m
   * @return
   * @see com.tmser.tr.teachingView.service.TeachingViewService#getRecordBagDetail(java.lang.Integer,
   *      org.springframework.ui.Model)
   */
  @Override
  public Integer getRecordBagDetail(Record model, Integer termId, Model m) {
    SearchVo searchVo = new SearchVo();
    searchVo.setTermId(termId);
    setDateRange(searchVo);
    Integer id = model.getBagId();
    // model.setStatus(1);
    Map<String, Object> paramMap = new HashMap<String, Object>();
    paramMap.put("startTime", searchVo.getStartTime());
    paramMap.put("endTime", searchVo.getEndTime());
    model.addCustomCondition(" and createTime>=:startTime and createTime<:endTime ", paramMap);
    Recordbag bag = recordBagService.findOne(id);
    model.addOrder("createTime desc");
    if (3 == Recordbag.switchResType(bag.getName()) || 6 == Recordbag.switchResType(bag.getName())) {
      model.pageSize(15);
    } else {
      model.pageSize(18);
    }
    PageList<Record> reList = recordService.findByPage(model);
    int flag = 0;
    for (Record record : reList.getDatalist()) {
      String time = DateUtils.formatDate(record.getCreateTime(), "yyyy-MM-dd");
      record.setTime(time);
      if (0 == Recordbag.switchResType(bag.getName()) || 1 == Recordbag.switchResType(bag.getName())
          || 2 == Recordbag.switchResType(bag.getName())) {
        recordBagService.saveLessonPlan(record);
      } else if (3 == Recordbag.switchResType(bag.getName())) {
        flag = recordBagService.saveActivity(record, flag, id);
      } else if (4 == Recordbag.switchResType(bag.getName())) {
        recordBagService.saveThesis(record, id);
      } else if (5 == Recordbag.switchResType(bag.getName())) {
        recordBagService.savePlainSummary(record, id);
      } else if (6 == Recordbag.switchResType(bag.getName())) {
        flag = recordBagService.saveLectureRecords(record, flag, id);
      } else {
        Resources res = resService.findOne(record.getPath());
        if (res != null) {
          record.setFlags(res.getExt());
        }
      }

    }
    m.addAttribute("data", reList);// 按照分页进行查询
    m.addAttribute("page", reList.getCurrentPage());
    m.addAttribute("name", bag.getName());
    m.addAttribute("searchVo", searchVo);
    return flag;
  }

  /**
   * 根据统计项的id查询统计数
   * 
   * @param searchVo
   * @param list
   * @return
   * @throws Exception
   * @author wangdawei
   */
  private Map<String, Object> getDataMapByEunm(SearchVo searchVo, List<String> list) throws Exception {
    Map<String, Object> dataMap = new HashMap<String, Object>();
    for (String id : list) {
      Integer count = teachingViewDao.getCount(searchVo, id);
      dataMap.put(id, count);
    }
    return dataMap;
  }

  /**
   * 设置统计的时间范围
   * 
   * @param searchVo
   * @return
   * @author wangdawei
   */
  @Override
  public SearchVo setDateRange(SearchVo searchVo) {
    Integer schoolYear = (Integer) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SCHOOLYEAR);// 学年
    searchVo.setSchoolYear(schoolYear);
    if (StringUtils.isBlank(searchVo.getStartTime()) && StringUtils.isBlank(searchVo.getEndTime())) {
      Integer term = searchVo.getTermId();
      if (StringUtils.isBlank(searchVo.getStartTime())) {
        searchVo.setStartTime(DateUtils.formatDate(schoolYearService.getTermStartDate(term), "yyyy-MM-dd HH:mm:ss"));
      } else {
        searchVo.setStartTime(searchVo.getStartTime() + " 00:00:00");
      }
      if (StringUtils.isBlank(searchVo.getEndTime())) {
        searchVo.setEndTime(DateUtils.formatDate(term.intValue() == 0 ? schoolYearService.getNextTermStartTime()
            : schoolYearService.getNextYearUpTermStartTime(), "yyyy-MM-dd HH:mm:ss"));
      } else {
        searchVo.setEndTime(searchVo.getEndTime() + " 23:59:59");
      }
    }
    return searchVo;
  }

  /**
   * 获得管理者详情页
   * 
   * @author wangyao
   * @param searchVo
   * @param userSpace
   * @return
   * @throws Exception
   * @see com.tmser.tr.teachingView.service.TeachingViewService#getManagerDataList(com.tmser.tr.teachingview.vo.SearchVo,
   *      com.tmser.tr.uc.bo.UserSpace)
   */
  @SuppressWarnings("unchecked")
  @Override
  public List<Map<String, Object>> getManagerDataList(final SearchVo searchVo, UserSpace userSpace) throws Exception {
    List<Map<String, Object>> dataList = null;
    if (userSpace != null && userSpace.getSysRoleId() != null) {
      Integer sysRoleId = userSpace.getSysRoleId();
      String cacheKey = createKey(SearchVo.TW_MANAGER, searchVo);
      ValueWrapper element = teachingViewDataCache.get(cacheKey);
      this.setDateRange(searchVo);// 设置时间限制
      if (element == null) {
        List<UserSpace> userSpaceList = new ArrayList<UserSpace>();
        dataList = new ArrayList<Map<String, Object>>();
        if (SysRole.XZ.getId().equals(sysRoleId)) {
          userSpaceList = getUserBySysRoleId(userSpace, SysRole.FXZ.getId(), SysRole.ZR.getId(), SysRole.XKZZ.getId(),
              SysRole.NJZZ.getId(), SysRole.BKZZ.getId());
        } else if (SysRole.FXZ.getId().equals(sysRoleId)) {
          userSpaceList = getUserBySysRoleId(userSpace, SysRole.ZR.getId(), SysRole.XKZZ.getId(), SysRole.NJZZ.getId(),
              SysRole.BKZZ.getId());
        } else if (SysRole.ZR.getId().equals(sysRoleId)) {
          userSpaceList = getUserBySysRoleId(userSpace, SysRole.XKZZ.getId(), SysRole.NJZZ.getId(),
              SysRole.BKZZ.getId());
        } else if (SysRole.XKZZ.getId().equals(sysRoleId)) {
          userSpaceList = getUserBySysRoleId(userSpace, SysRole.BKZZ.getId());
        } else if (SysRole.NJZZ.getId().equals(sysRoleId)) {
          userSpaceList = getUserBySysRoleId(userSpace, SysRole.BKZZ.getId());
        }
        Map<Integer, List<Integer>> userIdMap = new HashMap<Integer, List<Integer>>();
        Map<Integer, String> userNameMap = new HashMap<Integer, String>();
        for (UserSpace userSpace2 : userSpaceList) {
          if (userSpace2.getUserId() != null) {
            // 不包含自己
            if (userIdMap.containsKey(userSpace2.getUserId())) {
              userIdMap.get(userSpace2.getUserId()).add(userSpace2.getId());
            } else {
              List<Integer> spaceIdList = new ArrayList<Integer>();
              spaceIdList.add(userSpace2.getId());
              userIdMap.put(userSpace2.getUserId(), spaceIdList);// 每个用户对应的所有身份合集
              userNameMap.put(userSpace2.getUserId(), userSpace2.getUsername());
            }
          }
        }
        for (Integer userId : userIdMap.keySet()) {
          List<Integer> list = userIdMap.get(userId);
          if (!CollectionUtils.isEmpty(list)) {
            Map<String, Object> dataMap = new HashMap<String, Object>();
            dataMap.put("userName", userNameMap.get(userId));
            dataMap.put("userId", userId);
            dataMap.put("searchVo", searchVo);
            searchVo.setUserId(userId);
            searchVo.setSpaceIds(list);
            Map<String, Object> tempMap = getDataMapByEunm(searchVo, ManagerView.getIdsList());
            dataMap.putAll(tempMap);
            dataList.add(dataMap);
          }
        }
        if (!dataList.isEmpty()) {
          teachingViewDataCache.put(cacheKey, dataList);
        }
      } else {
        dataList = (List<Map<String, Object>>) element.get();
      }
    }
    final String flago = searchVo.getFlago();
    if (searchVo.getFlago() != null && !"userName".equals(searchVo.getFlago())) {
      Collections.sort(dataList, new Comparator<Map<String, Object>>() {
        @Override
        public int compare(Map<String, Object> o1, Map<String, Object> o2) {
          if ("asc".equals(searchVo.getFlags())) {
            return ((Integer) o1.get(flago)).compareTo((Integer) o2.get(flago));
          } else {
            return ((Integer) o2.get(flago)).compareTo((Integer) o1.get(flago));
          }
        }

      });
    } else if ("userName".equals(searchVo.getFlago())) {
      Collections.sort(dataList, new Comparator<Map<String, Object>>() {
        @Override
        public int compare(Map<String, Object> o1, Map<String, Object> o2) {
          if ("asc".equals(searchVo.getFlags())) {
            return ((String) o1.get(flago)).compareTo((String) o2.get(flago));
          } else {
            return ((String) o2.get(flago)).compareTo((String) o1.get(flago));
          }
        }

      });
    }
    return dataList;
  }

  /**
   * 根据身份获得对应用户
   * 
   * @author wangyao
   * @param userspace
   * @param sysRoleId
   * @return
   */
  private List<UserSpace> getUserBySysRoleId(UserSpace userspace, Integer... sysRoleId) {
    StringBuilder sql = new StringBuilder();
    Map<String, Object> paramMap = new HashMap<String, Object>();
    UserSpace userSpace = new UserSpace();
    userSpace.setOrgId(userspace.getOrgId());
    userSpace.setEnable(UserSpace.ENABLE);
    UserSpace us = userSpaceService.findOne(userspace.getId());
    if (us.getPhaseId() != null && us.getPhaseId().equals(userspace.getPhaseId())) {
      userSpace.setPhaseId(userspace.getPhaseId());
    } else {
      sql.append(" and phaseId in (:phaseIds)");
      paramMap.put("phaseIds", Arrays.asList(new Integer[] { userspace.getPhaseId(), us.getPhaseId() }));
    }
    if (SysRole.XKZZ.getId().equals(userspace.getSysRoleId())) {
      userSpace.setSubjectId(userspace.getSubjectId());
    }
    if (SysRole.NJZZ.getId().equals(userspace.getSysRoleId())) {
      userSpace.setGradeId(userspace.getGradeId());
    }
    if (sysRoleId != null) {
      sql.append(" and sysRoleId in (:sysRoleId) and userId <> :userId");
      paramMap.put("userId", userspace.getUserId());
      paramMap.put("sysRoleId", Arrays.asList(sysRoleId));
    }
    userSpace.setSchoolYear((Integer) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SCHOOLYEAR));
    userSpace.addCustomCondition(" and sysRoleId <> :teacher " + sql.toString(), paramMap);
    paramMap.put("teacher", SysRole.TEACHER.getId());
    return userSpaceService.findAll(userSpace);
  }

  /**
   * 获得用的详细身份信息
   * 
   * @author wangyao
   * @param userspaceList
   * @param searchVo
   * @return
   */
  private Map<String, Object> getUserSubjectAndGrades(List<UserSpace> userspaceList, SearchVo searchVo) {
    Map<String, Object> metaMap = new HashMap<String, Object>();
    Set<Integer> subjectSet = new TreeSet<Integer>();
    Set<Integer> gradeSet = new TreeSet<Integer>();
    List<Integer> subjectList = new ArrayList<Integer>();
    List<Integer> gradeList = new ArrayList<Integer>();
    List<Integer> spaceIdList = new ArrayList<Integer>();
    List<Integer> sysRoleIdList = new ArrayList<Integer>();
    Organization org = orgService.findOne(searchVo.getOrgId());
    if (org != null) {
      if (!CollectionUtils.isEmpty(userspaceList)) {
        for (UserSpace userSpace2 : userspaceList) {
          if (!SysRole.TEACHER.getId().equals(userSpace2.getSysRoleId()) && userSpace2.getSubjectId() != null) {
            subjectSet.add(userSpace2.getSubjectId());// 查出所有角色不相同的学科ID
          }
          if (!SysRole.TEACHER.getId().equals(userSpace2.getSysRoleId()) && userSpace2.getGradeId() != null) {
            gradeSet.add(userSpace2.getGradeId());// 查出所有角色不相同的年级ID
          }
          spaceIdList.add(userSpace2.getId());
          sysRoleIdList.add(userSpace2.getSysRoleId());
        }
      }
      for (Integer gradeId : gradeSet) {
        if (gradeId == 0) {
          List<Meta> listAllGrade = MetaUtils.getOrgTypeMetaProvider().listAllGrade(org.getSchoolings(),
              searchVo.getPhaseId());
          if (!CollectionUtils.isEmpty(listAllGrade)) {
            for (Meta meta : listAllGrade) {
              gradeList.add(meta.getId());
            }
          }
          break;
        } else {
          gradeList.add(gradeId);
        }
      }
      Integer[] areaIds = StringUtils.toIntegerArray(org.getAreaIds().substring(1, org.getAreaIds().lastIndexOf(",")),
          ",");
      for (Integer subjectId : subjectSet) {
        if (subjectId == 0) {
          List<Meta> listAllSubjectByPhaseId = MetaUtils.getPhaseSubjectMetaProvider().listAllSubject(org.getId(),
              searchVo.getPhaseId(), areaIds);
          if (!CollectionUtils.isEmpty(listAllSubjectByPhaseId)) {
            for (Meta meta : listAllSubjectByPhaseId) {
              subjectList.add(meta.getId());
            }
          }
          break;
        } else {
          subjectList.add(subjectId);
        }
      }
    }
    metaMap.put("sysRoleIdList", sysRoleIdList);// 角色元数据集合
    searchVo.setSpaceIds(spaceIdList);
    searchVo.setGradeIds(gradeList);
    searchVo.setSubjectIds(subjectList);
    return metaMap;
  }

  /**
   * 
   * @author wangyao
   * @param searchVo
   * @return
   */
  private List<UserSpace> getUserList(SearchVo searchVo) {
    UserSpace currentUserSpace = (UserSpace) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE); // 用户空间
    UserSpace userSpace = new UserSpace();
    userSpace.setOrgId(searchVo.getOrgId());
    userSpace.setEnable(UserSpace.ENABLE);
    userSpace.setUserId(searchVo.getUserId());
    StringBuilder sql = new StringBuilder();
    Map<String, Object> paramMap = new HashMap<String, Object>();
    UserSpace us = userSpaceService.findOne(currentUserSpace.getId());
    if (us.getPhaseId() != null && us.getPhaseId().equals(currentUserSpace.getPhaseId())) {
      userSpace.setPhaseId(currentUserSpace.getPhaseId());
    } else {
      sql.append(" and phaseId in (:phaseIds)");
      paramMap.put("phaseIds", Arrays.asList(new Integer[] { currentUserSpace.getPhaseId(), us.getPhaseId() }));
    }
    if (SysRole.XZ.getId().equals(currentUserSpace.getSysRoleId())) {
      sql.append(" and sysRoleId in (:sysRoleId)");
      paramMap.put("sysRoleId", Arrays.asList(SysRole.FXZ.getId(), SysRole.ZR.getId(), SysRole.XKZZ.getId(),
          SysRole.NJZZ.getId(), SysRole.BKZZ.getId()));
    } else if (SysRole.FXZ.getId().equals(currentUserSpace.getSysRoleId())) {
      sql.append(" and sysRoleId in (:sysRoleId)");
      paramMap.put("sysRoleId",
          Arrays.asList(SysRole.ZR.getId(), SysRole.XKZZ.getId(), SysRole.NJZZ.getId(), SysRole.BKZZ.getId()));
    } else if (SysRole.ZR.getId().equals(currentUserSpace.getSysRoleId())) {
      sql.append(" and sysRoleId in (:sysRoleId)");
      paramMap.put("sysRoleId", Arrays.asList(SysRole.XKZZ.getId(), SysRole.NJZZ.getId(), SysRole.BKZZ.getId()));
    } else if (SysRole.XKZZ.getId().equals(currentUserSpace.getSysRoleId())) {
      sql.append(" and sysRoleId in (:sysRoleId)");
      paramMap.put("sysRoleId", Arrays.asList(SysRole.BKZZ.getId()));
    } else if (SysRole.NJZZ.getId().equals(currentUserSpace.getSysRoleId())) {
      sql.append(" and sysRoleId in (:sysRoleId)");
      paramMap.put("sysRoleId", Arrays.asList(SysRole.BKZZ.getId()));
    }
    userSpace.addCustomCondition(sql.toString(), paramMap);
    userSpace.setSchoolYear((Integer) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SCHOOLYEAR));
    return userSpaceService.findAll(userSpace);
  }

  /**
   * 获得对应管理者详情
   * 
   * @author wangyao
   * @param searchVo
   * @return
   * @throws Exception
   * @see com.tmser.tr.teachingView.service.TeachingViewService#getManagerDetailDataList(com.tmser.tr.teachingview.vo.SearchVo)
   */
  @SuppressWarnings("unchecked")
  @Override
  public List<Map<String, Object>> getManagerDetailDataList(SearchVo searchVo) throws Exception {
    List<UserSpace> userSpaceList = getUserList(searchVo);
    List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
    String cacheKey = createKey(SearchVo.TW_MANAGER_LIST, searchVo);
    ValueWrapper element = teachingViewDataCache.get(cacheKey);
    if (element == null) {
      Map<String, Object> paramMap = this.getUserSubjectAndGrades(userSpaceList, searchVo);
      searchVo.setUserSpaceList(userSpaceList);
      if (!CollectionUtils.isEmpty(userSpaceList)) {
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("userName", userSpaceList.get(0).getUsername());
        List<Integer> sysRoleIdList = (List<Integer>) paramMap.get("sysRoleIdList");
        boolean activityLeader = false;
        boolean schActivityLeader = false;
        for (Integer sysRoleId : sysRoleIdList) {
          activityLeader = activityService.isLeader(sysRoleId);
          if (activityLeader)
            break;
        }
        for (Integer sysRoleId : sysRoleIdList) {
          if (SysRole.XKZZ.getId().equals(sysRoleId) || SysRole.BKZZ.getId().equals(sysRoleId)
              || SysRole.NJZZ.getId().equals(sysRoleId)) {
            schActivityLeader = true;
          }
          if (schActivityLeader)
            break;
        }
        List<Integer> roleIds = JyCollectionUtils.getValues(userSpaceList, "roleId");
        searchVo.setRoleIds(roleIds);
        this.setDateRange(searchVo);
        dataMap.put("haveOrgActivity", activityLeader);// 如果有发起集体备课的权限
        dataMap.put("haveOrgSchActivity", schActivityLeader);// 如果有发起校际教研的权限
        dataMap.putAll(getDataMapByEunm(searchVo, ManagerDetailView.getIdsList()));
        dataList.add(dataMap);
      }
    } else {
      dataList = (List<Map<String, Object>>) element.get();
    }
    return dataList;
  }

  /**
   * 获得教案课件信息
   * 
   * @author wangyao
   * @param searchVo
   * @return
   * @see com.tmser.tr.teachingView.service.TeachingViewService#getManagerLessonDetailDataByType(com.tmser.tr.teachingview.vo.SearchVo)
   */
  @Override
  public Map<String, Object> getManagerLessonDetailDataByType(SearchVo searchVo) {
    Map<String, Object> dataMap = new HashMap<String, Object>();
    List<UserSpace> userSpaceList = getUserList(searchVo);// 用户的所有身份
    searchVo.setUserSpaceList(userSpaceList);
    this.getUserSubjectAndGrades(userSpaceList, searchVo);
    Map<String, Object> paramMap = new HashMap<String, Object>();
    this.setDateRange(searchVo);// 设置时间限制
    StringBuilder sql1 = new StringBuilder();
    // 教案、课件、反思可查阅数
    LessonPlan lesson = new LessonPlan();
    if (!CollectionUtils.isEmpty(searchVo.getGradeIds())) {
      sql1.append(" and gradeId in (:gradeId)");
    }
    if (!CollectionUtils.isEmpty(searchVo.getSubjectIds())) {
      sql1.append(" and subjectId in (:subjectId)");
    }
    sql1.append(" and submitTime >= :startTime and submitTime < :endTime");
    paramMap.put("startTime", searchVo.getStartTime());
    paramMap.put("endTime", searchVo.getEndTime());
    paramMap.put("gradeId", searchVo.getGradeIds());
    paramMap.put("subjectId", searchVo.getSubjectIds());
    lesson.setIsSubmit(true);
    lesson.setPlanType(Integer.valueOf(searchVo.getFlago()));
    lesson.setEnable(1);
    lesson.setOrgId(searchVo.getOrgId());
    lesson.setPhaseId(searchVo.getPhaseId());
    lesson.addCustomCondition(sql1.toString(), paramMap);
    lesson.addGroup("infoId");
    List<LessonPlan> findAll = lessonPlanService.findAll(lesson);
    if (!CollectionUtils.isEmpty(findAll)) {
      List<Integer> lessonId = new ArrayList<Integer>();
      for (LessonPlan lessonPlan : findAll) {
        if (!lessonId.contains(lessonPlan.getInfoId())) {
          lessonId.add(lessonPlan.getInfoId());
        }
      }
      StringBuilder sql = new StringBuilder();
      LessonInfo lessonInfo = new LessonInfo();
      if (!CollectionUtils.isEmpty(lessonId)) {
        sql.append(" and id in (:ids)");
        paramMap.put("ids", lessonId);
      }
      switch (Integer.valueOf(searchVo.getFlago())) {
      case LessonPlan.JIAO_AN:
        sql.append(" and jiaoanSubmitCount > 0");
        break;
      case LessonPlan.KE_JIAN:
        sql.append(" and kejianSubmitCount > 0");
        break;
      case LessonPlan.KE_HOU_FAN_SI:
        sql.append(" and fansiSubmitCount > 0");
        break;
      }
      lessonInfo.addCustomCondition(sql.toString(), paramMap);
      lessonInfo.addOrder("submitTime desc");
      if ("0".equals(searchVo.getFlags()) || searchVo.getFlags() == null) {
        lessonInfo.addPage(searchVo.getPage());
      }
      lessonInfo.getPage().setPageSize(28);
      dataMap.put("chekInfoData", getCheckInfo(searchVo, searchVo.getFlago()));
      dataMap.put("lessonInfoData", lessonInfoService.findByPage(lessonInfo));
    }
    return dataMap;
  }

  /**
   * 获得查阅信息
   * 
   * @author wangyao
   * @param searchVo
   * @param listType
   * @return
   */
  private Map<Integer, CheckInfo> getCheckInfo(SearchVo searchVo, String listType) {
    StringBuilder sql = new StringBuilder();
    Map<String, Object> paramMap = new HashMap<String, Object>();
    CheckInfo model = new CheckInfo();
    model.addCustomCulomn("id,resId,isUpdate");
    model.setPhase(searchVo.getPhaseId());
    model.setUserId(searchVo.getUserId());
    model.setResType(Integer.valueOf(listType));
    List<Integer> spaceIds = searchVo.getSpaceIds();
    if (!CollectionUtils.isEmpty(spaceIds)) {
      sql.append(" and spaceId in (:spaceIds)");
    }
    if (searchVo.getStartTime() != null && searchVo.getEndTime() != null) {
      sql.append(" and createtime >= :startTime and createtime < :endTime");
    }
    paramMap.put("spaceIds", spaceIds);
    paramMap.put("startTime", searchVo.getStartTime());
    paramMap.put("endTime", searchVo.getEndTime());
    model.addCustomCondition(sql.toString(), paramMap);
    model.addOrder("createtime desc");
    List<CheckInfo> cklist = checkInfoService.findAll(model);
    Map<Integer, CheckInfo> checkIds = new HashMap<Integer, CheckInfo>();
    for (CheckInfo c : cklist) {
      checkIds.put(c.getResId(), c);
    }
    return checkIds;
  }

  /**
   * 获得反思信息
   * 
   * @author wangyao
   * @param searchVo
   * @param listType
   * @return
   * @see com.tmser.tr.teachingView.service.TeachingViewService#getManagerLessonFanSi(com.tmser.tr.teachingview.vo.SearchVo,
   *      java.lang.String)
   */
  @Override
  public Map<String, Object> getManagerLessonFanSi(SearchVo searchVo, String listType) {
    Map<String, Object> dataMap = new HashMap<String, Object>();
    List<UserSpace> userSpaceList = getUserList(searchVo);// 用户的所有身份
    searchVo.setUserSpaceList(userSpaceList);
    this.getUserSubjectAndGrades(userSpaceList, searchVo);
    StringBuilder sql1 = new StringBuilder();
    Map<String, Object> paramMap = new HashMap<String, Object>();
    LessonPlan model = new LessonPlan();
    model.setPlanType(Integer.valueOf(listType));
    model.setOrgId(searchVo.getOrgId());
    model.setIsSubmit(true);
    model.setEnable(1);
    model.setPhaseId(searchVo.getPhaseId());
    if (!CollectionUtils.isEmpty(searchVo.getGradeIds())) {
      sql1.append(" and gradeId in (:gradeId)");
    }
    if (!CollectionUtils.isEmpty(searchVo.getSubjectIds())) {
      sql1.append(" and subjectId in (:subjectId)");
    }
    sql1.append(" and submitTime >= :startTime and submitTime < :endTime");
    paramMap.put("gradeId", searchVo.getGradeIds());
    paramMap.put("subjectId", searchVo.getSubjectIds());
    paramMap.put("startTime", searchVo.getStartTime());
    paramMap.put("endTime", searchVo.getEndTime());
    if ("1".equals(searchVo.getFlags())) {
      model.addPage(searchVo.getPage());
    }
    model.getPage().setPageSize(28);
    model.addCustomCondition(sql1.toString(), paramMap);
    model.addOrder("submitTime desc");
    dataMap.put("fansiData", lessonPlanService.findByPage(model));
    dataMap.put("fansiCheckData", getCheckInfo(searchVo, listType));
    return dataMap;
  }

  /**
   * 获得查阅集体备课列表
   * 
   * @author wangyao
   * @param searchVo
   * @return
   * @see com.tmser.tr.teachingView.service.TeachingViewService#getManagerCheckActivityDetailData(com.tmser.tr.teachingview.vo.SearchVo)
   */
  @Override
  public Map<String, Object> getManagerCheckActivityDetailData(SearchVo searchVo) {
    Map<String, Object> map = new HashMap<String, Object>();
    List<UserSpace> userSpaceList = getUserList(searchVo);// 用户的所有身份
    searchVo.setUserSpaceList(userSpaceList);
    this.getUserSubjectAndGrades(userSpaceList, searchVo);
    this.setDateRange(searchVo);// 设置时间限制
    Activity model = new Activity();
    StringBuilder sql = new StringBuilder();
    Map<String, Object> dataMap = new HashMap<String, Object>();
    model.addPage(searchVo.getPage());
    model.getPage().setPageSize(8);
    StringBuilder sqlStr = new StringBuilder();
    for (UserSpace userSpace : userSpaceList) {
      Integer sysRoleId = userSpace.getSysRoleId();
      model.setIsSubmit(true);// 已提交
      if (SysRole.XKZZ.getId().equals(sysRoleId)) { // 学科组长
        sqlStr.append(" subjectIds like :subjectId or ");
        dataMap.put("subjectId", "%," + userSpace.getSubjectId() + ",%");
      } else if (SysRole.NJZZ.getId().equals(sysRoleId)) { // 年级组长
        sqlStr.append("  gradeIds like :gradeId or ");
        dataMap.put("gradeId", "%," + userSpace.getGradeId() + ",%");
      } else if (SysRole.BKZZ.getId().equals(sysRoleId)) { // 备课组长
        dataMap.put("subjectId1", "%," + userSpace.getSubjectId() + ",%");
        dataMap.put("gradeId1", "%," + userSpace.getGradeId() + ",%");
        sqlStr.append(" (gradeIds like :gradeId1 and subjectIds like :subjectId1 ) or ");
      }
    }
    String string = sqlStr.toString();
    if (string.length() > 4) {
      boolean endsWith = string.endsWith(" or ");
      if (endsWith) {
        string = string.substring(0, string.length() - " or ".length());
      }
      sql.append(" and ( " + string + " )");
    }
    dataMap.put("organizeUserId", searchVo.getUserId());
    sql.append(" and createTime >= :startTime and createTime < :endTime");
    dataMap.put("startTime", searchVo.getStartTime());
    dataMap.put("endTime", searchVo.getEndTime());
    model.setOrgId(searchVo.getOrgId());// 机构
    model.setPhaseId(searchVo.getPhaseId());// 学段
    model.setIsSubmit(true);
    model.addCustomCondition(" and organizeUserId != :organizeUserId" + sql, dataMap);
    model.addGroup("id");
    model.addOrder(" createTime desc ");
    PageList<Activity> listPage = activityService.findByPage(model);
    map.put("listPage", listPage);
    map.put("activityData", getCheckInfo(searchVo, String.valueOf(ResTypeConstants.ACTIVITY)));
    return map;
  }

  /**
   * 获得管理者集体备课详情列表
   * 
   * @author wangyao
   * @param searchVo
   * @return
   * @see com.tmser.tr.teachingView.service.TeachingViewService#getManagerActivityDetailData(com.tmser.tr.teachingview.vo.SearchVo)
   */
  @Override
  public Map<String, Object> getManagerActivityDetailData(SearchVo searchVo) {
    boolean isLeader = false;
    Map<String, Object> map = new HashMap<String, Object>();
    Map<String, Object> paramMap = new HashMap<String, Object>();
    List<UserSpace> userSpaceList = getUserList(searchVo);// 用户的所有身份
    this.getUserSubjectAndGrades(userSpaceList, searchVo);
    searchVo.setUserSpaceList(userSpaceList);
    this.setDateRange(searchVo);// 设置时间限制
    for (UserSpace userSpace : userSpaceList) {
      if (activityService.isLeader(userSpace.getSysRoleId())) {
        isLeader = true;
        if (isLeader)
          break;// 如果有发起的权限
      }
    }
    PageList<Activity> listPage = new PageList<Activity>(new ArrayList<Activity>(), new Page());
    // 列表选型
    Activity activity1 = new Activity();
    Activity activity = new Activity();
    // 页码
    if ("0".equals(searchVo.getFlago())) {
      activity1.addPage(searchVo.getPage());
    } else if ("1".equals(searchVo.getFlago())) {
      activity.addPage(searchVo.getPage());
    }
    activity1.getPage().setPageSize(8);
    activity.getPage().setPageSize(8);

    String sql1 = "";
    sql1 = " and createTime >= :startTime and createTime < :endTime";
    paramMap.put("startTime", searchVo.getStartTime());
    paramMap.put("endTime", searchVo.getEndTime());
    // 加载发起活动列表
    activity1.setOrganizeUserId(searchVo.getUserId());
    activity1.setStatus(1);
    activity1.setPhaseId(searchVo.getPhaseId());
    activity1.setOrganizeUserId(searchVo.getUserId());
    activity1.addOrder("releaseTime desc");
    activity1.addCustomCondition(sql1, paramMap);
    PageList<Activity> listPage1 = activityService.findByPage(activity1);

    String sql = "";
    sql += " and editType <> :editType";
    List<Integer> resourceSpaceId = new ArrayList<Integer>();
    paramMap.put("editType", ActivityTracks.ZHUBEI);
    ActivityTracks tracks = new ActivityTracks();
    tracks.setUserId(searchVo.getUserId());
    tracks.setSchoolYear(schoolYearService.getCurrentSchoolYear());
    if (!CollectionUtils.isEmpty(searchVo.getSpaceIds())) {
      sql += " and spaceId in (:spaceIds)";
      paramMap.put("spaceIds", searchVo.getSpaceIds());
    }
    tracks.addCustomCondition(sql + " and crtDttm >= :startTime and crtDttm < :endTime ", paramMap);
    paramMap.put("startTime", searchVo.getStartTime());
    paramMap.put("endTime", searchVo.getEndTime());
    tracks.addOrder(" crtDttm desc");
    List<ActivityTracks> tracksList = activityTracksService.findAll(tracks);
    for (ActivityTracks activityTracks : tracksList) {
      if (activityTracks.getActivityId() != null && !resourceSpaceId.contains(activityTracks.getActivityId())) {
        resourceSpaceId.add(activityTracks.getActivityId());
      }
    }
    Map<String, Object> paramMap1 = new HashMap<String, Object>();
    String sql2 = " and crtDttm >= :startTime and crtDttm < :endTime";
    paramMap1.put("startTime", searchVo.getStartTime());
    paramMap1.put("endTime", searchVo.getEndTime());
    Discuss discuss = new Discuss();
    discuss.setTypeId(ResTypeConstants.ACTIVITY);
    discuss.setCrtId(searchVo.getUserId());
    if (!CollectionUtils.isEmpty(searchVo.getSpaceIds())) {
      sql2 += " and spaceId in (:spaceIds)";
      paramMap1.put("spaceIds", searchVo.getSpaceIds());
    }
    discuss.addCustomCondition(sql2, paramMap1);
    discuss.addOrder(" crtDttm desc");
    List<Discuss> findAllDiscuss = discussService.findAll(discuss);
    for (Discuss discusses : findAllDiscuss) {
      if (discusses.getActivityId() != null && !resourceSpaceId.contains(discusses.getActivityId())) {
        resourceSpaceId.add(discusses.getActivityId());
      }
    }
    if (!CollectionUtils.isEmpty(resourceSpaceId)) {
      activity.buildCondition(" and id in (:activityId)").put("activityId", resourceSpaceId);
      listPage = activityService.findByPage(activity);
    }
    map.put("launchData", listPage1);
    map.put("partInData", listPage);
    map.put("isLeader", isLeader);
    return map;
  }

  /**
   * 获得计划总结可查阅列表
   * 
   * @author wangyao
   * @param searchVo
   * @return
   * @see com.tmser.tr.teachingView.service.TeachingViewService#getManagerCheckPlanSummaryDetailData(com.tmser.tr.teachingview.vo.SearchVo)
   */
  @Override
  public Map<String, Object> getManagerCheckPlanSummaryDetailData(SearchVo searchVo) {
    Map<String, Object> map = new HashMap<String, Object>();
    List<UserSpace> userSpaceList = getUserList(searchVo);// 用户的所有身份
    searchVo.setUserSpaceList(userSpaceList);
    this.setDateRange(searchVo);// 设置时间限制
    this.getUserSubjectAndGrades(userSpaceList, searchVo);

    List<PlainSummary> planList = new ArrayList<PlainSummary>();
    this.getPlanSummarySql(userSpaceList, planList, searchVo, 1);
    List<Integer> planIds = JyCollectionUtils.getValues(planList, "id");
    Page page1 = new Page();
    if ("1".equals(searchVo.getFlago())) {
      page1 = searchVo.getPage();
    }
    page1.setPageSize(28);
    PlainSummary plan = new PlainSummary();
    plan.addPage(page1);
    PageList<PlainSummary> planPage = new PageList<PlainSummary>(null, page1);
    if (!CollectionUtils.isEmpty(planIds)) {
      plan.buildCondition(" and id in (:ids)").put("ids", planIds);
      planPage = plainSummaryService.findByPage(plan);
    }
    List<PlainSummary> summaryList = new ArrayList<PlainSummary>();
    this.getPlanSummarySql(userSpaceList, summaryList, searchVo, 2);
    List<Integer> summaryIds = JyCollectionUtils.getValues(summaryList, "id");
    Page page2 = new Page();
    if ("2".equals(searchVo.getFlago())) {
      page2 = searchVo.getPage();
    }
    page2.setPageSize(28);
    plan.addPage(page2);
    PageList<PlainSummary> summaryPage = new PageList<PlainSummary>(null, page2);
    if (!CollectionUtils.isEmpty(summaryIds)) {
      plan.buildCondition(" and id in (:ids)").put("ids", summaryIds);
      summaryPage = plainSummaryService.findByPage(plan);
    }
    // plan.getPage().setPageSize(28);
    // paramMap.put("category", Arrays.asList(2));
    // page2.setTotalCount(summaryList.size());
    // PageList<PlainSummary> summaryPage = new
    // PageList<PlainSummary>(summaryList, page2);
    map.put("chekInfoPlanData", getCheckInfo(searchVo, String.valueOf(ResTypeConstants.TPPLAIN_SUMMARY_PLIAN)));
    map.put("chekInfoSummaryData", getCheckInfo(searchVo, String.valueOf(ResTypeConstants.TPPLAIN_SUMMARY_SUMMARY)));
    map.put("planPage", planPage);
    map.put("summaryPage", summaryPage);
    return map;
  }

  /**
   * @param userSpaceList
   * @param planSummaryList
   * @param searchVo
   * @param type
   */
  private void getPlanSummarySql(List<UserSpace> userSpaceList, List<PlainSummary> planSummaryList, SearchVo searchVo,
      int type) {
    for (UserSpace userSpace : userSpaceList) {
      Map<String, Object> paramMap = new HashMap<String, Object>();
      Integer sysRoleId = userSpace.getSysRoleId();
      StringBuilder sql = new StringBuilder();
      PlainSummary plan = new PlainSummary();
      plan.addAlias("p");
      plan.addCustomCulomn(" DISTINCT p.id ");
      if (sysRoleId != null && !SysRole.TEACHER.getId().equals(sysRoleId)) {
        if (SysRole.XKZZ.getId().equals(sysRoleId)) { // 学科组长
          plan.addJoin(
              JOINTYPE.INNER,
              "(select id,roleId,userId,gradeId,subjectId from UserSpace where enable = :enable and orgId = :orgId and schoolYear = :schoolYear"
                  + " and phaseId = :phaseId and subjectId = :subjectId and sysRoleId = :sysRoleId and userId <> :userId) s")
              .on("p.gradeId = s.grade_id and p.userId=s.user_id and p.subjectId = s.subject_id");
        } else if (SysRole.NJZZ.getId().equals(sysRoleId)) { // 年级组长
          plan.addJoin(
              JOINTYPE.INNER,
              "(select id,roleId,userId,gradeId,subjectId from UserSpace where enable = :enable and orgId = :orgId and schoolYear = :schoolYear"
                  + " and phaseId = :phaseId and gradeId = :gradeId and sysRoleId = :sysRoleId and userId <> :userId) s")
              .on("p.gradeId = s.grade_id and p.userId=s.user_id and p.subjectId = s.subject_id");
        } else if (SysRole.BKZZ.getId().equals(sysRoleId)) { // 备课组长
          plan.addJoin(
              JOINTYPE.INNER,
              "(select id,roleId,userId,gradeId,subjectId from UserSpace where enable = :enable and orgId = :orgId and userId <> :userId and schoolYear = :schoolYear"
                  + " and phaseId = :phaseId and gradeId = :gradeId and subjectId = :subjectId and sysRoleId = :sysRoleId) s")
              .on("p.gradeId = s.grade_id and p.userId=s.user_id and p.subjectId = s.subject_id");
        } else if (SysRole.ZR.getId().equals(sysRoleId)) { // 主任
          plan.addJoin(
              JOINTYPE.INNER,
              "(select id,roleId,userId,gradeId,subjectId from UserSpace where enable = :enable and orgId = :orgId and userId <> :userId and schoolYear = :schoolYear"
                  + " and phaseId = :phaseId and sysRoleId = :sysRoleId) s").on(
              "p.gradeId = s.grade_id and p.userId=s.user_id and p.subjectId = s.subject_id");
        }
        paramMap.put("schoolYear", userSpace.getSchoolYear());
        paramMap.put("subjectId", userSpace.getSubjectId());
        paramMap.put("gradeId", userSpace.getGradeId());
        paramMap.put("phaseId", searchVo.getPhaseId());
        paramMap.put("orgId", searchVo.getOrgId());
        paramMap.put("phaseId", searchVo.getPhaseId());
        paramMap.put("enable", 1);
        paramMap.put("userId", searchVo.getUserId());
        paramMap.put("sysRoleId", SysRole.TEACHER.getId());

        paramMap.put("startTime", searchVo.getStartTime());
        paramMap.put("endTime", searchVo.getEndTime());
        paramMap.put("category", Arrays.asList(type));
        sql.append(" and p.submitTime >= :startTime and p.submitTime < :endTime and p.category in (:category)");
        plan.addCustomCondition(sql.toString(), paramMap);
        plan.setIsSubmit(1);
        plan.setOrgId(searchVo.getOrgId());
        plan.setPhaseId(searchVo.getPhaseId());
        plan.addGroup("p.id");
        plan.addOrder("p.submitTime desc");
        planSummaryList.addAll(plainSummaryService.findAll(plan));
      }
    }
  }

  /**
   * 获得计划总结撰写数列表
   * 
   * @author wangyao
   * @param plan
   * @param searchVo
   * @return
   * @see com.tmser.tr.teachingView.service.TeachingViewService#getManagerPersonPlanWriteDetailData(com.tmser.tr.plainsummary.bo.PlainSummary,
   *      com.tmser.tr.teachingview.vo.SearchVo)
   */
  @Override
  public Map<String, Object> getManagerPersonPlanWriteDetailData(PlainSummary plan, SearchVo searchVo) {
    Map<String, Object> map = new HashMap<String, Object>();
    List<UserSpace> userSpaceList = getUserList(searchVo);// 用户的所有身份
    searchVo.setUserSpaceList(userSpaceList);
    List<Integer> roleIds = JyCollectionUtils.getValues(userSpaceList, "roleId");
    this.setDateRange(searchVo);// 设置时间限制
    String sql = "";
    plan.setUserId(searchVo.getUserId());
    plan.setOrgId(searchVo.getOrgId());
    plan.setPhaseId(searchVo.getPhaseId());
    Map<String, Object> paramMap = new HashMap<String, Object>();
    Integer isShare = plan.getIsShare();
    if (isShare != null && isShare > 0) {
      sql += " and shareTime >= :startTime and shareTime < :endTime";
    } else {
      sql += " and crtDttm >= :startTime and crtDttm < :endTime";
    }
    sql += " and category in (:category)";
    if (String.valueOf(ResTypeConstants.TPPLAIN_SUMMARY_PLIAN).equals(searchVo.getFlags())) {
      paramMap.put("category", Arrays.asList(1, 3));// 计划
    } else {
      paramMap.put("category", Arrays.asList(2, 4));// 总结
    }
    if (!CollectionUtils.isEmpty(roleIds)) {
      sql += " and userRoleId in (:roleIds)";
      paramMap.put("roleIds", roleIds);
    }
    paramMap.put("startTime", searchVo.getStartTime());
    paramMap.put("endTime", searchVo.getEndTime());
    plan.addCustomCondition(sql, paramMap);
    plan.addOrder("crtDttm desc");
    List<PlainSummary> findAll = plainSummaryService.findAll(plan);
    map.put("listPage", findAll);
    map.put("count", findAll.size());
    return map;
  }

  /**
   * 获得听课记录节数列表
   * 
   * @author wangyao
   * @param searchVo
   * @return
   * @see com.tmser.tr.teachingView.service.TeachingViewService#getManagerLectureDetailData(com.tmser.tr.teachingview.vo.SearchVo)
   */
  @Override
  public Map<String, Object> getManagerLectureDetailData(SearchVo searchVo) {
    Map<String, Object> map = new HashMap<String, Object>();
    List<UserSpace> userSpaceList = getUserList(searchVo);// 用户的所有身份
    searchVo.setUserSpaceList(userSpaceList);
    this.setDateRange(searchVo);// 设置时间限制
    LectureRecords model = new LectureRecords();
    model.setLecturepeopleId(searchVo.getUserId());// 听课人ID
    model.setPhaseId(searchVo.getPhaseId());
    model.setIsDelete(false);// 不删除
    model.setIsEpub(1);// 发布
    model.addPage(searchVo.getPage());
    model.getPage().setPageSize(8);// 设置每页的展示数
    Map<String, Object> paramMap = new HashMap<String, Object>();
    String sql = " and crtDttm >= :startTime and crtDttm < :endTime";
    paramMap.put("startTime", searchVo.getStartTime());
    paramMap.put("endTime", searchVo.getEndTime());
    model.addCustomCondition(sql, paramMap);
    model.addOrder("epubTime desc");// 按照发布时间降序
    PageList<LectureRecords> plList = lectureRecordsService.findByPage(model);// 查询当前页的评论
    map.put("listPage", plList);
    return map;
  }

  /**
   * 获得教学文章列表
   * 
   * @author wangyao
   * @param thesis
   * @param searchVo
   * @return
   * @see com.tmser.tr.teachingView.service.TeachingViewService#getManagerThesisDetailData(com.tmser.tr.thesis.bo.Thesis,
   *      com.tmser.tr.teachingview.vo.SearchVo)
   */
  @Override
  public Map<String, Object> getManagerThesisDetailData(Thesis thesis, SearchVo searchVo) {
    Map<String, Object> map = new HashMap<String, Object>();
    List<UserSpace> userSpaceList = getUserList(searchVo);// 用户的所有身份
    searchVo.setUserSpaceList(userSpaceList);
    this.setDateRange(searchVo);// 设置时间限制
    thesis.setUserId(searchVo.getUserId());// 用户Id
    thesis.setOrgId(searchVo.getOrgId());
    thesis.setPhaseId(searchVo.getPhaseId());
    thesis.setEnable(1);// 有效
    thesis.addOrder("lastupDttm desc");
    thesis.getPage().setPageSize(35);
    Map<String, Object> paramMap = new HashMap<String, Object>();
    Integer isShare = thesis.getIsShare();
    String sql = "";
    if (isShare != null && isShare > 0) {
      sql += " and shareTime >= :startTime and shareTime < :endTime";
    } else {
      sql += " and crtDttm >= :startTime and crtDttm < :endTime";
    }
    paramMap.put("startTime", searchVo.getStartTime());
    paramMap.put("endTime", searchVo.getEndTime());
    thesis.addCustomCondition(sql, paramMap);
    PageList<Thesis> listPage = thesisService.findByPage(thesis);
    map.put("listPage", listPage);
    return map;
  }

  /**
   * 获得同伴互助留言列表
   * 
   * @author wangyao
   * @param searchVo
   * @return
   * @see com.tmser.tr.teachingView.service.TeachingViewService#getManagerCompanionDetailData(com.tmser.tr.teachingview.vo.SearchVo)
   */
  @Override
  public Map<String, Object> getManagerCompanionDetailData(SearchVo searchVo) {
    Map<String, Object> map = new HashMap<String, Object>();
    List<UserSpace> userSpaceList = getUserList(searchVo);// 用户的所有身份
    searchVo.setUserSpaceList(userSpaceList);
    this.setDateRange(searchVo);// 设置时间
    JyCompanionMessage message = new JyCompanionMessage();
    Map<String, Object> paramMap = new HashMap<String, Object>();
    paramMap.put("startTime", searchVo.getStartTime());
    paramMap.put("endTime", searchVo.getEndTime());
    paramMap.put("userId", searchVo.getUserId());
    String sql = "";
    if (searchVo.getStartTime() != null && searchVo.getEndTime() != null && searchVo.getUserId() != null) {
      sql += " and senderTime >= :startTime and senderTime < :endTime and userIdSender = :userId";
    }
    message.addCustomCondition(sql, paramMap);
    message.addPage(searchVo.getPage());
    message.getPage().setPageSize(5);
    ;
    message.addOrder("senderTime desc");
    PageList<JyCompanionMessage> listPage = jyCompanionMessageService.findByPage(message);
    map.put("listPage", listPage);
    return map;
  }

  /**
   * 获得发起校际教研列表
   * 
   * @author wangyao
   * @param searchVo
   * @return
   * @see com.tmser.tr.teachingView.service.TeachingViewService#getManagerSchActivityDetailData(com.tmser.tr.teachingview.vo.SearchVo)
   */
  @SuppressWarnings("unchecked")
  @Override
  public Map<String, Object> getManagerSchActivityDetailData(SearchVo searchVo) {
    Map<String, Object> dataMap = new HashMap<String, Object>();
    Integer schoolYear = (Integer) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SCHOOLYEAR); // 学年
    Map<String, Object> circleMap = getCircleMap(searchVo.getOrgId(), schoolYear);
    boolean isLeader = false;
    List<UserSpace> userSpaceList = getUserList(searchVo);// 用户的所有身份
    searchVo.setUserSpaceList(userSpaceList);
    this.setDateRange(searchVo);// 设置时间
    for (UserSpace userSpace : userSpaceList) {
      Integer sysRoleId = userSpace.getSysRoleId();
      if (SysRole.XKZZ.getId().equals(sysRoleId) || SysRole.BKZZ.getId().equals(sysRoleId)
          || SysRole.NJZZ.getId().equals(sysRoleId)) {
        isLeader = true;
      }
      if (isLeader)
        break;// 如果有发起的权限
    }
    SchoolActivity sa = new SchoolActivity();
    PageList<SchoolActivity> listPage = new PageList<SchoolActivity>(null, new Page());
    if (isLeader) {
      if ("0".equals(searchVo.getFlago())) {
        sa.addPage(searchVo.getPage());
      }
      sa.getPage().setPageSize(8);
      ;
      sa.setStatus(1);// 正式发文
      sa.setSchoolYear(schoolYear);// 学年
      String sql = " and createTime >= :startTime and createTime < :endTime";
      Map<String, Object> paramMap = new HashMap<String, Object>();
      paramMap.put("startTime", searchVo.getStartTime());
      paramMap.put("endTime", searchVo.getEndTime());
      sa.setOrganizeUserId(searchVo.getUserId());
      sa.setPhaseId(searchVo.getPhaseId());
      List<Integer> circleIds = (List<Integer>) circleMap.get("circleIds");
      if (!(circleIds != null && circleIds.size() > 0)) {
        circleIds = new ArrayList<Integer>();
        circleIds.add(0);
      }
      paramMap.put("circleIds", circleIds);
      sql += " and schoolTeachCircleId in (:circleIds) ";
      sa.addCustomCondition(sql, paramMap);
      sa.addOrder("createTime desc");
      listPage = schoolActivityService.findByPage(sa);
    }
    getTuiChu(listPage, circleMap);
    dataMap.put("data", listPage);
    dataMap.put("isPart", isLeader);
    return dataMap;
  }

  private void getTuiChu(PageList<SchoolActivity> listPage, Map<String, Object> circleMap) {
    boolean isTuiChu = false;
    if (listPage.getDatalist() != null && listPage.getDatalist().size() > 0) {
      for (SchoolActivity saTemp : listPage.getDatalist()) {
        @SuppressWarnings("unchecked")
        List<Integer> listTwo = (List<Integer>) circleMap.get("tuiCircleIds");
        if (listTwo != null && listTwo.size() > 0) {
          for (Integer stcId : listTwo) {
            if (stcId != null && stcId.equals(saTemp.getSchoolTeachCircleId())) {
              isTuiChu = true;
              break;
            }
          }
          if (!isTuiChu) {// 没有退出，才进行封装数据
            SchoolTeachCircleOrg stco = new SchoolTeachCircleOrg();
            stco.setStcId(saTemp.getSchoolTeachCircleId());
            stco.addCustomCondition(" and state != " + SchoolTeachCircleOrg.YI_JU_JUE, new HashMap<String, Object>());
            stco.addOrder("sort asc");
            saTemp.setStcoList(schoolTeachCircleOrgService.findAll(stco));
          }
          saTemp.setIsTuiChu(isTuiChu);
          isTuiChu = false;
        } else {
          saTemp.setIsTuiChu(isTuiChu);
          SchoolTeachCircleOrg stco = new SchoolTeachCircleOrg();
          stco.setStcId(saTemp.getSchoolTeachCircleId());
          stco.addCustomCondition(" and state != " + SchoolTeachCircleOrg.YI_JU_JUE, new HashMap<String, Object>());
          stco.addOrder("sort asc");
          saTemp.setStcoList(schoolTeachCircleOrgService.findAll(stco));
        }
      }
    }
  }

  /**
   * 获得参与校际教研列表
   * 
   * @author wangyao
   * @param searchVo
   * @return
   * @see com.tmser.tr.teachingView.service.TeachingViewService#getManagerPartSchActivityDetailData(com.tmser.tr.teachingview.vo.SearchVo)
   */
  @Override
  public PageList<SchoolActivity> getManagerPartSchActivityDetailData(SearchVo searchVo) {
    List<UserSpace> userSpaceList = this.getUserList(searchVo);// 用户的所有身份
    this.setDateRange(searchVo);// 设置时间
    List<Integer> spaceIds = JyCollectionUtils.getValues(userSpaceList, "id");
    String sql = " and editType <> :editType and crtDttm >= :startTime and crtDttm < :endTime";
    Map<String, Object> paramMap = new HashMap<String, Object>();
    paramMap.put("startTime", searchVo.getStartTime());
    paramMap.put("endTime", searchVo.getEndTime());
    List<Integer> resourceSpaceId = new ArrayList<Integer>();
    paramMap.put("editType", SchoolActivityTracks.ZHUBEI);
    SchoolActivityTracks tracks = new SchoolActivityTracks();
    tracks.setUserId(searchVo.getUserId());
    tracks.setSchoolYear(schoolYearService.getCurrentSchoolYear());
    if (!CollectionUtils.isEmpty(spaceIds)) {
      sql += " and spaceId in (:spaceIds)";
      paramMap.put("spaceIds", spaceIds);
    }
    tracks.addOrder(" crtDttm desc");
    tracks.addCustomCondition(sql, paramMap);
    List<SchoolActivityTracks> tracksList = schoolActivityTracksService.findAll(tracks);
    for (SchoolActivityTracks activityTracks : tracksList) {
      if (activityTracks.getActivityId() != null && !resourceSpaceId.contains(activityTracks.getActivityId())) {
        resourceSpaceId.add(activityTracks.getActivityId());
      }
    }
    Discuss discuss = new Discuss();
    String sql1 = " and crtDttm >= :startTime and crtDttm < :endTime";
    discuss.setCrtId(searchVo.getUserId());
    if (!CollectionUtils.isEmpty(spaceIds)) {
      sql1 += " and spaceId in (:spaceIds)";
      paramMap.put("spaceIds", spaceIds);
    }
    discuss.setTypeId(ResTypeConstants.SCHOOLTEACH);
    discuss.addOrder(" crtDttm desc");
    discuss.addCustomCondition(sql1, paramMap);
    List<Discuss> findAllDiscuss = discussService.findAll(discuss);
    for (Discuss discusses : findAllDiscuss) {
      if (discusses.getActivityId() != null && !resourceSpaceId.contains(discusses.getActivityId())) {
        resourceSpaceId.add(discusses.getActivityId());
      }
    }
    SchoolActivity sa = new SchoolActivity();
    if ("1".equals(searchVo.getFlago())) {
      sa.addPage(searchVo.getPage());
    }
    sa.getPage().setPageSize(8);
    ;
    PageList<SchoolActivity> listPage = new PageList<SchoolActivity>(null, new Page());
    if (!CollectionUtils.isEmpty(resourceSpaceId)) {
      sa.buildCondition(" and id in (:activityId)").put("activityId", resourceSpaceId);
      listPage = schoolActivityService.findByPage(sa);
    }
    Integer schoolYear = (Integer) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SCHOOLYEAR); // 学年
    Map<String, Object> circleMap = getCircleMap(searchVo.getOrgId(), schoolYear);
    getTuiChu(listPage, circleMap);
    return listPage;
  }

  /**
   * 获得的当前用户所在学校参与的教研圈，分别获得所参与教研圈并且状态是通过、恢复 还有就是退出的
   * 
   * @author wangyao
   * @param orgId
   * @param schoolYear
   * @return
   */
  public Map<String, Object> getCircleMap(Integer orgId, Integer schoolYear) {
    Map<String, Object> returnMap = new HashMap<String, Object>();
    // 查询当前教师所在机构参与的当前学年的教研圈，并且状态分别为（同意：2 恢复：5 和 退出：4）,其中退出只能查看退出之前的信息
    SchoolTeachCircleOrg stco = new SchoolTeachCircleOrg();
    stco.setSchoolYear(schoolYear);
    stco.setOrgId(orgId);
    List<Integer> states = new ArrayList<Integer>();
    states.add(SchoolTeachCircleOrg.YI_TONG_YI);
    states.add(SchoolTeachCircleOrg.YI_HUI_FU);
    Map<String, Object> map = new HashMap<String, Object>();
    map.put("states", states);
    stco.addCustomCondition("and state in (:states) ", map);
    stco.addGroup("stcId");
    List<SchoolTeachCircleOrg> listOne = schoolTeachCircleOrgService.findAll(stco);
    states = new ArrayList<Integer>();
    states.add(SchoolTeachCircleOrg.YI_TUI_CHU);
    map.put("states", states);
    List<SchoolTeachCircleOrg> listTwo = schoolTeachCircleOrgService.findAll(stco);
    // 教研圈Ids
    List<Integer> circleIds = new ArrayList<Integer>();
    List<Integer> tuiCircleIds = new ArrayList<Integer>();
    for (SchoolTeachCircleOrg stco1 : listOne) {
      circleIds.add(stco1.getStcId());
    }
    for (SchoolTeachCircleOrg stco2 : listTwo) {
      circleIds.add(stco2.getStcId());
      tuiCircleIds.add(stco2.getStcId());
    }
    returnMap.put("circleIds", circleIds);
    returnMap.put("tuiCircleIds", tuiCircleIds);
    return returnMap;
  }

  /**
   * 获得同伴互助详情
   * 
   * @author wangyao
   * @param searchVo
   * @param userIdReceiver
   * @return
   * @see com.tmser.tr.teachingView.service.TeachingViewService#getViewCompanionMessage(com.tmser.tr.teachingview.vo.SearchVo,
   *      java.lang.Integer)
   */
  @Override
  public Map<String, Object> getViewCompanionMessage(SearchVo searchVo, Integer userIdSender) {
    Map<String, Object> dataMap = new HashMap<String, Object>();
    List<UserSpace> userSpaceList = getUserList(searchVo);// 用户的所有身份
    searchVo.setUserSpaceList(userSpaceList);
    getUserDetail(userSpaceList, searchVo);
    this.setDateRange(searchVo);// 设置时间
    JyCompanionMessage message = new JyCompanionMessage();
    Map<String, Object> paramMap = new HashMap<String, Object>();
    String sql = "and senderTime >= :startTime and senderTime < :endTime";
    paramMap.put("startTime", searchVo.getStartTime());
    paramMap.put("endTime", searchVo.getEndTime());
    if (userIdSender != null && searchVo.getUserId() != null) {
      sql += " and ((userIdSender=:currentUserId and userIdReceiver=:companionUserId) or (userIdSender=:companionUserId and userIdReceiver=:currentUserId))";
      paramMap.put("currentUserId", searchVo.getUserId());
      paramMap.put("companionUserId", userIdSender);
    }
    message.addCustomCondition(sql, paramMap);
    message.addOrder("senderTime desc");
    message.addPage(searchVo.getPage());
    ;
    PageList<JyCompanionMessage> findByPage = jyCompanionMessageService.findByPage(message);
    dataMap.put("messageData", findByPage);
    return dataMap;
  }

  private void getUserDetail(List<UserSpace> userSpaceList, SearchVo searchVo) {
    List<Integer> gradeIds = new ArrayList<Integer>();
    List<Integer> subjectIds = new ArrayList<Integer>();
    String bookName = "";
    for (UserSpace userSpace : userSpaceList) {
      Integer sysRoleId = userSpace.getSysRoleId();
      if (!StringUtils.isEmpty(userSpace.getBookId())) {
        Book book = bookService.findOne(userSpace.getBookId());
        String formatName = book.getFormatName();
        if (formatName != null && !bookName.contains(formatName)) {
          bookName += formatName + "、";
        }
      }
      if (SysRole.TEACHER.getId().equals(sysRoleId)) {
        if (!gradeIds.contains(userSpace.getGradeId())) {
          gradeIds.add(userSpace.getGradeId());
        }
        if (!subjectIds.contains(userSpace.getSubjectId())) {
          subjectIds.add(userSpace.getSubjectId());
        }
      }
    }
    if (!StringUtils.isEmpty(bookName)) {
      searchVo.setFlags(bookName.substring(0, bookName.length() - 1));
    }
    searchVo.setGradeIds(gradeIds);
    searchVo.setSubjectIds(subjectIds);
  }

  /**
   * 获得评论详情
   * 
   * @author wangyao
   * @param info
   * @param m
   * @see com.tmser.tr.teachingView.service.TeachingViewService#findAllCommentReply(com.tmser.tr.comment.bo.CommentInfo,
   *      org.springframework.ui.Model)
   */
  @Override
  public void findAllCommentReply(CommentInfo info, Model m) {
    CommentInfo model = new CommentInfo();// 评论实体
    model.pageSize(5);// 设置每页的展示数
    model.setType(CommentInfo.PTPL);// 限制评论类型
    model.setIsDelete(false);// 删除状态
    model.setIsHidden(false);// 隐藏状态
    model.setResType(info.getResType());
    model.setAuthorId(info.getAuthorId());
    model.setResId(info.getResId());

    model.addOrder("crtDttm desc");// 按照创建时间降序,如果时间相同则按照用户ID排序
    model.currentPage(info.getPage().getCurrentPage());// 设置这是第几页

    PageList<CommentInfo> plList = commentInfoService.findByPage(model);// 查询当前页的评论

    List<CommentInfo> dataList = plList.getDatalist();// 得到当前页的评论

    Map<Integer, List<CommentInfo>> huifuMap = new HashMap<Integer, List<CommentInfo>>();// 存放评论的map
    for (CommentInfo commentInfo : dataList) {
      CommentInfo huifuCom = new CommentInfo();
      huifuCom.setOpinionId(commentInfo.getId());// 查询当前评论回复
      huifuCom.setIsDelete(false);// 删除状态
      huifuCom.setIsHidden(false);// 隐藏状态
      huifuCom.addOrder("crtDttm desc");// 按照创建时间降序,如果时间相同则按照用户ID排序

      List<CommentInfo> huifuList = commentInfoService.findAll(huifuCom);// 当前评论回复的集合
      huifuMap.put(commentInfo.getId(), huifuList);
    }

    m.addAttribute("data", plList);// 按照分页进行查询
    m.addAttribute("huifuMap", huifuMap);// 回复map的集合
    m.addAttribute("model", info);
    m.addAttribute("containsInput", "true".equalsIgnoreCase(info.getFlags()) || "1".equals(info.getFlags()) ? "1" : "0");
    m.addAttribute("titleShow", (info.getTitleShow() != null && info.getTitleShow() == true) ? "1" : "0");
  }

  @Override
  public Map<String, Object> findAllSubmitThesis(SearchVo searchVo) {
    Map<String, Object> map = new HashMap<String, Object>();
    List<UserSpace> userSpaceList = getUserList(searchVo);// 用户的所有身份
    searchVo.setUserSpaceList(userSpaceList);
    this.setDateRange(searchVo);// 设置时间限制
    this.getUserSubjectAndGrades(userSpaceList, searchVo);

    List<Thesis> thesisList = new ArrayList<Thesis>();
    List<Thesis> thesisLists = new ArrayList<Thesis>();
    this.getThesisData(userSpaceList, thesisList, searchVo);
    map.put("chekInfoThesisData", getCheckInfo(searchVo, String.valueOf(ResTypeConstants.JIAOXUELUNWEN)));
    List<Integer> thesisIds = new ArrayList<Integer>();
    for (Thesis thesis : thesisList) {// 多个身份id去重
      if (thesis != null && thesis.getId() != null) {
        if (!thesisIds.contains(thesis.getId())) {
          thesisIds.add(thesis.getId());
          thesisLists.add(thesis);
        }
      }
    }
    map.put("thesisList", thesisLists);
    return map;
  }

  @Override
  public Map<String, Object> findAllSubmitLecture(SearchVo searchVo) {
    Map<String, Object> map = new HashMap<String, Object>();
    List<UserSpace> userSpaceList = getUserList(searchVo);// 用户的所有身份
    searchVo.setUserSpaceList(userSpaceList);
    this.setDateRange(searchVo);// 设置时间限制
    this.getUserSubjectAndGrades(userSpaceList, searchVo);

    List<LectureRecords> lectureList = new ArrayList<LectureRecords>();
    this.getLectureSubmitData(userSpaceList, lectureList, searchVo);
    map.put("chekInfoLectureData", getCheckInfo(searchVo, String.valueOf(ResTypeConstants.LECTURE)));
    searchVo.getPage().setPageSize(15);
    PageList<LectureRecords> listPage = new PageList<LectureRecords>(null, searchVo.getPage());
    LectureRecords lecture = new LectureRecords();
    lecture.addPage(searchVo.getPage());
    List<Integer> ids = JyCollectionUtils.getValues(lectureList, "id");
    if (!CollectionUtils.isEmpty(ids)) {
      lecture.buildCondition(" and id in (:ids)").put("ids", ids);
      listPage = lectureRecordsService.findByPage(lecture);
    }
    map.put("lectureList", listPage);
    return map;
  }

  private void getThesisData(List<UserSpace> userSpaceList, List<Thesis> thesisList, SearchVo searchVo) {
    for (UserSpace userSpace : userSpaceList) {
      Map<String, Object> paramMap = new HashMap<String, Object>();
      Integer sysRoleId = userSpace.getSysRoleId();
      StringBuilder sql = new StringBuilder();
      Thesis plan = new Thesis();
      plan.addAlias("t");
      plan.addCustomCulomn(" DISTINCT t.* ");
      if (sysRoleId != null && !SysRole.TEACHER.getId().equals(sysRoleId)) {
        if (SysRole.XKZZ.getId().equals(sysRoleId)) { // 学科组长
          plan.addJoin(
              JOINTYPE.INNER,
              "(select id,roleId,userId,gradeId,subjectId from UserSpace where enable = :enable and orgId = :orgId and schoolYear = :schoolYear"
                  + " and phaseId = :phaseId and subjectId = :subjectId and sysRoleId = :sysRoleId) s").on(
              "t.userId=s.user_id");
        } else if (SysRole.NJZZ.getId().equals(sysRoleId)) { // 年级组长
          plan.addJoin(
              JOINTYPE.INNER,
              "(select id,roleId,userId,gradeId,subjectId from UserSpace where enable = :enable and orgId = :orgId and schoolYear = :schoolYear"
                  + " and phaseId = :phaseId and gradeId = :gradeId and sysRoleId = :sysRoleId) s").on(
              "t.userId=s.user_id");
        } else if (SysRole.BKZZ.getId().equals(sysRoleId)) { // 备课组长
          plan.addJoin(
              JOINTYPE.INNER,
              "(select id,roleId,userId,gradeId,subjectId from UserSpace where enable = :enable and orgId = :orgId and schoolYear = :schoolYear"
                  + " and phaseId = :phaseId and gradeId = :gradeId and subjectId = :subjectId and sysRoleId = :sysRoleId) s")
              .on("t.userId=s.user_id");
        } else if (SysRole.ZR.getId().equals(sysRoleId)) { // 主任
          plan.addJoin(
              JOINTYPE.INNER,
              "(select id,roleId,userId,gradeId,subjectId from UserSpace where enable = :enable and orgId = :orgId and schoolYear = :schoolYear"
                  + " and phaseId = :phaseId and sysRoleId in (:sysRoleIds)) s").on("t.userId=s.user_id");
        }
        paramMap.put("schoolYear", userSpace.getSchoolYear());
        paramMap.put("subjectId", userSpace.getSubjectId());
        paramMap.put("gradeId", userSpace.getGradeId());
        paramMap.put("phaseId", searchVo.getPhaseId());
        paramMap.put("orgId", searchVo.getOrgId());
        paramMap.put("phaseId", searchVo.getPhaseId());
        paramMap.put("enable", 1);
        paramMap.put("userId", searchVo.getUserId());
        paramMap.put("sysRoleId", SysRole.TEACHER.getId());
        paramMap.put("sysRoleIds",
            Arrays.asList(SysRole.XKZZ.getId(), SysRole.NJZZ.getId(), SysRole.BKZZ.getId(), SysRole.TEACHER.getId()));

        paramMap.put("startTime", searchVo.getStartTime());
        paramMap.put("endTime", searchVo.getEndTime());
        sql.append(" and t.submitTime >= :startTime and t.submitTime < :endTime");
        plan.addCustomCondition(sql.toString(), paramMap);
        plan.setIsSubmit(1);
        plan.setOrgId(searchVo.getOrgId());
        plan.setPhaseId(searchVo.getPhaseId());
        plan.addGroup("t.id");
        plan.addOrder("t.submitTime desc");
        thesisList.addAll(thesisService.findAll(plan));
      }
    }
  }

  private void getLectureSubmitData(List<UserSpace> userSpaceList, List<LectureRecords> lectureList, SearchVo searchVo) {
    for (UserSpace userSpace : userSpaceList) {
      Map<String, Object> paramMap = new HashMap<String, Object>();
      Integer sysRoleId = userSpace.getSysRoleId();
      StringBuilder sql = new StringBuilder();
      LectureRecords lecture = new LectureRecords();
      lecture.addAlias("t");
      lecture.addCustomCulomn(" DISTINCT t.* ");
      if (sysRoleId != null && !SysRole.TEACHER.getId().equals(sysRoleId)) {
        if (SysRole.XKZZ.getId().equals(sysRoleId)) { // 学科组长
          lecture
              .addJoin(
                  JOINTYPE.INNER,
                  "(select id,roleId,userId,gradeId,subjectId from UserSpace where enable = :enable and orgId = :orgId and schoolYear = :schoolYear"
                      + " and phaseId = :phaseId and subjectId = :subjectId and sysRoleId = :sysRoleId) s").on(
                  "t.lecturepeopleId=s.user_id");
        } else if (SysRole.NJZZ.getId().equals(sysRoleId)) { // 年级组长
          lecture
              .addJoin(
                  JOINTYPE.INNER,
                  "(select id,roleId,userId,gradeId,subjectId from UserSpace where enable = :enable and orgId = :orgId and schoolYear = :schoolYear"
                      + " and phaseId = :phaseId and gradeId = :gradeId and sysRoleId = :sysRoleId) s").on(
                  "t.lecturepeopleId=s.user_id");
        } else if (SysRole.BKZZ.getId().equals(sysRoleId)) { // 备课组长
          lecture
              .addJoin(
                  JOINTYPE.INNER,
                  "(select id,roleId,userId,gradeId,subjectId from UserSpace where enable = :enable and orgId = :orgId and schoolYear = :schoolYear"
                      + " and phaseId = :phaseId and gradeId = :gradeId and subjectId = :subjectId and sysRoleId = :sysRoleId) s")
              .on("t.lecturepeopleId=s.user_id");
        } else if (SysRole.ZR.getId().equals(sysRoleId)) { // 主任
          lecture
              .addJoin(
                  JOINTYPE.INNER,
                  "(select id,roleId,userId,gradeId,subjectId from UserSpace where enable = :enable and orgId = :orgId and schoolYear = :schoolYear"
                      + " and phaseId = :phaseId and sysRoleId in (:sysRoleIds)) s").on("t.lecturepeopleId=s.user_id");
        }
        paramMap.put("schoolYear", userSpace.getSchoolYear());
        paramMap.put("subjectId", userSpace.getSubjectId());
        paramMap.put("gradeId", userSpace.getGradeId());
        paramMap.put("phaseId", searchVo.getPhaseId());
        paramMap.put("orgId", searchVo.getOrgId());
        paramMap.put("phaseId", searchVo.getPhaseId());
        paramMap.put("enable", 1);
        paramMap.put("userId", searchVo.getUserId());
        paramMap.put("sysRoleId", SysRole.TEACHER.getId());
        paramMap.put("sysRoleIds",
            Arrays.asList(SysRole.XKZZ.getId(), SysRole.NJZZ.getId(), SysRole.BKZZ.getId(), SysRole.TEACHER.getId()));

        paramMap.put("startTime", searchVo.getStartTime());
        paramMap.put("endTime", searchVo.getEndTime());
        sql.append(" and t.submitTime >= :startTime and t.submitTime < :endTime");
        lecture.addCustomCondition(sql.toString(), paramMap);
        lecture.setIsSubmit(1);
        lecture.setIsDelete(false);
        lecture.setIsEpub(1);// 正式发文
        lecture.setOrgId(searchVo.getOrgId());
        lecture.setPhaseId(searchVo.getPhaseId());
        lecture.addGroup("t.id");
        lecture.addOrder("t.submitTime desc");
        lectureList.addAll(lectureRecordsService.findAll(lecture));
      }
    }
  }

}
