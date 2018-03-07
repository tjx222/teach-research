package com.tmser.tr.bjysdk.common;

/**
 * Created by yfwang on 2017/11/15.
 */
public interface Marshaller<T, R> {

  public T marshall(R input) throws ResponseParseException;

}
