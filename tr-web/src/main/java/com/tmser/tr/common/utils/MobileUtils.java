/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.common.utils;

import com.tmser.tr.common.service.Device;
import com.tmser.tr.utils.SpringContextHolder;

/**
 * <pre>
 *  设备判定工具类
 * </pre>
 *
 * @author tmser
 * @version $Id: MobileUtils.java, v 1.0 2016年1月14日 下午5:36:02 tmser Exp $
 */
public class MobileUtils {
	
	private static Device device = null;
	
	public static boolean isNormal(){
		intDevice();
		return device != null ? device.isNormal() : true;
	}

	/**
	 * True if this device is a mobile device such as an Apple iPhone or an Nexus One Android.
	 * Could be used by a pre-handle interceptor to redirect the user to a dedicated mobile web site.
	 * Could be used to apply a different page layout or stylesheet when the device is a mobile device.
	 */
	public static boolean isMobile(){
		intDevice();
		return device != null ? device.isMobile() : false;
	}

	/**
	 * True if this device is a tablet device such as an Apple iPad or a Motorola Xoom.
	 * Could be used by a pre-handle interceptor to redirect the user to a dedicated tablet web site.
	 * Could be used to apply a different page layout or stylesheet when the device is a tablet device.
	 */
	public static boolean isTablet(){
		intDevice();
		return device != null ? device.isTablet() : false;
	}
	
	public static void intDevice() {
		if(device == null){
			device = SpringContextHolder.getBeanDefaultNull(Device.class);
		}
	}

}
