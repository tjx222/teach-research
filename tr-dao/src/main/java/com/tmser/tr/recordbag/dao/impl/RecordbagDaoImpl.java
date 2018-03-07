/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.recordbag.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.tmser.tr.common.dao.AbstractDAO;
import com.tmser.tr.recordbag.bo.Recordbag;
import com.tmser.tr.recordbag.dao.RecordbagDao;

/**
 * 成长档案袋 Dao 实现类
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: Recordbag.java, v 1.0 2015-04-13 Generate Tools Exp $
 */
@Repository
public class RecordbagDaoImpl extends AbstractDAO<Recordbag,Integer> implements RecordbagDao {

	/**
	 * @param schoolYear
	 * @param orgId
	 * @param userId
	 * @return
	 * @see com.tmser.tr.recordbag.dao.RecordbagDao#updateResCount(java.lang.Integer, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public int updateResCount(Integer schoolYear, Integer orgId,
			Integer userId) {
		StringBuilder sql = new StringBuilder("update Recordbag b SET b.resCount = "
				+ "(SELECT count(r.recordId) "
				+ "from Record r where r.bagId = b.id and r.schoolYear=:shoolyear) where 1=1 ");
		
		if(orgId != null ){
			sql.append(" and r.orgId = :orgId");
		}
		
		if(userId != null ){
			sql.append(" and r.teacherId = :userId");
		}
		
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("shoolyear", schoolYear);
		params.put("orgId", orgId);
		params.put("userId", String.valueOf(userId));
		
		return super.updateWithNamedSql(sql.toString(), params);
	}

}
