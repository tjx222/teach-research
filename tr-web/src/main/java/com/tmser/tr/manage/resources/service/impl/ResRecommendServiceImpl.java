package com.tmser.tr.manage.resources.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmser.tr.common.dao.BaseDAO;
import com.tmser.tr.common.service.AbstractService;
import com.tmser.tr.manage.resources.bo.ResRecommend;
import com.tmser.tr.manage.resources.dao.ResRecommendDao;
import com.tmser.tr.manage.resources.service.ResRecommendService;
/**
 * 
 * 推荐资源
 * @author csj
 * @version $Id: ResRecommendServiceImpl.java, v 1.0 2015年2月11日 上午11:13:25 csj Exp $
 */
@Service
@Transactional
public class ResRecommendServiceImpl extends AbstractService<ResRecommend, Integer> implements ResRecommendService {

	@Autowired
	private ResRecommendDao resRecommendDao;
	
	@Override
	public BaseDAO<ResRecommend, Integer> getDAO() {
		return resRecommendDao;
	}
	

}
