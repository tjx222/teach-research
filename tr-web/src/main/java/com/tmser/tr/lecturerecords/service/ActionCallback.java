package com.tmser.tr.lecturerecords.service;

/**
 * 
 * @author 
 * 动作之后信息回调公共接口
 */
public interface ActionCallback {
	/**
	 * 动作之后回调
	 * @param resid
	 * @param restype
	 */
	void actionSuccessCallback(Integer resid,Integer restype);
	
	/**
	 * 是否支持回调
	 * @param restype
	 * @return
	 */
	boolean support(Integer restype);
	
	/**
	 * 删除听课意见成功后回调
	 * @param resid
	 */
	void  deleteSuccessCallback(Integer resid);
}
