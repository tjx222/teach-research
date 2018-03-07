package com.tmser.tr.back.schconfig.teach.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmser.tr.back.schconfig.teach.service.OrgJcManageService;
import com.tmser.tr.back.schconfig.teach.service.PublisherManageService;
import com.tmser.tr.manage.meta.Meta;
import com.tmser.tr.manage.meta.MetaUtils;
import com.tmser.tr.manage.meta.bo.Book;
import com.tmser.tr.manage.meta.bo.BookSync;
import com.tmser.tr.manage.meta.bo.MetaRelationship;
import com.tmser.tr.manage.meta.bo.PublishRelationship;
import com.tmser.tr.manage.meta.dao.BookDao;
import com.tmser.tr.manage.meta.dao.BookSyncDao;
import com.tmser.tr.manage.meta.service.BookService;
import com.tmser.tr.manage.meta.service.BookSyncService;
import com.tmser.tr.manage.org.bo.Area;
import com.tmser.tr.manage.org.bo.Organization;
import com.tmser.tr.manage.org.service.AreaService;
import com.tmser.tr.manage.org.service.OrganizationService;
import com.tmser.tr.utils.StringUtils;

@Service
@Transactional
public class OrgJcManageServiceImpl implements OrgJcManageService {

  /**
   * <pre>
   *
   * </pre>
   */
  private static final String NAME = "name";
  /**
   * <pre>
   *
   * </pre>
   */
  private static final String ID = "id";
  @Autowired
  private OrganizationService orgService;
  @Autowired
  private BookService bookService;
  @Autowired
  private AreaService areaService;
  @Autowired
  private BookSyncDao bookSyncDao;
  @Autowired
  private BookSyncService bookSyncService;
  @Autowired
  private BookDao commidityDao;
  @Autowired
  private PublisherManageService publisherManageService;

  @Override
  public List<Map<String, Object>> findOrgJCtree(Integer orgId) {
    Organization org = orgService.findOne(orgId);
    String areaIds = org.getAreaIds();
    Integer[] idArray = StringUtils.toIntegerArray(areaIds.substring(1, areaIds.lastIndexOf(",")), ",");
    List<Map<String, Object>> resultMap = new ArrayList<Map<String, Object>>();
    Integer schoolings = org.getSchoolings();
    List<Meta> xdlist = MetaUtils.getOrgTypeMetaProvider().listAllPhaseMeta(schoolings);
    for (Meta phase : xdlist) {
      Map<String, Object> map = new HashMap<String, Object>();
      map.put(ID, phase.getId());
      map.put(NAME, phase.getName());
      MetaRelationship phaseRelation = MetaUtils.getPhaseMetaProvider().getMetaRelationshipByPhaseId(phase.getId());
      ;
      List<Meta> xkList = MetaUtils.getPhaseSubjectMetaProvider().listAllSubject(orgId, phaseRelation.getId(), idArray);
      List<Map<String, Object>> xkPublisherlist = new ArrayList<Map<String, Object>>();
      if (xkList != null && xkList.size() > 0) {
        for (Meta subject : xkList) {
          Map<String, Object> map2 = new HashMap<String, Object>();
          map2.put(ID, subject.getId());
          map2.put(NAME, subject.getName());
          List<Meta> cbslist = new ArrayList<Meta>();
          PublishRelationship model = new PublishRelationship();
          model.setOrgId(orgId);
          model.setPhaseId(phaseRelation.getId());
          model.setSubjectId(subject.getId());
          model.setScope("org");
          cbslist.addAll(publisherManageService.listAllPubliserMetaByScope(model));
          map2.put("child", cbslist);
          xkPublisherlist.add(map2);
        }
        map.put("child", xkPublisherlist);
      }

      resultMap.add(map);

      List<Meta> listAllGradByPhaseId = MetaUtils.getOrgTypeMetaProvider().listAllGrade(schoolings,
          phaseRelation.getId());
      List<Map<String, Object>> xdnjlist = new ArrayList<Map<String, Object>>();
      if (listAllGradByPhaseId != null && listAllGradByPhaseId.size() > 0) {
        for (Meta sysConfig : listAllGradByPhaseId) {
          Map<String, Object> obj = new HashMap<String, Object>();
          obj.put(ID, sysConfig.getId());
          obj.put(NAME, sysConfig.getName());
          xdnjlist.add(obj);
        }
        map.put("njlist", xdnjlist);
      }

    }
    return resultMap;
  }

  @Override
  public List<Map<String, Object>> findAreaJCtree(Integer areaId) {
    List<Map<String, Object>> resultMap = new ArrayList<Map<String, Object>>();
    List<Meta> xdlist = MetaUtils.getPhaseMetaProvider().listAllPhaseMeta();
    List<Integer> areaIds = areaService.getAreaIds(areaId);
    Integer[] areaArray = new Integer[areaIds.size()];
    Integer[] array = areaIds.toArray(areaArray);
    for (Meta phase : xdlist) {
      Map<String, Object> map = new HashMap<String, Object>();
      map.put(ID, phase.getId());
      map.put(NAME, phase.getName());
      MetaRelationship phaseRelation = MetaUtils.getPhaseMetaProvider().getMetaRelationshipByPhaseId(phase.getId());
      List<Meta> xkList = MetaUtils.getPhaseSubjectMetaProvider().listAllSubject(null, phaseRelation.getId(), array);
      List<Map<String, Object>> xkPublisherlist = new ArrayList<Map<String, Object>>();
      if (xkList != null && xkList.size() > 0) {
        for (Meta subject : xkList) {
          Map<String, Object> map2 = new HashMap<String, Object>();
          map2.put(ID, subject.getId());
          map2.put(NAME, subject.getName());
          List<Meta> cbslist = new ArrayList<Meta>();
          PublishRelationship model = new PublishRelationship();
          model.setAreaId(areaId);
          model.setPhaseId(phaseRelation.getId());
          model.setSubjectId(subject.getId());
          model.setScope("area");
          cbslist.addAll(publisherManageService.listAllPubliserMetaByScope(model));
          map2.put("child", cbslist);
          xkPublisherlist.add(map2);
        }
        map.put("child", xkPublisherlist);
      }

      resultMap.add(map);
      List<Meta> listAllGradByPhaseId = MetaUtils.getPhaseGradeMetaProvider().listAllGradeByPhaseId(
          phaseRelation.getId());
      List<Map<String, Object>> xdnjlist = new ArrayList<Map<String, Object>>();
      if (listAllGradByPhaseId != null && listAllGradByPhaseId.size() > 0) {
        for (Meta sysConfig : listAllGradByPhaseId) {
          Map<String, Object> obj = new HashMap<String, Object>();
          obj.put(ID, sysConfig.getId());
          obj.put(NAME, sysConfig.getName());
          xdnjlist.add(obj);
        }
        map.put("njlist", xdnjlist);
      }

    }
    return resultMap;

  }

  @Override
  public List<BookSync> findBookSync(BookSync book) {
    Organization organization = orgService.findOne(book.getOrgId());
    List<Integer> areaIds = new ArrayList<Integer>();
    areaIds.add(Area.DEFALUT_AREA);
    if (organization != null) {
      areaIds.addAll(areaService.getAreaIds(organization.getAreaId()));
    } else if (book.getAreaId() != null) {
      areaIds.add(book.getAreaId());
    }
    return bookSyncService.getBookSyncByOrg(book.getOrgId(), areaIds, book.getGradeLevelId(), book.getSubjectId(),
        book.getPhaseId(), book.getPublisherId());
  }

  @Override
  public void deleteBookSyncById(Integer id, Integer orgId, Integer areaId) {
    BookSync bookSync = bookSyncDao.get(id);
    Book book = bookService.findOne(bookSync.getComId());
    if (bookSync.getOrgId().equals(orgId) || bookSync.getAreaId().equals(areaId)) {
      bookSyncService.delete(id);
      book.setSaleType(0);
      book.setRelationComId("");
      bookService.update(book);
      // 修改relationComId为该书籍的bookSync的关联状态
      bookSyncDao.delRelationComId(bookSync.getComId());

      // 修改relationComId为该书籍的book的关联状态
      commidityDao.delRelationComId(bookSync.getComId());
    } else {
      BookSync model = new BookSync();
      model.setComId(bookSync.getComId());
      model.setPhaseId(bookSync.getPhaseId());
      model.setGradeLevelId(bookSync.getGradeLevelId());
      model.setSubjectId(bookSync.getSubjectId());
      model.setPublisherId(bookSync.getPublisherId());
      model.setComName(bookSync.getComName());
      model.setFasciculeId(bookSync.getFasciculeId());
      model.setBookInTime(new Date());
      if (orgId != null) {
        model.setOrgId(orgId);
        model.setAreaId(Area.DEFALUT_AREA);
      }
      if (areaId != null) {
        model.setOrgId(Organization.DEFAULT_ORG);
        model.setAreaId(areaId);
      }
      model.setEnable(1);
      bookSyncDao.insert(model);
    }
  }

  @Override
  public List<Book> findUnAddBooks(BookSync bookSync) {
    List<BookSync> findBookSync = findBookSync(bookSync);
    List<String> comIds = new ArrayList<String>();
    if (findBookSync != null && findBookSync.size() > 0) {
      for (BookSync bookSync2 : findBookSync) {
        comIds.add(bookSync2.getComId());
      }
    }
    Book book = new Book();
    book.setPhaseId(bookSync.getPhaseId());
    book.setSubjectId(bookSync.getSubjectId());
    book.setPublisherId(bookSync.getPublisherId());
    book.setGradeLevelId(bookSync.getGradeLevelId());
    if (comIds.size() > 0) {
      book.buildCondition(" and comId not in (:comIds)").put("comIds", comIds);
    }
    return bookService.findAll(book);

  }

}
