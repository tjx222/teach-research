/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.uc.bo;



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
import com.tmser.tr.manage.meta.bo.Menu;

 /**
 * 用户菜单 Entity
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: UserMenu.java, v 1.0 2015-02-12 Generate Tools Exp $
 */
@SuppressWarnings("serial")
@Entity
@Table(name = UserMenu.TABLE_NAME)
public class UserMenu extends QueryObject {
	public static final String TABLE_NAME="sys_user_menu";
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;

	@Column(name="sys_role_id")
	private Integer sysRoleId;

	@Column(name="menu_id")
	private Integer menuId;
	
	@Column(name="display")
	private Boolean display;

	/**
	 *图标名称，支持个性化命名
	 **/
	@Column(name="name")
	private String name;

	/**
	 *图标id，支持个性化
	 **/
	@Column(name="ico")
	private String ico;

	/**
	 *越小越靠前
	 **/
	@Column(name="sort")
	private Integer sort;

	@Column(name="user_id")
	private Integer userId;
	
	/**
	 * 菜单实体
	 */
	@Transient
	private Menu menu;
	
	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	public void setId(Integer id){
		this.id = id;
	}

	public Integer getId(){
		return this.id;
	}

	public void setSysRoleId(Integer sysRoleId){
		this.sysRoleId = sysRoleId;
	}

	public Integer getSysRoleId(){
		return this.sysRoleId;
	}

	public Boolean getDisplay() {
		return display;
	}

	public void setDisplay(Boolean display) {
		this.display = display;
	}

	public void setMenuId(Integer menuId){
		this.menuId = menuId;
	}

	public Integer getMenuId(){
		return this.menuId;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return this.name;
	}

	public void setIco(String ico){
		this.ico = ico;
	}

	public String getIco(){
		return this.ico;
	}

	public void setSort(Integer sort){
		this.sort = sort;
	}

	public Integer getSort(){
		return this.sort;
	}

	public void setUserId(Integer userId){
		this.userId = userId;
	}

	public Integer getUserId(){
		return this.userId;
	}

	@Override
	public boolean equals(final Object other) {
			if (!(other instanceof UserMenu))
				return false;
			UserMenu castOther = (UserMenu) other;
			return new EqualsBuilder().append(id, castOther.id).isEquals();
	}

	@Override
	public int hashCode() {
			return new HashCodeBuilder().append(id).toHashCode();
	}
}


