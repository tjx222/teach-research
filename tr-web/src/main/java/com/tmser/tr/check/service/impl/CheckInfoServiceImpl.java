/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.check.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.util.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmser.tr.check.bo.CheckInfo;
import com.tmser.tr.check.bo.CheckOpinion;
import com.tmser.tr.check.dao.CheckInfoDao;
import com.tmser.tr.check.dao.CheckOpinionDao;
import com.tmser.tr.check.service.CheckInfoService;
import com.tmser.tr.common.ResTypeConstants;
import com.tmser.tr.common.dao.BaseDAO;
import com.tmser.tr.common.service.AbstractService;
import com.tmser.tr.common.utils.WebThreadLocalUtils;
import com.tmser.tr.uc.utils.SessionKey;
import com.tmser.tr.utils.JyAssert;
/**
 * 查阅基础信息 服务实现类
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: CheckInfo.java, v 1.0 2015-03-14 Generate Tools Exp $
 */
@Service
@Transactional
public class CheckInfoServiceImpl extends AbstractService<CheckInfo, Integer> implements CheckInfoService {
	private static Logger logger = LoggerFactory.getLogger(CheckInfoServiceImpl.class);
	@Autowired
	private CheckInfoDao checkInfoDao;
	
	
	@Autowired
	private CheckOpinionDao checkOpinionDao;
	/**
	 * @return
	 * @see com.tmser.tr.common.service.BaseService#getDAO()
	 */
	@Override
	public BaseDAO<CheckInfo, Integer> getDAO() {
		return checkInfoDao;
	}

	@Override
	public Integer deleteCheckOptionOfLessonPlan(Integer resId) {
		JyAssert.notNull(resId, "check resourse id and type can't be null");
		
		Integer size = 0;
		CheckInfo model = new CheckInfo();
		model.setResType(ResTypeConstants.JIAOAN);
		model.setResId(resId);
		
		List<CheckInfo> cklist = checkInfoDao.listAll(model);
		size = cklist !=null ? cklist.size() : 0 ;
		if(CollectionUtils.isEmpty(cklist)){
			for(CheckInfo ck : cklist){
				CheckOpinion opinionModel = new CheckOpinion();
				opinionModel.setCheckInfoId(ck.getId());
				List<CheckOpinion> opList = checkOpinionDao.listAll(opinionModel);
				for(CheckOpinion op : opList){
					checkOpinionDao.delete(op.getId());
				}
				checkInfoDao.delete(ck.getId());
			}
		}
		
		return size;
	}

	/**
	 * @param resid
	 * @param restype
	 * @return
	 * @see com.tmser.tr.check.service.CheckInfoService#updateCheckInfoUpdateState(java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public boolean updateCheckInfoUpdateState(Integer resId, Integer restype) {
			JyAssert.notNull(resId, "check resourse id can't be null");
			JyAssert.notNull(restype, "check resourse type can't be null");
			Integer updatenum = checkInfoDao.updateCheckInfoUpdateState(resId,restype,
					(Integer)WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SCHOOLYEAR));
			logger.debug("update effect num : {}",updatenum);
		
		return true;
	}
	/**
	 * 查询某一学年某一撰写人某一类型的所有资源
	 * @param schoolYear
	 * @param resType
	 * @param authorId 撰写者
	 * @return
	 */
	@Override
	public Map<Integer,Object> getCheckData(Integer schoolYear,Integer resType,Integer authorId) {
		Map<Integer,Object> map = new HashMap<Integer,Object>();
		CheckInfo check = new CheckInfo();
		check.setResType(resType);
		check.setAuthorId(authorId);
		check.setSchoolYear(schoolYear);
		List<CheckInfo> findAll = checkInfoDao.listAll(check);
		for (CheckInfo checkInfo : findAll) {
			map.put(checkInfo.getResId(), checkInfo.getResId());
		}
		return map;
	}
}
