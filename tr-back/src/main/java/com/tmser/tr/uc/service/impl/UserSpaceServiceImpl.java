/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.uc.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import com.tmser.tr.common.dao.BaseDAO;
import com.tmser.tr.uc.bo.UserSpace;
import com.tmser.tr.common.service.AbstractService;
import com.tmser.tr.uc.service.UserSpaceService;
import com.tmser.tr.uc.dao.UserSpaceDao;
/**
 * 用户空间信息 服务实现类
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: UserSpace.java, v 1.0 2015-02-03 Generate Tools Exp $
 */
@Service
@Transactional
public class UserSpaceServiceImpl extends AbstractService<UserSpace, Integer> implements UserSpaceService {

	@Autowired
	private UserSpaceDao userSpaceDao;
	
	/**
	 * @return
	 * @see com.tmser.tr.common.service.BaseService#getDAO()
	 */
	@Override
	public BaseDAO<UserSpace, Integer> getDAO() {
		return userSpaceDao;
	}

	/**
	 * 通过学科集合和年级集合查询用户集合
	 * @param subjectIds
	 * @param gradeIds
	 * @return
	 * @see com.tmser.tr.uc.service.UserSpaceService#findUserBySubjectAndGrade(java.lang.String, java.lang.String)
	 */
	@Override
	public List<UserSpace> findUserBySubjectAndGrade(String subjectIds,String gradeIds) {
		UserSpace us = new UserSpace();
		String hql = "";
		Map<String,Object> map = new HashMap<String,Object>();
		if(subjectIds!=null && !"".equals(subjectIds)){
			String[] subIds = subjectIds.split(",");
			List<String> tempList = new ArrayList<String>();
			for(String str : subIds){
				tempList.add(str);
			}
			hql += " and subjectId in (:subjectId) ";
			map.put("subjectId", tempList);
		}
		if(gradeIds!=null && !"".equals(gradeIds)){
			String[] graIds = gradeIds.split(",");
			List<String> tempList = new ArrayList<String>();
			for(String str : graIds){
				tempList.add(str);
			}
			hql += " and gradeId in (:gradeId) ";
			map.put("gradeId", tempList);
		}
		us.addCustomCondition(hql, map);
		us.addGroup(" userId ");
		us.addOrder(" sysRoleId desc ");
		us.addCustomCulomn(" userId ");
		if(!"".equals(hql)){
			return userSpaceDao.listAll(us);
		}else{
			return null;
		}
		
	}

	/**
	 * 通过用户姓名、角色、学科、年级判断空间是否已经存在(同一个机构下)
	 * @param userName
	 * @param roleId
	 * @param subjectId
	 * @param gradeId
	 * @return
	 * @see com.tmser.tr.uc.service.UserSpaceService#spaceAlreadyExist(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public boolean spaceAlreadyExist(String userName, Integer roleId,
			Integer subjectId, Integer gradeId,Integer orgId,Integer schoolYear) {
		UserSpace userSpace = new UserSpace();
		userSpace.setOrgId(orgId);
		userSpace.setUsername(userName);
		userSpace.setRoleId(roleId);
		userSpace.setSubjectId(subjectId);
		userSpace.setGradeId(gradeId);
		userSpace.setSchoolYear(schoolYear);
		if(userSpaceDao.getOne(userSpace)!=null){
			return true;
		}
		return false;
	}

	/**
	 * 通过用户姓名、角色、学科、学段判断空间是否已经存在（同一个机构下）
	 * @param userName
	 * @param roleId
	 * @param subjectId
	 * @param phaseId
	 * @return
	 * @see com.tmser.tr.uc.service.UserSpaceService#spaceAlreadyExist2(java.lang.String, java.lang.Integer, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public boolean spaceAlreadyExist2(String userName, Integer roleId,
			Integer subjectId, Integer phaseId,Integer orgId,Integer schoolYear) {
		UserSpace userSpace = new UserSpace();
		userSpace.setOrgId(orgId);
		userSpace.setUsername(userName);
		userSpace.setRoleId(roleId);
		userSpace.setSubjectId(subjectId);
		userSpace.setPhaseId(phaseId);
		userSpace.setSchoolYear(schoolYear);
		if(userSpaceDao.getOne(userSpace)!=null){
			return true;
		}
		return false;
	}

	
}
