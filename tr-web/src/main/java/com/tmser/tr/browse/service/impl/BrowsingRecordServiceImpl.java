/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.browse.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmser.tr.activity.bo.Activity;
import com.tmser.tr.activity.bo.SchoolActivity;
import com.tmser.tr.activity.service.ActivityService;
import com.tmser.tr.activity.service.SchoolActivityService;
import com.tmser.tr.annunciate.bo.JyAnnunciate;
import com.tmser.tr.annunciate.service.JyAnnunciateService;
import com.tmser.tr.browse.BaseResTypes;
import com.tmser.tr.browse.bo.BrowsingCount;
import com.tmser.tr.browse.bo.BrowsingRecord;
import com.tmser.tr.browse.dao.BrowsingCountDao;
import com.tmser.tr.browse.dao.BrowsingRecordDao;
import com.tmser.tr.browse.service.BrowsingRecordService;
import com.tmser.tr.common.dao.BaseDAO;
import com.tmser.tr.common.service.AbstractService;
import com.tmser.tr.common.utils.WebThreadLocalUtils;
import com.tmser.tr.lecturerecords.bo.LectureRecords;
import com.tmser.tr.lecturerecords.service.LectureRecordsService;
import com.tmser.tr.lessonplan.bo.LessonPlan;
import com.tmser.tr.plainsummary.bo.PlainSummary;
import com.tmser.tr.plainsummary.service.PlainSummaryService;
import com.tmser.tr.recordbag.bo.Recordbag;
import com.tmser.tr.recordbag.service.RecordbagService;
import com.tmser.tr.thesis.bo.Thesis;
import com.tmser.tr.thesis.service.ThesisService;
import com.tmser.tr.uc.bo.User;
import com.tmser.tr.uc.utils.SessionKey;
import com.tmser.tr.writelessonplan.service.LessonPlanService;
/**
 * 资源浏览记录 服务实现类
 * <pre>
 *
 * </pre>
 *
 * @author tmser
 * @version $Id: BrowsingRecord.java, v 1.0 2015-11-09 tmser Exp $
 */
@Service
@Transactional
public class BrowsingRecordServiceImpl extends AbstractService<BrowsingRecord, Integer> implements BrowsingRecordService {

	@Autowired
	private BrowsingRecordDao browsingRecordDao;
	@Autowired
	private BrowsingCountDao browsingCountDao;
	@Autowired
	private LessonPlanService lessonPlanService;
	@Autowired
	private RecordbagService recordbagService;
	@Autowired
	private PlainSummaryService plainSummaryService;
	@Autowired
	private ActivityService activityService;
	@Autowired
	private ThesisService thesisService;
	@Autowired
	private LectureRecordsService lectureRecordsService;
	@Autowired
	private SchoolActivityService schoolActivityService;
	@Autowired
	private JyAnnunciateService jyAnnunciateService;

	/**
	 * @return
	 * @see com.tmser.tr.common.service.BaseService#getDAO()
	 */
	@Override
	public BaseDAO<BrowsingRecord, Integer> getDAO() {
		return browsingRecordDao;
	}

	/**
	 * 添加资源浏览记录
	 * @param type
	 * @param resId
	 * @see com.tmser.tr.browse.service.BrowsingRecordService#addBrowseRecord(java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public void addBrowseRecord(Integer type, Integer resId) {
		if(type!=null && resId!=null){
			User user = (User)WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_USER);
			Boolean isNotSelf = true;
			int childType = 0;
			int orgId = 0;
			if(type.intValue()==BaseResTypes.BKZY){//备课资源
				LessonPlan obj = lessonPlanService.findOne(resId);
				if(obj.getUserId().intValue()==user.getId().intValue()){
					isNotSelf = false;
				}else{
					childType = obj.getPlanType();
					orgId = obj.getOrgId();
				}
			}else if(type.intValue()==BaseResTypes.CZDAD){//成长档案袋
				Recordbag obj = recordbagService.findOne(resId);
				if(obj.getCrtId().intValue()==user.getId().intValue()){
					isNotSelf = false;
				}else{
					childType = obj.getType();
					orgId = obj.getOrgId();
				}
			}else if(type.intValue()==BaseResTypes.JHZJ){//计划总结
				PlainSummary obj = plainSummaryService.findOne(resId);
				if(obj.getCrtId().intValue()==user.getId().intValue()){
					isNotSelf = false;
				}else{
					childType = obj.getCategory();
					orgId = obj.getOrgId();
				}
			}else if(type.intValue() == BaseResTypes.JTBK){//集体备课
				Activity obj = activityService.findOne(resId);
				if(obj.getCrtId().intValue()==user.getId().intValue()){
					isNotSelf = false;
				}else{
					childType = obj.getTypeId();
					orgId = obj.getOrgId();
				}
			}else if(type.intValue() == BaseResTypes.JXLW){//教学论文
				Thesis obj = thesisService.findOne(resId);
				if(obj.getCrtId().intValue()==user.getId().intValue()){
					isNotSelf = false;
				}
			
			}else if(type.intValue() == BaseResTypes.TKJL){//听课记录
				LectureRecords obj = lectureRecordsService.findOne(resId);
				if(obj.getCrtId().intValue()==user.getId().intValue()){
					isNotSelf = false;
				}else{
					childType = obj.getType();
					orgId = obj.getOrgId();
				}
			}else if(type.intValue() == BaseResTypes.XJJY){//校际教研
				SchoolActivity obj = schoolActivityService.findOne(resId);
				if(obj.getCrtId().intValue()==user.getId().intValue()){
					isNotSelf = false;
				}else{
					childType = obj.getTypeId();
					orgId = obj.getOrgId();
				}
			}else if(type.intValue() == BaseResTypes.TZGG){//通知公告
				//点击量不做过滤
				JyAnnunciate obj = jyAnnunciateService.findOne(resId);
				orgId = obj.getOrgId();
			}
			if(isNotSelf){
				//浏览记录添加
				BrowsingRecord br = new BrowsingRecord();
				br.setType(type);
				br.setResId(resId);
				br.setUserId(user.getId());
				BrowsingRecord oldBr = browsingRecordDao.getOne(br);
				if(oldBr!=null){
					oldBr.setCount(oldBr.getCount()+1);
					oldBr.setLastTime(new Date());
					browsingRecordDao.update(oldBr);
				}else{
					br.setChildType(childType);
					br.setOrgId(orgId);
					br.setResShare(true);
					br.setCount(1);
					br.setLastTime(new Date());
					browsingRecordDao.insert(br);
				}
				//浏览总数添加
				BrowsingCount bc = new BrowsingCount();
				bc.setType(type);
				bc.setResId(resId);
				BrowsingCount oldBc = browsingCountDao.getOne(bc);
				if(oldBc!=null){
					oldBc.setCount(oldBc.getCount()+1);
					browsingCountDao.update(oldBc);
				}else{
					bc.setChildType(childType);
					bc.setOrgId(orgId);
					bc.setResShare(true);
					bc.setCount(1);
					browsingCountDao.insert(bc);
				}
			}
		}
	}

	/**
	 * 修改浏览记录资源的分享状态
	 * @param type
	 * @param resId
	 * @param isShare
	 * @see com.tmser.tr.browse.service.BrowsingRecordService#updateBrowseRecordShare(java.lang.Integer, java.lang.Integer, java.lang.Boolean)
	 */
	@Override
	public void updateBrowseRecordShare(Integer type, Integer resId,Boolean isShare) {
		browsingRecordDao.update(type,resId,isShare);
		BrowsingCount bc = new BrowsingCount();
		bc.setType(type);
		bc.setResId(resId);
		bc = browsingCountDao.getOne(bc);
		if(bc!=null){
			bc.setResShare(isShare);
			browsingCountDao.update(bc);
		}
	}
}