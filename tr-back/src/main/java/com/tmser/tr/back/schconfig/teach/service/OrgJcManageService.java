package com.tmser.tr.back.schconfig.teach.service;

import java.util.List;
import java.util.Map;

import com.tmser.tr.manage.meta.bo.Book;
import com.tmser.tr.manage.meta.bo.BookSync;

public interface OrgJcManageService {

  /**
   * 查找教材树形结构
   * 
   * @param orgId
   *          学校id
   * @return list of map
   */
  List<Map<String, Object>> findOrgJCtree(Integer orgId);

  /**
   * 查找教材树形结构
   * 
   * @param areaId
   *          区域id
   * @return list of map
   */
  List<Map<String, Object>> findAreaJCtree(Integer areaId);

  /**
   * 
   * @param book
   *          com.mainbo.platform.meta.bo.BookSync
   * @return list of com.mainbo.platform.meta.bo.BookSync
   */
  List<BookSync> findBookSync(BookSync book);

  /**
   * 
   * @param id
   *          booksync id
   * @param orgId
   *          学校id
   * @param areaId
   *          区域id
   */
  void deleteBookSyncById(Integer id, Integer orgId, Integer areaId);

  /**
   * 查找机构未同步的教材
   * 
   * @param bookSync
   *          com.mainbo.platform.meta.bo.BookSync
   * @return list of com.mainbo.platform.meta.bo.Book
   */
  List<Book> findUnAddBooks(BookSync bookSync);

}
