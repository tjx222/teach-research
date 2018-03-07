/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.uc.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmser.tr.common.dao.BaseDAO;
import com.tmser.tr.common.service.AbstractService;
import com.tmser.tr.common.utils.WebThreadLocalUtils;
import com.tmser.tr.manage.resources.bo.Resources;
import com.tmser.tr.manage.resources.service.ResourcesService;
import com.tmser.tr.uc.bo.User;
import com.tmser.tr.uc.dao.UserDao;
import com.tmser.tr.uc.service.UserService;
import com.tmser.tr.uc.utils.SessionKey;
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
		if (!StringUtils.isEmpty(photoPath)) {
			Resources resources = resourcesService.findOne(photoPath);
			User updateModelUser = new User();
			updateModelUser.setOriginalPhoto(resources.getPath());
			updateModelUser.setId(oldUser.getId());
			updateModelUser.setPhoto(resourcesService.resizeImage(resources, 134, 128));
			userDao.update(updateModelUser);
			resourcesService.updateTmptResources(photoPath);
			// 尝试删除老头像
			if(!StringUtils.isEmpty(oldUser.getOriginalPhoto())){
				resourcesService.deleteWebResources(oldUser.getOriginalPhoto());
			}
			oldUser.setOriginalPhoto(resources.getPath());
			oldUser.setPhoto(updateModelUser.getPhoto());
		}

		WebThreadLocalUtils.setSessionAttrbitue(SessionKey.CURRENT_USER,
				oldUser);
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
