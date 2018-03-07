/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.manage.meta.dao;

import java.util.List;

import com.tmser.tr.common.dao.BaseDAO;
import com.tmser.tr.manage.meta.bo.BookChapter;

 /**
 * 书籍的章节目录 DAO接口
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: CommdityBookChapter.java, v 1.0 2015-02-04 Generate Tools Exp $
 */
public interface BookChapterDao extends BaseDAO<BookChapter, String>{
	/**
	 * 查询书章节，并且查询是否有子节点
	 * @param comId
	 * @param parentId
	 * @return
	 */
	List<BookChapter> listBookChapterWithChildState(String comId,String parentId);
}