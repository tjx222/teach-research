/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.lecturerecords.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmser.tr.common.ResTypeConstants;
import com.tmser.tr.common.dao.BaseDAO;
import com.tmser.tr.common.service.AbstractService;
import com.tmser.tr.common.utils.WebThreadLocalUtils;
import com.tmser.tr.lecturerecords.bo.LectureRecords;
import com.tmser.tr.lecturerecords.dao.LectureRecordsDao;
import com.tmser.tr.lecturerecords.service.ActionCallback;
import com.tmser.tr.lecturerecords.service.LectureRecordsService;
import com.tmser.tr.lessonplan.bo.LessonInfo;
import com.tmser.tr.manage.meta.MetaUtils;
import com.tmser.tr.manage.meta.bo.Book;
import com.tmser.tr.manage.meta.bo.BookChapter;
import com.tmser.tr.manage.meta.service.BookChapterHerperService;
import com.tmser.tr.manage.meta.service.BookChapterService;
import com.tmser.tr.manage.meta.service.BookService;
import com.tmser.tr.manage.meta.vo.BookLessonVo;
import com.tmser.tr.manage.resources.service.ResourcesService;
import com.tmser.tr.myplanbook.service.MyPlanBookService;
import com.tmser.tr.notice.constants.NoticeType;
import com.tmser.tr.notice.service.impl.NoticeUtils;
import com.tmser.tr.uc.bo.UserSpace;
import com.tmser.tr.uc.utils.SessionKey;
/**
 * 听课记录 服务实现类
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: LectureRecords.java, v 1.0 2015-03-30 Generate Tools Exp $
 */
@Service
@Transactional
public class LectureRecordsServiceImpl extends AbstractService<LectureRecords, Integer> implements LectureRecordsService {

	@Autowired
	private LectureRecordsDao lectureRecordsDao;

	@Autowired
	private MyPlanBookService myPlanBookService;
	
	@Autowired
	private BookService bookService;
	
	@Autowired
	private BookChapterService bookChapterService;
	
	@Autowired
	private ResourcesService resourcesService;
	
	private final static Pattern RESID_PATTERN = Pattern.compile("/jy/manage/res/download/(\\w{32})");

	/**
	 * @return
	 * @see com.tmser.tr.common.service.BaseService#getDAO()
	 */
	@Override
	public BaseDAO<LectureRecords, Integer> getDAO() {
		return lectureRecordsDao;
	}

	/**
	 * @param lectureRecords
	 * @see com.tmser.tr.lecturerecords.service.LectureRecordsService#saveLectureRecords(com.tmser.tr.lecturerecords.bo.LectureRecords)
	 */
	@Override
	public void saveOrupdateLectureRecords(LectureRecords lectureRecords) {
		if(lectureRecords.getTopicId() == null && lectureRecords.getType() == 0 && lectureRecords.getIsEpub() == 1){
			//获取当前用户空间
			UserSpace userSpace = (UserSpace)WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE);
			//学年
			Integer schoolYear = (Integer)WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SCHOOLYEAR);
			//学期
			Integer termId = (Integer)WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_TERM);
			LessonInfo lessonInfo = new LessonInfo();
			lessonInfo.setLessonId(lectureRecords.getLessonId());
			lessonInfo.setUserId(lectureRecords.getTeachingpeopleId());
			lessonInfo.setSchoolYear(schoolYear);
			LessonInfo temp = myPlanBookService.findOne(lessonInfo);
			BookChapter bc = bookChapterService.findOne(lectureRecords.getLessonId());
			lectureRecords.setTopic(bc.getChapterName());
			if(temp==null && bc != null){//不存在则新增
				Book book = bookService.findOne(bc.getComId());
				lessonInfo.setLessonName(lectureRecords.getTopic());
				lessonInfo.setBookId(book.getComId());
				lessonInfo.setBookShortname(book.getFormatName());
				lessonInfo.setGradeId(lectureRecords.getGradeId());
				lessonInfo.setSubjectId(lectureRecords.getSubjectId());
				lessonInfo.setFasciculeId(book.getFasciculeId());
				lessonInfo.setTermId(termId);
				lessonInfo.setPhaseId(MetaUtils.getPhaseMetaProvider().getMetaRelationshipByPhaseId(book.getPhaseId()).getId());
				lessonInfo.setOrgId(lectureRecords.getOrgId());
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
				lessonInfo.setCrtId(userSpace.getUserId());
				lessonInfo.setCrtDttm(new Date());
				lessonInfo.setCurrentFrom(LessonInfo.FROM_ME);
				lessonInfo = myPlanBookService.save(lessonInfo);
				lectureRecords.setTopicId(lessonInfo.getId());
				lectureRecords.setPhaseId(lessonInfo.getPhaseId());
			}else{
				if(temp!=null){
					lectureRecords.setTopicId(temp.getId());
					lectureRecords.setPhaseId(temp.getPhaseId());
				}
			}
		}
		
		if(lectureRecords.getId()==null){
			Set<String> resIdList = filterContent(lectureRecords.getLectureContent());
			for(String resId : resIdList){
				resourcesService.updateTmptResources(resId);
			}
			this.save(lectureRecords);
			if(lectureRecords.getIsEpub()==1&&lectureRecords.getType()==0){//只有发布的时候、校内听课记录才通知
				notifyChecked(lectureRecords.getTopicId(), ResTypeConstants.LECTURE);
				sendLectureNotice(lectureRecords);
			}
		}else{
			LectureRecords oldRecords = lectureRecordsDao.get(lectureRecords.getId());
			Set<String> oldResIdList = filterContent(oldRecords.getLectureContent());
			Set<String> resIdList = filterContent(lectureRecords.getLectureContent());
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
			this.update(lectureRecords);
			if(lectureRecords.getIsEpub()==1&&lectureRecords.getType()==0){//只有发布的时候、校内听课记录才通知
				notifyChecked(lectureRecords.getTopicId(), ResTypeConstants.LECTURE);
				sendLectureNotice(lectureRecords);
			}
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
	 * 删除听课意见
	 * @param lr
	 */
	@Override
	public LectureRecords deleteOrShare(Integer id,String state){
		LectureRecords lr = lectureRecordsDao.get(id);
		if("分享".equals(state)){
			lr.setIsShare(1);
			lr.setShareTime(new Date());//分享时间
			lr.setLastupDttm(new Date());//最后修改时间
		}else if("取消分享".equals(state)){
			lr.setIsShare(0);
			lr.setLastupDttm(new Date());//最后修改时间
			
		}else if("删除".equals(state)){
			lr.setIsDelete(true);
			lr.setLastupDttm(new Date());//最后修改时间
			//校外听课记录没有topicid，无需删除
			if(lr.getTopicId()!=null){
				notifyDeleted(lr.getTopicId(), ResTypeConstants.LECTURE);
			}
		}
		lectureRecordsDao.update(lr);
		return lr;
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
		NoticeUtils.sendNotice(NoticeType.LECTURERECORDS, title, userSpace.getUserId(), lr.getTeachingpeopleId(), noticeInfo);
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


	@Autowired
	private BookChapterHerperService bchService; //获取书——课题service

	/**
	 * 得到书籍目录排序集合
	 * @param lessonInfoList
	 * @return
	 */
	@Override
	public List<BookLessonVo> getBookCatalogs(List<LessonInfo> lessonInfoList){
		//获取当前用户空间
		UserSpace userSpace = (UserSpace)WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE);

		//从缓存中获取课题map
		Map<String,BookLessonVo> lessonsMap = new HashMap<String, BookLessonVo>(bchService.getBookChapterMapByComId(userSpace.getBookId()));
		List<BookLessonVo> lessonlist = new ArrayList<BookLessonVo>();//除根节点外的节点集合
		Map<Integer,BookLessonVo> rootMap = new HashMap<Integer, BookLessonVo>();
		for(LessonInfo temp : lessonInfoList){
			lessonlist = getLessonLevel(temp.getLessonId(),lessonlist,lessonsMap,rootMap);
		}
		List<Integer> orderValueList = new ArrayList<Integer>(rootMap.keySet());
		Collections.sort(orderValueList);//对排序值排序
		//将顶层章节加入到课题集合里
		for(int i=0;i<orderValueList.size();i++){
			lessonlist.add(rootMap.get(orderValueList.get(i)));
		}
		return lessonlist;
	}

	/**
	 * 递归获取课题的层级集合
	 * @param lessonId
	 * @param lessonlist
	 * @param lessonsMap
	 * @return
	 */
	private List<BookLessonVo> getLessonLevel(String lessonId,List<BookLessonVo> lessonlist,Map<String,BookLessonVo> lessonsMap,Map<Integer,BookLessonVo> rootMap){
		BookLessonVo bookLessonVo = lessonsMap.get(lessonId);
		if(bookLessonVo!=null){
			if(!"-1".equals( bookLessonVo.getParentId())){
				lessonlist.add(bookLessonVo);
				lessonsMap.remove(lessonId);
				lessonlist = getLessonLevel(bookLessonVo.getParentId(),lessonlist,lessonsMap,rootMap);
			}else{
				//将顶层的章节的排序值和章节bean对应提取到map里
				rootMap.put(bookLessonVo.getOrderNum(), bookLessonVo);
			}
		}
		return lessonlist;
	}

	/**
	 * 根据课题id获取课题的扩展信息
	 * @param lessonId
	 * @return
	 * @see com.tmser.tr.myplanbook.service.MyPlanBookService#getLessonInfoByLessonId(java.lang.String)
	 */
	@Override
	public LessonInfo getLessonInfoByLessonId(LessonInfo info) {
		//学年
		Integer schoolYear = (Integer)WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SCHOOLYEAR);
		info.setSchoolYear(schoolYear);
		LessonInfo lessonInfo = myPlanBookService.findOne(info);
		return lessonInfo;
	}

	/**
	 * 获取已提交或未提交的听课记录
	 * @param isSubmit
	 * @return
	 * @see com.tmser.tr.lecturerecords.service.LectureRecordsService#findIsOrNotSubmitRecords(java.lang.Boolean)
	 */
	@Override
	public List<LectureRecords> findIsOrNotSubmitRecords(Integer isSubmit) {
		UserSpace userSpace = (UserSpace)WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE);
		Integer schoolYear = (Integer)WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SCHOOLYEAR);
		LectureRecords lr = new LectureRecords();
		lr.setLecturepeopleId(userSpace.getUserId());
		lr.setSchoolYear(schoolYear);
		lr.setIsSubmit(isSubmit);
		lr.setIsDelete(false);
		lr.setIsEpub(1);
		lr.addOrder(" crtDttm desc ");
		List<LectureRecords> recordsList = findAll(lr); 
		return recordsList;
	}

	/**
	 * 提交听课记录
	 * @param lessonPlanIdsStr
	 * @param isSubmit
	 * @throws Exception
	 * @see com.tmser.tr.lecturerecords.service.LectureRecordsService#submitOrUnsubmitLectureRecords(java.lang.String, java.lang.Boolean)
	 */
	@Override
	public void submitLectureRecords(String lessonPlanIdsStr) throws Exception {
		LectureRecords lr = new LectureRecords();
		Date currentDate = new Date();
		lr.setIsSubmit(1);
		for(String id:lessonPlanIdsStr.split(",")){
			lr.setId(Integer.valueOf(id));
			lr.setSubmitTime(currentDate);
			lr.setLastupDttm(currentDate);
			update(lr);
		}
	}

	/**
	 * 取消提交听课记录
	 * @param lessonPlanIdsStr
	 * @return
	 * @throws Exception
	 * @see com.tmser.tr.lecturerecords.service.LectureRecordsService#UnsubmitLectureRecords(java.lang.String)
	 */
	@Override
	public Integer unsubmitLectureRecords(String lessonPlanIdsStr)
			throws Exception {
		Date currentDate = new Date();
		Integer flag = 0;
		for(String id:lessonPlanIdsStr.split(",")){
			LectureRecords lr = findOne(Integer.valueOf(id));
			if(lr.getIsScan()==null || lr.getIsScan()==0){ //未查阅
				lr.setIsSubmit(0);
				lr.setSubmitTime(null);
				lr.setLastupDttm(currentDate);
				update(lr);
			}else{
				flag = 1;
			}
		}
		return flag;
	}
	
	
	
	
}
