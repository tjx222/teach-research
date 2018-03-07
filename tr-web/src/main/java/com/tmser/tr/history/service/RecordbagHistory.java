/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.history.service;

import java.util.List;

import com.tmser.tr.recordbag.bo.Record;
import com.tmser.tr.recordbag.bo.Recordbag;

/**
 * <pre>
 *  历史资源-成长档案袋service
 * </pre>
 *
 * @author dell
 * @version $Id: RecordbagHistory.java, v 1.0 2016年6月6日 下午4:03:11 dell Exp $
 */
public interface RecordbagHistory {
	
	/**
	 * 统计历史资源成长档案袋精选数
	 */
	Integer getRecordbagHistory(Integer userId,Integer currentYear);
	
	/**
	 * 历史资源成长档案袋列表页
	 */
	List<Recordbag> getAll(Integer currentYear,Recordbag recordbag);
	

	/**
	 * 历史资源成长档案袋-教案、课件、反思列表页
	 */
	void showLessonPlanHistory(Record record);
	
	/**
	 * 历史资源成长档案袋-教学文章列表页
	 */
	void showThesisHistory(Record record);
	
	/**
	 * 历史资源成长档案袋-听课记录列表页
	 */
	void showLectureRecordHistory(Record record);
	
	/**
	 * 历史资源成长档案袋-计划总结列表页
	 */
	void showPainSummaryHistory(Record record);
	

	/**
	 * 历史资源成长档案袋-教研活动列表页
	 */
	Integer showActivityHistory(Record record);
	
	/**
	 * 历史资源成长档案袋-自建档案袋列表页
	 */
	void showOtherHistory(Record record);
}
