package com.tmser.tr.back.schconfig.teach.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmser.tr.back.jxtx.service.PublishRelationshipService;
import com.tmser.tr.back.schconfig.teach.service.PublisherManageService;
import com.tmser.tr.back.schconfig.vo.PublisherVo;
import com.tmser.tr.manage.meta.Meta;
import com.tmser.tr.manage.meta.MetaConstants;
import com.tmser.tr.manage.meta.MetaUtils;
import com.tmser.tr.manage.meta.bo.MetaRelationship;
import com.tmser.tr.manage.meta.bo.PublishRelationship;
import com.tmser.tr.manage.meta.bo.SysDic;
import com.tmser.tr.manage.meta.dao.SysDicDao;
import com.tmser.tr.manage.org.bo.Organization;
import com.tmser.tr.manage.org.service.AreaService;
import com.tmser.tr.manage.org.service.OrganizationService;
import com.tmser.tr.utils.StringUtils;

@Service
@Transactional
public class PublisherManageServiceImpl implements PublisherManageService {

  /**
   * <pre>
   *
   * </pre>
   */
  private static final String COMMA = ",";

  @Autowired
  private PublishRelationshipService publishRelationshipService;

  @Autowired
  private OrganizationService orgService;

  @Autowired
  private AreaService areaService;
  @Autowired
  private SysDicDao sysDicDao;

  @Override
  public List<Map<String, Object>> findXDXKtree() {
    List<Map<String, Object>> resultMap = new ArrayList<>();
    List<Meta> xdlist = MetaUtils.getPhaseMetaProvider().listAllPhaseMeta();
    for (Meta config : xdlist) {
      Map<String, Object> map = new HashMap<>();
      map.put("id", config.getId());
      map.put("name", config.getName());
      List<Meta> xkList = MetaUtils.getPhaseSubjectMetaProvider().listAllSubjectByPhaseId(config.getId());
      map.put("child", xkList);
      resultMap.add(map);
    }
    return resultMap;
  }

  @Override
  public List<Map<String, Object>> findXDXKtree(Integer orgId) {
    List<Map<String, Object>> resultMap = new ArrayList<>();
    Organization org = orgService.findOne(orgId);
    Integer schoolings = org.getSchoolings();
    String areaIds = org.getAreaIds();
    Integer[] areaIdArr = StringUtils.toIntegerArray(areaIds.substring(1, areaIds.lastIndexOf(COMMA)), COMMA);
    List<MetaRelationship> xdlist = MetaUtils.getOrgTypeMetaProvider().listAllPhase(schoolings);
    for (MetaRelationship config : xdlist) {
      Map<String, Object> map = new HashMap<>();
      map.put("id", config.getId());
      map.put("name", config.getName());
      List<Meta> xkList = MetaUtils.getPhaseSubjectMetaProvider().listAllSubject(orgId, config.getId(), areaIdArr);
      map.put("child", xkList);
      resultMap.add(map);
    }
    return resultMap;
  }

  @Override
  public List<Map<String, Object>> findXDXKtree1(Integer areaId) {
    List<Map<String, Object>> resultMap = new ArrayList<>();
    List<Meta> xdlist = MetaUtils.getPhaseMetaProvider().listAllPhaseMeta();
    List<Integer> areaIds = areaService.getAreaIds(areaId);
    Integer[] areaArray = new Integer[areaIds.size()];
    Integer[] array = areaIds.toArray(areaArray);
    for (Meta config : xdlist) {
      Map<String, Object> map = new HashMap<>();
      MetaRelationship metaRelationshipByPhaseId = MetaUtils.getPhaseMetaProvider().getMetaRelationshipByPhaseId(
          config.getId());
      map.put("id", metaRelationshipByPhaseId.getId());
      map.put("name", metaRelationshipByPhaseId.getName());
      List<Meta> phaseSubjectList = MetaUtils.getPhaseSubjectMetaProvider().listAllSubject(null,
          metaRelationshipByPhaseId.getId(), array);
      map.put("child", phaseSubjectList);
      resultMap.add(map);
    }
    return resultMap;
  }

  @Override
  public void save(Integer[] publisherIds, PublishRelationship model) {
    List<PublishRelationship> list = new ArrayList<>();
    for (int i = 0; i < publisherIds.length; i++) {
      model.setEid(publisherIds[i]);
      model.setEnable(0);
      if (publishRelationshipService.findOne(model) != null) {
        model.setEnable(1);
        publishRelationshipService.update(model);
      } else {
        model.setEnable(1);
        model.setName(MetaUtils.getMeta(publisherIds[i]).getName());
        model.setShortName(model.getName());
        model.setCrtDttm(new Date());
        model.setSort(0);
        list.add(model);
      }
    }
    if (!list.isEmpty()) {
      publishRelationshipService.batchSave(list);
    }
  }

  /**
   * 
   * @param ids
   *          出版社ids
   * @param phaseId
   *          学段id
   * @param subjectId
   *          学科id
   * @param orgId
   *          学校id
   */
  public void update(Integer[] ids, Integer phaseId, Integer subjectId, String orgId) {
  }

  @Override
  public void del(Integer id, PublishRelationship model) {
    if (model.getAreaId() != null) {
      model.setScope("area");
    } else if (model.getOrgId() != null) {
      model.setScope("org");
    } else {
      model.setScope("sys");
    }
    PublishRelationship ship = new PublishRelationship();
    ship.setEid(id);
    ship.setSubjectId(model.getSubjectId());
    ship.setPhaseId(model.getPhaseId());
    ship.setEnable(1);
    ship = publishRelationshipService.findOne(ship);
    if (ship.getScope().equals(model.getScope())) {
      publishRelationshipService.delete(ship.getId());
    } else {
      model.setEnable(0);
      model.setEid(id);
      publishRelationshipService.save(model);
    }

  }

  @Override
  public List<Meta> getUnAddList(PublishRelationship model) {
    List<Meta> allSubjectMetas = new ArrayList<Meta>();
    if ("org".equals(model.getScope())) {
      allSubjectMetas.addAll(MetaUtils.getPublisherMetaProvider().listAllPublisherMetaWithOrg(model.getOrgId()));
    } else if ("area".equals(model.getScope())) {
      allSubjectMetas.addAll(MetaUtils.getPublisherMetaProvider().listAllPublisherMetaWithArea(model.getAreaId()));
    }
    List<Meta> addedSubjects = new ArrayList<>();
    addedSubjects.addAll(this.listAllPubliserMetaByScope(model));
    Set<Integer> ids = new HashSet<>();
    if (!addedSubjects.isEmpty()) {
      for (Meta meta : addedSubjects) {
        ids.add(meta.getId());
      }
    }
    Iterator<Meta> iterator = allSubjectMetas.iterator();
    while (iterator.hasNext()) {
      Meta meta = iterator.next();
      if (ids.contains(meta.getId())) {
        iterator.remove();
      }
    }
    return allSubjectMetas;
  }

  /**
   * @param vo
   * @param orgId
   * @see com.tmser.tr.back.schconfig.teach.service.PublisherManageService#saveCustom(com.tmser.tr.back.schconfig.vo.PublisherVo,
   *      java.lang.String)
   */
  @Override
  public void saveCustom(PublisherVo vo) {
    SysDic dic = new SysDic();
    PublishRelationship model = new PublishRelationship();
    if (vo.getAreaId() != null) {
      model.setScope("area");
      dic.setScope("area");
      model.setAreaId(vo.getAreaId());
      dic.setAreaId(vo.getAreaId());
    } else if (vo.getOrgId() != null) {
      model.setScope("org");
      dic.setScope("org");
      model.setOrgId(vo.getOrgId());
      dic.setOrgId(vo.getOrgId());
    } else {
      model.setScope("sys");
      dic.setScope("sys");
    }
    dic.setName(vo.getName());
    dic.setParentId(MetaConstants.PUBLISHER_METADATA_ID);
    dic.setCascadeDicIds(MetaConstants.PUBLISHER_METADATA_ID.toString());
    dic.setDicLevel(2);
    dic.setDicStatus(SysDic.DICSTATUS_ACTIVE);
    dic.setDicOrderby(0);
    dic.setOperator("");
    dic.setChildCount(0);
    dic = sysDicDao.insert(dic);
    dic.setCascadeDicIds(dic.getCascadeDicIds().concat("_").concat(dic.getId().toString()));
    sysDicDao.update(dic);
    model.setOrder("0");
    model.setEid(dic.getId());
    model.setName(dic.getName());
    model.setPhaseId(vo.getPhaseId());
    model.setSubjectId(vo.getSubjectId());
    model.setShortName(dic.getName());
    model.setEnable(1);
    model.setCrtDttm(new Date());
    publishRelationshipService.save(model);
  }

  @Override
  public List<Meta> listAllPubliserMetaByScope(PublishRelationship model) {
    if ("sys".equals(model.getScope())) {
      return listAllPubliserMetaSys(model);
    }
    if ("org".equals(model.getScope())) {
      String areaIds = orgService.findOne(model.getOrgId()).getAreaIds();
      Integer[] areaIdArr = StringUtils.toIntegerArray(areaIds.substring(1, areaIds.lastIndexOf(",")), ",");
      return listAllPubliserMetaOrg(model, Arrays.asList(areaIdArr));
    }
    if ("area".equals(model.getScope())) {
      List<Integer> areaIds = areaService.getAreaIds(model.getAreaId());
      return listAllPubliserMetaArea(model, areaIds);
    }

    return Collections.emptyList();
  }

  private List<Meta> listAllPubliserMetaSys(PublishRelationship model) {
    model.setScope("sys");
    List<PublishRelationship> findAll = publishRelationshipService.findAll(model);
    Set<Integer> eids = new HashSet<>();
    for (PublishRelationship publishRelationship : findAll) {
      eids.add(publishRelationship.getEid());
    }
    if (!eids.isEmpty()) {
      return MetaUtils.getPublisherMetaProvider().getMetaByIds(eids);
    }
    return Collections.emptyList();
  }

  private List<Meta> listAllPubliserMetaOrg(PublishRelationship model, List<Integer> areaIdArr) {
    List<Meta> all = new ArrayList<>();
    PublishRelationship areamodel = new PublishRelationship();
    areamodel.setPhaseId(model.getPhaseId());
    areamodel.setSubjectId(model.getSubjectId());
    all.addAll(listAllPubliserMetaArea(areamodel, areaIdArr));
    model.setScope("org");
    model.setEnable(1);
    List<PublishRelationship> findAll = publishRelationshipService.findAll(model);
    Set<Integer> eids = new HashSet<>();
    for (PublishRelationship publishRelationship : findAll) {
      eids.add(publishRelationship.getEid());
    }
    if (!eids.isEmpty()) {
      all.addAll(MetaUtils.getPublisherMetaProvider().getMetaByIds(eids));
    }
    model.setEnable(0);
    List<PublishRelationship> delShips = publishRelationshipService.findAll(model);
    Set<Integer> delids = new HashSet<>();
    for (PublishRelationship publishRelationship : delShips) {
      delids.add(publishRelationship.getEid());
    }
    Iterator<Meta> iterator = all.iterator();
    while (iterator.hasNext()) {
      Meta next = iterator.next();
      if (delids.contains(next.getId())) {
        iterator.remove();
      }
    }
    return all;
  }

  private List<Meta> listAllPubliserMetaArea(PublishRelationship model, List<Integer> areaIdArr) {
    List<Meta> all = new ArrayList<>();
    PublishRelationship model1 = new PublishRelationship();
    model1.setSubjectId(model.getSubjectId());
    model1.setPhaseId(model.getPhaseId());
    all.addAll(listAllPubliserMetaSys(model1));
    Set<Integer> eids = new HashSet<>();
    Set<Integer> delids = new HashSet<>();
    for (Integer areaId : areaIdArr) {
      model.setAreaId(areaId);
      model.setScope("area");
      model.setEnable(1);
      List<PublishRelationship> ships = publishRelationshipService.findAll(model);
      for (PublishRelationship publishRelationship : ships) {
        eids.add(publishRelationship.getEid());
      }
      model.setEnable(0);
      List<PublishRelationship> delShips = publishRelationshipService.findAll(model);
      for (PublishRelationship publishRelationship : delShips) {
        delids.add(publishRelationship.getEid());
      }
    }
    if (!eids.isEmpty()) {
      all.addAll(MetaUtils.getPublisherMetaProvider().getMetaByIds(eids));
    }
    Iterator<Meta> iterator = all.iterator();
    while (iterator.hasNext()) {
      Meta next = iterator.next();
      if (delids.contains(next.getId())) {
        iterator.remove();
      }
    }
    return all;
  }
}
