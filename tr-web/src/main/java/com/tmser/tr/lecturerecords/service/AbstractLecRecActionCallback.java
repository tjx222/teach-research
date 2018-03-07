package com.tmser.tr.lecturerecords.service;

import org.springframework.beans.factory.InitializingBean;

public abstract class AbstractLecRecActionCallback implements ActionCallback, InitializingBean{

	/**
	 * 注册回调函数，检测查阅状态是否更新，容器启动的时候，初始化
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		LectureRecordsService.callbackList.add(this);//增加听课记录
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
