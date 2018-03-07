/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.common.web.listener;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;
import com.tmser.tr.common.utils.WebThreadLocalUtils;

/**
 * <pre>
 *
 * </pre>
 *
 * @author tmser
 * @version $Id: DefaultSessionListener.java, v 1.0 2015年2月12日 上午10:41:25 tmser Exp $
 */
public class RequestContextListener implements ServletRequestListener {

	@Override
	public void requestInitialized(ServletRequestEvent requestEvent) {
		if (!(requestEvent.getServletRequest() instanceof HttpServletRequest)) {
			throw new IllegalArgumentException(
					"Request is not an HttpServletRequest: " + requestEvent.getServletRequest());
		}
		HttpServletRequest request = (HttpServletRequest) requestEvent.getServletRequest();
		WebThreadLocalUtils.setRequest(request);
	}

	@Override
	public void requestDestroyed(ServletRequestEvent requestEvent) {
		HttpServletRequest request = WebThreadLocalUtils.getRequest();
		if (request != null) {
			WebThreadLocalUtils.clear();
		}
	}

}
