package com.tmser.tr.message.bo;

import java.io.Serializable;

public class Message<T extends Serializable> {
	
	/**
	 * 消息类型
	 */
	private Integer messageType;
	
	/**
	 * 消息内容
	 */
	private T messageContent;

	public Integer getMessageType() {
		return messageType;
	}

	public void setMessageType(Integer messageType) {
		this.messageType = messageType;
	}

	public T getMessageContent() {
		return messageContent;
	}

	public void setMessageContent(T messageContent) {
		this.messageContent = messageContent;
	}
	
}
