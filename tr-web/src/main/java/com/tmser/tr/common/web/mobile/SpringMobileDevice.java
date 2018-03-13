/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.common.web.mobile;

import com.tmser.tr.common.service.Device;

/**
 * <pre>
 *
 * </pre>
 *
 * @author tmser
 * @version $Id: SpringDevice.java, v 1.0 2016年1月14日 下午5:57:16 tmser Exp $
 */
public class SpringMobileDevice implements Device {

  /**
   * @return
   * @see com.tmser.tr.common.service.Device#isNormal()
   */
  @Override
  public boolean isNormal() {
    return false;
  }

  /**
   * @return
   * @see com.tmser.tr.common.service.Device#isMobile()
   */
  @Override
  public boolean isMobile() {
    return true;
  }

  /**
   * @return
   * @see com.tmser.tr.common.service.Device#isTablet()
   */
  @Override
  public boolean isTablet() {
    return false;
  }

}
