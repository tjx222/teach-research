/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */

package com.tmser.tr.manage.meta.service;

import java.util.List;

import com.tmser.tr.common.service.BaseService;
import com.tmser.tr.manage.meta.bo.Book;
import com.tmser.tr.manage.meta.bo.BookSync;

/**
 * 书籍对象 服务类
 * 
 * @author 3020mt
 *
 */

public interface BookService extends BaseService<Book, String> {

  /**
   * 获得教材集合通过教材对象
   * 
   * @param book
   *          Book
   * @return list of Book
   */
  List<Book> findBooksOfJys(Book book);

  /**
   * 保存教材、修改教材
   * 
   * @param comIds
   *          书籍ids
   * @param book
   *          BookSync
   */
  void saveJc(String[] comIds, BookSync book);

  /**
   * 根据学段获取教案版本
   * 
   * @param phaseId
   *          学段id
   * @return list of Book
   */
  List<Book> getEditionNamesByPhaseId(Integer phaseId);

  /**
   * 增加关联关系
   * 
   * @param book
   *          Book
   * @param book2
   *          Book
   */
  void addRelation(Book book, Book book2);

  /**
   * 通过教材对象获取已同步的教材
   * 
   * @param book
   *          BookSync
   * @return list of BookSync
   */
  List<BookSync> findBookSync(BookSync book);

  /**
   * 通过学段获取教材出版社
   * 
   * @param phaseId
   *          学段id
   * @return list of BookSync
   */
  List<BookSync> getPublisherNamesByPhaseId(Integer phaseId);

  /**
   * 删除BookSync
   * 
   * @param id
   *          id
   * @return id
   */
  Integer deleteBookSyncById(Integer id);

  /**
   * 获取BookSync
   * 
   * @param book
   *          BookSync
   * @return BookSync
   */
  BookSync findOneBookSync(BookSync book);

  /**
   * 保存教材以及同步记录
   * 
   * @param book
   *          BookSync
   */
  void saveBookAndBookSync(BookSync book);

  /**
   * 通过ids获取书籍
   * 
   * @param ids
   *          书籍ids
   * @return list of BookSync
   */
  List<BookSync> getBookSyncByIds(List<Integer> ids);

  /**
   * 获取现有检查简称
   * 
   * @param pulishId
   *          出版社id
   * @return 出版社简称
   */
  String findPulishName(Integer pulishId);

}
