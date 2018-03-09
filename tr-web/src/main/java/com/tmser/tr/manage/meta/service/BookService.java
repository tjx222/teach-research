/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.manage.meta.service;

import com.tmser.tr.common.service.BaseService;
import com.tmser.tr.manage.meta.bo.Book;

/**
 * 书籍对象 服务类
 * <pre>
 *
 * </pre>
 *
 * @author tmser
 * @version $Id: Commidity.java, v 1.0 2015-02-06 tmser Exp $
 */

public interface BookService extends BaseService<Book, String>{
	Book getBookByLessonId(String lessonId);
}
