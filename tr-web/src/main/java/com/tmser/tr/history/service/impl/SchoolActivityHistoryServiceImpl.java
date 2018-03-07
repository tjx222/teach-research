/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.history.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmser.tr.activity.bo.SchoolActivity;
import com.tmser.tr.activity.bo.SchoolTeachCircle;
import com.tmser.tr.activity.bo.SchoolTeachCircleOrg;
import com.tmser.tr.activity.bo.TeachSchedule;
import com.tmser.tr.activity.service.SchoolActivityService;
import com.tmser.tr.activity.service.SchoolTeachCircleOrgService;
import com.tmser.tr.activity.service.SchoolTeachCircleService;
import com.tmser.tr.activity.service.TeachScheduleService;
import com.tmser.tr.common.orm.SqlMapping;
import com.tmser.tr.common.page.Page;
import com.tmser.tr.common.page.PageList;
import com.tmser.tr.history.service.SchoolActivityHistoryService;
import com.tmser.tr.history.vo.SearchVo;
import com.tmser.tr.manage.org.bo.Organization;
import com.tmser.tr.manage.org.service.OrganizationService;
import com.tmser.tr.uc.SysRole;
import com.tmser.tr.uc.bo.UserSpace;
import com.tmser.tr.uc.service.UserSpaceService;
import com.tmser.tr.utils.StringUtils;

/**
 * <pre>
 *
 * </pre>
 *
 * @author zpp
 * @version $Id: SchoolActivityHistoryService.java, v 1.0 2016年5月24日 上午11:34:33
 *          zpp Exp $
 */
@Service
@Transactional
public class SchoolActivityHistoryServiceImpl implements SchoolActivityHistoryService {
	@Resource
	private UserSpaceService userSpaceService;
	@Resource
	private SchoolTeachCircleService schoolTeachCircleService;
	@Resource
	private SchoolTeachCircleOrgService schoolTeachCircleOrgService;
	@Resource
	private OrganizationService organizationService;
	@Resource
	private SchoolActivityService schoolActivityService;
	@Resource
	private TeachScheduleService teachScheduleService;

	/**
	 * 获取校级教研历史学年数据
	 * 
	 * @param sv
	 * @return
	 * @see com.tmser.tr.history.service.SchoolActivityHistoryService#listSchoolActivity(com.tmser.tr.history.vo.SearchVo)
	 */
	@Override
	public Map<String, Object> listSchoolActivity(SearchVo sv) {
		UserSpace us = userSpaceService.findOne(sv.getSpaceId());
		Map<String, Object> returnMap = new HashMap<String, Object>();
		if(us!=null){
			Integer typeId = sv.getTypeId();//1.发起  2.参与
			if(typeId==null){
				typeId=1;
				sv.setTypeId(1);
			}
			Integer sysRoleId = us.getSysRoleId();
			SchoolActivity sa = new SchoolActivity();
			SchoolActivity satwo = new SchoolActivity();
			sa.addPage(sv.getPage());
			sa.setStatus(1);// 正式发文
			satwo.setStatus(1);// 正式发文
			sa.setSchoolYear(sv.getSchoolYear());// 学年
			satwo.setSchoolYear(sv.getSchoolYear());// 学年
			if(sv.getTermId()!=null){
				sa.setTerm(sv.getTermId());// 学期
				satwo.setTerm(sv.getTermId());// 学期
			}
			sa.setPhaseId(us.getPhaseId());
			satwo.setPhaseId(us.getPhaseId());
			sa.addOrder("releaseTime desc");
			Map<String, Object> circleMap = new HashMap<String,Object>();
			circleMap.put("userId", us.getUserId());
			circleMap.put("subjectId", us.getSubjectId());
			circleMap.put("gradeId", us.getGradeId());
			circleMap.put("userId", us.getUserId());
			circleMap.put("termId", sv.getTermId());
			circleMap.put("activeName", "%"+sv.getSearchStr()+"%");
			circleMap.put("userIds", "%,"+us.getUserId()+",%");
			String hql = "";
			String hqltwo = "";
			if (sysRoleId.intValue() == SysRole.TEACHER.getId().intValue()) {// 教师
				// 查询要包含当前人是备课
				if(StringUtils.isNotEmpty(sv.getSearchStr())){
					hql += " and activityName like :activeName";
				}
				if(sv.getTermId()!=null){
					if(StringUtils.isNotEmpty(sv.getSearchStr())){
						hql += " or (expertIds like :userIds and term=:termId and activityName like :activeName) "
								+ "or (mainUserId=:userId and mainUserSubjectId=:subjectId and mainUserGradeId=:gradeId and term=:termId and activityName like :activeName) ";
					}else{
						hql += " or (expertIds like :userIds and term=:termId) or (mainUserId=:userId and mainUserSubjectId=:subjectId and mainUserGradeId=:gradeId and term=:termId) ";
					}
				}else{
					if(StringUtils.isNotEmpty(sv.getSearchStr())){
						hql += " or (expertIds like :userIds and activityName like :activeName)"
								+ " or (mainUserId=:userId and mainUserSubjectId=:subjectId and mainUserGradeId=:gradeId and activityName like :activeName) ";
					}else{
						hql += " or expertIds like :userIds or (mainUserId=:userId and mainUserSubjectId=:subjectId and mainUserGradeId=:gradeId) ";
					}
				}
				sa.addCustomCondition(hql, circleMap);
				sa.setOrgids(SqlMapping.LIKE_PRFIX + "," + us.getOrgId() + "," + SqlMapping.LIKE_PRFIX);
				sa.setSubjectIds(SqlMapping.LIKE_PRFIX + "," + us.getSubjectId() + "," + SqlMapping.LIKE_PRFIX);
				sa.setGradeIds(SqlMapping.LIKE_PRFIX + "," + us.getGradeId() + "," + SqlMapping.LIKE_PRFIX);
				returnMap.put("role", "notCreate");
			} else if (sysRoleId.intValue() == SysRole.XZ.getId().intValue() || sysRoleId.intValue() == SysRole.FXZ.getId().intValue() || sysRoleId == SysRole.ZR.getId().intValue()) {// 校长、副校长或者是主任
				sa.setPhaseId(null);
				if(StringUtils.isNotEmpty(sv.getSearchStr())){
					hql += " and activityName like :activeName";
				}
				if(sv.getTermId()!=null){
					if(StringUtils.isNotEmpty(sv.getSearchStr())){
						hql += " or (expertIds like :userIds and term=:termId and activityName like :activeName) ";
					}else{
						hql += " or (expertIds like :userIds and term=:termId) ";
					}
				}else{
					if(StringUtils.isNotEmpty(sv.getSearchStr())){
						hql += " or (expertIds like :userIds and activityName like :activeName)";
						
					}else{
						hql += " or expertIds like :userIds ";
					}
				}
				sa.addCustomCondition(hql, circleMap);
				sa.setOrgids(SqlMapping.LIKE_PRFIX + "," + us.getOrgId() + "," + SqlMapping.LIKE_PRFIX);
				returnMap.put("role", "notCreate");
			} else if (sysRoleId.intValue() == SysRole.BKZZ.getId().intValue()) {// 备课组长
				if(StringUtils.isNotEmpty(sv.getSearchStr())){
					hql += " and activityName like :activeName";
					hqltwo += " and activityName like :activeName";
				}
				if (typeId==null || typeId == 1) {// 发起与管理
					sa.setOrganizeUserId(us.getUserId());
					sa.setSpaceId(us.getId());// 当前身份发起的
					if(sv.getTermId()!=null){
						if(StringUtils.isNotEmpty(sv.getSearchStr())){
							hqltwo += " and organizeUserId != :userId or (expertIds like :userIds and term=:termId and activityName like :activeName)";
						}else{
							hqltwo += " and organizeUserId != :userId or (expertIds like :userIds and term=:termId)";
						}
					}else{
						if(StringUtils.isNotEmpty(sv.getSearchStr())){
							hqltwo += " and organizeUserId != :userId or (expertIds like :userIds and activityName like :activeName)";
						}else{
							hqltwo += " and organizeUserId != :userId or expertIds like :userIds";
						}
					}
					satwo.setSubjectIds(SqlMapping.LIKE_PRFIX + "," + us.getSubjectId() + "," + SqlMapping.LIKE_PRFIX);
					satwo.setGradeIds(SqlMapping.LIKE_PRFIX + "," + us.getGradeId() + "," + SqlMapping.LIKE_PRFIX);
				} else {// 参与及查看
					if(sv.getTermId()!=null){
						if(StringUtils.isNotEmpty(sv.getSearchStr())){
							hql += " and organizeUserId != :userId or (expertIds like :userIds and term=:termId and activityName like :activeName)";
						}else{
							hql += " and organizeUserId != :userId or (expertIds like :userIds and term=:termId)";
						}
					}else{
						if(StringUtils.isNotEmpty(sv.getSearchStr())){
							hql += " and organizeUserId != :userId or (expertIds like :userIds activityName like :activeName)";
						}else{
							hql += " and organizeUserId != :userId or expertIds like :userIds";
						}
					}
					sa.setSubjectIds(SqlMapping.LIKE_PRFIX + "," + us.getSubjectId() + "," + SqlMapping.LIKE_PRFIX);
					sa.setGradeIds(SqlMapping.LIKE_PRFIX + "," + us.getGradeId() + "," + SqlMapping.LIKE_PRFIX);
					satwo.setOrganizeUserId(us.getUserId());
					satwo.setSpaceId(us.getId());// 当前身份发起的
				}
				sa.setOrgids(SqlMapping.LIKE_PRFIX + "," + us.getOrgId() + "," + SqlMapping.LIKE_PRFIX);
				sa.addCustomCondition(hql, circleMap);
				satwo.setOrgids(SqlMapping.LIKE_PRFIX + "," + us.getOrgId() + "," + SqlMapping.LIKE_PRFIX);
				satwo.addCustomCondition(hqltwo, circleMap);
				returnMap.put("role", "canCreate");
			}else if (sysRoleId.intValue() == SysRole.XKZZ.getId().intValue()) {// 学科组长
				if(StringUtils.isNotEmpty(sv.getSearchStr())){
					hql += " and activityName like :activeName";
					hqltwo += " and activityName like :activeName";
				}
				if (typeId == 1) {// 发起与管理
					sa.setOrganizeUserId(us.getUserId());
					sa.setSpaceId(us.getId());// 当前身份发起的
					if(sv.getTermId()!=null){
						if(StringUtils.isNotEmpty(sv.getSearchStr())){
							hqltwo += " and organizeUserId != :userId or (expertIds like :userIds and term=:termId and activityName like :activeName)";
						}else{
							hqltwo += " and organizeUserId != :userId or (expertIds like :userIds and term=:termId)";
						}
					}else{
						if(StringUtils.isNotEmpty(sv.getSearchStr())){
							hqltwo += " and organizeUserId != :userId or (expertIds like :userIds and activityName like :activeName)";
						}else{
							hqltwo += " and organizeUserId != :userId or expertIds like :userIds";
						}
					}
					satwo.setSubjectIds(SqlMapping.LIKE_PRFIX + "," + us.getSubjectId() + "," + SqlMapping.LIKE_PRFIX);
				} else {// 参与及查看
					if(sv.getTermId()!=null){
						if(StringUtils.isNotEmpty(sv.getSearchStr())){
							hql += " and organizeUserId != :userId or (expertIds like :userIds and term=:termId and activityName like :activeName)";
						}else{
							hql += " and organizeUserId != :userId or (expertIds like :userIds and term=:termId)";
						}
					}else{
						if(StringUtils.isNotEmpty(sv.getSearchStr())){
							hql += " and organizeUserId != :userId or (expertIds like :userIds and activityName like :activeName)";
						}else{
							hql += " and organizeUserId != :userId or expertIds like :userIds";
						}
					}
					sa.setSubjectIds(SqlMapping.LIKE_PRFIX + "," + us.getSubjectId() + "," + SqlMapping.LIKE_PRFIX);
					satwo.setOrganizeUserId(us.getUserId());
					satwo.setSpaceId(us.getId());// 当前身份发起的
				}
				sa.setOrgids(SqlMapping.LIKE_PRFIX + "," + us.getOrgId() + "," + SqlMapping.LIKE_PRFIX);
				sa.addCustomCondition(hql, circleMap);
				satwo.setOrgids(SqlMapping.LIKE_PRFIX + "," + us.getOrgId() + "," + SqlMapping.LIKE_PRFIX);
				satwo.addCustomCondition(hqltwo, circleMap);
				returnMap.put("role", "canCreate");
			} else if (sysRoleId.intValue() == SysRole.NJZZ.getId().intValue()) {// 年级组长
				if(StringUtils.isNotEmpty(sv.getSearchStr())){
					hql += " and activityName like :activeName";
					hqltwo += " and activityName like :activeName";
				}
				if (typeId == 1) {// 发起与管理
					sa.setOrganizeUserId(us.getUserId());
					sa.setSpaceId(us.getId());// 当前身份发起的
					if(sv.getTermId()!=null){
						if(StringUtils.isNotEmpty(sv.getSearchStr())){
							hqltwo += " and organizeUserId != :userId or (expertIds like :userIds and term=:termId and activityName like :activeName)";
						}else{
							hqltwo += " and organizeUserId != :userId or (expertIds like :userIds and term=:termId)";
						}
					}else{
						if(StringUtils.isNotEmpty(sv.getSearchStr())){
							hqltwo += " and organizeUserId != :userId or (expertIds like :userIds and activityName like :activeName)";
						}else{
							hqltwo += " and organizeUserId != :userId or expertIds like :userIds";
						}
					}
					satwo.setGradeIds(SqlMapping.LIKE_PRFIX + "," + us.getGradeId() + "," + SqlMapping.LIKE_PRFIX);
				} else {// 参与及查看
					if(sv.getTermId()!=null){
						if(StringUtils.isNotEmpty(sv.getSearchStr())){
							hql += " and organizeUserId != :userId or (expertIds like :userIds and term=:termId and activityName like :activeName)";
						}else{
							hql += " and organizeUserId != :userId or (expertIds like :userIds and term=:termId)";
						}
					}else{
						if(StringUtils.isNotEmpty(sv.getSearchStr())){
							hql += " and organizeUserId != :userId or (expertIds like :userIds and activityName like :activeName)";
						}else{
							hql += " and organizeUserId != :userId or expertIds like :userIds";
						}
					}
					sa.setGradeIds(SqlMapping.LIKE_PRFIX + "," + us.getGradeId() + "," + SqlMapping.LIKE_PRFIX);
					satwo.setOrganizeUserId(us.getUserId());
					satwo.setSpaceId(us.getId());// 当前身份发起的
				}
				sa.setOrgids(SqlMapping.LIKE_PRFIX + "," + us.getOrgId() + "," + SqlMapping.LIKE_PRFIX);
				sa.addCustomCondition(hql, circleMap);
				satwo.setOrgids(SqlMapping.LIKE_PRFIX + "," + us.getOrgId() + "," + SqlMapping.LIKE_PRFIX);
				satwo.addCustomCondition(hqltwo, circleMap);
				returnMap.put("role", "canCreate");
			}else if (sysRoleId.intValue() == SysRole.JYY.getId().intValue()) {// 教研员
				if(StringUtils.isNotEmpty(sv.getSearchStr())){
					hql += " and activityName like :activeName";
					hqltwo += " and activityName like :activeName";
				}
				if (typeId == 1) {// 发起与管理
					sa.setOrganizeUserId(us.getUserId());
					sa.setSpaceId(us.getId());// 当前身份发起的
					Organization organization = organizationService.findOne(us.getOrgId());
					if(sv.getTermId()!=null){
						if(StringUtils.isNotEmpty(sv.getSearchStr())){
							hqltwo += " and organizeUserId != :userId or (expertIds like :userIds and term=:termId and activityName like :activeName)";
						}else{
							hqltwo += " and organizeUserId != :userId or (expertIds like :userIds and term=:termId)";
						}
					}else{
						if(StringUtils.isNotEmpty(sv.getSearchStr())){
							hqltwo += " and organizeUserId != :userId or (expertIds like :userIds and activityName like :activeName)";
						}else{
							hqltwo += " and organizeUserId != :userId or expertIds like :userIds";
						}
					}
					satwo.setAreaIds(SqlMapping.LIKE_PRFIX + "," + organization.getAreaId() + "," + SqlMapping.LIKE_PRFIX);
					satwo.setSubjectIds(SqlMapping.LIKE_PRFIX + "," + us.getSubjectId() + "," + SqlMapping.LIKE_PRFIX);
				} else {// 参与及查看
					Organization organization = organizationService.findOne(us.getOrgId());
					if(sv.getTermId()!=null){
						if(StringUtils.isNotEmpty(sv.getSearchStr())){
							hql += " and organizeUserId != :userId or (expertIds like :userIds and term=:termId and activityName like :activeName)";
						}else{
							hql += " and organizeUserId != :userId or (expertIds like :userIds and term=:termId)";
						}
					}else{
						if(StringUtils.isNotEmpty(sv.getSearchStr())){
							hql += " and organizeUserId != :userId or (expertIds like :userIds and activityName like :activeName)";
						}else{
							hql += " and organizeUserId != :userId or expertIds like :userIds";
						}
					}
					sa.setAreaIds(SqlMapping.LIKE_PRFIX + "," + organization.getAreaId() + "," + SqlMapping.LIKE_PRFIX);
					sa.setSubjectIds(SqlMapping.LIKE_PRFIX + "," + us.getSubjectId() + "," + SqlMapping.LIKE_PRFIX);
					satwo.setOrganizeUserId(us.getUserId());
					satwo.setSpaceId(us.getId());// 当前身份发起的
				}
				sa.addCustomCondition(hql, circleMap);
				satwo.addCustomCondition(hqltwo, circleMap);
				returnMap.put("role", "canCreate");
			}else if (sysRoleId.intValue() == SysRole.JYZR.getId().intValue()) {// 教研主任
				if(StringUtils.isNotEmpty(sv.getSearchStr())){
					hql += " and activityName like :activeName";
					hqltwo += " and activityName like :activeName";
				}
				if (typeId == 1) {// 发起与管理
					sa.setOrganizeUserId(us.getUserId());
					sa.setSpaceId(us.getId());// 当前身份发起的
					Organization organization = organizationService.findOne(us.getOrgId());
					if(sv.getTermId()!=null){
						if(StringUtils.isNotEmpty(sv.getSearchStr())){
							hqltwo += " and organizeUserId != :userId or (expertIds like :userIds and term=:termId and activityName like :activeName)";
						}else{
							hqltwo += " and organizeUserId != :userId or (expertIds like :userIds and term=:termId)";
						}
					}else{
						if(StringUtils.isNotEmpty(sv.getSearchStr())){
							hqltwo += " and organizeUserId != :userId or (expertIds like :userIds and activityName like :activeName)";
						}else{
							hqltwo += " and organizeUserId != :userId or expertIds like :userIds";
						}
					}
					satwo.setAreaIds(SqlMapping.LIKE_PRFIX + "," + organization.getAreaId() + "," + SqlMapping.LIKE_PRFIX);
				} else {// 参与及查看
					Organization organization = organizationService.findOne(us.getOrgId());
					if(sv.getTermId()!=null){
						if(StringUtils.isNotEmpty(sv.getSearchStr())){
							hql += " and organizeUserId != :userId or (expertIds like :userIds and term=:termId and activityName like :activeName)";
						}else{
							hql += " and organizeUserId != :userId or (expertIds like :userIds and term=:termId)";
						}
					}else{
						if(StringUtils.isNotEmpty(sv.getSearchStr())){
							hql += " and organizeUserId != :userId or (expertIds like :userIds and activityName like :activeName)";
						}else{
							hql += " and organizeUserId != :userId or expertIds like :userIds";
						}
					}
					sa.setAreaIds(SqlMapping.LIKE_PRFIX + "," + organization.getAreaId() + "," + SqlMapping.LIKE_PRFIX);
					satwo.setOrganizeUserId(us.getUserId());
					satwo.setSpaceId(us.getId());// 当前身份发起的
				}
				sa.addCustomCondition(hql, circleMap);
				satwo.addCustomCondition(hqltwo, circleMap);
				returnMap.put("role", "canCreate");
			}
			PageList<SchoolActivity> listPage = schoolActivityService.findByPage(sa);
			returnMap.put("data", listPage);
			returnMap.put("currentCount", listPage.getPage().getTotalCount());
			if(returnMap.get("role").equals("canCreate")){
				returnMap.put("otherCount", schoolActivityService.count(satwo));
			}
			returnMap.put("sv", sv);
			if (listPage.getDatalist() != null && listPage.getDatalist().size() > 0) {
				for (SchoolActivity saTemp : listPage.getDatalist()) {
					SchoolTeachCircleOrg stco = new SchoolTeachCircleOrg();
					stco.setStcId(saTemp.getSchoolTeachCircleId());
					stco.addCustomCondition(" and state != " + SchoolTeachCircleOrg.YI_JU_JUE, new HashMap<String, Object>());
					stco.addOrder("sort asc");
					saTemp.setStcoList(schoolTeachCircleOrgService.findAll(stco));
				}
			}
		}
		return returnMap;
	}

	/**
	 * 历年资源-查看教研进度表
	 * @param sv
	 * @return
	 * @see com.tmser.tr.history.service.SchoolActivityHistoryService#findTeachlList(com.tmser.tr.history.vo.SearchVo)
	 */
	@Override
	public PageList<TeachSchedule> findTeachlList(SearchVo sv) {
		UserSpace us = userSpaceService.findOne(sv.getSpaceId());
		if (us!=null) {
			List<Integer> stcolist=schoolTeachCircleOrgService.getCircleByOrgId(us.getOrgId());
			TeachSchedule teachSchedule = new TeachSchedule();
			Page page = sv.getPage();
			page.setPageSize(15);//每页显示多少条
			teachSchedule.addPage(page);
			teachSchedule.addOrder("lastupDttm desc");
			teachSchedule.setSchoolYear(sv.getSchoolYear());
			if("canCreate".equals(sv.getSearchStr())){
				teachSchedule.setCrtId(us.getUserId());
			}else{
				if (stcolist.size()!=0) {
					Map<String,Object> paramMap = new HashMap<String,Object>();
					paramMap.put("stcidlist", stcolist);
					teachSchedule.addCustomCondition(" and schoolTeachCircleId in(:stcidlist)", paramMap);	
				}
				teachSchedule.setIsRelease(true);
				Integer sysRoleId=us.getSysRoleId();
				if(sysRoleId.intValue() == SysRole.TEACHER.getId().intValue()){//教师 
					teachSchedule.setSubjectId(us.getSubjectId());
					teachSchedule.setGradeId(us.getGradeId());
				}else if(sysRoleId.intValue() == SysRole.XZ.getId().intValue() || sysRoleId == SysRole.FXZ.getId().intValue() || sysRoleId == SysRole.ZR.getId().intValue()){//校长、副校长或者是主任				
				}else if(sysRoleId.intValue()==SysRole.BKZZ.getId().intValue()){//备课组长
					teachSchedule.setSubjectId(us.getSubjectId());
					teachSchedule.setGradeId(us.getGradeId());
				}else if(sysRoleId.intValue()==SysRole.XKZZ.getId().intValue()){//学科组长
					teachSchedule.setSubjectId(us.getSubjectId());;
				}else if(sysRoleId.intValue()==SysRole.NJZZ.getId().intValue()){//年级组长
					teachSchedule.setGradeId(us.getGradeId());
				}else if(sysRoleId.intValue()==SysRole.JYY.getId().intValue()){//教研员
					Organization org = organizationService.findOne(us.getOrgId());
					teachSchedule.setAreaIds(SqlMapping.LIKE_PRFIX+","+org.getAreaId()+","+SqlMapping.LIKE_PRFIX);
				}else if(sysRoleId.intValue()==SysRole.JYZR.getId().intValue()){//教研主任
					Organization org = organizationService.findOne(us.getOrgId());
					teachSchedule.setAreaIds(SqlMapping.LIKE_PRFIX+","+org.getAreaId()+","+SqlMapping.LIKE_PRFIX);
				}
				
			}
			PageList<TeachSchedule> listPage = teachScheduleService.findByPage(teachSchedule);
			return listPage;
		}
		return null;
	}

	/**
	 * 历年资源-查看校级教研圈
	 * @param sv
	 * @return
	 * @see com.tmser.tr.history.service.SchoolActivityHistoryService#findTeachcircleList(com.tmser.tr.history.vo.SearchVo)
	 */
	@Override
	public List<SchoolTeachCircle> findTeachcircleList(SearchVo sv) {
		UserSpace userSpace = userSpaceService.findOne(sv.getSpaceId());; // 用户空间
		Integer schoolYear = sv.getSchoolYear(); // 学年
		Integer sysRoleId = userSpace.getSysRoleId();
		SchoolTeachCircle stc = new SchoolTeachCircle();
		if (sysRoleId.intValue() == SysRole.JYY.getId().intValue() || sysRoleId.intValue() == SysRole.JYZR.getId().intValue()) {
			// 教研员和教研主任
			Organization org = organizationService.findOne(userSpace.getOrgId());
			stc.setAreaIds(SqlMapping.LIKE_PRFIX + "," + org.getAreaId() + "," + SqlMapping.LIKE_PRFIX);
			stc.setSchoolYear(schoolYear);
			stc.addOrder("lastupDttm desc");
		} else {
			SchoolTeachCircleOrg stco = new SchoolTeachCircleOrg();
			stco.setOrgId(userSpace.getOrgId());
			stco.setSchoolYear(schoolYear);
			stco.addCustomCulomn("distinct(stcId)");
			stco.addCustomCondition(" and state in (" + SchoolTeachCircleOrg.YI_TONG_YI + "," + SchoolTeachCircleOrg.YI_HUI_FU + ")", new HashMap<String, Object>());
			List<SchoolTeachCircleOrg> listAll = schoolTeachCircleOrgService.findAll(stco);
			if (listAll != null && listAll.size() > 0) {
				List<Integer> tempList = new ArrayList<Integer>();
				for (SchoolTeachCircleOrg stcoT : listAll) {
					tempList.add(stcoT.getStcId());
				}
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("stcids", tempList);
				stc.addCustomCondition("and id in (:stcids)", map);
				stc.addOrder("lastupDttm desc");
			} else {
				stc.setId(-1);
			}
		}
		List<SchoolTeachCircle> stcList = schoolTeachCircleService.findAll(stc);
		if (stcList != null && stcList.size() > 0) {
			for (SchoolTeachCircle stct : stcList) {
				SchoolTeachCircleOrg stco = new SchoolTeachCircleOrg();
				stco.setStcId(stct.getId());
				stco.addOrder(" sort asc ");
				List<SchoolTeachCircleOrg> listAll = schoolTeachCircleOrgService.findAll(stco);
				stct.setStcoList(listAll);
			}
		}
		return stcList;
	}

		
		
}
