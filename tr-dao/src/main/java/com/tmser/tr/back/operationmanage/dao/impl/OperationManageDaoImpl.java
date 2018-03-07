/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.back.operationmanage.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tmser.tr.back.operationmanage.dao.OperationManageDao;
import com.tmser.tr.back.operationmanage.vo.SearchVo;
import com.tmser.tr.common.ResTypeConstants;
import com.tmser.tr.common.dao.AbstractQuery;
import com.tmser.tr.common.orm.SqlMapping;
import com.tmser.tr.uc.SysRole;

/**
 * <pre>
 * 后台运营管理dao
 * </pre>
 *
 * @author daweiwbs
 * @version $Id: OperationManageDaoImpl.java, v 1.0 2015年11月5日 上午10:18:12
 *          daweiwbs Exp $
 */
@Repository
public class OperationManageDaoImpl extends AbstractQuery implements OperationManageDao {

  @Autowired(required = false)
  private SqlMapping sqlMapping = null;

  @Override
  protected String mappingSql(String oldsql) {
    return this.sqlMapping.mapping(oldsql);
  }

  /**
   * 获取机构内的用户数量
   * 
   * @param id
   * @param search
   * @return
   * @see com.tmser.tr.back.dao.OperationManageDao#getUserCount(java.lang.Integer,
   *      com.tmser.tr.back.vo.SearchVo)
   */
  @Override
  public Integer getUserCount(SearchVo search) {
    Map<String, Object> args = getArgsforSql(search);
    StringBuilder sql = new StringBuilder(
        "select count(distinct a.userId) from UserSpace a where a.orgId = :orgId and a.phaseId = :phaseId and a.enable = :enable ");
    if (search.getSubjectId() != null) {
      sql.append(" and a.subjectId = :subjectId");
    }
    if (search.getGradeId() != null) {
      sql.append(" and a.gradeId = :gradeId");
    }
    return countByNamedSql(sql.toString(), args);
  }

  /**
   * 获取撰写教案总数
   * 
   * @param orgId
   * @param search
   * @return
   * @see com.tmser.tr.back.dao.OperationManageDao#getLessonPlanCount(java.lang.Integer,
   *      com.tmser.tr.back.vo.SearchVo)
   */
  @Override
  public Integer getLessonPlanCount(SearchVo search) {
    Map<String, Object> args = getArgsforSql(search);
    args.put("planType", 0);
    StringBuilder sql = new StringBuilder(
        "select count(a.planId) from LessonPlan a where a.enable = :enable and a.orgId = :orgId and a.planType = :planType and a.phaseId = :phaseId and a.crtDttm >= :startTime and a.crtDttm <= :endTime");
    if (search.getSubjectId() != null) {
      sql.append(" and a.subjectId = :subjectId");
    }
    if (search.getGradeId() != null) {
      sql.append(" and a.gradeId = :gradeId");
    }
    return countByNamedSql(sql.toString(), args);
  }

  /**
   * 查阅数
   * 
   * @param orgId
   * @param search
   * @return
   * @see com.tmser.tr.back.dao.OperationManageDao#getViewCount(java.lang.Integer,
   *      com.tmser.tr.back.vo.SearchVo)
   */
  @Override
  public Integer getViewCount(SearchVo search) {
    Map<String, Object> args = getArgsforSql(search);
    args.put("type0", 0);
    args.put("type1", 1);
    args.put("type2", 2);
    args.put("type3", 3);
    args.put("type5", 5);
    args.put("type8", 8);
    args.put("type9", 9);
    StringBuilder sql = new StringBuilder(
        "select count(distinct a.resId) from CheckInfo a INNER JOIN UserSpace u on a.spaceId = u.id and u.orgId = :orgId where a.phase = :phaseId and a.resType in (:type0,:type1,:type2,:type3,:type5,:type8,:type9) and a.createtime >= :startTime and a.createtime <= :endTime");
    if (search.getSubjectId() != null) {
      sql.append(" and a.subjectId = :subjectId");
    }
    if (search.getGradeId() != null) {
      sql.append(" and a.gradeId = :gradeId");
    }
    return countByNamedSql(sql.toString(), args);
  }

  /**
   * 获取分享发表数
   * 
   * @param orgId
   * @param search
   * @return
   * @see com.tmser.tr.back.dao.OperationManageDao#getShareCount(java.lang.Integer,
   *      com.tmser.tr.back.vo.SearchVo)
   */
  @Override
  public Integer getShareCount(SearchVo search) {
    Map<String, Object> args = getArgsforSql(search);
    String conditionStr = "";
    if (search.getSubjectId() != null) {
      conditionStr = conditionStr + " and a.subjectId = :subjectId";
    }
    if (search.getGradeId() != null) {
      conditionStr = conditionStr + " and a.gradeId = :gradeId";
    }
    StringBuilder sql1 = new StringBuilder(
        "select count(a.planId) from LessonPlan a where a.orgId = :orgId and a.phaseId = :phaseId and a.isShare = :isShare and a.enable = :enable and a.shareTime >= :startTime and a.shareTime <= :endTime "
            + conditionStr);
    StringBuilder sql2 = new StringBuilder(
        "select count(a.id) from LectureRecords a where a.phaseId = :phaseId and a.isShare = :isShare and a.isDelete = :isDelete and a.shareTime >= :startTime and a.shareTime <= :endTime and a.orgId = :orgId and a.isEpub = :isEpub ");
    StringBuilder sql3 = new StringBuilder(
        "select (case when sum(a.resCount) is null then 0 else sum(a.resCount) end) from Recordbag a inner join UserSpace u on u.userId=a.teacherId and a.gradeId = u.gradeId and a.subjectId = u.subjectId and u.phaseId = :phaseId and u.sysRoleId=:sysRoleId where a.orgId = :orgId and a.share=:isShare and a.shareTime >= :startTime and a.shareTime <= :endTime"
            + conditionStr);
    StringBuilder sql4 = new StringBuilder(
        "select count(a.id) from PlainSummary a where a.isShare = :isShare and a.shareTime >= :startTime and a.shareTime <= :endTime and a.orgId = :orgId and a.phaseId = :phaseId "
            + conditionStr);
    StringBuilder sql5 = new StringBuilder(
        "select count(a.id) from Thesis a where a.isShare = :isShare and a.shareTime >= :startTime and a.shareTime <= :endTime and a.orgId = :orgId and a.phaseId = :phaseId and a.enable = :enable ");
    StringBuilder sql6 = new StringBuilder(
        "select count(id) from Activity where isShare = :isShare and orgId = :orgId and phaseId = :phaseId and shareTime >= :startTime and shareTime <= :endTime");
    if (search.getSubjectId() != null) {
      sql6.append(" and subjectIds like :lksubjectId");
    }
    if (search.getGradeId() != null) {
      sql6.append(" and gradeIds like :lkgradeId");
    }
    StringBuilder sql7 = new StringBuilder(
        "select count(id) from SchoolActivity where isShare = :isShare and orgId = :orgId and phaseId = :phaseId and shareTime >= :startTime and shareTime <= :endTime");
    if (search.getSubjectId() != null) {
      sql7.append(" and subjectIds like :lksubjectId");
    }
    if (search.getGradeId() != null) {
      sql7.append(" and gradeIds like :lkgradeId");
    }
    args.put("sysRoleId", SysRole.TEACHER.getId());
    return countByNamedSql(sql1.toString(), args) + countByNamedSql(sql2.toString(), args)
        + countByNamedSql(sql3.toString(), args) + countByNamedSql(sql4.toString(), args)
        + countByNamedSql(sql5.toString(), args) + countByNamedSql(sql6.toString(), args)
        + countByNamedSql(sql7.toString(), args);
  }

  /**
   * 获取集体备课发起数量
   * 
   * @param orgId
   * @param search
   * @return
   * @see com.tmser.tr.back.dao.OperationManageDao#getActivityPushCount(java.lang.Integer,
   *      com.tmser.tr.back.vo.SearchVo)
   */
  @Override
  public Integer getActivityPushCount(SearchVo search) {
    Map<String, Object> args = getArgsforSql(search);
    args.put("status", 1);
    StringBuilder sql = new StringBuilder(
        "select count(id) from Activity where orgId = :orgId and phaseId = :phaseId and status = :status and releaseTime >= :startTime and releaseTime <= :endTime");
    if (search.getSubjectId() != null) {
      sql.append(" and subjectIds like :lksubjectId");
    }
    if (search.getGradeId() != null) {
      sql.append(" and gradeIds like :lkgradeId");
    }
    return countByNamedSql(sql.toString(), args);
  }

  /**
   * 获取集体备课的参与人数
   * 
   * @param orgId
   * @param search
   * @return
   * @see com.tmser.tr.back.dao.OperationManageDao#getActivityJoinCount(java.lang.Integer,
   *      com.tmser.tr.back.vo.SearchVo)
   */
  @Override
  public Integer getActivityJoinCount(SearchVo search) {
    Map<String, Object> args = getArgsforSql(search);
    args.put("editType", 2);
    String conditionStr = "";
    String conditionStr1 = "";
    conditionStr1 = conditionStr1 + " INNER JOIN Activity b on a.activityId = b.id and b.phaseId = :phaseId ";
    if (search.getSubjectId() != null) {
      conditionStr = conditionStr + " and b.subjectIds like :lksubjectId";
      conditionStr1 = conditionStr1 + " and b.subjectIds like :lksubjectId";
    }
    if (search.getGradeId() != null) {
      conditionStr = conditionStr + " and b.gradeIds like :lkgradeId";
      conditionStr1 = conditionStr1 + " and b.gradeIds like :lkgradeId";
    }
    StringBuilder sql = new StringBuilder("select COUNT(DISTINCT m.activity_id,m.user_id) from");
    sql.append("(select a.activityId,a.userId from ActivityTracks a " + conditionStr1
        + " where a.orgId = :orgId and a.editType <> :editType and a.crtDttm >= :startTime and a.crtDttm <= :endTime");
    sql.append(" union all select c.activityId,c.crtId from Discuss c INNER JOIN Activity b on c.activityId = b.id and b.orgId = :orgId and b.phaseId = :phaseId and c.typeId=:typeId "
        + conditionStr + " where c.crtDttm >= :startTime and c.crtDttm <= :endTime) m");
    args.put("typeId", ResTypeConstants.ACTIVITY);
    return countByNamedSql(sql.toString(), args);
  }

  /**
   * 获取成长档案精品资源数
   * 
   * @param orgId
   * @param search
   * @return
   * @see com.tmser.tr.back.dao.OperationManageDao#getProgressResCount(java.lang.Integer,
   *      com.tmser.tr.back.vo.SearchVo)
   */
  @Override
  public Integer getProgressResCount(SearchVo search) {
    Map<String, Object> args = getArgsforSql(search);
    StringBuilder sql = new StringBuilder(
        "select sum(a.resCount) from Recordbag a inner join UserSpace u on u.userId=a.teacherId and a.gradeId = u.gradeId and a.subjectId = u.subjectId and u.phaseId = :phaseId and u.sysRoleId=:sysRoleId where a.orgId = :orgId and a.del = :isDelete and a.createTime >=:startTime and a.createTime <= :endTime ");
    if (search.getSubjectId() != null) {
      sql.append(" and a.subjectId = :subjectId");
    }
    if (search.getGradeId() != null) {
      sql.append(" and a.gradeId = :gradeId");
    }
    args.put("sysRoleId", SysRole.TEACHER.getId());
    return countByNamedSql(sql.toString(), args);
  }

  /**
   * 同伴互助留言数
   * 
   * @param id
   * @param search
   * @return
   * @see com.tmser.tr.back.dao.OperationManageDao#getPeerMessageCount(java.lang.Integer,
   *      com.tmser.tr.back.vo.SearchVo)
   */
  @Override
  public Integer getPeerMessageCount(SearchVo search) {
    Map<String, Object> args = getArgsforSql(search);
    StringBuilder sql = new StringBuilder(
        "select count(a.id) from JyCompanionMessage a where a.userIdSender in (select DISTINCT(b.userId) from UserSpace b where b.orgId = :orgId and b.phaseId = :phaseId ) and a.senderTime >= :startTime and a.senderTime <= :endTime");
    return countByNamedSql(sql.toString(), args);
  }

  /**
   * 获取资源总数
   * 
   * @param orgId
   * @param search
   * @return
   * @see com.tmser.tr.back.dao.OperationManageDao#getResTotalCount(java.lang.Integer,
   *      com.tmser.tr.back.vo.SearchVo)
   */
  @Override
  public Integer getResTotalCount(SearchVo search) {
    Map<String, Object> args = getArgsforSql(search);
    args.put("status", 1);
    args.put("typeId", ResTypeConstants.SCHOOLTEACH);
    String conditionStr = "";
    String conditionStr1 = "";
    String conditionStr2 = "";
    if (search.getSubjectId() != null) {
      conditionStr = conditionStr + " and subjectId = :subjectId";
      conditionStr1 = conditionStr1 + " and subjectIds like :lksubjectId";
      conditionStr2 = conditionStr2 + " and b.subjectIds like :lksubjectId";
    }
    if (search.getGradeId() != null) {
      conditionStr = conditionStr + " and gradeId = :gradeId";
      conditionStr1 = conditionStr1 + " and gradeIds like :lkgradeId";
      conditionStr2 = conditionStr2 + " and b.gradeIds like :lkgradeId";
    }
    StringBuilder sql1 = new StringBuilder(
        "select count(planId) from LessonPlan where orgId = :orgId and phaseId = :phaseId and crtDttm >= :startTime and crtDttm <= :endTime and enable = :enable "
            + conditionStr);
    StringBuilder sql2 = new StringBuilder(
        "select count(id) from Thesis where crtDttm >= :startTime and crtDttm <= :endTime and orgId = :orgId and phaseId = :phaseId and enable = :enable");
    StringBuilder sql3 = new StringBuilder(
        "select count(id) from LectureRecords where crtDttm >= :startTime and crtDttm <= :endTime and orgId = :orgId and phaseId = :phaseId and isDelete = :isDelete and isEpub = :isEpub ");
    StringBuilder sql5 = new StringBuilder(
        "select count(id) from PlainSummary where phaseId = :phaseId and crtDttm >= :startTime and crtDttm <= :endTime and orgId = :orgId"
            + conditionStr);
    StringBuilder sql8 = new StringBuilder("select count(DISTINCT m.activity_id) from ");
    sql8.append("(select a.activityId from SchoolActivityTracks a INNER JOIN SchoolActivity b on a.activityId = b.id and b.phaseId = :phaseId "
        + conditionStr2 + " where a.orgId = :orgId and a.crtDttm >=:startTime and a.crtDttm <=:endTime ");
    sql8.append("union ALL select c.activityId from Discuss c inner join UserSpace u on c.spaceId = u.id and u.orgId = :orgId INNER JOIN SchoolActivity b on c.activityId = b.id and b.phaseId = :phaseId "
        + conditionStr2 + " where c.typeId = :typeId and c.crtDttm >=:startTime and c.crtDttm <=:endTime ");
    sql8.append("union all select b.id as activity_id from SchoolActivity b where b.orgId = :orgId and b.phaseId = :phaseId and b.status = :status and b.createTime >= :startTime and b.createTime <= :endTime "
        + conditionStr2 + " ) m");

    return countByNamedSql(sql1.toString(), args) + countByNamedSql(sql2.toString(), args)
        + countByNamedSql(sql3.toString(), args) + getProgressResCount(search) + countByNamedSql(sql5.toString(), args)
        + getActivityPushCount(search) + countByNamedSql(sql8.toString(), args);
  }

  /**
   * 教案总数（老师）
   * 
   * @param userId
   * @param search
   * @return
   * @see com.tmser.tr.back.dao.OperationManageDao#getLessonPlanCount_teacher(java.lang.Integer,
   *      com.tmser.tr.back.vo.SearchVo)
   */
  @Override
  public Integer getLessonPlanCount_teacher(SearchVo search) {
    Map<String, Object> args = getArgsforSql(search);
    args.put("jiaoan", 0);
    String conditionStr = "";
    if (search.getSubjectId() != null) {
      conditionStr = conditionStr + " and subjectId = :subjectId";
    }
    if (search.getGradeId() != null) {
      conditionStr = conditionStr + " and gradeId = :gradeId";
    }
    StringBuilder sql = new StringBuilder(
        "select count(planId) from LessonPlan where userId = :userId and enable = :enable and planType = :jiaoan and crtDttm >= :startTime and crtDttm <= :endTime "
            + conditionStr);
    return countByNamedSql(sql.toString(), args);
  }

  /**
   * 获取参数map
   * 
   * @param sql
   * @param userId
   * @param search
   * @return
   */
  private Map<String, Object> getArgsforSql(SearchVo search) {
    Map<String, Object> args = new HashMap<String, Object>();
    args.put("subjectId", search.getSubjectId());
    args.put("gradeId", search.getGradeId());
    args.put("userId", search.getUserId());
    args.put("startTime", search.getStartTime());
    args.put("endTime", search.getEndTime());
    args.put("teacher", SysRole.TEACHER.getId());
    args.put("roleId", search.getRoleId());
    args.put("areaId", search.getAreaId());
    args.put("orgId", search.getOrgId());
    args.put("phaseId", search.getPhaseId());
    args.put("enable", 1);
    args.put("isDelete", 0);
    args.put("isEpub", 1);
    args.put("isShare", 1);

    args.put("lksubjectId", "%," + search.getSubjectId() + ",%");
    args.put("lkgradeId", "%," + search.getGradeId() + ",%");
    args.put("lkuserId", "%," + search.getUserId() + ",%");
    args.put("lkorgId", "%," + search.getOrgId() + ",%");

    return args;
  }

  /**
   * 课件总数（老师）
   * 
   * @param userId
   * @param search
   * @return
   * @see com.tmser.tr.back.dao.OperationManageDao#getKejianCount_teacher(java.lang.Integer,
   *      com.tmser.tr.back.vo.SearchVo)
   */
  @Override
  public Integer getKejianCount_teacher(SearchVo search) {
    Map<String, Object> args = getArgsforSql(search);
    args.put("kejian", 1);
    String conditionStr = "";
    if (search.getSubjectId() != null) {
      conditionStr = conditionStr + " and subjectId = :subjectId";
    }
    if (search.getGradeId() != null) {
      conditionStr = conditionStr + " and gradeId = :gradeId";
    }
    StringBuilder sql = new StringBuilder(
        "select count(planId) from LessonPlan where userId = :userId and enable = :enable and planType = :kejian and crtDttm >= :startTime and crtDttm <= :endTime "
            + conditionStr);
    return countByNamedSql(sql.toString(), args);
  }

  /**
   * 反思总数（老师）
   * 
   * @param userId
   * @param search
   * @return
   * @see com.tmser.tr.back.dao.OperationManageDao#getFansiCount_teacher(java.lang.Integer,
   *      com.tmser.tr.back.vo.SearchVo)
   */
  @Override
  public Integer getFansiCount_teacher(SearchVo search) {
    Map<String, Object> args = getArgsforSql(search);
    args.put("kehou", 2);
    args.put("qita", 3);
    String conditionStr = "";
    if (search.getSubjectId() != null) {
      conditionStr = conditionStr + " and subjectId = :subjectId";
    }
    if (search.getGradeId() != null) {
      conditionStr = conditionStr + " and gradeId = :gradeId";
    }
    StringBuilder sql = new StringBuilder(
        "select count(planId) from LessonPlan where userId = :userId and enable = :enable and (planType = :kehou or planType = :qita) and crtDttm >= :startTime and crtDttm <= :endTime "
            + conditionStr);
    return countByNamedSql(sql.toString(), args);
  }

  /**
   * 听课记录节数（老师）
   * 
   * @param userId
   * @param search
   * @return
   * @see com.tmser.tr.back.dao.OperationManageDao#getListenCount_teacher(java.lang.Integer,
   *      com.tmser.tr.back.vo.SearchVo)
   */
  @Override
  public Integer getListenCount_teacher(SearchVo search) {
    Map<String, Object> args = getArgsforSql(search);
    StringBuilder sql = new StringBuilder(
        "select sum(numberLectures) from LectureRecords where lecturepeopleId = :userId and phaseId = :phaseId and isDelete = :isDelete and isEpub = :isEpub and crtDttm >= :startTime and crtDttm <= :endTime ");
    return countByNamedSql(sql.toString(), args);
  }

  /**
   * 教学文章篇数（老师）
   * 
   * @param userId
   * @param search
   * @return
   * @see com.tmser.tr.back.dao.OperationManageDao#getTeachTextCount_teacher(java.lang.Integer,
   *      com.tmser.tr.back.vo.SearchVo)
   */
  @Override
  public Integer getTeachTextCount_teacher(SearchVo search) {
    Map<String, Object> args = getArgsforSql(search);
    StringBuilder sql = new StringBuilder(
        "select count(id) from Thesis where userId = :userId and enable = :enable and crtDttm >= :startTime and crtDttm <= :endTime ");
    return countByNamedSql(sql.toString(), args);
  }

  /**
   * 计划总结总数（老师）
   * 
   * @param userId
   * @param search
   * @return
   * @see com.tmser.tr.back.dao.OperationManageDao#getPlanSummaryCount_teacher(java.lang.Integer,
   *      com.tmser.tr.back.vo.SearchVo)
   */
  @Override
  public Integer getPlanSummaryCount_teacher(SearchVo search) {
    Map<String, Object> args = getArgsforSql(search);
    String conditionStr = "";
    if (search.getSubjectId() != null) {
      conditionStr = conditionStr + " and subjectId = :subjectId";
    }
    if (search.getGradeId() != null) {
      conditionStr = conditionStr + " and gradeId = :gradeId";
    }
    StringBuilder sql = new StringBuilder(
        "select count(id) from PlainSummary where userId = :userId and roleId = :teacher and crtDttm >= :startTime and crtDttm <= :endTime "
            + conditionStr);
    return countByNamedSql(sql.toString(), args);
  }

  /**
   * 集体备课参与次数（老师）
   * 
   * @param userId
   * @param search
   * @return
   * @see com.tmser.tr.back.dao.OperationManageDao#getActivityJoinCount_teacher(java.lang.Integer,
   *      com.tmser.tr.back.vo.SearchVo)
   */
  @Override
  public Integer getActivityJoinCount_teacher(SearchVo search) {
    Map<String, Object> args = getArgsforSql(search);
    args.put("editType", 2);
    String conditionStr = "";
    String conditionStr1 = "";
    conditionStr = conditionStr + " INNER JOIN UserSpace b on a.spaceId = b.id and b.sysRoleId = :teacher ";
    conditionStr1 = conditionStr1 + " INNER JOIN UserSpace b on c.spaceId = b.id and b.sysRoleId = :teacher ";
    if (search.getSubjectId() != null) {
      conditionStr = conditionStr + " and b.subjectId = :subjectId ";
      conditionStr1 = conditionStr1 + " and b.subjectId = :subjectId ";
    }
    if (search.getGradeId() != null) {
      conditionStr = conditionStr + " and b.gradeId = :gradeId ";
      conditionStr1 = conditionStr1 + " and b.gradeId = :gradeId ";
    }
    StringBuilder sql = new StringBuilder("select COUNT(DISTINCT m.activity_id) from");
    sql.append("(select a.activityId from ActivityTracks a "
        + conditionStr
        + " where a.userId = :userId and a.editType <> :editType and a.crtDttm >= :startTime and a.crtDttm <= :endTime ");
    sql.append(" union all select c.activityId from Discuss c " + conditionStr1
        + " where c.crtId = :userId and c.typeId=:typeId and c.crtDttm >= :startTime and c.crtDttm <= :endTime) m");
    args.put("typeId", ResTypeConstants.ACTIVITY);
    return countByNamedSql(sql.toString(), args);
  }

  /**
   * 集体备课讨论数（老师）
   * 
   * @param userId
   * @param search
   * @return
   * @see com.tmser.tr.back.dao.OperationManageDao#getActivityDiscussCount_teacher(java.lang.Integer,
   *      com.tmser.tr.back.vo.SearchVo)
   */
  @Override
  public Integer getActivityDiscussCount_teacher(SearchVo search) {
    Map<String, Object> args = getArgsforSql(search);
    String conditionStr1 = "";
    conditionStr1 = conditionStr1 + " INNER JOIN UserSpace b on c.spaceId = b.id and b.sysRoleId = :teacher";
    if (search.getSubjectId() != null) {
      conditionStr1 = conditionStr1 + " and b.subjectId = :subjectId ";
    }
    if (search.getGradeId() != null) {
      conditionStr1 = conditionStr1 + " and b.gradeId = :gradeId ";
    }
    StringBuilder sql = new StringBuilder("select COUNT(c.id) from Discuss c " + conditionStr1
        + " where c.crtId = :userId and c.typeId=:typeId and c.crtDttm >= :startTime and c.crtDttm <= :endTime");
    args.put("typeId", ResTypeConstants.ACTIVITY);
    return countByNamedSql(sql.toString(), args);
  }

  /**
   * 集体备课任主备人次数（老师）
   * 
   * @param userId
   * @param search
   * @return
   * @see com.tmser.tr.back.dao.OperationManageDao#getActivityMainCount_teacher(java.lang.Integer,
   *      com.tmser.tr.back.vo.SearchVo)
   */
  @Override
  public Integer getActivityMainCount_teacher(SearchVo search) {
    Map<String, Object> args = getArgsforSql(search);
    args.put("status", 1);
    StringBuilder sql = new StringBuilder(
        "select count(id) from Activity where mainUserId = :userId and status = :status and releaseTime >= :startTime and releaseTime <= :endTime");
    if (search.getSubjectId() != null) {
      sql.append(" and mainUserSubjectId = :subjectId ");
    }
    if (search.getGradeId() != null) {
      sql.append(" and mainUserGradeId = :gradeId ");
    }
    return countByNamedSql(sql.toString(), args);
  }

  /**
   * 校际教研参与次数（老师）
   * 
   * @param userId
   * @param search
   * @return
   * @see com.tmser.tr.back.dao.OperationManageDao#getSchoolActivityJoinCount_teacher(java.lang.Integer,
   *      com.tmser.tr.back.vo.SearchVo)
   */
  @Override
  public Integer getSchoolActivityJoinCount_teacher(SearchVo search) {
    Map<String, Object> args = getArgsforSql(search);
    args.put("editType", 2);
    args.put("typeId", ResTypeConstants.SCHOOLTEACH);
    String conditionStr = "";
    String conditionStr1 = "";
    conditionStr = conditionStr + " INNER JOIN UserSpace b on a.spaceId = b.id and b.sysRoleId = :teacher ";
    conditionStr1 = conditionStr1 + " INNER JOIN UserSpace b on c.spaceId = b.id and b.sysRoleId = :teacher ";
    if (search.getSubjectId() != null) {
      conditionStr = conditionStr + " and b.subjectId = :subjectId ";
      conditionStr1 = conditionStr1 + " and b.subjectId = :subjectId ";
    }
    if (search.getGradeId() != null) {
      conditionStr = conditionStr + " and b.gradeId = :gradeId ";
      conditionStr1 = conditionStr1 + " and b.gradeId = :gradeId ";
    }
    StringBuilder sql = new StringBuilder("select COUNT(DISTINCT m.activity_id) from");
    sql.append("(select a.activityId from SchoolActivityTracks a "
        + conditionStr
        + " where a.userId = :userId and a.editType <> :editType and a.crtDttm >= :startTime and a.crtDttm <= :endTime ");
    sql.append(" union all select c.activityId from Discuss c " + conditionStr1
        + " where c.typeId = :typeId and c.crtId = :userId and c.crtDttm >= :startTime and c.crtDttm <= :endTime) m");
    return countByNamedSql(sql.toString(), args);
  }

  /**
   * 校际教研讨论数（老师）
   * 
   * @param userId
   * @param search
   * @return
   * @see com.tmser.tr.back.dao.OperationManageDao#getSchoolActivityDiscussCount_teacher(java.lang.Integer,
   *      com.tmser.tr.back.vo.SearchVo)
   */
  @Override
  public Integer getSchoolActivityDiscussCount_teacher(SearchVo search) {
    Map<String, Object> args = getArgsforSql(search);
    args.put("typeId", ResTypeConstants.SCHOOLTEACH);
    String conditionStr1 = "";
    conditionStr1 = conditionStr1 + " INNER JOIN UserSpace b on c.spaceId = b.id and b.sysRoleId = :teacher ";
    if (search.getSubjectId() != null) {
      conditionStr1 = conditionStr1 + " and b.subjectId = :subjectId ";
    }
    if (search.getGradeId() != null) {
      conditionStr1 = conditionStr1 + " and b.gradeId = :gradeId ";
    }
    StringBuilder sql = new StringBuilder("select COUNT(c.id) from Discuss c " + conditionStr1
        + " where c.typeId = :typeId and c.crtId = :userId and c.crtDttm >= :startTime and c.crtDttm <= :endTime");
    return countByNamedSql(sql.toString(), args);
  }

  /**
   * 校际教研任主备人次数（老师）
   * 
   * @param userId
   * @param search
   * @return
   * @see com.tmser.tr.back.dao.OperationManageDao#getSchoolActivityMainCount_teacher(java.lang.Integer,
   *      com.tmser.tr.back.vo.SearchVo)
   */
  @Override
  public Integer getSchoolActivityMainCount_teacher(SearchVo search) {
    Map<String, Object> args = getArgsforSql(search);
    args.put("status", 1);
    StringBuilder sql = new StringBuilder(
        "select count(id) from SchoolActivity where mainUserId = :userId and status = :status and releaseTime >= :startTime and releaseTime <= :endTime");
    if (search.getSubjectId() != null) {
      sql.append(" and mainUserSubjectId = :subjectId ");
    }
    if (search.getGradeId() != null) {
      sql.append(" and mainUserGradeId = :gradeId ");
    }
    return countByNamedSql(sql.toString(), args);
  }

  /**
   * 同伴互助留言数（老师）
   * 
   * @param userId
   * @param search
   * @return
   * @see com.tmser.tr.back.dao.OperationManageDao#getPeerMessageCount_teacher(java.lang.Integer,
   *      com.tmser.tr.back.vo.SearchVo)
   */
  @Override
  public Integer getPeerMessageCount_teacher(SearchVo search) {
    Map<String, Object> args = getArgsforSql(search);
    StringBuilder sql = new StringBuilder(
        "select count(id) from JyCompanionMessage where userIdSender = :userId and senderTime >= :startTime and senderTime <= :endTime");
    return countByNamedSql(sql.toString(), args);
  }

  /**
   * 成长档案袋已精选资源数（老师）
   * 
   * @param userId
   * @param search
   * @return
   * @see com.tmser.tr.back.dao.OperationManageDao#getProgressResCount_teacher(java.lang.Integer,
   *      com.tmser.tr.back.vo.SearchVo)
   */
  @Override
  public Integer getProgressResCount_teacher(SearchVo search) {
    Map<String, Object> args = getArgsforSql(search);
    args.put("status", 1);
    StringBuilder sql = new StringBuilder(
        "select count(a.recordId) from Record a INNER JOIN Recordbag b on a.bagId = b.id and b.teacherId = :userId");
    if (search.getSubjectId() != null) {
      sql.append(" and b.subjectId = :subjectId");
    }
    if (search.getGradeId() != null) {
      sql.append(" and b.gradeId = :gradeId");
    }
    sql.append(" where a.createTime >=:startTime and a.createTime <= :endTime and a.status = :status");
    return countByNamedSql(sql.toString(), args);
  }

  /**
   * 分享发表数（老师）
   * 
   * @param userId
   * @param search
   * @return
   * @see com.tmser.tr.back.dao.OperationManageDao#getShareCount_teacher(java.lang.Integer,
   *      com.tmser.tr.back.vo.SearchVo)
   */
  @Override
  public Integer getShareCount_teacher(SearchVo search) {
    Map<String, Object> args = getArgsforSql(search);
    args.put("isShare", 1);
    args.put("share", 1);
    String conditionStr = "";
    if (search.getSubjectId() != null) {
      conditionStr = conditionStr + " and subjectId = :subjectId";
    }
    if (search.getGradeId() != null) {
      conditionStr = conditionStr + " and gradeId = :gradeId";
    }
    StringBuilder sql1 = new StringBuilder(
        "select count(planId) from LessonPlan where isShare = :isShare and shareTime >= :startTime and shareTime <= :endTime and userId = :userId"
            + conditionStr);
    StringBuilder sql2 = new StringBuilder(
        "select count(id) from LectureRecords where isShare = :isShare and shareTime >= :startTime and shareTime <= :endTime and lecturepeopleId = :userId");
    StringBuilder sql3 = new StringBuilder(
        "select (case when sum(resCount) is null then 0 else sum(resCount) end) from Recordbag  where teacherId=:userId and share=:share and shareTime >= :startTime and shareTime <= :endTime"
            + conditionStr);
    StringBuilder sql4 = new StringBuilder(
        "select count(id) from PlainSummary where roleId=:teacher and isShare = :isShare and shareTime >= :startTime and shareTime <= :endTime and userId = :userId and roleId = :teacher "
            + conditionStr);
    StringBuilder sql5 = new StringBuilder(
        "select count(id) from Thesis where isShare = :isShare and shareTime >= :startTime and shareTime <= :endTime and userId = :userId and enable = :enable");
    return countByNamedSql(sql1.toString(), args) + countByNamedSql(sql2.toString(), args)
        + countByNamedSql(sql3.toString(), args) + countByNamedSql(sql4.toString(), args)
        + countByNamedSql(sql5.toString(), args);
  }

  /**
   * 查阅教案数（管理者）
   * 
   * @param userId
   * @param search
   * @return
   * @see com.tmser.tr.back.dao.OperationManageDao#getViewLessonPlanCount_leader(java.lang.Integer,
   *      com.tmser.tr.back.vo.SearchVo)
   */
  @Override
  public Integer getViewLessonPlanCount_leader(SearchVo search) {
    Map<String, Object> args = getArgsforSql(search);
    args.put("resType", ResTypeConstants.JIAOAN);
    StringBuilder sql = new StringBuilder("select count(distinct a.resId) from CheckInfo a ");
    // if(search.getRoleId()!=null || search.getSubjectId()!=null ||
    // search.getGradeId()!=null){
    sql.append(" inner join UserSpace u on a.spaceId = u.id and u.orgId = :orgId ");
    if (search.getRoleId() != null) {
      sql.append(" and u.sysRoleId = :roleId");
    }
    if (search.getSubjectId() != null) {
      sql.append(" and u.subjectId = :subjectId");
    }
    if (search.getGradeId() != null) {
      sql.append(" and u.gradeId = :gradeId");
    }
    // }
    sql.append(" where a.phase = :phaseId and a.resType = :resType and a.userId = :userId and a.createtime >= :startTime and a.createtime <= :endTime");
    return countByNamedSql(sql.toString(), args);
  }

  /**
   * 查阅课件数（管理者）
   * 
   * @param userId
   * @param search
   * @return
   */
  @Override
  public Integer getViewKejianCount_leader(SearchVo search) {
    Map<String, Object> args = getArgsforSql(search);
    args.put("resType", ResTypeConstants.KEJIAN);
    StringBuilder sql = new StringBuilder("select count(distinct a.resId) from CheckInfo a ");
    // if(search.getRoleId()!=null || search.getSubjectId()!=null ||
    // search.getGradeId()!=null){
    sql.append(" inner join UserSpace u on a.spaceId = u.id and u.orgId = :orgId ");
    if (search.getRoleId() != null) {
      sql.append(" and u.sysRoleId = :roleId");
    }
    if (search.getSubjectId() != null) {
      sql.append(" and u.subjectId = :subjectId");
    }
    if (search.getGradeId() != null) {
      sql.append(" and u.gradeId = :gradeId");
    }
    // }
    sql.append(" where a.phase = :phaseId and a.resType = :resType and a.userId = :userId and a.createtime >= :startTime and a.createtime <= :endTime");
    return countByNamedSql(sql.toString(), args);
  }

  /**
   * 查阅反思数（管理者）
   * 
   * @param userId
   * @param search
   * @return
   */
  @Override
  public Integer getViewFansiCount_leader(SearchVo search) {
    Map<String, Object> args = getArgsforSql(search);
    args.put("resType1", ResTypeConstants.FANSI);
    args.put("resType2", ResTypeConstants.FANSI_OTHER);
    StringBuilder sql = new StringBuilder("select count(distinct a.resId) from CheckInfo a ");
    // if(search.getRoleId()!=null || search.getSubjectId()!=null ||
    // search.getGradeId()!=null){
    sql.append(" inner join UserSpace u on a.spaceId = u.id and u.orgId = :orgId ");
    if (search.getRoleId() != null) {
      sql.append(" and u.sysRoleId = :roleId");
    }
    if (search.getSubjectId() != null) {
      sql.append(" and u.subjectId = :subjectId");
    }
    if (search.getGradeId() != null) {
      sql.append(" and u.gradeId = :gradeId");
    }
    // }
    sql.append(" where a.phase = :phaseId and (a.resType = :resType1 or a.resType = :resType2) and a.userId = :userId and a.createtime >= :startTime and a.createtime <= :endTime");
    return countByNamedSql(sql.toString(), args);
  }

  /**
   * 查阅计划总结数（管理者）
   * 
   * @param userId
   * @param search
   * @return
   */
  @Override
  public Integer getViewPlanSummaryCount_leader(SearchVo search) {
    Map<String, Object> args = getArgsforSql(search);
    args.put("resType1", ResTypeConstants.TPPLAIN_SUMMARY_PLIAN);
    args.put("resType2", ResTypeConstants.TPPLAIN_SUMMARY_SUMMARY);
    StringBuilder sql = new StringBuilder("select count(distinct a.resId) from CheckInfo a ");
    // if(search.getRoleId()!=null || search.getSubjectId()!=null ||
    // search.getGradeId()!=null){
    sql.append(" inner join UserSpace u on a.spaceId = u.id and u.orgId = :orgId ");
    if (search.getRoleId() != null) {
      sql.append(" and u.sysRoleId = :roleId");
    }
    if (search.getSubjectId() != null) {
      sql.append(" and u.subjectId = :subjectId");
    }
    if (search.getGradeId() != null) {
      sql.append(" and u.gradeId = :gradeId");
    }
    // }
    sql.append(" where a.phase = :phaseId and (a.resType = :resType1 or a.resType = :resType2) and a.userId = :userId and a.createtime >= :startTime and a.createtime <= :endTime");
    return countByNamedSql(sql.toString(), args);
  }

  /**
   * 听课记录数（管理者）
   * 
   * @param userId
   * @param search
   * @return
   */
  @Override
  public Integer getListenCount_leader(SearchVo search) {
    Map<String, Object> args = getArgsforSql(search);
    StringBuilder sql = new StringBuilder(
        "select count(id) from LectureRecords where phaseId = :phaseId and lecturepeopleId = :userId and isDelete = :isDelete and isEpub = :isEpub and crtDttm >= :startTime and crtDttm <= :endTime ");
    return countByNamedSql(sql.toString(), args);
  }

  /**
   * 教学文章数（管理者）
   * 
   * @param userId
   * @param search
   * @return
   */
  @Override
  public Integer getTeachTextCount_leader(SearchVo search) {
    Map<String, Object> args = getArgsforSql(search);
    StringBuilder sql = new StringBuilder(
        "select count(id) from Thesis where userId = :userId and enable = :enable and crtDttm >= :startTime and crtDttm <= :endTime ");
    return countByNamedSql(sql.toString(), args);
  }

  /**
   * 计划总结数（管理者）
   * 
   * @param userId
   * @param search
   * @return
   */
  @Override
  public Integer getPlanSummaryCount_leader(SearchVo search) {
    Map<String, Object> args = getArgsforSql(search);
    String conditionStr = " and roleId <> :teacher";
    if (search.getRoleId() != null) {
      conditionStr = conditionStr + " and roleId = :roleId";
    }
    if (search.getSubjectId() != null) {
      conditionStr = conditionStr + " and subjectId = :subjectId";
    }
    if (search.getGradeId() != null) {
      conditionStr = conditionStr + " and gradeId = :gradeId";
    }
    StringBuilder sql = new StringBuilder(
        "select count(id) from PlainSummary where userId = :userId and crtDttm >= :startTime and crtDttm <= :endTime "
            + conditionStr);
    return countByNamedSql(sql.toString(), args);
  }

  /**
   * 集体备课发布数（管理者）
   * 
   * @param userId
   * @param search
   * @return
   */
  @Override
  public Integer getActivityPushCount_leader(SearchVo search) {
    Map<String, Object> args = getArgsforSql(search);
    args.put("status", 1);
    StringBuilder sql = new StringBuilder("select COUNT(a.id) from Activity a ");
    if (search.getRoleId() != null || search.getSubjectId() != null || search.getGradeId() != null) {
      sql.append(" inner join UserSpace u on a.spaceId = u.id ");
      if (search.getRoleId() != null) {
        sql.append(" and u.sysRoleId = :roleId");
      }
      if (search.getSubjectId() != null) {
        sql.append(" and u.subjectId = :subjectId");
      }
      if (search.getGradeId() != null) {
        sql.append(" and u.gradeId = :gradeId");
      }
    }
    sql.append(" where a.organizeUserId = :userId and a.status = :status and a.releaseTime>=:startTime and a.releaseTime<=:endTime");
    return countByNamedSql(sql.toString(), args);
  }

  /**
   * 集体备课参与数（管理者）
   * 
   * @param userId
   * @param search
   * @return
   */
  @Override
  public Integer getActivityJoinCount_leader(SearchVo search) {
    Map<String, Object> args = getArgsforSql(search);
    args.put("editType", 2);
    String conditionStr = "";
    String conditionStr1 = "";
    conditionStr = conditionStr + " INNER JOIN UserSpace u on a.spaceId = u.id and u.sysRoleId <> :teacher ";
    conditionStr1 = conditionStr1 + " INNER JOIN UserSpace u on c.spaceId = u.id and u.sysRoleId <> :teacher ";
    if (search.getRoleId() != null) {
      conditionStr = conditionStr + " and u.sysRoleId = :roleId ";
      conditionStr1 = conditionStr1 + " and u.sysRoleId = :roleId ";
    }
    if (search.getSubjectId() != null || search.getGradeId() != null) {
      conditionStr = conditionStr + " INNER JOIN Activity x on a.activityId = x.id ";
      conditionStr1 = conditionStr1 + " INNER JOIN Activity x on c.activityId = x.id ";
      if (search.getSubjectId() != null) {
        conditionStr = conditionStr + " and x.subjectIds like :lksubjectId ";
        conditionStr1 = conditionStr1 + " and x.subjectIds like :lksubjectId ";
      }
      if (search.getGradeId() != null) {
        conditionStr = conditionStr + " and x.gradeIds like :lkgradeId ";
        conditionStr1 = conditionStr1 + " and x.gradeIds like :lkgradeId ";
      }
    }
    StringBuilder sql = new StringBuilder("select COUNT(DISTINCT m.activity_id) from ");
    sql.append("(select a.activityId from ActivityTracks a "
        + conditionStr
        + " where a.userId = :userId and a.editType <> :editType and a.crtDttm >= :startTime and a.crtDttm <= :endTime ");
    sql.append(" union all select c.activityId from Discuss c " + conditionStr1
        + " where c.crtId = :userId and c.typeId = :typeId and c.crtDttm >= :startTime and c.crtDttm <= :endTime) m");
    args.put("typeId", ResTypeConstants.ACTIVITY);
    return countByNamedSql(sql.toString(), args);
  }

  /**
   * 集体备课讨论数（管理者）
   * 
   * @param userId
   * @param search
   * @return
   */
  @Override
  public Integer getActivityDiscussCount_leader(SearchVo search) {
    Map<String, Object> args = getArgsforSql(search);
    String conditionStr1 = "";
    conditionStr1 = conditionStr1 + " INNER JOIN UserSpace u on c.spaceId = u.id and u.sysRoleId <> :teacher ";
    if (search.getRoleId() != null) {
      conditionStr1 = conditionStr1 + " and u.sysRoleId = :roleId ";
    }
    if (search.getSubjectId() != null || search.getGradeId() != null) {
      conditionStr1 = conditionStr1 + " INNER JOIN Activity x on c.activityId = x.id ";
      if (search.getSubjectId() != null) {
        conditionStr1 = conditionStr1 + " and x.subjectIds like :lksubjectId ";
      }
      if (search.getGradeId() != null) {
        conditionStr1 = conditionStr1 + " and x.gradeIds like :lkgradeId ";
      }
    }
    StringBuilder sql = new StringBuilder("select COUNT(c.id) from Discuss c " + conditionStr1
        + " where c.crtId = :userId and c.typeId = :typeId and c.crtDttm >= :startTime and c.crtDttm <= :endTime");
    args.put("typeId", ResTypeConstants.ACTIVITY);
    return countByNamedSql(sql.toString(), args);
  }

  /**
   * 集体备课查阅数（管理者）
   * 
   * @param userId
   * @param search
   * @return
   */
  @Override
  public Integer getActivityViewCount_leader(SearchVo search) {
    Map<String, Object> args = getArgsforSql(search);
    args.put("resType", ResTypeConstants.ACTIVITY);
    StringBuilder sql = new StringBuilder("select count(distinct a.resId) from CheckInfo a ");
    sql.append(" inner join UserSpace u on a.spaceId = u.id and u.orgId = :orgId ");
    if (search.getRoleId() != null) {
      sql.append(" and u.sysRoleId = :roleId ");
    }
    if (search.getSubjectId() != null || search.getGradeId() != null) {
      sql.append(" INNER JOIN Activity x on a.resId = x.id ");
      if (search.getSubjectId() != null) {
        sql.append(" and x.subjectIds like :lksubjectId ");
      }
      if (search.getGradeId() != null) {
        sql.append(" and x.gradeIds like :lkgradeId ");
      }
    }
    sql.append(" where a.phase = :phaseId and a.resType = :resType and a.userId = :userId and a.createtime >= :startTime and a.createtime <= :endTime");
    return countByNamedSql(sql.toString(), args);
  }

  /**
   * 校际教研发布数（管理者）
   * 
   * @param userId
   * @param search
   * @return
   */
  @Override
  public Integer getSchoolActivityPushCount_leader(SearchVo search) {
    Map<String, Object> args = getArgsforSql(search);
    args.put("status", 1);
    StringBuilder sql = new StringBuilder("select COUNT(a.id) from SchoolActivity a ");
    if (search.getRoleId() != null || search.getSubjectId() != null || search.getGradeId() != null) {
      sql.append(" inner join UserSpace u on a.spaceId = u.id ");
      if (search.getRoleId() != null) {
        sql.append(" and u.sysRoleId = :roleId");
      }
      if (search.getSubjectId() != null) {
        sql.append(" and u.subjectId = :subjectId");
      }
      if (search.getGradeId() != null) {
        sql.append(" and u.gradeId = :gradeId");
      }
    }
    sql.append(" where a.organizeUserId = :userId and a.status = :status and a.releaseTime>=:startTime and a.releaseTime<=:endTime");
    return countByNamedSql(sql.toString(), args);
  }

  /**
   * 校际教研参与数（管理者）
   * 
   * @param userId
   * @param search
   * @return
   */
  @Override
  public Integer getSchoolActivityJoinCount_leader(SearchVo search) {
    Map<String, Object> args = getArgsforSql(search);
    args.put("editType", 2);
    args.put("typeId", ResTypeConstants.SCHOOLTEACH);
    String conditionStr = "";
    String conditionStr1 = "";
    conditionStr = conditionStr + " INNER JOIN UserSpace u on a.spaceId = u.id and u.sysRoleId <> :teacher ";
    conditionStr1 = conditionStr1 + " INNER JOIN UserSpace u on c.spaceId = u.id and u.sysRoleId <> :teacher ";
    if (search.getRoleId() != null) {
      conditionStr = conditionStr + " and u.sysRoleId = :roleId ";
      conditionStr1 = conditionStr1 + " and u.sysRoleId = :roleId ";
    }
    if (search.getSubjectId() != null || search.getGradeId() != null) {
      conditionStr = conditionStr + " INNER JOIN Activity x on a.activityId = x.id ";
      conditionStr1 = conditionStr1 + " INNER JOIN Activity x on c.activityId = x.id ";
      if (search.getSubjectId() != null) {
        conditionStr = conditionStr + " and x.subjectIds like :lksubjectId ";
        conditionStr1 = conditionStr1 + " and x.subjectIds like :lksubjectId ";
      }
      if (search.getGradeId() != null) {
        conditionStr = conditionStr + " and x.gradeIds like :lkgradeId ";
        conditionStr1 = conditionStr1 + " and x.gradeIds like :lkgradeId ";
      }
    }
    StringBuilder sql = new StringBuilder("select COUNT(DISTINCT m.activity_id) from");
    sql.append("(select a.activityId from SchoolActivityTracks a "
        + conditionStr
        + " where a.userId = :userId and a.editType <> :editType and a.crtDttm >= :startTime and a.crtDttm <= :endTime ");
    sql.append(" union all select c.activityId from Discuss c " + conditionStr1
        + " where c.typeId = :typeId and c.crtId = :userId and c.crtDttm >= :startTime and c.crtDttm <= :endTime) m");
    return countByNamedSql(sql.toString(), args);
  }

  /**
   * 校际教研讨论数（管理者）
   * 
   * @param userId
   * @param search
   * @return
   */
  @Override
  public Integer getSchoolActivityDiscussCount_leader(SearchVo search) {
    Map<String, Object> args = getArgsforSql(search);
    args.put("typeId", ResTypeConstants.SCHOOLTEACH);
    String conditionStr1 = "";
    conditionStr1 = conditionStr1 + " INNER JOIN UserSpace u on c.spaceId = u.id and u.sysRoleId <> :teacher ";
    if (search.getRoleId() != null) {
      conditionStr1 = conditionStr1 + " and u.sysRoleId = :roleId ";
    }
    if (search.getSubjectId() != null || search.getGradeId() != null) {
      conditionStr1 = conditionStr1 + " INNER JOIN Activity x on c.activityId = x.id ";
      if (search.getSubjectId() != null) {
        conditionStr1 = conditionStr1 + " and x.subjectIds like :lksubjectId ";
      }
      if (search.getGradeId() != null) {
        conditionStr1 = conditionStr1 + " and x.gradeIds like :lkgradeId ";
      }
    }
    StringBuilder sql = new StringBuilder("select COUNT(c.id) from Discuss c " + conditionStr1
        + " where c.typeId = :typeId and c.crtId = :userId and c.crtDttm >= :startTime and c.crtDttm <= :endTime");
    return countByNamedSql(sql.toString(), args);
  }

  /**
   * 同伴互助留言数（管理者）
   * 
   * @param userId
   * @param search
   * @return
   */
  @Override
  public Integer getPeerMessageCount_leader(SearchVo search) {
    Map<String, Object> args = getArgsforSql(search);
    StringBuilder sql = new StringBuilder(
        "select count(id) from JyCompanionMessage where userIdSender = :userId and senderTime >= :startTime and senderTime <= :endTime");
    return countByNamedSql(sql.toString(), args);
  }

  /**
   * 分享发布篇数（管理者）
   * 
   * @param userId
   * @param search
   * @return
   */
  @Override
  public Integer getShareCount_leader(SearchVo search) {
    Map<String, Object> args = getArgsforSql(search);
    args.put("isShare", 1);
    String conditionStr = "";
    String conditionStr1 = " inner join UserSpace u on a.spaceId = u.id and u.sysRoleId <> :teacher ";
    if (search.getRoleId() != null) {
      conditionStr1 = conditionStr1 + " and u.sysRoleId = :roleId ";
    }
    if (search.getSubjectId() != null) {
      conditionStr = conditionStr + " and subjectId = :subjectId";
      conditionStr1 = conditionStr1 + " and u.subjectId = :subjectId ";
    }
    if (search.getGradeId() != null) {
      conditionStr = conditionStr + " and gradeId = :gradeId";
      conditionStr1 = conditionStr1 + " and u.gradeId = :gradeId ";
    }
    StringBuilder sql1 = new StringBuilder(
        "select count(id) from LectureRecords where isShare = :isShare and shareTime >= :startTime and shareTime <= :endTime and lecturepeopleId = :userId");
    StringBuilder sql2 = new StringBuilder(
        "select count(id) from PlainSummary where roleId = :roleId and isShare = :isShare and shareTime >= :startTime and shareTime <= :endTime and userId = :userId"
            + conditionStr);
    StringBuilder sql3 = new StringBuilder(
        "select count(id) from Thesis where isShare = :isShare and shareTime >= :startTime and shareTime <= :endTime and userId = :userId and enable = :enable");
    StringBuilder sql4 = new StringBuilder(
        "select count(a.id) from Activity a "
            + conditionStr1
            + " where a.isShare = :isShare and a.shareTime >= :startTime and a.shareTime <= :endTime and a.organizeUserId = :userId");
    StringBuilder sql5 = new StringBuilder(
        "select count(a.id) from SchoolActivity a "
            + conditionStr1
            + " where a.isShare = :isShare and a.shareTime >= :startTime and a.shareTime <= :endTime and a.organizeUserId = :userId");
    return countByNamedSql(sql1.toString(), args) + countByNamedSql(sql2.toString(), args)
        + countByNamedSql(sql3.toString(), args) + countByNamedSql(sql4.toString(), args)
        + countByNamedSql(sql5.toString(), args);
  }

  /**
   * 区域下用户总数
   * 
   * @param search
   * @return
   * @see com.tmser.tr.back.dao.OperationManageDao#getUserTotalCountOfArea(com.tmser.tr.back.vo.SearchVo)
   */
  @Override
  public Integer getUserTotalCountOfArea(SearchVo search) {
    Map<String, Object> args = getArgsforSql(search);
    StringBuilder sql = new StringBuilder(
        "select count(distinct a.userId) from UserSpace a inner join Organization o on a.orgId = o.id and o.areaId = :areaId where a.phaseId = :phaseId and a.enable = :enable ");
    if (search.getSubjectId() != null) {
      sql.append(" and a.subjectId = :subjectId");
    }
    if (search.getGradeId() != null) {
      sql.append(" and a.gradeId = :gradeId");
    }
    return countByNamedSql(sql.toString(), args);
  }

  /**
   * 区域下撰写教案的总数
   * 
   * @param search
   * @return
   * @see com.tmser.tr.back.dao.OperationManageDao#getLessonPlanTotalCount(com.tmser.tr.back.vo.SearchVo)
   */
  @Override
  public Integer getLessonPlanTotalCountOfArea(SearchVo search) {
    Map<String, Object> args = getArgsforSql(search);
    args.put("planType", 0);
    StringBuilder sql = new StringBuilder(
        "select count(a.planId) from LessonPlan a inner join Organization o on a.orgId = o.id and o.areaId = :areaId where a.enable = :enable and a.planType = :planType and a.phaseId = :phaseId and a.crtDttm >= :startTime and a.crtDttm <= :endTime");
    if (search.getSubjectId() != null) {
      sql.append(" and a.subjectId = :subjectId");
    }
    if (search.getGradeId() != null) {
      sql.append(" and a.gradeId = :gradeId");
    }
    return countByNamedSql(sql.toString(), args);
  }

  /**
   * 区域下的查阅总数
   * 
   * @param search
   * @return
   * @see com.tmser.tr.back.dao.OperationManageDao#getViewTotalCountOfArea(com.tmser.tr.back.vo.SearchVo)
   */
  @Override
  public Integer getViewTotalCountOfArea(SearchVo search) {
    Map<String, Object> args = getArgsforSql(search);
    args.put("type0", 0);
    args.put("type1", 1);
    args.put("type2", 2);
    args.put("type3", 3);
    args.put("type5", 5);
    args.put("type8", 8);
    args.put("type9", 9);
    StringBuilder sql = new StringBuilder(
        "select count(distinct a.resId) from CheckInfo a INNER JOIN UserSpace u on a.spaceId = u.id inner join Organization o on u.orgId = o.id and o.areaId = :areaId where a.phase = :phaseId and a.resType in (:type0,:type1,:type2,:type3,:type5,:type8,:type9) and a.createtime >= :startTime and a.createtime <= :endTime");
    if (search.getSubjectId() != null) {
      sql.append(" and a.subjectId = :subjectId");
    }
    if (search.getGradeId() != null) {
      sql.append(" and a.gradeId = :gradeId");
    }
    return countByNamedSql(sql.toString(), args);
  }

  /**
   * 区域下分享发表总数
   * 
   * @param search
   * @return
   * @see com.tmser.tr.back.dao.OperationManageDao#getShareTotalCountOfArea(com.tmser.tr.back.vo.SearchVo)
   */
  @Override
  public Integer getShareTotalCountOfArea(SearchVo search) {
    Map<String, Object> args = getArgsforSql(search);
    String conditionStr = "";
    if (search.getSubjectId() != null) {
      conditionStr = conditionStr + " and a.subjectId = :subjectId";
    }
    if (search.getGradeId() != null) {
      conditionStr = conditionStr + " and a.gradeId = :gradeId";
    }
    StringBuilder sql1 = new StringBuilder(
        "select count(a.planId) from LessonPlan a inner join Organization o on a.orgId = o.id and o.areaId = :areaId where a.phaseId = :phaseId and a.isShare = :isShare and a.enable = :enable and a.shareTime >= :startTime and a.shareTime <= :endTime "
            + conditionStr);
    StringBuilder sql2 = new StringBuilder(
        "select count(a.id) from LectureRecords a inner join Organization o on a.orgId = o.id and o.areaId = :areaId where a.phaseId = :phaseId and a.isShare = :isShare and a.isDelete = :isDelete and a.shareTime >= :startTime and a.shareTime <= :endTime and a.isEpub = :isEpub ");
    StringBuilder sql3 = new StringBuilder(
        "select (case when sum(a.resCount) is null then 0 else sum(a.resCount) end) from Recordbag a inner join Organization o on a.orgId = o.id and o.areaId = :areaId inner join UserSpace u on u.userId=a.teacherId and a.gradeId = u.gradeId and a.subjectId = u.subjectId and u.phaseId = :phaseId and u.sysRoleId = :sysRoleId where a.share=:isShare and a.shareTime >= :startTime and a.shareTime <= :endTime"
            + conditionStr);
    StringBuilder sql4 = new StringBuilder(
        "select count(a.id) from PlainSummary a inner join Organization o on a.orgId = o.id and o.areaId = :areaId where a.isShare = :isShare and a.shareTime >= :startTime and a.shareTime <= :endTime and a.phaseId = :phaseId "
            + conditionStr);
    StringBuilder sql5 = new StringBuilder(
        "select count(a.id) from Thesis a inner join Organization o on a.orgId = o.id and o.areaId = :areaId where a.isShare = :isShare and a.shareTime >= :startTime and a.shareTime <= :endTime and a.phaseId = :phaseId and a.enable = :enable ");
    StringBuilder sql6 = new StringBuilder(
        "select count(a.id) from Activity a inner join Organization o on a.orgId = o.id and o.areaId = :areaId where a.isShare = :isShare and a.phaseId = :phaseId and a.shareTime >= :startTime and a.shareTime <= :endTime");
    if (search.getSubjectId() != null) {
      sql6.append(" and a.subjectIds like :lksubjectId");
    }
    if (search.getGradeId() != null) {
      sql6.append(" and a.gradeIds like :lkgradeId");
    }
    StringBuilder sql7 = new StringBuilder(
        "select count(a.id) from SchoolActivity a inner join Organization o on a.orgId = o.id and o.areaId = :areaId where a.isShare = :isShare and a.phaseId = :phaseId and a.shareTime >= :startTime and a.shareTime <= :endTime");
    if (search.getSubjectId() != null) {
      sql7.append(" and a.subjectIds like :lksubjectId");
    }
    if (search.getGradeId() != null) {
      sql7.append(" and a.gradeIds like :lkgradeId");
    }
    args.put("sysRoleId", SysRole.TEACHER.getId());
    return countByNamedSql(sql1.toString(), args) + countByNamedSql(sql2.toString(), args)
        + countByNamedSql(sql3.toString(), args) + countByNamedSql(sql4.toString(), args)
        + countByNamedSql(sql5.toString(), args) + countByNamedSql(sql6.toString(), args)
        + countByNamedSql(sql7.toString(), args);
  }

  /**
   * 区域下集体备课的发表总数
   * 
   * @param search
   * @return
   * @see com.tmser.tr.back.dao.OperationManageDao#getActivityPushTotalCountOfArea(com.tmser.tr.back.vo.SearchVo)
   */
  @Override
  public Integer getActivityPushTotalCountOfArea(SearchVo search) {
    Map<String, Object> args = getArgsforSql(search);
    args.put("status", 1);
    StringBuilder sql = new StringBuilder(
        "select count(a.id) from Activity a inner join Organization o on a.orgId = o.id and o.areaId = :areaId where a.phaseId = :phaseId and a.status = :status and a.releaseTime >= :startTime and a.releaseTime <= :endTime");
    if (search.getSubjectId() != null) {
      sql.append(" and a.subjectIds like :lksubjectId");
    }
    if (search.getGradeId() != null) {
      sql.append(" and a.gradeIds like :lkgradeId");
    }
    return countByNamedSql(sql.toString(), args);
  }

  /**
   * 区域下集体备课的参与总数
   * 
   * @param search
   * @return
   * @see com.tmser.tr.back.dao.OperationManageDao#getActivityJoinTotalCountOfArea(com.tmser.tr.back.vo.SearchVo)
   */
  @Override
  public Integer getActivityJoinTotalCountOfArea(SearchVo search) {
    Map<String, Object> args = getArgsforSql(search);
    args.put("editType", 2);
    String conditionStr = "";
    String conditionStr1 = "";
    conditionStr1 = conditionStr1 + " INNER JOIN Activity b on a.activityId = b.id and b.phaseId = :phaseId ";
    if (search.getSubjectId() != null) {
      conditionStr = conditionStr + " and b.subjectIds like :lksubjectId";
      conditionStr1 = conditionStr1 + " and b.subjectIds like :lksubjectId";
    }
    if (search.getGradeId() != null) {
      conditionStr = conditionStr + " and b.gradeIds like :lkgradeId";
      conditionStr1 = conditionStr1 + " and b.gradeIds like :lkgradeId";
    }
    StringBuilder sql = new StringBuilder("select COUNT(DISTINCT m.activity_id,m.user_id) from");
    sql.append("(select a.activityId,a.userId from ActivityTracks a inner join Organization o on a.orgId = o.id and o.areaId = :areaId "
        + conditionStr1 + " where a.editType <> :editType and a.crtDttm >= :startTime and a.crtDttm <= :endTime");
    sql.append(" union all select c.activityId,c.crtId from Discuss c INNER JOIN Activity b on c.activityId = b.id and c.typeId = :typeId and b.phaseId = :phaseId "
        + conditionStr
        + " inner join Organization o on b.orgId = o.id and o.areaId = :areaId where c.crtDttm >= :startTime and c.crtDttm <= :endTime) m");
    args.put("typeId", ResTypeConstants.ACTIVITY);
    return countByNamedSql(sql.toString(), args);
  }

  /**
   * 区域下成长档案资源总数
   * 
   * @param search
   * @return
   * @see com.tmser.tr.back.dao.OperationManageDao#getProgressResTotalCountOfArea(com.tmser.tr.back.vo.SearchVo)
   */
  @Override
  public Integer getProgressResTotalCountOfArea(SearchVo search) {
    Map<String, Object> args = getArgsforSql(search);
    StringBuilder sql = new StringBuilder(
        "select sum(a.resCount) from Recordbag a inner join Organization o on a.orgId = o.id and o.areaId = :areaId inner join UserSpace u on u.userId=a.teacherId and a.gradeId = u.gradeId and a.subjectId = u.subjectId and u.sysRoleId = :sysRoleId and u.phaseId = :phaseId where a.del = :isDelete and a.createTime >=:startTime and a.createTime <= :endTime ");
    if (search.getSubjectId() != null) {
      sql.append(" and a.subjectId = :subjectId");
    }
    if (search.getGradeId() != null) {
      sql.append(" and a.gradeId = :gradeId");
    }
    args.put("sysRoleId", SysRole.TEACHER.getId());
    return countByNamedSql(sql.toString(), args);
  }

  /**
   * 区域下同伴互助留言总数
   * 
   * @param search
   * @return
   * @see com.tmser.tr.back.dao.OperationManageDao#getPeerMessageTotalCountOfArea(com.tmser.tr.back.vo.SearchVo)
   */
  @Override
  public Integer getPeerMessageTotalCountOfArea(SearchVo search) {
    Map<String, Object> args = getArgsforSql(search);
    StringBuilder sql = new StringBuilder(
        "select count(a.id) from JyCompanionMessage a where a.userIdSender in (select DISTINCT(b.userId) from UserSpace b inner join Organization o on b.orgId = o.id and o.areaId = :areaId where b.phaseId = :phaseId ) and a.senderTime >= :startTime and a.senderTime <= :endTime");
    return countByNamedSql(sql.toString(), args);
  }

  /**
   * 区域下资源总数
   * 
   * @param search
   * @return
   * @see com.tmser.tr.back.dao.OperationManageDao#getResTotalCountOfArea(com.tmser.tr.back.vo.SearchVo)
   */
  @Override
  public Integer getResTotalCountOfArea(SearchVo search) {
    Map<String, Object> args = getArgsforSql(search);
    args.put("status", 1);
    args.put("typeId", ResTypeConstants.SCHOOLTEACH);
    String conditionStr = "";
    String conditionStr1 = "";
    String conditionStr2 = "";
    if (search.getSubjectId() != null) {
      conditionStr = conditionStr + " and a.subjectId = :subjectId";
      conditionStr1 = conditionStr1 + " and a.subjectIds like :lksubjectId";
      conditionStr2 = conditionStr2 + " and b.subjectIds like :lksubjectId";
    }
    if (search.getGradeId() != null) {
      conditionStr = conditionStr + " and a.gradeId = :gradeId";
      conditionStr1 = conditionStr1 + " and a.gradeIds like :lkgradeId";
      conditionStr2 = conditionStr2 + " and b.gradeIds like :lkgradeId";
    }
    StringBuilder sql1 = new StringBuilder(
        "select count(a.planId) from LessonPlan a inner join Organization o on a.orgId = o.id and o.areaId = :areaId where a.phaseId = :phaseId and a.crtDttm >= :startTime and a.crtDttm <= :endTime and a.enable = :enable "
            + conditionStr);
    StringBuilder sql2 = new StringBuilder(
        "select count(a.id) from Thesis a inner join Organization o on a.orgId = o.id and o.areaId = :areaId where a.crtDttm >= :startTime and a.crtDttm <= :endTime and a.phaseId = :phaseId and a.enable = :enable");
    StringBuilder sql3 = new StringBuilder(
        "select count(a.id) from LectureRecords a inner join Organization o on a.orgId = o.id and o.areaId = :areaId where a.crtDttm >= :startTime and a.crtDttm <= :endTime and a.phaseId = :phaseId and a.isDelete = :isDelete and a.isEpub = :isEpub ");
    StringBuilder sql5 = new StringBuilder(
        "select count(a.id) from PlainSummary a inner join Organization o on a.orgId = o.id and o.areaId = :areaId where a.phaseId = :phaseId and a.crtDttm >= :startTime and a.crtDttm <= :endTime "
            + conditionStr);
    StringBuilder sql8 = new StringBuilder("select count(DISTINCT m.activity_id) from ");
    sql8.append("(select a.activityId from SchoolActivityTracks a inner join Organization o on a.orgId = o.id and o.areaId = :areaId INNER JOIN SchoolActivity b on a.activityId = b.id and b.phaseId = :phaseId "
        + conditionStr2 + " where a.crtDttm >=:startTime and a.crtDttm <=:endTime ");
    sql8.append("union ALL select c.activityId from Discuss c inner join UserSpace u on c.spaceId = u.id inner join Organization o on u.orgId = o.id and o.areaId = :areaId INNER JOIN SchoolActivity b on c.activityId = b.id and b.phaseId = :phaseId "
        + conditionStr2 + " where c.typeId = :typeId and c.crtDttm >=:startTime and c.crtDttm <=:endTime ");
    sql8.append("union all select b.id as activity_id from SchoolActivity b inner join Organization o on b.orgId = o.id and o.areaId = :areaId where b.phaseId = :phaseId and b.status = :status and b.createTime >= :startTime and b.createTime <= :endTime "
        + conditionStr2 + " ) m");

    return countByNamedSql(sql1.toString(), args) + countByNamedSql(sql2.toString(), args)
        + countByNamedSql(sql3.toString(), args) + getProgressResTotalCountOfArea(search)
        + countByNamedSql(sql5.toString(), args) + getActivityPushTotalCountOfArea(search)
        + countByNamedSql(sql8.toString(), args);
  }

  /**
   * 学校下老师撰写教案总数
   * 
   * @param search
   * @return
   * @see com.tmser.tr.back.dao.OperationManageDao#getLessonPlanTotalCount_teacher(com.tmser.tr.back.vo.SearchVo)
   */
  @Override
  public Integer getLessonPlanTotalCount_teacher(SearchVo search) {
    Map<String, Object> args = getArgsforSql(search);
    args.put("planType", 0);
    String conditionStr = "";
    if (search.getSubjectId() != null) {
      conditionStr = conditionStr + " and a.subjectId = :subjectId";
    }
    if (search.getGradeId() != null) {
      conditionStr = conditionStr + " and a.gradeId = :gradeId";
    }
    StringBuilder sql = new StringBuilder(
        "select count(distinct a.planId) from LessonPlan a where a.enable = :enable and a.phaseId = :phaseId and a.orgId = :orgId and a.planType = :planType and a.crtDttm >= :startTime and a.crtDttm <= :endTime "
            + conditionStr);
    return countByNamedSql(sql.toString(), args);
  }

  /**
   * 学校下老师上传课件的总数
   * 
   * @param search
   * @return
   * @see com.tmser.tr.back.dao.OperationManageDao#getKejianTotalCount_teacher(com.tmser.tr.back.vo.SearchVo)
   */
  @Override
  public Integer getKejianTotalCount_teacher(SearchVo search) {
    Map<String, Object> args = getArgsforSql(search);
    args.put("planType", 1);
    String conditionStr = "";
    if (search.getSubjectId() != null) {
      conditionStr = conditionStr + " and a.subjectId = :subjectId";
    }
    if (search.getGradeId() != null) {
      conditionStr = conditionStr + " and a.gradeId = :gradeId";
    }
    StringBuilder sql = new StringBuilder(
        "select count(distinct a.planId) from LessonPlan a where a.enable = :enable and a.phaseId = :phaseId and a.orgId = :orgId and a.planType = :planType and a.crtDttm >= :startTime and a.crtDttm <= :endTime "
            + conditionStr);
    return countByNamedSql(sql.toString(), args);
  }

  /**
   * 学校下老师的反思总数
   * 
   * @param search
   * @return
   * @see com.tmser.tr.back.dao.OperationManageDao#getFansiTotalCount_teacher(com.tmser.tr.back.vo.SearchVo)
   */
  @Override
  public Integer getFansiTotalCount_teacher(SearchVo search) {
    Map<String, Object> args = getArgsforSql(search);
    args.put("planType1", 2);
    args.put("planType2", 3);
    String conditionStr = "";
    if (search.getSubjectId() != null) {
      conditionStr = conditionStr + " and a.subjectId = :subjectId";
    }
    if (search.getGradeId() != null) {
      conditionStr = conditionStr + " and a.gradeId = :gradeId";
    }
    StringBuilder sql = new StringBuilder(
        "select count(distinct a.planId) from LessonPlan a where a.enable = :enable and a.phaseId = :phaseId and a.orgId = :orgId and (a.planType = :planType1 or a.planType = :planType2) and a.crtDttm >= :startTime and a.crtDttm <= :endTime "
            + conditionStr);
    return countByNamedSql(sql.toString(), args);
  }

  /**
   * 学校下老师的听课记录总数
   * 
   * @param search
   * @return
   * @see com.tmser.tr.back.dao.OperationManageDao#getListenTotalCount_teacher(com.tmser.tr.back.vo.SearchVo)
   */
  @Override
  public Integer getListenTotalCount_teacher(SearchVo search) {
    Map<String, Object> args = getArgsforSql(search);
    String conditionStr = "";
    if (search.getSubjectId() != null) {
      conditionStr = conditionStr + " and u.subjectId = :subjectId";
    }
    if (search.getGradeId() != null) {
      conditionStr = conditionStr + " and u.gradeId = :gradeId";
    }
    StringBuilder sql = new StringBuilder(
        "select count(a.id) from LectureRecords a where a.lecturepeopleId in (select distinct u.userId from UserSpace u where u.orgId = :orgId and u.sysRoleId = :teacher and u.phaseId = :phaseId "
            + conditionStr
            + ") and a.phaseId = :phaseId and a.orgId = :orgId and a.isDelete = :isDelete and a.isEpub = :isEpub and a.crtDttm >= :startTime and a.crtDttm <= :endTime ");
    return countByNamedSql(sql.toString(), args);
  }

  /**
   * 学校下老师的教学文章总数
   * 
   * @param search
   * @return
   * @see com.tmser.tr.back.dao.OperationManageDao#getTeachTextTotalCount_teacher(com.tmser.tr.back.vo.SearchVo)
   */
  @Override
  public Integer getTeachTextTotalCount_teacher(SearchVo search) {
    Map<String, Object> args = getArgsforSql(search);
    String conditionStr = "";
    if (search.getSubjectId() != null) {
      conditionStr = conditionStr + " and u.subjectId = :subjectId";
    }
    if (search.getGradeId() != null) {
      conditionStr = conditionStr + " and u.gradeId = :gradeId";
    }
    StringBuilder sql = new StringBuilder(
        "select count(a.id) from Thesis a where a.userId in (select distinct u.userId from UserSpace u where u.orgId = :orgId and u.sysRoleId = :teacher and u.phaseId = :phaseId "
            + conditionStr
            + ") and a.orgId = :orgId and a.phaseId = :phaseId and a.enable = :enable and a.crtDttm >= :startTime and a.crtDttm <= :endTime ");
    return countByNamedSql(sql.toString(), args);
  }

  /**
   * 学校下老师的计划总结总数
   * 
   * @param search
   * @return
   * @see com.tmser.tr.back.dao.OperationManageDao#getPlanSummaryTotalCount_teacher(com.tmser.tr.back.vo.SearchVo)
   */
  @Override
  public Integer getPlanSummaryTotalCount_teacher(SearchVo search) {
    Map<String, Object> args = getArgsforSql(search);
    String conditionStr = "";
    if (search.getSubjectId() != null) {
      conditionStr = conditionStr + " and subjectId = :subjectId";
    }
    if (search.getGradeId() != null) {
      conditionStr = conditionStr + " and gradeId = :gradeId";
    }
    StringBuilder sql = new StringBuilder(
        "select count(id) from PlainSummary where orgId = :orgId and phaseId = :phaseId and roleId = :teacher and crtDttm >= :startTime and crtDttm <= :endTime "
            + conditionStr);
    return countByNamedSql(sql.toString(), args);
  }

  /**
   * 学校下老师参与集体备课总数
   * 
   * @param search
   * @return
   * @see com.tmser.tr.back.dao.OperationManageDao#getActivityJoinTotalCount_teacher(com.tmser.tr.back.vo.SearchVo)
   */
  @Override
  public Integer getActivityJoinTotalCount_teacher(SearchVo search) {
    Map<String, Object> args = getArgsforSql(search);
    args.put("editType", 2);
    String conditionStr = "";
    String conditionStr1 = "";
    conditionStr1 = conditionStr1
        + " INNER JOIN UserSpace u on a.spaceId = u.id and u.sysRoleId = :teacher and u.phaseId = :phaseId ";
    conditionStr = conditionStr
        + " INNER JOIN UserSpace u on c.spaceId = u.id and u.sysRoleId = :teacher and u.phaseId = :phaseId and u.orgId = :orgId ";
    if (search.getSubjectId() != null) {
      conditionStr = conditionStr + " and u.subjectId = :subjectId ";
      conditionStr1 = conditionStr1 + " and u.subjectId = :subjectId ";
    }
    if (search.getGradeId() != null) {
      conditionStr = conditionStr + " and u.gradeId = :gradeId ";
      conditionStr1 = conditionStr1 + " and u.gradeId = :gradeId ";
    }
    StringBuilder sql = new StringBuilder("select COUNT(DISTINCT m.activity_id,m.user_id) from");
    sql.append("(select a.activityId as activity_id,a.crtId as user_id from ActivityTracks a " + conditionStr1
        + " where a.editType <> :editType and a.orgId = :orgId and a.crtDttm >= :startTime and a.crtDttm <= :endTime ");
    sql.append(" union all select c.activityId as activity_id,c.crtId as user_id from Discuss c " + conditionStr
        + " where c.typeId =: typeId and c.crtDttm >= :startTime and c.crtDttm <= :endTime) m");
    args.put("typeId", ResTypeConstants.ACTIVITY);
    return countByNamedSql(sql.toString(), args);
  }

  /**
   * 学校下老师集体备课的讨论数
   * 
   * @param search
   * @return
   * @see com.tmser.tr.back.dao.OperationManageDao#getActivityDiscussTotalCount_teacher(com.tmser.tr.back.vo.SearchVo)
   */
  @Override
  public Integer getActivityDiscussTotalCount_teacher(SearchVo search) {
    Map<String, Object> args = getArgsforSql(search);
    String conditionStr1 = "";
    conditionStr1 = conditionStr1
        + " INNER JOIN UserSpace u on c.spaceId = u.id and u.orgId = :orgId and u.sysRoleId = :teacher and u.phaseId = :phaseId ";
    if (search.getSubjectId() != null) {
      conditionStr1 = conditionStr1 + " and u.subjectId = :subjectId ";
    }
    if (search.getGradeId() != null) {
      conditionStr1 = conditionStr1 + " and u.gradeId = :gradeId ";
    }
    StringBuilder sql = new StringBuilder("select COUNT(c.id) from Discuss c " + conditionStr1
        + " where c.typeId = :typeId and c.crtDttm >= :startTime and c.crtDttm <= :endTime");
    args.put("typeId", ResTypeConstants.ACTIVITY);
    return countByNamedSql(sql.toString(), args);
  }

  /**
   * 学校下教师任主备人的总数
   * 
   * @param search
   * @return
   * @see com.tmser.tr.back.dao.OperationManageDao#getActivityMainTotalCount_teacher(com.tmser.tr.back.vo.SearchVo)
   */
  @Override
  public Integer getActivityMainTotalCount_teacher(SearchVo search) {
    Map<String, Object> args = getArgsforSql(search);
    args.put("status", 1);
    args.put("typeId", 1);
    StringBuilder sql = new StringBuilder(
        "select count(id) from Activity where typeId = :typeId and status = :status and orgId = :orgId and phaseId = :phaseId and releaseTime >= :startTime and releaseTime <= :endTime ");
    if (search.getSubjectId() != null) {
      sql.append(" and mainUserSubjectId = :subjectId");
    }
    if (search.getGradeId() != null) {
      sql.append(" and mainUserGradeId = :gradeId ");
    }
    return countByNamedSql(sql.toString(), args);
  }

  /**
   * 学校下老师校际教研的参与数
   * 
   * @param search
   * @return
   * @see com.tmser.tr.back.dao.OperationManageDao#getSchoolActivityJoinTotalCount_teacher(com.tmser.tr.back.vo.SearchVo)
   */
  @Override
  public Integer getSchoolActivityJoinTotalCount_teacher(SearchVo search) {
    Map<String, Object> args = getArgsforSql(search);
    args.put("editType", 2);
    args.put("typeId", ResTypeConstants.SCHOOLTEACH);
    String conditionStr = "";
    String conditionStr1 = "";
    conditionStr1 = conditionStr1
        + " INNER JOIN UserSpace u on a.spaceId = u.id and u.sysRoleId = :teacher and u.phaseId = :phaseId ";
    conditionStr = conditionStr
        + " INNER JOIN UserSpace u on c.spaceId = u.id and u.sysRoleId = :teacher and u.phaseId = :phaseId and u.orgId = :orgId ";
    if (search.getSubjectId() != null) {
      conditionStr = conditionStr + " and u.subjectId = :subjectId ";
      conditionStr1 = conditionStr1 + " and u.subjectId = :subjectId ";
    }
    if (search.getGradeId() != null) {
      conditionStr = conditionStr + " and u.gradeId = :gradeId ";
      conditionStr1 = conditionStr1 + " and u.gradeId = :gradeId ";
    }
    StringBuilder sql = new StringBuilder("select COUNT(DISTINCT m.activity_id,m.user_id) from");
    sql.append("(select a.activityId as activity_id,a.crtId as user_id from SchoolActivityTracks a " + conditionStr1
        + " where a.editType <> :editType and a.orgId = :orgId and a.crtDttm >= :startTime and a.crtDttm <= :endTime ");
    sql.append(" union all select c.activityId as activity_id,c.crtId as user_id from Discuss c " + conditionStr
        + " where c.typeId = :typeId and c.crtDttm >= :startTime and c.crtDttm <= :endTime) m");
    return countByNamedSql(sql.toString(), args);
  }

  /**
   * 学校下老师校际教研的讨论数
   * 
   * @param search
   * @return
   * @see com.tmser.tr.back.dao.OperationManageDao#getSchoolActivityDiscussTotalCount_teacher(com.tmser.tr.back.vo.SearchVo)
   */
  @Override
  public Integer getSchoolActivityDiscussTotalCount_teacher(SearchVo search) {
    Map<String, Object> args = getArgsforSql(search);
    args.put("typeId", ResTypeConstants.SCHOOLTEACH);
    String conditionStr1 = "";
    conditionStr1 = conditionStr1
        + " INNER JOIN UserSpace u on c.spaceId = u.id and u.orgId = :orgId and u.sysRoleId = :teacher and u.phaseId = :phaseId ";
    if (search.getSubjectId() != null) {
      conditionStr1 = conditionStr1 + " and u.subjectId = :subjectId ";
    }
    if (search.getGradeId() != null) {
      conditionStr1 = conditionStr1 + " and u.gradeId = :gradeId ";
    }
    StringBuilder sql = new StringBuilder("select COUNT(c.id) from Discuss c " + conditionStr1
        + " where c.typeId = :typeId and c.crtDttm >= :startTime and c.crtDttm <= :endTime");
    return countByNamedSql(sql.toString(), args);
  }

  /**
   * 学校下教师校际教研任主备人的总次数
   * 
   * @param search
   * @return
   * @see com.tmser.tr.back.dao.OperationManageDao#getSchoolActivityMainTotalCount_teacher(com.tmser.tr.back.vo.SearchVo)
   */
  @Override
  public Integer getSchoolActivityMainTotalCount_teacher(SearchVo search) {
    Map<String, Object> args = getArgsforSql(search);
    args.put("status", 1);
    args.put("typeId", 1);
    StringBuilder sql = new StringBuilder(
        "select count(a.id) from SchoolActivity a where a.typeId = :typeId and a.mainUserOrgId = :orgId and a.phaseId = :phaseId and a.status = :status and a.releaseTime >= :startTime and a.releaseTime <= :endTime");
    if (search.getSubjectId() != null) {
      sql.append(" and a.mainUserSubjectId = :subjectId ");
    }
    if (search.getGradeId() != null) {
      sql.append(" and a.mainUserGradeId = :gradeId ");
    }
    return countByNamedSql(sql.toString(), args);
  }

  /**
   * 学校下老师的同伴互助留言总数
   * 
   * @param search
   * @return
   * @see com.tmser.tr.back.dao.OperationManageDao#getPeerMessageTotalCount_teacher(com.tmser.tr.back.vo.SearchVo)
   */
  @Override
  public Integer getPeerMessageTotalCount_teacher(SearchVo search) {
    Map<String, Object> args = getArgsforSql(search);
    StringBuilder sql = new StringBuilder(
        "select count(a.id) from JyCompanionMessage a where a.senderTime >= :startTime and a.senderTime <= :endTime and a.userIdSender in (select distinct u.userId from UserSpace u where u.orgId = :orgId and u.phaseId = :phaseId and u.sysRoleId = :teacher )");
    return countByNamedSql(sql.toString(), args);
  }

  /**
   * 学校下老师成长档案袋资源总数
   * 
   * @param search
   * @return
   * @see com.tmser.tr.back.dao.OperationManageDao#getProgressResTotalCount_teacher(com.tmser.tr.back.vo.SearchVo)
   */
  @Override
  public Integer getProgressResTotalCount_teacher(SearchVo search) {
    Map<String, Object> args = getArgsforSql(search);
    args.put("status", 1);
    StringBuilder sql = new StringBuilder(
        "select count(a.recordId) from Record a INNER JOIN Recordbag b on a.bagId = b.id and b.orgId = :orgId ");
    if (search.getSubjectId() != null) {
      sql.append(" and b.subjectId = :subjectId");
    }
    if (search.getGradeId() != null) {
      sql.append(" and b.gradeId = :gradeId");
    }
    sql.append(" inner join UserSpace u on b.teacherId = u.userId and b.gradeId = u.gradeId and b.subjectId = u.subjectId and u.sysRoleId = :sysRoleId and u.phaseId = :phaseId where a.createTime >=:startTime and a.createTime <= :endTime and a.status = :status");
    args.put("sysRoleId", SysRole.TEACHER.getId());
    return countByNamedSql(sql.toString(), args);
  }

  /**
   * 学校下老师的分享总数
   * 
   * @param search
   * @return
   * @see com.tmser.tr.back.dao.OperationManageDao#getShareTotalCount_teacher(com.tmser.tr.back.vo.SearchVo)
   */
  @Override
  public Integer getShareTotalCount_teacher(SearchVo search) {
    Map<String, Object> args = getArgsforSql(search);
    args.put("isShare", 1);
    String conditionStr = "";
    if (search.getSubjectId() != null) {
      conditionStr = conditionStr + " and a.subjectId = :subjectId";
    }
    if (search.getGradeId() != null) {
      conditionStr = conditionStr + " and a.gradeId = :gradeId";
    }
    StringBuilder sql1 = new StringBuilder(
        "select count(a.planId) from LessonPlan a where a.isShare = :isShare and a.orgId = :orgId and a.phaseId = :phaseId and a.shareTime >= :startTime and a.shareTime <= :endTime"
            + conditionStr);
    StringBuilder sql2 = new StringBuilder(
        "select count(b.id) from LectureRecords b where b.lecturepeopleId in (select distinct a.userId from UserSpace a where a.orgId = :orgId and a.sysRoleId = :teacher and a.phaseId = :phaseId "
            + conditionStr
            + ") and b.isShare = :isShare and b.orgId = :orgId and b.phaseId = :phaseId and b.shareTime >= :startTime and b.shareTime <= :endTime");
    StringBuilder sql3 = new StringBuilder(
        "select case when sum(a.resCount) is null then 0 else sum(a.resCount) end from Recordbag a inner join UserSpace u on u.userId=a.teacherId and a.gradeId = u.gradeId and a.subjectId = u.subjectId and u.sysRoleId = :sysRoleId and u.phaseId = :phaseId where a.orgId = :orgId and a.share=:isShare and a.del = :isDelete and a.shareTime >= :startTime and a.shareTime <= :endTime"
            + conditionStr + "");
    StringBuilder sql4 = new StringBuilder(
        "select count(a.id) from PlainSummary a where a.isShare = :isShare and a.phaseId = :phaseId and a.orgId = :orgId and a.roleId = :teacher and a.shareTime >= :startTime and a.shareTime <= :endTime"
            + conditionStr);
    StringBuilder sql5 = new StringBuilder(
        "select count(b.id) from Thesis b where b.userId in (select distinct a.userId from UserSpace a where a.orgId = :orgId and a.sysRoleId = :teacher and a.phaseId = :phaseId "
            + conditionStr
            + ") and b.isShare = :isShare and b.orgId = :orgId and b.phaseId = :phaseId and b.shareTime >= :startTime and b.shareTime <= :endTime and b.enable = :enable");
    args.put("sysRoleId", SysRole.TEACHER.getId());
    return countByNamedSql(sql1.toString(), args) + countByNamedSql(sql2.toString(), args)
        + countByNamedSql(sql3.toString(), args) + countByNamedSql(sql4.toString(), args)
        + countByNamedSql(sql5.toString(), args);
  }

  /**
   * 学校下管理者查阅教案的总数
   * 
   * @param search
   * @return
   * @see com.tmser.tr.back.dao.OperationManageDao#getViewLessonPlanTotalCount_leader(com.tmser.tr.back.vo.SearchVo)
   */
  @Override
  public Integer getViewLessonPlanTotalCount_leader(SearchVo search) {
    Map<String, Object> args = getArgsforSql(search);
    args.put("resType", ResTypeConstants.JIAOAN);
    StringBuilder sql = new StringBuilder(
        "select sum(m.res_count) from (select count(distinct a.resId) res_count from CheckInfo a ");
    sql.append(" inner join UserSpace u on a.spaceId = u.id and u.orgId = :orgId");
    if (search.getRoleId() != null) {
      sql.append(" and u.sysRoleId = :roleId");
    }
    if (search.getSubjectId() != null) {
      sql.append(" and u.subjectId = :subjectId");
    }
    if (search.getGradeId() != null) {
      sql.append(" and u.gradeId = :gradeId");
    }
    sql.append(" where a.phase = :phaseId and a.resType = :resType and a.createtime >= :startTime and a.createtime <= :endTime group by a.userId) m");
    return countByNamedSql(sql.toString(), args);
  }

  /**
   * 学校下管理者查阅课件的总数
   * 
   * @param search
   * @return
   * @see com.tmser.tr.back.dao.OperationManageDao#getViewKejianTotalCount_leader(com.tmser.tr.back.vo.SearchVo)
   */
  @Override
  public Integer getViewKejianTotalCount_leader(SearchVo search) {
    Map<String, Object> args = getArgsforSql(search);
    args.put("resType", ResTypeConstants.KEJIAN);
    StringBuilder sql = new StringBuilder(
        "select sum(m.res_count) from (select count(distinct a.resId) res_count from CheckInfo a ");
    sql.append(" inner join UserSpace u on a.spaceId = u.id and u.orgId = :orgId");
    if (search.getRoleId() != null) {
      sql.append(" and u.sysRoleId = :roleId");
    }
    if (search.getSubjectId() != null) {
      sql.append(" and u.subjectId = :subjectId");
    }
    if (search.getGradeId() != null) {
      sql.append(" and u.gradeId = :gradeId");
    }
    sql.append(" where a.phase = :phaseId and a.resType = :resType and a.createtime >= :startTime and a.createtime <= :endTime group by a.userId) m");
    return countByNamedSql(sql.toString(), args);
  }

  /**
   * 学校下的管理者查阅反思的总数
   * 
   * @param search
   * @return
   * @see com.tmser.tr.back.dao.OperationManageDao#getViewFansiTotalCount_leader(com.tmser.tr.back.vo.SearchVo)
   */
  @Override
  public Integer getViewFansiTotalCount_leader(SearchVo search) {
    Map<String, Object> args = getArgsforSql(search);
    args.put("resType1", ResTypeConstants.FANSI);
    args.put("resType2", ResTypeConstants.FANSI_OTHER);
    StringBuilder sql = new StringBuilder(
        "select sum(m.res_count) from (select count(distinct a.resId) res_count from CheckInfo a ");
    sql.append(" inner join UserSpace u on a.spaceId = u.id and u.orgId = :orgId");
    if (search.getRoleId() != null) {
      sql.append(" and u.sysRoleId = :roleId");
    }
    if (search.getSubjectId() != null) {
      sql.append(" and u.subjectId = :subjectId");
    }
    if (search.getGradeId() != null) {
      sql.append(" and u.gradeId = :gradeId");
    }
    sql.append(" where a.phase = :phaseId and (a.resType = :resType1 or a.resType = :resType2) and a.createtime >= :startTime and a.createtime <= :endTime group by a.userId) m");
    return countByNamedSql(sql.toString(), args);
  }

  /**
   * 学校下管理者查阅计划总结的总数
   * 
   * @param search
   * @return
   * @see com.tmser.tr.back.dao.OperationManageDao#getViewPlanSummaryTotalCount_leader(com.tmser.tr.back.vo.SearchVo)
   */
  @Override
  public Integer getViewPlanSummaryTotalCount_leader(SearchVo search) {
    Map<String, Object> args = getArgsforSql(search);
    args.put("resType1", ResTypeConstants.TPPLAIN_SUMMARY_PLIAN);
    args.put("resType2", ResTypeConstants.TPPLAIN_SUMMARY_SUMMARY);
    StringBuilder sql = new StringBuilder(
        "select sum(m.res_count) from (select count(distinct a.resId) res_count from CheckInfo a ");
    sql.append(" inner join UserSpace u on a.spaceId = u.id and u.orgId = :orgId");
    if (search.getRoleId() != null) {
      sql.append(" and u.sysRoleId = :roleId");
    }
    if (search.getSubjectId() != null) {
      sql.append(" and u.subjectId = :subjectId");
    }
    if (search.getGradeId() != null) {
      sql.append(" and u.gradeId = :gradeId");
    }
    sql.append(" where a.phase = :phaseId and (a.resType = :resType1 or a.resType = :resType2) and a.createtime >= :startTime and a.createtime <= :endTime group by a.userId) m");
    return countByNamedSql(sql.toString(), args);
  }

  /**
   * 学校下管理者的听课记录总数
   * 
   * @param search
   * @return
   * @see com.tmser.tr.back.dao.OperationManageDao#getListenTotalCount_leader(com.tmser.tr.back.vo.SearchVo)
   */
  @Override
  public Integer getListenTotalCount_leader(SearchVo search) {
    Map<String, Object> args = getArgsforSql(search);
    String conditionStr = "";
    if (search.getRoleId() != null) {
      conditionStr = conditionStr + " and u.sysRoleId = :roleId";
    }
    if (search.getSubjectId() != null) {
      conditionStr = conditionStr + " and u.subjectId = :subjectId";
    }
    if (search.getGradeId() != null) {
      conditionStr = conditionStr + " and u.gradeId = :gradeId";
    }
    StringBuilder sql = new StringBuilder(
        "select count(a.id) from LectureRecords a where a.lecturepeopleId in (select distinct u.userId from UserSpace u where u.orgId = :orgId and u.sysRoleId <> :teacher and u.phaseId = :phaseId "
            + conditionStr
            + ") and a.orgId = :orgId and a.phaseId = :phaseId and a.isDelete = :isDelete and a.isEpub = :isEpub and a.crtDttm >= :startTime and a.crtDttm <= :endTime ");
    return countByNamedSql(sql.toString(), args);
  }

  /**
   * 学校下管理者的教学文章总数
   * 
   * @param search
   * @return
   * @see com.tmser.tr.back.dao.OperationManageDao#getTeachTextTotalCount_leader(com.tmser.tr.back.vo.SearchVo)
   */
  @Override
  public Integer getTeachTextTotalCount_leader(SearchVo search) {
    Map<String, Object> args = getArgsforSql(search);
    String conditionStr = "";
    if (search.getRoleId() != null) {
      conditionStr = conditionStr + " and u.sysRoleId = :roleId";
    }
    if (search.getSubjectId() != null) {
      conditionStr = conditionStr + " and u.subjectId = :subjectId";
    }
    if (search.getGradeId() != null) {
      conditionStr = conditionStr + " and u.gradeId = :gradeId";
    }
    StringBuilder sql = new StringBuilder(
        "select count(a.id) from Thesis a where a.userId in (select distinct u.userId from UserSpace u where u.orgId = :orgId and u.sysRoleId <> :teacher and u.phaseId = :phaseId "
            + conditionStr
            + ") and a.orgId = :orgId and a.phaseId = :phaseId and a.enable = :enable and a.crtDttm >= :startTime and a.crtDttm <= :endTime ");
    return countByNamedSql(sql.toString(), args);
  }

  /**
   * 学校下管理者的计划总结的总数
   * 
   * @param search
   * @return
   * @see com.tmser.tr.back.dao.OperationManageDao#getPlanSummaryTotalCount_leader(com.tmser.tr.back.vo.SearchVo)
   */
  @Override
  public Integer getPlanSummaryTotalCount_leader(SearchVo search) {
    Map<String, Object> args = getArgsforSql(search);
    String conditionStr = " and roleId <> :teacher";
    if (search.getRoleId() != null) {
      conditionStr = conditionStr + " and roleId = :roleId";
    }
    if (search.getSubjectId() != null) {
      conditionStr = conditionStr + " and subjectId = :subjectId";
    }
    if (search.getGradeId() != null) {
      conditionStr = conditionStr + " and gradeId = :gradeId";
    }
    StringBuilder sql = new StringBuilder(
        "select count(id) from PlainSummary where orgId = :orgId and phaseId = :phaseId and crtDttm >= :startTime and crtDttm <= :endTime "
            + conditionStr);
    return countByNamedSql(sql.toString(), args);
  }

  /**
   * 学校下管理者的集体备课发布总数
   * 
   * @param search
   * @return
   * @see com.tmser.tr.back.dao.OperationManageDao#getActivityPushTotalCount_leader(com.tmser.tr.back.vo.SearchVo)
   */
  @Override
  public Integer getActivityPushTotalCount_leader(SearchVo search) {
    Map<String, Object> args = getArgsforSql(search);
    args.put("status", 1);
    StringBuilder sql = new StringBuilder("select COUNT(a.id) from Activity a ");
    if (search.getRoleId() != null || search.getSubjectId() != null || search.getGradeId() != null) {
      sql.append(" inner join UserSpace u on a.spaceId = u.id ");
      if (search.getRoleId() != null) {
        sql.append(" and u.sysRoleId = :roleId");
      }
      if (search.getSubjectId() != null) {
        sql.append(" and u.subjectId = :subjectId");
      }
      if (search.getGradeId() != null) {
        sql.append(" and u.gradeId = :gradeId");
      }
    }
    sql.append(" where a.orgId = :orgId and a.phaseId = :phaseId and a.status = :status and a.releaseTime>=:startTime and a.releaseTime<=:endTime");
    return countByNamedSql(sql.toString(), args);
  }

  /**
   * 学校下管理者的集体备课参与的总数
   * 
   * @param search
   * @return
   * @see com.tmser.tr.back.dao.OperationManageDao#getActivityJoinTotalCount_leader(com.tmser.tr.back.vo.SearchVo)
   */
  @Override
  public Integer getActivityJoinTotalCount_leader(SearchVo search) {
    Map<String, Object> args = getArgsforSql(search);
    args.put("editType", 2);
    String conditionStr = "";
    String conditionStr1 = "";
    conditionStr = conditionStr
        + " INNER JOIN UserSpace u on a.spaceId = u.id and u.sysRoleId <> :teacher and u.phaseId = :phaseId";
    conditionStr1 = conditionStr1
        + " INNER JOIN UserSpace u on c.spaceId = u.id and u.sysRoleId <> :teacher and u.orgId = :orgId and u.phaseId = :phaseId ";
    if (search.getRoleId() != null) {
      conditionStr = conditionStr + " and u.sysRoleId = :roleId ";
      conditionStr1 = conditionStr1 + " and u.sysRoleId = :roleId ";
    }
    if (search.getSubjectId() != null || search.getGradeId() != null) {
      conditionStr = conditionStr + " INNER JOIN Activity x on a.activityId = x.id ";
      conditionStr1 = conditionStr1 + " INNER JOIN Activity x on c.activityId = x.id ";
      if (search.getSubjectId() != null) {
        conditionStr = conditionStr + " and x.subjectIds like :lksubjectId ";
        conditionStr1 = conditionStr1 + " and x.subjectIds like :lksubjectId ";
      }
      if (search.getGradeId() != null) {
        conditionStr = conditionStr + " and x.gradeIds like :lkgradeId ";
        conditionStr1 = conditionStr1 + " and x.gradeIds like :lkgradeId ";
      }
    }
    StringBuilder sql = new StringBuilder("select COUNT(DISTINCT m.activity_id,m.user_id) from ");
    sql.append("(select a.activityId as activity_id,a.crtId as user_id from ActivityTracks a " + conditionStr
        + " where a.orgId = :orgId and a.editType <> :editType and a.crtDttm >= :startTime and a.crtDttm <= :endTime ");
    sql.append(" union all select c.activityId as activity_id,c.crtId as user_id from Discuss c " + conditionStr1
        + " where c.crtDttm >= :startTime and c.crtDttm <= :endTime) m");
    return countByNamedSql(sql.toString(), args);
  }

  /**
   * 学校下管理者的集体备课的讨论总数
   * 
   * @param search
   * @return
   * @see com.tmser.tr.back.dao.OperationManageDao#getActivityDiscussTotalCount_leader(com.tmser.tr.back.vo.SearchVo)
   */
  @Override
  public Integer getActivityDiscussTotalCount_leader(SearchVo search) {
    Map<String, Object> args = getArgsforSql(search);
    String conditionStr1 = "";
    conditionStr1 = conditionStr1
        + " INNER JOIN UserSpace u on c.spaceId = u.id and u.sysRoleId <> :teacher and u.orgId = :orgId and u.phaseId = :phaseId ";
    if (search.getRoleId() != null) {
      conditionStr1 = conditionStr1 + " and u.sysRoleId = :roleId ";
    }
    if (search.getSubjectId() != null || search.getGradeId() != null) {
      conditionStr1 = conditionStr1 + " INNER JOIN Activity x on c.activityId = x.id ";
      if (search.getSubjectId() != null) {
        conditionStr1 = conditionStr1 + " and x.subjectIds like :lksubjectId ";
      }
      if (search.getGradeId() != null) {
        conditionStr1 = conditionStr1 + " and x.gradeIds like :lkgradeId ";
      }
    }
    StringBuilder sql = new StringBuilder("select COUNT(c.id) from Discuss c " + conditionStr1
        + " where c.typeId = :typeId and c.crtDttm >= :startTime and c.crtDttm <= :endTime");
    args.put("typeId", ResTypeConstants.ACTIVITY);
    return countByNamedSql(sql.toString(), args);
  }

  /**
   * 学校下管理者集体备课查阅总数
   * 
   * @param search
   * @return
   * @see com.tmser.tr.back.dao.OperationManageDao#getActivityViewTotalCount_leader(com.tmser.tr.back.vo.SearchVo)
   */
  @Override
  public Integer getActivityViewTotalCount_leader(SearchVo search) {
    Map<String, Object> args = getArgsforSql(search);
    args.put("resType", ResTypeConstants.ACTIVITY);
    StringBuilder sql = new StringBuilder(
        "select sum(m.res_count) from (select count(distinct a.resId) res_count from CheckInfo a ");
    sql.append(" inner join UserSpace u on a.spaceId = u.id and u.orgId = :orgId ");
    if (search.getRoleId() != null) {
      sql.append(" and u.sysRoleId = :roleId");
    }
    // if(search.getSubjectId()!=null || search.getGradeId()!=null){
    // sql.append(" INNER JOIN Activity x on a.resId = x.id ");
    // if(search.getSubjectId()!=null){
    // sql.append(" and x.subjectIds like :lksubjectId ");
    // }
    // if(search.getGradeId()!=null){
    // sql.append(" and x.gradeIds like :lkgradeId ");
    // }
    // }
    if (search.getSubjectId() != null) {
      sql.append(" and u.subjectId = :subjectId");
    }
    if (search.getGradeId() != null) {
      sql.append(" and u.gradeId = :gradeId");
    }
    sql.append(" where a.phase = :phaseId and a.resType = :resType and a.createtime >= :startTime and a.createtime <= :endTime group by a.userId) m");
    return countByNamedSql(sql.toString(), args);
  }

  /**
   * 学校下管理者的校际教研发布总数
   * 
   * @param search
   * @return
   * @see com.tmser.tr.back.dao.OperationManageDao#getSchoolActivityPushTotalCount_leader(com.tmser.tr.back.vo.SearchVo)
   */
  @Override
  public Integer getSchoolActivityPushTotalCount_leader(SearchVo search) {
    Map<String, Object> args = getArgsforSql(search);
    args.put("status", 1);
    StringBuilder sql = new StringBuilder("select COUNT(a.id) from SchoolActivity a ");
    if (search.getRoleId() != null || search.getSubjectId() != null || search.getGradeId() != null) {
      sql.append(" inner join UserSpace u on a.spaceId = u.id ");
      if (search.getRoleId() != null) {
        sql.append(" and u.sysRoleId = :roleId");
      }
      if (search.getSubjectId() != null) {
        sql.append(" and u.subjectId = :subjectId");
      }
      if (search.getGradeId() != null) {
        sql.append(" and u.gradeId = :gradeId");
      }
    }
    sql.append(" where a.orgId = :orgId and a.phaseId = :phaseId and a.status = :status and a.releaseTime>=:startTime and a.releaseTime<=:endTime");
    return countByNamedSql(sql.toString(), args);
  }

  /**
   * 学校下管理者的校际教研参与总数
   * 
   * @param search
   * @return
   * @see com.tmser.tr.back.dao.OperationManageDao#getSchoolActivityJoinTotalCount_leader(com.tmser.tr.back.vo.SearchVo)
   */
  @Override
  public Integer getSchoolActivityJoinTotalCount_leader(SearchVo search) {
    Map<String, Object> args = getArgsforSql(search);
    args.put("editType", 2);
    args.put("typeId", ResTypeConstants.SCHOOLTEACH);
    String conditionStr = "";
    String conditionStr1 = "";
    conditionStr = conditionStr
        + " INNER JOIN UserSpace u on a.spaceId = u.id and u.sysRoleId <> :teacher and u.phaseId = :phaseId ";
    conditionStr1 = conditionStr1
        + " INNER JOIN UserSpace u on c.spaceId = u.id and u.sysRoleId <> :teacher and u.orgId = :orgId and u.phaseId = :phaseId ";
    if (search.getRoleId() != null) {
      conditionStr = conditionStr + " and u.sysRoleId = :roleId ";
      conditionStr1 = conditionStr1 + " and u.sysRoleId = :roleId ";
    }
    if (search.getSubjectId() != null || search.getGradeId() != null) {
      conditionStr = conditionStr + " INNER JOIN Activity x on a.activityId = x.id ";
      conditionStr1 = conditionStr1 + " INNER JOIN Activity x on c.activityId = x.id ";
      if (search.getSubjectId() != null) {
        conditionStr = conditionStr + " and x.subjectIds like :lksubjectId ";
        conditionStr1 = conditionStr1 + " and x.subjectIds like :lksubjectId ";
      }
      if (search.getGradeId() != null) {
        conditionStr = conditionStr + " and x.gradeIds like :lkgradeId ";
        conditionStr1 = conditionStr1 + " and x.gradeIds like :lkgradeId ";
      }
    }
    StringBuilder sql = new StringBuilder("select COUNT(DISTINCT m.activity_id,m.user_id) from");
    sql.append("(select a.activityId as activity_id,a.userId as user_id from SchoolActivityTracks a " + conditionStr
        + " where a.orgId = :orgId and a.editType <> :editType and a.crtDttm >= :startTime and a.crtDttm <= :endTime ");
    sql.append(" union all select c.activityId as activity_id,c.crtId as user_id from Discuss c " + conditionStr1
        + " where c.typeId = :typeId and c.crtDttm >= :startTime and c.crtDttm <= :endTime) m");
    return countByNamedSql(sql.toString(), args);
  }

  /**
   * 学校下管理者的校际教研讨论总数
   * 
   * @param search
   * @return
   * @see com.tmser.tr.back.dao.OperationManageDao#getSchoolActivityDiscussTotalCount_leader(com.tmser.tr.back.vo.SearchVo)
   */
  @Override
  public Integer getSchoolActivityDiscussTotalCount_leader(SearchVo search) {
    Map<String, Object> args = getArgsforSql(search);
    args.put("typeId", ResTypeConstants.SCHOOLTEACH);
    String conditionStr1 = "";
    conditionStr1 = conditionStr1
        + " INNER JOIN UserSpace u on c.spaceId = u.id and u.sysRoleId <> :teacher and u.orgId = :orgId and u.phaseId = :phaseId ";
    if (search.getRoleId() != null) {
      conditionStr1 = conditionStr1 + " and u.sysRoleId = :roleId ";
    }
    if (search.getSubjectId() != null || search.getGradeId() != null) {
      conditionStr1 = conditionStr1 + " INNER JOIN Activity x on c.activityId = x.id ";
      if (search.getSubjectId() != null) {
        conditionStr1 = conditionStr1 + " and x.subjectIds like :lksubjectId ";
      }
      if (search.getGradeId() != null) {
        conditionStr1 = conditionStr1 + " and x.gradeIds like :lkgradeId ";
      }
    }
    StringBuilder sql = new StringBuilder("select COUNT(c.id) from Discuss c " + conditionStr1
        + " where c.typeId = :typeId and c.crtDttm >= :startTime and c.crtDttm <= :endTime");
    return countByNamedSql(sql.toString(), args);
  }

  /**
   * 学校下管理者的同伴互助留言总数
   * 
   * @param search
   * @return
   * @see com.tmser.tr.back.dao.OperationManageDao#getPeerMessageTotalCount_leader(com.tmser.tr.back.vo.SearchVo)
   */
  @Override
  public Integer getPeerMessageTotalCount_leader(SearchVo search) {
    Map<String, Object> args = getArgsforSql(search);
    StringBuilder sql = new StringBuilder(
        "select count(a.id) from JyCompanionMessage a where a.senderTime >= :startTime and a.senderTime <= :endTime and a.userIdSender in (select distinct u.userId from UserSpace u where u.orgId = :orgId and u.phaseId = :phaseId and u.sysRoleId <> :teacher )");
    return countByNamedSql(sql.toString(), args);
  }

  /**
   * 学校下管理者分享的总数
   * 
   * @param search
   * @return
   * @see com.tmser.tr.back.dao.OperationManageDao#getShareTotalCount_leader(com.tmser.tr.back.vo.SearchVo)
   */
  @Override
  public Integer getShareTotalCount_leader(SearchVo search) {
    Map<String, Object> args = getArgsforSql(search);
    args.put("isShare", 1);
    String conditionStr = "";
    String conditionStr1 = " inner join UserSpace u on a.spaceId = u.id and u.sysRoleId <> :teacher ";
    String conditionStr2 = "";
    if (search.getRoleId() != null) {
      conditionStr1 = conditionStr1 + " and u.sysRoleId = :roleId ";
      conditionStr2 = conditionStr2 + " and u.sysRoleId = :roleId ";
    }
    if (search.getSubjectId() != null) {
      conditionStr = conditionStr + " and subjectId = :subjectId";
      conditionStr1 = conditionStr1 + " and u.subjectId = :subjectId ";
      conditionStr2 = conditionStr2 + " and u.subjectId = :subjectId ";
    }
    if (search.getGradeId() != null) {
      conditionStr = conditionStr + " and gradeId = :gradeId";
      conditionStr1 = conditionStr1 + " and u.gradeId = :gradeId ";
      conditionStr2 = conditionStr2 + " and u.gradeId = :gradeId ";
    }
    StringBuilder sql1 = new StringBuilder(
        "select count(a.id) from LectureRecords a where a.lecturepeopleId in (select distinct u.userId from UserSpace u where u.orgId = :orgId and u.sysRoleId <> :teacher and u.phaseId = :phaseId "
            + conditionStr2
            + ") and a.isShare = :isShare and a.shareTime >= :startTime and a.shareTime <= :endTime and a.orgId = :orgId and a.phaseId = :phaseId");
    StringBuilder sql2 = new StringBuilder(
        "select count(id) from PlainSummary where roleId = :roleId and isShare = :isShare and shareTime >= :startTime and shareTime <= :endTime and orgId = :orgId and phaseId = :phaseId "
            + conditionStr);
    StringBuilder sql3 = new StringBuilder(
        "select count(a.id) from Thesis a where a.userId in (select distinct u.userId from UserSpace u where u.orgId = :orgId and u.sysRoleId <> :teacher and u.phaseId = :phaseId "
            + conditionStr2
            + ") and a.isShare = :isShare and a.shareTime >= :startTime and a.shareTime <= :endTime and a.orgId = :orgId and a.phaseId = :phaseId and a.enable = :enable");
    StringBuilder sql4 = new StringBuilder(
        "select count(a.id) from Activity a "
            + conditionStr1
            + " where a.isShare = :isShare and a.shareTime >= :startTime and a.shareTime <= :endTime and a.orgId = :orgId and a.phaseId = :phaseId ");
    StringBuilder sql5 = new StringBuilder(
        "select count(a.id) from SchoolActivity a "
            + conditionStr1
            + " where a.isShare = :isShare and a.shareTime >= :startTime and a.shareTime <= :endTime and a.orgId = :orgId and a.phaseId = :phaseId ");
    return countByNamedSql(sql1.toString(), args) + countByNamedSql(sql2.toString(), args)
        + countByNamedSql(sql3.toString(), args) + countByNamedSql(sql4.toString(), args)
        + countByNamedSql(sql5.toString(), args);
  }

  /**
   * @param search
   * @return
   * @see com.tmser.tr.back.operationmanage.dao.OperationManageDao#getLessonPlanCountLesson(com.tmser.tr.back.operationmanage.vo.SearchVo)
   */
  @Override
  public Integer getLessonPlanCountLesson(SearchVo search) {
    Map<String, Object> args = getArgsforSql(search);
    args.put("planType", 0);
    StringBuilder sql = new StringBuilder(
        "select count(distinct a.infoId) from LessonPlan a where a.enable = :enable and a.orgId = :orgId and a.planType = :planType and a.phaseId = :phaseId and a.crtDttm >= :startTime and a.crtDttm <= :endTime");
    if (search.getSubjectId() != null) {
      sql.append(" and a.subjectId = :subjectId");
    }
    if (search.getGradeId() != null) {
      sql.append(" and a.gradeId = :gradeId");
    }
    return countByNamedSql(sql.toString(), args);
  }

  /**
   * @param search
   * @return
   * @see com.tmser.tr.back.operationmanage.dao.OperationManageDao#getLessonCount_teacher(com.tmser.tr.back.operationmanage.vo.SearchVo)
   */
  @Override
  public Integer getLessonCount_teacher(SearchVo search) {
    Map<String, Object> args = getArgsforSql(search);
    args.put("jiaoan", 0);
    String conditionStr = "";
    if (search.getSubjectId() != null) {
      conditionStr = conditionStr + " and subjectId = :subjectId";
    }
    if (search.getGradeId() != null) {
      conditionStr = conditionStr + " and gradeId = :gradeId";
    }
    StringBuilder sql = new StringBuilder(
        "select count(distinct infoId) from LessonPlan where userId = :userId and enable = :enable and planType = :jiaoan and crtDttm >= :startTime and crtDttm <= :endTime "
            + conditionStr);
    return countByNamedSql(sql.toString(), args);
  }

  /**
   * @param search
   * @return
   * @see com.tmser.tr.back.operationmanage.dao.OperationManageDao#getKejianLesson_teacher(com.tmser.tr.back.operationmanage.vo.SearchVo)
   */
  @Override
  public Integer getKejianLesson_teacher(SearchVo search) {
    Map<String, Object> args = getArgsforSql(search);
    args.put("kejian", 1);
    String conditionStr = "";
    if (search.getSubjectId() != null) {
      conditionStr = conditionStr + " and subjectId = :subjectId";
    }
    if (search.getGradeId() != null) {
      conditionStr = conditionStr + " and gradeId = :gradeId";
    }
    StringBuilder sql = new StringBuilder(
        "select count(distinct infoId) from LessonPlan where userId = :userId and enable = :enable and planType = :kejian and crtDttm >= :startTime and crtDttm <= :endTime "
            + conditionStr);
    return countByNamedSql(sql.toString(), args);
  }

  /**
   * @param search
   * @return
   * @see com.tmser.tr.back.operationmanage.dao.OperationManageDao#getFansiLesson_teacher(com.tmser.tr.back.operationmanage.vo.SearchVo)
   */
  @Override
  public Integer getFansiLesson_teacher(SearchVo search) {
    Map<String, Object> args = getArgsforSql(search);
    args.put("kehou", 2);
    args.put("qita", 3);
    String conditionStr = "";
    if (search.getSubjectId() != null) {
      conditionStr = conditionStr + " and subjectId = :subjectId";
    }
    if (search.getGradeId() != null) {
      conditionStr = conditionStr + " and gradeId = :gradeId";
    }
    StringBuilder sql = new StringBuilder(
        "select count(distinct infoId) from LessonPlan where userId = :userId and enable = :enable and (planType = :kehou or planType = :qita) and crtDttm >= :startTime and crtDttm <= :endTime "
            + conditionStr);
    return countByNamedSql(sql.toString(), args);
  }

  /**
   * @param search
   * @return
   * @see com.tmser.tr.back.operationmanage.dao.OperationManageDao#getLessonTotalCount_teacher(com.tmser.tr.back.operationmanage.vo.SearchVo)
   */
  @Override
  public Integer getLessonTotalCount_teacher(SearchVo search) {
    Map<String, Object> args = getArgsforSql(search);
    args.put("planType", 0);
    String conditionStr = "";
    if (search.getSubjectId() != null) {
      conditionStr = conditionStr + " and a.subjectId = :subjectId";
    }
    if (search.getGradeId() != null) {
      conditionStr = conditionStr + " and a.gradeId = :gradeId";
    }
    StringBuilder sql = new StringBuilder(
        "select count(distinct a.infoId) from LessonPlan a where a.enable = :enable and a.phaseId = :phaseId and a.orgId = :orgId and a.planType = :planType and a.crtDttm >= :startTime and a.crtDttm <= :endTime "
            + conditionStr);
    return countByNamedSql(sql.toString(), args);
  }

  /**
   * @param search
   * @return
   * @see com.tmser.tr.back.operationmanage.dao.OperationManageDao#getKejianTotalLesson_teacher(com.tmser.tr.back.operationmanage.vo.SearchVo)
   */
  @Override
  public Integer getKejianTotalLesson_teacher(SearchVo search) {
    Map<String, Object> args = getArgsforSql(search);
    args.put("kejian", 1);
    String conditionStr = "";
    if (search.getSubjectId() != null) {
      conditionStr = conditionStr + " and subjectId = :subjectId";
    }
    if (search.getGradeId() != null) {
      conditionStr = conditionStr + " and gradeId = :gradeId";
    }
    StringBuilder sql = new StringBuilder(
        "select count(distinct infoId) from LessonPlan where userId = :userId and enable = :enable and planType = :kejian and crtDttm >= :startTime and crtDttm <= :endTime "
            + conditionStr);
    return countByNamedSql(sql.toString(), args);
  }

  /**
   * @param search
   * @return
   * @see com.tmser.tr.back.operationmanage.dao.OperationManageDao#getFansiTotalLesson_teacher(com.tmser.tr.back.operationmanage.vo.SearchVo)
   */
  @Override
  public Integer getFansiTotalLesson_teacher(SearchVo search) {
    Map<String, Object> args = getArgsforSql(search);
    args.put("kehou", 2);
    args.put("qita", 3);
    String conditionStr = "";
    if (search.getSubjectId() != null) {
      conditionStr = conditionStr + " and subjectId = :subjectId";
    }
    if (search.getGradeId() != null) {
      conditionStr = conditionStr + " and gradeId = :gradeId";
    }
    StringBuilder sql = new StringBuilder(
        "select count(distinct infoId) from LessonPlan where userId = :userId and enable = :enable and (planType = :kehou or planType = :qita) and crtDttm >= :startTime and crtDttm <= :endTime "
            + conditionStr);
    return countByNamedSql(sql.toString(), args);
  }

  /**
   * @param search
   * @return
   * @see com.tmser.tr.back.operationmanage.dao.OperationManageDao#getLessonPlanTotalCountLessonOfArea(com.tmser.tr.back.operationmanage.vo.SearchVo)
   */
  @Override
  public Integer getLessonPlanTotalCountLessonOfArea(SearchVo search) {
    Map<String, Object> args = getArgsforSql(search);
    args.put("planType", 0);
    StringBuilder sql = new StringBuilder(
        "select count(distinct a.infoId) from LessonPlan a inner join Organization o on a.orgId = o.id and o.areaId = :areaId where a.enable = :enable and a.planType = :planType and a.phaseId = :phaseId and a.crtDttm >= :startTime and a.crtDttm <= :endTime");
    if (search.getSubjectId() != null) {
      sql.append(" and a.subjectId = :subjectId");
    }
    if (search.getGradeId() != null) {
      sql.append(" and a.gradeId = :gradeId");
    }
    return countByNamedSql(sql.toString(), args);
  }

}
