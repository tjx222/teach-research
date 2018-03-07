/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.manage.meta.bo;



import javax.persistence.*;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.tmser.tr.common.bo.BaseObject;

 /**
 * 系统入口 Entity
 * <pre>
 *
 * </pre>
 *
 * @author zpp
 * @version $Id: SysEntrance.java, v 1.0 2015-10-14 zpp Exp $
 */
@SuppressWarnings("serial")
@Entity
@Table(name = SysEntrance.TABLE_NAME)
public class SysEntrance extends BaseObject {
	public static final String TABLE_NAME="sys_entrance";
	
		/**
	 *使用area_sys_role_id 作为主键
	 **/
	@Id
	@Column(name="id")
	private Integer id;

	@Column(name="url",length=128)
	private String url;


	public void setId(Integer id){
		this.id = id;
	}

	public Integer getId(){
		return this.id;
	}

	public void setUrl(String url){
		this.url = url;
	}

	public String getUrl(){
		return this.url;
	}


	
	@Override
	public boolean equals(final Object other) {
			if (!(other instanceof SysEntrance))
				return false;
			SysEntrance castOther = (SysEntrance) other;
			return new EqualsBuilder().append(id, castOther.id).isEquals();
	}

	@Override
	public int hashCode() {
			return new HashCodeBuilder().append(id).toHashCode();
	}
}


