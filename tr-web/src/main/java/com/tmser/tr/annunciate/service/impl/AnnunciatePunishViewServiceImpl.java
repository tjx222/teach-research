/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.annunciate.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;

import com.tmser.tr.annunciate.bo.AnnunciatePunishView;
import com.tmser.tr.annunciate.bo.JyAnnunciate;
import com.tmser.tr.annunciate.dao.AnnunciatePunishViewDao;
import com.tmser.tr.annunciate.dao.JyAnnunciateDao;
import com.tmser.tr.annunciate.service.AnnunciatePunishViewService;
import com.tmser.tr.annunciate.vo.JyAnnunciateVo;
import com.tmser.tr.common.dao.BaseDAO;
import com.tmser.tr.common.page.PageList;
import com.tmser.tr.common.service.AbstractService;
import com.tmser.tr.uc.bo.User;
import com.tmser.tr.uc.dao.UserDao;
import com.tmser.tr.uc.utils.CurrentUserContext;
import com.tmser.tr.utils.JyCollectionUtils;
/**
 * 通告发布查看 服务实现类
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: AnnunciatePunishView.java, v 1.0 2015-07-01 Generate Tools Exp $
 */
@Service
@Transactional
public class AnnunciatePunishViewServiceImpl extends AbstractService<AnnunciatePunishView, Integer> implements AnnunciatePunishViewService {

	@Autowired
	private AnnunciatePunishViewDao annunciatePunishViewDao;
	
	@Autowired
	private JyAnnunciateDao jyAnnunciateDao;
	
	@Autowired
	private UserDao userDao;
	/**
	 * @return
	 * @see com.tmser.tr.common.service.BaseService#getDAO()
	 */
	@Override
	public BaseDAO<AnnunciatePunishView, Integer> getDAO() {
		return annunciatePunishViewDao;
	}
	
	/**
	 * 分页查询发布的通告
	 * @param vo
	 * @return
	 * @see com.tmser.tr.annunciate.service.AnnunciatePunishViewService#findPunishsByPage(com.tmser.tr.annunciate.vo.JyAnnunciateVo)
	 */
	@Override
	public PageList<JyAnnunciateVo> findPunishsByPage(JyAnnunciateVo vo,Model m) {
		Integer sysRoleId=CurrentUserContext.getCurrentSpace().getSysRoleId();
		if (sysRoleId==1378||sysRoleId==1380) {
			vo.setOrgId(-1);
		}else {
			vo.setOrgId(CurrentUserContext.getCurrentSpace().getOrgId());
			vo.addCustomCondition("and orgId in("+CurrentUserContext.getCurrentSpace().getOrgId()+",-1)", new HashMap<String, Object>());
		}
		vo.setIsDelete(0);
		PageList<JyAnnunciate> annunciatePage = jyAnnunciateDao.findPunishsByPage(vo);
		//转换为vo对象
		List<JyAnnunciateVo> vos = JyCollectionUtils.convertList(annunciatePage.getDatalist(),JyAnnunciateVo.class);
		//填充用户消息
		fillUserInfo(vos);
		//填充查看状态
		fillViewState(vos);
		m.addAttribute("annunciateNum", vos.size());
		return new PageList<JyAnnunciateVo>(vos, annunciatePage.getPage());
	}
	
	/**
	 * 填充查看状态
	 * @param vos
	 */
	private void fillViewState(List<JyAnnunciateVo> vos) {
		if(CollectionUtils.isEmpty(vos)){
			return;
		}
		List<Integer> annunciateIds = JyCollectionUtils.getValues(vos, "id");
		List<AnnunciatePunishView> views = annunciatePunishViewDao.findUserView(CurrentUserContext.getCurrentUserId(),annunciateIds);
		for(JyAnnunciateVo vo : vos){
			for(AnnunciatePunishView view:views){
				if(view.getAnnunciateId().equals(vo.getId())){
					vo.setIsViewed(JyAnnunciateVo.IS_VIEWED_TURE);
				}
			}
		}
	}

	/**
	 * 填充用户消息
	 * @param vos
	 */
	private void fillUserInfo(List<JyAnnunciateVo> vos) {
		if(CollectionUtils.isEmpty(vos)){
			return;
		}
		List<Integer> userIds = JyCollectionUtils.getValues(vos, "userId");
		List<User> users = userDao.findByIds(userIds);
		for(JyAnnunciateVo vo : vos){
			for(User user : users){
				if(user.getId().equals(vo.getUserId())){
					vo.setUserName(user.getName());
				}
			}
		}
	}
	
	/**
	 * 删除发布的通知公告
	 * @param id
	 * @see com.tmser.tr.annunciate.service.AnnunciatePunishViewService#deletePunishAnnunciate(java.lang.Integer)
	 */
	@Override
	public void deletePunishAnnunciate(Integer id) {
		//删除查阅记录
		//annunciatePunishViewDao.deleteView(id);
		JyAnnunciate annunciate = new JyAnnunciate();
		annunciate.setId(id);
		annunciate.setStatus(0);
		annunciate.setCrtDttm(new Date());;
		//通告状态更新为在草稿箱中
		jyAnnunciateDao.update(annunciate);
	}

	/**
	 * @return
	 * @see com.tmser.tr.annunciate.service.AnnunciatePunishViewService#getAnnunciate()
	 */
	@Override
	public Integer getAnnunciateNum() {
		// TODO Auto-generated method stub
		JyAnnunciate jAnnunciate=new JyAnnunciate();
		jAnnunciate.setIsDelete(0);
		jAnnunciate.setStatus(1);
		jAnnunciate.addCustomCondition("and orgId in ("+CurrentUserContext.getCurrentSpace().getOrgId()+",-1)", new HashMap<String, Object>());
		Integer jyanum=jyAnnunciateDao.count(jAnnunciate);
		
		AnnunciatePunishView aView=new AnnunciatePunishView();
		aView.setUserId(CurrentUserContext.getCurrentUserId());
		Integer apvnum=annunciatePunishViewDao.count(aView);	
		return jyanum-apvnum;
	}

	/**
	 *  查看已发布未读的通知公告
	 * @return
	 * @see com.tmser.tr.annunciate.service.AnnunciatePunishViewService#getNotReadJyList()
	 */
	@Override
	public List<JyAnnunciate> getNotReadJyList() {
		// TODO Auto-generated method stub
		return jyAnnunciateDao.getNotReadJyList(CurrentUserContext.getCurrentSpace().getOrgId(),CurrentUserContext.getCurrentUserId());
	}
	
}
