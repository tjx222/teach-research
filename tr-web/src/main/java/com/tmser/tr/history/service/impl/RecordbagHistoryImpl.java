/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.history.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmser.tr.activity.bo.Activity;
import com.tmser.tr.activity.service.ActivityService;
import com.tmser.tr.common.orm.SqlMapping;
import com.tmser.tr.history.service.RecordbagHistory;
import com.tmser.tr.lecturerecords.bo.LectureRecords;
import com.tmser.tr.lecturerecords.service.LectureRecordsService;
import com.tmser.tr.lessonplan.bo.LessonPlan;
import com.tmser.tr.manage.meta.bo.Book;
import com.tmser.tr.manage.meta.bo.BookChapter;
import com.tmser.tr.manage.meta.service.BookChapterService;
import com.tmser.tr.manage.meta.service.BookService;
import com.tmser.tr.manage.resources.bo.Resources;
import com.tmser.tr.manage.resources.service.ResourcesService;
import com.tmser.tr.plainsummary.bo.PlainSummary;
import com.tmser.tr.plainsummary.service.PlainSummaryService;
import com.tmser.tr.recordbag.bo.Record;
import com.tmser.tr.recordbag.bo.Recordbag;
import com.tmser.tr.recordbag.dao.RecordDao;
import com.tmser.tr.recordbag.dao.RecordbagDao;
import com.tmser.tr.thesis.bo.Thesis;
import com.tmser.tr.thesis.service.ThesisService;
import com.tmser.tr.uc.SysRole;
import com.tmser.tr.uc.utils.CurrentUserContext;
import com.tmser.tr.utils.DateUtils;
import com.tmser.tr.utils.StringUtils;
import com.tmser.tr.writelessonplan.service.LessonPlanService;

/**
 * <pre>
 *  历史资源-成长档案袋impl
 * </pre>
 *
 * @author dell
 * @version $Id: RecordbagHistoryImpl.java, v 1.0 2016年6月6日 下午4:07:44 dell Exp $
 */

@Service
@Transactional
public class RecordbagHistoryImpl implements RecordbagHistory {
	
	@Autowired
	private RecordDao recordDao;
	
	@Autowired
	private RecordbagDao recordbagDao;
	
	@Autowired
	private LessonPlanService lessonPlanService;
	
	@Autowired
	private BookService bookService;
	
	@Autowired
	private ResourcesService resourcesService;
	
	@Autowired
	private ActivityService activityService;
	
	@Autowired
	private ThesisService thesisService;
	
	@Autowired
	private LectureRecordsService lectureRecordsService;
	
	@Autowired
	private PlainSummaryService plainSummaryService;
	
	@Autowired
	private BookChapterService bookChapterService;
	
	/**
	 * 统计资源数
	 * @param userId
	 * @param code
	 * @param currentYear
	 * @return
	 * @see com.tmser.tr.history.service.RecordbagHistory#getRecordbagHistory(java.lang.Integer, java.lang.String, java.lang.Integer)
	 */
	@Override
	public Integer getRecordbagHistory(Integer userId,Integer currentYear) {
			Record record=new Record();
			record.setUserId(userId);
			record.setSchoolYear(currentYear);
		return recordDao.count(record);
	}

	/**
	 * 成长档案袋 列表展示
	 * @return
	 * @see com.tmser.tr.history.service.RecordbagHistory#getAll()
	 */
	@Override
	public List<Recordbag> getAll(Integer currentYear,Recordbag recordbag) {
		Recordbag model=new Recordbag();
		model.setDelete(0);
		model.setTeacherId(String.valueOf(CurrentUserContext.getCurrentUserId()));
		model.setGradeId(recordbag.getGradeId());
		model.setSubjectId(recordbag.getSubjectId());
		if (StringUtils.isNotEmpty(recordbag.getName())) {
			model.setName(SqlMapping.LIKE_PRFIX+recordbag.getName()+SqlMapping.LIKE_PRFIX);
		}
		recordbag.addOrder("sort asc");
		List<Recordbag> rList= recordbagDao.listAll(model);
		for (Recordbag r:rList) {
			Record record=new Record();
			record.setSchoolYear(currentYear);
			record.setBagId(r.getId());
			r.setResCount(recordDao.count(record));
		}
		return rList;
	}

	/**
	 * @param record
	 * @see com.tmser.tr.history.service.RecordbagHistory#showLessonPlanHistory(com.tmser.tr.recordbag.bo.Record)
	 */
	@Override
	public void showLessonPlanHistory(Record record) {
		// TODO Auto-generated method stub
		LessonPlan plan = lessonPlanService.findOne(record.getResId());
		if (plan != null) {
			Book book = bookService.findOne(plan.getBookId());
			String cebie = book.getFascicule();// 获得册别名称
			String grade = book.getGradeLevel();// 获得年级名称
			String name="";
			if (StringUtils.isNotEmpty(plan.getLessonId())) {
				BookChapter bChapter=bookChapterService.findOne(plan.getLessonId());
				if (bChapter!=null) {
					 name=bChapter.getChapterName();//获取课题
				}
				
			}
			Resources re = resourcesService.findOne(plan.getResId());
			if (re != null && re.getExt() != null)
				record.setFlags(re.getExt());
			record.setFlago("【" + grade + cebie + "】");
			Map<String, String> map=new HashMap<String, String>();
			if (plan.getPlanType()==LessonPlan.KE_HOU_FAN_SI) {
				map.put("rtype", "课后反思");
			}
			if (plan.getPlanType()==LessonPlan.QI_TA_FAN_SI) {
				map.put("rtype", "其他反思");
			}
			map.put("type", String.valueOf(plan.getPlanType()));
			if (re!=null&&re.getSize()!=null) {
				map.put("size", String.valueOf(re.getSize()));
			}
			map.put("bookShortname", plan.getBookShortname());
			map.put("infoId", String.valueOf(plan.getInfoId()));
			map.put("isShare",  String.valueOf(plan.getIsShare()));
			map.put("time",  DateUtils.formatDate(plan.getCrtDttm(), "yyyy-MM-dd"));
			map.put("name",  name);
			record.setExt(map);
		}
	}

	/**
	 * @param record
	 * @return
	 * @see com.tmser.tr.history.service.RecordbagHistory#showActivityHistory(com.tmser.tr.recordbag.bo.Record)
	 */
	@Override
	public Integer showActivityHistory(Record record) {
		// TODO Auto-generated method stub
		Activity at = activityService.findOne(record.getResId());
		Integer flag = 1;
		if (at != null) {
			String term = "";// 获得学期名称
			Integer termId = at.getTerm();// 获得学期Id
			if (termId == 0) {
				term = "上学期";
			} else {
				term = "下学期";
			}
			record.setFlago("【" + term + "】【" + at.getTypeName() + "】");
			record.setFlags(String.valueOf(at.getTypeId()));
			record.setCreateTime(new Date());
			Map<String, String> map = new HashMap<String, String>();
			map.put("subjectName", at.getSubjectName());
			map.put("gradeName", at.getGradeName());
			map.put("mainUserName", at.getOrganizeUserName());
			map.put("typeId", String.valueOf(at.getTypeId()));
			map.put("typeName", at.getTypeName());
			String start = DateUtils.formatDate(at.getStartTime(), "MM-dd HH:mm");
			String end = "";
			if (at.getEndTime() != null)
				end = DateUtils.formatDate(at.getEndTime(), "MM-dd HH:mm");
			map.put("startTime", start);
			map.put("endTime", end);
			map.put("commentsNum", String.valueOf(at.getCommentsNum()));
			record.setExt(map);
		}
		return flag;
	}

	/**
	 * @param record
	 * @see com.tmser.tr.history.service.RecordbagHistory#showThesisHistory(com.tmser.tr.recordbag.bo.Record)
	 */
	@Override
	public void showThesisHistory(Record record) {
		Thesis thesis = thesisService.findOne(record.getResId());
		if (thesis != null) {
			String term = "";// 获得学期名称
			Integer termId = thesis.getSchoolTerm();// 获得学期Id
			if (termId == 0) {
				term = "上学期";
			} else {
				term = "下学期";
			}
			record.setCreateTime(new Date());
			record.setFlago("【" + term + "】【" + thesis.getThesisType() + "】");
			record.setFlags(thesis.getFileSuffix());
			Map<String, String> map=new HashMap<String, String>();
			map.put("type", thesis.getThesisType());
			map.put("isShare",  String.valueOf(thesis.getIsShare()));
			map.put("time",  DateUtils.formatDate(thesis.getCrtDttm(), "yyyy-MM-dd"));
			Resources re = resourcesService.findOne(thesis.getResId());
			if (re!=null&&re.getSize()!=null) {
				map.put("size", String.valueOf(re.getSize()));
			}
			record.setExt(map);
		}
		
	}

	/**
	 * @param record
	 * @see com.tmser.tr.history.service.RecordbagHistory#showLectureRecordHistory(com.tmser.tr.recordbag.bo.Record)
	 */
	@Override
	public void showLectureRecordHistory(Record record) {
		// TODO Auto-generated method stub
		LectureRecords lr = lectureRecordsService.findOne(record.getResId());
		if (lr != null) {
			String string = "校内";
			if (lr.getType().intValue() == 1) {
				string = "校外";
			}
			String term = "上学期";
			if (lr.getTerm().intValue() == 1) {
				term = "下学期";
			}
			record.setFlago("【" + term + "】【" + string + "】");
			String t = DateUtils.formatDate(record.getCreateTime(), "MM-dd");
			record.setTime(t);
			Map<String, String> map = new HashMap<String, String>();
			map.put("teachPeople", lr.getTeachingPeople());
			if (lr.getType() == 1)
				map.put("grade", lr.getGradeSubject());
			if (lr.getGrade() != null && lr.getSubject() != null)
				map.put("grade", lr.getGrade() + lr.getSubject());
			map.put("num", String.valueOf(lr.getNumberLectures()));
			record.setExt(map);

		}
		
	}

	/**
	 * @param record
	 * @see com.tmser.tr.history.service.RecordbagHistory#showPainSummaryHistory(com.tmser.tr.recordbag.bo.Record)
	 */
	@Override
	public void showPainSummaryHistory(Record record) {
		// TODO Auto-generated method stub
		PlainSummary ps = plainSummaryService.findOne(record.getResId());
		if (ps != null) {
			String term = "";// 获得学期名称
			Integer termId = ps.getTerm();// 获得学期Id
			if (termId == 0) {
				term = "上学期";
			} else {
				term = "下学期";
			}
			record.setFlago("【" + term + "】【" + switchCategory(ps.getCategory(), ps.getRoleId()) + "】");
			record.setFlags(ps.getContentFileType());
			Map<String, String> map=new HashMap<String, String>();
			map.put("type", switchCategory(ps.getCategory(), ps.getRoleId()));
			map.put("isShare",  String.valueOf(ps.getIsShare()));
			map.put("time",  DateUtils.formatDate(ps.getCrtDttm(), "yyyy-MM-dd"));
			Resources re = resourcesService.findOne(ps.getContentFileKey());
			if (re!=null&&re.getSize()!=null) {
				map.put("size", String.valueOf(re.getSize()));
			}
			record.setExt(map);
		}
		
	}
	private String switchCategory(int key, Integer roleId) {
		String str = "";
		// 1:个人计划；2：个人总结；3、当前身份计划；4、当前身份总结
		if (key == 1) {
			str = "个人计划";
		} else if (key == 2) {
			str = "个人总结";
		} else if (key == 3) {
			if (SysRole.XZ.getId().equals(roleId) || SysRole.ZR.getId().equals(roleId)) {
				str = "学校";
			} else if (SysRole.NJZZ.getId().equals(roleId)) {
				str = "年级";
			} else if (SysRole.XKZZ.getId().equals(roleId)) {
				str = "学科";
			} else if (SysRole.BKZZ.getId().equals(roleId)) {
				str = "备课";
			}
			str += "计划";
		} else if (key == 4) {
			if (SysRole.XZ.getId().equals(roleId) || SysRole.ZR.getId().equals(roleId)) {
				str = "学校";
			} else if (SysRole.NJZZ.getId().equals(roleId)) {
				str = "年级";
			} else if (SysRole.XKZZ.getId().equals(roleId)) {
				str = "学科";
			} else if (SysRole.BKZZ.getId().equals(roleId)) {
				str = "备课";
			}
			str += "总结";
		} else {
			str = "未找到该类型";
		}
		return str;
	}

	/**
	 * @param record
	 * @see com.tmser.tr.history.service.RecordbagHistory#showOtherHistory(com.tmser.tr.recordbag.bo.Record)
	 */
	@Override
	public void showOtherHistory(Record record) {
		if (StringUtils.isNotEmpty(record.getPath())) {
			Resources resources=resourcesService.findOne(record.getPath());
				if (resources!=null) {
					Map<String, String> map=new HashMap<String, String>();
					if (resources!=null&&resources.getSize()!=null) {
						map.put("size", String.valueOf(resources.getSize()));
						map.put("time", DateUtils.formatDate(record.getCreateTime(), "yyyy-MM-dd"));
					}
					record.setFlags(resources.getExt());
					record.setExt(map);
				}
				
		}
	}

}
