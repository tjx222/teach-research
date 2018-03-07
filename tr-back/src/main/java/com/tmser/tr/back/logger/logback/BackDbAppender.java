/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.back.logger.logback;

import java.util.Date;

import ch.qos.logback.classic.spi.CallerData;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.UnsynchronizedAppenderBase;

import com.tmser.tr.back.logger.LoggerModule;
import com.tmser.tr.back.logger.bo.OperateLog;
import com.tmser.tr.back.logger.service.OperateLogService;
import com.tmser.tr.common.utils.RequestUtils;
import com.tmser.tr.common.utils.WebThreadLocalUtils;
import com.tmser.tr.uc.utils.CurrentUserContext;
import com.tmser.tr.utils.SpringContextHolder;

/**
 * <pre>
 * 后台操作-数据库日志记录器
 * </pre>
 *
 * @author tmser
 * @version $Id: BackDbAppender.java, v 1.0 2015年9月17日 下午2:18:58 tmser Exp $
 */
public class BackDbAppender extends UnsynchronizedAppenderBase<ILoggingEvent>{
	
	private OperateLogService operateLogService;
    static final StackTraceElement EMPTY_CALLER_DATA = CallerData.naInstance();
	/**
	 * @param eventObject
	 * @see ch.qos.logback.core.UnsynchronizedAppenderBase#append(java.lang.Object)
	 */
	@Override
	protected void append(ILoggingEvent eventObject) {
		 OperateLog logger = new OperateLog();
		 bindLoggingEventArgumentsWithLogger(logger, eventObject.getArgumentArray());
		 bindLoggingEventWithInsertLogger(logger, eventObject);
		 try {
			logger.setUserId(CurrentUserContext.getCurrentUserId());
		} catch (Exception e1) {//no current user
			logger.setUserId(0);
		}
		 logger.setIp(RequestUtils.getIpAddr(WebThreadLocalUtils.getRequest()));
		 
		 if(operateLogService == null){
			 operateLogService = SpringContextHolder.getBean(OperateLogService.class);
		 }
		 try {
			operateLogService.save(logger);
		} catch (Exception e) {
			 addError("Appender [" + name + "] failed to save to database.", e);
		}
	}
	
	void bindLoggingEventWithInsertLogger(OperateLog logger,ILoggingEvent event) {
		StackTraceElement caller = extractFirstCaller(event.getCallerData());
		logger.setCreatetime(new Date(event.getTimeStamp()));
		logger.setMessage(event.getFormattedMessage());
		logger.setCallerClass(caller.getClassName());
		logger.setThreadName(event.getThreadName());
		if(logger.getModule()  == null){
			logger.setModule(LoggerModule.parse(logger.getCallerClass()).getCname());
		}
	}
	
	void bindLoggingEventArgumentsWithLogger(OperateLog logger,Object[] argArray){
		 int arrayLen = argArray != null ? argArray.length : 0;
		 if(arrayLen > 0 ) {
		      logger.setType(String.valueOf(argArray[0]));
		      if(arrayLen > 1)
		    	  logger.setModule(String.valueOf(argArray[1]));
		      
		 }
	}
	
	  private StackTraceElement extractFirstCaller(StackTraceElement[] callerDataArray) {
		    StackTraceElement caller = EMPTY_CALLER_DATA;
		    if(hasAtLeastOneNonNullElement(callerDataArray))
		      caller = callerDataArray[1];
		    return caller;
		  }
	  
	  private boolean hasAtLeastOneNonNullElement(StackTraceElement[] callerDataArray) {
		    return callerDataArray != null && callerDataArray.length > 1 && callerDataArray[1] != null;
		  }

}
