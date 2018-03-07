/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.manage.org.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
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
import com.tmser.tr.manage.org.bo.Area;
import com.tmser.tr.manage.org.bo.Organization;
import com.tmser.tr.manage.org.bo.OrganizationRelationship;
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
 * @version $Id: Organization.java, v 1.0 2015-03-19 tmser Exp $
 */
@Service
@Transactional
public class OrganizationServiceImpl extends AbstractService<Organization, Integer> implements OrganizationService {

	@Autowired
	private OrganizationDao organizationDao;

	@Autowired
	private AreaService areaService; 
	
	@Autowired
	private ResourcesService resourcesService;
	
	@Autowired
	private OrganizationRelationshipService organizationRelationshipService;
	
	/**
	 * @return
	 * @see com.tmser.tr.common.service.BaseService#getDAO()
	 */
	@Override
	public BaseDAO<Organization, Integer> getDAO() {
		return organizationDao;
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

	@Override
	public List<Organization> findOrgByNameAndAreaIds(String schoolName,
			String areaIds) {
		UserSpace userSpace = (UserSpace) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE); // 用户空间
		Organization organization = organizationDao.get(userSpace.getOrgId());
		Organization org = new Organization();
		String hql = "";
		if (organization != null) {
			// org.setAreaId(organization.getAreaId());//同一区域下的
			org.setOrgType(organization.getOrgType());// 同一种机构类型（会员校、体验校、虚拟校）
			String phaseTypes = organization.getPhaseTypes();// 学段学校类型有交集的
			if (StringUtils.isNotEmpty(phaseTypes)) {
				phaseTypes = phaseTypes.substring(1, phaseTypes.length() - 1);
				String[] split = phaseTypes.split(",");
				hql += "and (";
				for (int i = 0; i < split.length; i++) {
					if (i == 0) {
						hql += " phaseTypes like '%," + split[i] + ",%'";
					} else {
						hql += " or phaseTypes like '%," + split[i] + ",%'";
					}
				}
				hql += ") and areaIds='"+areaIds+"' ";
			} else {
				org.setPhaseTypes("isempty");
			}
		} else {
			org.setAreaId(-1);
		}
		org.setName(SqlMapping.LIKE_PRFIX + schoolName + SqlMapping.LIKE_PRFIX);
		org.addCustomCondition(hql + " and id != " + userSpace.getOrgId(), new HashMap<String, Object>());
		org.addCustomCulomn("id,shortName,areaName");
		List<Organization> listAll = organizationDao.listAll(org);
		return listAll;
	}

}
