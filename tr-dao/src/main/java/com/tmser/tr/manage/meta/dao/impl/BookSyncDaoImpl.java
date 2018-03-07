/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */

package com.tmser.tr.manage.meta.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.tmser.tr.common.dao.AbstractDAO;
import com.tmser.tr.common.dao.annotation.UseCache;
import com.tmser.tr.manage.meta.bo.BookSync;
import com.tmser.tr.manage.meta.dao.BookSyncDao;

/**
 * <pre>
 *
 * </pre>
 *
 * @author 3020mt
 * @version $Id: BookSyncDaoImpl.java, v 1.0 2016年7月15日 下午4:02:58 3020mt Exp $
 */
@Repository
@UseCache
public class BookSyncDaoImpl extends AbstractDAO<BookSync, Integer> implements BookSyncDao {

  @Override
  public List<BookSync> getByIds(List<Integer> ids) {
    String sql = "select * from BookSync where id in (:ids)";
    Map<String, Object> args = new HashMap<String, Object>();
    args.put("ids", ids);
    return this.queryByNamedSql(sql, args, this.getMapper());
  }

  @Override
  public Integer delRelationComId(String comId) {
    String sql = "update BookSync set relationComId='' where relationComId=:comId";
    Map<String, Object> args = new HashMap<String, Object>();
    args.put("comId", comId);
    return this.updateWithNamedSql(sql, args);
  }

  @Override
  public List<BookSync> getBookSyncsByOrg(Integer orgId, List<Integer> areaIds, Integer gradeId, Integer subjectId,
      Integer phaseId, Integer publisherId) {
    StringBuilder sql = new StringBuilder("SELECT * FROM BookSync pc WHERE 1=1 ");
    Map<String, Object> args = new HashMap<String, Object>();
    if (orgId == null && areaIds == null) {
      sql.append(" AND pc.orgId = '0' ");
      sql.append(" AND pc.areaId = 0");
    }

    if (areaIds != null && orgId == null) {
      sql.append(" AND pc.areaId in(:areaIds)");
      sql.append(" AND pc.comId NOT IN (SELECT b.comId FROM BookSync b WHERE b.areaId IN (:areaIds) AND b.enable = 0)");
      args.put("areaIds", areaIds);
    }

    if (orgId != null && areaIds != null) {
      sql.append(" AND (pc.areaId IN (:areaIds) OR pc.orgId = :orgId)");
      sql.append(" AND pc.comId NOT IN ( SELECT b.comId FROM BookSync b WHERE b.orgId = :orgId AND b.enable = 0)");
      args.put("orgId", orgId);
      args.put("areaIds", areaIds);
    }
    sql.append(" AND pc.enable = 1");
    if (gradeId != null) {
      sql.append(" AND pc.gradeLevelId = :gradeId");
    }
    if (subjectId != null) {
      sql.append(" AND pc.subjectId = :subjectId");
    }
    if (gradeId != null) {
      sql.append(" AND pc.phaseId = :phaseId");
    }
    if (publisherId != null) {
      sql.append(" AND pc.publisherId = :publisherId");
    }
    args.put("gradeId", gradeId);
    args.put("subjectId", subjectId);
    args.put("phaseId", phaseId);
    args.put("publisherId", publisherId);

    return this.queryByNamedSql(sql.toString(), args, getMapper());
  }

}
