/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.activity.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

import com.tmser.tr.activity.bo.ActivityTracks;
import com.tmser.tr.common.service.BaseService;
import com.zhuozhengsoft.pageoffice.FileSaver;
import com.zhuozhengsoft.pageoffice.OpenModeType;
import com.zhuozhengsoft.pageoffice.PageOfficeCtrl;

/**
 * 教案修改留痕 服务类
 * <pre>
 *
 * </pre>
 *
 * @author wangdawei
 * @version $Id: ActivityTracks.java, v 1.0 2015-04-03 wangdawei Exp $
 */

public interface ActivityTracksService extends BaseService<ActivityTracks, Integer>{

	/**
	 * 获取这里好的集备教案
	 * @param id
	 * @return
	 */
	List<ActivityTracks> getActivityTracks_zhengli(Integer id);

	/**
	 * 打开主备教案对应的修改意见教案
	 * @param planId 教案id
	 * @param activityId 集备id
	 * @param request PageOfficeCtrl
	 * @param editType 修改类型， 在ActivityTracks下，YIJIAN：教案修改意见  ZHENGLI：教案整理
	 * @return 
	 */
	ActivityTracks openTracksFileOfLessonPlan(Integer planId, Integer activityId,
			HttpServletRequest request, Integer editType,Model m);

	/**
	 * 获取某主备教案的意见教案集合
	 * @param planId
	 * @param activityId 
	 * @return
	 */
	List<ActivityTracks> getActivityTracks_yijian(Integer planId, Integer activityId);

	/**
	 * 保存教案的修改教案
	 * @param fs
	 * @return
	 */
	Integer saveLessonPlanTracks(FileSaver fs);

	/**
	 * 打卡word文件
	 * @param resId
	 * @param poc
	 * @param omt 
	 */
	void openWordFileByRevision(String resId, PageOfficeCtrl poc,OpenModeType omt);

	/**
	 * 获取活动下的整理或意见留痕数量
	 * @param id
	 * @return
	 */
	Integer getTrackCountByActivityId(Integer id);

	/**
	 * 获取集备活动的原始主备教案
	 * @param id
	 * @return
	 */
	List<ActivityTracks> getActivityTracks_zhubei(Integer id);
}
