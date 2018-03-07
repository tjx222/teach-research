/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.manage.org.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmser.tr.common.dao.BaseDAO;
import com.tmser.tr.common.orm.SqlMapping;
import com.tmser.tr.common.service.AbstractService;
import com.tmser.tr.common.utils.WebThreadLocalUtils;
import com.tmser.tr.manage.meta.Meta;
import com.tmser.tr.manage.meta.MetaUtils;
import com.tmser.tr.manage.meta.bo.MetaRelationship;
import com.tmser.tr.manage.org.bo.Area;
import com.tmser.tr.manage.org.bo.Organization;
import com.tmser.tr.manage.org.bo.OrganizationRelationship;
import com.tmser.tr.manage.org.dao.AreaDao;
import com.tmser.tr.manage.org.dao.OrganizationDao;
import com.tmser.tr.manage.org.service.AreaService;
import com.tmser.tr.manage.org.service.OrganizationRelationshipService;
import com.tmser.tr.manage.org.service.OrganizationService;
import com.tmser.tr.manage.resources.service.ResourcesService;
import com.tmser.tr.uc.bo.User;
import com.tmser.tr.uc.bo.UserSpace;
import com.tmser.tr.uc.utils.CurrentUserContext;
import com.tmser.tr.uc.utils.SessionKey;
import com.tmser.tr.utils.StringUtils;

/**
 * 机构 服务实现类
 * 
 * <pre>
 * 
 * </pre>
 * 
 * @author tmser
 * @version $Id: OrganizationServiceImpl.java, v 1.0 2015-03-19 tmser Exp $
 */
@Service
@Transactional
public class OrganizationServiceImpl extends AbstractService<Organization, Integer> implements OrganizationService {

	@Autowired
	private OrganizationDao organizationDao;
	
	@Autowired
	private AreaDao areaDao;
	@Autowired
	private OrganizationRelationshipService organizationRelationshipService;
	@Autowired
	private AreaService areaService;
	@Autowired
	private ResourcesService resourcesService;
	/**
	 * @return
	 * @see com.tmser.tr.common.service.BaseService#getDAO()
	 */
	@Override
	public BaseDAO<Organization, Integer> getDAO() {
		return organizationDao;
	}

	/**
	 * 通过学校名称检索学校
	 * 
	 * @param schoolName
	 * @return
	 * @see com.tmser.tr.manage.org.service.OrganizationService#findOrgByName(java.lang.String)
	 */
	@Override
	public List<Organization> findOrgByName(String schoolName) {
		UserSpace userSpace = (UserSpace) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE); // 用户空间
		Organization org = new Organization();
		org.setName(SqlMapping.LIKE_PRFIX + schoolName + SqlMapping.LIKE_PRFIX);
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("orgId", userSpace.getOrgId());
		org.addCustomCondition("and id != :orgId", paramMap);
		org.addCustomCulomn("id,name");
		List<Organization> listAll = organizationDao.listAll(org);
		return listAll;
	}

	/**
	 * 通过id连城的字符串（如：,1,22,333,）获取机构集合
	 * 
	 * @param orgsJoinIds
	 * @return
	 * @see com.tmser.tr.manage.org.service.OrganizationService#getOrgListByIdsStr(java.lang.String)
	 */
	@Override
	public List<Organization> getOrgListByIdsStr(String orgsJoinIds) {
		List<Organization> orgList = null;
		if (orgsJoinIds != null && !"".equals(orgsJoinIds)) {
			orgList = new ArrayList<Organization>();
			String[] idsArray = orgsJoinIds.substring(1, orgsJoinIds.length() - 1).split(",");
			for (String id : idsArray) {
				Organization org = organizationDao.get(Integer.valueOf(id));
				orgList.add(org);
			}
		}
		return orgList;
	}

	/**
	 * 通过区域id获得所有机构
	 * 
	 * @param areaId
	 * @param type
	 * @return
	 * @see com.tmser.tr.manage.org.service.OrganizationService#getOrgByAreaId(java.lang.Integer)
	 */
	@Override
	public List<Organization> getOrgByAreaId(Integer areaId, Integer type) {
		Organization org = new Organization();
		org.setAreaId(areaId);
		org.addCustomCulomn("id,shortName,name");
		if (type == null) {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("param1", 0);
			paramMap.put("param2", 1);
			org.addCustomCondition(" and (type = :param1 or type = :param2)", paramMap);
		} else {
			org.setType(type);// 学校、机构
		}
		List<Organization> listAll = organizationDao.listAll(org);
		return listAll;
	}

	/**
	 * 通过名称检索出所有的机构
	 * 
	 * @param name
	 * @param type
	 * @return
	 * @see com.tmser.tr.manage.org.service.OrganizationService#getAllOrgByName(java.lang.String)
	 */
	@Override
	public Map<String, Object> getAllOrgByName(String name, Integer type) {
		Organization org = new Organization();
		Map<String, Object> map = new HashMap<String, Object>();
		List<Area> areaList = new ArrayList<Area>();
		org.setType(type);// 学校、部门...
		org.setName(SqlMapping.LIKE_PRFIX + name + SqlMapping.LIKE_PRFIX);
		org.addCustomCulomn("id,shortName,name,areaIds,areaId,areaName");
		List<Organization> listAll = organizationDao.listAll(org);
		for (Organization orgTemp : listAll) {
			String fullName = orgTemp.getName();
			Area area = areaDao.get(orgTemp.getAreaId());
			List<Area> areaLis = new ArrayList<Area>();
			while (area.getId() != 0) {
				areaLis.add(area);
				if (area.getParentId() == 0) {
					break;
				}
				area = areaDao.get(area.getParentId());
			}
			areaList.addAll(areaLis);
			orgTemp.setName(orgTemp.getAreaName() + fullName);
		}
		List<Area> listTemp = new ArrayList<Area>();
		Iterator<Area> it = areaList.iterator();
		while (it.hasNext()) {
			Area a = it.next();
			if (listTemp.contains(a)) {
				it.remove();
			} else {
				listTemp.add(a);
			}
		}
		map.put("areaList", listTemp);
		map.put("orgList", listAll);
		return map;
	}

	/**
	 * @param areaId
	 * @return
	 * @see com.tmser.tr.manage.org.service.OrganizationService#getOrgByAreaId(java.lang.Integer)
	 */
	@Override
	public List<Integer> getOrgIdsByAreaId(Integer areaId) {
		Organization org = new Organization();
		org.addCustomCulomn("id");
		org.setType(Organization.SCHOOL);
		org.setAreaIds(SqlMapping.LIKE_PRFIX + "," + areaId + "," + SqlMapping.LIKE_PRFIX);
		List<Organization> orgs = findAll(org);

		List<Integer> ids = new ArrayList<Integer>();
		for (Organization o : orgs) {
			ids.add(o.getId());
		}
		return ids;
	}
	
	/**
	 * 通过机构id查找对应学段
	 * 
	 * @param orgId
	 * @return
	 * @see com.tmser.tr.manage.meta.service.MetaRelationshipService#findMetaShipByOrgId(java.lang.Integer)
	 */
	@Override
	public List<MetaRelationship> listPhasebyOrgId(Integer orgId) {
		Organization org = organizationDao.get(orgId);
		List<MetaRelationship> metaList = new ArrayList<MetaRelationship>();
		if (org != null) {
			String phaseTypes = org.getPhaseTypes();
			if (StringUtils.isNotEmpty(phaseTypes)) {
				String[] splitPhase = StringUtils.split(phaseTypes,StringUtils.COMMA);
				for (int i = 0; i < splitPhase.length; i++) {
					if (null != splitPhase[i] && StringUtils.isNotEmpty(splitPhase[i])) {
						MetaRelationship one = MetaUtils.getPhaseMetaProvider().getMetaRelationship(Integer.valueOf(splitPhase[i]));
						metaList.add(one);
					}
				}
			}
		}
		return metaList;
	}
	
	@Override
	public void createOrganization(Organization org) {
		if(org != null && org.getAreaId() != null){
			Integer areaId = org.getAreaId();
			Area area = areaService.findOne(areaId);
			Map<String,String> areaParams =getAreaIds(area);
			org.setTreeLevel(0);//添加学校代表的级别
			org.setAreaIds(","+areaParams.get("areaIds")+",");//区域层级ids
			org.setSort(0);
			org.setOrgType(1);
			org.setEnable(Organization.ENABLE);
			org.setAreaName(areaParams.get("areaNames"));
			User user = CurrentUserContext.getCurrentUser();
			Date now = new Date();
			org.setCrtDttm(now);
			org.setCrtId(user == null ? 0 : user.getId());
			save(org);
			if(org != null && StringUtils.isNotEmpty(org.getLogo())){
				resourcesService.updateTmptResources(org.getLogo());
			}
			List<OrganizationRelationship> orList = new ArrayList<OrganizationRelationship>();
			if(org.getType().intValue() == Organization.SCHOOL && org.getSchoolings() != null){
				Map<Integer, List<Meta>> phaseGradeMap = MetaUtils.getOrgTypeMetaProvider().listPhaseGradeMap(org.getSchoolings());
				if (phaseGradeMap.size() > 0 ){
					for (Integer phaseId : phaseGradeMap.keySet()) {
						OrganizationRelationship or = new OrganizationRelationship();
						or.setOrgId(org.getId());
						or.setPhaseId(phaseId);
						or.setSchooling(phaseGradeMap.get(phaseId).size());
						orList.add(or);
					}
					
				}
				organizationRelationshipService.deleteByOrgId(org.getId());
				organizationRelationshipService.batchInsert(orList);
			}
			
		}
		
	}
	/**
	 * 拼接区域层级ids
	 * @param area
	 * @return
	 */
	private Map<String,String> getAreaIds(Area area){
		Map<String,String> nameMap = new HashMap<String,String>();
		List<Integer> areaList = new ArrayList<Integer>();
		List<String> areaNameList = new ArrayList<String>();
		while(area.getId()!=0){
			areaList.add(area.getId());
			areaNameList.add(area.getName());
			if(area.getParentId()==0) {
				break;
			}
			area = areaService.findOne(area.getParentId());
		}
		Collections.reverse(areaList);
		Collections.reverse(areaNameList);
		String areaIds = StringUtils.join(areaList, ",");
		String areaNames = StringUtils.join(areaNameList, "");
		nameMap.put("areaIds",areaIds);
		nameMap.put("areaNames",areaNames);
		return nameMap;
	}
}
