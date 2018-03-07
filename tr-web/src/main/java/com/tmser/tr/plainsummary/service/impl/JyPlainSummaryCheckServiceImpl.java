/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.plainsummary.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.tmser.tr.check.bo.CheckInfo;
import com.tmser.tr.check.dao.CheckInfoDao;
import com.tmser.tr.common.ResTypeConstants;
import com.tmser.tr.common.utils.WebThreadLocalUtils;
import com.tmser.tr.plainsummary.bo.PlainSummary;
import com.tmser.tr.plainsummary.dao.PlainSummaryDao;
import com.tmser.tr.plainsummary.service.JyPlainSummaryCheckService;
import com.tmser.tr.plainsummary.vo.PlainSummaryCheckStatisticsVo;
import com.tmser.tr.plainsummary.vo.PlainSummarySimpleCountVo;
import com.tmser.tr.plainsummary.vo.PlainSummaryVo;
import com.tmser.tr.uc.SysRole;
import com.tmser.tr.uc.bo.User;
import com.tmser.tr.uc.bo.UserSpace;
import com.tmser.tr.uc.dao.UserDao;
import com.tmser.tr.uc.dao.UserSpaceDao;
import com.tmser.tr.uc.utils.CurrentUserContext;
import com.tmser.tr.uc.utils.SessionKey;
import com.tmser.tr.utils.JyCollectionUtils;
/**
 * plainSummary 服务实现类
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: JyPlainSummaryCheck.java, v 1.0 2015-04-09 Generate Tools Exp $
 */
@Service
@Transactional
public class JyPlainSummaryCheckServiceImpl implements JyPlainSummaryCheckService {

	@Autowired
	private CheckInfoDao checkInfoDao;
	
	@Autowired
	private PlainSummaryDao plainSummaryDao;
	
	@Autowired
	private UserSpaceDao userSpaceDao;
	
	@Autowired
	private UserDao userDao;
	
	/**
	 * 获取被评阅教师工作空间的统计信息
	 * @param gradeId
	 * @param subjectId
	 * @param teacherName
	 * 
	 * @return
	 * @see com.tmser.tr.plainsummary.service.JyPlainSummaryCheckService#getUserSpaceStatistics(java.lang.Integer, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public List<PlainSummaryCheckStatisticsVo> getUserSpaceStatistics(
			Integer gradeId, Integer subjectId) {
		List<PlainSummaryCheckStatisticsVo> result = new ArrayList<>();
		UserSpace model = new UserSpace();
		model.setSysRoleId(SysRole.TEACHER.getId());
		model.setGradeId(gradeId);
		model.setSubjectId(subjectId);
		model.setOrgId(CurrentUserContext.getCurrentUser().getOrgId());
		model.setSchoolYear((Integer) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SCHOOLYEAR));
		//查询符合的工作空间
		List<UserSpace> uses = userSpaceDao.listAll(model);
		if(CollectionUtils.isEmpty(uses)){
			return result;
		}
		//初始化统计序列
		for(UserSpace us:uses){
			//排除当前用户
			if(!CurrentUserContext.getCurrentUserId().equals(us.getUserId())){
				PlainSummaryCheckStatisticsVo vo =new PlainSummaryCheckStatisticsVo();
				vo.setUserSpaceId(us.getId());
				vo.setUserId(us.getUserId());
				result.add(vo);
			}
		}
		//填充用户详情
		fillUserDetail(result);
		//填充工作空间统计学习
		fillWorkSpaceStatistics(result,gradeId,subjectId);
		return result;
	}
	
	/**
	 * 填充工作空间统计信息
	 * @param vos
	 */
	private void fillWorkSpaceStatistics(
			List<PlainSummaryCheckStatisticsVo> vos,Integer gradeId,Integer subjectId) {
		if(CollectionUtils.isEmpty(vos)){
			return;
		}
//		List<Integer> spaceIds = JyCollectionUtils.getValues(vos, "userSpaceId");
		User crrentUs = (User) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_USER);
		Integer schoolYear = (Integer)WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SCHOOLYEAR);
		UserSpace us = (UserSpace)WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE);
		Integer phaseId = us.getPhaseId();
		//统计教师撰写和提交的计划信息
		Map<Integer,PlainSummaryCheckStatisticsVo > writePlainSummaryStatisticsMap = plainSummaryDao.getPlainSummaryStatistics(null,gradeId,subjectId,crrentUs.getId(),SysRole.TEACHER.getId(),schoolYear,phaseId,crrentUs.getOrgId());
		
		for(PlainSummaryCheckStatisticsVo vo:vos){
			//填充统计信息
			PlainSummaryCheckStatisticsVo writePlainSummaryStatistics= writePlainSummaryStatisticsMap.get(vo.getUserId());
			//填充计划统计信息
			if(writePlainSummaryStatistics==null){
				vo.setPlainNum(0);
				vo.setPlainSubmitNum(0);
				vo.setPlainCheckedNum(0);
				vo.setSummaryNum(0);
				vo.setSummarySubmitNum(0);
				vo.setSummaryCheckedNum(0);
			}else{
				vo.setPlainNum(writePlainSummaryStatistics.getPlainNum());
				vo.setPlainSubmitNum(writePlainSummaryStatistics.getPlainSubmitNum());
				vo.setPlainCheckedNum(writePlainSummaryStatistics.getPlainCheckedNum());
				vo.setSummaryNum(writePlainSummaryStatistics.getSummaryNum());
				vo.setSummarySubmitNum(writePlainSummaryStatistics.getSummarySubmitNum());
				vo.setSummaryCheckedNum(writePlainSummaryStatistics.getSummaryCheckedNum());
			}
		}
	}


	/**
	 * 填充用户详情
	 * @param vos
	 */
	private void fillUserDetail(List<PlainSummaryCheckStatisticsVo> vos) {
		if(CollectionUtils.isEmpty(vos)){
			return;
		}
		//遍历填充用户学习
		for(PlainSummaryCheckStatisticsVo vo:vos){
			//填充用户信息
			User user = userDao.get(vo.getUserId());
			vo.setUser(user);
		}
		
	}

	/**
	 * 根据用户空间查询计划总结，多用户空间
	 * @param userSpaceIds
	 * @param checkStatus
	 * @return
	 * @see com.tmser.tr.plainsummary.service.JyPlainSummaryCheckService#findPlainSummaryVoByRole(java.lang.Integer, java.lang.Integer, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public List<PlainSummaryVo> findPlainSummaryVo(Integer checkStatus,Integer gradeId,Integer subjectId,Integer userId,Integer roleId) {
		//根据用户空间查询计划总结
		return findPlainSummaryVoByUserSpace(null,checkStatus,gradeId,subjectId,userId,roleId);
	}
	
	/**
	 * 根据用户空间查询计划总结,单用户空间
	 * @param userSpaceId
	 * @param checkStatus
	 * @return
	 * @see com.tmser.tr.plainsummary.service.JyPlainSummaryCheckService#findPlainSummaryVoByUserSpace(java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public List<PlainSummaryVo> findPlainSummaryVo(Integer checkStatus,Integer gradeId, Integer subjectId,Integer userId) {
		//根据用户空间获取计划总结文档
		return findPlainSummaryVoByUserSpace(null,checkStatus,gradeId,subjectId,userId,SysRole.TEACHER.getId());
	}
	
	@Override
	public List<PlainSummaryVo> findPlainSummaryVoByTerm(Integer category,Integer term,Integer gradeId,Integer subjectId,Integer userId,Integer roleId){
		List<PlainSummaryVo> lpv=this.findPlainSummaryVoByUserSpace(category, null,gradeId,subjectId,userId,roleId);
		if(term!=null && (term==0 || term==1)){
			Iterator<PlainSummaryVo> iterator = lpv.iterator();
			while(iterator.hasNext()){
				PlainSummaryVo vo = iterator.next();
				if(term.equals(vo.getTerm())){					
					iterator.remove();
				}
			}
		}
		return lpv;
	}
	
	/**
	 * 根据用户空间查询计划总结，多用户空间
	 * @param userSpaceIds
	 * @param category
	 * @param checkStatus
	 * @return
	 */
	public List<PlainSummaryVo> findPlainSummaryVoByUserSpace(Integer category,Integer checkStatus,Integer gradeId,Integer subjectId,Integer userId,Integer roleId){
		//获取计划总结文档
		Integer schoolYear = (Integer)WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SCHOOLYEAR);
		UserSpace us = (UserSpace)WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE);
		Integer phaseId = us.getPhaseId();
		List<PlainSummary> pses = plainSummaryDao.findByUserSpace(category,1,gradeId,subjectId,userId,roleId,schoolYear,phaseId,us.getOrgId());
		List<PlainSummaryVo> vos = new ArrayList<PlainSummaryVo>();
		//PlainSummary 对象转换为vo类型
		for(PlainSummary ps : pses){
			PlainSummaryVo vo = new PlainSummaryVo();
			BeanUtils.copyProperties(ps, vo);
			vos.add(vo);
		}
		List<Integer> resTypes = new ArrayList<Integer>();
		resTypes.add(ResTypeConstants.TPPLAIN_SUMMARY_PLIAN);
		resTypes.add(ResTypeConstants.TPPLAIN_SUMMARY_SUMMARY);
		//获取所有审阅的资源id
		List<Integer> resIds = checkInfoDao.findResIdsAndUserId(resTypes,CurrentUserContext.getCurrentUserId() );
		//遍历设置审阅状态
		for(PlainSummaryVo vo:vos){
			for(Integer resId:resIds){
				if(vo.getId().equals(resId)){
					vo.setCheckStatus(1);
				}
			}
		}
		filterCheckStatus(vos,checkStatus);
		return vos;
	}

	/**
	 * 过滤审阅状态
	 * @param vos
	 * @param checkStatus
	 */
	private void filterCheckStatus(List<PlainSummaryVo> vos, Integer checkStatus) {
		if(CollectionUtils.isEmpty(vos)){
			return ;
		}
		//当只需要审阅状态文档或只需要未审阅状态文档时才进行过滤
		if(checkStatus==null||(checkStatus!=1&&checkStatus!=0)){
			return;
		}
		//仅需要对审阅状态计划总结
			Iterator<PlainSummaryVo> iterator = vos.iterator();
			while(iterator.hasNext()){
				PlainSummaryVo vo = iterator.next();
				if(checkStatus==1&&(vo.getCheckStatus()==null||vo.getCheckStatus()!=1)){//当仅需要查阅状态时
					iterator.remove();
				}else if(checkStatus==0&&vo.getCheckStatus()!=null&&vo.getCheckStatus()==1){//当仅需要未审阅状态时
					iterator.remove();
				}
			}
	}

	/**
	 * 获取提交时间在指定时间之前切距离指定日期最近的文档，单工作空间
	 * @param workSpaceId
	 * @param category
	 * @param date
	 * @param checkStatus
	 * @return
	 * @see com.tmser.tr.plainsummary.service.PlainSummaryService#getPrePlanSummary(java.lang.Integer, java.lang.Integer, java.util.Date, java.util.Date)
	 */
	@Override
	public PlainSummaryVo getPrePlanSummary(Integer checkplId) {
		PlainSummary pl = plainSummaryDao.get(checkplId);
		PlainSummaryVo vo = new PlainSummaryVo();
		BeanUtils.copyProperties(pl, vo);
		return vo;
	}
	
	/**
	 * 获取提交时间在指定时间之后切距离指定日期最近的文档，单工作空间
	 * @param workSpaceId
	 * @param category
	 * @param date
	 * @param checkStatus
	 * @return
	 * @see com.tmser.tr.plainsummary.service.PlainSummaryService#getNextPlanSummary(java.lang.Integer, java.lang.Integer, java.util.Date, java.util.Date)
	 */
	@Override
	public PlainSummaryVo getNextPlanSummary(Integer workSpaceId,
			Integer category, Integer psId, Integer checkStatus,Integer gradeId,Integer subjectId,Integer roleId) {
		List<Integer> workSpaceIds = new ArrayList<Integer>();
		workSpaceIds.add(workSpaceId);
		return getNextPlanSummary(workSpaceIds,category,psId,checkStatus,gradeId,subjectId,roleId);
	}
	
	/**
	 * 获取提交时间在指定时间之前切距离指定日期最近的文档，多工作空间
	 * @param userSpaceIds
	 * @param category
	 * @param psId
	 * @param checkStatus
	 * @return
	 */
	@Override
	public PlainSummaryVo getPrePlanSummary(List<Integer> userSpaceIds,
			Integer category, Integer psId, Integer checkStatus,Integer cantainCurrent,Integer gradeId,Integer subjectId,Integer roleId) {
		User user = (User) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_USER);
		if(CollectionUtils.isEmpty(userSpaceIds)){
			return null;
		}
		PlainSummary pl = plainSummaryDao.get(psId);
		Integer checkUserId = pl.getUserId();
		UserSpace us = (UserSpace)WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE);
		Integer schoolYear = us.getSchoolYear();
		PlainSummaryVo vo = plainSummaryDao.findPrePlainSummary( category, user.getId(), checkStatus, psId,cantainCurrent,gradeId,subjectId,checkUserId,roleId,schoolYear);
		if(vo==null){
			return null;
		}
		//设置计划总结审阅状态
		CheckInfo model = new CheckInfo();
		
		model.setUserId(user.getId());
		model.setResId(vo.getId());
		Integer resType=(vo.getCategory()==1||vo.getCategory()==3)?ResTypeConstants.TPPLAIN_SUMMARY_PLIAN:ResTypeConstants.TPPLAIN_SUMMARY_SUMMARY;
		model.setResType(resType);
		//查询计划总计审阅情况
		int count = checkInfoDao.count(model);
		if(count>0){
			vo.setCheckStatus(1);
		}
		return vo;
	}
	
	/**
	 * 获取提交时间在指定时间之后切距离指定日期最近的文档，多工作空间
	 * @param userSpaceIds
	 * @param category
	 * @param date
	 * @param checkStatus
	 * @return
	 */
	@Override
	public PlainSummaryVo getNextPlanSummary(List<Integer> userSpaceIds,
			Integer category, Integer psId, Integer checkStatus,Integer gradeId,Integer subjectId,Integer roleId) {
		User user = (User) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_USER);
		UserSpace us = (UserSpace)WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE);
		Integer schoolYear = us.getSchoolYear();
		PlainSummary pl = plainSummaryDao.get(psId);
		Integer checkUser = pl.getUserId();
		PlainSummaryVo vo = plainSummaryDao.findNextPlainSummary(userSpaceIds, category, user.getId(), checkStatus, psId,gradeId,subjectId,roleId,schoolYear,checkUser);
		if(vo==null){
			return null;
		}
		//设置计划总结审阅状态
		CheckInfo model = new CheckInfo();
		model.setUserId(user.getId());
		model.setResId(vo.getId());
		Integer resType=(vo.getCategory()==1||vo.getCategory()==3)?ResTypeConstants.TPPLAIN_SUMMARY_PLIAN:ResTypeConstants.TPPLAIN_SUMMARY_SUMMARY;
		model.setResType(resType);
		//查询计划总计审阅情况
		int count = checkInfoDao.count(model);
		if(count>0){
			vo.setCheckStatus(1);
		}
		return vo;
	}
	
	/**
	 * 获取计划总结详情
	 * @param planSummaryId
	 * @return
	 * @see com.tmser.tr.plainsummary.service.JyPlainSummaryCheckService#getPlanSummaryVo(java.lang.Integer)
	 */
	@Override
	public PlainSummaryVo getPlanSummaryVo(Integer planSummaryId) {
		PlainSummaryVo result = new PlainSummaryVo();
		//查询计划总结
		PlainSummary plainSummary = plainSummaryDao.get(planSummaryId);
		BeanUtils.copyProperties(plainSummary, result);
		CheckInfo model = new CheckInfo();
		User curUser = (User) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_USER);
		model.setUserId(curUser.getId());
		model.setResId(planSummaryId);
		Integer resType=(plainSummary.getCategory()==1||plainSummary.getCategory()==3)?ResTypeConstants.TPPLAIN_SUMMARY_PLIAN:ResTypeConstants.TPPLAIN_SUMMARY_SUMMARY;
		model.setResType(resType);
		//查询计划总计审阅情况
		int count = checkInfoDao.count(model);
		if(count>0){
			result.setCheckStatus(1);
		}
		Integer crtId = plainSummary.getCrtId();
		//查询用户消息
		User user = userDao.get(crtId);
		result.setEditName(user.getName());
		return result;
	}

	/**
	 * 查询被查阅工作空间的计划总结统计
	 * @param checkedSpaceIds
	 * @return
	 * @see com.tmser.tr.plainsummary.service.JyPlainSummaryCheckService#getPlainSummaryStatistics(java.util.List)
	 */
	@Override
	public PlainSummaryCheckStatisticsVo getPlainSummaryStatistics(Integer gradeId,Integer subjectId,Integer userId,
			Integer roleId,Integer phaseId) {
		PlainSummaryCheckStatisticsVo result = new PlainSummaryCheckStatisticsVo();
		User crrentUs = (User) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_USER); 
		Integer schoolYear = (Integer) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SCHOOLYEAR);
		//统计教师撰写和提交的计划信息
//		UserSpace us = (UserSpace)WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE);
//		Integer phaseId = us.getPhaseId();
		Map<Integer,PlainSummaryCheckStatisticsVo > writePlainSummaryStatisticsMap = plainSummaryDao.getPlainSummaryStatistics(userId,gradeId,subjectId,crrentUs.getId(),roleId,schoolYear,phaseId,crrentUs.getOrgId());
		List<PlainSummaryCheckStatisticsVo> vos = JyCollectionUtils.getMapValue(writePlainSummaryStatisticsMap);
		if(CollectionUtils.isEmpty(vos)){
			return result;
		}
		//统计所有
		for(PlainSummaryCheckStatisticsVo vo:vos){
			result.setPlainNum(result.getPlainNum()+vo.getPlainNum());
			result.setPlainSubmitNum(result.getPlainSubmitNum()+vo.getPlainSubmitNum());
			result.setPlainCheckedNum(result.getPlainCheckedNum()+vo.getPlainCheckedNum());
			result.setSummaryNum(result.getSummaryNum()+vo.getSummaryNum());
			result.setSummarySubmitNum(result.getSummarySubmitNum()+vo.getSummarySubmitNum());
			result.setSummaryCheckedNum(result.getSummaryCheckedNum()+vo.getSummaryCheckedNum());
		}
		return result;
	}
	
	/**
	 * 根据用户空间id统计计划总计
	 * @param userSpaceId
	 * @return
	 * @see com.tmser.tr.plainsummary.service.JyPlainSummaryCheckService#countByUserSpaceId(java.lang.Integer)
	 */
	@Override
	public PlainSummarySimpleCountVo countByUserSpaceId(Integer userSpaceId) {
		PlainSummary searcher = new PlainSummary();
		UserSpace us = userSpaceDao.get(userSpaceId);
		searcher.setUserRoleId(us.getRoleId());
		searcher.setGradeId(us.getGradeId());
		searcher.setPhaseId(us.getPhaseId());
		searcher.setUserId(us.getUserId());
		searcher.setSubjectId(us.getSubjectId());
		//统计撰写个数
		Integer writerNum = plainSummaryDao.count(searcher);
		searcher.setIsSubmit(1);
		//统计提交个数
		Integer submitNum = plainSummaryDao.count(searcher);
		CheckInfo info = new CheckInfo();
		List<Integer> resTypes = new ArrayList<Integer>();
		resTypes.add(ResTypeConstants.TPPLAIN_SUMMARY_PLIAN);
		resTypes.add(ResTypeConstants.TPPLAIN_SUMMARY_SUMMARY);
		info.setResTypes(resTypes);
		info.setRoleId(us.getRoleId());
		info.setGradeId(us.getGradeId());
		info.setSubjectId(us.getSubjectId());
		info.setAuthorId(us.getUserId());
//		info.setUserSpaceId(userSpaceId);
		//统计查阅个数
		Integer checkNum = checkInfoDao.countResNum(info);
		PlainSummarySimpleCountVo vo = new PlainSummarySimpleCountVo();
		vo.setPlainSummaryNum(writerNum);
		vo.setPlainSummarySubmitNum(submitNum);
		vo.setPlainSummaryCheckedNum(checkNum);
		return vo;
	}
}
