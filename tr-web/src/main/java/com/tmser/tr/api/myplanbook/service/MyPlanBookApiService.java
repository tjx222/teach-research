/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.api.myplanbook.service;

import java.util.List;
import java.util.Map;

import com.tmser.tr.common.vo.Result;
import com.tmser.tr.lessonplan.bo.LessonInfo;
import com.tmser.tr.lessonplan.bo.LessonPlan;
import com.tmser.tr.manage.meta.bo.Book;

/**
 * <pre>
 * 离线端 我的备课本service
 * </pre>
 *
 * @author huyanfang
 * @version $Id: MyPlanBookService.java, v 1.0 2015年4月14日 下午3:28:45 huyanfang
 *          Exp $
 */
public interface MyPlanBookApiService {

	/**
	 * 移动离线端-获取备课本信息
	 */
	List<Map<String, Object>> getLessonPlan(Integer userid);

	/**
	 * 移动离线端-获取备课本意见
	 */
	List<Map<String, Object>> getLessonInfo(Integer userid);

	/**
	 * 移动离线端-保存备课信息
	 */
	Result saveMyPlanBook(String lessonplan);

	/**
	 * 移动离线端-分享备课信息
	 */
	Result shareMyPlanBook(Integer planid);

	/**
	 * 移动离线端-删除备课信息
	 */
	Result deleteMyPlanBook(Integer planid);

	/**
	 * 新增课题信息
	 * 
	 * @param lessonId
	 * @param book
	 * @return
	 */
	LessonInfo saveLessonInfo(LessonPlan lp, Book book);

	/**
	 * 批量提交备课资源
	 * 
	 * @param lessonPlanIdsStr
	 * @return
	 * @throws Exception
	 */
	Result submitLessonPlansByIdStr(String lessonPlanIdsStr);

	/**
	 * 取消批量提交备课资源
	 * 
	 * @param lessonPlanIdsStr
	 * @return
	 * @throws Exception
	 */
	Result unSubmitLessonPlansByIdStr(String lessonPlanIdsStr);

	/**
	 * 获得备课资源最后更新时间
	 * 
	 * @param id
	 * @return
	 */
	Map<String, Object> resLastuptime(Integer id);

	/**
	 * 返回我的备课本查阅意见、听课意见是否有更新
	 * @param userid
	 * @param lessonid
	 * @return
	 */
	Map<String, Object> getupdatestate(Integer userid, String lessonid);

	/**
	 * 修改我的备课本查阅意见、听课意见是否有更新
	 * @param userid
	 * @param lessonid
	 * @param type
	 * @return
	 */
	Result updatestate(Integer userid, String lessonid, String type);
}