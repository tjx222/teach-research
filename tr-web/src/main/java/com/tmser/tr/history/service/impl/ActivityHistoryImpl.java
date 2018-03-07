/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.history.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmser.tr.activity.bo.Activity;
import com.tmser.tr.activity.service.ActivityService;
import com.tmser.tr.common.orm.SqlMapping;
import com.tmser.tr.common.page.PageList;
import com.tmser.tr.history.dao.HistoryDao;
import com.tmser.tr.history.service.ActivityHistory;
import com.tmser.tr.history.vo.SearchVo;
import com.tmser.tr.uc.SysRole;
import com.tmser.tr.uc.bo.UserSpace;
import com.tmser.tr.uc.service.UserSpaceService;
import com.tmser.tr.utils.StringUtils;

/**
 * <pre>
 * 集体备课历史查询
 * </pre>
 *
 * @author wangdawei
 * @version $Id: ActivityHistoryImpl.java, v 1.0 2016年5月26日 下午3:45:58 wangdawei Exp $
 */
@Service
@Transactional
public class ActivityHistoryImpl implements ActivityHistory {

	@Autowired
	private ActivityService activityService;
	@Autowired
	private HistoryDao historyDao;
	@Autowired
	private UserSpaceService userSpaceService;
	
	/**
	 * 获取已发布集体备课学年统计数
	 * @param userId
	 * @param code
	 * @param currentYear
	 * @return
	 * @see com.tmser.tr.history.service.ActivityHistory#getActivityHistoryCount(java.lang.Integer, java.lang.String, java.lang.Integer)
	 */
	@Override
	public Integer getActivityHistoryCount_issue(Integer userId,Integer currentYear) {
		SearchVo searchVo = new SearchVo();
		searchVo.setSchoolYear(currentYear);
		Activity activity = new Activity();
		activity.setOrganizeUserId(userId);
		activity.setStatus(1);
		activity.setSchoolYear(currentYear);
		return activityService.count(activity);
	}

	/**
	 * 获取已参与集体备课学年统计数
	 * @param userId
	 * @param code
	 * @param currentYear
	 * @return
	 * @see com.tmser.tr.history.service.ActivityHistory#getActivityHistoryCount_join(java.lang.Integer, java.lang.String, java.lang.Integer)
	 */
	@Override
	public Integer getActivityHistoryCount_join(Integer userId,Integer currentYear) {
		SearchVo searchVo = new SearchVo();
		searchVo.setSchoolYear(currentYear);
		searchVo.setUserId(userId);
		return historyDao.getActivityHistoryCount_join(searchVo);
	}

	/**
	 * 根据用户空间获取发起的集体备课集合
	 * @param searchVo
	 * @return
	 * @see com.tmser.tr.history.service.ActivityHistory#getHistoryActivityListBySpaceId_faqi(com.tmser.tr.history.vo.SearchVo)
	 */
	@Override
	public PageList<Activity> getHistoryActivityListBySpaceId_faqi(
			SearchVo searchVo) {
		Activity activity = new Activity();
		activity.setSpaceId(searchVo.getSpaceId());
		activity.setStatus(1);
		if(!StringUtils.isBlank(searchVo.getSearchStr())){
			activity.setActivityName(SqlMapping.LIKE_PRFIX+searchVo.getSearchStr()+SqlMapping.LIKE_PRFIX);
		}
		activity.setSchoolYear(searchVo.getSchoolYear());
		if(searchVo.getTermId()!=null){
			activity.setTerm(searchVo.getTermId());
		}
		activity.addOrder(" releaseTime desc ");
		searchVo.pageSize(12);
		activity.addPage(searchVo.getPage());
		return activityService.findByPage(activity);
	}

	/**
	 * 根据用户空间获取可参与的集体备课集合
	 * @param searchVo
	 * @return
	 * @see com.tmser.tr.history.service.ActivityHistory#getHistoryActivityListBySpaceId_canyu(com.tmser.tr.history.vo.SearchVo)
	 */
	@Override
	public PageList<Activity> getHistoryActivityListBySpaceId_canyu(
			SearchVo searchVo) {
		UserSpace userSpace = userSpaceService.findOne(searchVo.getSpaceId());
		Activity activity = new Activity();
		if(!StringUtils.isBlank(searchVo.getSearchStr())){
			activity.setActivityName(SqlMapping.LIKE_PRFIX+searchVo.getSearchStr()+SqlMapping.LIKE_PRFIX);
		}
		Integer sysRoleId = userSpace.getSysRoleId();
		activity.setOrgId(userSpace.getOrgId());
		activity.setStatus(1);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("spaceId", userSpace.getId());
		map.put("gradeId", "%"+userSpace.getGradeId()+"%");
		map.put("subjectId", "%"+userSpace.getSubjectId()+"%");
		map.put("userId", userSpace.getUserId());
		if(sysRoleId.intValue() == SysRole.XKZZ.getId().intValue()) {
			//学科组长
			activity.addCustomCondition(" and spaceId!=:spaceId ", map);
			activity.setSubjectIds(SqlMapping.LIKE_PRFIX + "," + userSpace.getSubjectId()+ "," + SqlMapping.LIKE_PRFIX);
		} else if(sysRoleId.intValue() == SysRole.NJZZ.getId().intValue()) {
			//年级组长
			activity.setGradeIds(SqlMapping.LIKE_PRFIX + "," + userSpace.getGradeId()+ "," + SqlMapping.LIKE_PRFIX);
		} else if(sysRoleId.intValue() == SysRole.BKZZ.getId().intValue()) {
			//备课组长
			activity.addCustomCondition(" and spaceId!=:spaceId ", map);
			activity.setGradeIds(SqlMapping.LIKE_PRFIX + "," + userSpace.getGradeId()+ "," + SqlMapping.LIKE_PRFIX);
			activity.setSubjectIds(SqlMapping.LIKE_PRFIX + "," + userSpace.getSubjectId()+ "," + SqlMapping.LIKE_PRFIX);
		} else if(sysRoleId.intValue() == SysRole.TEACHER.getId().intValue()){
			//老师
			activity.addCustomCondition(" and ((gradeIds like :gradeId and subjectIds like :subjectId) or (mainUserGradeId=:gradeId and mainUserSubjectId=:subjectId and mainUserId=:userId))", map);
		}
		activity.setSchoolYear(searchVo.getSchoolYear());
		if(searchVo.getTermId()!=null){
			activity.setTerm(searchVo.getTermId());
		}
		activity.addOrder("releaseTime desc");
		searchVo.pageSize(12);
		activity.addPage(searchVo.getPage());
		return activityService.findByPage(activity);
	}

	/**
	 * 根据用户空间获取发起的集体备课数量
	 * @param searchVo
	 * @return
	 * @see com.tmser.tr.history.service.ActivityHistory#getHistoryActivityCountBySpaceId_faqi(com.tmser.tr.history.vo.SearchVo)
	 */
	@Override
	public Integer getHistoryActivityCountBySpaceId_faqi(SearchVo searchVo) {
		Activity activity = new Activity();
		activity.setSpaceId(searchVo.getSpaceId());
		activity.setStatus(1);
		if(!StringUtils.isBlank(searchVo.getSearchStr())){
			activity.setActivityName(SqlMapping.LIKE_PRFIX+searchVo.getSearchStr()+SqlMapping.LIKE_PRFIX);
		}
		activity.setSchoolYear(searchVo.getSchoolYear());
		if(searchVo.getTermId()!=null){
			activity.setTerm(searchVo.getTermId());
		}
		activity.addOrder(" releaseTime desc ");
		return activityService.count(activity);
	}

	/**
	 * 根据用户空间获取已参与的集体备课数量
	 * @param searchVo
	 * @return
	 * @see com.tmser.tr.history.service.ActivityHistory#getHistoryActivityCountBySpaceId_canyu(com.tmser.tr.history.vo.SearchVo)
	 */
	@Override
	public Integer getHistoryActivityCountBySpaceId_canyu(SearchVo searchVo) {
		UserSpace userSpace = userSpaceService.findOne(searchVo.getSpaceId());
		Activity activity = new Activity();
		if(!StringUtils.isBlank(searchVo.getSearchStr())){
			activity.setActivityName(SqlMapping.LIKE_PRFIX+searchVo.getSearchStr()+SqlMapping.LIKE_PRFIX);
		}
		Integer sysRoleId = userSpace.getSysRoleId();
		activity.setOrgId(userSpace.getOrgId());
		activity.setStatus(1);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("spaceId", userSpace.getId());
		map.put("gradeId", "%"+userSpace.getGradeId()+"%");
		map.put("subjectId", "%"+userSpace.getSubjectId()+"%");
		map.put("userId", userSpace.getUserId());
		if(sysRoleId.intValue() == SysRole.XKZZ.getId().intValue()) {
			//学科组长
			activity.addCustomCondition(" and spaceId!=:spaceId ", map);
			activity.setSubjectIds(SqlMapping.LIKE_PRFIX + "," + userSpace.getSubjectId()+ "," + SqlMapping.LIKE_PRFIX);
		} else if(sysRoleId.intValue() == SysRole.NJZZ.getId().intValue()) {
			//年级组长
			activity.setGradeIds(SqlMapping.LIKE_PRFIX + "," + userSpace.getGradeId()+ "," + SqlMapping.LIKE_PRFIX);
		} else if(sysRoleId.intValue() == SysRole.BKZZ.getId().intValue()) {
			//备课组长
			activity.addCustomCondition(" and spaceId!=:spaceId ", map);
			activity.setGradeIds(SqlMapping.LIKE_PRFIX + "," + userSpace.getGradeId()+ "," + SqlMapping.LIKE_PRFIX);
			activity.setSubjectIds(SqlMapping.LIKE_PRFIX + "," + userSpace.getSubjectId()+ "," + SqlMapping.LIKE_PRFIX);
		} else if(sysRoleId.intValue() == SysRole.TEACHER.getId().intValue()){
			//老师
			activity.addCustomCondition(" and ((gradeIds like :gradeId and subjectIds like :subjectId) or (mainUserGradeId=:gradeId and mainUserSubjectId=:subjectId and mainUserId=:userId))", map);
		}
		activity.setSchoolYear(searchVo.getSchoolYear());
		if(searchVo.getTermId()!=null){
			activity.setTerm(searchVo.getTermId());
		}
		activity.addOrder("releaseTime desc");
		return activityService.count(activity);
	}

	

}
