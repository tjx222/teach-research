/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.manage.meta;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.CacheManager;

import com.alibaba.druid.support.json.JSONUtils;
import com.tmser.tr.manage.meta.bo.MetaRelationship;
import com.tmser.tr.manage.meta.bo.PublishRelationship;
import com.tmser.tr.manage.meta.bo.SysDic;
import com.tmser.tr.manage.meta.dao.MetaRelationshipDao;
import com.tmser.tr.manage.meta.dao.PublishRelationshipDao;
import com.tmser.tr.manage.meta.dao.SysDicDao;
import com.tmser.tr.manage.org.dao.AreaDao;
import com.tmser.tr.manage.org.dao.OrganizationDao;
import com.tmser.tr.utils.StringUtils;

/**
 * <pre>
 * 默认基础数据类型服务
 * </pre>
 *
 * @author tmser
 * @version $Id: MetaProvider.java, v 1.0 2016年9月30日 上午9:46:02 tmser Exp $
 */
public abstract class DefaultMetaProvider implements MetaProvider {

  @Autowired
  protected MetaRelationshipDao metaRelationshipDao;

  @Autowired
  private SysDicDao sysDicDao;
  @Autowired
  private PublishRelationshipDao publishRelationshipDao;
  @Autowired
  protected OrganizationDao orgDao;
  @Autowired
  protected AreaDao areaDao;

  @Resource(name = "cacheManger")
  protected CacheManager cacheManager;

  protected Cache metaCache;

  @PostConstruct
  public void init() {
    metaCache = cacheManager.getCache(MetaConstants.META_CACHE_NAME);
  }

  protected Cache getCache() {
    return metaCache;
  }

  @Override
  public void evictCache(Integer metashipId) {
    Cache cache = null;
    String key = getCacheName(metashipId);
    if (key != null && (cache = getCache()) != null) {
      cache.evict(key);
    }
  }

  protected abstract String getCacheName(Integer metashipId);

  /**
   * @param metaId
   * @return
   * @see com.tmser.tr.manage.meta.service.MetaProvider#getMeta(java.lang.Integer)
   */
  @Override
  public Meta getMeta(Integer metaId) {
    return sysDicDao.get(metaId);
  }

  @SuppressWarnings("unchecked")
  protected List<Meta> listMetaByType(int type) {
    Cache cache = null;
    if ((cache = getCache()) != null) {
      ValueWrapper cacheElement = cache.get(MetaConstants.DIC_META_CACHE_NAME + type);
      if (cacheElement != null) {
        return (List<Meta>) cacheElement.get();
      }
    }

    SysDic model = new SysDic();
    model.setParentId(type);
    model.addOrder("dicOrderby asc");
    model.setScope("sys");
    List<Meta> metaList = new ArrayList<>();
    metaList.addAll(sysDicDao.listAll(model));
    if ((cache = getCache()) != null) {
      cache.put(MetaConstants.DIC_META_CACHE_NAME + type, metaList);
    }

    return metaList;
  }

  protected List<Meta> listMetaByTypeWithScope(Integer type, String scope, Object scopeId) {
    SysDic model = new SysDic();
    model.setParentId(type);
    if ("org".equals(scope)) {
      model.setOrgId((Integer) scopeId);

    } else if ("area".equals(scope)) {
      model.setAreaId((Integer) scopeId);
    }
    model.setDicStatus(SysDic.DICSTATUS_ACTIVE);
    model.addOrder("dicOrderby asc");
    List<Meta> metaList = new ArrayList<>();
    metaList.addAll(sysDicDao.listAll(model));
    return metaList;
  }

  @SuppressWarnings("unchecked")
  protected List<Meta> listGradeMeta(int type) {
    Cache cache = null;
    if ((cache = getCache()) != null) {
      ValueWrapper cacheElement = cache.get(MetaConstants.META_GRADE_META_CACHE_NAME + type);
      if (cacheElement != null) {
        return (List<Meta>) cacheElement.get();
      }
    }

    SysDic model = new SysDic();
    model.addAlias("s");
    model.setDicLevel(3);
    model.addOrder("s.dicOrderby asc");
    model.buildCondition(" and s.cascadeDicIds like :type").put("type", type + "_%");
    List<Meta> metaList = new ArrayList<>();
    metaList.addAll(sysDicDao.listAll(model));
    if ((cache = getCache()) != null) {
      cache.put(MetaConstants.META_GRADE_META_CACHE_NAME + type, metaList);
    }

    return metaList;
  }

  /**
   * @param metaId
   * @return
   * @see com.tmser.tr.manage.meta.service.MetaProvider#getMeta(java.lang.Integer)
   */
  @Override
  public MetaRelationship getMetaRelationshipByPhaseId(Integer phaseId) {
    MetaRelationship model = new MetaRelationship();
    model.setType(getMetaRelationType());
    model.setEid(phaseId);
    return metaRelationshipDao.getOne(model);
  }

  /**
   * 获取所有基础数据
   * 
   * @return
   */
  @Override
  public List<MetaRelationship> listAll() {
    MetaRelationship model = new MetaRelationship();
    model.setType(getMetaRelationType());
    model.addOrder("sort asc");
    return metaRelationshipDao.listAll(model);
  }

  /**
   * 按id获取原数据
   * 
   * @param id
   * @return
   */
  @Override
  public MetaRelationship getMetaRelationship(Integer metashipId) {
    return metaRelationshipDao.get(metashipId);
  }

  protected abstract Integer getMetaRelationType();

  /**
   * 获取出版社关系数据
   * 
   * @param publishipId
   * @return
   */
  protected PublishRelationship getPublishRelationship(Integer publishipId) {
    return publishRelationshipDao.get(publishipId);
  }

  /**
   * 获取出版社关系列表
   * 
   * @param model
   * @return
   */
  protected List<PublishRelationship> getPublishRelationshipList(PublishRelationship model) {
    return publishRelationshipDao.listAll(model);
  }

  @Override
  public List<Meta> getMetaByIds(Collection<Integer> ids) {
    List<Meta> list = new ArrayList<>();
    list.addAll(sysDicDao.listByIds(ids));
    return list;
  }

  @Override
  public List<Meta> getMetaByPid(Integer pid) {
    List<Meta> list = new ArrayList<>();
    SysDic model = new SysDic();
    model.setParentId(pid);
    model.setScope("sys");
    list.addAll(sysDicDao.listAll(model));
    return list;
  }

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
  public static class DefaultPhaseMetaProvider extends DefaultMetaProvider implements PhaseMetaProvider {

    /**
     * @return
     * @see com.tmser.tr.manage.meta.service.impl.DefaultMetaProvider#getMetaRelationType()
     */
    @Override
    protected Integer getMetaRelationType() {
      return MetaRelationship.T_XD;
    }

    /**
     * @return
     * @see com.tmser.tr.manage.meta.MetaProvider.PhaseMetaProvider#listAllPhaseMeta()
     */
    @Override
    public List<Meta> listAllPhaseMeta() {
      return listMetaByType(MetaConstants.PHASE_METADATA_ID);
    }

    @Override
    protected String getCacheName(Integer metashipId) {
      return null;
    }

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
  public static class DefaultPhaseSubjectMetaProvider extends DefaultMetaProvider implements PhaseSubjectMetaProvider {

    /**
     * @param metaId
     * @return
     * @see com.tmser.tr.manage.meta.service.MetaProvider.PhaseSubjectMetaProvider#listAllSubject(java.lang.Integer)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Meta> listAllSubject(Integer metashipId) {
      Cache cache = null;
      if ((cache = getCache()) != null) {
        ValueWrapper cacheElement = cache.get(MetaConstants.META_SUBJECT_CACHE_NAME + metashipId);
        if (cacheElement != null) {
          return (List<Meta>) cacheElement.get();
        }
      }

      MetaRelationship mr = super.getMetaRelationship(metashipId);
      if (mr != null) {
        List<Meta> metaList = new ArrayList<>();
        String[] metaIds = StringUtils.split(mr.getIds(), StringUtils.COMMA);
        for (String metaId : metaIds) {
          Meta meta = super.getMeta(Integer.valueOf(metaId));
          if (meta != null)
            metaList.add(meta);
        }

        if (cache != null) {
          cache.put(MetaConstants.META_SUBJECT_CACHE_NAME + metashipId, metaList);
        }

        return metaList;
      }
      return null;
    }

    @Override
    protected String getCacheName(Integer metashipId) {
      return MetaConstants.META_SUBJECT_CACHE_NAME + metashipId;
    }

    /**
     * @return
     * @see com.tmser.tr.manage.meta.service.impl.DefaultMetaProvider#getMetaRelationType()
     */
    @Override
    protected Integer getMetaRelationType() {
      return MetaRelationship.T_XD_XK;
    }

    /**
     * @param phaseId
     * @return
     * @see com.tmser.tr.manage.meta.MetaProvider.PhaseSubjectMetaProvider#listAllSubjectByPhaseId(java.lang.Integer)
     */
    @Override
    public List<Meta> listAllSubjectByPhaseId(Integer phaseId) {
      MetaRelationship mr = getMetaRelationshipByPhaseId(phaseId);
      return mr == null ? null : listAllSubject(mr.getId());
    }

    /**
     * @return
     * @see com.tmser.tr.manage.meta.MetaProvider.PhaseSubjectMetaProvider#listAllSubjectMeta()
     */
    @Override
    public List<Meta> listAllSubjectMeta() {
      return listMetaByType(MetaConstants.SUBJECT_METADATA_ID);
    }

    @Override
    public List<Meta> listAllSubject(Integer orgId, Integer phaseId, Integer[] areaIds) {
      MetaRelationship orgSubjectShip = getMetaRelationshipByOrg(orgId, phaseId);
      List<Meta> listAllSubjectByArea = listAllSubjectByArea(phaseId, areaIds);
      if (orgId == null) {
        return listAllSubjectByArea;
      }
      List<Meta> allConfigSubject = new ArrayList<>();
      if (orgSubjectShip == null || StringUtils.isEmpty(orgSubjectShip.getIds())) {
        // 如果学校没有配置学科，则获取区域学科
        allConfigSubject = listAllSubjectByArea;
      } else {
        // 有则获取学校配置的学科
        allConfigSubject = listAllSubject(orgSubjectShip.getId());
        allConfigSubject.addAll(listAllSubjectByArea);
      }
      // 删除学校删除的学科
      Set<String> deleteSubject = new HashSet<>();
      List<Meta> listMetaDeleteByOrg = listMetaDeleteByOrg(orgId, phaseId);
      if (listMetaDeleteByOrg != null && listMetaDeleteByOrg.size() > 0) {
        for (Meta meta : listMetaDeleteByOrg) {
          deleteSubject.add(meta.getId().toString());
        }
      }

      List<Meta> allSubject = new ArrayList<>();
      for (Meta meta : allConfigSubject) {
        if (!deleteSubject.contains(meta.getId().toString())) {
          allSubject.add(meta);
          deleteSubject.add(meta.getId().toString());
        }
      }
      return allSubject;
    }

    public List<Meta> listAllSubjectByArea(Integer phaseId, Integer[] areaIds) {
      List<Meta> allConfigSubject = new ArrayList<>();
      // 先获取各个区域配置的学科
      for (int i = 0; i < areaIds.length; i++) {
        MetaRelationship areaSubjectConfig = areaSubjectConfig(phaseId, areaIds[i]);
        if (areaSubjectConfig != null) {
          allConfigSubject.addAll(listAllSubject(areaSubjectConfig.getId()));
        }
      }
      List<Meta> listAllSubjectByPhaseId = listAllSubjectByPhaseId(phaseId);
      if (listAllSubjectByPhaseId != null && listAllSubjectByPhaseId.size() > 0) {
        allConfigSubject.addAll(listAllSubjectByPhaseId);
      }

      // 删除区域删除的学科
      Set<String> deleteSubject = new HashSet<>();
      for (int i = 0; i < areaIds.length; i++) {
        List<MetaRelationship> listMetaDeleteByArea = listMetaDeleteByArea(areaIds[i], phaseId);
        for (MetaRelationship meta : listMetaDeleteByArea) {
          deleteSubject.add(meta.getIds());
        }
      }
      List<Meta> allSubject = new ArrayList<>();
      for (Meta meta : allConfigSubject) {
        if (!deleteSubject.contains(meta.getId().toString())) {
          allSubject.add(meta);
          deleteSubject.add(meta.getId().toString());
        }
      }

      return allSubject;
    }

    /**
     * 获取区域删除的学科
     * 
     * @param areaId
     *          区域id
     * @param phaseId
     *          学段id 为null时查询所有删除的学科
     * 
     * @return List
     */
    protected List<MetaRelationship> listMetaDeleteByArea(Integer areaId, Integer phaseId) {
      MetaRelationship model = new MetaRelationship();
      model.setAreaId(areaId);
      if (phaseId == null) {
        model.addCustomCondition(" and phase is null");
      } else {
        model.setEid(phaseId);
      }
      model.setType(getMetaRelationType());
      return metaRelationshipDao.listAll(model);
    }

    // 找出学校删除的学科
    protected List<Meta> listMetaDeleteByOrg(Integer orgId, Integer phaseId) {
      MetaRelationship model = new MetaRelationship();
      model.setOrgId(orgId);
      if (phaseId == null) {
        model.addCustomCondition(" and phase is null");
      } else {
        model.setEid(phaseId);
      }
      model.setType(getMetaRelationType());
      model.setEnable(false);
      List<MetaRelationship> listAll = metaRelationshipDao.listAll(model);
      Set<Integer> dicIds = new HashSet<>();
      for (MetaRelationship metaRelationship : listAll) {
        dicIds.add(Integer.valueOf(metaRelationship.getIds()));
      }
      List<Meta> metaList = new ArrayList<>();
      if (!dicIds.isEmpty()) {
        metaList.addAll(super.getMetaByIds(dicIds));
      }
      return metaList;
    }

    protected MetaRelationship areaSubjectConfig(Integer phaseId, Integer areaId) {
      MetaRelationship model = new MetaRelationship();
      model.setAreaId(areaId);
      model.setEid(phaseId);
      model.setType(getMetaRelationType());
      // model.setEnable(true);
      return metaRelationshipDao.getOne(model);
    }

    @Override
    public MetaRelationship getMetaRelationshipByOrg(Integer orgId, Integer phaseId) {
      MetaRelationship config = new MetaRelationship();
      config.setOrgId(orgId);
      config.setEid(phaseId);
      config.setType(getMetaRelationType());
      // config.setEnable(true);
      return metaRelationshipDao.getOne(config);
    }

    @Override
    public MetaRelationship getMetaRelationshipByArea(Integer areaId, Integer phaseId) {
      MetaRelationship config = new MetaRelationship();
      config.setAreaId(areaId);
      config.setEid(phaseId);
      config.setType(getMetaRelationType());
      return metaRelationshipDao.getOne(config);
    }

    @Override
    public List<Meta> listAllMeta(Integer orgId, Integer[] areaIds) {
      List<Meta> configs = new ArrayList<>();
      // 系统学科元数据
      configs.addAll(listMetaByType(MetaConstants.SUBJECT_METADATA_ID));
      // 区域学科元数据
      List<Meta> metasArea = listAllMetaByArea(areaIds);
      configs.addAll(metasArea);
      if (orgId == null) {
        return configs;
      }
      // 获取学校自定义学科
      List<Meta> orgMetas = listMetaByTypeWithScope(MetaConstants.SUBJECT_METADATA_ID, "org", orgId);
      configs.addAll(orgMetas);
      return configs;

    }

    // 根据区域ids获取学科元数据
    protected List<Meta> listAllMetaByArea(Integer[] areaIds) {
      List<Meta> subjects = new ArrayList<>();
      for (int i = 0; i < areaIds.length; i++) {
        // 获取区域自定义学科
        List<Meta> listMetaByArea = listMetaByTypeWithScope(MetaConstants.SUBJECT_METADATA_ID, "area", areaIds[i]);
        if (listMetaByArea != null && listMetaByArea.size() > 0) {
          subjects.addAll(listMetaByArea);
        }
      }
      return subjects;
    }

  }

  /**
   * 学段年级原数据服务
   * 
   * <pre>
   * 提供当前平台支持的所有年级学科相关服务
   * </pre>
   *
   * @author tmser
   * @version $Id: MetaProvider.java, v 1.0 2016年9月30日 上午9:47:28 3020mt Exp $
   */
  public static class DefaultPhaseGradeMetaProvider extends DefaultMetaProvider implements PhaseGradeMetaProvider {

    /**
     * @param metaId
     * @return
     * @see com.tmser.tr.manage.meta.service.MetaProvider.PhaseGradeMetaProvider#listAllGrade(java.lang.Integer)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Meta> listAllGrade(Integer metashipId) {
      Cache cache = null;
      if ((cache = getCache()) != null) {
        ValueWrapper cacheElement = cache.get(MetaConstants.META_GRADE_CACHE_NAME + metashipId);
        if (cacheElement != null) {
          return (List<Meta>) cacheElement.get();
        }
      }

      MetaRelationship mr = super.getMetaRelationship(metashipId);
      if (mr != null) {
        List<Meta> metaList = new ArrayList<>();
        String[] metaIds = StringUtils.split(mr.getIds(), StringUtils.COMMA);
        for (String metaId : metaIds) {
          Meta meta = super.getMeta(Integer.valueOf(metaId));
          if (meta != null)
            metaList.add(meta);
        }

        if (cache != null) {
          cache.put(MetaConstants.META_GRADE_CACHE_NAME + metashipId, metaList);
        }

        return metaList;
      }
      return null;
    }

    @Override
    protected String getCacheName(Integer metashipId) {
      return MetaConstants.META_GRADE_CACHE_NAME + metashipId;
    }

    /**
     * @return
     * @see com.tmser.tr.manage.meta.service.impl.DefaultMetaProvider#getMetaRelationType()
     */
    @Override
    protected Integer getMetaRelationType() {
      return MetaRelationship.T_XD_NJ;
    }

    /**
     * @param phaseId
     * @return
     * @see com.tmser.tr.manage.meta.MetaProvider.PhaseGradeMetaProvider#listAllGradeByPhaseId(java.lang.Integer)
     */
    @Override
    public List<Meta> listAllGradeByPhaseId(Integer phaseId) {
      MetaRelationship mr = getMetaRelationshipByPhaseId(phaseId);
      return mr == null ? null : listAllGrade(mr.getId());
    }

    /**
     * @return
     * @see com.tmser.tr.manage.meta.MetaProvider.PhaseGradeMetaProvider#listAllGradeMeta()
     */
    @Override
    public List<Meta> listAllGradeMeta() {
      return listGradeMeta(MetaConstants.PHASE_METADATA_ID);
    }

  }

  /**
   * 学校类型原数据服务
   * 
   * <pre>
   * 提供当前平台支持的所有学校类型相关服务
   * </pre>
   *
   * @author tmser
   * @version $Id: MetaProvider.java, v 1.0 2016年9月30日 上午9:47:28 3020mt Exp $
   */
  public static class DefaultOrgTypeMetaProvider extends DefaultMetaProvider implements OrgTypeMetaProvider {

    /**
     * 根据学校类型及学段id获取，包含的年级列表
     * 
     * @param metaId
     *          学校类型id
     * @param phaseId
     *          学段id
     * @return
     */
    @Override
    public List<Meta> listAllGrade(Integer metaId, Integer phaseId) {
      return listPhaseGradeMap(metaId).get(phaseId);
    }

    /**
     * 获取根据学校类型id获取学段年级关系
     * 
     * @param metaId
     *          学校类型id
     * @return Map<phaseId, grade list>
     */
    @Override
    @SuppressWarnings("unchecked")
    public Map<Integer, List<Meta>> listPhaseGradeMap(Integer metashipId) {
      Cache cache = null;
      if ((cache = getCache()) != null) {
        ValueWrapper cacheElement = cache.get(MetaConstants.META_ORGTYPE_CACHE_NAME + metashipId);
        if (cacheElement != null) {
          return (Map<Integer, List<Meta>>) cacheElement.get();
        }
      }

      MetaRelationship mr = super.getMetaRelationship(metashipId);

      if (mr != null) {
        Map<Integer, List<Meta>> map = new HashMap<>();
        if (StringUtils.isNotBlank(mr.getIds())) {
          List<Object> rs = (List<Object>) JSONUtils.parse(mr.getIds());
          for (Object obj : rs) {
            if (obj instanceof Map) {
              Map<String, List<Integer>> phaseInfos = (Map<String, List<Integer>>) obj;
              for (String k : phaseInfos.keySet()) {
                List<Meta> metaList = new ArrayList<>();
                for (Integer metaId : phaseInfos.get(k)) {
                  Meta meta = super.getMeta(metaId);
                  if (meta != null)
                    metaList.add(meta);
                }

                map.put(Integer.valueOf(k), metaList);
              }
            }
          }
        }

        if (cache != null) {
          cache.put(MetaConstants.META_ORGTYPE_CACHE_NAME + metashipId, map);
        }

        return map;
      }

      return Collections.emptyMap();
    }

    @Override
    protected String getCacheName(Integer metashipId) {
      return MetaConstants.META_ORGTYPE_CACHE_NAME + metashipId;
    }

    /**
     * @return
     * @see com.tmser.tr.manage.meta.service.impl.DefaultMetaProvider#getMetaRelationType()
     */
    @Override
    protected Integer getMetaRelationType() {
      return MetaRelationship.T_ORG_TYPE;
    }

    /**
     * @param metashipId
     * @return
     * @see com.tmser.tr.manage.meta.MetaProvider.OrgTypeMetaProvider#listAllPhase(java.lang.Integer)
     */
    @Override
    public List<Meta> listAllPhaseMeta(Integer metashipId) {
      Map<Integer, List<Meta>> ms = listPhaseGradeMap(metashipId);
      List<Meta> metaList = new ArrayList<>();
      for (Integer key : ms.keySet()) {
        MetaRelationship mr = getMetaRelationship(key);
        metaList.add(getMeta(mr.getEid()));
      }
      return metaList;
    }

    /**
     * @param metashipId
     * @return
     * @see com.tmser.tr.manage.meta.MetaProvider.OrgTypeMetaProvider#listAllPhaseMeta(java.lang.Integer)
     */
    @Override
    public List<MetaRelationship> listAllPhase(Integer metashipId) {
      Map<Integer, List<Meta>> ms = listPhaseGradeMap(metashipId);
      List<MetaRelationship> metaList = new ArrayList<>();
      for (Integer key : ms.keySet()) {
        MetaRelationship mr = getMetaRelationship(key);
        metaList.add(mr);
      }
      return metaList;
    }

    /**
     * @param metashipId
     * @return
     * @see com.tmser.tr.manage.meta.MetaProvider.OrgTypeMetaProvider#listPhaseGradeCountMap(java.lang.Integer)
     */
    @Override
    public Map<Integer, Integer> listPhaseGradeCountMap(Integer metashipId) {

      Map<Integer, List<Meta>> phaseGrades = listPhaseGradeMap(metashipId);
      Map<Integer, Integer> gradeCountMap = new HashMap<>();
      for (Integer phase : phaseGrades.keySet()) {
        if (getMetaRelationship(phase).getEid() == 142) {
          gradeCountMap.put(phase, 3);
        } else {
          gradeCountMap.put(phase, phaseGrades.get(phase).size());
        }
      }

      return gradeCountMap;
    }
  }

  public static class DefaultPublisherMetaProvider extends DefaultMetaProvider implements PublisherMetaProvider {
    /**
     * @return
     * @see com.tmser.tr.manage.meta.DefaultMetaProvider#getMetaRelationType()
     */
    @Override
    protected Integer getMetaRelationType() {
      throw new RuntimeException("not supports.");
    }

    /**
     * @return
     * @see com.tmser.tr.manage.meta.MetaProvider.PublisherMetaProvider#listAllPublisherMeta()
     */
    @Override
    public List<Meta> listAllPublisherMeta() {
      return listMetaByType(MetaConstants.PUBLISHER_METADATA_ID);
    }

    /**
     * @param publishipId
     * @return
     * @see com.tmser.tr.manage.meta.MetaProvider.PublisherMetaProvider#getPublishRelationship(java.lang.Integer)
     */
    @Override
    public PublishRelationship getPublishRelationshipById(Integer publishipId) {
      return getPublishRelationship(publishipId);
    }

    /**
     * @param model
     * @return
     * @see com.tmser.tr.manage.meta.MetaProvider.PublisherMetaProvider#findList(com.tmser.tr.manage.meta.bo.PublishRelationship)
     */
    @Override
    public List<PublishRelationship> findList(PublishRelationship model) {
      return getPublishRelationshipList(model);
    }

    @Override
    protected String getCacheName(Integer metashipId) {
      throw null;
    }

    private List<Meta> listAllPublisherMetaWithScope(String scope, Integer scopeId) {
      return listMetaByTypeWithScope(MetaConstants.PUBLISHER_METADATA_ID, scope, scopeId);
    }

    @Override
    public List<Meta> listAllPublisherMetaWithOrg(Integer orgId) {
      List<Meta> list = new ArrayList<Meta>();
      Integer areaId = orgDao.get(orgId).getAreaId();
      // 获取区域自定义出版社元数据时已经查询系统元数据，故此处不需要再查一遍
      list.addAll(listAllPublisherMetaWithArea(areaId));
      list.addAll(listAllPublisherMetaWithScope("org", orgId));

      return list;
    }

    @Override
    public List<Meta> listAllPublisherMetaWithArea(Integer areaId) {
      List<Meta> list = new ArrayList<Meta>();
      list.addAll(listAllPublisherMeta());
      List<Integer> areaIds = areaDao.getAreaIds(areaId);
      for (Integer id : areaIds) {
        list.addAll(listAllPublisherMetaWithScope("area", id));
      }
      return list;
    }

  }

}
