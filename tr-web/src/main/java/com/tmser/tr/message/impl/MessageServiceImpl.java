package com.tmser.tr.message.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import com.tmser.tr.message.Listener;
import com.tmser.tr.message.MessageService;
import com.tmser.tr.message.bo.Message;

/**
 * 
 * <pre>
 * 消息服务
 * </pre>
 *
 * @author wanzheng
 * @version $Id: MessageServiceImpl.java, v 1.0 May 10, 2015 11:35:26 AM wanzheng Exp $
 */
@Service
public class MessageServiceImpl implements MessageService,ApplicationContextAware{
	
	private ApplicationContext context;
	
	private Map<Integer,List<Listener>> messageListenersMap = new HashMap<Integer,List<Listener>>();
	
	private LinkedBlockingQueue<Message> messageQueue = new LinkedBlockingQueue<Message>();
	
	private MessageServiceImpl(){
		//初始化监听列表
		//initListenerList();
		//初始化消息监听线程
		initMessageHandleThread();
	}
	
	/**
	 * 初始化消息监听队列
	 */
	private void initListenerList() {
		Map<String, Listener> beansMap = context.getBeansOfType(Listener.class);
		Set<Entry<String, Listener>> entrySet = beansMap.entrySet();
		Iterator<Entry<String, Listener>> it = entrySet.iterator();
		while(it.hasNext()){
			Entry<String, Listener> entry = it.next();
			addListener(entry.getValue());
		}
	}

	/**
	 * 初始化消息处理线程
	 */
	private void initMessageHandleThread() {
		Runnable handleMessage = new Runnable() {
			@Override
			public void run() {
				synchronized(messageListenersMap){
					Message message = messageQueue.poll();
					List<Listener> list = messageListenersMap.get(message.getMessageType());
					for(Listener l:list){
						l.handle(message);
					}
				}
				
				
			}
		};
		
	}

	/**
	 * 新增消息监听
	 * @param messageType
	 * @param l
	 * @see com.tmser.tr.message.MessageService#addListener(java.lang.Integer, com.tmser.tr.message.Listener)
	 */
	@Override
	public  void addListener(Listener l) {
		Integer messageType = l.getMessageType();
		synchronized(messageListenersMap){
			List<Listener> list = messageListenersMap.get(messageType);
			if(list==null){
				list =new ArrayList<Listener>();
				messageListenersMap.put(messageType, list);
			}
			list.add(l);
		}
		
	}
	
	/**
	 * 新增消息
	 * @param message
	 * @see com.tmser.tr.message.MessageService#addMessage(com.tmser.tr.message.bo.Message)
	 */
	@Override
	public void addMessage(Message<? extends Serializable> message) {
		messageQueue.offer(message);
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		context=applicationContext;
	}

}
