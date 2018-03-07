/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.school.show.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import com.tmser.tr.annunciate.bo.JyAnnunciate;
import com.tmser.tr.annunciate.dao.JyAnnunciateDao;
import com.tmser.tr.annunciate.vo.JyAnnunciateVo;
import com.tmser.tr.common.dao.BaseDAO;
import com.tmser.tr.school.show.bo.SchoolShow;
import com.tmser.tr.common.page.PageList;
import com.tmser.tr.common.service.AbstractService;
import com.tmser.tr.school.show.service.SchoolShowService;
import com.tmser.tr.school.show.dao.SchoolShowDao;
import com.tmser.tr.uc.utils.CurrentUserContext;
/**
 * 为学校首页提供数据 服务实现类
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: SchoolShow.java, v 1.0 2015-09-23 Generate Tools Exp $
 */
@Service
@Transactional
public class SchoolShowServiceImpl extends AbstractService<SchoolShow, String> implements SchoolShowService {

	@Autowired
	private SchoolShowDao schoolShowDao;
	@Autowired
	private JyAnnunciateDao jyAnnunciateDao;
	/**
	 * @return
	 * @see com.tmser.tr.common.service.BaseService#getDAO()
	 */
	@Override
	public BaseDAO<SchoolShow, String> getDAO() {
		return schoolShowDao;
	}

	@Override
	public List<SchoolShow> findAllShowInfo(SchoolShow model) {
		return schoolShowDao.listAll(model);
	}

	/**
	 * 通知公告列表
	 * @param vo
	 * @return
	 * @see com.tmser.tr.school.show.service.SchoolShowService#getPageList(com.tmser.tr.annunciate.vo.JyAnnunciateVo)
	 */
	@Override
	public PageList<JyAnnunciate> getPageList(JyAnnunciateVo vo) {
		// TODO Auto-generated method stub
		vo.addCustomCondition("and orgId in ("+CurrentUserContext.getCurrentSpace().getOrgId()+",-1)", new HashMap<String, Object>());
		vo.addOrder("crtDttm desc");
		PageList<JyAnnunciate>  pageList=jyAnnunciateDao.listPage(vo);
		return pageList;
	}

}
