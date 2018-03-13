/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.myplanbook.service.impl;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.Assert;

import com.tmser.tr.activity.bo.Activity;
import com.tmser.tr.activity.bo.ActivityTracks;
import com.tmser.tr.activity.service.ActivityService;
import com.tmser.tr.activity.service.ActivityTracksService;
import com.tmser.tr.browse.BaseResTypes;
import com.tmser.tr.browse.utils.BrowseRecordUtils;
import com.tmser.tr.check.service.CheckInfoService;
import com.tmser.tr.common.ResTypeConstants;
import com.tmser.tr.common.dao.BaseDAO;
import com.tmser.tr.common.service.AbstractService;
import com.tmser.tr.common.utils.WebThreadLocalUtils;
import com.tmser.tr.lessonplan.bo.LessonInfo;
import com.tmser.tr.lessonplan.bo.LessonPlan;
import com.tmser.tr.lessonplan.dao.LessonInfoDao;
import com.tmser.tr.lessonplan.dao.LessonPlanDao;
import com.tmser.tr.manage.meta.bo.Book;
import com.tmser.tr.manage.meta.service.BookChapterHerperService;
import com.tmser.tr.manage.meta.service.BookService;
import com.tmser.tr.manage.meta.vo.BookLessonVo;
import com.tmser.tr.manage.resources.service.ResourcesService;
import com.tmser.tr.myplanbook.service.MyPlanBookService;
import com.tmser.tr.uc.bo.User;
import com.tmser.tr.uc.bo.UserSpace;
import com.tmser.tr.uc.utils.CurrentUserContext;
import com.tmser.tr.uc.utils.SessionKey;
import com.tmser.tr.utils.StringUtils;

/**
 * <pre>
 * 我的备课本服务类
 * </pre>
 *
 * @author wangdawei
 * @version $Id: MyPlanBookServiceImpl.java, v 1.0 2015年3月5日 下午3:28:45 wangdawei
 *          Exp $
 */
@Service
@Transactional
public class MyPlanBookServiceImpl extends AbstractService<LessonInfo, Integer> implements MyPlanBookService {
  private final static Logger logger = LoggerFactory.getLogger(MyPlanBookServiceImpl.class);
  @Autowired
  private LessonInfoDao lessonInfoDao; // 课题信息DAO
  @Autowired
  private LessonPlanDao lessonPlanDao;// 备课资源dao
  @Autowired
  private BookChapterHerperService bchService; // 获取书——课题service
  @Autowired
  private ResourcesService resourcesService;
  @Autowired
  private ActivityService activityService;
  @Autowired
  private ActivityTracksService activityTracksService;
  @Autowired(required = false)
  private CheckInfoService checkInfoService;
  @Autowired
  private BookService bookService;

  @Override
  public LessonInfo saveLessonInfo(UserSpace userSpace, String lessonId, String lessonName, Integer planType, Book book,
      Integer schoolYear, Integer termId) {
    LessonInfo lessonInfo = new LessonInfo();
    lessonInfo.setLessonId(lessonId);
    lessonInfo.setUserId(userSpace.getUserId());
    lessonInfo.setSchoolYear(schoolYear);
    LessonInfo temp = lessonInfoDao.getOne(lessonInfo);
    if (temp == null) {// 不存在则新增
      lessonInfo.setLessonName(lessonName);
      lessonInfo.setBookId(book.getComId());
      lessonInfo.setBookShortname(book.getFormatName());
      lessonInfo.setGradeId(userSpace.getGradeId());
      lessonInfo.setSubjectId(userSpace.getSubjectId());
      lessonInfo.setFasciculeId(book.getFasciculeId());
      lessonInfo.setTermId(termId);
      lessonInfo.setPhaseId(userSpace.getPhaseId());
      lessonInfo.setOrgId(userSpace.getOrgId());
      lessonInfo.setScanUp(false);
      lessonInfo.setVisitUp(false);
      lessonInfo.setCommentUp(false);
      lessonInfo.setScanCount(0);
      lessonInfo.setVisitCount(0);
      lessonInfo.setCommentCount(0);
      lessonInfo.setJiaoanShareCount(0);
      lessonInfo.setKejianShareCount(0);
      lessonInfo.setFansiShareCount(0);
      lessonInfo.setJiaoanSubmitCount(0);
      lessonInfo.setKejianSubmitCount(0);
      lessonInfo.setFansiSubmitCount(0);
      if (planType == LessonPlan.JIAO_AN) {
        lessonInfo.setJiaoanCount(1);
        lessonInfo.setFansiCount(0);
        lessonInfo.setKejianCount(0);
      } else if (planType == LessonPlan.KE_JIAN) {
        lessonInfo.setKejianCount(1);
        lessonInfo.setJiaoanCount(0);
        lessonInfo.setFansiCount(0);
      } else if (planType == LessonPlan.KE_HOU_FAN_SI) {
        lessonInfo.setFansiCount(1);
        lessonInfo.setJiaoanCount(0);
        lessonInfo.setKejianCount(0);
      }
      lessonInfo.setCrtId(userSpace.getUserId());
      lessonInfo.setCrtDttm(new Date());
      lessonInfo.setCurrentFrom(LessonInfo.FROM_ME);
      return lessonInfoDao.insert(lessonInfo);
    } else {
      LessonInfo model = new LessonInfo();
      if (planType == LessonPlan.JIAO_AN) {
        model.addCustomCulomn("jiaoanCount = jiaoanCount+1");
        temp.setJiaoanCount(temp.getJiaoanCount() + 1);
      } else if (planType == LessonPlan.KE_JIAN) {
        model.addCustomCulomn("kejianCount = kejianCount+1");
        temp.setKejianCount(temp.getKejianCount() + 1);
      } else if (planType == LessonPlan.KE_HOU_FAN_SI) {
        model.addCustomCulomn("fansiCount = fansiCount+1");
        temp.setFansiCount(temp.getFansiCount() + 1);
      }

      model.setId(temp.getId());
      try {
        int rs = update(model);
        logger.debug("update lession info id [{}],result {}", temp.getId(), rs);
      } catch (Exception e) {
        logger.error("", e);
        throw e;
      }
      return temp;
    }
  }

  /**
   * 新增课题信息 （如果已存在则不增加）
   * 
   * @param lessonId
   * @param bookId
   * @return
   * @see com.tmser.tr.myplanbook.service.MyPlanBookService#saveLessonInfo(java.lang.String,
   *      java.lang.String)
   */
  @Override
  public LessonInfo saveLessonInfo(String lessonId, Integer gradeId, Integer subjectId, String lessonName,
      Integer planType) {
    // 获取当前用户空间
    User user = CurrentUserContext.getCurrentUser(); // 学年
    Integer schoolYear = (Integer) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SCHOOLYEAR);
    // 学期
    Integer termId = (Integer) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_TERM);
    LessonInfo lessonInfo = new LessonInfo();
    lessonInfo.setLessonId(lessonId);
    lessonInfo.setUserId(user.getId());
    lessonInfo.setSchoolYear(schoolYear);
    LessonInfo temp = lessonInfoDao.getOne(lessonInfo);
    if (temp == null) {// 不存在则新增
      if (StringUtils.isNotBlank(lessonId)) {
        Book book = bookService.getBookByLessonId(lessonId);
        lessonInfo.setLessonName(lessonName);
        lessonInfo.setBookId(book.getComId());
        lessonInfo.setBookShortname(book.getFormatName());
        lessonInfo.setGradeId(gradeId);
        lessonInfo.setSubjectId(subjectId);
        lessonInfo.setFasciculeId(book.getFasciculeId());
        lessonInfo.setTermId(termId);
        lessonInfo.setPhaseId(book.getPhaseId());
        lessonInfo.setOrgId(user.getOrgId());
        lessonInfo.setScanUp(false);
        lessonInfo.setVisitUp(false);
        lessonInfo.setCommentUp(false);
        lessonInfo.setScanCount(0);
        lessonInfo.setVisitCount(0);
        lessonInfo.setCommentCount(0);
        lessonInfo.setJiaoanShareCount(0);
        lessonInfo.setKejianShareCount(0);
        lessonInfo.setFansiShareCount(0);
        lessonInfo.setJiaoanSubmitCount(0);
        lessonInfo.setKejianSubmitCount(0);
        lessonInfo.setFansiSubmitCount(0);
        if (planType == LessonPlan.JIAO_AN) {
          lessonInfo.setJiaoanCount(1);
          lessonInfo.setFansiCount(0);
          lessonInfo.setKejianCount(0);
        } else if (planType == LessonPlan.KE_JIAN) {
          lessonInfo.setKejianCount(1);
          lessonInfo.setJiaoanCount(0);
          lessonInfo.setFansiCount(0);
        } else if (planType == LessonPlan.KE_HOU_FAN_SI) {
          lessonInfo.setFansiCount(1);
          lessonInfo.setJiaoanCount(0);
          lessonInfo.setKejianCount(0);
        }
        lessonInfo.setCrtId(user.getId());
        lessonInfo.setCrtDttm(new Date());
        lessonInfo.setCurrentFrom(LessonInfo.FROM_ME);
        return lessonInfoDao.insert(lessonInfo);
      } else {
        Assert.isTrue(false, "保存lessonInfo失败，lessonId为空");
        return null;
      }
    } else {
      LessonInfo model = new LessonInfo();
      if (planType == LessonPlan.JIAO_AN) {
        model.addCustomCulomn("jiaoanCount = jiaoanCount+1");
        temp.setJiaoanCount(temp.getJiaoanCount() + 1);
      } else if (planType == LessonPlan.KE_JIAN) {
        model.addCustomCulomn("kejianCount = kejianCount+1");
        temp.setKejianCount(temp.getKejianCount() + 1);
      } else if (planType == LessonPlan.KE_HOU_FAN_SI) {
        model.addCustomCulomn("fansiCount = fansiCount+1");
        temp.setFansiCount(temp.getFansiCount() + 1);
      }

      model.setId(temp.getId());
      try {
        int rs = update(model);
        logger.debug("update lession info id [{}],result {}", temp.getId(), rs);
      } catch (Exception e) {
        logger.error("", e);
        throw e;
      }
      return temp;
    }
  }

  /**
   * @return
   * @see com.tmser.tr.common.service.BaseService#getDAO()
   */
  @Override
  public BaseDAO<LessonInfo, Integer> getDAO() {
    return lessonInfoDao;
  }

  /**
   * 获取有备课资源的课题集合
   * 
   * @param bookId
   * @return
   * @see com.tmser.tr.myplanbook.service.MyPlanBookService#getLessonListForMyPlanBook(java.lang.String)
   */
  @Override
  public List<BookLessonVo> getLessonListForMyPlanBook(String bookId) {
    return bchService.getBookChapterByComId(bookId);
  }

  /**
   * 获取有备课资源的课题集合
   * 
   * @param bookId
   * @return
   * @see com.tmser.tr.myplanbook.service.MyPlanBookService#getLessonListForMyPlanBook(java.lang.String)
   */
  @Override
  public List<BookLessonVo> getLessonTreeMyPlanBook(String bookId) {
    return bchService.getBookChapterTreeByComId(bookId);
  }

  /**
   * 根据课题id获取课题的扩展信息
   * 
   * @param lessonId
   * @return
   * @see com.tmser.tr.myplanbook.service.MyPlanBookService#getLessonInfoByLessonId(java.lang.String)
   */
  @Override
  public LessonInfo getLessonInfoByLessonId(String lessonId) {
    // 获取当前用户空间
    UserSpace userSpace = (UserSpace) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE);
    // 学年
    Integer schoolYear = (Integer) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SCHOOLYEAR);
    LessonInfo lessonInfo = new LessonInfo();
    lessonInfo.setLessonId(lessonId);
    lessonInfo.setUserId(userSpace.getUserId());
    lessonInfo.setSchoolYear(schoolYear);
    lessonInfo = lessonInfoDao.getOne(lessonInfo);
    return lessonInfo;
  }

  /**
   * 分享备课资源
   * 
   * @param planId
   * @return
   * @see com.tmser.tr.myplanbook.service.MyPlanBookService#sharePlanOfLessonById(java.lang.Integer)
   */
  @Override
  public void sharePlanOfLessonById(Integer planId) {
    LessonPlan lessonPlan = lessonPlanDao.get(planId);
    Date shareDate = new Date();
    lessonPlan.setIsShare(true);
    lessonPlan.setShareTime(shareDate);
    lessonPlanDao.update(lessonPlan);
    LessonInfo lessonInfo = lessonInfoDao.get(lessonPlan.getInfoId());
    lessonInfo.setShareTime(shareDate);
    if (lessonPlan.getPlanType() == LessonPlan.JIAO_AN) {
      lessonInfo.addCustomCulomn("jiaoanShareCount = jiaoanShareCount+1");
    } else if (lessonPlan.getPlanType() == LessonPlan.KE_JIAN) {
      lessonInfo.addCustomCulomn("kejianShareCount = kejianShareCount+1");
    } else if (lessonPlan.getPlanType() == LessonPlan.KE_HOU_FAN_SI) {
      lessonInfo.addCustomCulomn("fansiShareCount = fansiShareCount+1");
    }
    update(lessonInfo);
    // 修改浏览记录分享状态
    BrowseRecordUtils.updateBrowseRecordShare(BaseResTypes.BKZY, planId, true);
  }

  /**
   * 取消分享备课资源
   * 
   * @param planId
   * @return 0:代表取消成功 1：取消失败，该资源已被评论 2：取消失败，程序运行出错
   * @see com.tmser.tr.myplanbook.service.MyPlanBookService#unSharePlanOfLessonById(java.lang.Integer)
   */
  @Override
  public Boolean unSharePlanOfLessonById(Integer planId) {
    LessonPlan lessonPlan = lessonPlanDao.get(planId);
    if (lessonPlan.getIsComment()) {
      return false;
    } else {
      lessonPlan.setIsShare(false);
      lessonPlanDao.update(lessonPlan);
      LessonInfo lessonInfo = lessonInfoDao.get(lessonPlan.getInfoId());
      if (lessonPlan.getPlanType() == LessonPlan.JIAO_AN) {
        lessonInfo.addCustomCulomn("jiaoanShareCount = jiaoanShareCount-1");
      } else if (lessonPlan.getPlanType() == LessonPlan.KE_JIAN) {
        lessonInfo.addCustomCulomn("kejianShareCount = kejianShareCount-1");
      } else if (lessonPlan.getPlanType() == LessonPlan.KE_HOU_FAN_SI) {
        lessonInfo.addCustomCulomn("fansiShareCount = fansiShareCount-1");
      }
      update(lessonInfo);
      // 修改浏览记录分享状态
      BrowseRecordUtils.updateBrowseRecordShare(BaseResTypes.BKZY, planId, false);
      return true;
    }
  }

  /**
   * 删除一条备课资源 （已提交和已分享的禁止删除）
   * 
   * @param planId
   * @return
   * @see com.tmser.tr.myplanbook.service.MyPlanBookService#deleteLessonPlanById(java.lang.Integer)
   */
  @Override
  public void deleteLessonPlanById(Integer planId) {
    LessonPlan lessonPlan = lessonPlanDao.get(planId);
    // 没有提交或分享则删除
    if (!lessonPlan.getIsSubmit() && !lessonPlan.getIsShare()) {
      lessonPlanDao.delete(planId);

      LessonInfo lessonInfo = findOne(lessonPlan.getInfoId());// 课题信息
      if (lessonPlan.getPlanType() == LessonPlan.JIAO_AN) {
        lessonInfo.addCustomCulomn(" jiaoanCount = jiaoanCount-1");
      } else if (lessonPlan.getPlanType() == LessonPlan.KE_JIAN) {
        lessonInfo.addCustomCulomn(" kejianCount = kejianCount-1");
      } else if (lessonPlan.getPlanType() == LessonPlan.KE_HOU_FAN_SI) {
        lessonInfo.addCustomCulomn(" fansiCount = fansiCount-1");
      }
      update(lessonInfo);

      LessonPlan temp = new LessonPlan();
      temp.setInfoId(lessonInfo.getId());
      temp.setEnable(1);
      List<LessonPlan> tempList = lessonPlanDao.listAll(temp);
      if (tempList == null || tempList.size() == 0) {// 说明课题下没有备课资源，则删除相应的课题信息记录
        delete(lessonInfo.getId());
      }
      // resourcesService.delete(lessonPlan.getResId()); //删除存储资源记录
      resourcesService.deleteResources(lessonPlan.getResId());
    }
  }

  private String getSubmitSql(Integer type) {
    String sql = "";
    if (Integer.valueOf(LessonPlan.JIAO_AN).equals(type)) {
      sql = " and jiaoanCount > :count";
    } else if (Integer.valueOf(LessonPlan.KE_JIAN).equals(type)) {
      sql = " and kejianCount > :count";
    } else if (Integer.valueOf(LessonPlan.KE_HOU_FAN_SI).equals(type)) {
      sql = " and fansiCount > :count";
    } else {
      sql = " and (jiaoanCount > :count or kejianCount > :count or fansiCount > :count)";
    }
    return sql;
  }

  /**
   * 获取已提交或未提交的备课资源
   * 
   * @param bookId
   *          书的id
   * @param isSubmit（0:未提交，1:已提交）
   * @return
   * @see com.tmser.tr.myplanbook.service.MyPlanBookService#getIsOrNotSubmitLessonPlanByBookId(java.lang.String,
   *      java.lang.Integer)
   */
  @Override
  public Map<String, Object> getIsOrNotSubmitLessonPlanByBookId(String bookId, Boolean isSubmit, Integer type) {
    Map<String, Object> dataList = new HashMap<String, Object>();

    UserSpace userSpace = (UserSpace) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE);
    // 学年
    Integer schoolYear = (Integer) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SCHOOLYEAR);

    // 获取已备课的课题集合
    LessonInfo lessonInfo = new LessonInfo();
    lessonInfo.addCustomCulomn("id,lessonId");
    lessonInfo.setBookId(bookId);
    lessonInfo.setUserId(userSpace.getUserId());
    lessonInfo.setSchoolYear(schoolYear);
    Map<String, Object> paramMap = new HashMap<String, Object>();
    paramMap.put("count", 0);
    lessonInfo.addCustomCondition(getSubmitSql(type), paramMap);
    List<LessonInfo> lessonInfoList = findAll(lessonInfo);
    if (lessonInfoList == null || lessonInfoList.size() == 0) {
      return dataList;
    }

    Map<String, BookLessonVo> allChps = bchService.getBookChapterMapByComId(bookId);

    LessonPlan lessonPlan = new LessonPlan();
    lessonPlan.setIsSubmit(isSubmit);
    lessonPlan.setEnable(1);
    for (LessonInfo temp : lessonInfoList) {
      List<LessonPlan> jiaoanList = new ArrayList<LessonPlan>();
      List<LessonPlan> kejianList = new ArrayList<LessonPlan>();
      List<LessonPlan> fansiList = new ArrayList<LessonPlan>();
      lessonPlan.setInfoId(temp.getId());
      lessonPlan.addOrder(" planType asc");
      if (Integer.valueOf(LessonPlan.JIAO_AN).equals(type)) {
        lessonPlan.setPlanType(LessonPlan.JIAO_AN);
        jiaoanList = lessonPlanDao.listAll(lessonPlan);// 课题下的已提交或未提交的教案集合
      } else if (Integer.valueOf(LessonPlan.KE_JIAN).equals(type)) {
        lessonPlan.setPlanType(LessonPlan.KE_JIAN);
        kejianList = lessonPlanDao.listAll(lessonPlan);// 课题下的已提交或未提交的课件集合
      } else if (Integer.valueOf(LessonPlan.KE_HOU_FAN_SI).equals(type)) {
        lessonPlan.setPlanType(LessonPlan.KE_HOU_FAN_SI);
        fansiList = lessonPlanDao.listAll(lessonPlan);// 课题下的已提交或未提交的反思集合
      } else {
        lessonPlan.setPlanType(LessonPlan.JIAO_AN);
        jiaoanList = lessonPlanDao.listAll(lessonPlan);// 课题下的已提交或未提交的教案集合
        lessonPlan.setPlanType(LessonPlan.KE_JIAN);
        kejianList = lessonPlanDao.listAll(lessonPlan);// 课题下的已提交或未提交的课件集合
        lessonPlan.setPlanType(LessonPlan.KE_HOU_FAN_SI);
        fansiList = lessonPlanDao.listAll(lessonPlan);// 课题下的已提交或未提交的反思集合
      }
      if (!jiaoanList.isEmpty() || !kejianList.isEmpty() || !fansiList.isEmpty()) {
        Map<String, Object> lessonMap = new HashMap<String, Object>();
        lessonMap.put("isLeaf", true);
        lessonMap.put("jiaoanList", jiaoanList);
        lessonMap.put("kejianList", kejianList);
        lessonMap.put("fansiList", fansiList);
        dataList.put(temp.getLessonId(), lessonMap);
        BookLessonVo bv = allChps.get(temp.getLessonId());
        if (bv != null) {
          bv = allChps.get(bv.getParentId());
        }

        while (bv != null) {
          Map<String, Object> cmap = new HashMap<String, Object>();
          cmap.put("isLeaf", false);
          dataList.put(bv.getLessonId(), cmap);
          bv = allChps.get(bv.getParentId());
        }
      }
    }

    return dataList;
  }

  /**
   * 批量提交备课资源
   * 
   * @param lessonPlanIdsStr
   * @return
   * @see com.tmser.tr.myplanbook.service.MyPlanBookService#submitLessonPlansByIdStr(java.lang.String)
   */
  @Override
  public void submitLessonPlansByIdStr(String lessonPlanIdsStr) {
    if (lessonPlanIdsStr != null && !"".equals(lessonPlanIdsStr)) {
      String[] idsArray = lessonPlanIdsStr.split(",");
      Date submitTime = new Date();
      Integer restype = null;
      Set<Integer> resIds = new HashSet<Integer>();
      // 更新备课资源表
      for (int i = 0; i < idsArray.length; i++) {
        LessonPlan lessonPlan = lessonPlanDao.get(Integer.valueOf(idsArray[i]));
        lessonPlan.setIsSubmit(true);
        lessonPlan.setSubmitTime(submitTime);
        lessonPlan.setLastupDttm(submitTime);
        lessonPlanDao.update(lessonPlan);
        LessonInfo lessonInfo = new LessonInfo();
        lessonInfo.setId(lessonPlan.getInfoId());
        restype = getCheckResType(lessonPlan.getPlanType());
        if (lessonPlan.getPlanType() == LessonPlan.JIAO_AN) {
          lessonInfo.addCustomCulomn("jiaoanSubmitCount = jiaoanSubmitCount+1");
        } else if (lessonPlan.getPlanType() == LessonPlan.KE_JIAN) {
          lessonInfo.addCustomCulomn("kejianSubmitCount = kejianSubmitCount+1");
        } else if (lessonPlan.getPlanType() == LessonPlan.KE_HOU_FAN_SI) {
          lessonInfo.addCustomCulomn("fansiSubmitCount = fansiSubmitCount+1");
        }
        lessonInfo.setSubmitTime(submitTime);
        lessonInfoDao.update(lessonInfo);
        resIds.add(lessonInfo.getId());
      }
      updateCheckInfo(restype, resIds);
    }
  }

  private void updateCheckInfo(Integer restype, Set<Integer> resIds) {
    if (checkInfoService != null) {
      for (Integer resid : resIds) {
        checkInfoService.updateCheckInfoUpdateState(resid, restype);
      }
    }
  }

  private Integer getCheckResType(Integer planType) {
    switch (planType) {
    case LessonPlan.JIAO_AN:
      return ResTypeConstants.JIAOAN;
    case LessonPlan.KE_JIAN:
      return ResTypeConstants.KEJIAN;
    case LessonPlan.KE_HOU_FAN_SI:
      return ResTypeConstants.FANSI;
    }

    return null;

  }

  /**
   * 取消提交备课资源
   * 
   * @param lessonPlanIdsStr
   * @return
   * @see com.tmser.tr.myplanbook.service.MyPlanBookService#unSubmitLessonPlansByIdStr(java.lang.String)
   */
  @Override
  public void unSubmitLessonPlansByIdStr(String lessonPlanIdsStr) {
    if (lessonPlanIdsStr != null && !"".equals(lessonPlanIdsStr)) {
      String[] idsArray = lessonPlanIdsStr.split(",");
      Date submitTime = new Date();
      // 更新备课资源表
      for (int i = 0; i < idsArray.length; i++) {
        LessonPlan lessonPlan = lessonPlanDao.get(Integer.valueOf(idsArray[i]));
        lessonPlan.setIsSubmit(false);
        lessonPlan.setLastupDttm(submitTime);
        Map<String, Object> tempMap = new HashMap<String, Object>();
        tempMap.put("name", false);
        lessonPlan.addCustomCondition("and isScan =:name", tempMap);
        lessonPlanDao.update(lessonPlan);
        LessonInfo lessonInfo = new LessonInfo();
        lessonInfo.setId(lessonPlan.getInfoId());
        if (lessonPlan.getPlanType() == LessonPlan.JIAO_AN) {
          lessonInfo.addCustomCulomn("jiaoanSubmitCount = jiaoanSubmitCount-1");
        } else if (lessonPlan.getPlanType() == LessonPlan.KE_JIAN) {
          lessonInfo.addCustomCulomn("kejianSubmitCount = kejianSubmitCount-1");
        } else if (lessonPlan.getPlanType() == LessonPlan.KE_HOU_FAN_SI) {
          lessonInfo.addCustomCulomn("fansiSubmitCount = fansiSubmitCount-1");
        }
        lessonInfo.setSubmitTime(submitTime);
        lessonInfoDao.update(lessonInfo);
      }
    }
  }

  /**
   * 单条取消提交备课资源
   * 
   * @param lessonPlanIdsStr
   * @return
   * @see com.tmser.tr.myplanbook.service.MyPlanBookService#unSubmitLessonPlansByIdStr(java.lang.String)
   */
  @Override
  public Integer unSubmitLessonPlansById(Integer planId) {
    int flag = 0;
    if (planId != null) {
      Date submitTime = new Date();
      // 更新备课资源表
      LessonPlan lessonPlan = lessonPlanDao.get(planId);
      if (lessonPlan.getIsScan()) {
        flag = 1;
      } else {
        lessonPlan.setIsSubmit(false);
        lessonPlan.setLastupDttm(submitTime);
        Map<String, Object> tempMap = new HashMap<String, Object>();
        tempMap.put("name", false);
        lessonPlan.addCustomCondition("and isScan =:name", tempMap);
        lessonPlanDao.update(lessonPlan);
        LessonInfo lessonInfo = new LessonInfo();
        lessonInfo.setId(lessonPlan.getInfoId());
        if (lessonPlan.getPlanType() == LessonPlan.JIAO_AN) {
          lessonInfo.addCustomCulomn("jiaoanSubmitCount = jiaoanSubmitCount-1");
        } else if (lessonPlan.getPlanType() == LessonPlan.KE_JIAN) {
          lessonInfo.addCustomCulomn("kejianSubmitCount = kejianSubmitCount-1");
        } else if (lessonPlan.getPlanType() == LessonPlan.KE_HOU_FAN_SI) {
          lessonInfo.addCustomCulomn("fansiSubmitCount = fansiSubmitCount-1");
        }
        lessonInfo.setSubmitTime(submitTime);
        lessonInfoDao.update(lessonInfo);
      }
    } else {
      flag = 2;
    }
    return flag;
  }

  /**
   * 接收集体备课的整理教案
   * 
   * @param activityId
   * @throws FileNotFoundException
   * @see com.tmser.tr.myplanbook.service.MyPlanBookService#receiveLessonPlanOfActivity(java.lang.Integer)
   */
  @Override
  public Model receiveLessonPlanOfActivity(Integer activityId, Model m) throws FileNotFoundException {
    // 获取当前用户空间
    UserSpace userSpace = (UserSpace) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE);
    // 学年
    Integer schoolYear = (Integer) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SCHOOLYEAR);
    // 学期
    Integer termId = (Integer) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_TERM);
    Activity activity = activityService.findOne(activityId);
    if (activity == null) {
      m.addAttribute("result", "fail3");
      m.addAttribute("info", "接收失败，该活动已被删除");
      return m;
    }
    LessonInfo mainUserLessonInfo = findOne(activity.getInfoId()); // 主备人的lessonInfo
    // 如果学科、年级、和学年 与主备人的不符，则不能接收教案
    if (userSpace.getSubjectId().intValue() != mainUserLessonInfo.getSubjectId().intValue()
        || userSpace.getGradeId().intValue() != mainUserLessonInfo.getGradeId().intValue()
        || schoolYear.intValue() != mainUserLessonInfo.getSchoolYear().intValue()) {
      m.addAttribute("result", "fail1");
      m.addAttribute("info", "接收失败，无权限");
      return m;
    }
    List<ActivityTracks> tracksList = activityTracksService.getActivityTracks_zhengli(activityId);
    if (tracksList == null || tracksList.size() <= 0) {
      m.addAttribute("result", "fail2");
      m.addAttribute("info", "接受失败，无可接收的教案");
      return m;
    }
    // 判断当前用户下是否有lessonInfo信息
    LessonInfo lessonInfo = new LessonInfo();
    lessonInfo.setLessonId(mainUserLessonInfo.getLessonId());
    lessonInfo.setSchoolYear(schoolYear);
    lessonInfo.setUserId(userSpace.getUserId());
    LessonInfo temp = lessonInfoDao.getOne(lessonInfo);
    if (temp == null) { // 不存在则新增
      lessonInfo.setLessonName(mainUserLessonInfo.getLessonName());
      lessonInfo.setBookId(mainUserLessonInfo.getBookId());
      lessonInfo.setBookShortname(mainUserLessonInfo.getBookShortname());
      lessonInfo.setGradeId(userSpace.getGradeId());
      lessonInfo.setSubjectId(userSpace.getSubjectId());
      lessonInfo.setFasciculeId(mainUserLessonInfo.getFasciculeId());
      lessonInfo.setTermId(termId);
      lessonInfo.setPhaseId(userSpace.getPhaseId());
      lessonInfo.setOrgId(userSpace.getOrgId());
      lessonInfo.setScanUp(false);
      lessonInfo.setVisitUp(false);
      lessonInfo.setCommentUp(false);
      lessonInfo.setScanCount(0);
      lessonInfo.setVisitCount(0);
      lessonInfo.setCommentCount(0);
      lessonInfo.setJiaoanShareCount(0);
      lessonInfo.setKejianShareCount(0);
      lessonInfo.setFansiShareCount(0);
      lessonInfo.setJiaoanSubmitCount(0);
      lessonInfo.setKejianSubmitCount(0);
      lessonInfo.setFansiSubmitCount(0);
      lessonInfo.setJiaoanCount(tracksList.size());
      lessonInfo.setFansiCount(0);
      lessonInfo.setKejianCount(0);
      lessonInfo.setCrtId(userSpace.getUserId());
      lessonInfo.setCrtDttm(new Date());
      lessonInfo.setCurrentFrom(LessonInfo.FROM_ACTIVITY);
      lessonInfo = lessonInfoDao.insert(lessonInfo);
    } else {// 已存在则
      lessonInfo = temp;
      lessonInfo.setJiaoanCount(tracksList.size());
      lessonInfo.setJiaoanSubmitCount(0);
      lessonInfo.setJiaoanShareCount(0);
      // 删除课题的查阅信息
      if (checkInfoService != null) {
        checkInfoService.deleteCheckOptionOfLessonPlan(lessonInfo.getId());
      }
      lessonInfo.setScanCount(0);
      lessonInfo.setCurrentFrom(LessonInfo.FROM_ACTIVITY);
      update(lessonInfo);

      // 逻辑删除（enable置为0）课题下的原教案
      lessonPlanDao.enableLessonPlan(lessonInfo.getId());
    }
    // 将主备教案加入到教案表
    for (ActivityTracks activityTrack : tracksList) {
      // 复制存储资源
      String newResId = resourcesService.copyRes(activityTrack.getResId());
      // 复制教案
      LessonPlan lessonPlan = new LessonPlan(lessonInfo.getId(), activityTrack.getPlanName(), newResId,
          LessonPlan.JIAO_AN, userSpace.getUserId(), lessonInfo.getSubjectId(), lessonInfo.getGradeId(),
          lessonInfo.getBookId(), lessonInfo.getBookShortname(), lessonInfo.getLessonId(), activityTrack.getHoursId(),
          null, lessonInfo.getOrgId(), lessonInfo.getFasciculeId(), schoolYear, termId, userSpace.getPhaseId(),
          activityTrack.getOrderValue(), new Date(), 1);
      lessonPlanDao.insert(lessonPlan);
    }
    m.addAttribute("result", "success");
    m.addAttribute("info", "接收成功！");
    return m;
  }

  /**
   * 获取提交本课题资源除了当前人的其他人列表
   * 
   * @param lessonInfo
   * @return
   * @see com.tmser.tr.myplanbook.service.MyPlanBookService#lessonSubmitOthers(com.tmser.tr.lessonplan.bo.LessonInfo)
   */
  @Override
  public List<LessonPlan> lessonSubmitOthers(LessonInfo lessonInfo) {
    LessonPlan model = new LessonPlan();
    model.setLessonId(lessonInfo.getLessonId());
    model.setPlanType(lessonInfo.getPhaseId());// 复用学段字段来标识
    model.setSchoolYear(lessonInfo.getSchoolYear());
    model.setIsSubmit(true);// 已提交
    model.setOrgId(lessonInfo.getOrgId());
    Map<String, Object> map = new HashMap<String, Object>();
    map.put("userId", lessonInfo.getUserId());
    model.addCustomCondition(" and userId != :userId", map);
    model.addGroup("userId");
    model.addCustomCulomn("planId,userId,lessonId");
    model.addOrder("submitTime desc");
    return lessonPlanDao.listAll(model);
  }

}
