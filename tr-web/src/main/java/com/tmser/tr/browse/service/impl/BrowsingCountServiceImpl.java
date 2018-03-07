/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.browse.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmser.tr.browse.bo.BrowsingCount;
import com.tmser.tr.browse.dao.BrowsingCountDao;
import com.tmser.tr.browse.service.BrowsingCountService;
import com.tmser.tr.common.dao.BaseDAO;
import com.tmser.tr.common.service.AbstractService;
/**
 * 资源浏览总数记录 服务实现类
 * <pre>
 *
 * </pre>
 *
 * @author zpp
 * @version $Id: BrowsingCount.java, v 1.0 2015-11-11 zpp Exp $
 */
@Service
@Transactional
public class BrowsingCountServiceImpl extends AbstractService<BrowsingCount, Integer> implements BrowsingCountService {

	@Autowired
	private BrowsingCountDao browsingCountDao;

	/**
	 * @return
	 * @see com.tmser.tr.common.service.BaseService#getDAO()
	 */
	@Override
	public BaseDAO<BrowsingCount, Integer> getDAO() {
		return browsingCountDao;
	}

	/**
	 * 通过资源类型和资源ID获得浏览次数
	 * @param type
	 * @param resId
	 * @return
	 * @see com.tmser.tr.browse.service.BrowsingCountService#getCount(java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public int getCount(Integer type, Integer resId) {
		BrowsingCount bc = new BrowsingCount();
		bc.setType(type);
		bc.setResId(resId);
		bc.addCustomCulomn("count");
		bc = browsingCountDao.getOne(bc);
		if(bc!=null){
			return bc.getCount();
		}
		return 0;
	}

}
