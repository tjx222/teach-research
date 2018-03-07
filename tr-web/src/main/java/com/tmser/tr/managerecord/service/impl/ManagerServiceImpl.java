/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.managerecord.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmser.tr.activity.bo.Activity;
import com.tmser.tr.activity.bo.SchoolActivity;
import com.tmser.tr.activity.dao.ActivityDao;
import com.tmser.tr.activity.dao.SchoolActivityDao;
import com.tmser.tr.check.bo.CheckInfo;
import com.tmser.tr.check.dao.CheckInfoDao;
import com.tmser.tr.common.ResTypeConstants;
import com.tmser.tr.common.dao.AbstractQuery;
import com.tmser.tr.common.utils.WebThreadLocalUtils;
import com.tmser.tr.lecturerecords.bo.LectureRecords;
import com.tmser.tr.lecturerecords.dao.LectureRecordsDao;
import com.tmser.tr.managerecord.service.CheckRecordContainer;
import com.tmser.tr.managerecord.service.CheckRecordService;
import com.tmser.tr.managerecord.service.ManagerService;
import com.tmser.tr.managerecord.service.ManagerVO;
import com.tmser.tr.plainsummary.bo.PlainSummary;
import com.tmser.tr.plainsummary.dao.PlainSummaryDao;
import com.tmser.tr.uc.bo.UserSpace;
import com.tmser.tr.uc.utils.SessionKey;

/**
 * <pre>
 *
 * </pre>
 *
 * @author sysc
 * @version $Id: managerServiceImpl.java, v 1.0 2015年5月7日 上午11:26:21 sysc Exp $
 */
@Service
@Transactional
public class ManagerServiceImpl extends AbstractQuery implements ManagerService {

	@Autowired
	private CheckInfoDao checkInfoDao;

	@Autowired
	private PlainSummaryDao plainDao;

	@Autowired
	private LectureRecordsDao lectureRecordsDao;

	@Autowired
	private ActivityDao activityDao;

	@Autowired
	private SchoolActivityDao schoolActivityDao;

	/**
	 * @param grade
	 * @param term
	 * @param subject
	 * @return
	 * @see com.tmser.tr.managerecord.service.ManagerService#findRecordList(int,
	 *      int, int)
	 */
	@Override
	public List<ManagerVO> findRecordList(int grade, int subject, int term) {
		List<ManagerVO> mapList = new ArrayList<ManagerVO>();
		Set<CheckRecordService> serviceSet = CheckRecordContainer.getCheckRecordServiceSet();
		for (CheckRecordService crs : serviceSet) {
			mapList.add(crs.getCheckRecord(grade, term, subject));
		}
		return mapList;
	}

	/**
	 * @param term
	 * @return
	 * @see com.tmser.tr.managerecord.service.ManagerService#findMangerList(int)
	 */
	@Override
	public List<ManagerVO> findMangerList(Integer term) {
		UserSpace userSpace = (UserSpace) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE);
		Integer schoolYear = (Integer) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SCHOOLYEAR);
		List<ManagerVO> mapList = new ArrayList<ManagerVO>();
		ManagerVO planManager = new ManagerVO();
		PlainSummary plan = new PlainSummary();
		plan.setOrgId(userSpace.getOrgId());
		plan.setTerm(term);
		plan.setSchoolYear(schoolYear);
		plan.setPhaseId(userSpace.getPhaseId());
		int schoolCount = plainDao.count(plan);// 计划总结全校数
		plan.setUserId(userSpace.getUserId());
		int writeCount = plainDao.count(plan);// 计划总结撰写数
		plan.setIsShare(1);
		int punishCount = plainDao.count(plan);// 计划总结分享数

		planManager.setSubmitNum(schoolCount);
		planManager.setCheckNum(writeCount);
		planManager.setShareNum(punishCount);
		planManager.setName("计划总结");
		planManager.setUri("planDetail?listType=0");
		mapList.add(planManager);

		ManagerVO planManager1 = new ManagerVO();
		LectureRecords lec = new LectureRecords();
		lec.setIsDelete(false);
		lec.setTerm(term);
		lec.setOrgId(userSpace.getOrgId());
		lec.setIsEpub(1);
		lec.setSchoolYear(schoolYear);
		lec.setPhaseId(userSpace.getPhaseId());
		int schoolCount1 = lectureRecordsDao.count(lec);// 听课记录撰写数

		lec.setLecturepeopleId(userSpace.getUserId());
		int writeCount1 = lectureRecordsDao.count(lec);// 听课记录撰写数
		lec.setIsShare(1);

		int punishCount1 = lectureRecordsDao.count(lec);// 听课记录分享数
		planManager1.setSubmitNum(schoolCount1);
		planManager1.setCheckNum(writeCount1);
		planManager1.setShareNum(punishCount1);
		planManager1.setName("听课记录");
		planManager1.setUri("lecturedetailed?listType=0");
		mapList.add(planManager1);
		return mapList;
	}

	/**
	 * @param term
	 * @return
	 * @see com.tmser.tr.managerecord.service.ManagerService#findActivityList(int)
	 */
	@Override
	public List<ManagerVO> findActivityList(Integer term) {
		UserSpace userSpace = (UserSpace) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE);
		Integer schoolYear = (Integer) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SCHOOLYEAR);
		List<ManagerVO> voList = new ArrayList<ManagerVO>();
		ManagerVO am = new ManagerVO();

		Activity activity = new Activity();
		activity.addAlias("a");
		activity.addCustomCulomn("a.id");
		activity.setTerm(term);
		activity.setOrgId(userSpace.getOrgId());
		activity.setStatus(1);
		activity.setPhaseId(userSpace.getPhaseId());
		activity.setSchoolYear(schoolYear);
		int allCount = activityDao.count(activity);// 全校集体备课活动数
		activity.setOrganizeUserId(userSpace.getUserId());// 用户Id

		int myCount = activityDao.count(activity);// 发起集体备课活动数

		activity.setIsShare(true);
		int shareCount = activityDao.count(activity);// 分享集体备课活动数

		activity.setOrganizeUserId(null);
		activity.setIsShare(null);
		activity.buildCondition(" and EXISTS(select b.id from Discuss b where b.activityId = a.id and b.typeId=:typeId and b.crtId=:crtId LIMIT 0,1)")
		.put("typeId", ResTypeConstants.ACTIVITY)
		.put("crtId", userSpace.getUserId());
		int joinCount = activityDao.count(activity);   
		
		am.setSubmitNum(allCount); // 总数
		am.setCheckNum(myCount);// 个人发起数
		am.setCommentNum(joinCount);// 个人参与数

		am.setShareNum(shareCount); // 分享数

		am.setName("集体备课");
		am.setUri("activity");
		voList.add(am);

		ManagerVO am2 = new ManagerVO();
		
		SchoolActivity schoolActivity = new SchoolActivity();
		schoolActivity.addAlias("a");
		schoolActivity.addCustomCulomn("a.id");
		schoolActivity.setTerm(term);
		schoolActivity.setStatus(1);
		schoolActivity.setOrgId(userSpace.getOrgId());
		schoolActivity.setPhaseId(userSpace.getPhaseId());
		schoolActivity.setSchoolYear(schoolYear);
		int schoolAllCount = schoolActivityDao.count(schoolActivity);// 校际活动数

		schoolActivity.setOrganizeUserId(userSpace.getUserId());// 用户Id
		int mySchoolCount = schoolActivityDao.count(schoolActivity);// 发起校际活动数

		schoolActivity.setIsShare(true);
		int shareSchoolCount = schoolActivityDao.count(schoolActivity);// 分享校际活动数

		schoolActivity.setOrganizeUserId(null);
		schoolActivity.setIsShare(null);
		schoolActivity.buildCondition(" and EXISTS(select b.id from Discuss b where b.activityId = a.id and b.typeId=:typeId and b.crtId=:crtId LIMIT 0,1)")
		.put("typeId", ResTypeConstants.SCHOOLTEACH)
		.put("crtId", userSpace.getUserId());

		int jnCount = schoolActivityDao.count(schoolActivity);

		am2.setSubmitNum(schoolAllCount); // 校际总数
		am2.setCheckNum(mySchoolCount);// 个人校际发起数
		am2.setCommentNum(jnCount);// 校际参与数
		am2.setShareNum(shareSchoolCount); // 校际分享数
		am2.setName("校际教研");
		am2.setUri("school");
		voList.add(am2);

		return voList;
	}

	/**
	 * @param sql
	 * @param id
	 * @return
	 * @see com.tmser.tr.managerecord.service.ManagerService#count(java.lang.String,
	 *      int)
	 */
	@Override
	public void index(Map<String, Integer> map) {
		UserSpace userSpace = (UserSpace) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE);
		Integer schoolYear = (Integer) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SCHOOLYEAR);
		List<Integer> resTypes = new ArrayList<Integer>();
		Set<CheckRecordService> serviceSet = CheckRecordContainer.getCheckRecordServiceSet();
		for (CheckRecordService crs : serviceSet) {
			for (Integer type : crs.getType()) {
				resTypes.add(type);
			}
		}
		Map<String, Object> paramMap = new HashMap<String, Object>(2);
		paramMap.put("resTypes", resTypes);

		CheckInfo model = new CheckInfo();
		model.setSchoolYear(schoolYear);
		model.setUserId(userSpace.getUserId());
		model.setPhase(userSpace.getPhaseId());
		model.addCustomCondition("and resType in(:resTypes) ", paramMap);
		int checkCount = checkInfoDao.count(model);// 查阅数

		model.setHasOptinion(true);
		int optionCount = checkInfoDao.count(model);// 查阅意见数

		PlainSummary plan = new PlainSummary();
		plan.setUserId(userSpace.getUserId());
		plan.setSchoolYear(schoolYear);
		plan.setPhaseId(userSpace.getPhaseId());
		int writeCount = plainDao.count(plan);// 计划总结撰写数

		plan.setIsShare(1);
		int punishCount = plainDao.count(plan);// 计划总结分享数

		LectureRecords lec = new LectureRecords();
		lec.setIsDelete(false);
		lec.setLecturepeopleId(userSpace.getUserId());
		lec.setIsEpub(1);
		lec.setSchoolYear(schoolYear);
		lec.setPhaseId(userSpace.getPhaseId());
		int writeCount1 = lectureRecordsDao.count(lec);// 听课记录撰写数

		lec.setIsShare(1);
		int punishCount1 = lectureRecordsDao.count(lec);// 听课记录分享数

		Activity activity = new Activity();
		activity.addAlias("a");
		activity.addCustomCulomn("a.id");
		activity.setOrganizeUserId(userSpace.getUserId());// 用户Id
		activity.setStatus(1);
		activity.setPhaseId(userSpace.getPhaseId());
		activity.setSchoolYear(schoolYear);
		int myCount = activityDao.count(activity);// 发起集体备课活动数

		activity.setIsShare(true);
		int shareCount = activityDao.count(activity);// 分享集体备课活动数

		activity.setOrganizeUserId(null);
		activity.setIsShare(null);
		activity.buildCondition(" and EXISTS(select b.id from Discuss b where b.activityId = a.id and b.typeId=:typeId and b.crtId=:crtId LIMIT 0,1)")
		.put("typeId", ResTypeConstants.ACTIVITY)
		.put("crtId", userSpace.getUserId());
		
		int joinCount = activityDao.count(activity);     

		SchoolActivity schoolActivity = new SchoolActivity();
		schoolActivity.addAlias("a");
		schoolActivity.addCustomCulomn("a.id");
		schoolActivity.setStatus(1);
		schoolActivity.setOrgId(userSpace.getOrgId());

		schoolActivity.setOrganizeUserId(userSpace.getUserId());// 用户Id
		schoolActivity.setSchoolYear(schoolYear);
		schoolActivity.setPhaseId(userSpace.getPhaseId());
		int mySchoolCount = schoolActivityDao.count(schoolActivity);// 发起校际活动数

		schoolActivity.setIsShare(true);
		int shareSchoolCount = schoolActivityDao.count(schoolActivity);// 分享校际活动数
		
		schoolActivity.setOrganizeUserId(null);
		schoolActivity.setIsShare(null);
		schoolActivity.buildCondition(" and EXISTS(select b.id from Discuss b where b.activityId = a.id and b.typeId=:typeId and b.crtId=:crtId LIMIT 0,1)")
		.put("typeId", ResTypeConstants.SCHOOLTEACH)
		.put("crtId", userSpace.getUserId());

		int jnCount = schoolActivityDao.count(schoolActivity);

		map.put("checkCount", checkCount);
		map.put("optionCount", optionCount);
		map.put("writeCount", writeCount + writeCount1);
		map.put("punishCount", punishCount + punishCount1);
		map.put("myCount", myCount + mySchoolCount);
		map.put("shareCount", shareCount + shareSchoolCount);
		map.put("jionCount", joinCount + jnCount);
	}
}
