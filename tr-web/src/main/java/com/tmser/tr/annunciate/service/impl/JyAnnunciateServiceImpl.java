/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.annunciate.service.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.tmser.tr.annunciate.bo.JyAnnunciate;
import com.tmser.tr.annunciate.dao.JyAnnunciateDao;
import com.tmser.tr.annunciate.service.JyAnnunciateService;
import com.tmser.tr.annunciate.vo.JyAnnunciateVo;
import com.tmser.tr.common.dao.BaseDAO;
import com.tmser.tr.common.orm.SqlMapping;
import com.tmser.tr.common.page.PageList;
import com.tmser.tr.common.service.AbstractService;
import com.tmser.tr.common.utils.WebThreadLocalUtils;
import com.tmser.tr.manage.org.bo.Organization;
import com.tmser.tr.manage.org.service.OrganizationService;
import com.tmser.tr.manage.resources.service.ResourcesService;
import com.tmser.tr.uc.SysRole;
import com.tmser.tr.uc.bo.UserSpace;
import com.tmser.tr.uc.utils.CurrentUserContext;
import com.tmser.tr.uc.utils.SessionKey;
/**
 * 通告 服务实现类
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: JyAnnunciate.java, v 1.0 2015-06-12 Generate Tools Exp $
 */
@Service
@Transactional
public class JyAnnunciateServiceImpl extends AbstractService<JyAnnunciate, Integer> implements JyAnnunciateService {

	@Autowired
	private JyAnnunciateDao jyAnnunciateDao;
	
	@Autowired
	private ResourcesService resourcesService;
	
	@Autowired
	private OrganizationService organizationService;
	
	/**
	 * @return
	 * @see com.tmser.tr.common.service.BaseService#getDAO()
	 */
	@Override
	public BaseDAO<JyAnnunciate, Integer> getDAO() {
		return jyAnnunciateDao;
	}
	
	/**
	 * 通知公告列表
	 * @param jyAnnunciateVo
	 * @return
	 * @see com.tmser.tr.annunciate.service.JyAnnunciateService#getAnnunciateList(com.tmser.tr.annunciate.vo.JyAnnunciateVo)
	 */
	@Override
	public PageList<JyAnnunciate> getAnnunciateList(
			JyAnnunciateVo jyAnnunciateVo,Model m) {
		jyAnnunciateVo.setIsDelete(0);
		jyAnnunciateVo.setOrgId(CurrentUserContext.getCurrentUser().getOrgId());
		PageList<JyAnnunciate> pageList=jyAnnunciateDao.listPage(jyAnnunciateVo);
		Integer jaSum=jyAnnunciateDao.count(jyAnnunciateVo);
		m.addAttribute("jaSum", jaSum);
		return pageList;
	}


	/**
	 * 保存通告
	 * @param jyAnnunciate
	 * @param status
	 * @see com.tmser.tr.annunciate.service.JyAnnunciateService#saveAnnunciate(com.tmser.tr.annunciate.bo.JyAnnunciate, java.lang.Integer)
	 */
	@Override
	public void saveAnnunciate(JyAnnunciate annunciate, Integer status) {
		UserSpace userSpace = (UserSpace)WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE); //用户空间
		//保存公告通知
		annunciate.setStatus(status);//通告状态
		annunciate.setCrtId(userSpace.getUserId());//创建人
		annunciate.setCrtDttm(new Date());//创建时间
		annunciate.setLastupId(userSpace.getUserId());//最后更新用户
		annunciate.setLastupDttm(new Date());//最后更新时间
		if (annunciate.getFromWhere()==null) {
			annunciate.setFromWhere("XXX(20XX)XX号");
		}
		if (annunciate.getIsDisplay()==null) {
			annunciate.setIsDisplay(0);
		}
		if (Integer.parseInt(annunciate.getType())==0) {
			annunciate.setRedTitleId(0);
		}
		
		Set<String> newAttachs = new HashSet<String>();
		
		if (annunciate.getAttachs()!=null) {
			String atta[]=annunciate.getAttachs().split("#");
			for (int i = 0; i < atta.length; i++) {
				newAttachs.add(atta[i]);
				resourcesService.updateTmptResources(atta[i]);
			}
		}
		
		if (annunciate.getId()!=null) {
			JyAnnunciate old = findOne(annunciate.getId());
			if(StringUtils.isNotEmpty(old.getAttachs()) ){
				String atta[]=old.getAttachs().split("#");
				for (int i = 0; i < atta.length; i++) {
					if(!newAttachs.contains(atta[i]))
					resourcesService.deleteResources(atta[i]);
				}
			}
			
			jyAnnunciateDao.update(annunciate);
		}else {
			// TODO Auto-generated method stub
			Integer roleId=userSpace.getSysRoleId().intValue();
			if (roleId==SysRole.JYZR.getId().intValue()||roleId==SysRole.JYY.getId().intValue()) {//是教研主任或者教研员
				annunciate.setAnnunciateType(1);
				annunciate.setIsForward(0);
			}else {
				annunciate.setAnnunciateType(0);
				annunciate.setIsForward(1);
			}
			if(StringUtils.isEmpty(annunciate.getForwardDescription())){
				annunciate.setForwardDescription("");
			}
			annunciate.setUserId(userSpace.getUserId());//用户id
			annunciate.setOrgId(userSpace.getOrgId());//机构id
			annunciate.setSpaceId(userSpace.getId());//工作空间id
			annunciate.setAnnunciateRange(0);//通告范围,暂设为 学校
			annunciate.setIsDelete(0);//是否已删除
			annunciate.setIsEnable(1);//是否可用
			annunciate.setIsTop(false);
			jyAnnunciateDao.insert(annunciate);
		}
		
	}
	

	/**
	 * 获取上一篇通知公告
	 * @param id
	 * @param orgId
	 * @param status
	 * @return
	 * @see com.tmser.tr.annunciate.service.JyAnnunciateService#getPreAnnunciate(java.lang.Integer, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public JyAnnunciate getPreAnnunciate(Integer id, Integer orgId,
			Integer status,Integer type) {
		// TODO Auto-generated method stub
		JyAnnunciate jAnnunciate=new JyAnnunciate();
		jAnnunciate.setId(id);
		jAnnunciate.setOrgId(orgId);
		jAnnunciate.setStatus(status);
		jAnnunciate.setUserId(CurrentUserContext.getCurrentUserId());
		return jyAnnunciateDao.getPreAnnunciate(jAnnunciate,type);
	}

	/**
	 * 获取下一篇通知公告
	 * @param id
	 * @param orgId
	 * @param status
	 * @return
	 * @see com.tmser.tr.annunciate.service.JyAnnunciateService#getNextAnnunciate(java.lang.Integer, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public JyAnnunciate getNextAnnunciate(Integer id, Integer orgId,
			Integer status,Integer type) {
		// TODO Auto-generated method stub
		JyAnnunciate jAnnunciate=new JyAnnunciate();
		jAnnunciate.setId(id);
		jAnnunciate.setOrgId(orgId);
		jAnnunciate.setStatus(status);
		jAnnunciate.setUserId(CurrentUserContext.getCurrentUserId());
		return jyAnnunciateDao.getNextAnnunciate(jAnnunciate,type);
	}

	@Override
	public List<Organization> getOrgListOfArea(String search) {
		UserSpace userSpace = (UserSpace)WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE); //用户空间
		Organization organization = organizationService.findOne(userSpace.getOrgId());
		Organization model = new Organization();
		model.setAreaId(organization.getAreaId());
		model.setOrgType(organization.getOrgType());
		model.setType(0);//0代表学校
		//model.setOrgType(0);//会员校
		model.setEnable(1);
		model.setPhaseTypes(SqlMapping.LIKE_PRFIX +userSpace.getPhaseId()+ SqlMapping.LIKE_PRFIX);//加入当前用户所选的学校类型筛选
		if(search!=null && !"".equals(search)){
			model.setName(SqlMapping.LIKE_PRFIX +search+ SqlMapping.LIKE_PRFIX);
		}
		List<Organization> orgList = organizationService.findAll(model);
		return orgList;
	}
	
	/**
	 * 转发通知公告
	 */
	@Override
	public void forwardAnnunciate(JyAnnunciate jyAnnunciate) {
		//转发
		Integer orgId=CurrentUserContext.getCurrentUser().getOrgId();
		JyAnnunciate model=jyAnnunciateDao.get(jyAnnunciate.getId());
		if (StringUtils.isNotEmpty(model.getOrgsJoinIds())) {
			model.setId(null);
			model.setOrgId(orgId);
			model.setOrgsJoinIds(null);
			model.setOrgsJoinCount(null);
			model.setCrtDttm(new Date());
			model.setUserId(CurrentUserContext.getCurrentUserId());
			model.setIsForward(1);
			model.setForwardDescription(jyAnnunciate.getForwardDescription());
			model.setStatus(jyAnnunciate.getStatus());
			model.setLastupDttm(new Date());
			jyAnnunciateDao.insert(model);
			//该学校已转发
			if (StringUtils.isEmpty(jyAnnunciate.getFlags())) {
				jyAnnunciate.setForwardDescription(","+orgId+",");
			}else {
				jyAnnunciate.setForwardDescription(jyAnnunciate.getFlags()+orgId+",");
			}
			jyAnnunciateDao.update(jyAnnunciate);
		}else {
			jyAnnunciateDao.update(jyAnnunciate);
		}
	}
}
