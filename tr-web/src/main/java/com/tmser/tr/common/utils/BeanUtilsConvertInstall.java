/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.common.utils;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.beanutils.converters.SqlTimestampConverter;

/**
 * <pre>
 *
 * </pre>
 *
 * @author ljh
 * @version $Id: BeanUtilsConvertInstall.java, v 1.0 2017年10月19日 下午5:27:10 ljh
 *          Exp $
 */
public class BeanUtilsConvertInstall {

  static {
    ConvertUtils.register(new DateConverter(null), java.util.Date.class);
    ConvertUtils.register(new SqlTimestampConverter(null), java.sql.Timestamp.class);
  }
}
