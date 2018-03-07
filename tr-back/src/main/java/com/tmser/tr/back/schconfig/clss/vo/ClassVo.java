/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.back.schconfig.clss.vo;

import java.io.Serializable;
import java.util.List;

import com.tmser.tr.schconfig.clss.bo.SchClass;

/**
 * <pre>
 *  班级管理vo
 * </pre>
 *
 * @author tmser
 * @version $Id: ClassVo.java, v 1.0 2016年7月25日 上午10:52:29 3020mt Exp $
 */
public class ClassVo implements Serializable{

	/**
	 * <pre>
	 *
	 * </pre>
	 */
	private static final long serialVersionUID = 1L;
	
	
	private List<SchClass> classes;


	/** 
	 * Getter method for property <tt>classes</tt>. 
	 * @return property value of classes 
	 */
	public List<SchClass> getClasses() {
		return classes;
	}


	/**
	 * Setter method for property <tt>classes</tt>.
	 * @param classes value to be assigned to property classes
	 */
	public void setClasses(List<SchClass> classes) {
		this.classes = classes;
	}
	
	
}
