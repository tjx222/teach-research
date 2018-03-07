/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.lecturerecords.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmser.tr.common.ResTypeConstants;
import com.tmser.tr.common.dao.BaseDAO;
import com.tmser.tr.common.service.AbstractService;
import com.tmser.tr.common.utils.WebThreadLocalUtils;
import com.tmser.tr.lecturerecords.bo.LectureReply;
import com.tmser.tr.lecturerecords.dao.LectureRecordsDao;
import com.tmser.tr.lecturerecords.dao.LectureReplyDao;
import com.tmser.tr.lecturerecords.service.ActionCallback;
import com.tmser.tr.lecturerecords.service.LectureReplyService;
import com.tmser.tr.notice.constants.NoticeType;
import com.tmser.tr.notice.service.impl.NoticeUtils;
import com.tmser.tr.uc.bo.UserSpace;
import com.tmser.tr.uc.utils.SessionKey;
/**
 * 听课记录回复 服务实现类
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: LectureReply.java, v 1.0 2015-04-27 Generate Tools Exp $
 */
@Service
@Transactional
public class LectureReplyServiceImpl extends AbstractService<LectureReply, Integer> implements LectureReplyService {

	@Autowired
	private LectureReplyDao lectureReplyDao;
	
	@Autowired
	private LectureRecordsDao lectureRecordsDao;
	
	/**
	 * @return
	 * @see com.tmser.tr.common.service.BaseService#getDAO()
	 */
	@Override
	public BaseDAO<LectureReply, Integer> getDAO() {
		return lectureReplyDao;
	}

	/**
	 * @param lectureReply
	 * @see com.tmser.tr.lecturereply.service.LectureReplyService#saveReply(com.tmser.tr.lecturerecords.bo.LectureReply)
	 */
	@Override
	public void saveReply(LectureReply lectureReply) {
		this.save(lectureReply);
		if(lectureReply.getParentId()==null){//只在授课人回复的时候触发
			notifyChecked(lectureReply.getResId(), ResTypeConstants.LECTURE);
			sendLectureReplyNotice(lectureReply);
		}else{
			//听课记录回复的回复回调函数
			notifyReply2(lectureReply.getResId(), ResTypeConstants.LECTURE);
			sendLectureReplyToReplyNotice(lectureReply);
		}
	}
	/**
	 * 发送听课回复通知
	 * @param lr
	 */
	private void sendLectureReplyNotice(LectureReply lr){
		String topic=lectureRecordsDao.get(lr.getResId()).getTopic();
		String title=lr.getUsername()+"对您  "+topic+" 课题的听课记录进行了回复";
		UserSpace userSpace = (UserSpace)WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE); //用户空间
		Map<String,Object> noticeInfo = new HashMap<String, Object>();
		noticeInfo.put("tname",lr.getUsername());//授课人姓名
		noticeInfo.put("teacherId",lr.getTeacherId());//授课人id
		noticeInfo.put("authorId",lr.getAuthorId());//听课人id 
		noticeInfo.put("id",lr.getResId());//听课记录id 
		noticeInfo.put("topic",topic);//听课记录id 
		noticeInfo.put("spaceName", userSpace.getSpaceName());
		lr.getId();
		NoticeUtils.sendNotice(NoticeType.LECTUREREPLY, title, userSpace.getUserId(), lr.getAuthorId(), noticeInfo);
	}
	/**
	 * 发送听课回复的回复通知
	 * @param lr
	 */
	private void sendLectureReplyToReplyNotice(LectureReply lr){
		Integer id=lectureReplyDao.get(lr.getParentId()).getResId();
		String topic=lectureRecordsDao.get(id).getTopic();
		Integer teacherId=lectureReplyDao.get(lr.getParentId()).getTeacherId();
		String title=lr.getUsername()+"回复了您  "+topic+" 课题的听课记录的回复";
		UserSpace userSpace = (UserSpace)WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE); //用户空间
		Map<String,Object> noticeInfo = new HashMap<String, Object>();
		
		noticeInfo.put("tname",lr.getUsername());
		noticeInfo.put("teacherId",teacherId);//授课人id
		noticeInfo.put("authorId",lr.getUserId());//听课人id  
		noticeInfo.put("id",id);//听课记录id 
		noticeInfo.put("topic",topic);//听课记录id 
		noticeInfo.put("spaceName", userSpace.getSpaceName());
		NoticeUtils.sendNotice(NoticeType.LECTUREREPLYTOREPLY, title, userSpace.getUserId(), teacherId, noticeInfo);
	}
	/**
	 * 听课记录新增回复回调
	 * @param resid
	 * @param restype
	 */
	private void notifyChecked(Integer resid,Integer restype){
		for(ActionCallback ccb : LectureReplyService.replycallbackList){
			if(ccb.support(restype)){
				ccb.actionSuccessCallback(resid, restype);
			}
		}
	}
	
	/**
	 * 听课记录新增回复的回复进行回调
	 * @param resid
	 * @param restype
	 */
	private void notifyReply2(Integer resid,Integer restype){
		for(ActionCallback ccb : LectureReplyService.reply2callbackList){
			if(ccb.support(restype)){
				ccb.actionSuccessCallback(resid, restype);
			}
		}
	}
}
