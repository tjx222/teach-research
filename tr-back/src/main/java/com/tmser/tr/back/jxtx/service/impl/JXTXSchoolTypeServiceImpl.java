package com.tmser.tr.back.jxtx.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmser.tr.back.jxtx.exception.SchoolTypeNameAlreadyExistException;
import com.tmser.tr.back.jxtx.service.JXTXSchoolTypeService;
import com.tmser.tr.manage.meta.Meta;
import com.tmser.tr.manage.meta.MetaUtils;
import com.tmser.tr.manage.meta.bo.MetaRelationship;
import com.tmser.tr.manage.meta.dao.MetaRelationshipDao;

/**
 * 
 * <pre>
 *
 * </pre>
 *
 * @author 川子
 * @version $Id: SchTypeManagerServiceImpl.java, v 1.0 2015年8月25日 上午10:56:56 川子 Exp $
 */
@Service
@Transactional
public class JXTXSchoolTypeServiceImpl implements JXTXSchoolTypeService{

	@Autowired
	private MetaRelationshipDao metaRelationshipDao;
	
	/**
	 * 删除学校类型
	 * @param id
	 * @see com.tmser.tr.back.jxtx.service.JXTXSchoolTypeService#delSchType(java.lang.Integer)
	 */
	@Override
	public void delSchType(Integer id) {
		metaRelationshipDao.delete(id);
	}

	@Override
	public Map<String, Object> goSaveOrUpdate(Integer id) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		// 查询所有学段
		List<MetaRelationship> phaseMshipList = MetaUtils.getPhaseMetaProvider().listAll();
		resultMap.put("xdList", phaseMshipList);
		Map<Integer, List<Meta>> xdNjMap = new HashMap<Integer, List<Meta>>();
		for (MetaRelationship metaRelationship : phaseMshipList) {
			List<Meta> gradeList = MetaUtils.getPhaseGradeMetaProvider().listAllGradeByPhaseId(metaRelationship.getId());
			xdNjMap.put(metaRelationship.getId(), gradeList);
		}
		resultMap.put("xdnjMap", xdNjMap);
		if(id!= null){
			MetaRelationship mrp = this.metaRelationshipDao.get(id);
			resultMap.put("mrp", mrp);
		}
		return resultMap;
	}

	@Override
	public void addOrUpdateSchType(MetaRelationship mr) {
		
		mr.setType(MetaRelationship.T_ORG_TYPE);
		if(mr.getId()!=null){
			metaRelationshipDao.update(mr);
			MetaUtils.getOrgTypeMetaProvider().evictCache(mr.getId());
		}else{
			MetaRelationship model = new MetaRelationship();
			model.setType(MetaRelationship.T_ORG_TYPE);
			model.setName(mr.getName());
			if (metaRelationshipDao.getOne(model) != null) {
				throw new SchoolTypeNameAlreadyExistException();
			}
			metaRelationshipDao.insert(mr);
		}
	}
	
}
