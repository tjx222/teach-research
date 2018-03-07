package com.tmser.tr.api.writelessonplan.service.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.tmser.tr.api.myplanbook.vo.LessonPlanMapping;
import com.tmser.tr.api.service.SchoolYearUtilService;
import com.tmser.tr.api.utils.TypeConvert;
import com.tmser.tr.api.writelessonplan.service.LessonPlanApiService;
import com.tmser.tr.common.bo.QueryObject;
import com.tmser.tr.common.vo.Result;
import com.tmser.tr.lessonplan.bo.LessonInfo;
import com.tmser.tr.lessonplan.bo.LessonPlan;
import com.tmser.tr.lessonplan.dao.LessonInfoDao;
import com.tmser.tr.lessonplan.dao.LessonPlanDao;
import com.tmser.tr.lessonplantemplate.bo.LessonPlanTemplate;
import com.tmser.tr.lessonplantemplate.dao.LessonPlanTemplateDao;
import com.tmser.tr.manage.meta.bo.Book;
import com.tmser.tr.manage.meta.service.BookService;
import com.tmser.tr.manage.org.bo.Organization;
import com.tmser.tr.manage.org.dao.OrganizationDao;
import com.tmser.tr.manage.resources.bo.ResRecommend;
import com.tmser.tr.manage.resources.bo.Resources;
import com.tmser.tr.manage.resources.dao.ResRecommendDao;
import com.tmser.tr.manage.resources.service.ResourcesService;
import com.tmser.tr.uc.bo.UserSpace;
import com.tmser.tr.uc.dao.UserSpaceDao;
import com.tmser.tr.utils.StringUtils;
import com.tmser.tr.writelessonplan.service.impl.LessonPlanServiceImpl;

/**
 * 移动离线端-备课资源表 服务实现类
 * 
 * @author
 * @version 1.0 2016-04-15
 */
@Service
@Transactional
public class LessonPlanApiServiceImpl implements LessonPlanApiService {
	private static final Logger logger = LoggerFactory.getLogger(LessonPlanServiceImpl.class);
	@Resource
	private LessonPlanTemplateDao lessonPlanTemplateDao;
	@Resource
	private LessonInfoDao lessonInfoDao;
	@Resource
	private LessonPlanDao lessonPlanDao;
	@Resource
	private ResourcesService resourcesService;
	@Resource
	private SchoolYearUtilService schoolYearUtilService;
	@Resource
	private ResRecommendDao resRecommendDao;
	@Resource
	private UserSpaceDao userSpaceDao;
	@Resource
	private OrganizationDao organizationDao;
	@Resource
	private BookService bookService;

	/**
	 * 移动离线端-保存教案
	 * 
	 * @param file
	 *            文件
	 * @param
	 * @param
	 * @return
	 * @see com.tmser.tr.lessonplan.service.LessonPlanApiService#saveLessonPlan
	 */
	@Override
	public Result saveLessonPlan(String lessonplan, MultipartFile file) {
		LessonPlanMapping lpm = JSON.parseObject(lessonplan, LessonPlanMapping.class);
		if (lpm != null) {
			if (lpm.getHoursId()==null) {
				return new Result(0, "教案课时不能为空", new Date(), null);
			}
			// 学年
			Integer schoolYear = schoolYearUtilService.getCurrentSchoolYear();
			// 学期
			Integer termId = schoolYearUtilService.getCurrentTerm();
			String lessonId = lpm.getLessonId();// 课题id
			String lessonHours = lpm.getHoursId();// 课时id连成的字符串

			// 判断将要保存的教案是否和以保存教案的课时重复
			if (hoursIdOreadyExist(lessonId, lessonHours, lpm.getUserId())) {
				return new Result(0, "要保存的教案课时不能重复", new Date(), null);
			}

			String relativeUrl = File.separator + "jiaoan" + File.separator + "o_" + lpm.getOrgId() + File.separator + String.valueOf(schoolYear) + File.separator + String.valueOf(lpm.getSubjectId())
					+ File.separator + lessonId; // 相对路径 如：/2015/3/3
			if (lpm.getPlanId() == null) {// 教案不存在则新增教案
				// 调用上传接口将文件上传返回相对路径
				InputStream inputStream = null;
				try {
					inputStream = file.getInputStream();
				} catch (IOException e) {
					logger.error("", e);
				}
				Resources resources = resourcesService.saveResources(inputStream, file.getOriginalFilename(), file.getSize(), relativeUrl);
				if (resources != null) {
					// 保存教案信息
					String bookId = lpm.getBookId();
					Book book = bookService.findOne(bookId);
					// 增加其课题信息记录(不存在增加，已存在则教案数量+1)
					LessonInfo lessonInfo = saveLessonInfo(lpm, book);
					if (lessonInfo != null) {
						Integer tpId = null;
						if (lpm.getTpId() != null) {
							tpId = lpm.getTpId();
						}
						// 构造待新增的教案
						LessonPlan lessonPlan = new LessonPlan(lessonInfo.getId(), lpm.getPlanName(), resources.getId(), 0, lpm.getUserId(), lpm.getSubjectId(), lpm.getGradeId(), bookId, book.getFormatName(),
								lessonId, lessonHours, tpId, lpm.getOrgId(), book.getFasciculeId(), schoolYear, termId, lpm.getPhaseId(), Integer.valueOf(lessonHours.substring(0, 1)), new Date(), 1);
						lessonPlan.setClientId(lpm.getClientId());
						lessonPlan = lessonPlanDao.insert(lessonPlan);
						// logger.info("撰写教案：新增教案成功！
						// 操作人id："+userSpace.getUserId());
						// planId = String.valueOf(lessonPlan.getPlanId());
						LessonPlanMapping reobj = TypeConvert.convert(lessonPlan, LessonPlanMapping.class);
						if (StringUtils.isNotEmpty(lessonPlan.getResId())) {
							Resources res = resourcesService.findOne(lessonPlan.getResId());
							if(res!=null){
								reobj.setInfo_id(lessonPlan.getResId()+"."+res.getExt());
							}else{
								reobj.setInfo_id("");
							}
						}
						return new Result(1, "教案保存成功", new Date(), reobj);
					}
				}
			}
			return null;
		} else {
			return new Result(0, "教案不能为空", new Date(), null);
		}
	}

	/**
	 * 移动离线端-修改教案
	 * 
	 * @param file
	 *            文件
	 * @param
	 * @param
	 * @return
	 * @see com.tmser.tr.lessonplan.service.LessonPlanApiService#updateLessonPlan
	 */
	@Override
	public Result updateLessonPlan(String lessonplan, MultipartFile file) {
		LessonPlanMapping lpm = JSON.parseObject(lessonplan, LessonPlanMapping.class);
		if (lpm == null) {
			return new Result(0, "教案不能为空", new Date(), null);
		}
		if (lpm.getPlanId() == null) {
			return new Result(0, "教案planId不能为空", new Date(), null);
		}
		if (lpm.getHoursId() == null) {
			return new Result(0, "教案课题课时不能为空", new Date(), null);
		}
		// 学年
		Integer schoolYear = schoolYearUtilService.getCurrentSchoolYear();
		Integer planId = lpm.getPlanId();// 教案id
		LessonPlan lessonPlan = lessonPlanDao.get(planId);
		String lessonHours = lpm.getHoursId();// 课时id连成的字符串
		String planName = lessonPlan.getPlanName();
		String relativeUrl = File.separator + "jiaoan" + File.separator + String.valueOf(schoolYear) + File.separator + String.valueOf(lpm.getSubjectId()) + File.separator + lessonPlan.getLessonId();
		// 调用上传接口将文件上传返回相对路径
		InputStream inputStream = null;
		try {
			inputStream = file.getInputStream();
		} catch (IOException e) {
			logger.error("", e);
		}
		Resources resources = resourcesService.updateResources(inputStream, file.getOriginalFilename(), file.getSize(), relativeUrl, lessonPlan.getResId());
		if (resources != null) {
			// 构造待更新的教案
			if (!lessonPlan.getHoursId().equals(lessonHours)) {
				lessonPlan.setPlanName(planName);
				lessonPlan.setHoursId(lessonHours);
			}
			lessonPlan.setLastupId(lpm.getUserId());
			lessonPlan.setLastupDttm(new Date());
			lessonPlanDao.update(lessonPlan);
		}
		LessonPlan lp = lessonPlanDao.get(planId);
		LessonPlanMapping reobj = TypeConvert.convert(lp, LessonPlanMapping.class);
		if (StringUtils.isNotEmpty(lp.getResId())) {
			Resources res = resourcesService.findOne(lessonPlan.getResId());
			if(res!=null){
				reobj.setInfo_id(lessonPlan.getResId()+"."+res.getExt());
			}else{
				reobj.setInfo_id("");
			}
		}
		return new Result(1, "教案修改成功", new Date(), reobj);
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
		// TODO Auto-generated method stub
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
	 * 判断将要保存的教案是否和以保存教案的课时重复
	 * 
	 * @param lessonId
	 * @param lessonHours
	 * @return
	 */
	private boolean hoursIdOreadyExist(String lessonId, String lessonHours, Integer userId) {
		// 学年
		Integer schoolYear = schoolYearUtilService.getCurrentSchoolYear();
		LessonPlan lp = new LessonPlan();
		lp.setLessonId(lessonId);
		lp.setUserId(userId);
		lp.setSchoolYear(schoolYear);
		String hoursIdStr = getHoursStrOfWritedLessonById(lp);
		String[] hoursArray = lessonHours.split(",");
		for (String hour : hoursArray) {
			if (hoursIdStr.contains(hour)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 根据课题id获取已写过教案的课时id连成的字符串
	 * 
	 * @param lessonId
	 * @return
	 * @see com.tmser.tr.writelessonplan.service.LessonPlanService#getHoursStrOfWritedLessonById(java.lang.String)
	 */
	@Override
	public String getHoursStrOfWritedLessonById(LessonPlan lessonPlan) {
		StringBuilder hoursStr = new StringBuilder(StringUtils.COMMA);
		lessonPlan.setEnable(1);
		List<LessonPlan> lessonPlanList = lessonPlanDao.listAll(lessonPlan);
		if (lessonPlanList != null && lessonPlanList.size() > 0) {
			for (LessonPlan lp : lessonPlanList) {
				if(StringUtils.isNotBlank(lp.getHoursId())){
					hoursStr.append(lp.getHoursId()).append(StringUtils.COMMA);
				}
			}
		}
		return hoursStr.toString();
	}

	/**
	 * 获取模板集合，包含系统自带的
	 * 
	 * @param orgId
	 * @return
	 * @see com.tmser.tr.lessonplantemplate.service.LessonPlanTemplateService#getTemplateListByOrg(java.lang.Integer)
	 */
	@Override
	public List<Map<String, Object>> getTemplateListByOrg(Integer orgId) {
		List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
		LessonPlanTemplate model = new LessonPlanTemplate();
		model.setOrgId(orgId);
		model.setEnable(1);
		model.setTpType(1);
		if (orgId != null) {
			List<LessonPlanTemplate> lptList = lessonPlanTemplateDao.listAll(model);
			if (!CollectionUtils.isEmpty(lptList)) {
				for (LessonPlanTemplate lpt : lptList) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("tp_id", lpt.getTpId());
					map.put("tp_name", lpt.getTpName());
					map.put("org_id", lpt.getOrgId());
					map.put("org_name", lpt.getOrgName());
					// map.put("tp_type", lpt.getTpType());
					map.put("tp_url", lpt.getResId() + "." + resourcesService.findOne(lpt.getResId()).getExt());
					map.put("tp_photo", lpt.getIco() + (StringUtils.isBlank(lpt.getIco()) ? null : "." + resourcesService.findOne(lpt.getIco()).getExt()));
					data.add(map);
				}
			}
		}
		return data;
	}

	/**
	 * 获得推送资源
	 * 
	 * @param rr
	 * @return
	 * @see com.tmser.tr.api.writelessonplan.service.LessonPlanApiService#findCommendResource(ResRecommend)
	 */
	@Override
	public List<ResRecommend> findCommendResource(ResRecommend rr) {
		rr.setEnable(1);
		// rr.pageSize(8);
		rr.addOrder(" qualify desc,resSecondType asc");
		return resRecommendDao.listAll(rr);
	}

	/**
	 * 获取同伴资源
	 * 
	 * @param spaceId
	 * @param lessonId
	 * @param planType
	 * @return
	 * @see com.tmser.tr.api.writelessonplan.service.LessonPlanApiService#getPeerResource(java.lang.Integer,java.lang.String,java.lang.Integer)
	 */
	@Override
	public List<LessonPlan> getPeerResource(Integer spaceId, String lessonId, Integer planType) {
		// 获取当前用户空间
		UserSpace userSpace = userSpaceDao.get(spaceId);
		if (userSpace != null) {
			LessonPlan lp = new LessonPlan();
			Organization org = organizationDao.get(userSpace.getOrgId());
			lp.setLessonId(lessonId);
			lp.setPlanType(planType);
			lp.addAlias("lp");
			lp.setGradeId(userSpace.getGradeId());
			lp.setSubjectId(userSpace.getSubjectId());
			Map<String, Object> params = new HashMap<String, Object>();
			lp.addJoin(QueryObject.JOINTYPE.INNER, "Organization o").on("lp.orgId = o.id and o.orgType = :orgType and o.enable=1");
			params.put("orgType", org.getOrgType());
			params.put("userId", userSpace.getUserId());
			lp.addCustomCondition("and lp.userId != :userId", params);
			lp.setIsShare(true);
			// lp.pageSize(6);
			List<LessonPlan> lessonPlanList = lessonPlanDao.listAll(lp);
			return lessonPlanList;
		}
		return null;
	}
}
