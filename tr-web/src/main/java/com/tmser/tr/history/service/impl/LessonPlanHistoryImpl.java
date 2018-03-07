/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.history.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmser.tr.common.orm.SqlMapping;
import com.tmser.tr.common.page.PageList;
import com.tmser.tr.history.service.LessonPlanHistory;
import com.tmser.tr.history.vo.SearchVo;
import com.tmser.tr.lessonplan.bo.LessonPlan;
import com.tmser.tr.manage.meta.bo.BookChapter;
import com.tmser.tr.manage.meta.service.BookChapterService;
import com.tmser.tr.manage.meta.service.BookService;
import com.tmser.tr.manage.resources.bo.Resources;
import com.tmser.tr.manage.resources.service.ResourcesService;
import com.tmser.tr.uc.bo.UserSpace;
import com.tmser.tr.uc.service.UserSpaceService;
import com.tmser.tr.utils.StringUtils;
import com.tmser.tr.writelessonplan.service.LessonPlanService;

/**
 * <pre>
 *  历史教学资源查询service实现类
 * </pre>
 *
 * @author wangdawei
 * @version $Id: LessonPlanHistoryImpl.java, v 1.0 2016年5月25日 下午4:28:40 wangdawei Exp $
 */
@Service
@Transactional
public class LessonPlanHistoryImpl implements LessonPlanHistory {

	@Autowired
	private LessonPlanService lessonPlanService;
	@Autowired
	private UserSpaceService userSpaceService;
    @Autowired
    private ResourcesService resourceService;
    @Autowired
    private BookChapterService bookChapterService;
    @Autowired
    private BookService bookService;
	
	/**
	 * 获取历史教学资源的统计值
	 * @param userId
	 * @param code
	 * @param currentYear
	 * @return
	 * @see com.tmser.tr.history.service.LessonPlanHistory#getLessonPlanHistoryCount(java.lang.Integer, java.lang.String, java.lang.Integer)
	 */
	@Override
	public Integer getLessonPlanHistoryCount(Integer userId, String code,
			Integer currentYear) {
		LessonPlan lessonPlan = new LessonPlan();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("planType1", LessonPlan.KE_HOU_FAN_SI);
		map.put("planType2", LessonPlan.QI_TA_FAN_SI);
		lessonPlan.setUserId(userId);
		lessonPlan.setEnable(1);
		lessonPlan.setSchoolYear(currentYear);
		if("zxja".equals(code)){
			lessonPlan.setPlanType(LessonPlan.JIAO_AN);
		}else if("sckj".equals(code)){
			lessonPlan.setPlanType(LessonPlan.KE_JIAN);
		}else if("jxfs".equals(code)){
			lessonPlan.addCustomCondition(" and (planType=:planType1 or planType=:planType2)", map);
		}
		return lessonPlanService.count(lessonPlan);
	}

	/**
	 * 获取历史教案集合
	 * @param searchVo
	 * @return
	 * @see com.tmser.tr.history.service.LessonPlanHistory#getHistoryJiaoanList(com.tmser.tr.history.vo.SearchVo)
	 */
	@Override
	public PageList<LessonPlan> getHistoryJiaoanList(SearchVo searchVo) {
		UserSpace us = userSpaceService.findOne(searchVo.getSpaceId());
		LessonPlan lp = new LessonPlan();
		lp.setUserId(us.getUserId());
		lp.setEnable(1);
		lp.setSubjectId(us.getSubjectId());
		lp.setGradeId(us.getGradeId());
		lp.setPlanType(LessonPlan.JIAO_AN);
		if(!StringUtils.isBlank(searchVo.getSearchStr())){
			lp.setPlanName(SqlMapping.LIKE_PRFIX+searchVo.getSearchStr()+SqlMapping.LIKE_PRFIX);
		}
		lp.setSchoolYear(searchVo.getSchoolYear());
		if(searchVo.getTermId()!=null){
			lp.setTermId(searchVo.getTermId());
		}
		lp.addOrder(" crtDttm desc,lessonId asc");
		searchVo.pageSize(12);
		lp.addPage(searchVo.getPage());
		return lessonPlanService.findByPage(lp);
	}

	/**
	 * 获取教学资源的详细信息
	 * @param planId
	 * @return
	 * @see com.tmser.tr.history.service.LessonPlanHistory#getLessonPlanDetailInfo(java.lang.Integer)
	 */
	@Override
	public Map<String, Object> getLessonPlanDetailInfo(Integer planId) {
		LessonPlan lp = lessonPlanService.findOne(planId);
		Resources recource = resourceService.findOne(lp.getResId());
		Map<String,Object> infoMap = new HashMap<String,Object>();
		infoMap.put("planName",lp.getPlanName());
		if(lp.getPlanType().intValue()==2){
			infoMap.put("typeName","课后反思" );
		}else if(lp.getPlanType().intValue()==3){
			infoMap.put("typeName","其他反思" );
		}
		if(StringUtils.isNotBlank(lp.getLessonId())){
			BookChapter bookChapter = bookChapterService.findOne(lp.getLessonId());
			infoMap.put("lessonName",bookChapter.getChapterName());
			infoMap.put("formatName", bookService.findOne(bookChapter.getComId()).getFormatName());
		}
		infoMap.put("date", new SimpleDateFormat("yyyy-MM-dd").format(lp.getCrtDttm()));
		infoMap.put("submitStatus",lp.getIsSubmit()?"已提交":"未提交");
		infoMap.put("shareStatus", lp.getIsShare()?"已分享":"未分享");
		infoMap.put("size",new BigDecimal(recource.getSize()/1024).setScale(0, BigDecimal.ROUND_HALF_UP));
		infoMap.put("ext",recource.getExt());
		return infoMap;
	}

	/**
	 * 获取历史课件集合
	 * @param searchVo
	 * @return
	 * @see com.tmser.tr.history.service.LessonPlanHistory#getHistoryKejianList(com.tmser.tr.history.vo.SearchVo)
	 */
	@Override
	public PageList<LessonPlan> getHistoryKejianList(SearchVo searchVo) {
		UserSpace us = userSpaceService.findOne(searchVo.getSpaceId());
		LessonPlan lp = new LessonPlan();
		lp.setUserId(us.getUserId());
		lp.setEnable(1);
		lp.setSubjectId(us.getSubjectId());
		lp.setGradeId(us.getGradeId());
		lp.setPlanType(LessonPlan.KE_JIAN);
		if(!StringUtils.isBlank(searchVo.getSearchStr())){
			lp.setPlanName(SqlMapping.LIKE_PRFIX+searchVo.getSearchStr()+SqlMapping.LIKE_PRFIX);
		}
		lp.setSchoolYear(searchVo.getSchoolYear());
		if(searchVo.getTermId()!=null){
			lp.setTermId(searchVo.getTermId());
		}
		lp.addOrder(" crtDttm desc,lessonId asc");
		searchVo.pageSize(12);
		lp.addPage(searchVo.getPage());
		return lessonPlanService.findByPage(lp);
	}

	/**
	 * 获取历史反思集合
	 * @param searchVo
	 * @return
	 * @see com.tmser.tr.history.service.LessonPlanHistory#getHistoryFansiList(com.tmser.tr.history.vo.SearchVo)
	 */
	@Override
	public PageList<LessonPlan> getHistoryFansiList(SearchVo searchVo) {
		UserSpace us = userSpaceService.findOne(searchVo.getSpaceId());
		LessonPlan lp = new LessonPlan();
		lp.setUserId(us.getUserId());
		lp.setEnable(1);
		lp.setSubjectId(us.getSubjectId());
		lp.setGradeId(us.getGradeId());
		if(!StringUtils.isBlank(searchVo.getSearchStr())){
			lp.setPlanName(SqlMapping.LIKE_PRFIX+searchVo.getSearchStr()+SqlMapping.LIKE_PRFIX);
		}
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("planType1", LessonPlan.KE_HOU_FAN_SI);
		map.put("planType2", LessonPlan.QI_TA_FAN_SI);
		if(searchVo.getTypeId()==null){
			lp.addCustomCondition(" and (planType=:planType1 or planType=:planType2) ",map);
		}else if(searchVo.getTypeId()==LessonPlan.KE_HOU_FAN_SI){
			lp.addCustomCondition(" and planType=:planType1 ",map);
		}else if(searchVo.getTypeId()==LessonPlan.QI_TA_FAN_SI){
			lp.addCustomCondition(" and planType=:planType2 ",map);
		}
		lp.setSchoolYear(searchVo.getSchoolYear());
		if(searchVo.getTermId()!=null){
			lp.setTermId(searchVo.getTermId());
		}
		lp.addOrder(" crtDttm desc,lessonId asc");
		searchVo.pageSize(12);
		lp.addPage(searchVo.getPage());
		return lessonPlanService.findByPage(lp);
	}
	
	

}
