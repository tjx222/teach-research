/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.manage.publishrelationship.service;

import java.util.List;

import com.tmser.tr.common.service.BaseService;
import com.tmser.tr.manage.meta.bo.BookSync;
import com.tmser.tr.manage.meta.bo.PublishRelationship;

/**
 * <pre>
 * 出版社关联关系管理的service层接口
 * </pre>
 *
 * @author ghw
 * @version $Id: PublishRelationshipService.java, v 1.0 2015-08-27 ghw Exp $
 */

public interface PublishRelationshipService extends BaseService<PublishRelationship, Integer> {

	/**
	 * 通过学段学科年级查询出版社信息集合,从book表中查出相关数据
	 * 
	 * @param xdid
	 * @param xkid
	 * @return
	 */
	List<BookSync> findListByXDXKNj(Integer phaseId, Integer subjectId, Integer grade);
}
