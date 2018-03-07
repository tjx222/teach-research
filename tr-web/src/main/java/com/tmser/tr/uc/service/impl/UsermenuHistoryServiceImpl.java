/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.uc.service.impl;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmser.tr.common.dao.BaseDAO;
import com.tmser.tr.common.service.AbstractService;
import com.tmser.tr.manage.meta.dao.MenuDao;
import com.tmser.tr.uc.bo.UserMenu;
import com.tmser.tr.uc.bo.UsermenuHistory;
import com.tmser.tr.uc.dao.UserMenuDao;
import com.tmser.tr.uc.dao.UsermenuHistoryDao;
import com.tmser.tr.uc.service.UsermenuHistoryService;
import com.tmser.tr.utils.StringUtils;
/**
 * 用户功能历史记录 服务实现类
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: UsermenuHistory.java, v 1.0 2016-05-19 Generate Tools Exp $
 */
@Service
@Transactional
public class UsermenuHistoryServiceImpl extends AbstractService<UsermenuHistory, Integer> implements UsermenuHistoryService {

	@Autowired
	private UsermenuHistoryDao usermenuHistoryDao;
	
	@Autowired
	private UserMenuDao usermenuDao;
	
	@Autowired
	private MenuDao menuDao;
	
	/**
	 * @return
	 * @see com.tmser.tr.common.service.BaseService#getDAO()
	 */
	@Override
	public BaseDAO<UsermenuHistory, Integer> getDAO() {
		return usermenuHistoryDao;
	}

	/**
	 * @param userId
	 * @param schoolYear
	 * @return
	 * @see com.tmser.tr.uc.service.UsermenuHistoryService#listUserHistory(java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public Set<String> listUserHistory(Integer userId, Integer schoolYear) {
		if(userId == null || schoolYear == null){
			return Collections.emptySet();
		}
		UsermenuHistory model = new UsermenuHistory();
		model.setUserId(userId);
		model.setSchoolYear(schoolYear);
		UsermenuHistory um = usermenuHistoryDao.getOne(model);
		Set<String> rs = new HashSet<String>();
		if(um != null){
			for(String code : StringUtils.split(um.getMenus(), StringUtils.COMMA)){
				rs.add(code);
			}
		}
		return rs;
	}

	/**
	 * @param userId
	 * @param schoolYear
	 * @return
	 * @see com.tmser.tr.uc.service.UsermenuHistoryService#createUserHistory(java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public UsermenuHistory createUserHistory(Integer userId, Integer schoolYear) {
		if(userId == null || schoolYear == null){
			return null;
		}
		UsermenuHistory model = new UsermenuHistory();
		model.setUserId(userId);
		model.setSchoolYear(schoolYear);
		UsermenuHistory um = usermenuHistoryDao.getOne(model);
		if(um == null){
			Set<String> menuCodeSet = new HashSet<String>();
			UserMenu umodel = new UserMenu();
			umodel.setUserId(userId);
			List<UserMenu> umList = usermenuDao.listAll(umodel);
			for(UserMenu u : umList){
				menuCodeSet.add(menuDao.get(u.getMenuId()).getCode());
			}
			if(menuCodeSet.size() > 0){
				model.setMenus(StringUtils.join(menuCodeSet.iterator(),StringUtils.COMMA));
				return model;
			}
		}
		return null;
	}

	/**
	 * @param uhs
	 * @see com.tmser.tr.uc.service.UsermenuHistoryService#batchInsert(java.util.List)
	 */
	@Override
	public void batchInsert(List<UsermenuHistory> uhs) {
		usermenuHistoryDao.batchInsert(uhs);
	}

}
