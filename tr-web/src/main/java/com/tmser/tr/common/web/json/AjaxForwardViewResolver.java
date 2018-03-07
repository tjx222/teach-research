/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.common.web.json;

import java.util.Locale;

import org.springframework.core.Ordered;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.AbstractCachingViewResolver;
import org.springframework.web.servlet.view.InternalResourceView;

/**
 * <pre>
 *
 * </pre>
 *
 * @author 3020mt
 * @version $Id: AjaxForwardViewResover.java, v 1.0 2016年11月18日 下午1:21:08 3020mt Exp $
 */
public class AjaxForwardViewResolver extends AbstractCachingViewResolver implements Ordered{
	
	public static final String FORWARD_URL_PREFIX = "forward:";
	
	private String contentType;
	
	private int order = Integer.MAX_VALUE;
	
	/**
	 * Set the content type for all views.
	 * <p>May be ignored by view classes if the view itself is assumed
	 * to set the content type, e.g. in case of JSPs.
	 */
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	/**
	 * Return the content type for all views, if any.
	 */
	protected String getContentType() {
		return this.contentType;
	}

	
	/**
	 * Set the order in which this {@link org.springframework.web.servlet.ViewResolver}
	 * is evaluated.
	 */
	public void setOrder(int order) {
		this.order = order;
	}

	/**
	 * Return the order in which this {@link org.springframework.web.servlet.ViewResolver}
	 * is evaluated.
	 */
	@Override
	public int getOrder() {
		return this.order;
	}
	
	/**
	 * @param viewName
	 * @param locale
	 * @return
	 * @throws Exception
	 * @see org.springframework.web.servlet.view.AbstractCachingViewResolver#loadView(java.lang.String, java.util.Locale)
	 */
	@Override
	protected View loadView(String viewName, Locale locale) throws Exception {
		if (viewName.startsWith(FORWARD_URL_PREFIX)) {
			String contentType = getContentType();
			String forwardUrl = viewName.substring(FORWARD_URL_PREFIX.length());
			InternalResourceView view = new InternalResourceView(forwardUrl);
			if (contentType != null) {
				view.setContentType(contentType);
			}
			return view;
		}
		return null;
	}

}
