/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.uc.bo;



import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.tmser.tr.common.bo.BaseObject;

 /**
 * 应用信息 Entity
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: App.java, v 1.0 2016-01-11 Generate Tools Exp $
 */
@SuppressWarnings("serial")
@Entity
@Table(name = App.TABLE_NAME)
public class App extends BaseObject {
	public static final String TABLE_NAME="sys_app";
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;

	/**
	 *应用名称
	 **/
	@Column(name="appname",length=128)
	private String appname;

	@Column(name="appid",length=64)
	private String appid;

	/**
	 *应用访问密匙
	 **/
	@Column(name="appkey",length=64)
	private String appkey;

	/**
	 *登录名前缀
	 **/
	@Column(name="login_pre",length=16)
	private String loginPre;
	
	/**
	 *应用基础地址
	 **/
	@Column(name="login_url",length=256)
	private String loginUrl;

	/**
	 *应用基础地址
	 **/
	@Column(name="url",length=128)
	private String url;

	/**
	 *联系人电话
	 **/
	@Column(name="phone",length=16)
	private String phone;

	/**
	 *联系人
	 **/
	@Column(name="contact",length=20)
	private String contact;

	/**
	 *app类型
	 **/
	@Column(name="type")
	private String type;
	
	@Transient
	private List<AppParam> param;
	
	public void setId(Integer id){
		this.id = id;
	}

	public Integer getId(){
		return this.id;
	}

	public void setAppname(String appname){
		this.appname = appname;
	}

	public String getAppname(){
		return this.appname;
	}


	public void setAppid(String appid){
		this.appid = appid;
	}

	/** 
	 * Getter method for property <tt>param</tt>. 
	 * @return property value of param 
	 */
	public List<AppParam> getParam() {
		return param;
	}

	/**
	 * Setter method for property <tt>param</tt>.
	 * @param param value to be assigned to property param
	 */
	public void setParam(List<AppParam> param) {
		this.param = param;
	}

	public String getAppid(){
		return this.appid;
	}

	public void setAppkey(String appkey){
		this.appkey = appkey;
	}

	public String getAppkey(){
		return this.appkey;
	}

	public void setLoginPre(String loginPre){
		this.loginPre = loginPre;
	}

	public String getLoginPre(){
		return this.loginPre;
	}

	public void setUrl(String url){
		this.url = url;
	}

	public String getUrl(){
		return this.url;
	}

	public void setPhone(String phone){
		this.phone = phone;
	}

	public String getPhone(){
		return this.phone;
	}

	public void setContact(String contact){
		this.contact = contact;
	}

	public String getContact(){
		return this.contact;
	}

	public void setType(String type){
		this.type = type;
	}

	public String getType(){
		return this.type;
	}

	public String getLoginUrl() {
		return loginUrl;
	}

	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}

	
	@Override
	public boolean equals(final Object other) {
			if (!(other instanceof App))
				return false;
			App castOther = (App) other;
			return new EqualsBuilder().append(id, castOther.id).isEquals();
	}

	@Override
	public int hashCode() {
			return new HashCodeBuilder().append(id).toHashCode();
	}
}


