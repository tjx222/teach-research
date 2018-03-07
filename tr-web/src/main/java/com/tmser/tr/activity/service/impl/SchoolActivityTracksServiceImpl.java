/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.activity.service.impl;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.tmser.tr.activity.bo.ActivityTracks;
import com.tmser.tr.activity.bo.SchoolActivity;
import com.tmser.tr.activity.bo.SchoolActivityTracks;
import com.tmser.tr.activity.dao.SchoolActivityDao;
import com.tmser.tr.activity.dao.SchoolActivityTracksDao;
import com.tmser.tr.activity.service.ActivityCoordinateControlService;
import com.tmser.tr.activity.service.SchoolActivityTracksService;
import com.tmser.tr.common.dao.BaseDAO;
import com.tmser.tr.common.service.AbstractService;
import com.tmser.tr.common.utils.WebThreadLocalUtils;
import com.tmser.tr.manage.resources.bo.Resources;
import com.tmser.tr.manage.resources.service.ResourcesService;
import com.tmser.tr.uc.bo.UserSpace;
import com.tmser.tr.uc.utils.SessionKey;
import com.zhuozhengsoft.pageoffice.FileSaver;
import com.zhuozhengsoft.pageoffice.OfficeVendorType;
import com.zhuozhengsoft.pageoffice.OpenModeType;
import com.zhuozhengsoft.pageoffice.PageOfficeCtrl;

/**
 * 校际教研教案修改留痕 服务实现类
 * 
 * <pre>
 *
 * </pre>
 *
 * @author wangdawei
 * @version $Id: SchoolActivityTracks.java, v 1.0 2015-05-28 wangdawei Exp $
 */
@Service
@Transactional
public class SchoolActivityTracksServiceImpl extends AbstractService<SchoolActivityTracks, Integer>
		implements SchoolActivityTracksService {
	private static final Logger logger = LoggerFactory.getLogger(SchoolActivityTracksServiceImpl.class);

	@Autowired
	private SchoolActivityTracksDao schoolActivityTracksDao;
	@Autowired
	private ResourcesService resourcesService;
	@Autowired
	private SchoolActivityDao schoolActivityDao;
	@Autowired
	private ActivityCoordinateControlService activityCoordinateControlService;

	/**
	 * @return
	 * @see com.tmser.tr.common.service.BaseService#getDAO()
	 */
	@Override
	public BaseDAO<SchoolActivityTracks, Integer> getDAO() {
		return schoolActivityTracksDao;
	}

	/**
	 * 打开主备教案对应的修改教案
	 * 
	 * @param planId
	 * @param activityId
	 * @param request
	 * @param editType
	 * @return
	 * @see com.tmser.tr.activity.service.SchoolActivityTracksService#openTracksFileOfLessonPlan(java.lang.Integer,
	 *      java.lang.Integer, javax.servlet.http.HttpServletRequest,
	 *      java.lang.Integer)
	 */
	@Override
	public SchoolActivityTracks openTracksFileOfLessonPlan(Integer planId, Integer activityId,
			HttpServletRequest request, Integer editType, Model m) {
		PageOfficeCtrl poc = new PageOfficeCtrl(request);
		poc.setServerPage(request.getContextPath() + "/poserver.zz");
		SchoolActivityTracks activityTracks = new SchoolActivityTracks();
		activityTracks.setActivityId(activityId);
		activityTracks.setPlanId(planId);
		activityTracks.setEditType(editType);
		if (editType == SchoolActivityTracks.YIJIAN) {
			// 获取当前用户空间
			UserSpace userSpace = (UserSpace) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE);
			activityTracks.setUserId(userSpace.getUserId());
		}
		activityTracks = schoolActivityTracksDao.getOne(activityTracks);
		if (activityTracks == null) { // 如果主备教案对应的修改教案不存在，则打开原主备教案
			// 设置控件标题
			poc.setCaption("留痕模式修改主备教案");
			activityTracks = schoolActivityTracksDao.get(planId);
		} else {
			// 设置控件标题
			poc.setCaption("修改教案留痕");
		}
		// 如果是整理，则需要判定发起人和参与人的协同控制
		SchoolActivity schoolActivity = schoolActivityDao.get(activityId);
		if (editType.intValue() == SchoolActivityTracks.ZHENGLI.intValue()) {
			if (schoolActivity.getIsSend()) {
				openWordFileByRevision(activityTracks.getResId(), poc, OpenModeType.docReadOnly);
				m.addAttribute("openModeType", "docReadOnly");
			} else {
				if (activityCoordinateControlService.getPowerOfZhengli(activityTracks.getResId())) {
					openWordFileByRevision(activityTracks.getResId(), poc, OpenModeType.docRevisionOnly);
					m.addAttribute("openModeType", "docRevisionOnly");
				} else {
					openWordFileByRevision(activityTracks.getResId(), poc, OpenModeType.docReadOnly);
					m.addAttribute("openModeType", "docReadOnly");
				}
			}
		} else if (editType.intValue() == SchoolActivityTracks.YIJIAN.intValue()) {
			openWordFileByRevision(activityTracks.getResId(), poc, OpenModeType.docRevisionOnly);
			m.addAttribute("openModeType", "docRevisionOnly");
		}
		m.addAttribute("resId", activityTracks.getResId());
		m.addAttribute("isSend", schoolActivity.getIsSend());
		return activityTracks;
	}

	/**
	 * 打开word文件
	 * 
	 * @param resId
	 *            资源id
	 * @param poc
	 * @param omt
	 *            打开模式
	 */
	@Override
	public void openWordFileByRevision(String resId, PageOfficeCtrl poc, OpenModeType omt) {
		UserSpace userSpace = (UserSpace) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE);
		// 设置word文件的保存页面
		poc.setSaveFilePage("saveLessonPlanTracks");
		// 设置文档加载后执行的js方法
		poc.setJsFunction_AfterDocumentOpened("AfterDocumentOpened");
		poc.setMenubar(false);// 不显示菜单栏
		poc.setOfficeToolbars(false);// 不显示office工具栏
		poc.setCustomToolbar(false);// 不显示自定义的工具栏
		// 调用接口获取下载到本地的临时文件路径
		String localResPath = resourcesService.viewResources(resId);
		File f = new File(localResPath);
		if (f.isAbsolute() && localResPath.startsWith("/")) {
			localResPath = "file://" + localResPath;
		}
		poc.setOfficeVendor(OfficeVendorType.AutoSelect);
		poc.webOpen(localResPath, omt, userSpace.getUsername());
		poc.setTagId("PageOfficeCtrl1");// 此行必需
	}

	/**
	 * @param planId
	 * @param activityId
	 * @return
	 * @see com.tmser.tr.activity.service.SchoolActivityTracksService#getActivityTracks_yijian(java.lang.Integer,
	 *      java.lang.Integer)
	 */
	@Override
	public List<SchoolActivityTracks> getActivityTracks_yijian(Integer planId, Integer activityId) {
		SchoolActivityTracks activityTracks = new SchoolActivityTracks();
		activityTracks.setActivityId(activityId);
		activityTracks.setEditType(SchoolActivityTracks.YIJIAN);
		activityTracks.buildCondition(" and (planId = :planid or planId = 0)").put("planid", planId);
		activityTracks.addOrder(" userId,planId desc");
		return findAll(activityTracks);
	}

	/**
	 * @param activityId
	 * @return
	 * @see com.tmser.tr.activity.service.SchoolActivityTracksService#getActivityTracks_zhengli(java.lang.Integer)
	 */
	@Override
	public List<SchoolActivityTracks> getActivityTracks_zhengli(Integer activityId) {
		SchoolActivityTracks activityTracks = new SchoolActivityTracks();
		activityTracks.setActivityId(activityId);
		activityTracks.setEditType(SchoolActivityTracks.ZHENGLI);
		activityTracks.addOrder(" userId,planId desc");
		return findAll(activityTracks);
	}

	/**
	 * 保存教案的修改教案
	 * 
	 * @param fs
	 * @return
	 * @see com.tmser.tr.activity.service.SchoolActivityTracksService#saveLessonPlanTracks(com.zhuozhengsoft.pageoffice.FileSaver)
	 */
	@Override
	public Integer saveLessonPlanTracks(FileSaver fs) {
		UserSpace userSpace = (UserSpace) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE);
		Integer schoolYear = (Integer) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SCHOOLYEAR);// 学年
		String editType = fs.getFormField("editType");
		String planId = fs.getFormField("planId");
		String activityId = fs.getFormField("activityId");
		// 验证：如果已结束则不可再保存修改留痕
		// if(Integer.valueOf(editType).intValue()==ActivityTracks.YIJIAN.intValue()){
		SchoolActivity activity = schoolActivityDao.get(Integer.valueOf(activityId));
		if (activity.getIsOver()) {
			fs.setCustomSaveResult("isOver");
			return null;
		}
		// }
		// 已发送整理教案后不可再整理
		if (Integer.valueOf(editType).intValue() == ActivityTracks.ZHENGLI.intValue()) {
			if (activity.getIsSend()) {
				fs.setCustomSaveResult("isSend");
				return null;
			}
		}
		SchoolActivityTracks lessonPlan = schoolActivityTracksDao.get(Integer.valueOf(planId));
		if (lessonPlan == null) { // 不存在则说明活动的主备教案被发起人修改了
			fs.setCustomSaveResult("zhubeiIsEdit");
			return null;
		}
		String trackId = fs.getFormField("trackId");
		String relativeUrl = File.separator + "schoolActivityTracks" + File.separator + String.valueOf(schoolYear)
				+ File.separator + activityId;
		if ("".equals(trackId)) {// 修改教案id为空，则新增
			// 调用上传接口将文件上传返回相对路径
			Resources resources = resourcesService.saveResources(fs.getFileStream(), fs.getFileName(),
					Long.valueOf(fs.getFileSize()), relativeUrl);
			if (resources != null) {
				SchoolActivityTracks activityTracks = new SchoolActivityTracks();
				activityTracks.setActivityId(Integer.valueOf(activityId));
				activityTracks.setPlanId(Integer.valueOf(planId));
				activityTracks.setEditType(Integer.valueOf(editType));
				activityTracks.setPlanName(lessonPlan.getPlanName());
				activityTracks.setUserId(userSpace.getUserId());
				activityTracks.setUserName(userSpace.getUsername());
				activityTracks.setSubjectId(userSpace.getSubjectId());
				activityTracks.setGradeId(userSpace.getGradeId());
				activityTracks.setSpaceId(userSpace.getId());
				activityTracks.setHoursId(lessonPlan.getHoursId());
				activityTracks.setOrderValue(lessonPlan.getOrderValue());
				activityTracks.setSchoolYear(schoolYear);
				activityTracks.setResId(resources.getId());
				activityTracks.setLessonId(lessonPlan.getLessonId());
				activityTracks.setCrtId(userSpace.getUserId());
				activityTracks.setOrgId(userSpace.getOrgId());
				activityTracks.setLastupId(userSpace.getUserId());
				activityTracks.setCrtDttm(new Date());
				activityTracks.setLastupDttm(new Date());
				activityTracks = save(activityTracks);
				logger.info("成功保存校际教研的修改教案！ 操作人id：" + userSpace.getUserId());
				return activityTracks.getId();
			} else {
				return null;
			}
		} else {// 修改
			SchoolActivityTracks activityTracks = findOne(Integer.valueOf(trackId));
			Resources resources = resourcesService.updateResources(fs.getFileStream(), fs.getFileName(),
					Long.valueOf(fs.getFileSize()), relativeUrl, activityTracks.getResId());
			if (resources != null) {
				return activityTracks.getId();
			} else {
				return null;
			}
		}
	}

	/**
	 * 获取活动下的整理或意见留痕数量
	 * 
	 * @param id
	 * @return
	 * @see com.tmser.tr.activity.service.SchoolActivityTracksService#getTrackCountByActivityId(java.lang.Integer)
	 */
	@Override
	public Integer getTrackCountByActivityId(Integer id) {
		SchoolActivityTracks tracks = new SchoolActivityTracks();
		tracks.setActivityId(id);
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("zhengli", SchoolActivityTracks.ZHENGLI);
		paramMap.put("yijian", SchoolActivityTracks.YIJIAN);
		tracks.addCustomCondition(" and (editType = :zhengli or editType = :yijian)", paramMap);
		return schoolActivityTracksDao.count(tracks);
	}

	/**
	 * 获取活动的原始主备教案集合
	 * 
	 * @param id
	 * @return
	 * @see com.tmser.tr.activity.service.SchoolActivityTracksService#getActivityTracks_zhubei(java.lang.Integer)
	 */
	@Override
	public List<SchoolActivityTracks> getActivityTracks_zhubei(Integer id) {
		SchoolActivityTracks activityTracks = new SchoolActivityTracks();
		activityTracks.setActivityId(id);
		activityTracks.setEditType(SchoolActivityTracks.ZHUBEI);
		activityTracks.addOrder(" orderValue asc");
		return findAll(activityTracks);
	}

}
