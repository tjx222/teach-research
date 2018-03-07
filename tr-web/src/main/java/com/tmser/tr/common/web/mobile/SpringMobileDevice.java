/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.common.web.mobile;

import org.springframework.mobile.device.DeviceUtils;
import org.springframework.mobile.device.site.SitePreference;
import org.springframework.mobile.device.site.SitePreferenceUtils;
import com.tmser.tr.common.service.Device;
import com.tmser.tr.common.utils.WebThreadLocalUtils;

/**
 * <pre>
 *
 * </pre>
 *
 * @author tmser
 * @version $Id: SpringDevice.java, v 1.0 2016年1月14日 下午5:57:16 tmser Exp $
 */
public class SpringMobileDevice implements Device{

	/**
	 * @return
	 * @see com.tmser.tr.common.service.Device#isNormal()
	 */
	@Override
	public boolean isNormal() {
		try {
			return SitePreferenceUtils
					.getCurrentSitePreference(WebThreadLocalUtils.getRequest()) == SitePreference.NORMAL;
		} catch (Exception e) {
			return true;
		}
	}

	/**
	 * @return
	 * @see com.tmser.tr.common.service.Device#isMobile()
	 */
	@Override
	public boolean isMobile() {
		try {
			return !isNormal() && (SitePreferenceUtils
					.getCurrentSitePreference(WebThreadLocalUtils.getRequest()) == SitePreference.MOBILE || 
					DeviceUtils.getCurrentDevice(WebThreadLocalUtils.getRequest()).isMobile());
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * @return
	 * @see com.tmser.tr.common.service.Device#isTablet()
	 */
	@Override
	public boolean isTablet() {
		try {
			return !isNormal() && (SitePreferenceUtils
					.getCurrentSitePreference(WebThreadLocalUtils.getRequest()) == SitePreference.TABLET ||
							DeviceUtils.getCurrentDevice(WebThreadLocalUtils.getRequest()).isTablet());
		} catch (Exception e) {
			return false;
		}
	}

}
