/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.courseware.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmser.tr.common.page.Page;
import com.tmser.tr.common.page.PageList;
import com.tmser.tr.common.utils.WebThreadLocalUtils;
import com.tmser.tr.courseware.service.CoursewareService;
import com.tmser.tr.lessonplan.bo.LessonInfo;
import com.tmser.tr.lessonplan.bo.LessonPlan;
import com.tmser.tr.lessonplan.dao.LessonPlanDao;
import com.tmser.tr.manage.meta.bo.Book;
import com.tmser.tr.manage.meta.bo.BookChapter;
import com.tmser.tr.manage.meta.service.BookChapterHerperService;
import com.tmser.tr.manage.meta.service.BookChapterService;
import com.tmser.tr.manage.meta.service.BookService;
import com.tmser.tr.manage.resources.service.ResourcesService;
import com.tmser.tr.myplanbook.service.MyPlanBookService;
import com.tmser.tr.uc.bo.UserSpace;
import com.tmser.tr.uc.utils.SessionKey;
import com.tmser.tr.utils.StringUtils;

/**
 * 课件相关功能操作Service实现类
 * 
 * <pre>
 *
 * </pre>
 *
 * @author tmser
 * @version $Id: CoursewareServiceImpl.java, v 1.0 2015年2月10日 下午2:53:52 tmser Exp
 *          $
 */
@Service
@Transactional
public class CoursewareServiceImpl implements CoursewareService {
	private String muPlanName = "";

	@Autowired
	private LessonPlanDao lessonPlanDao;
	@Autowired
	private MyPlanBookService myPlanBookService;
	@Autowired
	private ResourcesService resourcesService;
	@Autowired
	private BookService bookService;
	@Autowired
	private BookChapterHerperService bookChapterHerperService;
	@Autowired
	private BookChapterService bookChapterService;
	/**
	 * 保存课件
	 * 
	 * @param lp
	 * @see com.tmser.tr.courseware.service.CoursewareService#saveCourseware(com.tmser.tr.lessonplan.bo.LessonPlan)
	 */
	@Override
	public void saveCourseware(LessonPlan lp) {
		if (lp != null) {
			UserSpace userSpace = (UserSpace) WebThreadLocalUtils
					.getSessionAttrbitue(SessionKey.CURRENT_SPACE); // 用户空间
			Integer schoolYear = (Integer) WebThreadLocalUtils
					.getSessionAttrbitue(SessionKey.CURRENT_SCHOOLYEAR);// 学年
			Integer termId = (Integer) WebThreadLocalUtils
					.getSessionAttrbitue(SessionKey.CURRENT_TERM);// 学期
			// 保存课件信息入库（注:课件名称做做判重处理）
			LessonPlan lptemp = new LessonPlan();
			lptemp.setCrtId(userSpace.getUserId());// 用户Id
			lptemp.setEnable(1);// 有效
			lptemp.setOrgId(userSpace.getOrgId());// 机构Id
			lptemp.setLessonId(lp.getLessonId());
			lptemp.setSchoolYear(schoolYear);// 学年
			lptemp.setPlanType(lp.getPlanType());
			lptemp.setPlanName(lp.getPlanName());
			if (lp.getPlanId() != null) {// 修改
				LessonPlan lessonPlan = lessonPlanDao.get(lp.getPlanId());
				if (!lessonPlan.getLessonId().equals(lp.getLessonId())) {// 将原来的课题下的资源重命名
					LessonPlan lpTemp = new LessonPlan();
					lpTemp.setPlanType(lessonPlan.getPlanType());
					lpTemp.setCrtId(lessonPlan.getCrtId());
					lpTemp.setLessonId(lessonPlan.getLessonId());
					lpTemp.setOrgId(lessonPlan.getOrgId());
					lpTemp.setSchoolYear(lessonPlan.getSchoolYear());
					lpTemp.addOrder(" crtDttm asc ");
					List<LessonPlan> listAll = lessonPlanDao.listAll(lpTemp);
					if (listAll != null && listAll.size() > 1) {
						BookChapter s = bookChapterService.findOne(lessonPlan
								.getLessonId());
						int i = 0;
						for (LessonPlan lpt : listAll) {
							if (lpt.getPlanId().intValue() != lp.getPlanId()
									.intValue()) {// 不相等
								if (i == 0) {
									lpt.setPlanName(s.getChapterName());
								} else {
									int j = i + 1;
									lpt.setPlanName(s.getChapterName() + j);
								}
								i++;
								lessonPlanDao.update(lpt);
							}
						}
					}

					muPlanName = lp.getPlanName();
					String planName = setOnlyPlanName(lptemp, 1, lp.getPlanId());
					lp.setPlanName(planName);// 重新命名后的名称
				} else {
					lp.setPlanName(lessonPlan.getPlanName());// 重新命名后的名称
				}
				lp.setLastupId(userSpace.getUserId());
				lp.setLastupDttm(new Date());

				// 添加备课资源
				LessonInfo saveLessonInfo = myPlanBookService.saveLessonInfo(
						lp.getLessonId(), muPlanName, LessonPlan.KE_JIAN);
				lp.setInfoId(saveLessonInfo.getId());
				// 检查是否修改了文件资源
				if (StringUtils.isNotEmpty(lp.getResId())) {
					LessonPlan old = lessonPlanDao.get(lp.getPlanId());
					if (old != null && old.getResId() != null
							&& !lp.getResId().equals(old.getResId())) {
						resourcesService.deleteResources(old.getResId());// 删除原来资源
						// 更改资源状态
						resourcesService.updateTmptResources(lp.getResId());
					}
				}
				lessonPlanDao.update(lp);
			} else {// 保存
				muPlanName = lp.getPlanName();
				String planName = setOnlyPlanName(lptemp, 1, 0);

				lp.setPlanName(planName);// 重新命名后的名称
				lp.setUserId(userSpace.getUserId());
				lp.setSubjectId(userSpace.getSubjectId());
				lp.setGradeId(userSpace.getGradeId());
				lp.setOrgId(userSpace.getOrgId());
				lp.setSchoolYear(schoolYear);
				lp.setIsSubmit(false);// 未提交
				lp.setIsShare(false);// 未分享
				lp.setIsScan(false);// 未查阅
				lp.setIsComment(false);// 未评论
				lp.setCommentUp(false);// 评论已更新
				lp.setScanUp(false);// 查阅已更新
				lp.setDownNum(0);// 下载量为0
				lp.setCrtId(userSpace.getUserId());// 创建人
				lp.setCrtDttm(new Date());// 创建时间
				lp.setLastupId(userSpace.getUserId());// 最后更新人
				lp.setLastupDttm(new Date());// 最后更新时间
				lp.setEnable(1);// 有效
				lp.setTermId(termId);// 学期
				lp.setPhaseId(userSpace.getPhaseId());// 学段

				// 添加备课资源
				LessonInfo saveLessonInfo = myPlanBookService.saveLessonInfo(
						lp.getLessonId(), muPlanName, LessonPlan.KE_JIAN);
				lp.setInfoId(saveLessonInfo.getId());
				lp.setFasciculeId(saveLessonInfo.getFasciculeId());
				lp.setBookShortname(saveLessonInfo.getBookShortname());// 书的简称
				lp.setBookId(saveLessonInfo.getBookId());
				lessonPlanDao.insert(lp);
				// 保存成功
				// 更改资源状态
				resourcesService.updateTmptResources(lp.getResId());

			}
		}
	}

	/**
	 * 获得不重复的名称
	 * 
	 * @param planName
	 * @return
	 */
	@Override
	public String setOnlyPlanName(LessonPlan lp, int i, Integer planId) {
		String planName = lp.getPlanName();
		int j = i + 1;
		List<LessonPlan> list = lessonPlanDao.list(lp, 1);
		if (list != null && list.size() > 0) {
			if (list.get(0).getPlanId().intValue() == planId.intValue()) {
				return planName;
			} else {
				lp.setPlanName(muPlanName + j);
				return setOnlyPlanName(lp, j, 0);
			}
		} else {
			return planName;
		}
	}

	/**
	 * 通过条件查询课件列表信息
	 * 
	 * @param lp
	 * @return
	 * @see com.tmser.tr.courseware.service.CoursewareService#findCourseList(com.tmser.tr.lessonplan.bo.LessonPlan)
	 */
	@Override
	public PageList<LessonPlan> findCourseList(LessonPlan lp, Page page) {
		// 通过不同情况封装不同查询条件,当前学年 ,用户,listPage
		UserSpace userSpace = (UserSpace) WebThreadLocalUtils
				.getSessionAttrbitue(SessionKey.CURRENT_SPACE); // 用户空间
		Integer schoolYear = (Integer) WebThreadLocalUtils
				.getSessionAttrbitue(SessionKey.CURRENT_SCHOOLYEAR);// 学年

		lp.setUserId(userSpace.getUserId());// 用户Id
		lp.setPlanType(LessonPlan.KE_JIAN);// 课件
		lp.setEnable(1);// 有效
		lp.setOrgId(userSpace.getOrgId());// 机构Id
		lp.setGradeId(userSpace.getGradeId());// 年级Id
		lp.setSubjectId(userSpace.getSubjectId());// 学科Id
		lp.setSchoolYear(schoolYear);// 学年

		lp.addOrder("lastupDttm desc");
		// page.setPageSize(8);
		lp.addPage(page);

		PageList<LessonPlan> listPage = lessonPlanDao.listPage(lp);

		return listPage;
	}

	/**
	 * 获得当前登陆用户、当前学年的所有有效的课件
	 * 
	 * @return
	 * @see com.tmser.tr.courseware.service.CoursewareService#findAllCourseList()
	 */
	@Override
	public List<LessonPlan> findAllCourseList() {
		UserSpace userSpace = (UserSpace) WebThreadLocalUtils
				.getSessionAttrbitue(SessionKey.CURRENT_SPACE); // 用户空间
		Integer schoolYear = (Integer) WebThreadLocalUtils
				.getSessionAttrbitue(SessionKey.CURRENT_SCHOOLYEAR);// 学年
		if (userSpace != null) {
			LessonPlan lp = new LessonPlan();
			lp.setUserId(userSpace.getUserId());// 用户Id
			lp.setPlanType(LessonPlan.KE_JIAN);// 课件
			lp.setEnable(1);// 有效
			lp.setOrgId(userSpace.getOrgId());// 机构Id
			lp.setGradeId(userSpace.getGradeId());// 年级Id
			lp.setSubjectId(userSpace.getSubjectId());// 学科Id
			lp.setSchoolYear(schoolYear);// 学年
			return lessonPlanDao.listAll(lp);
		} else {
			return null;
		}
	}

	/**
	 * 删除课件
	 * 
	 * @param lp
	 * @return
	 * @see com.tmser.tr.courseware.service.CoursewareService#deleteRethink(com.tmser.tr.lessonplan.bo.LessonPlan)
	 */
	@Override
	public Boolean deleteCourseware(LessonPlan lp) {
		lp = lessonPlanDao.get(lp.getPlanId());
		if (lp != null) {
			LessonPlan lpTemp = new LessonPlan();
			lpTemp.setPlanType(lp.getPlanType());
			lpTemp.setCrtId(lp.getCrtId());
			lpTemp.setLessonId(lp.getLessonId());
			lpTemp.setOrgId(lp.getOrgId());
			lpTemp.setSchoolYear(lp.getSchoolYear());
			lpTemp.addOrder(" crtDttm asc ");
			List<LessonPlan> listAll = lessonPlanDao.listAll(lpTemp);
			if (listAll != null && listAll.size() > 1) {
				BookChapter s = bookChapterService.findOne(lp.getLessonId());
				int i = 0;
				for (LessonPlan lpt : listAll) {
					if (lpt.getPlanId().intValue() != lp.getPlanId().intValue()) {// 不相等
						if (i == 0) {
							lpt.setPlanName(s.getChapterName());
						} else {
							int j = i + 1;
							lpt.setPlanName(s.getChapterName() + j);
						}
						i++;
						lessonPlanDao.update(lpt);
					}
				}
			}
			// 课件调用统一删除接口
			myPlanBookService.deleteLessonPlanById(lp.getPlanId());
		}

		return true;
	}

	/**
	 * @param lp
	 * @return
	 * @see com.tmser.tr.courseware.service.CoursewareService#sharingCourseware(com.tmser.tr.lessonplan.bo.LessonPlan)
	 */
	@Override
	public Boolean sharingCourseware(LessonPlan lp) {
		// 注释：返回值 0：成功，1：已经有评论，2：失败
		Boolean isOk = true;
		if (lp.getIsShare()) {// 分享
			myPlanBookService.sharePlanOfLessonById(lp.getPlanId());
		} else {// 取消分享
			isOk = myPlanBookService.unSharePlanOfLessonById(lp.getPlanId());
		}
		return isOk;
	}
	
	/**
	 * 提交课件之前的数据查询展示
	 * 
	 * @param isSubmit
	 * @return
	 * @see com.tmser.tr.courseware.service.CoursewareService#getSubmitData(java.lang.String)
	 */
	@Override
	public Map<String,Object> getSubmitData(Integer isSubmit) {
		UserSpace userSpace = (UserSpace) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE); // 用户空间
		Map<String,Object> retMap = new HashMap<String,Object>();
		Book book = bookService.findOne(userSpace.getBookId());
		Integer term = (Integer)WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_TERM);
		String bookId = book.getComId();
		String upTerm = "";
		String downTerm = "";
		if(book.getFasciculeId()!=178){
			if(term==0){//上学期
				if(book.getFasciculeId()==176){
					upTerm = bookId;
					downTerm = book.getRelationComId();
				}else{
					downTerm = bookId;
					upTerm = book.getRelationComId();
				}
			}else{//下学期
				if(book.getFasciculeId()==177){
					downTerm = bookId;
					upTerm = book.getRelationComId();
				}else{
					upTerm = bookId;
					downTerm = book.getRelationComId();
				}
			}
			if(StringUtils.isNotEmpty(downTerm)){
				retMap.put("name2", "下册书籍目录");
				retMap.put("treeList2", bookChapterHerperService.getBookChapterTreeByComId(downTerm));
				retMap.put("list2", myPlanBookService.getIsOrNotSubmitLessonPlanByBookId(downTerm,Integer.valueOf(1).equals(isSubmit),LessonPlan.KE_JIAN));
			}
			if(StringUtils.isNotEmpty(upTerm)){
				retMap.put("name", "上册书籍目录");
				retMap.put("treeList", bookChapterHerperService.getBookChapterTreeByComId(upTerm));
				retMap.put("list", myPlanBookService.getIsOrNotSubmitLessonPlanByBookId(upTerm,Integer.valueOf(1).equals(isSubmit),LessonPlan.KE_JIAN));
			}
			
		}else{
			retMap.put("treeList", bookChapterHerperService.getBookChapterTreeByComId(bookId));
			retMap.put("list", myPlanBookService.getIsOrNotSubmitLessonPlanByBookId(bookId,Integer.valueOf(1).equals(isSubmit),LessonPlan.KE_JIAN));
			retMap.put("name", "全一册书籍目录");
		}
		return retMap;
	}

	/**
	 * 提交或者取消提交课件
	 * 
	 * @param isSubmit
	 * @param planIds
	 * @return
	 * @see com.tmser.tr.courseware.service.CoursewareService#submitRethink(String,String)
	 */
	@Override
	public Boolean submitCourseware(String isSubmit, String planIds) {
		if ("0".equals(isSubmit)) {
			myPlanBookService.submitLessonPlansByIdStr(planIds);
		} else if ("1".equals(isSubmit)) {
			myPlanBookService.unSubmitLessonPlansByIdStr(planIds);
		}
		return true;
	}

}
