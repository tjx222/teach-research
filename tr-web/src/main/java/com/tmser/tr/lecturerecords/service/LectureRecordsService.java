/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.lecturerecords.service;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.tmser.tr.common.service.BaseService;
import com.tmser.tr.lecturerecords.bo.LectureRecords;
import com.tmser.tr.lessonplan.bo.LessonInfo;
import com.tmser.tr.manage.meta.vo.BookLessonVo;

/**
 * 听课记录 服务类
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: LectureRecords.java, v 1.0 2015-03-30 Generate Tools Exp $
 */

public interface LectureRecordsService extends BaseService<LectureRecords, Integer>{
	//设置增加评论或者回复时候回调函数
	List<ActionCallback> callbackList = new CopyOnWriteArrayList<ActionCallback>();

	/**
	 * 保存或者修改听课记录
	 * @param commentInfo
	 * @return
	 */
	public void saveOrupdateLectureRecords(LectureRecords lectureRecords);

	/**
	 * 删除或者分享听课记录
	 * @param commentInfo
	 * @return
	 */
	LectureRecords deleteOrShare(Integer id,String state);

	/**
	 * 得到书籍目录排序集合
	 * @param lessonInfoList
	 * @return
	 */
	public List<BookLessonVo> getBookCatalogs(List<LessonInfo> lessonInfoList);

	/**
	 * 根据课题id获取课题的扩展信息
	 * @param lessonId
	 * @return
	 * @see com.tmser.tr.myplanbook.service.MyPlanBookService#getLessonInfoByLessonId(java.lang.String)
	 */
	public LessonInfo getLessonInfoByLessonId(LessonInfo info);

	public List<LectureRecords> findIsOrNotSubmitRecords(Integer isSubmit);

	
	public void submitLectureRecords(String lessonPlanIdsStr) throws Exception;
	public Integer unsubmitLectureRecords(String lessonPlanIdsStr) throws Exception;
}
