/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.plainsummary.service;

import java.util.List;

import com.tmser.tr.plainsummary.vo.PlainSummaryCheckStatisticsVo;
import com.tmser.tr.plainsummary.vo.PlainSummarySimpleCountVo;
import com.tmser.tr.plainsummary.vo.PlainSummaryVo;

/**
 * plainSummary 服务类
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: JyPlainSummaryCheck.java, v 1.0 2015-04-09 Generate Tools Exp $
 */

public interface JyPlainSummaryCheckService {

	/**
	 *获取被评阅工作空间的统计信息
	 * @param gradeId 被评阅的年级id
	 * @param subjectId 被评阅的学科id
	 * @return
	 */
	List<PlainSummaryCheckStatisticsVo> getUserSpaceStatistics(Integer gradeId,
			Integer subjectId);

	/**
	 * 获取提交时间在指定时间之前切距离指定日期最近的文档，单工作空间
	 * @param workSpaceId
	 * @param category
	 * @param date
	 * @param checkStatus
	 * @return
	 */
	PlainSummaryVo getPrePlanSummary(Integer checkplId);
	
	/**
	 * 获取提交时间在指定时间之后切距离指定日期最近的文档，单工作空间
	 * @param workSpaceId
	 * @param category
	 * @param date
	 * @param checkStatus
	 * @return
	 */
	PlainSummaryVo getNextPlanSummary(Integer workSpaceId, Integer category,
			Integer psId, Integer checkStatus,Integer gradeId,Integer subjectId,Integer roleId);
	
	/**
	 * 获取提交时间在指定时间之前切距离指定日期最近的文档，多工作空间
	 * @param userSpaceIds
	 * @param category
	 * @param date
	 * @param checkStatus
	 * @return
	 */
	PlainSummaryVo getPrePlanSummary(List<Integer> userSpaceIds,
			Integer category, Integer psId, Integer checkStatus,Integer cantainCurrent,Integer gradeId,Integer subjectId,Integer roleId);
	
	/**
	 * 获取提交时间在指定时间之后切距离指定日期最近的文档，多工作空间
	 * @param userSpaceIds
	 * @param category
	 * @param date
	 * @param checkStatus
	 * @return
	 */
	PlainSummaryVo getNextPlanSummary(List<Integer> userSpaceIds,
			Integer category, Integer psId, Integer checkStatus,Integer gradeId,Integer subjectId,Integer roleId);
	/**
	 * 根据用户空间查询计划总结，多用户空间
	 * @param userSpaceIds
	 * @param checkStatus
	 * @return
	 */
	List<PlainSummaryVo> findPlainSummaryVo(Integer checkStatus,Integer gradeId,Integer subjectId,Integer userId,Integer roleId);
	
	/**
	 * 根据用户空间查询计划总结，单用户空间
	 * @param userSpaceIds
	 * @param checkStatus
	 * @return
	 */
	List<PlainSummaryVo> findPlainSummaryVo(Integer checkStatus,Integer gradeId,Integer subjectId,Integer userId);
	
	/**
	 * 获取计划总结详情
	 * @param planSummaryId
	 * @return
	 */
	PlainSummaryVo getPlanSummaryVo(Integer planSummaryId);
	
	/**
	 * 查询工作空间的统计清空
	 * @param checkedSpaceIds
	 * @return
	 */
	PlainSummaryCheckStatisticsVo getPlainSummaryStatistics(Integer gradeId,Integer subjectId,Integer userId,Integer roleId,Integer phaseId);
	
	/**
	 * 根据用户空间id统计计划总计
	 * @param userSpaceId
	 * @return
	 */
	PlainSummarySimpleCountVo countByUserSpaceId(Integer userSpaceId);

	/**
	 * 根据学期筛选计划总结
	 * @param userSpaceIds
	 * @param category
	 * @param term
	 * @return
	 */
	public List<PlainSummaryVo> findPlainSummaryVoByTerm(Integer category, Integer term,Integer gradeId,Integer subjectId,Integer userId,Integer roleId);
	
}
