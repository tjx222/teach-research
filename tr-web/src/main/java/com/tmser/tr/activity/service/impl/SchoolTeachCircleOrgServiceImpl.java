/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.activity.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmser.tr.activity.bo.SchoolTeachCircleOrg;
import com.tmser.tr.activity.dao.SchoolTeachCircleOrgDao;
import com.tmser.tr.activity.service.SchoolTeachCircleOrgService;
import com.tmser.tr.common.dao.BaseDAO;
import com.tmser.tr.common.service.AbstractService;
import com.tmser.tr.uc.service.SchoolYearService;

/**
 * 校际教研圈附属机构 服务实现类
 * <pre>
 *
 * </pre>
 *
 * @author zpp
 * @version $Id: SchoolTeachCircleOrg.java, v 1.0 2015-05-12 zpp Exp $
 */
@Service
@Transactional
public class SchoolTeachCircleOrgServiceImpl extends AbstractService<SchoolTeachCircleOrg, Integer> implements SchoolTeachCircleOrgService {

	@Autowired
	private SchoolTeachCircleOrgDao schoolTeachCircleOrgDao;
	@Resource
	private SchoolYearService schoolYearService;
	
	/**
	 * @return
	 * @see com.tmser.tr.common.service.BaseService#getDAO()
	 */
	@Override
	public BaseDAO<SchoolTeachCircleOrg, Integer> getDAO() {
		return schoolTeachCircleOrgDao;
	}

	/**
	 * 根据指定的状态，判断机构是否满足指定的教研圈
	 * @param orgId
	 * @param schoolTeachCircleId
	 * @param stateArray
	 * @return
	 */
	@Override
	public boolean ifMatchTheCircleByStates(Integer orgId, Integer schoolTeachCircleId,Integer[] stateArray) {
		SchoolTeachCircleOrg stco = new SchoolTeachCircleOrg();
		stco.setOrgId(orgId);
		stco.setStcId(schoolTeachCircleId);
		if(stateArray!=null && stateArray.length>0){
			Map<String,Object> paramMap = new HashMap<String,Object>();
			String condition = "state=:state0";
			paramMap.put("state0",stateArray[0]);
			for(int i=1;i<stateArray.length;i++){
				condition += " or state=:state"+i;
				paramMap.put("state"+i,stateArray[i]);
			}
			stco.addCustomCondition(" and ("+condition+")", paramMap);
		}
		int count = count(stco);
		if(count>0){
			return true;
		}else{
			return false;
		}
	}

	/**
	 * 获取所有参与的学校的名称
	 * @param schoolTeachCircleId
	 * @return
	 * @see com.tmser.tr.activity.service.SchoolTeachCircleOrgService#getJoinOrgNamesByCircleId(java.lang.Integer)
	 */
	@Override
	public String getJoinOrgNamesByCircleId(Integer schoolTeachCircleId) {
		SchoolTeachCircleOrg stco = new SchoolTeachCircleOrg();
		stco.setStcId(schoolTeachCircleId);
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("state1", SchoolTeachCircleOrg.YI_TONG_YI);
		paramMap.put("state2", SchoolTeachCircleOrg.YI_HUI_FU);
		stco.addCustomCondition(" and (state=:state1 or state=:state2)", paramMap);
		List<SchoolTeachCircleOrg> orgList = findAll(stco);
		String joinOrgNames = "";
		if(orgList.size()>0){
			for(SchoolTeachCircleOrg org:orgList){
				joinOrgNames += org.getOrgName()+"、";
			}
			joinOrgNames = joinOrgNames.substring(0, joinOrgNames.length()-1);
		}
		
		return joinOrgNames;
	}
	
	/**
	 * 获取所有参与的学校的id
	 * @param schoolTeachCircleId
	 * @return
	 * @see com.tmser.tr.activity.service.SchoolTeachCircleOrgService#getJoinOrgNamesByCircleId(java.lang.Integer)
	 */
	@Override
	public List<Integer> getJoinOrgIdsByCircleId(Integer schoolTeachCircleId) {
		SchoolTeachCircleOrg stco = new SchoolTeachCircleOrg();
		stco.setStcId(schoolTeachCircleId);
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("state1", SchoolTeachCircleOrg.YI_TONG_YI);
		paramMap.put("state2", SchoolTeachCircleOrg.YI_HUI_FU);
		stco.addCustomCondition(" and (state=:state1 or state=:state2)", paramMap);
		List<SchoolTeachCircleOrg> orgList = findAll(stco);
		List<Integer> joinOrgIds = new ArrayList<Integer>();
		if(orgList.size()>0){
			for(SchoolTeachCircleOrg org:orgList){
				joinOrgIds.add(org.getOrgId());
			}
		}
		return joinOrgIds;
	}

	/**
	 * 获取某机构（学校）所参与的教研圈
	 * @param orgId
	 * @return
	 * @see com.tmser.tr.activity.service.SchoolTeachCircleOrgService#getCircleByOrgId(java.lang.Integer)
	 */
	@Override
	public List<Integer> getCircleByOrgId(Integer orgId) {
		List<Integer> stcIds = new ArrayList<Integer>();
		if(orgId!=null){
			SchoolTeachCircleOrg stco = new SchoolTeachCircleOrg();
			stco.setOrgId(orgId);
			Map<String,Object> paramMap = new HashMap<String,Object>();
			paramMap.put("state1", SchoolTeachCircleOrg.YI_TONG_YI);
			paramMap.put("state2", SchoolTeachCircleOrg.YI_HUI_FU);
			stco.addCustomCondition(" and (state=:state1 or state=:state2)", paramMap);
			stco.setSchoolYear(schoolYearService.getCurrentSchoolYear());
			stco.addCustomCulomn("distinct(stcId)");
			List<SchoolTeachCircleOrg> findAll = findAll(stco);
			if(findAll.size()>0){
				for(SchoolTeachCircleOrg org:findAll){
					stcIds.add(org.getStcId());
				}
			}
		}
		return stcIds;
	}

}
