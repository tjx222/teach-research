/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * <p>
 * 防止重复提交注解，用于方法上<br/>
 * 在表单页面的进入方法和表单数据处理的方法上都注解UseToken()，
 * 其中表单数据处理方法value 值设置为true. @UseToken(true)
 * 同时需要在表单的页面的form 中增加 <ui:token/> 标签<br/>
 * 
 * </p>
 *
 * @author tmser
 * @version $Id: UseToken.java, v 1.0 2015年2月1日 下午11:02:42 tmser Exp $
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface UseToken {
	/**
	 * Whether check the token.
	 */
	boolean value() default false;
}
