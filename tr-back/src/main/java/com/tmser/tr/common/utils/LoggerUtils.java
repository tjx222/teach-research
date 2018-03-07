/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.helpers.MessageFormatter;

import com.tmser.tr.back.logger.LoggerModule;
import com.tmser.tr.back.logger.LoggerType;

/**
 * <pre>
 * 系统操作日志工具
 * </pre>
 *
 * @author tmser
 * @version $Id: LoggerUtils.java, v 1.0 2015年9月17日 下午5:15:40 tmser Exp $
 */
public abstract class LoggerUtils {

	private static final Logger dblogger = LoggerFactory.getLogger("backDbLogger");
	
	private static final String format(String message,Object[] argumentArray){
		return  argumentArray != null ? MessageFormatter.arrayFormat(message, argumentArray)
	              .getMessage() : message;
	}
	
	/**
	 * 新增日志
	 * @param msg
	 */
	public static final void insertLogger(String msg,Object... arguments){
		dblogger.info(format(msg,arguments),LoggerType.INSERT.getCname());
	}
	
	/**
	 * 新增日志
	 * @param msg
	 * @param module 所属模块
	 */
	public static final void insertLogger(LoggerModule module,String msg,Object... arguments){
		dblogger.info(format(msg,arguments),LoggerType.INSERT.getCname(),module.getCname());
	}
	
	/**
	 * 更新日志
	 * @param msg
	 */
	public static final void updateLogger(String msg,Object... arguments){
		dblogger.info(format(msg,arguments),LoggerType.UPDATE.getCname());
	}
	
	public static final void updateLogger(LoggerModule module,String msg,Object... arguments){
		dblogger.info(format(msg,arguments),LoggerType.UPDATE.getCname(),module.getCname());
	}
	
	/**
	 * 删除日志
	 * @param msg
	 */
	public static final void deleteLogger(String msg,Object... arguments){
		dblogger.info(format(msg,arguments),LoggerType.DELETE.getCname());
	}
	
	public static final void deleteLogger(LoggerModule module,String msg,Object... arguments){
		dblogger.info(format(msg,arguments),LoggerType.DELETE.getCname(),module.getCname());
	}
	
	/**
	 * 查找日志
	 * @param msg
	 */
	public static final void searchLogger(String msg,Object... arguments){
		dblogger.info(format(msg,arguments),LoggerType.SEARCH.getCname());
	}
	
	public static final void searchLogger(LoggerModule module,String msg,Object... arguments){
		dblogger.info(format(msg,arguments),LoggerType.SEARCH.getCname(),module.getCname());
	}
	
	/**
	 * 导入日志
	 * @param msg
	 */
	public static final void importLogger(String msg,Object... arguments){
		dblogger.info(format(msg,arguments),LoggerType.IMPORT.getCname());
	}
	
	public static final void importLogger(LoggerModule module,String msg,Object... arguments){
		dblogger.info(format(msg,arguments),LoggerType.IMPORT.getCname(),module.getCname());
	}
	
	
	/**
	 * 导出日志
	 * @param msg
	 */
	public static final void exportLogger(String msg,Object... arguments){
		dblogger.info(format(msg,arguments),LoggerType.EXPORT.getCname());
	}
	
	public static final void exportLogger(LoggerModule module,String msg,Object... arguments){
		dblogger.info(format(msg,arguments),LoggerType.EXPORT.getCname(),module.getCname());
	}
	
	
	/**
	 * 日志
	 * @param msg
	 */
	public static final void logger(LoggerType type,String msg,Object... arguments){
		dblogger.info(format(msg,arguments),type.getCname());
	}
	
	public static final void logger(LoggerModule module,LoggerType type,String msg,Object... arguments){
		dblogger.info(format(msg,arguments),type.getCname(),module.getCname());
	}
	
}
