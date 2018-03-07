/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.manage.meta;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.tmser.tr.manage.meta.bo.MetaRelationship;
import com.tmser.tr.manage.meta.bo.PublishRelationship;

/**
 * <pre>
 * 基础数据类型服务
 * </pre>
 *
 * @author tmser
 * @version $Id: MetaProvider.java, v 1.0 2016年9月30日 上午9:46:02 tmser Exp $
 */
public interface MetaProvider {

  /**
   * 获取所有基础数据
   * 
   * @return
   */
  List<MetaRelationship> listAll();

  /**
   * 按关系id获取关系原数据
   * 
   * @param metashipId
   * @return
   */
  MetaRelationship getMetaRelationship(Integer metashipId);

  /**
   * 根据学段id获取关系原数据
   * 
   * @param phaseId
   * @return
   */
  MetaRelationship getMetaRelationshipByPhaseId(Integer phaseId);

  /**
   * 根据metaid基础数据
   * 
   * @return
   */
  Meta getMeta(Integer metaId);

  /**
   * 清除缓存，按关系id
   */
  void evictCache(Integer metashipId);

  List<Meta> getMetaByIds(Collection<Integer> ids);

  List<Meta> getMetaByPid(Integer pid);

  /**
   * 学段原数据服务
   * 
   * <pre>
   * 提供当前平台支持的所有学段相关服务
   * </pre>
   *
   * @author tmser
   * @version $Id: MetaProvider.java, v 1.0 2016年9月30日 上午9:47:28 tmser Exp $
   */
  interface PhaseMetaProvider extends MetaProvider {

    /**
     * 从DIC中获取所有学段元数据
     * 
     * @return
     */
    List<Meta> listAllPhaseMeta();
  }

  /**
   * 学段学科原数据服务
   * 
   * <pre>
   * 提供当前平台支持的所有学段学科相关服务
   * </pre>
   *
   * @author tmser
   * @version $Id: MetaProvider.java, v 1.0 2016年9月30日 上午9:47:28 3020mt Exp $
   */
  interface PhaseSubjectMetaProvider extends MetaProvider {
    /**
     * 获取学段下所有学科
     * 
     * @param metashipId
     * @return
     */
    List<Meta> listAllSubject(Integer metashipId);

    /**
     * 获取学段下所有学科
     * 
     * @param meta_phaseId
     * @return
     */
    List<Meta> listAllSubjectByPhaseId(Integer meta_phaseId);

    /**
     * 从DIC中获取所有学科元数据
     * 
     * @return
     */
    List<Meta> listAllSubjectMeta();

    /**
     * 获取配置的所有学科
     * 
     * @param orgId
     *          不为null时查询学校配置的学科 为null时查询区域配置的学科
     * @param phaseId
     *          学段id
     * @param areaId
     *          区域id
     * @return List
     */
    List<Meta> listAllSubject(Integer orgId, Integer phaseId, Integer[] areaIds);

    MetaRelationship getMetaRelationshipByOrg(Integer orgId, Integer phaseId);

    MetaRelationship getMetaRelationshipByArea(Integer areaId, Integer phaseId);

    /**
     * @param orgId
     * @param areaIds
     * @return
     */
    List<Meta> listAllMeta(Integer orgId, Integer[] areaIds);

  }

  /**
   * 学段年级原数据服务
   * 
   * <pre>
   * 提供当前平台支持的所有学段学科相关服务
   * </pre>
   *
   * @author tmser
   * @version $Id: MetaProvider.java, v 1.0 2016年9月30日 上午9:47:28 3020mt Exp $
   */
  interface PhaseGradeMetaProvider extends MetaProvider {
    /**
     * 获取学段下所有年级
     * 
     * @param metashipId
     * @return
     */
    List<Meta> listAllGrade(Integer metashipId);

    /**
     * 从DIC中获取所有学科元数据
     * 
     * @return
     */
    List<Meta> listAllGradeMeta();

    /**
     * 通过学段id 获取学段下所有年级
     * 
     * @param phaseId
     * @return
     */
    List<Meta> listAllGradeByPhaseId(Integer phaseId);

  }

  /**
   * 学校类型原数据服务
   * 
   * <pre>
   * 提供当前平台支持的所有学段年级相关服务
   * </pre>
   *
   * @author tmser
   * @version $Id: MetaProvider.java, v 1.0 2016年9月30日 上午9:47:28 3020mt Exp $
   */
  interface OrgTypeMetaProvider extends MetaProvider {

    /**
     * 根据学校类型及学段id获取，包含的年级列表
     * 
     * @param metashipId
     *          学校类型id
     * @param phaseId
     *          学段id
     * @return
     */
    List<Meta> listAllGrade(Integer metashipId, Integer phaseId);

    /**
     * 根据学校类型及学段id获取，包含的学段列表
     * 
     * @param phaseId
     *          学段id
     * @return
     */
    List<Meta> listAllPhaseMeta(Integer metashipId);

    /**
     * 根据学校类型及学段id获取，包含的学段列表
     * 
     * @param phaseId
     *          学段id
     * @return
     */
    List<MetaRelationship> listAllPhase(Integer metashipId);

    /**
     * 获取学校类型包含的<学段-年级列表>
     * 
     * @param metashipId
     *          学校类型id
     * @param phaseId
     *          学段id
     * @return Map<phaseId, grade list>
     */
    Map<Integer, List<Meta>> listPhaseGradeMap(Integer metashipId);

    /**
     * 获取学校类型包含的<学段-实际年级数>
     * 
     * @param metashipId
     *          学校类型id
     * @param phaseId
     *          学段id
     * @return Map<phaseId, grade count>
     */
    Map<Integer, Integer> listPhaseGradeCountMap(Integer metashipId);

  }

  /**
   * 出版社元数据服务
   * 
   * <pre>
   *
   * </pre>
   *
   * @author ghw
   * @version $Id: MetaProvider.java, v 1.0 2016年10月9日 下午1:53:38 3020mt Exp $
   */
  interface PublisherMetaProvider extends MetaProvider {
    /**
     * 获取所有出版社元数据信息
     * 
     * @return
     */
    List<Meta> listAllPublisherMeta();

    List<Meta> listAllPublisherMetaWithOrg(Integer orgId);

    List<Meta> listAllPublisherMetaWithArea(Integer areaId);

    /**
     * 获取出版社信息
     * 
     * @param publishipId
     * @return
     */
    PublishRelationship getPublishRelationshipById(Integer publishipId);

    /**
     * 获取出版社关系信息
     * 
     * @param model
     * @return
     */
    List<PublishRelationship> findList(PublishRelationship model);

  }
}
