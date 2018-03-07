/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.uc.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmser.tr.common.dao.BaseDAO;
import com.tmser.tr.common.page.PageList;
import com.tmser.tr.common.service.AbstractService;
import com.tmser.tr.common.utils.WebThreadLocalUtils;
import com.tmser.tr.manage.resources.bo.Resources;
import com.tmser.tr.manage.resources.service.ResourcesService;
import com.tmser.tr.uc.bo.User;
import com.tmser.tr.uc.dao.UserDao;
import com.tmser.tr.uc.service.UserService;
import com.tmser.tr.uc.utils.SessionKey;
import com.tmser.tr.uc.vo.UserSearchModel;
import com.tmser.tr.utils.StringUtils;
/**
 * 用户信息 服务实现类
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: User.java, v 1.0 2015-01-31 Generate Tools Exp $
 */
@Service
@Transactional
public class UserServiceImpl extends AbstractService<User, Integer> implements UserService {

	@Autowired
	private UserDao userDao;
	@Autowired
	private ResourcesService resourcesService;
	
	/**
	 * @return
	 * @see com.tmser.tr.common.service.BaseService#getDAO()
	 */
	@Override
	public BaseDAO<User, Integer> getDAO() {
		return userDao;
	}

	/**
	 * @param photoPath
	 * @see com.tmser.tr.uc.service.UserService#modifyPhoto(java.lang.String)
	 */
	@Override
	public void modifyPhoto(String photoPath) {
		User oldUser = (User) WebThreadLocalUtils
				.getSessionAttrbitue(SessionKey.CURRENT_USER);
		if (StringUtils.isNotEmpty(photoPath)) {
			Resources resources = resourcesService.findOne(photoPath);
			User updateModelUser = new User();
			updateModelUser.setOriginalPhoto(resources.getPath());
			updateModelUser.setId(oldUser.getId());
			updateModelUser.setPhoto(resourcesService.resizeImage(resources, 134, 128));
			userDao.update(updateModelUser);
			resourcesService.updateTmptResources(photoPath);
			// 尝试删除老头像
			if(!org.apache.commons.lang3.StringUtils.isEmpty(oldUser.getOriginalPhoto())){
				resourcesService.deleteWebResources(oldUser.getOriginalPhoto());
			}
			oldUser.setOriginalPhoto(resources.getPath());
			oldUser.setPhoto(updateModelUser.getPhoto());
		}

		WebThreadLocalUtils.setSessionAttrbitue(SessionKey.CURRENT_USER,
				oldUser);
	}
    
	/**
	 * 根据真实姓名判断用户是否已经存在
	 * @param userName
	 * @return
	 * @see com.tmser.tr.uc.service.UserService#userAlreadyExistByName(java.lang.String)
	 */
	@Override
	public User getUserByName(String userName,Integer orgId) {
		User user = new User();
		user.setOrgId(orgId);
		user.setName(userName);
		return userDao.getOne(user);
	}

	@Override
	public PageList<Map<String,Object>> findUsersBySearchModel(UserSearchModel usm){
		boolean needSpace = false;
		StringBuilder sql = 
				new StringBuilder("SELECT l.id id, l.loginname loginname, l.logincode logincode,"
						+ "l.`enable` `enable`, u.`name` `name`, u.`appId` appId, u.crtDttm crtDttm,"
						+ "u.orgId orgId, u.orgName orgName from Login l, User u ");
		if(usm.getGradeId() != null ||
				usm.getSubjectId() != null ||
				usm.getPhaseId() != null ||
				usm.getRoleId() != null ||
				usm.getSysRoleId() != null||
				usm.getSchoolYear() != null){
			sql.append(",UserSpace s where l.id = u.id  and u.id = s.userId ");
			needSpace = true;
		}else{
			sql.append("where l.id = u.id ");
		}
		
		Map<String,Object> parmas = new HashMap<String,Object>();
		if(usm.getId() != null){
			sql.append("and l.id = :id ");
			parmas.put("id", usm.getId());
		}else{
			if(StringUtils.isNotEmpty(usm.getAccount())){
				sql.append("and l.loginname like :account ");
				parmas.put("account", "%"+usm.getAccount().trim()+"%");
			}
			if(StringUtils.isNotEmpty(usm.getName()) && !needSpace){
				sql.append("and u.name like :name ");
				parmas.put("name", "%"+usm.getName().trim()+"%");
			}
			
			if(StringUtils.isNotEmpty(usm.getOrgName())){
				sql.append("and u.orgName like :orgName ");
				parmas.put("orgName", "%"+usm.getOrgName().trim()+"%");
			}
			
			if(usm.getOrgId() != null && !needSpace){
				sql.append("and u.orgId = :orgId ");
				parmas.put("orgId", usm.getOrgId());
			}
			
			if(usm.getOrgIds() != null && usm.getOrgIds().size() > 0 && !needSpace){
				sql.append("and u.orgId in (:orgIds) ");
				parmas.put("orgIds", usm.getOrgIds());
			}
			
			if(usm.getAppId() != null){
				sql.append("and u.appId = :appId ");
				parmas.put("appId", usm.getAppId());
			}
			
			if(usm.getUserType() != null){
				sql.append("and u.userType = :userType ");
				parmas.put("userType", usm.getUserType());
			}
			
			if(usm.getPhaseId() != null && needSpace){
				sql.append("and s.phaseId in(0, :phaseId) ");
				parmas.put("phaseId", usm.getPhaseId());
			}
			
			if(usm.getSubjectId() != null && needSpace){
				sql.append("and s.subjectId = :subjectId ");
				parmas.put("subjectId", usm.getSubjectId());
			}
			
			if(usm.getSchoolYear() != null){
				sql.append("and s.schoolYear = :schoolYear ");
				parmas.put("schoolYear", usm.getSchoolYear());
			}
			
			if(usm.getRoleId() != null && needSpace){
				sql.append("and s.roleId = :roleId ");
				parmas.put("roleId", usm.getRoleId());
			}else if(usm.getSysRoleId() != null && needSpace){
				sql.append("and s.sysRoleId = :sysRoleId ");
				parmas.put("sysRoleId", usm.getSysRoleId());
			}
			
			if(usm.getGradeId() != null && needSpace){
				sql.append("and s.gradeId = :gradeId ");
				parmas.put("gradeId", usm.getGradeId());
			}
			
			if(usm.getOrgId() != null && needSpace){
				sql.append("and s.orgId = :orgId ");
				parmas.put("orgId", usm.getOrgId());
			}
			
			if(usm.getOrgIds() != null && usm.getOrgIds().size() > 0 &&  needSpace){
				sql.append("and s.orgId in (:orgIds) ");
				parmas.put("orgIds", usm.getOrgIds());
			}
			
			if(StringUtils.isNotEmpty(usm.getName()) && needSpace){
				sql.append("and s.username like :name ");
				parmas.put("name", "%"+usm.getName().trim()+"%");
			}
		}
		
		if(needSpace){
			sql.append(" GROUP BY s.user_id ");
		}
		
		if(StringUtils.isNotBlank(usm.order())){
			sql.append(" order by ").append(usm.order());
		}
		
		return userDao.queryPageByNamedSql(sql.toString(), parmas, usm.getPage());
	}
	
	/**
	 * @param userType
	 * @return
	 * @see com.tmser.tr.uc.service.UserService#newUser(java.lang.Integer)
	 */
	@Override
	public User newUser(Integer id,Integer userType) {
		User user = new User();
		Date now = new Date();
		user.setCrtDttm(now);
		user.setLastupDttm(now);
		user.setLastLogin(now);
		user.setCrtId(0);
		user.setUserType(userType);
		user.setId(id);
		return user;
	}

}
