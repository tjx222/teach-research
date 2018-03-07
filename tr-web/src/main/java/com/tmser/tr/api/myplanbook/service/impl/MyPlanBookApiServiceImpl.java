/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.api.myplanbook.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tmser.tr.api.myplanbook.service.MyPlanBookApiService;
import com.tmser.tr.api.myplanbook.vo.LessonPlanMapping;
import com.tmser.tr.api.service.SchoolYearUtilService;
import com.tmser.tr.api.utils.TypeConvert;
import com.tmser.tr.browse.BaseResTypes;
import com.tmser.tr.browse.utils.BrowseRecordUtils;
import com.tmser.tr.check.service.CheckInfoService;
import com.tmser.tr.common.ResTypeConstants;
import com.tmser.tr.common.vo.Result;
import com.tmser.tr.lessonplan.bo.LessonInfo;
import com.tmser.tr.lessonplan.bo.LessonPlan;
import com.tmser.tr.lessonplan.dao.LessonInfoDao;
import com.tmser.tr.lessonplan.dao.LessonPlanDao;
import com.tmser.tr.manage.meta.bo.Book;
import com.tmser.tr.manage.meta.service.BookService;
import com.tmser.tr.manage.resources.bo.Resources;
import com.tmser.tr.manage.resources.service.ResourcesService;
import com.tmser.tr.utils.StringUtils;

/**
 * <pre>
 * 离线端我的备课本接口控制类
 * </pre>
 *
 * @author huyanfang
 * @version $Id: MyPlanBookServiceImpl.java, v 1.0 2015年4月14日 下午3:28:45
 *          huyanfang Exp $
 */
@Service
@Transactional
public class MyPlanBookApiServiceImpl implements MyPlanBookApiService {
	@Resource
	private LessonPlanDao lessonPlanDao;
	@Resource
	private LessonInfoDao lessonInfoDao;
	@Resource
	private BookService bookService;
	@Resource
	private ResourcesService resourcesService;
	@Resource
	private SchoolYearUtilService schoolYearUtilService;
	@Autowired(required = false)
	private CheckInfoService checkInfoService;

	/**
	 * 移动离线端-获取备课本信息
	 */
	@Override
	public List<Map<String, Object>> getLessonPlan(Integer userid) {
		List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
		LessonPlan model = new LessonPlan();
		if (userid != null) {
			model.setUserId(userid);
			List<LessonPlan> lpList = lessonPlanDao.listAll(model);
			if (!CollectionUtils.isEmpty(lpList)) {
				for (LessonPlan lp : lpList) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("plan_id", lp.getPlanId());
					map.put("plan_name", lp.getPlanName());
					if (StringUtils.isNotEmpty(lp.getResId())) {
						Resources res = resourcesService.findOne(lp.getResId());
						if(res!=null){
							map.put("info_id", lp.getResId()+"."+res.getExt());
						}else{
							map.put("info_id", "");
						}
					}
					map.put("res_id", lp.getResId());
					map.put("plan_type", lp.getPlanType());
					map.put("digest", lp.getDigest());
					map.put("user_id", lp.getUserId());
					map.put("subject_id", lp.getSubjectId());
					map.put("grade_id", lp.getGradeId());
					map.put("book_id", lp.getBookId());
					map.put("book_shortname", lp.getBookShortname());
					map.put("lesson_id", lp.getLessonId());
					map.put("hours_id", lp.getHoursId());
					map.put("fascicule_id", lp.getFasciculeId());
					map.put("tp_id", lp.getTpId());
					map.put("org_id", lp.getOrgId());
					map.put("school_year", lp.getSchoolYear());
					map.put("term_id", lp.getTermId());
					map.put("phase_id", lp.getPhaseId());
					map.put("is_submit", lp.getIsSubmit());
					map.put("is_share", lp.getIsShare());
					map.put("submit_time", lp.getSubmitTime());
					map.put("share_time", lp.getShareTime());
					map.put("is_scan", lp.getIsScan());
					map.put("scan_up", lp.getScanUp());
					map.put("is_comment", lp.getIsComment());
					map.put("comment_up", lp.getCommentUp());
					map.put("down_num", lp.getDownNum());
					map.put("order_value", lp.getOrderValue());
					map.put("crt_id", lp.getCrtId());
					map.put("crt_dttm", lp.getCrtDttm());
					map.put("lastup_id", lp.getLastupId());
					map.put("lastup_dttm", lp.getLastupDttm());
					map.put("enable", lp.getEnable());
					map.put("client_id", lp.getClientId());
					map.put("origin", lp.getOrigin()==null ? 0 : lp.getOrigin());
					data.add(map);
				}
			}
		}
		return data;
	}

	/**
	 * 移动离线端-获取备课本意见
	 */
	@Override
	public List<Map<String, Object>> getLessonInfo(Integer userid) {
		List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
		LessonInfo model = new LessonInfo();
		if (userid != null) {
			model.setUserId(userid);
			List<LessonInfo> list = lessonInfoDao.listAll(model);
			for (LessonInfo l : list) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("lesson_id", l.getLessonId());
				map.put("lesson_name", l.getLessonName());
				map.put("jiaoan_shareCount", l.getJiaoanShareCount());
				map.put("kejian_shareCount", l.getKejianShareCount());
				map.put("fansi_shareCount", l.getFansiShareCount());
				map.put("jiaoan_submitCount", l.getJiaoanSubmitCount());
				map.put("kejian_submitCount", l.getKejianSubmitCount());
				map.put("fansi_submitCount", l.getFansiSubmitCount());
				map.put("jiaoan_count", l.getJiaoanCount());
				map.put("kejian_count", l.getKejianCount());
				map.put("fansi_count", l.getFansiCount());
				map.put("scan_count", l.getScanCount());
				map.put("visit_count", l.getVisitCount());
				map.put("comment_count", l.getCommentCount());
				map.put("client_id", l.getClientId());
				data.add(map);
			}
		}
		return data;
	}

	/**
	 * 移动离线端-保存备课本信息
	 */
	@Override
	public Result saveMyPlanBook(String lessonplan) {
		LessonPlanMapping lpm = JSON.parseObject(lessonplan, LessonPlanMapping.class);
		if (lpm != null) {
			if (StringUtils.isEmpty(lpm.getClientId())) {
				return new Result(0, "客户端ID不能为空", new Date(), null);
			}
			if (StringUtils.isEmpty(lpm.getPlanName())) {
				return new Result(0, "课题不能为空", new Date(), null);
			}
			if (lpm.getPlanType() == null) {
				return new Result(0, "课题资源类型不能为空", new Date(), null);
			}
			if (StringUtils.isEmpty(lpm.getResId())) {
				return new Result(0, "资源ID不能为空", new Date(), null);
			}
			if (StringUtils.isEmpty(lpm.getBookId())) {
				return new Result(0, "书ID不能为空", new Date(), null);
			}
			// 添加备课资源
			Book book = bookService.findOne(lpm.getBookId());
			LessonInfo lessonInfo = saveLessonInfo(lpm, book);
			lpm.setInfoId(lessonInfo.getId());
			lpm.setFasciculeId(book.getFasciculeId());
			lpm.setBookShortname(book.getFormatName());// 书的简称
			lpm.setTermId(schoolYearUtilService.getCurrentTerm());
			LessonPlan lp = lessonPlanDao.insert(lpm);
			// 保存成功
			// 更改资源状态
			resourcesService.updateTmptResources(lp.getResId());
			LessonPlanMapping reobj = TypeConvert.convert(lp, LessonPlanMapping.class);
			if (StringUtils.isNotEmpty(lp.getResId())) {
				Resources res = resourcesService.findOne(lp.getResId());
				if(res!=null){
					reobj.setInfo_id(lp.getResId()+"."+res.getExt());
				}else{
					reobj.setInfo_id("");
				}
			}
			return new Result(1, "备课本信息保存成功", new Date(), reobj);

		} else {
			return new Result(0, "备课本信息不完整", new Date(), null);
		}
	}

	/**
	 * 新增课题信息 （如果已存在则不增加）
	 * 
	 * @param lessonId
	 * @param bookId
	 * @return
	 * @see com.tmser.tr.myplanbook.service.MyPlanBookApiService#saveLessonInfo(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public LessonInfo saveLessonInfo(LessonPlan lp, Book book) {
		// 获取当前用户空间
		LessonInfo lessonInfo = new LessonInfo();
		lessonInfo.setLessonId(lp.getLessonId());
		lessonInfo.setUserId(lp.getUserId());
		lessonInfo.setSchoolYear(lp.getSchoolYear());
		LessonInfo temp = lessonInfoDao.getOne(lessonInfo);
		if (temp == null) {// 不存在则新增
			lessonInfo.setLessonName(lp.getPlanName());
			lessonInfo.setBookId(book.getComId());
			lessonInfo.setBookShortname(book.getFormatName());
			lessonInfo.setGradeId(lp.getGradeId());
			lessonInfo.setSubjectId(lp.getSubjectId());
			lessonInfo.setFasciculeId(book.getFasciculeId());
			lessonInfo.setTermId(schoolYearUtilService.getCurrentTerm());
			lessonInfo.setPhaseId(lp.getPhaseId());
			lessonInfo.setOrgId(lp.getOrgId());
			lessonInfo.setScanUp(false);
			lessonInfo.setVisitUp(false);
			lessonInfo.setCommentUp(false);
			lessonInfo.setScanCount(0);
			lessonInfo.setVisitCount(0);
			lessonInfo.setCommentCount(0);
			lessonInfo.setJiaoanShareCount(0);
			lessonInfo.setKejianShareCount(0);
			lessonInfo.setFansiShareCount(0);
			lessonInfo.setJiaoanSubmitCount(0);
			lessonInfo.setKejianSubmitCount(0);
			lessonInfo.setFansiSubmitCount(0);
			if (lp.getPlanType() == LessonPlan.JIAO_AN) {
				lessonInfo.setJiaoanCount(1);
				lessonInfo.setFansiCount(0);
				lessonInfo.setKejianCount(0);
			} else if (lp.getPlanType() == LessonPlan.KE_JIAN) {
				lessonInfo.setKejianCount(1);
				lessonInfo.setJiaoanCount(0);
				lessonInfo.setFansiCount(0);
			} else if (lp.getPlanType() == LessonPlan.KE_HOU_FAN_SI) {
				lessonInfo.setFansiCount(1);
				lessonInfo.setJiaoanCount(0);
				lessonInfo.setKejianCount(0);
			}
			lessonInfo.setCrtId(lp.getUserId());
			lessonInfo.setCrtDttm(new Date());
			lessonInfo.setCurrentFrom(LessonInfo.FROM_ME);
			return lessonInfoDao.insert(lessonInfo);
		} else {
			LessonInfo model = new LessonInfo();
			if (lp.getPlanType() == LessonPlan.JIAO_AN) {
				model.addCustomCulomn("jiaoanCount = jiaoanCount+1");
				temp.setJiaoanCount(temp.getJiaoanCount() + 1);
			} else if (lp.getPlanType() == LessonPlan.KE_JIAN) {
				model.addCustomCulomn("kejianCount = kejianCount+1");
				temp.setKejianCount(temp.getKejianCount() + 1);
			} else if (lp.getPlanType() == LessonPlan.KE_HOU_FAN_SI) {
				model.addCustomCulomn("fansiCount = fansiCount+1");
				temp.setFansiCount(temp.getFansiCount() + 1);
			}
			model.setId(temp.getId());
			return temp;
		}
	}

	/**
	 * 移动离线端-分享备课本信息
	 */
	@Override
	public Result shareMyPlanBook(Integer planid) {
		LessonPlan lessonPlan = lessonPlanDao.get(planid);
		if (lessonPlan == null) {
			return new Result(0, "没有此条备课本信息", new Date(), null);
		}
		if (BooleanUtils.isTrue(lessonPlan.getIsShare())) {
			if (lessonPlan.getIsComment()) {
				return new Result(0, "备课本信息已评论不能取消分享", new Date(), null);
			} else {
				lessonPlan.setIsShare(false);
				lessonPlanDao.update(lessonPlan);
				LessonInfo lessonInfo = lessonInfoDao.get(lessonPlan.getInfoId());
				if (lessonPlan.getPlanType() == LessonPlan.JIAO_AN) {
					lessonInfo.addCustomCulomn("jiaoanShareCount = jiaoanShareCount-1");
				} else if (lessonPlan.getPlanType() == LessonPlan.KE_JIAN) {
					lessonInfo.addCustomCulomn("kejianShareCount = kejianShareCount-1");
				} else if (lessonPlan.getPlanType() == LessonPlan.KE_HOU_FAN_SI) {
					lessonInfo.addCustomCulomn("fansiShareCount = fansiShareCount-1");
				}
				lessonInfoDao.update(lessonInfo);
				// 修改浏览记录分享状态
				BrowseRecordUtils.updateBrowseRecordShare(BaseResTypes.BKZY, planid, false);
				LessonPlanMapping reobj = TypeConvert.convert(lessonPlan, LessonPlanMapping.class);
				if (StringUtils.isNotEmpty(lessonPlan.getResId())) {
					reobj.setInfo_id(lessonPlan.getResId()+"."+resourcesService.findOne(lessonPlan.getResId()).getExt());
				}
				return new Result(1, "备课本信息取消分享成功", new Date(), reobj);
			}
		} else {
			Date shareDate = new Date();
			lessonPlan.setIsShare(true);
			lessonPlan.setShareTime(shareDate);
			lessonPlanDao.update(lessonPlan);
			LessonInfo lessonInfo = lessonInfoDao.get(lessonPlan.getInfoId());
			lessonInfo.setShareTime(shareDate);
			if (lessonPlan.getPlanType() == LessonPlan.JIAO_AN) {
				lessonInfo.addCustomCulomn("jiaoanShareCount = jiaoanShareCount+1");
			} else if (lessonPlan.getPlanType() == LessonPlan.KE_JIAN) {
				lessonInfo.addCustomCulomn("kejianShareCount = kejianShareCount+1");
			} else if (lessonPlan.getPlanType() == LessonPlan.KE_HOU_FAN_SI) {
				lessonInfo.addCustomCulomn("fansiShareCount = fansiShareCount+1");
			}
			lessonInfoDao.update(lessonInfo);
			// 修改浏览记录分享状态
			BrowseRecordUtils.updateBrowseRecordShare(BaseResTypes.BKZY, planid, true);
			LessonPlanMapping reobj = TypeConvert.convert(lessonPlan, LessonPlanMapping.class);
			if (StringUtils.isNotEmpty(lessonPlan.getResId())) {
				reobj.setInfo_id(lessonPlan.getResId()+"."+resourcesService.findOne(lessonPlan.getResId()).getExt());
			}
			return new Result(1, "备课本信息分享成功", new Date(), reobj);
		}

	}

	/**
	 * 移动离线端-删除备课本信息（已提交和已分享的禁止删除）
	 */
	@Override
	public Result deleteMyPlanBook(Integer planid) {
		if (planid != null) {
			LessonPlan lessonPlan = lessonPlanDao.get(planid);
			if (lessonPlan == null) {
				return new Result(0, "没有此条备课信息", new Date(), null);
			}
			// 没有提交或分享则删除
			if (lessonPlan.getIsSubmit() != null && BooleanUtils.isTrue(lessonPlan.getIsSubmit())) {
				return new Result(0, "备课信息已提交不能删除", new Date(), null);
			}
			if (lessonPlan.getIsShare() != null && BooleanUtils.isTrue(lessonPlan.getIsSubmit())) {
				return new Result(0, "备课信息已分享不能删除", new Date(), null);
			}
			LessonInfo lessonInfo = lessonInfoDao.get(lessonPlan.getInfoId());// 课题信息
			if (lessonInfo != null) {
				if (lessonPlan.getPlanType() == LessonPlan.JIAO_AN) {
					lessonInfo.addCustomCulomn(" jiaoanCount = jiaoanCount-1");
				} else if (lessonPlan.getPlanType() == LessonPlan.KE_JIAN) {
					lessonInfo.addCustomCulomn(" kejianCount = kejianCount-1");
				} else if (lessonPlan.getPlanType() == LessonPlan.KE_HOU_FAN_SI) {
					lessonInfo.addCustomCulomn(" fansiCount = fansiCount-1");
				}
				lessonInfoDao.update(lessonInfo);

				LessonPlan temp = new LessonPlan();
				temp.setInfoId(lessonInfo.getId());
				temp.setEnable(1);
				List<LessonPlan> tempList = lessonPlanDao.listAll(temp);
				if (tempList == null || tempList.size() == 0) {// 说明课题下没有备课资源，则删除相应的课题信息记录
					lessonInfoDao.delete(lessonInfo.getId());
				}
			}
			// resourcesService.delete(lessonPlan.getResId()); //删除存储资源记录
			if (lessonPlan.getResId() != null) {
				resourcesService.deleteResources(lessonPlan.getResId());
			}
			lessonPlanDao.delete(planid);
			LessonPlanMapping reobj = TypeConvert.convert(lessonPlan, LessonPlanMapping.class);
			if (StringUtils.isNotEmpty(lessonPlan.getResId())) {
				reobj.setInfo_id(lessonPlan.getResId()+"."+resourcesService.findOne(lessonPlan.getResId()).getExt());
			}
			return new Result(1, "备课信息删除成功", new Date(), reobj);
		} else {
			return new Result(0, "备课信息planid不能为空", new Date(), null);
		}
	}

	/**
	 * 批量提交备课资源
	 * 
	 * @param lessonPlanIdsStr
	 * @return
	 * @see com.tmser.tr.myplanbook.service.MyPlanBookService#submitLessonPlansByIdStr(java.lang.String)
	 */
	@Override
	public Result submitLessonPlansByIdStr(String lessonPlanIdsStr) {
		JSONObject jObject = JSON.parseObject(lessonPlanIdsStr);
		Set<Entry<String, Object>> set = jObject.entrySet();
		Iterator<Entry<String, Object>> it = set.iterator();
		List<LessonPlanMapping> list = new ArrayList<LessonPlanMapping>();
		Date submitTime = new Date();
		Integer restype = null;
		if (!set.isEmpty()) {
			Set<Integer> resIds = new HashSet<Integer>();
			while (it.hasNext()) {
				Integer planId = null;
				try {
					planId = Integer.parseInt(it.next().getValue().toString());
				} catch (NumberFormatException e) {
					// valid planid
				}
				if (planId == null) {
					return new Result(0, "备课本信息planid不能为空", new Date(), null);
				}
				LessonPlan lessonPlan = lessonPlanDao.get(planId);
				if (lessonPlan == null) {
					return new Result(0, "没有此条备课本信息", new Date(), null);
				}
				lessonPlan.setIsSubmit(true);
				lessonPlan.setSubmitTime(submitTime);
				lessonPlan.setLastupDttm(submitTime);
				lessonPlanDao.update(lessonPlan);
				LessonInfo lessonInfo = new LessonInfo();
				lessonInfo.setId(lessonPlan.getInfoId());
				restype = getCheckResType(lessonPlan.getPlanType());
				if (lessonPlan.getPlanType() == LessonPlan.JIAO_AN) {
					lessonInfo.addCustomCulomn("jiaoanSubmitCount = jiaoanSubmitCount+1");
				} else if (lessonPlan.getPlanType() == LessonPlan.KE_JIAN) {
					lessonInfo.addCustomCulomn("kejianSubmitCount = kejianSubmitCount+1");
				} else if (lessonPlan.getPlanType() == LessonPlan.KE_HOU_FAN_SI) {
					lessonInfo.addCustomCulomn("fansiSubmitCount = fansiSubmitCount+1");
				}
				lessonInfo.setSubmitTime(submitTime);
				lessonInfoDao.update(lessonInfo);
				resIds.add(lessonInfo.getId());
				LessonPlanMapping reobj = TypeConvert.convert(lessonPlan, LessonPlanMapping.class);
				if (StringUtils.isNotEmpty(lessonPlan.getResId())) {
					reobj.setInfo_id(lessonPlan.getResId()+"."+resourcesService.findOne(lessonPlan.getResId()).getExt());
				}
				list.add(reobj);
			}
			// 更新备课资源表
			updateCheckInfo(restype, resIds);
			return new Result(1, "备课本信息提交成功", new Date(), list);
		} else {
			return new Result(0, "备课本信息不完整", new Date(), null);
		}
	}

	/**
	 * 取消批量提交备课资源
	 * 
	 * @param lessonPlanIdsStr
	 * @return
	 * @see com.tmser.tr.myplanbook.service.MyPlanBookService#submitLessonPlansByIdStr(java.lang.String)
	 */
	@Override
	public Result unSubmitLessonPlansByIdStr(String lessonPlanIdsStr) {
		JSONObject jObject = JSON.parseObject(lessonPlanIdsStr);
		Set<Entry<String, Object>> set = jObject.entrySet();
		Iterator<Entry<String, Object>> it = set.iterator();
		List<LessonPlanMapping> list = new ArrayList<LessonPlanMapping>();
		Date submitTime = new Date();
		if (!set.isEmpty()) {
			while (it.hasNext()) {
				Integer planId = null;
				try {
					planId = Integer.parseInt(it.next().getValue().toString());
				} catch (NumberFormatException e) {
				}
				if (planId == null) {
					return new Result(0, "备课本信息planid不能为空", new Date(), null);
				}
				LessonPlan lessonPlan = lessonPlanDao.get(planId);
				if (lessonPlan == null) {
					return new Result(0, "没有此条备课本信息", new Date(), null);
				}
				lessonPlan.setIsSubmit(false);
				lessonPlan.setLastupDttm(submitTime);
				Map<String, Object> tempMap = new HashMap<String, Object>();
				tempMap.put("name", false);
				lessonPlan.addCustomCondition("and isScan =:name", tempMap);
				lessonPlanDao.update(lessonPlan);
				LessonInfo lessonInfo = new LessonInfo();
				lessonInfo.setId(lessonPlan.getInfoId());
				if (lessonPlan.getPlanType() == LessonPlan.JIAO_AN) {
					lessonInfo.addCustomCulomn("jiaoanSubmitCount = jiaoanSubmitCount-1");
				} else if (lessonPlan.getPlanType() == LessonPlan.KE_JIAN) {
					lessonInfo.addCustomCulomn("kejianSubmitCount = kejianSubmitCount-1");
				} else if (lessonPlan.getPlanType() == LessonPlan.KE_HOU_FAN_SI) {
					lessonInfo.addCustomCulomn("fansiSubmitCount = fansiSubmitCount-1");
				}
				lessonInfoDao.update(lessonInfo);
				LessonPlanMapping reobj = TypeConvert.convert(lessonPlan, LessonPlanMapping.class);
				if (StringUtils.isNotEmpty(lessonPlan.getResId())) {
					reobj.setInfo_id(lessonPlan.getResId()+"."+resourcesService.findOne(lessonPlan.getResId()).getExt());
				}
				list.add(reobj);
			}
			return new Result(1, "备课本信息取消提交成功", new Date(), list);
		} else {
			return new Result(0, "备课本信息不完整", new Date(), null);
		}
	}

	private void updateCheckInfo(Integer restype, Set<Integer> resIds) {
		if (checkInfoService != null) {
			for (Integer resid : resIds) {
				checkInfoService.updateCheckInfoUpdateState(resid, restype);
			}
		}
	}

	private Integer getCheckResType(Integer planType) {
		switch (planType) {
		case LessonPlan.JIAO_AN:
			return ResTypeConstants.JIAOAN;
		case LessonPlan.KE_JIAN:
			return ResTypeConstants.KEJIAN;
		case LessonPlan.KE_HOU_FAN_SI:
			return ResTypeConstants.FANSI;
		}

		return null;
	}

	/**
	 * 获得备课资源的最后修改时间
	 * 
	 * @param id
	 * @return
	 * @see com.tmser.tr.api.base.service.MobileBasicService#resLastuptime(java.lang.Integer)
	 */
	@Override
	public Map<String, Object> resLastuptime(Integer id) {
		LessonPlan lessonPlan = lessonPlanDao.get(id);
		Map<String, Object> map = new HashMap<String, Object>();
		if (lessonPlan != null) {
			map.put("lastuptime", lessonPlan.getLastupDttm());
		}
		return map;
	}

	/**
	 * 返回我的备课本查阅意见、听课意见是否有更新
	 * @param userid
	 * @param lessonid
	 * @return
	 * @see com.tmser.tr.api.myplanbook.service.MyPlanBookApiService#getupdatestate(java.lang.Integer, java.lang.String)
	 */
	@Override
	public Map<String, Object> getupdatestate(Integer userid, String lessonid) {
		Map<String, Object> map = new HashMap<String, Object>();
		LessonInfo lessonInfo = new LessonInfo();
		lessonInfo.setLessonId(lessonid);
		lessonInfo.setUserId(userid);
		lessonInfo.setSchoolYear(schoolYearUtilService.getCurrentSchoolYear());
		lessonInfo = lessonInfoDao.getOne(lessonInfo);
		if(lessonInfo!=null){
			if(lessonInfo.getScanUp()){
				map.put("scan_up", true);
			}else{
				map.put("scan_up", false);
			}
			if(lessonInfo.getVisitUp()){
				map.put("visit_up", true);
			}else{
				map.put("visit_up", false);
			}
		}else{
			map.put("scan_up", false);
			map.put("visit_up", false);
		}
		return map;
	}

	/**
	 * @param userid
	 * @param lessonid
	 * @param type
	 * @return
	 * @see com.tmser.tr.api.myplanbook.service.MyPlanBookApiService#updatestate(java.lang.Integer, java.lang.String, java.lang.String)
	 */
	@Override
	public Result updatestate(Integer userid, String lessonid, String type) {
		LessonInfo lessonInfo = new LessonInfo();
		lessonInfo.setLessonId(lessonid);
		lessonInfo.setUserId(userid);
		lessonInfo.setSchoolYear(schoolYearUtilService.getCurrentSchoolYear());
		lessonInfo = lessonInfoDao.getOne(lessonInfo);
		if(lessonInfo!=null){
			if("scan_up".equals(type)){
				lessonInfo.setScanUp(false);
				lessonInfoDao.update(lessonInfo);
				return new Result(1, "更新状态成功！", new Date(), "");
			}
			if("visit_up".equals(type)){
				lessonInfo.setVisitUp(false);
				lessonInfoDao.update(lessonInfo);
				return new Result(1, "更新状态成功！", new Date(), "");
			}
			return new Result(0, "更新状态类型不正确！", new Date(), "");
		}else{
			return new Result(0, "课题信息不存在！", new Date(), "");
		}
	}

}