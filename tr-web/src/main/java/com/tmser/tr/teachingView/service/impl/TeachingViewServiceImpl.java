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
import com.tmser.tr.activity.service.ActivityService;
import com.tmser.tr.activity.service.ActivityTracksService;
import com.tmser.tr.check.bo.CheckInfo;
import com.tmser.tr.check.service.CheckInfoService;
import com.tmser.tr.comment.bo.CommentInfo;
import com.tmser.tr.comment.bo.Discuss;
import com.tmser.tr.comment.service.CommentInfoService;
import com.tmser.tr.comment.service.DiscussService;
import com.tmser.tr.common.ResTypeConstants;
import com.tmser.tr.common.page.Page;
import com.tmser.tr.common.page.PageList;
import com.tmser.tr.common.utils.WebThreadLocalUtils;
import com.tmser.tr.companion.bo.JyCompanionMessage;
import com.tmser.tr.companion.service.JyCompanionMessageService;
import com.tmser.tr.lessonplan.bo.LessonInfo;
import com.tmser.tr.lessonplan.bo.LessonPlan;
import com.tmser.tr.lessonplan.service.LessonInfoService;
import com.tmser.tr.lessonplan.service.LessonPlanService;
import com.tmser.tr.manage.meta.Meta;
import com.tmser.tr.manage.meta.MetaUtils;
import com.tmser.tr.manage.meta.bo.Book;
import com.tmser.tr.manage.meta.service.BookService;
import com.tmser.tr.manage.org.bo.Organization;
import com.tmser.tr.manage.org.service.OrganizationService;
import com.tmser.tr.manage.resources.bo.Resources;
import com.tmser.tr.manage.resources.service.ResourcesService;
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
  private JyCompanionMessageService jyCompanionMessageService;
  @Autowired
  private SchoolYearService schoolYearService;
  @Autowired
  private ActivityTracksService activityTracksService;
  @Autowired
  private DiscussService discussService;
  @Autowired
  private LessonPlanService lessonPlanService;
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
            WebThreadLocalUtils.getRequest().getContextPath() + "/jy/teachingView/manager/teachingView_t_detail?termId="
                + searchVo.getTermId() + "&gradeId=" + searchVo.getGradeId() + "&subjectId=" + searchVo.getSubjectId()
                + "&spaceId=" + teacher.getId());
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
        tempMap.put("url",
            WebThreadLocalUtils.getRequest().getContextPath()
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
        tempMap.put("url",
            WebThreadLocalUtils.getRequest().getContextPath()
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
    activity.addCustomCondition(
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
    m.addAttribute("containsInput",
        "true".equalsIgnoreCase(info.getFlags()) || "1".equals(info.getFlags()) ? "1" : "0");
    m.addAttribute("titleShow", (info.getTitleShow() != null && info.getTitleShow() == true) ? "1" : "0");
  }

}
