package com.tmser.tr.api.utils;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.tmser.tr.common.page.PageList;

/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */

/**
 * <pre>
 * 类型转换工具类
 * </pre>
 *
 * @author tmser
 * @version $Id: TypeConvert.java, v 1.0 2016年4月18日 下午5:52:57 3020mt Exp $
 */
public abstract class TypeConvert {

	/**
	 * 转换类型
	 * 
	 * @param from
	 *            要转类型对象
	 * @param to
	 *            要转换到的对象类型
	 * @return
	 */
	public final static <T> T convert(Object from, Class<T> to) {
		// Null is just null.
		if (from == null) {
			return null;
		}

		// Can we cast? Then just do it.
		if (to.isAssignableFrom(from.getClass())) {
			return to.cast(from);
		}

		T t = null;
		try {
			t = to.newInstance();
			BeanUtils.copyProperties(from, t);
		} catch (Exception e) {
			// do nothing
		}

		return t;
	}

	/**
	 * 转换PageList
	 * 
	 * @param from
	 * @param to
	 * @return
	 */
	public final static <T> PageList<T> convert(PageList<?> from, Class<T> to) {
		if (from == null) {
			return null;
		}

		List<T> datalist = new ArrayList<T>(from.getDatalist().size());
		for (Object u : from.getDatalist()) {
			datalist.add(convert(u, to));
		}
		return new PageList<T>(datalist, from.getPage());
	}

	/**
	 * 转换List
	 * 
	 * @param from
	 * @param to
	 * @return
	 */

	public final static <T> List<T> convert(List<?> from, Class<T> to) {
		if (from == null) {
			return null;
		}

		List<T> cus = new ArrayList<T>(from.size());
		for (Object o : from) {
			cus.add(convert(o, to));
		}
		return cus;
	}

}
