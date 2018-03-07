/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.manage.meta.dao;

import com.tmser.tr.common.dao.BaseDAO;
import com.tmser.tr.manage.meta.bo.MetaRelationship;

 /**
 * 元数据关系表 DAO接口
 * <pre>
 *
 * </pre>
 *
 * @author tmser
 * @version $Id: MetaRelationship.java, v 1.0 2015-03-11 tmser Exp $
 */
public interface MetaRelationshipDao extends BaseDAO<MetaRelationship, Integer>{
	/**
	 * 根据eid 和 type 查找相关的关系实体
	 * @param eid
	 * @param type
	 * @return
	 */
	MetaRelationship getByEidAndType(Integer eid,Integer type,Integer sort);
}