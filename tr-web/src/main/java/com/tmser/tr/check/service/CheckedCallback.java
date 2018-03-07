package com.tmser.tr.check.service;

/**
 * 
 * @author 
 * 查阅信息回调
 */
public interface CheckedCallback {
	/**
	 * 查阅结果回调
	 * @param resid
	 * @param restype
	 */
	void checkSuccessCallback(Integer resid,Integer restype,String content);
	
	/**
	 * 是否支持查阅信息
	 * @param restype
	 * @return
	 */
	boolean support(Integer restype);
}
