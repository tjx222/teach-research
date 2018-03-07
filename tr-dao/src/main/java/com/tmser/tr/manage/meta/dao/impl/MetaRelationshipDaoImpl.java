/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.manage.meta.dao.impl;

import org.springframework.stereotype.Repository;

import com.tmser.tr.common.dao.AbstractDAO;
import com.tmser.tr.manage.meta.bo.MetaRelationship;
import com.tmser.tr.manage.meta.dao.MetaRelationshipDao;

/**
 * 元数据关系表 Dao 实现类
 * <pre>
 *
 * </pre>
 *
 * @author tmser
 * @version $Id: MetaRelationship.java, v 1.0 2015-03-11 tmser Exp $
 */
@Repository
public class MetaRelationshipDaoImpl extends AbstractDAO<MetaRelationship,Integer> implements MetaRelationshipDao {

	/**
	 * @param eid
	 * @param type
	 * @return
	 * @see com.tmser.tr.manage.meta.dao.MetaRelationshipDao#getByEidAndType(java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public MetaRelationship getByEidAndType(Integer eid, Integer type,Integer sort) {
		MetaRelationship model = new MetaRelationship();
		model.setEid(eid);
		model.setType(type);
		model.setSort(sort);
		return this.getOne(model);
	}

}
