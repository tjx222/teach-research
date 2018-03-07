/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.uc.controller.ws.wx;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.tmser.tr.common.bo.QueryObject.JOINTYPE;
import com.tmser.tr.manage.meta.MetaUtils;
import com.tmser.tr.manage.meta.bo.MetaRelationship;
import com.tmser.tr.manage.org.bo.Organization;
import com.tmser.tr.manage.org.dao.OrganizationDao;
import com.tmser.tr.manage.org.service.OrganizationService;
import com.tmser.tr.uc.bo.AppParam;
import com.tmser.tr.uc.bo.Login;
import com.tmser.tr.uc.bo.User;
import com.tmser.tr.uc.controller.ws.wx.utils.ConnectUtils;
import com.tmser.tr.uc.controller.ws.wx.utils.Platform;
import com.tmser.tr.uc.controller.ws.wx.utils.WxJsonResult;
import com.tmser.tr.uc.service.AppParamService;
import com.tmser.tr.uc.service.LoginService;
import com.tmser.tr.uc.service.PasswordService;
import com.tmser.tr.uc.service.SsoService;
import com.tmser.tr.uc.service.UserService;
import com.tmser.tr.utils.Identities;
import com.tmser.tr.utils.StringUtils;

/**
 * <pre>
 * 文轩单点登录服务类
 * </pre>
 *
 * @author tmser
 * @version $Id: WenxuanLogincodeSsoServiceImpl.java, v 1.0 2016年1月13日 下午2:32:43
 *          tmser Exp $
 */
@Transactional
public class WenxuanLogincodeSsoServiceImpl implements SsoService {

	@Resource
	private LoginService loginService;

	@Autowired
	private UserService userService;

	@Resource
	private PasswordService passwordService;

	@Resource
	private OrganizationService organizationService;

	@Resource
	private OrganizationDao organizationDao;
	@Resource
	private AppParamService appParamService;

	/**
	 * @param params
	 * @return
	 * @see com.tmser.tr.uc.service.SsoService#validate(java.util.Map)
	 */
	@Override
	public boolean validate(Map<String, String> params) {
		try {
			return StringUtils.isNotEmpty(logincode(params));
		} catch (Exception e) {
			// do nothing
		}
		return false;
	}

	/**
	 * @param params
	 * @return
	 * @see com.tmser.tr.uc.service.SsoService#getAppId(java.util.Map)
	 */
	@Override
	public String getAppId(Map<String, String> params) {
		return params.get("appid");
	}

	/**
	 * @param params
	 * @return
	 * @see com.tmser.tr.uc.service.SsoService#getAppkey(java.util.Map)
	 */
	@Override
	public String getAppkey(Map<String, String> params) {
		return params.get("appkey");
	}

	/**
	 * @param params
	 * @return
	 * @see com.tmser.tr.uc.service.SsoService#logincode(java.util.Map)
	 */
	@Override
	public String logincode(Map<String, String> params) {
		String arr = null;
		try {
			arr = Platform.decrypt(params.get("uid"));
			return arr.split("_")[1];
		} catch (Exception e) {
			// do nothing
		}
		return null;
	}

	/**
	 * @param params
	 * @return
	 */
	public Integer getOrgid(Map<String, String> params) {
		String arr = null;
		try {
			arr = Platform.decrypt(params.get("uid"));
			return Integer.valueOf(arr.split("_")[0]);
		} catch (Exception e) {
			// do nothing
		}
		return null;
	}

	/**
	 * @param params
	 * @return
	 * @see com.tmser.tr.uc.service.SsoService#loginFailedCallback(java.util.Map)
	 */
	@Override
	public Login loginFailedCallback(Integer appLocalId,
			Map<String, String> params) {
		String arr = null;
		try {
			arr = Platform.decrypt(params.get("uid"));
		} catch (Exception e) {
			// do nothing
		}

		WxUserVo result = load(arr,getAppId(params));
		if (result != null) {
			Integer orgId = getOrgid(params);
			String orgName = "";
			if (orgId != null) {
				Organization org = null;
				for (WxUserVo.Org o : result.getOrgs()) {
					if (o.getAreaid() == 0 || !(o.getOrgid().equals(result.getOrgId()))) {
						continue;
					}
					Organization model = new Organization();
					model.setTrdpartyOrgId(o.getOrgid());
					org = organizationService.findOne(model);
					if (org == null) {
						org = new Organization();
						org.setName(o.getOrgname());
						org.setShortName(StringUtils.isBlank(o.getSimplename()) ? o
								.getOrgname() : o.getSimplename());
						org.setAreaId(o.getAreaid());
						if (o.getOrgtype() == 9) {
							orgName = org.getName();
							String phaseidStr = o.getPhaseid();
							String[] phaseids = phaseidStr.split(StringUtils.COMMA);
							StringBuilder phaseTypes = new StringBuilder(StringUtils.COMMA);
							List<Integer> mPhaseIds = new ArrayList<Integer>();
							for (String phaseid : phaseids) {
								MetaRelationship mrs = MetaUtils.getPhaseMetaProvider().getMetaRelationshipByPhaseId(Integer.valueOf(phaseid));
								phaseTypes.append(mrs.getId()).append(StringUtils.COMMA);
								mPhaseIds.add(Integer.valueOf(phaseid));
							}
							String phaseTypeStr = phaseTypes.toString();
							org.setPhaseTypes(phaseTypeStr);
							phaseTypeStr = phaseTypeStr.substring(1,phaseTypeStr.length());
							MetaRelationship metaRelationship = comfirmSchoolings(phaseTypeStr,o.getEducationalid());
							
							org.setSchoolings(metaRelationship.getId());
							org.setType(Organization.SCHOOL);
						} else {
							org.setType(Organization.UNIT);
						}

						org.setTrdpartyOrgId(o.getOrgid());
						organizationService.createOrganization(org);
					} 
					orgId = org.getId();
				}
			}

			Login lmodel = new Login();
			String lc = logincode(params);
			lmodel.setLogincode(lc);
			lmodel.addAlias("l");
			lmodel.addJoin(JOINTYPE.INNER, "User u").on("u.id = l.id");
			lmodel.buildCondition(" and u.appId = :appid").put("appid", appLocalId);
			lmodel.addCustomCulomn("l.id");
			if (loginService.findOne(lmodel) == null) {
				// 创建账户
				Login login = loginService.newLogin(Identities.uuid2(),
						passwordService.encryptPassword("", "123456", ""));
				login.setLogincode(lc);
				login = loginService.save(login);
				Organization o = organizationService.findOne(orgId);
				User user = userService.newUser(login.getId(), o.getType());
				user.setName(result.getName());
				user.setNickname(result.getName());
				user.setMail(result.getEmail());
				user.setIdcard(result.getIdNumber());
				user.setEnable(result.getIsDeleted() == 0 ? 1 : 0);
				user.setAddress(result.getHome());
				user.setCellphone(result.getPhone());
				user.setOrgId(orgId);
				user.setOrgName(orgName != null ? o.getName() : "");
				user.setAppId(appLocalId);
				user.setCellphoneValid(false);
				user.setCellphoneView(false);
				user.setMailValid(false);
				user.setMailView(false);
				user.setIsFamousTeacher(0);
				user.setSex(result.getSex());
				user = userService.save(user);
				return login;
			}
		}
		return null;
	}

	WxUserVo load(String uid,String appid) {
		if (StringUtils.isEmpty(uid)) {
			return null;
		}
		String infoUrl = null;
		AppParam param = new AppParam();
		param.setAppid(appid);
		List<AppParam> findAll = appParamService.findAll(param);
		Map<String, String> paramMap = new HashMap<String, String>();
		if(!CollectionUtils.isEmpty(findAll)){
			for (AppParam appParam : findAll) {
				if("infoUrl".equals(appParam.getName())){
					infoUrl = appParam.getVal();
					continue;
				}
				paramMap.put(appParam.getName(), appParam.getVal());
				
			}
		}
		
		paramMap.put("uid", uid);
		WxJsonResult rs = JSON.parseObject(ConnectUtils.get(infoUrl, paramMap),
				WxJsonResult.class);
		return rs != null ? rs.getUser() : null;
	}
	
	MetaRelationship comfirmSchoolings(String phaseStr,String myearStr){
		List<MetaRelationship> mrList = MetaUtils.getOrgTypeMetaProvider().listAll();
		String[] phaseArr = phaseStr.split(StringUtils.COMMA);
		String[] yearArr = myearStr.split(StringUtils.COMMA);
		for (MetaRelationship metaRelationship : mrList) {
			Map<Integer,Integer> listAllGrade = MetaUtils.getOrgTypeMetaProvider().listPhaseGradeCountMap(metaRelationship.getId());
			if (listAllGrade.size() == phaseArr.length) {
				int checkedSize = 0;
				for (Integer phase : listAllGrade.keySet()) {
					for(int i =0;i<phaseArr.length;i++){
						MetaRelationship phaseRe = MetaUtils.getMetaRelation(Integer.valueOf(phaseArr[i]));
						if (phase.equals(phaseRe.getId())) {
							for(int j = 0; j< yearArr.length; j++){
								if(listAllGrade.get(phase).equals(Integer.valueOf(yearArr[j]))){
									checkedSize++;
									continue;
								}
								
							}
						}
					}
					
				}
				if(checkedSize == listAllGrade.size()){
					return metaRelationship;
				}
			}
			
		}
		return null;
	}
}
