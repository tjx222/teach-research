/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.annunciate.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import com.tmser.tr.common.dao.BaseDAO;
import com.tmser.tr.annunciate.bo.JyRedTitle;
import com.tmser.tr.common.service.AbstractService;
import com.tmser.tr.annunciate.service.JyRedTitleService;
import com.tmser.tr.annunciate.dao.JyRedTitleDao;
/**
 * 红头文件表头 服务实现类
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: JyRedTitle.java, v 1.0 2015-06-12 Generate Tools Exp $
 */
@Service
@Transactional
public class JyRedTitleServiceImpl extends AbstractService<JyRedTitle, Integer> implements JyRedTitleService {

	@Autowired
	private JyRedTitleDao jyRedTitleDao;
	
	/**
	 * @return
	 * @see com.tmser.tr.common.service.BaseService#getDAO()
	 */
	@Override
	public BaseDAO<JyRedTitle, Integer> getDAO() {
		return jyRedTitleDao;
	}

}
