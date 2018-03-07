/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.plainsummary.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.tmser.tr.common.dao.AbstractDAO;
import com.tmser.tr.common.page.PageList;
import com.tmser.tr.plainsummary.bo.PlainSummary;
import com.tmser.tr.plainsummary.dao.PlainSummaryDao;
import com.tmser.tr.plainsummary.vo.PlainSummaryCheckStatisticsVo;
import com.tmser.tr.plainsummary.vo.PlainSummaryVo;

/**
 * 计划总结 Dao 实现类
 * 
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: PlainSummary.java, v 1.0 2015-03-19 Generate Tools Exp $
 */
@Repository
public class PlainSummaryDaoImpl extends AbstractDAO<PlainSummary, Integer>
		implements PlainSummaryDao {

	@Override
	public Integer share(Integer psId) {
		return this.update(
				"upate plainSummary ps set ps.is_share=1 where ps.id=?", psId);
	}

	/**
	 * @param userId
	 * @param spaceIds
	 * @return
	 * @see com.tmser.tr.plainsummary.dao.PlainSummaryDao#getTeacherPlainStatistics(java.util.List)
	 */
	@Override
	public Map<Integer, PlainSummaryCheckStatisticsVo> getPlainSummaryStatistics(Integer userId,
			Integer gradeId,Integer subjectId,Integer checkUserId,Integer roleId,
			Integer schoolYear,Integer phaseId,Integer orgId) {
		final Map<Integer, PlainSummaryCheckStatisticsVo> map = new HashMap<>();
		Map<String,Object> argMap = new HashMap<String, Object>();
		argMap.put("userId", userId);
		argMap.put("gradeId", gradeId);
		argMap.put("subjectId", subjectId);
		argMap.put("checkUserId", checkUserId);
		argMap.put("roleId",roleId);
		argMap.put("schoolYear",schoolYear);
		argMap.put("phaseId", phaseId);
		argMap.put("orgId", orgId);
		//统计计划撰写数
		StringBuffer countpl = new StringBuffer("select userId userId,count(1) total from PlainSummary where roleId = :roleId and schoolYear = :schoolYear and orgId=:orgId");
		if(userId != null){
			countpl.append(" and userId = :userId");
		}
		if(phaseId != null){
			countpl.append(" and phaseId = :phaseId");
		}
		if(gradeId != null){
			countpl.append(" and gradeId = :gradeId");
		}
		if(subjectId != null){
			countpl.append(" and subjectId = :subjectId");
		}
		countpl.append(" and (category=1 or category=3) group by userId");
		this.queryByNamedSql(
				countpl.toString(),
				argMap, new RowMapper<PlainSummaryCheckStatisticsVo>() {
					@Override
					public PlainSummaryCheckStatisticsVo mapRow(ResultSet rs,
							int rowNum) throws SQLException {
						Integer userId = rs.getInt("userId");
						PlainSummaryCheckStatisticsVo vo = map.get(userId);
						if (vo == null) {
							vo = new PlainSummaryCheckStatisticsVo();
							map.put(userId, vo);
						}
						vo.setPlainNum(rs.getInt("total"));
						return null;
					}
				});
		//统计计划提交数
		StringBuffer countplSubmit = new StringBuffer("select userId userId,count(1) total from PlainSummary where roleId = :roleId and schoolYear = :schoolYear and orgId=:orgId");
		if(userId != null){
			countplSubmit.append(" and userId = :userId");
		}
		if(phaseId != null){
			countplSubmit.append(" and phaseId = :phaseId");
		}
		if(gradeId != null){
			countplSubmit.append(" and gradeId = :gradeId");
		}
		if(subjectId != null){
			countplSubmit.append(" and subjectId = :subjectId");
		}
		
			countplSubmit.append(" and (category=1 or category=3)");
		countplSubmit.append(" and isSubmit=1 group by userId");
		this.queryByNamedSql(
				countplSubmit.toString(),
				argMap, new RowMapper<PlainSummaryCheckStatisticsVo>() {
					@Override
					public PlainSummaryCheckStatisticsVo mapRow(ResultSet rs,
							int rowNum) throws SQLException {
						Integer userId = rs.getInt("userId");
						PlainSummaryCheckStatisticsVo vo = map.get(userId);
						if (vo == null) {
							vo = new PlainSummaryCheckStatisticsVo();
							map.put(userId, vo);
						}
						vo.setPlainSubmitNum(rs.getInt("total"));
						return null;
					}
				});
		//统计计划评阅数
		StringBuffer countplReview = new StringBuffer("select ps.userId userId,count(distinct ci.resId) total from CheckInfo ci left join PlainSummary ps on");
		countplReview.append(" ci.resId = ps.id where ps.roleId = :roleId and ps.schoolYear = :schoolYear and ps.orgId=:orgId");
		if(userId != null){
			countplReview.append(" and ps.userId = :userId");
		}
		if(phaseId != null){
			countplReview.append(" and ps.phaseId = :phaseId");
		}
		if(gradeId != null){
			countplReview.append(" and ps.gradeId = :gradeId");
		}
		if(subjectId != null){
			countplReview.append(" and ps.subjectId = :subjectId");
		}
			countplReview.append(" and (ps.category=1 or ps.category=3)");
		countplReview.append(" and ps.isSubmit=1 and ci.resType=8 and ci.userId =:checkUserId group by ps.userId;");
		this.queryByNamedSql(countplReview.toString(), argMap,
				new RowMapper<PlainSummaryCheckStatisticsVo>() {
					@Override
					public PlainSummaryCheckStatisticsVo mapRow(ResultSet rs,
							int rowNum) throws SQLException {
						Integer userId = rs.getInt("userId");
						PlainSummaryCheckStatisticsVo vo = map.get(userId);
						if (vo == null) {
							vo = new PlainSummaryCheckStatisticsVo();
							map.put(userId, vo);
						}
						vo.setPlainCheckedNum(rs.getInt("total"));
						return null;
					}
				});
		//统计总结撰写
		StringBuffer countplW = new StringBuffer("select userId userId,count(1) total from PlainSummary where roleId = :roleId and schoolYear = :schoolYear and orgId=:orgId");
		if(userId != null){
			countplW.append(" and userId = :userId");
		}
		if(phaseId != null){
			countplW.append(" and phaseId = :phaseId");
		}
		if(gradeId != null){
			countplW.append(" and gradeId = :gradeId");
		}
		if(subjectId != null){
			countplW.append(" and subjectId = :subjectId");
		}
			countplW.append(" and (category=2 or category=4)");
		countplW.append(" group by userId");
		this.queryByNamedSql(
				countplW.toString(),
				argMap, new RowMapper<PlainSummaryCheckStatisticsVo>() {
					@Override
					public PlainSummaryCheckStatisticsVo mapRow(ResultSet rs,
							int rowNum) throws SQLException {
						Integer userId = rs.getInt("userId");
						PlainSummaryCheckStatisticsVo vo = map.get(userId);
						if (vo == null) {
							vo = new PlainSummaryCheckStatisticsVo();
							map.put(userId, vo);
						}
						vo.setSummaryNum(rs.getInt("total"));
						return null;
					}
				});
		//统计总结提交数
		StringBuffer countZjSubmit = new StringBuffer("select userId userId,count(1) total from PlainSummary where roleId = :roleId and schoolYear = :schoolYear and orgId=:orgId");
		if(userId != null){
			countZjSubmit.append(" and userId = :userId");
		}
		if(phaseId != null){
			countZjSubmit.append(" and phaseId = :phaseId");
		}
		if(gradeId != null){
			countZjSubmit.append(" and gradeId = :gradeId");
		}
		if(subjectId != null){
			countZjSubmit.append(" and subjectId = :subjectId");
		}
			countZjSubmit.append(" and (category=2 or category=4)");
		countZjSubmit.append(" and isSubmit=1 group by userId");
		this.queryByNamedSql(
				countZjSubmit.toString(),
				argMap, new RowMapper<PlainSummaryCheckStatisticsVo>() {
					@Override
					public PlainSummaryCheckStatisticsVo mapRow(ResultSet rs,
							int rowNum) throws SQLException {
						Integer userId = rs.getInt("userId");
						PlainSummaryCheckStatisticsVo vo = map.get(userId);
						vo.setSummarySubmitNum(rs.getInt("total"));
						map.put(userId, vo);
						return null;
					}
				});
		// 统计总结评阅数
		StringBuffer countZjReview = new StringBuffer("select ps.userId userId,count(distinct ci.resId) total from CheckInfo ci left join PlainSummary ps on ");
		countZjReview.append("ci.resId = ps.id where ps.roleId = :roleId and ps.schoolYear = :schoolYear and ps.orgId=:orgId");
		if(userId != null){
			countZjReview.append(" and ps.userId = :userId");
		}
		if(phaseId != null){
			countZjReview.append(" and ps.phaseId = :phaseId");
		}
		if(gradeId != null){
			countZjSubmit.append(" and ps.gradeId = :gradeId");
		}
		if(subjectId != null){
			countZjSubmit.append(" and ps.subjectId = :subjectId");
		}
			countZjSubmit.append(" and (ps.category=2 or ps.category=4)");
		countZjReview.append(" and ci.resType=9 and ci.userId =:checkUserId group by ps.userId;");
		this.queryByNamedSql(
				countZjReview.toString(),
				argMap, new RowMapper<PlainSummaryCheckStatisticsVo>() {
					@Override
					public PlainSummaryCheckStatisticsVo mapRow(ResultSet rs,
							int rowNum) throws SQLException {
						Integer userId = rs.getInt("userId");
						PlainSummaryCheckStatisticsVo vo = map.get(userId);
						if (vo == null) {
							vo = new PlainSummaryCheckStatisticsVo();
							map.put(userId, vo);
						}
						vo.setSummaryCheckedNum(rs.getInt("total"));
						return null;
					}
				});
		return map;
	}

	/**
	 * 根据用户空间、类型查询计划总结
	 * 
	 * @param userSpaceIds
	 * @param category
	 * @return
	 * @see com.tmser.tr.plainsummary.dao.PlainSummaryDao#findByUserSpace(java.util.List,
	 *      java.lang.Integer)
	 */
	@Override
	public List<PlainSummary> findByUserSpace(Integer category, Integer isSubmit,Integer gradeId,Integer subjectId,Integer userId,
			Integer roleId,Integer schoolYear,Integer phaseId,Integer orgId) {
		RowMapper<PlainSummary> mapper = mapperCreater
				.createMapper(PlainSummary.class);
		Map<String,Object > paramMap = new HashMap<String, Object>();
		paramMap.put("category", category);
		paramMap.put("isSubmit", isSubmit);
		paramMap.put("gradeId", gradeId);
		paramMap.put("subjectId", subjectId);
		paramMap.put("userId", userId);
		paramMap.put("roleId", roleId);
		paramMap.put("schoolYear", schoolYear);
		paramMap.put("phaseId", phaseId);
		paramMap.put("orgId", orgId);
		if (category != null && isSubmit != null) {
			StringBuffer sql = new StringBuffer("select id,userId,title,category,contentFileKey,contentFileType");
			sql.append(",isSubmit,crtDttm,submitTime,term from PlainSummary where roleId = :roleId and orgId = :orgId");
			sql.append(" and schoolYear = :schoolYear");
			if(userId != null){
				sql.append(" and userId = :userId");
			}
			if(phaseId != null){
				sql.append(" and phaseId = :phaseId");
			}
			if(gradeId != null){
				sql.append(" and gradeId = :gradeId");
			}
			if(subjectId != null){
				sql.append(" and subjectId = :subjectId");
			}
			sql.append(" and category = :category and isSubmit=:isSubmit order by submitTime desc,id desc");
			return this
					.queryByNamedSql(sql.toString(),
							paramMap, mapper);
		} else if (category != null) {
			StringBuffer sql = new StringBuffer("select id,userId,title,category,contentFileKey,contentFileType");
			sql.append(",isSubmit,crtDttm,submitTime,term from PlainSummary where roleId = :roleId and orgId = :orgId");
			sql.append(" and schoolYear = :schoolYear");
			if(userId != null){
				sql.append(" and userId = :userId");
			}
			if(phaseId != null){
				sql.append(" and phaseId = :phaseId");
			}
			if(gradeId != null){
				sql.append(" and gradeId = :gradeId");
			}
			if(subjectId != null){
				sql.append(" and subjectId = :subjectId");
			}
			sql.append(" and category=:category order by submitTime desc,id desc");
			return this
					.queryByNamedSql(sql.toString(),
							paramMap, mapper);
		} else if (isSubmit != null) {
			StringBuffer sql = new StringBuffer("select id,userId,title,category,contentFileKey,contentFileType");
			sql.append(",isSubmit,crtDttm,submitTime,term from PlainSummary where roleId = :roleId and orgId = :orgId");
			sql.append(" and schoolYear = :schoolYear");
			if(userId != null){
				sql.append(" and userId = :userId");
			}
			if(phaseId != null){
				sql.append(" and phaseId = :phaseId");
			}
			if(subjectId != null){
				sql.append(" and subjectId = :subjectId");
			}
			if(gradeId != null){
				sql.append(" and gradeId = :gradeId");
			}
			sql.append(" and isSubmit = :isSubmit order by submitTime desc,id desc");
			return this
					.queryByNamedSql(sql.toString(),
							paramMap, mapper);
		} else {
			StringBuffer sql = new StringBuffer("select id,userId,title,category,contentFileKey,contentFileType");
			sql.append(",isSubmit,crtDttm,submitTime,term from PlainSummary where roleId = :roleId and orgId = :orgId");
			sql.append(" and schoolYear = :schoolYear");
			if(userId != null){
				sql.append(" and userId = :userId");
			}
			if(phaseId != null){
				sql.append(" and phaseId = :phaseId");
			}
			if(gradeId != null){
				sql.append(" and gradeId = :gradeId");
			}
			if(subjectId != null){
				sql.append(" and subjectId = :subjectId");
			}
			sql.append(" order by submitTime desc,id desc");
			return this
					.queryByNamedSql(sql.toString(),
							paramMap, mapper);
		}

	}

	@Override
	public PlainSummaryVo findPrePlainSummary(
			Integer category, Integer checkWorkSpaceId, Integer checkstatus,
			Integer psId,Integer cantainCurrent,Integer gradeId,Integer subjectId,
			Integer userId,Integer roleId,Integer schoolYear) {
		RowMapper<PlainSummaryVo> mapper = ParameterizedBeanPropertyRowMapper
				.newInstance(PlainSummaryVo.class);
		Map<String,Object> argMap = new HashMap<String, Object>();
		argMap.put("checkWorkSpaceId", checkWorkSpaceId);
		argMap.put("checkstatus", checkstatus);
		argMap.put("category", category);
		argMap.put("psId", psId);
		argMap.put("gradeId", gradeId);
		argMap.put("subjectId", subjectId);
		argMap.put("userId", userId);
		argMap.put("roleId", roleId);
		argMap.put("schoolYear", schoolYear);
		// 查询所有状态
		if (checkstatus == null) {
			if(cantainCurrent!=null&&cantainCurrent==1){
				StringBuffer sql = new StringBuffer();
				sql.append("select ps.id id,ps.userId userId,ps.title title,ps.category category,ps.contentFileKey contentFileKey,ps.contentFileType contentFileType")
				.append(",ps.isSubmit isSubmit,ps.crtDttm crtDttm,ps.submitTime submitTime from PlainSummary ps")
				.append(" where ps.userId = :userId and ps.roleId = :roleId and ps.schoolYear = :schoolYear");
				if(gradeId != null){
					sql.append(" and ps.gradeId = :gradeId");
				}
				if(subjectId != null){
					sql.append(" and ps.subjectId = :subjectId");
				}
				sql.append(" and ps.category = :category  and ps.isSubmit=1 order by ps.submitTime desc,ps.id desc limit 1");
				return this.queryByNamedSqlForSingle(sql.toString(),argMap, mapper);
			}
			StringBuffer sql = new StringBuffer();
			sql.append("select ps.id id,ps.userId userId,ps.title title,ps.category category,ps.contentFileKey contentFileKey,ps.contentFileType contentFileType");
			sql.append(",ps.isSubmit isSubmit,ps.crtDttm crtDttm,ps.submitTime submitTime from PlainSummary ps");
			sql.append(" where ps.userId = :userId and ps.roleId = :roleId and ps.schoolYear = :schoolYear");
			if(gradeId != null){
				sql.append(" and ps.gradeId = :gradeId");
			}
			if(subjectId != null){
				sql.append(" and ps.subjectId = :subjectId");
			}
			sql.append(" and ps.category = :category and ps.submitTime<(select ps.submitTime from PlainSummary ps where ps.id=:psId) and ps.id <> :psId and ps.isSubmit=1 order by ps.submitTime desc,ps.id desc limit 1");
			return this.queryByNamedSqlForSingle(sql.toString(),argMap, mapper);
			
		}
		// 仅查询审阅状态
		if (checkstatus == 1) {
			if(cantainCurrent!=null&&cantainCurrent==1){
				StringBuffer sql = new StringBuffer();
				sql.append("select ps.id id,ps.userId userId,ps.title title,ps.category category,ps.contentFileKey contentFileKey,ps.contentFileType contentFileType");
				sql.append(",ps.isSubmit isSubmit,ps.crtDttm crtDttm,ps.submitTime submitTime from PlainSummary ps where");
				sql.append(" ps.userId = :userId and ps.roleId = :roleId and ps.schoolYear = :schoolYear");
				if(gradeId != null){
					sql.append(" and ps.gradeId = :gradeId");
				}
				if(subjectId != null){
					sql.append(" and ps.subjectId = :subjectId");
				}
				sql.append(" and ps.category= :category  and EXISTS ( ");
				sql.append("select pi.resId from CheckInfo pi where pi.userId= :checkWorkSpaceId and ps.id=pi.resId)");
				sql.append(" order by ps.submitTime desc,ps.id desc limit 1");
				return this
						.queryByNamedSqlForSingle(sql.toString(), argMap,mapper);
			}
			StringBuffer sql = new StringBuffer();
			sql.append("select ps.id id,ps.userId userId,ps.title title,ps.category category,ps.contentFileKey contentFileKey,ps.contentFileType contentFileType");
			sql.append(",ps.isSubmit isSubmit,ps.crtDttm crtDttm,ps.submitTime submitTime from PlainSummary ps where");
			sql.append(" ps.userId = :userId and ps.roleId = :roleId and ps.schoolYear = :schoolYear");
			if(gradeId != null){
				sql.append(" and ps.gradeId = :gradeId");
			}
			if(subjectId != null){
				sql.append(" and ps.subjectId = :subjectId");
			}
			sql.append(" and ps.isSubmit=1 and ps.category= :category and ps.submitTime<(select ps.submitTime from PlainSummary ps where ps.id=:psId) and ps.id <> :psId and EXISTS (");
			sql.append("select pi.resId from CheckInfo pi where pi.userId= :checkWorkSpaceId and ps.id=pi.resId)");
			sql.append(" order by ps.submitTime desc,ps.id desc limit 1");
			return this
					.queryByNamedSqlForSingle(sql.toString(), argMap,mapper);
		}
		// 仅查询未审阅状态
		if (checkstatus == 0) {
			if(cantainCurrent!=null&&cantainCurrent==1){
				StringBuffer sql = new StringBuffer();
				sql.append("select ps.id id,ps.userId userId,ps.title title,ps.category category,ps.contentFileKey contentFileKey,ps.contentFileType contentFileType");
				sql.append(",ps.isSubmit isSubmit,ps.crtDttm crtDttm,ps.submitTime submitTime from PlainSummary ps where");
				sql.append(" ps.userId = :userId and ps.roleId = :roleId and ps.schoolYear = :schoolYear");
				if(gradeId != null){
					sql.append(" and ps.gradeId = :gradeId");
				}
				if(subjectId != null){
					sql.append(" and ps.subjectId = :subjectId");
				}
				sql.append(" and ps.isSubmit=1 and ps.category= :category and not EXISTS (");
				sql.append("select pi.resId from CheckInfo pi where pi.userId= :checkWorkSpaceId and ps.id=pi.resId)");
				sql.append(" order by ps.submitTime desc,ps.id desc limit 1");
				return this
						.queryByNamedSqlForSingle(sql.toString(), argMap,mapper);
			}
			StringBuffer sql = new StringBuffer();
			sql.append("select ps.id id,ps.userId userId,ps.title title,ps.category category,ps.contentFileKey contentFileKey,");
			sql.append("ps.contentFileType contentFileType,ps.isSubmit isSubmit,ps.crtDttm crtDttm,ps.submitTime submitTime from PlainSummary ps where");
			sql.append(" ps.userId = :userId ps.roleId = :roleId and ps.schoolYear = :schoolYear");
			if(gradeId != null){
				sql.append(" and ps.gradeId = :gradeId");
			}
			if(subjectId != null){
				sql.append(" and ps.subjectId = :subjectId");
			}
			sql.append(" and ps.isSubmit=1 and ps.category= :category and ps.submitTime<(select ps.submitTime from PlainSummary ps where ps.id=:psId) and ps.id <> :psId and not EXISTS (");
			sql.append("select pi.resId from CheckInfo pi where pi.userId= :checkWorkSpaceId and ps.id=pi.resId) order by ps.submitTime desc,ps.id desc limit 1");
			return this
					.queryByNamedSqlForSingle(sql.toString(), argMap,mapper);
		}
		return null;
	}

	@Override
	public PlainSummaryVo findNextPlainSummary(
			List<Integer> chekedUserSpaceIds, Integer category,
			Integer checkWorkSpaceId, Integer checkstatus, Integer psId,
			Integer gradeId,Integer subjectId,Integer roleId,Integer schoolYear,Integer userId) {
		RowMapper<PlainSummaryVo> mapper = ParameterizedBeanPropertyRowMapper
				.newInstance(PlainSummaryVo.class);
		Map<String,Object> argMap = new HashMap<String, Object>();
		argMap.put("chekedUserSpaceIds", chekedUserSpaceIds);
		argMap.put("checkWorkSpaceId", checkWorkSpaceId);
		argMap.put("checkstatus", checkstatus);
		argMap.put("category", category);
		argMap.put("psId", psId);
		
		argMap.put("gradeId", gradeId);
		argMap.put("subjectId", subjectId);
		argMap.put("roleId", roleId);
		argMap.put("schoolYear", schoolYear);
		argMap.put("userId", userId);
		// 查询所有状态
		if (checkstatus == null) {
			StringBuffer sql = new StringBuffer();
			sql.append("select ps.id id,ps.userId userId,ps.title title,ps.category category,ps.contentFileKey contentFileKey,ps.contentFileType contentFileType");
			sql.append(",ps.isSubmit isSubmit,ps.crtDttm crtDttm,ps.submitTime submitTime from PlainSummary ps where ps.userId = :userId and ps.roleId = :roleId and ps.schoolYear = :schoolYear");
			if(gradeId != null){
				sql.append(" and ps.gradeId = :gradeId");
			}
			if(subjectId != null){
				sql.append(" and ps.subjectId = :subjectId");
			}
			sql.append(" and ps.category = :category and ps.submitTime > (select ps.submitTime from PlainSummary ps where ps.id=:psId) and ps.id <> :psId  and ps.isSubmit=1 order by ps.submitTime,ps.id limit 1");
			return this
					.queryByNamedSqlForSingle(sql.toString(),argMap, mapper);
		}
		// 仅查询审阅状态
		if (checkstatus == 1) {
			StringBuffer sql = new StringBuffer();
			sql.append("select ps.id id,ps.userId userId,ps.title title,ps.category category,ps.contentFileKey contentFileKey,ps.contentFileType contentFileType");
			sql.append(",ps.isSubmit isSubmit,ps.crtDttm crtDttm,ps.submitTime submitTime from PlainSummary ps where ps.userId = :userId");
			sql.append(" and ps.roleId = :roleId and ps.schoolYear = :schoolYear");
			if(gradeId != null){
				sql.append(" and ps.gradeId = :gradeId");
			}
			if(subjectId != null){
				sql.append(" and ps.subjectId = :subjectId");
			}
			sql.append(" and ps.isSubmit=1 and ps.category=:category and ps.submitTime > (select ps.submitTime from PlainSummary ps where ps.id=:psId) and ps.id <> :psId and EXISTS (");
			sql.append("select pi.resId from CheckInfo pi where pi.userId=:checkWorkSpaceId and ps.id=pi.resId) order by ps.crtDttm,ps.id limit 1");
			return this
					.queryByNamedSqlForSingle(sql.toString(), argMap, mapper);
		}
		// 仅查询未审阅状态
		if (checkstatus == 0) {
			StringBuffer sql = new StringBuffer();
			sql.append("select ps.id id,ps.userId userId,ps.title title,ps.category category,ps.contentFileKey contentFileKey,ps.contentFileType contentFileType");
			sql.append(",ps.isSubmit isSubmit,ps.crtDttm crtDttm,ps.submitTime submitTime from PlainSummary ps where");
			sql.append(" ps.userId = :userId and ps.roleId = :roleId and ps.schoolYear = :schoolYear");
			if(gradeId != null){
				sql.append(" and ps.gradeId = :gradeId");
			}
			if(subjectId != null){
				sql.append(" and ps.subjectId = :subjectId");
			}
			sql.append(" and ps.isSubmit=1 and ps.category=:category and ps.submitTime > (select ps.submitTime from PlainSummary ps where ps.id=:psId) and ps.id <> :psId and not EXISTS (");
			sql.append("select pi.resId from CheckInfo pi where pi.userId=:checkWorkSpaceId and ps.id=pi.resId) order by ps.submitTime,ps.id limit 1");
			return this
					.queryByNamedSqlForSingle(sql.toString(), argMap, mapper);
		}
		return null;
	}
	
	/**
	 * 获取上一篇发布的计划总结
	 * @param psId
	 * @return
	 * @see com.tmser.tr.plainsummary.dao.PlainSummaryDao#getPrePunishPlanSummary(java.lang.Integer)
	 */
	@Override
	public PlainSummary getPrePunishPlanSummary(Integer psId,Integer orgId) {
		String sql = "select * from PlainSummary ps where ps.punishTime > (select ps.punishTime from PlainSummary ps where ps.id=:id) and ps.id<>:id and ps.isPunish=1 and ps.orgId=:orgId order by ps.punishTime ";
		Map<String, Object> argMap  = new HashMap<String, Object>();
		argMap.put("id", psId);
		argMap.put("orgId", orgId);
		return this.queryByNamedSqlForSingle(sql, argMap, this.mapper);
	}
	
	/**
	 * 获取下一篇发布的计划总结
	 * @param psId
	 * @return
	 * @see com.tmser.tr.plainsummary.dao.PlainSummaryDao#getPrePunishPlanSummary(java.lang.Integer)
	 */
	@Override
	public PlainSummary getNexPunishPlanSummary(Integer psId,Integer orgId) {
		String sql = "select * from PlainSummary ps where ps.punishTime<(select ps.punishTime from PlainSummary ps where ps.id=:id) and ps.id<>:id and ps.isPunish=1 and ps.orgId=:orgId order by ps.punishTime desc";
		Map<String, Object> argMap  = new HashMap<String, Object>();
		argMap.put("id", psId);
		argMap.put("orgId", orgId);
		return this.queryByNamedSqlForSingle(sql, argMap, this.mapper);
	}
	
	/**
	 * 分页查询已发布的计划总结，包含当前用户
	 * @param ps
	 * @param currentUserId
	 * @return
	 * @see com.tmser.tr.plainsummary.dao.PlainSummaryDao#findPunishsByPage(com.tmser.tr.plainsummary.bo.PlainSummary, java.lang.Integer)
	 */
	@Override
	public PageList<PlainSummary> findPunishsByPage(PlainSummary ps,
			List<Integer> circleIds) {
		
		String sql = "select ps.id,ps.title,ps.roleId,ps.category,ps.contentFileKey,ps.punishTime,ps.userId from PlainSummary ps where ps.isPunish=1 and ps.orgId=:orgId and ps.punishRange=0  order by ps.punishTime desc"	;
		if(!CollectionUtils.isEmpty(circleIds)){
			sql = "select ps.id,ps.title,ps.roleId,ps.category,ps.contentFileKey,ps.punishTime,ps.userId from PlainSummary ps where ps.isPunish=1 and (ps.orgId=:orgId  or (ps.schoolCircleId in(:circleIds) and ps.punishRange=1)) order by ps.punishTime desc";
		}
		Map<String, Object> argMap = new HashMap<String, Object>();
		argMap.put("orgId", ps.getOrgId());
		argMap.put("circleIds", circleIds);
		return this.queryPageByNamedSql(sql, argMap, this.mapper, ps.getPage());
	}
	
	/**
	 * 查询已发布未查看的计划总结
	 * @param num
	 * @param psSearch
	 * @return
	 * @see com.tmser.tr.plainsummary.dao.PlainSummaryDao#findUnViewPunishs(java.lang.Integer, com.tmser.tr.plainsummary.bo.PlainSummary)
	 */
	@Override
	public List<PlainSummary> findUnViewPunishs(Integer num,
			PlainSummary ps,List<Integer> circleIds) {
		String sql = "select * from PlainSummary ps where ps.isPunish=1 and ps.orgId=:orgId and ps.id not in (select pv.plainSummaryId from  PlainSummaryPunishView pv where pv.userId=:currentUserId) order by ps.punishTime desc"	;
		if(!CollectionUtils.isEmpty(circleIds)){
			sql = "select * from PlainSummary ps where ps.isPunish=1 and (ps.orgId=:orgId or (ps.schoolCircleId in(:circleIds) and ps.punishRange=1)) and ps.id not in (select pv.plainSummaryId from  PlainSummaryPunishView pv where pv.userId=:currentUserId) order by ps.punishTime desc";
		}
		Map<String, Object> argMap = new HashMap<String, Object>();
		argMap.put("orgId", ps.getOrgId());
		argMap.put("currentUserId", ps.getUserId());
		argMap.put("circleIds", circleIds);
		return this.queryByNamedSqlWithLimit(sql, argMap,this.mapper, num);
	}

	/**
	 * @param checkplId
	 * @return
	 * @see com.tmser.tr.plainsummary.dao.PlainSummaryDao#findPrePlainSummary(java.lang.Integer)
	 */
	@Override
	public PlainSummaryVo findPrePlainSummary(Integer checkplId) {
		return null;
	}

}
