package com.tmser.tr.bjysdk.common;

/**
 * Utils for common coding.
 */
public class CodingUtils {

    public static void assertParameterNotNull(Object param, String paramName) {
        if (param == null) {
            throw new NullPointerException("ParameterIsNull,paramName= "+paramName);
        }
    }
}
