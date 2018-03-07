/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.back.logger;

/**
 * <pre>
 *
 * </pre>
 *
 * @author tmser
 * @version $Id: LoggerType.java, v 1.0 2015年9月17日 下午5:05:22 tmser Exp $
 */
public enum LoggerType {

	INSERT("新增"), UPDATE("修改"), DELETE("删除"), SEARCH("查询"), IMPORT(
			"导入"), EXPORT("导出"), OTHER("其他");

	private String cname;

	LoggerType(String cname) {
		this.cname = cname;
	}

	public String getCname() {
		return cname;
	}

}
