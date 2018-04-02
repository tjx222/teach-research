package com.tmser.tr.message;

import java.io.Serializable;

import com.tmser.tr.message.bo.Message;

/**
 * 
 * <pre>
 * 消息服务service
 * </pre>
 *
 * @author tmser
 * @version $Id: MessageService.java, v 1.0 May 10, 2015 11:28:57 AM tmser
 *          Exp $
 */
public interface MessageService {

  /**
   * 新增消息类型
   * 
   * @param messageType
   * @param l
   */
  public void addListener(Listener l);

  /**
   * 新增消息
   * 
   * @param message
   */
  public void addMessage(Message<? extends Serializable> message);
}
