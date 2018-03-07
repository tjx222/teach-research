/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */

package com.tmser.tr.manage.meta.dao;

import java.util.List;

import com.tmser.tr.common.dao.BaseDAO;
import com.tmser.tr.manage.meta.bo.Book;

/**
 * 书籍对象 DAO接口
 * 
 * <pre>
 *
 * </pre>
 *
 * @author zpp
 * @version $Id: Commidity.java, v 1.0 2015-02-06 zpp Exp $
 */
public interface BookDao extends BaseDAO<Book, String> {

  /**
   * 通过书籍id批量查询书籍
   * 
   * @param bookIds
   *          书籍id
   * @return List
   */
  List<Book> findByComIds(List<String> bookIds);

  /**
   * 删除relationComId 为
   * 
   * @param comId
   *          的数据的关联状态
   * @return Integer
   */
  Integer delRelationComId(String comId);

}