package com.tmser.tr.check.service;

import org.springframework.beans.factory.InitializingBean;

public abstract class AbstractCheckedCallback implements CheckedCallback, InitializingBean{

	/**
	 * 注册回调函数，检测查阅状态是否更新
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		CheckInfoService.callbackList.add(this);
	}

	/**
	 * @param restype
	 * @return
	 * @see com.tmser.tr.check.service.CheckedCallback#support(java.lang.Integer)
	 */
	@Override
	public boolean support(Integer restype) {
		return getType().equals(restype);
	}
	
	protected abstract Integer getType();
}
