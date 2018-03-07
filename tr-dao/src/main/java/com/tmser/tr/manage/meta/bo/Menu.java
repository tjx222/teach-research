/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.manage.meta.bo;



import java.util.ArrayList;
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

import com.tmser.tr.common.bo.QueryObject;

 /**
 * 系统入口模块 Entity
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: Menu.java, v 1.0 2015-02-12 Generate Tools Exp $
 */
@SuppressWarnings("serial")
@Entity
@Table(name = Menu.TABLE_NAME)
public class Menu extends QueryObject {
	public static final String TABLE_NAME="sys_menu";
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Column(name="name")
	private String name;

	@Column(name="parentid")
	private Integer parentid;

	@Column(name="ico_id")
	private Integer icoId;

	/**
	 *权限码
	 **/
	@Column(name="code")
	private String code;
	
	@Column(name="url")
	private String url;
	
	/**
	 * 简单描述
	 */
	@Column(name="descs",length=128)
	private String desc;
	
	/**
	 *0 不固定
            1 固定
	 **/
	@Column(name="fixed")
	private Integer fixed;

	@Column(name="sort")
	private Integer sort;

	@Column(name="target")
	private String target;
	
	@Column(name="sys_role_id")
	private Integer sysRoleId;
	
	/**
	 * 是否支持pc 
	 */
	@Column(name="is_normal")
	private Boolean isNormal;
	
	/**
	 * 是否支持移动 
	 */
	@Column(name="is_mobile")
	private Boolean isMobile;
	
	/**
	 * 是否支持平板
	 */
	@Column(name="is_tablet")
	private Boolean isTablet;
	

	@Transient 
	private Icon icon;
	
	
	
	/** 
	 * Getter method for property <tt>isNormal</tt>. 
	 * @return property value of isNormal 
	 */
	public Boolean getIsNormal() {
		return isNormal;
	}


	/**
	 * Setter method for property <tt>isNormal</tt>.
	 * @param isNormal value to be assigned to property isNormal
	 */
	public void setIsNormal(Boolean isNormal) {
		this.isNormal = isNormal;
	}


	/** 
	 * Getter method for property <tt>isMobile</tt>. 
	 * @return property value of isMobile 
	 */
	public Boolean getIsMobile() {
		return isMobile;
	}


	/**
	 * Setter method for property <tt>isMobile</tt>.
	 * @param isMobile value to be assigned to property isMobile
	 */
	public void setIsMobile(Boolean isMobile) {
		this.isMobile = isMobile;
	}


	/** 
	 * Getter method for property <tt>isTablet</tt>. 
	 * @return property value of isTablet 
	 */
	public Boolean getIsTablet() {
		return isTablet;
	}


	/**
	 * Setter method for property <tt>isTablet</tt>.
	 * @param isTablet value to be assigned to property isTablet
	 */
	public void setIsTablet(Boolean isTablet) {
		this.isTablet = isTablet;
	}

	@Transient 
	private List<Menu> subMenus;
	
	public List<Menu> getSubMenus() {
		return subMenus;
	}


	public String getDesc() {
		return desc;
	}


	public void setDesc(String desc) {
		this.desc = desc;
	}


	public void setSubMenus(List<Menu> subMenus) {
		this.subMenus = subMenus;
	}
	
	public void putMenu(Menu menu){
		if(this.subMenus == null){
			this.subMenus = new ArrayList<Menu>();
		}
		this.subMenus.add(menu);
	}

	public void setId(Integer id){
		this.id = id;
	}

	public Integer getId(){
		return this.id;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return this.name;
	}

	public void setParentid(Integer parentid){
		this.parentid = parentid;
	}

	public Integer getParentid(){
		return this.parentid;
	}

	/** 
	 * Getter method for property <tt>target</tt>. 
	 * @return property value of target 
	 */
	public String getTarget() {
		return target;
	}

	/**
	 * Setter method for property <tt>target</tt>.
	 * @param target value to be assigned to property target
	 */
	public void setTarget(String target) {
		this.target = target;
	}

	public void setIcoId(Integer icoId){
		this.icoId = icoId;
	}

	public Integer getIcoId(){
		return this.icoId;
	}

	public void setCode(String code){
		this.code = code;
	}

	public String getCode(){
		return this.code;
	}

	public void setFixed(Integer fixed){
		this.fixed = fixed;
	}

	public Integer getFixed(){
		return this.fixed;
	}

	public void setSort(Integer sort){
		this.sort = sort;
	}

	public Integer getSort(){
		return this.sort;
	}

	public void setSysRoleId(Integer sysRoleId){
		this.sysRoleId = sysRoleId;
	}

	public Integer getSysRoleId(){
		return this.sysRoleId;
	}

	public Icon getIcon() {
		return icon;
	}

	public void setIcon(Icon icon) {
		this.icon = icon;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public boolean equals(final Object other) {
			if (!(other instanceof Menu))
				return false;
			Menu castOther = (Menu) other;
			return new EqualsBuilder().append(id, castOther.id).isEquals();
	}

	@Override
	public int hashCode() {
			return new HashCodeBuilder().append(id).toHashCode();
	}
}


