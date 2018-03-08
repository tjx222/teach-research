/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */

package com.tmser.tr.manage.meta.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmser.tr.back.logger.LoggerModule;
import com.tmser.tr.common.dao.BaseDAO;
import com.tmser.tr.common.service.AbstractService;
import com.tmser.tr.common.utils.LoggerUtils;
import com.tmser.tr.manage.meta.Meta;
import com.tmser.tr.manage.meta.MetaUtils;
import com.tmser.tr.manage.meta.bo.Book;
import com.tmser.tr.manage.meta.bo.BookSync;
import com.tmser.tr.manage.meta.dao.BookDao;
import com.tmser.tr.manage.meta.dao.BookSyncDao;
import com.tmser.tr.manage.meta.service.BookService;
import com.tmser.tr.manage.meta.service.BookSyncService;
import com.tmser.tr.manage.org.bo.Area;
import com.tmser.tr.manage.org.bo.Organization;
import com.tmser.tr.utils.Identities;
import com.tmser.tr.utils.StringUtils;

/**
 * 书籍对象 服务实现类
 * 
 * <pre>
 *
 * </pre>
 *
 * @author ghw
 * @version $Id: BookServiceImpl.java, v 1.0 2017年3月15日 上午9:30:16 ghw Exp $
 */
@Service
@Transactional
public class BookServiceImpl extends AbstractService<Book, String> implements BookService {

  @Autowired
  private BookDao commidityDao;
  @Autowired
  private BookSyncDao bookSyncDao;
  @Autowired
  private BookSyncService bookSyncService;

  @Override
  public List<Book> findBooksOfJys(Book book) {
    Integer grade = book.getGradeLevelId();
    Integer currGrade;
    try {
      currGrade = Integer.valueOf(book.getFlago());
    } catch (NumberFormatException e) {
      logger.warn("", e);
      currGrade = grade;
    }

    book.addAlias("b");
    book.setComType(1);// 电子课本、电子教材
    book.setSysDelete(0);
    book.addCustomCulomn("b.comId,b.comName,b.formatName,b.publisher,b.gradeLevel,b.gradeLevelId,"
        + "b.relationComId,b.bookEdtion,b.fasciculeId,b.fascicule,b.comOrder,b.bookInTime");
    book.addOrder("b.gradeLevelId asc");
    book.buildCondition(" and b.comId not in (select c.comId from BookSync c where c.gradeLevelId = :gradeId"
        + " and c.subjectId = :subjectId and c.publisherId = :publisherId  and c.orgId=:orgId and c.areaId=:areaId)")
        .put("gradeId", currGrade).put("subjectId", book.getSubjectId()).put("publisherId", book.getPublisherId())
        .put("orgId", 0).put("areaId", 0);
    List<Book> rs = commidityDao.listAll(book);
    return rs;
  }

  @Override
  public String findPulishName(Integer pulishId) {
    if (pulishId == null) {
      return "";
    }

    BookSync model = new BookSync();
    model.setPublisherId(pulishId);
    model.addCustomCulomn("formatName");
    model.buildCondition(" and LENGTH(formatName) > 1 ");
    model = bookSyncDao.getOne(model);
    if (model != null) {
      return model.getFormatName();
    }
    return "";
  }

  @Override
  public BaseDAO<Book, String> getDAO() {
    return commidityDao;
  }

  @Override
  public List<Book> getEditionNamesByPhaseId(Integer phaseId) {
    Book book = new Book();
    book.addCustomCulomn("distinct formatName");
    book.setPhaseId(phaseId);
    book.setSaleType(2);
    Map<String, Object> paramMap = new HashMap<String, Object>();
    book.addCustomCondition(" and formatName is not null and formatName != ''", paramMap);
    return commidityDao.listAll(book);
  }

  @Override
  public void saveJc(String[] comIds, BookSync book) {
    // 修改单个对象
    if (StringUtils.isNotEmpty(book.getComId())) {
      if (StringUtils.isBlank(book.getFormatName())) {
        book.setFormatName(findPulishName(book.getPublisherId()));
      }
      bookSyncDao.update(book);
      Integer fasciculeId = book.getFasciculeId();
      Book bk = commidityDao.get(book.getComId());
      if (fasciculeId != null) {
        Meta meta = MetaUtils.getMeta(fasciculeId);
        if (meta != null) {
          bk.setFascicule(meta.getName());
        }
      }
      BeanUtils.copyProperties(book, bk);
      commidityDao.update(bk);
      LoggerUtils.updateLogger(LoggerModule.JXTX, "教学体系——教材管理——修改教材，教材ID：{}", book.getComId());
      // 修改多项数据
    } else {
      for (int i = 0; i < comIds.length; i++) {
        String[] cvs = comIds[i].split("#");
        String comid = cvs[0];
        Book bookTemp = commidityDao.get(comid);
        bookTemp.setSaleType(2);// 教研平台使用类型
        String formatName = bookTemp.getComName();
        if (cvs.length > 1 && cvs[1].length() > 0) {
          formatName = cvs[1];
        }
        if (StringUtils.isBlank(formatName)) {
          formatName = findPulishName(book.getPublisherId());
        }
        commidityDao.update(bookTemp);

        BookSync bs = new BookSync();
        bs.setPhaseId(book.getPhaseId());
        bs.setSubjectId(bookTemp.getSubjectId());
        bs.setPublisherId(bookTemp.getPublisherId());
        bs.setGradeLevelId(book.getGradeLevelId());
        bs.setComId(bookTemp.getComId());
        if (book.getOrgId() == null) {
          bs.setOrgId(Organization.DEFAULT_ORG);
        } else {
          bs.setOrgId(book.getOrgId());
        }
        if (book.getAreaId() == null) {
          bs.setAreaId(Area.DEFALUT_AREA);
        } else {
          bs.setAreaId(book.getAreaId());
        }

        BookSync old = bookSyncDao.getOne(bs);
        if (old == null) {
          // 如果同步表中不存在将书籍插入到同步书籍表中
          BeanUtils.copyProperties(bookTemp, bs);
          if (book.getOrgId() == null) {
            bs.setOrgId(Organization.DEFAULT_ORG);
          } else {
            bs.setOrgId(book.getOrgId());
          }

          if (book.getAreaId() == null) {
            bs.setAreaId(Area.DEFALUT_AREA);
          } else {
            bs.setAreaId(book.getAreaId());
          }

          bs.setGradeLevel(MetaUtils.getMeta(book.getGradeLevelId()).getName());
          bs.setGradeLevelId(book.getGradeLevelId());
          bs.setPhaseId(book.getPhaseId());
          bs.setPhase(MetaUtils.getMeta(book.getPhaseId()).getName());
          bs.setEnable(1);
          bs.setFormatName(formatName);
          bs.setOrder(String.valueOf(bs.getFasciculeId()));
          bs = bookSyncDao.insert(bs);
        } else {
          bs.setEnable(1);
          bs.setFormatName(formatName);
          bookSyncDao.update(bs);
        }
        LoggerUtils.updateLogger(LoggerModule.JXTX, "教学体系——教材管理——批量选中要使用的教材");
      }

    }
  }

  @Override
  public void addRelation(Book book, Book book2) {
    book.setRelationComId(book2.getComId());
    book2.setRelationComId(book.getComId());
    commidityDao.update(book);
    commidityDao.update(book2);
    // 在同步表中更新关联信息
    BookSync bookSync = new BookSync();
    bookSync.setComId(book2.getComId());
    for (BookSync bookSync1 : bookSyncDao.listAll(bookSync)) {
      if (!book.getComId().equals(bookSync1.getRelationComId())) {
        bookSync1.setRelationComId(book.getComId());
        bookSyncDao.update(bookSync1);
      }

    }

    bookSync.setComId(book.getComId());
    for (BookSync bookSync1 : bookSyncDao.listAll(bookSync)) {
      if (!book2.getComId().equals(bookSync1.getRelationComId())) {
        bookSync1.setRelationComId(book2.getComId());
        bookSyncDao.update(bookSync1);
      }
    }
  }

  @Override
  public List<BookSync> findBookSync(BookSync book) {
    /*
     * if (book.getAreaId() == null) {
     * book.setAreaId(Area.DEFALUT_AREA);
     * 
     * }
     * if (book.getOrgId() == null) {
     * book.setOrgId(Organization.DEFAULT_ORG);
     * }
     */
    book.addOrder("formatName asc,fasciculeId asc");
    book.setEnable(1);
    List<BookSync> list = bookSyncDao.listAll(book);
    return list;
  }

  @Override
  public Integer deleteBookSyncById(Integer id) {
    BookSync bookSync = bookSyncDao.get(id);
    Book book = commidityDao.get(bookSync.getComId());
    bookSyncService.delete(id);
    // 修改书籍的关联状态
    book.setSaleType(0);
    book.setRelationComId("");
    final Integer integer = Integer.valueOf(commidityDao.update(book));
    // 修改relationComId为该书籍的bookSync的关联状态
    bookSyncDao.delRelationComId(bookSync.getComId());
    // 修改relationComId为该书籍的book的关联状态
    commidityDao.delRelationComId(bookSync.getComId());
    return integer;
  }

  @Override
  public BookSync findOneBookSync(BookSync book) {
    return bookSyncDao.getOne(book);
  }

  @Override
  public List<BookSync> getPublisherNamesByPhaseId(Integer phaseId) {
    BookSync bk = new BookSync();
    bk.setPhaseId(phaseId);
    bk.addCustomCulomn("distinct formatName");
    bk.addCustomCondition(" order by formatName asc");
    return bookSyncDao.listAll(bk);
  }

  @Override
  public void saveBookAndBookSync(BookSync bookSync) {
    Book book = new Book();
    BeanUtils.copyProperties(bookSync, book);
    Integer gradeLevelId = book.getGradeLevelId();
    String gradeLevelName = MetaUtils.getMeta(gradeLevelId).getName();
    book.setGradeLevel(gradeLevelName);
    Integer phaseId = book.getPhaseId();
    String phase = MetaUtils.getMeta(phaseId).getName();
    book.setPhase(phase);
    Integer publisherId = book.getPublisherId();
    String publisher = MetaUtils.getMeta(publisherId).getName();
    book.setPublisher(publisher);
    Integer subjectId = book.getSubjectId();
    String subject = MetaUtils.getMeta(subjectId).getName();
    book.setSubject(subject);
    book.setFascicule(MetaUtils.getMeta(book.getFasciculeId()).getName());
    book.setComType(1);
    book.setComTypeName("电子课本");
    book.setBookInTime(new Date());
    book.setSaleType(2);
    book.setIsDisplay(1);
    book.setSysDelete(0);
    book.setComId(Identities.uuid2());
    book = commidityDao.insert(book);

    LoggerUtils.updateLogger(LoggerModule.JXTX, "教学体系——教材管理——添加自定义教材，教材ID：{}", book.getComId());
    bookSync.setComId(book.getComId());
    bookSync.setComName(book.getComName());
    bookSync.setEnable(1);
    bookSync.setGradeLevelId(gradeLevelId);
    bookSync.setGradeLevel(gradeLevelName);
    bookSync.setPhaseId(phaseId);
    bookSync.setPhase(phase);
    bookSync.setPublisherId(publisherId);
    bookSync.setPublisher(publisher);
    bookSync.setSubjectId(subjectId);
    bookSync.setSubject(subject);
    bookSync.setBookInTime(new Date());
    if (bookSync.getAreaId() == null) {
      bookSync.setAreaId(Area.DEFALUT_AREA);
    }
    if (bookSync.getOrgId() == null) {
      bookSync.setOrgId(Organization.DEFAULT_ORG);
    }

    bookSync = bookSyncDao.insert(bookSync);
    LoggerUtils.updateLogger(LoggerModule.JXTX, "教学体系——教材管理——添加自定义教材同步记录，教材ID：{}", bookSync.getComId());
  }

  @Override
  public List<BookSync> getBookSyncByIds(List<Integer> ids) {
    return bookSyncDao.getByIds(ids);
  }

}
