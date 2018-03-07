/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */

package com.tmser.tr.manage.meta.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.tmser.tr.common.dao.AbstractDAO;
import com.tmser.tr.common.dao.annotation.UseCache;
import com.tmser.tr.manage.meta.bo.Book;
import com.tmser.tr.manage.meta.dao.BookDao;

/**
 * 书籍对象 Dao 实现类
 * 
 * <pre>
 *
 * </pre>
 *
 * @author zpp
 * @version $Id: Commidity.java, v 1.0 2015-02-06 zpp Exp $
 */
@Repository
@UseCache
public class BookDaoImpl extends AbstractDAO<Book, String> implements BookDao {

  /**
   * 通过书籍id批量查询书籍
   * 
   * @param bookIds
   *          书籍id集合
   * @return List
   * @see com.tmser.tr.manage.meta.dao.BookDao#findByComIds(java.util.List)
   */
  @Override
  public List<Book> findByComIds(List<String> bookIds) {
    String sql = "select * from Book where comId in (:bookIds)";
    Map<String, Object> args = new HashMap<String, Object>();
    args.put("bookIds", bookIds);
    return this.queryByNamedSql(sql, args, this.mapper);
  }

  @Override
  public Integer delRelationComId(String comId) {
    String sql = "update Book set relationComId = '' where relationComId = ?";
    return this.update(sql, comId);
  }

}
