/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.manage.publishrelationship.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmser.tr.common.dao.BaseDAO;
import com.tmser.tr.common.service.AbstractService;
import com.tmser.tr.manage.meta.bo.BookSync;
import com.tmser.tr.manage.meta.bo.PublishRelationship;
import com.tmser.tr.manage.meta.dao.BookSyncDao;
import com.tmser.tr.manage.meta.dao.PublishRelationshipDao;
import com.tmser.tr.manage.publishrelationship.service.PublishRelationshipService;

/**
 * <pre>
 * 出版社关联关系管理的service层实现类
 * </pre>
 *
 * @author ghw
 * @version $Id: PublishRelationshipServiceImpl.java, v 1.0 2015-08-27 ghw Exp $
 */
@Service
@Transactional
public class PublishRelationshipServiceImpl extends AbstractService<PublishRelationship, Integer> implements PublishRelationshipService {

	@Autowired
	private PublishRelationshipDao publishRelationshipDao;
	@Autowired
	private BookSyncDao bookdao;

	/**
	 * 通过学段学科年级查询出版社信息集合,从book表中查出相关数据
	 * 
	 * @param xdid
	 * @param xkid
	 * @return
	 * @see com.tmser.tr.back.jxtx.service.PublishRelationshipService#findListByXDXK(java.lang.Integer,
	 *      java.lang.Integer)
	 */
	@Override
	public List<BookSync> findListByXDXKNj(Integer phaseId, Integer subjectId, Integer grade) {
		BookSync bk = new BookSync();
		if (phaseId != null) {
			bk.setPhaseId(phaseId);
		}
		if (subjectId != null) {
			bk.setSubjectId(subjectId);
		}
		if (grade != null) {
			bk.setGradeLevelId(grade);
		}
		bk.addCustomCulomn(" distinct formatName");
		List<BookSync> returnList = new ArrayList<BookSync>();
		returnList = bookdao.listAll(bk);
		return returnList;
	}

	/**
	 * @return
	 * @see com.tmser.tr.common.service.BaseService#getDAO()
	 */
	@Override
	public BaseDAO<PublishRelationship, Integer> getDAO() {
		return publishRelationshipDao;
	}

}
