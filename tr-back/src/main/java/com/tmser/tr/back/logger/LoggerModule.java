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
 * @version $Id: LoggerModule.java, v 1.0 2015年9月17日 下午5:54:02 tmser Exp $
 */
public enum LoggerModule {
	/**
	 *  枚举顺序：子模块先于父模块， 相同父级模块集中放置
	 */
	
	UC("uc","用户管理"),
	ZZJG("back.zzjg","组织机构"),
	JXTX("back.jxtx","教学体系"),
	YYGL("back.yygl","应用管理"),
	JSGL("back.jsgl","角色管理"),
	YHGL("back.yhgl","用户管理"),
	ZYGL("back.zygl","资源管理"),
	PTGG("back.ptgg","平台公告"),
	XXSY("back.xxsy","学校首页"),
	ZYJG("back.zyjg","资源监管"),
	RZGG("back.rzgg","日志公告"),
	YJFK("back.yjfk","意见反馈"),
	AQJK("back.aqjk","安全监控"),
	YUNYINGGl("back.yunying","运营管理"),
	TASK("back.task","定时任务"),
	MONITOR("back.monitor","系统监控"),
	
	SYNCSCHOOL("syncschool" ,"同步学校"),
	
	OTHER("_other_","未知模块");

	private String cname;
	private String code;
	
	LoggerModule(String code,String cname){
		this.cname = cname;
		this.code = code;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	public static final LoggerModule parse(String className){
		LoggerModule[] roles = LoggerModule.values();
		for(int i=0;i<roles.length;i++){
			if(className != null && className.contains(roles[i].code)){
				 return roles[i];
			}
		}
		
		return OTHER;
	}
	
}
