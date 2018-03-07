/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.lessonplan.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import com.tmser.tr.common.dao.BaseDAO;
import com.tmser.tr.lessonplan.bo.LessonInfo;
import com.tmser.tr.common.service.AbstractService;
import com.tmser.tr.lessonplan.service.LessonInfoService;
import com.tmser.tr.lessonplan.dao.LessonInfoDao;
/**
 * 备课信息 服务实现类
 * <pre>
 *
 * </pre>
 *
 * @author tmser
 * @version $Id: LessonInfo.java, v 1.0 2015-03-12 tmser Exp $
 */
@Service
@Transactional
public class LessonInfoServiceImpl extends AbstractService<LessonInfo, Integer> implements LessonInfoService {

	@Autowired
	private LessonInfoDao lessonInfoDao;
	
	/**
	 * @return
	 * @see com.tmser.tr.common.service.BaseService#getDAO()
	 */
	@Override
	public BaseDAO<LessonInfo, Integer> getDAO() {
		return lessonInfoDao;
	}

}
