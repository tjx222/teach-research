/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.back.jxtx.service;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.tmser.tr.manage.meta.bo.BookChapter;

/**
 * <pre>
 * 
 * </pre>
 * 
 * @author zpp
 * @version $Id: JXTXBaseManageService.java, v 1.0 2015年8月24日 下午4:21:09 zpp Exp
 *          $
 */
public interface JXTXBaseManageService {

  /**
   * 查询学段学科树
   * 
   * @return
   */
  List<Map<String, Object>> findXDXKtree();

  /**
   * 查询封装目录树形结构
   * 
   * @return
   */
  List<Map<String, Object>> findCatalogTree();

  /**
   * 获得书籍目录树型结构
   * 
   * @return
   */
  List<Map<String, Object>> findBookCatalogTree(String comId);

  /**
   * 导出目录
   * 
   * @param file
   * @param areaId
   * @param orgType
   */
  File exportChapter(String comId);

  /**
   * 删除导出的目录节点信息
   * 
   * @param comId
   */
  void delExportChapter(String comId);

  /**
   * 查找教材树形结构
   * 
   * @return
   */
  List<Map<String, Object>> findJCtree();

  List<BookChapter> findBookCatalogTreeForZtree(String comId);

}
