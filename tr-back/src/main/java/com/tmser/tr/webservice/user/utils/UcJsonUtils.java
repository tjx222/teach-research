/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.webservice.user.utils;

import com.alibaba.fastjson.JSON;
import com.tmser.tr.webservice.user.data.ResultModel;

/**
 * <pre>
 * json 转换工具
 * </pre>
 *
 * @author tmser
 * @version $Id: JsonUtils.java, v 1.0 2015年11月30日 下午5:40:44 tmser Exp $
 */
public abstract class UcJsonUtils {

	public static final ResultModel parseUcResult(String json){
		return JSON.parseObject(json, ResultModel.class);
	}
}
