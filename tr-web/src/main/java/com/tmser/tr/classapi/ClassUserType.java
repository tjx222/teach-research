/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.classapi;


/**
 * <pre>
 *
 * </pre>
 *
 * @author yangchao
 * @version $Id: ClassUserType.java, v 1.0 2016年9月20日 下午2:29:02 yangchao Exp $
 */
public enum ClassUserType {
	
	MASTER("主持人",1), SPEAKER("主讲人", 2),NORMAL("参会者",8),MANAGER("监课人",32);
	
	private  String name;
	
    private  Integer value;
    
    public String getName() {
        return name;
    }
    
    public Integer getValue() {
        return value;
    }
    
    private ClassUserType(String name, int value) {
        this.name = name;
        this.value = value;
    }
    /**
     * 根据数值得到名称
     * @param value
     * @return
     */
    public static String getName(int value) {
        for (ClassUserType c : ClassUserType.values()) {
            if (c.getValue() == value) {
                return c.name;
            }
        }
        return null;
    }
}
