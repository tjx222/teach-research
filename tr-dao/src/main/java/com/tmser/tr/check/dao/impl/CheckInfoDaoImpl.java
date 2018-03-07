/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */

package com.tmser.tr.check.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.tmser.tr.check.bo.CheckInfo;
import com.tmser.tr.check.dao.CheckInfoDao;
import com.tmser.tr.common.ResTypeConstants;
import com.tmser.tr.common.dao.AbstractDAO;

/**
 * 查阅基础信息 Dao 实现类
 * 
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: CheckInfo.java, v 1.0 2015-03-14 Generate Tools Exp $
 */
@Repository
public class CheckInfoDaoImpl extends AbstractDAO<CheckInfo, Integer> implements
    CheckInfoDao {
  /**
   * @see com.tmser.tr.lessonplan.dao.LessonInfoDao#countUncommitLesson(com.tmser.tr.lessonplan.bo.LessonInfo)
   */
  @Override
  public List<Map<String, Object>> countCheckInfoGroupByAuth(CheckInfo model) {
    StringBuilder sql = new StringBuilder();
    List<Object> argList = new ArrayList<Object>();
    sql.append("select l.authorId userId, count(l.id) cnt from CheckInfo l where 1=1 ");

    if (model.getUserId() != null) {
      sql.append("and l.userId = ? ");
      argList.add(model.getUserId());
    }

    if (model.getGradeId() != null) {
      sql.append("and l.gradeId = ? ");
      argList.add(model.getGradeId());
    }
    if (model.getSubjectId() != null) {
      sql.append("and l.subjectId = ? ");
      argList.add(model.getSubjectId());
    }

    if (model.getResType() != null) {
      if (model.getResType().equals(ResTypeConstants.FANSI)) {
        sql.append("and l.resType in(?,?) ");
        argList.add(model.getResType());
        argList.add(ResTypeConstants.FANSI_OTHER);
      } else {
        sql.append("and l.resType = ? ");
        argList.add(model.getResType());
      }

    }

    if (model.getSchoolYear() != null) {
      sql.append("and l.schoolYear = ? ");
      argList.add(model.getSchoolYear());
    }
    sql.append(" group by l.authorId ");

    return this.query(sql.toString(), argList.toArray());
  }

  @Override
  public List<Map<String, Object>> countCheckInfoGroupByUser(CheckInfo model) {
    Map<String, Object> paramMap = new HashMap<String, Object>();
    StringBuilder sql = new StringBuilder();
    sql.append("select l.authorId userId, count(l.id) cnt from CheckInfo l where 1=1 ");
    if (model.getAuthorId() != null) {
      sql.append("and l.authorId = :authorId ");
      paramMap.put("authorId", model.getAuthorId());
    }
    if (model.getUserId() != null) {
      sql.append("and l.userId = :userId ");
      paramMap.put("userId", model.getUserId());
    }
    if (model.getSpaceId() != null) {
      sql.append("and l.spaceId = :spaceId ");
      paramMap.put("spaceId", model.getSpaceId());
    }
    if (model.getGradeId() != null) {
      sql.append("and l.gradeId = :gradeId ");
      paramMap.put("gradeId", model.getGradeId());
    }
    if (model.getSubjectId() != null) {
      sql.append("and l.subjectId = :subjectId ");
      paramMap.put("subjectId", model.getSubjectId());
    }
    if (model.getResType() != null) {
      if (model.getResType().equals(ResTypeConstants.FANSI)) {
        sql.append("and l.resType in(:resType) ");
        paramMap.put("resType",
            Arrays.asList(model.getResType(), ResTypeConstants.FANSI_OTHER));
      } else {
        sql.append("and l.resType = :resType ");
        paramMap.put("resType", model.getResType());
      }
    }
    if (model.getSchoolYear() != null) {
      sql.append("and l.schoolYear = :schoolYear ");
      paramMap.put("schoolYear", model.getSchoolYear());
    }
    if (model.customCondition() != null) {
      sql.append(model.customCondition().getConditon());
      Map<String, Object> paramMaps = model.customCondition().getParamMap();
      if (paramMap != null) {
        for (String param : paramMaps.keySet()) {
          paramMap.put(param, paramMaps.get(param));
        }
      }
    }
    sql.append(" group by l.authorId ");

    return this.queryByNamedSql(sql.toString(), paramMap);
  }

  /**
   * 
   * @see com.tmser.tr.check.dao.CheckInfoDao#updateCheckInfoUpdateState(java.lang.Integer,
   *      java.lang.Integer)
   */
  @Override
  public Integer updateCheckInfoUpdateState(Integer resId, Integer restype,
      Integer schoolYear) {
    StringBuilder sql = new StringBuilder();
    sql.append("update CheckInfo set isUpdate = ? where resId = ? and resType = ? and schoolYear = ?");
    return this
        .update(sql.toString(), Boolean.TRUE, resId, restype, schoolYear);
  }

  /**
   * 统计查阅的资源数目
   * 
   * @see com.tmser.tr.check.dao.CheckInfoDao#countResNum(com.tmser.tr.check.bo.CheckInfo)
   */
  @Override
  public Integer countResNum(CheckInfo info) {
    String sql = "select count( distinct ci.resId) totalNum from CheckInfo ci where ci.resType in (:resTypes) "
        + "and ci.resId in (select id from PlainSummary where isSubmit=1 and gradeId = :gradeId and subjectId = :subjectId"
        + " and userRoleId = :roleId and userId = :userId)";
    Map<String, Object> argMap = new HashMap<String, Object>();
    argMap.put("resTypes", info.getResTypes());
    argMap.put("gradeId", info.getGradeId());
    argMap.put("subjectId", info.getSubjectId());
    argMap.put("roleId", info.getRoleId());
    argMap.put("userId", info.getAuthorId());
    return this.queryByNamedSqlForSingle(sql, argMap, new RowMapper<Integer>() {
      @Override
      public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
        return rs.getInt("totalNum");
      }
    });
  }

  /**
   * 获取所有审阅的资源id
   * 
   * @see com.tmser.tr.check.dao.CheckInfoDao#findResIds(com.tmser.tr.check.bo.CheckInfo)
   */
  @Override
  public List<Integer> findResIds(List<Integer> resTypes) {
    String sql = "select ci.resId resId from CheckInfo ci where ci.resType in(:resTypes)";
    Map<String, Object> argMap = new HashMap<String, Object>();
    argMap.put("resTypes", resTypes);
    return this.queryByNamedSql(sql, argMap, new RowMapper<Integer>() {
      @Override
      public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
        return rs.getInt("resId");
      }
    });
  }

  /**
   * 获取所有审阅的资源id
   * 
   * @see com.tmser.tr.check.dao.CheckInfoDao#findResIdsAndUserId(java.util.List,
   *      java.lang.Integer)
   */
  @Override
  public List<Integer> findResIdsAndUserId(List<Integer> resTypes,
      Integer userId) {
    String sql = "select ci.resId resId from CheckInfo ci where ci.resType in(:resTypes) and ci.userId=:userId";
    Map<String, Object> argMap = new HashMap<String, Object>();
    argMap.put("resTypes", resTypes);
    argMap.put("userId", userId);
    return this.queryByNamedSql(sql, argMap, new RowMapper<Integer>() {
      @Override
      public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
        return rs.getInt("resId");
      }
    });
  }

  @Override
  public List<Map<String, Object>> getAllCheckInfoAverage(CheckInfo ck,
      Integer orgId) {
    final String sqlStr = "select ci.authorId authorId,ci.author author,"
        + "ROUND(avg(case when ci.resType='0' then ci.level end),2) jaAvg,"
        + "ROUND(avg(case when ci.resType='1' then ci.level end),2) kjAvg,"
        + "ROUND(avg(case when ci.resType='5' then  ci.level end),2) jyhdAvg,"
        + "ROUND(avg(case when ci.resType='2' OR ci.resType='3' then ci.level end),2) fsAvg,"
        + "ROUND(avg(case when ci.resType='8' OR ci.resType='9' then ci.level end),2) jhzjAvg,"
        + "count(case when ci.resType='0' then ci.userId end) jaCount,count(case when ci.resType='1' then ci.userId end) kjCount,"
        + "count(case when ci.resType='5' then ci.userId end) jyhdCount,"
        + "count(case when ci.resType='2' OR ci.resType='3' then ci.userId end) fsCount,"
        + "count(case when ci.resType='8' OR ci.resType='9' then ci.userId end) jhzjCount"
        + " from CheckInfo ci,User u where 1=1 and ci.level>0 and ci.schoolYear=:schoolYear "
        + "and ci.term=:termId and ci.gradeId=:gradeId and ci.subjectId=:subjectId and ci.authorId=u.id and u.orgId=:orgId "
        + "GROUP BY ci.authorId, ci.author";
    Map<String, Object> argMap = new HashMap<>();
    argMap.put("schoolYear", ck.getSchoolYear());
    argMap.put("termId", ck.getTerm());
    argMap.put("gradeId", ck.getGradeId());
    argMap.put("subjectId", ck.getSubjectId());
    argMap.put("orgId", orgId);
    return this.queryByNamedSql(sqlStr, argMap);
  }

  @Override
  public List<Map<String, Object>> getAvgByAuthordId(CheckInfo ck) {
    StringBuilder sql = new StringBuilder();
    sql.append("select resId resId,author author,title title,"
        + " resType resType,"
        + " ROUND(avg(level),2) level,count(userId) userCount from CheckInfo where level>0 "
        + "and authorId=:authorId and schoolYear=:schoolYear and term=:termId");
    Map<String, Object> paramMap = new HashMap<String, Object>();

    if (ck.getGradeId() != null) {
      sql.append(" and gradeId=:gradeId ");
      paramMap.put("gradeId", ck.getGradeId());
    }

    if (ck.getSubjectId() != null) {
      sql.append(" and subjectId=:subjectId");
      paramMap.put("subjectId", ck.getSubjectId());
    }

    if (ck.getResType().equals(ResTypeConstants.FANSI)) {
      sql.append(" and resType in(:resType) ");
      paramMap.put("resType",
          Arrays.asList(ck.getResType(), ResTypeConstants.FANSI_OTHER));
    } else if (ck.getResType().equals(ResTypeConstants.TPPLAIN_SUMMARY_SUMMARY)) {
      sql.append(" and resType in(:resType) ");
      paramMap.put("resType", Arrays.asList(ck.getResType(),
          ResTypeConstants.TPPLAIN_SUMMARY_PLIAN));
    } else {
      sql.append(" and resType=:resType");
      paramMap.put("resType", ck.getResType());
    }
    sql.append(" group by resId,author,title");
    paramMap.put("authorId", ck.getAuthorId());
    paramMap.put("schoolYear", ck.getSchoolYear());
    paramMap.put("termId", ck.getTerm());
    return this.queryByNamedSql(sql.toString(), paramMap);
  }

  @Override
  public List<Map<String, Object>> getAllCheckInfoAverageByAuthor(CheckInfo ck) {
    StringBuilder sql = new StringBuilder();
    sql.append("select authorId authorId,");
    sql.append("ROUND(avg(case when resType=0 then level end),2) jaAvg,");
    sql.append("ROUND(avg(case when resType=1 then level end),2) kjAvg,");
    sql.append("ROUND(avg(case when resType=5 then  level end),2) jyhdAvg,");
    sql.append("ROUND(avg(case when resType=2 OR resType=3 then level end),2) fsAvg,");
    sql.append("ROUND(avg(case when resType=8 OR resType=9 then level end),2) jhzjAvg,");
    sql.append("count(case when resType=0 then userId end) jaCount,");
    sql.append("count(case when resType=1 then userId end) kjCount,");
    sql.append("count(case when resType=5 then userId end) jyhdCount,");
    sql.append("count(case when resType=2 OR resType=3 then userId end) fsCount,");
    sql.append("count(case when resType=8 OR resType=9 then userId end) jhzjCount");
    sql.append(" from (select * from CheckInfo where 1=1 and level>0 and schoolYear=:schoolYear ");
    sql.append("and term=:termId and gradeId=:gradeId and subjectId=:subjectId ");
    sql.append("and authorId=:authorId and resType in(0,1,2,3,8,9) union all select * from CheckInfo where 1=1 and level>0 and schoolYear=:schoolYear ");
    sql.append("and resType in(5)");
    sql.append("and term=:termId and authorId=:authorId ) as result");
    Map<String, Object> argMap = new HashMap<>();
    argMap.put("schoolYear", ck.getSchoolYear());
    argMap.put("termId", ck.getTerm());
    argMap.put("gradeId", ck.getGradeId());
    argMap.put("subjectId", ck.getSubjectId());
    argMap.put("authorId", ck.getAuthorId());
    return this.queryByNamedSql(sql.toString(), argMap);
  }

  @Override
  public Integer getAvgByInfoId(CheckInfo ck) {
    String sql = "select avg(level) level from CheckInfo ck where ck.resId=:resId and ck.authorId=:authorId and ck.schoolYear=:schoolYear";
    Map<String, Object> args = new HashMap<String, Object>();
    args.put("resId", ck.getResId());
    args.put("authorId", ck.getAuthorId());
    args.put("schoolYear", ck.getSchoolYear());
    return this.queryByNamedSqlForSingle(sql, args, new RowMapper<Integer>() {
      @Override
      public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
        return rs.getInt("level");
      }
    });
  }

	/** (non-Javadoc)
	 * @see com.tmser.tr.check.dao.CheckInfoDao#countCheckLessonPlanGroupByAuth(com.tmser.tr.check.bo.CheckInfo)
	 */
	@Override
	public List<Map<String, Object>> countCheckLessonPlanGroupByAuth(CheckInfo model) {
		StringBuilder sql = new StringBuilder();
	    List<Object> argList = new ArrayList<Object>();
	    sql.append("select p.userId userId, count(p.planId) cnt from LessonPlan p inner join CheckInfo l on p.infoId=l.resId where 1=1 ");
	
	    if (model.getUserId() != null) {
	      sql.append("and l.userId = ? ");
	      argList.add(model.getUserId());
	    }
	
	    if (model.getGradeId() != null) {
	      sql.append("and l.gradeId = ? ");
	      argList.add(model.getGradeId());
	    }
	    if (model.getSubjectId() != null) {
	      sql.append("and l.subjectId = ? ");
	      argList.add(model.getSubjectId());
	    }
	
	    if (model.getResType() != null) {
	      if (model.getResType().equals(ResTypeConstants.FANSI)) {
	        sql.append("and l.resType in(?,?) ");
	        argList.add(model.getResType());
	        argList.add(ResTypeConstants.FANSI_OTHER);
	      } else {
	        sql.append("and l.resType = ? ");
	        argList.add(model.getResType());
	      }
	      sql.append("and p.planType = ? ");
	      argList.add(model.getResType());
	    }
	
	    if (model.getSchoolYear() != null) {
	      sql.append("and l.schoolYear = ? ");
	      argList.add(model.getSchoolYear());
	    }
	    sql.append(" group by p.userId ");
	
	    return this.query(sql.toString(), argList.toArray());
	}
}
