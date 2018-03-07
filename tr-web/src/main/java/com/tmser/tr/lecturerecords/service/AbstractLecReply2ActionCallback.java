package com.tmser.tr.lecturerecords.service;

import org.springframework.beans.factory.InitializingBean;

public abstract class AbstractLecReply2ActionCallback implements ActionCallback, InitializingBean{
	/**
	 * 注册回调函数，是否有新的听课记录回复的回复，容器启动的时候，初始化
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		LectureReplyService.reply2callbackList.add(this);
	}
}
