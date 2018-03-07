/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.activity.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

import com.tmser.tr.activity.bo.SchoolActivityTracks;
import com.tmser.tr.common.service.BaseService;
import com.zhuozhengsoft.pageoffice.FileSaver;
import com.zhuozhengsoft.pageoffice.OpenModeType;
import com.zhuozhengsoft.pageoffice.PageOfficeCtrl;

/**
 * 校际教研教案修改留痕 服务类
 * <pre>
 *
 * </pre>
 *
 * @author wangdawei
 * @version $Id: SchoolActivityTracks.java, v 1.0 2015-05-28 wangdawei Exp $
 */

public interface SchoolActivityTracksService extends BaseService<SchoolActivityTracks, Integer>{

	/**
	 * 打开主备教案对应的修改教案
	 * @param planId
	 * @param activityId
	 * @param request
	 * @param editType
	 * @return
	 */
	SchoolActivityTracks openTracksFileOfLessonPlan(Integer planId, Integer activityId,
			HttpServletRequest request, Integer editType,Model m);

	/**
	 * 已某方式打开word文件
	 * @param resId
	 * @param poc
	 * @param docreadonly
	 */
	void openWordFileByRevision(String resId, PageOfficeCtrl poc,
			OpenModeType docreadonly);

	/**
	 * 获取主备教案的意见留痕集合
	 * @param planId
	 * @param activityId
	 * @return
	 */
	List<SchoolActivityTracks> getActivityTracks_yijian(Integer planId,
			Integer activityId);

	/**
	 * 获取主备教案的整理留痕集合
	 * @param activityId
	 * @return
	 */
	List<SchoolActivityTracks> getActivityTracks_zhengli(Integer activityId);

	/**
	 * 保存教案的修改教案
	 * @param fs
	 * @return
	 */
	Integer saveLessonPlanTracks(FileSaver fs);

	/**
	 * 获取活动下的整理或意见留痕数量
	 * @param id
	 * @return
	 */
	Integer getTrackCountByActivityId(Integer id);

	/**
	 * 获取校际教研活动的原始主备教案
	 * @param id
	 * @return
	 */
	List<SchoolActivityTracks> getActivityTracks_zhubei(Integer id);

}
