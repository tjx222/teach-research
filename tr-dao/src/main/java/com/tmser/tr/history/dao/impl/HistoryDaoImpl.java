/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.history.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tmser.tr.common.ResTypeConstants;
import com.tmser.tr.common.dao.AbstractQuery;
import com.tmser.tr.common.orm.SqlMapping;
import com.tmser.tr.history.dao.HistoryDao;
import com.tmser.tr.history.vo.SearchVo;

/**
 * <pre>
 *  历史查询dao实现
 * </pre>
 *
 * @author wangdawei
 * @version $Id: HistoryDaoImpl.java, v 1.0 2016年5月30日 下午3:39:41 wangdawei Exp $
 */
@Repository
public class HistoryDaoImpl extends AbstractQuery implements HistoryDao {

	@Autowired(required=false)
	private SqlMapping sqlMapping = null;
	@Override
	protected String mappingSql(String oldsql){
		return this.sqlMapping.mapping(oldsql);
	}
	/**
	 * 按学年获取集体备课的参与数
	 * @author wangdawei
	 * @param searchVo
	 * @see com.tmser.tr.history.dao.HistoryDao#getActivityHistoryCount_join(com.tmser.tr.history.vo.SearchVo)
	 */
	@Override
	public Integer getActivityHistoryCount_join(SearchVo searchVo) {
		Map<String,Object> args = getArgs(searchVo);
		args.put("editType", 2);
		args.put("typeId", ResTypeConstants.ACTIVITY);
		StringBuffer sql = new StringBuffer("select COUNT(DISTINCT m.activity_id) from");
		sql.append("(select a.activityId from ActivityTracks a inner join Activity b on a.activityId = b.id and b.schoolYear = :schoolYear where a.userId = :userId and a.editType <> :editType ");
		sql.append(" union all select c.activityId from Discuss c inner join Activity b on c.activityId = b.id and c.typeId=:typeId and b.schoolYear = :schoolYear where c.crtId = :userId ) m");
		return countByNamedSql(sql.toString(), args);
	}

	/**
	 * 获取args
	 * @author wangdawei
	 * @param search
	 * @return
	 */
	private Map<String,Object> getArgs(SearchVo search){
		Map<String,Object> args = new HashMap<String, Object>();
		args.put("userId",search.getUserId());
		args.put("spaceId", search.getSpaceId());
		args.put("termId", search.getTermId());
		args.put("schoolYear", search.getSchoolYear());
//		args.put("startTime", search.getStartTime());
//		args.put("endTime", search.getEndTime());
		return args;
	}

	/**
	 * 获得某个学年某个人的校际教研参与数
	 * @param searchVo
	 * @return
	 * @see com.tmser.tr.history.dao.HistoryDao#getSchoolActivityHistoryCount_join(com.tmser.tr.history.vo.SearchVo)
	 */
	@Override
	public Integer getSchoolActivityHistoryCount_join(SearchVo searchVo) {
		Map<String,Object> args = getArgs(searchVo);
		args.put("editType", 2);
		args.put("typeId", ResTypeConstants.SCHOOLTEACH);//校级教研
		StringBuffer sql = new StringBuffer("select COUNT(m.activity_id) from");
		sql.append("(select a.activityId from SchoolActivityTracks a inner join SchoolActivity b on a.activityId = b.id and b.schoolYear = :schoolYear where a.userId = :userId and a.editType <> :editType ");
		sql.append(" union select c.activityId from Discuss c inner join SchoolActivity b on c.activityId = b.id and b.schoolYear = :schoolYear where c.crtId = :userId and c.typeId=:typeId ) m");
		return countByNamedSql(sql.toString(), args);
	}
}
