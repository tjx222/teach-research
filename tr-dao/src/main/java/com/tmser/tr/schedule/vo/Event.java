/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.schedule.vo;

import java.io.Serializable;
import java.util.Map;

/**
 * <pre>
 *
 * </pre>
 *
 * @author 延方
 * @version $Id: Event.java, v 1.0 2015年4月9日 上午11:39:40 延方 Exp $
 */
public class Event implements Serializable{
    
     /**
	 * <pre>
	 *
	 * </pre>
	 */
	private static final long serialVersionUID = -8198501806119546235L;
	
	private Map<String, String> event;

	public  Map<String, String> getEvent() {
		return event;
	}

	public void setEvent( Map<String, String> event) {
		this.event = event;
	}
     
}
