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
import com.tmser.tr.manage.meta.bo.Book;
import com.tmser.tr.manage.meta.dao.BookDao;
import com.tmser.tr.manage.meta.service.BookService;
/**
 * 书籍对象 服务实现类
 * <pre>
 *
 * </pre>
 *
 * @author zpp
 * @version $Id: Commidity.java, v 1.0 2015-02-06 zpp Exp $
 */
@Service
@Transactional
public class BookServiceImpl extends AbstractService<Book, String> implements BookService {

	@Autowired
	private BookDao commidityDao;
	
	/**
	 * @return
	 * @see com.tmser.tr.common.service.BaseService#getDAO()
	 */
	@Override
	public BaseDAO<Book, String> getDAO() {
		return commidityDao;
	}

	/**
	 * @param lessonId
	 * @return
	 * @see com.tmser.tr.manage.meta.service.BookService#getBookByLessonId(java.lang.String)
	 */
	@Override
	public Book getBookByLessonId(String lessonId) {
		Book model  = new Book();
		model.addAlias("b");
		model.setComType(1);//电子教材
		model.buildCondition(" and b.comId in (select c.comId from BookChapter c where c.chapterId=:chapterId)")
		.put("chapterId", lessonId);
		return commidityDao.getOne(model);
	}

}
