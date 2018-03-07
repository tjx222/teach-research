package com.tmser.tr.bjysdk.common;

/**
 * Created by yfwang on 2017/11/15.
 */

public interface ResponseParser<T> {
    /**
     * Converts the result from stream to a java object.
     * @param resultStream The stream of the result.
     * @return The java Type T object that the result stands for.
     * @throws ResponseParseException Failed to parse the result.
     */
    public T parse(String content) throws ResponseParseException;
}
