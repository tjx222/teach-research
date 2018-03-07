/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.lecturerecords.service;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.tmser.tr.lecturerecords.bo.LectureReply;
import com.tmser.tr.lecturerecords.service.ActionCallback;
import com.tmser.tr.common.service.BaseService;

/**
 * 听课记录回复 服务类
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: LectureReply.java, v 1.0 2015-04-27 Generate Tools Exp $
 */

public interface LectureReplyService extends BaseService<LectureReply, Integer>{
	//设置增加听课记录回复
	List<ActionCallback> replycallbackList = new CopyOnWriteArrayList<ActionCallback>();
	
	//设置增加听课记录回复的回复
	List<ActionCallback> reply2callbackList = new CopyOnWriteArrayList<ActionCallback>();
	
	/**
	 * 保存听课记录回复
	 * @param lectureReply
	 */
	public void saveReply(LectureReply lectureReply);
}
