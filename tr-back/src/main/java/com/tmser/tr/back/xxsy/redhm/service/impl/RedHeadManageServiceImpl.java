/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.back.xxsy.redhm.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.tmser.tr.back.xxsy.redhm.service.RedHeadManageService;
import com.tmser.tr.common.dao.BaseDAO;
import com.tmser.tr.common.service.AbstractService;
import com.tmser.tr.common.utils.WebThreadLocalUtils;
import com.tmser.tr.manage.org.bo.Organization;
import com.tmser.tr.redheadmanage.bo.RedHeadManage;
import com.tmser.tr.redheadmanage.dao.RedHeadManageDao;
import com.tmser.tr.uc.utils.CurrentUserContext;
import com.tmser.tr.uc.utils.SessionKey;

/**
 * 红头管理 服务实现类
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: RedHeadManage.java, v 1.0 2015-08-26 Generate Tools Exp $
 */
@Service
@Transactional
public class RedHeadManageServiceImpl extends AbstractService<RedHeadManage, Integer>  implements RedHeadManageService {

	@Autowired 
	private RedHeadManageDao redHeadManageDao;
	
	/**
	 * 显示所有学校下的红头标题
	 * @return
	 * @see com.tmser.tr.back.redhm.service.RedHeadManageService#redhmlist()
	 */
	@Override
	public List<RedHeadManage> getredhmlist(Model m,RedHeadManage rhm) {
		// TODO Auto-generated method stub
		//rhm.setCrtId(CurrentUserContext.getCurrentUserId());
		rhm.setIsDelete(0);//是否删除
		rhm.setIsEnable(1);//是否可用
		rhm.addOrder("crtDttm desc");
		List<RedHeadManage> rhmlist=redHeadManageDao.listAll(rhm);
		
		//查询所有学校     
		/*UserManagescope um=new UserManagescope();
		um.setUserId(us.getUserId());
		List<UserManagescope> umlist=new ArrayList<UserManagescope>();
		List<UserManagescope> uList=userManagescopeDao.listAll(um);
		for (UserManagescope u:uList) {
			UserManagescope findone=userManagescopeDao.get(u.getId());
		    rhm.setOrgId(u.getOrgId());
		    List<RedHeadManage> rhmList=redHeadManageDao.listAll(rhm);
		    findone.setRemList(rhmList);
		    umlist.add(findone);
		    //m.addAttribute("rhmList", rhmList);
		}*/
		return rhmlist;
	}

	/**
	 * 修改或增加一条红头
	 * @param rhm
	 * @see com.tmser.tr.back.redhm.service.RedHeadManageService#updateOrSaveRedHead(com.tmser.tr.redheadmanage.bo.RedHeadManage)
	 */
	@Override
	public void updateOrSaveRedHead(RedHeadManage rhm) {
		Organization o = (Organization) WebThreadLocalUtils
				.getSessionAttrbitue(SessionKey.CURRENT_ORG);
		rhm.setCrtId(CurrentUserContext.getCurrentUserId());
		rhm.setLastupId(CurrentUserContext.getCurrentUserId());
		if (rhm.getOrgId()==null) {
			rhm.setOrgId(o.getId());
		}
		if (rhm.getId()!=null) {
			redHeadManageDao.update(rhm);
		}else {
			rhm.setIsDelete(0);
			rhm.setIsEnable(1);
			rhm.setCrtDttm(new Date());
			rhm.setLastupDttm(new Date());
			redHeadManageDao.insert(rhm);
		}
		// TODO Auto-generated method stub
	}

	/**
	 * 删除一条红头标题记录
	 * @param id
	 * @see com.tmser.tr.back.redhm.service.RedHeadManageService#deleteRedHead(java.lang.Integer)
	 */
	@Override
	public void deleteRedHead(Integer id) {
		// TODO Auto-generated method stub
		RedHeadManage rhm=new RedHeadManage();
		rhm.setId(id);
		rhm.setIsDelete(1);
		redHeadManageDao.update(rhm);
	}

	/**
	 * 编辑
	 * @param id
	 * @see com.tmser.tr.back.redhm.service.RedHeadManageService#updateRedHead(java.lang.Integer)
	 */
	@Override
	public RedHeadManage updateRedHead(Integer id) {
		return redHeadManageDao.get(id);
	}

	@Override
	public BaseDAO<RedHeadManage, Integer> getDAO() {
		// TODO Auto-generated method stub
		return redHeadManageDao;
	}


}
