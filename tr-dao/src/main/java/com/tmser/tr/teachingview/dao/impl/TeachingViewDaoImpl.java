package com.tmser.tr.teachingview.dao.impl;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.tmser.tr.activity.bo.ActivityTracks;
import com.tmser.tr.common.ResTypeConstants;
import com.tmser.tr.common.dao.AbstractQuery;
import com.tmser.tr.common.orm.SqlMapping;
import com.tmser.tr.lessonplan.bo.LessonPlan;
import com.tmser.tr.teachingview.dao.TeachingViewDao;
import com.tmser.tr.teachingview.vo.SearchVo;
import com.tmser.tr.uc.SysRole;
import com.tmser.tr.uc.bo.UserSpace;

/**
 * 
 * <pre>
 * 教研一览dao实现类
 * </pre>
 *
 * @author wangdawei
 * @version $Id: TeachingViewDaoImpl.java, v 1.0 2016年4月11日 下午3:33:18 wangdawei
 *          Exp $
 */
@Repository
public class TeachingViewDaoImpl extends AbstractQuery implements TeachingViewDao {

  @Autowired(required = false)
  private SqlMapping sqlMapping = null;

  @Override
  protected String mappingSql(String oldsql) {
    return this.sqlMapping.mapping(oldsql);
  }

  /**
   * 根据统计项的id和查询条件查询统计数
   * 
   * @param searchVo
   * @param id
   * @return
   * @throws SecurityException
   * @throws NoSuchMethodException
   * @see com.tmser.tr.teachingview.dao.TeachingViewDao#getCount(com.tmser.tr.teachingview.vo.SearchVo,
   *      java.lang.String)
   * @author wangdawei
   */
  @Override
  public Integer getCount(SearchVo searchVo, String id) throws Exception {
    Method m = TeachingViewDaoImpl.class.getDeclaredMethod("count_" + id, SearchVo.class);
    Integer count = (Integer) m.invoke(this, searchVo);
    return count;
  }

  /**
   * 获取教案撰写数量（教师）
   * 
   * @param searchVo
   * @return
   * @author wangdawei
   */
  public Integer count_jiaoanWrite(SearchVo searchVo) {
    StringBuilder condition = new StringBuilder("");
    if (searchVo.getGradeId() != null) {
      condition.append(" and a.gradeId = :gradeId");
    }
    if (searchVo.getSubjectId() != null) {
      condition.append(" and a.subjectId = :subjectId");
    }
    if (searchVo.getUserId() != null) {
      condition.append(" and a.userId = :userId");
    }
    Map<String, Object> argsMap = getArgsMap(searchVo);
    argsMap.put("planType", 0);
    argsMap.put("enable", 1);
    String sql = "select count(distinct a.infoId) from LessonPlan a where a.orgId = :orgId and a.crtDttm >= :startTime and a.crtDttm < :endTime and a.enable = :enable and a.planType = :planType "
        + condition.toString();
    return countByNamedSql(sql, argsMap);
  }

  /**
   * 获取教案撰写总篇数（教师）
   * 
   * @param searchVo
   * @return
   */
  public Integer count_jiaoanTotal(SearchVo searchVo) {
    StringBuilder condition = new StringBuilder("");
    if (searchVo.getGradeId() != null) {
      condition.append(" and a.gradeId = :gradeId");
    }
    if (searchVo.getSubjectId() != null) {
      condition.append(" and a.subjectId = :subjectId");
    }
    if (searchVo.getUserId() != null) {
      condition.append(" and a.userId = :userId");
    }
    Map<String, Object> argsMap = getArgsMap(searchVo);
    argsMap.put("planType", 0);
    argsMap.put("enable", 1);
    String sql = "select count(distinct a.planId) from LessonPlan a where a.orgId = :orgId and a.crtDttm >= :startTime and a.crtDttm < :endTime and a.enable = :enable and a.planType = :planType "
        + condition.toString();
    return countByNamedSql(sql, argsMap);
  }

  /**
   * 教案分享数
   * 
   * @param searchVo
   * @return
   * @author wangdawei
   */
  public Integer count_jiaoanShare(SearchVo searchVo) {
    StringBuilder condition = new StringBuilder("");
    if (searchVo.getGradeId() != null) {
      condition.append(" and a.gradeId = :gradeId");
    }
    if (searchVo.getSubjectId() != null) {
      condition.append(" and a.subjectId = :subjectId");
    }
    if (searchVo.getUserId() != null) {
      condition.append(" and a.userId = :userId");
    }
    Map<String, Object> argsMap = getArgsMap(searchVo);
    argsMap.put("planType", 0);
    argsMap.put("enable", 1);
    String sql = "select count(a.planId) from LessonPlan a where a.orgId = :orgId and a.shareTime >= :startTime and a.shareTime < :endTime and a.isShare = :isShare and a.enable = :enable and a.planType = :planType "
        + condition.toString();
    argsMap.put("isShare", 1);
    return countByNamedSql(sql, argsMap);
  }

  /**
   * 获取课件撰写数量（教师）
   * 
   * @param searchVo
   * @return
   * @author wangdawei
   */
  public Integer count_kejianWrite(SearchVo searchVo) {
    StringBuilder condition = new StringBuilder("");
    if (searchVo.getGradeId() != null) {
      condition.append(" and a.gradeId = :gradeId");
    }
    if (searchVo.getSubjectId() != null) {
      condition.append(" and a.subjectId = :subjectId");
    }
    if (searchVo.getUserId() != null) {
      condition.append(" and a.userId = :userId");
    }
    Map<String, Object> argsMap = getArgsMap(searchVo);
    argsMap.put("planType", 1);
    argsMap.put("enable", 1);
    String sql = "select count(distinct a.infoId) from LessonPlan a where a.orgId = :orgId and a.crtDttm >= :startTime and a.crtDttm < :endTime and a.enable = :enable and a.planType = :planType "
        + condition.toString();
    return countByNamedSql(sql, argsMap);
  }

  /**
   * 获取课件撰写总篇数（教师）
   * 
   * @param searchVo
   * @return
   * @author wangdawei
   */
  public Integer count_kejianTotal(SearchVo searchVo) {
    StringBuilder condition = new StringBuilder("");
    if (searchVo.getGradeId() != null) {
      condition.append(" and a.gradeId = :gradeId");
    }
    if (searchVo.getSubjectId() != null) {
      condition.append(" and a.subjectId = :subjectId");
    }
    if (searchVo.getUserId() != null) {
      condition.append(" and a.userId = :userId");
    }
    Map<String, Object> argsMap = getArgsMap(searchVo);
    argsMap.put("planType", 1);
    argsMap.put("enable", 1);
    String sql = "select count(a.planId) from LessonPlan a where a.orgId = :orgId and a.crtDttm >= :startTime and a.crtDttm < :endTime and a.enable = :enable and a.planType = :planType "
        + condition.toString();
    return countByNamedSql(sql, argsMap);
  }

  /**
   * 课件分享数
   * 
   * @param searchVo
   * @return
   * @author wangdawei
   */
  public Integer count_kejianShare(SearchVo searchVo) {
    StringBuilder condition = new StringBuilder("");
    if (searchVo.getGradeId() != null) {
      condition.append(" and a.gradeId = :gradeId");
    }
    if (searchVo.getSubjectId() != null) {
      condition.append(" and a.subjectId = :subjectId");
    }
    if (searchVo.getUserId() != null) {
      condition.append(" and a.userId = :userId");
    }
    Map<String, Object> argsMap = getArgsMap(searchVo);
    argsMap.put("planType", 1);
    argsMap.put("enable", 1);
    String sql = "select count(a.planId) from LessonPlan a where a.orgId = :orgId and a.shareTime >= :startTime and a.shareTime < :endTime and a.isShare = :isShare and a.enable = :enable and a.planType = :planType "
        + condition.toString();
    argsMap.put("isShare", 1);
    return countByNamedSql(sql, argsMap);
  }

  /**
   * 反思撰写数（教师）
   * 
   * @param searchVo
   * @return
   * @author wangdawei
   */
  public Integer count_fansiWrite(SearchVo searchVo) {
    StringBuilder condition = new StringBuilder("");
    if (searchVo.getGradeId() != null) {
      condition.append(" and a.gradeId = :gradeId");
    }
    if (searchVo.getSubjectId() != null) {
      condition.append(" and a.subjectId = :subjectId");
    }
    if (searchVo.getUserId() != null) {
      condition.append(" and a.userId = :userId");
    }
    Map<String, Object> argsMap = getArgsMap(searchVo);
    argsMap.put("planType", 2);
    argsMap.put("planType1", 3);
    argsMap.put("enable", 1);
    String sql = "select count(distinct a.infoId) from LessonPlan a where a.orgId = :orgId and a.crtDttm >= :startTime and a.crtDttm < :endTime and a.enable = :enable and a.planType = :planType "
        + condition.toString();
    String sql1 = "select count(a.planId) from LessonPlan a where a.orgId = :orgId and a.crtDttm >= :startTime and a.crtDttm < :endTime and a.enable = :enable and a.planType = :planType1 "
        + condition.toString();
    return countByNamedSql(sql, argsMap) + countByNamedSql(sql1, argsMap);
  }

  /**
   * 反思撰写总篇数（教师）
   * 
   * @param searchVo
   * @return
   * @author wangdawei
   */
  public Integer count_fansiTotal(SearchVo searchVo) {
    StringBuilder condition = new StringBuilder("");
    if (searchVo.getGradeId() != null) {
      condition.append(" and a.gradeId = :gradeId");
    }
    if (searchVo.getSubjectId() != null) {
      condition.append(" and a.subjectId = :subjectId");
    }
    if (searchVo.getUserId() != null) {
      condition.append(" and a.userId = :userId");
    }
    Map<String, Object> argsMap = getArgsMap(searchVo);
    argsMap.put("planType", 2);
    argsMap.put("planType1", 3);
    argsMap.put("enable", 1);
    String sql = "select count(a.planId) from LessonPlan a where a.orgId = :orgId and a.crtDttm >= :startTime and a.crtDttm < :endTime and a.enable = :enable and a.planType = :planType "
        + condition.toString();
    String sql1 = "select count(a.planId) from LessonPlan a where a.orgId = :orgId and a.crtDttm >= :startTime and a.crtDttm < :endTime and a.enable = :enable and a.planType = :planType1 "
        + condition.toString();
    return countByNamedSql(sql, argsMap) + countByNamedSql(sql1, argsMap);
  }

  /**
   * 反思分享数（教师）
   * 
   * @param searchVo
   * @return
   * @author wangdawei
   */
  public Integer count_fansiShare(SearchVo searchVo) {
    StringBuilder condition = new StringBuilder("");
    if (searchVo.getGradeId() != null) {
      condition.append(" and a.gradeId = :gradeId");
    }
    if (searchVo.getSubjectId() != null) {
      condition.append(" and a.subjectId = :subjectId");
    }
    if (searchVo.getUserId() != null) {
      condition.append(" and a.userId = :userId");
    }
    Map<String, Object> argsMap = getArgsMap(searchVo);
    argsMap.put("planType", 2);
    argsMap.put("planType1", 3);
    argsMap.put("enable", 1);
    String sql = "select count(a.planId) from LessonPlan a where a.orgId = :orgId and a.shareTime >= :startTime and a.shareTime < :endTime and a.isShare = :isShare and a.enable = :enable and (a.planType = :planType or a.planType = :planType1) "
        + condition.toString();
    argsMap.put("isShare", 1);
    return countByNamedSql(sql, argsMap);
  }



  /**
   * 集体备课参与数（教师）
   * 
   * @param searchVo
   * @return
   * @author wangdawei
   */
  public Integer count_activityJoin(SearchVo searchVo) {
    Map<String, Object> argsMap = getArgsMap(searchVo);
    StringBuilder condition = new StringBuilder("");
    StringBuilder condition1 = new StringBuilder("");
    StringBuilder sql;
    argsMap.put("typeId", ResTypeConstants.ACTIVITY);
    if (searchVo.getSpaceId() != null) {
      condition.append(" where a.spaceId = :spaceId");
      condition1.append(" where b.spaceId = :spaceId and b.typeId=:typeId");
      condition.append(
          " and a.editType != :editType and a.orgId = :orgId and a.crtDttm >= :startTime and a.crtDttm < :endTime ");
      condition1.append(" and b.crtDttm >= :startTime and b.crtDttm < :endTime ");
      argsMap.put("editType", ActivityTracks.ZHUBEI);
      sql = new StringBuilder("select count(distinct m.activity_id) from (select a.activityId from ActivityTracks a ");
      sql.append(condition.toString());
      sql.append(" union all select b.activityId from Discuss b ");
      sql.append(condition1.toString());
      sql.append(" ) m ");
    } else {
      condition.append(
          " where a.spaceId in(select u.id from UserSpace u where u.orgId = :orgId and u.sysRoleId = :sysRoleId and u.enable = :enable ");
      condition1.append(
          " where b.typeId=:typeId and b.spaceId in(select u.id from UserSpace u where u.orgId = :orgId and u.sysRoleId = :sysRoleId and u.enable = :enable ");
      if (searchVo.getGradeId() != null) {
        condition.append(" and u.gradeId = :gradeId");
        condition1.append(" and u.gradeId = :gradeId");
      }
      if (searchVo.getSubjectId() != null) {
        condition.append(" and u.subjectId = :subjectId");
        condition1.append(" and u.subjectId = :subjectId");
      }
      condition.append(")");
      condition1.append(")");
      argsMap.put("sysRoleId", SysRole.TEACHER.getId());
      // condition1.append(" where 1=1 ");
      condition.append(
          " and a.editType != :editType and a.orgId = :orgId and a.crtDttm >= :startTime and a.crtDttm < :endTime ");
      condition1.append(" and b.crtDttm >= :startTime and b.crtDttm < :endTime ");
      argsMap.put("editType", ActivityTracks.ZHUBEI);
      sql = new StringBuilder(
          "select count(distinct m.activity_id,m.user_id) from (select a.activityId as activity_id,a.userId as user_id from ActivityTracks a ");
      sql.append(condition.toString());
      sql.append(" union all select b.activityId as activity_id,b.crtId as user_id from Discuss b ");
      sql.append(condition1.toString());
      sql.append(" ) m ");
    }
    return countByNamedSql(sql.toString(), argsMap);
  }

  /**
   * 可参与集体备课数（教师）
   * 
   * @param searchVo
   * @return
   * @author wangdawei
   */
  public Integer count_activityCanJoin(SearchVo searchVo) {
    StringBuilder sql = new StringBuilder(
        "select count(a.id) from Activity a where a.createTime >= :startTime and a.createTime < :endTime and a.status = :status and a.orgId = :orgId and ( (");
    Map<String, Object> argsMap = getArgsMap(searchVo);
    argsMap.put("status", 1);
    argsMap.put("gradeId1", "%," + searchVo.getGradeId() + ",%");
    argsMap.put("subjectId1", "%," + searchVo.getSubjectId() + ",%");
    StringBuilder condition = new StringBuilder("");
    if (searchVo.getGradeId() != null) {
      condition.append(" a.gradeIds like :gradeId1");
      if (searchVo.getSubjectId() != null) {
        condition.append(" and a.subjectIds like :subjectId1");
      }
    } else {
      if (searchVo.getSubjectId() != null) {
        condition.append(" a.subjectIds like :subjectId1");
      }
    }
    condition.append(") or (");
    if (searchVo.getUserId() != null) {
      condition
          .append(" a.mainUserGradeId = :gradeId and a.mainUserSubjectId = :subjectId and a.mainUserId = :userId))");
    } else {
      StringBuilder condition1 = new StringBuilder("");
      StringBuilder condition2 = new StringBuilder("");
      if (searchVo.getGradeId() != null) {
        condition1.append(" and u.gradeId = :gradeId");
        condition2.append(" and a.mainUserGradeId = :gradeId");
      }
      if (searchVo.getSubjectId() != null) {
        condition1.append(" and u.subjectId = :subjectId");
        condition2.append(" and a.mainUserSubjectId = :subjectId");
      }
      condition.append(
          "a.mainUserId in (select distinct u.userId from UserSpace u where u.enable = :enable and u.orgId = :orgId and u.sysRoleId = :sysRoleId "
              + condition1.toString() + ") " + condition2.toString() + "))");
      argsMap.put("enable", 1);
      argsMap.put("sysRoleId", SysRole.TEACHER.getId());
    }
    sql.append(condition.toString());
    return countByNamedSql(sql.toString(), argsMap);
  }


  /**
   * 成长档案袋资源精选数
   * 
   * @param searchVo
   * @return
   * @author wangdawei
   */
  public Integer count_teacherRecordRes(SearchVo searchVo) {
    StringBuilder sql = new StringBuilder(
        "select count(a.recordId) from Record a inner join Recordbag b on a.bagId = b.id and b.orgId = :orgId and b.`delete` = :del ");
    Map<String, Object> argsMap = getArgsMap(searchVo);
    argsMap.put("del", 0);
    if (searchVo.getUserId() != null) {
      sql.append(" and b.teacherId = :userId");
    }
    if (searchVo.getGradeId() != null) {
      sql.append(" and b.gradeId = :gradeId");
    }
    if (searchVo.getSubjectId() != null) {
      sql.append(" and b.subjectId = :subjectId");
    }
    sql.append(" where a.createTime>=:startTime and a.createTime<:endTime ");
    // argsMap.put("schoolYear", searchVo.getSchoolYear());
    argsMap.put("status", 1);
    return countByNamedSql(sql.toString(), argsMap);
  }

  /**
   * 校际教研可参与数（教师）
   * 
   * @param searchVo
   * @return
   * @author wangdawei
   */
  public Integer count_schoolActivityCanJoin(SearchVo searchVo) {
    StringBuilder sql = new StringBuilder(
        "select count(a.id) from SchoolActivity a inner join SchoolTeachCircleOrg s on a.schoolTeachCircleId = s.stcId and s.orgId = :orgId and (s.state = :state1 or s.state = :state2) where a.status = :status and a.createTime >= :startTime and a.createTime < :endTime and ( (");
    Map<String, Object> argsMap = getArgsMap(searchVo);
    argsMap.put("status", 1);
    argsMap.put("state1", 2);
    argsMap.put("state2", 5);
    argsMap.put("gradeId1", "%," + searchVo.getGradeId() + ",%");
    argsMap.put("subjectId1", "%," + searchVo.getSubjectId() + ",%");
    StringBuilder condition = new StringBuilder("");
    if (searchVo.getGradeId() != null) {
      condition.append(" a.gradeIds like :gradeId1");
      if (searchVo.getSubjectId() != null) {
        condition.append(" and a.subjectIds like :subjectId1");
      }
    } else {
      if (searchVo.getSubjectId() != null) {
        condition.append(" a.subjectIds like :subjectId1");
      }
    }
    condition.append(") or (");
    if (searchVo.getUserId() != null) {
      condition
          .append(" a.mainUserGradeId = :gradeId and a.mainUserSubjectId = :subjectId and a.mainUserId = :userId))");
    } else {
      StringBuilder condition1 = new StringBuilder("");
      StringBuilder condition2 = new StringBuilder("");
      if (searchVo.getGradeId() != null) {
        condition1.append(" and u.gradeId = :gradeId");
        condition2.append(" and a.mainUserGradeId = :gradeId");
      }
      if (searchVo.getSubjectId() != null) {
        condition1.append(" and u.subjectId = :subjectId");
        condition2.append(" and a.mainUserSubjectId = :subjectId");
      }
      condition.append(
          "a.mainUserId in (select distinct u.userId from UserSpace u where u.enable = :enable and u.orgId = :orgId and u.sysRoleId = :sysRoleId "
              + condition1.toString() + ") " + condition2.toString() + "))");
      argsMap.put("enable", 1);
      argsMap.put("sysRoleId", SysRole.TEACHER.getId());
    }
    sql.append(condition.toString());
    return countByNamedSql(sql.toString(), argsMap);
  }

  /**
   * 分享发表总数（教师）
   * 
   * @param searchVo
   * @return
   * @author wangdawei
   */
  public Integer count_share(SearchVo searchVo) {
    StringBuilder sql = new StringBuilder(
        "select count(a.id) from Recordbag a where a.share = :isShare and a.orgId = :orgId and a.`delete` = :del and a.shareTime>=:startTime and a.shareTime<:endTime ");
    Map<String, Object> argsMap = getArgsMap(searchVo);
    argsMap.put("del", 0);
    argsMap.put("isShare", 1);
    if (searchVo.getUserId() != null) {
      sql.append(" and a.teacherId = :userId");
    }
    if (searchVo.getGradeId() != null) {
      sql.append(" and a.gradeId = :gradeId");
    }
    if (searchVo.getSubjectId() != null) {
      sql.append(" and a.subjectId = :subjectId");
    }
    argsMap.put("sysRoleId", SysRole.TEACHER.getId());
    argsMap.put("enable", 1);
    return countByNamedSql(sql.toString(), argsMap) + count_jiaoanShare(searchVo) + count_kejianShare(searchVo)
        + count_fansiShare(searchVo);
  }

  /**
   * 获取查询条件map
   * 
   * @param searchVo
   * @return
   * @author wangdawei
   */
  private Map<String, Object> getArgsMap(SearchVo searchVo) {
    Map<String, Object> args = new HashMap<String, Object>();
    args.put("orgId", searchVo.getOrgId());
    args.put("termId", searchVo.getTermId());
    args.put("phaseId", searchVo.getPhaseId());
    args.put("gradeId", searchVo.getGradeId());
    args.put("subjectId", searchVo.getSubjectId());
    args.put("userId", searchVo.getUserId());
    args.put("spaceId", searchVo.getSpaceId());
    args.put("startTime", searchVo.getStartTime());
    args.put("endTime", searchVo.getEndTime());
    args.put("enable", 1);
    args.put("lkorgId", "%," + searchVo.getOrgId() + ",%");
    args.put("lkgradeId", "%," + searchVo.getGradeId() + ",%");
    args.put("lksubjectId", "%," + searchVo.getSubjectId() + ",%");
    args.put("lkuserId", "%," + searchVo.getUserId() + ",%");

    return args;
  }

  /**
   * 教案查阅数（管理者）
   * 
   * @author wangyao
   * @param searchVo
   * @return
   */
  public Integer count_jiaoan_read(SearchVo searchVo) {
    StringBuilder condition = new StringBuilder("");
    if (searchVo.getUserId() != null) {
      condition.append(" and a.userId = :userId");
    }
    if (searchVo.getPhaseId() != null) {
      condition.append(" and a.phase = :phaseId");
    }
    if (!CollectionUtils.isEmpty(searchVo.getSpaceIds())) {
      condition.append(" and a.spaceId in (:spaceIds)");
    }
    condition.append(" and a.resType = :resType");
    Map<String, Object> argsMap = getArgsMap(searchVo);
    argsMap.put("spaceIds", searchVo.getSpaceIds());
    argsMap.put("phaseId", searchVo.getPhaseId());
    argsMap.put("resType", ResTypeConstants.JIAOAN);
    String sql = "select count(distinct a.resId) from CheckInfo a where a.createtime >= :startTime and a.createtime < :endTime "
        + condition.toString();
    return countByNamedSql(sql, argsMap);
  }

  /**
   * 课件查阅数（管理者）
   * 
   * @author wangyao
   * @param searchVo
   * @return
   */
  public Integer count_kejian_read(SearchVo searchVo) {
    StringBuilder condition = new StringBuilder("");
    if (searchVo.getUserId() != null) {
      condition.append(" and a.userId = :userId");
    }
    if (searchVo.getPhaseId() != null) {
      condition.append(" and a.phase = :phaseId");
    }
    if (!CollectionUtils.isEmpty(searchVo.getSpaceIds())) {
      condition.append(" and a.spaceId in (:spaceIds)");
    }
    condition.append(" and a.resType = :resType");
    Map<String, Object> argsMap = getArgsMap(searchVo);
    argsMap.put("spaceIds", searchVo.getSpaceIds());
    argsMap.put("phaseId", searchVo.getPhaseId());
    argsMap.put("resType", ResTypeConstants.KEJIAN);
    String sql = "select count(distinct a.resId) from CheckInfo a where a.createtime >= :startTime and a.createtime < :endTime "
        + condition.toString();
    return countByNamedSql(sql, argsMap);
  }

  /**
   * 反思查阅数（管理者）
   * 
   * @author wangyao
   * @param searchVo
   * @return
   */
  public Integer count_fansi_read(SearchVo searchVo) {
    StringBuilder condition = new StringBuilder("");
    if (searchVo.getUserId() != null) {
      condition.append(" and a.userId = :userId");
    }
    if (searchVo.getPhaseId() != null) {
      condition.append(" and a.phase = :phaseId");
    }
    if (!CollectionUtils.isEmpty(searchVo.getSpaceIds())) {
      condition.append(" and a.spaceId in (:spaceIds)");
    }
    condition.append(" and a.resType in (:resType)");
    List<Integer> resTypeList = new ArrayList<Integer>();
    resTypeList.add(ResTypeConstants.FANSI);
    resTypeList.add(ResTypeConstants.FANSI_OTHER);
    Map<String, Object> argsMap = getArgsMap(searchVo);
    argsMap.put("spaceIds", searchVo.getSpaceIds());
    argsMap.put("phaseId", searchVo.getPhaseId());
    argsMap.put("resType", resTypeList);
    String sql = "select count(distinct a.resId) from CheckInfo a where a.createtime >= :startTime and a.createtime < :endTime "
        + condition.toString();
    return countByNamedSql(sql, argsMap);
  }

  /**
   * 计划总结查阅数（管理者）
   * 
   * @author wangyao
   * @param searchVo
   * @return
   */
  public Integer count_plan_summary_read(SearchVo searchVo) {
    StringBuilder condition = new StringBuilder("");
    if (searchVo.getUserId() != null) {
      condition.append(" and a.userId = :userId");
    }
    if (searchVo.getPhaseId() != null) {
      condition.append(" and a.phase = :phaseId");
    }
    if (!CollectionUtils.isEmpty(searchVo.getSpaceIds())) {
      condition.append(" and a.spaceId in (:spaceIds)");
    }
    condition.append(" and a.resType in (:resType)");
    Map<String, Object> argsMap = getArgsMap(searchVo);
    argsMap.put("spaceIds", searchVo.getSpaceIds());
    argsMap.put("phaseId", searchVo.getPhaseId());
    List<Integer> resTypeList = new ArrayList<Integer>();
    resTypeList.add(ResTypeConstants.TPPLAIN_SUMMARY_PLIAN);
    resTypeList.add(ResTypeConstants.TPPLAIN_SUMMARY_SUMMARY);
    argsMap.put("resType", resTypeList);
    String sql = "select count(distinct a.resId) from CheckInfo a where a.createtime >= :startTime and a.createtime < :endTime "
        + condition.toString();
    return countByNamedSql(sql, argsMap);
  }

  /**
   * 集体备课查阅数（管理者）
   * 
   * @author wangyao
   * @param searchVo
   * @return
   */
  public Integer count_activity_read(SearchVo searchVo) {
    StringBuilder condition = new StringBuilder("");
    if (searchVo.getUserId() != null) {
      condition.append(" and a.userId = :userId");
    }
    if (searchVo.getPhaseId() != null) {
      condition.append(" and a.phase = :phaseId");
    }
    if (!CollectionUtils.isEmpty(searchVo.getSpaceIds())) {
      condition.append(" and a.spaceId in (:spaceIds)");
    }
    condition.append(" and a.resType in (:resType)");
    Map<String, Object> argsMap = getArgsMap(searchVo);
    argsMap.put("spaceIds", searchVo.getSpaceIds());
    argsMap.put("phaseId", searchVo.getPhaseId());
    argsMap.put("resType", ResTypeConstants.ACTIVITY);
    String sql = "select count(distinct a.resId) from CheckInfo a where a.createtime >= :startTime and a.createtime < :endTime "
        + condition.toString();
    return countByNamedSql(sql, argsMap);
  }

  /**
   * 集体备课发起数（管理者）
   * 
   * @author wangyao
   * @param searchVo
   * @return
   */
  public Integer count_activity_origination(SearchVo searchVo) {
    StringBuilder condition = new StringBuilder("");
    if (searchVo.getOrgId() != null) {
      condition.append(" and a.orgId = :orgId");
    }
    if (searchVo.getUserId() != null) {
      condition.append(" and a.organizeUserId = :organizeUserId");
    }
    if (searchVo.getPhaseId() != null) {
      condition.append(" and a.phaseId = :phaseId");
    }
    Map<String, Object> argsMap = getArgsMap(searchVo);
    argsMap.put("spaceIds", searchVo.getSpaceIds());
    argsMap.put("phaseId", searchVo.getPhaseId());
    argsMap.put("status", 1);
    argsMap.put("organizeUserId", searchVo.getUserId());

    String sql = "select count(distinct a.id) from Activity a where a.status = :status and a.createTime >= :startTime and a.createTime < :endTime "
        + condition.toString();
    return countByNamedSql(sql, argsMap);
  }

  /**
   * 获取教案可查阅数（管理者）
   * 
   * @author wangyao
   * @param searchVo
   * @return
   */
  public Integer count_jiaoan_allow_read(SearchVo searchVo) {
    String str = "";
    StringBuilder condition = new StringBuilder("");
    if (searchVo.getOrgId() != null) {
      condition.append(" and a.orgId = :orgId");
      str += " and orgId = :orgId";
    }
    if (searchVo.getPhaseId() != null) {
      condition.append(" and a.phaseId = :phaseId");
      str += " and phaseId = :phaseId";
    }
    Map<String, Object> argsMap = getArgsMap(searchVo);
    List<Integer> gradeIds = searchVo.getGradeIds();
    List<Integer> subjectIds = searchVo.getSubjectIds();
    if (!CollectionUtils.isEmpty(gradeIds)) {
      str += " and gradeId in (:gradeIds)";
      argsMap.put("gradeIds", gradeIds);
    }
    if (!CollectionUtils.isEmpty(subjectIds)) {
      str += " and subjectId in (:subjectIds)";
      argsMap.put("subjectIds", subjectIds);
    }
    String sqlStr = " INNER JOIN (select infoId from LessonPlan where isSubmit=:isSubmit and submitTime >= :startTime and submitTime < :endTime and planType=:planType and enable=:enable "
        + str + ") s on s.info_id=a.id ";
    argsMap.put("isSubmit", true);
    argsMap.put("enable", true);
    argsMap.put("jiaoanSubmitCount", 0);
    argsMap.put("planType", ResTypeConstants.JIAOAN);
    argsMap.put("phaseId", searchVo.getPhaseId());
    String sql = "select count(distinct a.id) from LessonInfo a " + sqlStr
        + " where a.jiaoanSubmitCount >:jiaoanSubmitCount  " + condition.toString();
    return countByNamedSql(sql, argsMap);
  }

  /**
   * 获取课件可查阅数（管理者）
   * 
   * @author wangyao
   * @param searchVo
   * @return
   */
  public Integer count_kejian_allow_read(SearchVo searchVo) {
    StringBuilder condition = new StringBuilder("");
    String str = "";
    if (searchVo.getOrgId() != null) {
      condition.append(" and a.orgId = :orgId");
      str += " and orgId = :orgId";
    }
    if (searchVo.getPhaseId() != null) {
      condition.append(" and a.phaseId = :phaseId");
      str += " and phaseId = :phaseId";
    }
    Map<String, Object> argsMap = getArgsMap(searchVo);
    List<Integer> gradeIds = searchVo.getGradeIds();
    List<Integer> subjectIds = searchVo.getSubjectIds();
    if (!CollectionUtils.isEmpty(gradeIds)) {
      str += " and gradeId in (:gradeIds)";
      argsMap.put("gradeIds", gradeIds);
    }
    if (!CollectionUtils.isEmpty(subjectIds)) {
      str += " and subjectId in (:subjectIds)";
      argsMap.put("subjectIds", subjectIds);
    }
    String sqlStr = " INNER JOIN (select infoId from LessonPlan where isSubmit=:isSubmit and submitTime >= :startTime and submitTime < :endTime and planType=:planType and enable=:enable "
        + str + ") s on s.info_id=a.id ";
    argsMap.put("isSubmit", true);
    argsMap.put("enable", true);
    argsMap.put("planType", ResTypeConstants.KEJIAN);
    argsMap.put("kejianSubmitCount", 0);
    argsMap.put("phaseId", searchVo.getPhaseId());
    String sql = "select count(distinct a.id) from LessonInfo a " + sqlStr
        + " where a.kejianSubmitCount >:kejianSubmitCount " + condition.toString();
    return countByNamedSql(sql, argsMap);
  }

  /**
   * 获取反思可查阅数（管理者）
   * 
   * @author wangyao
   * @param searchVo
   * @return
   */
  public Integer count_fansi_allow_read(SearchVo searchVo) {
    StringBuilder condition = new StringBuilder("");
    String str = "";
    if (searchVo.getOrgId() != null) {
      condition.append(" and a.orgId = :orgId");
      str += " and orgId = :orgId";
    }
    if (searchVo.getPhaseId() != null) {
      condition.append(" and a.phaseId = :phaseId");
      str += " and phaseId = :phaseId";
    }
    Map<String, Object> argsMap = getArgsMap(searchVo);
    List<Integer> gradeIds = searchVo.getGradeIds();
    List<Integer> subjectIds = searchVo.getSubjectIds();
    String str1 = "";
    if (!CollectionUtils.isEmpty(gradeIds)) {
      str += " and gradeId in (:gradeIds)";
      str1 += " and a.gradeId in (:gradeIds)";
      argsMap.put("gradeIds", gradeIds);
    }
    if (!CollectionUtils.isEmpty(subjectIds)) {
      str += " and subjectId in (:subjectIds)";
      str1 += " and a.subjectId in (:subjectIds)";
      argsMap.put("subjectIds", subjectIds);
    }
    String sqlStr = " INNER JOIN (select infoId from LessonPlan where isSubmit=:isSubmit and submitTime >= :startTime and submitTime < :endTime and planType=:planType and enable=:enable "
        + str + ") s on s.info_id=a.id ";
    argsMap.put("enable", true);
    argsMap.put("planType", ResTypeConstants.FANSI);
    argsMap.put("fansiSubmitCount", 0);
    argsMap.put("phaseId", searchVo.getPhaseId());
    argsMap.put("isSubmit", true);
    String sql = "select count(distinct a.id) from LessonInfo a " + sqlStr
        + " where a.fansiSubmitCount >:fansiSubmitCount " + condition.toString();
    int fansiCount = countByNamedSql(sql, argsMap);// 所有已经提交的
    argsMap.put("planType", LessonPlan.QI_TA_FAN_SI);
    String sql1 = "select count(distinct a.planId) from LessonPlan a where a.isSubmit = :isSubmit and a.planType = :planType and a.submitTime >= :startTime and a.submitTime < :endTime and a.enable=:enable "
        + str1 + condition.toString();
    int qiTaCount = countByNamedSql(sql1, argsMap);// 所有已经提交的
    return fansiCount + qiTaCount;
  }

  /**
   * 集体备课可查阅数（管理者）
   * 
   * @author wangyao
   * @param searchVo
   * @return
   */
  public Integer count_activity_allow_read(SearchVo searchVo) {
    List<UserSpace> userspaceList = searchVo.getUserSpaceList();
    StringBuilder condition = new StringBuilder("");
    StringBuilder conditionStr = new StringBuilder("");
    Integer count = 0;
    if (!CollectionUtils.isEmpty(userspaceList)) {
      Map<String, Object> argsMap = getArgsMap(searchVo);
      for (UserSpace userSpace : userspaceList) {
        Integer sysRoleId = userSpace.getSysRoleId();
        if (SysRole.XKZZ.getId().equals(sysRoleId)) { // 学科组长
          conditionStr.append(" a.subjectIds like :subjectId1 or ");
          argsMap.put("subjectId1", "%," + userSpace.getSubjectId() + ",%");
        } else if (SysRole.NJZZ.getId().equals(sysRoleId)) { // 年级组长
          conditionStr.append(" a.gradeIds like :gradeId1 or ");
          argsMap.put("gradeId1", "%," + userSpace.getGradeId() + ",%");
        } else if (SysRole.BKZZ.getId().equals(sysRoleId)) { // 备课组长
          argsMap.put("subjectId", userSpace.getSubjectId());
          argsMap.put("gradeId", userSpace.getGradeId());
          conditionStr.append(" (a.gradeIds like :lkgradeId and a.subjectIds like :lksubjectId) or ");
        }
      }
      String string = conditionStr.toString();
      if (string.length() > 4) {
        boolean endsWith = string.endsWith(" or ");
        if (endsWith) {
          string = string.substring(0, string.length() - " or ".length());
        }
        condition.append(" and ( " + string + " )");
      }
      if (searchVo.getOrgId() != null) {
        condition.append(" and a.orgId = :orgId");
      }
      if (searchVo.getPhaseId() != null) {
        condition.append(" and a.phaseId = :phaseId");
      }
      argsMap.put("phaseId", searchVo.getPhaseId());
      argsMap.put("isSubmit", true);
      argsMap.put("organizeUserId", searchVo.getUserId());
      String sql = "select count(distinct a.id) from Activity a where a.isSubmit = :isSubmit and a.organizeUserId != :organizeUserId"
          + " and a.createTime >= :startTime and a.createTime < :endTime " + condition.toString();
      count = countByNamedSql(sql, argsMap);
    }
    return count;
  }

  /**
   * 集体备课分享数（管理者）
   * 
   * @author wangyao
   * @param searchVo
   * @return
   */
  public Integer count_activity_share(SearchVo searchVo) {
    StringBuilder condition = new StringBuilder("");
    if (searchVo.getOrgId() != null) {
      condition.append(" and a.orgId = :orgId");
    }
    if (searchVo.getPhaseId() != null) {
      condition.append(" and a.phaseId = :phaseId");
    }
    if (searchVo.getUserId() != null) {
      condition.append(" and a.organizeUserId = :organizeUserId");
    }
    Map<String, Object> argsMap = getArgsMap(searchVo);
    argsMap.put("phaseId", searchVo.getPhaseId());
    argsMap.put("organizeUserId", searchVo.getUserId());
    argsMap.put("isShare", true);
    String sql = "select count(distinct a.id) from Activity a where a.isShare=:isShare and a.shareTime >= :startTime and a.shareTime < :endTime "
        + condition.toString();
    return countByNamedSql(sql, argsMap);
  }

  /**
   * 集体备课可参与数（管理者）
   * 
   * @author wangyao
   * @param searchVo
   * @return
   */
  public Integer count_activity_allow_part_in(SearchVo searchVo) {
    List<UserSpace> userspaceList = searchVo.getUserSpaceList();
    List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
    List<Integer> countList = new ArrayList<Integer>();
    if (!CollectionUtils.isEmpty(userspaceList)) {
      for (UserSpace userSpace : userspaceList) {
        StringBuilder condition = new StringBuilder("");
        Integer sysRoleId = userSpace.getSysRoleId();
        if (sysRoleId.intValue() == SysRole.XKZZ.getId().intValue()) { // 学科组长
          condition.append(" and a.subjectIds like :lksubjectId and a.spaceId!=:spaceId");
        } else if (sysRoleId.intValue() == SysRole.NJZZ.getId().intValue()) { // 年级组长
          condition.append(" and a.gradeIds like :lkgradeId");
        } else if (sysRoleId.intValue() == SysRole.BKZZ.getId().intValue()) { // 备课组长
          condition
              .append(" and a.gradeIds like :lkgradeId and a.subjectIds like :lksubjectId and a.spaceId!=:spaceId");
        } else if (sysRoleId.intValue() == SysRole.TEACHER.getId().intValue()) { // 教师
          condition.append(
              " and ((a.gradeIds like :lkgradeId and a.subjectIds like :lksubjectId) or (a.subjectIds like :subjectId and a.mainUserId=:userId))");
        }
        if (searchVo.getOrgId() != null) {
          condition.append(" and a.orgId = :orgId");
        }
        if (searchVo.getPhaseId() != null) {
          condition.append(" and a.phaseId = :phaseId");
        }
        Map<String, Object> argsMap = getArgsMap(searchVo);
        argsMap.put("subjectId", userSpace.getSubjectId());
        argsMap.put("gradeId", userSpace.getGradeId());
        argsMap.put("phaseId", searchVo.getPhaseId());
        argsMap.put("status", 1);
        argsMap.put("organizeUserId", searchVo.getUserId());
        argsMap.put("spaceId", userSpace.getId());
        String sql = "select a.id from Activity a where a.status = :status and a.createTime >= :startTime and a.createTime < :endTime "
            + condition.toString();
        listMap.addAll(queryByNamedSql(sql, argsMap));
      }
    }
    for (Map<String, Object> map : listMap) {
      Integer id = (Integer) map.get("id");
      if (id != null) {
        if (!countList.contains(id)) {
          countList.add(id);
        }
      }
    }
    return countList.size();
  }

  /**
   * 集体备课参与数（管理者）
   * 
   * @author wangyao
   * @param searchVo
   * @return
   */
  public Integer count_activity_part_in(SearchVo searchVo) {
    String aSpaceIds = "";
    String cSpaceIds = "";
    StringBuilder condition = new StringBuilder("");
    if (!CollectionUtils.isEmpty(searchVo.getSpaceIds())) {
      aSpaceIds = " and a.spaceId in (:spaceIds)";
      cSpaceIds = " and c.spaceId in (:spaceIds)";
    }
    Map<String, Object> argsMap = getArgsMap(searchVo);
    argsMap.put("spaceIds", searchVo.getSpaceIds());
    argsMap.put("editType", ActivityTracks.ZHUBEI);
    argsMap.put("typeId", ResTypeConstants.ACTIVITY);
    String sql = "select count(DISTINCT t.activity_id) from (select a.activityId from ActivityTracks a where a.userId = :userId and a.editType <> :editType"
        + aSpaceIds + " and a.crtDttm >= :startTime and a.crtDttm < :endTime"
        + " union all select c.activityId from Discuss c where c.crtId = :userId and c.typeId=:typeId " + cSpaceIds
        + " and c.crtDttm >= :startTime and c.crtDttm < :endTime) t" + condition.toString();
    return countByNamedSql(sql, argsMap);
  }


}
