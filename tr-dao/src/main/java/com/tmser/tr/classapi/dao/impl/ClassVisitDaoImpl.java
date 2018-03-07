/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.classapi.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.tmser.tr.classapi.bo.ClassVisit;
import com.tmser.tr.classapi.dao.ClassVisitDao;
import com.tmser.tr.common.dao.AbstractDAO;
import com.tmser.tr.utils.DateUtils;

/**
 * 课堂参与Dao 实现类
 * <pre>
 *
 * </pre>
 *
 * @author ljh
 * @version $Id: ClassUser.java, v 1.0 2016-12-21 ljh Exp $
 */
@Repository
public class ClassVisitDaoImpl extends AbstractDAO<ClassVisit,Integer> implements ClassVisitDao {

	@Override
	public List<ClassVisit> yhjrsjResultByDay(ClassVisit model) {
		String sql = "select count(*) count,date_format(joinTime, '%Y-%m-%d') as startTime  from ClassVisit where joinTime>=:_begin and joinTime<=:_end group by date_format(joinTime, '%Y-%m-%d')";
		Map<String,Object> args = new HashMap<String, Object>();
		Date nextDay = DateUtils.addDays(model.getEndTime(),1);
		args.put("_begin", DateUtils.formatDate(model.getStartTime(), "yyyy-MM-dd"));
		args.put("_end", DateUtils.formatDate(nextDay, "yyyy-MM-dd"));
	    return this.queryByNamedSql(sql, args,  new RowMapper<ClassVisit>(){
			@Override
			public ClassVisit mapRow(ResultSet rs, int rowNum) throws SQLException {
				ClassVisit vo = new ClassVisit();
				vo.setCount(rs.getInt("count"));
				vo.setStartTime(rs.getDate("startTime"));
				return vo;
			}
		});
	}

	@Override
	public List<ClassVisit> yhjrsjResult(ClassVisit model) {
		String sql = "select count(*) count,date_format(joinTime, '%Y-%m-%d %H:%i:%s') as startTime from ClassVisit where joinTime like :_begin group by date_format(joinTime, '%Y-%m-%d %H:%i')";
		Map<String,Object> args = new HashMap<String, Object>();
		args.put("_begin", "%"+DateUtils.formatDate(model.getStartTime(), "yyyy-MM-dd")+"%");
	    return this.queryByNamedSql(sql, args,  new RowMapper<ClassVisit>(){
			@Override
			public ClassVisit mapRow(ResultSet rs, int rowNum) throws SQLException {
				ClassVisit vo = new ClassVisit();
				vo.setCount(rs.getInt("count"));
				String timeStr = rs.getString("startTime");
				try {
					vo.setStartTime(DateUtils.parseDate(timeStr, "yyyy-MM-dd HH:mm:ss"));
				} catch (ParseException e) {
				}
				return vo;
			}
		});
	}
}
