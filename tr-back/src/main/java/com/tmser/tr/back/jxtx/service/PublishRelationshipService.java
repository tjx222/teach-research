/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.back.jxtx.service;

import java.util.List;

import com.tmser.tr.common.service.BaseService;
import com.tmser.tr.common.vo.Datas;
import com.tmser.tr.manage.meta.Meta;
import com.tmser.tr.manage.meta.bo.PublishRelationship;

/**
 * <pre>
 *	出版社关联关系管理的service层接口
 * </pre>
 *
 * @author zpp
 * @version $Id: PublishRelationshipService.java, v 1.0 2015-08-27 zpp Exp $
 */

public interface PublishRelationshipService extends BaseService<PublishRelationship, Integer>{


	/**
	 * 从元数据中查找出来出版社信息
	 * @param pr
	 * @return
	 */
	List<Meta> findCBSFromSD(PublishRelationship pr);

	/**
	 * 保存出版社信息
	 * @param pr
	 * @param objArr
	 */
	void saveCbs(Datas publishs, PublishRelationship pr);

}
