/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.manage.meta;

import java.util.List;

import com.tmser.tr.manage.meta.MetaProvider.OrgTypeMetaProvider;
import com.tmser.tr.manage.meta.MetaProvider.PhaseGradeMetaProvider;
import com.tmser.tr.manage.meta.MetaProvider.PhaseMetaProvider;
import com.tmser.tr.manage.meta.MetaProvider.PhaseSubjectMetaProvider;
import com.tmser.tr.manage.meta.MetaProvider.PublisherMetaProvider;
import com.tmser.tr.manage.meta.bo.MetaRelationship;
import com.tmser.tr.utils.SpringContextHolder;

/**
 * <pre>
 * 元数据提供工具类
 * </pre>
 *
 * @author tmser
 * @version $Id: MetaUtils.java, v 1.0 2016年9月30日 下午3:44:47 tmser Exp $
 */
public abstract class MetaUtils {

  private static PhaseMetaProvider phaseMetaProvider;

  private static PhaseSubjectMetaProvider phaseSubjectMetaProvider;

  private static PhaseGradeMetaProvider phaseGradeMetaProvider;

  private static OrgTypeMetaProvider orgTypeMetaProvider;

  private static PublisherMetaProvider publisherMetaProvider;

  public static final PhaseMetaProvider getPhaseMetaProvider() {
    if (phaseMetaProvider == null) {
      phaseMetaProvider = SpringContextHolder.getBean("phaseMetaProvider");
    }
    return phaseMetaProvider;
  }

  public static final PhaseSubjectMetaProvider getPhaseSubjectMetaProvider() {
    if (phaseSubjectMetaProvider == null) {
      phaseSubjectMetaProvider = SpringContextHolder.getBean("phaseSubjectMetaProvider");
    }
    return phaseSubjectMetaProvider;
  }

  public static final PhaseGradeMetaProvider getPhaseGradeMetaProvider() {
    if (phaseGradeMetaProvider == null) {
      phaseGradeMetaProvider = SpringContextHolder.getBean("phaseGradeMetaProvider");
    }
    return phaseGradeMetaProvider;
  }

  public static final OrgTypeMetaProvider getOrgTypeMetaProvider() {
    if (orgTypeMetaProvider == null) {
      orgTypeMetaProvider = SpringContextHolder.getBean("orgTypeMetaProvider");
    }
    return orgTypeMetaProvider;
  }

  /**
   * 根据id获取元数据
   * 
   * @param metaId
   * @return
   */
  public static final Meta getMeta(Integer metaId) {
    return getPhaseMetaProvider().getMeta(metaId);
  }

  public static final List<Meta> getMetaByPid(Integer pid) {
    return getPhaseMetaProvider().getMetaByPid(pid);
  }

  /**
   * 根据id获取元数据关系实体
   * 
   * @param metaId
   * @return
   */
  public static final MetaRelationship getMetaRelation(Integer relationId) {
    return getPhaseMetaProvider().getMetaRelationship(relationId);
  }

  public static final PublisherMetaProvider getPublisherMetaProvider() {
    if (publisherMetaProvider == null) {
      publisherMetaProvider = SpringContextHolder.getBean("publisherMetaProvider");
    }
    return publisherMetaProvider;
  }

}
