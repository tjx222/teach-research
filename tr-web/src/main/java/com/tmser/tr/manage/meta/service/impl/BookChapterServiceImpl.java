/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.manage.meta.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmser.tr.common.dao.BaseDAO;
import com.tmser.tr.common.service.AbstractService;
import com.tmser.tr.manage.meta.bo.BookChapter;
import com.tmser.tr.manage.meta.dao.BookChapterDao;
import com.tmser.tr.manage.meta.service.BookChapterService;
/**
 * 书籍的章节目录 服务实现类
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: CommdityBookChapter.java, v 1.0 2015-02-04 Generate Tools Exp $
 */
@Service
@Transactional
public class BookChapterServiceImpl extends AbstractService<BookChapter, String> implements BookChapterService {

	@Autowired
	private BookChapterDao bookChapterDao;
	
	/**
	 * @return
	 * @see com.tmser.tr.common.service.BaseService#getDAO()
	 */
	@Override
	public BaseDAO<BookChapter, String> getDAO() {
		return bookChapterDao;
	}

}
