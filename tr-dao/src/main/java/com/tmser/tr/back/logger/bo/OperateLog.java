/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.back.logger.bo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.tmser.tr.common.bo.QueryObject;

 /**
 * 后台操作日志 Entity
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: OperateLog.java, v 1.0 2015-09-17 Generate Tools Exp $
 */
@SuppressWarnings("serial")
@Entity
@Table(name = OperateLog.TABLE_NAME)
public class OperateLog extends QueryObject {
	public static final String TABLE_NAME="bk_operate_log";
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;

	@Column(name="createtime")
	private Date createtime;

	@Column(name="user_id")
	private Integer userId;

	/**
	 *操作类型,枚举<新增，修改，删除，查找>
	 **/
	@Column(name="type",length=16)
	private String type;

	/**
	 *操作详情描述
	 **/
	@Column(name="message",length=2048)
	private String message;

	/**
	 *所属模块名称
	 **/
	@Column(name="module",length=16)
	private String module;

	/**
	 *操作时ip
	 **/
	@Column(name="ip",length=32)
	private String ip;

	@Column(name="caller_class",length=128)
	private String callerClass;

	@Column(name="thread_name",length=64)
	private String threadName;

    private Integer sysRoleId;
	
	public void setId(Long id){
		this.id = id;
	}

	public Long getId(){
		return this.id;
	}

	public void setCreatetime(Date createtime){
		this.createtime = createtime;
	}

	public Date getCreatetime(){
		return this.createtime;
	}

	public void setUserId(Integer userId){
		this.userId = userId;
	}

	public Integer getUserId(){
		return this.userId;
	}

	public void setType(String type){
		this.type = type;
	}

	public String getType(){
		return this.type;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return this.message;
	}

	public void setModule(String module){
		this.module = module;
	}

	public String getModule(){
		return this.module;
	}

	public void setIp(String ip){
		this.ip = ip;
	}

	public String getIp(){
		return this.ip;
	}


	public Integer getSysRoleId() {
		return sysRoleId;
	}

	public void setSysRoleId(Integer sysRoleId) {
		this.sysRoleId = sysRoleId;
	}

	public void setCallerClass(String callerClass){
		this.callerClass = callerClass;
	}

	public String getCallerClass(){
		return this.callerClass;
	}

	public void setThreadName(String threadName){
		this.threadName = threadName;
	}

	public String getThreadName(){
		return this.threadName;
	}


	
	@Override
	public boolean equals(final Object other) {
			if (!(other instanceof OperateLog))
				return false;
			OperateLog castOther = (OperateLog) other;
			return new EqualsBuilder().append(id, castOther.id).isEquals();
	}

	@Override
	public int hashCode() {
			return new HashCodeBuilder().append(id).toHashCode();
	}
}


