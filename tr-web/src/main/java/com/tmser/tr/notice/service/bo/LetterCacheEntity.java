package com.tmser.tr.notice.service.bo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

import org.springframework.util.CollectionUtils;
import org.springframework.web.context.request.async.DeferredResult;

import com.tmser.tr.notice.bo.JyLetter;

/**
 * 
 * <pre>
 * 站内信缓存对象
 * </pre>
 *
 * @author wanzheng
 * @version $Id: LetterCache.java, v 1.0 May 12, 2015 1:57:24 PM wanzheng Exp $
 */
public class LetterCacheEntity {
	
	/**
	 * 站内信等待返回
	 */
	List<DeferredResult<Object>> results = new ArrayList<DeferredResult<Object>>();
	
	/**
	 * 站内信消息
	 */
	Queue<JyLetter> letterQueue = new ConcurrentLinkedDeque<JyLetter>();
	
	/**
	 * 新增异步返回结果
	 * @param result
	 */
	public synchronized void addResult(final DeferredResult<Object> result){
		JyLetter letter = letterQueue.poll();
		//已有站内信，直接返回
		if(letter==null){
			result.setResult(letter);
		}else{//没有站内信，加入等待队列
			
			final LetterCacheEntity self = this;
			/**
			 * 超时从等待队列中移除
			 */
			Runnable timout = new Runnable() {
				
				@Override
				public void run() {
					synchronized (self) {
						//防止移除前，已被其它线程移除
						try{
							results.remove(result);
						}catch(Exception e){
							
						}
						
					}
					
				}
			};
			result.onTimeout(timout);
			results.add(result);
		}
	}
	
	/**
	 * 新增站内信
	 * @param letter
	 */
	public synchronized void addLetter(JyLetter letter){
		//如果没有等待请求，直接放入站内信对象
		if(CollectionUtils.isEmpty(results)){
			letterQueue.offer(letter);
		}else{//逐一发送站内信
			Iterator<DeferredResult<Object>> it = results.iterator();
			while(it.hasNext()){
				DeferredResult<Object> result = it.next();
				if(!result.isSetOrExpired()){
					result.setResult(letter);
					it.remove();
				}
			}
		}
	}
}
