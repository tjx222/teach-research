/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.back.xxsy.intro.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmser.tr.back.xxsy.intro.service.SchoolShowService;
import com.tmser.tr.common.dao.BaseDAO;
import com.tmser.tr.common.service.AbstractService;
import com.tmser.tr.school.show.bo.SchoolShow;
import com.tmser.tr.school.show.dao.SchoolShowDao;
/**
 * 学校展示数据 服务实现类
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: SchoolShow.java, v 1.0 2015-09-22 Generate Tools Exp $
 */
@Service
@Transactional
public class SchoolShowServiceImpl extends AbstractService<SchoolShow, String> implements SchoolShowService {

	@Autowired
	private SchoolShowDao schoolShowDao;
	
	/**
	 * @return
	 * @see com.tmser.tr.common.service.BaseService#getDAO()
	 */
	@Override
	public BaseDAO<SchoolShow, String> getDAO() {
		return schoolShowDao;
	}


	

}
