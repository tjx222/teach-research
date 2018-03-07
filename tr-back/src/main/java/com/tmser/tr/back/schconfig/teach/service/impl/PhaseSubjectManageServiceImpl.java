package com.tmser.tr.back.schconfig.teach.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import net.sf.ehcache.Cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmser.tr.back.jxtx.service.JXTXSubjectManageService;
import com.tmser.tr.back.schconfig.teach.service.PhaseSubjectManageService;
import com.tmser.tr.common.utils.WebThreadLocalUtils;
import com.tmser.tr.manage.meta.Meta;
import com.tmser.tr.manage.meta.MetaConstants;
import com.tmser.tr.manage.meta.MetaUtils;
import com.tmser.tr.manage.meta.bo.MetaRelationship;
import com.tmser.tr.manage.meta.bo.SysDic;
import com.tmser.tr.manage.meta.dao.SysDicDao;
import com.tmser.tr.manage.org.bo.Organization;
import com.tmser.tr.manage.org.service.AreaService;
import com.tmser.tr.manage.org.service.OrganizationService;
import com.tmser.tr.uc.bo.User;
import com.tmser.tr.uc.utils.SessionKey;
import com.tmser.tr.utils.StringUtils;

@Service
@Transactional
public class PhaseSubjectManageServiceImpl implements PhaseSubjectManageService {
  /**
   * <pre>
   *
   * </pre>
   */
  private static final String COMMA = ",";
  @Autowired
  private OrganizationService orgService;
  @Autowired
  private JXTXSubjectManageService sysConfigService;
  @Autowired
  private AreaService areaService;
  @Autowired
  private SysDicDao sysDicDao;
  @Resource(name = "cacheManger")
  private CacheManager cacheManager;

  @Override
  public List<Meta> getUnAddList(String orgId, Integer phaseId) {
    List<Meta> unAddedList = new ArrayList<>();
    Organization org = orgService.findOne(Integer.valueOf(orgId));
    String areaIds = org.getAreaIds();
    List<Meta> subjectList = MetaUtils.getPhaseSubjectMetaProvider().listAllSubject(Integer.valueOf(orgId), phaseId,
        StringUtils.toIntegerArray(areaIds.substring(1, areaIds.lastIndexOf(COMMA)), COMMA));

    List<Meta> phaseSubjectList = MetaUtils.getPhaseSubjectMetaProvider().listAllMeta(Integer.valueOf(orgId),
        StringUtils.toIntegerArray(areaIds.substring(1, areaIds.lastIndexOf(COMMA)), COMMA));
    Set<Integer> subjects = new HashSet<>();
    if (subjectList != null && subjectList.size() > 0) {
      for (Meta model : subjectList) {
        subjects.add(model.getId());
      }
    }
    for (Meta model : phaseSubjectList) {
      if (!subjects.contains(model.getId())) {
        unAddedList.add(model);
      }
    }
    return unAddedList;
  }

  @Override
  public List<Meta> getUnAddList(Integer areaId, Integer phaseId) {
    List<Meta> unAddedList = new ArrayList<>();
    // 获取区域下配置的学科
    List<Integer> areaIds = areaService.getAreaIds(areaId);
    Integer[] areaArray = new Integer[areaIds.size()];
    Integer[] array = areaIds.toArray(areaArray);
    List<Meta> subjectList = MetaUtils.getPhaseSubjectMetaProvider().listAllSubject(null, phaseId, array);
    // 获取所有学科
    List<Meta> phaseSubjectList = MetaUtils.getPhaseSubjectMetaProvider().listAllMeta(null, array);
    Set<Integer> subjects = new HashSet<>();
    if (subjectList != null && subjectList.size() > 0) {
      for (Meta model : subjectList) {
        subjects.add(model.getId());
      }
    }
    for (Meta model : phaseSubjectList) {
      if (!subjects.contains(model.getId())) {
        unAddedList.add(model);
      }
    }
    return unAddedList;
  }

  @Override
  public Meta get(String orgId, String name) {
    SysDic model = new SysDic();
    model.setName(name);
    model.setOrgId(Integer.valueOf(orgId));
    Meta meta = sysDicDao.getOne(model);
    if (meta == null) {
      meta = get(orgService.findOne(Integer.valueOf(orgId)).getAreaId(), name);
    }
    return meta;
  }

  @Override
  public Meta get(Integer areaId, String name) {
    SysDic model = new SysDic();
    model.setName(name);
    model.setAreaId(areaId);
    Meta meta = sysDicDao.getOne(model);
    if (meta == null) {
      SysDic model1 = new SysDic();
      model1.setName(name);
      meta = sysDicDao.getOne(model1);
    }
    return meta;
  }

  @Override
  public void saveCustom(Integer phaseId, Integer areaId, String name, String descs) {
    SysDic model = new SysDic();
    model.setName(name);
    model.setAreaId(areaId);
    model.setDicStatus(SysDic.DICSTATUS_ACTIVE);
    model.setParentId(MetaConstants.SUBJECT_METADATA_ID);
    model.setDicLevel(2);
    model.setDicOrderby(0);
    model.setScope("area");
    model.setDicStatus(SysDic.DICSTATUS_ACTIVE);
    User user = (User) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_USER);
    model.setOperator(user.getName());
    model.setCascadeDicIds(MetaConstants.SUBJECT_METADATA_ID.toString());
    model.setChildCount(0);
    model = sysDicDao.insert(model);
    model.setCascadeDicIds(model.getCascadeDicIds().concat("_").concat(model.getId().toString()));
    Integer[] ids = new Integer[] { model.getId() };
    save(ids, phaseId, areaId);
  }

  @Override
  public void saveCustom(Integer phaseId, String orgId, String name, String descs) {
    SysDic model = new SysDic();
    model.setName(name);
    model.setOrgId(Integer.valueOf(orgId));
    model.setParentId(MetaConstants.SUBJECT_METADATA_ID);
    model.setScope("org");
    model.setDicLevel(2);
    model.setDicOrderby(0);
    model.setDicStatus(SysDic.DICSTATUS_ACTIVE);
    User user = (User) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_USER);
    model.setOperator(user.getName());
    model.setCascadeDicIds(MetaConstants.SUBJECT_METADATA_ID.toString());
    model.setChildCount(0);
    model = sysDicDao.insert(model);
    model.setCascadeDicIds(model.getCascadeDicIds().concat("_").concat(model.getId().toString()));
    sysDicDao.count(model);
    save(model.getId().toString(), phaseId, Integer.valueOf(orgId));
  }

  @Override
  public void save(String subjectIds, Integer phaseId, Integer orgId) {
    MetaRelationship configByOrg = MetaUtils.getPhaseSubjectMetaProvider().getMetaRelationshipByOrg(orgId, phaseId);
    String areaIds = orgService.findOne(orgId).getAreaIds();
    Integer[] ids = StringUtils.toIntegerArray(subjectIds, COMMA);
    Map<Integer, String> addSubjects = new HashMap<>();
    for (int i = 0; i < ids.length; i++) {
      addSubjects.put(ids[i], MetaUtils.getMeta(ids[i]).getName());
    }
    if (configByOrg == null) {
      MetaRelationship config = new MetaRelationship();
      config.setOrgId(orgId);
      config.setEid(phaseId);
      config.setType(MetaRelationship.T_XD_XK);
      List<Meta> orgSubjects = MetaUtils.getPhaseSubjectMetaProvider().listAllSubject(orgId, phaseId,
          StringUtils.toIntegerArray(areaIds.substring(1, areaIds.lastIndexOf(COMMA)), COMMA));
      StringBuilder value = new StringBuilder();
      StringBuilder descs = new StringBuilder();
      for (Meta sysConfig : orgSubjects) {
        value.append(sysConfig.getId().intValue() + COMMA);
        descs.append(sysConfig.getName() + COMMA);
      }
      for (Integer id : ids) {
        value.append(id.intValue() + COMMA);
        descs.append(MetaUtils.getPhaseSubjectMetaProvider().getMeta(id).getName() + COMMA);
      }
      config.setIds(value.length() > 0 ? value.substring(0, value.length() - 1) : "");
      config.setDescs(descs.length() > 0 ? descs.substring(0, descs.length() - 1) : "");
      configByOrg = sysConfigService.save(config);

    } else {
      StringBuilder value = new StringBuilder(configByOrg.getIds());
      StringBuilder descs = new StringBuilder(configByOrg.getDescs());
      MetaRelationship config = new MetaRelationship();
      config.setId(configByOrg.getId());
      for (Integer id : ids) {
        value.append(COMMA + id.intValue());
        descs.append(COMMA + MetaUtils.getPhaseSubjectMetaProvider().getMeta(id).getName());
      }
      if (value.length() > 0 && value.indexOf(COMMA) == 0) {
        config.setIds(value.length() > 0 ? value.substring(1, value.length()) : "");
        config.setDescs(descs.length() > 0 ? descs.substring(1, descs.length()) : "");
      } else {
        config.setIds(value.toString());
        config.setDescs(descs.toString());
      }
      configByOrg.setIds(config.getIds());
      configByOrg.setDescs(config.getDescs());
      sysConfigService.update(config);
    }
    clearMetaCache();
  }

  @Override
  public void save(Integer[] ids, Integer phaseId, Integer areaId) {
    MetaRelationship areaConfig = MetaUtils.getPhaseSubjectMetaProvider().getMetaRelationshipByArea(areaId, phaseId);
    Map<Integer, String> addSubjects = new HashMap<>();
    for (Integer id : ids) {
      addSubjects.put(id, MetaUtils.getMeta(id).getName());
    }
    if (areaConfig == null) {
      MetaRelationship config = new MetaRelationship();
      config.setAreaId(areaId);
      config.setEid(phaseId);
      config.setType(MetaRelationship.T_XD_XK);
      List<Integer> areaIds = areaService.getAreaIds(areaId);
      Integer[] areaArray = new Integer[areaIds.size()];
      Integer[] array = areaIds.toArray(areaArray);
      List<Meta> orgSubjects = MetaUtils.getPhaseSubjectMetaProvider().listAllSubject(null, phaseId, array);
      StringBuilder value = new StringBuilder();
      StringBuilder descs = new StringBuilder();
      for (Meta sysConfig : orgSubjects) {
        value.append(sysConfig.getId().intValue() + COMMA);
        descs.append(sysConfig.getName() + COMMA);
      }
      for (Integer id : ids) {
        value.append(id.intValue() + COMMA);
        descs.append(MetaUtils.getPhaseSubjectMetaProvider().getMeta(id).getName() + COMMA);
      }
      config.setIds(value.substring(0, value.length() - 1));
      config.setDescs(descs.substring(0, descs.length() - 1));
      areaConfig = sysConfigService.save(config);

    } else {
      StringBuilder value = new StringBuilder(areaConfig.getIds());
      StringBuilder descs = new StringBuilder(areaConfig.getDescs());
      MetaRelationship config = new MetaRelationship();
      config.setId(areaConfig.getId());
      for (Integer id : ids) {
        value.append(COMMA + id.intValue());
        descs.append(COMMA + MetaUtils.getPhaseSubjectMetaProvider().getMeta(id).getName());
      }

      if (value.length() > 0 && value.indexOf(COMMA) == 0) {
        config.setIds(value.length() > 0 ? value.substring(1) : "");
        config.setDescs(descs.length() > 0 ? descs.substring(1) : "");
      } else {
        config.setIds(value.toString());
        config.setDescs(descs.toString());
      }
      areaConfig.setIds(config.getIds());
      areaConfig.setDescs(config.getDescs());
      sysConfigService.update(config);
    }
    clearMetaCache();
  }

  private void clearMetaCache() {
    org.springframework.cache.Cache cache = cacheManager.getCache(MetaConstants.META_CACHE_NAME);
    if (cache != null) {
      ((Cache) cache.getNativeCache()).clearStatistics();
      cache.clear();
    }
  }

  @Override
  public void delete(Integer id, Integer phaseId, String orgId) {

    MetaRelationship configByOrg = MetaUtils.getPhaseSubjectMetaProvider().getMetaRelationshipByOrg(
        Integer.valueOf(orgId), phaseId);
    if (configByOrg != null) {
      String value = configByOrg.getIds();
      StringBuilder sbValue = new StringBuilder(COMMA);
      sbValue.append(value);
      sbValue.append(COMMA);
      String deleteID = COMMA + id.intValue();
      if (sbValue.indexOf(deleteID) != -1) {
        sbValue.delete(sbValue.indexOf(deleteID), sbValue.indexOf(deleteID) + deleteID.length());
        StringBuilder descs = new StringBuilder(COMMA);
        descs.append(configByOrg.getDescs());
        descs.append(COMMA);
        String deleteDesc = COMMA + MetaUtils.getPhaseSubjectMetaProvider().getMeta(id).getName();
        descs.delete(descs.indexOf(deleteDesc), descs.indexOf(deleteDesc) + deleteDesc.length());
        configByOrg.setIds(sbValue.substring(1, sbValue.length() - 1));
        configByOrg.setDescs(descs.substring(1, descs.length() - 1));
        MetaRelationship config = new MetaRelationship();
        config.setId(configByOrg.getId());
        config.setIds(configByOrg.getIds());
        config.setDescs(configByOrg.getDescs());

        sysConfigService.update(config);
      }
    } else {
      configByOrg = new MetaRelationship();
      configByOrg.setEid(phaseId);
      configByOrg.setOrgId(Integer.valueOf(orgId));
      String areaIds = orgService.findOne(Integer.valueOf(orgId)).getAreaIds();
      List<Meta> orgSubjects = MetaUtils.getPhaseSubjectMetaProvider().listAllSubject(Integer.valueOf(orgId), phaseId,
          StringUtils.toIntegerArray(areaIds.substring(1, areaIds.lastIndexOf(COMMA)), COMMA));
      StringBuilder value = new StringBuilder();
      StringBuilder descs = new StringBuilder();
      for (Meta sysConfig : orgSubjects) {
        value.append(sysConfig.getId().intValue() + COMMA);
        descs.append(sysConfig.getName() + COMMA);
      }
      configByOrg.setIds(value.length() > 0 ? value.substring(0, value.length() - 1) : "");
      configByOrg.setDescs(descs.length() > 0 ? descs.substring(0, descs.length() - 1) : "");
      configByOrg = sysConfigService.save(configByOrg);
    }
    clearMetaCache();
  }

  @Override
  public void delete(Integer id, Integer phaseId, Integer areaId) {
    MetaRelationship areaConfig = MetaUtils.getPhaseSubjectMetaProvider().getMetaRelationshipByArea(areaId, phaseId);
    if (areaConfig != null) {
      String value = areaConfig.getIds();
      StringBuilder sbValue = new StringBuilder(value);
      String deleteID = COMMA + id.intValue();
      int index = value.indexOf(deleteID);
      if (index <= 0) {
        sbValue.delete(0, deleteID.length());
      } else {
        sbValue.delete(index, index + deleteID.length());
      }
      StringBuilder descs = new StringBuilder(areaConfig.getDescs());
      String deleteDesc = COMMA + MetaUtils.getMeta(id).getName();
      int dindex = descs.indexOf(deleteDesc);
      if (dindex <= 0) {
        descs.delete(0, deleteDesc.length());
      } else {
        descs.delete(dindex, dindex + deleteDesc.length());

      }
      areaConfig.setIds(sbValue.toString());
      areaConfig.setDescs(descs.toString());

      MetaRelationship model = new MetaRelationship();
      model.setId(areaConfig.getId());
      model.setIds(areaConfig.getIds());
      model.setDescs(areaConfig.getDescs());

      sysConfigService.update(model);

    } else {
      areaConfig = new MetaRelationship();
      areaConfig.setEid(phaseId);
      areaConfig.setAreaId(areaId);
      List<Integer> areaIds = areaService.getAreaIds(areaId);
      Integer[] areaIdArr = new Integer[areaIds.size()];
      areaIds.toArray(areaIdArr);
      List<Meta> subjects = MetaUtils.getPhaseSubjectMetaProvider().listAllSubject(null, phaseId, areaIdArr);
      StringBuilder value = new StringBuilder();
      StringBuilder descs = new StringBuilder();
      for (Meta sysConfig : subjects) {
        value.append(sysConfig.getId().intValue() + COMMA);
        descs.append(sysConfig.getName() + COMMA);
      }
      areaConfig.setIds(value.substring(0, value.length() - 1));
      areaConfig.setDescs(descs.substring(0, descs.length() - 1));
      areaConfig = sysConfigService.save(areaConfig);
    }
    clearMetaCache();
  }
}
