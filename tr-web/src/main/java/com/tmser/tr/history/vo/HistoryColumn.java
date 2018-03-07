/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.history.vo;

import java.io.Serializable;

/**
 * <pre>
 *
 * </pre>
 *
 * @author 3020mt
 * @version $Id: HistoryColumn.java, v 1.0 2016年5月18日 下午4:01:13 3020mt Exp $
 */
@SuppressWarnings("serial")
public class HistoryColumn implements Comparable<HistoryColumn>,Serializable,Cloneable{
	public HistoryColumn(String code,String name,String[] countDesc,Integer sort){
		this.code = code;
		this.name = name;
		this.sort = sort;
		this.countDesc =  countDesc;
	}
	/**
	 * 当前栏目code
	 */
	private String code;

	/**
	 * 栏目名称
	 */
	private String name;
	
	/**
	 * 栏目名称
	 */
	private String[] countDesc;
	
	/**
	 * 统计数
	 */
	private Integer[] count;
	
	/** 
	 * Getter method for property <tt>countDesc</tt>. 
	 * @return property value of countDesc 
	 */
	public String[] getCountDesc() {
		return countDesc;
	}


	/**
	 * Setter method for property <tt>countDesc</tt>.
	 * @param countDesc value to be assigned to property countDesc
	 */
	public void setCountDesc(String[] countDesc) {
		this.countDesc = countDesc;
	}
	/**
	 * 排序
	 */
	private Integer sort;
	

	/** 
	 * Getter method for property <tt>code</tt>. 
	 * @return property value of code 
	 */
	public String getCode() {
		return code;
	}


	/**
	 * Setter method for property <tt>code</tt>.
	 * @param code value to be assigned to property code
	 */
	public void setCode(String code) {
		this.code = code;
	}


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


	/** 
	 * Getter method for property <tt>sort</tt>. 
	 * @return property value of sort 
	 */
	public Integer getSort() {
		return sort;
	}


	/**
	 * Setter method for property <tt>sort</tt>.
	 * @param sort value to be assigned to property sort
	 */
	public void setSort(Integer sort) {
		this.sort = sort;
	}


	/** 
	 * Getter method for property <tt>count</tt>. 
	 * @return property value of count 
	 */
	public Integer[] getCount() {
		return count;
	}


	/**
	 * Setter method for property <tt>count</tt>.
	 * @param count value to be assigned to property count
	 */
	public void setCount(Integer[] count) {
		this.count = count;
	}

	public HistoryColumn copy(){
		HistoryColumn o = null;
		try {
			o = (HistoryColumn) clone();
			o.setCountDesc(this.getCountDesc().clone());
		} catch (Exception e) {
			//DO NOTHING 
		}
		return o != null ? o : new HistoryColumn(this.code,this.name,
				this.countDesc != null ? this.countDesc.clone() : null,this.sort);
	}

	/**
	 * @param o
	 * @return
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(HistoryColumn o) {
		if(o == null || o.sort > sort){
			return -1;
		}
		if(o.sort < sort ){
			return 1;
		}
		return 0;
	}
}
