/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.schoolres.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tmser.tr.common.dao.AbstractQuery;
import com.tmser.tr.common.orm.SqlMapping;
import com.tmser.tr.common.page.Page;
import com.tmser.tr.common.page.PageList;
import com.tmser.tr.schoolres.dao.SchoolResDao;

/**
 * 听课记录 Dao 实现类
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: LectureRecords.java, v 1.0 2015-03-31 Generate Tools Exp $
 */
@Repository
public class SchoolResDaoImpl extends AbstractQuery implements SchoolResDao{
	
	@Autowired(required=false)
	private SqlMapping sqlMapping = null;
	
	@Override
	protected String mappingSql(String oldsql){
		return this.sqlMapping.mapping(oldsql);
	}
	
	/**
	 * @param recordbag
	 * @return
	 * @see com.tmser.tr.schoolres.dao.schoolResDao#findAllRecordbags(com.tmser.tr.recordbag.bo.Recordbag)
	 */
	@Override
	public List<Map<String, Object>> findAllRecordbags(Integer orgID) {
		// TODO Auto-generated method stub
		String sql="SELECT teacherId teacherId,gradeId gradeId,subjectId subjectId,max(shareTime) shareTime,max(modifyTime) modifyTime,COUNT(*) as fenxiangshu FROM Recordbag WHERE share=? and del=? and orgId=? GROUP BY teacherId,gradeId,subjectId";
		List<Map<String, Object>> queryByNamedSql = query(sql, new Object[]{1,0,orgID});
		return queryByNamedSql;
	}

	/**
	 * @return
	 * @see com.tmser.tr.schoolres.dao.SchoolResDao#findPageRecordbags()
	 */
	@Override
	public PageList<Map<String, Object>> findPageRecordbags(Page page,Integer orgID) {
		// TODO Auto-generated method stub
		String sql="SELECT teacherId teacherId,gradeId gradeId,subjectId subjectId,max(shareTime) shareTime,max(modifyTime) modifyTime,COUNT(*) as fenxiangshu FROM Recordbag WHERE share=? and del=? and orgId=? GROUP BY teacherId,gradeId,subjectId";
		PageList<Map<String, Object>> query = queryPage(sql, new Object[]{1,0,orgID}, page);
		return query;
	}
}

