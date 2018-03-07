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

import com.tmser.tr.classapi.bo.ClassInfo;
import com.tmser.tr.classapi.dao.ClassInfoDao;
import com.tmser.tr.common.dao.AbstractDAO;
import com.tmser.tr.utils.DateUtils;

/**
 * 课堂附件信息 Dao 实现类
 * <pre>
 *
 * </pre>
 *
 * @author yc
 * @version $Id: ClassInfo.java, v 1.0 2016-09-20 yc Exp $
 */
@Repository
public class ClassInfoDaoImpl extends AbstractDAO<ClassInfo,String> implements ClassInfoDao {

	@Override
	public List<ClassInfo> ktbftjResultByDay(ClassInfo model) {
		String sql = "select count(*) as count,startTime from ClassInfo where startTime>=:_begin and startTime<=:_end group by date_format(startTime, '%Y-%m-%d')";
		Map<String,Object> args = new HashMap<String, Object>();
		Date nextDay = DateUtils.addDays(model.getEndTime(),1);
		args.put("_begin", DateUtils.formatDate(model.getStartTime(), "yyyy-MM-dd"));
		args.put("_end", DateUtils.formatDate(nextDay, "yyyy-MM-dd"));
	    return this.queryByNamedSql(sql, args,  new RowMapper<ClassInfo>(){
			@Override
			public ClassInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
				ClassInfo vo = new ClassInfo();
				vo.setCount(rs.getInt("count"));
				vo.setStartTime(rs.getDate("startTime"));
				return vo;
			}
		});
	}

	@Override
	public List<ClassInfo> ktbftjResult(ClassInfo model) {
		String sql = "select count(*) count,date_format(startTime, '%Y-%m-%d %H:%i:%s') startTime from ClassInfo where startTime like :_begin group by startTime";
		Map<String,Object> args = new HashMap<String, Object>();
		args.put("_begin", "%"+DateUtils.formatDate(model.getStartTime(), "yyyy-MM-dd")+"%");
	    return this.queryByNamedSql(sql, args,  new RowMapper<ClassInfo>(){
			@Override
			public ClassInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
				ClassInfo vo = new ClassInfo();
				vo.setCount(rs.getInt("count"));
				String timeStr =  rs.getString("startTime");
				try {
					vo.setStartTime(DateUtils.parseDate(timeStr, "yyyy-MM-dd HH:mm:ss"));
				} catch (ParseException e) {
				}
				return vo;
			}
		});
	}
}
