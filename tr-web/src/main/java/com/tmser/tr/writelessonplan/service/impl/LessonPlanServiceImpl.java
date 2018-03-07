package com.tmser.tr.writelessonplan.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.CollectionUtils;

import com.tmser.tr.common.dao.BaseDAO;
import com.tmser.tr.common.page.PageList;
import com.tmser.tr.common.service.AbstractService;
import com.tmser.tr.common.utils.WebThreadLocalUtils;
import com.tmser.tr.common.vo.Result;
import com.tmser.tr.courseware.service.CoursewareService;
import com.tmser.tr.lessonplan.bo.LessonInfo;
import com.tmser.tr.lessonplan.bo.LessonPlan;
import com.tmser.tr.lessonplan.dao.LessonInfoDao;
import com.tmser.tr.lessonplan.dao.LessonPlanDao;
import com.tmser.tr.manage.meta.bo.Book;
import com.tmser.tr.manage.meta.bo.BookChapter;
import com.tmser.tr.manage.meta.dao.BookChapterDao;
import com.tmser.tr.manage.meta.service.BookService;
import com.tmser.tr.manage.org.bo.Organization;
import com.tmser.tr.manage.org.service.OrganizationService;
import com.tmser.tr.manage.resources.bo.Resources;
import com.tmser.tr.manage.resources.service.ResourcesService;
import com.tmser.tr.myplanbook.service.MyPlanBookService;
import com.tmser.tr.uc.SysRole;
import com.tmser.tr.uc.bo.Login;
import com.tmser.tr.uc.bo.UserSpace;
import com.tmser.tr.uc.dao.LoginDao;
import com.tmser.tr.uc.dao.UserSpaceDao;
import com.tmser.tr.uc.service.SchoolYearService;
import com.tmser.tr.uc.utils.SessionKey;
import com.tmser.tr.utils.StringUtils;
import com.tmser.tr.writelessonplan.service.LessonPlanService;
import com.zhuozhengsoft.pageoffice.FileSaver;

/**
 * 备课资源表 服务实现类
 * 
 * @author wangdawei
 * @version 1.0 2015-02-03
 */
@Service
@Transactional
public class LessonPlanServiceImpl extends AbstractService<LessonPlan, Integer> implements LessonPlanService {

  private static final Logger logger = LoggerFactory.getLogger(LessonPlanServiceImpl.class);
  @Autowired
  private LessonPlanDao lessonPlanDao;
  @Autowired
  private LessonInfoDao lessonInfoDao;
  @Autowired
  private ResourcesService resourcesService;
  @Autowired
  private MyPlanBookService myPlanBookService; // 我的备课本服务类
  @Autowired
  private BookService bookService;// 获取上下册书 service
  @Autowired
  private OrganizationService orgService;
  @Autowired
  private LoginDao loginDao;
  @Autowired
  private UserSpaceDao userSpaceDao;
  @Autowired
  private BookChapterDao commdityBookChapterDao;
  @Autowired
  private SchoolYearService schoolYearService;
  @Autowired
  private CoursewareService coursewareService;

  /**
   * @param fs
   * @return
   * @see com.tmser.tr.writelessonplan.service.LessonPlanService#saveLessonPlan(com.zhuozhengsoft.pageoffice.FileSaver)
   */
  @Override
  public Integer saveLessonPlan(FileSaver fs) {
    // 获取当前用户空间
    UserSpace userSpace = (UserSpace) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE);
    // 学年
    Integer schoolYear = (Integer) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SCHOOLYEAR);
    // 学期
    Integer termId = (Integer) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_TERM);
    String planId = fs.getFormField("planId");// 教案id
    String lessonId = fs.getFormField("lessonId");// 课题id
    String lessonName = fs.getFormField("lessonName");
    String lessonHours = fs.getFormField("hoursIdStr");// 课时id连成的字符串

    if (userSpace.getSysRoleId().intValue() != SysRole.TEACHER.getId().intValue()) { // 如果不是老师
      return 0;
    }
    // 判断将要保存的教案是否和以保存教案的课时重复
    if (hoursIdOreadyExist(lessonId, lessonHours)) {
      return null;
    }

    String relativeUrl = File.separator + "jiaoan" + File.separator + "o_" + userSpace.getOrgId() + File.separator
        + String.valueOf(schoolYear) + File.separator + String.valueOf(userSpace.getSubjectId()) + File.separator
        + lessonId; // 相对路径
                    // 如：/2015/3/3
    if (StringUtils.isBlank(planId)) {// 教案不存在则新增教案
      // 调用上传接口将文件上传返回相对路径
      Resources resources = resourcesService.saveResources(fs.getFileStream(), fs.getFileName(),
          Long.valueOf(fs.getFileSize()), relativeUrl);
      if (resources != null) {
        // 保存教案信息
        String bookId = fs.getFormField("bookId");
        Book book = bookService.findOne(bookId);
        // 增加其课题信息记录(不存在增加，已存在则教案数量+1)
        LessonInfo lessonInfo = myPlanBookService.saveLessonInfo(lessonId, lessonName, LessonPlan.JIAO_AN);
        if (lessonInfo != null) {
          Integer tpId = null;
          if (!"".equals(fs.getFormField("tpId"))) {
            tpId = Integer.valueOf(fs.getFormField("tpId"));
          }

          String planName = null;// 备课资源名称
          if (lessonHours.startsWith("-1")) {
            planName = lessonName + "(不分课时)";
          } else {
            planName = lessonName + "(第" + lessonHours + "课时)";
          }
          Integer order = 0;
          try {
            order = Integer.valueOf(lessonHours.split(",")[0]);
          } catch (NumberFormatException e) {
            //
          }
          // 构造待新增的教案
          LessonPlan lessonPlan = new LessonPlan(lessonInfo.getId(), planName, resources.getId(), 0,
              userSpace.getUserId(), userSpace.getSubjectId(), userSpace.getGradeId(), bookId, book.getFormatName(),
              lessonId, lessonHours, tpId, userSpace.getOrgId(), book.getFasciculeId(), schoolYear, termId,
              userSpace.getPhaseId(), order, new Date(), 1);
          lessonPlan.setState(0);
          lessonPlan = lessonPlanDao.insert(lessonPlan);
          logger.info("撰写教案：新增教案成功！ 操作人id：" + userSpace.getUserId());
          planId = String.valueOf(lessonPlan.getPlanId());
        }
      }
    }
    return Integer.valueOf(planId);
  }

  // 根据lesson获取子章节
  @SuppressWarnings("unchecked")
  public Map<String, Object> findCharacterTree(BookChapter bookchapter) {
    Map<String, Object> mapone = new HashMap<String, Object>();
    mapone.put("id", bookchapter.getChapterId());
    mapone.put("name", bookchapter.getChapterName());
    mapone.put("parentId", bookchapter.getParentId());
    mapone.put("orderNum", bookchapter.getOrderNum());
    mapone.put("chapterLevel", bookchapter.getChapterLevel());
    mapone.put("child", null);
    BookChapter bc = new BookChapter();
    bc.setParentId(bookchapter.getChapterId());
    bc.addCustomCulomn("chapterId,chapterName,parentId,orderNum,chapterLevel");
    bc.addOrder("chapterLevel asc,orderNum asc");
    List<BookChapter> listone = commdityBookChapterDao.listAll(bc);
    Object child = mapone.get("child");
    List<Map<String, Object>> childlist = null;
    if (child == null) {
      childlist = new ArrayList<Map<String, Object>>();
    } else {
      childlist = (List<Map<String, Object>>) (mapone).get("child");
    }
    if (listone != null && listone.size() > 0) {
      for (BookChapter bcone : listone) {
        Map<String, Object> map = findCharacterTree(bcone);
        childlist.add(map);
      }

    }
    mapone.put("child", childlist);
    return mapone;
  }

  @Override
  public Map<String, Object> toEditLessonPlanRemote(String lessonid, String username) {
    Map<String, Object> map = new HashMap<String, Object>();
    Login login = new Login();
    login.setLoginname(username);
    login = loginDao.getOne(login);
    Integer userId = login.getId();
    map.put("userId", userId);
    BookChapter bookchapter = commdityBookChapterDao.get(lessonid);
    if (bookchapter == null) {
      return null;
    }
    String chapterName = bookchapter.getChapterName();
    map.put("chapterName", chapterName);
    // 获取章节树
    Map<String, Object> chapterMap = findCharacterTree(bookchapter);
    map.put("chapterTree", chapterMap);
    return map;
  }

  /**
   * 平台对接保存简案
   * 
   * @param lessonid
   * @param username
   * @param content
   * @return
   * @throws IOException
   */
  @Override
  public Result saveLessonPlanRemote(String lessonid, String userId, String content) throws IOException {
    Result re = Result.newInstance();
    // 学年
    Integer schoolYear = schoolYearService.getCurrentSchoolYear();
    // 学期
    Integer termId = schoolYearService.getCurrentTerm();
    BookChapter bookchapter = commdityBookChapterDao.get(lessonid);
    if (bookchapter == null) {
      re.setCode(0);
      re.setMsg("没有该章节");
      return re;
    }
    String bookId = bookchapter.getComId();
    // 如果bookId不存在则不存储
    if (bookId == null) {
      re.setCode(0);
      re.setMsg("没有该课本");
      return re;
    }
    String lessonName = bookchapter.getChapterName();
    Book book = bookService.findOne(bookId);
    UserSpace userSpace = isCanSaveLessonplan(Integer.valueOf(userId), book);
    if (userSpace == null) {
      re.setCode(0);
      re.setMsg("请撰写本年级本科目下的简案");
      return re;
    }
    Integer fasciculeId = book.getFasciculeId();
    String bookShortName = book.getFormatName();
    String lessonHours = "0";// 课时暂时为0
    // 判断将要保存的教案是否和以保存教案的课时重复
    if (hoursIdOreadyExist(userSpace, lessonHours, schoolYear, lessonid)) {
      re.setCode(0);
      re.setMsg("该课时已经存在，请重新选择课时");
      return re;
    }
    String relativeUrl = File.separator + "jiaoan" + File.separator + "o_" + userSpace.getOrgId() + File.separator
        + String.valueOf(schoolYear) + File.separator + String.valueOf(userSpace.getSubjectId()) + File.separator
        + lessonid;
    String tempUrl = WebThreadLocalUtils.getRequest().getSession().getServletContext().getRealPath("/");
    String filePath = string2Word(content, tempUrl, lessonName);
    File file = new File(filePath);
    InputStream is = new FileInputStream(file);
    Resources resources = resourcesService.saveResources(is, bookchapter.getChapterName() + ".doc",
        (long) is.available(), relativeUrl);
    if (resources != null) {
      LessonInfo lessonInfo = myPlanBookService.saveLessonInfo(userSpace, lessonid, lessonName, LessonPlan.JIAO_AN,
          book, schoolYear, termId);
      logger.info("新增课题信息成功！ 操作人id：" + userSpace.getUserId());
      if (lessonInfo != null) {
        String planName = lessonName + "(简案)";
        LessonPlan lessonPlan = new LessonPlan(lessonInfo.getId(), planName, resources.getId(), 0,
            userSpace.getUserId(), userSpace.getSubjectId(), userSpace.getGradeId(), bookId, bookShortName, lessonid,
            lessonHours, 0, userSpace.getOrgId(), fasciculeId, schoolYear, termId, userSpace.getPhaseId(),
            Integer.valueOf(lessonHours.substring(0, 1)), new Date(), 1);
        lessonPlan = lessonPlanDao.insert(lessonPlan);
        logger.info("撰写教案：新增简案成功！ 操作人id：" + userSpace.getUserId() + ":" + lessonPlan);
      }
    }
    file.delete();
    re.setCode(1);
    re.setMsg("保存成功");
    return re;
  }

  /**
   * 对接平台时判断简案是否和已保存的简案重复
   * 
   * @param userSpace
   * @param lessonHours
   * @param schoolYear
   * @param lessonId
   * @return
   */
  private boolean hoursIdOreadyExist(UserSpace userSpace, String lessonHours, Integer schoolYear, String lessonId) {
    LessonPlan lp = new LessonPlan();
    lp.setLessonId(lessonId);
    lp.setUserId(userSpace.getUserId());
    lp.setSchoolYear(schoolYear);
    String hoursIdStr = getHoursStrOfWritedLessonById(lp);
    String[] hoursArray = lessonHours.split(StringUtils.COMMA);
    for (String hour : hoursArray) {
      if (hoursIdStr.contains(StringUtils.COMMA + hour + StringUtils.COMMA)) {
        return true;
      }
    }
    return false;

  }

  /**
   * 判断将要保存的教案是否和以保存教案的课时重复
   * 
   * @param lessonId
   * @param lessonHours
   * @return
   */
  private boolean hoursIdOreadyExist(String lessonId, String lessonHours) {
    // 获取当前用户空间
    UserSpace userSpace = (UserSpace) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE);
    // 学年
    Integer schoolYear = (Integer) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SCHOOLYEAR);
    LessonPlan lp = new LessonPlan();
    lp.setLessonId(lessonId);
    lp.setUserId(userSpace.getUserId());
    lp.setSchoolYear(schoolYear);
    String hoursIdStr = getHoursStrOfWritedLessonById(lp);
    String[] hoursArray = lessonHours.split(StringUtils.COMMA);
    for (String hour : hoursArray) {
      if (hoursIdStr.contains(StringUtils.COMMA + hour + StringUtils.COMMA)) {
        return true;
      }
    }
    return false;
  }

  /**
   * 根据课题id获取已写过教案的课时id连成的字符串
   * 
   * @param lessonId
   * @return
   * @see com.tmser.tr.writelessonplan.service.LessonPlanService#getHoursStrOfWritedLessonById(java.lang.String)
   */
  @Override
  public String getHoursStrOfWritedLessonById(LessonPlan lessonPlan) {
    StringBuilder hoursStr = new StringBuilder(StringUtils.COMMA);
    lessonPlan.setEnable(1);
    List<LessonPlan> lessonPlanList = findAll(lessonPlan);
    if (lessonPlanList != null && lessonPlanList.size() > 0) {
      for (LessonPlan lp : lessonPlanList) {
        if (StringUtils.isNotBlank(lp.getHoursId())) {
          hoursStr.append(lp.getHoursId()).append(StringUtils.COMMA);
        }
      }
    }
    return hoursStr.toString();
  }

  /**
   * @return
   * @see com.tmser.tr.common.service.BaseService#getDAO()
   */
  @Override
  public BaseDAO<LessonPlan, Integer> getDAO() {
    return lessonPlanDao;
  }

  /**
   * 通过id获取备课资源
   * 
   * @param planId
   * @return
   * @see com.tmser.tr.writelessonplan.service.LessonPlanService#getLessonPlanById(java.lang.Integer)
   */
  @Override
  public LessonPlan getLessonPlanById(Integer planId) {
    return findOne(planId);
  }

  /**
   * 获取同伴资源
   * 
   * @param lessonPlan
   * @return
   * @see com.tmser.tr.writelessonplan.service.LessonPlanService#getPeerResource(com.tmser.tr.lessonplan.bo.LessonPlan)
   */
  @Override
  public PageList<LessonPlan> getPeerResource(LessonPlan lessonPlan) {
    // 获取当前用户空间
    UserSpace userSpace = (UserSpace) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE);
    Organization org = orgService.findOne(userSpace.getOrgId());
    lessonPlan.setUserId(userSpace.getUserId());
    lessonPlan.setGradeId(userSpace.getGradeId());
    lessonPlan.setSubjectId(userSpace.getSubjectId());
    lessonPlan.setOrgId(org.getOrgType());
    lessonPlan.setIsShare(true);
    lessonPlan.pageSize(6);
    PageList<LessonPlan> lessonPlanList = lessonPlanDao.getPeerResource(lessonPlan);
    return lessonPlanList;
  }

  /**
   * 获取最新的备课资源
   * 
   * @param planType
   *          资源类型 （0：教案，1：课件，2：课后反思，3：其他反思）null:(教案 课件 课后反思)
   * @return
   * @see com.tmser.tr.writelessonplan.service.LessonPlanService#getLatestLessonPlan()
   */
  @Override
  public LessonPlan getLatestLessonPlan(Book book, Integer planType) {
    UserSpace userSpace = (UserSpace) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE);
    Integer schoolYear = (Integer) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SCHOOLYEAR);// 学年
    LessonPlan lessonPlan = lessonPlanDao.getLatestLessonPlan(book, userSpace.getUserId(), userSpace.getSubjectId(),
        schoolYear, planType);
    return lessonPlan;
  }

  /**
   * 获取书下的备课资源统计数据（教案数量，课件数量，反思数量，已提交数量，已分享数量）
   * 
   * @param bookId
   * @return
   * @see com.tmser.tr.writelessonplan.service.LessonPlanService#getCountDataForLessonPlan(java.lang.String)
   */
  @Override
  public Map<String, Integer> getCountDataOfPlanForBook(String bookId) {
    UserSpace userSpace = (UserSpace) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE);
    Integer schoolYear = (Integer) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SCHOOLYEAR);// 学年
    Map<String, Integer> countData = new HashMap<String, Integer>();
    LessonInfo lessonInfo = new LessonInfo();
    lessonInfo.setBookId(bookId);
    lessonInfo.setUserId(userSpace.getUserId());
    lessonInfo.setSchoolYear(schoolYear);
    lessonInfo.addCustomCondition(" and jiaoanCount>0");
    Integer lessonCount_jiaoan = lessonInfoDao.count(lessonInfo);
    lessonInfo.addCustomCondition(" and kejianCount>0");
    Integer lessonCount_kejian = lessonInfoDao.count(lessonInfo);
    lessonInfo.addCustomCondition(" and fansiCount>0");
    Integer lessonCount_fansi = lessonInfoDao.count(lessonInfo);
    LessonPlan lessonPlan = new LessonPlan();
    lessonPlan.setBookId(bookId);
    lessonPlan.setUserId(userSpace.getUserId());
    lessonPlan.setSchoolYear(schoolYear);
    lessonPlan.setEnable(1);
    lessonPlan.setPlanType(0);
    Integer jiaoanCount = count(lessonPlan);
    lessonPlan.setPlanType(1);
    Integer kejianCount = count(lessonPlan);
    lessonPlan.setPlanType(2);
    Integer fansiCount = count(lessonPlan);
    lessonPlan.setPlanType(null);
    lessonPlan.setIsShare(true);
    Integer shareCount = count(lessonPlan);
    lessonInfo.addCustomCondition(" and (jiaoanSubmitCount>0 or kejianSubmitCount>0 or fansiSubmitCount>0) ");
    Integer submitCount = lessonInfoDao.count(lessonInfo);
    countData.put("lessonCount_jiaoan", lessonCount_jiaoan);
    countData.put("lessonCount_kejian", lessonCount_kejian);
    countData.put("lessonCount_fansi", lessonCount_fansi);
    countData.put("jiaoanCount", jiaoanCount);
    countData.put("kejianCount", kejianCount);
    countData.put("fansiCount", fansiCount);
    countData.put("submitCount", submitCount);
    countData.put("shareCount", shareCount);
    return countData;
  }

  /**
   * 获取课题下的备课资源统计数据 (教案数量、反思数量、课件数量)
   * 
   * @param lessonId
   * @return
   * @see com.tmser.tr.writelessonplan.service.LessonPlanService#getCountDataOfPlanForLesson(java.lang.String)
   */
  @Override
  public Map<String, Integer> getCountDataOfPlanForLesson(String lessonId) {
    UserSpace userSpace = (UserSpace) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE);
    Integer schoolYear = (Integer) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SCHOOLYEAR);// 学年
    Map<String, Integer> countData = new HashMap<String, Integer>();
    LessonPlan lessonPlan = new LessonPlan();
    lessonPlan.setLessonId(lessonId);
    lessonPlan.setUserId(userSpace.getUserId());
    lessonPlan.setSchoolYear(schoolYear);
    lessonPlan.setEnable(1);
    lessonPlan.setPlanType(0);
    Integer jiaoanCount = count(lessonPlan);
    lessonPlan.setPlanType(1);
    Integer kejianCount = count(lessonPlan);
    lessonPlan.setPlanType(2);
    Integer fansiCount = count(lessonPlan);
    countData.put("jiaoanCount", jiaoanCount);
    countData.put("kejianCount", kejianCount);
    countData.put("fansiCount", fansiCount);
    return countData;
  }

  /**
   * 获取课题下的教案资源集合
   * 
   * @param lessonId
   * @return
   * @see com.tmser.tr.writelessonplan.service.LessonPlanService#getLessonPlanListForLesson(java.lang.String)
   */
  @Override
  public List<LessonPlan> getLessonPlanListForLesson(String lessonId) {
    UserSpace userSpace = (UserSpace) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE);
    Integer schoolYear = (Integer) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SCHOOLYEAR);// 学年
    LessonPlan lessonPlan = new LessonPlan();
    lessonPlan.setLessonId(lessonId);
    lessonPlan.setUserId(userSpace.getUserId());
    lessonPlan.setSchoolYear(schoolYear);
    lessonPlan.setEnable(1);
    lessonPlan.setPlanType(LessonPlan.JIAO_AN);
    lessonPlan.addOrder(" orderValue asc");
    List<LessonPlan> lessonPlanList = findAll(lessonPlan);
    return lessonPlanList;
  }

  /**
   * 获取课题下的课件资源集合
   * 
   * @param lessonId
   * @return
   * @see com.tmser.tr.writelessonplan.service.LessonPlanService#getLessonPlanListForLesson(java.lang.String)
   */
  @Override
  public List<LessonPlan> getKeJianListForLesson(String lessonId) {
    UserSpace userSpace = (UserSpace) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE);
    Integer schoolYear = (Integer) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SCHOOLYEAR);// 学年
    LessonPlan lessonPlan = new LessonPlan();
    lessonPlan.setLessonId(lessonId);
    lessonPlan.setUserId(userSpace.getUserId());
    lessonPlan.setSchoolYear(schoolYear);
    lessonPlan.setEnable(1);
    lessonPlan.setPlanType(LessonPlan.KE_JIAN);
    lessonPlan.addOrder(" crtDttm asc");
    List<LessonPlan> kejianList = findAll(lessonPlan);
    return kejianList;
  }

  /**
   * 获取课题下的其他反思资源集合
   * 
   * @param lessonId
   * @return
   * @see com.tmser.tr.writelessonplan.service.LessonPlanService#getLessonPlanListForLesson(java.lang.String)
   */
  @Override
  public List<LessonPlan> getFanSiListForLesson(String lessonId) {
    UserSpace userSpace = (UserSpace) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE);
    Integer schoolYear = (Integer) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SCHOOLYEAR);// 学年
    LessonPlan lessonPlan = new LessonPlan();
    lessonPlan.setLessonId(lessonId);
    lessonPlan.setUserId(userSpace.getUserId());
    lessonPlan.setSchoolYear(schoolYear);
    lessonPlan.setEnable(1);
    lessonPlan.setPlanType(LessonPlan.KE_HOU_FAN_SI);
    lessonPlan.addOrder(" crtDttm asc");
    List<LessonPlan> fansiList = findAll(lessonPlan);
    return fansiList;
  }

  /**
   * 修改教案
   * 
   * @param fs
   * @return
   * @see com.tmser.tr.writelessonplan.service.LessonPlanService#editLessonPlan(com.zhuozhengsoft.pageoffice.FileSaver)
   */
  @Override
  public Boolean editLessonPlan(FileSaver fs) {
    boolean flag = true;
    // 获取当前用户空间
    UserSpace userSpace = (UserSpace) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE);
    // 学年
    Integer schoolYear = (Integer) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SCHOOLYEAR);
    try {
      String planId = fs.getFormField("planId");// 教案id
      if (StringUtils.isBlank(planId)) {
        return false;
      }
      LessonPlan lessonPlan = lessonPlanDao.get(Integer.valueOf(planId));
      String oldResId = lessonPlan.getResId();
      String lessonHours = fs.getFormField("hoursIdStr");// 课时id连成的字符串
      String planName = lessonPlan.getPlanName();
      String relativeUrl = File.separator + "jiaoan" + File.separator + String.valueOf(schoolYear) + File.separator
          + String.valueOf(userSpace.getSubjectId()) + File.separator + lessonPlan.getLessonId();
      // 调用上传接口将文件上传返回相对路径
      Resources resources = resourcesService.updateResources(fs.getFileStream(), fs.getFileName(),
          Long.valueOf(fs.getFileSize()), relativeUrl, lessonPlan.getResId());
      if (resources != null) {
        // 构造待更新的教案
        if (!lessonPlan.getHoursId().equals(lessonHours)) {
          String lessonName = lessonInfoDao.get(lessonPlan.getInfoId()).getLessonName();
          if (lessonHours.length() == 9) {
            planName = lessonName + "(全案)";
          } else {
            planName = lessonName + "(第" + lessonHours + "课时)";
          }
          lessonPlan.setPlanName(planName);
          lessonPlan.setHoursId(lessonHours);
        }
        lessonPlan.setLastupId(userSpace.getUserId());
        lessonPlan.setLastupDttm(new Date());
        lessonPlan.setResId(resources.getId());
        lessonPlanDao.update(lessonPlan);
      }
    } catch (NumberFormatException e) {
      logger.error("修改教案", e);
      flag = false;
    }
    return flag;
  }

  /**
   * 获取课题下的教案集合
   * 
   * @param infoId
   * @return
   * @see com.tmser.tr.writelessonplan.service.LessonPlanService#getJiaoanByInfoId(java.lang.Integer)
   */
  @Override
  public List<LessonPlan> getJiaoanByInfoId(Integer infoId) {
    LessonPlan lessonPlan = new LessonPlan();
    lessonPlan.setInfoId(infoId);
    lessonPlan.setEnable(1);
    Map<String, Object> paramMap = new HashMap<String, Object>();
    paramMap.put("type", LessonPlan.JIAO_AN);
    lessonPlan.addCustomCondition(" and planType = :type", paramMap);
    lessonPlan.addOrder(" orderValue asc");
    return lessonPlanDao.listAll(lessonPlan);
  }

  /**
   * 根据教案类型将查阅意见的状态更新为已查看（查阅意见已更新置为0）
   * 
   * @param lessonPlan
   * @see com.tmser.tr.writelessonplan.service.LessonPlanService#setAlreadyShowByPlanType(com.tmser.tr.lessonplan.bo.LessonPlan)
   */
  @Override
  public void setScanListAlreadyShowByType(LessonPlan lessonPlan) {
    List<LessonPlan> planList = lessonPlanDao.listAll(lessonPlan);
    for (LessonPlan lp : planList) {
      lp.setScanUp(false);
      update(lp);
    }
  }

  /**
   * 对接课件
   * 
   * @param loginname
   * @param lessonid
   * @param path
   * @return
   */
  @Override
  public Integer abutmentTeachingRemote(String loginName, String lessonId, String url, String bookId, String lessonName) {
    Integer planId = null;
    Login login = new Login();
    login.setLogincode(loginName);
    login = loginDao.getOne(login);
    // 用户不存在则不存
    if (login == null) {
      return null;
    }
    Integer userId = login.getId();
    // 不存在课本则不存
    Book book = bookService.findOne(bookId);
    if (book == null) {
      return null;
    }

    // 用户没有权限不保存
    UserSpace userSpace = isCanSaveLessonplan(userId, book);
    if (userSpace == null) {
      return null;
    }

    if (StringUtils.isEmpty(lessonId)) {
      return null;
    }

    // 判断是否已经存在该平台上传的该课件,已上传则更新时间即可
    LessonPlan lp = new LessonPlan();
    lp.setOrigin(1);
    lp.setLessonId(lessonId);
    LessonPlan lessonplan = lessonPlanDao.getOne(lp);
    if (lessonplan != null) {
      lp.setPlanId(lessonplan.getPlanId());
      lp.setPlanName(lessonName);
      lp.setLastupDttm(new Date());
      lessonPlanDao.update(lp);
      return lp.getPlanId();
    }

    // 学年
    Integer schoolYear = schoolYearService.getCurrentSchoolYear();
    // 学期
    Integer termId = schoolYearService.getCurrentTerm();
    Integer fasciculeId = book.getFasciculeId();
    String bookShortName = book.getFormatName();

    // 没有该章节，直接存lessonPlan，并且enable设置为0不可用
    BookChapter bk = new BookChapter();
    bk.setComId(bookId);
    bk.setChapterId(lessonId);
    bk = commdityBookChapterDao.getOne(bk);
    if (bk == null) {
      InputStream is = null;
      String filename = url.substring(url.lastIndexOf(File.separator) + 1);
      Resources resources = resourcesService.saveResources(is, filename, 0L, url);
      lp.setUserId(userId);
      lp.setIsSubmit(false);// 未提交
      lp.setIsShare(false);// 未分享
      lp.setIsScan(false);// 未查阅
      lp.setIsComment(false);// 未评论
      lp.setCommentUp(false);// 评论已更新
      lp.setScanUp(false);// 查阅已更新
      lp.setDownNum(0);// 下载量为0
      lp.setCrtId(userId);// 创建人
      lp.setCrtDttm(new Date());// 创建时间
      lp.setLastupId(userId);// 最后更新人
      lp.setLastupDttm(new Date());// 最后更新时间
      lp.setTermId(termId);// 学期
      lp.setSchoolYear(schoolYear);
      lp.setFasciculeId(fasciculeId);
      lp.setBookShortname(bookShortName);// 书的简称
      lp.setBookId(bookId);
      lp.setPlanName(lessonName);
      String noreplaname = coursewareService.setOnlyPlanName(lp, 1, 0);
      lp.setPlanName(noreplaname);
      // 该用户没有写该课件的权限时，将该课件状态设置为不可用
      lp.setEnable(0);
      lp.setPlanType(LessonPlan.KE_JIAN);
      lp.setResId(resources.getId());
      lp.setOrigin(1);
      lp = lessonPlanDao.insert(lp);
      logger.info("撰写课件：该用户没有权限，该课件不可用！ 操作人id：" + userId + ":" + lp);
      planId = lp.getPlanId();
      return planId;
    }

    InputStream is = null;
    String filename = url.substring(url.lastIndexOf("/") + 1);
    Resources resources = resourcesService.saveResources(is, filename, 0L, url);
    if (resources != null) {
      LessonInfo lessonInfo = myPlanBookService.saveLessonInfo(userSpace, lessonId, lessonName, LessonPlan.KE_JIAN,
          book, schoolYear, termId);
      logger.info("新增课题信息成功！ 操作人id：" + userSpace.getUserId());
      if (lessonInfo != null) {
        lp.setInfoId(lessonInfo.getId());
        lp.setUserId(userId);
        lp.setPlanName(lessonName);
        lp.setSubjectId(userSpace.getSubjectId());
        lp.setGradeId(userSpace.getGradeId());
        lp.setOrgId(userSpace.getOrgId());
        lp.setIsSubmit(false);// 未提交
        lp.setIsShare(false);// 未分享
        lp.setIsScan(false);// 未查阅
        lp.setIsComment(false);// 未评论
        lp.setCommentUp(false);// 评论已更新
        lp.setScanUp(false);// 查阅已更新
        lp.setDownNum(0);// 下载量为0
        lp.setCrtId(userId);// 创建人
        lp.setCrtDttm(new Date());// 创建时间
        lp.setLastupId(userId);// 最后更新人
        lp.setLastupDttm(new Date());// 最后更新时间
        lp.setEnable(1);// 有效
        lp.setTermId(termId);// 学期
        lp.setSchoolYear(schoolYear);
        lp.setPhaseId(userSpace.getPhaseId());// 学段
        lp.setFasciculeId(fasciculeId);
        lp.setBookShortname(bookShortName);// 书的简称
        lp.setBookId(bookId);
        lessonName = coursewareService.setOnlyPlanName(lp, 1, 0);
        lp.setPlanName(lessonName);
        lp.setPlanType(LessonPlan.KE_JIAN);
        lp.setResId(resources.getId());
        lp = lessonPlanDao.insert(lp);
        logger.info("撰写课件：新增课件成功！ 操作人id：" + userSpace.getUserId() + ":" + lp);
        planId = lp.getPlanId();
      }
    }
    return planId;
  }

  /**
   * 判断用户是否有权限保存该lessonid对应的lessonplan
   * 
   * @param userId
   * @param lessonid
   * @return
   */
  private UserSpace isCanSaveLessonplan(Integer userId, Book book) {
    Integer subjectId = book.getSubjectId();
    Integer gradeId = book.getGradeLevelId();
    UserSpace userSpace = new UserSpace();
    userSpace.setUserId(userId);
    userSpace.setSubjectId(subjectId);
    userSpace.setGradeId(gradeId);
    userSpace.setSysRoleId(SysRole.TEACHER.getId());// 设置为老师
    userSpace.setEnable(1);
    userSpace.setSchoolYear(schoolYearService.getCurrentSchoolYear());
    List<UserSpace> usList = userSpaceDao.listAll(userSpace);
    if (CollectionUtils.isEmpty(usList)) {
      return null;
    }
    for (UserSpace us : usList) {
      String bookid = us.getBookId();
      if (bookid.equals(book.getComId()) || bookid.equals(book.getRelationComId())) {
        userSpace = us;
      }
    }
    return userSpace;
  }

  /**
   * 
   * @param url
   * @param loginName
   * @return
   * @see com.tmser.tr.writelessonplan.service.LessonPlanService#deleabutmentTeachingPlan(java.lang.String,
   *      java.lang.String)
   */
  @Override
  public Result deleabutmentTeachingPlan(String url, String loginName) {
    Result re = new Result();
    Login login = new Login();
    login.setLogincode(loginName);
    login = loginDao.getOne(login);
    if (login == null) {
      re.setCode(2);
      re.setMsg("您不能删除该课件");
      return re;
    }
    Integer userId = login.getId();
    Resources res = new Resources();
    res.setPath(url);
    res = resourcesService.findOne(res);
    if (res == null) {
      re.setCode(3);
      re.setMsg("没有该课件");
      return re;
    }
    LessonPlan lessonPlan = new LessonPlan();
    lessonPlan.setResId(res.getId());
    lessonPlan = lessonPlanDao.getOne(lessonPlan);
    if (lessonPlan == null) {
      re.setCode(3);
      re.setMsg("没有该课件");
      return re;
    }
    if (userId.intValue() != lessonPlan.getUserId().intValue()) {
      re.setCode(2);
      re.setMsg("您不能删除该课件");
      return re;
    }
    if (coursewareService.deleteCourseware(lessonPlan)) {
      re.setCode(1);
      re.setMsg("删除成功");
    } else {
      re.setCode(0);
      re.setMsg("删除失败");
    }
    return re;
  }

  /**
   * @param Bookid
   * @param lessonid
   * @param loginname
   * @return
   * @see com.tmser.tr.uc.service.LessonPlanRemoteService#validateLessonIdBookId(java.lang.String,
   *      java.lang.String, java.lang.String)
   */
  @Override
  public Result validateLessonIdBookId(String bookId, String lessonId, String loginname) {
    Result re = new Result();
    Book book = bookService.findOne(bookId);
    // 有没有该书籍
    if (book == null) {
      re.setCode(0);
      re.setMsg("找不到该课本");
      return re;
    }
    Login login = new Login();
    login.setLogincode(loginname);
    login = loginDao.getOne(login);
    if (login == null) {
      re.setCode(0);
      re.setMsg("用户不存在");
      return re;
    }
    Integer userId = login.getId();
    UserSpace us = isCanSaveLessonplan(userId, book);
    // 用户有没有权限写这本书籍下的课件
    if (us == null) {
      re.setCode(0);
      re.setMsg("请撰写本年级本科目下的课件");
      return re;
    }
    if (lessonId == null) {
      re.setCode(0);
      re.setMsg("请选择章节");
      return re;
    }
    BookChapter bk = new BookChapter();
    bk.setComId(bookId);
    bk.setChapterId(lessonId);
    bk = commdityBookChapterDao.getOne(bk);
    // 有没有该章节
    if (bk == null) {
      re.setCode(1);
      re.setMsg("课本没有该章节");
      return re;
    }
    BookChapter bk1 = new BookChapter();
    bk1.setParentId(lessonId);
    bk1 = commdityBookChapterDao.getOne(bk1);
    // 该章节是否为最终叶子章节
    if (bk1 == null) {
      re.setCode(1);
      re.setMsg("该章节即为最终子章节");
      return re;
    }
    Map<String, Object> map = findCharacterTree(bk);
    re.setCode(2);
    re.setMsg("获取子章节成功");
    re.setData(map);
    return re;
  }

  // 将字符串写入word
  private String string2Word(String content, String tempUrl, String filename) throws IOException {
    XWPFDocument doc = new XWPFDocument();
    String[] cont = content.split("<br/>");
    for (String string : cont) {
      XWPFParagraph para = doc.createParagraph();
      XWPFRun run = para.createRun();
      run.setText(string);
    }

    String filePath = tempUrl + filename.replaceAll("[*:/\\\\<>?|\"]+", "") + ".doc";
    FileOutputStream out = new FileOutputStream(filePath);
    doc.write(out);
    out.close();
    return filePath;
  }

  /**
   * 通过上传教案 保存教案
   * 
   * @param fs
   * @return
   * @see com.tmser.tr.writelessonplan.service.LessonPlanService#saveLessonPlan(com.zhuozhengsoft.pageoffice.FileSaver)
   */
  @Override
  public Integer saveLessonPlanWithFile(LessonPlan params, String resId) {
    // 获取当前用户空间
    UserSpace userSpace = (UserSpace) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE);
    // 学年
    Integer schoolYear = (Integer) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SCHOOLYEAR);
    // 学期
    Integer termId = (Integer) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_TERM);
    Integer planId = params.getPlanId();// 教案id
    String lessonId = params.getLessonId();// 课题id
    String lessonName = params.getLessonName();// 课题名称
    String lessonHours = params.getLessonHours();// 课时id连成的字符串

    if (userSpace.getSysRoleId().intValue() != SysRole.TEACHER.getId().intValue()) { // 如果不是老师
      return 0;
    }
    // 判断将要保存的教案是否和以保存教案的课时重复
    if (hoursIdOreadyExist(lessonId, lessonHours)) {
      return null;
    }
    if (planId == null) {// 教案不存在则新增教案
      // 保存教案信息
      String bookId = params.getBookId();
      Book book = bookService.findOne(bookId);
      // 增加其课题信息记录(不存在增加，已存在则教案数量+1)
      LessonInfo lessonInfo = myPlanBookService.saveLessonInfo(lessonId, lessonName, LessonPlan.JIAO_AN);
      if (lessonInfo != null) {
        Integer tpId = null;
        if (params.getTpId() != null) {
          tpId = Integer.valueOf(params.getTpId());
        }

        String planName = null;// 备课资源名称
        if (lessonHours.startsWith("-1")) {
          planName = lessonName + "(不分课时)";
        } else {
          planName = lessonName + "(第" + lessonHours + "课时)";
        }
        Integer order = 0;
        try {
          order = Integer.valueOf(lessonHours.split(",")[0]);
        } catch (NumberFormatException e) {
          //
        }
        // 构造待新增的教案
        LessonPlan lessonPlan = new LessonPlan(lessonInfo.getId(), planName, resId, 0, userSpace.getUserId(),
            userSpace.getSubjectId(), userSpace.getGradeId(), bookId, book.getFormatName(), lessonId, lessonHours,
            tpId, userSpace.getOrgId(), book.getFasciculeId(), schoolYear, termId, userSpace.getPhaseId(), order,
            new Date(), 1);
        lessonPlan.setState(0);
        lessonPlan = lessonPlanDao.insert(lessonPlan);
        planId = lessonPlan.getPlanId();
        logger.info("撰写教案：新增教案成功！ 操作人id：" + userSpace.getUserId());
      }
    }
    return planId;
  }

  /**
   * @param resId
   * @param planId
   * @see com.tmser.tr.writelessonplan.service.LessonPlanService#updateLessonPlanWithResId(java.lang.String,
   *      java.lang.String)
   */
  @Override
  public Result updateLessonPlanWithResId(String resId, Integer planId) {
    Result result = Result.newInstance();
    LessonPlan lessonPlan = lessonPlanDao.get(planId);
    try {
      if (lessonPlan != null && resId != null) {
        String oldResId = lessonPlan.getResId();
        resourcesService.deleteResources(oldResId);
        // 更新lessonPlan
        lessonPlan.setResId(resId);
        lessonPlanDao.update(lessonPlan);
        resourcesService.updateTmptResources(resId);
      } else {
        result.setCode(500);
      }
    } catch (Exception e) {
      TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
      result.setCode(500);
      logger.error("", e);
    }
    return result;
  }
}
