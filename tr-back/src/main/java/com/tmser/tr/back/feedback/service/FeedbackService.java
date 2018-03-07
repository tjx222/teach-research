package com.tmser.tr.back.feedback.service;

import com.tmser.tr.common.service.BaseService;
import com.tmser.tr.feedback.bo.Recieve;

public interface FeedbackService extends BaseService<Recieve, Integer> {
	
	/**
	 * 获取反馈详情
	 * @param recieve
	 * @return
	 */
	Recieve getRecieveDetail(Recieve recieve);


}
