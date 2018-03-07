/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.companion.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.tmser.tr.common.dao.AbstractDAO;
import com.tmser.tr.common.page.PageList;
import com.tmser.tr.companion.bo.JyCompanion;
import com.tmser.tr.companion.dao.JyCompanionDao;
import com.tmser.tr.companion.vo.JyCompanionSearchVo;
import com.tmser.tr.companion.vo.JyCompanionVo;
import com.tmser.tr.uc.SysRole;

/**
 * 同伴互助 Dao 实现类
 * 
 * <pre>
 * 
 * </pre>
 * 
 * @author Generate Tools
 * @version $Id: JyCompanion.java, v 1.0 2015-05-20 Generate Tools Exp $
 */
@Repository
public class JyCompanionDaoImpl extends AbstractDAO<JyCompanion, Integer>
implements JyCompanionDao {

	/**
	 * 验证并返回是朋友关系的id
	 * 
	 * @param currentUserId
	 * @param copanionIds
	 * @return
	 */
	@Override
	public List<Integer> validFriendRelation(Integer currentUserId,
			List<Integer> copanionIds) {
		String sql = "select userIdCompanion userIdCompanion from JyCompanion where userId=:currentUserId and userIdCompanion in(:copanionIds) and is_friend=1";

		Map<String, Object> argMap = new HashMap<>();
		argMap.put("currentUserId", currentUserId);
		argMap.put("copanionIds", copanionIds);
		return this.queryByNamedSql(sql, argMap, new RowMapper<Integer>() {
			@Override
			public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getInt("userIdCompanion");
			}
		});
	}

	/**
	 * 查询本学期联系过的人员 findLatestConmunicateCompanions
	 * 
	 * @param currentUserId
	 * @param startDate
	 * @return
	 * @see com.tmser.tr.companion.dao.JyCompanionDao#findLatestConmunicateCompanions(java.lang.Integer,
	 *      java.util.Date)
	 */
	@Override
	public List<JyCompanionVo> findLatestConmunicateCompanions(
			Integer currentUserId, Date startDate) {
		String sql = "select * from JyCompanion where userId=? and lastCommunicateTime>? order by lastCommunicateTime desc";
		Object[] args = { currentUserId, startDate };
		List<JyCompanion> companions = this.query(sql, args, this.mapper);
		List<JyCompanionVo> result = new ArrayList<>();
		for (JyCompanion companion : companions) {
			JyCompanionVo vo = new JyCompanionVo();
			BeanUtils.copyProperties(companion, vo);
			result.add(vo);
		}
		return result;
	}

	/**
	 * 同伴列表查询
	 * 
	 * @param searchVo
	 * @return
	 * @see com.tmser.tr.companion.dao.JyCompanionDao#findUserIds(com.tmser.tr.companion.vo.JyCompanionSearchVo)
	 */
	@Override
	public PageList<Integer> findUserIds(JyCompanionSearchVo searchVo) {
		String sql = "select rs.userId userId from (#matchSql#) rs order by #orderSql#";
		Map<String, Object> argMap = new HashMap<>();
		if (!searchVo.getIsSameSchool() && CollectionUtils.isEmpty(searchVo.getSchoolIds())) {
			List<Integer> emptData = Collections.emptyList();
			return new PageList<Integer>(emptData,searchVo);
		}
		
		// 组装查询sql
		String matchSql = buildMatchSql(searchVo,
				buildCondition(searchVo, argMap));
		// 组装排序sql
		String orderSql = buildOrderSql(searchVo);
		sql = sql.replace("#matchSql#", matchSql);
		sql = sql.replace("#orderSql#", orderSql);

		return this.queryPageByNamedSql(sql, argMap, new RowMapper<Integer>() {

			@Override
			public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getInt("userId");
			}
		}, searchVo);
	}

	/**
	 * 组装排序sql
	 * 
	 * @param searchVo
	 * @return
	 */
	private String buildOrderSql(JyCompanionSearchVo vo) {
		String sql = "";
		if (vo.getIsSameSchool()) {// 查询同校同伴
			if (!SysRole.TEACHER.getId().equals(vo.getCurrentRoleId())) {// 非教师身份
				sql = "rs.sysRoleId desc,rs.subjectOrder,rs.gradeId,rs.userId";
			} else {// 教师身份
				sql = "rs.sysRoleId,rs.subjectOrder,rs.gradeId,rs.userId";
			}
		} else {// 查看校外同伴
			if (SysRole.ZR.getId().equals(vo.getCurrentRoleId())
					|| SysRole.FXZ.getId().equals(vo.getCurrentRoleId())
					|| SysRole.XZ.getId().equals(vo.getCurrentRoleId())) {// 校长、主任
				sql = "rs.orgId,rs.sysRoleId desc,rs.userId";
			} else if (SysRole.XKZZ.getId().equals(vo.getCurrentRoleId())) {// 学科组长
				sql = "rs.orgId,rs.gradeId,rs.userId";
			} else if (SysRole.NJZZ.getId().equals(vo.getCurrentRoleId())) {// 学科组长
				sql = "rs.orgId,rs.subjectOrder,rs.userId";
			} else {// 备课组长、教师
				sql = "rs.orgId,rs.userId";
			}
		}
		return sql;
	}

	/**
	 * 组装查询sql
	 * 
	 * @param searchVo
	 * @return
	 */
	private String buildMatchSql(JyCompanionSearchVo vo, String condition) {
		StringBuilder sb = new StringBuilder("select ");
		if (!SysRole.TEACHER.getId().equals(vo.getCurrentRoleId())
				&& vo.getIsSameSchool()) {// 如果非老师身份查询同校伙伴
			sb.append("us.userId userId,max(us.sysRoleId) sysRoleId,min(us.subjectId) subjectId,min(us.gradeId) gradeId,min(us.subjectOrder) subjectOrder");
		} else if (SysRole.TEACHER.getId().equals(vo.getCurrentRoleId())
				&& vo.getIsSameSchool()) {// 如果老师身份查询同校伙伴
			sb.append("us.userId userId,min(us.sysRoleId) sysRoleId,min(us.subjectId) subjectId,min(us.gradeId) gradeId,min(us.subjectOrder) subjectOrder");
		} else if (!vo.getIsSameSchool()) {// 如果查询非同校伙伴
			sb.append("us.userId userId,max(us.sysRoleId) sysRoleId,min(us.subjectId) subjectId,min(us.gradeId) gradeId,min(us.orgId) orgId,min(us.subjectOrder) subjectOrder");
		}
		sb.append(" from UserSpace us left join User u on us.userId=u.id where #condition# group by us.userId");
		return sb.toString().replace("#condition#", condition);
	}

	/**
	 * 构建条件sql
	 * 
	 * @param vo
	 * @return
	 */
	private String buildCondition(JyCompanionSearchVo vo,
			Map<String, Object> argMap) {
		StringBuilder sb = new StringBuilder();
		sb.append(" u.id <>:currentUserId ");
		argMap.put("currentUserId", vo.getCurrentUserId());
		sb.append(" and us.phaseId = :phaseId ");
		argMap.put("phaseId", vo.getPhaseId());
		sb.append(" and us.enable = :enable");
		argMap.put("enable", 1);
		if(vo.getSchoolYear() != null){
			sb.append(" and us.schoolYear = :schoolYear ");
			argMap.put("schoolYear", vo.getSchoolYear());
		}
		
		if (vo.getIsSameSchool()) {
			sb.append(" and us.orgId=:schoolId ");
			argMap.put("schoolId", vo.getSchoolId());
		} else {
			if (!CollectionUtils.isEmpty(vo.getSchoolIds())) {
				sb.append(" and us.orgId in(:schoolIds) ");
				argMap.put("schoolIds", vo.getSchoolIds());
			}else{//没有同伴校
				sb.append(" and us.orgId=-1 ");
			}
		}

		if (StringUtils.isNotBlank(vo.getUserName())) {
			if (vo.getIsSameSchool()) {
				sb.append(" and u.name like '%"+vo.getUserName()+"%' ");
			} else {
				sb.append(" and (u.name like '%"+vo.getUserName()+"%' ");
				sb.append(" or u.nickname like '%"+vo.getUserName()+"%' )");
			}
		}
		argMap.put("userName", vo.getUserName());

		if (vo.getRoleId() != null) {
			sb.append(" and us.sysRoleId=:roleId ");
		}
		argMap.put("roleId", vo.getRoleId());

		if (vo.getGradeId() != null) {
			sb.append(" and us.gradeId=:gradeId ");
		}
		argMap.put("gradeId", vo.getGradeId());

		if (vo.getSubjectId() != null) {
			sb.append(" and us.subjectId=:subjectId ");
		}
		argMap.put("subjectId", vo.getSubjectId());

		if (StringUtils.isNotBlank(vo.getProfession())) {
			sb.append(" and u.profession=:profession ");
		}
		argMap.put("profession", vo.getProfession());

		if (vo.getSchoolAge() != null) {
			sb.append(" and u.schoolAge=:schoolAge ");
		}
		argMap.put("schoolAge", vo.getSchoolAge());

		if (vo.getIsSameSchool()) {
			// 教师身份
			if (SysRole.TEACHER.getId().equals(vo.getCurrentRoleId())) {

				sb.append(" and ((us.sysRoleId=");
				sb.append(SysRole.TEACHER.getId());

				sb.append(" and us.subjectId=");
				sb.append(vo.getCurrentSubjectId());

				sb.append(" )or(  ");

				sb.append(" us.sysRoleId=");
				sb.append(SysRole.BKZZ.getId());

				sb.append(" and us.gradeId=");
				sb.append(vo.getCurrentGradeId());

				sb.append(" and us.subjectId=");
				sb.append(vo.getCurrentSubjectId());

				sb.append(" )or(  ");

				sb.append(" us.sysRoleId=");
				sb.append(SysRole.NJZZ.getId());

				sb.append(" and us.gradeId=");
				sb.append(vo.getCurrentGradeId());

				sb.append(" )or(  ");

				sb.append(" us.sysRoleId=");
				sb.append(SysRole.XKZZ.getId());

				sb.append(" and us.subjectId=");
				sb.append(vo.getCurrentSubjectId());

				sb.append(" )or(  ");

				sb.append("us.sysRoleId in (:sysRoleIds) ");
				List<Integer> sysRoleIds = new ArrayList<Integer>();
				sysRoleIds.add(SysRole.ZR.getId());
				sysRoleIds.add(SysRole.FXZ.getId());
				sysRoleIds.add(SysRole.XZ.getId());
				argMap.put("sysRoleIds", sysRoleIds);

				sb.append("  ))");
			}
		} else {// 校外同伴
			if (SysRole.ZR.getId().equals(vo.getCurrentRoleId())
					|| SysRole.FXZ.getId().equals(vo.getCurrentRoleId())
					|| SysRole.XZ.getId().equals(vo.getCurrentRoleId())) {// 校长、主任
				sb.append(" and us.sysRoleId in (:sysRoleIds) ");
				List<Integer> sysRoleIds = new ArrayList<Integer>();
				sysRoleIds.add(SysRole.ZR.getId());
				sysRoleIds.add(SysRole.FXZ.getId());
				sysRoleIds.add(SysRole.XZ.getId());
				argMap.put("sysRoleIds", sysRoleIds);
			} else if (SysRole.XKZZ.getId().equals(vo.getCurrentRoleId())) {// 学科组长

				sb.append(" and us.sysRoleId=");
				sb.append(SysRole.XKZZ.getId());

				sb.append(" and us.subjectId=");
				sb.append(vo.getCurrentSubjectId());

			} else if (SysRole.NJZZ.getId().equals(vo.getCurrentRoleId())) {// 学科组长

				sb.append(" and us.sysRoleId=");
				sb.append(SysRole.NJZZ.getId());

				sb.append(" and us.gradeId=");
				sb.append(vo.getCurrentGradeId());

			} else if (SysRole.BKZZ.getId().equals(vo.getCurrentRoleId())) {// 备课组长

				sb.append(" and us.sysRoleId=");
				sb.append(SysRole.BKZZ.getId());

				sb.append(" and us.gradeId=");
				sb.append(vo.getCurrentGradeId());

				sb.append(" and us.subjectId=");
				sb.append(vo.getCurrentSubjectId());

			} else if (SysRole.TEACHER.getId().equals(vo.getCurrentRoleId())) {// 教师

				sb.append(" and us.sysRoleId=");
				sb.append(SysRole.TEACHER.getId());

				sb.append(" and us.gradeId=");
				sb.append(vo.getCurrentGradeId());

				sb.append(" and us.subjectId=");
				sb.append(vo.getCurrentSubjectId());
			}
		}
		return sb.toString();
	}

	/**
	 * 新增同伴关系,每个同伴关系分别以一方为维度添加一条记录
	 * 
	 * @param companion
	 * @see com.tmser.tr.companion.dao.JyCompanionDao#addCompanion(com.tmser.tr.companion.bo.JyCompanion)
	 */
	@Override
	public void addCompanion(JyCompanion companion) {
		this.insert(companion);
		JyCompanion newCompanion = new JyCompanion();
		BeanUtils.copyProperties(companion, newCompanion);

		newCompanion.setUserId(companion.getUserIdCompanion());
		newCompanion.setUserName(companion.getUserNameCompanion());
		newCompanion.setUserNickname(companion.getUserNicknameCompanion());

		newCompanion.setUserIdCompanion(companion.getUserId());
		newCompanion.setUserNameCompanion(companion.getUserName());
		newCompanion.setUserNicknameCompanion(companion.getUserNickname());
		this.insert(newCompanion);

	}

	/**
	 * 更新为好友
	 * 
	 * @param currentUserId
	 * @param userIdCompanion
	 * @return
	 * @see com.tmser.tr.companion.dao.JyCompanionDao#updateCompanionToFriend(java.lang.Integer,
	 *      java.lang.Integer)
	 */
	@Override
	public Integer updateCompanionToFriend(Integer currentUserId,
			Integer userIdCompanion) {
		return this
				.update("update JyCompanion set isFriend=1 where (userId=? and userIdCompanion=?) or (userId=? and userIdCompanion=?)",
						currentUserId, userIdCompanion, userIdCompanion,
						currentUserId);
	}

	/**
	 * 删除好友关系
	 * 
	 * @param currentUserId
	 * @param userIdCompanion
	 * @see com.tmser.tr.companion.dao.JyCompanionDao#deleteCompanion(java.lang.Integer,
	 *      java.lang.Integer)
	 */
	@Override
	public void deleteCompanion(Integer currentUserId, Integer userIdCompanion) {
		this.update(
				"update JyCompanion set isFriend=0 where (userId=? and userIdCompanion=?) or (userId=? and userIdCompanion=?)",
				currentUserId, userIdCompanion, userIdCompanion, currentUserId);
	}

	@Override
	public Integer updateLastCommunicateTime(Integer currentUserId,
			Integer userIdCompanion) {
		return this
				.update("update JyCompanion set lastCommunicateTime=now() where (userId=? and userIdCompanion=?) or (userId=? and userIdCompanion=?)",
						currentUserId, userIdCompanion, userIdCompanion,
						currentUserId);
	}

}
