/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.history.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.tmser.tr.check.bo.CheckInfo;
import com.tmser.tr.check.bo.CheckOpinion;
import com.tmser.tr.check.service.CheckInfoService;
import com.tmser.tr.check.service.CheckOpinionService;
import com.tmser.tr.common.ResTypeConstants;
import com.tmser.tr.common.bo.QueryObject.JOINTYPE;
import com.tmser.tr.history.service.ManagerCheckHistoryCount;
import com.tmser.tr.history.vo.HistoryColumn;
import com.tmser.tr.history.vo.SearchVo;
import com.tmser.tr.uc.SysRole;
import com.tmser.tr.uc.bo.UserSpace;
import com.tmser.tr.uc.service.UserSpaceService;

/**
 * <pre>
 *  查阅意见资源历史统计服务
 * </pre>
 *
 * @author wangyao
 * @version $Id: ManagerCheckHistoryCountImpl.java, v 1.0 2016年5月25日 下午4:24:00 wangyao Exp $
 */
@Service("managerCheck")
@Transactional
public class ManagerCheckHistoryCountImpl implements ManagerCheckHistoryCount {
	
	private List<HistoryColumn> columns = new ArrayList<HistoryColumn>();
	@Autowired
	private CheckInfoService checkInfoService;
	@Autowired
	private CheckOpinionService checkOpinionService;
	@Autowired
	private UserSpaceService userSpaceService;

	public ManagerCheckHistoryCountImpl(){
		columns.add(new HistoryColumn("cygljl", "管理查阅",new String[]{"查阅教案","查阅课件","查阅反思","查阅教学文章","查阅听课记录","查阅集体备课","查阅计划总结"}, 110));
	}
	/**
	 * @param code
	 * @return
	 * @see com.tmser.tr.history.service.HistoryCount#supports(java.lang.String)
	 */
	@Override
	public boolean supports(String code) {
		for(HistoryColumn cm : columns){
			if(cm.getCode().equals(code)){
				return true;
			}
		}
		return false;
	}
	private HistoryColumn support(String code) {
		for(HistoryColumn cm : columns){
			if(cm.getCode().equals(code)){
				return cm;
			}
		}
		return null;
	}

	
	/**
	 * @return
	 * @see com.tmser.tr.history.service.HistoryCount#getColumns()
	 */
	@Override
	public List<HistoryColumn> getColumns() {
		return columns;
	}

	/**
	 * @param code
	 * @param currentYear
	 * @return 
	 * @see com.tmser.tr.history.service.HistoryCount#count(java.lang.String, java.lang.Integer)
	 */
	@Override
	public Integer[] count(Integer userId,String code, Integer currentYear) {
		List<Integer> count = new ArrayList<Integer>();
		if(supports(code)){
			HistoryColumn support = support(code);
			if(support != null){
				String[] countDesc = support.getCountDesc();
				List<UserSpace> spaceList = userSpaceService.listUserSpaceBySchoolYear(userId, currentYear);
				List<Integer> ids = new ArrayList<>();
				if(!CollectionUtils.isEmpty(spaceList)){
					for (UserSpace space : spaceList) {
						if(!SysRole.TEACHER.getId().equals(space.getSysRoleId())){
							ids.add(space.getId());
						}
					}
				}
				for (int i = 0; i < countDesc.length; i++) {
					CheckInfo model = new CheckInfo();
					model.setUserId(userId);
					model.setSchoolYear(currentYear);
					StringBuilder sql = new StringBuilder();
				    Map<String, Object> paramMap = new HashMap<String,Object>();
					if("查阅教案".equals(countDesc[i])){
						model.setResType(ResTypeConstants.JIAOAN);
					}else if("查阅课件".equals(countDesc[i])){
						model.setResType(ResTypeConstants.KEJIAN);
					}else if("查阅反思".equals(countDesc[i])){
						sql.append(" and resType in (:resType)");
						paramMap.put("resType", Arrays.asList(new Integer[]{ResTypeConstants.FANSI,ResTypeConstants.FANSI_OTHER}));
					}else if("查阅集体备课".equals(countDesc[i])){
						model.setResType(ResTypeConstants.ACTIVITY);
					}else if("查阅计划总结".equals(countDesc[i])){
						sql.append(" and resType in (:resType)");
						paramMap.put("resType", Arrays.asList(new Integer[]{ResTypeConstants.TPPLAIN_SUMMARY_PLIAN,ResTypeConstants.TPPLAIN_SUMMARY_SUMMARY}));
					}else if("查阅教学文章".equals(countDesc[i])){
						model.setResType(ResTypeConstants.JIAOXUELUNWEN);
					}else if("查阅听课记录".equals(countDesc[i])){
						model.setResType(ResTypeConstants.LECTURE);
					}
					if(!CollectionUtils.isEmpty(ids)){
						sql.append(" and spaceId in (:spaceIds)");
						paramMap.put("spaceIds", ids);
					}
					model.addCustomCondition(sql.toString(), paramMap);
					count.add(checkInfoService.count(model));
				}
			}
		}
		return count.toArray(new Integer[count.size()]);
	}
	/**
	 * @param jiaoan
	 * @param userId
	 * @param term
	 * @return
	 */
	@Override
	public Integer getManagerCheckCountByType(Integer[] types, Integer spaceId,
			Integer year){
		CheckInfo model = new CheckInfo();
		model.setSpaceId(spaceId);
	    model.setSchoolYear(year);
	    Integer checkCount = 0;
		for(Integer type : types){
			model.setResType(type);
		    checkCount += checkInfoService.count(model); //查阅数
		}
		return checkCount;
	}
	@Override
	public Integer getManagerCheckOptionCountByType(Integer[] types, Integer spaceId,
			Integer year){
		Map<String,Object> paramMap = new HashMap<String,Object>();
		CheckOpinion option = new CheckOpinion();
		Integer checkCount = 0;
		option.addAlias("c");
		option.setSpaceId(spaceId);
		option.setIsDelete(false);
		option.setIsHidden(false);
	    String sql = " c.checkInfoId = f.id and f.school_year = :schoolYear and f.spaceId = c.spaceId ";
		paramMap.put("schoolYear", year);
		option.addJoin(JOINTYPE.INNER, "CheckInfo f").on(sql);
		option.addCustomCondition("", paramMap);
		for(Integer type : types){
			option.setResType(type);
			checkCount += checkOpinionService.findAll(option).size();
		}
		return checkCount;
	}
	@Override
	public Map<String,Object> getCheckOptionMapList(List<CheckInfo> checkInfoList,SearchVo searchVo) {
		Map<String,Object> dataMap = new HashMap<String,Object>();
		Integer count = 0;
		List<Map<String, Object>> checkMapList = new ArrayList<Map<String,Object>>();
		if(!CollectionUtils.isEmpty(checkInfoList)){
			CheckOpinion temp1 = new CheckOpinion();
			temp1.setType(0);
			temp1.setIsDelete(false);
			temp1.setIsHidden(false);
			temp1.addOrder(" crtTime desc");
			for(CheckInfo checkInfo:checkInfoList){
				Map<String,Object> checkMap = new HashMap<String, Object>();
				checkMap.put("checkInfo", checkInfo);
				temp1.setCheckInfoId(checkInfo.getId());
				temp1.setSpaceId(checkInfo.getSpaceId());
				List<CheckOpinion> parentList = checkOpinionService.findAll(temp1);
				List<Map<String,Object>> optionMapList = new ArrayList<Map<String,Object>>();
				count += parentList.size();
 				CheckOpinion temp2 = new CheckOpinion();
 				temp2.setType(1);
 				temp2.setIsDelete(false);
				temp2.setIsHidden(false);
				temp2.addOrder(" crtTime asc");
				for(CheckOpinion checkOption:parentList){
					Map<String,Object> optionMap = new HashMap<String, Object>();
					temp2.setOpinionId(checkOption.getId());
					temp2.setSpaceId(checkInfo.getSpaceId());
					List<CheckOpinion> childList = checkOpinionService.findAll(temp2);
					optionMap.put("parent",checkOption);
					optionMap.put("childList", childList);
					optionMapList.add(optionMap);
					count += childList.size();
				}
				checkMap.put("optionMapList", optionMapList);
				checkMapList.add(checkMap);
			}
		}
		dataMap.put("data", checkMapList);
		dataMap.put("count", count);
		return dataMap;
	}
}
