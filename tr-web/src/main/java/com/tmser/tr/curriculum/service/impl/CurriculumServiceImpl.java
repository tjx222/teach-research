package com.tmser.tr.curriculum.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmser.tr.common.dao.BaseDAO;
import com.tmser.tr.common.service.AbstractService;
import com.tmser.tr.curriculum.bo.Curriculum;
import com.tmser.tr.curriculum.dao.CurriculumDao;
import com.tmser.tr.curriculum.service.CurriculumService;

/**
 * 课程表 服务实现类
 * 
 * @author hu'yan'fang
 * @version 1.0 2015-01-30
 */
@Service
@Transactional
public class CurriculumServiceImpl extends AbstractService<Curriculum, Integer> implements CurriculumService {

	@Autowired
	private CurriculumDao curriculumDao;

	@Override
	public BaseDAO<Curriculum, Integer> getDAO() {
		return curriculumDao;
	}

}
