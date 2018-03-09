/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.api.lecturerecords.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.tmser.tr.api.lecturerecords.service.MobileLecturerecordsService;
import com.tmser.tr.api.lecturerecords.vo.LectureRecordsMapping;
import com.tmser.tr.api.service.SchoolYearUtilService;
import com.tmser.tr.api.utils.TypeConvert;
import com.tmser.tr.common.ResTypeConstants;
import com.tmser.tr.common.utils.WebThreadLocalUtils;
import com.tmser.tr.common.vo.Result;
import com.tmser.tr.lecturerecords.bo.LectureRecords;
import com.tmser.tr.lecturerecords.dao.LectureRecordsDao;
import com.tmser.tr.lecturerecords.service.ActionCallback;
import com.tmser.tr.lecturerecords.service.LectureRecordsService;
import com.tmser.tr.lessonplan.bo.LessonInfo;
import com.tmser.tr.lessonplan.dao.LessonInfoDao;
import com.tmser.tr.manage.meta.bo.Book;
import com.tmser.tr.manage.meta.bo.BookChapter;
import com.tmser.tr.manage.meta.service.BookChapterService;
import com.tmser.tr.manage.meta.service.BookService;
import com.tmser.tr.manage.resources.service.ResourcesService;
import com.tmser.tr.myplanbook.service.MyPlanBookService;
import com.tmser.tr.notice.constants.NoticeType;
import com.tmser.tr.notice.service.impl.NoticeUtils;
import com.tmser.tr.uc.bo.UserSpace;
import com.tmser.tr.uc.utils.SessionKey;
import com.tmser.tr.utils.StringUtils;

/**
 * <pre>
 *
 * </pre>
 *
 * @author tmser
 * @version $Id: MobileLecturerecordsServiceImpl.java, v 1.0 2016年4月15日
 *          下午5:39:43 tmser Exp $
 */
@Service
@Transactional
public class MobileLecturerecordsServiceImpl implements MobileLecturerecordsService {

	@Resource
	private SchoolYearUtilService schoolYearUtilService;
	@Resource
	private LectureRecordsDao lectureRecordsDao;
	@Resource
	private ResourcesService resourcesService;
	@Resource
	private LessonInfoDao lessonInfoDao;
	@Resource
	private MyPlanBookService myPlanBookService;
	@Resource
	private BookService bookService;
	@Resource
	private BookChapterService bookChapterService;
	
	private final static Pattern RESID_PATTERN = Pattern.compile("/jy/manage/res/download/(\\w{32})");

	/**
	 * 通过用户ID和类型ID（校内或校外）来获取听课记录列表信息
	 * 
	 * @param userid
	 * @param optime
	 * @return
	 * @see com.tmser.tr.api.lecturerecords.service.MobileLecturerecordsService#findInfoList(java.lang.Integer,
	 *      java.lang.String)
	 */
	@Override
	public List<LectureRecordsMapping> findInfoList(Integer userid, String optime) {
		LectureRecords model = new LectureRecords();
		// model.pageSize(15);//设置每页的展示数
		model.setSchoolYear(schoolYearUtilService.getCurrentSchoolYear());// 当前学年
		model.setLecturepeopleId(userid);// 听课人ID
		model.setIsDelete(false);// 不删除 model.setIsEpub(1);//发布
		model.addOrder("crtDttm desc");// 按照发布时间降序
		if (StringUtils.isNotEmpty(optime)) {
			model.addCustomCondition("and crtDttm >= '" + optime + "'");
		}
		List<LectureRecords> lectureRecords = lectureRecordsDao.listAll(model);
		if (lectureRecords != null) {
			for (LectureRecords lr : lectureRecords) {
				if (lr.getTopicId() != null && lr.getTopicId() != 0) {
					LessonInfo li = lessonInfoDao.get(lr.getTopicId());
					if(li!=null){
						lr.setLessonId(li.getLessonId());
					}
				}
			}
		}
		return TypeConvert.convert(lectureRecords, LectureRecordsMapping.class);
	}

	/**
	 * 听课记录信息保存（发布）
	 * 
	 * @param lrm
	 * @return
	 * @see com.tmser.tr.api.lecturerecords.service.MobileLecturerecordsService#saveLecture(com.tmser.tr.api.lecturerecords.vo.LectureRecordsMapping)
	 */
	@Override
	public Result saveLecture(String lectureinfo) {
		LectureRecordsMapping lrm = JSON.parseObject(lectureinfo, LectureRecordsMapping.class);
		if (lrm != null) {
			if (StringUtils.isEmpty(lrm.getClientId())) {
				return new Result(0, "客户端ID不能为空", new Date(), null);
			}
			if (StringUtils.isEmpty(lrm.getTopic())) {
				return new Result(0, "听课记录的主题不能为空", new Date(), null);
			}
			if (lrm.getLecturepeopleId() == null || lrm.getLecturepeopleId() == 0) {
				return new Result(0, "听课人不能为空", new Date(), null);
			}
			if (lrm.getType() == 0) {
				if (StringUtils.isEmpty(lrm.getLessonId())) {
					return new Result(0, "课题lessonId不能为空", new Date(), null);
				}
				LessonInfo lessonInfo = new LessonInfo();
				lessonInfo.setUserId(lrm.getTeachingpeopleId());
				lessonInfo.setLessonId(lrm.getLessonId());
				lessonInfo.setSchoolYear(schoolYearUtilService.getCurrentSchoolYear());
				LessonInfo litemp = myPlanBookService.findOne(lessonInfo);
				if (litemp == null) {
					BookChapter bc = bookChapterService.findOne(lrm.getLessonId());
					if(bc!=null){
						Book book = bookService.findOne(bc.getComId());
						lessonInfo.setLessonName(lrm.getTopic());
						lessonInfo.setBookId(book.getComId());
						lessonInfo.setBookShortname(book.getFormatName());
						lessonInfo.setGradeId(lrm.getGradeId());
						lessonInfo.setSubjectId(lrm.getSubjectId());
						lessonInfo.setFasciculeId(book.getFasciculeId());
						lessonInfo.setTermId(schoolYearUtilService.getCurrentTerm());
						lessonInfo.setPhaseId(lrm.getPhaseId());
						lessonInfo.setOrgId(lrm.getOrgId());
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
						lessonInfo.setJiaoanCount(0);
						lessonInfo.setFansiCount(0);
						lessonInfo.setKejianCount(0);
						lessonInfo.setCrtId(lrm.getLecturepeopleId());
						lessonInfo.setCrtDttm(new Date());
						lessonInfo.setCurrentFrom(LessonInfo.FROM_ME);
						lessonInfo = myPlanBookService.save(lessonInfo);
						lrm.setTopicId(lessonInfo.getId());
					}else{
						return new Result(0, "找不到此课题信息", new Date(), null);
					}
				}else{
					lrm.setTopicId(litemp.getId());
				}
			}
			lrm.setSchoolYear(schoolYearUtilService.getCurrentSchoolYear());
			lrm.setTerm(schoolYearUtilService.getCurrentTerm());
			lrm.setResType(ResTypeConstants.LECTURE);// 当前资源类型
			lrm.setIsShare(0);
			lrm.setIsDelete(false);
			lrm.setIsSubmit(0);
			lrm.setIsReply(0);
			lrm.setReplyUp(0);
			lrm.setIsComment(0);
			lrm.setCommentUp(0);
			lrm.setCrtDttm(new Date());// 创建时间
			lrm.setLastupDttm(new Date());// 最近修改的时间
			if(lrm.getType()==1){//校外，直接发布
				lrm.setIs_epub(1);
			}
			LectureRecords insert = lectureRecordsDao.insert(lrm);
			Set<String> resIdList = filterContent(lrm.getLectureContent());
			for(String resId : resIdList){//更新资源
				resourcesService.updateTmptResources(resId);
			}
			if(lrm.getIsEpub()==1&&lrm.getType()==0){//只有发布的时候、校内听课记录才通知
				lrm.setEpub_time(new Date());//发布时间
				notifyChecked(lrm.getTopicId(), ResTypeConstants.LECTURE);
				sendLectureNotice(lrm);
			}
			insert.setLessonId(lrm.getLessonId());
			return new Result(1, "听课记录信息保存成功", new Date(), TypeConvert.convert(insert, LectureRecordsMapping.class));
		} else {
			return new Result(0, "数据信息不完整", new Date(), null);
		}
	}

	/**
	 * 听课记录信息修改
	 * 
	 * @param lrm
	 * @return
	 * @see com.tmser.tr.api.lecturerecords.service.MobileLecturerecordsService#updateLecture(com.tmser.tr.api.lecturerecords.vo.LectureRecordsMapping)
	 */
	@Override
	public Result updateLecture(String lectureinfo) {
		LectureRecordsMapping lrm = JSON.parseObject(lectureinfo, LectureRecordsMapping.class);
		if (lrm != null) {
			if (StringUtils.isEmpty(lrm.getClientId())) {
				return new Result(0, "客户端ID不能为空", new Date(), null);
			}
			if (StringUtils.isEmpty(lrm.getTopic())) {
				return new Result(0, "听课记录的主题不能为空", new Date(), null);
			}
			if (lrm.getLecturepeopleId() == null || lrm.getLecturepeopleId() == 0) {
				return new Result(0, "听课人不能为空", new Date(), null);
			}
			if (lrm.getId() == null) {
				return new Result(0, "听课记录id不能为空", new Date(), null);
			}
			if (lrm.getType() == 0) {
				if (StringUtils.isEmpty(lrm.getLessonId())) {
					return new Result(0, "课题lessonId不能为空", new Date(), null);
				}
				LessonInfo lessonInfo = new LessonInfo();
				lessonInfo.setUserId(lrm.getTeachingpeopleId());
				lessonInfo.setLessonId(lrm.getLessonId());
				lessonInfo.setSchoolYear(schoolYearUtilService.getCurrentSchoolYear());
				LessonInfo litemp = myPlanBookService.findOne(lessonInfo);
				if (litemp == null) {
					BookChapter bc = bookChapterService.findOne(lrm.getLessonId());
					if(bc!=null){
						Book book = bookService.findOne(bc.getComId());
						lessonInfo.setLessonName(lrm.getTopic());
						lessonInfo.setBookId(book.getComId());
						lessonInfo.setBookShortname(book.getFormatName());
						lessonInfo.setGradeId(lrm.getGradeId());
						lessonInfo.setSubjectId(lrm.getSubjectId());
						lessonInfo.setFasciculeId(book.getFasciculeId());
						lessonInfo.setTermId(schoolYearUtilService.getCurrentTerm());
						lessonInfo.setPhaseId(lrm.getPhaseId());
						lessonInfo.setOrgId(lrm.getOrgId());
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
						lessonInfo.setJiaoanCount(0);
						lessonInfo.setFansiCount(0);
						lessonInfo.setKejianCount(0);
						lessonInfo.setCrtId(lrm.getLecturepeopleId());
						lessonInfo.setCrtDttm(new Date());
						lessonInfo.setCurrentFrom(LessonInfo.FROM_ME);
						lessonInfo = myPlanBookService.save(lessonInfo);
						lrm.setTopicId(lessonInfo.getId());
					}else{
						return new Result(0, "找不到此课题信息", new Date(), null);
					}
				}else{
					lrm.setTopicId(litemp.getId());
				}
			}
			LectureRecords lRecords = lectureRecordsDao.get(lrm.getId());
			if (lRecords != null) {
				Set<String> oldResIdList = filterContent(lRecords.getLectureContent());
				Set<String> resIdList = filterContent(lrm.getLectureContent());
				for(String resId : oldResIdList){//删除旧的
					if(oldResIdList.contains(resId)){
						resIdList.remove(resId);
					}else{
						resourcesService.deleteResources(resId);
					}
				}
				for(String resId : resIdList){//更新新资源
					resourcesService.updateTmptResources(resId);
				}
				lrm.setLastupDttm(new Date());//最新修改时间
				lectureRecordsDao.update(lrm);
				lRecords = lectureRecordsDao.get(lrm.getId());//获取修改不后的听课记录
				if(lRecords.getIsEpub()==1&&lRecords.getType()==0){//只有发布的时候、校内听课记录才通知
					lrm.setEpub_time(new Date());//发布时间
					notifyChecked(lRecords.getTopicId(), ResTypeConstants.LECTURE);
					sendLectureNotice(lRecords);
				}
				if(lRecords.getType()==0){
					String lessonId = lessonInfoDao.get(lRecords.getTopicId()).getLessonId();
					lRecords.setLessonId(lessonId);
				}
				return new Result(1, "听课记录信息修改成功", new Date(), TypeConvert.convert(lRecords, LectureRecordsMapping.class));
			} else {
				return new Result(0, "此条听课记录已不存在", new Date(), null);
			}
		} else {
			return new Result(0, "数据信息不完整", new Date(), null);
		}
	}
	
	Set<String> filterContent(String content){
		Set<String> resIdList = new HashSet<String>();
		if(StringUtils.isNotEmpty(content)){
			Matcher m = RESID_PATTERN.matcher(content);
			while(m.find()) {
				resIdList.add(m.group(1));
			}
		}
		
		return resIdList;
	}
	/**
	 * 听课记录信息删除
	 * 
	 * @param lrm
	 * @return
	 * @see com.tmser.tr.api.lecturerecords.service.MobileLecturerecordsService#deleteLecture(com.tmser.tr.api.lecturerecords.vo.LectureRecordsMapping)
	 */
	@Override
	public Result deleteLecture(Integer lectureid) {
		if (lectureid != null) {
			LectureRecords lr = lectureRecordsDao.get(lectureid);
			if (lr == null) {
				return new Result(0, "此条听课记录不存在", new Date(), null);
			}
			lr.setIsDelete(true);
			lr.setLastupDttm(new Date());
			lectureRecordsDao.update(lr);
			if(lr.getTopicId()!=null){
				notifyDeleted(lr.getTopicId(), ResTypeConstants.LECTURE);
			}
			return new Result(1, "听课记录信息删除成功", new Date(), TypeConvert.convert(lectureRecordsDao.get(lectureid), LectureRecordsMapping.class));
		} else {
			return new Result(0, "听课记录id不能为空", new Date(), null);
		}
	}

	/**
	 * 听课记录信息分享
	 * 
	 * @param lrm
	 * @return
	 * @see com.tmser.tr.api.lecturerecords.service.MobileLecturerecordsService#shareLecture(com.tmser.tr.api.lecturerecords.vo.LectureRecordsMapping)
	 */
	@Override
	public Result shareLecture(Integer lectureid) {
		if (lectureid != null) {
			LectureRecords lr = lectureRecordsDao.get(lectureid);
			if (lr == null) {
				return new Result(0, "此条听课记录不存在", new Date(), null);
			}
			if (lr.getIsShare() == 0) {
				lr.setIsShare(1);
				lr.setShareTime(new Date());
				lectureRecordsDao.update(lr);
				lr = lectureRecordsDao.get(lectureid);
				if(lr.getType()==0){
					String lessonId = lessonInfoDao.get(lr.getTopicId()).getLessonId();
					lr.setLessonId(lessonId);
				}
				return new Result(1, "听课记录信息分享成功", new Date(), TypeConvert.convert(lr, LectureRecordsMapping.class));
			} else {
				lr.setIsShare(0);
				lectureRecordsDao.update(lr);
				lr = lectureRecordsDao.get(lectureid);
				if(lr.getType()==0){
					String lessonId = lessonInfoDao.get(lr.getTopicId()).getLessonId();
					lr.setLessonId(lessonId);
				}
				return new Result(1, "听课记录信息取消分享成功", new Date(), TypeConvert.convert(lr, LectureRecordsMapping.class));
			}
		} else {
			return new Result(0, "听课记录id不能为空", new Date(), null);
		}
	}
	/**
	 * 发送听课通知
	 * @param lr
	 */
	private void sendLectureNotice(LectureRecords lr){
		String title=lr.getLecturePeople()+"听了您的  "+lr.getTopic()+" 课题";
		UserSpace userSpace = (UserSpace)WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE); //用户空间
		Map<String,Object> noticeInfo = new HashMap<String, Object>();
		noticeInfo.put("tingkeren", lr.getLecturePeople());
		noticeInfo.put("teachingpeopleId", lr.getTeachingpeopleId());
		noticeInfo.put("lecturepeopleId", lr.getLecturepeopleId());
		noticeInfo.put("topic", lr.getTopic());
		noticeInfo.put("id", lr.getId());
		noticeInfo.put("resType", lr.getResType());
		noticeInfo.put("spaceName", userSpace.getSpaceName());
		NoticeUtils.sendNotice(NoticeType.LECTURERECORDS, title, lr.getLecturepeopleId(), lr.getTeachingpeopleId(), noticeInfo);
	}

	/**
	 * 增加一个听课记录回调
	 * @param resid
	 * @param restype
	 */
	private void notifyChecked(Integer resid,Integer restype){
		for(ActionCallback ccb : LectureRecordsService.callbackList){
			if(ccb.support(restype)){
				ccb.actionSuccessCallback(resid, restype);
			}
		}
	}

	/**
	 * 增加一个听课记录回调
	 * @param resid
	 * @param restype
	 */
	private void notifyDeleted(Integer resid,Integer restype){
		for(ActionCallback ccb : LectureRecordsService.callbackList){
			if(ccb.support(restype)){
				ccb.deleteSuccessCallback(resid);
			}
		}
	}

	/**
	 * 修改听课记录回复和评论的状态
	 * @param lectureid
	 * @param type
	 * @return
	 * @see com.tmser.tr.api.lecturerecords.service.MobileLecturerecordsService#uplecupsta(java.lang.Integer, java.lang.String)
	 */
	@Override
	public Result uplecupsta(Integer lectureid, String type) {
		if (lectureid != null && StringUtils.isNotEmpty(type)) {
			LectureRecords lr = lectureRecordsDao.get(lectureid);
			if (lr == null) {
				return new Result(0, "此条听课记录已经不存在", new Date(), null);
			}
			if("comment_up".equals(type) || "reply_up".equals(type)){
				if("comment_up".equals(type)){
					lr.setCommentUp(0);
				}else if("reply_up".equals(type)){
					lr.setReplyUp(0);
				}
				lectureRecordsDao.update(lr);
				if(lr.getType()==0){
					String lessonId = lessonInfoDao.get(lr.getTopicId()).getLessonId();
					lr.setLessonId(lessonId);
				}
				return new Result(1, "听课记录回复或者评论状态修改成功", new Date(), TypeConvert.convert(lr, LectureRecordsMapping.class));
			}else{
				return new Result(0, "传入参数type不合法", new Date(), null);
			}
		} else {
			return new Result(0, "接口请求参数信息不能为空", new Date(), null);
		}
	}


}
