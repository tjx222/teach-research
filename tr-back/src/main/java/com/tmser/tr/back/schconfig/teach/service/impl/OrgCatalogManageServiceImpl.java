package com.tmser.tr.back.schconfig.teach.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmser.tr.back.schconfig.teach.service.OrgCatalogManageService;
import com.tmser.tr.back.schconfig.teach.service.PublisherManageService;
import com.tmser.tr.manage.meta.Meta;
import com.tmser.tr.manage.meta.MetaUtils;
import com.tmser.tr.manage.meta.bo.BookSync;
import com.tmser.tr.manage.meta.bo.MetaRelationship;
import com.tmser.tr.manage.meta.bo.PublishRelationship;
import com.tmser.tr.manage.meta.service.BookSyncService;
import com.tmser.tr.manage.org.bo.Area;
import com.tmser.tr.manage.org.bo.Organization;
import com.tmser.tr.manage.org.service.AreaService;
import com.tmser.tr.manage.org.service.OrganizationService;
import com.tmser.tr.uc.bo.UserManagescope;
import com.tmser.tr.uc.dao.UserManagescopeDao;
import com.tmser.tr.utils.StringUtils;

@Service
@Transactional
public class OrgCatalogManageServiceImpl implements OrgCatalogManageService {

  @Autowired
  private OrganizationService orgService;

  @Autowired
  private AreaService areaService;
  @Autowired
  private BookSyncService bookSyncService;
  @Autowired
  private UserManagescopeDao userManageScopeDao;
  @Autowired
  private PublisherManageService publisherManageService;

  @Override
  public List<Map<String, Object>> findOrgJCtree(Integer orgId) {
    Organization org = orgService.findOne(orgId);
    String areaIds = org.getAreaIds();
    Integer[] areaIdsArr = StringUtils.toIntegerArray(areaIds.substring(1, areaIds.lastIndexOf(",")), ",");
    return createTreeMap(org, areaIdsArr);
  }

  @Override
  public List<Map<String, Object>> findAreaJCtree(Integer areaId) {
    List<Integer> areaIds = areaService.getAreaIds(areaId);
    Integer[] areaArray = new Integer[areaIds.size()];
    Integer[] array = areaIds.toArray(areaArray);
    Organization org = new Organization();
    // 携带参数areaId查询相关area的数据
    org.setAreaId(areaId);
    return createTreeMap(org, array);
  }

  private List<Map<String, Object>> createTreeMap(Organization organization, Integer[] array) {
    List<Map<String, Object>> resultMap = new ArrayList<Map<String, Object>>();
    List<Meta> xdlist;
    Integer orgId = null;
    if (organization.getId() != null) {
      Integer schoolings = organization.getSchoolings();
      xdlist = MetaUtils.getOrgTypeMetaProvider().listAllPhaseMeta(schoolings);
      orgId = organization.getId();
    } else {
      xdlist = MetaUtils.getPhaseMetaProvider().listAllPhaseMeta();
    }

    List<Integer> areaIdsList = new ArrayList<>(Arrays.asList(array));
    areaIdsList.add(Area.DEFALUT_AREA);
    // 最外层学段
    for (Meta phase : xdlist) {
      MetaRelationship phaseRelation = MetaUtils.getPhaseMetaProvider().getMetaRelationshipByPhaseId(phase.getId());
      Map<String, Object> map = new HashMap<String, Object>();
      map.put("name", phase.getName());
      final List<Meta> xkList = MetaUtils.getPhaseSubjectMetaProvider().listAllSubject(orgId, phaseRelation.getId(),
          array);
      List<Meta> njList;
      if (organization != null) {
        njList = MetaUtils.getOrgTypeMetaProvider().listAllGrade(organization.getSchoolings(), phaseRelation.getId());
      } else {
        njList = MetaUtils.getPhaseGradeMetaProvider().listAllGradeByPhaseId(phaseRelation.getId());
      }
      List<BookSync> list = bookSyncService.getBookSyncByOrg(orgId, areaIdsList, null, null, phaseRelation.getId(),
          null);
      Map<String, List<BookSync>> subPubGradeBookMap = new HashMap<>();
      for (BookSync bookSync : list) {
        String key = "s_" + bookSync.getSubjectId() + "g" + bookSync.getGradeLevelId() + "p"
            + bookSync.getPublisherId();
        List<BookSync> blist = subPubGradeBookMap.get(key);
        if (blist == null) {
          blist = new ArrayList<>(4);
        }
        blist.add(bookSync);
        subPubGradeBookMap.put(key, blist);
      }

      map.put("njlist", njList);
      if (xkList != null && xkList.size() > 0) {
        List<Map<String, Object>> child1 = new ArrayList<Map<String, Object>>();
        for (Meta subject : xkList) {
          Map<String, Object> map2 = new HashMap<String, Object>();
          map2.put("name", subject.getName());
          List<Meta> cbslist = new ArrayList<Meta>();
          PublishRelationship model = new PublishRelationship();
          if (orgId != null) {
            model.setOrgId(orgId);
            model.setScope("org");
          } else {
            model.setScope("area");
            model.setAreaId(organization.getAreaId());
          }
          model.setPhaseId(phaseRelation.getId());
          model.setSubjectId(subject.getId());

          cbslist.addAll(publisherManageService.listAllPubliserMetaByScope(model));
          if (cbslist != null && cbslist.size() > 0) {
            List<Map<String, Object>> child2 = new ArrayList<Map<String, Object>>();
            // 第三层出版社
            for (Meta prt : cbslist) {
              Map<String, Object> map3 = new HashMap<String, Object>();
              map3.put("name", prt.getName());
              if (njList != null && njList.size() > 0) {
                List<Map<String, Object>> child3 = new ArrayList<Map<String, Object>>();
                // 第四层年级
                for (Meta grade : njList) {
                  Map<String, Object> map4 = new HashMap<String, Object>();
                  map4.put("name", grade.getName());
                  String key = "s_" + subject.getId() + "g" + grade.getId() + "p" + prt.getId();
                  map4.put("child", subPubGradeBookMap.get(key));
                  child3.add(map4);
                }
                map3.put("child", child3);
              }
              child2.add(map3);
            }
            map2.put("child", child2);
          }
          child1.add(map2);
        }
        map.put("child", child1);
      }
      resultMap.add(map);
    }
    return resultMap;
  }

  @Override
  public UserManagescope findUserManagescopes(Integer accountId, Integer orgId) {
    UserManagescope model = new UserManagescope();
    model.setUserId(accountId);
    model.setOrgId(orgId);
    return userManageScopeDao.getOne(model);
  }

}
