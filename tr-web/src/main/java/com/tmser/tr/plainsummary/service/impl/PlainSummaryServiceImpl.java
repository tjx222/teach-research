/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.plainsummary.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.tmser.tr.common.dao.BaseDAO;
import com.tmser.tr.common.service.AbstractService;
import com.tmser.tr.common.utils.WebThreadLocalUtils;
import com.tmser.tr.manage.resources.service.ResourcesService;
import com.tmser.tr.plainsummary.bo.PlainSummary;
import com.tmser.tr.plainsummary.dao.PlainSummaryDao;
import com.tmser.tr.plainsummary.service.PlainSummaryPunishViewService;
import com.tmser.tr.plainsummary.service.PlainSummaryService;
import com.tmser.tr.uc.bo.User;
import com.tmser.tr.uc.utils.CurrentUserContext;
import com.tmser.tr.uc.utils.SessionKey;
import com.tmser.tr.utils.JyAssert;

/**
 * 计划总结 服务实现类
 * 
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: PlainSummary.java, v 1.0 2015-03-19 Generate Tools Exp $
 */
@Service
@Transactional
public class PlainSummaryServiceImpl extends
		AbstractService<PlainSummary, Integer> implements PlainSummaryService {

	@Autowired
	private PlainSummaryDao plainSummaryDao;

	@Autowired
	private PlainSummaryPunishViewService plainSummaryPunishViewService;
	
	@Autowired
	private ResourcesService resourcesService;
	
	/**
	 * 分享
	 */
	@Override
	public void share(Integer psId) {
		PlainSummary model = new PlainSummary();
		model.setId(psId);
		model.setIsShare(1);
		model.setShareTime(new Date());
		Integer result = plainSummaryDao.update(model);
		JyAssert.isTrue(result == 1, "数据已过期，请刷新后重试!");
	}

	/**
	 * 取消分享
	 */
	@Override
	public void cancelShare(Integer psId) {
		verifyData(psId);
		PlainSummary ps = new PlainSummary();
		ps.setIsShare(0);
		ps.setId(psId);
		ps.addCustomCondition("and isShare=1 and isReview=0",
				new HashMap<String, Object>());
		Integer result = plainSummaryDao.update(ps);
		JyAssert.isTrue(result == 1, "数据已过期，请刷新后重试!");
	}

	/**
	 * 发布
	 */
	@Override
	@Valid
	public void punish(Integer psId,@NotNull Integer punishRange,Integer schoolCircleId) {
		if(punishRange==1){
			Assert.isTrue(schoolCircleId!=null, "校际教研圈必选！");
		}
		
		PlainSummary model = new PlainSummary();
		model.setId(psId);
		model.setIsPunish(1);
		model.setPunishTime(new Date());
		model.setPunishRange(punishRange);
		if(punishRange==1){
			model.setSchoolCircleId(schoolCircleId);
		}
		Integer result = plainSummaryDao.update(model);
		JyAssert.isTrue(result == 1, "数据已过期，请刷新后重试!");
	}

	/**
	 * 取消发布
	 */
	@Override
	public void cancelPunsih(Integer psId) {
		verifyData(psId);
		PlainSummary ps = new PlainSummary();
		ps.setIsPunish(0);
		ps.setId(psId);
		ps.addCustomCondition("and isPunish=1 and isReview=0",
				new HashMap<String, Object>());
		plainSummaryDao.update(ps);
		//删除查阅记录
		plainSummaryPunishViewService.delteView(psId);
	}

	/**
	 * 提交
	 */
	@Override
	public void submit(Integer psId) {
		PlainSummary model = new PlainSummary();
		model.setId(psId);
		model.setIsSubmit(1);
		model.setSubmitTime(new Date());
		// 提交记录
		Integer result = plainSummaryDao.update(model);
		JyAssert.isTrue(result == 1, "数据已过期，请刷新后重试!");
	}
	
	/**
	 * 取消提交
	 */
	@Override
	public void cancelSubmit(Integer psId) {
		verifyData(psId);
		PlainSummary ps = new PlainSummary();
		ps.setIsSubmit(0);
		ps.setId(psId);
		ps.addCustomCondition("and isSubmit=1 and isCheck=0",
				new HashMap<String, Object>());
		Integer result = plainSummaryDao.update(ps);
		JyAssert.isTrue(result == 1, "数据已过期，请刷新后重试!");
	}

	/**
	 * 编辑
	 */
	@Override
	public void edit(PlainSummary ps) {
		JyAssert.notNull(ps, "编辑内容不能为空！");
		verifyData(ps.getId());
		
		ps.addCustomCondition("and isSubmit=0 and isPunish=0 and isShare=0",
				new HashMap<String, Object>());
		ps.setLastupDttm(new Date());
		if(StringUtils.isNotEmpty(ps.getContentFileKey())){
			updateRes(ps);
		}else{
			ps.setContentFileKey(null);
		}
		Integer result = plainSummaryDao.update(ps);
		JyAssert.isTrue(result == 1, "数据已过期，请刷新后重试!");
	}
	
	void updateRes(PlainSummary ps){
		PlainSummary old = plainSummaryDao.get(ps.getId());
		if(!old.getContentFileKey().equals(ps.getContentFileKey())){
			resourcesService.deleteResources(old.getContentFileKey());
			//回调确认文件已保存
			resourcesService.updateTmptResources(ps.getContentFileKey());
		}
		
	}
	

	/**
	 * 验证请求数据
	 * 
	 * @param psId
	 */
	private void verifyData(Integer psId) {
		JyAssert.notNull(psId, "id不能为空!");
		// 获取计划总结信息
		PlainSummary ps = plainSummaryDao.get(psId);
		JyAssert.notNull(ps, "数据不存在！");
		// 获取用户信息
		User user = (User) WebThreadLocalUtils
				.getSessionAttrbitue(SessionKey.CURRENT_USER);
		JyAssert.isTrue(user.getId().equals(ps.getUserId()), "没有操作权限!");
	}

	/**
	 * @return
	 * @see com.tmser.tr.common.service.BaseService#getDAO()
	 */
	@Override
	public BaseDAO<PlainSummary, Integer> getDAO() {
		return plainSummaryDao;
	}

	/**
	 * 获取上一篇发布的计划总结
	 * plainSummaryService
	 * @param psId
	 * @return
	 * @see com.tmser.tr.plainsummary.service.PlainSummaryService#getPrePunishPlanSummary(java.lang.Integer)
	 */
	@Override
	public PlainSummary getPrePunishPlanSummary(Integer psId) {
		return plainSummaryDao.getPrePunishPlanSummary(psId,CurrentUserContext.getCurrentUser().getOrgId());
	}
	
	/**
	 * 获取下一篇计划总结
	 * @param psId
	 * @return
	 * @see com.tmser.tr.plainsummary.service.PlainSummaryService#getNextPunishPlanSummary(java.lang.Integer)
	 */
	@Override
	public PlainSummary getNextPunishPlanSummary(Integer psId) {
		return plainSummaryDao.getNexPunishPlanSummary(psId,CurrentUserContext.getCurrentUser().getOrgId());
	}
	
	/**
	 * @param id
	 * @see com.tmser.tr.common.service.AbstractService#delete(java.io.Serializable)
	 */
	@Override
	public void delete(Integer id) {
		PlainSummary ps = findOne(id);
		if(ps != null){
			resourcesService.deleteResources(ps.getContentFileKey());
			super.delete(id);
		}
	}

	/**
	 * @param ps
	 * @return
	 * @see com.tmser.tr.plainsummary.service.PlainSummaryService#resourceSubmitOthers(com.tmser.tr.plainsummary.bo.PlainSummary)
	 */
	@Override
	public List<PlainSummary> resourceSubmitOthers(PlainSummary ps) {
		// TODO Auto-generated method stub
		PlainSummary model=new PlainSummary();
		model.setOrgId(ps.getOrgId());
		model.setCategory(ps.getCategory());
		model.setSubjectId(ps.getSubjectId());
		model.setGradeId(ps.getGradeId());
		model.setSchoolYear(ps.getSchoolYear());
		model.setIsSubmit(1);
		Map<String, Object> paramMap=new HashMap<String, Object>();
		paramMap.put("userId", ps.getUserId());
		model.addCustomCondition("and userId != :userId", paramMap);
		model.addGroup("userId");
		model.addOrder("submitTime desc");
		return plainSummaryDao.listAll(model);
	}

}
