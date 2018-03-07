/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.plainsummary.dao;

import java.util.List;
import java.util.Map;

import com.tmser.tr.common.dao.BaseDAO;
import com.tmser.tr.common.page.PageList;
import com.tmser.tr.plainsummary.bo.PlainSummary;
import com.tmser.tr.plainsummary.vo.PlainSummaryCheckStatisticsVo;
import com.tmser.tr.plainsummary.vo.PlainSummaryVo;

 /**
 * 计划总结 DAO接口
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: PlainSummary.java, v 1.0 2015-03-19 Generate Tools Exp $
 */
public interface PlainSummaryDao extends BaseDAO<PlainSummary, Integer>{
	
	/**
	 * 分享
	 * @param psId
	 * @return
	 */
	Integer share(Integer psId);

	/**
	 * 根据工作空间统计计划总结的数据 
	 * @param userId
	 * @param spaceIds
	 * @return
	 */
	Map<Integer, PlainSummaryCheckStatisticsVo> getPlainSummaryStatistics(Integer userId,
			Integer gradeId,Integer subjectId,Integer checkUserId,Integer roleId,Integer schoolYear,
			Integer phaseId,Integer orgId);

	/**
	 * 根据用户空间、类型查询计划总结
	 * @param userSpaceIds
	 * @param category
	 * @return
	 */
	List<PlainSummary> findByUserSpace(Integer category,Integer isSubmit,Integer gradeId,
			Integer subjectId,Integer userId,Integer roleId,Integer schoolYear,Integer phaseId,
			Integer orgId);

	/**
	 * 在给定时间之前提交的的计划总结
	 * @param chekedUserSpaceIds 被审阅工作空间
	 * @param category
	 * @param checkWorkSpaceId 审阅人工作空间
	 * @param checkstatus
	 * @param date
	 * @return
	 */
	PlainSummaryVo findPrePlainSummary(Integer category,Integer checkWorkSpaceId,
									   Integer checkstatus,Integer psId,Integer cantainCurrent,
									   Integer gradeId,Integer subjectId,Integer userId,
									   Integer roleId,Integer schoolYear);
	
	/**
	 * 在给定时间之后提交的的计划总结
	 * @param chekedUserSpaceIds 被审阅工作空间
	 * @param category
	 * @param checkWorkSpaceId 审阅人工作空间
	 * @param checkstatus
	 * @param date
	 * @return
	 */
	PlainSummaryVo findNextPlainSummary(List<Integer> chekedUserSpaceIds,Integer category,Integer checkWorkSpaceId,Integer checkstatus,Integer psId,
										Integer gradeId,Integer subjectId,Integer roleId,Integer schoolYear,Integer userId);
	
	/**
	 * 获取上一篇发布的计划总结
	 * @param psId
	 * @return
	 */
	PlainSummary getPrePunishPlanSummary(Integer psId,Integer orgId);
	
	/**
	 * 获取下一篇发布的计划总结
	 * @param psId
	 * @return
	 */
	PlainSummary getNexPunishPlanSummary(Integer psId,Integer orgId);
	
	/**
	 * 分页查询已发布的计划总结,包含当前用户
	 * @param ps
	 * @param currentUserId
	 * @return
	 */
	PageList<PlainSummary> findPunishsByPage(PlainSummary ps,
			List<Integer> circleIds);
	
	/**
	 * 查询已发布未查看的计划总结
	 * @param num
	 * @param psSearch
	 * @return
	 */
	List<PlainSummary> findUnViewPunishs(Integer num, PlainSummary psSearch,List<Integer> circleIds);

	/**
	 * 查询下一篇计划总结，根据要查询的计划总结id
	 * @param checkplId 下一篇计划总结的id
	 * @return
	 */
	PlainSummaryVo findPrePlainSummary(Integer checkplId);
}