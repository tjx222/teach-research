/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.managerecord.service;

import java.io.Serializable;

/**
 * <pre>
 *
 * </pre>
 *
 * @author sysc
 * @version $Id: Manager.java, v 1.0 2015年5月12日 上午10:09:55 sysc Exp $
 */
public class ManagerVO implements Serializable{
	/**
	 * <pre>
	 *
	 * </pre>
	 */
	private static final long serialVersionUID = -6149233439277473587L;
	
	/**
	 * 前端显示名称  查阅课件  必填项
	 */
	private String name;
	/**
	 * ResTypeConstants中的类型  在getType中实现
	 */
	private Integer[] type;
	/**
	 * 当前用户（包括其所有的身份所对应的）教师提交的数量
	 */
	private Integer submitNum;//第一个
	/**
	 * 当前用户阅览的数量  非必填  如果填写此项 就按照填写的结果显示  否则在接口中实现（当追加新的统计对象的时候可以不用修改接口代码）
	 */
	private Integer checkNum;//第二个
	/**
	 * 当前用户评论的数量  非必填  如果填写此项 就按照填写的结果显示  否则在接口中实现（当追加新的统计对象的时候可以不用修改接口代码）
	 */
	private Integer commentNum;//第三个
	
	/**
	 * 分享数
	 */
	private Integer shareNum;
	
	/**
	 * totalNum上显示的文字（因为显示的文字与角色相关）
	 */
	private String title;
	
	/**
	 * 入口地址uri
	 */
	private String uri;
	
	/** 
	 * Getter method for property <tt>name</tt>. 
	 * @return property value of name 
	 */
	public String getName() {
		return name;
	}
	/**
	 * Setter method for property <tt>name</tt>.
	 * @param name value to be assigned to property name
	 */
	public void setName(String name) {
		this.name = name;
	}
	public Integer[] getType() {
		return type;
	}
	public void setType(Integer[] type) {
		this.type = type;
	}
	public Integer getSubmitNum() {
		return submitNum;
	}
	public void setSubmitNum(Integer submitNum) {
		this.submitNum = submitNum;
	}
	public Integer getCheckNum() {
		return checkNum;
	}
	public void setCheckNum(Integer checkNum) {
		this.checkNum = checkNum;
	}
	public Integer getCommentNum() {
		return commentNum;
	}
	public void setCommentNum(Integer commentNum) {
		this.commentNum = commentNum;
	}
	
	public Integer getShareNum() {
		return shareNum;
	}
	public void setShareNum(Integer shareNum) {
		this.shareNum = shareNum;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	
}
