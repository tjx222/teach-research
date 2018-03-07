package com.tmser.tr.message;

import java.io.Serializable;

import com.tmser.tr.message.bo.Message;

public interface Listener <T extends Serializable> {

	/**
	 * 获取消息类型
	 * @return
	 */
	public Integer getMessageType();
	
	/**
	 * 消息处理接口
	 * @param message
	 */
	public void handle(Message<T> message);
}
