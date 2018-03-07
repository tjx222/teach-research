package com.tmser.tr.lecturerecords.service;

import org.springframework.beans.factory.InitializingBean;

public abstract class AbstractLecReplyActionCallback implements ActionCallback, InitializingBean{
	/**
	 * 注册回调函数，是否有新的听课记录回复，容器启动的时候，初始化
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		LectureReplyService.replycallbackList.add(this);//增加听课记录回复
	}
	
	/**
	 * @param resid
	 * @see com.tmser.tr.lecturerecords.service.ActionCallback#deleteSuccessCallback(java.lang.Integer)
	 */
	@Override
	public void deleteSuccessCallback(Integer resid) {
		// TODO Auto-generated method stub
		
	}

}
