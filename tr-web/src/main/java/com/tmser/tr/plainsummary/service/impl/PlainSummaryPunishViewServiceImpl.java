/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.plainsummary.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.tmser.tr.activity.bo.SchoolTeachCircleOrg;
import com.tmser.tr.activity.dao.SchoolTeachCircleOrgDao;
import com.tmser.tr.common.dao.BaseDAO;
import com.tmser.tr.common.page.PageList;
import com.tmser.tr.common.service.AbstractService;
import com.tmser.tr.common.utils.WebThreadLocalUtils;
import com.tmser.tr.plainsummary.bo.PlainSummary;
import com.tmser.tr.plainsummary.bo.PlainSummaryPunishView;
import com.tmser.tr.plainsummary.dao.PlainSummaryDao;
import com.tmser.tr.plainsummary.dao.PlainSummaryPunishViewDao;
import com.tmser.tr.plainsummary.service.PlainSummaryPunishViewService;
import com.tmser.tr.plainsummary.vo.PlainSummaryVo;
import com.tmser.tr.uc.bo.User;
import com.tmser.tr.uc.dao.UserDao;
import com.tmser.tr.uc.utils.CurrentUserContext;
import com.tmser.tr.uc.utils.SessionKey;
import com.tmser.tr.utils.JyCollectionUtils;
/**
 * 计划总结发布查看记录 服务实现类
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: PlainSummaryPunishView.java, v 1.0 2015-06-30 Generate Tools Exp $
 */
@Service
@Transactional
public class PlainSummaryPunishViewServiceImpl extends AbstractService<PlainSummaryPunishView, Integer> implements PlainSummaryPunishViewService {

	@Autowired
	private PlainSummaryPunishViewDao plainSummaryPunishViewDao;
	
	@Autowired
	private PlainSummaryDao plainSummaryDao;
	
	@Autowired
	private SchoolTeachCircleOrgDao schoolTeachCircleOrgDao;
	
	@Autowired
	private UserDao userDao;
	/**
	 * @return
	 * @see com.tmser.tr.common.service.BaseService#getDAO()
	 */
	@Override
	public BaseDAO<PlainSummaryPunishView, Integer> getDAO() {
		return plainSummaryPunishViewDao;
	}

	@Override
	public Integer delteView(@NotNull Integer psId) {
		return plainSummaryPunishViewDao.delteView(psId);
	}
	
	
	
	/**
	 * 分页查询已发布的计划总结
	 * @param ps
	 * @return
	 */
	@Override
	public PageList<PlainSummaryVo> findPunishsByPage(PlainSummary ps) {
		SchoolTeachCircleOrg circleOrgSearch = new SchoolTeachCircleOrg();
		circleOrgSearch.setOrgId(CurrentUserContext.getCurrentUser().getOrgId());
		circleOrgSearch.setSchoolYear((Integer) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SCHOOLYEAR));
		//查询学校所属校际教研圈
		List<SchoolTeachCircleOrg> circleOrgs = schoolTeachCircleOrgDao.listAll(circleOrgSearch);
		List<Integer> circleIds = JyCollectionUtils.getValues(circleOrgs, "stcId");
		//分页查询用户能够查看的已发布计划总计
		PageList<PlainSummary> psPage = plainSummaryDao.findPunishsByPage(ps,circleIds);
		List<PlainSummaryVo> datalist = new ArrayList<PlainSummaryVo>();
		PageList<PlainSummaryVo> page = new PageList<PlainSummaryVo>(datalist, psPage.getPage());
		//如果查询为空，直接返回
		if(CollectionUtils.isEmpty(psPage.getDatalist())){
			return page;
		}
		//将bo转换为vo
		for(PlainSummary item:psPage.getDatalist()){
			PlainSummaryVo vo = new PlainSummaryVo();
			BeanUtils.copyProperties(item, vo);
			datalist.add(vo);
		}
		//填充查阅状态
		fillViewState(datalist);
		//填充用户消息
		fillUserInfo(datalist);
		return page;
	}
	
	/**
	 * 填充用户消息
	 * @param datalist
	 */
	private void fillUserInfo(List<PlainSummaryVo> datalist) {
		if(CollectionUtils.isEmpty(datalist)){
			return;
		}
		List<Integer> userIds = JyCollectionUtils.getValues(datalist, "userId");
		List<User> list = userDao.findByIds(userIds);
		for(PlainSummaryVo vo:datalist){
			for(User user:list){
				if(user.getId().equals(vo.getUserId())){
					vo.setUserName(user.getName());
				}
				
			}
		}
	}

	/**
	 * 填充查阅状态
	 * @param datalist
	 */
	private void fillViewState(List<PlainSummaryVo> datalist) {
		if(CollectionUtils.isEmpty(datalist)){
			return;
		}
		List<Integer> psIds = JyCollectionUtils.getValues(datalist, "id");
		List<PlainSummaryPunishView> views = plainSummaryPunishViewDao.findView(CurrentUserContext.getCurrentUserId(),psIds);
		if(CollectionUtils.isEmpty(views)){
			return;
		}
		//遍历设置查阅状态
		for(PlainSummaryVo vo:datalist){
			for(PlainSummaryPunishView view:views){
				if(vo.getId().equals(view.getPlainSummaryId())){
					vo.setIsViewed(PlainSummaryVo.IS_VIEW_VIEWED);
				}
			}
		}
	}
	
	/**
	 * 查询未读的已提交计划总结数目
	 * @return
	 * @see com.tmser.tr.plainsummary.service.PlainSummaryPunishViewService#getUnViewNum()
	 */
	@Override
	public Integer getUnViewNum() {
		PlainSummary psSearch = new PlainSummary();
		psSearch.setOrgId(CurrentUserContext.getCurrentUser().getOrgId());
		//psSearch.setIsPunish(1);
		//Integer totalNum = plainSummaryDao.count(psSearch);
		Integer totalNum =findPunishsByPage(psSearch).getTotalCount();
		PlainSummaryPunishView viewSearch = new PlainSummaryPunishView();
		viewSearch.setUserId(CurrentUserContext.getCurrentUserId());
		Integer viewNum = plainSummaryPunishViewDao.count(viewSearch);
		return totalNum-viewNum;
	}
	
	/**
	 * 查询已发布未查看的计划总结
	 * @param num
	 * @return
	 * @see com.tmser.tr.plainsummary.service.PlainSummaryPunishViewService#findUnviews(java.lang.Integer)
	 */
	@Override
	public List<PlainSummaryVo> findUnviews(Integer num) {
		PlainSummary psSearch = new PlainSummary();
		psSearch.setOrgId(CurrentUserContext.getCurrentUser().getOrgId());
		psSearch.setUserId(CurrentUserContext.getCurrentUserId());
		SchoolTeachCircleOrg circleOrgSearch = new SchoolTeachCircleOrg();
		circleOrgSearch.setOrgId(CurrentUserContext.getCurrentUser().getOrgId());
		circleOrgSearch.setSchoolYear((Integer) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SCHOOLYEAR));
		//查询学校所属校际教研圈
		List<SchoolTeachCircleOrg> circleOrgs = schoolTeachCircleOrgDao.listAll(circleOrgSearch);
		List<Integer> circleIds = JyCollectionUtils.getValues(circleOrgs, "stcId");
		List<PlainSummary> list = plainSummaryDao.findUnViewPunishs(num,psSearch,circleIds);
		return JyCollectionUtils.convertList(list, PlainSummaryVo.class);
	}

}
