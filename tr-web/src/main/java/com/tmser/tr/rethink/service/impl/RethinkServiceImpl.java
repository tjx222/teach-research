/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.rethink.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmser.tr.browse.BaseResTypes;
import com.tmser.tr.browse.utils.BrowseRecordUtils;
import com.tmser.tr.common.dao.BaseDAO;
import com.tmser.tr.common.page.PageList;
import com.tmser.tr.common.service.AbstractService;
import com.tmser.tr.common.utils.WebThreadLocalUtils;
import com.tmser.tr.lessonplan.bo.LessonInfo;
import com.tmser.tr.lessonplan.bo.LessonPlan;
import com.tmser.tr.lessonplan.dao.LessonPlanDao;
import com.tmser.tr.lessonplan.service.MyPlanBookService;
import com.tmser.tr.manage.meta.bo.Book;
import com.tmser.tr.manage.meta.bo.BookChapter;
import com.tmser.tr.manage.meta.service.BookChapterHerperService;
import com.tmser.tr.manage.meta.service.BookChapterService;
import com.tmser.tr.manage.meta.service.BookService;
import com.tmser.tr.manage.resources.service.ResourcesService;
import com.tmser.tr.rethink.service.RethinkService;
import com.tmser.tr.uc.bo.User;
import com.tmser.tr.uc.bo.UserSpace;
import com.tmser.tr.uc.utils.CurrentUserContext;
import com.tmser.tr.uc.utils.SessionKey;
import com.tmser.tr.utils.StringUtils;

/**
 * <pre>
 * 反思相关功能操作Service实现类
 * </pre>
 *
 * @author tmser
 * @version $Id: RethinkServiceImpl.java, v 1.0 2015年2月10日 下午2:57:03 tmser Exp $
 */
@Service
@Transactional
public class RethinkServiceImpl extends AbstractService<LessonPlan, Integer> implements RethinkService {

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
	 * 分页查询反思反思
	 * 
	 * @param lp
	 * @return
	 * @see com.tmser.tr.rethink.service.RethinkService#findCourseList(com.tmser.tr.lessonplan.bo.LessonPlan)
	 */
	@Override
	public PageList<LessonPlan> findCourseList(LessonPlan lp) {
		User user = CurrentUserContext.getCurrentUser();
		// 通过不同情况封装不同查询条件,当前学年 ,用户,listPage
		Integer planType = lp.getPlanType();
		PageList<LessonPlan> listPage = null;
		lp.setUserId(user.getId());// 用户Id
		lp.setEnable(1);// 有效
		lp.addOrder("lastupDttm desc");
		if (planType == null) {// 初次进入
			lp.setPlanType(LessonPlan.KE_HOU_FAN_SI);
		}
		listPage = lessonPlanDao.listPage(lp);
		
		return listPage;
	}

	/**
	 * 保存教学反思
	 * 
	 * @param lp
	 * @see com.tmser.tr.rethink.service.RethinkService#saveRethink(com.tmser.tr.lessonplan.bo.LessonPlan,
	 *      org.springframework.web.multipart.commons.CommonsMultipartFile)
	 */
	@Override
	public Boolean saveRethink(LessonPlan lp) {
		if (StringUtils.isEmpty(lp.getPlanName()) || lp.getPlanType() == null
				|| (lp.getPlanType() == LessonPlan.KE_HOU_FAN_SI && StringUtils.isEmpty(lp.getLessonId()))) {
			return false;
		}
		User user = CurrentUserContext.getCurrentUser(); // 用户空间
		Integer schoolYear = (Integer) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SCHOOLYEAR);// 学年
		Integer termId = (Integer) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_TERM);// 学期
		// 保存反思信息入库（注:反思名称做做判重处理）
		LessonPlan lptemp = new LessonPlan();
		lptemp.setUserId(user.getId());// 用户Id
		lptemp.setEnable(1);// 有效
		lptemp.setOrgId(user.getOrgId());// 机构Id
		lptemp.setSchoolYear(schoolYear);// 学年
		lptemp.setPlanType(lp.getPlanType());
		lptemp.setPlanName(lp.getPlanName());
		if (lp.getPlanId() != null) {// 修改
			LessonPlan lessonPlan = lessonPlanDao.get(lp.getPlanId());
			if (lp.getPlanType() == LessonPlan.KE_HOU_FAN_SI && !lessonPlan.getLessonId().equals(lp.getLessonId())) {// 将原来的课题下的资源重命名
				LessonPlan lpTemp = new LessonPlan();
				lpTemp.setPlanType(lessonPlan.getPlanType());
				lpTemp.setCrtId(lessonPlan.getCrtId());
				lpTemp.setLessonId(lessonPlan.getLessonId());
				lpTemp.setOrgId(lessonPlan.getOrgId());
				lpTemp.setSchoolYear(lessonPlan.getSchoolYear());
				lpTemp.addOrder(" crtDttm asc ");
				List<LessonPlan> listAll = lessonPlanDao.listAll(lpTemp);
				if (listAll != null && listAll.size() > 1) {
					BookChapter s = bookChapterService.findOne(lessonPlan.getLessonId());
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
			}

			lp.setLastupId(user.getId());
			lp.setLastupDttm(new Date());

			if (lp.getPlanType() == LessonPlan.KE_HOU_FAN_SI) {
				if (lessonPlan.getLessonId().equals(lp.getLessonId())) {
					lp.setPlanName(lessonPlan.getPlanName());
				} else {
					muPlanName = lp.getPlanName();
					String planName = setOnlyPlanName(lptemp, 1);
					lp.setPlanName(planName);// 重新命名后的名称
				}
				// 添加备课资源
				LessonInfo saveLessonInfo = myPlanBookService.saveLessonInfo(lp.getLessonId(),
						lp.getGradeId(),lp.getSubjectId(),muPlanName,
						LessonPlan.KE_HOU_FAN_SI);
				lp.setInfoId(saveLessonInfo.getId());
			}
			// 检查是否修改了文件资源
			if (!("".equals(lp.getResId()))) {
				LessonPlan old = lessonPlanDao.get(lp.getPlanId());
				if (old != null && old.getResId() != null && !lp.getResId().equals(old.getResId())) {
					resourcesService.deleteResources(old.getResId());// 删除原来资源
					// 更改资源状态
					resourcesService.updateTmptResources(lp.getResId());
				}
			}
			lessonPlanDao.update(lp);
		} else {// 保存

			if (lp.getPlanType() == LessonPlan.KE_HOU_FAN_SI) {// 课后反思
				muPlanName = lp.getPlanName();
				String planName = setOnlyPlanName(lptemp, 1);
				lp.setPlanName(planName);// 重新命名后的名称
			}

			lp.setUserId(user.getId());
			lp.setOrgId(user.getOrgId());
			lp.setSchoolYear(schoolYear);
			lp.setIsSubmit(false);// 未提交
			lp.setIsShare(false);// 未分享
			lp.setIsScan(false);// 未查阅
			lp.setIsComment(false);// 未评论
			lp.setCommentUp(false);// 评论已更新
			lp.setScanUp(false);// 查阅已更新
			lp.setDownNum(0);// 下载量为0
			lp.setCrtId(user.getId());// 创建人
			lp.setCrtDttm(new Date());// 创建时间
			lp.setLastupId(user.getId());// 最后更新人
			lp.setLastupDttm(new Date());// 最后更新时间
			lp.setTermId(termId);// 学期
			lp.setEnable(1);// 有效

			if (lp.getPlanType() == LessonPlan.KE_HOU_FAN_SI) {
				// 添加备课资源
				LessonInfo saveLessonInfo = myPlanBookService.saveLessonInfo(lp.getLessonId(),
						lp.getGradeId(),lp.getSubjectId(),muPlanName,
						LessonPlan.KE_HOU_FAN_SI);
				lp.setInfoId(saveLessonInfo.getId());
				lp.setBookShortname(saveLessonInfo.getBookShortname());// 书的简称
				lp.setFasciculeId(saveLessonInfo.getFasciculeId());
				lp.setBookId(saveLessonInfo.getBookId());
				lp.setPhaseId(saveLessonInfo.getPhaseId());
			}

			lessonPlanDao.insert(lp);
			// 保存成功
			// 更改资源状态
			resourcesService.updateTmptResources(lp.getResId());
		}
		return true;

	}

	/**
	 * 获得不重复的名称
	 * 
	 * @param planName
	 * @return
	 */
	private String setOnlyPlanName(LessonPlan lp, int i) {
		String planName = lp.getPlanName();
		int j = i + 1;
		List<LessonPlan> list = lessonPlanDao.list(lp, 1);
		if (list != null && list.size() > 0) {
			lp.setPlanName(muPlanName + j);
			return setOnlyPlanName(lp, j);
		} else {
			return planName;
		}
	}

	/**
	 * 删除教学反思
	 * 
	 * @param lp
	 *            return
	 * @see com.tmser.tr.rethink.service.RethinkService#deleteRethink(java.lang.Integer)
	 */
	@Override
	public Boolean deleteRethink(LessonPlan lp) {

		if (lp.getPlanType() == LessonPlan.KE_HOU_FAN_SI) {
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
				// 课后反思调用统一删除接口
				myPlanBookService.deleteLessonPlanById(lp.getPlanId());
			}
		} else {
			// 删除本身
			lessonPlanDao.delete(lp.getPlanId());
			// 删除资源表记录
			resourcesService.deleteResources(lp.getResId());
		}
		return true;
	}

	/**
	 * 分享教学反思
	 * 
	 * @param lp
	 * @see com.tmser.tr.rethink.service.RethinkService#sharingRethink(com.tmser.tr.lessonplan.bo.LessonPlan)
	 */
	@Override
	public Boolean sharingRethink(LessonPlan lp) {
		// 注释：返回值 0：成功，1：已经有评论，2：失败
		Boolean isOk = true;
		if (lp.getPlanType() == LessonPlan.KE_HOU_FAN_SI) {// 课后反思
			if (lp.getIsShare()) {// 分享
				myPlanBookService.sharePlanOfLessonById(lp.getPlanId());
			} else {// 取消分享
				isOk = myPlanBookService.unSharePlanOfLessonById(lp.getPlanId());
			}
		} else {// 其他反思
			lp.setShareTime(new Date());
			lessonPlanDao.update(lp);
			// 修改浏览记录分享状态
			if (lp.getIsShare()) {// 分享
				BrowseRecordUtils.updateBrowseRecordShare(BaseResTypes.BKZY, lp.getPlanId(), true);
			} else {// 取消分享
				BrowseRecordUtils.updateBrowseRecordShare(BaseResTypes.BKZY, lp.getPlanId(), false);
			}
		}
		return isOk;
	}

	/**
	 * @return
	 * @see com.tmser.tr.common.service.BaseService#getDAO()
	 */
	@Override
	public BaseDAO<LessonPlan, Integer> getDAO() {
		return this.lessonPlanDao;
	}

	/**
	 * 查询未提交的数据并进行封装处理
	 * 
	 * @param planType
	 * @return
	 * @see com.tmser.tr.rethink.service.RethinkService#getSubmitData(java.lang.String)
	 */
	@Override
	public Map<String, Object> getSubmitData(Integer isSubmit) {

		UserSpace userSpace = (UserSpace) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE); // 用户空间
		Map<String, Object> retMap = new HashMap<String, Object>();
		Book book = bookService.findOne(userSpace.getBookId());
		Integer term = (Integer) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_TERM);
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
				retMap.put("list2", myPlanBookService.getIsOrNotSubmitLessonPlanByBookId(downTerm,Integer.valueOf(1).equals(isSubmit),LessonPlan.KE_HOU_FAN_SI));
			}
			if(StringUtils.isNotEmpty(upTerm)){
				retMap.put("name", "上册书籍目录");
				retMap.put("treeList", bookChapterHerperService.getBookChapterTreeByComId(upTerm));
				retMap.put("list", myPlanBookService.getIsOrNotSubmitLessonPlanByBookId(upTerm,Integer.valueOf(1).equals(isSubmit),LessonPlan.KE_HOU_FAN_SI));
			}
		} else {
			retMap.put("treeList", bookChapterHerperService.getBookChapterTreeByComId(bookId));
			retMap.put("list", myPlanBookService.getIsOrNotSubmitLessonPlanByBookId(bookId,Integer.valueOf(1).equals(isSubmit),LessonPlan.KE_HOU_FAN_SI));
			retMap.put("name", "全一册书籍目录");
		}
		return retMap;
	}


	/**
	 * 
	 * @param isSubmit
	 * @return
	 * @see com.tmser.tr.rethink.service.RethinkService#getQTrethink(java.lang.String)
	 */
	@Override
	public List<LessonPlan> getQTrethink(Integer isSubmit) {
		UserSpace userSpace = (UserSpace) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE); // 用户空间
		Integer schoolYear = (Integer) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SCHOOLYEAR);// 学年
		LessonPlan lp = new LessonPlan();
		lp.setUserId(userSpace.getUserId());// 用户Id
		lp.setEnable(1);// 有效
		lp.setOrgId(userSpace.getOrgId());// 机构Id
		lp.setGradeId(userSpace.getGradeId());// 年级Id
		lp.setSubjectId(userSpace.getSubjectId());// 学科Id
		lp.setSchoolYear(schoolYear);// 学年
		if (Integer.valueOf(0).equals(isSubmit)) {
			lp.setIsSubmit(false);// 未提交的
		} else if (Integer.valueOf(1).equals(isSubmit)) {
			lp.setIsSubmit(true);// 已提交的
		}
		lp.setPlanType(LessonPlan.QI_TA_FAN_SI);// 其他反思
		lp.addOrder("crtDttm desc");
		List<LessonPlan> lpList = lessonPlanDao.listAll(lp);
		return lpList;
	}

	/**
	 * 提交或者取消提交反思
	 * 
	 * @param isSubmit
	 * @param planIds
	 * @param qtFanSiIds
	 * @return
	 * @see com.tmser.tr.rethink.service.RethinkService#submitRethink(String,String,String)
	 */
	@Override
	public Boolean submitRethink(String isSubmit, String planIds, String qtFanSiIds) {
		if ("0".equals(isSubmit)) {
			myPlanBookService.submitLessonPlansByIdStr(planIds);
			if (qtFanSiIds != null && !"".equals(qtFanSiIds)) {
				String[] idsArray = qtFanSiIds.split(",");
				Date submitTime = new Date();
				// 更新备课资源表
				for (int i = 0; i < idsArray.length; i++) {
					LessonPlan lessonPlan = new LessonPlan();
					lessonPlan.setPlanId(Integer.parseInt(idsArray[i]));
					lessonPlan.setIsSubmit(true);
					lessonPlan.setSubmitTime(submitTime);
					lessonPlan.setLastupDttm(submitTime);
					lessonPlanDao.update(lessonPlan);
				}
			}
		} else if ("1".equals(isSubmit)) {
			myPlanBookService.unSubmitLessonPlansByIdStr(planIds);
			if (qtFanSiIds != null && !"".equals(qtFanSiIds)) {
				String[] idsArray = qtFanSiIds.split(",");
				Date submitTime = new Date();
				// 更新备课资源表
				for (int i = 0; i < idsArray.length; i++) {
					LessonPlan lessonPlan = new LessonPlan();
					lessonPlan.setPlanId(Integer.parseInt(idsArray[i]));
					lessonPlan.setIsSubmit(false);
					lessonPlan.setSubmitTime(submitTime);
					lessonPlan.setLastupDttm(submitTime);
					lessonPlanDao.update(lessonPlan);
				}
			}
		}
		return true;
	}

}
