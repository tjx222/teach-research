/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.schconfig.clss.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmser.tr.common.dao.BaseDAO;
import com.tmser.tr.common.service.AbstractService;
import com.tmser.tr.schconfig.clss.bo.SchClass;
import com.tmser.tr.schconfig.clss.bo.SchClassUser;
import com.tmser.tr.schconfig.clss.dao.SchClassDao;
import com.tmser.tr.schconfig.clss.service.SchClassService;
import com.tmser.tr.schconfig.clss.service.SchClassUserService;

/**
 * 学校班级 服务实现类
 * <pre>
 *
 * </pre>
 *
 * @author tmser
 * @version $Id: SchClass.java, v 1.0 2016-02-19 tmser Exp $
 */
@Service
@Transactional
public class SchClassServiceImpl extends AbstractService<SchClass, Integer> implements SchClassService {

	@Autowired
	private SchClassDao schClassDao;
	@Autowired
	private SchClassUserService schClassUserService;
	
	/**
	 * @return
	 * @see com.tmser.tr.common.service.BaseService#getDAO()
	 */
	@Override
	public BaseDAO<SchClass, Integer> getDAO() {
		return schClassDao;
	}

	@Override
	public SchClassUser getUserIdByGradeAndBanJi(Integer gradeId,Integer subjectId,Integer banjiId) {
		SchClassUser classUser = new SchClassUser();
		classUser.setClassId(banjiId);
		classUser.setSubjectId(subjectId);
		return  schClassUserService.findOne(classUser);				
	}

	@Override
	public List<SchClass> getSchByGradeIdAndOrgId(Integer gradeId, Integer orgId, Integer status) {
		SchClass sch = new SchClass();
		sch.setGradeId(gradeId);
		sch.setOrgId(orgId);
		if(status==0 || status==1){
			sch.setEnable(status);
		}
		sch.addOrder(" sort ASC ");
		List<SchClass> listAll = schClassDao.listAll(sch);
		return listAll;
	}

}
