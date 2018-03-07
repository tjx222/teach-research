/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.back.zzjg.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.tmser.tr.back.logger.LoggerModule;
import com.tmser.tr.back.logger.bo.OperateLog;
import com.tmser.tr.back.logger.service.OperateLogService;
import com.tmser.tr.back.zzjg.SyncSchConstant;
import com.tmser.tr.back.zzjg.WxSchoolVo;
import com.tmser.tr.back.zzjg.WxSchoolVo.School;
import com.tmser.tr.back.zzjg.exception.SchoolSyncFailedException;
import com.tmser.tr.back.zzjg.service.SynchronizeOrgService;
import com.tmser.tr.manage.meta.MetaUtils;
import com.tmser.tr.manage.meta.bo.MetaRelationship;
import com.tmser.tr.manage.org.bo.Organization;
import com.tmser.tr.manage.org.service.OrganizationService;
import com.tmser.tr.uc.bo.App;
import com.tmser.tr.uc.bo.AppParam;
import com.tmser.tr.uc.controller.ws.wx.utils.ConnectUtils;
import com.tmser.tr.uc.service.AppParamService;
import com.tmser.tr.uc.service.AppService;
import com.tmser.tr.utils.StringUtils;

/**
 * <pre>
 *
 * </pre>
 *
 * @author 3020mt
 * @version $Id: SynchronizeOrgServiceImpl.java, v 1.0 2016年11月21日 上午9:53:10
 *          3020mt Exp $
 */
@Transactional
@Service
public class SynchronizeOrgServiceImpl implements SynchronizeOrgService {
	
	private static final Logger dblogger = LoggerFactory.getLogger("schoolsyncDbLogger");
	private static final Logger logger = LoggerFactory.getLogger(SynchronizeOrgServiceImpl.class);

	@Autowired
	private AppParamService appParamService;
	@Autowired
	private OrganizationService organizationService;
	@Autowired
	private AppService appService;
	@Autowired
	private OperateLogService operateLogService;

	/**
	 * @return
	 * @see com.tmser.tr.uc.service.SynchronizeOrgService#synchronousSchool()
	 */
	@Override
	public void synchronousSchool(String appid, String appkey, Long logId) {
		Long time = null;
		try {
			if(StringUtils.isNotBlank(appid)){
				OperateLog operateLog = null;
				if(logId == null){
					OperateLog logmodel = new OperateLog();
					logmodel.setModule(LoggerModule.SYNCSCHOOL.getCname());
					logmodel.setMessage(SyncSchConstant.SYNCSCH_SUCCESS);
					//用1表示成功
					logmodel.setType(SyncSchConstant.SYNCSCH_SUCCESS_TYPE);
					logmodel.addOrder(" createtime desc");
					operateLog = operateLogService.findOne(logmodel);
				}else{
					operateLog = operateLogService.findOne(logId);
					if(operateLog == null){
						return;
					}
				}
				if (operateLog != null) {
					time = operateLog.getCreatetime().getTime();
				}
				
				List<Organization> orgs = parseSchools(findCallbackUrl(appid,appkey), time);
				if (!CollectionUtils.isEmpty(orgs)){
					for (Organization organization : orgs) {
						 organizationService.createOrganization(organization);
					}
				}
			}
		} catch (Exception e) {
			logger.error("sysnc school failed!",e);
			throw new SchoolSyncFailedException(time);
		}
	}
	
	protected Map<String, String> findCallbackUrl(String appid, String appkey){
		App model = new App();
		model.setAppid(appid);
		model.setAppkey(appkey);
		model.setType("platform_callback");
		model.setEnable(App.ENABLE);
		App app = appService.findOne(model);
		Map<String, String> paramMap = new HashMap<String, String>();
		if(app != null){
			AppParam param = new AppParam();
			param.setAppid(app.getAppid());
			List<AppParam> findAll = appParamService.findAll(param);
			
			if (!CollectionUtils.isEmpty(findAll)) {
				for (AppParam appParam : findAll) {
					paramMap.put(appParam.getName(), appParam.getVal());
				}
			}
		}
		return paramMap;
	}
	
	protected List<Organization> parseSchools(Map<String, String> paramMap, Long time) {
		String url = paramMap != null ? paramMap.get("schoolUrl") : null;
		if(StringUtils.isBlank(url)){
			return null;
		}
		
		paramMap.remove("schoolUrl");
		paramMap.remove("infoUrl");
		paramMap.put("time", time == null ? "":String.valueOf(time));
		WxSchoolVo result = JSON.parseObject(ConnectUtils.get(url,paramMap), WxSchoolVo.class);
		
		List<Organization> orgs = new ArrayList<>();
		if (result != null) {
			List<School> s = result.getSchools();
			if (!CollectionUtils.isEmpty(s)) {
				for (School school : s) {
					Organization org = new Organization();
					org.setTrdpartyOrgId(school.getId());
					if (organizationService.findOne(org) == null) {
						org.setType(Organization.SCHOOL);
						org.setName(school.getName());
						org.setSchWebsite(school.getWebsite());
						org.setAddress(school.getAddress());
						org.setCode(school.getZipcode());
						org.setPhoneNumber(school.getTel());
						org.setEmail(school.getEmail());
						org.setAreaId(school.getPlace());
						if (StringUtils.isEmpty(school.getFstage())) {
							dblogger.info("没有学段信息：{}", school.getId());
							continue;
						}
						
						String phases = school.getFstage();
						String[] phaseArr = StringUtils.split(phases, StringUtils.COMMA);
						StringBuilder sb = new StringBuilder(StringUtils.COMMA);
						for (String phase : phaseArr) {
							MetaRelationship mr = MetaUtils.getPhaseMetaProvider().getMetaRelationshipByPhaseId(Integer.valueOf(phase));
							if (mr != null) {
								sb.append(mr.getId() + StringUtils.COMMA);
							}
						}
						org.setPhaseTypes(sb.toString());
						Organization model = new Organization();
						model.setTrdpartyOrgId(school.getEdudeptid());
						model = organizationService.findOne(model);
						if (model != null) {
							org.setParentId(model.getId());
						}
						org.setTrdpartyOrgId(school.getId());

						MetaRelationship mr = comfirmSchoolings(sb.toString().substring(1, sb.length() - 1), school.getFeducational());
						if (mr == null) {
							dblogger.info("找不到对应学校类型：{}", school.getId());
							continue;
						}
						org.setSchoolings(mr.getId());
						orgs.add(org);
					}
				}
				
			}
		}
		return orgs;
	}

	MetaRelationship comfirmSchoolings(String phaseStr, String myearStr) {
		List<MetaRelationship> mrList = MetaUtils.getOrgTypeMetaProvider().listAll();
		String[] phaseArr = phaseStr.split(StringUtils.COMMA);
		String[] yearArr = myearStr.split(StringUtils.COMMA);
		for (MetaRelationship metaRelationship : mrList) {
			Map<Integer, Integer> listAllGrade = MetaUtils.getOrgTypeMetaProvider().listPhaseGradeCountMap(metaRelationship.getId());
			if (listAllGrade.size() == phaseArr.length) {
				int checkedSize = 0;
				for (Integer phase : listAllGrade.keySet()) {
					for (int i = 0; i < phaseArr.length; i++) {
						MetaRelationship phaseRe = MetaUtils.getMetaRelation(Integer.valueOf(phaseArr[i]));
						if (phase.equals(phaseRe.getId())) {
							for (int j = 0; j < yearArr.length; j++) {
								if (listAllGrade.get(phase).equals(Integer.valueOf(yearArr[j]))) {
									checkedSize++;
									continue;
								}

							}
						}
					}

				}
				if (checkedSize == listAllGrade.size()) {
					return metaRelationship;
				}
			}

		}
		return null;
	}
}
