package com.tmser.tr.bjysdk;

import com.alibaba.fastjson.JSON;
import com.tmser.tr.bjysdk.common.Marshaller;
import com.tmser.tr.bjysdk.common.ResponseParseException;
import com.tmser.tr.bjysdk.common.ResponseParser;
import com.tmser.tr.bjysdk.model.BJYMtgResult;
import com.tmser.tr.utils.StringUtils;

/**
 * Created by yfwang on 2017/11/8.
 */
public class JSONResponseParser<T extends BJYMtgResult> implements ResponseParser<T>, Marshaller<String, T> {

    private Class<T> modelClass;

    public JSONResponseParser(Class<T> modelClass) {
        assert (modelClass != null);
        this.modelClass = modelClass;
    }

    @Override
    public String marshall(T input) throws ResponseParseException {
        return null;
    }

    @Override
    public T parse(String content) throws ResponseParseException {
        T rs = null;
        try {
            if (StringUtils.isEmpty(content)) {
                throw new ResponseParseException("content is null");
            }
            BJYMtgResult mtgResult = JSON.parseObject(content, BJYMtgResult.class);
            String data = mtgResult.getData();
            if (StringUtils.isNotEmpty(data)) {
                rs = JSON.parseObject(data,modelClass);
            }else{
                rs= modelClass.newInstance();
            }
            if (rs instanceof BJYMtgResult) {
                rs.setCode(mtgResult.getCode());
                rs.setMsg(mtgResult.getMsg());
            }
            return rs;
        } catch (Exception e) {
            throw new ResponseParseException("content parse failed");
        }
    }

}
