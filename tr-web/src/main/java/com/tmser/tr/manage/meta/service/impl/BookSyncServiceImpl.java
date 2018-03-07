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
import com.tmser.tr.manage.meta.bo.BookSync;
import com.tmser.tr.manage.meta.dao.BookSyncDao;
import com.tmser.tr.manage.meta.service.BookSyncService;

/**
 * <pre>
 *
 * </pre>
 *
 * @author 3020mt
 * @version $Id: BookSyncServiceImpl.java, v 1.0 2017年2月22日 下午3:45:56 3020mt Exp
 *          $
 */
@Service
@Transactional
public class BookSyncServiceImpl extends AbstractService<BookSync, Integer> implements BookSyncService {

	@Autowired
	private BookSyncDao bookSyncDao;

	@Override
	public BaseDAO<BookSync, Integer> getDAO() {
		return bookSyncDao;
	}

}
